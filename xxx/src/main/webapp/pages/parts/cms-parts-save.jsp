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
 <link rel="stylesheet" href="css/cms-headerPublic.css"/>
  <link rel="stylesheet" href="css/cms-parts-save.css"/>
  <link rel="stylesheet" href="css/cms-parts.css"/>
  <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
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
        <%@include file="/header.jsp" %>
            <div class="page-message">
                <h3><span class="label label-default">Now</span>  <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a href="pages/parts/cms-parts-save.jsp?collection=1" class="active"><fmt:message  key="index-collect"/></a></h3>
            </div>
           <div class="table-responsive">
                <div class="right-body left">
                    <div class="parts-search">
                          <table class="table save-table" id="dataTable">
                               <tr class="parts-search-input parts-search-data" id="save-title">
                                <th class="search-checkbox"><input type="checkbox" onclick="selectCheckBox();" id="fircb"/></th>
                                <th class="parts-search-img"><fmt:message  key="waiguan"/></th>
                                <th><fmt:message  key="yqjmc"/></th>
                                <th><fmt:message  key="ggxh"/></th>
                                <th><fmt:message  key="zzs"/></th>
                                <th><fmt:message  key="zldj"/></th>
                                <th><fmt:message  key="sjsc"/></th>                                 
                               <th class="parts-search-admin"><div class="save-admin">
                               <a class=" btn btn-xs partsDelete-btn" onclick="deleteUserPn();"><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="deleteBtn"/></a>
                            </div></th> 
                            </tr> 
                       	 </table>
                       	 <div class="table-footers">
                        <div class="pagination" id="partDataPage"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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
     <!-- 添加到产品页面 -->
 <div class="parts-product-window">
    <div class="parts-product-window-header bg-danger">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>添加到产品</a>
        <button type="button" class="close parts-product-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
       <div class="productmix-treeBox">
       	<ul id="bomtree" class="newztree"></ul>
		</div>
       <table class="parts-product-window-table">
        	<tr>
                <td colspan="2">
                <a class="btn btn-danger left" onclick="addToProduct();">保存</a>
                </td>
            </tr>
        </table>
</div>   
        
<!---------页尾---------->
<%@include file="/footer.jsp" %>
        </div>
    </div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/partdatas.js"></script>
<script>

