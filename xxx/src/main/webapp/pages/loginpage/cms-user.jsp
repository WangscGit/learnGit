<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
     <%@include file="/base.jsp" %>
    <link rel="stylesheet" href="css/cms-user.css"/>    
    <link rel="stylesheet" href="uploadTree/proBomZTree.css" type="text/css">
    <link rel="stylesheet" href="zTreeStyle/zTreeStylepartCheck.css" type="text/css"/>
    <link rel="stylesheet" href="zTreeStyle/zTreeStylepartCheck.css" type="text/css"/>
    <link rel="stylesheet" href="bootstrap-select/bootstrap-select.css" type="text/css"/>
    <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="bootstrap-select/bootstrap-select.js"></script>
</head>
<body>     
     <%@include file="/public.jsp" %>
<div class="containerAll">
<div class="containerAllPage">
<!---------表格---------->
<div id="main">
<%@include file="/header.jsp" %>
    <div class="page-message">
       <h3><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>><a><fmt:message  key="menuManager"/></a>><a class="active"><fmt:message  key="user-page"/></a></h3>
        </div>
    <div role="tabpanel" class="Tab">
        <ul class="nav nav-tabs" role="tablist" id="myTab">
            <li role="presentation" class="active" id="userLi"><a href="#user"  hidefocus="true" aria-controls="user" role="tab" data-toggle="tab" onclick="initUserTalbe();"><fmt:message  key="user-accordmanager"/></a>
            	<ul id="userUl" hidden="true">
            		<li role="presentation"><a href="#post"  hidefocus="true" aria-controls="post" role="tab" data-toggle="tab" onclick="initPostTalbe();"><fmt:message  key="user-optionmanager"/></a></li>
            		<li role="presentation"><a href="#department"  hidefocus="true"  aria-controls="department" role="tab" data-toggle="tab" onclick="initDepartmentTalbe();"><fmt:message  key="user-deptmanager"/></a></li>
            	</ul>
            </li>            
            <li role="presentation"><a href="#group"  hidefocus="true" aria-controls="group" role="tab" data-toggle="tab" onclick="initGroupTab();"><fmt:message  key="user-group"/></a></li>
            <li role="presentation" id="limitLi"><a href="#limits"  hidefocus="true" aria-controls="limits" role="tab" data-toggle="tab" onclick="initRightsTab();" ><fmt:message  key="user-rights"/></a>
                    <ul id="limitUl" hidden="true">
            		<li role="presentation"><a href="#addlimit"  hidefocus="true"  aria-controls="addlimit" role="tab" data-toggle="tab"  onclick="initAddlimitTalbe();"><fmt:message  key="user-insertRight"/></a></li>
                  </ul>
            </li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="user">
             
                <div class="table-responsive user-table-box">
                        
                    <div class="batch">
                        <div class="left tr-number-box">
                            <a class="btn btn-xs btn-danger daoru-btn left"><span><span class="glyphicon glyphicon-arrow-down"></span>  <fmt:message  key="importBtn"/></span>
                            	<input type="file" class="daoru" name="userFile" onchange="readExcel(this)" id="bb"/>
                            </a>
                            <a class="btn btn-xs btn-danger left" onclick="exportUserByCondition()"><span class="glyphicon glyphicon-arrow-up"></span>  <fmt:message  key="exportButton"/></a>
                            <a class="btn btn-xs btn-danger left intoBoard" onclick="onloadTemp()"><span class="glyphicon glyphicon-save"></span>  <fmt:message  key="intoBoard"/></a>
                            <a class="btn btn-xs btn-danger left userAddbtn"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="insertBtn"/></a>
                             <a class="btn btn-xs btn-danger left userEditbtn" href="javascript:userUpdateBefore()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="modifyBtn"/></a>
                              <a class="btn btn-xs btn-danger left userDeletebtn" href="javascript:deleteUser()"><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="deleteBtn"/></a>
                        </div>
                        <div class="input-group right soso-user">
                            <input type="text" class="form-control" name="loginNameSearch" id="loginNameSearch" placeholder="<fmt:message  key="placeholder-loginname"/>" aria-describedby="basic-addon2">
                            <span class="input-group-addon" id="basic-addon2"><span class="glyphicon glyphicon-search" id="userSearch"></span></span>
                        </div>
                    </div>
                    <table class="table table-striped table-hover table-bordered" id="user-table">
                        <thead>
                        <tr role="row">
                        <th>
                          <input type="checkbox" id="checkAlluser" onclick="checkAlluser()">
                        </th>
                            <th>
                                <fmt:message  key="user-employNo"/>
                            </th>
                            <th>
                                <fmt:message  key="user-userNo"/>
                            </th>
                            <th>
                                <fmt:message  key="user-chineseName"/>
                            </th>
                            <th>
                                <fmt:message  key="user-accord"/>
                            </th>
                            <th>
                                <fmt:message  key="user-option"/>
                            </th>
                            <th>
                                <fmt:message  key="user-email"/>
                            </th>
                            <th>
                                <fmt:message  key="user-phone"/>
                            </th>
                            <th>
                                <fmt:message  key="user-mobil"/>
                            </th>
                            <th>
                                <fmt:message  key="user-dept"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createror"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createtime"/>
                            </th>
                            <th>
                                <fmt:message  key="user-state"/>
                            </th>                         
                        </tr>
                        </thead>
                        <tbody id="show-user">
                        </tbody>
                        
                    </table>
                    <div class="table-footer">
                      <div id="Pagination" class="pagination"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="post">
                <div class="table-responsive post-table-box">
                <div class="postBtn-box tr-number-box">
