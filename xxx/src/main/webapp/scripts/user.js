/** 权限、组、用户主要js */


/**
 * 全选、反选
 * @param {触发单击事件input的Id} fircb
 * @param {复选框的name值} name
 */
function selectCheckBox(fircb,name){
	var j=0;
	$.each($("input[name='"+name+"']"),function(i,o){
		if($(o).prop("checked")==false){
			if($("#"+fircb).prop("checked")==true){
				$(o).prop("checked",true);
				j++;
			}
		}
    });
	if(j==0){
		if($("#"+fircb).prop("checked")==false){
			$("input[name='"+name+"']").prop("checked",false);
		}
	}
}
function initUserTalbe(){
	$("#user").show();
	$("#department").hide();
	$("#post").hide();
	$("#group").hide();
	$("#limits").hide();
	$("#addlimit").hide();
}
function initPostTalbe(){
	$("#post").show();
	$("#department").hide();
	$("#user").hide();
	$("#group").hide();
	$("#limits").hide();
	$("#addlimit").hide();
}
function initAddlimitTalbe(){
	$("#addlimit").show();
	$("#post").hide();
	$("#department").hide();
	$("#user").hide();
	$("#group").hide();
	$("#limits").hide();
	initAddRightsTree();
}
//初始化添加权限页面权限树
function initAddRightsTree(){
	var setting = {
 		view : {
			showIcon : false,
			showLine : false
		},
		callback : {
			onClick : rightNodeOnClick
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	$.ajax({
		url : "HrRightsController/selectHrRightsByGroupId.do",
		dataType : "json",
		data:{"groupId":""},
		cache : false,
		success : function(json) {
			//生成树
			var zNodes =json.rightsList;
			$.fn.zTree.init($("#addRightsTree"), setting, zNodes);
			var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
			treeObj.expandAll(true); // 默认展开全部
		},
		error : function() {
		}
	});
}
//添加权限页面单击权限节点事件
function rightNodeOnClick(event, treeId, treeNode, clickFlag){
	var isDataRights=treeNode.dataRights==false? "否":"是";
	var menuUrl=treeNode.menuUrl==undefined? "无":treeNode.menuUrl;
	$("#rightNode").html("<tr><td></td><td>"+treeNode.name+"</td><td>"+menuUrl+"</td><td>"+isDataRights+"</td></tr>");
}
//删除权限信息
function deleteRights(){
	loadProperties();
	var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length==0){
		layer.alert($.i18n.prop("proAlertMsg"));
		return;
	}
	if(nodes[0].children!=undefined){
		layer.alert($.i18n.prop("qxzmjjd"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"),$.i18n.prop("resetBtn") ]// 按钮
	}, function() {
		$.ajax({
			url : "HrRightsController/deleteHrRights.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"id":nodes[0].id
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg3"));
				initAddRightsTree();
				$("#rightNode").html("");
			},
			error : function() {
			}
		});
	});
}
//点击添加节点按钮
function beforeAddRights(type){
	loadProperties();
	var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length==0){
		layer.alert($.i18n.prop("proAlertMsg"));
		return;
	}
	
//	if(type!='0'&&nodes[0].dataRights==true){
//		layer.alert($.i18n.prop("qxzfsjqx"));
//		return;
//	}
	if(type=='1'){//添加同级节点
		$(".addlimitAddbtn1").attr("data-target","#addlimit-addWindow").attr("data-toggle","modal");
		$(".addlimit-addWindow").show();
	}
	if(type=='2'){//添加下级节点
		$(".addlimitAddbtn").attr("data-target","#addlimit-add1Window").attr("data-toggle","modal");
		$(".addlimit-add1Window").show();
	}
	if(type=='0'){//编辑
		var menuUrl=nodes[0].menuUrl==undefined? "无":nodes[0].menuUrl;
		$("#rightNodeName2").val(nodes[0].name);
		$("#rightNodeUrl2").val(menuUrl);
		$(".addlimitEditbtn").attr("data-target","#addlimit-editWindow").attr("data-toggle","modal");
		$(".addlimit-editWindow").show();
	}
}
//弹窗返回按钮
function backBtn(className){
	if(className==undefined || className==''){
		layer.alert('class名为空！');
		return;
	}
	$('.'+className).hide();
}
//添加同级节点弹窗中保存按钮
function addRights(){
	loadProperties();
	var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
	var nodes = treeObj.getSelectedNodes();
	var rightsName=$("#rightNodeName").val();
	if(rightsName==undefined||rightsName==''){
		layer.alert($.i18n.prop("qsrjdm"));
		return;
	}
	var url=$("#rightNodeUrl").val();
	var hrRights=new Object();
	hrRights.rightsName=rightsName;
	hrRights.url=url;
	hrRights.parentId=nodes[0].pId;
	hrRights.dataRights=nodes[0].dataRights;
	$.ajax({
			url : "HrRightsController/insertHrRights.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"jsonData":JSON.stringify(hrRights)
			},
			success : function(json) {
				layer.alert(json.message);
				initAddRightsTree();
				$("#rightNodeName").val("");
				$("#rightNodeUrl").val("");
				$(".addlimit-addWindow").hide();
				$(".modal-backdrop").hide();
			},
			error : function() {
			}
		});
}
//添加下级节点弹窗中保存按钮
function addRights1(){
	loadProperties();
	var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
	var nodes = treeObj.getSelectedNodes();
	var rightsName=$("#rightNodeName1").val();
	if(rightsName==undefined||rightsName==''){
		layer.alert($.i18n.prop("qsrjdm"));
		return;
	}
	var url=$("#rightNodeUrl1").val();
	var hrRights=new Object();
	hrRights.rightsName=rightsName;
	hrRights.url=url;
	hrRights.parentId=nodes[0].id;
	hrRights.dataRights=nodes[0].dataRights;
	$.ajax({
			url : "HrRightsController/insertHrRights.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"jsonData":JSON.stringify(hrRights)
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg2"));
				initAddRightsTree();
				$("#rightNodeName1").val("");
				$("#rightNodeUrl1").val("");
				$(".addlimit-add1Window").hide();
				$(".modal-backdrop").hide();
			},
			error : function() {
			}
		});
}
//编辑权限节点弹窗中保存按钮
function editRights(){
	loadProperties();
	var treeObj = $.fn.zTree.getZTreeObj("addRightsTree");
	var nodes = treeObj.getSelectedNodes();
	var rightsName=$("#rightNodeName2").val();
	var url=$("#rightNodeUrl2").val();
	var hrRights=new Object();
	hrRights.rightsName=rightsName;
	hrRights.url=url;
	hrRights.parentId=nodes[0].pId;
	hrRights.id=nodes[0].id;
	hrRights.dataRights=nodes[0].dataRights;
	hrRights.rightsNote=nodes[0].rightsNote;
	$.ajax({
			url : "HrRightsController/updateHrRights.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"jsonData":JSON.stringify(hrRights)
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg2"));
				initAddRightsTree();
				$("#rightNodeName2").val("");
				$("#rightNodeUrl2").val("");
				$(".addlimit-editWindow").hide();
				$(".modal-backdrop").hide();
			},
			error : function() {
			}
		});
}
function initDepartmentTalbe(){
	$("#post").hide();
	$("#department").show();
	$("#user").hide();
	$("#group").hide();
	$("#limits").hide();
	$("#addlimit").hide();
}
function initGroupTab(){
	$("#post").hide();
	$("#department").hide();
	$("#user").hide();
	$("#group").show();
	$("#limits").hide();
	$("#addlimit").hide();
	$("#groupfircb").prop("checked",false);
	initGroupList();
}
//添加组信息
function addGroup(){
	loadProperties();
//	var groupIndex=$("#inGroupIndex").val();
	var groupName=$("#inGroupName").val();
//	if(groupIndex==""){
//		layer.alert($.i18n.prop("zbhbnwk"));
//		return;
//	}
	if(groupName==""){
		layer.alert($.i18n.prop("zmcbnwk"));
		return;
	}
	var group=new Object();
//	group.groupIndex=groupIndex;
	group.groupName=groupName;
	$.ajax({
		url : "HrGroupController/insertGroup.do",
		dataType : "json",
		data:{"group":JSON.stringify(group)},
		cache : false,
		type : "post",
		success : function(json) {
			
			layer.alert(json.message);
			if(json.isSucess!='0'){
				$(".group-addWindow").hide();
				initGroupList();
			}
		},
		error : function() {
			// alert("数据连接异常,注册失败！");
		}
	});
}
//修改组数据弹窗
function showEditGroupDia(){
	var message=en=='zh'?"请选择单条数据":"Please select single data";
	var id=$("input[name='groupCheckBox']:checkbox:checked")
	if(id.length!=1){
		layer.alert(message);
		return;
	}
	var tds=$("input[name='groupCheckBox']:checkbox:checked").parent().parent().find("td");
//	$("#edGroupIndex").val(tds.eq(1).text());
	$("#edGroupName").val(tds.eq(1).text());
	$(".group-editWindow").show();
    
}
//修改组数据
function editGroup(){
	var group=new Object();
	group.groupId=$("input[name='groupCheckBox']:checkbox:checked").val();
//	group.groupIndex=$("#edGroupIndex").val();
	group.groupName=$("#edGroupName").val();
	$.ajax({
		url : "HrGroupController/updateGroup.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"group" : JSON.stringify(group)
		},
		success : function(json) {
			layer.alert(json.message);
			if(json.isSucess!='0'){
				$(".group-editWindow").hide();
				initGroupList();
			}
		},
		error : function() {
		}
	});
}
//删除组数据
function  deleteGroup(){
	loadProperties();
	var message=en=='zh'?"请选中至少一条数据":"Please select at least one data";
	var ids=new Array;
	$("input[name='groupCheckBox']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-delete1"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
		}, function() {
		$.ajax({
			url : "HrGroupController/deleteGroup.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"ids" : JSON.stringify(ids)
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg3"));
				$("input[name='groupCheckBox']:checkbox:checked")
						.parent().parent().remove();
			},
			error : function() {
			}
		});
	});
}
//组管理中组列表初始化
var cou;//总数，用于控制添加时是否刷新分页信息
function initGroupList(pageNo){
	if(isNaN(pageNo)){
    	pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic)+parseInt(pageNo);
	$.ajax({
        	url: "HrGroupController/selectAllGroupByPage.do",
        	data: {'pageNo':pageNo},
  			dataType: "json",
  			cache: false, 
  			success: function(json){
  				var xscy=en=='zh'?"显示成员":"DisplayMember";
  				var mysj=en=='zh'?"没有数据":"No Data";
  				var html = '';
  				if(null == json.list || "" == json.list){
					html +='<tr>';
					html +='<td colspan="13" align="center">--'+mysj+'--</td>';
					html +='</tr>';
					$('#show-group').html(html);
				}else{
					var list = json.list;
        	        var pageNo = json.pageNo;
        	        var pageSize= json.pageSize;
        	        
        	        for(var i=0;i<list.length;i++){
        	        	var group=list[i];
        	        	html += "<tr><td><input type=\"checkbox\" name=\"groupCheckBox\" value=\""+group.groupId+"\"></td><td>"+group.groupName+"</td><td>"+group.createuserName+"</td><td>"+toDate(group.createtime)+"</td><td>"+group.modifyuserName+"</td><td>"+toDate(group.modifytime)+"</td><td class=\"edit-td\"><div class=\"user-btnGroup2\"><a class=\"btn btn-danger btn-xs group-member\" onclick=\"showUserDia(this);\"><span class=\"glyphicon glyphicon-star-empty\"></span>"+xscy+"</a></div></td></tr>"
        	        }
        	        //分页插件
    				if(cou != json.count){
    					cou=json.count
    			    	$("#groupPagination").pagination(json.count,{
    	    	    		items_per_page : pageSize,
    	    	            num_edge_entries : pageNo,
    	    	            num_display_entries : 8,
    	    	            callback: function(pageNo, panel){
    	    	            	if(list==null){
    	    	                	initGroupList(pageNo);
						    	}
    	    	            },
    	   	                link_to:"javascript:void(0);"
    	    	        });
					}
    			  	$('#show-group').html(html);
    			  	list =null;
				}
  			},
  			error: function(){
  				layer.alert("数据连接异常,注册失败！");
  			}
	});
}
//显示成员弹窗
var groupId=0;
function showUserDia(_this){
	var tds=$(_this).parent().parent().parent().find("td");
	groupId=tds.eq(0).find("input").val();
	$.ajax({
		url : "HrGroupController/showAllUserDia.do",
		dataType : "json",
		cache : false,
		type : "post",
		data:{"groupId":groupId},
		success : function(json) {
			if(json.userList==undefined){
				json.userList=new Array;
			}
			if(json.groupUserList==undefined){
				json.groupUserList=new Array;
			}
			var html="";
			var groupHtml="";
			for(var i=0;i<json.userList.length;i++){
				var user=json.userList[i];
				html+="<option value=\""+user.userId+"\">"+user.userName+"</option>"
			}
			$("#lbJobLis").html(html);
			for(var j=0;j<json.groupUserList.length;j++){
				var groupUser=json.groupUserList[j];
				groupHtml+="<option value=\""+groupUser.userId+"\">"+groupUser.userName+"</option>";
			}
			$("#sellbJobLis").html(groupHtml);
			$(".group-user-box").show();
		},
		error : function() {
		}
	});
}
function saveGroupUser(){
	if(groupId==0){
		return;
	}
	var options=$("#sellbJobLis").find("option");
	var userIds=new Array;
	if(options!=undefined){
		for(var i=0;i<options.length;i++){
			userIds.push(options.eq(i).val());
		}
	}
	$.ajax({
		url : "HrGroupController/saveGroupUser.do",
		dataType : "json",
		cache : false,
		type : "post",
		data:{"userIds":JSON.stringify(userIds),"groupId":groupId},
		success : function(json) {
			layer.alert(json.message);
			$(".group-user-box").hide();
		},
		error : function() {
		}
	});
}


