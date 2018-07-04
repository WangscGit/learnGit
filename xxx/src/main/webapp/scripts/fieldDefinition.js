$(function() {
			$.ajax({
						url : '/cms_cloudy/partComponent/createPartData.do',
						dataType : 'json',
						cache : false,
						success : function(json) {
							// 判断数据库是否存在Part_Data表
						},
						error : function() {
							layer.alert("服务器连接异常，请联系管理员!");
						}
					});
		});
/** 字段定义异步加载* */
function fieldFunction() {
	$.ajax({
		url : 'partComponent/selectPartPrimaryAttribute.do',
		dataType : 'json',
		cache : false,
		success : function(jsons) {
			var html = '';
			var json = jsons.partList;
			var types = jsons.types;
			var lang = jsons.lang;
			var editBtn="编辑";
			if (null == json || "" == json || json.length == 0) {
				html += '<tr>';
				if("zh" == lang){
					html += '<td colspan="12" align="center">--没有数据--</td>';
				}else{
				    html += '<td colspan="12" align="center">--No data--</td>';
				}
				html += '</tr>';
			} else {
				var noValue = " -- ";
				for (var i = 0; i < json.length; i++) {
					var addBasic = '1';
					var addBefore = i + '';
					var addAfter = parseInt(addBasic) + parseInt(addBefore);
					html += '<tr id="' + addAfter + '">';
					html += '<td><input type="radio" name="radio1" value="'
							+ addAfter + '" onClick="radioChecked(this)"></td>';
					html += '<td class="change-td">' + json[i].fieldName
							+ '</td>';
					html += '<td class="change-td">' + json[i].showName
							+ '</td>';
					html += '<td class="change-td">' + json[i].englishName
							+ '</td>';
					html += '<td class="change-td">' + json[i].description
							+ '</td>';
					var dataType1 = "";
					if (json[i].dataType.indexOf("bit") != -1) {// 布尔类型
						dataType1 = '<span class="data-span">Boolean</span>';
					} else if (json[i].dataType.indexOf("datetime") != -1) {// 时间类型
						dataType1 = '<span class="data-span">Date</span>';
					} else if (json[i].dataType.indexOf("selectList") != -1) {// 下拉列表
						dataType1 = '<span class="data-span">enum</span><a class="left productmix-addchildren" onclick="showSelectListDia('
								+ "'"
								+ json[i].fieldName
								+ "'"
								+ ');"><span class="glyphicon glyphicon-plus" ></span></a>';
					} else if (json[i].dataType.indexOf("int") != -1) {// 字符串
						dataType1 = '<span class="data-span">Integer</span>';
					} else {
						dataType1 = '<span class="data-span">String</span>';
					}
					html += '<td class="data-td">' + dataType1 + '</td>';
					html += '<td>'
					html += '<select class="form-control">';
					if("zh" == lang){
						html += "<option value=''>--请选择--</option>";
					}else{
						editBtn = "Editor";
					    html += "<option value=''>--Select--</option>";
					}
					for (var x = 0; x < types.length; x++) {
						var value = parseInt(x) + parseInt(addBasic);
						if (value == json[i].type) {
							html += "<option value='" + value
									+ "' selected='true'>" + types[x]
									+ "</option>";
						} else {
							html += "<option value='" + value + "'>" + types[x]
									+ "</option>";
						}
					}
					html += '</select>';
					html += '</td>';
					if (json[i].display == true) {
						html += '<td><input type="checkbox" name="isDisplay" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isDisplay"/></td>';
					}
					if (json[i].update == true) {
						html += '<td><input type="checkbox" name="isUpdate" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isUpdate"/></td>';
					}
					if (json[i].audit == true) {
						html += '<td><input type="checkbox" name="isAudit" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isAudit"/></td>';
					}
					if (json[i].search == true) {
						html += '<td><input type="checkbox" name="isSearch" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isSearch"/></td>';
					}
					if (json[i].match == true) {
						html += '<td><input type="checkbox" name="isMatch" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isMatch"/></td>';
					}
					if (json[i].insert == true) {
						html += '<td><input type="checkbox" name="isInsert" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isInsert"/></td>';
					}
					if (json[i].apply == true) {
						html += '<td><input type="checkbox" name="isApply" checked/></td>';
					} else {
						html += '<td><input type="checkbox" name="isApply"/></td>';
					}
					html += '<td class="edit-td">';
					html += '<div class="databtn-group1" >';
					html += '<a class="btn btn-danger btn-xs data-change" href="javascript:updataBefore('
							+ json[i].id
							+ ')"><span class="glyphicon glyphicon-edit"></span>'+editBtn+'</a>';
					// html +='<a class="btn btn-info btn-xs data-delete"><span
					// class="glyphicon glyphicon-edit"></span>删除</a>';
					html += ' </div>';
					html += '<div class="databtn-group2" style="display:none">';
					html += '<a onclick="updateField('
							+ addAfter
							+ ','
							+ json[i].id
							+ ')" class="btn btn-danger btn-xs data-save"><span class="glyphicon glyphicon-edit"></span>保存</a>';
					html += '<a class="btn btn-warning btn-xs data-cancel"><span class="glyphicon glyphicon-edit"></span>取消</a>';
					html += ' </div>';
					html += '</td>';
					html += '</tr>';
				}
			}
			$("#showField").html(html);
			intFieldType(types,lang);
		},
		error : function() {
			layer.alert("数据加载异常，请联系管理员！");
		}
	});
}
function newField() {
	loadProperties();
	var fieldName = $("input[name='fieldName']").val();
	var showName = $("input[name='showName']").val();
	var dataType = $("select[name='dataType']").val();
	var englishName = $("input[name='englishName']").val();
	var re = /^[0-9]+.?[0-9]*$/;
	if (re.test(fieldName)) {
		layer.alert($.i18n.prop("dataCheck-name"));
		return false;
	}
	// 获取下拉列表的值
	var fieldArray = new Array;
	if (dataType == 'selectList') {
		var trs = $("#valueTable").find("tr");
		var valueArray = new Array;
		for (var i = 1; i < trs.length; i++) {
			var fieldSelect = new Object();
			tds = $(trs[i]).find("td");
			var value = tds.eq(0).find("input").val();
			var name = tds.eq(0).find("input").val();
			if (value == "" && name == "") {
				continue;
			}
			fieldSelect.value = value;
			fieldSelect.name = name;
			fieldSelect.fieldName = fieldName;
			fieldArray.push(fieldSelect);
			valueArray.push(value);
		}
		// 验证value是否重复
		var s = valueArray.join(",") + ",";
		for (var i = 0; i < valueArray.length; i++) {
			if (s.replace(valueArray[i] + ",", "").indexOf(valueArray[i] + ",") > -1) {
				layer.alert("数组中有重复元素：" + valueArray[i]);
				return;
			}
		}
	}
	var description = $("input[name='description']").val();
	var typeValue = $("#addSelectType").val();
	var isUesd = "false";
	var isDisplay = "false";
	var isUpdate = "false";
	var isAudit = "false";
	var isSearch = "false";
	var isMatch = "false";
	var isNull = "否";
	var type = "add";
	var reg = /[^\x00-\xff]/ig;// 判断是否存在中文和全角字符
	if ("" == fieldName || null == fieldName) {
		layer.alert($.i18n.prop("dataCheck-fieldName"));
		return;
	}
	if ("" == englishName || null == englishName) {
		layer.alert($.i18n.prop("dataCheck-engName"));
		return;
	}
	if (reg.test(fieldName)) {
		layer.alert($.i18n.prop("dataCheck-fieldName2"));
		return;
	}
	if ("" == showName || null == showName) {
		layer.alert($.i18n.prop("dataCheck-showName"));
		return;
	}
	if ("" == dataType || null == dataType) {
		layer.alert($.i18n.prop("dataCheck-dataType"));
		return;
	}
	if ("" == typeValue || null == typeValue || "请选择" == typeValue) {
		layer.alert($.i18n.prop("dataCheck-type"));
		return;
	}
	// if($("input[name='isUesd']").prop('checked')){
	// isUesd = "true";
	// }
	var isSearchs = $("#isSearch").prop('checked');
	if (isSearchs) {
		isSearch = "true";
	}
	var isDisplays = $("#isDisplay").prop('checked');
	if (isDisplays) {
		isDisplay = "true";
	}
	var isUpdates = $("#isUpdate").prop('checked');
	if (isUpdates) {
		isUpdate = "true";
	}
	var isAudits = $("#isAudit").prop('checked');
	if (isAudits) {
		isAudit = "true";
	}
	var isMatchs = $("#isMatch").prop('checked');
	if (isMatchs) {
		isMatch = "true";
	}
	var isNull = $("#isNull").prop('checked');
	if (isNull) {
		isNull = "是";
	}
	var isInsert = $("#isInsert").prop('checked');
	if (isInsert) {
		isInsert = "true";
	}
	var isApply = $("#isApply").prop('checked');
	if (isApply) {
		isApply = "true";
	}
	$.ajax({
				url : 'partComponent/insertPartPrimaryAttribute.do',
				contentType : 'application/json;charset=UTF-8',
				data : {
					'fieldArray' : encodeURI(JSON.stringify(fieldArray)),
					'fieldName' : fieldName,
					'showName' : encodeURI(showName),
					'englishName' : encodeURI(englishName),
					'description' : encodeURI(description),
					'dataType' : dataType,
					'isDisplay' : isDisplay,
					'isUpdate' : isUpdate,
					'isAudit' : isAudit,
					'isSearch' : isSearch,
					'isMatch' : isMatch,
					'type' : type,
					'typeValue' : typeValue,
					'isNull' : isNull,
					'isInsert' : isInsert,
					'isApply' : isApply
				},
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (null != json && json.result == "add") {
						layer.alert($.i18n.prop("operationSus"));
						$("input[name='fieldName']").val('');
						$("input[name='showName']").val('');
						$("input[name='englishName']").val('');
						$("input[name='description']").val('');
						$("select[name='type']").val('');
						$("#dataTypes option:first").prop("selected",
								'selected');
						$("#dataTypes").attr('class', 'form-control left');
						$("#addImgBtn").hide();
						var trs = $("#valueTable").find("tr");
						for (var i = 1; i < trs.length; i++) {
							tds = $(trs[i]).find("td");
							tds.eq(0).find("input").val('');
							tds.eq(1).find("input").val('');
						}
						$("#isSearch").attr('checked', false);
						$("#isDisplay").attr('checked', false);
						$("#isUpdate").attr('checked', false);
						$("#isAudit").attr('checked', false);
						$("#isMatch").attr('checked', false);
						$("#isInsert").attr('checked', false);
						$("#isApply").attr('checked', false);
						showDaibanField();
					} else if (null != json && json.result == "addRepeat") {
						layer.alert($.i18n.prop("data-alert1"));
					}
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			});
}

