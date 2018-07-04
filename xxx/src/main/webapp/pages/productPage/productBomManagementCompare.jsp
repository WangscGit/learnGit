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
 <link rel="stylesheet" href="css/productBomManagementCompare.css" /> 
<script type="text/javascript" src="scripts/countData.js"></script>
<!-- ZTree样式引入 -->
 <link rel="stylesheet" href="uploadTree/proBomZTree.css" type="text/css">
 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
<script type="text/javascript" src="scripts/ECharts/echarts.common.min.js"></script>
<script type="text/javascript" src="scripts/SelectBox.min.js"></script>
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
     <script type="text/javascript" src="scripts/jquery.lettering.js"></script>
	<script type="text/javascript" src="scripts/jquery.textillate.js"></script>
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
						<a href="pages/productPage/productBomManagement.jsp"><fmt:message  key="productBom"/></a>>
						<a href="javascript:void(0)" class="active"><fmt:message  key="compareproBom"/></a>
					</h3>
				</div>
				<div class="table-responsive">
					<div class="compareBtnGroups">			
                                <a class="btn btn-danger" onclick="compareBomByCondition('1')"><span class="glyphicon glyphicon-arrow-down"></span><fmt:message  key="CPEBomByPR"/></a>
                                <a class="btn btn-danger" onclick="compareBomByCondition('2')"><span class="glyphicon glyphicon-arrow-up"></span><fmt:message  key="CPEBomByPC"/></a>
                                <a class="btn btn-danger" style="display:none;" id="exportCompareBtn" onclick="exportCompareResult()"><span class="glyphicon glyphicon-sort"></span> <fmt:message  key="CPEBomExport"/></a>					        		                                       
					</div>
					<div class="compareBody">
					     <div class="left one">
					         <p><b><fmt:message  key="compareBom1"/></b></p>
					         <div class="tableBox">
					          <table class="table table-bordered">        
	      
	   </table>
	   </div>
					     </div>
					     <div class="left two">
					         <p><b><fmt:message  key="compareBom2"/></b></p>
					          <div class="tableBox">
					          <table class="table table-bordered">        
	        
	   </table>
	   </div>
					     </div>
					</div>
					<div class="clear"></div>
					<div class="compareResult">
					<p><b><fmt:message  key="compareResuit"/></b></p>
					<div class="tableBox">
					          <table class="table table-bordered table-hover">        
	         <tr>
	              <td><span><fmt:message  key="CPEPartCode"/></span></td>
	              <td><span><fmt:message  key="CPEItem"/></span></td>
	              <td><span><fmt:message  key="CPEPF"/></span></td>
	              <td><span><fmt:message  key="CPEMF"/></span></td>
	              <td><span>Value</span></td>
	         </tr>
	         <tbody id="show-compareResult">
	         
             </tbody>
	   </table>
	   </div>
					</div>
				</div>
			</div>
			</div>
			<!---------页尾---------->
			<%@include file="/footer.jsp"%>
		</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script>
	
		$(function(){
			initCompareBomList();//bom对比页面初始化
		})
		
		var partNumberList;//所有BOM的物料编码集合
		var partReferenceList;//所有BOM的元器件符号集合
		var bomSize;//对比BOM个数
		var lang;//语言
	   //bom对比页面初始化
		function initCompareBomList(compareResult) {
			var b = new Base64();
			var proList=GetQueryString("proList");
			var bomList=b.decode(proList);
// 			var bomList = new Array();
// 			for(var i=1;i<3;i++){
// 				var obj = new Object();
// 				obj.excelName = "test"+i+".xls";
// 				obj.productID = "201";
// 				bomList.push(obj);
// 			}
			$.ajax({
				url : 'productManageController/initCompareBomList.do',
				type : "POST",
				dataType : 'json',
				data : "bomList=" + bomList,
				success : function(json) {
					partNumberList=json.partNumberList;//物料编码集合
					partReferenceList=json.partReferenceList;//物料编码集合
					bomSize=json.size;//BOM个数
					lang=json.lang;//语言
					json=json.list;//BOM集合
					var html = '';
					for(var x=0;x<json.length;x++){//json----Bom对比个数
						var paramList = json[x];//bom中元器件信息集合
						var seqNo = parseInt(x)+parseInt('1');
						var _class = "";
						var _b=""
						if(seqNo == 1){
							_class = "left one"
							if(lang == 'zh'){
								_b="对比BOM（1）";
							}else{
								_b="Compare the bom（1）"
							}
						}else if(seqNo == 2){
							_class = "left two"
							if(lang == 'zh'){
								_b="对比BOM（2）";
							}else{
								_b="Compare the bom（2）"
							}
						}else{
							_class = "left three"
						    _b="对比BOM（3）";
						}
						html += '<div class="'+_class+'">';
						html += '<p><b>'+_b+'</b></p>';
						html += '<div class="tableBox">';
						html += '<table class="table table-bordered">';
						html += '<tr>';
						if('zh' == lang){
							html += '<td class="mustwidth"><span>物料编码</span></td>';
							html += '<td class="mustwidth"><span>规格型号</span></td>';
							html += '<td class="mustwidth"><span>元器件符号</span></td>';
							html += '<td class="mustwidth"><span>制造商</span></td>';
							html += '<td class="mustwidth"><span>Value</span></td>';
						}else{
							html += '<td class="mustwidth"><span>Part Number</span></td>';
							html += '<td class="mustwidth"><span>ITEM</span></td>';
							html += '<td class="mustwidth"><span>Part Reference</span></td>';
							html += '<td class="mustwidth"><span>Manufacturer</span></td>';
							html += '<td class="mustwidth"><span>Value</span></td>';
						}
						html += '</tr>';
						for(var i=0;i<paramList.length;i++){
							var partData = paramList[i];
							var PartNumberResule = typeof(partData.ptNumber) == "undefined"?"":partData.ptNumber;
							var PartCode = typeof(partData.PartCode) == "undefined"?"":partData.PartCode;
							var ItemResule = typeof(partData.Item) == "undefined"?"":partData.Item;
							var PRResule = typeof(partData.Part_Reference) == "undefined"?"":partData.Part_Reference;
							var MFResule = typeof(partData.Manufacturer) == "undefined"?"":partData.Manufacturer;
							var ValueResule = typeof(partData.Value) == "undefined"?"":partData.Value;
							if(checkDataList(compareResult,PartNumberResule)){
								html += '<tr bgcolor="#d9534f">';
							}else{
								html += '<tr>';
							}
							html += '<td class="mustwidth" style="display:none"><span title="'+PartNumberResule+'">'+PartNumberResule+'</span></td>';
							html += '<td class="mustwidth"><span title="'+PartCode+'">'+PartCode+'</span></td>';
							html += '<td class="mustwidth"><span title="'+ItemResule+'">'+ItemResule+'</span></td>';
							html += '<td class="mustwidth"><span title="'+PRResule+'">'+PRResule+'</span></td>';
							html += '<td class="mustwidth"><span title="'+MFResule+'">'+MFResule+'</span></td>';
							html += '<td class="mustwidth"><span title="'+ValueResule+'">'+ValueResule+'</span></td>';
							html += '</tr>';
						}
						html += '</table>';
						html += '</div>';
						html += ' </div>';
					}
					$(".compareBody").html(html);
				},
				error : function() {
					layer.alert("数据加载异常，请联系管理员！");
				}
			});
		}
		//通过PartNumber/Reference进行Bom对比
		var partNumberCR;//partNumber对比结果集
		var partReferenceCR;//Part_Reference对比结果集
		function compareBomByCondition(type){
			var bomCollection;
			$("#exportCompareBtn").attr("onclick","exportCompareResult("+type+")");//最后一次对比条件
			if(type == '2'){
				if(null == partNumberCR || typeof(partNumberCR) == "undefined"){
					bomCollection = partNumberList;
				}else{
					showCompareResult(partNumberCR)
					return;
				}
			}else{
				if(null == partReferenceCR || typeof(partReferenceCR) == "undefined"){
					bomCollection = partReferenceList;
				}else{
					showCompareResult(partReferenceCR)
					return;
				}
			}
			$.ajax({
			    url: 'productManageController/compareBomByCondition.do',
			    type: "POST",
			    data: {'bomCollection':JSON.stringify(bomCollection),'type':type,'bomSize':bomSize},
	            dataType: 'json',
	            cache: false,
	            success: function(json){
	            if(type == '2'){
		           partNumberCR = json;
	            }else{
	               partReferenceCR = json;
	            }
	            showCompareResult(json);
	            },
	            error: function(){
	            	layer.alert("服务器异常，黏贴操作失败！");
	            	return;
	            }
	         });
		}
		//BOM对比结果展示
		function showCompareResult(compareResult){
			var html = '';
			if(null == compareResult || compareResult.length <= 0){
				html += '<tr>';
				html += '<td colspan="5" align="center">'
						+ '-- no data --' + '</td>';
				html += '</tr>';
				$("#show-compareResult").html(html);
				$("#exportCompareBtn").hide();
				initCompareBomList(compareResult);
			}else{
				for(var x=0;x<compareResult.length;x++){
					var partData = compareResult[x];
					var PartNumberResule = typeof(partData.PartNumber) == "undefined"?"":partData.PartNumber;
					var PartCodeResult = typeof(partData.PartCode) == "undefined"?"":partData.PartCode;
					var ItemResule = typeof(partData.Item) == "undefined"?"":partData.Item;
					var PRResule = typeof(partData.Part_Reference) == "undefined"?"":partData.Part_Reference;
					var MFResule = typeof(partData.Manufacturer) == "undefined"?"":partData.Manufacturer;
					var ValueResule = typeof(partData.Value) == "undefined"?"":partData.Value;
					html += '<tr>';
					html += '<td class="mustwidth" style="display:none"><span>'+PartNumberResule+'</span></td>';
					html += '<td class="mustwidth"><span>'+PartCodeResult+'</span></td>';
					html += '<td class="mustwidth"><span>'+ItemResule+'</span></td>';
					html += '<td class="mustwidth"><span>'+PRResule+'</span></td>';
					html += '<td class="mustwidth"><span>'+MFResule+'</span></td>';
					html += '<td class="mustwidth"><span>'+ValueResule+'</span></td>';
					html += '</tr>';
				}
				$("#show-compareResult").html(html);
				$("#exportCompareBtn").show();//展示导出结果按钮
				initCompareBomList(compareResult);
			}
		}
		//檢查是否為不同項
		function checkDataList(data,pNumber){
			var result = false;
			if(null == data || typeof(data) == "undefined"){
				return result;
			}else{
				for(var x=0;x<data.length;x++){
					var partNumber = data[x].PartNumber;
					if(pNumber == partNumber){
						result = true;
						break;
					}
				}
			}
			return result;
		}
		//导出对比结果
		function exportCompareResult(type){
			var resultList;
			if(type == '1'){
				resultList = partReferenceCR;
			}else{
				resultList = partNumberCR;
			}
			if(null != resultList || typeof(resultList) != "undefined"){
				window.location.href=getContextPathForWS()+"/productManageController/exportCompareResult.do?resultList="+JSON.stringify(resultList);
			}
		}
	</script>
</body>
</html>