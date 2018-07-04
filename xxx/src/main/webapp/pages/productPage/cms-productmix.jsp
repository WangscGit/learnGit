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
						<a><fmt:message  key="menuManager"/></a>>
						<a href="pages/productPage/cms-productmix.jsp" class="active"><fmt:message  key="menuProduct"/></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="productmix-box">
					        <div class="btn-box">
					        		<div class="hand-work left" id="c2Btn">
					        		         <div class="hand-workbtn1" ><span class="glyphicon glyphicon-hand-down"></span> <fmt:message  key="operationBtn"/>
					        		         <div><b class="handb"></b></div>
					        				 <ul class="handul">					        						
<!-- 					        						<li><a class="btn btn-xs productmix-copy" href="javascript:copyBom()"><span class="glyphicon glyphicon-copy"></span>  拷贝</a></li> -->
<!-- 					        		                <li><a class="btn btn-xs productmix-paste" href="javascript:pasteBom()"><span class="glyphicon glyphicon-paste"></span>  黏贴</a></li> -->
					        						<li><a class="btn btn-xs productmix-edit" href="javascript:updateNode()"><span class="glyphicon glyphicon-edit"></span> <fmt:message  key="modifyBtn"/></a></li>
					        						<li><a class="btn btn-xs productmix-delete" href="javascript:deleteBom()"><span class="glyphicon glyphicon-remove"></span> <fmt:message  key="deleteBtn"/></a></li>
					        						<li><a class="btn btn-xs productmix-refresh" href="javascript:refreshBom()"><span class="glyphicon glyphicon-refresh"></span> <fmt:message  key="refreshBtn"/></a></li>
					        						<li><a class="btn btn-xs productmix-Bom" href="javascript:exportBom()"><span class="glyphicon glyphicon-remove"></span> <fmt:message  key="exportBtn"/></a></li>
					        				</ul>
					        		      </div>						        						        				
					        		</div>
					        		<div class="hand-work2 right"><img src="images/sup.png"></div>		        		
					        </div>
							<div class="productmix-treeBox">
							   <ul id="bomtree" class="ztree"></ul>
							</div>
					</div>
					<div class="productmix-dateBox">
						 <div class="dateBox-btnGroup">
						 	<div class="dateBox-btnGroup-nav">
                                <a class="btn btn-xs btn-danger productmix-addchildren" href="javascript:addProCode()" id="cxh" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proModelBtn"></fmt:message></a>
                                <a class="btn btn-xs btn-danger productmix-addchildren" href="javascript:addProSystem()" id="cfx" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proSystemBtn"/></a>
                                <a class="btn btn-xs btn-danger productmix-addchildren" href="javascript:addProEngine()" id="cdj" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proSingleBtn"/></a>
                                <a class="btn btn-xs btn-danger productmix-addchildren" href="javascript:addProBroad()" id="cdb" style="display:none"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="proVeneerBtn"/></a>
                             </div>
						 </div>
					<div>
					<p class="p1" id="p1"><b></b><fmt:message  key="proNodesMsg"/></p>
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
                   <div class="saveBtn" id="btn0"><a class="btn btn-danger btn-xs" href="javascript:saveAttr1()" id="A0"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt0()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
<!--                    <div class="saveBtn" id="btn5"><a class="btn btn-danger btn-xs" href="javascript:updateAttr1()">修改</a><a class="btn btn-danger btn-xs" href="javascript:closeAtt0()">取消</a></div> -->
                   </div>
					<div class="attr-message attr1" id="attr1">
				<table class="attr-message-table">
                    <tr>
                        <td class="chooseUser">
                            <span><fmt:message  key="proModelname"/>：</span>                        
                             <input type="text" class="form-control" name="productName"/>
                        </td>
                        <td>
                            <span><fmt:message  key="proSyscreateuser"/>：</span>
                            <a class="btn btn-xs btn-danger attruser-btn left" onclick="getThis(this)" href="javascript:selectUser()"><fmt:message  key="selectBtn1"/></a><span id="firthUser" class="userShow"></span>
                        </td>                        
                    </tr>               
                </table>
                <div class="saveBtn" id="btn1"><a class="btn btn-danger btn-xs" href="javascript:saveAttr1()"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt1()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                <div class="saveBtn" id="btn5"><a class="btn btn-danger btn-xs" href="javascript:updateAttr1()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt1()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                </div>
                <div class="attr-message attr2"  id="attr2">
                <table class="attr-message-table">
                    <tr>
                        <td class="chooseUser">
                            <span><fmt:message  key="proSub-Systemname"/>：</span><input type="text" name="systemProductName" class="form-control"/>
                        </td>
                        <td>
                            <span><fmt:message  key="proEnginecreateuser"/>：</span><a class="btn btn-xs btn-danger attruser-btn left" onclick="getThis(this)" href="javascript:selectUser()"><fmt:message  key="selectBtn1"/></a><span id="secondtUser" class="userShow"></span>
                        </td>                        
                    </tr>
                </table>
                   <div class="saveBtn" id="btn2"><a class="btn btn-danger btn-xs" href="javascript:saveAttr2()"><span class="glyphicon glyphicon-floppy-disk"></span><fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt2()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                   <div class="saveBtn" id="btn6"><a class="btn btn-danger btn-xs" href="javascript:updateAttr2()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt2()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                </div>
                 <div class="attr-message attr3"  id="attr3">
                <table class="attr-message-table">       
                    <tr>
                        <td class="chooseUser"><!-- class="shorttd" 选择单板人员样式乱 -->
                            <span><fmt:message  key="proEnginename"/>：</span><input type="text" class="form-control" name="engineProductName"/>
                        </td>
                        <td>
                            <span><fmt:message  key="proBoardcreateuser"/>：</span><a class="btn btn-xs btn-danger attruser-btn left" onclick="getThis(this)" href="javascript:selectUser()"><fmt:message  key="selectBtn1"/></a><span id="thirdUser" class="userShow"></span>
                        </td>                        
                    </tr>
                </table>
                <div class="saveBtn" id="btn3"><a class="btn btn-danger btn-xs"  href="javascript:saveAttr3()"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt3()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                <div class="saveBtn" id="btn7"><a class="btn btn-danger btn-xs" href="javascript:updateAttr3()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt3()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                </div>
                 <div class="attr-message attr4"  id="attr4">
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
                <div class="saveBtn" id="btn4"><a class="btn btn-danger btn-xs" href="javascript:saveAttr4()"><span class="glyphicon glyphicon-floppy-disk"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt4()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
                <div class="saveBtn" id="btn8"><a class="btn btn-danger btn-xs" href="javascript:updateAttr4()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="saveBtn"/></a><a class="btn btn-danger btn-xs" href="javascript:closeAtt4()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a></div>
					</div>
						 </div>
