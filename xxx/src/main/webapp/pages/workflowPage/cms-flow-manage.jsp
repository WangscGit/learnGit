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
     <link rel="stylesheet" href="<%=path %>/scripts/jquery-ui-1.11.4/jquery-ui.theme.css">
    <link rel="stylesheet" href="<%=path %>/css/cms-flow-manage.css"/>
    <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
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
       <h3><span class="label label-default">Now</span><a>首页</a>><a>流程管理</a></h3>
    </div>
        <div class="table-responsive">
        <div class="flow-manage">
             <div class="left" id="treeDiv">
                    <ul id="flowTree" class="ztree"></ul>
                </div>
        </div>
        <div class="flow-manage-right">
   <div class="flow-manage-head">
   <div class="flow-manage-head-nav">
                <div class="btn1 left">
                <a class="btn btn-danger btn-xs flow-design-online"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="flowForm"/></a>
                <ul class="flow-manage-ul">
                       <li class="shenqing"><a><fmt:message  key="flow-apply1"/></a></li>
                </ul>
                </div>
                <div class="btn2 left">
                <a class="btn btn-danger btn-xs flow-design-setting" onclick="loadProcessInformation()"><span class="glyphicon glyphicon-download-alt"></span>  <fmt:message  key="reportDown"/></a>               
                </div>
            </div>
    <div class="flow-manage-head-input">
        <table class="manageTable table">
            <tr>
                <td>
                    <span><fmt:message  key="bt"/>：</span>
                    <input type="text" class="form-control" name="processName"/>
                </td>
                <td>
                    <span><fmt:message  key="flow-state"/>：</span>
                    <select name="processState" class="form-control">
                        <option value="">请选择</option>
                        <option value="未启动">未启动</option>
                        <option value="审批中">审批中</option>
                        <option value="审批通过">审批通过</option>
                    </select>
                </td>
                <td>
                    <span><fmt:message  key="lcfqr"/>：</span><input type="text" class="form-control" name="processTaskPerson"/>
                </td>
                <td rowspan="2">
                <a class="btn btn-danger flow-daiban-head-reset" onclick="resetManageSearch();"><span class="glyphicon glyphicon-repeat view-soso"></span> &nbsp;<fmt:message  key="ResetBotton"/></a>
                <a class="btn btn-danger flow-view-head-soso" onclick="manageSearch()"><span class="glyphicon glyphicon-search view-soso"></span>&nbsp;<fmt:message  key="cxbtn"/></a>
                </td>
            </tr>
            <tr>
               <td>
                    <span><fmt:message  key="processType"/>：</span>
                    <select id="formTypeFormManage" class="form-control">
                    </select>
                </td>
                <td>
                    <span><fmt:message  key="fqsj"/>：</span><input type="text" class="form-control" name="hiredate" readonly id="createTimeBegin"/>
                </td>
                <td>
                    <span><fmt:message  key="flow-createEnd"/>：</span><input type="text" class="form-control" name="hiredate" readonly id="createTimeFinish"/>
                </td>
                <td>
<!--                    <span>业务标识：</span><input type="text" class="form-control" name="item"/> -->
                </td>
            </tr>
        </table>
    </div>
</div>
<div class="flow-view-table-box">
    <table class="table table-bordered table-hover manage-table">
        <thead class="table-title">
        <tr class="bg-gray">
            <td><input type="checkbox" id="manageCheckAll" onclick="manageCheckAll()"/></td>
            <td><fmt:message  key="upload-seqno"/></td>
            <td><fmt:message  key="bt"/></td>
            <td><fmt:message  key="flow-name"/></td>
<%--             <td><fmt:message  key="rwmc"/></td> --%>
            <td><fmt:message  key="lcfqr"/></td>
            <td><fmt:message  key="fqsj"/></td>
<!--             <td>当前办理</td> -->
<!--             <td>超时时间</td> -->
            <td><fmt:message  key="user-state"/></td>
            <td class="handWork"><fmt:message  key="operationBtn"/></td>
        </tr>
        </thead>
        <tbody id="processManageList">
       
        </tbody>
    </table>
    <div class="table-footers">
               <div id="Pagination" class="pagination"></div>
     </div>
