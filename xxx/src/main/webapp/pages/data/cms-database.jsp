<%@ page language="java" pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" >
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <%@include file="/base.jsp" %>
   	<link rel="stylesheet" href="scripts/jquery-autocomplete/jquery.autocomplete.css" type="text/css">
    <link rel="stylesheet" href="css/cms-headerPublic.css"/>
    <link rel="stylesheet" href="css/cms-database.css"/>
	<link rel="stylesheet" href="zTreeStyle/zTreeStyle.css" type="text/css">
	<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css" type="text/css"/>
	<script type="text/javascript" src="scripts/jquery-autocomplete/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="scripts/jquery-autocomplete/jquery-migrate-1.2.1.js"></script>
	<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
</head>
<body>
<%@include file="/public.jsp" %>
 <!---------主体---------->
 <!---------添加节点框---------->
<div class="containerAll">
    <div class="containerAllPage">
<!---------表格---------->
<div id="main">
<%@include file="/header.jsp" %>
    <div class="page-message">
        <h3><span class="label label-default">Now</span> <a href="index.jsp"><fmt:message  key="homePage"/></a>><a><fmt:message  key="menuManager"/></a>><a class="active"><fmt:message  key="dataBase-page"/></a></h3>       
    </div>
    <div class="table-responsive">
        <div class="dataHeader">
                      <a href="javascript:void(0)" class="btn btn-danger left dataList-btn"><fmt:message  key="typeInf"/></a>
            <a href="javascript:fieldFunction()" class="btn btn-danger left dataField-btn"><fmt:message  key="fieldDefinition"/></a>
