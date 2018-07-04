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
    <link rel="stylesheet" href="<%=path %>/css/cms-flow-manage-add.css"/>
</head>
<body>
<%@include file="/public.jsp" %>
	<div class="containerAll">
		<div class="containerAllPage">
		<div id="main">
		<%@include file="/header.jsp"%>
		<div class="page-message">
	<h3><span class="label label-default">Now</span><a  href="pages/loginpage/index.jsp">首页</a>><a  href="pages/workflowPage/cms-flow.jsp">流程管理</a>><a href="pages/workflowPage/cms-flow-manage-add.jsp">元器件申请</a></h3>
</div>
  <div class="table-responsive">
        <div class="flow-manageAddLeft left">
                <div class="flow-manageAddLeft-head">元器件申请单</div>
                 <table class="table table-striped table-hover table-bordered manageAddtable">               
                <tbody>
                <tr>
                    <td><span>字段一：</span></td>
                    <td><input type="text" class="form-control"/></td>
                </tr>
               <tr>
                    <td><span>字段二：</span></td>
                    <td><input type="text" class="form-control"/></td>
                </tr>
                <tr>
                    <td><span>备注：</span></td>
                    <td><input type="text" class="form-control"/></td>
                </tr>
               <tr>
                    <td><span>下一步任务：</span></td>
                    <td><input type="radio" class=""/><label>入库确认</label></td>
                </tr>
                <tr>
                    <td><span>任务执行人：</span></td>
                    <td><input type="text" class="form-control"/></td>
                </tr>
                </tbody>
            </table>
            <div class="manageaddBtns">
                    <a class="btn btn-xs"><span class="glyphicon glyphicon-floppy-saved"></span>  暂存</a>
                    <a class="btn btn-xs"><span class="glyphicon glyphicon-ok-circle"></span>  提交</a>
                    <a class="btn btn-xs"><span class="glyphicon glyphicon-remove-sign"></span>  取消</a>
             </div>
        </div>
</div>
</div>
<%@include file="/footer.jsp"%>
</div>
	</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script>
    $(function () {
        
    });
</script>
</body>
</html>