</div>
</div>
</div>
<!-- 过程弹窗 -->
<div class="flow-view-processWindow">
    <div class="flow-view-processWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="flow-his"/></a>
        <button type="button" class="close flow-view-processWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-view-processWindow-body">
        <div class="flow-view-processWindow-left">
            <table class="table table-striped table-hover table-bordered">
                 <thead class="table-title">
            <tr>        
            <td><fmt:message  key="upload-seqno"/></td>
            <td><fmt:message  key="rwmc"/></td>
            <td><fmt:message  key="user-createtime"/></td>
            <td><fmt:message  key="spjssj"/></td>
            <td><fmt:message  key="zxr"/></td>
            <td><fmt:message  key="spyj"/></td>
            </tr>
            </thead>
            <tbody id="hisActivitiHtml">
            
            </tbody>
            </table>
        </div>     
    </div>
</div>
<!-- 流程图弹窗 -->
<div class="flow-view-processWindow1">
    <div class="flow-view-processWindow1-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="flow-png"/></a>
        <button type="button" class="close flow-view-processWindow1-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-view-processWindow-body">       
        <div class="flow-view-processWindow-picture" id="hisActivitiPng">
                
        </div>
    </div>
</div>
<!-- 催办弹窗 -->
<div class="flow-view-askWindow">
    <div class="flow-view-askWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="flow-Reminders"/></a>
        <button type="button" class="close flow-view-askWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <a><fmt:message  key="flow-Reminders-Person"/>：</a><input type="text" class="form-control" name="launchPerson" disabled/>
    </div>
    <div class="flow-view-askWindow-body">
        <div class="choose-table-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span><fmt:message  key="flow-Reminders-contend"/>：</span></td>
                    <td><input type="text" class="form-control ask-input" name="msgContent" value=""/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="tzfs"/>：</span></td>
                    <td>
                        <input type="checkbox" checked="checked" disabled/><fmt:message  key="nbxx"/>
                    </td>
                </tr>
            </table>
        </div>
        <div class="choose-bottom">
            <a class="btn btn-gray" onclick="closeDialog('flow-view-askWindow')"><span class="glyphicon glyphicon-remove-circle"></span>  <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" id="pressId"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>
<!-- 元器件名称选择页面 -->
			<div class="parts-applyChoose-window">
				<div class="parts-applyChoose-window-header bg-gray">
					<a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="flow-apply-before"/></a>
					<button type="button" class="close parts-applyChoose-window-close"
						aria-label="Close">
						<span>&times;</span>
					</button>
				</div>
				<div class="applyChooseNameTree">
					   <ul id="partTree" class="ztree"></ul>
				</div>
				<div class="applyChoose-btn"><a class="btn btn-gray"
							onclick="goPartApplyPage()" id="goPartApplyPage"><fmt:message  key="saveBtn"/></a></div>				
			</div>
<div class="flow-view-turnWindow">
    <div class="flow-view-turnWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  转办页面</a>
        <button type="button" class="close flow-view-turnWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-view-turnWindow-body">
        <div class="choose-table-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td>任务名称</td>
                    <td>开始时间</td>
                    <td>原办理人</td>
                    <td>办理人</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>总经理审批</td>
                    <td>2017-8-10</td>
                    <td>秋水</td>
                    <td><a class="btn btn-xs btn-warning turnChooseUser">用户选择</a></td>
                </tr>
                <tr>
                    <td>总经理审批</td>
                    <td>2017-8-10</td>
                    <td>秋水</td>
                    <td><a class="btn btn-xs btn-warning turnChooseUser">用户选择</a></td>
                </tr>
                <tr>
                    <td>总经理审批</td>
                    <td>2017-8-10</td>
                    <td>秋水</td>
                    <td><a class="btn btn-xs btn-warning turnChooseUser">用户选择</a></td>
                </tr>
                <tr>
                    <td>总经理审批</td>
                    <td>2017-8-10</td>
                    <td>秋水</td>
                    <td><a class="btn btn-xs btn-warning turnChooseUser">用户选择</a></td>
                </tr>
                <tr>
                    <td>总经理审批</td>
                    <td>2017-8-10</td>
                    <td>秋水</td>
                    <td><a class="btn btn-xs btn-warning turnChooseUser">用户选择</a></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="choose-bottom">
            <a class="btn btn-gray"><span class="glyphicon glyphicon-remove-circle"></span>  取消</a>
            <a class="btn btn-gray"><span class="glyphicon glyphicon-ok-circle"></span>  确定</a>
        </div>
    </div>