<div>
						 			<p class="p2"><b></b><fmt:message  key="proComponentMsg"/><a class="btn btn-danger btn-xs right" onclick="deleteDataFrombom()" id="deleteDataFrombom"><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="prodelComponentMsg"/></a></p>
						 			<div class="parts-message">
						 					<table class="table table-bordered table-hover" id="showPartdata">
						 					</table>
						 					<div class="fenye">
						 		            <div id="Pagination" class="pagination"></div>                               
						 					</div>
						 			</div>
						 </div>
						  <div>
						 			<p class="p2"><b></b><fmt:message  key="proAnalysis"/></p>
						 			<div class="parts-statistic">
						 			<div class="left china" id="china" style="width: 380px; height: 325px;">						 					
						 			</div>
						 			<div class="right super" id="super" style="width: 380px; height: 325px;">				 					
						 			</div>
						 			</div>
						 </div>						 						 		
					</div>
				</div>
			</div>
			<div class="productmix-chooseuser-window" >
					<div class="productmix-chooseuser-window-header bg-info">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="selectUser"/></a>
        <button type="button" class="close productmix-chooseuser-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="productmix-chooseuser-window-header2">
        <input type="text" class="form-control" id="searchText" placeholder="<fmt:message  key="placeholder-searchInp"/>"/>
        <a class="btn btn-warning" onclick="selectUser();"><span class="glyphicon glyphicon-search"></span>  <fmt:message  key="serachBtn"/></a>
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
            <a class="btn btn-warning" href="javascript:resetSelectUser()"><span class="glyphicon glyphicon-remove-circle"></span>  <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-info" href="javascript:saveSelectUser()"><span class="glyphicon glyphicon-ok-circle"></span>  <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>
			</div>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/productBom.js"></script>
	<script>
		$(function(){
			$("[name='hiredate']").datepicker({
	            dateFormat : "yy-mm-dd"
	          });
			$("[name='hiredate1']").datepicker({
				dateFormat : "yy-mm-dd"
			});
			new SelectBox($('.box1'),[
			      					{name:'实验型号',id:'SY'},
			      					{name:'神舟系列',id:'SZ'},
			      					{name:'嫦娥系列',id:'CE'},
			      					{name:'海洋系列',id:'HY'},
			      					{name:'实践系列',id:'SJ'},
			      					{name:'环境系列',id:'HJ'},
			      					{name:'货运系列',id:'TZ'}
			      				],function(result){
				                     var tempIndex = JSON.stringify(result.name);
			                         window.sessionStorage["selectBox"] = tempIndex;
			      				});
			new SelectBox($('.box2'),[
				      					{name:'实验型号',id:'SY'},
				      					{name:'神舟系列',id:'SZ'},
				      					{name:'嫦娥系列',id:'CE'},
				      					{name:'海洋系列',id:'HY'},
				      					{name:'实践系列',id:'SJ'},
				      					{name:'环境系列',id:'HJ'},
				      					{name:'货运系列',id:'TZ'}
				      				],function(result){
				                        var tempIndex = JSON.stringify(result.name);
				                         window.sessionStorage["selectBoxUpdate"] = tempIndex;
				      				});
			$('.productmix-chooseuser-window').draggable();

			countProductPn();
			
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