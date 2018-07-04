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
     <link rel="stylesheet" href="<%=path %>/css/cms-design-process-createDatabase.css"/>
     <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/jquery.SuperSlide.2.1.1.js"></script>
        <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
</head>
<body>
      <div class="pageBox">
           <div class="top">
                 <p><img src="images/design1.png">&nbsp;申请新元器件</p>                                 
                  <div>建库流程</div>   
              </div>                   
              <div class="bottom">
                     <p><img src="images/design2.png">&nbsp;流程图</p>
                     <div>
                                流程图 
                     </div>                  
              </div>                  
    </div>
    <script type="text/javascript">jQuery(".slideTxtBox").slide({trigger:"click"}); </script>
</body>
</html>