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
<link rel="stylesheet" href="css/cms-flow-manage-look.css" />
<link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
<link rel="stylesheet" href="css/cms-parts.css"/>
<link rel="stylesheet" href="js-tree/dist/themes/default/style.css">
 <script type="text/javascript" src="js-tree/dist/jstree.min.js"></script>
<!-- ZTree样式引入 -->
<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css"
	type="text/css" />
<link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css"
	type="text/css" />
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
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
							href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a><fmt:message  key="menuManager"/></a>><a href="pages/workflowPage/cms-flow-manage.jsp"><fmt:message  key="flow-page"/></a>><a
							href="javaScript:void(0);" class="active"><fmt:message  key="flow-apply3"/></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="partsAddTable-box">
					<div class="tabletitleBox">
					        <div class="liuchengTitle tabletitle left"><b></b><span><fmt:message  key="sqxx"/></span></div>
					        <div class="right"><a class="btn btn-danger flowPicBtn"><span class="glyphicon glyphicon-picture"></span>&nbsp;<fmt:message  key="flow-png"/></a></div>
					   </div>
						<table class="table table-striped table-bordered partsAddTable shenqingTable"
							id="partDataTable">
							<tbody id="partDataTbody">
							</tbody>
							<tr class="Liucheng" id="typeOptionId">							     
							      <td colspan="2" class="aboutLiucheng"><fmt:message  key="ryspgx"/></td>
								<td colspan="3" id=""><select name="typeOption"
									id="typeOption" class="form-control" disabled>
										<option value="or">or</option>
										<option value="and">and</option>
								</select></td>
							</tr>
<!-- 							<tr>							       -->
<%-- 							      <td colspan="2"class="aboutLiucheng"><fmt:message  key="xzzxr"/></td> --%>
<!-- 							      <td colspan="3" id=""><input -->
<!-- 									class="form-control workMen" id="taskPerson" disabled/></td> -->
<!-- 							</tr> -->
						</tbody>
						</table>
						<div class="liuchengTitle-box">
                        <div class="liuchengTitle left" id="liuchengTitle"><b></b><span><fmt:message  key="flow-png"/></span></div>
						<div class="btns right">
						    <a
								class="btn btn-xs" href="javascript:history.go(-1);"><span
								class="glyphicon glyphicon-repeat"></span> <fmt:message  key="goback"/></a>
						</div>
						</div>
					</div>
				    <div class="flow-manageAddRight">
				        <div id = "flowPng">
				                
				        </div>
         </div>
				</div>
			</div>
			<!-- 元器件添加数据手册选择页面 -->
			<div class="parts-producthand-window">
				<div class="parts-producthand-window-header bg-success">
					<a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="select_datasheet"/></a>
					<button type="button" class="close parts-producthand-window-close"
						aria-label="Close">
						<span>&times;</span>
					</button>
				</div>
				<div class="productmix-treeBox1">
					<ul id="datasheetTree" class="newztree"></ul>
				</div>
				<table class="parts-producthand-window-table">
					<tr>
						<td colspan="2"><a class="btn btn-danger left"
							onclick="saveSelectDatasheet()"><fmt:message  key="saveBtn"/></a></td>
					</tr>
				</table>
			</div>
			<!-- 任务执行人用户选择页面 -->
			<div class="flow-setting-nodeUser-choose">
    <div class="flow-setting-nodeUser-choose-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="xzzxr"/></a>
        <button type="button" class="close flow-setting-nodeUser-choose-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <input type="text" class="form-control" />
        <a class="btn btn-warning"><span class="glyphicon glyphicon-search"></span>  <fmt:message  key="serachBtn"/></a>
    </div>
    <div class="flow-setting-nodeUser-choose-body">
        <div class="nodeUser-choose-tree">
            <div id="jstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="j1_2" aria-busy="false">
               <ul id="userTree" class="ztree"></ul>
            </div>
        </div>
        <div class="choose-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td class="choose-table-input"><input type="checkbox" id="checkAlluser" onclick="checkAlluser()"/></td>
                    <td>账号</td>
                    <td>姓名</td>
                </tr>
                </thead>
                <tbody id="show-user">
                
                </tbody>
            </table>
        </div>      
        <div class="clear"></div>
        <div class="table-footer">
           <div id="Pagination" class="pagination"></div>
        </div>
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-warning" href="javascript:closeDialog('flow-setting-nodeUser-choose')"><span class="glyphicon glyphicon-remove-circle"></span>  取消</a>
            <a class="btn btn-info" href="javascript:userChooseFromPartApply()"><span class="glyphicon glyphicon-ok-circle"></span>  确定</a>
        </div>
    </div>
</div> 
<!-- 上传原理图符号弹窗 -->
   <div class="flow-updateSymble-look">
    <div class="flow-updateSymble-look-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="lookSymbol"/></a>
        <button type="button" class="close flow-updateSymble-look-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-updateSymble-look-body">
        <div class="choose-box">
         <div class="left showSymbol"></div>
             <div class="right showSymbol"></div>
            <div class="clear"></div>
             <div class="table-footers">
           <div id="Pagination" class="pagination"></div>
        </div>
        </div>             
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" href=""><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" href=""><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
    </div>
</div> 

<!-- 上传原理图符号弹窗 -->
<%@include file="/uploadDia.jsp"%>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/workFlowJavascript.js"></script>
	<script type="text/javascript" src="scripts/partTree.js"></script>
	<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
	<script type="text/javascript">
		$(function(){
			initpartApplyLookPage();
			processPngLook();
			$('.parts-images img').zoomify();
			 $('.flow-setting-nodeUser-choose,.parts-producthand-window,.flow-updateSymble-look').draggable();
		})
	</script>
</body>
</html>