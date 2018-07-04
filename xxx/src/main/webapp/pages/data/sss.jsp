<%@ page language="java" pageEncoding="utf-8"%>  
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=path %>/css/cms-headerPublic.css"/>
    <link rel="stylesheet" href="<%=path %>/css/cms-database.css"/>
    <link rel="stylesheet" href="<%=path %>/css/animate.css">
    <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.min.css">
    <script type="text/javascript" src="<%=path %>/scripts/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<%=path %>/scripts/respond.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/html5shiv.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/DOMAssistantCompressed-2.7.4.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/ie-css3.js"></script>
    <![endif]-->
<style type="text/css">  
.s1 {  
 background-color: yellow;  
}  
</style>  
</head>
<body>
<!--登录框-->
<div id="modal-login" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
        <div id="login-box-out">
            <div id="login-box">
                <span data-dismiss="modal" class="close">&times;</span>
                <div class="login-title">
                    <a><img src="<%=path %>/images/cloud.png"/></a>
                    <b>CmsCloudy</b>
                </div>
                <br/>
                <div class="with-line">
                    <div class="line"></div>
                    <b>请输入账号密码登录</b>
                    <div class="line"></div>
                </div>
                <br/>
                <div class="input-group">
                    <input type="text" placeholder="请输入账号"/><br/><br/>
                    <input type="password" placeholder="请输入密码"/><br/><br/>
                    <a class="login-btn">登录</a><br/>
                    <p>
                        <span class="left">忘记密码>></span>
                        <span class="right click-register" data-toggle="modal" data-dismiss="modal" data-target="#modal-register">点击注册>></span>
                        <b class="right">还没有cms账号？</b>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<!--注册框-->
<div id="modal-register" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
        <div id="register-box-out">
            <div id="register-box">
                <span data-dismiss="modal" class="close">&times;</span>
                <div class="login-title">
                    <a><img src="<%=path %>/images/cloud.png"/></a>
                    <b>CmsCloudy</b>
                </div>
                <br/>
                <div class="with-line">
                    <div class="line"></div>
                    <b>请输入基本信息注册</b>
                    <div class="line"></div>
                </div>
                <br/>
                <div class="input-group">
                    <input type="text" placeholder="请输入账号"/><br/><br/>
                    <input type="password" placeholder="请输入密码"/><br/><br/>
                    <input type="password" placeholder="请确认密码"/><br/><br/>
                    <input type="email" placeholder="请输入邮箱"/><br/><br/>
                    <div class="register-statement">
                        <input type="checkbox" class="register-check"/>
                        <i>我已认真阅读并接受</i>
                        <a href="#">《CmsCloudy声明》</a>
                    </div><br/>
                    <a class="register-btn">注册</a><br/>
                    <p>
                        <span class="right click-login" data-target="#modal-login" data-toggle="modal" data-dismiss="modal">直接登录>></span>
                        <b class="right">已有账号？</b>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<!--导航及下拉-->