<!--             <a href="javascript:void(0)" class="btn btn-danger left databaseCopy-btn">数据库备份</a> -->
        </div>
        <div class="dataList">
        <div class="data-left left">
            <div class="partTreeBackground left">
				<ul id="partTree" class="ztree"></ul>
			</div>
        </div>
        <div class="data-right left">
                <div class="data-table-box">
                          <div class="btn-box">
                         		 <a href="javascript:void(0)" class="btn btn-danger btn-xs right" onclick="deleteNode();"><span class="glyphicon glyphicon-remove"></span><fmt:message  key="deleteBtn"/></a>
                            	 <a href="javascript:void(0)" class="btn btn-danger btn-xs right" onclick="addOrUpdateNode();"><span class="glyphicon glyphicon-save"></span><fmt:message  key="saveBtn"/></a>
                            	 <a href="javascript:void(0)" class="btn btn-danger btn-xs right" onclick="clickUpdateBtn();"><span class="glyphicon glyphicon-edit"></span><fmt:message  key="modifyBtn"/></a>
                            	 <a href="javascript:void(0)" class="btn btn-danger btn-xs right" onclick="clickAddBtn();"><span class="glyphicon glyphicon-plus"></span><fmt:message  key="insertBtn"/></a>
                         </div>
                         <div class="header">
                        		<b></b><fmt:message  key="jbxx"/>
                        </div>
                        <table class="data-more">                                                               
                            <tr>                           	
                                <td class="tdwidth"><span><fmt:message  key="dyj"/>：</span></td>
                                <td>
                                	<input id="parentNum" type="text" class="form-control inputlong" value=""/>
								</td>						
                            </tr>
                            <tr>
                                <td class="tdwidth"><span><b>*</b><fmt:message  key="dej"/>：</span></td>
                                <td><input  id="childNum" type="text" class="form-control inputlong" value="" /></td>
                            </tr>
                            <tr>
                                <td class="tdwidth"><span><b>*</b><fmt:message  key="lx"/>：</span></td>
                                <td><input id="partType" type="text" class="form-control inputlong" value="" /></td>
                            </tr>
                            <tr>
                                <td class="tdwidth"><span><b>*</b><fmt:message  key="ywm"/>：</span></td>
                                <td><input id="enName" type="text" class="form-control inputlong" value="" /></td>
                            </tr>
                            <tr>
                                <td class="tdwidth" ><span><fmt:message  key="sctp"/>：</span></td>
                                 <td>
                                <a class="inputBox left"><input id="typeImg" name="typeImg" type="file" onchange="limitImg('typeImg','img1', 100, 20, 20);" class="left" /><fmt:message  key="xztp"/></a>
                                      <span class="hopeimg left">[<fmt:message  key="tjcc"/>:18px*18px]</span>                                     
                                  </td>
                                  </tr>
                            <tr>
                            	<td class="tdwidth" ><span><fmt:message  key="tpll"/>：</span></td>
                            	<td>
                            		<img id="img1"/>
                            	</td>
                            </tr>
                            </table>
                            
                            <div id="specialProperties">
                            <div class="header">
                        		<b></b><fmt:message  key="tssx"/>
                        </div>
                            <table class="data-more">                          
                            <tr>
                                <td class="tdwidth" ><span><fmt:message  key="tssx"/>1：</span></td>
                                <td><input type="text" id="specialPropertie1" class="form-control" value=""/></td>
                              	<td class="tdwidth" ><span><fmt:message  key="tssx"/>2：</span></td>
                                <td><input type="text" id="specialPropertie2" class="form-control" value=""/></td>
                            </tr>
                              
                            <tr>
                                <td><span><fmt:message  key="tssx"/>3：</span></td>
                                <td><input type="text" id="specialPropertie3" class="form-control" value=""/></td>
                             <td><span><fmt:message  key="tssx"/>4：</span></td>
                                <td><input type="text" id="specialPropertie4" class="form-control" value=""/></td>
                            </tr>                             
                            <tr>
                                <td><span><fmt:message  key="tssx"/>5：</span></td>
                                <td><input type="text" id="specialPropertie5" class="form-control" value=""/></td>
                            <td><span><fmt:message  key="tssx"/>6：</span></td>
                                <td><input type="text" id="specialPropertie6" class="form-control" value=""/></td>
                            </tr>
                                
                            <tr>
                                <td><span><fmt:message  key="tssx"/>7：</span></td>
                                <td><input type="text" id="specialPropertie7" class="form-control" value=""/></td>
                            <td><span><fmt:message  key="tssx"/>8：</span></td>
                                <td><input type="text" id="specialPropertie8" class="form-control" value=""/></td>
                            </tr>
                                
                            <tr>
                                <td><span><fmt:message  key="tssx"/>9：</span></td>
                                <td><input type="text" id="specialPropertie9" class="form-control" value=""/></td>
                            <td><span><fmt:message  key="tssx"/>10：</span></td>
                                <td><input type="text" id="specialPropertie10" class="form-control" value=""/></td>
                            </tr>
                            <tr>
                                <td><span><fmt:message  key="tssx"/>11：</span></td>
                                <td><input type="text" id="specialPropertie11"  class="form-control" value=""/></td>
                            	<td><span><fmt:message  key="tssx"/>12：</span></td>
                                <td><input type="text" id="specialPropertie12"  class="form-control" value=""/></td>
                            </tr>
                        </table>
                        </div>
                </div>
                
        </div>
        </div>
        <div class="dataField">
             <ul class="dataField-ul">
	     <li class="first"><a  href="javascript:showDaibanField()" class="new-field-btn"><fmt:message  key="insertField"/>  <span class="glyphicon glyphicon-plus"></span></a></li>
	     <li><a href="javascript:moveUp()"><fmt:message  key="moveUp"/>  <span class="glyphicon glyphicon-arrow-up"></span></a></li>
	     <li><a href="javascript:moveDown()"><fmt:message  key="movedown"/>  <span class="glyphicon glyphicon-arrow-down"></span></a></li>
	     <li><a href="javascript:saveChange()"><fmt:message  key="saveBtn"/>  <span class="glyphicon glyphicon-floppy-saved"></span></a></li>
            </ul>
            <table class="table table-striped table-hover table-bordered" id="dataField-table">
                <tr></tr>
                <thead>
                <tr role="row">
                    <th>
                    </th>
                    <th>
                        <fmt:message  key="fieldName"/>
                    </th>
                    <th>
                        <fmt:message  key="showName"/>
                    </th>
                    <th>
                        <fmt:message  key="englishName"/>
                    </th>
                    <th>
                        <fmt:message  key="remark"/>
                    </th>
                    <th>
                        <fmt:message  key="dataType"/>
                    </th>
                    <th>
                        <fmt:message  key="category"/>
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
                        IsInsert
                    </th>
                    <th>
                        IsApply
                    </th>
                    <th>
                        <fmt:message  key="editorBtn"/>
                    </th>
                </tr>
                </thead>
                <tbody id="showField">
                </tbody>
            </table>
