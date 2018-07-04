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
    <link rel="stylesheet" href="<%=path %>/css/cms-flow-daiLi.css"/>
    <link rel="stylesheet" href="<%=path %>/js-tree/dist/themes/default/style.css">
    <script type="text/javascript" src="<%=path %>/js-tree/dist/jstree.min.js"></script>
</head>
<body>
<%@include file="/public.jsp" %>
	<div class="containerAll">
		<div class="containerAllPage">
		<div id="main">
		<%@include file="/header.jsp"%>
		<div class="page-message">
    <h3><span class="label label-default">Now</span> <a href="">首页</a>><a href="">流程管理</a>><a href="" class="active">流程代理</a></h3>
</div>
        <div class="table-responsive">
<div class="flow-daili-head">
    <div class="flow-daili-head-input">
        <table>
            <tr>
                <td>
                    <span>查询标题：</span><input type="text" class="form-control"/>
                </td>
                <td>
                    <span>授权人：</span><input type="text" class="form-control"/>
                </td>
                <td>
                    <span>是否有效：</span>
                    <select name="" id="" class="form-control">
                        <option value="">请选择</option>
                        <option value="">有效</option>
                        <option value="">无效</option>
                    </select>
                </td>
            </tr>
            <tr>
            	 <td>
                    <span>代理类型：</span>
                    <select name="" id="" class="form-control">
                        <option value="">请选择</option>
                        <option value="">待办</option>
                        <option value="">代理</option>
                        <option value="">转办</option>
                        <option value="">沟通</option>
                        <option value="">流转</option>
                    </select>
                </td>
            </tr>
        </table>
        <div class="flow-daili-head-nav">
        <a class="btn flow-daili-head-soso"><span class="glyphicon glyphicon-search view-soso"></span>  搜索</a>
        <a class="btn flow-daili-head-add"><span class="glyphicon glyphicon-plus view-soso"></span>  添加</a>
        <a class="btn flow-daili-head-reset"><span class="glyphicon glyphicon-repeat view-soso"></span>  重置</a>
    </div>
    </div>
</div>
<div class="flow-daili-table-box">
    <table class="table table-bordered table-hover daili-table">
        <thead class="table-title">
        <tr class="success">
            <td class="edit-td"><input type="checkbox"/></td>
            <td>序号</td>
            <td>标题</td>
            <td>授权人姓名</td>
            <td>代理人</td>
            <td>开始时间</td>
            <td>结束时间</td>
            <td>是否有效</td>
            <td>代理类型</td>
            <td>操作</td>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
           <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                   <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                   <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
           <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
           <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
             <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
            <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        <tr>
            <td class="edit-td"><input type="checkbox"/></td>
            <td>1</td>
            <td>1234567</td>
            <td>1234567</td>
            <td>lili</td>
            <td>2017/7/21</td>
            <td>已完成</td>
            <td>有效</td>
            <td>1.0</td>
             <td class="edit-td edit-td-btn">
               <div class="daili-btnGroup1" style="display:none">
                                <a class="btn btn-primary btn-xs daili-table-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-success btn-xs daili-table-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a>
               </div>
               <div class="daili-btnGroup2">
                <a class="btn btn-info btn-xs daili-table-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                <a class="btn btn-warning btn-xs daili-table-more"><span class="glyphicon glyphicon-star"></span>  详情</a>
                <a class="btn btn-success btn-xs daili-table-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
               </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</div>
