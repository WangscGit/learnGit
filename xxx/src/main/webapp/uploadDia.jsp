<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/flow-updateSymble-upload.css" />
<!-- 上传弹窗 -->
   <div class="flow-updateSymble-upload">
    <div class="flow-updateSymble-upload-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="applySymbol"/></a>
        <button type="button" onclick="closeDialog('flow-updateSymble-upload')" class="close flow-updateSymble-upload-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header3">
       <div class="chooseFile">
      <span><fmt:message  key="xzkwj"/>:</span>
        <select class="form-control" id="selectFile1">
        <option>-no value-</option>
        </select>
       </div>
      <div class="uploadFile">
     	<form id="batchUploadDatesheetFileForm1"  method="post"  enctype="multipart/form-data">		
			<a class="inputBox-end right"><input id="uploadFileBtn1" name="dateSheetUpload" class="right uploadFileBtn1"  onchange="batchUploadFile1();"  type="file"   multiple/><fmt:message  key="wjsc"/></a>	
	 	</form>		
	 </div>
    </div>
    <div class="flow-updateSymble-body">
        <div class="choose-box">
             <div class="left showSymbol"></div>
             <div class="right showSymbol"></div>
            <div class="clear"></div>
             <div class="table-footers">
           <div id="Pagination1" class="pagination"></div>
        </div>
        </div>             
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" href="javascript:closeDialog('flow-updateSymble-upload')"><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="clickDetermineBtn()"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>

<script type="text/javascript">
/**上传按钮，弹出弹窗
 */
var sId="";
function initUploadDia(spanId){
	$("#uploadFileBtn1").val('');
	sId=spanId;
	var path="";
	if(spanId.indexOf('welding_library')!=-1){
		path="cms_server/Design/Footprint/Pad";
	}
	if(spanId.indexOf('package_symbols')!=-1){
		path="cms_server/Design/Footprint/FPT";
	}
	if(spanId.indexOf('step_model')!=-1){
		path="cms_server/Design/STEPModel";
	}
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
				if(name == latestVal.name){
					html += '<option value=' + name + ' selected>' + name
						+ '</option>';
				}else{
				    html += '<option value=' + name + '>' + name
						+ '</option>';
				}
			}
			$('#selectFile1').html(html);
			$(".flow-updateSymble-upload").show();
		},
		error : function() {
			layer.alert("服务器异常，请联系管理员！");
		}
	});
}

/**
 * 
 */
function clickDetermineBtn() {
	$("#"+sId).html($('#selectFile1').val());
	$(".flow-updateSymble-upload").hide();
}
/**
 * 异步上传文件
 */
function batchUploadFile1() {
	var formData = new FormData($("#batchUploadDatesheetFileForm1")[0]);
	if(sId.indexOf('welding_library')!=-1){
		if(!$("#uploadFileBtn1").val().endsWith(".pad")){
			layer.alert($.i18n.prop("padalert"));
			return;
		}
		formData.append("uploadpath","cms_server/Design/Footprint/Pad");
	}
	if(sId.indexOf('package_symbols')!=-1){
		if(!$("#uploadFileBtn1").val().endsWith(".dra")){
			layer.alert($.i18n.prop("draalert"));
			return;
		}
		formData.append("uploadpath","cms_server/Design/Footprint/FPT");
	}
	if(sId.indexOf('step_model')!=-1){
		if(!$("#uploadFileBtn1").val().endsWith(".stp")){
			layer.alert($.i18n.prop("stpalert"));
			return;
		}
		formData.append("uploadpath","cms_server/Design/STEPModel");
	}
	$.ajax({
		url : 'fileUpload/batchUploadFile.do',
		type : 'POST',
		dataType : 'json', // 接受数据格式
		data : formData,
		async : false,
		cache : false,
		contentType : false,
		processData : false,
		success : function(data) {
			$("#uploadFileBtn1").val('');
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
									initUploadDia(sId);//刷新弹窗
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
									initUploadDia(sId);//刷新弹窗
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
				initUploadDia(sId);//刷新弹窗
			}
		},
		error : function(data) {
			alert(data);
		}
	});
}
$(function() {
	$('.flow-updateSymble-upload').draggable();
});
</script>