<!-- 							 <a class="btn batch-btn btn-xs btn-info left postAddbtn"><span class="glyphicon glyphicon-plus"></span>  添加</a> -->
                             <a class="btn batch-btn btn-xs btn-danger left postEditbtn"  href="javascript:positionUpdateBefore()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="modifyBtn"/></a>
                              <a class="btn batch-btn btn-xs btn-danger left postDeletebtn" href="javascript:deletePosition()"><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="deleteBtn"/></a>
                        </div>
                    <table class="table table-striped table-hover table-bordered" id="post-table">
                        <thead>
                        <tr role="row">
                         <th>
                         <input type="checkbox" id="checkAllPosition" onclick="checkAllPosition()">
                        </th>
                            <th>
                                <fmt:message  key="user-optionSeq"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionJop"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createror"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createtime"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionMdf"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionMdftime"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionRek"/>
                            </th>                         
                        </tr>
                        </thead>
                        <tbody id="show-position">
                        </tbody>                       
                    </table>
                    <div class="table-footer">
                        <div id="PaginationPosition" class="pagination"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="addlimit">
                <div class="table-responsive addlimit-table-box">
                <div class="addlimitBtn-box tr-number-box">
 							 <a class="btn btn-xs btn-danger left addlimitAddbtn1" onclick="beforeAddRights('1');"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="tjtjjd"/></a> 
 							 <a class="btn btn-xs btn-danger left addlimitAddbtn" onclick="beforeAddRights('2');"><span class="glyphicon glyphicon-plus"></span>  <fmt:message  key="tjxjjd"/></a> 
                             <a class="btn btn-xs btn-danger left addlimitEditbtn"  onclick="beforeAddRights('0');"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="editorBtn"/></a>
                             <a class="btn btn-xs btn-danger left addlimitDeletebtn" onclick="deleteRights();"><span class="glyphicon glyphicon-remove"></span>  <fmt:message  key="deleteBtn"/></a>
                </div> 
                <div class="addlimit-table-body">
                          <!--权限tree-->
                    <div class="addlimit-tree left">
                        <ul id="addRightsTree" class="ztree"></ul>
                    </div>
                         
                                 <table class="table table-striped table-hover table-bordered left" id="addlimit-table">
                                         <thead>
                        <tr role="row">
                         <th>
                        </th>
                            <th>
                                <fmt:message  key="jdm"/>
                            </th>
                            <th>
                                <fmt:message  key="ljdz"/>
                            </th>
                            <th>
                                <fmt:message  key="sfsjqx"/>
                            </th>                                          
                        </tr>
                        </thead>
                        <tbody id="rightNode">
                        </tbody>    
                    </table>
                         
                </div>                   
                    <div class="table-footer">
                        <div id="PaginationPosition" class="pagination"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="department">
                <div class="table-responsive department-table-box">
                <div class="postBtn-box tr-number-box">
