<%@ page language="java" pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
   <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/cms-message.css"/>
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
        <h3><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a class="active"><fmt:message  key="message-Page"/></a></h3>
    </div>
    <div class="table-responsive">
        <div class="message-parts">
            <div class="message-view left">
                <h4><fmt:message  key="messageView"/></h4>
                <span class="trangle"></span>
            </div>
            <div class="message-send left">
                <h4><fmt:message  key="messageRelease"/></h4>
                <span class="trangle" style="display:none"></span>
            </div>
        </div>
        <div class="message-view-box">
        <div class="message-view-btn">
            <a class="btn batch-btn btn-danger left" onclick="noReadMsg()"><span class="glyphicon glyphicon-folder-open"></span>  <fmt:message  key="messageNoRead"/></a>
            <a class="btn batch-btn btn-danger left" onclick="allMsg()"><span class="glyphicon glyphicon-comment"></span>  <fmt:message  key="messageAll"/></a>
            <span id="queryType" style="display:none"></span>
        </div>
        <table class="table table-bordered table-hover message-view-table">
            <thead class="table-title">
            <tr>
                <td><fmt:message  key="msgType"/></td>
                <td><fmt:message  key="msgTittle"/></td>
                <td><fmt:message  key="msgComment"/></td>
                <td><fmt:message  key="msgPriority"/></td>
                <td><fmt:message  key="msgSendpeople"/></td>
                <td><fmt:message  key="msgSendtime"/></td>
                <td id="readTime" style="display:none"><fmt:message  key="msgReadtime"/></td>
                <td id="readCheck"><fmt:message  key="msgWhetherread"/></td>
            </tr>
            </thead>
            <tbody id="messageList">
            
            </tbody>
        </table>
        </div>
    </div>
</div>
<!---------页尾---------->
<%@include file="/footer.jsp" %>
</div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/message.js"></script>
<script>
$(function(){
    $('.message-add-page,.message-edit-page,.message-more-page').draggable();
})
</script>
</body>
</html>