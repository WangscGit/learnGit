<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/capture-uploadWindow.css" />
<script type="text/javascript" src="scripts/doublebox-bootstrap.js"></script>

<!-- 上传capture原理图 -->
 <div class="capture-uploadWindow">
        <div class="capture-uploadWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="upload-file"/></a>
        <button type="button" class="close capture-uploadWindow-close" aria-label="Close" onclick="closeCaptureWindow()"><span>&times;</span></button>
        </div>
        <div class="capture-uploadWindow-body">     
        <div class="pictureBox">
           <div class="pictureLeft left">
            <table class="table">
             <tr>
                   <td><span>库文件：</span></td>
                   <td>
                   <select class="form-control" id="capturesSelectTag">
                   </select>
                   </td>              
             </tr>
        </table>
                <p><b>原理图</b></p>
                <div></div>
                 <p><b>组成员</b></p>
                <div></div>
                 <p><b>原理图符号列表</b></p>
                <div class="three"></div>
           </div>
           <div class="pictureRight right">
            <table class="table">
             <tr>
                   <td><span>Type：</span></td>
                   <td><select class="form-control"><option>1</option><option>2</option></select></td>
                   <td>
                   <form id="batchUploadFilesFormCapture" action="" method="post" enctype="multipart/form-data">
                     <a class="uploadFileBtnBox" onclick="">
                          <input class="uploadFileBtn" id="batchUploadFilesInpFromCapture" name="batchUploadFilesInp"  onchange="batchUploadFilesFromCapture('cms_server/Design/SYM/CDSSYM')"  type="file" class="right"  multiple accept=".olb"/><fmt:message  key="wjsc"/>
                          <input type="hidden" name="test">
                      </a>
                   </form>
              </td>
             </tr>
             <tr>
                   <td><span>Type：</span></td>
                   <td><select class="form-control"><option>1</option><option>2</option></select></td>
                   <td></td>
             </tr>
        </table>
           symbol</div>
        </div>
        </div>
         <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" href="javascript:closeDialog('capture-uploadWindow')"><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="determineSelect('1')"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
 </div>
<script type="text/javascript">
//关闭弹窗按钮
function closeCaptureWindow(){
    $(".capture-uploadWindow").hide();
}
/**
 * 上传管理界面----文件上传(非文件夹)
 */
function batchUploadFilesFromCapture(filePath){
	var alertMsg = '';
	var fileVal = document.getElementById("batchUploadFilesInpFromCapture").files;
	for (var x = 0; x < fileVal.length; x++) {
		var acceptVal = fileVal[x].name.toLowerCase().split('.').splice(-1);
		if (acceptVal != "olb") {
			alertMsg = "该节点下只能上传" + "olb" + "格式文件！"
			$("#batchUploadFilesInpFromCapture").val('');
			break;
		}
		if(x == fileVal.length - 1){
			startBatchUploadFilesFromCapture(filePath);//非文件夹上传
		}
	}
	if(alertMsg != ''){
		layer.alert(alertMsg);
	}
}
/**
 * 上传管理界面----文件上传(非文件夹)
 */
function startBatchUploadFilesFromCapture(filePath) {
	var formData = new FormData($("#batchUploadFilesFormCapture")[0]);
	$.ajax({
		url : 'fileUpload/batchUploadFilesFromManage.do?filePath='+filePath,
		type : 'POST',
		dataType : 'json', // 接受数据格式
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		beforeSend : function() {
			$("#LoadingPng").css("display", "block");
		},
		success : function(data) {
			$("#batchUploadFilesInpFromCapture").val('');
			var alikeList = data.list;
			if (alikeList.length > 0) {
				var json = {
					title : $.i18n.prop("tipTxt") + "：",
					msg : $.i18n.prop("upload-alert3"),
					buttons : [{
						title : $.i18n.prop("saveBtn"),
						color : "purple",
						click : function() {
							$.ajax({
								url : 'fileUpload/saveAlikeFile.do',
								data : 'dataList=' + JSON.stringify(alikeList),
								dataType : 'json',
								cache : false,
								success : function(datas) {
									var jsons = {
										title : $.i18n.prop("tipTxt") + "：",
										msg : $.i18n.prop("alertMsg2"),
										buttons : [{
											title : $.i18n.prop("determineBtn"),
											color : "red",
											click : function() {
											}
										}]
									}
									$.alertView(jsons);
									initFileList(filePath)
								},
								error : function() {
									layer.alert("保存操作失败！");
								}
							})
						}
					}, {
						title : $.i18n.prop("coverTxt"),
						color : "green",
						click : function() {
							$.ajax({
								url : 'fileUpload/coverAlikeFile.do',
								data : 'dataList=' + JSON.stringify(alikeList),
								dataType : 'json',
								cache : false,
								success : function(datas) {
									var jsons = {
										title : $.i18n.prop("tipTxt") + "：",
										msg : $.i18n.prop("operationSus"),
										buttons : [{
											title : $.i18n.prop("determineBtn"),
											color : "red",
											click : function() {
											}
										}]
									}
									$.alertView(jsons);
									initFileList(filePath)
								},
								error : function() {
									layer.alert("覆盖操作失败！");
								}
							})
						}
					}, {
						title : $.i18n.prop("cancelBtn"),
						color : "red",
						click : function() {
							$.ajax({
								url : 'fileUpload/deleteAlikeFile.do',
								data : 'dataList=' + JSON.stringify(alikeList),
								dataType : 'json',
								cache : false,
								success : function(datas) {
									var jsons = {
										title : $.i18n.prop("tipTxt") + "：",
										msg : $.i18n.prop("operationSus"),
										buttons : [{
											title : $.i18n.prop("determineBtn"),
											color : "red",
											click : function() {
											}
										}]
									}
									$.alertView(jsons);
									initFileList(filePath)
								},
								error : function() {
									layer.alert("覆盖操作失败！");
								}
							})
						}
					}]
				}
				$.alertView(json);
			}else{
				layer.alert($.i18n.prop("alertMsg4"));
				initFileList(filePath)
			}
		},
		complete : function() {
			$("#LoadingPng").hide();
		},
		error : function(data) {
			layer.alert("服务器异常，请联系管理员！");
		}
	});
}
</script>
