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
 <link rel="stylesheet" href="css/cms-productmix.css" /> 
 <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.theme.css">
<script type="text/javascript" src="scripts/countData.js"></script>
<!-- ZTree样式引入 -->
 <link rel="stylesheet" href="uploadTree/proBomZTree.css" type="text/css">
 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
<script type="text/javascript" src="scripts/ECharts/echarts.common.min.js"></script>
<script type="text/javascript" src="scripts/SelectBox.min.js"></script>
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
</head>
<body>
	<%@include file="/public.jsp" %>
	<div class="containerAll">
		<div class="containerAllPage">
			<!---------表格---------->
		   <div id="main">
		   <%@include file="/header.jsp"%>
				<div class="page-message">
					<h3 id="navigation">
						<span class="label label-default">Now</span>
						<a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>>
						<a href="pages/productPage/cms-productBom.jsp" class="active"><fmt:message  key="menuProduct"/></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="productmix-box">
							<div class="productmix-treeBox">
							   <ul id="bomtree" class="ztree"></ul>
							</div>
					</div>
					<div class="productmix-dateBox">
						 <div class="dateBox-btnGroup">
						 	<div class="dateBox-btnGroup-nav">
                                <a class="btn btn-danger productmix-addchildren" onclick="addProCode()" id="cxh" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proAdd"/></a>
                                <a class="btn btn-danger productmix-addchildren" onclick="addProductBroad()" id="cdb" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proSingleAdd"/></a>
                                	<a class="btn btn-danger productmix-edit" onclick="updateNode()"><span class="glyphicon glyphicon-edit"></span> <fmt:message  key="modifyBtn"/></a>
					        		<a class="btn btn-danger productmix-delete" onclick="deleteBom()"><span class="glyphicon glyphicon-remove"></span> <fmt:message  key="deleteBtn"/></a>
					        		<a class="btn btn-danger productmix-refresh" onclick="refreshBom()"><span class="glyphicon glyphicon-refresh"></span> <fmt:message  key="refreshBtn"/></a>
                             </div>
						 </div>
					<div>
					<p class="p1" id="p1"><b></b><span class="Pname"><fmt:message  key="proNodesMsg"/></span></p>
			   <div class="attr-message attr1" id="attr0">
				<table class="attr-message-table">
                    <tr>
                        <td>
                            <span>产品名称：</span>                        
                             <input type="text" class="form-control" name="showRootName"/>
                        </td>
<!--                         <td> -->
<!--                             <span>产品类型：</span> -->
<!--                             <div class="box2"></div> -->
<!--                         </td> -->
                    </tr>
                   </table>
                   <div class="saveBtn" id="btn0"><a class="btn btn-danger btn-xs" onclick="saveAttr1()" id="A0"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" onclick="closeAtt0()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