<div class="nav-son-outbox">
<div class="nav-son-box">
    <nav class="navbar navbar-default nav-son">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%=path %>/pages/loginpage/index.jsp"><img src="<%=path %>/images/logonew.png" alt="公司logo"/></a>
            </div>

             <div class="no-message">
            <div>
                <a href="<%=path %>/pages/messagePage/cms-message.jsp">
                    <b class="">未读消息</b>
                    <a class="num-group">
                    <span class="badge-box">3</span>
                    <span class="span1"></span>
                    </a>
                </a>
            </div>
            <div>
                <a href="<%=path %>/pages/workflowPage/cms-flow.jsp">
                <b>待办任务</b>
                <a class="num-group">
                <span class="badge-box">2</span>
                <span class="span2"></span>
                </a>
                </a>
            </div>
        </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                 <ul class="nav navbar-nav index-nav left">
                <li class="active1 navLi"><a href="<%=path %>/pages/loginpage/index.jsp" hidefocus="true">首页<span class="sr-only">(current)</span></a></li>
                <li class="navLi">
                    <a href="<%=path %>/pages/parts/cms-parts.jsp"  hidefocus="true">元器件库</a>
                    <ul>
                        <li><a href="<%=path %>/pages/parts/cms-parts.jsp">常用库</a></li>
                        <li><a href="<%=path %>/pages/parts/cms-parts.jsp">基础库</a></li>
                        <li><a href="<%=path %>/pages/parts/cms-parts.jsp">电阻器</a></li>
                        <li><a href="<%=path %>/pages/parts/cms-parts.jsp">电容器</a></li>
                        <li><a href="<%=path %>/pages/parts/cms-parts.jsp">半导体分立器件</a></li>
                    </ul>
                </li>
                <li class="navLi"><a href="<%=path %>/pages/loginpage/cms-upload.jsp" hidefocus="true">产品信息</a></li>
                <li class="navLi">
                    <a href="<%=path %>/pages/loginpage/cms-user.jsp" hidefocus="true">管理模块</a>
                   <ul>
                        <li><a href="<%=path %>/pages/loginpage/cms-upload.jsp">上传管理</a></li>
                        <li><a href="<%=path %>/pages/messagePage/cms-message.jsp">消息管理</a></li>
                        <li><a href="<%=path %>/pages/loginpage/cms-user.jsp">用户管理</a></li>
                        <li><a href="<%=path %>/pages/workflowPage/cms-flow.jsp">流程管理</a></li>
                    </ul>
                </li>
                <li class="navLi">
                    <a href="<%=path %>/pages/workflowPage/cms-flow.jsp" hidefocus="true">服务支持</a>
                </li>
                <li class="navLi"><a href="<%=path %>/pages/messagePage/cms-message.jsp" hidefocus="true">关于我们</a></li>
            </ul>
                <form class="navbar-form navbar-left" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="元器件">
                    </div>
                    <button type="submit" class="btn btn-default">Search</button>
                </form>
                <ul class="login-div right">
                    <li><a data-target="#modal-register" class="btn2 btn btn-info right" data-toggle="modal">注册</a></li>
                    <li><a data-target="#modal-login" class="btn1 btn btn-danger right" data-toggle="modal">登录</a></li>
                    <div class="dropdown right">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            迪丽热巴
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>  管理员</a></li>
                            <li role="presentation" class="login-out"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>  退出</a></li>
                        </ul>
                    </div>
                </ul>
            </div>
        </div>
    </nav>
</div>
</div>
 <!---------主体---------->
<div class="containerAll">
    <div class="containerAllPage">
