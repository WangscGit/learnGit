<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/cms-parts-history.css"/>
    <link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
    <script type="text/javascript">
    function openHIstory(PartNumber,VersionID){
    	$.ajax({
    		url: 'partComponentArrt/selectPartHistoriesDetailList.do',
    		data: {'VersionID':VersionID,'PartNumber':PartNumber},
    		dataType: 'json',
    		cache: false,
    		success: function(json){
                  var showNames = json.showNames;
                  var queryFields = json.queryFields;
                  var dataHistory = json.dataHistory;
                  var dataPart = json.dataPart;
                  var dataHistorys = dataHistory[0];
                  var dataParts = dataPart[0];
                  if(showNames.length>0 && dataHistory.length>0 && dataPart.length>0){
                	  var html = '';
                	   for(var i=0;i<showNames.length;i++){
                		   if('null' == dataHistorys[i].replace(/(^\s*)|(\s*$)/g, "") || null == dataHistorys[i].replace(/(^\s*)|(\s*$)/g, "") || '' == dataHistorys[i].replace(/(^\s*)|(\s*$)/g, "")){
                			   dataHistorys[i] = "";
                		   }
                		   if('null' == dataParts[i].replace(/(^\s*)|(\s*$)/g, "") || null == dataParts[i].replace(/(^\s*)|(\s*$)/g, "")  || '' == dataParts[i].replace(/(^\s*)|(\s*$)/g, "")){
                			   dataParts[i] = "";
                		   }
                		   if(queryFields[i] == 'PartNumber'){
                			   html += '<tr style="display:none">';
                       	    }else{
                       	       html += '<tr>';
                       	    }
                		   html +='<td>'+showNames[i]+'</td>';
                           if(dataHistorys[i] == dataParts[i]){
                           html += '<td>'+'<span class="glyphicon glyphicon-ok-sign ok"></span>'+'</td>';
                           }else{
                           html += '<td>'+'<span class="glyphicon glyphicon-exclamation-sign warn"></span>'+'</td>';
                           }
                           if(queryFields[i] == "schematic_img" || queryFields[i] == "ens_img" || queryFields[i] == "shape_img" || queryFields[i] == "size_img" || queryFields[i] == "characteristic_curve_img" || queryFields[i] == "typical_ap_img"){
                        	    html += '<td class="partsImages">'
                        		html += '<div class="left parts-images" data-toggle="tooltip"';
                        	    html += 'data-placement="right" title="图片上传最佳尺寸【550px*320px】">';
               					html += '<img src=' + dataHistorys[i] + '>';
               					html += '</div>';
               					html += '</td>'
               					html += '<td class="partsImages">'
               					html += '<div class="left parts-images" data-toggle="tooltip"';
               					html += 'data-placement="right" title="图片上传最佳尺寸【550px*320px】">';
               					html += '<img src=' + dataParts[i] + '>';
               					html += '</div>';
               					html += '</td>'
                           }else{
                        	   html +='<td class="valueLength">'+dataHistorys[i]+'</td>';
                               html +='<td class="valueLength">'+dataParts[i]+'</td>';
                           }
                           html += '</tr>';     
                	   }
                	   $("#valueCompare").html(html);
                	   /**图片显示**/
                       $('.parts-images img').zoomify();
                  }
    		},
    		error: function(){
    			layer.alert("数据加载异常，请联系管理员！");
    		}
    	});
        $(".parts-history-window-box2").show();
    }
    </script>
</head>
<body>
<%@include file="/public.jsp" %>
<div class="containerAll">
    <div class="containerAllPage">
        <!---------表格---------->
        <div id="main">
        <%@include file="/header.jsp" %>
            <div class="page-message">
                <h3 id="historyNavigation"><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a href="pages/parts/cms-parts.jsp"><fmt:message  key="ComponentNavigator"/></a>></h3>
            </div>
            <div class="table-responsive">
                <div class="parts-history-window-body">           
                    <div class="parts-history-window-box1">
                     <div class="box-title">
                        <b><fmt:message  key="history-modifyRecording"/></b>
                    </div>
                        <table class="table table-striped table-hover table-bordered parts-history-table1">
                            <thead class="table-title">
                            <tr>
                                <td><fmt:message  key="history-modifyVersion"/></td>
                                <td><fmt:message  key="modifyTime"/></td>
                                <td><fmt:message  key="modifyUser"/></td>
                            </tr>
                            </thead>
                            <tbody id="historiesData">
                            </tbody>
                        </table>
                    </div>
                    <div class="parts-history-window-box2">
                       <div class="box-title">
                        <b><fmt:message  key="history-compare"/></b>
                    </div>
                        <table class="table table-striped table-hover table-bordered parts-history-table">
                            <thead class="table-title">
                            <tr>
                                <td><fmt:message  key="history-attr"/></td>
                                <td><fmt:message  key="history-Anastomosis"/></td>
                                <td><fmt:message  key="history-oldVersion"/></td>
                                <td><fmt:message  key="history-nowVersion"/></td>
                            </tr>
                            </thead>
                            <tbody id="valueCompare">
                            </tbody>
                        </table>
                    </div>
                </div>
        </div>
                    </div>
        <!---------页尾---------->
<%@include file="/footer.jsp" %>
    </div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/partsHistories.js"></script>
<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
</body>
</html>