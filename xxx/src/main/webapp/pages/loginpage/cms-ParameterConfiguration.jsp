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
 <link rel="stylesheet" href="css/cms-ParameterConfiguration.css" /> 
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
						<a href="pages/productPage/cms-productBom.jsp" class="active">系统参数配置</a>
					</h3>
				</div>
				<div class="table-responsive">
                         <div class="selectBox">
                                      <div>
                                              <span class="left"><b>设计工具：</b></span>
                                              <select class="form-control left" id="paramConfig" onchange="saveParam();"><option value='Consept'>Consept HDL</option><option value='OrCAD'>OrCAD Capture</option></select>
                                      </div>
                         </div>
				</div>
			</div>
			</div>

			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/product/productMix.js"></script>
	<script type="text/javascript">
	$(function(){
		selectParam();
	})
	function saveParam(){
		var paramConfig=$("#paramConfig").val();
		$.ajax({
			url : "HrRightsController/saveParam.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"paramConfig":paramConfig
			},
			success : function(json) {
				
			},
			error : function() {
			}
		});
	}
	//初始化页面 查询配置的系统参数
	function selectParam(){
		$.ajax({
			url : "HrRightsController/selectParam.do",
			dataType : "json",
			cache : false,
			type : "post",
			success : function(json) {
				var param=json.parameter;
				if(param!=undefined&&param.length>0){
					$("#paramConfig").val(param[0].parameterValue);
				}
			},
			error : function() {
			}
		});
	}
	</script>
</body>
</html>