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
<link rel="stylesheet" href="css/cms-parts-edit.css" />
<link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
<link rel="stylesheet" href="css/cms-parts.css"/>
<!-- ZTree样式引入 -->
<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css"
	type="text/css" />
<link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css"
	type="text/css" />
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
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
							href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a
							href="javaScript:void(0);" class="active"><fmt:message  key="updatePartData"/></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="partsAddTable-box">
						<table class="table table-striped table-bordered partsAddTable"
							id="partDataTable">
						</table>

						<div class="btns right">
							<a class="btn btn-xs" onclick="saveFromPage('2');"><span
								class="glyphicon glyphicon-save"></span> <fmt:message  key="saveBtn"/></a> <a
								class="btn btn-xs" href="javascript:history.go(-1);"><span
								class="glyphicon glyphicon-repeat"></span> <fmt:message  key="goback"/></a>
						</div>
					</div>
				</div>
			</div>
			<!-- 元器件添加数据手册选择页面 -->
			<div class="parts-producthand-window">
				
			</div>
			<!-- 上传原理图符号弹窗 -->
   <div class="flow-updateSymble">
    <div class="flow-updateSymble-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="applySymbol"/></a>
        <button type="button" class="close flow-updateSymble-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2 choose-header3">
       <div class="chooseFile">
      <span><fmt:message  key="xzkwj"/>:</span>
        <select class="form-control" id="selectFile">
        <option>-no value-</option>
        </select>
       </div>
       <div class="uploadFile"><a class="uploadFileBtnBox" onclick="symbolUpload('0')"><input type="text" id="uploadFileBtn"/><fmt:message  key="wjsc"/></a></div>
    </div>
    <div class="flow-updateSymble-body">
        <div class="choose-box">
             <div class="left showSymbol"></div>
             <div class="right showSymbol"></div>
            <div class="clear"></div>
             <div class="table-footers">
           <div id="Pagination" class="pagination"></div>
        </div>
        </div>             
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" href="javascript:closeDialog('flow-updateSymble')"><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="determineSelect('0')"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
    </div>
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
			<!-- 上传弹窗 -->
			<%@include file="/uploadDia.jsp"%>
			<!---------可替代物料弹窗---------->
			<%@include file="/partDataDia.jsp"%>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/partTree.js"></script>
	<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
	<script type="text/javascript" src="scripts/symbolUpload.js"></script>
	<script type="text/javascript">
		$(function(){
			initEditPage();
			loadDatesheetDiv();//数据手册弹出框加载
			$('.parts-images img').zoomify();
			$(".parts-producthand-window").draggable();
		})
	</script>
</body>
</html>