<!---------表格---------->
<div id="main">
    <div class="page-message">
        <h3><span class="label label-default">Now</span> <a href="index.jsp">首页</a>><a>数据库管理</a></h3>
    </div>
    <div class="table-responsive">
        <div class="dataHeader">
            <a href="#" class="btn btn-danger left dataList-btn">分类信息</a>
            <a href="javascript:fieldFunction()" class="btn btn-danger left dataField-btn">字段定义</a>
            <a href="#" class="btn btn-danger left databaseCopy-btn">数据库备份</a>
            <a href="#" class="btn btn-danger left datadelete">删除</a>
        </div>
        <div class="dataList">
        <div class="data-left left">
            <div class="data-tree left">
           <div id="jstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="j1_1" aria-busy="false">
                <ul class="jstree-container-ul jstree-children" role="group">
                    <li role="treeitem" aria-selected="false" aria-level="1" aria-labelledby="j1_1_anchor" aria-expanded="true" id="j1_1" class="jstree-node jstree-open">
                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                        <!--<a class="jstree-anchor" href="#" tabindex="-1" id="j1_1_anchor">
                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>Cadence设计文件
                        </a>-->
                        <a class="btn">Cadence设计文件</a>
                        <ul role="group" class="jstree-children" style="">
                            <li role="treeitem" data-jstree="{ &quot;opened&quot; : true }" aria-selected="false" aria-level="2" aria-labelledby="j1_2_anchor" aria-expanded="false" id="j1_2" class="jstree-node jstree-closed">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <!--<a class="jstree-anchor" href="#" tabindex="-1" id="j1_2_anchor"><i class="jstree-icon jstree-themeicon" role="presentation"></i>Capture原理图符号库
                                </a>-->
                                <a class="btn">Capture原理图符号库</a>
                                <ul role="group" class="jstree-children" style="">
                                    <li role="treeitem" aria-selected="true" aria-level="3" aria-labelledby="j1_3_anchor" id="j1_3" class="jstree-node  jstree-leaf jstree-last">
                                    <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                    <!--<a class="jstree-anchor jstree-clicked" href="#" tabindex="-1" id="j1_3_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>原理图符号库（带PSpice模型信息）</a>-->
                                    <a class="btn">原理图符号库（带PSpice模型信息）</a>
                                    </li>
                                </ul>
                            </li>
                            <li role="treeitem" data-jstree="{ &quot;opened&quot; : true }" aria-selected="false" aria-level="4" aria-labelledby="j1_4_anchor" aria-expanded="false" id="j1_4" class="jstree-node jstree-closed">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <!--<a class="jstree-anchor" href="#" tabindex="-1" id="j1_4_anchor"><i class="jstree-icon jstree-themeicon" role="presentation"></i>封装库
                                </a>-->
                                <a class="btn">封装库</a>
                                <ul role="group" class="jstree-children" style="">
                                    <li role="treeitem" aria-selected="true" aria-level="5" aria-labelledby="j1_5_anchor" id="j1_5" class="jstree-node  jstree-leaf jstree-last">
                                    <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                    <!--<a class="jstree-anchor jstree-clicked" href="#" tabindex="-1" id="j1_5_anchor">
                                        <i class="jstree-icon jstree-themeicon" role="presentation"></i>焊盘库</a>-->
                                    <a class="btn">焊盘库</a>
                                    </li>
                                </ul>
                                <ul role="group" class="jstree-children" style="">
                                    <li role="treeitem" aria-selected="true" aria-level="11" aria-labelledby="j1_11_anchor" id="j1_11" class="jstree-node  jstree-leaf jstree-last">
                                    <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                    <!--<a class="jstree-anchor jstree-clicked" href="#" tabindex="-1" id="j1_11_anchor">
                                        <i class="jstree-icon jstree-themeicon" role="presentation"></i>flash</a>-->
                                    <a class="btn">flash</a>
                                </li>
                                </ul>
                            </li>
                            <li role="treeitem" aria-selected="false" aria-level="6" aria-labelledby="j1_6_anchor" id="j1_6" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <!--<a class="jstree-anchor" href="#" tabindex="-1" id="j1_6_anchor">
                                    <i class="jstree-icon jstree-themeicon jstree-themeicon-custom" role="presentation" style="background-image: url(&quot;//jstree.com/tree-icon.png&quot;); background-position: center center; background-size: auto;"></i>
                                    STEP模型</a>-->
                                <a class="btn">STEP模型</a>
                            </li>
                            <li role="treeitem" data-jstree="{ &quot;opened&quot; : true }" aria-selected="false" aria-level="7" aria-labelledby="j1_7_anchor" aria-expanded="false" id="j1_7" class="jstree-node jstree-closed">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <!--<a class="jstree-anchor" href="#" tabindex="-1" id="j1_7_anchor"><i class="jstree-icon jstree-themeicon" role="presentation"></i>典型电路
                                </a>-->
                                <a class="btn">典型电路</a>
                                <ul role="group" class="jstree-children" style="">
                                    <li role="treeitem" aria-selected="true" aria-level="8" aria-labelledby="j1_8_anchor" id="j1_8" class="jstree-node  jstree-leaf jstree-last">
                                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                        <!--<a class="jstree-anchor jstree-clicked" href="#" tabindex="-1" id="j1_8_anchor">
                                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>DSN文件</a>-->
                                        <a class="btn">DSN文件</a>
                                    </li>
                                </ul>
                                <ul role="group" class="jstree-children" style="">
                                    <li role="treeitem" aria-selected="true" aria-level="12" aria-labelledby="j1_12_anchor" id="j1_12" class="jstree-node  jstree-leaf jstree-last">
                                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                        <!--<a class="jstree-anchor jstree-clicked" href="#" tabindex="-1" id="j1_12_anchor">
                                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>MDD文件</a>-->
                                        <a class="btn">MDD文件</a>
                                    </li>
                                </ul>
                            </li>
                           <!-- <li role="treeitem" data-jstree="{ &quot;icon&quot; : &quot;glyphicon glyphicon-leaf&quot; }" aria-selected="false" aria-level="9" aria-labelledby="j1_9_anchor" id="j1_9" class="jstree-node  jstree-leaf jstree-last">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor" href="#" tabindex="-1" id="j1_9_anchor">
                                    <i class="jstree-icon jstree-themeicon glyphicon glyphicon-leaf jstree-themeicon-custom" role="presentation"></i>
                                自定义图标类（引导）</a>
                            </li>-->
                        </ul>
                            </li>
                    <li role="treeitem" aria-selected="false" aria-level="10" aria-labelledby="j1_10_anchor" id="j1_10" class="jstree-node  jstree-leaf jstree-last">
                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                        <!--<a class="jstree-anchor" href="//www.jstree.com" tabindex="-1" id="j1_10_anchor">
                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>
                            数据手册</a>-->
                        <a class="btn">数据手册</a>
                    </li>
                    </ul>
            </div>
            </div>
        </div>
        <div class="data-right left">
                <div class="data-table-box">
                        <table class="data-more">
                        <tr class="header">
                        		<td colspan="2">基本信息</td>
                        </tr>
                        <tbody>
                        	 <tr>
                                <td><span>第一级：</span></td>
                                <td><select name="" id="" class="form-control">
                                <option value=""></option>
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                </select></td>
                            </tr>
                            <tr>
                                <td><span>第二级：</span></td>
                                <td><input type="text" class="form-control" value="" /></td>
                            </tr>
                            <tr>
                                <td><span>类型：</span></td>
                                <td><input type="text" class="form-control input-height" value="" /></td>
                            </tr>
                            <tr class="header">
                        		<td colspan="2">特殊属性</td>
                        </tr>
                             <tr>
                                <td><span>所属类：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>细类：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性1：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性2：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性3：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性4：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性5：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性6：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性7：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性8：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性9：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span>特性属性10：</span></td>
                                <td><input type="text" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <a href="#" class="btn btn-danger right">取消</a>
                                    <a href="#" class="btn btn-success right">提交</a>
                                </td>
                            </tr>
                        </tbody>
                        </table>
                </div>
        </div>
        </div>
        <div class="dataField">
             <ul class="dataField-ul">
	     <li class="first"><a href="#" class="new-field-btn">新增字段</a></li>
	     <li><a href="#">上移<span class="glyphicon glyphicon-arrow-up"></span></a></li>
	     <li><a href="#">下移<span class="glyphicon glyphicon-arrow-down"></span></a></li>
            </ul>
            <table class="table table-striped table-hover table-bordered" id="dataField-table">
                <thead>
                <tr role="row">
                    <th>
                        IsUesd
                    </th>
                    <th>
                        字段名称
                    </th>
                    <th>
                        显示名称
                    </th>
                    <th>
                        备注
                    </th>
                    <th>
                        字段属性
                    </th>
                    <th>
                        字段分组
                    </th>
                    <th>
                        IsDisplay
                    </th>
                    <th>
                        IsUpdate
                    </th>
                    <th>
                        IsAudit
                    </th>
                    <th>
                        IsSearch
                    </th>
                    <th>
                        IsMatch
                    </th>
                    <th>
                        编辑
                    </th>
                </tr>
                </thead>

                <tbody id="showField">
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                        <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                        <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                         <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                         <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                         <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                         <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
<!--                 <tr> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td>kjscbblsac</td> -->
<!--                     <td>物料编码</td> -->
<!--                     <td>元器件物料编码</td> -->
<!--                     <td>必选</td> -->
<!--                     <td>基本组</td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td><input type="checkbox"/></td> -->
<!--                     <td class="edit-td"> -->
<!--                        <div class=""> -->
<!--                             <a class="btn btn-success btn-xs data-change"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                             <a class="btn btn-info btn-xs data-delete"><span class="glyphicon glyphicon-edit"></span>  删除</a> -->
<!--                         </div> -->
<!--                     </td> -->
<!--                 </tr> -->
                </tbody>
            </table>
