<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
     <link rel="stylesheet" href="css/cms-headerPublic.css"/>
    <link rel="stylesheet" href="<%=path %>/css/cms-parts-evaluate.css"/>
</head>
<body>
<%@include file="/public.jsp" %>
<div class="containerAll">
    <div class="containerAllPage">
        <!---------表格---------->
        <div id="main">
        <%@include file="/header.jsp" %>
            <div class="page-message">
                <h3><span class="label label-default">Now</span>  <a href="">元器件</a>><a href="" class="active">评价页面</a></h3>
            </div>
            <div class="table-responsive">
               <div class="evaluate-nav">
                    <ul>
                        <li class="first">全部评价（36万+）</li>
                        <li>好评（8600+）</li>
                        <li>中评（60+）</li>
                        <li>差评（80+）</li>
                        <li class="last">默认时间排序</li>
                    </ul>
                </div>
                <div class="evaluate-body left">
                    <div class="left evaluate-user">
                        <div class="evaluate-name">
                            <img src="images/eva-user.png" alt=""/>
                            <span>王*凡</span>
                        </div>
                    </div>
                    <div class="evaluate-word left">
                        <div class="star"><img src="images/starts5.png" alt=""/></div>
                        <div>东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！</div>
                        <div>
                            <span>【瓷介电容器(sdgu-376)】</span>
                            <span class="evaluate-data">2017-09-20 18:03</span>
                        </div>
                    </div>
                </div>
                <div class="evaluate-body left">
                    <div class="left evaluate-user">
                        <div class="evaluate-name">
                            <img src="images/eva-user.png" alt=""/>
                            <span>王*凡</span>
                        </div>
                    </div>
                    <div class="evaluate-word left">
                        <div class="star"><img src="images/starts5.png" alt=""/></div>
                        <div>东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！</div>
                        <div>
                            <span>【瓷介电容器(sdgu-376)】</span>
                            <span class="evaluate-data">2017-09-20 18:03</span>
                        </div>
                    </div>
                </div>
                <div class="evaluate-body left">
                    <div class="left evaluate-user">
                        <div class="evaluate-name">
                            <img src="images/eva-user.png" alt=""/>
                            <span>王*凡</span>
                        </div>
                    </div>
                    <div class="evaluate-word left">
                        <div class="star"><img src="images/starts5.png" alt=""/></div>
                        <div>东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！</div>
                        <div>
                            <span>【瓷介电容器(sdgu-376)】</span>
                            <span class="evaluate-data">2017-09-20 18:03</span>
                        </div>
                    </div>
                </div>
                <div class="evaluate-body left">
                    <div class="left evaluate-user">
                        <div class="evaluate-name">
                            <img src="images/eva-user.png" alt=""/>
                            <span>王*凡</span>
                        </div>
                    </div>
                    <div class="evaluate-word left">
                        <div class="star"><img src="images/starts5.png" alt=""/></div>
                        <div>东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！东西收到了，元器件完好，包装仔细，还会回购！</div>
                        <div>
                            <span>【瓷介电容器(sdgu-376)】</span>
                            <span class="evaluate-data">2017-09-20 18:03</span>
                        </div>
                    </div>
                </div>
                <div class="table-footer">
                            <ul class="pagination upload-page">
                                <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
                                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li><a href="#">下一页</a></li>
                            </ul>
                        </div>
            </div>
        </div>
        <!---------页尾---------->
<%@include file="/footer.jsp" %>
    </div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
</body>
</html>