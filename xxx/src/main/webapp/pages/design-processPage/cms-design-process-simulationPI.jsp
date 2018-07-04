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
     <link rel="stylesheet" href="<%=path %>/css/cms-design-process-simulationPI.css"/>
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
                 <p><img src="images/design6.png">&nbsp;项目信息</p>                                 
                <table class="table table-bordered table-hover topTable">
                           <tr>
                                  <td><span>名称：</span></td>
                                  <td>XXXXX项目</td>
                                  <td><span>状态：</span></td>
                                  <td>已审核</td>
                                  <td><span>发起人：</span></td>
                                  <td>admin</td>
                           </tr>
                           <tr>
                                  <td><span>发起时间：</span></td>
                                  <td>2018/3/8</td>
                                  <td><span>原理图输入：</span></td>
                                  <td></td>
                                  <td><span>设计源文件：</span></td>
                                  <td>
                                         <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-save"></span>&nbsp;保存</a>
                                         <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;下载</a>
                                  </td>
                           </tr>                        
                </table>    
              </div>                   
              <div class="bottom">
                     <p><img src="images/design8.png">&nbsp;仿真报告</p>
                     <div>
                     <a><span class="glyphicon glyphicon-list-alt"></span>&nbsp;仿真报告;</a> 
                     <a><span class="glyphicon glyphicon-list-alt"></span>&nbsp;仿真报告;</a>
                     <a><span class="glyphicon glyphicon-list-alt"></span>&nbsp;仿真报告;</a> 
                     </div>                  
              </div>                  
    </div>
    <script type="text/javascript">jQuery(".slideTxtBox").slide({trigger:"click"}); </script>
</body>
</html>