</div>
        <div class="databaseCopy">
             <div class="databaseCopy-left left"><b class="left">名字：</b><input type="text" class="form-control left"/><a href="" class="btn btn-info datacopy-save left">保存</a></div>
              <div class="databaseCopy-right left"><p>现有数据库备份</p>
              <table class="table table-striped table-hover table-bordered">
	<tr>
		<td><span>名称：</span></td>
		<td><span>备份者：</span></td>
		<td><span>备份时间：</span></td>
		<td><span>状态：</span></td>
		<td><span>操作：</span></td>
	</tr>
	<tr>
		<td>aaaaa</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>aaaaa</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>aaaaa</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>aaaaa</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>aaaaa</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>chbuc</td>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-warning btn-xs">删除</a>
		</td>
	</tr>
</table>
</div>
             <div>
             </div>
         </div>
        <div class="new-field">
                  <table class="new-field-table1 left">
            <tr>
            <td><span><b>*</b>名称：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="fieldName" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b>显示名称：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="showName" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b>类型：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="dataType" value="nvarchar(500)"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>备注：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="description" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b>字段属性：</span></td>
            <td colspan="2"><input type="text" class="form-control"  value="noValue"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>字段分组：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="noValue"/></td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span><b>*</b>IsUesd：<input type="checkbox" name="isUesd" id = "isUesd" value="isUesd"/></span>
                <span><b>*</b>IsDisplay：<input type="checkbox" name="isDisplay"/></span>
                <span><b>*</b>IsUpdate：<input type="checkbox" name="isUpdate"/></span>
            </td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span><b>*</b>IsAudit：<input type="checkbox" name="isAudit" /></span>
                <span><b>*</b>IsSearch：<input type="checkbox" name="isSearch"/></span>
                <span><b>*</b>IsMatch：<input type="checkbox" name="isMatch"/></span></td>
        </tr>
        <tr>
            <td colspan="3">
                <a href="javascript:newFieldReset()" class="btn btn-danger right">取消</a>
                <a href="javascript:newField()" class="btn btn-success right">确定</a>
            </td>
        </tr>
    </table>
                <div class="new-field-table2 right">
                <b>待定变更表</b>
               <table class="table table-striped table-hover table-bordered">
	<tr>
		<td><span>项目类型：</span></td>
		<td><span>属性名称：</span></td>
		<td><span>数据类型：</span></td>
		<td><span>操作：</span></td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
	<tr>
		<td>347687@qq.com</td>
		<td>2017/9/14</td>
		<td>成功</td>
		<td>
			<a href="" class="btn btn-danger btn-xs">还原</a>
			<a href="" class="btn btn-info btn-xs">删除</a>
		</td>
	</tr>