<!--                              <a class="btn batch-btn btn-xs btn-info left departmentAddbtn"><span class="glyphicon glyphicon-plus"></span>  添加</a> -->
                             <a class="btn btn-xs btn-danger left departmentEditbtn" href="javascript:departmentUpdateBefore()"><span class="glyphicon glyphicon-edit"></span>  <fmt:message  key="modifyBtn"/></a>
                             <a class="btn btn-xs btn-danger left departmentDeletebtn" href="javascript:deleteDepartment()"><span class="glyphicon glyphicon-remove"></span><fmt:message  key="deleteBtn"/></a>
                        </div>
                    <table class="table table-striped table-hover table-bordered" id="department-table">
                        <thead>
                        <tr role="row">
                            <th>
                         <input type="checkbox" id="checkAllDepartment" onclick="checkAllDepartment()">
                        </th>
                            <th>
                                <fmt:message  key="user-optionSeq"/>
                            </th>
                            <th>
                                <fmt:message  key="user-deptName"/>
                            </th>
                            <th>
                                <fmt:message  key="user-deptNumber"/>
                            </th>
                            <th>
                                <fmt:message  key="user-depthead"/>
                            </th>
                            <th>
                                <fmt:message  key="user-deptRemark"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createror"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createtime"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionMdf"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionMdftime"/>
                            </th>   
                             <th>
                                <fmt:message  key="operationBtn"/>
                            </th>                       
                        </tr>
                        </thead>
                        <tbody id="show-department">
                        </tbody>                        
                    </table>
                    <div class="table-footer">
                       <div id="PaginationDepartment" class="pagination"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="group">
                <div class="table-responsive group-table-box">
                <div class="postBtn-box tr-number-box">
                            <a class="btn btn-xs btn-danger left groupAddbtn" ><span class="glyphicon glyphicon-plus"></span><fmt:message  key="insertBtn"/></a>
                             <a class="btn btn-xs btn-danger left groupEditbtn" onclick="showEditGroupDia();"><span class="glyphicon glyphicon-edit"></span><fmt:message  key="editorBtn"/></a>
                              <a class="btn btn-xs btn-danger left groupDeletebtn" onclick="deleteGroup();"><span class="glyphicon glyphicon-remove"></span><fmt:message  key="deleteBtn"/></a>
                        </div>
                    <table class="table table-striped table-bordered" id="group-table">
                        <thead>
                        <tr role="row">
                            <th>
                                 <input type="checkbox" id="groupfircb" onclick="selectCheckBox('groupfircb','groupCheckBox');">
                           </th>
                           
                            <th>
                                <fmt:message  key="zmc"/>
                            </th>
                            <th>
                                <fmt:message  key="user-createror"/>
                            </th>
                            <th>
                               <fmt:message  key="user-createtime"/>
                            </th>
                            <th>
                                <fmt:message  key="user-optionMdf"/>
                            </th>
                            <th>
                            	<fmt:message  key="user-optionMdftime"/>
                            </th>
                            <th>
                                <fmt:message  key="operationBtn"/>
                            </th>
                        </tr>
                        </thead>
                        <tbody id="show-group">
                       
                        </tbody>                       
                    </table>
                    <div class="table-footer">
                      <div id="groupPagination" class="pagination"></div>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="limits">
                <div class="tree-box">
                    <br/>
                    <div class="limit-group left" >
                        <div class="left">
                            <fmt:message  key="zmc"/>  <select  id="groupSelectList" onchange="initRightsTree();">
                            
                        </select>
                        
                        </div>
                        <div class="left limit-btn">
                            <div class="left">
                                <a class="btn btn-danger limit-edit" onclick="checkGroupSelect();"><fmt:message  key="editorBtn"/></a>
                            </div>
                            <div style="display: none" class="left limit-btn1">
                                <a class="btn btn-danger limit-save left" onclick="saveGroupRights();"><fmt:message  key="saveBtn"/></a>
                                <a class="btn btn-danger limit-cancel left"><fmt:message  key="resetBtn"/></a>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <br/>
                  
                    <!--权限tree-->
                    <div class="addlimit-tree">
                        <ul id="rightsTree" class="ztree"></ul>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>
