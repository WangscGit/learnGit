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
<link rel="stylesheet" href="css/cms-statistic-Dullmaterial.css" />
<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.theme.css">
<script type="text/javascript" src="scripts/ECharts/echarts.common.min.js"></script>
<script type="text/javascript" src="scripts/countData.js"></script>
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
						<a href="pages/statisticPage/cms-statistic-Dullmaterial.jsp"><fmt:message  key="proAnalysis"/></a>>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="statistic-box">
									<div class="time-box">
										<span><fmt:message  key="xzrqfw"/>：</span><input type="text" id="text" class="form-control left"
											name="hiredate" readonly><span> <fmt:message  key="zzz"/>：</span><input
											type="text" id="text1"  class="form-control left" name="hiredate" readonly>
											<a class="statistic-btn btn btn-danger" onclick="showChart();" id="one"><fmt:message  key="determine"/></a>	
									</div>
									<div class="statisBox">
									<div class="left" id="div1" style="width: 50%; height: 400px;"></div>
									<div class="right china" id="china" style="width:50%; height: 400px;"></div>						 					
						 			</div>
						 			<div class="statisBox">
									<div class="left" id="div2" style="width: 50%; height: 400px;"></div>
									<div class="right" id="div3" style="width: 50%; height: 400px;"></div>
									</div>
									<div>
						 			<div class="statisBox">
						 			<div class="left super" id="super" style="width: 50%; height: 400px;"></div>				 											 			
						 			</div>
									 </div>
					</div>
				</div>
			</div>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript" src="scripts/laydate/laydate.js"></script>
	<script>
		$(function(){
			laydate.render({
				  elem: '#text'
				});
			laydate.render({
				  elem: '#text1'
				});
			laydate.render({
				  elem: '#text2'
				});
			laydate.render({
				  elem: '#text3'
				});
			laydate.render({
				  elem: '#text4'
				});
			laydate.render({
				  elem: '#text5'
				});
			showChart();
		});
		
	</script>
</body>
</html>