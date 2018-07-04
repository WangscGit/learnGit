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
 	<%@include file="/base.jsp" %>
    <link rel="stylesheet" href="css/cms-parts.css"/>   
	<!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
 	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
<!-- 	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepartCheck.css" type="text/css"/> -->
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
    <script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="/cms_cloudy/scripts/pdfobject.js"></script>
</head>
<body>
<%@include file="/public.jsp" %>
<div class="containerAll">
    <div class="containerAllPage">
        <!---------表格---------->
        <div id="main">
        <%@include file="/header.jsp" %>
            <div class="page-message">
                <h3 id="navigation"><span class="label label-default">Now</span>  <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a href="pages/parts/cms-parts.jsp"><fmt:message  key="ComponentNavigator"/></a>></h3>
            <div class="parts-search-btngroup" id="dataRightsBtn">
                      </div>
            </div>
           <div class="table-responsive" >
                <div class="leftsidebar_box clear left" id="treeDiv">
                    <ul id="partTree" class="ztree"></ul>
                </div>
                <div class="right-body left">
               
                    <div>
                        <div class="parts-choose left" id="manufacturerDiv" >
                        </div>
                        <div class="parts-choose left parts-choose1" id="keyComponentDiv">
                        </div>
                    </div>
                    <div class="parts-search">                   
                         <table class="table" id="dataTable"> 
                         </table>                     
                        <div class="table-footers">
                        	<div class="pagination" id="partDataPage"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
         <!-- 对比弹框 -->
        <div class="parts-compare-box">
              <div class="parts-compare-title">
                   <div class="title-left left">
                         <b><fmt:message  key="contrastColumn"/></b>
                   </div>
                  <div class="title-right left">
                         <a class="compare-hidden"><fmt:message  key="contrastHide"/></a>
                  </div>
              </div>
              <div class="parts-compare-body">
                  <div class="item-empty-box" id="compareDialog">
                  </div>
              </div>
            <div class="diff-operate">
                <a href="<%=path %>/pages/parts/cms-parts-compare.jsp?tempPartMark=<%=tempPartMark %>"  class="btn-compare-b"><fmt:message  key="contrastBtn"/></a>
                <a class="del-items" href ="javascript:clearCompare()"><fmt:message  key="contrastReset"/></a>
            </div>
        </div>
<!--         添加页面 -->
<!--         <div class="parts-add-window"> -->
<!--     <div class="parts-add-window-header bg-gray"> -->
<%--         <a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="addPartData"/></a> --%>
<!--         <button type="button" class="close parts-add-window-close" aria-label="Close"><span>&times;</span></button> -->
<!--     </div> -->
<!--        <div class="parts-add-window-table-box"> -->
<!--         <table class="parts-add-window-table" id="addPartDataTable"> -->
<!--         </table> -->
<!--        </div> -->
<!--        <table class="parts-add-window-table"> -->
<!--         	<tr> -->
<!--                 <td colspan="2"> -->
<%--                 <a class="btn btn-gray left" onclick="addPartData();"><fmt:message  key="saveBtn"/></a> --%>
<!--                 </td> -->
<!--             </tr> -->
<!--         </table> -->
<!-- </div> -->
<!--   修改页面 --> 
<!--         <div class="parts-change-window"> -->
<!--     <div class="parts-change-window-header bg-gray"> -->
<%--         <a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="updatePartData"/></a> --%>
<!--         <button type="button" class="close parts-change-window-close" aria-label="Close"><span>&times;</span></button> -->
<!--     </div> -->
<!--        <div class="parts-change-window-table-box"> -->
<!--         <table class="parts-change-window-table" id="updatePartDataTable"> -->
<!--         </table>      -->
<!--         </div> -->
<!--          <table class="parts-change-window-table"> -->
<!--             <tr> -->
<!--                 <td colspan="2"> -->
<%--                 <a class="btn btn-gray left" onclick="updatePartData();" ><fmt:message  key="saveBtn"/></a>              --%>
<!--                 </td> -->
<!--             </tr> -->
<!--         </table> -->
<!-- </div> -->
<!-- 导入页面 -->
        <div class="parts-into-window">
    <div class="parts-into-window-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="importPartData"/></a>
        <button type="button" class="close parts-into-window-close" onclick="closeExportDiv()" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="select-box">
        <p>ExcelSheet: </p>
        <select name='type' class='form-control'  id='addSelectSheet' onchange="changeSheet()"></select> 
    </div>       
   <div class="parts-into-window-table-box">
        <table class="parts-into-window-table table table-bordered" id="exportField">
        </table>     
        </div>
         <table class="parts-into-window-table">
            <tr>
                <td>
                <a class="btn btn-gray left" onclick="saveExportDatas();" id="saveExportDatas"><fmt:message  key="importBotton"/></a>             
                </td>
                <td>
                <a class="btn btn-gray left" onclick="closeExportDatas();" ><fmt:message  key="resetBtn"/></a>             
                </td>
            </tr>
        </table>
</div>

<!-- 添加到产品页面 -->
<!--  <div class="parts-product-window"> -->
<!--     <div class="parts-product-window-header bg-gray"> -->
<%--         <a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="minute-addPro"/></a> --%>
<!--         <button type="button" class="close parts-product-window-close" aria-label="Close"><span>&times;</span></button> -->
<!--     </div> -->
<!--        <div class="productmix-treeBox"> -->
<!--        	<ul id="bomtree" class="newztree"></ul> -->
<!-- 		</div> -->
<!--        <table class="parts-product-window-table"> -->
<!--         	<tr> -->
<!--                 <td colspan="2"> -->
<%--                 <a class="btn btn-gray left" onclick="addToProduct();"><fmt:message  key="saveBtn"/></a> --%>
<!--                 </td> -->
<!--             </tr> -->
<!--         </table> -->
<!-- </div> -->
<!-- 元器件添加数据手册选择页面 -->
 <div class="parts-producthand-window parts-producthand1-window">
    
</div>
<!-- 流程审批弹出框 -->
<div class="message-add-page-end">
    <div class="message-edit-header-end bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message key="approvalBtn" /></a>
        <button type="button" class="close message-add-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="message-edit-header2-end">
        <table class="message-edit-table-end">
            <tr>
                <td><span><b>*</b><fmt:message key="ryspgx" />：</span></td>
                <td><select name="appraveType" id="appraveType"  class="form-control">
                    <option value="or">or</option>
                    <option value="and">and</option>
                </select></td>
            </tr>
            <tr>
                <td><span><b>*</b><fmt:message key="xzzxr" />：</span></td>
                <td>
                    <div class="addTableBox-end">
                    <table class="table table-bordered table-hover addTable-end" id="userListAdd">
                    </table>
                    </div>
                </td>
            </tr>
        </table>
        <br/>
        <div class="message-edit-table-btn-end">
            <a class="btn btn-gray message-add-sure" onclick="saveProcessMsg()"><fmt:message key="determine" /></a>
            <a class="btn btn-gray message-add-cancel" onclick="closeDialog('message-add-page-end')"><fmt:message key="resetBtn" /></a>
        </div>
    </div>
</div>
<!---------页尾---------->
<%@include file="/footer.jsp" %>
        </div>
    </div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/productBom.js"></script>
<script type="text/javascript" src="scripts/cms-commond.js"></script>
<script>
$(function(){
    createTree();
    foundDataRights();
    initTableHeader();
//     loadDatesheetDiv();//数据手册弹出框加载
    $('.parts-add-window,.parts-change-window,.parts-into-window,.parts-product-window,.parts-producthand-window,.message-add-page-end').draggable();
	})
</script>
</body>
</html>