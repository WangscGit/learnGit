<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>宇航元器件选用平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<%@include file="/base.jsp"%>
<link rel="stylesheet" href="css/cms-headerPublic.css" />
<link rel="stylesheet" href="css/cms-flow-shenPi.css" />
<!-- ZTree样式引入 -->
<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css"
	type="text/css" />
<link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css"
	type="text/css" />
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
			<div id="main">
				<%@include file="/header.jsp"%>
				<div class="page-message">
					<h3>
						<span class="label label-default">Now</span><a
							href="pages/loginpage/index.jsp"><fmt:message key="homePage" /></a>><a><fmt:message
								key="menuManager" /></a>><a href="pages/workflowPage/cms-flow.jsp"><fmt:message
								key="flow-page" /></a>><a
							href="pages/workflowPage/cms-flow-daiBan.jsp"><fmt:message
								key="flow-doing" /></a>><a class="active"><fmt:message
								key="flow-shenpi" /></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="flow-shenPi-left left">
						<a class="btn btn-danger shenpi-agree-btn" onclick="agreeDia();"><span
							class="glyphicon glyphicon-ok"></span>
						<fmt:message key="ty" /></a> <a
							class="btn btn-danger shenpi-back-btn" id="disagreeBtn"
							style="display: none"><span
							class="glyphicon glyphicon-arrow-right"></span>
						<fmt:message key="bty" /></a> <a
							class="btn btn-danger shenpi-toother-btn"><span
							class="glyphicon glyphicon-arrow-right"></span>
						<fmt:message key="zb" /></a> <a
							class="btn btn-danger shenpi-save-btn"
							onclick="savepartDataFromShenpiPage();"><span
							class="glyphicon glyphicon-floppy-disk"></span>
						<fmt:message key="saveBtn" /></a> <a
							class="btn btn-danger shenpi-send-btn"><span
							class="glyphicon glyphicon-share"></span> <fmt:message key="cs" /></a>
						<a class="btn btn-danger shenpi-flow-btn"><span
							class="glyphicon glyphicon-bookmark"></span> <fmt:message
								key="lct" /></a> <a class="btn btn-danger shenpi-history-btn"
							onclick="getTaskHistory();"><span
							class="glyphicon glyphicon-info-sign"></span> <fmt:message
								key="spls" /></a>
						<!-- <a class="btn shenpi-turn-btn"><span class="glyphicon glyphicon-retweet"></span>  发起流转</a>
    <a class="btn shenpi-help-btn"><span class="glyphicon glyphicon-sort"></span>  转办代办</a> -->
					</div>
					<div class="flow-shenPi-table-box right">
						<table
							class="table table-striped table-bordered partsAddTable shenqingTable"
							id="partDataTable">
						</table>
					</div>
				</div>
				<!--  同意弹窗-->
				<div class="shenPi-agree-box SPBox">
					<div class="shenPi-agree-box-header bg-gray">
						<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
								key="sprw" /></a>
						<button type="button" class="close shenPi-agree-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<table class="table table-bordered">
						<tr id="jumpTr">
							<td><span><fmt:message key="tzlx" />：</span></td>
							<td colspan="3" class="nomalJump"><input type="radio"
								name="jumpType" value="normal" onclick="changeTiaozhuanType1();"
								checked />正常跳转 <b><input type="radio" name="jumpType"
									id="freeStyle" value="freeStyle"
									onclick="changeTiaozhuanType();" />自由跳转</b></td>
						</tr>
						<tr id="nodeSelectTr" style="display: none">
							<td><span><fmt:message key="mbjd" />：</span></td>
							<td><select name="nodeOption" id="nodeOption"
								class="form-control">
							</select></td>
						</tr>
						<tr style="display: none" id="typeTr">
							<td><span><fmt:message key="ryspgx" />：</span></td>
							<td><select name="typeOption" id="typeOption1"
								class="form-control">
									<option value="or">or</option>
									<option value="and">and</option>
							</select></td>
						</tr>
						<tr>
							<td><span><fmt:message key="xibzxr" />：</span></td>
							<td><a id="selectBtn"
								class="btn btn-danger btn-xs left shenPi-agree-box-chooseUser-btn"
								onclick="initUserList('','agreeSelectUser','loginAgreeSelectUser','0')"><span
									class="glyphicon glyphicon-user"></span> <fmt:message
										key="selectBtn1" /></a> <span id="agreeSelectUser"></span> <span
								id="loginAgreeSelectUser" style="display: none"></span></td>
						</tr>
						<tr>
							<td><span><fmt:message key="spyj" />：</span></td>
							<td colspan="3"><input type="text" id="aproveAdvice"
								name="aproveAdvice" class="form-control shenpi-input" /></td>
						</tr>
					</table>
					<div class="shenPi-agree-box-header2">
						<a class="btn btn-gray" href="javascript:closeShenPiAgreeBox()"><span
							class="glyphicon glyphicon-repeat"></span> <fmt:message
								key="resetBtn" /></a> <a class="btn btn-gray"
							href="javascript:makeShenPiAgreeBox('agree')"><span
							class="glyphicon glyphicon-ok-circle"></span> <fmt:message
								key="determine" /></a>
					</div>
				</div>
				<!-- 打回弹窗 -->
				<div class="shenPi-back-box SPBox">
					<div class="shenPi-back-box-header bg-gray">
						<a><span class="glyphicon glyphicon-tasks"></span> 打回</a>
						<button type="button" class="close shenPi-back-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<table class="table table-bordered">
						<tr>
							<td><span><fmt:message key="spyj" />：</span></td>
							<td colspan="3"><input type="text" id="disAgreeAdvice"
								name="disAgreeAdvice" class="form-control shenpi-input" /></td>
						</tr>
					</table>
					<div class="shenPi-back-box-header2">
						<a class="btn btn-gray" href="javascript:closeShenPiAgreeBox()"><span
							class="glyphicon glyphicon-repeat"></span> <fmt:message
								key="resetBtn" /></a> <a class="btn btn-gray"
							href="javascript:makeShenPiAgreeBox('disagree')"><span
							class="glyphicon glyphicon-ok-circle"></span> <fmt:message
								key="determine" /></a>
					</div>
				</div>
				<!--转办弹窗  -->
				<div class="shenPi-zhuanban-box SPBox">
					<div class="shenPi-zhuanban-box-header bg-gray">
						<a><span class="glyphicon glyphicon-tasks"></span>&nbsp;<fmt:message
								key="zbrw" /></a>
						<button type="button" class="close shenPi-zhuanban-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<table class="table table-bordered">
						<tr>
							<td class="zhuanbanUser"><span><fmt:message
										key="xibzxr" />：</span></td>
							<td><a
								class="btn btn-danger btn-xs left shenPi-agree-box-chooseUser-btn"
								onclick="initUserList('','agreeSelectUser1','loginAgreeSelectUser1','0')"><span
									class="glyphicon glyphicon-user"></span> <fmt:message
										key="selectBtn1" /></a> <span id="agreeSelectUser1"></span> <span
								id="loginAgreeSelectUser1" style="display: none"></span></td>
						</tr>
					</table>
					<div class="shenPi-zhuanban-box-header2">
						<a class="btn btn-gray" href="javascript:closeZhuanbanAgreeBox()"><span
							class="glyphicon glyphicon-repeat"></span> <fmt:message
								key="resetBtn" /></a> <a class="btn btn-gray"
							href="javascript:makeZhuanban()"><span
							class="glyphicon glyphicon-ok-circle"></span> <fmt:message
								key="determine" /></a>
					</div>
				</div>
				<!-- 元器件添加数据手册选择页面 -->
				<div class="parts-producthand-window">
					<div class="parts-producthand-window-header bg-gray">
						<a class="left"><span class="glyphicon glyphicon-tasks"></span>
						<fmt:message key="select_datasheet" /></a>
						<button type="button" class="close parts-producthand-window-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<div class="productmix-treeBox1-end">
						<ul id="datasheetTree" class="newztree"></ul>
					</div>
					<div class="producthand-btn left">
						<a class="btn btn-gray withshangchuan"
							onclick="selectDatesheetFromshenpi()"><fmt:message
								key="saveBtn" /></a>
						<div class="uploadDatesheetDiv">
							<form id="batchUploadDatesheetFileForm" action="" method="post"
								enctype="multipart/form-data">
								<a class="inputBox-end btn btn-gray right"><input
									id="dateSheetUpload" name="dateSheetUpload"
									onchange="batchUploadFile()" type="file" class="right" multiple />上传</a>
								<!-- 				<input type="file" id="dateSheetUpload" name="dateSheetUpload" multiple  style="filter:alpha(opacity=0);opacity:0;width: 0;height: 0;"/>  -->
							</form>
						</div>
					</div>
				</div>

				<!-- 流程图 -->
				<div class="shenPi-flow-box SPBox">
					<div class="shenPi-flow-box-header bg-gray" id="processPng">
						<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
								key="lct" /></a>
						<button type="button" class="close shenPi-flow-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<div class="flow-box-body" id="pngDiv"></div>
				</div>
				<div class="shenPi-history-box SPBox">
					<div class="shenPi-history-box-header bg-gray">
						<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
								key="spls" /></a>
						<button type="button" class="close shenPi-history-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<div class="shenpibox">
						<table class="table table-striped table-hover table-bordered">
							<thead class="table-title">
								<tr>
									<td><fmt:message key="upload-seqno" /></td>
									<td><fmt:message key="rwmc" /></td>
									<td><fmt:message key="user-createtime" /></td>
									<td><fmt:message key="spjssj" /></td>
									<td><fmt:message key="zxr" /></td>
									<td><fmt:message key="spyj" /></td>
								</tr>
							</thead>
							<tbody id="taskHistory">

							</tbody>
						</table>
					</div>
				</div>
				<!-- 抄送弹窗 -->
				<div class="shenPi-send-box SPBox">
					<div class="shenPi-send-box-header bg-gray">
						<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
								key="lccs" /></a>
						<button type="button" class="close shenPi-send-box-close"
							aria-label="Close">
							<span>&times;</span>
						</button>
					</div>
					<table class="table table-bordered">
						<tr>
							<td><span><fmt:message key="tzfs" />：</span></td>
							<td><input type="checkbox" checked="checked" disabled />
							<fmt:message key="nbxx" /></td>
						</tr>
						<tr>
							<td><span><fmt:message key="ry" />：</span></td>
							<td><a
								class="btn btn-danger btn-xs shenPi-send-box-chooseUser-btn"
								onclick="initUserList('','agreeSelectUser2','loginAgreeSelectUser2','0')"><span
									class="glyphicon glyphicon-user"></span> <fmt:message
										key="xzry" /></a> <span id="agreeSelectUser2"></span> <span
								id="loginAgreeSelectUser2" style="display: none"></span></td>
						</tr>
						<tr>
							<td><span><fmt:message key="yy" />：</span></td>
							<td><input type="text"
								class="form-control shenpi-input Input" name="processCC" /></td>
						</tr>
					</table>
					<div class="shenPi-send-box-header2">
						<a class="btn btn-gray" onclick="closeDialog('shenPi-send-box')"><span
							class="glyphicon glyphicon-repeat"></span> <fmt:message
								key="resetBtn" /></a> <a class="btn btn-gray"
							onclick="sendProcessCC()"><span
							class="glyphicon glyphicon-ok-circle"></span> <fmt:message
								key="determine" /></a>
					</div>
				</div>
			</div>
			<!-- 上传原理图符号弹窗 -->
			<div class="flow-updateSymble-end">
				<div class="flow-updateSymble-header-end bg-gray">
					<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
							key="applySymbol" /></a>
					<button type="button"
						onclick="closeDialog('flow-updateSymble-end')"
						class="close flow-updateSymble-close" aria-label="Close">
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
							type="text" id="uploadFileBtn" />
						<fmt:message key="wjsc" /></a>
					</div>
				</div>
				<div class="flow-updateSymble-body-end">
					<div class="choose-box-end">
						<div class="left showSymbol"></div>
						<div class="right showSymbol"></div>
						<div class="clear"></div>
						<div class="table-footers">
							<div id="Pagination" class="pagination"></div>
						</div>
					</div>
					<div class="nodeUser-choose-bottom">
						<a class="btn btn-gray"
							href="javascript:closeDialog('flow-updateSymble-end')"><span
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
			<!-- 上传capture原理图 -->
			<%@include file="/pages/publicPages/capture-uploadWindow.jsp"%>
			<!---------可替代物料弹窗---------->
			<%@include file="/partDataDia.jsp"%>
			<!-- 用户选择弹窗 -->
			<%@include file="/UserChoose.jsp"%>
			<!-- 上传弹窗 -->
			<%@include file="/uploadDia.jsp"%>

			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/workFlowJavascript.js"></script>
	<script type="text/javascript" src="scripts/partTree.js"></script>
	<script type="text/javascript" src="scripts/symbolUpload.js"></script>
	<script>
		$(function() {
			initSpBtn();//按钮初始化
			$(
					'.shenPi-agree-box,.shenPi-send-box,.shenPi-send-box-chooseUser,.shenPi-flow-box,.shenPi-history-box,.shenPi-turn-box,.shenPi-turn-box-chooseUser,.shenPi-zhuanban-box,.shenPi-back-box,.parts-producthand-window,.flow-updateSymble-end,.stream-uploadWindow,.capture-uploadWindow')
					.draggable();
			initPartPage();
		});
// 		document
// 				.write("<script type='text/javascript' "
// 						+ "src='
<%-- 	<%=request.getContextPath()%>/pages/staticpage/js/stream-v1.js?" + new Date()     --%>
// 			    + "'></s" + "cript>"); 
	</script>
</body>
</html>