//用户列表初始化
function initUserList(name,pageNo){
	var loginName = name;
	if(typeof(loginName) == "undefined" ){
    	loginName = "";
   }
   if(isNaN(pageNo)){
    	pageNo = 0;
   }
  var addBasic = '1';
  pageNo = parseInt(addBasic)+parseInt(pageNo);
  $.ajax({
        	url: "/cms_cloudy/user/selectAllUser.do",
        	data: {'loginName':loginName,'pageNo':pageNo},
  			dataType: "json",
  			cache: false, 
  			success: function(json){
  				var html = '';
  				if(null == json.list || "" == json.list){
					html +='<tr>';
					html +='<td colspan="13" align="center">--没有数据--</td>';
					html +='</tr>';
					  $('#show-user').html(html);
				}else{
					var list = json.list;
					var count = json.count;
        	        var pageNo = json.pageNo;
        	        var pageSize= json.pageSize;
					for( var i = 0;i<list.length;i++){
					var user = list[i];
					html +='<tr>';
				    html += '<td>'+'<input type="checkbox" onclick="checkOneUser('+user.userId+')" name="checkOneUser" value="'+user.userId+'">'+'</td>';
					html +='<td>'+user.employeeNumber+'</td>';
					html +='<td>'+user.userNumber+'</td>';
					html +='<td>'+user.userName+'</td>';
					html +='<td>'+user.loginName+'</td>';
					//<select name='type' class='form-control select-control' type='text' id='positionUpdate' name="positionUpdate"></select>
					html +='<td class="edit-select">'+'<select name="type" class="form-control select-control" type="text" id='+'position'+user.userId+' name="selectPos" disabled>'+'</select>'+'</td>';
					html +='<td>'+user.email+'</td>';
					html +='<td>'+user.telephone+'</td>';
					html +='<td>'+user.mobilePhone+'</td>';
					html +='<td class="edit-select">'+'<select name="type" class="form-control select-control" type="text" id='+'dept'+user.userId+' name="selectDept" disabled>'+'</select>'+'</td>';
					html +='<td class="center">'+user.createuser+'</td>';
					html +='<td>'+toDate(user.createtime)+'</td>';
					if(user.isOrNot == 0){
					html +='<td>'+'在职'+'</td>';	
					}else if(user.isOrNot == 1){
					html +='<td>'+'离职'+'</td>';
					}else{
					html +='<td>'+user.isOrNot+'</td>';	
					}
					html +='</tr>';
					selectFull3(user);
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
    	    	                    	   initUserList(name,pageNo);
						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	        });
    			}
    			  $('#show-user').html(html);
    			  count =null;
				}
  			},
  			error: function(){
  				layer.alert("数据连接异常,注册失败！");
  			}
          });
}
// 判断用户是否已经登录
function hasLogined() {
	$.ajax({
				url : "/cms_cloudy/login/hasLogined.do",
				cache : false,
				dataType : "json",
				success : function(json) {
					if (json.result == "yes") {
						$(".login-div>li").hide().next(".dropdown").show();
						$(".login-btn").attr("data-dismiss", "modal");
					}
				},
				error : function() {
					layer.alert("数据连接异常,注册失败！");
				}
			});
}
// 用户搜索
document.getElementById('userSearch').onclick=function(){
    var loginName = $("input[name='loginNameSearch']").val();
    if("" == loginName){
       $("#Pagination").html('');
       initUserList(loginName);
    }else{
       $("#Pagination").html('');
  	   initUserList(loginName);
    }
} 
//导入excel
 function readExcel(){
    	   $.ajaxFileUpload
	        (
	            {
	                url:'/cms_cloudy/user/readExcel.do',
	                secureuri:false,//一般设置为false
	                fileElementId:'bb',//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                dataType: 'json',//返回值类型 一般设置为json
	                success : function(data, status) // 服务器成功响应处理函数
				{
					layer.alert(data.message, {}, function() {
						         $("#Pagination").html('');
					             initUserList("");
								location.reload();
							});
				},
	                error: function (data, status, e)// 服务器响应失败处理函数
	                {
	                	layer.alert("数据连接异常,请联系管理员!");
	                }
	            }
	        )
       }
  //用户删除
 function deleteUser(pid){
 	var ids=new Array;
	$("input[name='checkOneUser']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length == 0){
	  layer.alert("请选择要删除的数据！");
	  return;
	}
	layer.confirm('请确认是否删除选中信息!', {
		btn : [ '确定', '取消' ]// 按钮
	}, function() {
    	    $.ajax({
            	url: "/cms_cloudy/user/deleteUser.do",
            	cache: false,
            	data: {"id":JSON.stringify(ids)},
            	dataType: "json",
            	success: function(json){
      	 			  layer.alert("删除成功！");
      	 			   $("#Pagination").html('');
          			   initUserList("");
            	},
            	error: function(){
    	 			  layer.alert("数据连接异常！");
            	}
            });
	});
 }
