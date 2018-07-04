<% 
String path = request.getContextPath();
String basePath=request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + path + "/";
String lang = "zh";
Object langType=request.getSession().getAttribute("lang");
if(null != langType){
	lang = langType.toString();
}
lang=lang.trim();
%>
<base href="<%=basePath %>"/>
<link rel="shortcut icon" type="image/x-icon" href="<%=path %>/images/logoico.ico" />
<link href="css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" href="css/cms-headerPublic.css"/>
<link rel="stylesheet" href="css/public.css"/>
<link rel="stylesheet" href="css/pagination.css"/>
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
<link rel="stylesheet" href="scripts/jquery.idcode/jquery.idcode.css">
<link rel="stylesheet" href="css/cms-common.css">
<link rel="stylesheet" href="font-awesome-4.7.0/css/font-awesome.min.css">
<%--引入jstl国际化与格式化标签库 --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- spring处理国际化标签 -->
<!-- 一、设置本地化对象 -->
<fmt:setLocale value="<%=lang %>"/>
<fmt:setBundle basename="messages" />
<script type="text/javascript" src="scripts/jquery-1.11.3.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui-1.11.4/jquery-ui-1.js"></script>
<script type="text/javascript" src="scripts/jquery-ui-1.11.4/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript" src="scripts/jPaginate/jquery.pagination.js"></script>
<script type="text/javascript" src="scripts/rights.js"></script>
<script type="text/javascript" src="scripts/push.js"></script>
<script type="text/javascript" src="scripts/publicResource.js"></script>
<script type="text/javascript" src="scripts/sockjs/sockjs.min.js"></script> 
<script type="text/javascript" src="scripts/login/login.js"></script> 
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="scripts/jquery.idcode/jquery.idcode.js"></script>
<script type="text/javascript" src="scripts/jquery.raty.min.js"></script>
<script type="text/javascript" src="scripts/jquery.i18n.properties-min-1.0.9.js"></script>
<!-- 异步文件上传JS -->
<script type="text/javascript" src="scripts/ajaxfileupload.js"></script>
  <!--[if lt IE 9]>
    <script type="text/javascript" src="scripts/respond.min.js"></script>
    <script type="text/javascript" src="scripts/html5shiv.min.js"></script>
    <script type="text/javascript" src="scripts/DOMAssistantCompressed-2.7.4.js"></script>
    <script type="text/javascript" src="scripts/ie-css3.js"></script>
    <![endif]-->
<script type="text/javascript">
var en='<%=lang %>';
function loadProperties(){
	     jQuery.i18n.properties({
	            name : 'strings', //资源文件名称
	            path : 'i18n/', //资源文件路径
	            mode : 'map', //用Map的方式使用资源文件中的值
	            cache :true,
	            language : en,
	            callback : function() {//加载成功后设置显示内容
	            	if(en=='en'){
	            		dynamicLoading.css("css/cms-language-en.css"); 
	            	}	            	
	            }
	     });
}
</script>