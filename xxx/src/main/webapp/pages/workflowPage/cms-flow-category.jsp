<%@ page language="java"   pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
     <link rel="stylesheet" href="css/cms-headerPublic.css" />
    <link rel="stylesheet" href="<%=path %>/css/cms-flow-category.css"/>
     <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
</head>
<body>
<%@include file="/public.jsp" %>
	<div class="containerAll">
		<div class="containerAllPage">
		<div id="main">
		<%@include file="/header.jsp"%>
		<div class="page-message">
       <h3><span class="label label-default">Now</span><a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a class="active"><fmt:message  key="processType"/></a></h3>
    </div>
        <div class="table-responsive">
                <div class="flow-categoryTree">
             <div class="left" id="treeDiv">
                    <ul id="flowTree" class="ztree"></ul>
                </div>
        </div>
        <div class="flow-category">
       <div class="flow-categoryBtns">
            <a class="btn btn-xs btn-danger categoryAddBtn"><span class="glyphicon glyphicon-plus"></span>&nbsp;<fmt:message  key="flow-addCategory"/></a>           
            <a class="btn btn-xs btn-danger categoryDeleteBtn" onclick="batchDeleteCategoty()"><span class="glyphicon glyphicon-remove"></span>&nbsp;<fmt:message  key="deleteBtn"/></a>
     </div>
       <div class="flow-categoryTablebox">
       <table class="table table-bordered table-hover categoryTable">
        <thead class="table-title">
        <tr class="bg-gray">
            <td><input type="checkbox" id="selectAllCategory" onclick="selectAllCategory()"/></td>
            <td><fmt:message  key="upload-seqno"/></td>
            <td><fmt:message  key="flow-categoryName"/></td>
            <td><fmt:message  key="flow-categorySign"/></td>
            <td><fmt:message  key="createUser"/></td>
            <td><fmt:message  key="user-createtime"/></td>
            <td><fmt:message  key="remark"/></td>
            <td><fmt:message  key="operationBtn"/></td>
        </tr>
        </thead>
        <tbody id="initCategoryList">
        </tbody>
    </table>
    <div class="table-footers">
               <div id="Pagination" class="pagination"></div>
     </div>
</div>
</div>
</div>
<!-- 添加分类弹窗 -->
<div class="categoryAddWindow">
    <div class="categoryAddWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>&nbsp;<fmt:message  key="flow-addCategory"/></a>
        <button type="button" class="close categoryAddWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="categoryAddWindow-body">
            <table class="table table-bordered table-hover AddTable">
                    <tr>
                          <td><span><b>*</b><fmt:message  key="flow-categoryName"/>：</span></td>
                          <td><input type="text" class="form-control" name="categoryName"/></td>
                    </tr>
                    <tr>
                          <td><span><b>*</b><fmt:message  key="flow-categorySign"/>：</span></td>
                          <td><input type="text" class="form-control" name="categorySign"/></td>
                    </tr>
                    <tr>
                          <td><span><fmt:message  key="remark"/>：</span></td>
                          <td><input type="text" class="form-control" name="remark"/></td>
                    </tr>
            </table>
            <div class="bottomBtns">
            <a class="btn btn-xs btn-gray right categoryReturn" onclick="closeDialog('categoryAddWindow')"><span class="glyphicon glyphicon-arrow-left"></span>&nbsp;<fmt:message  key="resetBtn"/></a>
                   <a class="btn btn-xs btn-gray right categorySave" onclick="insertProcessCategory()"><span class="glyphicon glyphicon-save"></span>&nbsp;<fmt:message  key="saveBtn"/></a>   
            </div>
    </div>
</div>
<!-- 修改分类弹窗 -->
<div class="categoryEditWindow">
    <div class="categoryEditWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>&nbsp;<fmt:message  key="flow-editCategory"/></a>
        <button type="button" class="close categoryEditWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="categoryAddWindow-body">
        <table class="table table-bordered table-hover EditTable">
                    <tr>
                          <td><span><b>*</b><fmt:message  key="flow-categoryName"/>：</span></td>
                          <td style="display:none"><input type="text" class="form-control" name="CategoryId"/></td>
                          <td><input type="text" class="form-control" name="updateCategoryName"/></td>
                    </tr>
                    <tr>
                          <td><span><b>*</b><fmt:message  key="flow-categorySign"/>：</span></td>
                          <td><input type="text" class="form-control" readonly name="updateCategorySign"/></td>
                    </tr>
                    <tr>
                          <td><span><fmt:message  key="remark"/>：</span></td>
                          <td><input type="text" class="form-control" name="updateRemark"/></td>
                    </tr>
            </table>
            <div class="bottomBtns">
            <a class="btn btn-xs btn-gray right categoryReturn" onclick="closeDialog('categoryEditWindow')"><span class="glyphicon glyphicon-arrow-left"></span>&nbsp;<fmt:message  key="resetBtn"/></a>
                   <a class="btn btn-xs btn-gray right categorySave" onclick="updateProcessCategory()"><span class="glyphicon glyphicon-save"></span>&nbsp;<fmt:message  key="saveBtn"/></a>   
            </div>
    </div>
</div>
</div>
<%@include file="/footer.jsp"%>
</div>
	</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/workFlowJavascript.js"></script>
<script>
$(function(){
	$(".categoryAddBtn").click(function(){
		$(".categoryAddWindow").show();
	})
	$(".categoryAddWindow-close").click(function(){
		$(".categoryAddWindow").hide();
	})
		$(".categoryEditBtn").click(function(){
		$(".categoryEditWindow").show();
	})
	$(".categoryEditWindow-close").click(function(){
		$(".categoryEditWindow").hide();
	})
    $('.categoryAddWindow,.categoryEditWindow').draggable();
	flowTree();
	initCategoryList();//流程类别列表初始化
})
</script>
</body>
</html>