</div>
<div class="user-addWindow">
    <div class="user-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="user-insert"/></a>
        <button type="button" class="close user-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>    
    <div class="window-body">
        <div class="window-box adduser-window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span><fmt:message  key="user-employNo"/>：</span></td>
                    <td><input type="text" class="form-control" name="employeeNumberAdd"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-userNo"/>：</span></td>
                    <td><input type="text" class="form-control" name="userNumberAdd"/></td>
                </tr>
                <tr>
                    <td><span><b>*</b><fmt:message  key="user-chineseName"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="userNameAdd"/>
                    </td>
                </tr>
                <tr>
                    <td><span><b>*</b><fmt:message  key="user-accord"/>：</span></td>
                    <td><input type="text" class="form-control" name="loginName"/></td>
                </tr>
                <tr>
                    <td><span><b>*</b><fmt:message  key="user-password"/>：</span></td>
                    <td><input type="password" class="form-control" name="passWord"/></td>
                </tr>
                <tr>
                    <td><span><b>*</b><fmt:message  key="user-cfmpassword"/>：</span></td>
                    <td><input type="password" class="form-control" name="passWord2"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-option"/>：</span></td>
                    <td><select name='type' class='form-control select-control'  id='addPosition' name="position"></select></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-email"/>：</span></td>
                    <td><input type="text" class="form-control" name="email"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-phone"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="telephoneAdd"/>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-mobil"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="mobilePhoneAdd"/>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-dept"/>：</span></td>
                    <td>
                        <select name='type' class='form-control select-control' id='addDepartments' name="department"></select>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-zu"/>：</span></td>
                    <td>
                       <label for="id_select"></label>
                        <select name='type' id="id_select" class='form-control select-control selectpicker bla bla bli'  multiple data-live-search="true">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-state"/>：</span></td>
                    <td>
                        <select name='type' class='form-control select-control' type='text'  name="isOrNot">
                           <option value="0">在职</option>
                           <option value="1">离职</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </div>
      <div class="Window-header2">
         <a class="btn btn-gray" href="javascript:closeAddUser()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a>
        <a class="btn btn-gray" href="javascript:addUser()"><span class="glyphicon glyphicon-save"></span>  <fmt:message  key="saveBtn"/></a>
    </div>
</div>
<div class="user-editWindow">
    <div class="user-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="user-modify"/></a>
        <button type="button" class="close user-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="edituser-window-box window-box">
            <table class="table table-striped table-hover table-bordered">
               <tr>
                    <td><span><fmt:message  key="user-employNo"/>：</span></td>
                    <td><input type="text" class="form-control" name="employeeNumberUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-userNo"/>：</span></td>
                    <td><input type="text" class="form-control" name="userNumberUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-chineseName"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="userNameUpdate"/>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-accord"/>：</span></td>
                    <td><input type="text" class="form-control" name="loginNameUpdate" readonly/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-option"/>：</span></td>
<!--                     <input type="text" class="form-control" name="positionUpdate"/> -->
                    <td><select name='type' class='form-control select-control' type='text' id='positionUpdate' name="positionUpdate"></select></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-email"/>：</span></td>
                    <td><input type="text" class="form-control" name="emailUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-phone"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="telephoneUpdate"/>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-mobil"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="mobilePhoneUpdate"/>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-dept"/>：</span></td>
                    <td>