//日期格式转换
function toDate(v) {
    	var date = new Date();
    	if(isNaN(v.time)){
    		date.setTime(v);
    	}else{
    		date.setTime(v.time);
    	}
    	
    	var y = date.getFullYear();
    	var m = date.getMonth()+1;
    	m = m<10?'0'+m:m;
    	var d = date.getDate();
    	d = d<10?("0"+d):d;
    	var h = date.getHours();
    	h = h<10?("0"+h):h;
    	var M = date.getMinutes();
    	M = M<10?("0"+M):M;
    	var str = y+"-"+m+"-"+d;
    	return str;
 }
//初始化权限管理tab页
function initRightsTab(){
	$("#user").hide();
	$("#department").hide();
	$("#post").hide();
	$("#group").hide();
	$("#addlimit").hide();
	$("#limits").show();
	$("#groupSelectList").find("option:selected").val("0");
	initGroupSelectList();
	initRightsTree();
}
//初始化权限树
function initRightsTree(){
 	var groupId=$("#groupSelectList").find("option:selected").val();
 	var setting = {
 		view : {
			// removeHoverDom: removeHoverDom,
			showIcon : false,
			showLine : false
		},
		check: {
			enable: true
		},
		callback : {
			onClick : null
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	$.ajax({
		url : "HrRightsController/selectHrRightsByGroupId.do",
		dataType : "json",
		data:{"groupId":groupId},
		cache : false,
		success : function(json) {
			//生成树
			var zNodes =json.rightsList;
			$.fn.zTree.init($("#rightsTree"), setting, zNodes);
			var treeObj = $.fn.zTree.getZTreeObj("rightsTree");
			treeObj.expandAll(true); // 默认展开全部
		},
		error : function() {
			// layer.alert("数据连接异常,注册失败！");
		}

	});
}

//生成组下拉框
function initGroupSelectList(){
	$.ajax({
		url : "HrGroupController/selectAllGroup.do",
		dataType : "json",
		cache : false,
		success : function(json) {
			var html="<option value=\"0\">请选择</option>";
			$.each(json.hgList, function(index, content){
				html+="<option value=\"" + content.groupId + "\">" + content.groupName+ "</option>";
			});
			$("#groupSelectList").html(html);
		},
		error : function() {
			// layer.alert("数据连接异常,注册失败！");
		}
	});
}
//选择组后点编辑按钮
function checkGroupSelect(){
	var groupId=$("#groupSelectList").find("option:selected").val();
	if(groupId==0){
		layer.alert("请选择一个组");
		return;
	}
	$(".limit-edit").parent().hide().next().show();
}

//点击保存按钮，保存组权限关系
function saveGroupRights(){
	var groupId=$("#groupSelectList").find("option:selected").val();
	var treeObj = $.fn.zTree.getZTreeObj("rightsTree");
	var nodes=treeObj.getChangeCheckedNodes();
	var removeRights=new Array;
	var addRights=new Array;
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].checked==false){
			removeRights.push(nodes[i].id);
		}else{
			addRights.push(nodes[i].id);
		}
	}
	$.ajax({
		url : "HrRightsController/insertHrRightsGroup.do",
		dataType : "json",
		type : "post",
		data:{"removeRights":JSON.stringify(removeRights),
			  "addRights":JSON.stringify(addRights),
			  "groupId":groupId
		},
		cache : false,
		success : function(json) {
			initRightsTree();
			$(".limit-save").parent().hide().prev().show();
		},
		error : function() {
			// layer.alert("数据连接异常,注册失败！");
		}
	});
}
function hideGroupDia(){
	$(".group-addWindow").hide();
}
function hideGroupeditDia(){
	$(".group-editWindow").hide();
}
/******权限组按钮替换+input框可选*****/

$(".limit-cancel").click(function(){
    $(this).parent().hide().prev().show();
});

