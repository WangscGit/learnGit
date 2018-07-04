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
 <link rel="stylesheet" href="css/productBomManagement.css" /> 
 <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.theme.css">
<script type="text/javascript" src="scripts/countData.js"></script>
<!-- ZTree样式引入 -->
 <link rel="stylesheet" href="uploadTree/proBomZTree.css" type="text/css">
 <link rel="stylesheet" href="zTreeStyle/zTreeStylepartCheck.css" type="text/css"/>
<script type="text/javascript" src="scripts/ECharts/echarts.common.min.js"></script>
<script type="text/javascript" src="scripts/SelectBox.min.js"></script>
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
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
						<a href="pages/productPage/cms-productBom.jsp" class="active"><fmt:message  key="productBom"/></a>
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
                                <a class="btn btn-danger leadBomBtn" onclick="initExportExcelDia();" id="cxh"><span class="glyphicon glyphicon-arrow-down"></span> <fmt:message  key="inproBom"/></a>
                                <a class="btn btn-danger outBomBtn" onclick="initOutBomWindow('0');" id="cdb"><span class="glyphicon glyphicon-arrow-up"></span> <fmt:message  key="outproBom"/></a>
                                	<a class="btn btn-danger compareBomBtn" onclick="goBomComparePage();"><span class="glyphicon glyphicon-sort"></span>  <fmt:message  key="compareproBom"/></a>			        		
                             </div>
						 </div>
                        <div>
						 			<p class="p2"><b></b><span class="Pname"><fmt:message  key="proComponentMsg"/></span><a class="btn btn-danger btn-xs right" onclick="deleteProductBomPn()" ><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="prodelComponentMsg"/></a><a class="btn btn-xs btn-danger right productmix-delete" onclick="saveProBomRemark();"><span class="glyphicon glyphicon-saved"></span> <fmt:message  key="prosaveComponentMsg"/></a>
						 			<select class="form-control right" onchange="switchBom()" id="switchBom"></select><span class="right BOMname">BOM名称：</span></p>
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
<!-- 导入产品BOM --> 
 <div id="leadBomWindow" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
<div class="leadBomWindow">
	<div class="leadBomWindow-header bg-gray">
		<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="inproBom"/></a>
		<button type="button" class="close leadBomWindow-close"
			aria-label="Close" data-toggle="modal" data-dismiss="modal">
			<span>&times;</span>
		</button>
	</div>
	<div class="leadBomWindow-body">
	   <div class="leadbtnGroup">
	    <div>
	                <div class="lieming left"><b><fmt:message  key="lieming"/>：</b><select class="form-control" onchange="changeSheet();" id="sheetSelect"></select>
	                </div>
	                <div class="lieming lieming2 left"><b><fmt:message  key="whatrow"/>：</b><select class="form-control"  id="startRow"></select> </div>
	               <div class="lieming lieming2 left"><b><span>*</span><fmt:message  key="bomname"/>：</b><input class="form-control"  id="bomName"></input> </div>
	               <div class="right btns">
          <a class="btn btn-xs btn-danger lead-upload"><span class="glyphicon glyphicon-arrow-down"></span><fmt:message  key="upload-file"/>
               <input type="file" class="lead-uploadInput" onchange="exportBomExcel();" name="bomExcel" id="bomExcel"/>
           </a>
	        <a class="btn btn-xs btn-danger" onclick="checkExcelData();" id="checkExcelBtn"><span class="glyphicon glyphicon-tags"></span>&nbsp;<fmt:message  key="inbomcheck"/></a>
            <a class="btn btn-xs btn-danger" onclick="saveProductBomPn();" id="exportExcelBtn"><span class="glyphicon glyphicon-upload"></span>&nbsp;<fmt:message  key="upload-file"/></a>
	               </div>
	        </div>
	   </div>
	   <div class="tableBox">
	   <table class="table table-bordered table-hover" id="sheetTable">
	   </table>
	   </div>
	</div>
</div>
</div>
</div>
<!-- 导出产品BOM --> 
 <div id="outBomWindow" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
<div class="outBomWindow">
	<div class="outBomWindow-header bg-gray">
		<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="outproBom"/></a>
		<button type="button" class="close outBomWindow-close"
			aria-label="Close"  data-toggle="modal" data-dismiss="modal">
			<span>&times;</span>
		</button>
	</div>
	<div class="leadBomWindow-body">         
  <div class="outbtnGroup">
   <a class="btn btn-xs btn-danger left" onclick="addColumn();" id="addColumn"><span class="glyphicon glyphicon-tags"></span>&nbsp;<fmt:message  key="addout"/></a>
	        <div>
	               <div class="left checks">
	               <span><input type="radio" name="radiobutton" onclick="initOutBomWindow();" value="Single"/><label>Single</label></span>
	               <span><input type="radio" name="radiobutton" onclick="initOutBomWindow();" value="Delimited"/><label>Delimited</label></span>
	               </div>
	        </div>
	   </div> 
	   <div class="tableBox">
	   <table class="table table-bordered table-hover" id="exportExcelTable">      
	         <tr id="exportFiledTr" >
	              <td class="harfwidth"><span>序号</span></td>
	              <td class="harfwidth"><span>数量</span></td>
	              <td class="daoruInput"><span>编码</span></td>
	              <td class="harfwidth"><span>位号</span></td>
	         </tr>
	         <tbody id="exportDataTable"></tbody>
	   </table>
	   </div>
	</div>
	<div class="choose-bottom">
			<a class="btn btn-gray"  data-dismiss="modal"  data-toggle="modal"  onclick="closeDialog('outBomWindow')"><span
				class="glyphicon glyphicon-remove-circle"></span> <fmt:message
					key="resetBtn" /></a> <a class="btn btn-gray"  data-dismiss="modal"  data-toggle="modal" 
				onclick="exportExcelData()"><span
				class="glyphicon glyphicon-ok-circle"></span> <fmt:message
					key="determine" /></a>
	</div>
</div>
</div>
</div>
			</div>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/productBom.js"></script>
	<script type="text/javascript" src="scripts/tableExport/tableExport.js"></script>
	<script>
		$(function(){
			 $("#switchBom").hide();//BOM下拉框切换
			 $(".BOMname").hide();//BOM下拉框切换
			$("[name='hiredate']").datepicker({
	            dateFormat : "yy-mm-dd"
	          });
			$("[name='hiredate1']").datepicker({
				dateFormat : "yy-mm-dd"
			});
			
			$(".leadBomWindow-close").click(function(){
					$("#leadBomWindow").hide();
					$(".leadBomWindow").hide();
		   })
		  
			$(".outBomWindow-close").click(function(){
					$("#outBomWindow").hide();
					$(".outBomWindow").hide();
		   })
			$(".compareBomWindow-close").click(function(){
			    $(".compareBomWindow").css({"display":"none"});
		   })
/* 		   $(".peizhi").click(function(){
			   window.location.href="/cms_cloudy/pages/loginpage/cms-ParameterConfiguration.jsp";
		   }) */
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
			$('.leadBomWindow,.outBomWindow').draggable();

			/**页面初始化**/
			initProBomPnTree();
			//$("#p1").hide();
			$("#deleteDataFrombom").hide();
			$("#attr0").css('display','none'); 
			$("#attr1").css('display','none'); 
			$("#attr2").css('display','none'); 
			$("#attr3").css('display','none'); 
			$("#attr4").css('display','none'); 
			//$("#id").css('display','block');
		})
	</script>
</body>
</html>