</div>
        <div class="databaseCopy">
             <div class="databaseCopy-left left"><b class="left">名字：</b><input type="text" class="form-control left"/><a href="" class="btn btn-gray datacopy-save left">保存</a></div>
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
        <div class="new-field-table-box">
                  <table class="new-field-table1 left">
            <tr>
            <td><span><b>*</b><fmt:message  key="fieldName"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="fieldName" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b><fmt:message  key="showName"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="showName" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b><fmt:message  key="englishName"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="englishName" value=""/></td>
        </tr>
        <tr>
            <td><span><b>*</b><fmt:message  key="dataType"/>：</span></td>
            <td colspan="2">
            	<select  class="form-control  left" name="dataType"  id="dataTypes" onchange="showAddImg();">
            		<option value="nvarchar(500)">String</option>
            		<option value="bit">Boolean</option>
            		<option value="datetime">Date</option>
            		<option value="int">Integer</option>
            		<option value="selectList">enum</option>
            	</select>            	 
            </td>        
        </tr>        
        <tr>
            <td><span><b>*</b><fmt:message  key="category"/>：</span></td>
            <td colspan="1"><select name='type' class='form-control select-control' type='text' id='addSelectType'></select></td>
        </tr>
        <tr>
            <td><span><fmt:message  key="remark"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="description" value=""/></td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span>IsSearch：<input type="checkbox"  id = "isSearch" /></span>
                <span>IsDisplay：<input type="checkbox" id="isDisplay"/></span>
                <span>IsUpdate：<input type="checkbox" id="isUpdate"/></span>
            </td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span>IsAudit：<input type="checkbox" id="isAudit" /></span>
                <span>IsMatch：<input type="checkbox" id="isMatch"/></span>
                <span>IsNull：<input type="checkbox" id="isNull" checked="checked"/></span>
                </td>
                
        </tr>
        <tr class="checkbox-span">
            <td colspan="3">
            <span>IsInsert：<input type="checkbox" id="isInsert" /></span>
            <span>IsApply：<input type="checkbox" id="isApply" /></span>
            </td>
        </tr>
    </table>
        <div class="btns">
                <a href="javascript:newFieldReset()" class="btn btn-danger right"><fmt:message  key="resetBtn"/></a>
                <a href="javascript:newField()" class="btn btn-danger right"><fmt:message  key="determine"/></a>
    </div>
    <div id="addImgBtn"><a class="btn btn-danger btn-xs right" onclick="showSelectDia();"><span class="glyphicon glyphicon-plus"></span></a></div>
                </div>
                <div class="new-field-table2 right">
                <b><fmt:message  key="changList"/></b>
               <table class="table table-striped table-hover table-bordered">
	 <thead>
	 <tr role="row" >
	                <th>
	                    <fmt:message  key="fieldName"/>
                    </th>
                    <th>
                        <fmt:message  key="showName"/>
                    </th>
                    <th>
                        <fmt:message  key="dataType"/>
                    </th>
                    <th>
                       <fmt:message  key="remark"/>
                    </th>
                    <th>
                       <fmt:message  key="operationBtn"/>
                    </th>
       </tr>
    </thead>
    <tbody id="daibanField">
    </tbody>
</table>
<a href="javascript:commitData()" class="btn btn-danger"><fmt:message  key="commitData"/></a>
</div>
            </div>  
    </div>
</div>

