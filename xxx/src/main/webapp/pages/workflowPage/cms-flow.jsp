<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
     <link rel="stylesheet" href="css/cms-headerPublic.css"/>
    <link rel="stylesheet" href="css/cms-flow.css"/>
   <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.css">
    <link rel="stylesheet" href="scripts/jquery-ui-1.11.4/jquery-ui.theme.css"> 
    <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
</head>
<body>
 <%@include file="/public.jsp" %>
 <!---------主体---------->
<div class="containerAll">
    <div class="containerAllPage">
<!---------表格---------->
<div id="main">
 <%@include file="/header.jsp" %>
    <div class="page-message">       <h3><span class="label label-default">Now</span><a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a class="active">流程设计</a></h3>
    </div>   
    <div class="table-responsive">
    <div class="flow-parts">
             <div class="left" id="treeDiv">
                    <ul id="flowTree" class="ztree"></ul>
                </div>
        </div>
        <div class="flow-parts-right">
        <div class="flow-design-head">
            <div class="flow-design-head-nav">
                <div class="Btn1 left">
                <a class="btn btn-xs btn-danger flow-design-online" id="processDes"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="flow-online"/></a>
                </div>
<!--                 <div class="Btn2 left"> -->
<%--                 <a class="btn btn-xs flow-design-setting"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="flow-Setup"/></a> --%>
<!--                 <ul class="flow-design-ul"> -->
<!--                        <li><a onclick="settingFormType('0')">元器件申请</a></li> -->
<!--                 </ul> -->
<!--                 </div> -->
            </div>
            <div class="flow-design-head-input">
                <input class="flow-design-search form-control"  placeholder="<fmt:message  key="flow-pleaseholder1"/>" id="searchMsg"/><a class="btn btn-danger" onclick="initHtml()"><fmt:message  key="serachBtn"/></a>
            </div>
        </div>
        <table class="table table-bordered table-hover flow-table">
            <thead class="table-title">
        <tr>
             <td><input type="checkbox" id="checkAll" onclick="checkAll()"></td>
                <td><fmt:message  key="upload-seqno"/></td>
                <td><fmt:message  key="template-name"/></td>
                <td><fmt:message  key="flow-key"/></td>
                <td><fmt:message  key="category"/></td>
                <td><fmt:message  key="user-createror"/></td>
                <td><fmt:message  key="user-createtime"/></td>
<%--                 <td><fmt:message  key="flow-state"/></td> --%>
<!--          <td>生产状态</td> -->
                <td><fmt:message  key="flow-version"/></td>
                <td class="table-title-work"><fmt:message  key="operationBtn"/></td>
            </tr>
            </thead>
            <tbody id="processBasicList">
            
            </tbody>
        </table>
        <div class="table-footers">
        <div class="pagination" id="partDataPage"></div>
        </div>
        </div>
    </div>
</div>
<div class="flow-design-window">
    <div class="flow-design-window-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="flow-new"/></a>
        <button type="button" class="close flow-design-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
        <table class="flow-design-window-table">
            <tr>
                <td><span><b>*</b><fmt:message  key="flow-name"/>：</span></td>
                <td><input type="text" id="workFlowModelName" name="workFlowModelName" class="form-control"/></td>
            </tr>
             <tr>
                <td><span><b>*</b><fmt:message  key="category"/>：</span></td>
                <td>
                  <select id="formType" class="form-control">
                        <option value="0">元器件申请</option>
                   </select>
                </td>
             </tr>
            <tr>
                <td><span><b></b><fmt:message  key="flow-Describe"/>：</span></td>
                <td><input type="text" id="workFlowDescription" name="workFlowDescription" class="form-control flow-design-window-input"/></td>
            </tr>
            <tr>
                <td colspan="2">
                <a class="btn btn-gray left" href="javascript:createmodel()"><fmt:message  key="saveBtn"/></a>
                </td>
            </tr>
        </table>
</div>
<!-- 配置弹窗 -->
<div class="flow-allocation-window">
    <div class="flow-allocation-window-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="flow-peizhi"/></a>
        <button type="button" class="close flow-allocation-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-allocation-window-body">
             <div><span class="left">审批节点:</span>
             <select class="form-control left" id="processTask" onchange="getProcessConfig();">
             </select>
              <a class="btn btn-gray left" onclick="saveProcessConfig();">保存</a>
             </div>
             <table class="table table-bordered table-hover allocation-table">
                    <tr>
                            <td><input type="checkbox" id="normalAttrs"/></td>
                            <td><span>基本属性</span></td>
                      </tr>
                       <tr>
                            <td><input type="checkbox" id="qualityAttrs"/></td>
                            <td><span>质量属性</span></td>
                      </tr>
                       <tr>
                            <td><input type="checkbox" id="designAttrs"/></td>
                            <td><span>设计属性</span></td>
                      </tr>
                       <tr>
                            <td><input type="checkbox" id="purchaseAttrs"/></td>
                            <td><span>采购属性</span></td>
                      </tr>
             </table>
    </div>    
</div>
<!---------页尾---------->
<%@include file="/footer.jsp" %>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/workFlowJavascript.js"></script>
</body>
<script>
$("[name='hiredate']").datepicker(
{dateFormat: "yy年mm月dd日"}
);
 $("[name='hiredate']").datepicker($.datepicker.regional['zh-CN']);
$(function(){
	flowTree();
	initHtml();
	loadProcessCategory();//流程类别加载
    $('.flow-design-window,.flow-design-fenleiwindow,.flow-allocation-window').draggable();
	/*判断是否有placeholder属性*/
 	function isPlaceholder(){  
        var input = document.createElement('input');  
        return "placeholder" in input;  
    }  
	if( isPlaceholder() ) {
	   	console.log("1")  
	}else {
		console.log("2");  
		if( $('.flow-design-search').val()=="" ){
			$('.flow-design-search').css({
	    		"color" : "#ccc"  
			})  
			$('.flow-design-search').val("请输入流程信息");  
		}  
		$('.flow-design-search').focus(function () {
			if( $(this).val()=="请输入流程信息" ){  
	    		$(this).val("");  
	    	}  
	    	$('.flow-design-search').css({  
	        	"color" : "#fff"  
	    	})  
		})    
		$('.flow-design-search').blur(function () {
			if( $(this).val()=="" ){
				$(this).val("请输入流程信息");  
				$('.flow-design-search').css({
	                "color" : "#ccc"  
	            })  
	    	}else {
	    		$('.flow-design-search').css({  
	        		"color" : "#fff"  
	        	})  
	    }  
		}) 
       }
})
</script>
</body>
</html>