function newFieldReset() {
	$(".new-field").hide();
	// $(".databaseCopy").show();
	// $(".dataList").show();
	$(".dataField").show();
}
var moveRow = false;
var moveRadio = false;
var rowIndex = 0;
var current = 0;
var up = 0;
var down = 0;
function radioChecked(obj) {
	moveRadio = obj;
	moveRow = obj.parentNode.parentNode;
	down = moveRow.rowIndex;
	current = parseInt(down) - parseInt(1);
	up = parseInt(current) - parseInt(1);
}
function moveUp() {
	if (current == 1) {
		layer.alert("当前选择行为首行，不能进行上移操作！");
		return;
	}
	$("#showField tr:nth-child(" + up + ")")
			.insertAfter($("#showField tr:nth-child(" + current + ")"));
	current = parseInt(current) - parseInt(1);
	down = parseInt(down) - parseInt(1);
	up = parseInt(current) - parseInt(1);
}
function moveDown() {
	if (document.getElementById("showField").rows.length == current) {
		layer.alert("当前选择行为最后一行，不能进行下移操作！");
		return;
	}
	$("#showField tr:nth-child(" + current + ")")
			.insertAfter($("#showField tr:nth-child(" + down + ")"));
	current = parseInt(current) + parseInt(1);
	down = parseInt(down) + parseInt(1);
	up = parseInt(current) - parseInt(1);
}
function saveChange() {
	var trList = $("#showField").children("tr");
	var fieldList = new Array();

	for (var i = 0; i < trList.length; i++) {
		var tdArr = trList.eq(i).find("td");
		var fieldName = tdArr.eq(1).text();
		var showName = tdArr.eq(2).text();
		var type = tdArr.eq(6).find("select").val();
		var isDisplay = tdArr.eq(7).find("input[type='checkbox']")
				.is(":checked");
		var isUpdate = tdArr.eq(8).find("input[type='checkbox']")
				.is(":checked");
		var isAudit = tdArr.eq(9).find("input[type='checkbox']").is(":checked");
		var isSearch = tdArr.eq(10).find("input[type='checkbox']")
				.is(":checked");
		var isMatch = tdArr.eq(11).find("input[type='checkbox']")
				.is(":checked");
		var isInsert = tdArr.eq(12).find("input[type='checkbox']")
				.is(":checked");
		var isApply = tdArr.eq(13).find("input[type='checkbox']")
				.is(":checked");
		var data = {
			fieldName : fieldName,
			showName : showName,
			type : type,
			display : isDisplay,
			update : isUpdate,
			audit : isAudit,
			search : isSearch,
			match : isMatch,
			insert : isInsert,
			apply : isApply
		};
		fieldList.push(data);
	}
	$.ajax({
				url : '/cms_cloudy/partComponent/updateForm.do',
				type : "POST",
				// contentType:"application/json;charset=utf-8",
				dataType : 'json',
				traditional : true, // 注意这个地方
				data : "fieldList=" + JSON.stringify(fieldList),
				success : function(json) {
					layer.alert("保存成功!");
				},
				error : function() {
					layer.alert("数据加载异常，请联系管理员！");
				}
			});
}
function showDaibanField() {
	var type = "daiban";
	$.ajax({
		url : 'partComponent/selectPartPrimaryAttribute.do',
		data : "type=" + type,
		dataType : 'json',
		cache : false,
		success : function(json) {
			var html = '';
			var lang = json.lang
			if (null == json || "" == json) {
				html += '<tr>';
				html += '<td colspan="4" align="center">--没有数据--</td>';
				html += '</tr>';
			} else {
				var partList = json.partList;
				var noValue = " -- ";
				var delBtn="删除";
				if("zh" != lang){
				    delBtn = "Delete";
				}
				for (var i = 0; i < partList.length; i++) {
					var dataType1 = "";
					if (partList[i].dataType.indexOf("bit") != -1) {// 布尔类型
						dataType1 = "Boolean";
					} else if (partList[i].dataType.indexOf("datetime") != -1) {// 时间类型
						dataType1 = "Date";
					} else if (partList[i].dataType.indexOf("selectList") != -1) {// 下拉列表
						dataType1 = '<span class="data-span">enum</span> <a class="btn btn-xs btn-danger  productmix-addchildren" onclick="showSelectListDia('
								+ "'"
								+ partList[i].fieldName
								+ "'"
								+ ');"><span class="glyphicon glyphicon-plus" ></span></a>';
					} else if (partList[i].dataType.indexOf("int") != -1) {// 字符串
						dataType1 = "Integer";
					} else {
						dataType1 = "String";
					}
					var addBasic = '1';
					var addBefore = i + '';
					var addAfter = parseInt(addBasic) + parseInt(addBefore);
					html += '<tr>';
					html += '<td class="change-td">' + partList[i].fieldName
							+ '</td>';
					html += '<td class="change-td">' + partList[i].showName
							+ '</td>';
					html += '<td class="change-td">' + dataType1 + '</td>';
					html += '<td class="change-td">' + partList[i].description
							+ '</td>';
					html += '<td class="edit-td">';
					html += '<a href="javascript:deleteField(' + partList[i].id
							+ ",'" + partList[i].fieldName + "'"
							+ ')" class="btn btn-info btn-xs">'+delBtn+'</a>';
					html += '</td>';
					html += '</tr>';
				}
			}
			$("#daibanField").html(html);
			$("#addImgBtn").hide();
		},
		error : function() {
			layer.alert("数据加载异常，请联系管理员！");
		}
	});
}
function deleteField(id, fieldName) {
	loadProperties();
	$.ajax({
				url : '/cms_cloudy/partComponent/deletePartPrimaryAttributes.do',
				data : {
					'id' : id,
					'fieldName' : fieldName
				},
				dataType : 'json',
				cache : false,
				success : function(json) {
					layer.alert($.i18n.prop("alertMsg3"));
					showDaibanField();
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			})
}
/** 将添加的字段全部提交到数据库* */
function commitData() {
	loadProperties();
	$.ajax({
				url : '/cms_cloudy/partComponent/commitData.do',
				dataType : 'json',
				cache : false,
				success : function(json) {
					layer.alert($.i18n.prop("operationSus"));
					showDaibanField();
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			})
}
function updateField(obj, id) {
	var aaa = $("#" + obj)
	var $td = aaa.children('td');
	var fieldName = $td.eq(1).find("input").val();
	var showName = $td.eq(2).find("input").val();
	var description = $td.eq(3).find("input").val();
	var type = $td.eq(4).find("select").val();
	var isDisplay = $td.eq(5).find("input[type='checkbox']").is(":checked");
	var isUpdate = $td.eq(6).find("input[type='checkbox']").is(":checked");
	var isAudit = $td.eq(7).find("input[type='checkbox']").is(":checked");
	var isSearch = $td.eq(8).find("input[type='checkbox']").is(":checked");
	var isMatch = $td.eq(9).find("input[type='checkbox']").is(":checked");
	var data = {
		id : id,
		fieldName : fieldName,
		showName : showName,
		description : description,
		type : type,
		display : isDisplay,
		update : isUpdate,
		audit : isAudit,
		search : isSearch,
		match : isMatch
	};
	$.ajax({
				url : '/cms_cloudy/partComponent/updateField.do',
				type : "POST",
				dataType : 'json',
				traditional : true,
/** 注意这个地方 */
				// 注意你妹
				data : "data=" + JSON.stringify(data),
				success : function(json) {
					layer.alert('编辑成功!');
					fieldFunction();
				},
				error : function() {
					layer.alert("数据加载异常，请联系管理员！");
				}
			})
}
// 选择enum类型时显示加号图标
function showAddImg() {
	if ($("#dataTypes").val() == 'selectList') {
		$("#dataTypes").attr('class', 'form-control wordType left');
		$("#addImgBtn").show();
	} else {
		$("#dataTypes").attr('class', 'form-control left');
		$("#addImgBtn").hide();
	}
}
// 字符类型为下拉列表时，维护下拉框的值
function showSelectDia() {
	if ($("#dataTypes").val() != 'selectList') {
		layer.alert("选择下拉列表时可维护列表值");
		return;
	}
	if (selectLock == 1) {
		selectLock = 0;
		$("#valueTable")
				.html("<tr><td><span class=\"Value\">value值</span></td><td></td></tr><tr><td><input type=\"text\" class=\"form-control\" /></td><td><a class=\"btn-danger btn valueDel\" onclick=\"removeTr(this);\"><span class=\"glyphicon glyphicon-remove\" ></span></a></td></tr>");
	}
	$(".select-addWindow").show();
}
// 隐藏 添加下拉列表弹窗
function hideSelectDia() {
	$(".select-addWindow").hide();
}
// 添加行
function addValueTr() {
	var html = "<tr><td><input type=\"text\" class=\"form-control\" /></td><td><a class=\"btn-danger btn valueDel\" onclick=\"removeTr(this);\"><span class=\"glyphicon glyphicon-remove\" ></span></a></td></tr>";
	$("#valueTable").append(html);
}
// 删除行
function removeTr(_this) {
	$(_this).parent().parent().remove();
}

var selectLock = 0;// 控制是否重置弹窗，以及确定按钮的作用。修改时为1，添加时为0；
var alfieldName = "";
// 修改下拉列表值弹窗
function showSelectListDia(fieldName) {
	selectLock = 1;
	alfieldName = fieldName;
	$.ajax({
		url : 'partComponent/getSelectListByFieldName.do',
		type : "POST",
		dataType : 'json',
		data : "fieldName=" + fieldName,
		success : function(json) {
			var fsList = json.fsList;
			var html = "<tr><td><span class=\"Value\">value</span></td><td></td></tr>";
			for (var i = 0; i < fsList.length; i++) {
				html += "<tr><td><input type=\"text\" class=\"form-control\" value=\""
						+ fsList[i].value
						+ "\" /></td><td><a class=\"btn-danger btn valueDel\" onclick=\"removeTr(this);\"><span class=\"glyphicon glyphicon-remove\" ></span></a></td></tr>";
			}
			if (fsList.length > 0) {
				$("#valueTable").html(html);
			}
			$(".select-addWindow").show();
		},
		error : function() {
			layer.alert("数据加载异常，请联系管理员！");
		}
	});
}

// 保存下拉列表值
function saveSelectList() {
	var trs = $("#valueTable").find("tr");
	var valueArray = new Array;
	var fieldArray = new Array;
	for (var i = 1; i < trs.length; i++) {
		var fieldSelect = new Object();
		tds = $(trs[i]).find("td");
		var value = tds.eq(0).find("input").val();
		var name = tds.eq(0).find("input").val();
		if (value == "") {
			continue;
		}
		valueArray.push(value);
		fieldSelect.value = value;
		fieldSelect.name = name;
		fieldSelect.fieldName = alfieldName;
		fieldArray.push(fieldSelect);
	}
	// 验证value是否重复
	var s = valueArray.join(",") + ",";
	for (var i = 0; i < valueArray.length; i++) {
		if (s.replace(valueArray[i] + ",", "").indexOf(valueArray[i] + ",") > -1) {
			layer.alert("数组中有重复元素：" + valueArray[i]);
			return;
		}
	}
	if (selectLock == 0) {
		$(".select-addWindow").hide();
		return;
	}
	if (selectLock == 1) {
		$.ajax({
					url : 'partComponent/saveSelectList.do',
					type : "post",
					dataType : 'json',
					data : {
						"fieldArray" : JSON.stringify(fieldArray),
						"fieldName" : alfieldName
					},
					success : function(json) {
						layer.alert("修改成功");
						$(".select-addWindow").hide();
					},
					error : function() {
						layer.alert("数据加载异常，请联系管理员！");
					}
				});
	}
}
/**
 * 字段定义编辑before
 */
function updataBefore(id) {
	$.ajax({
				url : '/cms_cloudy/partComponent/primaryAttributeUpdataBefore.do',
				data : {
					'id' : id
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (null != json && "" != json) {
						$("input[name='fieldNameUpdate']").val(json.fieldName);
						$("input[name='showNameUpdate']").val(json.showName);
						$("input[name='englishNameUpdate']").val(json.englishName);
						if (json.dataType.indexOf("nvarchar") >= 0) {
							$("#dataTypesUpdate").val("nvarchar(500)");
						} else {
							$("#dataTypesUpdate").val(json.dataType);
						}
						if ("0" == json.type) {
							$("#addSelectTypeUpdate").val("请选择");
						} else {
							$("#addSelectTypeUpdate").val(json.type);
						}
						$("input[name='descriptionUpdate']")
								.val(json.description);
						$("#isSearchUpdate").attr('checked', json.search);
						$("#isDisplayUpdate").attr('checked', json.display);
						$("#isUpdateUpdate").attr('checked', json.update);
						$("#isAuditUpdate").attr('checked', json.audit);
						$("#isMatchUpdate").attr('checked', json.match);
						$("#isApplyUpdate").attr('checked', json.apply);
						$("#isInsertUpdate").attr('checked', json.insert);
						if (json.isNull == "是") {
							$("#isNullUpdate").attr('checked', true);
						} else {
							$("#isNullUpdate").attr('checked', false);
						}
					} else {
						$(".field-editWindow").hide();
						layer.alert("找不到要修改的数据，请刷新后重试！");
					}
				},
				error : function() {
					layer.alert("服务器异常，请联系管理员！");
				}
			})
}

// 字段类别初始化
function intFieldType(select,lang) {
	var selectHtml = '';
	var chooseBtn="请选择";
	if("zh" != lang){
	   chooseBtn="Please choose";
	}
	if (null == select || 0 == select.length) {
		selectHtml += '<option value= "请选择">' + chooseBtn + '</option>';
	} else {
		selectHtml += '<option value= "请选择">' + chooseBtn + '</option>';
		for (var s = 0; s < select.length; s++) {
			var value = parseInt(1) + parseInt(s)
			selectHtml += '<option value= ' + value + '>' + select[s]
					+ '</option>';
		}
	}
	$("#addSelectType").html(selectHtml);
	$("#addSelectTypeUpdate").html(selectHtml);
}
// 保存字段定义修改信息
function updateFieldAttr() {
	loadProperties();
	var fieldList = new Array();
	var fieldName = $("input[name='fieldNameUpdate']").val();
	var showName = $("input[name='showNameUpdate']").val();
	var englishName = $("input[name='englishNameUpdate']").val();
	if ("" == showName || null == showName) {
		layer.alert($.i18n.prop("dataCheck-showName"));
		return;
	}
	var description = $("input[name='descriptionUpdate']").val();
	var type = $("#addSelectTypeUpdate").val();
	if (type == "请选择") {
		type = 0;
	}
	var search = $("#isSearchUpdate").prop('checked');
	var display = $("#isDisplayUpdate").prop('checked');
	var update = $("#isUpdateUpdate").prop('checked');
	var audit = $("#isAuditUpdate").prop('checked');
	var match = $("#isMatchUpdate").prop('checked');
	var insert = $("#isInsertUpdate").prop('checked');
	var apply = $("#isApplyUpdate").prop('checked');
	var data = {
		fieldName : fieldName,
		showName : showName,
		englishName : englishName,
		description : description,
		type : type,
		display : display,
		update : update,
		audit : audit,
		search : search,
		match : match,
		insert : insert,
		apply : apply
	};
	fieldList.push(data);
	$.ajax({
				url : '/cms_cloudy/partComponent/updateFieldAttr.do',
				type : "POST",
				dataType : 'json',
				traditional : true,
				data : "fieldList=" + JSON.stringify(fieldList),
				success : function(json) {
					layer.alert($.i18n.prop("alertMsg1"));
					$(".field-editWindow").hide();
					fieldFunction();
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			});
}
//关闭字段定义修改弹出框
function updateFieldclose() {
	$(".field-editWindow").hide();
}