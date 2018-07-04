/**bomTree初始化**/
function initBomTree(){
		var setting = {
		view : {
			// removeHoverDom: removeHoverDom,
			showIcon : false,
			showLine: false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : zTreeOnClick
		}
	};
	$.ajax({
				url : "productManageController/selectSelectProductBomList.do",
				dataType : "json",
				cache : false,
				success : function(json) {
					if(null != json && "" != json){
					/**如果没权限但有数据，就显示按钮**/
					$("#cfx").show();
					$("#cdj").show();
					$("#cdb").show();
					/**如果没权限但有数据，就显示按钮**/
					$(document).ready(function() {
								$.fn.zTree.init($("#bomtree"), setting, json);
								var treeObj = $.fn.zTree.getZTreeObj("bomtree");
								treeObj.expandAll(true); // 默认展开全部
								//获取节点  
                                 //返回一个根节点
                                 var node1 = treeObj.getNodesByFilter(function (node1) { return node1.nodeStage == 1 }, true);
                                 var node2 = treeObj.getNodesByFilter(function (node2) { return node2.nodeStage == 2 }, true);
                                 var node3 = treeObj.getNodesByFilter(function (node3) { return node3.nodeStage == 3 }, true);
                                 if(null != node2){
                                    node1 = null;
                                 }
                                 if (null != node3) {
         							node2 = null;
         						 }
                                 if(null != node1){
                                   treeObj.selectNode(node1);//选择该节点
                                   updateNode(node1);//更新该节点
                                   showAttr(node1);
                                   showPart(node1.id);
                                 }
                                 if(null != node2){
                                   treeObj.selectNode(node2);//选择该节点
                                   updateNode(node2);//更新该节点
                                   showAttr(node2);
                                   showPart(node2.id);
                                 }
                                 if(null != node3){
                                     treeObj.selectNode(node3);//选择该节点
                                     updateNode(node3);//更新该节点
                                     showAttr(node3);
                                     showBomPart(node3);
                                 }
			            });
					
					}else{
					$(document).ready(function() {
								$.fn.zTree.init($("#bomtree"), setting, json);
								var treeObj = $.fn.zTree.getZTreeObj("bomtree");
			            });
					}
				},
				error : function() {
					// alert("数据连接异常,注册失败！");
				}

			});
}
function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
};
//元器件数据展示
function showPart(id,pageNo){
 loadProperties();//国际化
 var productId = '';
 var excelName = '';
  if("" != id){
    productId=id;
  }else{
    zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
    productId=treeNode[0].id;
  }
  if(isNaN(pageNo)){
    		pageNo = 0;
   }
   var addBasic = '1';
   pageNo = parseInt(addBasic)+parseInt(pageNo);
  $.ajax({
     url: 'productManageController/selectPartDataByBom.do',
     data:{'productId':productId,'excelName':excelName,'pageNo':pageNo},
     dataType: 'json',
     cache: false,
     success: function(json){
     	var html = '';
     	html += '<tr>';
//        html += '<th>'+'<input type="checkbox" id="checkAll" onclick="checkAll()"/>'+'</th>';
        for(var x=0;x<json.title.length;x++){
        	if("zh" == json.lang){
        	   html += '<th>'+json.title[x].showName+'</th>';
        	}else{
        	  html += '<th>'+json.title[x].englishName+'</th>';
        	}
        }
        if("zh" == json.lang){
        	 html += '<th>'+'数量'+'</th>';
        	  html += '<th>'+'更多信息'+'</th>';
        }else{
            html += '<th>'+'Number'+'</th>';
            html += '<th>'+'More'+'</th>';
        }
        html += '</tr>';
        if(null == json.list){
           html +='<tr>';
		   html +='<td colspan="8" align="center">'+$.i18n.prop("noData")+'</td>';
		   html +='</tr>';
		   $("#showPartdata").html(html);
		   $("#deleteDataFrombom").hide();
		   $("#switchBom").hide();//BOM下拉框切换
		   $(".BOMname").hide();//BOM下拉框切换
        }else{
        	var list = json.list;
        	var bomList = json.bomList;
        	var count = json.count;
        	var pageNo = json.pageNo;
        	var pageSize= json.pageSize;
        	for(var i=0;i<list.length;i++){
        		var partData = list[i];
        		var PartNumber = (typeof(partData.PartNumber) == "undefined")?"":partData.PartNumber;
        		var Part_Type = (typeof(partData.Part_Type) == "undefined")?"":partData.Part_Type;
        		var ITEM = (typeof(partData.ITEM) == "undefined")?"":partData.ITEM;
        		var Part_Reference = (typeof(partData.Part_Reference) == "undefined")?"":partData.Part_Reference;
        		var Value = (typeof(partData.Value) == "undefined")?"":partData.Value;
        		var Numbers = (typeof(partData.Numbers) == "undefined")?"":partData.Numbers;
        		var Country = (typeof(partData.Country) == "undefined")?"":partData.Country;
        		var Manufacturer = (typeof(partData.Manufacturer) == "undefined")?"":partData.Manufacturer;
        		var KeyComponent = (typeof(partData.KeyComponent) == "undefined")?"":partData.KeyComponent;
        		var Datesheet = (typeof(partData.Datesheet) == "undefined")?"":partData.Datesheet;
        	    html += '<tr>';
//        	    html += '<td>'+'<input type="checkbox" onclick="checkOne('+partData.bomPnId+')" name="checkOne" value="'+partData.bomPnId+'">'+'</td>';
        	    html += '<td>'+Part_Type+'</td>';
        	    html += '<td>'+ITEM+'</td>';
        	    html += '<td>'+Manufacturer+'</td>';
        	    html += '<td class="zhiliang">'+KeyComponent+'</td>';
        	    html += '<td class="data-bookTd">'+"<a class=\"data-book\" href=\"javascript:viewPdf('"+Datesheet+"','"+PartNumber+"','"+partData.id+"','"+partData.isCollection+"')\"><img src=\"images/PDF.png\" />"+"Datesheets"+"</a>" +'</td>';
        	    html += '<td class="number">'+Numbers+'</td>';
        	    html += '<td class="more">'+'<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='+PartNumber+'&&partId='+partData.id+'&tempPartMark=null">'+$.i18n.prop("proGominuBtn")+'</a>'+'</td>';
        	    html += '</tr>';
        	}
        	//分页插件
    			if($("#Pagination").html().length == ''){
    			       $("#Pagination").pagination(
    			    		   count,
    	    	                {
    	    	                    items_per_page : pageSize,
    	    	                    num_edge_entries : pageNo,
    	    	                    num_display_entries : 8,
    	    	                    callback: function(pageNo, panel){
    	    	                       if(count==null){
    	    	                    	   showPart(id,pageNo);
						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	        });
    			}
    			//BOM名称加载开始
    			var bomHtml = '';
                for(var a=0;a<bomList.length;a++){
                	bomHtml += '<option value="'+bomList[a].pId+'">'+bomList[a].name+'</option>';
                }
     			//BOM名称加载结束
    		    $("#switchBom").html(bomHtml);
    		    $("#showPartdata").html(html);
    		    $("#switchBom").show();//bom文件下拉框显示
    		    $(".BOMname").show();//BOM下拉框切换
    		    $("#deleteDataFrombom").show();
    			count=null;
        }
     },
     error: function(){
       layer.alert($.i18n.prop("alertError"));
     }
  })
}
//根据BOM名称查询元器件数据
function showBomPart(node,pageNo){
	$("#switchBom").hide();//BOM下拉框切换--隐藏
	$(".BOMname").hide();//BOM下拉框切换--隐藏
	loadProperties();//国际化
	var productId;
	var excelName;
	if(null != node){
		productId = node.getParentNode().id;
		excelName = node.name;
	}else{
		zTree = $.fn.zTree.getZTreeObj("bomtree");
		var treeNode=zTree.getSelectedNodes();
		productId=treeNode.getParentNode().id;
		excelName=treeNode[0].name;
	}
	if(isNaN(pageNo)){
		pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic)+parseInt(pageNo);
	$.ajax({
		url: 'productManageController/selectPartDataByBom.do',
		data:{'productId':productId,'excelName':excelName,'pageNo':pageNo},
		dataType: 'json',
		cache: false,
		success: function(json){
			var html = '';
			html += '<tr>';
//			html += '<th>'+'<input type="checkbox" id="checkAll" onclick="checkAll()"/>'+'</th>';
			for(var x=0;x<json.title.length;x++){
				if("zh" == json.lang){
					html += '<th>'+json.title[x].showName+'</th>';
				}else{
					html += '<th>'+json.title[x].englishName+'</th>';
				}
			}
			if("zh" == json.lang){
				html += '<th>'+'数量'+'</th>';
				html += '<th>'+'更多信息'+'</th>';
			}else{
				html += '<th>'+'Number'+'</th>';
				html += '<th>'+'More'+'</th>';
			}
			html += '</tr>';
			if(null == json.list){
				html +='<tr>';
				html +='<td colspan="8" align="center">'+$.i18n.prop("noData")+'</td>';
				html +='</tr>';
				$("#showPartdata").html(html);
				$("#deleteDataFrombom").hide();
			}else{
				var list = json.list;
				var count = json.count;
				var pageNo = json.pageNo;
				var pageSize= json.pageSize;
				for(var i=0;i<list.length;i++){
					var partData = list[i];
					var PartNumber = (typeof(partData.PartNumber) == "undefined")?"":partData.PartNumber;
					var Part_Type = (typeof(partData.Part_Type) == "undefined")?"":partData.Part_Type;
					var ITEM = (typeof(partData.ITEM) == "undefined")?"":partData.ITEM;
					var Part_Reference = (typeof(partData.Part_Reference) == "undefined")?"":partData.Part_Reference;
					var Value = (typeof(partData.Value) == "undefined")?"":partData.Value;
					var Numbers = (typeof(partData.Numbers) == "undefined")?"":partData.Numbers;
					var Country = (typeof(partData.Country) == "undefined")?"":partData.Country;
					var Manufacturer = (typeof(partData.Manufacturer) == "undefined")?"":partData.Manufacturer;
					var KeyComponent = (typeof(partData.KeyComponent) == "undefined")?"":partData.KeyComponent;
					var Datesheet = (typeof(partData.Datesheet) == "undefined")?"":partData.Datesheet;
					html += '<tr>';
//					html += '<td>'+'<input type="checkbox" onclick="checkOne('+partData.bomPnId+')" name="checkOne" value="'+partData.bomPnId+'">'+'</td>';
					html += '<td>'+Part_Type+'</td>';
					html += '<td>'+ITEM+'</td>';
					html += '<td>'+Manufacturer+'</td>';
					html += '<td class="zhiliang">'+KeyComponent+'</td>';
					html += '<td class="data-bookTd">'+"<a class=\"data-book\" href=\"javascript:viewPdf('"+Datesheet+"','"+PartNumber+"','"+partData.id+"','"+partData.isCollection+"')\"><img src=\"images/PDF.png\" />"+"Datesheets"+"</a>" +'</td>';
					html += '<td class="number">'+Numbers+'</td>';
					html += '<td class="more">'+'<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='+PartNumber+'&partId='+partData.id+'&tempPartMark=null">'+$.i18n.prop("proGominuBtn")+'</a>'+'</td>';
					html += '</tr>';
				}
				//分页插件
				if($("#Pagination").html().length == ''){
					$("#Pagination").pagination(
							count,
							{
								items_per_page : pageSize,
								num_edge_entries : pageNo,
								num_display_entries : 8,
								callback: function(pageNo, panel){
									if(count==null){
										showPart(id,pageNo);
									}
								},
								link_to:"javascript:void(0);"
							});
				}
				$("#showPartdata").html(html);
				$("#deleteDataFrombom").show();
				count=null;
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
// 页面初始化展示节点信息
function showAttr(node) {
	if("3" == node.nodeStage){
		node = node.getParentNode();
	}
	if ("1" == node.nodeStage) {
		$("#p1").show();
		$("#attr0").css('display', 'none');
		$("#attr1").css('display', 'block');
		$("#attr2").css('display', 'none');
		$("input[name='productName']").val(node.productName);
		$("#firthUser").html(node.receiver);
		$('#btn0').hide();
		$('#btn1').hide();
		$('#btn2').hide();
		$('#btn3').hide();
		$('#btn4').hide();
	}else if ("2" == node.nodeStage) {
		var parentArray = Array();
		checkAllParents(parentArray, node);
		$("#p1").show();
		$("#attr0").css('display', 'none');
		$("#attr1").css('display', 'block');
		$("#attr2").css('display', 'block');
		$("input[name='productName']").val(parentArray[1].productName);
		$("#firthUser").html(parentArray[1].receiver);
		$("input[name='boardProductName']").val(parentArray[0].productName);
		$("input[name='productNo']").val(parentArray[0].productNo);
		$("input[name='productCode']").val(parentArray[0].productCode);
		$("#productStage").val(parentArray[0].productStage);
		$("#pTool").val(parentArray[0].pTool);
		$("#version").text(parentArray[0].version);
		$('#btn0').hide();
		$('#btn1').hide();
		$('#btn2').hide();
		$('#btn3').hide();
		$('#btn4').hide();
	}
	addDisabled();//查看状态下设置只读属性
}
/** 选择某一个节点* */
function zTreeOnClick(event, treeId, treeNode, clickFlag){
	if(treeNode.realId!=undefined){
		$(".productmix-edit").hide();
	}else{
   		$(".productmix-edit").show();
   	}
    closeUpdateBtn();
   var nodeStage = treeNode.nodeStage;
   if(typeof(treeNode.realId) != "undefined"){
    $("#Pagination").html('');
    showBomPart(treeNode);
    treeNode = treeNode.getParentNode();
    var parentArray = Array();
	   checkAllParents(parentArray,treeNode);
	$("#p1").show();
     $("#attr0").css('display','none'); 
     $("#attr1").css('display','block'); 
     $("#attr2").css('display','block'); 
     $("input[name='productName']").val(parentArray[1].productName);
     $("#firthUser").html(parentArray[1].receiver);
     $("input[name='boardProductName']").val(parentArray[0].productName);
     $("input[name='productNo']").val(parentArray[0].productNo);
     $("input[name='productCode']").val(parentArray[0].productCode);
     $("#productStage").val(parentArray[0].productStage);
     $("#pTool").val(parentArray[0].pTool);
     $("#version").text(parentArray[0].version);
     $('#btn0').hide();
     $('#btn1').hide();//暂时隐藏btn 
     $('#btn2').hide();//暂时隐藏btn 
     $('#btn3').hide();//暂时隐藏btn 
     $('#btn4').hide();//暂时隐藏btn 
   }else if(nodeStage == '1'){
    	$("#p1").show();
    	$("#attr0").css('display','none'); 
        $("#attr1").css('display','block'); 
        $("#attr2").css('display','none'); 
        $("input[name='productName']").val(treeNode.productName);
        $("#firthUser").html(treeNode.receiver);
        $('#btn1').hide();
        $("#Pagination").html('');
        showPart(treeNode.id);
        }else if(nodeStage == '2'){
         var parentArray = Array();
    	   checkAllParents(parentArray,treeNode);
    	$("#p1").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','block'); 
        $("#attr2").css('display','block'); 
        $("input[name='productName']").val(parentArray[1].productName);
        $("#firthUser").html(parentArray[1].receiver);
        $("input[name='boardProductName']").val(parentArray[0].productName);
        $("input[name='productNo']").val(parentArray[0].productNo);
        $("input[name='productCode']").val(parentArray[0].productCode);
        //$("#productStage option[value='"+parentArray[0].productStage+"']").attr("selected","selected");  
        $("#productStage").val(parentArray[0].productStage);
        $("#pTool").val(parentArray[0].pTool);
        $("#version").text(parentArray[0].version);
        //$("input[name='productStage']").val(parentArray[0].productStage);
        $('#btn0').hide();
        $('#btn1').hide();//暂时隐藏btn 
        $('#btn2').hide();//暂时隐藏btn 
        $('#btn3').hide();//暂时隐藏btn 
        $('#btn4').hide();//暂时隐藏btn 
        $("#Pagination").html('');
        showPart(treeNode.id);
    }
   addDisabled();//查看状态下设置只读属性
}
//根据text自动选中
function selectOption(productStage){
 $("#attr4 #productStage option").each(function(){  
        if($(this).text() == productStage){  
            $(this).attr("selected","selected");  
        }  
    });  
}
//根据text自动选中
function selectOptionTool(pTool){
 $("#attr4 #pTool option").each(function(){  
        if($(this).text() == pTool){  
            $(this).attr("selected","selected");  
        }  
    });  
}
//获取选中节点的所有父节点
function checkAllParents(parentArray,treeNode){
if (treeNode==null) {
return ;
}
else
{
treeNode.checked=true;
parentArray.push(treeNode);
checkAllParents(parentArray,treeNode.getParentNode());
}
}
//关闭DIV0
function closeAtt0(){
$("#attr0").css('display','none');
 document.getElementById('#attr0').innerHTML = "";
 closeUpdateBtn();
 }
//关闭DIV1
function closeAtt1(){
 $("#attr1").css('display','none');
 $("input[name='productName']").val('');
 $('#firstUser').html('');
 closeUpdateBtn();
 }
//关闭DIV3
function closeAtt3(){
$("#attr3").css('display','none');
 document.getElementById('#attr3').innerHTML = "";
  closeUpdateBtn();
}
//关闭DIV2
function closeAtt2() {
	$("#attr2").css('display', 'none');
	$("input[name='boardProductName']").val('');
	$("input[name='productNo']").val('');
	$("input[name='productCode']").val('');
	$("#productStage").val('');
	$("#pTool").val('');
	$("#version").text("A");
	closeUpdateBtn();
}
// 删除某个节点及其下的叶子节点
function deleteBom(){
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
   if("" != treeNode && null != treeNode){
	 layer.confirm($.i18n.prop("check-delete2"), {
			btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
			}, function() {
			var delId = treeNode[0].nodeStage==3?treeNode[0].name:treeNode[0].id;
			var parentNodeId = treeNode[0].nodeStage==3?treeNode[0].getParentNode().id:'';
			$.ajax({
				url : 'productManageController/deleteProductBom.do',
				data : {'id':delId,'nodeStage':treeNode[0].nodeStage,'parentNodeId':parentNodeId},
				dataType : 'json',
				cache : false,
				success : function(json) {
					layer.alert($.i18n.prop("alertMsg3"));
					initBomTree();
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			});
		});
   }else{
      layer.alert($.i18n.prop("proAlertMsg"));   
   }
}
//ids是一个数组 返回结果数组treeNode是选中的节点
 function getChildren(ids,treeNode){
    ids.push(treeNode.id);
    if (treeNode.isParent){
       for(var obj in treeNode.children){
        getChildren(ids,treeNode.children[obj]);
      }
    }
    return ids;
}
/**添加父级节点**/
function editAttr1(){
	 closeUpdateBtn();
  $("#p1").show();
  $("#btn1").show();
  $("#attr0").css('display','none'); 
  $("#attr1").css('display','block'); 
  $("#attr2").css('display','none'); 
  $("#attr3").css('display','none'); 
  $("#attr4").css('display','none'); 
        $("input[name='productName']").val('');
        $("input[name='systemProductName']").val('');
        $("input[name='selectBoxShow']").val('');
	    $('#firstUser').html('');
}
/**产品构建保存**/
function saveAttr1(){
	    loadProperties();//国际化
	    var productName = $("input[name='productName']").val();
	    var receiver = $("#firthUser").html();
	     if("" == productName || null == productName){
	        layer.alert("产品名称不能为空！");
	        return;
	    }
	    if("" == receiver){
	    	 layer.alert("单板创建人员不能为空！");
	    	 return;
	    }
        $.ajax({
		url: 'productManageController/insertProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,  
		data :{'productName':productName,'receiver':receiver,'nodeStage':'1','parentId':'0'},
		success: function(json){
			if(json.result == "first"){
				layer.alert($.i18n.prop("proAlertMsg9"));
				$("input[name='productName']").val('');
			}else{
				layer.alert($.i18n.prop("alertMsg2"));
				$("input[name='productName']").val('');
		        $("#firstUser").html('');
				initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
}
/**点击user树某一个节点**/
function userTreeOnClick(event, treeId, treeNode, clickFlag){
	$("#searchText").val('');
   selectUser(treeNode.id);
}
/**用户选择界面查询**/
function selectUser(group){
	var groupId = group;
	var loginName = $("#searchText").val();
	if(typeof(groupId) == "undefined" || '-1' == groupId){
    		groupId = "";
   }
   $.ajax({
	     url: 'user/selectAllUserByGroup.do',
	     data: {'groupId':groupId,'loginName':loginName,'rights':'true'},
	     dataType: 'json',
	     cache: false,
	     success: function(data){
	     	var json = data.list;
	        var html = '';
  				if(null == json || "" == json){
					html +='<tr>';
					html +='<td colspan="1" align="center">--没有数据--</td>';
					html +='</tr>';
				}else{
					var i =Math.ceil(json.length/3);
					var array = Array();
					for(var jj=0;jj<json.length;jj++){
						array.push(json[jj]);
					}
					var first ='yes';
					for(var x=0;x<i;x++){
						  var num = 0;
						  if( first !='yes'){
						  array.shift();
						  array.shift();
						  array.shift();
						  }
					      html += '<tr>';
					      if(array.length>0){
					      for(var a=0;a<array.length;a++){
					      	var user = array[a];
					      	if(null == user){
					      	  break;
					      	}
					         html += '<td>'+'<input type="checkbox" name="loginName" value='+user.loginName+'>'+user.userName+'('+user.loginName+')'+'</td>';//---L后面
					         num++;
					         //array.shift();
					          first ='no';
					         if(num == 3){
					         	num == 0;
					         	 break;
					         }
					      }
					      }
					    html += '</tr>';
					}
				}
				$("#showUser").html(html);
	     },
	     error: function(){
	      alert("服务器异常，加载用户组数据失败！");
	     }
	 })
}
/**保存选择的人员**/
function saveSelectUser(){
   var obj = document.getElementsByName("loginName");
    var loginName='';
    for(var k=0;k<obj.length;k++){
        if(obj[k].checked){
            loginName =loginName+obj[k].value+',';
        }
    }   
     loginName = loginName.substring(0,loginName.length-1);
     /*var div1 =document.getElementById("attr1").style.display; 
     var div2 =document.getElementById("attr2").style.display; 
     var div3 =document.getElementById("attr3").style.display; 
     if(div1 !="none") //不是隐藏状态
     {
        $("#firthUser").html(loginName);
        $(".productmix-chooseuser-window").hide();
     }
      if(div2 !="none") //不是隐藏状态
     {
        $("#secondtUser").html(loginName);
        $(".productmix-chooseuser-window").hide();
     }
      if(div3 !="none") //不是隐藏状态
     {
        $("#thirdUser").html(loginName);
        $(".productmix-chooseuser-window").hide();
     }*/
     var id = "#"+selectUserId;
      $(id).html(loginName);
      $(".productmix-chooseuser-window").hide();
      $(".modal-backdrop").hide();
}
//关闭用户选择窗口
function resetSelectUser(){
     $(".productmix-chooseuser-window").hide();
     $(".modal-backdrop").hide();
}
//添加下级节点
function editAttr(){
	 closeUpdateBtn();
     zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
    if("" == treeNode || null == treeNode){
        layer.alert("请选择一个节点信息！");
        return;
    }
    var nodeStage = treeNode[0].nodeStage;
    if(nodeStage == '1'){
    	$("#p1").show();
    	$("#btn2").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','none'); 
        $("#attr2").css('display','block'); 
        $("#attr3").css('display','none'); 
        $("#attr4").css('display','none');
        $("input[name='systemProductName']:eq(1)").val('');
	    $('#secondUser').html('');
    }else if(nodeStage == '2'){
        $("#p1").show();
        $("#btn3").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','none'); 
        $("#attr2").css('display','none'); 
        $("#attr3").css('display','block'); 
        $("#attr4").css('display','none'); 
        $("input[name='engineProductName']").val('');
	    $('#thirdUser').html('');
    }else if(nodeStage == '3'){
        $("#p1").show();
        $("#btn4").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','none'); 
        $("#attr2").css('display','none'); 
        $("#attr3").css('display','none'); 
        $("#attr4").css('display','block'); 
        $("input[name='boardProductName']").val('');
        $("input[name='productNo']").val('');
        $("input[name='productCode']").val('');
        $("#productStage").val('');  
        $("#pTool").val('');  
        $("#version").text("A");
    }else if(nodeStage == '4'){
       layer.alert("该节点为末级节点，不能添加下级节点！");
        $("#btn0").hide();
        $("#btn2").hide();
        $("#btn3").hide();
        $("#btn4").hide();
    }
}
//单机属性保存
function saveAttr3(){
	loadProperties();//国际化
	   zTree = $.fn.zTree.getZTreeObj("bomtree");
        var treeNode=zTree.getSelectedNodes();
	    var engineProductName = $("input[name='engineProductName']").val();
        var receiver = $("#thirdUser").html();
         if("" == engineProductName || null == engineProductName){
            layer.alert($.i18n.prop("proAlertMsg10"));
            return;
         }
          if("" == receiver || null == receiver){
            layer.alert($.i18n.prop("proAlertMsg11"));
            return;
         }
       $.ajax({
		url: 'productManageController/insertProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,   //注意这个地方
		data :{'engineProductName':engineProductName,'receiver':receiver,'nodeStage':'3','parentId':treeNode[0].id},
		success: function(json){
			if(json.result == "engine"){
			  layer.alert($.i18n.prop("proAlertMsg12"));
			  return;
			}else{
			  layer.alert($.i18n.prop("alertMsg2"));
			  $("input[name='engineProductName']").val('');
              $("#thirdUser").html('');
			  initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
}
//单板属性保存
function saveAttr2(){
	   loadProperties();//国际化
	   zTree = $.fn.zTree.getZTreeObj("bomtree");
        var treeNode=zTree.getSelectedNodes();
	    var boardProductName = $("input[name='boardProductName']").val();
	    var productNo = $("input[name='productNo']").val();
	    var productCode = '1';//$("input[name='productCode']").val()
	    var productStage = $("select[name='productStage']").val();
	    var pTool = $("select[name='pTool']").val();
         if("" == boardProductName || null == boardProductName){
            layer.alert($.i18n.prop("proAlertMsg15"));
            return;
         }
          if("" == productNo || null == productNo){
            layer.alert($.i18n.prop("proAlertMsg16"));
            return;
         }
         var regs=/^[a-zA-Z].*/;
         if(!regs.test(productNo)){
         	layer.alert($.i18n.prop("proAlertMsg17"));
            return;
         }
         var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im;
         var regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
        if(regEn.test(productNo) || regCn.test(productNo)) {
          layer.alert($.i18n.prop("proAlertMsg18"));
           return;
         }
          if("" == productCode || null == productCode){
            layer.alert($.i18n.prop("proAlertMsg19"));
            return;
         }
          if("" == productStage || null == productStage){
            layer.alert($.i18n.prop("proAlertMsg20"));
            return;
         }
          if("" == pTool || null == pTool){
            layer.alert($.i18n.prop("proAlertMsg21"));
            return;
         }
       $.ajax({
		url: 'productManageController/insertProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,  
		data :{'boardProductName':boardProductName,'productNo':productNo,'productCode':productCode,'productStage':productStage,'pTool':pTool,'nodeStage':'2','parentId':treeNode[0].id,'receiver':treeNode[0].receiver},
		success: function(json){
			if(json.result == "board"){
			  layer.alert($.i18n.prop("proAlertMsg24"));
			  return;
			}else if(json.result == "nameRep"){
				layer.alert($.i18n.prop("proAlertMsg22"));
				  return;
			}else if(json.result == "noRep"){
				layer.alert($.i18n.prop("proAlertMsg23"));
				  return;
			}else{
			  layer.alert($.i18n.prop("alertMsg2"));
			  $("input[name='boardProductName']").val('');
	          $("input[name='productNo']").val('');
	          $("input[name='productCode']").val('');
	          $("select[name='productStage']").val('');
	          $("select[name='pTool']").val('');
			  initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
}
//更新节点
function updateNode(){
	    loadProperties();//国际化
        zTree = $.fn.zTree.getZTreeObj("bomtree");
        var treeNode=zTree.getSelectedNodes();
        if("" == treeNode || null == treeNode){
            layer.alert($.i18n.prop("proAlertMsg"));
        	return;
        }
        if(treeNode[0].realId!=undefined){
       		$(".productmix-edit").hide();
       	}else{
       		$(".productmix-edit").show();
       	}
	var nodeStage = treeNode[0].nodeStage;
    if(nodeStage == '1'){
    	$("#p1").show();
    	$("#btn1").hide();
    	$("#btn3").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','block'); 
        $("#attr2").css('display','none'); 
        $("input[name='productName']").val(treeNode[0].productName);
        $("#firthUser").html(treeNode[0].receiver);
    }else if(nodeStage == '2'){
        $("#p1").show();
        $("#btn2").hide();
        $("#btn4").show();
        $("#attr0").css('display','none'); 
        $("#attr1").css('display','none'); 
        $("#attr2").css('display','block'); 
        $("input[name='boardProductName']").val(treeNode[0].productName);
        $("input[name='productNo']").val(treeNode[0].productNo);
        $("input[name='productCode']").val(treeNode[0].productCode);
        $("#productStage").val(treeNode[0].productStage);  
        $("#pTool").val(treeNode[0].pTool);  
        $("#version").text(treeNode[0].version);
    }
    removeDisabled();//移除只读效果
}
//更新一级树
function updateAttr1(){
	loadProperties();//国际化
     var result = Object();
	    result = JSON.parse(window.sessionStorage.getItem("selectBoxUpdate"));
	zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
     var productName = $("input[name='productName']").val();
     var receiver = $("#firthUser").html();
     if("" == productName || null == productName){
	        layer.alert("产品名称不能为空！");
	        return;
	    }
     if("" == receiver || null == receiver){
	        layer.alert("单板创建人员不能为空！");
	        return;
	    }
        $.ajax({
		url: 'productManageController/updateProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,   //注意这个地方
		data :{'productName':productName,'receiver':receiver,'nodeStage':'1','parentId':'0','id':treeNode[0].id},
		success: function(json){
			if(json.result == "first"){
				layer.alert($.i18n.prop("proAlertMsg9"));
				$("input[name='productName']").val('');
				return;
			}else{
				layer.alert($.i18n.prop("alertMsg1"));
				$("input[name='productName']").val('');
				$("#firthUser").html('');
				initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
}
//更新三级树
function updateAttr3(){
	loadProperties();//国际化
   zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
        var engineProductName = $("input[name='engineProductName']").val();
	    var receiver = $("#thirdUser").html();
	    if("" == engineProductName || null == engineProductName){
	    	layer.alert($.i18n.prop("proAlertMsg10"));
	    	return;
	    }
	    if("" == receiver){
	    	 layer.alert($.i18n.prop("proAlertMsg11"));
	    	 return;
	    }
	     $.ajax({
		url: 'productManageController/updateProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,   //注意这个地方
		data :{'engineProductName':engineProductName,'receiver':receiver,'nodeStage':'3','parentId':treeNode[0].pId,'id':treeNode[0].id},
		success: function(json){
			if(json.result == ""){
			   layer.alert($.i18n.prop("proAlertMsg25"));
			}else{
				layer.alert($.i18n.prop("alertMsg1"));
			    initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
}
//更新二级树
function updateAttr2(){
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
        var boardProductName = $("input[name='boardProductName']").val();
        var productNo = $("input[name='productNo']").val();
        var productCode = '1';
	    var productStage = $("select[name='productStage']").val();
	    var pTool = $("select[name='pTool']").val();
	    if("" == boardProductName || null == boardProductName){
	    	layer.alert($.i18n.prop("proAlertMsg15"));
	    	return;
	    }
        if("" == productNo || null == productNo){
            layer.alert($.i18n.prop("proAlertMsg16"));
            return;
         }
           var regs=/^[a-zA-Z].*/;
         if(!regs.test(productNo)){
         	layer.alert($.i18n.prop("proAlertMsg17"));
            return;
         }
         var regEn = /[`~!@#$%^&*()+<>?:"{},.\/;'[\]]/im;
         var regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;
        if(regEn.test(productNo) || regCn.test(productNo)) {
          layer.alert($.i18n.prop("proAlertMsg18"));
           return;
         }
          if("" == productCode || null == productCode){
            layer.alert(" 图号不能为空！");
            return;
         }
          if("" == productStage || null == productStage){
            layer.alert($.i18n.prop("proAlertMsg20"));
            return;
         }
          if("" == pTool || null == pTool){
            layer.alert($.i18n.prop("proAlertMsg21"));
            return;
         }
       $.ajax({
		url: 'productManageController/updateProductBom.do',
		type: "POST",
		dataType: 'json',
		traditional:true,  
		data :{'boardProductName':boardProductName,'productNo':productNo,'productCode':productCode,'productStage':productStage,'pTool':pTool,'nodeStage':'2','parentId':treeNode[0].pId,'id':treeNode[0].id,'receiver':treeNode[0].receiver},
		success: function(json){
			if(json.result == "board"){
			  layer.alert($.i18n.prop("proAlertMsg24"));
			  return;
			}else if(json.result == "nameRep"){
				layer.alert($.i18n.prop("proAlertMsg22"));
				  return;
			}else if(json.result == "noRep"){
				layer.alert($.i18n.prop("proAlertMsg23"));
				  return;
			}else{
			  layer.alert($.i18n.prop("alertMsg1"));
			  initBomTree();
			}
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
       })
	   
}
//刷新操作
function refreshBom(){
initBomTree();
}
var copyId = "";
//拷贝操作
function copyBom(){
 zTree = $.fn.zTree.getZTreeObj("bomtree");
 var treeNode=zTree.getSelectedNodes();
 if("" !=  treeNode){
    if("4" == treeNode[0].nodeStage){
    	copyId = treeNode[0].id;
        layer.alert("选择拷贝节点成功，请选择一个黏贴节点！");
    }else{
       layer.alert("只能选择根节点进行拷贝！");
       return;
    }
 }else{
   layer.alert("请选择要拷贝的节点！");
   return;
 }
}
//黏贴操作
function pasteBom(){
 zTree = $.fn.zTree.getZTreeObj("bomtree");
 var treeNode=zTree.getSelectedNodes();
  if("" != copyId){
    if("" != treeNode){
      if("3" == treeNode[0].nodeStage){
         $.ajax({
		    url: 'productManageController/copyProductBom.do',
            data: {'fromId':copyId,'toId':treeNode[0].id},
            dataType: 'json',
            cache: false,
            success: function(json){
               if(json.result == "fail"){
                  layer.alert("相同名称的单板不能超过24个！");
               }else{
                  layer.alert("操作成功！");
                  initBomTree();
               }
            },
            error: function(){
            layer.alert("服务器异常，黏贴操作失败！");
            return;
            }
         });
      }else if(treeNode[0].nodeStage=='4'){
		$.ajax({
		    url: 'productManageController/copyProductBom.do',
            data: {'fromId':copyId,'toId':treeNode[0].pId},
            dataType: 'json',
            cache: false,
            success: function(json){
               if(json.result == "fail"){
                  layer.alert("相同名称的单板不能超过24个！");
               }else{
                  layer.alert("操作成功！");
                  initBomTree();
               }
            },
            error: function(){
            	layer.alert("服务器异常，黏贴操作失败！");
            	return;
            }
         });
      }else{
		layer.alert("黏贴的对象暂时只能选择三、四级节点！");
		return;
      }
    }else{
     layer.alert("请选择要黏贴的对象！");
     return;
    }
  }else{
    layer.alert("请选择要拷贝的对象！");
    return;
  }
}
//导出excel
function exportBom(){
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
    if("" != treeNode){
       if("4" == treeNode[0].nodeStage){
         	window.location.href=getContextPathForWS()+"/productManageController/exportBom.do?id="+treeNode[0].id;
       }else{
          layer.alert($.i18n.prop("proAlertMsg28"));
       }
    }else{
      layer.alert($.i18n.prop("proAlertMsg"));
    }
}
//产品设计界面------用户所在组树结构加载
function initgroupUserTree(){
	$.ajax({
	   url: 'HrGroupController/selectAllGroupTree.do',
	   dataType: 'json',
	   cache: false,
	   success: function(json){
	   	    var list = json;
	 var settingUser = {
		view : {
			showIcon : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick :userTreeOnClick
		}
	};
	if(null != list && "" != list){
	  $.fn.zTree.init($("#userTree"), settingUser, list);//加载树结构
	  var treeObj = $.fn.zTree.getZTreeObj("userTree");
	  treeObj.expandAll(true); 
	}else{
	var zNodesUser =[
			{ id:-1, pId:0, name:"Design center", open:true}
		];
		   $.fn.zTree.init($("#userTree"), settingUser, zNodesUser);//加载树结构
	}
	   },
	   error: function(){
	   }
	})
}
//selectBox对应值
function selectBoxToValue(value,productType){
   if("SY" == productType){
       value ="实验型号"; 
   }
   if("SZ" == productType){
       value ="神舟系列"; 
   }
   if("CE" == productType){
       value ="嫦娥系列"; 
   }
   if("HY" == productType){
       value ="海洋系列"; 
   }
   if("SJ" == productType){
       value ="实践系列"; 
   }
   if("HJ" == productType){
       value ="环境系列"; 
   }
   if("TZ" == productType){
       value ="货运系列"; 
   }
   return value;
}
//selectBox对应值
function selectBoxToName(value,productType){
   if("实验型号" == productType){
       value ="SY"; 
   }
   if("神舟系列" == productType){
       value ="SZ"; 
   }
   if("嫦娥系列" == productType){
       value ="CE"; 
   }
   if("海洋系列" == productType){
       value ="HY"; 
   }
   if("实践系列" == productType){
       value ="SJ"; 
   }
   if("环境系列" == productType){
       value ="HJ"; 
   }
   if("货运系列" == productType){
       value ="TZ"; 
   }
   return value;
}
//关闭更新按钮
function closeUpdateBtn(){
        $("#btn0").show();
        $("#btn1").show();
        $("#btn2").show();
        $("#btn3").hide();
        $("#btn4").hide();
}
/**复选框全选反选操作**/
function checkAll(){
var CheckBox=document.getElementsByName('checkOne');
if($("#checkAll").prop('checked')){
  for(var i=0;i<CheckBox.length;i++){
  	if(CheckBox[i].checked==false){
  	    CheckBox[i].checked=true;
  	}
   }
}else{
   for(var i=0;i<CheckBox.length;i++){
   	if(CheckBox[i].checked=true){
   	    CheckBox[i].checked=false;
   	}
   }
}
}
/**复选框单个选择**/
function checkOne(id){
	var CheckBox=document.getElementsByName('checkOne');
    var num=0;
    if(CheckBox.length>0){
     for(var i=0;i<CheckBox.length;i++){
      if(CheckBox[i].checked){
      	num++;
      }
    }
    }
    if( num == CheckBox.length){
    $("#checkAll").prop('checked',true); 
    }else{
    $("#checkAll").prop('checked',false); 
    }
}
/**删除关系数据**/
function deleteDataFrombom(){
	loadProperties();//国际化
    var ids=new Array;
	$("input[name='checkOne']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert("请选中一条要删除的数据!");
		return;
	}
			layer.confirm($.i18n.prop("check-delete2"), {
			btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
			}, function(){
			$.ajax({
	            url: 'productManageController/deleteDataFrombom.do',
	           data: {
	                    'ids':JSON.stringify(ids)
	           },
	           dataType: 'json',
	           cache: false,
	           success: function(json){
	           layer.alert($.i18n.prop("alertMsg3"));
	           $("input[name='checkOne']:checkbox:checked").each(function(){
	       		$(this).parent().parent().remove();
	       	   });
	          },
	          error: function(){
		      layer.alert("服务器连接异常，请联系管理员！");
	          }
	        })
          });
     }
//型号创建
function addProCode(){
	removeDisabled();//移除只读效果
	closeUpdateBtn();
	  $("#p1").show();
	  $("#btn1").show();
	  $("#attr0").css('display','none'); 
	  $("#attr1").css('display','block'); 
	  $("#attr2").css('display','none'); 
      //滞空数据
	  $("input[name='productName']").val('');
	  $('#firthUser').html('');
}
//分系统创建
function addProSystem(){
	 closeUpdateBtn();
	 loadProperties();//国际化
     zTree = $.fn.zTree.getZTreeObj("bomtree");
    var treeNode=zTree.getSelectedNodes();
    if("" == treeNode || null == treeNode){
        layer.alert($.i18n.prop("proAlertMsg"));
        return;
    }
    var nodeStage = treeNode[0].nodeStage;
    $.ajax({
		url: 'productManageController/getRightAndUser.do',
		dataType: 'json',
		cache: false,
		type: 'post',
		success: function(json){
			var dataRightList=json.dataRightList;
			var user=json.user;
			var userC = user.loginName;
			var receiverC = treeNode[0].receiver;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			if(user==undefined){
				user=new Object;
			}
			if(userC != receiverC){
				if(dataRightList.length <= 0){
					layer.alert($.i18n.prop("proAlertMsg2"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				}else{
					var html = "";
					for(var i=0;i<dataRightList.length;i++){
						if(dataRightList[i].rightsNote=="cdj"){
							html = "yes";
						 }
					}
						if(html == "yes"){
							if(nodeStage == '1'){
						    	$("#p1").show();
						    	$("#btn2").show();
						        $("#attr0").css('display','none'); 
						        $("#attr1").css('display','none'); 
						        $("#attr2").css('display','block'); 
						        $("#attr3").css('display','none'); 
						        $("#attr4").css('display','none');
						        $("input[name='systemProductName']").val('');
							    $('#secondtUser').html('');
						    }else if(nodeStage == '2'){
						          layer.alert($.i18n.prop("proAlertMsg1"));
						          $('#btn0').hide();
						          $('#btn1').hide();
						          $('#btn2').hide();
						          $('#btn3').hide();
						          $('#btn4').hide();
						          $("#btn5").hide();
						          $("#btn6").hide();
						          $("#btn7").hide();
						          $("#btn8").hide();
						          return;
						    }else if(nodeStage == '3'){
						    	  layer.alert($.i18n.prop("proAlertMsg1"));
						    	  $('#btn0').hide();
						          $('#btn1').hide();
						          $('#btn2').hide();
						          $('#btn3').hide();
						          $('#btn4').hide();
						          $("#btn5").hide();
						          $("#btn6").hide();
						          $("#btn7").hide();
						          $("#btn8").hide();
						          return;
						    }else if(nodeStage == '4'){
						    	  layer.alert($.i18n.prop("proAlertMsg1"));
						    	  $('#btn0').hide();
						          $('#btn1').hide();
						          $('#btn2').hide();
						          $('#btn3').hide();
						          $('#btn4').hide();
						          $("#btn5").hide();
						          $("#btn6").hide();
						          $("#btn7").hide();
						          $("#btn8").hide();
						          return;
						    }
						}else{
							layer.alert($.i18n.prop("proAlertMsg2"));
							   $('#btn0').hide();
						       $('#btn1').hide();
						       $('#btn2').hide();
						       $('#btn3').hide();
						       $('#btn4').hide();
						       $("#btn5").hide();
						       $("#btn6").hide();
						       $("#btn7").hide();
						       $("#btn8").hide();
						       return;
						}
				     }
			}else{
				if(nodeStage == '1'){
			    	$("#p1").show();
			    	$("#btn2").show();
			        $("#attr0").css('display','none'); 
			        $("#attr1").css('display','none'); 
			        $("#attr2").css('display','block'); 
			        $("#attr3").css('display','none'); 
			        $("#attr4").css('display','none');
			        $("input[name='systemProductName']").val('');
				    $('#secondtUser').html('');
			    }else if(nodeStage == '2'){
			          layer.alert($.i18n.prop("proAlertMsg1"));
			          $('#btn0').hide();
			          $('#btn1').hide();
			          $('#btn2').hide();
			          $('#btn3').hide();
			          $('#btn4').hide();
			          $("#btn5").hide();
			          $("#btn6").hide();
			          $("#btn7").hide();
			          $("#btn8").hide();
			          return;
			    }else if(nodeStage == '3'){
			    	  layer.alert($.i18n.prop("proAlertMsg1"));
			    	  $('#btn0').hide();
			          $('#btn1').hide();
			          $('#btn2').hide();
			          $('#btn3').hide();
			          $('#btn4').hide();
			          $("#btn5").hide();
			          $("#btn6").hide();
			          $("#btn7").hide();
			          $("#btn8").hide();
			          return;
			    }else if(nodeStage == '4'){
			    	  layer.alert($.i18n.prop("proAlertMsg1"));
			    	  $('#btn0').hide();
			          $('#btn1').hide();
			          $('#btn2').hide();
			          $('#btn3').hide();
			          $('#btn4').hide();
			          $("#btn5").hide();
			          $("#btn6").hide();
			          $("#btn7").hide();
			          $("#btn8").hide();
			          return;
			    }
			}
				
		},
		error: function(json){
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
//单机创建
function addProEngine(){
	closeUpdateBtn();
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
   var treeNode=zTree.getSelectedNodes();
   if("" == treeNode || null == treeNode){
       layer.alert($.i18n.prop("proAlertMsg"));
       return;
   }
   var nodeStage = treeNode[0].nodeStage;
   $.ajax({
		url: 'productManageController/getRightAndUser.do',
		dataType: 'json',
		cache: false,
		type: 'post',
		success: function(json){
			var dataRightList=json.dataRightList;
			var user=json.user;
			var userC = user.loginName;
			var receiverC = treeNode[0].receiver;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			if(user==undefined){
				user=new Object;
			}
			if(userC != receiverC){
				if(dataRightList.length <= 0){
					layer.alert($.i18n.prop("proAlertMsg2"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				}else{
					var html = "";
					for(var i=0;i<dataRightList.length;i++){
						if(dataRightList[i].rightsNote=="cdj"){
							html = "yes";
						 }
					}
						if(html == "yes"){
							if(nodeStage == '1'){
								   layer.alert($.i18n.prop("proAlertMsg3"));
								   $('#btn0').hide();
							       $('#btn1').hide();
							       $('#btn2').hide();
							       $('#btn3').hide();
							       $('#btn4').hide();
							       $("#btn5").hide();
							       $("#btn6").hide();
							       $("#btn7").hide();
							       $("#btn8").hide();
							       return;
							   }else if(nodeStage == '2'){
							       $("#p1").show();
							       $("#btn3").show();
							       $("#attr0").css('display','none'); 
							       $("#attr1").css('display','none'); 
							       $("#attr2").css('display','none'); 
							       $("#attr3").css('display','block'); 
							       $("#attr4").css('display','none'); 
							       $("input[name='engineProductName']").val('');
								   $('#thirdUser').html('');
							   }else if(nodeStage == '3'){
								   layer.alert($.i18n.prop("proAlertMsg3"));
								   $('#btn0').hide();
							       $('#btn1').hide();
							       $('#btn2').hide();
							       $('#btn3').hide();
							       $('#btn4').hide();
							       $("#btn5").hide();
							       $("#btn6").hide();
							       $("#btn7").hide();
							       $("#btn8").hide();
							       return;
							   }else if(nodeStage == '4'){
								   layer.alert($.i18n.prop("proAlertMsg3"));
								   $('#btn0').hide();
							       $('#btn1').hide();
							       $('#btn2').hide();
							       $('#btn3').hide();
							       $('#btn4').hide();
							       $("#btn5").hide();
							       $("#btn6").hide();
							       $("#btn7").hide();
							       $("#btn8").hide();
							       return;
							   }
						}else{
							layer.alert($.i18n.prop("proAlertMsg2"));
							   $('#btn0').hide();
						       $('#btn1').hide();
						       $('#btn2').hide();
						       $('#btn3').hide();
						       $('#btn4').hide();
						       $("#btn5").hide();
						       $("#btn6").hide();
						       $("#btn7").hide();
						       $("#btn8").hide();
						       return;
						}
				     }
			}else{
				if(nodeStage == '1'){
					   layer.alert($.i18n.prop("proAlertMsg3"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }else if(nodeStage == '2'){
				       $("#p1").show();
				       $("#btn3").show();
				       $("#attr0").css('display','none'); 
				       $("#attr1").css('display','none'); 
				       $("#attr2").css('display','none'); 
				       $("#attr3").css('display','block'); 
				       $("#attr4").css('display','none'); 
				       $("input[name='engineProductName']").val('');
					   $('#thirdUser').html('');
				   }else if(nodeStage == '3'){
					   layer.alert($.i18n.prop("proAlertMsg3"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }else if(nodeStage == '4'){
					   layer.alert($.i18n.prop("proAlertMsg3"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }
			}
				
		},
		error: function(json){
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
//单板创建
function addProBroad(){
	closeUpdateBtn();
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
   var treeNode=zTree.getSelectedNodes();
   if("" == treeNode || null == treeNode){
       layer.alert($.i18n.prop("proAlertMsg"));
       return;
   }
   var nodeStage = treeNode[0].nodeStage;
   $.ajax({
		url: 'productManageController/getRightAndUser.do',
		dataType: 'json',
		cache: false,
		type: 'post',
		success: function(json){
			var dataRightList=json.dataRightList;
			var user=json.user;
			var userC = user.loginName;
			var receiverC = treeNode[0].receiver;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			if(user==undefined){
				user=new Object;
			}
			if(userC != receiverC){
				if(dataRightList.length <= 0){
					layer.alert($.i18n.prop("proAlertMsg2"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				}else{
					var html = "";
					for(var i=0;i<dataRightList.length;i++){
						if(dataRightList[i].rightsNote=="cdb"){
							html = "yes";
						 }
					}
			        var html="";
			         for(var i=0;i<dataRightList.length;i++){
				       if(dataRightList[i].rightsNote=="cdb"){
					     html = "yes";
				       }
			          }
			        if(html == "yes"){
				       if(nodeStage == '1'){
					   layer.alert($.i18n.prop("proAlertMsg4"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }else if(nodeStage == '2'){
					   layer.alert($.i18n.prop("proAlertMsg4"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }else if(nodeStage == '3'){
				       $("#p1").show();
				       $("#btn4").show();
				       $("#attr0").css('display','none'); 
				       $("#attr1").css('display','none'); 
				       $("#attr2").css('display','none'); 
				       $("#attr3").css('display','none'); 
				       $("#attr4").css('display','block'); 
				       $("input[name='boardProductName']").val('');
				       $("input[name='productNo']").val('');
				       $("input[name='productCode']").val('');
				       $("#productStage").val('');  
				       $("#pTool").val('');  
				       $("#version").text("A");
				   }else if(nodeStage == '4'){
					   layer.alert($.i18n.prop("proAlertMsg4"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       $("#btn5").hide();
				       $("#btn6").hide();
				       $("#btn7").hide();
				       $("#btn8").hide();
				       return;
				   }
			        }else{
						layer.alert($.i18n.prop("proAlertMsg2"));
						   $('#btn0').hide();
					       $('#btn1').hide();
					       $('#btn2').hide();
					       $('#btn3').hide();
					       $('#btn4').hide();
					       $("#btn5").hide();
					       $("#btn6").hide();
					       $("#btn7").hide();
					       $("#btn8").hide();
					       return;
					}
			     }
		}else{
			if(nodeStage == '1'){
				   layer.alert($.i18n.prop("proAlertMsg4"));
				   $('#btn0').hide();
			       $('#btn1').hide();
			       $('#btn2').hide();
			       $('#btn3').hide();
			       $('#btn4').hide();
			       $("#btn5").hide();
			       $("#btn6").hide();
			       $("#btn7").hide();
			       $("#btn8").hide();
			       return;
			   }else if(nodeStage == '2'){
				   layer.alert($.i18n.prop("proAlertMsg4"));
				   $('#btn0').hide();
			       $('#btn1').hide();
			       $('#btn2').hide();
			       $('#btn3').hide();
			       $('#btn4').hide();
			       $("#btn5").hide();
			       $("#btn6").hide();
			       $("#btn7").hide();
			       $("#btn8").hide();
			       return;
			   }else if(nodeStage == '3'){
			       $("#p1").show();
			       $("#btn4").show();
			       $("#attr0").css('display','none'); 
			       $("#attr1").css('display','none'); 
			       $("#attr2").css('display','none'); 
			       $("#attr3").css('display','none'); 
			       $("#attr4").css('display','block'); 
			       $("input[name='boardProductName']").val('');
			       $("input[name='productNo']").val('');
			       $("input[name='productCode']").val('');
			       $("#productStage").val('');  
			       $("#pTool").val('');  
			       $("#version").text("A");
			   }else if(nodeStage == '4'){
				   layer.alert($.i18n.prop("proAlertMsg4"));
				   $('#btn0').hide();
			       $('#btn1').hide();
			       $('#btn2').hide();
			       $('#btn3').hide();
			       $('#btn4').hide();
			       $("#btn5").hide();
			       $("#btn6").hide();
			       $("#btn7").hide();
			       $("#btn8").hide();
			       return;
			   }
		}
		},
		error: function(json){
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
/**获得选择的人员类型**/
var selectUserId = "";
$("body").on("click", ".attruser-btn", function () {	
 var selectedUsers = $("#firthUser").html().split(",");//已经选择的人员
 selectUserId = $(this).next().attr('id');
 if(selectUserId == ""){
	 return;
 }
    var groupId = "";
	var loginName = $("#searchText").val();
$.ajax({
	     url: 'user/selectAllUserByGroup.do',
	     data: {'groupId':groupId,'loginName':loginName,"rights":'true'},
	     dataType: 'json',
	     cache: false,
	     success: function(data){
	     	var json = data.list;
	        var html = '';
				if(null == json || "" == json){
					html +='<tr>';
					html +='<td colspan="1" align="center">--没有数据--</td>';
					html +='</tr>';
				}else{
					var i =Math.ceil(json.length/3);
					var array = Array();
					for(var jj=0;jj<json.length;jj++){
						array.push(json[jj]);
					}
					var first ='yes';
					for(var x=0;x<i;x++){
						  var num = 0;
						  if( first !='yes'){
						  array.shift();
						  array.shift();
						  array.shift();
						  }
					      html += '<tr>';
					      if(array.length>0){
					      for(var a=0;a<array.length;a++){
					      	var user = array[a];
					      	if(null == user){
					      	  break;
					      	}
					      	 var checked = selectedUsers.length <=0 || selectedUsers.indexOf(user.loginName)==-1?"":"checked='checked'";
					         html += '<td>'+'<input type="checkbox" name="loginName" '+checked+' value='+user.loginName+'>'+user.userName+'('+user.loginName+')'+'</td>';//---L后面
					         num++;
					         //array.shift();
					          first ='no';
					         if(num == 3){
					         	num == 0;
					         	 break;
					         }
					      }
					      }
					    html += '</tr>';
					}
				}
				$("#showUser").html(html);
				$(".productmix-chooseuser-window").show();
	     },
	     error: function(){
	      alert("服务器异常，加载用户组数据失败！");
	     }
	 })
});
/**pdf预览**/
function viewPdf(_this,partNumber,partType,isColl){
	if("" == _this || null == _this){
		layer.alert("Not found Data !");
		return;
	}
	var pdfNum = _this.split(",");
	if(pdfNum.length >1){//跳转至相对应的锚点
		var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
	    var r1 = window.location.search.substr(1).match(reg1);
	    if(r1!=null){
	    	r1=unescape(r1[2]);
	    }
      window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-particulars.jsp?goMinute="+partNumber+"&tempPartMark="+r1+"&partId="+partType+"&goPdf=-1";//isColl
	}else{
		$.ajax({
			url : getContextPathForWS()+"/partComponentArrt/setPdfnameSession.do",
			data:'files='+_this,
			cache : false,
			type: 'post',
			dataType : "json",
			success : function(json) {
				if(json == "notFound"){
					layer.alert("服务器找不到相对应的数据手册！");
				}else{
				  for(var i=0;i<json.length;i++){
					  window.location.href=getContextPathForWS()+"/generic/web/viewer.html?file="+getContextPathForWS()+"/partComponentArrt/displayPDF.do";				  
			       }
				}
			},
			error : function() {
				layer.alert("数据连接异常,注册失败！");
			}
		});
	}
}
//单板创建
function addProductBroad(){
  closeUpdateBtn();
	loadProperties();//国际化
    zTree = $.fn.zTree.getZTreeObj("bomtree");
   var treeNode=zTree.getSelectedNodes();
   if("" == treeNode || null == treeNode){
       layer.alert($.i18n.prop("proAlertMsg"));
       return;
   }
   var nodeStage = treeNode[0].nodeStage;
   if (nodeStage != "1") {
		layer.alert($.i18n.prop("proAlertMsg4"));
		$('#btn0').hide();
		$('#btn1').hide();
		$('#btn2').hide();
		$('#btn3').hide();
		$('#btn4').hide();
		return;
	}
   $.ajax({
		url: 'productManageController/getRightAndUser.do',
		dataType: 'json',
		cache: false,
		type: 'post',
		success: function(json){
			var dataRightList=json.dataRightList;
			var user=json.user;
			var userC = user.loginName;//当前登录人
			var receiverC = treeNode[0].receiver;//节点接收创建人
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			if(user==undefined){
				user=new Object;
			}
			if(userC != receiverC){//当前登录人和节点创建人不同时查看是否有权限操作此功能
				if(dataRightList.length <= 0){
					layer.alert($.i18n.prop("proAlertMsg2"));
					   $('#btn0').hide();
				       $('#btn1').hide();
				       $('#btn2').hide();
				       $('#btn3').hide();
				       $('#btn4').hide();
				       return;
				}else{
			        var html="";
			         for(var i=0;i<dataRightList.length;i++){
				       if(dataRightList[i].rightsNote=="cdb"){//拥有创建单板的权限
					     html = "yes";
				       }
			          }
			        if(html == "yes"){
				       if(nodeStage == '1'){
					  $("#p1").show();
				       $("#btn2").show();
				       $("#attr0").css('display','none'); 
				       $("#attr1").css('display','none'); 
				       $("#attr2").css('display','block'); 
				       $("input[name='boardProductName']").val('');
				       $("input[name='productNo']").val('');
				       $("input[name='productCode']").val('');
				       $("#productStage").val('');  
				       $("#pTool").val('');  
				       $("#version").text("A");
			        }else{
						layer.alert($.i18n.prop("proAlertMsg2"));
						   $('#btn0').hide();
					       $('#btn1').hide();
					       $('#btn2').hide();
					       $('#btn3').hide();
					       $('#btn4').hide();
					       return;
					}
			     }else{
			     layer.alert($.i18n.prop("proAlertMsg2"));
						   $('#btn0').hide();
					       $('#btn1').hide();
					       $('#btn2').hide();
					       $('#btn3').hide();
					       $('#btn4').hide();
					       return;
			     }
			}
		}else{
			if(nodeStage == '1'){
				   $("#p1").show();
			       $("#btn2").show();
			       $("#attr0").css('display','none'); 
			       $("#attr1").css('display','none'); 
			       $("#attr2").css('display','block'); 
			       $("input[name='boardProductName']").val('');
			       $("input[name='productNo']").val('');
			       $("input[name='productCode']").val('');
			       $("#productStage").val('');  
			       $("#pTool").val('');  
			       $("#version").text("A");
			   }else{
				   layer.alert($.i18n.prop("proAlertMsg4"));
				   $('#btn0').hide();
			       $('#btn1').hide();
			       $('#btn2').hide();
			       $('#btn3').hide();
			       $('#btn4').hide();
			       return;
			   }
		}
		},
		error: function(json){
			layer.alert($.i18n.prop("alertError"));
		}
	})
	removeDisabled();//移除只读效果
}
//产品设计管理------BOM切换
function switchBom(pageNo){
	var productId = $("#switchBom").val();
	var excelName = $("#switchBom option:checked").text();
	  if(isNaN(pageNo)){
	    		pageNo = 0;
	   }
	   var addBasic = '1';
	   pageNo = parseInt(addBasic)+parseInt(pageNo);
	  $.ajax({
	     url: 'productManageController/selectPartDataByBom.do',
	     data:{'productId':productId,'excelName':excelName,'pageNo':pageNo},
	     dataType: 'json',
	     cache: false,
	     success: function(json){
	     	var html = '';
	     	html += '<tr>';
	        html += '<th>'+'<input type="checkbox" id="checkAll" onclick="checkAll()"/>'+'</th>';
	        for(var x=0;x<json.title.length;x++){
	        	if("zh" == json.lang){
	        	   html += '<th>'+json.title[x].showName+'</th>';
	        	}else{
	        	  html += '<th>'+json.title[x].englishName+'</th>';
	        	}
	        }
	        if("zh" == json.lang){
	        	 html += '<th>'+'数量'+'</th>';
	        	  html += '<th>'+'更多信息'+'</th>';
	        }else{
	            html += '<th>'+'Number'+'</th>';
	            html += '<th>'+'More'+'</th>';
	        }
	        html += '</tr>';
	        if(null == json.list){
	           html +='<tr>';
			   html +='<td colspan="8" align="center">'+$.i18n.prop("noData")+'</td>';
			   html +='</tr>';
			   $("#showPartdata").html(html);
			   $("#switchBom").hide();//BOM下拉框切换
			   $(".BOMname").hide();;//BOM下拉框切换
			   $("#deleteDataFrombom").hide();
	        }else{
	        	var list = json.list;
	        	var count = json.count;
	        	var pageNo = json.pageNo;
	        	var pageSize= json.pageSize;
	        	for(var i=0;i<list.length;i++){
	        		var partData = list[i];
	        		var PartNumber = (typeof(partData.PartNumber) == "undefined")?"":partData.PartNumber;
	        		var Part_Type = (typeof(partData.Part_Type) == "undefined")?"":partData.Part_Type;
	        		var ITEM = (typeof(partData.ITEM) == "undefined")?"":partData.ITEM;
	        		var Part_Reference = (typeof(partData.Part_Reference) == "undefined")?"":partData.Part_Reference;
	        		var Value = (typeof(partData.Value) == "undefined")?"":partData.Value;
	        		var Numbers = (typeof(partData.Numbers) == "undefined")?"":partData.Numbers;
	        		var Country = (typeof(partData.Country) == "undefined")?"":partData.Country;
	        		var Manufacturer = (typeof(partData.Manufacturer) == "undefined")?"":partData.Manufacturer;
	        		var KeyComponent = (typeof(partData.KeyComponent) == "undefined")?"":partData.KeyComponent;
	        		var Datesheet = (typeof(partData.Datesheet) == "undefined")?"":partData.Datesheet;
	        	    html += '<tr>';
	        	    html += '<td>'+'<input type="checkbox" onclick="checkOne('+partData.bomPnId+')" name="checkOne" value="'+partData.bomPnId+'">'+'</td>';
	        	    html += '<td>'+Part_Type+'</td>';
	        	    html += '<td>'+ITEM+'</td>';
	        	    html += '<td>'+Manufacturer+'</td>';
	        	    html += '<td class="zhiliang">'+KeyComponent+'</td>';
	        	    html += '<td class="data-bookTd">'+"<a class=\"data-book\" href=\"javascript:viewPdf('"+Datesheet+"','"+PartNumber+"','"+partData.id+"','"+partData.isCollection+"')\"><img src=\"images/PDF.png\" />"+"Datesheets"+"</a>" +'</td>';
	        	    html += '<td class="number">'+Numbers+'</td>';
	        	    html += '<td class="more">'+'<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='+PartNumber+'&&partId='+partData.id+'&tempPartMark=null">'+$.i18n.prop("proGominuBtn")+'</a>'+'</td>';
	        	    html += '</tr>';
	        	}
	        	//分页插件
	    			if($("#Pagination").html().length == ''){
	    			       $("#Pagination").pagination(
	    			    		   count,
	    	    	                {
	    	    	                    items_per_page : pageSize,
	    	    	                    num_edge_entries : pageNo,
	    	    	                    num_display_entries : 8,
	    	    	                    callback: function(pageNo, panel){
	    	    	                       if(count==null){
	    	    	                    	   showPart(id,pageNo);
							                 }
	    	    	                   },
	    	   	                    link_to:"javascript:void(0);"
	    	    	        });
	    			}
	    		    $("#showPartdata").html(html);
	    		    $("#deleteDataFrombom").show();
	    			count=null;
	        }
	     },
	     error: function(){
	       layer.alert($.i18n.prop("alertError"));
	     }
	  })
	
}
//添加只读状态
function addDisabled(){
	 $("input[name='productName']").attr("onfocus","this.blur()");
     $("#firthUser").prev().hide();
     $("input[name='boardProductName']").attr("onfocus","this.blur()");
     $("input[name='productNo']").attr("onfocus","this.blur()");
     $("#productStage").attr("onfocus","this.defaultIndex=this.selectedIndex;");
     $("#productStage").attr("onchange","this.selectedIndex=this.defaultIndex;");
     $("#pTool").attr("onfocus","this.defaultIndex=this.selectedIndex;");
     $("#pTool").attr("onchange","this.selectedIndex=this.defaultIndex;");
}
//取消只读状态
function removeDisabled(){
	$("input[name='productName']").removeAttr("onfocus");
	$("#firthUser").prev().show();
    $("input[name='boardProductName']").removeAttr("onfocus");
    $("input[name='productNo']").removeAttr("onfocus");
    $("#productStage").removeAttr("onfocus");
    $("#productStage").removeAttr("onchange");
    $("#pTool").removeAttr("onfocus");
    $("#pTool").removeAttr("onchange");
}