<div class="select-addWindow">
    <div class="select-addWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="enumValue"/></a>
        <button type="button" class="close select-addWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="btnGroups">
        <a class="btn btn-danger btn-xs productmix-addchildren" onclick="addValueTr();"><span class="glyphicon glyphicon-plus" ></span>  <fmt:message  key="insertLine"/></a>
    </div>  
    <div class="window-body">
        <div class="tableBox">
            <table class="table table-striped table-hover table-bordered"  id="valueTable">
               <tr>
                    <td><span class="Value">value</span></td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <input type="text" class="form-control" />
                    </td>
                    <td><a class="btn-danger btn valueDel" onclick="removeTr(this);"><span class="glyphicon glyphicon-remove" ></span></a></td>
                </tr>            
            </table>
        </div>
    </div>
     <div class="Window-header2">
         <a href="javascript:updateFieldclose()" class="btn btn-gray right"><span class="glyphicon glyphicon-remove" ></span>  <fmt:message  key="resetBtn"/></a>
        <a class="btn btn-gray" onclick="saveSelectList();"><span class="glyphicon glyphicon-save" ></span>  <fmt:message  key="determine"/></a>        
    </div>
</div>
<div class="field-editWindow">
    <div class="field-editWindow-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="editField"/></a>
        <button type="button" class="close field-editWindow-close" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="window-body">
        <div class="tableBox">           
            <table class="field-editTable new-field-table1 left">
            <tr>
            <td><span><fmt:message  key="fieldName"/>：</span></td> 
            <td colspan="2"><input type="text" class="form-control" name="fieldNameUpdate" value="" disabled/></td>
        </tr>
        <tr>
            <td><span><fmt:message  key="showName"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="showNameUpdate" value=""/></td>
        </tr>
        <tr>
            <td><span><fmt:message  key="englishName"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="englishNameUpdate" value=""/></td>
        </tr>
        <tr>
            <td><span><fmt:message  key="dataType"/>：</span></td>
            <td colspan="2">
            	<select  class="form-control  left" name="dataType"  id="dataTypesUpdate" onchange="showAddImg();" disabled>
            		<option value="nvarchar(500)">String</option>
            		<option value="bit">Boolean</option>
            		<option value="datetime">Date</option>
            		<option value="int">Integer</option>
            		<option value="selectList">enum</option>
            	</select>
             	<div id="updateImgBtn" style="display:none"><a class="btn btn-danger right" onclick="showSelectDia();"><span class="glyphicon glyphicon-plus"></span></a></div> 
            </td>
        </tr>
        <tr>
            <td><span><fmt:message  key="category"/>：</span></td>
            <td colspan="1"><select name='type' class='form-control select-control' type='text' id='addSelectTypeUpdate'></select></td>
        </tr>
        <tr>
            <td><span><fmt:message  key="remark"/>：</span></td>
            <td colspan="2"><input type="text" class="form-control" name="descriptionUpdate" value=""/></td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span>IsSearch：<input type="checkbox"  id = "isSearchUpdate" /></span>
                <span>IsDisplay：<input type="checkbox" id="isDisplayUpdate"/></span>
                <span>IsUpdate：<input type="checkbox" id="isUpdateUpdate"/></span>
            </td>
        </tr>
        <tr class="checkbox-span">
            <td colspan="3"><span>IsAudit：<input type="checkbox" id="isAuditUpdate" /></span>
                <span>IsMatch：<input type="checkbox" id="isMatchUpdate"/></span>
                <span>IsNull：<input type="checkbox" id="isNullUpdate"  disabled/></span>
                </td>             
        </tr>
        <tr class="checkbox-span">
            <td colspan="3">
            	<span>IsInsert：<input type="checkbox" id="isInsertUpdate" /></span>
            	<span>IsApply：<input type="checkbox" id="isApplyUpdate" /></span>
            </td>             
        </tr>     
    </table>
    <div class="btns">
                        <a href="javascript:updateFieldclose()" class="btn btn-gray right"><span class="glyphicon glyphicon-remove" ></span>  <fmt:message  key="resetBtn"/></a>
                <a href="javascript:updateFieldAttr()" class="btn btn-gray right"><span class="glyphicon glyphicon-ok" ></span>  <fmt:message  key="determine"/></a>
    </div>
        </div>
    </div>
</div>

<!---------页尾---------->
<%@include file="/footer.jsp" %>
</div>
</div>
<script type="text/javascript" src="scripts/javascript.js"></script>
<script type="text/javascript" src="scripts/fieldDefinition.js"></script>
<script type="text/javascript" src="scripts/partTree.js"></script>

<script>
$(function(){
	 getNodes();
	 $("#specialProperties").hide();
	 $(".select-addWindow,.field-editWindow").draggable();
});
</script>
</body>
</html>