<!--                         <input type="text" class="form-control" name="departmentUpdate"/> -->
                        <select name='type' class='form-control select-control' type='text' id='departmentUpdate' name="departmentUpdate"></select>
                    </td>
                </tr>
                 <tr>
                    <td><span><fmt:message  key="user-zu"/>：</span></td>
                    <td>
                       <label for="id_select1"></label>
                        <select name='type' id="id_select1" class='form-control select-control selectpicker bla bla bli'  multiple data-live-search="true">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-state"/>：</span></td>
                    <td>
<!--                         <input type="text" class="form-control" name="isOrNotUpdate"/> -->
                        <select name='type' class='form-control select-control' type='text' id="isOrNotUpdate"  name="isOrNotUpdate">
                           <option value="0">在职</option>
                           <option value="1">离职</option>
                        </select>
                    </td>
                </tr>
            </table>
        </div>
    </div>
           <div class="Window-header2">
                   <a class="btn btn-gray" href="javascript:closeUploadUser()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a>
        <a class="btn btn-gray" href="javascript:updateUser()"><span class="glyphicon glyphicon-save"></span>  <fmt:message  key="saveBtn"/></a>
    </div>
</div>
<div class="post-addWindow">
    <div class="post-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  添加职位</a>
        <button type="button" class="close post-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>     
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span>职位名称：</span></td>
                    <td><input type="text" class="form-control" name="positionName"/></td>
                </tr>
                <tr>
                    <td><span>职位说明：</span></td>
                    <td>
                        <input type="text" class="form-control heightInput" name="positionRemark"/>
                    </td>
                </tr>               
            </table>
        </div>
    </div>
    <div class="Window-header2">
        <a class="btn btn-gray" href="javascript:closeAddPosition()"><span class="glyphicon glyphicon-arrow-left"></span>  返回</a>
                <a class="btn btn-gray" href="javascript:addPosition()"><span class="glyphicon glyphicon-save"></span>  保存</a>
    </div>
</div>
<div class="post-editWindow">
    <div class="post-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="user-optionEdit"/></a>
        <button type="button" class="close post-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span><fmt:message  key="user-optionJop"/>：</span></td>
                    <td><input type="text" class="form-control" name="positionNameUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-optionRek"/>：</span></td>
                    <td>
                        <input type="text" class="form-control heightInput" name="positionRemarkUpdate"/>
                    </td>
                </tr>               
            </table>
        </div>
    </div>
           <div class="Window-header2">
        <a class="btn btn-gray" href="javascript:closeUpdatePosition()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a>
               <a class="btn btn-gray" href="javascript:updatePosition()"><span class="glyphicon glyphicon-save"></span>  <fmt:message  key="saveBtn"/></a>
    </div>
</div>
<div class="department-addWindow">
    <div class="department-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  添加部门</a>
        <button type="button" class="close department-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span>部门名称：</span></td>
                    <td><input type="text" class="form-control" name="departmentName"/></td>
                </tr>
                <tr>
                    <td><span>部门编号：</span></td>
                    <td>
                        <input type="text" class="form-control" name="departmentNo"/>
                    </td>
                </tr>    
                 <tr>
                    <td><span>部门负责人：</span></td>
                    <td><input type="text" class="form-control" name="departmentMaster"/></td>
                </tr>
                <tr>
                    <td><span>部门描述：</span></td>
                    <td>
                        <input type="text" class="form-control heightInput" name="departmentDescript"/>
                    </td>
                </tr>               
            </table>
        </div>
    </div>
           <div class="Window-header2">
        <a class="btn btn-gray" href="javascript:closeAddDepartment()"><span class="glyphicon glyphicon-arrow-left"></span>  返回</a>
               <a class="btn btn-gray" href="javascript:addDepartment()"><span class="glyphicon glyphicon-save"></span>  保存</a>
    </div>