<div class="flow-daili-table-footer">
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
<div class="flow-daili-addWindow">
    <div class="flow-daili-addWindow-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  添加代理</a>
        <button type="button" class="close flow-daili-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-daili-addWindow-header2">
        <a class="btn btn-info"><span class="glyphicon glyphicon-save"></span>  保存</a>
        <a class="btn btn-warning"><span class="glyphicon glyphicon-arrow-left"></span>  返回</a>
    </div>
    <div class="flow-daili-window-body">
        <div class="flow-daili-window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span>代理类型：</span></td>
                    <td>
                        <input type="radio"/>全权代理
                        <input type="radio"/>部分代理
                        <input type="radio"/>条件代理
                    </td>
                </tr>
                <tr>
                    <td><span>标题：</span></td>
                    <td><input type="text" class="form-control input-long"/></td>
                </tr>
                <tr>
                    <td><span>授权人姓名：</span></td>
                    <td>
                        <input type="text" class="form-control input-long"/>
                        <a class="btn btn-warning btn-xs flow-daili-window-chooseUser1Btn">选择</a>
                    </td>
                </tr>
                <tr>
                    <td><span>开始日期：</span></td>
                    <td><input type="text" class="form-control input-long" name='hiredate'/></td>
                </tr>
                <tr>
                    <td><span>结束日期：</span></td>
                    <td><input type="text" class="form-control input-long" name='hiredate'/></td>
                </tr>
                <tr>
                    <td><span>是否有效：</span></td>
                    <td>
                        <input type="radio"/>禁止
                        <input type="radio"/>启用
                    </td>
                </tr>
                <tr>
                    <td><span>代理人：</span></td>
                    <td>
                        <input type="text" class="form-control input-long"/>
                        <a class="btn btn-warning btn-xs flow-daili-window-chooseUser2Btn">选择</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="flow-daili-editWindow">
    <div class="flow-daili-editWindow-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  添加代理</a>
        <button type="button" class="close flow-daili-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-daili-editWindow-header2">
        <a class="btn btn-info"><span class="glyphicon glyphicon-save"></span>  保存</a>
        <a class="btn btn-warning"><span class="glyphicon glyphicon-arrow-left"></span>  返回</a>
    </div>
    <div class="flow-daili-window-body">
        <div class="flow-daili-window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span>代理类型：</span></td>
                    <td>
                        <input type="radio"/>全权代理
                        <input type="radio"/>部分代理
                        <input type="radio"/>条件代理
                    </td>
                </tr>
                 <tr>
                    <td><span>序号：</span></td>
                    <td><input type="text" class="form-control input-long"/></td>
                </tr>
                <tr>
                    <td><span>标题：</span></td>
                    <td><input type="text" class="form-control input-long"/></td>
                </tr>
                <tr>
                    <td><span>授权人姓名：</span></td>
                    <td>
                        <input type="text" class="form-control input-long"/>
                        <a class="btn btn-warning btn-xs flow-daili-window-chooseUser1Btn">选择</a>
                    </td>
                </tr>
                <tr>
                    <td><span>开始日期：</span></td>
                    <td><input type="text" class="form-control input-long" name='hiredate'/></td>
                </tr>
                <tr>
                    <td><span>结束日期：</span></td>
                    <td><input type="text" class="form-control input-long" name='hiredate'/></td>
                </tr>
                <tr>
                    <td><span>是否有效：</span></td>
                    <td>
                        <input type="radio"/>禁止
                        <input type="radio"/>启用
                    </td>
                </tr>
                <tr>
                    <td><span>代理人：</span></td>
                    <td>
                        <input type="text" class="form-control input-long"/>
                        <a class="btn btn-warning btn-xs flow-daili-window-chooseUser2Btn">选择</a>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="flow-daili-moreWindow">
    <div class="flow-daili-moreWindow-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  详情信息</a>
        <button type="button" class="close flow-daili-moreWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-daili-morewindow-body">
        <div class="flow-daili-morewindow-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                		<td><span><b>*</b>标题：</span></td>
                		<td>河南</td>
                </tr>
                <tr>
                		<td><span><b>*</b>授权人ID：</span></td>
                		<td>1</td>
                </tr>
                  <tr>
                		<td><span><b>*</b>授权人姓名：</span></td>
                		<td>系统管理员</td>
                </tr>
                <tr>
                		<td><span><b>*</b>开始生效时间：</span></td>
                		<td>2017.5.18</td>
                </tr>
                  <tr>
                		<td><span><b>*</b>结束日期：</span></td>
                		<td>2017.8.22</td>
                </tr>
                <tr>
                		<td><span><b>*</b>是否有效：</span></td>
                		<td>no</td>
                </tr>
                  <tr>
                		<td><span><b>*</b>代理人ID：</span></td>
                		<td>admin</td>
                </tr>
                <tr>
                		<td><span><b>*</b>代理人：</span></td>
                		<td>1</td>
                </tr>
                  <tr>
                		<td><span><b>*</b>流程定义Key：</span></td>
                		<td>sfasf</td>
                </tr>
                <tr>
                		<td><span><b>*</b>代理类型：</span></td>
                		<td>条件代理</td>
                </tr>
                <tr>
                		<td><span><b>*</b>创建人ID：</span></td>
                		<td>admin</td>
                </tr>
                <tr>
                		<td><span><b>*</b>创建人时间：</span></td>
                		<td>2016.9.1</td>
                </tr>
                  <tr>
                		<td><span><b>*</b>创建者所属组织ID：</span></td>
                		<td>00000020488</td>
                </tr>
                <tr>
                		<td><span><b>*</b>更新人ID：</span></td>
                		<td></td>
                </tr>
                <tr>
                		<td><span><b>*</b>更新时间：</span></td>
                		<td></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<div class="flow-daili-window-chooseUser1">
    <div class="flow-daili-window-chooseUser1-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  用户选择</a>
        <button type="button" class="close flow-daili-window-chooseUser1-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <input type="text" class="form-control"/>
        <a class="btn btn-warning"><span class="glyphicon glyphicon-search"></span>  搜索</a>
    </div>
    <div class="flow-daili-window-chooseUser1-body">
        <div class="nodeUser-choose-tree">
            <div id="ajstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="j1_2" aria-busy="false">
                <ul class="jstree-container-ul jstree-children" role="group">
                    <li role="treeitem" aria-selected="false" aria-level="1" aria-labelledby="j1_1_anchor" aria-expanded="true" id="aj1_1" class="jstree-node  jstree-open">
                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                        <a class="jstree-anchor" href="#" tabindex="-1" id="aj1_1_anchor">
                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>电子设计中心
                        </a>
                        <ul role="group" class="jstree-children">
                            <li role="treeitem" data-jstree="{ &quot;selected&quot; : true }" aria-selected="true" aria-level="2" aria-labelledby="j1_2_anchor" id="aj1_2" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor  jstree-clicked" href="#" tabindex="-1" id="aj1_2_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>管理组
                                </a>
                            </li>
                            <li role="treeitem" aria-selected="false" aria-level="2" aria-labelledby="j1_3_anchor" id="aj1_3" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor" href="#" tabindex="-1" id="aj1_3_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>设计组
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="choose-table-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>姓名</td>
                    <td>账号</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>孙红雷</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄渤</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄磊</td>
                    <td>456</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>罗志祥</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="table-footer clear">
            <ul class="pagination">
                <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">下一页
                </a></li>
            </ul>
        </div>
        <div class="choose-bottom">
            <a class="btn btn-warning"><span class="glyphicon glyphicon-remove-circle"></span>  取消</a>
            <a class="btn btn-info"><span class="glyphicon glyphicon-ok-circle"></span>  确定</a>
        </div>
    </div>
