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
     <link rel="stylesheet" href="<%=path %>/css/cms-design-process-materialSelection.css"/>
     <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
        <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	 <link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css" type="text/css"/>
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
</head>
<body>
      <div class="pageBox">
           <div class="top">
                 <p><img src="images/design4.png">&nbsp;物料选择</p>
                  <div class="materialwork">
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-export"></span>&nbsp;物料推送</a>
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-search"></span>&nbsp;精确查询</a>
              </div>
                 <div class="messageFind">
                   <input type="text" class="form-control left"/>
                   <a class="btn btn-info left"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</a>
              </div>
                <table class="table table-bordered table-hover topTable">
                        <thead>
                        <tr>
                                <td><input type="checkbox"/></td>
                                <td>序号</td>
                                <td>名称</td>
                                <td>类别</td>
                                <td>版本号</td>
                                <td>创建者</td>
                                <td>创建时间</td>
                        </tr>
                        </thead>
                        <tbody>
                           <tr>
                           <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                         <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                         <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                         <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                         <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                        </tbody>   
                </table>    
              </div>   
              <div class="middle">
                     <p><img src="images/design2.png">&nbsp;物料详细信息展示</p>                    
                <table class="table table-bordered table-hover middleTable">
                      <thead>
                        <tr>
                                <td><input type="checkbox"/></td>
                                <td>序号</td>
                                <td>名称</td>
                                <td>类别</td>
                                <td>版本号</td>
                                <td>创建者</td>
                                <td>创建时间</td>
                                <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                           <tr>
                                <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                                <td><a class="btn btn-xs btn-info compareBtn"><span class="glyphicon glyphicon-sort"></span>&nbsp;对比</a></td>
                        </tr>
                         <tr>
                               <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                                <td><a class="btn btn-xs btn-info compareBtn"><span class="glyphicon glyphicon-sort"></span>&nbsp;对比</a></td>
                        </tr>
                         <tr>
                                <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                                <td><a class="btn btn-xs btn-info compareBtn"><span class="glyphicon glyphicon-sort"></span>&nbsp;对比</a></td>
                        </tr>
                         <tr>
                                <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                                <td><a class="btn btn-xs btn-info compareBtn"><span class="glyphicon glyphicon-sort"></span>&nbsp;对比</a></td>
                        </tr>
                         <tr>
                               <td><input type="checkbox"/></td>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                                <td><a class="btn btn-xs btn-info compareBtn"><span class="glyphicon glyphicon-sort"></span>&nbsp;对比</a></td>
                        </tr>
                        </tbody>                                              
                </table>
              </div>
              <div class="bottom">
                     <p><img src="images/design5.png">&nbsp;对比功能</p>                    
                    <div class="more">对比功能页面展示</div>
              </div>
                 <!-- 对比弹框 -->
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
                         <dl class="hasItem" id="cmp_item_2788652" fore="0">
                                <dt><a href="cms-parts-detail.html"><img src="/cms_cloudy/images/p1.jpg" width="50" height="50"></a></dt>
                                <dd><a class="diff-item-name" href="">ECL电路</a><span class="p-price"><b class="xinghao">procesTest</b><a class="price-delete" href="">删除</a></span></dd>
                           </dl>
                           <dl class="hasItem" id="cmp_item_2788652" fore="0">
                                <dt><a href="cms-parts-detail.html"><img src="/cms_cloudy/images/p1.jpg" width="50" height="50"></a></dt>
                                <dd><a class="diff-item-name" href="">ECL电路</a><span class="p-price"><b class="xinghao">procesTest</b><a class="price-delete" href="">删除</a></span></dd>
                           </dl>
                           <dl class="item-empty"><dt>3</dt><dd>您还可以继续添加</dd></dl>
                           <dl class="item-empty"><dt>4</dt><dd>您还可以继续添加</dd></dl>
                  </div>
              </div>
            <div class="diff-operate">
                <a href="" class="btn-compare-b"><fmt:message  key="contrastBtn"/></a>
                <a class="del-items" href =""><fmt:message  key="contrastReset"/></a>
            </div>
        </div>             
    </div>
    <script>
    $("body").on("click", ".compareBtn", function () {
        $(".parts-compare-box").css("display","block");
    });
    $("body").on("click", ".compare-hidden", function () {
        $(".parts-compare-box").css("display","none");
    });
    </script>
</body>
</html>