</div>
<div class="department-editWindow">
    <div class="department-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span> <fmt:message  key="user-deptEdit"/></a>
        <button type="button" class="close department-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">
                <tr>
                    <td><span><fmt:message  key="user-deptName"/>：</span></td>
                    <td><input type="text" class="form-control" name="departmentNameUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-deptNumber"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" name="departmentNoUpdate"/>
                    </td>
                </tr>    
                 <tr>
                    <td><span><fmt:message  key="user-depthead"/>：</span></td>
                    <td><input type="text" class="form-control" name="departmentMasterUpdate"/></td>
                </tr>
                <tr>
                    <td><span><fmt:message  key="user-deptRemark"/>：</span></td>
                    <td>
                        <input type="text" class="form-control heightInput" name="departmentDescriptUpdate"/>
                    </td>
                </tr>                
            </table>
        </div>
    </div>
         <div class="Window-header2">
        <a class="btn btn-gray" href="javascript:closeUpdateDepartment()"><span class="glyphicon glyphicon-arrow-left"></span>  <fmt:message  key="resetBtn"/></a>
               <a class="btn btn-gray" href="javascript:updateDepartment()"><span class="glyphicon glyphicon-save"></span>  <fmt:message  key="saveBtn"/></a>
    </div>
</div>
<div class="group-addWindow">
    <div class="group-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="tjz"/>  </a>
        <button type="button" class="close group-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>      
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">
<!--                <tr> -->
<%--                     <td><span><fmt:message  key="zbh"/>：</span></td> --%>
<!--                     <td> -->
<!--                         <input type="text" class="form-control" id="inGroupIndex"/> -->
<!--                     </td> -->
<!--                 </tr>  -->
                <tr>
                    <td><span><fmt:message  key="zmc"/>：</span></td>
                    <td><input type="text" class="form-control" id="inGroupName"/></td>
                </tr>               
            </table>
        </div>
    </div>
    <div class="Window-header2">
        <a class="btn btn-gray"  onclick="hideGroupDia();"><span class="glyphicon glyphicon-arrow-left"></span><fmt:message  key="goback"/>  </a>
               <a class="btn btn-gray" onclick="addGroup();"><span class="glyphicon glyphicon-save" ></span><fmt:message  key="saveBtn"/></a>
    </div>
</div>
<!-- 组编辑弹窗 -->
<div class="group-editWindow">
    <div class="group-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="bjz"/></a>
        <button type="button" class="close group-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>      
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">               
<!--                 <tr> -->
<%--                     <td><span><fmt:message  key="zbh"/>：</span></td> --%>
<!--                     <td> -->
<!--                         <input type="text" class="form-control" id="edGroupIndex"/> -->
<!--                     </td> -->
<!--                 </tr>  -->
                <tr>
                    <td><span><fmt:message  key="zmc"/>：</span></td>
                    <td><input type="text" class="form-control" id="edGroupName"/></td>
                </tr>                         
            </table>
        </div>
    </div>
    <div class="Window-header2">
        <a class="btn btn-gray" onclick="hideGroupeditDia();"><span class="glyphicon glyphicon-arrow-left"></span><fmt:message  key="goback"/></a>
               <a class="btn btn-gray" onclick="editGroup();"><span class="glyphicon glyphicon-save" ></span><fmt:message  key="saveBtn"/></a>
    </div>
</div>
<!-- 添加权限中添加同级节点弹窗 -->
 <div id="addlimit-addWindow" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