</div>
<div class="flow-daili-window-chooseUser2">
    <div class="flow-daili-window-chooseUser2-header bg-success">
        <a><span class="glyphicon glyphicon-tasks"></span>  用户选择</a>
        <button type="button" class="close flow-daili-window-chooseUser2-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="choose-header2">
        <input type="text" class="form-control"/>
        <a class="btn btn-warning"><span class="glyphicon glyphicon-search"></span>  搜索</a>
    </div>
    <div class="flow-daili-window-chooseUser2-body">
        <div class="nodeUser-choose-tree">
            <div id="bjstree1" class="demo jstree jstree-1 jstree-default" role="tree" aria-multiselectable="true" tabindex="0" aria-activedescendant="bj1_2" aria-busy="false">
                <ul class="jstree-container-ul jstree-children" role="group">
                    <li role="treeitem" aria-selected="false" aria-level="1" aria-labelledby="j1_1_anchor" aria-expanded="true" id="bj1_1" class="jstree-node  jstree-open">
                        <i class="jstree-icon jstree-ocl" role="presentation"></i>
                        <a class="jstree-anchor" href="#" tabindex="-1" id="bj1_1_anchor">
                            <i class="jstree-icon jstree-themeicon" role="presentation"></i>电子设计中心
                        </a>
                        <ul role="group" class="jstree-children">
                            <li role="treeitem" data-jstree="{ &quot;selected&quot; : true }" aria-selected="true" aria-level="2" aria-labelledby="j1_2_anchor" id="bj1_2" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor  jstree-clicked" href="#" tabindex="-1" id="bj1_2_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>管理组
                                </a>
                            </li>
                            <li role="treeitem" aria-selected="false" aria-level="2" aria-labelledby="j1_3_anchor" id="bj1_3" class="jstree-node  jstree-leaf">
                                <i class="jstree-icon jstree-ocl" role="presentation"></i>
                                <a class="jstree-anchor" href="#" tabindex="-1" id="bj1_3_anchor">
                                    <i class="jstree-icon jstree-themeicon" role="presentation"></i>设计组
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="choose-table-box">
            <table class="table table-striped table-hover table-bordered">
                <thead class="table-title">
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>姓名</td>
                    <td>账号</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>孙红雷</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄渤</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>黄磊</td>
                    <td>456</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>罗志祥</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>张艺兴</td>
                    <td>123</td>
                </tr>
                <tr>
                    <td class="choose-table-input"><input type="checkbox"/></td>
                    <td>王迅</td>
                    <td>admin</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="table-footer clear">
            <ul class="pagination">
                <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">上一页</span></a></li>
                <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">下一页
                </a></li>
            </ul>
        </div>
        <div class="choose-bottom">
            <a class="btn btn-warning"><span class="glyphicon glyphicon-remove-circle"></span>  取消</a>
            <a class="btn btn-info"><span class="glyphicon glyphicon-ok-circle"></span>  确定</a>
        </div>
    </div>
</div>
</div>
<%@include file="/footer.jsp"%>
</div>
	</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script>
    $("[name='hiredate']").datepicker(
            {dateFormat: "yy年mm月dd日"}
    );
    $(function () {
        $('#ajstree1').jstree();
        $('#bjstree1').jstree();
        $('.choose-table-box,.flow-daili-window-chooseUser2,.flow-daili-window-chooseUser1,.flow-daili-moreWindow,.flow-daili-addWindow,.flow-daili-editWindow').draggable();
    });
</script>
</body>
</html>