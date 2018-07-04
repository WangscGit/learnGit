<%@ page language="java" pageEncoding="utf-8"%>
<%
	String tempPartMark = request.getParameter("tempPartMark");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>宇航元器件选用平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<%@include file="/base.jsp"%>
<link rel="stylesheet" href="css/cms-headerPublic.css" />
<link rel="stylesheet" href="css/cms-flow-manage-edit.css" />
<link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
<link rel="stylesheet" href="css/cms-parts.css" />
<link rel="stylesheet" href="js-tree/dist/themes/default/style.css">
<script type="text/javascript" src="js-tree/dist/jstree.min.js"></script>
<!-- ZTree样式引入 -->
<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css"
	type="text/css" />
<link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css"
	type="text/css" />
<!-- <link rel="stylesheet" href="zTreeStyle/zTreeStylepartCheck.css" type="text/css"/> -->
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
<!-- 批量上传 -->
<link href="pages/staticpage/css/stream-v1.css" rel="stylesheet"
	type="text/css">
<!-- 弹出层 -->
<link rel="stylesheet" href="uiAlertView/css/uiAlertView-1.0.0.css"
	type="text/css">
<script type="text/javascript"
	src="uiAlertView/js/jquery.uiAlertView-1.0.0.js"></script>
</head>
<body>
	<%@include file="/public.jsp"%>
	<div class="containerAll">
		<div class="containerAllPage">
			<!---------表格---------->
			<div id="main">
				<%@include file="/header.jsp"%>
				<div class="page-message">
					<h3>
						<span class="label label-default">Now</span> <a
							href="pages/loginpage/index.jsp"><fmt:message key="homePage" /></a>><a><fmt:message
								key="menuManager" /></a>><a
							href="pages/workflowPage/cms-flow-manage.jsp"><fmt:message
								key="flow-page" /></a>><a href="javaScript:void(0);" class="active"><fmt:message
								key="flow-apply2" /></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="partsAddTable-box">
						<div class="tabletitleBox">
							<div class="liuchengTitle tabletitle left">
								<b></b><span><fmt:message key="sqxx" /></span>
							</div>
							<div class="right">
								<a class="btn btn-danger flowPicBtn"><span
									class="glyphicon glyphicon-picture"></span>&nbsp;<fmt:message
										key="flow-png" /></a>
							</div>
						</div>
						<table
							class="table table-striped table-bordered partsAddTable shenqingTable"
							id="partDataTable">
							<tbody id="partDataTbody">
							</tbody>
							<tr class="Liucheng">
								<td colspan="2" class="aboutLiucheng"><fmt:message
										key="ryspgx" /></td>
								<td colspan="3" id=""><select name="typeOption"
									id="typeOption" class="form-control">
										<option value="or">or</option>
										<option value="and">and</option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2" class="aboutLiucheng"><span
									style="color: red;">*</span>
								<fmt:message key="xzzxr" /></td>
								<td colspan="3" id=""><input class="form-control workMen"
									id="taskPerson" style="display: none" /> <input
									class="form-control workMen" id="taskPersonName"
									onclick="initUserList('','taskPersonName','taskPerson','0')" />
								</td>
							</tr>
							</tbody>
						</table>
						<div class="liuchengTitle-box">
							<div class="liuchengTitle left" id="liuchengTitle">
								<b></b><span><fmt:message key="flow-png" /></span>
							</div>
							<div class="btns right">
								<a class="btn btn-xs" onclick="savepartApplyPage('2');"><span
									class="glyphicon glyphicon-floppy-saved"></span> <fmt:message
										key="saveBtn" /></a> <a class="btn btn-xs"
									onclick="savepartApplyPage('4');"><span
									class="glyphicon glyphicon-save"></span> <fmt:message
										key="summitBtn" /></a> <a class="btn btn-xs"
									href="javascript:history.go(-1);"><span
									class="glyphicon glyphicon-repeat"></span> <fmt:message
										key="goback" /></a>
							</div>
						</div>
					</div>
					<div class="flow-manageAddRight">
						<div id="flowPng"></div>
					</div>
				</div>
			</div>
			<!-- 元器件添加数据手册选择页面 -->
			<div class="parts-producthand-window"></div>
			<!-- 上传原理图符号弹窗 -->
			<div class="flow-updateSymble-edit">
				<div class="flow-updateSymble-edit-header bg-gray">
					<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
							key="applySymbol" /></a>
					<button type="button" class="close flow-updateSymble-edit-close"
						aria-label="Close">
						<span>&times;</span>
					</button>
				</div>
				<div class="choose-header2 choose-header3">
					<div class="chooseFile">
						<span><fmt:message key="xzkwj" />:</span> <select
							class="form-control" id="selectFile">
							<option>-no value-</option>
						</select>
					</div>
					<div class="uploadFile">
						<a class="uploadFileBtnBox" onclick="symbolUpload('0')"><input
							type="text" id="uploadFileBtnEdit" />
						<fmt:message key="wjsc" /></a>
					</div>
				</div>
				<div class="flow-updateSymble-edit-body">
					<div class="choose-box">
						<div class="left showSymbol"></div>
						<div class="right showSymbol"></div>
						<div class="clear"></div>
						<div class="table-footers">
							<div id="Pagination" class="pagination"></div>
						</div>
					</div>
					<div class="nodeUser-choose-bottom">
						<a class="btn btn-gray"
							href="javascript:closeDialog('flow-updateSymble-edit')"><span
							class="glyphicon glyphicon-remove-circle"></span> <fmt:message
								key="resetBtn" /></a> <a class="btn btn-gray"
							onclick="determineSelect('0')"><span
							class="glyphicon glyphicon-ok-circle"></span> <fmt:message
								key="determine" /></a>
					</div>
				</div>
			</div>
			<!-- 文件上传弹窗 -->
			<div class="stream-uploadWindow">
				<div class="user-addWindow-header bg-gray">
					<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
							key="upload-file" /></a>
					<button type="button" class="close stream-uploadWindow-close"
						aria-label="Close">
						<span>&times;</span>
					</button>
				</div>
				<div id="i_select_files"></div>
				<div id="i_stream_files_queue"></div>
				<button class="btn btn-xs btn-gray"
					onclick="javascript:_t.upload();" id="uploadBtn">Upload</button>
				<br> Messages:
				<div id="i_stream_message_container" class="stream-main-upload-box"
					style="overflow: auto; height: 200px;"></div>
				<br>
			</div>
			<!-- 上传文件夹隐藏form -->
			<div class="uploadFolderForm" style="display: none">
				<form action="" method="post" name="uploadFolderForm"
					id="uploadFolderForm" enctype="multipart/form-data">
					<a class="inputBox-end right"><input name="uploadFolderInp"
						id="uploadFolderInp" type="file" webkitdirectory /></a>
				</form>
			</div>
			<!-- 上传原理图符号弹窗 -->
			<%@include file="/uploadDia.jsp"%>
			<!-- 用户选择弹窗 -->
			<%@include file="/UserChoose.jsp"%>
			<!---------可替代物料弹窗---------->
			<%@include file="/partDataDia.jsp"%>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/workFlowJavascript.js"></script>
	<script type="text/javascript" src="scripts/partTree.js"></script>
	<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
	<script type="text/javascript" src="scripts/symbolUpload.js"></script>
	<script type="text/javascript">
		$(function(){
			initpartApplyPage();//页面初始化
			processPngLook();//流程图加载
			createTree();//
			loadDatesheetDiv();//数据手册弹出框加载
			$('.parts-images img').zoomify();
			 $('.flow-setting-nodeUser-choose,.parts-producthand-window,.flow-updateSymble-edit,.flow-replaceChoose-edit,.stream-uploadWindow,.flow-replaceChoose').draggable();			
		})
// 		document.write("<script type='text/javascript' "    
<%-- 	    + "src='<%=request.getContextPath()%> --%>
// 		/pages/staticpage/js/stream-v1.js?"
// 						+ new Date() + "'></s" + "cript>");
	</script>
</body>
</html>