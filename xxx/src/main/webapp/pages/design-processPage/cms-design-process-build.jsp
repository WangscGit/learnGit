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
    <link rel="stylesheet" href="<%=path %>/css/cms-design-process-build.css"/>
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
             <p><img src="images/design1.png">&nbsp;创建信息</p>
              <div class="creatework">
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-tags"></span>&nbsp;自定义项目</a>
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;增加</a>
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-edit"></span>&nbsp;编辑</a>
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</a>
              </div>
                <table class="table table-bordered table-hover topTable">
                        <tr>
                                <td><span>工具列表：</span></td>
                                <td></td>
                                <td><span>上传电路spec定义：</span></td>
                                <td class="spec"><a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-upload"></span>&nbsp;上传
                                         <input type="file" class="uploadspec"/></a>
                                         <b>(支持ppt,word,pdf格式)</b>
                                 </td>
                        </tr>
                        <tr>
                                <td><span>项目类型：</span></td>
                                <td><select class="form-control">
                                             <option>电源类</option>
                                             <option>高速板卡类</option>
                                              <option>高速板卡类</option>
                                              <option>驱动模块</option>
                                              <option>显示类</option>
                                              <option>转接板</option>
                                </select></td>
                                <td><span>设计类型：</span></td>
                                <td><select class="form-control">
                                              <option>通用PCB设计</option>
                                              <option>数模混合设计仿真</option>
                                              <option>高速PCB设计</option>
                                </select></td>
                        </tr>
                </table>    
              </div>   
              <div class="middle">
                     <p><img src="images/design2.png">&nbsp;项目展示</p>
                     <div class="creatework">
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-star"></span>&nbsp;当前项目</a>
                   <a class="btn btn-xs btn-info"><span class="glyphicon glyphicon-time"></span>&nbsp;历史项目</a>
              </div>
                     <div class="messageFind">
                   <input type="text" class="form-control left" placeholder="请输入名称或类型"/>
                   <a class="btn btn-info left"><span class="glyphicon glyphicon-search"></span>&nbsp;查询</a>
              </div>
                <table class="table table-bordered table-hover middleTable">
                      <thead>
                        <tr>
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
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
                                <td>1</td>
                                <td>项目一</td>
                                <td>pcb设计</td>
                                <td>3</td>
                                <td>admin</td>
                                <td>2018/3/2</td>
                        </tr>
                         <tr>
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
              <div class="bottom">
                     <p><img src="images/design3.png">&nbsp;公共电路模块展示</p>                    
                    <div class="more">公共电路模块展示</div>                
              </div>             
    </div>
</body>
</html>