$(function(){
    $(".leftsidebar_box dd").hide();
    $(".leftsidebar_box dt").click(function(){
        $(this).css({"background-color": "#f2f2f2"});
        $(this).parent().find('dd').removeClass("menu_chioce");
        $(".leftsidebar_box dt img").attr("src","images/select_xl01.png");
        $(this).parent().find("img").attr("src","images/select_xl.png");
        $(".menu_chioce").slideUp();
        $(this).parent().find('dd').slideToggle();
        $(this).parent().find('dd').addClass("menu_chioce");
    });
    foundDataRights();//获取权限信息
    $(".leftsidebar_box dt").css({"background-color":"#fff"});
    colle();
})
//进入我的收藏页面会有collection参数（我的收藏与主数据页面调用后台方法一样，所有用collection参数区分）
function colle(pageNo){
	loadProperties();
	if(isNaN(pageNo)){
		pageNo = 0;
	}
	var addBasic = '1';
    pageNo = parseInt(addBasic)+parseInt(pageNo);
    var url = location.href;
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
    var item=paraString[0].split("=");
    var collection;
    var value;
    if(item.length>=2){
        collection=item[0];
        value=item[1];
    }

    $.ajax({
        url : "partDataController/selectPartData.do",
        dataType : "json",
        cache : false,
        type : "post",
        data:{collection:value,"pageNo":pageNo},
        success : function(json) {// 生成页面标签
            //展示数据
            var resultList = json.resultList;
            var str = "";
            var array = [];
            var compareNums="";
			if(window.sessionStorage.getItem("searchIndex")!=undefined){
				compareNums = window.sessionStorage.getItem("searchIndex");
			}
            for (var i = 0; i < resultList.length; i++) {
                var onePartData = resultList[i];
                //主数据table
                var headerStr = "<tr class=\"parts-search-data\"><td class=\"search-checkbox\"><input name=\"partCheck\" type=\"checkbox\" value=\"" + onePartData.id + "\"/></td><td class=\"parts-search-img\"><img class=\"part-introduce\" src=\"images/parts-1.png\" /></td>"
                headerStr += "<td style=\"display:none\">" + onePartData.PartNumber + "</td>";
                headerStr += "<td>" + onePartData.Part_Type + "</td>";
                headerStr += "<td>" + onePartData.ITEM + "</td>";
                headerStr += "<td>" + onePartData.Manufacturer + "</td>";
                headerStr += "<td>" + onePartData.KeyComponent + "</td>";
				headerStr += "<td>" + "<a class=\"data-book\" href=\"javascript:viewPdfInPartPage('"+onePartData.Datesheet+"','"+onePartData.PartNumber+"','"+onePartData.id+"','"+onePartData.isCollection+"')\"><img src=\"images/PDF.png\" />"+"Datesheets"+"</a>" + "</td>";
				/* headerStr += "<td  style=\"display:none\"><input class=\"form-control\"  type=\"text\" value=\"1\" name=\"proNum\" class=\"\"/></td>"; */
				//根据权限及收藏信息拼接footerStr
				var footerStr="";
				var f1="<td class=\"hand-work\">";
				var f2="<div class=\"view-box\"><i class=\"fa fa-caret-square-o-down fa-lg\"></i><a  class=\"partsMore\" href=\"pages/parts/cms-parts-particulars.jsp?goMinute="+onePartData.PartNumber+"&partId="+onePartData.id+"&tempPartMark="+GetQueryString("tempPartMark")+"\">"+$.i18n.prop("proGominuBtn")+"</a></div>  ";
				//判断是否关注
				var isColl=onePartData.isCollection==true? "images/love-red.png":"images/love.png";
				//判断对比栏中是否有此元器件
				var isCompare=compareNums.indexOf(onePartData.PartNumber)!=-1? "checked=\"checked\"":"";
				
				var f3="<div class=\"view-box view-box-work\"><div class=\"left compare-label\"><input type=\"checkbox\""+isCompare+" id="+onePartData.PartNumber+" onclick=clickCheckBox(\""+onePartData.PartNumber+"\") class=\"compare-check\"/> <span>"+$.i18n.prop("contrast")+"</span></div><div class=\"left compare-view\" onclick=\"addCollection('"+onePartData.PartNumber+"',this);\"><img src=\""+isColl+"\" class=\"left\"  /><span>"+$.i18n.prop("collection")+"</span></div></div></td></tr>";
				//判断选用权限，saveToPro在ringhts.js中维护
				var isSaveToPro=saveToPro==1? "<div class=\"view-box view-box1\"><i class=\"xuanyong\"></i><a  class=\"partsMore\" onclick=\"addToProductDialog(this);\">"+$.i18n.prop("selectionBtn")+">></a></div>":"";
				//历史记录权限，historyRights在ringhts.js中维护
				var isHistoryRights=historyRights==1? "<div class=\"view-box\"><i class=\"fa fa-history fa-lg\"></i><a href=\"pages/parts/cms-parts-history.jsp?goHistory="+onePartData.PartNumber+"&tempPartMark="+GetQueryString("tempPartMark")+"\" class=\"partsHistory\">"+$.i18n.prop("history")+">></a></div>":"";
				footerStr=f1+isSaveToPro+f2+isHistoryRights+f3;
				
				str += headerStr + footerStr;
            }
            var count = json.count;
            if ($("#partDataPage").html().length == 0) {
                $("#partDataPage").pagination(count, {
                    items_per_page: 20,
                    num_edge_entries: pageNo,
                    num_display_entries: 3,
                    callback: function (pageNo, panel) {
                    	if(resultList==null){
                        	colle(pageNo);
                    	}
                    },
                    link_to: "javascript:void(0);"
                });
            }
            $("#save-title").after(str);
        }
        });
}

function deleteUserPn(){
	var checkedPn=$("input[name='partCheck']:checkbox:checked");
	if(checkedPn==undefined||checkedPn.length==0){
        layer.alert("请选中至少一条数据");
        return;
    }
	var partNumbers=new Array();
	for(var i=0;i<checkedPn.length;i++){
		var partNumber= checkedPn.eq(i).parent().parent().find("td").eq(2).html();
		partNumbers.push(partNumber);
	}
    
    $.ajax({
        url : "partComponentArrt/deleteUserPn.do",
        dataType : "json",
        cache : false,
        type : "post",
        data : {
            "partNumbers" : JSON.stringify(partNumbers)
        },
        success : function(json) {
        	layer.alert(json.message);
            $("input[name='partCheck']:checkbox:checked").parent().parent().remove();
        },
        error : function() {
        }
    });
}
</script>
</body>
</html>