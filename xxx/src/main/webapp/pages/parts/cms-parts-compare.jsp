<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="<%=path %>/css/cms-parts-compare.css"/>
    <link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
</head>
<body>
<%@include file="/public.jsp" %>
<div class="containerAll">
    <div class="containerAllPage">
        <!---------表格---------->
        <div id="main">
        <%@include file="/header.jsp" %>
            <div class="page-message">
                <h3 id="compareNavigation"><span class="label label-default">Now</span><a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a href="pages/parts/cms-parts.jsp"><fmt:message  key="ComponentNavigator"/></a>></h3>
            </div>
            <div class="table-responsive">
                <div class="compare-accordion-body">
                    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                        <div>
                            <table class="compare-picture-table compare-table" ID="firstTr">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="add-simple-window">
            <div class="add-simple-window-header">
                <b>为你推荐</b><a>查看更多>></a>
                <button type="button" class="close add-simple-window-close" aria-label="Close"><span>&times;</span></button>
            </div>
            <table class="compare-picture-table compare-table">
                <tr>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                    <td>
                        <div class="compare-goods-add">
                            <dl>
                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                <dd>瓷介电容器</dd>
                                <dd><a href="" class="btn btn-danger">加入对比</a></dd>
                            </dl>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <!---------页尾---------->
<%@include file="/footer.jsp" %>
    </div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/partsCompare.js"></script>
<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
</body>
</html>