
/** 复选框全选反选操作* */
function checkAll() {
	var CheckBox = document.getElementsByName('checkOne');
	if ($("#checkAll").prop('checked')) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked == false) {
				CheckBox[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked = true) {
				CheckBox[i].checked = false;
			}
		}
	}
}
/** 复选框单个选择* */
function checkOne(id) {
	var CheckBox = document.getElementsByName('checkOne');
	var num = 0;
	if (CheckBox.length > 0) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked) {
				num++;
			}
		}
	}
	if (num == CheckBox.length) {
		$("#checkAll").prop('checked', true);
	} else {
		$("#checkAll").prop('checked', false);
	}
}

/** pdf预览* */
function viewPdf(_this, partNumber, partType) {
	if ("" == _this || null == _this) {
		layer.alert("Not found Data !");
		return;
	}
	var pdfNum = _this.split(",");
	if (pdfNum.length > 1) {// 跳转至相对应的锚点
		var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
		var r1 = window.location.search.substr(1).match(reg1);
		if (r1 != null) {
			r1 = unescape(r1[2]);
		}
	      window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-particulars.jsp?goMinute="+partNumber+"&tempPartMark="+r1+"&partId="+partType+"&goPdf=-1";//isColl
	} else {
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

var excelmap = "";// 解析后的excel数据,全局变量用于切换sheet
var excelName = "";// 上传的文件名
var productId=0;// 选择的单板id
// 初始化导入bom弹窗
function initExportExcelDia() {
	zTree = $.fn.zTree.getZTreeObj("bomtree");
	var treeNode = zTree.getSelectedNodes();
	if(treeNode.length==0){
		layer.alert($.i18n.prop("selectProductNode"));  
		return;
	}
	if(treeNode[0].pId==null){
		layer.alert($.i18n.prop("selectProductNode"));
		return;
	}
	productId = treeNode[0].id;
	var realId=treeNode[0].realId;
	if(realId!=undefined){// 此时所选节点为文件节点
		productId=productId-realId;
		/* $(".leadBomBtn").attr("data-toggle","modal").attr("data-target","#leadBomWindow").attr("data-dismiss","modal"); */
	}
	var sheetTable = "<tr><td  align=\"center\">" + $.i18n.prop("noData")
			+ "</td></tr>";
	$("#sheetTable").html(sheetTable);
	var sheetHtml = "<option>无</option>";
	$("#sheetSelect").html(sheetHtml);
	$("#checkExcelBtn").hide();
	$("#exportExcelBtn").hide();
	$("#leadBomWindow").show();
	$(".leadBomWindow").show();
}
// 解析bom的excel文件
function exportBomExcel() {
	var fileType = $("#bomExcel").val();
	excelName = fileType;
	var fileName = fileType.substring(fileType.lastIndexOf(".") + 1)
			.toLowerCase();
	if (fileName != "xls" && fileName != "xlsx") {
		layer.alert("请选择excel格式文件上传！");
		fileType = "";
		return
	}
	$.ajaxFileUpload({
				url : 'productBomController/getExcelContent.do',
				secureuri : false,
				fileElementId : 'bomExcel',
				dataType : 'json',
				success : function(data, status) {
					if (data.msg == '1') {
						$("#bomExcel").val("");
						excelmap = data.excelmap;// 解析后的excel数据,全局变量用于切换sheet
						var sheetNameList = data.sheetNameList;// sheet名集合
						var sheetHtml = "";
						for (var i = 0; i < sheetNameList.length; i++) {
							sheetHtml += "<option value=\"" + sheetNameList[i]
									+ "\">";
							sheetHtml += sheetNameList[i];
							sheetHtml += "</option>";
						}
						$("#sheetSelect").html(sheetHtml);
						// 构建数据表格
						if (sheetNameList != undefined
								&& sheetNameList.length != 0) {
							getDataBySheetName(sheetNameList[0]);// 构建数据表格
						}
					}
				},
				error : function(data, status, e)// 服务器响应失败处理函数
				{
					layer.alert("数据连接异常,请联系管理员!");
				}
			})
}

// 切换sheet
function changeSheet() {
	// 构建数据表格
	var sheetName = $("#sheetSelect").val();
	getDataBySheetName(sheetName);
}
// 根据sheetName构建数据表格
function getDataBySheetName(sheetName) {
	if (sheetName != undefined && sheetName != '') {
		var rowList = excelmap[sheetName];
		var firstTr = "<tr>";// 动态生成第一行下拉框
		var sheetTable = "";
		var srowHtml="";
		for (var i = 0; i < rowList.length; i++) {
			// 生成startRow行标
			srowHtml+="<option value=\""+(i+1)+"\">"+(i+1)+"</option>"
			var row = rowList[i];
			var dataTr = "<tr>"
			for (var j = 0; j < row.length; j++) {
				if (i == 0) {
					firstTr += "<td><select class=\"form-control\" name=\"filedName\"><option value=\"0\"></option><option value=\"PartNumber\">PartNumber</option><option value=\"KeyComponent\">reference</option></select></td>";
				}
				dataTr += "<td><span>" + row[j] + "</span></td>";
				if (i == 0 && j == row.length - 1) {
					firstTr += "</tr>";
					sheetTable += firstTr;
				}
			}
			dataTr += "</tr>";
			sheetTable += dataTr;
		}
		
		if (rowList.length == 0) {
			sheetTable += "<tr><td  align=\"center\">" + $.i18n.prop("noData")
					+ "</td></tr>";
		}else{
			$("#checkExcelBtn").show();
		}
		$("#sheetTable").html(sheetTable);
		$("#startRow").html(srowHtml);
	}
}
// 检查
var dataMap=new Object();// 保存数据
function checkExcelData() {
	var startRow = parseInt($("#startRow").val());
	var selectList = $("select[name='filedName']");
	var index = new Array();
	var filedNameArray = new Array();
	for (var i = 0; i < selectList.length; i++) {
		var s = selectList.eq(i);
		if (s.val() != 0) {// 获取当前列的值
			index.push(i);
			filedNameArray.push(s.val());
		}
	}
	if (index.length == 0) {
		layer.alert("请选择要检查的列");
		return;
	}
	var a = new Array();
	var trs = $("#sheetTable").find("tr");
	if (trs == undefined || trs.length < startRow + 1) {
		layer.alert("无检查数据!");
		return;
	}
	for (var j = startRow; j < trs.length; j++) {
		var dataObj = new Object();
		for (var i = 0; i < index.length; i++) {
			var cell = trs.eq(j).children("td").eq(index[i]).find("span");
			dataObj[filedNameArray[i]] = cell.html();
		}
		a.push(dataObj);
	}
	var lock=1;
	$.ajax({
		url : "productBomController/checkExcelData.do",
		data : {
			"jsonData" : JSON.stringify(a)
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json) {
			if (json.msg == '1') {// 查不到的数据变红
					var ins=new Array();
					var trs = $("#sheetTable").find("tr");
					for(var i=1;i<3;i++){
						var cells=trs.eq(i).find("td");
						for(var j=0;j<cells.length;j++){
							var cell=cells.eq(j).find("span");
							if(cell.html()=='Quantity'){
								ins.push(j);
								continue;
							}
							if(cell.html()=='Reference'){
								ins.push(j);
								continue;
							}
							if(cell.html().toLowerCase()=='Part_Number'.toLowerCase()){
								ins.push(j);
								continue;
							}
						}
					}
					if(ins.length!=3){
						layer.alert("文档表头数据有误！");
						return;
					}
					$("#sheetTable").find("td").removeAttr("style");
					for (var j = startRow; j < trs.length; j++) {
						if (json.indexStr.indexOf("," + (j - startRow) + ",") != -1) {// 定位出检查不到的行
							lock=0;
							for (var i = 0; i < index.length; i++) {
								var cell = trs.eq(j).children("td").eq(index[i]);
								cell.attr("style", "background: #FF0000");
							}
						}else{
							for (var i = 0; i < index.length; i++) {
								var cell = trs.eq(j).children("td").eq(index[i]);
								cell.attr("style", "background: #00FF00");
							}
					}
				}
				if(startRow>=trs.length){
					lock=0;
				}
				
				if(lock==1){// 可以上传时，保存数据
					var productBomPnList=new Array();
					
					// 保存数据
					for(var i=startRow;i<trs.length;i++){
						var productBomPn=new Object();// 保存数据
						var cells=trs.eq(i);
						productBomPn.numbers=cells.find("td").eq(ins[0]).find("span").html();
						productBomPn.reference=cells.find("td").eq(ins[1]).find("span").html();
						productBomPn.partNumber=cells.find("td").eq(ins[2]).find("span").html();
						productBomPn.sheetName=$("#sheetSelect").val();
						productBomPnList.push(productBomPn);
					}
					dataMap[$("#sheetSelect").val()]=productBomPnList;
				}else if(lock==0){
					$("#exportExcelBtn").hide();
				}
				if(Object.getOwnPropertyNames(dataMap).length==$("#sheetSelect").find("option").length){
					$("#exportExcelBtn").show();
				}
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
// 上传检查后的数据
function saveProductBomPn(){
	var bomName=$("#bomName").val();
	if(bomName==undefined||bomName==""){
		layer.alert("请输入bom名");
		return;
	}
	$.ajax({
		url : "productBomController/saveProductBomPn.do",
		data : {
			'dataMap':JSON.stringify(dataMap),
			'bomName':bomName,
			'productId':productId
			
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json) {
			$(".leadBomWindow").hide();
// showBomPnPart(0);
			location.reload();
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
/** bom管理页面树初始化* */
function initProBomPnTree() {
	var setting = {
		view : {
			// removeHoverDom: removeHoverDom,
			showIcon : false,
			showLine : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		check: {
			enable: true
		},
		callback : {
			onClick : bomPNodeOnClick
		}
	};
	$.ajax({
				url : "productManageController/selectSelectProductBomList.do",
				dataType : "json",
				cache : false,
				success : function(json) {
					if (null != json && "" != json) {
						$.fn.zTree.init($("#bomtree"), setting,json);
						var treeObj = $.fn.zTree.getZTreeObj("bomtree");
						treeObj.expandAll(true); // 默认展开全部
						// 获取节点
						// 返回一个根节点
						var node1 = treeObj.getNodesByFilter(
								function(node1) {
									return node1.nodeStage == 1
								}, true);
						var node2 = treeObj.getNodesByFilter(
								function(node2) {
									return node2.nodeStage == 2
								}, true);
						var node3 = treeObj.getNodesByFilter(
								function(node3) {
									return node3.nodeStage == 3
								}, true);
						var node4 = treeObj.getNodesByFilter(
								function(node4) {
									return node4.nodeStage == 4
								}, true);
						if (null != node2) {
							node1 = null;
						}
						if (null != node3) {
							node2 = null;
						}
						if (null != node4) {
							node3 = null;
						}
						if (null != node1) {
							treeObj.selectNode(node1);// 选择该节点
							showBomPnPart(0);
						}
						if (null != node2) {
							treeObj.selectNode(node2);// 选择该节点
							showBomPnPart(0);
						}
						if (null != node3) {
							treeObj.selectNode(node3);// 选择该节点
							showBomPnPart(0);
						}
						if (null != node4) {
							treeObj.selectNode(node4);// 选择该节点
							showBomPnPart(0);
						}
					}
				},
				error : function() {
					// alert("数据连接异常,注册失败！");
				}

			});
}
/** 选择某一个节点* */
function bomPNodeOnClick(event, treeId, treeNode, clickFlag) {
	if(treeNode.pId==null){// 选中的不是单板时
		$("#switchBom").hide();// BOM下拉框切换
		$(".BOMname").hide();// BOM下拉框切换
		return;
	}
	if(treeNode.children==undefined||treeNode.children.length==0){
		$("#switchBom").hide();// BOM下拉框切换
		$(".BOMname").hide();// BOM下拉框切换
	}else{
		$("#switchBom").show();// BOM下拉框切换
		$(".BOMname").show();// BOM下拉框切换
	}
	showBomPnPart(0);
}
// bom管理元器件数据展示
var count;// 总数，用于控制添加时是否刷新分页信息
var globalPageNo=0;
function showBomPnPart(pageNo) {
	loadProperties();// 国际化
	zTree = $.fn.zTree.getZTreeObj("bomtree");
	var treeNode = zTree.getSelectedNodes();
	var productId = treeNode[0].id;
	var realId=treeNode[0].realId;
	var name="";
	if(realId!=undefined){// 此时所选节点为文件节点
		name=treeNode[0].name;
		productId=productId-realId;
	}
	if (isNaN(pageNo)) {
		pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic) + parseInt(pageNo);
	globalPageNo=pageNo-1;
	$.ajax({
		url : 'productBomController/selectProductBomByBomPn.do',
		data : {
			'productId' : productId,
			'pageNo' : pageNo,
			'excelName':name
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			var list = json.list;
			var bomList = json.bomList;
			var pageNo = json.pageNo;
			var pageSize = json.pageSize;
			var html = '';
			html += '<tr>';
			html += '<th>'
					+ '<input type="checkbox" id="checkAll" onclick="checkAll()"/>'
					+ '</th>';
			for (var x = 0; x < json.title.length; x++) {
				if ("zh" == json.lang) {
					html += '<th>' + json.title[x].showName + '</th>';
				} else {
					html += '<th>' + json.title[x].englishName + '</th>';
				}
			}
			if ("zh" == json.lang) {
				html += '<th>' + '数量' + '</th>';
				html += '<th>' + '更多信息' + '</th>';
				html += '<th>' + '备注' + '</th>';
			} else {
				html += '<th>' + 'Number' + '</th>';
				html += '<th>' + 'More' + '</th>';
				html += '<th>' + 'remark' + '</th>';
			}
			html += '</tr>';
			if (null == json.list) {
				html += '<tr>';
				html += '<td colspan="9" align="center">'
						+ $.i18n.prop("noData") + '</td>';
				html += '</tr>';
				$("#switchBom").hide();// BOM下拉框切换
				$(".BOMname").hide();// BOM下拉框切换
			} else {
				for (var i = 0; i < list.length; i++) {
					var partData = list[i];
					var PartNumber = (typeof(partData.PartNumber) == "undefined")
							? ""
							: partData.PartNumber;
					var Part_Type = (typeof(partData.Part_Type) == "undefined")
							? ""
							: partData.Part_Type;
					var ITEM = (typeof(partData.ITEM) == "undefined")
							? ""
							: partData.ITEM;
					var Part_Reference = (typeof(partData.Part_Reference) == "undefined")
							? ""
							: partData.Part_Reference;
					var Value = (typeof(partData.Value) == "undefined")
							? ""
							: partData.Value;
					var Numbers = (typeof(partData.Numbers) == "undefined")
							? ""
							: partData.Numbers;
					var Country = (typeof(partData.Country) == "undefined")
							? ""
							: partData.Country;
					var Manufacturer = (typeof(partData.Manufacturer) == "undefined")
							? ""
							: partData.Manufacturer;
					var KeyComponent = (typeof(partData.KeyComponent) == "undefined")
							? ""
							: partData.KeyComponent;
					var Datesheet = (typeof(partData.Datesheet) == "undefined")
							? ""
							: partData.Datesheet;
					html += '<tr>';
					html += '<td>'
							+ '<input type="checkbox" onclick="checkOne('
							+ partData.id + ')" name="checkOne" value="'
							+ partData.bomPnId + '">' + '</td>';
					html += '<td>' + Part_Type + '</td>';
					html += '<td>' + ITEM + '</td>';
					html += '<td>' + Manufacturer + '</td>';
					html += '<td class="zhiliang">' + KeyComponent + '</td>';
					html += '<td class="data-bookTd">'
							+ "<a class=\"data-book\" href=\"javascript:viewPdf('"
							+ Datesheet + "','" + PartNumber + "','"
							+ partData.id + "')\"><img src=\"images/PDF.png\" />"
							+ "Datesheets" + "</a>" + '</td>';
					html += '<td class="number">' + Numbers + '</td>';
					html += '<td class="more">'
							+ '<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='
							+ PartNumber + '&partId=' + partData.id
							+ '&tempPartMark=null">'
							+ $.i18n.prop("proGominuBtn") + '</a>' + '</td>';
					html += '<td class="beizhu">'
						+ "<select class=\"form-control\" name=\"remark\">";
					if(partData.remark=='装、待调'){
						html +='<option selected="true" value="装、待调">装、待调</option><option value="不装、待调">不装、待调</option><option value=""></option>';
					}else if(partData.remark=='不装、待调'){
						html +='<option  value="装、待调">装、待调</option><option selected="true" value="不装、待调">不装、待调</option><option value=""></option>';
					}else{
						html +='<option  value="装、待调">装、待调</option><option value="不装、待调">不装、待调</option><option selected="true" value=""></option>';
					}
					html+="</select>"
							+ '</td>';
					
					html += '</tr>';
				}
				// BOM名称加载开始
				var bomHtml = '';
	            for(var a=0;a<bomList.length;a++){
	            	bomHtml += '<option value="'+bomList[a].pId+'">'+bomList[a].name+'</option>';
	            }
	 			// BOM名称加载结束
			    $("#switchBom").html(bomHtml);
			}
			
			// 分页插件
			if (count != json.count) {
				count=json.count;
				$("#Pagination").pagination(count, {
							items_per_page : pageSize,
							num_edge_entries : pageNo,
							num_display_entries : 8,
							callback : function(pageNo, panel) {
								if (list == null) {
									showBomPnPart(pageNo);
								}
							},
							link_to : "javascript:void(0);"
						});
			}
			
			$("#showPartdata").html(html);
			list = null;
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
// bom切换
function switchBom(){
	var productId = $("#switchBom").val();
	var excelName = $("#switchBom option:checked").text();
	var	pageNo = 0;
	var addBasic = '1';
	pageNo = parseInt(addBasic)+parseInt(pageNo);
	$.ajax({
		url : 'productBomController/selectProductBomByBomPn.do',
		data : {
			'productId' : productId,
			'pageNo' : pageNo,
			'excelName':excelName
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			var list = json.list;
			var bomList = json.bomList;
			var pageNo = json.pageNo;
			var pageSize = json.pageSize;
			var html = '';
			html += '<tr>';
			html += '<th>'
					+ '<input type="checkbox" id="checkAll" onclick="checkAll()"/>'
					+ '</th>';
			for (var x = 0; x < json.title.length; x++) {
				if ("zh" == json.lang) {
					html += '<th>' + json.title[x].showName + '</th>';
				} else {
					html += '<th>' + json.title[x].englishName + '</th>';
				}
			}
			if ("zh" == json.lang) {
				html += '<th>' + '数量' + '</th>';
				html += '<th>' + '更多信息' + '</th>';
				html += '<th>' + '备注' + '</th>';
			} else {
				html += '<th>' + 'Number' + '</th>';
				html += '<th>' + 'More' + '</th>';
				html += '<th>' + 'remark' + '</th>';
			}
			html += '</tr>';
			if (null == json.list) {
				html += '<tr>';
				html += '<td colspan="9" align="center">'
						+ $.i18n.prop("noData") + '</td>';
				html += '</tr>';
			} else {
				for (var i = 0; i < list.length; i++) {
					var partData = list[i];
					var PartNumber = (typeof(partData.PartNumber) == "undefined")
							? ""
							: partData.PartNumber;
					var Part_Type = (typeof(partData.Part_Type) == "undefined")
							? ""
							: partData.Part_Type;
					var ITEM = (typeof(partData.ITEM) == "undefined")
							? ""
							: partData.ITEM;
					var Part_Reference = (typeof(partData.Part_Reference) == "undefined")
							? ""
							: partData.Part_Reference;
					var Value = (typeof(partData.Value) == "undefined")
							? ""
							: partData.Value;
					var Numbers = (typeof(partData.Numbers) == "undefined")
							? ""
							: partData.Numbers;
					var Country = (typeof(partData.Country) == "undefined")
							? ""
							: partData.Country;
					var Manufacturer = (typeof(partData.Manufacturer) == "undefined")
							? ""
							: partData.Manufacturer;
					var KeyComponent = (typeof(partData.KeyComponent) == "undefined")
							? ""
							: partData.KeyComponent;
					var Datesheet = (typeof(partData.Datesheet) == "undefined")
							? ""
							: partData.Datesheet;
					html += '<tr>';
					html += '<td>'
							+ '<input type="checkbox" onclick="checkOne('
							+ partData.id + ')" name="checkOne" value="'
							+ partData.bomPnId + '">' + '</td>';
					html += '<td>' + Part_Type + '</td>';
					html += '<td>' + ITEM + '</td>';
					html += '<td>' + Manufacturer + '</td>';
					html += '<td class="zhiliang">' + KeyComponent + '</td>';
					html += '<td class="data-bookTd">'
							+ "<a class=\"data-book\" href=\"javascript:viewPdf('"
							+ Datesheet + "','" + PartNumber + "','"
							+ partData.id + "')\"><img src=\"images/PDF.png\" />"
							+ "Datesheets" + "</a>" + '</td>';
					html += '<td class="number">' + Numbers + '</td>';
					html += '<td class="more">'
						+ '<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='
						+ PartNumber + '&partId=' + partData.id
						+ '&tempPartMark=null">'
							+ $.i18n.prop("proGominuBtn") + '</a>' + '</td>';
					html += '<td class="beizhu">'
						+ "<select class=\"form-control\" name=\"remark\">";
					if(partData.remark=='装、待调'){
						html +='<option selected="true" value="装、待调">装、待调</option><option value="不装、待调">不装、待调</option><option value=""></option>';
					}else if(partData.remark=='不装、待调'){
						html +='<option  value="装、待调">装、待调</option><option selected="true" value="不装、待调">不装、待调</option><option value=""></option>';
					}else{
						html +='<option  value="装、待调">装、待调</option><option value="不装、待调">不装、待调</option><option selected="true" value=""></option>';
					}
					html+="</select>"
							+ '</td>';
					
					html += '</tr>';
				}
			}
			
			// 分页插件
			if (count != json.count) {
				count=json.count;
				$("#Pagination").pagination(count, {
							items_per_page : pageSize,
							num_edge_entries : pageNo,
							num_display_entries : 8,
							callback : function(pageNo, panel) {
								if (list == null) {
									showBomPnPart(pageNo);
								}
							},
							link_to : "javascript:void(0);"
						});
			}
			
			$("#showPartdata").html(html);
			list = null;
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	})
}
/** 删除bom中的元器件* */
function deleteProductBomPn() {
	loadProperties();// 国际化
	var ids = new Array;
	$("input[name='checkOne']:checkbox:checked").each(function() {
				ids.push($(this).val());
			});
	if (ids.length == 0) {
		layer.alert("请选中一条要删除的数据!");
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
			// 按钮
		}, function() {
		$.ajax({
					url : 'productBomController/deleteProductBomPn.do',
					data : {
						'ids' : JSON.stringify(ids)
					},
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert($.i18n.prop("alertMsg3"));
						$("#Pagination").html('');
						showBomPnPart(0);
					},
					error : function() {
						layer.alert("服务器连接异常，请联系管理员！");
					}
				})
	});
}
// 保存备注信息
function saveProBomRemark(){
	var proBomList = new Array;
	$("input[name='checkOne']:checkbox").each(function() {
		var proBom=new Object();
		proBom.id=$(this).val();
		var remark=$(this).parent().parent().find("select").val();
		proBom.remark=remark;
		proBomList.push(proBom);
	});
	$.ajax({
		url : 'productBomController/saveProBomRemark.do',
		data : {
			'jsonData' : JSON.stringify(proBomList)
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			layer.alert($.i18n.prop("alertMsg2"));
			showBomPnPart(globalPageNo);
		},
		error : function() {
			layer.alert("服务器连接异常，请联系管理员！");
		}
	});
}
// 初始化导出弹窗
var ProDataList="";// 用于添加列时显示数据
var fieldStr="";// 导出字段的下拉列表
function initOutBomWindow(state){
	fieldStr="";
	zTree = $.fn.zTree.getZTreeObj("bomtree");
	var treeNode = zTree.getSelectedNodes();
	var productId = treeNode[0].id;
	var realId=treeNode[0].realId;
	var excelName="";
	if(realId==undefined){
		layer.alert($.i18n.prop("qxzwjjd"));
		return;
	}
	if(realId!=undefined){// 此时所选节点为文件节点
		excelName=treeNode[0].name;
		productId=productId-realId;
		/* $(".outBomBtn").attr("data-toggle","modal").attr("data-target","#outBomWindow").attr("data-dismiss","modal"); */
		$(".outBomWindow").show();
		$("#outBomWindow").show();
	}
	var outType="";
	if(state=='0'){
		$("input[name='radiobutton']").eq(0).prop("checked",true);  
		outType="Single";
	}else{
		outType=$("input[name='radiobutton']:checked").val();
	}
	$.ajax({
		url : 'productBomController/initOutBomWindow.do',
		data : {
			'productId' : productId,
			'excelName':excelName,
			'outType':outType
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			// 生成字段的下拉列表
			var fieldList=json.fieldList;
			fieldStr+="<select class=\"form-control\" onchange='achieveProData(this);'><option value='0'>请选择</option>";
			for(var i=0;i<fieldList.length;i++){
				fieldStr+="<option value='"+fieldList[i].fieldName+"'>";
				fieldStr+=fieldList[i].showName;
				fieldStr+="</option>";
			}
			fieldStr+="</select>";
			var pList=json.pList;
			ProDataList=pList;
			var bHtml="";
			for(var i=0;i<pList.length;i++){
				var pro=pList[i];
				var keys=Object.keys(pro);
				bHtml+="<tr><td>"+(i+1)+"</td>";
				bHtml+="<td>";
				bHtml+=pro['numbers']==undefined?"":pro['numbers'];
				bHtml+="</td>";
				
				bHtml+="<td>";
				bHtml+=pro['PartCode']==undefined?"":pro['PartCode'];
				bHtml+="</td>";
				
				bHtml+="<td>";
				bHtml+=pro['reference']==undefined?"":pro['reference'];
				bHtml+="</td>";
				
				bHtml+="</tr>";	
			}
			$("#exportDataTable").html(bHtml);
			$("#exportFiledTr").find("td:gt(3)").remove();
		},
		error : function() {
			layer.alert("服务器连接异常，请联系管理员！");
		}
	});
}
// 添加导出字段
function addColumn(){
	$("#exportFiledTr").append("<td>"+fieldStr+"</td>");
}
// 获取所选字段一列的值
function achieveProData(_this){
	var trs=$("#exportDataTable").find("tr");
	var index=$(_this).parent().index();
	for(var i=0;i<trs.length;i++){
		var tdHtml="<td>"+(ProDataList[i][$(_this).val()]==undefined?"":ProDataList[i][$(_this).val()])+"</td>";
		if(index>trs.eq(i).find("td").length-1){
			trs.eq(i).append(tdHtml);
		}else{
			trs.eq(i).find("td").eq(index).html((ProDataList[i][$(_this).val()]==undefined?"":ProDataList[i][$(_this).val()]));
		}
	}
}
// 导出excel
function exportExcelData(){
	// 获取文件名
	zTree = $.fn.zTree.getZTreeObj("bomtree");
	var treeNode = zTree.getSelectedNodes();
	var realId=treeNode[0].realId;
	var excelName=treeNode[0].name+"_Bom";// 选择一个单板时文件名
	if(realId!=undefined){// 此时所选节点为文件节点
		excelName=treeNode[0].name.split('.xls')[0];
	}
	$("#exportExcelTable").tableExport({
		type:'excel',
		fileName:excelName
    });
	$(".outBomWindow").hide();
	$("#outBomWindow").hide();
	$(".modal-backdrop").hide();
// 获取文件名
// zTree = $.fn.zTree.getZTreeObj("bomtree");
// var treeNode = zTree.getSelectedNodes();
// var realId=treeNode[0].realId;
// var excelName=treeNode[0].name+"_Bom.xls";//选择一个单板时文件名
// if(realId!=undefined){//此时所选节点为文件节点
// excelName=treeNode[0].name;
// }
// var exportDataList=new Array();
// var showName=new Array();
// var fieldName=new Array();
// //获取列名
// var tds=$("#exportFiledTr").find("td");
// var pro=new Object();
// pro.Item=tds.eq(0).find("span").html();
// pro.Quantity=tds.eq(1).find("span").html();
// pro.Reference=tds.eq(2).find("span").html();
// pro.PART_NUMBER=tds.eq(3).find("span").html();
// showName.push(pro.Item);
// showName.push(pro.Quantity);
// showName.push(pro.Reference);
// showName.push(pro.PART_NUMBER);
// fieldName.push('Item');
// fieldName.push('Quantity');
// fieldName.push('Reference');
// fieldName.push('PART_NUMBER');
// if(tds.length>4){
// for(var i=4;i<tds.length;i++){
// pro[tds.eq(i).find("select").val()]=tds.eq(i).find("select").find("option:selected").text();
// showName.push(tds.eq(i).find("select").find("option:selected").text());
// fieldName.push(tds.eq(i).find("select").val());
// }
// }
// exportDataList.push(pro);
// //获取导出数据
// var trs=$("#exportDataTable").find("tr");
// for(var i=0;i<trs.length;i++){
// var pro=new Object();
// var cells=trs.eq(i).find("td");
// if(cells.length==4){//前四个固定列
// pro.Item=cells[0];
// pro.Quantity=cells[1];
// pro.Reference=cells[2];
// pro.PART_NUMBER=cells[3];
// }
// for(var j=4;j<cells.length;j++){
// var cell=cells.eq(j);
// pro[tds.eq(j).find("select").val()]=cell.html();
// }
// exportDataList.push(pro);
// }
//	
// $.ajax({
// url : 'productBomController/exportExcelData.do',
// data : {
// 'excelName':excelName,
// 'exportData':JSON.stringify(exportDataList),
// 'showName':JSON.stringify(showName),
// 'fieldName':JSON.stringify(fieldName)
// },
// dataType : 'json',
// cache : false,
// success : function(json) {
//			
// },
// error : function() {
// layer.alert("服务器连接异常，请联系管理员！");
// }
// });
}
// 点击对比按钮，进入对比页
function goBomComparePage(){
	treeObj = $.fn.zTree.getZTreeObj("bomtree");
	var nodes=treeObj.getChangeCheckedNodes();
	var proList=new Array();
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].checked==true){
			if(nodes[i].realId!=undefined){// 文件节点
				var pro=new Object();
				pro.excelName=nodes[i].name;
				pro.productID=nodes[i].id-nodes[i].realId;
				proList.push(pro);
			}
		}
	}
	if(proList.length!=2){
		layer.alert($.i18n.prop("bomMsg"));
		return;
	}
	var b = new Base64();
	var str=b.encode(JSON.stringify(proList));
	window.location.href = getContextPathForWS()+"/pages/productPage/productBomManagementCompare.jsp?proList="+str;
}