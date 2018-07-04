<%@ page language="java" pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
   <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/cms-messageSend.css"/>
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
        <h3><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a><fmt:message  key="menuManager"/></a>><a class="active"><fmt:message  key="message-Page"/></a></h3>
    </div>
    <div class="table-responsive">
        <div class="message-parts">
            <div class="message-view left">
                <h4><fmt:message  key="messageView"/></h4>
                <span class="trangle"></span>
            </div>
            <div class="message-send left">
                <h4><fmt:message  key="messageRelease"/></h4>
                <span class="trangle"></span>
            </div>
        </div>
        <div class="message-send-box">
         <div class="message-view-btn">
            <a class="btn batch-btn btn-danger left message-add" onclick=""><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="msgAdd"/></a>
            <!-- <span id="queryType" style="display:none"></span> --> 
        </div>
            <table class="table table-bordered table-hover message-send-table">
                <thead class="table-title">
								<tr>
									<td><fmt:message key="msgType" /></td>
									<td><fmt:message key="msgTittle" /></td>
									<td class="messageword"><fmt:message key="msgComment" /></td>
									<td><fmt:message key="msgPriority" /></td>
									<td><fmt:message key="msgSendpeople" /></td>
									<td><fmt:message key="msgSendtime" /></td>
									<td><fmt:message key="modifyUser" /></td>
									<td><fmt:message key="user-optionMdftime" /></td>
									<td class="messageEdit"><fmt:message key="editorBtn" /></td>
								</tr>
							</thead>
                <tbody id="sendMsgpage">
                </tbody>
            </table>
        </div>
    </div>
</div>
<div class="message-add-page">
    <div class="message-edit-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message key="msgAdd" /></a>
        <button type="button" class="close message-add-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="message-edit-header2">
      <!--  <p><span class="glyphicon glyphicon-edit"></span>  添加消息</p> -->
        <table class="message-edit-table">
            <tr>
                <td><span><b>*</b><fmt:message key="msgTittle" />：</span></td>
                <td><input type="text" class="form-control" name="msgTittle"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgType" />：</span></td>
                <td><select name="msgType" id="" class="form-control">
                    <option value="通知公告">通知公告</option>
                    <option value="审核信息">审核信息</option>
                    <option value="系统提醒">系统提醒</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgPriority" />：</span></td>
                <td><select name="msgLevel" id=""  class="form-control">
                    <option value="一般">一般</option>
                    <option value="紧急">紧急</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgComment" />：</span></td>
                <td><input type="text" class="form-control" name="msgContent"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgReceiver" />：</span></td>
                <td>
                   <!--  <select multiple class="form-control" name="receiverPerson" id="userListAdd">
                    </select> -->
                    <div class="addTableBox">
                    <table class="table table-bordered table-hover addTable" id="userListAdd">
                    </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
        <div class="message-edit-table-btn">
            <a class="btn btn-gray message-add-sure" onclick="saveMsg()"><fmt:message key="determine" /></a>
            <a class="btn btn-gray message-add-cancel" onclick="closeDialog('message-add-page')"><fmt:message key="resetBtn" /></a>
        </div>
    </div>
</div>
<div class="message-more-page">
    <div class="message-more-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message key="msgLook" /></a>
        <button type="button" class="close message-more-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="message-more-header2">
        <!-- <p><span class="glyphicon glyphicon-edit"></span>  详细信息</p> -->
        <table class="message-more-table">
            <tr>
                <td><span><b>*</b><fmt:message key="msgTittle" />：</span></td>
                <td><input type="text" class="form-control" readonly name="msgTittleLook"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgType" />：</span></td>
                <td><select name="msgTypeLook" id="" class="form-control" disabled>
                    <option value="通知公告">通知公告</option>
                    <option value="审核信息">审核信息</option>
                    <option value="系统提醒">系统提醒</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgPriority" />：</span></td>
                <td><select name="msgLevelLook" id=""  class="form-control" disabled>
                    <option value="紧急">紧急</option>
                    <option value="一般">一般</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgComment" />：</span></td>
                <td><input type="text" class="form-control" readonly name="msgContentLook"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgReceiver" />：</span></td>
                <td>
                    <!-- <select multiple class="form-control" disabled id="userListLook">
                    </select> -->
                    <div class="moreTableBox">
                    <table class="table table-bordered table-hover moreTable" id="userListLook">
                          
                    </table>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="message-edit-page">
    <div class="message-edit-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message key="msgModify" /></a>
        <button type="button" class="close message-edit-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="message-edit-header2">
        <!-- <p><span class="glyphicon glyphicon-edit"></span>  修改消息</p> -->
        <table class="message-edit-table">
            <tr>
                <td><span><b>*</b><fmt:message key="msgTittle" />：</span></td>
                <td><input type="text" class="form-control" name = "msgTittleUpdate"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgType" />：</span></td>
                <td><select name="msgTypeUpdate" id="" class="form-control">
                    <option value="通知公告">通知公告</option>
                    <option value="审核信息">审核信息</option>
                    <option value="系统提醒">系统提醒</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgPriority" />：</span></td>
                <td><select name="msgLevelUpdate" id=""  class="form-control">
                    <option value="一般">一般</option>
                    <option value="紧急">紧急</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgComment" />：</span></td>
                <td><input type="text" class="form-control" name="msgContentUpdate"/></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="msgReceiver" />：</span></td>
                <td>
                   <!--  <select multiple class="form-control" name="receiverPersonUpdate" id="userListUpdate">
                    </select> -->
                    <div class="editTableBox">
                    <table class="table table-bordered table-hover editTable" id="userListUpdate">
                    </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
        <div class="message-edit-table-btn">
            <a class="btn btn-gray message-edit-sure" href="modifyMsg()" id="modifyBtn"><fmt:message key="determine" /></a>
            <a class="btn btn-gray message-edit-cancel"><fmt:message key="resetBtn" /></a>
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
	initsendMsgpage();//消息发布列表刷新
	receiverList();//接收人列表加载
    $('.message-add-page,.message-edit-page,.message-more-page').draggable();
})
</script>
</body>
</html>