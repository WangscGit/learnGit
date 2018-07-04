<%@ page language="java"   pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
     <link rel="stylesheet" href="css/cms-headerPublic.css" />
     <link rel="stylesheet" href="<%=path %>/scripts/jquery-ui-1.11.4/jquery-ui.theme.css">
    <link rel="stylesheet" href="<%=path %>/css/cms-design-process.css"/>
    <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
        <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
</head>
<body>
<%@include file="/public.jsp" %>
	<div class="containerAll">
		<div class="containerAllPage">
		<div id="main">
		<%@include file="/header.jsp"%>
		<div class="page-message">
       <h3><span class="label label-default">Now</span><a>首页</a>><a>流程管理</a>><a class="active">流程监控</a></h3>
    </div>
        <div class="table-responsive">
        <div class="flow-design-processLeft left">
             <div id="img_bag" class="ztb_nav"> 
   <a href="javascript:void(0)" onmousedown="moveTop()" style="display:block; width:30px; margin:0 auto"> <img src="images/ztb_up.png" border="0" /></a> 
   <div id="img" class="ztb_content"> 
    <div style="position:relative;"> 
     <div class="online"></div> 
     <div class="ztb_main_01"> 
      <ul class="ztb_content_01"> 
       <li class="ztb_over"><a class="ztb_con_text building" onclick="clickli(this);">建立设计 <span class="ztb_time">2018-03-7</span></a>
       </li> 
       <li class="ztb_over"><a class="ztb_con_text materialSelection" onclick="clickli(this);">物料选型</a> 
         </li> 
         <li class="ztb_on"><a class="ztb_con_text designInput" onclick="clickli(this);">设计输入</a> 
        <ul class="ztb_content_02" id="zb" style="display:block"> 
         <li class="ztb_end"><a class="theoryInput">1.原理图输入</a></li> 
         <li class="ztb_end"><a class="ruleCheck">3.规则检查及报告</a></li> 
         <li class="ztb_end"><a class="consistencyCheck">4.一致性检查及报告</a></li> 
         <li class="ztb_end"><a class="BOMtable">5.BOM表</a></li> 
         <li class="ztb_end"><a>6.打包输出</a></li> 
         <li class="ztb_active"><a>7.PCB返标</a></li> 
        </ul> </li>
        <li class="ztb_on"><a class="ztb_con_text designReview" onclick="clickli(this);">设计评审</a>
             <ul class="ztb_content_02" id="zb" style="display:block"> 
         <li><a>1.评审意见</a></li> 
         <li><a>3.评审结果</a></li> 
        </ul>
        </li>
         <li class="ztb_on"><a class="ztb_con_text functionalSimulation" onclick="clickli(this);">功能仿真</a> 
        <ul class="ztb_content_02" id="zb" style="display:block"> 
         <li><a>1.创建模型</a></li> 
         <li><a>2.创建激励</a></li>
         <li><a>3.创建磁性元器件</a></li> 
         <li><a>4.执行仿真</a></li> 
         <li><a>5.报告分析</a></li>
        </ul> </li> 
       <li><a class="ztb_con_text simulationSI" onclick="clickli(this);">预仿真（SI）</a> 
        <ul class="ztb_content_02"> 
         <li><a>1.IBIS模型验证</a></li>
         <li> <a>2.执行仿真</a></li> 
         <li> <a>3.报告分析</a></li> 
        </ul> </li>
       <li><a class="ztb_con_text simulationPI" onclick="clickli(this);">预仿真（PI）</a> 
        <ul class="ztb_content_02"> 
         <li><a>1.电容模型验证</a></li>
         <li> <a>2.执行仿真</a></li> 
         <li> <a>3.报告分析</a></li> 
        </ul> </li> 
       <li><a class="ztb_con_text PCBdesign" onclick="clickli(this);">PCB设计</a> 
        <ul class="ztb_content_02"> 
         <li><a>1.PCB设计准备</a></li>
         <li> <a>2.布局设计</a></li> 
         <li> <a>3.布线设计</a></li>
         <li> <a>4.设计比对</a></li>
         <li> <a>5.工艺检查</a></li>
         <li> <a>6.加工文件</a></li>
        </ul> </li> 
         <li> <a></a><a class="ztb_con_text designReview" onclick="clickli(this);">设计评审</a> 
        </li>
      <li><a class="ztb_con_text lastSimulation" onclick="clickli(this);">后仿真</a> 
        <ul class="ztb_content_02"> 
         <li><a>1.SI仿真</a></li>
         <li> <a>2.PI仿真</a></li> 
         <li> <a>3.串行链路仿真</a></li>
         <li> <a>4.热仿真</a></li>
         <li> <a>5.EMI/EMC仿真</a></li>
         <li> <a>6.参数提取</a></li>
        </ul> </li> 
       <li> <a></a><a class="ztb_con_text StandardizeFigure" onclick="clickli(this);">标准化出图</a> </li> 
       <li> <a></a><a class="ztb_con_text pigeonhole" onclick="clickli(this);">归档</a> </li>
          <li> <a></a><a class="ztb_con_text createDatabase" onclick="clickli(this);">建库流程</a> </li> 
      </ul> 
     </div> 
    </div> 
   </div> 
   <a href="javascript:void(0)" onmousedown="moveBottom()" style="position:absolute; bottom:0px; right:110px"> <img src="images/ztb_down.png" border="0" /></a> 
  </div> 
        </div>
        <div class="flow-design-processRight left">
 <iframe name="myiframe" id="myframe" src="pages/design-processPage/cms-design-process-build.jsp" onload="changeFrameHeight()" frameborder="0" align="right" width="100%" scrolling="no">
            <p>你的浏览器不支持iframe标签</p>
        </iframe>
        </div>
</div>
</div>
<div class="clearfix"></div>
<%@include file="/footer.jsp"%>
</div>
	</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/design-process.js"></script>
<script>
$(function(){
    $("[name='hiredate']").datepicker(
            {dateFormat: "yy年mm月dd日"}
    );
    $('.flow-view-processWindow').draggable(); 
})
</script>
</body>
</html>