</div>
<div class="flow-setting-nodeUser-choose">
    <div class="flow-setting-nodeUser-choose-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  用户选择</a>
        <button type="button" class="close flow-setting-nodeUser-choose-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <input type="text" class="form-control"/>
        <a class="btn btn-warning"><span class="glyphicon glyphicon-search"></span>  搜索</a>
    </div>
    <div class="flow-setting-nodeUser-choose-body">
        <div class="nodeUser-choose-tree">
            <div id="jstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="j1_2" aria-busy="false">
                <ul class="jstree-container-ul jstree-children" role="group">
                    <li role="treeitem" aria-selected="false" aria-level="1" aria-labelledby="j1_1_anchor" aria-expanded="true" id="j1_1" class="jstree-node  jstree-open">
                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                        <a class="jstree-anchor" href="#" tabindex="-1" id="j1_1_anchor">
                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>电子设计中心
                        </a>
                        <ul role="group" class="jstree-children">
                            <li role="treeitem" data-jstree="{ &quot;selected&quot; : true }" aria-selected="true" aria-level="2" aria-labelledby="j1_2_anchor" id="j1_2" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor  jstree-clicked" href="#" tabindex="-1" id="j1_2_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>管理组
                                </a>
                            </li>
                            <li role="treeitem" aria-selected="false" aria-level="2" aria-labelledby="j1_3_anchor" id="j1_3" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor" href="#" tabindex="-1" id="j1_3_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>设计组
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="choose-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td class="choose-table-input"><input type="checkbox" id="checkAll" onclick=selectallbox(this) />
                    <td>账号</td>
                    <td>姓名</td>
                </tr>
                </thead>
                <tbody id="selectApprovePerson">
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>孙红雷</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄渤</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄磊</td>
                    <td>456</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>罗志祥</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr> 
                </tbody>
            </table>
        </div>      
        <div class="clear"></div>
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" href="javascript:resetDiv()"><span class="glyphicon glyphicon-remove-circle"></span>  取消</a>
            <a class="btn btn-gray" href="javascript:submitDiv()"><span class="glyphicon glyphicon-ok-circle"></span>  确定</a>
        </div>
    </div>
</div>
</div>
<%@include file="/footer.jsp"%>
</div>
	</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/partTree.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/workFlowJavascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/laydate/laydate.js"></script>
<script>
$(function(){
   /*  $("[name='hiredate']").datepicker(
            {dateFormat: "yy-mm-dd"}
    ); */
    laydate.render({
		  elem: '#createTimeBegin'
		});
    laydate.render({
		  elem: '#createTimeFinish'
		});
    flowTree();
    createProcessPartTree();
    initProcessManage();
    loadProcessCategory();
    loadPCFromManage();//表单申请类别加载
    $('.flow-view-processWindow,.flow-view-askWindow,.flow-view-turnWindow,.flow-setting-nodeUser-choose,.parts-applyChoose-window,.flow-view-processWindow1').draggable();
    $('#jstree1').jstree(); 
})
// 在线流程设计----类别加载
function loadPCFromManage() {
	$.ajax({
				url : "ProcessCategoryController/selectProcessCategoryList.do",
				dataType : "json",
				cache : false,
				type : "post",
				success : function(json) {
					var list = json.list;
					var html = '';
					var liHtml = '';
					if(null == list || list.length<=0){
						html += '<option value="">请选择</option>';
					}else{
					   html += '<option value="">请选择</option>';
					   for(var i=0;i<list.length;i++){
					   	var pc = list[i];
					   	var value = pc.categorySign == "partProcess" ? "0":pc.categorySign;
					     html += '<option value="'+value+'">'+pc.categoryName+'</option>';
					   }
					}
					$("#formTypeFormManage").html(html);
				},
				error : function() {
					layer.alert("数据异常！");
				}
			});
}
</script>
</body>
</html>