<div class="addlimit-addWindow">
    <div class="addlimit-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="tjtjjd"/></a>
        <button type="button" class="close addlimit-addWindow-close" aria-label="Close" data-dismiss="modal"><span>&times;</span></button>
    </div>      
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">               
                <tr>
                    <td><span><fmt:message  key="jdm"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" id="rightNodeName"/>
                    </td>
                </tr> 
                <tr>
                    <td><span><fmt:message  key="ljdz"/>：</span></td>
                    <td><input type="text" class="form-control" id="rightNodeUrl"/></td>
                </tr>                   
            </table>
        </div>
    </div>
     <div class="Window-header2">
        <a class="btn btn-gray" data-dismiss="modal"  data-toggle="modal" onclick="backBtn('addlimit-addWindow');"><span class="glyphicon glyphicon-arrow-left"></span><fmt:message  key="goback"/></a>
            <a class="btn btn-gray" data-dismiss="modal"  data-toggle="modal" onclick="addRights();"><span class="glyphicon glyphicon-save" ></span><fmt:message  key="saveBtn"/></a>
    </div>
</div>
</div>
</div>
<!-- 添加权限中添加下级节点弹窗 -->
 <div id="addlimit-add1Window" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
<div class="addlimit-add1Window">
    <div class="addlimit-add1Window-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="tjxjjd"/></a>
        <button type="button" class="close addlimit-add1Window-close" aria-label="Close" data-dismiss="modal"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">               
                <tr>
                    <td><span><fmt:message  key="jdm"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" id="rightNodeName1"/>
                    </td>
                </tr> 
                <tr>
                    <td><span><fmt:message  key="ljdz"/>：</span></td>
                    <td><input type="text" class="form-control" id="rightNodeUrl1"/></td>
                </tr>  
                 <!-- <tr>
                    <td><span>是否有数据权限：</span></td>
                    <td><input type="text" class="form-control" id="edGroupName"/></td>
                </tr>   -->                      
            </table>
        </div>
    </div>
        <div class="Window-header2">
        <a class="btn btn-gray" data-toggle="modal" data-dismiss="modal"  onclick="backBtn('addlimit-add1Window');"><span class="glyphicon glyphicon-arrow-left"></span><fmt:message  key="goback"/></a>
               <a class="btn btn-gray" data-dismiss="modal"  data-toggle="modal" onclick="addRights1();"><span class="glyphicon glyphicon-save" ></span><fmt:message  key="saveBtn"/></a>
    </div>
</div>
</div>
</div>
<!-- 添加权限中修改弹窗 -->
 <div id="addlimit-editWindow" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
<div class="addlimit-editWindow">
    <div class="addlimit-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="xgjd"/></a>
        <button type="button" class="close addlimit-editWindow-close" aria-label="Close" data-dismiss="modal"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="window-box">
            <table class="table table-striped table-hover table-bordered">               
                <tr>
                    <td><span><fmt:message  key="jdm"/>：</span></td>
                    <td>
                        <input type="text" class="form-control" id="rightNodeName2"/>
                    </td>
                </tr> 
                <tr>
                    <td><span><fmt:message  key="ljdz"/>：</span></td>
                    <td><input type="text" class="form-control" id="rightNodeUrl2"/></td>
                </tr>  
                <!--  <tr>
                    <td><span>是否有数据权限：</span></td>
                    <td><input type="text" class="form-control" id="edGroupName"/></td>
                </tr>  -->                       
            </table>
        </div>
    </div>
            <div class="Window-header2">
        <a class="btn btn-gray" data-toggle="modal" data-dismiss="modal"><span class="glyphicon glyphicon-arrow-left"></span><fmt:message  key="goback"/></a>
               <a class="btn btn-gray" data-dismiss="modal"  data-toggle="modal" onclick="editRights();"><span class="glyphicon glyphicon-save" ></span><fmt:message  key="saveBtn"/></a>
    </div>
</div>
</div>
</div>
<div class="department-user-box">
    <div class="department-user-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="user-Memberinformation"/></a>
        <button type="button" class="close department-user-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="department-users">
                 <table class="table table-striped table-hover table-bordered" id="deptUserList">               
                <tr>
                    <td><span>成员姓名：</span></td>
                    <td>
                              <b>刘涛</b>
                    </td>
                </tr> 
            </table>
    </div>
