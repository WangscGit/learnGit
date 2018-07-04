function loadTree2() {
	var PartNumber = getUrlParam("goMinute");
	$.ajax({
		url : '/cms_cloudy/productBomController/proAscend.do',
		data : 'partNumber=' + PartNumber,
		dataType : 'json',
		success : function(json) {
			var list = json.list;
			if ("" != list) {
				var myChart = echarts.init(document
						.getElementById('project-box'));
				var option = {
					title : {
						text : '',
						textStyle : {
							left : 'center',
							color : '#d9534f'
						}
					},
					toolbox : {
						show : true,
						feature : {
						// mark : {show: true},
						// dataView : {show: true, readOnly: false},
						// restore : {show: true},
						// saveAsImage : {show: true}
						}
					},
					series : [ {
						name : '树图',
						type : 'tree',
						orient : 'horizontal', // vertical horizontal
						rootLocation : {
							x : 280,
							y : 130
						}, // 根节点位置 {x: 100, y: 'center'}
						nodePadding : 20,
						layerPadding : 180,// 调整间距
						hoverable : false,
						roam : true,
						symbolSize : 18,
						itemStyle : {
							normal : {
								color : '#d9534f ',
								label : {
									show : true,
									position : 'right',
									formatter : "{b}",
									textStyle : {
										color : '#000',
										fontSize : 10
									}
								},
								lineStyle : {
									color : '#ccc',
									type : 'dashed' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

								}
							},
							emphasis : {
								color : '#d9534f',// 光标移动到图片颜色
								label : {
									show : true
								},
								borderWidth : 1
							}
						},

						data : [ {
							"name" : json.partNumber,
							"children" : json.listObj
						} ]
					} ]
				};
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			} else {
				var myChart = echarts.init(document
						.getElementById('project-box'));
				var option = {
					title : {
						text : '',
						textStyle : {
							left : 'center',
							color : '#d9534f'
						}
					},
					toolbox : {
						show : true,
						feature : {
						// mark : {show: true},
						// dataView : {show: true, readOnly: false},
						// restore : {show: true},
						// saveAsImage : {show: true}
						}
					},
					series : [ {
						name : '树图',
						type : 'tree',
						orient : 'horizontal', // vertical horizontal
						rootLocation : {
							x : 280,
							y : 130
						}, // 根节点位置 {x: 100, y: 'center'}
						nodePadding : 20,
						layerPadding : 180,// 调整间距
						hoverable : false,
						roam : true,
						symbolSize : 18,
						itemStyle : {
							normal : {
								color : '#d9534f ',
								label : {
									show : true,
									position : 'right',
									formatter : "{b}",
									textStyle : {
										color : '#000',
										fontSize : 10
									}
								},
								lineStyle : {
									color : '#ccc',
									type : 'dashed' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

								}
							},
							emphasis : {
								color : '#d9534f',// 光标移动到图片颜色
								label : {
									show : true
								},
								borderWidth : 1
							}
						},
						data : [ {
							"name" : PartNumber + "[qty=0]",
							"children" : []
						} ]
					} ]
				};
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
// 可替代料显示
function replaceShow() {
	loadProperties();// 国际化
	var PartNumber = getUrlParam("goMinute");
	$
			.ajax({
				url : 'partComponentArrt/selectPartDataToReplace.do',
				data : 'partNumber=' + PartNumber,
				dataType : 'json',
				cache : false,
				success : function(json) {
					var showNames = json.showName;
					var queryFields = json.queryFields;
					var dataList = json.dataList;
					var mapLinked = json.mapLinked;
					var mainHtml = '';
					// var detailHtml = '';
					if (null == mapLinked || "" == mapLinked
							|| mapLinked.length == 0) {
						mainHtml += '<tr>';
						mainHtml += '<th>';
						mainHtml += '<span>'
								+ '<input type="checkbox" class="left" onclick="highLightDifferent();"/>'
								+ '<span class="left">'
								+ $.i18n.prop("checkDiverse") + '</span>'
								+ '</span>';
						mainHtml += '<span>'
								+ '<input type="checkbox" class="left" onclick="hideSame();"/>'
								+ '<span class="left">'
								+ $.i18n.prop("checkSimple") + '</span>'
								+ '</span>';
						mainHtml += '</th>';
						for (var i = 0; i < 4; i++) {
							mainHtml += '<td>';
							mainHtml += ' <div class="add-simple">';
							mainHtml += '<a>' + '--' + '</a>';
							mainHtml += '</div>';
							mainHtml += '</td>';
						}
						mainHtml += ' </tr>';
						mainHtml += '<tr>';
						mainHtml += '<td colspan="' + 5 + '" align="center">'
								+ $.i18n.prop("noData") + '</td>';
						mainHtml += '</tr>';
					} else {
						var noData = parseInt(4) - parseInt(mapLinked.length);
						mainHtml += '<tr>';
						mainHtml += '<th>';
						mainHtml += '<span>'
								+ '<input type="checkbox" id="highLightDifferent" class="left" onclick="highLightDifferent();"/>'
								+ '<span class="left">'
								+ $.i18n.prop("checkDiverse") + '</span>'
								+ '</span>';
						mainHtml += '<span>'
								+ '<input type="checkbox" id="hideSame" class="left" onclick="hideSame();"/>'
								+ '<span class="left">'
								+ $.i18n.prop("checkSimple") + '</span>'
								+ '</span>';
						mainHtml += '</th>';
						for (var i = 0; i < mapLinked.length; i++) {
							mainHtml += '<td class="compare-goods-td">';
							mainHtml += '<div class="compare-goods">';
							mainHtml += '<dl>';
							mainHtml += '<dt>'
									+ '<img src="/cms_cloudy/images/compare1.png" alt=""/>'
									+ '</dt>';
							mainHtml += '<dd>' + mapLinked[i].Part_Type
									+ '</dd>';
							mainHtml += '<dd class="view-now-box">'
									+ '<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='
									+ mapLinked[i].PartNumber + '&partId='
									+ mapLinked[i].id + '&tempPartMark='
									+ mapLinked[i].TempPartMark
									+ '" class="btn btn-danger view-now">'
									+ $.i18n.prop("nLookBtn") + '</a>'
									+ '</dd>';
							mainHtml += '</dl>';
							mainHtml += ' <a class="compare-goods-delete right" href="javascript:deleteOneReplace(\''
									+ mapLinked[i].PartNumber
									+ '\')">'
									+ $.i18n.prop("deleteBtn") + '</a> ';
							mainHtml += '</div> ';
							mainHtml += '</td>';
						}
						if (noData > 0) {
							for (var n = 0; n < noData; n++) {
								mainHtml += '<td>';
								mainHtml += ' <div class="add-simple">';
								mainHtml += '<a>' + '--' + '</a>';
								mainHtml += '</div>';
								mainHtml += '</td>';
							}
						}
						mainHtml += ' </tr>';
						var aaaaa = "--";
						for (var j = 0; j < showNames.length; j++) {
							if(queryFields[j] == 'PartNumber'){
			            		mainHtml += '<tr style="display:none">';
			            	}else{
			            		mainHtml += '<tr>';
			            	}
							mainHtml += '<th>' + showNames[j] + '</th>';
							for (var xx = 0; xx < mapLinked.length; xx++) {
								var dataListArray = dataList[xx];
								if ("" == dataListArray[j]
										|| null == dataListArray[j]
										|| " null" == dataListArray[j]
										|| "null" == dataListArray[j]) {
									mainHtml += '<td>' + '' + '</td>';
								} else {
									if (queryFields[j] == "schematic_img"
											|| queryFields[j] == "ens_img"
											|| queryFields[j] == "shape_img"
											|| queryFields[j] == "size_img"
											|| queryFields[j] == "characteristic_curve_img"
											|| queryFields[j] == "typical_ap_img") {
										mainHtml += '<td class="partsImages">'
										mainHtml += '<div class="left parts-images" data-toggle="tooltip"';
										mainHtml += 'data-placement="right" title="图片上传最佳尺寸【550px*320px】">';
										mainHtml += '<img src='
												+ dataListArray[j] + '>';
										mainHtml += '</div>';
										mainHtml += '</td>'
										// mainHtml += '<td>' + 'xxx' + '</td>';
									} else {
										mainHtml += '<td>' + dataListArray[j]
												+ '</td>';
									}
								}
							}
							if (noData > 0) {
								for (var ss = 0; ss < noData; ss++) {
									mainHtml += '<td>' + aaaaa + '</td>';
								}
							}
							mainHtml += '</tr>';
						}
					}
					$("#firstTr").html(mainHtml);
					// $("#otherTr").html(detailHtml);
					/** ****鼠标移入对比页面商品时显示删除**** */
					$(".compare-goods-td").hover(function() {
						$(this).find(".compare-goods-delete").show();
					}, function() {
						$(this).find(".compare-goods-delete").hide();
					});
					/** 图片显示* */
					$('.parts-images img').zoomify();
					/** 删除ID行* */
					var tableList = document.getElementById("firstTr");
					var removeIdTr = tableList.getElementsByTagName("tr");
					if (removeIdTr.length > 1) {
						$(removeIdTr[1].remove());
					}
				},
				error : function() {
					layer.alert("数据连接异常，请联系管理员！");
				}
			})
}
function zoomImgShow() {
	png = getUrlParam('png');
	var main = '';
	var detail = '';
	if (png.indexOf("电路") > 0) {
		main += '<span class="move">' + '</span>';
		main += '<img width="200" height="200"  src="/cms_cloudy/images/jichengsmall.png" />';
		detail += '<img width="300" height="300" src="/cms_cloudy/images/jicheng.png" />';
		$("#zoomMain").html(main);
		$("#zoomDetail").html(detail);
	} else if (png.indexOf("电阻") > 0) {
		main += '<span class="move">' + '</span>';
		main += '<img width="200" height="200"  src="/cms_cloudy/images/dianzusmall.png" />';
		detail += '<img width="300" height="300" src="/cms_cloudy/images/dianzu.png" />';
		$("#zoomMain").html(main);
		$("#zoomDetail").html(detail);
	} else if (png.indexOf("电容") > 0) {
		main += '<span class="move">' + '</span>';
		main += '<img width="200" height="200"  src="/cms_cloudy/images/dianrongsmall.png" />';
		detail += '<img width="300" height="300" src="/cms_cloudy/images/dianrong.png" />';
		$("#zoomMain").html(main);
		$("#zoomDetail").html(detail);
	} else {
		main += '<span class="move">' + '</span>';
		main += '<img width="200" height="200"  src="/cms_cloudy/images/jichengsmall.png" />';
		detail += '<img width="300" height="300" src="/cms_cloudy/images/jicheng.png" />';
		$("#zoomMain").html(main);
		$("#zoomDetail").html(detail);
	}
}
function getUrlParam(key) {
	// 获取参数
	var url = window.location.search;
	// 正则筛选地址栏
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	// 匹配目标参数
	var result = url.substr(1).match(reg);
	// 返回参数值
	return result ? decodeURIComponent(result[2]) : null;
}
// 添加显示字段
function addField(type) {
	var j = 0;
	if (type == 1) {
		// nodeList详情页数据集
		for (var i = 0, l = nodeList.length; i < l; i++) {
			if (nodeList[i].type == type) {
				j++;
			}
		}
		// 生成下拉框
		var html = '';
		var name = 'select' + type;
		var uuid = "cms" + guid();
		html += '<tr>';
		html += '<td>';
		html += '<select id="'
				+ uuid
				+ '" name="'
				+ name
				+ '" class="form-control select-control" onchange="refresSelect(this);"></select>';
		html += '</td>';
		html += '<td name="' + type + '">'
				+ '<a class="newaddDelete" onclick="removeTr(this)">X</a>';
		html += '</td>';
		html += '</tr>';
		var selectNum = $("select[name = '" + name + "']");
		j = j + selectNum.length;
		var table = document.getElementById("treeTable1");
		var trs = table.getElementsByTagName("tr");
		$(trs[j - 1]).after(html);
		initSelect(uuid);
	} else if (type == 2) {
		for (var i = 0, l = nodeList.length; i < l; i++) {
			if (nodeList[i].type == 1 || nodeList[i].type == 2) {
				j++;
			}
		}
		var html = '';
		var name = 'select' + type;
		var uuid = "cms" + guid();
		html += '<tr>';
		html += '<td>';
		html += '<select id="'
				+ uuid
				+ '" name="'
				+ name
				+ '" class="form-control select-control" onchange="refresSelect(this);"></select>';
		html += '</td>';
		html += '<td name="' + type + '">'
				+ '<a class="newaddDelete" onclick="removeTr(this)">X</a>';
		html += '</td>';
		html += '</tr>';
		var selectNum = $("select[name = '" + name + "']");
		var selectNum1 = $("select[name = 'select1']");
		j = j + selectNum.length + selectNum1.length;
		var table = document.getElementById("treeTable1");
		var trs = table.getElementsByTagName("tr");
		$(trs[j - 1]).after(html);
		initSelect(uuid);
	} else if (type == 3) {
		for (var i = 0, l = nodeList.length; i < l; i++) {
			if (nodeList[i].type == 1 || nodeList[i].type == 2
					|| nodeList[i].type == 3) {
				j++;
			}
		}
		var html = '';
		var name = 'select' + type;
		var uuid = "cms" + guid();
		html += '<tr>';
		html += '<td>';
		html += '<select id="'
				+ uuid
				+ '" name="'
				+ name
				+ '" class="form-control select-control" onchange="refresSelect(this);"></select>';
		html += '</td>';
		html += '<td name="' + type + '">'
				+ '<a class="newaddDelete" onclick="removeTr(this)">X</a>';
		html += '</td>';
		html += '</tr>';
		var selectNum = $("select[name = '" + name + "']");
		var selectNum1 = $("select[name = 'select1']");
		var selectNum2 = $("select[name = 'select2']");
		j = j + selectNum.length + selectNum1.length + selectNum2.length;
		var table = document.getElementById("treeTable1");
		var trs = table.getElementsByTagName("tr");
		$(trs[j - 1]).after(html);
		initSelect(uuid);
	} else if (type == 4) {
		j = nodeList.length;
		var html = '';
		var name = 'select' + type;
		var uuid = "cms" + guid();
		html += '<tr>';
		html += '<td>';
		html += '<select id="'
				+ uuid
				+ '" name="'
				+ name
				+ '" class="form-control select-control" onchange="refresSelect(this);"></select>';
		html += '</td>';
		html += '<td name="' + type + '">'
				+ '<a class="newaddDelete" onclick="removeTr(this)">X</a>';
		html += '</td>';
		html += '</tr>';
		var selectNum = $("select[name = '" + name + "']");
		var selectNum1 = $("select[name = 'select1']");
		var selectNum2 = $("select[name = 'select2']");
		var selectNum3 = $("select[name = 'select3']");
		j = j + selectNum.length + selectNum1.length + selectNum2.length
				+ selectNum3.length;
		var table = document.getElementById("treeTable1");
		var trs = table.getElementsByTagName("tr");
		$(trs[j - 1]).after(html);
		initSelect(uuid);
	}
}
var attrList;
var language;
// 初始化字段下拉框
function initSelect(uuid) {
	loadProperties();// 国际化
	if (typeof (attrList) == "undefined") {
		$
				.ajax({
					url : '/cms_cloudy/partComponentArrt/getFiledAndDataType.do',
					dataType : 'json',
					cache : false,
					success : function(json) {
						attrList = json.list;
						language = json.lang;
						var html = '';
						html += '<option value= "请选择">'
								+ $.i18n.prop("selectBtn") + '</option>';
						if (attrList.length > 0) {
							var fl = "";
							$
									.each(
											attrList,
											function(index, content) {// 生成下拉框
												var fsString = JSON
														.stringify(content.fsList);
												fsString = fsString.replace(
														/\"/g, "~");
												fl = 'fsList="' + fsString
														+ '"';
												if ("zh" == language) {
													html += '<option value= "'
															+ content.fieldName
															+ '" datatype="'
															+ content.dataType
															+ '"' + fl + '>'
															+ content.showName
															+ '</option>';
												} else {
													html += '<option value= "'
															+ content.fieldName
															+ '" datatype="'
															+ content.dataType
															+ '"'
															+ fl
															+ '>'
															+ content.englishName
															+ '</option>';
												}
											});
						}
						$("#" + uuid).html(html);
					},
					error : function() {
						layer.alert($.i18n.prop("alertError"));
					}
				})
	} else {
		var html = '';
		html += '<option value= "请选择">' + $.i18n.prop("selectBtn")
				+ '</option>';
		if (attrList.length > 0) {
			var fl = "";
			$.each(attrList, function(index, content) {// 生成下拉框
				var fsString = JSON.stringify(content.fsList);
				fsString = fsString.replace(/\"/g, "~");
				fl = 'fslist="' + fsString + '"';
				if ("zh" == language) {
					html += '<option value= "' + content.fieldName
							+ '" datatype="' + content.dataType + '"' + fl
							+ '>' + content.showName + '</option>';
				} else {
					html += '<option value= "' + content.fieldName
							+ '" datatype="' + content.dataType + '"' + fl
							+ '>' + content.englishName + '</option>';
				}
			});
		}
		$("#" + uuid).html(html);
	}
}
// 刷新下拉框
function refresSelect(value) {
	var selectList = $("select");
	var s = 0;
	for (var i = 0; i < selectList.length; i++) {
		if ($(value).val() == $(selectList[i]).val()) {
			s++;
		}
	}
	if ('请选择' != $(value).val()) {
		if (s == 1) {
			var tdNex = $(value).parent().next();
			getSelectValue(tdNex, $(value).val(), $(value).find(
					"option:selected").attr("datatype"), $(value).find(
					"option:selected").attr("fslist"));
			// var htm = "<input type='text' name='" + $(value).val()
			// + "' class='form-control'/>";
			// tdNex.html(htm);
		} else {
			layer.alert("该字段已存在！");
			$(value).val('请选择');
			return;
		}
	}

}
/** 选择下拉字段对应值显示* */
function getSelectValue(tdNex, fieldName, dt, fsString) {
	var partNumber = getUrlParam("goMinute");
	var fsList = JSON.parse(fsString.replace(/~/g, "\""));
	$
			.ajax({
				url : "/cms_cloudy/partComponentArrt/selectPartData.do",
				data : {
					'PartNumber' : partNumber,
					"option" : fieldName
				},
				cache : false,
				type : 'post',
				dataType : "json",
				success : function(json) {
					var value = "";
					if (json != null) {
						value = json.value;
					}
					// 判断是输入框还是下拉列表
					var inorse = "<input type='text' name='"
							+ fieldName
							+ "' class='form-control newaddInput' value='"
							+ value
							+ "'/><a class='newaddDelete' onclick='removeTr(this)'>X</a>";
					var option = "";
					if (dt == "selectList") {
						for (var j = 0; j < fsList.length; j++) {
							if (fsList[j].value == value) {
								option += "<option value=\"" + fsList[j].value
										+ " \" selected=\"true\">"
										+ fsList[j].name + "</option>";
							} else {
								option += "<option value=\"" + fsList[j].value
										+ "\">" + fsList[j].name + "</option>";
							}
						}
						inorse = "<select name='"
								+ fieldName
								+ "' class='form-control newaddInput'>"
								+ option
								+ "</select><a class='newaddDelete' onclick='removeTr(this)'>X</a>";
					}

					if (null != json) {
						if (fieldName == "PartNumber"
								|| fieldName == "Part_Type") {
							var htm = "<input type='text' name='"
									+ fieldName
									+ "' value='"
									+ json.value
									+ "'class='form-control newaddInput' readonly/><a class='newaddDelete' onclick='removeTr(this)'>X</a>";
						} else {
							var htm = inorse;
						}
						tdNex.html(htm);
					} else {
						if (fieldName == "PartNumber"
								|| fieldName == "Part_Type") {
							var htm = "<input type='text' name='"
									+ fieldName
									+ "' class='form-control newaddInput' readonly/><a class='newaddDelete' onclick='removeTr(this)'>X</a>";
						} else {
							var htm = inorse;
						}
						tdNex.html(htm);
					}
				},
				error : function() {
					layer.alert("数据连接异常,注册失败！");
				}
			});
}
// 保存详情页修改数据
function saveMinuData(partState) {
	loadProperties();// 国际化
	var table = document.getElementById("treeTable1");
	var trs = table.getElementsByTagName("tr");
	var fieldList = new Array();
	for (var i = 0; i < trs.length; i++) {
		var tdArr = $(trs[i]).find("td");
		var td1 = tdArr[0].children;
		var td2 = tdArr[1].children;
		var type = $(td1).attr("name");
		var fieldName = '';
		var value = $(td2).val();
		if (typeof (type) == "undefined") {
			type = '';
			fieldName = $(td2).attr("name");
			if (fieldName == "state") {
				value = partState;
			}
		} else {
			type = type.charAt(type.length - 1);
			fieldName = $(td1).val();
			if (fieldName == "state") {
				value = partState;
			}
			if ("请选择" == fieldName) {
				continue;
			}
		}
		var data = {
			'fieldName' : fieldName,
			'value' : value,
			'type' : type
		};
		fieldList.push(data);
	}
	$.ajax({
		url : '/cms_cloudy/partComponentArrt/saveMinuData.do',
		data : {
			'data' : JSON.stringify(fieldList),
			'partNumber' : getUrlParam("goMinute")
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			if (json.result == "fail") {
				layer.alert("该器件已被检入,不能修改！");
			} else {
				layer.alert($.i18n.prop("operationSus"));
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
// 保存修改数据并存入历史表
function saveHistory(partState) {
	loadProperties();// 国际化
	var table = document.getElementById("treeTable1");
	var trs = table.getElementsByTagName("tr");
	var fieldList = new Array();
	for (var i = 0; i < trs.length; i++) {
		var tdArr = $(trs[i]).find("td");
		var td1 = tdArr[0].children;
		var td2 = tdArr[1].children;
		var type = $(td1).attr("name");
		var fieldName = '';
		var value = $(td2).val();
		if (typeof (type) == "undefined") {
			type = '';
			fieldName = $(td2).attr("name");
			if (fieldName == "state") {
				value = partState;
			}
		} else {
			type = type.charAt(type.length - 1);
			fieldName = $(td1).val();
			if (fieldName == "state") {
				value = partState;
			}
			if ("请选择" == fieldName) {
				continue;
			}
		}
		var data = {
			'fieldName' : fieldName,
			'value' : value,
			'type' : type
		};
		fieldList.push(data);
	}
	$.ajax({
		url : '/cms_cloudy/partComponentArrt/saveHistory.do',
		data : {
			'data' : JSON.stringify(fieldList),
			'partNumber' : getUrlParam("goMinute")
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			if (json.result == "fail") {
				layer.alert("该器件已被检入,不能修改！");
			} else {
				layer.alert($.i18n.prop("operationSus"));
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
function checkOut() {
	loadProperties();// 国际化
	$.ajax({
		url : '/cms_cloudy/partComponentArrt/searchState.do',
		data : {
			'partNumber' : getUrlParam("goMinute")
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			if (json.result == 'fail') {
				layer.alert("该器件正处于检出状态！");
			} else if (json.result == 'null') {
				layer.alert("找不到该器件的相关信息！");
			} else if (json.result == 'success') {
				layer.alert($.i18n.prop("operationSus"));
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
// 检出操作
function searchState() {
	var state = '';
	$.ajax({
		url : '/cms_cloudy/partComponentArrt/searchState.do',
		data : {
			'partNumber' : getUrlParam("goMinute")
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			var se = json.state;
			state = se;
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
	return state;
}
// 更新状态
function updateState() {
	loadProperties();// 国际化
	$.ajax({
		url : '/cms_cloudy/partComponentArrt/updateState.do',
		data : {
			'partNumber' : getUrlParam("goMinute")
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			layer.alert($.i18n.prop("operationSus"));
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
// 生成uuid
function S4() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
function guid() {
	return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4()
			+ S4() + S4());
}
// 点击选用，弹窗
function addToProductDialog1() {
	var setting = {
		view : {
			showIcon : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : selectProductNode
		}
	};
	$.ajax({
		url : "productBomController/selectSelectProductBomList.do",
		dataType : "json",
		cache : false,
		success : function(json) {
			$(document).ready(function() {
				$.fn.zTree.init($("#bomtree"), setting, json);
				var treeObj = $.fn.zTree.getZTreeObj("bomtree");
				treeObj.expandAll(true); // 默认展开全部
				$(".parts-product-window").show();
			});
		},
		error : function() {
			// alert("数据连接异常,注册失败！");
		}

	});
}

function addToProduct1() {
	var partNumber = getUrlParam("goMinute");
	var partNumbers = new Array;
	partNumbers.push(partNumber);
	var treeObj = $.fn.zTree.getZTreeObj("bomtree");
	var nodes = treeObj.getSelectedNodes();
	if (nodes.length == 0) {
		layer.alert("请选择一个单板");
	}
	var productId = nodes[0].id;
	// 调用后台接口 保存
	$.ajax({
		url : "productBomController/insertProductPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"partNumbers" : JSON.stringify(partNumbers),
			"productId" : productId
		},
		success : function(json) {
			$(".parts-product-window").hide();
			layer.alert("选用成功");
		},
		error : function() {
		}
	});
}
/** 删除一个可替代料* */
function deleteOneReplace(partNumberDetail) {
	var partNumberMain = getUrlParam("goMinute");
	$.ajax({
		url : 'partComponentArrt/deleteOneReplace.do',
		data : {
			'partNumberMain' : partNumberMain,
			'partNumberDetail' : partNumberDetail
		},
		dataType : 'json',
		cache : false,
		type : 'post',
		success : function(json) {
			if (json.result == "success") {
				// layer.alert("删除成功！");
				replaceShow();
			} else {
				// layer.alert("删除失败！");
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
/** pdf预览* */
/*
 * function viewPdf(_this){ $.ajax({ url :
 * "/cms_cloudy/partComponentArrt/setPdfnameSession.do", data:'files='+_this,
 * cache : false, type: 'post', dataType : "json", success : function(json) {
 * if(json.lenth>1){ for(var i=0;i<json.length;i++){
 * $(".data-book").attr("href","/cms_cloudy/pages/parts/cms-parts.jsp#-1"); }
 * }else{
 * window.open("/cms_cloudy/generic/web/viewer.html?file="+"/cms_cloudy/partComponentArrt/displayPDF.do"); } },
 * error : function() { layer.alert("数据连接异常,注册失败！"); } }); }
 */
/** pdf预览* */
function viewPdf(_this) {
	var pdfNum = _this.split(",");
	if (pdfNum.length > 1) {
		$("body").on(
				"click",
				".data-book",
				function() {
					$(this).attr("href",
							"/cms_cloudy/pages/parts/cms-parts-minute.jsp#-1");
				})
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
// 器件信息查询
var alternativeNum = null;
function selectPartdata() {
	var partNumber = getUrlParam("goMinute");
	$.ajax({
		url : "/cms_cloudy/partComponentArrt/selectPartData.do",
		data : 'PartNumber=' + partNumber,
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json) {
			if (null != json && json.length > 0) {
				var partData = json[0];
				$("#minuItem").html(partData.ITEM);
				$("#pdfId").attr("href",
						"javascript:minuPdf('" + partData.Datesheet + "')");
				var pdfs = null;
				if (null != partData.Datesheet && "" != partData.Datesheet) {
					pdfs = partData.Datesheet.split(",");// 数据手册获取
				}
				if (null != partData.Alternative_Part
						&& "" != partData.Alternative_Part) {
					alternativeNum = partData.Alternative_Part.split(",");// 可替代料获取
				}
				if (null != pdfs && pdfs.length > 1) {
					loadPdf(pdfs);// 加载多个数据手册
				} else {
					var option = {
						theme : 'vsStyle',
						expandLevel : 2
					};
					$('#treeTable1').treeTable(option);
					$("#trState").html("全部显示");
					changeTr();// 属性信息隐藏
				}
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
/** 详情页pidf预览* */
function minuPdf(_this) {
	if ("" == _this || null == _this) {
		layer.alert("No Datas");
		return;
	}
	var pdfNum = _this.split(",");
	if (pdfNum.length > 1) {// 跳转至相对应的锚点 offset().top-300},500
		// var e=document.getElementById("scrollToBottom")
		// e.scrollTop=e.scrollHeight;//div滚动条自动滚到到底部
		$("#changeSpan").removeClass("glyphicon-plus").addClass(
				"glyphicon-minus")
		$("#trState").html("部分隐藏");
		changeTr();
		$("html,body").animate({
			scrollTop : $("#-1").offset().top - 140
		}, 500)
	} else {
		$.ajax({
			url : "/cms_cloudy/partComponentArrt/setPdfnameSession.do",
			data : 'files=' + _this,
			cache : false,
			type : 'post',
			dataType : "json",
			success : function(json) {
				for (var i = 0; i < json.length; i++) {
					  window.location.href=getContextPathForWS()+"/generic/web/viewer.html?file="+getContextPathForWS()+"/partComponentArrt/displayPDF.do";				   
				}
			},
			error : function() {
				layer.alert("数据连接异常,注册失败！");
			}
		});
	}
}
// 删除行
function removeTr(_this) {
	$(_this).parent().parent().remove();
}
// pdf加载
function loadPdf(pdfs) {
	var showPdf = "";
	var loop = "yes";
	var rows = Math.ceil(pdfs.length / 2);
	showPdf += "<tr id='-1'>";
	showPdf += "<td><span controller='true'>" + "数据手册" + "</span>" + "</td>";
	showPdf += "<td></td>";
	showPdf += "</tr>";
	for (var r = 0; r < rows; r++) {
		var num = 0;
		var idDetaile = "showPdf" + r;
		if (loop != 'yes') {
			pdfs.shift();
			pdfs.shift();
		}
		if (pdfs.length > 0) {
			showPdf += "<tr id='" + idDetaile + "' pid='-1'>";
			for (var x = 0; x < pdfs.length; x++) {
				if (null == pdfs[x] || "" == pdfs[x]) {
					break;
				}
				showPdf += "<td>"
						+ "<a class=\"data-book\" name='Datesheet' href='javascript:viewPdf(\""
						+ pdfs[x]
						+ "\")'><img src='images/pdfw.png' class='tablepdfImg'>"
						+ pdfs[x] + "</a>" + "</td>";
				num++;
				loop = 'no';
				if (num == 2) {
					num == 0;
					break;
				}
			}
			showPdf += "</tr>";
		}
	}
	$("#treeTable1").append(showPdf);
	var option = {
		theme : 'vsStyle',
		expandLevel : 2
	};
	$('#treeTable1').treeTable(option);
	// 跳转至锚点
	var goPdf = getUrlParam("goPdf");
	if (null != goPdf && "-1" == goPdf) {
		// var e=document.getElementById("scrollToBottom")
		// e.scrollTop=e.scrollHeight;//div滚动条自动滚到到底部
		$("#changeSpan").removeClass("glyphicon-plus").addClass(
				"glyphicon-minus")
		$("#trState").html("部分隐藏");
		changeTr();// 属性信息隐藏
		$("html,body").animate({
			scrollTop : $("#-1").offset().top - 136
		}, 500)
	} else {
		$("#trState").html("全部显示");
		changeTr();// 属性信息隐藏
	}
}
// 可替代物料，高亮不同项，隐藏相同项
function highLightDifferent() {
	var table = document.getElementById("firstTr");// 获取表格
	var Tr = table.getElementsByTagName("tr");// 获取行
	if (Tr.length <= 1) {// 说明没有数据
		return;
	}
	if (null == alternativeNum || 0 == alternativeNum.length
			|| 1 == alternativeNum.length) {
		return;
	}
	if ($("#highLightDifferent").is(':checked')) {
		for (var i = 0; i < Tr.length; i++) {
			if (i == 0) {// 第一行不比较
				continue;
			}
			// alert(1);
			var list = new Array();
			var tdArr = $(Tr[i]).find("td");
			for (var n = 0; n < alternativeNum.length; n++) {
				var m = "";
				var value = tdArr.eq(n).html();
				value = value.replace(/^\s+|\s+$/g, "");
				// alert(2);
				if ("" == value || null == value) {
					m = "空";
				} else {
					m = tdArr.eq(n).text();
				}
				list.push(m);// 往集合里放td的值
			}
			list = list.unique3();// 数组去重
			// alert(3);
			if (list.length != 1) {// 去重之后的长度【1,1,1】去重为【1】
				$(Tr[i]).css("background", "#d9534f").css("color", "#fff");
			}
		}
	} else {
		for (var i = 0; i < Tr.length; i++) {
			$(Tr[i]).css("background", "").css("color", "#333");
		}
	}
}
function hideSame() {
	var table = document.getElementById("firstTr");// 获取表格
	var Tr = table.getElementsByTagName("tr");// 获取行
	if (Tr.length <= 1) {// 说明没有数据
		return;
	}
	if (null == alternativeNum || 0 == alternativeNum.length
			|| 1 == alternativeNum.length) {
		return;
	}
	if ($("#hideSame").is(':checked')) {
		for (var i = 0; i < Tr.length; i++) {
			if (i == 0) {// 第一行不比较
				continue;
			}
			// alert(1);
			var list = new Array();
			var tdArr = $(Tr[i]).find("td");
			for (var n = 0; n < alternativeNum.length; n++) {
				var m = "";
				var value = tdArr.eq(n).html();
				value = value.replace(/^\s+|\s+$/g, "");
				// alert(2);
				if ("" == value || null == value) {
					m = "空";
				} else {
					m = tdArr.eq(n).text();
				}
				list.push(m);// 往集合里放td的值
			}
			list = list.unique3();// 数组去重
			// alert(3);
			if (list.length == 1) {// 去重之后的长度【1,1,1】去重为【1】
				$(Tr[i]).hide();
			}
		}
	} else {
		for (var i = 0; i < Tr.length; i++) {
			$(Tr[i]).show();
		}
	}
}
// 数组去重
Array.prototype.unique3 = function() {
	var res = [];
	var json = {};
	for (var i = 0; i < this.length; i++) {
		if (!json[this[i]]) {
			res.push(this[i]);
			json[this[i]] = 1;
		}
	}
	return res;
}
function loadZhexian() {
	// 元器件分析
	var PartNumber = getUrlParam("goMinute");
	// 基于准备好的dom，初始化echarts实例
	$
			.ajax({
				type : "post",
				url : "partComponentArrt/comparePartsByECharts.do",
				data : "PartNumber=" + PartNumber,
				dataType : "json",
				success : function(data) {
					if (null == data) {
						$("#live").hide();
						return;
					} else {
						$("#live").show();
					}
					var xAxis = data.xAxis;
					var legends = data.legend;
					var datas = data.datas;
					var myChart = echarts.init(document
							.getElementById('live-zheXian'));
					var option = {
						title : {
							text : ''
						},
						tooltip : {
							trigger : 'axis',
							axisPointer : { // 坐标轴指示器，坐标轴触发有效
								type : 'line', // 默认为直线，可选为：'line' | 'shadow'
								lineStyle : { // 直线指示器样式设置
									color : '#C0C0C0',
									width : 2,
									type : 'dotted'// dashed --solid
								}
							},
							formatter : function(value) {
								var valueStr = "";
								if (value[0].value == 0) {
									valueStr = "New(新器件)";
								}
								if (value[0].value == 1) {
									valueStr = "Production(在产)";
								}
								if (value[0].value == 2) {
									valueStr = "NRND(限用器件)";
								}
								if (value[0].value == 3) {
									valueStr = "EOL(停产)";
								}
								if (value[0].value == 4) {
									valueStr = "Obsolete(失效)";
								}
								if (value[0].value == 5) {
									valueStr = "Unknown(未认证)";
								}
								var result = '日期：' + value[0].name + '<br>'
										+ '状态：' + valueStr;
								return result;
							}
						},
						legend : {
							show : true,
							orient : 'horizontal',
							data : [ {
								name : legends[0],
								textStyle : {
									fontSize : 12,
									// fontWeight : 'bolder',
									color : '#000000'
								},
								// icon :
								// 'image:///cms_cloudy/images/starx.png'//
								// 格式为'image://+icon文件地址'，其中image::后的//不能省略
								icon : 'diamond'// 格式为'image://+icon文件地址'，其中image::后的//不能省略
							} ]
						},
						calculable : false,
						toolbox : {
							show : false,
							feature : {
								dataZoom : {
									yAxisIndex : 'none'
								},
								dataView : {
									readOnly : false
								},
								magicType : {
									type : [ 'line', 'bar' ]
								},
								restore : {},
								saveAsImage : {}
							}
						},
						xAxis : {
							type : 'category',
							// splitLine:{show: false},//去除网格线
							// boundaryGap : false,
							data : xAxis,
							axisLine : {// 坐标轴线条相关设置(颜色等)
								lineStyle : {
									color : '#d0d0d0'
								}
							},
							axisLabel : {
								textStyle : {
									fontWeight : 'normal', // 标题颜色
									color : '#000000'
								},
								interval : 0,// 横轴信息全部显示
								rotate : -30
							// -30度角倾斜显示
							}
						},
						yAxis : {
							type : 'value',
							axisLine : {// 坐标轴线条相关设置(颜色等)
								lineStyle : {
									color : '#46A3FF'
								}
							},
							axisLabel : {
								textStyle : {
									fontWeight : 'normal', // 标题颜色
									color : '#000000'
								},
								formatter : function(value, index) {
									var texts = [];
									if (value === 0) {
										texts.push('New(新器件)');
									}
									if (value == 1) {
										texts.push('Production(在产)');
									}
									if (value == 2) {
										texts.push('NRND(限用器件)');
									}
									if (value == 3) {
										texts.push('EOL(停产)');
									}
									if (value == 4) {
										texts.push('Obsolete(失效)');
									}
									if (value == 5) {
										texts.push('Unknown(未认证)');
									}
									return texts;
								}
							},
							max : 5,
							min : 0
						},
						grid : {// 图像布局
							borderWidth : '0',
							x : 110
						},
						series : [ {
							name : legends[0],
							type : 'line',
							// symbol : 'emptypin',//拐点样式 arrow star6
							// emptypin，arrow，star6，emptyCircle，emptyHeart，droplet
							// symbolSize : 10,//拐点大小
							smooth : true,
							data : datas,
							markLine : {
								data : []
							},
							itemStyle : {
								normal : {
									color : "#d9534f"// 折线颜色
								}
							},
							lineStyle : {
								width : 1,// 折线宽度
								color : "#FF2D2D",// 折线颜色
								borderColor : "#006000" // 拐点边框颜色
							}
						} ]
					};
					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option);
				}
			});
}
// 产品追溯
function loadTree() {
	var PartNumber = getUrlParam("goMinute");
	$.ajax({
		url : '/cms_cloudy/productBomController/proAscend.do',
		data : 'partNumber=' + PartNumber,
		dataType : 'json',
		success : function(json) {
			var list = json.list;
			var data;
			if ("" == list || null == list) {
				// myChart.showLoading();
				data = [ {
					"name" : json.partNumber + "[qty=0]",
					"children" : []
				} ]
			} else {
				data = [ {
					"name" : json.partNumber,
					"children" : json.listObj
				} ]
			}
			var myChart = echarts.init(document.getElementById('project-box'));
			myChart.setOption(option = {
				tooltip : {
					trigger : 'item',
					triggerOn : 'mousemove'
				},
				series : [ {
					type : 'tree',

					name : '产品信息',

					data : data,

					top : '5%',
					left : '20%',
					bottom : '2%',
					right : '26%',

					symbolSize : 7,

					label : {
						normal : {
							position : 'left',
							verticalAlign : 'middle',
							align : 'right'
						}
					},

					leaves : {
						label : {
							normal : {
								position : 'right',
								verticalAlign : 'middle',
								align : 'left'
							}
						}
					},
					itemStyle : {
						normal : {
							color : "#d9534f",
							label : {
								show : true,
								position : 'inside',
								textStyle : {
									// 字体颜色、大小、加粗
									color : '#000',
									fontSize : 10,
									fontWeight : 'bolder'
								}
							}
						}
					},
					expandAndCollapse : true,

					animationDuration : 550,
					animationDurationUpdate : 750

				} ]
			});
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
// tr隐藏与显示 ---只显示22行数据
function changeTr() {
	var table = document.getElementById("treeTable1");// 获取表格
	var Tr = table.getElementsByTagName("tr");// 获取行
	if (Tr.length <= 22) {// 少于22行无需进行隐藏或者显示操作
		$(".showAllBox").hide();
		return;
	}
	if ($("#trState").html() == "全部显示") {// 正处于隐藏状态
		for (var i = 22; i < Tr.length; i++) {// 循环隐藏
			$(Tr[i]).hide();
		}
	} else {
		for (var i = 22; i < Tr.length; i++) {// 全部显示
			$(Tr[i]).show();
		}
	}
}
// 初始化详情页
function initParticularsAttr() {
	var id = getUrlParam("partId");
	$
			.ajax({
				url : getContextPathForWS()+'/partComponent/selectEditPageField.do',
				type : 'post',
				dataType : 'json',
				data : {
					"id" : id
				},
				cache : false,
				success : function(json) {
					var partData=json.partData;
					// 动态生成添加字段的显示名
					var insertList=json.insertList;
					// 生成其他其他添加字段
					var normalList=new Array();// 基本属性集合
					var qualityList=new Array();// 质量属性集合
					var designList=new Array();// 设计属性集合
					var purchaseList=new Array();// 采购属性集合
					for(var i=0;i<insertList.length;i++){
						if(insertList[i].type==1){// 基本属性
							normalList.push(insertList[i]);
							continue;
						}
						if(insertList[i].type==2){// 质量属性
							qualityList.push(insertList[i]);
							continue;
						}
						if(insertList[i].type==3){// 设计属性
							designList.push(insertList[i]);
							continue;
						}
						if(insertList[i].type==4){// 采购属性
							purchaseList.push(insertList[i]);
							continue;
						}
					}
					var normalObj=getParticularFieldTr(normalList,partData);
					var qualityObj=getParticularFieldTr(qualityList,partData);
					var designObj=getParticularFieldTr(designList,partData);
					var purchaseObj=getParticularFieldTr(purchaseList,partData);
					var normalAttrsStr=normalObj.str;// 基本属性
					var qualityAttrsStr=qualityObj.str;// 质量属性
					var designAttrsStr=designObj.str;// 设计属性
					var purchaseAttrsStr=purchaseObj.str;// 采购属性
					var normalRP=normalObj.rp;// 控制属性跨列的变量
					var qualityRP=qualityObj.rp;// 控制属性跨列的变量
					var designRP=designObj.rp;// 控制属性跨列的变量
					var purchaseRP=purchaseObj.rp;// 控制属性跨列的变量
					// 基本属性
					if(normalAttrsStr!=''){
						normalAttrsStr=normalAttrsStr.substring(4,normalAttrsStr.length);
						normalAttrsStr='<tr><td rowspan="'+normalRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("jbsx")+'</td>'+normalAttrsStr;
					}
					// 质量属性
					if(qualityAttrsStr!=''){
						qualityAttrsStr=qualityAttrsStr.substring(4,qualityAttrsStr.length);
						qualityAttrsStr='<tr><td rowspan="'+qualityRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("zlsx")+'</td>'+qualityAttrsStr;
					}
					// 设计属性
					if(designAttrsStr!=''){
						designAttrsStr=designAttrsStr.substring(4,designAttrsStr.length);
						designAttrsStr='<tr><td rowspan="'+designRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("sjsx")+'</td>'+designAttrsStr;
					}
					// 采购属性
					if(purchaseAttrsStr!=''){
						purchaseAttrsStr=purchaseAttrsStr.substring(4,purchaseAttrsStr.length);
						purchaseAttrsStr='<tr><td rowspan="'+purchaseRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("cgsx")+'</td>'+purchaseAttrsStr;
					}
					$("#partDataTable").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
					// 型号
					$("#minuItem").html(partData.ITEM);
					// 数据手册
					$("#pdfId").attr(
							"onclick",
							"particularsPdf('" + partData.Datesheet
									+ "')");
					var pdfs = null;
					if (null != partData.Datesheet && "" != partData.Datesheet) {
						pdfs = partData.Datesheet.split(",");// 数据手册获取
					}
					if (null != partData.Alternative_Part
							&& "" != partData.Alternative_Part) {
						alternativeNum = partData.Alternative_Part.split(",");// 可替代料获取
					}
					if (null != pdfs && pdfs.length > 1) {
						showPdfDiv(pdfs);// 加载多个数据手册
					} else {
						$(".Datesheets-box").hide();
					}
					 //pdf锚点跳转
					var goPdf= GetQueryString("goPdf");
					if("-1" == goPdf){
						$("#Datesheets").show();
						$("html,body").animate({
							scrollTop : $("#-1").offset().top - 1
						}, 500)
					}
					//是否收藏
					if(partData.isCollection == true){
			    		$("#loveSpan").attr("class","glyphicon glyphicon-heart");
					}else{
			    		$("#loveSpan").attr("class","glyphicon glyphicon-heart-empty");
					}
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}
//生成字段tr
function getParticularFieldTr(insertList,partData){
	var obj=new Object();
	var string="";
	var rp=0;
	for(var i=0;i<insertList.length;i++){
		var xh="";
		var value=partData==undefined?"":partData[insertList[i].fieldName];
		value=value==undefined?"":value;
		var str=value;
		if(',shape_img,size_img,characteristic_curve_img,typical_ap_img,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			str="<div class=\"left parts-images\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"图片上传最佳尺寸【550px*320px】\">" +
					"<img src=\""+value+"\" id=\""+insertList[i].fieldName+"1\"></div>";
		}
		if(i==insertList.length-1&&rp%2==0){
			string+="<tr><td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td colspan=\"3\" >"+str+"</td></tr>";
		}else{
			if(rp%2==0){
				string+="<tr>";
			}
			string+="<td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td class=\"valueLength\">"+str+"</td>";
			if(rp%2!=0){
				string+="</tr>";
			}
		}
		rp++;
	}
	obj.str=string;
	obj.rp=rp%2==0?parseInt(rp/2):(parseInt(rp/2)+1);
	return obj;
}
// pdf展示预览
function showPdfDiv(pdfs) {
	var html = '';
	for (var i = 0; i < pdfs.length; i++) {
		html += '	<p><i></i><a href="javascript:viewPdf(\'' + pdfs[i] + '\')">'
				+ pdfs[i] + '</a></p>';
	}
	$("#Datesheets-box").html(html);
	$(".Datesheets-box").show();
}
/** 详情页pdf预览* */
function particularsPdf(_this) {
	if ("" == _this || null == _this) {
		layer.alert("No Datas");
		return;
	}
	var pdfNum = _this.split(",");
	if (pdfNum.length > 1) {
		$("html,body").animate({
			scrollTop : $("#-1").offset().top - 1
		}, 500)
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
// 评价修改
function evaluateEdit(ts, id) {
	var evaluateWord = $(ts).parent().parent().prev().html(); // 评价内容
	var score = $(ts).parent().parent().prev().prev().attr("data-score"); // 评价内容
	$("input[name='evaContentUpdate']").val(evaluateWord);
	$('#starUpdate').attr("data-score", score);
	$('#starUpdate').raty({// 初始评分
		score : function() {
			return $(this).attr('data-score');
		}
	});
	$("#updateRaty").attr("onclick", "updateRaty(" + id + ")");
	$(".user-evaluateEdit-window").show();
}
// 保存修改结果----评价
function updateRaty(id) {
	loadProperties();
	var evaContent = $("input[name='evaContentUpdate']").val();
	var xxx = $('#starUpdate').raty('score');
	if (typeof (xxx) == "undefined") {
		layer.alert($.i18n.prop("minu-evaTip1"));
		return;
	}
	if ("" == evaContent) {
		layer.alert($.i18n.prop("minu-evaTip2"));
		return;
	}
	$.ajax({
		url : 'partComponentArrt/updatePartEvaluation.do',
		contentType : 'application/json;charset=UTF-8',
		data : {
			'Votes' : xxx,
			'evaContent' : encodeURI(evaContent),
			'id' : id
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			/**清空星级评价**/
			$('#starUpdate').raty({
				score : function() {
					return $(this).attr('data-score');
				}
			});
			$("input[name='evaContentUpdate']").val('')
			$(".user-evaluateEdit-window").hide();
			layer.alert("操作成功！");
			$("#Pagination").html('');
			evaluateShow();
		},
		error : function() {
			layer.alert("数据处理异常，请联系管理员!");
		}
	})
}
//评价删除
function evaluateDel(id) {
	loadProperties();//国际化
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [ $.i18n.prop("determineBtn"), $.i18n.prop("resetBtn") ]
	// 按钮
	}, function() {
		$.ajax({
			url : 'partComponentArrt/deletePartEvaluation.do',
			data : {
				'partEvaluationId' : id
			},
			dataType : 'json',
			cache : false,
			type : 'post',
			success : function(json) {
				layer.alert("刪除成功！");
				$("#Pagination").html('');
				evaluateShow();
			},
			error : function() {
				layer.alert("数据处理异常，请联系管理员!");
			}
		})
	});
}