<!--                    <div class="saveBtn" id="btn5"><a class="btn btn-danger btn-xs" href="javascript:updateAttr1()">修改</a><a class="btn btn-danger btn-xs" href="javascript:closeAtt0()">取消</a></div> -->
                   </div>
					<div class="attr-message attr1" id="attr1">
				<table class="attr-message-table">
                    <tr>
                        <td class="chooseUser">
                            <span><fmt:message  key="productName"/>：</span>                        
                             <input type="text" class="form-control" name="productName"/>
                        </td>
                        <td>
                            <span><fmt:message  key="proBoardcreateuser"/>：</span>
                            <a class="btn btn-xs btn-danger attruser-btn left"><fmt:message  key="selectBtn1"/></a><span id="firthUser" class="userShow"></span>
                        </td>                        
                    </tr>               
                </table>
                <div class="saveBtn" id="btn1"><a class="btn btn-danger btn-xs" onclick="saveAttr1()"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" onclick="closeAtt1()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                <div class="saveBtn" id="btn3"><a class="btn btn-danger btn-xs" onclick="updateAttr1()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" onclick="closeAtt1()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                </div>
                 <div class="attr-message attr4"  id="attr2">
                <table class="attr-message-table">
                    <tr>
                        <td class="chooseUser">
                            <span><fmt:message  key="proBoardname"/>：</span><input type="text" class="form-control" name="boardProductName"/>
                        </td>
                        <td>
                            <span><fmt:message  key="proDesignName"/>：</span><input type="text" class="form-control" name = "productNo"/>
                        </td>
                        </tr>
                        <tr>
                        <td class="chooseUser">
                            <span><fmt:message  key="proStage"/>：</span>
                            <select id="productStage" name="productStage" id="" class="form-control">
                            <option value=""><fmt:message  key="selectBtn2"/></option>
                            <option value="S">模样</option>
                            <option value="A">初样电性件</option>
                            <option value="B">初样鉴定件</option>
                            <option value="C">正样</option>
                            </select>
                        </td>
                        <td>
                         <span><fmt:message  key="proEdatool"/>：</span>
                            <select id="pTool" name="pTool" id="" class="form-control">
                            <option value=""><fmt:message  key="selectBtn2"/></option>
                            <option value="Concept HDL">Concept HDL</option>
                            <option value="OrCAD Capture">OrCAD Capture</option>
                            <option value="DxDesigner">DxDesigner</option>
                            </select>
                       </td>
                    </tr>
                </table> 
                <div class="saveBtn" id="btn2"><a class="btn btn-danger btn-xs" onclick="saveAttr2()"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" onclick="closeAtt2()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                <div class="saveBtn" id="btn4"><a class="btn btn-danger btn-xs" onclick="updateAttr2()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" onclick="closeAtt2()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
					</div>
						 </div>
<div>
						 			<p class="p2"><b></b><span class="Pname"><fmt:message  key="proComponentMsg"/></span><select class="form-control right" onchange="switchBom()" id="switchBom"></select><span class="right BOMname">BOM名称：</span></p>
						 			<div class="parts-message">
						 					<table class="table table-bordered table-hover" id="showPartdata">
						 					</table>
						 					<div class="fenye">
						 		            <div id="Pagination" class="pagination"></div>                               
						 					</div>
						 			</div>
						 </div>
					</div>
				</div>
			</div>
			</div>
	<!-- 人员选择弹窗 -->
			<div class="productmix-chooseuser-window" >
					<div class="productmix-chooseuser-window-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="selectUser"/></a>
        <button type="button" class="close productmix-chooseuser-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="productmix-chooseuser-window-header2">
        <input type="text" class="form-control" id="searchText" placeholder="<fmt:message  key="placeholder-searchInp"/>"/>
        <a class="btn btn-danger" onclick="selectUser();"><span class="glyphicon glyphicon-search"></span>  <fmt:message  key="serachBtn"/></a>
    </div>
    <div class="productmix-chooseuser-window-body">
        <div class="productmix-chooseuser-tree">
        		<ul id="userTree" class="ztree"></ul>
        </div>
        <div class="productmix-chooseuser-window-box">
            <table class="table table-striped table-hover table-bordered">
                <tbody id="showUser">
                </tbody>
            </table>
        </div>
        <div class="productmix-chooseuser-window-bottom">       
            <a class="btn btn-gray" onclick="resetSelectUser()"><span class="glyphicon glyphicon-remove-circle"></span>  <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="saveSelectUser()"><span class="glyphicon glyphicon-ok-circle"></span>  <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/product/productMix.js"></script>
	<script>
		$(function(){
			$('.productmix-chooseuser-window').draggable();

			/**页面初始化**/
			initBomTree();
			closeUpdateBtn();
			//$("#p1").hide();
			$("#deleteDataFrombom").hide();
			$("#attr0").css('display','none'); 
			$("#attr1").css('display','none'); 
			$("#attr2").css('display','none'); 
			$("#attr3").css('display','none'); 
			$("#attr4").css('display','none'); 
			//$("#id").css('display','block');
		    initgroupUserTree();
   
   $("#c2Btn").hide();
   $("#switchBom").hide();//BOM下拉框切换
   $(".BOMname").hide();//BOM下拉框切换
   productDataRights();
   $(".hand-work ul li").hover(function(){
	   $(this).css("background","#d9534f");
	   $(this).children("a").css("color","#fff")
   },function(){
	   $(this).css("background","");
	   $(this).children("a").css("color","")
   })
		})
	</script>
</body>
</html>