</div>
 <div class="group-user-box">
    <div class="group-user-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="user-Memberinformation"/></a>
        <button type="button" class="close group-user-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="group-users">
        <div class="left group-user well">
            <p><span class="label select-label"><span class="glyphicon glyphicon-user"></span></span>
                <fmt:message  key="syyh"/></p>
            <div class=""  id="drpJobList" onchange="BindJob(document.all.lbJobList,'//bigjobs',this.value,'')" name=drpJobList>
            </div>
            <div class="group-user-select">
                <select id="lbJobLis" ondblclick="AddItem(this,document.all.sellbJobList,document.all.drpJobList)" size=10; name=lbJobList  class="form-control">
                </select>
            </div>
        </div>
        <div class="left">
            <div class="group-user-btn">
                <p><span class="label label-default"><span class="glyphicon glyphicon-user"></span></span>
                    <fmt:message  key="xscytcsm"/></p>
                <div>
                    <a class="btn btn-gray btn-xs" onclick="AddItem(document.all.lbJobList,document.all.sellbJobList,document.all.drpJobList)"><fmt:message  key="insertBtn"/>  <span class='glyphicon glyphicon-plus-sign'></span></a>
                    <a class="btn btn-gray btn-xs" onclick="removeItem(document.all.sellbJobList)"> <fmt:message  key="deleteBtn"/>  <span class='glyphicon glyphicon-minus-sign'></span></a>
                </div>
            </div>
            <div class="sure" onclick="saveGroupUser();"><a class="btn btn-gray"><fmt:message  key="determine"/></a></div>
        </div>
        <div class="left group-user well group-user2">
            <p><span class="label select-label"><span class="glyphicon glyphicon-user"></span></span><fmt:message  key="dqzcy"/></p>
            <div class="group-user-select">
                <select id="sellbJobLis" ondblclick="removeItem(document.all.sellbJobList)" size=10 name=sellbJobList  class="form-control"></select>
            </div>
        </div>
    </div>
</div> 
<%@include file="/footer.jsp" %>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/user.js"></script>
<script type="text/javascript" src="scripts/departmentUser.js"></script>
<script>
$(function () {
   	$('.group-user-box,.user-addWindow,.user-editWindow,.post-addWindow,.post-editWindow').draggable();
	$('.department-user-box,.department-addWindow,.department-editWindow,.group-addWindow,.group-editWindow,.addlimit-addWindow,.addlimit-add1Window,.addlimit-editWindow').draggable();
	//账户管理下拉
	$("#userLi").hover(function(){
		$("#userUl").show();  
	},function(){
		$("#userUl").hide();  
	});
	$("#limitLi").hover(function(){
		$("#limitUl").show();  
	},function(){
		$("#limitUl").hide();  
	});
	/**user.js**/
   	initUserList();
   	initPosition();
   	initDepartment();
   	hasLogined();
});
$(".selectpicker").selectpicker({  
    noneSelectedText : '请选择'  
});  
$(window).on('load', function() {  
    $('.selectpicker').selectpicker('val', '');  
    $('.selectpicker').selectpicker('refresh');  
});
/*判断是否有placeholder属性*/
function isPlaceholder(){
    var input = document.createElement('input');  
    return "placeholder" in input;  
}  
if( isPlaceholder() ) {
    console.log("1")  
}else {
    console.log("2");  
    if( $('#loginNameSearch').val()=="" ){
        $('#loginNameSearch').css({
            "color" : "#ccc"
        })  
        $('#loginNameSearch').val("请输入用户名");  
    }  
    $('#loginNameSearch').focus(function () {
    	if( $(this).val()=="请输入用户名" ){
            $(this).val("");
        }  
        $('#loginNameSearch').css({
            "color" : "#666"  
        })  
    })    
    $('#loginNameSearch').blur(function () {
        if( $(this).val()=="" ){
            $(this).val("请输入用户名");  
            $('#loginNameSearch').css({  
                "color" : "#ccc"  
            })  
        }else {
            $('#loginNameSearch').css({  
                "color" : "#666"  
            })  
        }  
    })  
}

</script>
</body>
</html>