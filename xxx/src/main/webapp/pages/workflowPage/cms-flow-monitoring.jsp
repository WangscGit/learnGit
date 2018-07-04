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
    <link rel="stylesheet" href="css/cms-flow-monitoring.css"/>
    <link rel="stylesheet" href="css/cms-flow-shenPi.css"/>
   <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
    <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.theme.css">  
    <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
	 <script type="text/javascript" src="scripts/echarts.min.js"></script>
</head>
<body>
 <%@include file="/public.jsp" %>
 <!---------主体---------->
<div class="containerAll">
    <div class="containerAllPage">
<!---------表格---------->
<div id="main">
 <%@include file="/header.jsp" %>
    <div class="page-message">       <h3><span class="label label-default">Now</span><a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a class="active">流程监控</a></h3>
    </div>
    <div class="table-responsive">
    <div class="flow-monitoring">
             <div class="left" id="treeDiv">
                    <ul id="flowTree" class="ztree"></ul>
                </div>
        </div>
        <div class="flow-monitoring-right">
        <div class="flow-design-head">
            <div class="flow-view-head-input">
        <table class="manageTable table">
            <tr>
                <td>
                    <span><fmt:message  key="fqsj"/>：</span>
                    <input id="startTimeBegin" type="text" class="form-control" name="hiredate" readonly/>
                </td>
                <td>
                    <span><fmt:message  key="flow-createEnd"/>：</span>
                    <input id="startTimeEnd" type="text" class="form-control" name="hiredate" readonly/>
                </td>
                <td class="chooseUser">
                <span><fmt:message  key="lcfqr"/>：</span>
                	<a class="btn btn-xs btn-danger flow-daiban-chooseBtn left" onclick="initOneUserList('','','startUser')"><fmt:message  key="selectBtn1"/></a>
                    <span id="startUser"   style="display:none"></span><span id="startUserShow" class="chooseUserBox"  ></span>
                </td>
                <td class="jiankongBtns">
                	<a class="btn btn-danger flow-view-head-soso" onclick="processHis();"><span class="glyphicon glyphicon-search view-soso"></span>&nbsp;<fmt:message  key="cxbtn"/></a>                	<a class="btn btn-danger flow-daiban-head-reset" onclick="resetjiankongSearch();"><span class="glyphicon glyphicon-repeat view-soso"></span>  <fmt:message  key="ResetBotton"/></a>
                </td>
            </tr>
<!--             <tr> -->
<!--                 <td> -->
<%--                     <span><fmt:message  key="zhclsj"/>：</span><input id="lastTimeBegin" type="text" class="form-control" name="hiredate" readonly/> --%>
<!--                 </td> -->
<!--                 <td> -->
<%--                     <span><fmt:message  key="flow-createEnd"/>：</span><input id="lastTimeEnd" type="text" class="form-control" name="hiredate" readonly/> --%>
<!--                 </td> -->
<!--                 <td class="chooseUser"> -->
<%--                 	<a class="btn btn-xs btn-danger flow-daiban-chooseBtn" onclick="initOneUserList('','','lastUser')"><fmt:message  key="selectBtn1"/></a> --%>
<%--                     <span><fmt:message  key="zhclr"/>：</span><span id="lastUser" style="display:none"></span><span id="lastUserShow" class="chooseUserBox"></span> --%>
<!--                 </td> -->
<!--             </tr> -->
        </table>
    </div>
        </div>
        <table class="table table-bordered table-hover monitoring-table">
            <thead class="table-title">
            <tr class="bg-gray">
            <td><fmt:message  key="bt"/></td>
            <td><fmt:message  key="spzt"/></td>
            <td><fmt:message  key="lcfqr"/></td>
            <td><fmt:message  key="fqsj"/></td>
<%--             <td><fmt:message  key="zhclr"/></td> --%>
<%--             <td><fmt:message  key="zhclsj"/></td> --%>
            </tr>
            </thead>
            <tbody id="processHis">
            
            </tbody>
        </table>
        <div class="table-footers">
               <div id="jiankongPagination" class="pagination"></div>
     </div>            
        </div>
        <div class="clear"></div>
        <div class="monitoringChart">
        <div id="Chart1" style="width: 500px;height:400px;"></div>
        <div style="width: 500px;height:430px;" class="Chart2Box">
         <div id="Chart2" style="width: 500px;height:400px;"></div>
         </div>
        </div>   
    </div>
<div class="shenPi-agree-box-chooseUser SPBox">
    <div class="shenPi-agree-box-chooseUser-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="selectUser"/></a>
        <button type="button" class="close shenPi-agree-box-chooseUser-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <input type="text" class="form-control" id="searchText" placeholder=<fmt:message  key="placeholder-loginname"/>></input>
        <a class="btn btn-danger" onclick="searchOneUser()"><span class="glyphicon glyphicon-search"></span>  <fmt:message  key="serachBtn"/></a>
    </div>
    <div class="shenPi-agree-box-chooseUser-body">
        <div class="nodeUser-choose-tree">
            <div id="ejstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="ej1_2" aria-busy="false">
                <ul id="userTree" class="ztree"></ul>
            </div>
        </div>
        <div class="choose-table-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td class="choose-table-input"></td>
                    <td><fmt:message  key="name"/></td>
                    <td><fmt:message  key="user-accord"/></td>
                </tr>
                </thead>
                <tbody id="show-user">
                </tbody>
            </table>
        </div>
        <div class="table-footer">
           <div id="Pagination" class="pagination"></div>
        </div>
        <div class="choose-bottom">
            <a class="btn btn-gray" onclick="closeProcessSelectUsers()"><span class="glyphicon glyphicon-remove-circle"></span>  <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="processOneSelectUser()"><span class="glyphicon glyphicon-ok-circle"></span>  <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>
</div>

<!---------页尾---------->
<%@include file="/footer.jsp" %>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/workFlowJavascript.js"></script>
<script type="text/javascript" src="scripts/laydate/laydate.js"></script>
</body>
<script>
/* $("[name='hiredate']").datepicker(
	{dateFormat: "yy-mm-dd"}
); */
$(function(){
	laydate.render({
		  elem: '#startTimeBegin'
		});
	laydate.render({
		  elem: '#startTimeEnd'
		});
	laydate.render({
		  elem: '#lastTimeBegin'
		});
	laydate.render({
		  elem: '#lastTimeEnd'
		});
	processHis();//初始化流程数据列表
	flowTree();
	countTaskByUser();//统计图
	initOnegroupUserTree();//加载成员树
    $('.flow-design-window,.shenPi-agree-box-chooseUser').draggable();
})


</script>
</body>
</html>