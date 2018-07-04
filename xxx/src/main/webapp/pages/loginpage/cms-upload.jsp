<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="css/cms-headerPublic.css"/>
    <link rel="stylesheet" href="css/cms-user.css"/>
    <link rel="stylesheet" href="css/cms-upload.css"/>
    <link rel="stylesheet" href="uploadTree/uploadZTree.css" type="text/css">
    <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
    <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="js-tree/jquery.ztree.exedit.js"></script>
    <link rel="stylesheet" href="js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="js-tree/dist/jstree.min.js"></script>
    <!-- 批量上传 -->
    <link href="pages/staticpage/css/stream-v1.css" rel="stylesheet" type="text/css">
    <!-- 弹出层 -->
    <link rel="stylesheet" href="uiAlertView/css/uiAlertView-1.0.0.css" type="text/css">
    <script type="text/javascript" src="uiAlertView/js/jquery.uiAlertView-1.0.0.js"></script>
</head>
<body>
<%@include file="/public.jsp" %>
 <!---------主体---------->
<div class="containerAll">
    <div class="containerAllPage">
<!---------表格---------->
<div id="main">
<%@include file="/header.jsp" %>
    <div class="page-message">
        <h3><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a class="active"><fmt:message  key="upload-Page"/></a></h3>
    </div>
    <div class="table-responsive upload-main">
        <div id="jstree_div" class="left">
            <div class="zTreeDemoBackground left">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
        </div>      
     <div class="stream-uploadWindow">
        <div class="user-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="upload-file"/></a>
        <button type="button" class="close stream-uploadWindow-close" aria-label="Close"><span>&times;</span></button>
        </div>
     	<div id="i_select_files">
	    </div>
	    <div id="i_stream_files_queue">
	     </div>
	    <button class="btn btn-xs btn-danger loadBTN right" onclick="javascript:_t.upload();" id="uploadBtn">Upload</button>
<!-- 	    |<button onclick="javascript:_t.stop();">停止上传</button>|<button onclick="javascript:_t.cancel();">取消</button> -->
<!-- 	    |<button onclick="javascript:_t.disable();">禁用文件选择</button>|<button onclick="javascript:_t.enable();">启用文件选择</button> -->
	     <br>
	    Messages:
	    <div id="i_stream_message_container" class="stream-main-upload-box" style="overflow: auto;height:200px;">
	   </div>
<br>
 </div>
 <!-- 隐藏窗口：批量上传 -->
 <div class="batchUploadFilesDiv" style="display:none;">
 
 </div>
    <div class="left table-box">
     <div class="uploadBtn-box">
      <a class="btn btn-danger delete-btn right" href="javascript:deleteUploadFile()"><span class="glyphicon glyphicon-remove"></span><fmt:message  key="upload-del"/></a>
      <a class="btn btn-danger right design-process"><span class="glyphicon glyphicon-remove"></span>设计过程</a>
      <a class="btn btn-danger up-load right" href="javascript:uploadBefore()">
          <span class="glyphicon glyphicon-cloud"></span><fmt:message  key="upload-file"/>
      </a>
    <table class="table table-bordered table-hover upload-table right" id="mainTable">
        <thead class="table-title">
        <tr>
           <td><input type="checkbox" id="checkAll" onclick="checkAll()"></td>
            <td><fmt:message  key="upload-seqno"/></td>
            <td><fmt:message  key="upload-filename"/></td>
            <td><fmt:message  key="upload-filesize"/></td>
            <td><fmt:message  key="upload-user"/></td>
            <td><fmt:message  key="upload-time"/></td>
        </tr>
        </thead>
        <tbody id="fileHtml">
        </tbody>
    </table>
    <div class="clear"></div>
      <div class="table-footer right">
               <div id="Pagination" class="pagination"></div>
      </div>
    </div>
    <table class="table table-bordered table-hover upload-table right" id="uploadTable">
        <thead class="table-title">
        <tr>
            <td style="text-align:center;vertical-align:middle;">文件名</td>
            <td style="text-align:center;vertical-align:middle;"> 文件大小</td>
            <td style="text-align:center;vertical-align:middle;">上传中</td>
        </tr>
        </thead>
        <tbody id="uploadHtml">
        </tbody>
    </table>
    </div>
</div>
<!-- 上传文件夹隐藏form -->
<div class="uploadFolderForm" style="display:none">
     <form action="" method="post" name="uploadFolderForm" id="uploadFolderForm" enctype="multipart/form-data">
        <a class="inputBox-end right"><input name="uploadFolderInp" id="uploadFolderInp" type="file" webkitdirectory /></a>
     </form>
</div>  
<!---------页尾---------->
<%@include file="/footer.jsp" %>
</div>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/fileUpload.js"></script>
<script type="text/javascript" src="scripts/symbolUpload.js"></script>
<script>
$(function () {
	$('#jstree1').jstree();
	batchUploadFilesDiv();//批量上传DIV初始化
});
document.write("<script type='text/javascript' "    
	    + "src='<%=request.getContextPath()%>/pages/staticpage/js/stream-v1.js?" + new Date()    
	    + "'></s" + "cript>");
$(".design-process").click(function(){
	window.location.href = getContextPathForWS()+"/pages/design-processPage/cms-design-process.jsp";
})
</script>
</body>
</html>
