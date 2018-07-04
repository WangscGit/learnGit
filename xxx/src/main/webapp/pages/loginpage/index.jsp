<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
     <link rel="stylesheet" href="css/index.css"/>
     <script type="text/javascript" src="scripts/jquery.lettering.js"></script>
	<script type="text/javascript" src="scripts/jquery.textillate.js"></script>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<!--推送框星级显示-->
     <script type="text/javascript">
    $(function(){    
    });
    function createXMLHttpRequest(){
        if(window.XMLHttpRequest){
            XMLHttpR = new XMLHttpRequest();
        }else if(window.ActiveXObject){
            try{
                XMLHttpR = new ActiveXObject("Msxml2.XMLHTTP");
            }catch(e){
                try{
                    XMLHttpR = new ActiveXObject("Microsoft.XMLHTTP");
                }catch(e){
                }
            }
        }
    }
    function test(){
		document.location.href='<%=path %>/model/deploy/1.do';
    }
    function sendRequest(url){
    	url ="/cms_cloudy/pages/loginpage/cms-1.jsp";
        createXMLHttpRequest();
        XMLHttpR.open("post",url,true);
        XMLHttpR.setRequestHeader("Content-Type","text/html;charset=utf-8");
        XMLHttpR.onreadystatechange = processResponse;
        XMLHttpR.send(null);
    }
    function processResponse(){
        if(XMLHttpR.readyState ==4 && XMLHttpR.status == 200){
            document.write(XMLHttpR.responseText);
        }
    }
    </script>
</head>
<body>
<%@include file="/public.jsp" %>
<!---------表格---------->
<div id="main">
<%@include file="/header.jsp" %>
<div class="indexBanner">
     <div class="indexBannerImage">
          <div class="BannerImageZ">
                  <div class="soso-box">
    <div class="soso-word"><fmt:message  key="cmsMain"></fmt:message></div>
    <br>
        <input type="text" class="soso left" placeholder="<fmt:message  key="indexSearchInp"></fmt:message>" id="searchInput"/>
        <div class="soso-btn right"><a class="btn" onclick="searchFor();"><fmt:message  key="indexSearchBtn"/></a><a class="btn searchBtn" onclick="searchFor();"><img src="images/index-search.png"></a></div>
        <div class="clear"></div>
        <p class="soso-example"></p>
    </div>
          </div>
     </div>
</div>
<div class="table-responsive">
<div id="main1">
       <p class="intr1"><b><fmt:message  key="index-P1"/></b></p>
       <p class="intr2"><fmt:message  key="index-P2"/></p>
       <div class="intrBox">
               <div class="left first">
                        <img src="<%=path %>/images/logoSmall.png">
                        <p><fmt:message  key="index-P3"/></p>
               </div>
               <div class="left">
                        <img src="<%=path %>/images/logoSmall.png">
                        <p><fmt:message  key="index-P4"/></p>
               </div>
               <div class="left">
                        <img src="<%=path %>/images/logoSmall.png">
                        <p><fmt:message  key="index-P5"/></p>
               </div>
       </div>
</div>
<div class="clear"></div>
<div id="main2">
        <p class="intr1"><fmt:message  key="index-P6"/></p>
<div class="show1">
               <div class="world left">
               			<p class="first"><b><a href="pages/parts/cms-parts.jsp"><fmt:message  key="index-P7"/></a></b></p>
               			<br/>
               			<p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P8"/></p>               			             			      			 
               			 <!-- <br> -->
               			 <p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P9"/></p>            			
               </div>
               <div class="show-img left">
                    <a href="pages/parts/cms-parts.jsp"><img src="images/show11.png"></a>
               </div>
        </div>
         <div class="show2">
                <div class="show-img left">
                    <a href="javascript:goMinuJsp()"><img src="<fmt:message  key="index-minuPng"/>"></a>
                 </div>
                  <div class="world left">
               			<p class="first"><b><a href="javascript:goMinuJsp()"><fmt:message  key="index-P10"/></a></b></p>
               			<br>
               			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P11"/></p>              			          			
               </div>
        </div>
              <div class="show3">
                   <div class="world left">
               			<p class="first"><b><a href="pages/statisticPage/cms-statistic-Dullmaterial.jsp"><fmt:message  key="index-P12"/></a></b></p>
               			<br>
               			<p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P13"/></p>               			
               			<br>
               			<p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P14"/></p>        			
                    </div>
               <div class="show-img left">
                    <a href="pages/statisticPage/cms-statistic-Dullmaterial.jsp"><img src="images/show33.png"></a>
               </div>
        </div>
        <div class="show4">
         <div class="show-img left">
                    <a href="javascript:goProductJsp()"><img src="<fmt:message  key="index-proPng"/>"></a>
               </div>
                   <div class="world left">
               			<p class="first"><b><a href="javascript:goProductJsp()"><fmt:message  key="index-P15"/></a></b></p>
               			<br>
               			<p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P16"/></p>
               			<!-- <br> -->
               			  <p>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message  key="index-P17"/></p>               			             			
                    </div>            
        </div>
</div>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script> 
<!---------页尾---------->
<%@include file="/footer.jsp" %>
<script>
$(function () {
	$('.soso-intro').textillate({
		initialDelay: 1800, 	//设置动画开始时间
		in: { effect: 'flipInX'	//设置动画名称
		}
	});
	$('.soso-word').textillate({ in: { effect: 'rollIn' } });
	$('.soso-btn').hover(function(){
		$(this).children(".searchBtn").css("display","block").stop().css({right: -150}).animate({right: 0}, 800);
	},function(){
		$(this).children(".searchBtn").stop().css({right: 0}).animate({right: -150}, 800);
	})
	//搜索框回车事件
	$('#searchInput').keydown(function(e){
		e = arguments.callee.caller.arguments[0]||window.event;
		if(e.keyCode==13){
			searchFor();
		}
	});
});
$('.phone-img').addClass('animated infinite flash');
$('.phone-img').hover(function(){
	$(this).removeClass('animated flash')
},function(){
	$(this).addClass('animated infinite flash')
})
$('.btnClose').hover(function(){
	$(this).addClass('animated rotateIn')
},function(){
	$(this).removeClass('animated rotateIn')
})
</script>
 <script type="text/javascript">
 /**首页点击图片跳转至详情页**/
 function goMinuJsp(){
	 $.ajax({
			url : getContextPathForWS()+'/partComponent/selectPartGopage.do',
			type : 'post',
			dataType : 'json',
			cache : false,
			success : function(json) {
				if(null == json || "" == json){
					return;
				}else{
					window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-particulars.jsp?goMinute="+json[0].PartNumber+"&tempPartMark=null&partId="+json[0].id+"";
				}
			},
			error : function() {
				layer.alert("服务器异常，请联系管理员！");
			}
		})
 }
 /**首页点击图片跳转至产品信息页**/
 function goProductJsp(){
	   $.ajax({
		   url: getContextPathForWS()+"/login/hasLogined.do",
		   cache: false,
		   dataType: "json",
		   success: function(json){
			   if(json.result == "yes"){
				   window.location.href=getContextPathForWS()+"/pages/productPage/cms-productBom.jsp";
			   }else{
				   layer.alert("请先登录!");
			   }
		   },
		   error: function(){
			   layer.alert("数据连接异常,注册失败！");
		   }
 })
 }
$(document).ready(function(){
	    initIndexSearch();/**首页热门搜索初始化**/		
	    initLogin();//直接登录
}); 
$(window).load(function() {//頁面加載完成后 執行事件
	 $("#modal-search").hide();
	    $(".searchInput").hide();
});
</script> 
</html>