</table>
<a href="" class="btn btn-info">提交到数据库</a>
</div>
            </div>  
    </div>
</div>
<div class="dataChange-window">
    <div class="dataChange-window-header bg-danger">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  修改字段</a>
        <button type="button" class="close dataChange-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <table class="dataChange-window-table">
        <tr>
            <td><span><b>*</b>名称：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="电阻器" readonly/></td>
        </tr>
        <tr>
            <td><span><b>*</b>显示名称：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="金属电阻器"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>类型：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="电阻"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>备注：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="物料编码"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>字段属性：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="必选"/></td>
        </tr>
        <tr>
            <td><span><b>*</b>字段分组：</span></td>
            <td colspan="2"><input type="text" class="form-control" value="基本组"/></td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span><b>*</b>IsUesd：<input type="checkbox"/></span>
                <span><b>*</b>IsDisplay：<input type="checkbox"/></span>
                <span><b>*</b>IsUpdate：<input type="checkbox"/></span>
            </td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span><b>*</b>IsAudit：<input type="checkbox"/></span>
                <span><b>*</b>IsSearch：<input type="checkbox"/></span>
                <span><b>*</b>IsMatch：<input type="checkbox"/></span></td>
        </tr>
        <tr>
            <td colspan="3">
                <a href="" class="btn btn-danger right">取消</a>
                <a href="" class="btn btn-success right">确定</a>
            </td>
        </tr>
    </table>
</div>
<!---------页尾---------->
<div id="foot">
    <div class="footnav">
        <a class="first" href="/">首页</a>
        <span class="sep">|</span>
        <a href="/w/p/">产品中心</a>
        <span class="sep">|</span>
        <a href="/w/p/cms/">CMS</a>
        <span class="sep">|</span>
        <a href="/w/p/cadence-orcad/">Cadence OrCAD</a>
        <span class="sep">|</span>
        <a href="/w/p/cadence-allegro/">Cadence Allegro</a>
        <span class="sep">|</span>
        <a href="/w/p/gems/">电磁仿真</a>
        <span class="sep">|</span>
        <a href="/w/solutions/">解决方案</a>
        <span class="sep">|</span>
        <a href="/w/service/">服务支持</a>
        <span class="sep">|</span>
        <a href="/w/news/">新闻资讯</a>
        <span class="sep">|</span>
        <a href="/w/about/">关于我们</a>
    </div>
    <div class="copyright">
        <p style="text-align: center;">Copyright © 2005-2015 <strong>北京迪浩永辉技术有限公司</strong>  版权所有</p>
        <p style="text-align: center;">地址：北京市石景山区时代花园南路17号茂华大厦二层204室 &nbsp; &nbsp; 销售咨询热线：(86) 10-88552600 &nbsp;传真<span style="text-align: center; text-indent: 24px;">：</span>(86) 10-68868267</p>
        <p style="text-align: center;">销售邮箱：sales@bjdihao.com.cn &nbsp; &nbsp; &nbsp;技术服务邮箱：tech_server@bjdihao.com.cn&nbsp;</p>
        <p style="text-align: center;">京ICP备05027861号-2</p></div>
</div>
</div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/fieldDefinition.js"></script>
<script>
$(function () { $('#jstree1').jstree(); });
</script>
</body>
</html>