/**
 * 库文件加载
 * 
 * @param {}
 *            path
 */
function initFileList(path) {
	$.ajax({
		url : 'fileUpload/selectAllFile.do',
		data : 'path=' + path,
		dataType : 'json',
		cache : false,
		success : function(json) {
			var latestVal = json.pojo;
			json = json.list;
			var html = '';
			for (var x = 0; x < json.length; x++) {
				var file = json[x];
				var name = file.name;
				if (name == latestVal.name) {
					html += '<option value=' + name + ' selected>' + name
							+ '</option>';
				} else {
					html += '<option value=' + name + '>' + name + '</option>';
				}
			}
			$('#selectFile').html(html);
			$('#capturesSelectTag').html(html);
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	})
}
/**
 * 原理图符号上传弹出框初始化 0: Concept弹出框
 * 
 * @param {}
 *            type
 */
function symbolUploadBefore(type) {
	if ('0' == type) {
		initFileList('cms_server/Design/SYM/ADRSYM');
		$(".flow-updateSymble-end").show();
		$(".flow-updateSymble").show();
		$(".flow-updateSymble-edit").show();
	} else if ('1' == type) {
		initFileList('cms_server/Design/SYM/CDSSYM');
		$(".capture-uploadWindow").show();
	}
}
/**
 * 原理图符号上传界面初始化 0: Concept上传界面初始化
 * 
 * @param {}
 *            type
 */
function symbolUpload(type) {
	if ("0" == type) {
		 if (!!window.ActiveXObject || "ActiveXObject" in window) {
    		 layer.alert("IE浏览器暂时不支持上传文件夹！");
    		 return;
		 }else{
			$("#uploadFolderInp").click();
		 }
	}
	// $("#uploadBtn").show();
	// $(".stream-uploadWindow").show();
}
var _t;// Stream上传流值初始化
/**
 * Stream上传按钮构造
 * 
 * @param {}
 *            fileType
 * @param {}
 *            path
 */
function uploadSetting(fileType, path) {
	loadProperties();// 国际化
	if (fileType == ".doc") {
		var config = {
			browseFileId : "i_select_files",
			browseFileBtn : "<div id= 'docDiv' class=‘selectDiv’>"
					+ $.i18n.prop("streamFile1") + "</div>",
			dragAndDropArea : "i_select_files",
			dragAndDropTips : "<span>" + $.i18n.prop("streamFile2") + "</span>",
			filesQueueId : "i_stream_files_queue",
			filesQueueHeight : 200,
			messagerId : "i_stream_message_container",
			multipleFiles : true,
			autoUploading : false,
			postVarsPerFile : {
				param1 : path
			},
			swfURL : "/cms_cloudy/pages/staticpage/swf/FlashUploader.swf",
			tokenURL : "/cms_cloudy/tokencontroller/tk",
			frmUploadURL : "/cms_cloudy/formDatacontroller/fd",
			uploadURL : "/cms_cloudy/streamcontroller/upload",
			onQueueComplete : function() {
				nameAlike(path);
			}
		};
		_t = new Stream(config);
		$('#docDiv').html('');
	} else {
		var config = {
			browseFileId : "i_select_files",
			browseFileBtn : "<div class=‘selectDiv’>"
					+ $.i18n.prop("streamFile1") + "</div>",
			dragAndDropArea : "i_select_files",
			dragAndDropTips : "<span>" + $.i18n.prop("streamFile2") + "</span>",
			filesQueueId : "i_stream_files_queue",
			filesQueueHeight : 200,
			messagerId : "i_stream_message_container",
			multipleFiles : true,
			autoUploading : false,
			postVarsPerFile : {
				param1 : path
			},
			swfURL : "/cms_cloudy/pages/staticpage/swf/FlashUploader.swf",
			tokenURL : "/cms_cloudy/tokencontroller/tk",
			frmUploadURL : "/cms_cloudy/formDatacontroller/fd",
			uploadURL : "/cms_cloudy/streamcontroller/upload",
			extFilters : fileType,
			onQueueComplete : function() {
				nameAlike(path);
			}
		};
		_t = new Stream(config);
	}
}
/**
 * 是否存在相同文件名校验及处理;
 */
function nameAlike(filepath) {
	loadProperties();// 国际化
	$
			.ajax({
				url : 'fileUpload/checkNameAlike.do',
				dataType : 'json',
				cache : false,
				success : function(data) {
					if (null != data && "" != data) {
						var json = {
							title : $.i18n.prop("tipTxt") + "：",
							msg : $.i18n.prop("upload-alert3"),
							buttons : [
									{
										title : $.i18n.prop("saveBtn"),
										color : "purple",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/saveAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(data),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("alertMsg2"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														complete : function() {
															if (filepath == 'cms_server/Design/SYM/ADRSYM') {
																initFileList(filepath);
															}
														},
														error : function() {
															layer
																	.alert("保存操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("coverTxt"),
										color : "green",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/coverAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(data),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														complete : function() {
															if (filepath == 'cms_server/Design/SYM/ADRSYM') {
																initFileList(filepath);
															}
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("cancelBtn"),
										color : "red",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/deleteAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(data),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														complete : function() {
															if (filepath == 'cms_server/Design/SYM/ADRSYM') {
																initFileList(filepath);
															}
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									} ]
						}
						$.alertView(json);
					} else {
						var xx = {
							title : $.i18n.prop("tipTxt") + "：",
							msg : $.i18n.prop("alertMsg4"),
							buttons : [ {
								title : $.i18n.prop("determineBtn"),
								color : "red",
								click : function() {
								}
							} ]
						}
						$.alertView(xx);
						if (filepath == 'cms_server/Design/SYM/ADRSYM') {
							initFileList(filepath);
						}
					}
					_t.destroy();
					_t = null;
					$("#uploadBtn").hide();
					$(".stream-uploadWindow").hide();
					$("#i_stream_message_container").html('');
				},
				error : function() {
					layer.alert("数据加载异常，请联系管理员！");
				}
			})
}
// 关闭stream上传按钮
$("body").on("click", ".stream-uploadWindow-close", function() {
	_t.destroy();
	_t = null;
	$("#uploadBtn").hide();
	$(".stream-uploadWindow").hide();
	$("#i_stream_message_container").html('');
});
/**
 * 点击确定按钮 0：concept库文件确定选择
 * 
 * @param {}
 *            type
 */
function determineSelect(type) {
	if ('0' == type) {
		var selectVal = $("#selectFile").val();
		$("input[name='Sym_for_ADR']").val(selectVal);
		$(".flow-updateSymble").hide();
		$(".flow-updateSymble-end").hide();
		$(".flow-updateSymble-edit").hide();
	} else if ('1' == type) {
		var selectVal = $("#capturesSelectTag").val();
		$("input[name='Sym_for_CAP']").val(selectVal);
		$(".capture-uploadWindow").hide();
	}
}
/**
 * DateSheet异步上传
 */
function batchUploadFile() {
	var formData = new FormData($("#batchUploadDatesheetFileForm")[0]);
	$
			.ajax({
				url : 'fileUpload/batchUploadFile.do',
				type : 'POST',
				dataType : 'json', // 接受数据格式
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					$("#dateSheetUpload").val('');
					var alikeList = data.list;
					if (alikeList.length > 0) {
						var json = {
							title : $.i18n.prop("tipTxt") + "：",
							msg : $.i18n.prop("upload-alert3"),
							buttons : [
									{
										title : $.i18n.prop("saveBtn"),
										color : "purple",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/saveAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("alertMsg2"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															refresDatesheetTree();// 刷新数据手册列表
														},
														error : function() {
															layer
																	.alert("保存操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("coverTxt"),
										color : "green",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/coverAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															refresDatesheetTree();// 刷新数据手册列表
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("cancelBtn"),
										color : "red",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/deleteAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									} ]
						}
						$.alertView(json);
					} else {
						refresDatesheetTree();// 刷新数据手册列表
					}
				},
				error : function(data) {
					alert(data);
				}
			});
}
/**
 * 数据手册刷新
 */
function refresDatesheetTree() {
	$.ajax({
		url : 'partComponentArrt/selectDatasheets.do',
		dataType : 'json',
		cache : false,
		success : function(json) {
			var list = json.list;
			var settingDatasheet = {
				view : {
					showIcon : false
				},
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true
					}
				},
				callback : {
					onClick : selectTreeByDatesheet
				}
			};
			if (null != list && "" != list) {
				$.fn.zTree.init($("#datasheetTree"), settingDatasheet, list);// 加载树结构
				var treeObj = $.fn.zTree.getZTreeObj("datasheetTree");
				treeObj.expandAll(true);
			} else {
				var zNodesUser = [ {
					id : -1,
					pId : 0,
					name : "数据手册",
					open : true
				} ];
				$.fn.zTree.init($("#datasheetTree"), settingDatasheet,
						zNodesUser);// 加载树结构
			}
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！")
		}
	})
}
function startbatchUploadFilesFromManage(filePath) {
	var formData = new FormData($("#batchUploadFilesForm")[0]);
	$
			.ajax({
				url : 'fileUpload/batchUploadFilesFromManage.do?filePath='
						+ filePath,
				type : 'POST',
				dataType : 'json', // 接受数据格式
				data : formData,
				async : true,
				cache : false,
				contentType : false,
				processData : false,
				beforeSend : function() {
					$("#LoadingPng").css("display", "block");
				},
				success : function(data) {
					$("#batchUploadFilesInp").val('');
					var alikeList = data.list;
					if (alikeList.length > 0) {
						$("#LoadingPng").hide();
						var json = {
							title : $.i18n.prop("tipTxt") + "：",
							msg : $.i18n.prop("upload-alert3"),
							buttons : [
									{
										title : $.i18n.prop("saveBtn"),
										color : "purple",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/saveAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("alertMsg2"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															zTree = $.fn.zTree
																	.getZTreeObj("treeDemo");
															var nodes = zTree
																	.getSelectedNodes();
															zTreeOnClick(null,
																	null,
																	nodes[0],
																	null);
														},
														error : function() {
															layer
																	.alert("保存操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("coverTxt"),
										color : "green",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/coverAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															zTree = $.fn.zTree
																	.getZTreeObj("treeDemo");
															var nodes = zTree
																	.getSelectedNodes();
															zTreeOnClick(null,
																	null,
																	nodes[0],
																	null);
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("cancelBtn"),
										color : "red",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/deleteAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									} ]
						}
						$.alertView(json);
					} else {
						layer.alert($.i18n.prop("alertMsg4"));
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = zTree.getSelectedNodes();
						zTreeOnClick(null, null, nodes[0], null);
					}
				},
				complete : function() {
					$("#LoadingPng").hide();
					// $(".LoadingBox").hide();
					// $(".modal-backdrop").hide();
					// $("#LoadingPng").hide();
					// $("#LoadingPng").css("display","none");
				},
				error : function(data) {
					layer.alert("服务器异常，请联系管理员！");
				}
			});
}
// 上传文件夹
document.getElementById('uploadFolderInp').onchange = function(e) {
	var formData = new FormData($("#uploadFolderForm")[0]);
	$
			.ajax({
				url : 'fileUpload/uploadFolderForm.do?filePath='
						+ "cms_server/Design/SYM/ADRSYM",
				type : 'POST',
				dataType : 'json', // 接受数据格式
				data : formData,
				async : true,
				cache : false,
				contentType : false,
				processData : false,
				beforeSend : function() {
					$("#LoadingPng").css("display", "block");
				},
				success : function(data) {
					$("#uploadFolderInp").val('');
					var alikeList = data.list;
					if (alikeList.length > 0) {
						$("#LoadingPng").hide();
						var json = {
							title : $.i18n.prop("tipTxt") + "：",
							msg : $.i18n.prop("upload-alert3"),
							buttons : [
									{
										title : $.i18n.prop("saveBtn"),
										color : "purple",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/saveAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("alertMsg2"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															zTree = $.fn.zTree
																	.getZTreeObj("treeDemo");
															if (null != zTree) {
																var nodes = zTree
																		.getSelectedNodes();
																zTreeOnClick(
																		null,
																		null,
																		nodes[0],
																		null);
															} else {
																initFileList("cms_server/Design/SYM/ADRSYM");
															}
														},
														error : function() {
															layer
																	.alert("保存操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("coverTxt"),
										color : "green",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/coverAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
															zTree = $.fn.zTree
																	.getZTreeObj("treeDemo");
															if (null != zTree) {
																var nodes = zTree
																		.getSelectedNodes();
																zTreeOnClick(
																		null,
																		null,
																		nodes[0],
																		null);
															} else {
																initFileList("cms_server/Design/SYM/ADRSYM");
															}
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									},
									{
										title : $.i18n.prop("cancelBtn"),
										color : "red",
										click : function() {
											$
													.ajax({
														url : 'fileUpload/deleteAlikeFile.do',
														data : 'dataList='
																+ JSON
																		.stringify(alikeList),
														dataType : 'json',
														cache : false,
														success : function(
																datas) {
															var jsons = {
																title : $.i18n
																		.prop("tipTxt")
																		+ "：",
																msg : $.i18n
																		.prop("operationSus"),
																buttons : [ {
																	title : $.i18n
																			.prop("determineBtn"),
																	color : "red",
																	click : function() {
																	}
																} ]
															}
															$.alertView(jsons);
														},
														error : function() {
															layer
																	.alert("覆盖操作失败！");
														}
													})
										}
									} ]
						}
						$.alertView(json);
					} else {
						layer.alert($.i18n.prop("alertMsg4"));
						zTree = $.fn.zTree.getZTreeObj("treeDemo");
						if (null != zTree) {
							var nodes = zTree.getSelectedNodes();
							zTreeOnClick(null, null, nodes[0], null);
						} else {
							initFileList("cms_server/Design/SYM/ADRSYM");
						}
					}
				},
				complete : function() {
					$("#LoadingPng").hide();
				},
				error : function(data) {
					layer.alert("服务器异常，请联系管理员！");
				}
			});
};
function checkAccept(path, type) {
	var realType = '';
	if ("cms_server/Design/SYM/CDSSYM" == path) {
		if (type != "olb") {
			realType = "olb";
		}
	}
	if ("cms_server/Design/Footprint/Pad" == path) {
		if (type != "pad") {
			realType = "pad";
		}
	}
	if ("cms_server/Design/Footprint/Flash" == path) {
		if (type != "dra") {
			realType = "dra";
		}
	}
	if ("cms_server/Design/Footprint/FPT" == path) {
		if (type != "dra") {
			realType = "dra";
		}
	}
	if ("cms_server/Design/STEPModel" == path) {
		if (type != "stp") {
			realType = "stp";
		}
	}
	if ("cms_server/Design/SATModel" == path) {
		if (type != "sat") {
			realType = "sat";
		}
	}
	if ("cms_server/Design/TS/DSN" == path) {
		if (type != "dsn") {
			realType = "dsn";
		}
	}
	if ("cms_server/Design/TS/MDD" == path) {
		if (type != "mdd") {
			realType = "mdd";
		}
	}
	if ("cms_server/LMC" == path) {
		if (type != "lmc") {
			realType = "lmc";
		}
	}
	return realType;
}
/**
 * 上传管理界面----文件上传(非文件夹)
 */
function batchUploadFilesFromManage(filePath){
	var alertMsg = '';
	var fileVal = document.getElementById("batchUploadFilesInp").files;
	for (var x = 0; x < fileVal.length; x++) {
		var acceptVal = fileVal[x].name.toLowerCase().split('.').splice(-1);
		alertMsg = checkAccept(filePath, acceptVal);
		if (alertMsg != '') {
			alertMsg = "该节点下只能上传" + alertMsg + "格式文件！"
			$("#batchUploadFilesInp").val('');
			break;
		}
		if(x == fileVal.length - 1){
			startbatchUploadFilesFromManage(filePath);//非文件夹上传
		}
	}
	if(alertMsg != ''){
		layer.alert(alertMsg);
	}
}