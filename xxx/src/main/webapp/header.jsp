<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser" pageEncoding="utf-8"%>
<%
String loginName = "";
String language = "zh";
Object languageType=request.getSession().getAttribute("lang");
if(null != languageType){
	language = languageType.toString();
}
language=language.trim();
%>
 <!--高级搜索框-->
<div id="modal-search" class="modal">  <!--半透明遮罩-->
        <div class="modal-content"> <!--背景边框倒角阴影-->
            <div id="search-box-out">
  <div id="search-box">
      <span data-dismiss="modal" class="close">&times;</span>
       <div class="search-title">
          <b><fmt:message  key="gjcxt"/></b>
      </div>
    <div class="search-group">
        <table class="table table-striped table-bordered">
       <!-- <tr>
            <th>
                   关系
            </th>
            <th>
                   条件名称
            </th>
            <th>
                   符号
            </th>
            <th>
                   查询值
            </th>
            <th>
                   操作
            </th>
       </tr> -->
             <tr>
					<td>
					</td>
					<td>
						<select name="" id="filedName1"  class="form-control" onchange="changeSelect('1');">
							<option  value=""><fmt:message  key="selectBtn2"/></option>
						</select>
					</td>
					<td>
						<select name="" id="relation1"  class="form-control">
							<option  value=""><fmt:message  key="selectBtn2"/></option>
						</select>
						</td>
					<td><input type="text" class="form-control" id="dataColumnInput1"  />
					<input type="text" class="form-control" id="dataColumnData1" name="hiredate1" style="display:none"  readonly="readonly"/>
					<select name="" id="dataColumnSelect1"  class="form-control" style="display:none">
							<option value="True">True</option>
							<option value="False">False</option>
						</select>
					</td>
					<td class="searchEmpty"><a class="btn btn-xs btn-danger" onclick="resetData('1')"><fmt:message  key="qk"/></a></td>
				</tr>
				<tr>
					<td>
					<select name="" id="andor1"  class="form-control">
							<option value="and">and</option>
							<option value="or">or</option>
						</select>
					</td>
					<td>
						<select name="" id="filedName2"  class="form-control" onchange="changeSelect('2')">
							<option  value=""><fmt:message  key="selectBtn2"/></option>
						</select>
					</td>
					<td><select name="" id="relation2"  class="form-control">
							<option  value=""><fmt:message  key="selectBtn2"/></option>
						</select>
						</td>
					<td><input type="text" class="form-control" id="dataColumnInput2"  />
					<input type="text" class="form-control" id="dataColumnData2" name="hiredate2" style="display:none" readonly="readonly" />
					<select name="" id="dataColumnSelect2"  class="form-control" style="display:none">
							<option value="True">True</option>
							<option value="False">False</option>
						</select></td>
					<td class="searchEmpty"><a class="btn btn-xs btn-danger" onclick="resetData('2')"><fmt:message  key="qk"/></a></td>
				</tr>
				<tr>
					<td>
					<select name="" id="andor2"  class="form-control">
							<option value="and">and</option>
							<option value="or">or</option>
						</select>
					</td>
					<td>
						<select name="" id="filedName3"  class="form-control" onchange="changeSelect('3')">
							<option  value=""><fmt:message  key="selectBtn2"/></option>
						</select>
					</td>
					<td><select name="" id="relation3"  class="form-control">
							<option value=""><fmt:message  key="selectBtn2"/></option>
						</select>
						</td>
					<td><input type="text" class="form-control" id="dataColumnInput3"  />
					<input type="text" class="form-control" id="dataColumnData3" name="hiredate3" style="display:none" readonly="readonly"/>
					<select name="" id="dataColumnSelect3"  class="form-control" style="display:none">
							<option value="True">True</option>
							<option value="False">False</option>
						</select></td>
					<td class="searchEmpty"><a class="btn btn-xs btn-danger" onclick="resetData('3')"><fmt:message  key="qk"/></a></td>
				</tr>
        </table>
        <a class="SearchMore-btn btn-danger" id="highSearch" href="javascript:void(0);" onclick="searchByCondition('1');"><fmt:message  key="cxbtn"/></a>
      </div>
  </div>
            </div>
        </div>
 </div>
<!--导航及下拉-->
<%-- <div class="nav-son-box">
   <nav class="navbar navbar-default nav-son"> 
            <div class="navbar-header">
                <a class="navbar-brand" href="/cms_cloudy/pages/loginpage/index.jsp"><img src="/cms_cloudy/images/logo1.png" alt="公司logo"/></a>
            </div>
             <div class="no-message">           
             <ul class="login-div right">
                     <li class="index-login"><img src="images/login.png"><a data-target="#modal-login" class="btn1 right" data-toggle="modal"><b><fmt:message  key="loginBtn"></fmt:message></b></a></li>
                    <div class="dropdown right">
                    <button class="btn btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <img src="images/login.png">
                        <% 
                        Object userObj = (HrUser)session.getAttribute("user");
                        HrUser user = (HrUser)userObj;
                        if(user != null){
                        	loginName = user.getLoginName();
                        }else{
                        	loginName = "";
                        }
                        %>
                        <%=loginName %>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>  管理员</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:toMyCollection()"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>  <fmt:message  key="index-collect"/></a></li>
                        <li role="presentation" class="click-forget"  data-target="#modal-forget" data-toggle="modal" data-dismiss="modal"  id="rPwdRight"><a role="menuitem" tabindex="-1"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>  <fmt:message  key="index-resetPWD"/></a></li>
                        <li role="presentation" class="click-changePawd"  data-target="#modal-changePawd" data-toggle="modal" data-dismiss="modal"><a role="menuitem" tabindex="-1"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span>  <fmt:message  key="index-modifyPWD"/></a></li> 
                        <li role="presentation" class="login-out"><a role="menuitem" tabindex="-1" href="pages/loginpage/index.jsp"><span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>  <fmt:message  key="index-exit"/></a></li>
                    </ul>
                </div>
                </ul>    
                 <div class="navbar-form navbar-right" role="search" >
                    <div class="form-group">
                        <input id="model" type="text" class="left" placeholder="<fmt:message  key="specifications"/>"/>
                        <div class="right" id="Search" onclick="searchByCondition('0');"><i></i></div>
                    </div>            
                   <!--  <button type="button" style="background:none;color:#b9b9b9" class="btn btn-default" id="Search" onclick="searchByCondition('0');">查询</button> -->
                    <a data-target="#modal-search" style="background:none;color:#b9b9b9" class="btn btn-default" id="SearchMore" data-toggle="modal"><fmt:message  key="advancedQuery"/></a>
                </div> 
                <div class="change-language right" onclick="changeLanguage()">
                 <%
                 if(language.indexOf("zh")>=0){
                	  out.write("<img src=\"images/Chinese.png\">"); 
                     out.write("<a class=\"language\">中文(简体)</a>");
                 }else{
                   	 out.write("<img src=\"images/English.png\">");
                     out.write("<a class=\"language\">English</a>");                   
                 }
               %>
                </div>                   
        </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">          
                
            </div>
    </nav> 
</div> --%>
<!--导航及下拉new-->
<div class="CmsHeader">
      <div class="searchBox">
           <div class="logoBox left">
                <a class="navbar-brand" href="/cms_cloudy/pages/loginpage/index.jsp"><img src="/cms_cloudy/images/CMSlogo.png" alt="公司logo"/></a>
            </div>           
            <div class="loginBox right">
            <ul class="login-div right">
                     <li class="index-login"><img src="images/login.png"><a data-target="#modal-login" class="btn1 right" data-toggle="modal"><b><fmt:message  key="loginBtn"></fmt:message></b></a></li>
                    <div class="dropdown right">
                    <button class="btn btn-xs dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-expanded="true">
                        <img src="images/login.png">
                        <% 
                        Object userObj = (HrUser)session.getAttribute("user");
                        HrUser user = (HrUser)userObj;
                        if(user != null){
                        	loginName = user.getLoginName();
                        }else{
                        	loginName = "";
                        }
                        %>
                        <%=loginName %>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:void(0)"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>  管理员</a></li>
                        <li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:toMyCollection()"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>  <fmt:message  key="index-collect"/></a></li>
                        <li role="presentation" class="click-forget"  data-target="#modal-forget" data-toggle="modal" data-dismiss="modal"  id="rPwdRight"><a role="menuitem" tabindex="-1"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>  <fmt:message  key="index-resetPWD"/></a></li>
                        <li role="presentation" class="click-changePawd"  data-target="#modal-changePawd" data-toggle="modal" data-dismiss="modal"><a role="menuitem" tabindex="-1"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span>  <fmt:message  key="index-modifyPWD"/></a></li> 
                        <li role="presentation" class="login-out"><a role="menuitem" tabindex="-1" href="pages/loginpage/index.jsp"><span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>  <fmt:message  key="index-exit"/></a></li>
                    </ul>
                </div>
                </ul>    
            <div class="login left">
                 <div class="change-language right" onclick="changeLanguage()">
                 <%
                 if(language.indexOf("zh")>=0){
                	  out.write("<img src=\"images/Chinese.png\">"); 
                     out.write("<a class=\"language\">中文(简体)</a>");
                 }else{
                   	 out.write("<img src=\"images/English.png\">");
                     out.write("<a class=\"language\">English</a>");                   
                 }
               %>
                </div> 
            </div>
            </div>
             <div class="searchInput right">
                    <div class="navbar-form navbar-right" role="search" >
                    <div class="form-group">
                        <input id="model" type="text" class="left" placeholder="<fmt:message  key="specifications"/>"/>
                        <div class="right" id="Search" onclick="searchByCondition('0');"><i></i></div>
                    </div>            
                   <!--  <button type="button" style="background:none;color:#b9b9b9" class="btn btn-default" id="Search" onclick="searchByCondition('0');">查询</button> -->
                    <a data-target="#modal-search" style="background:none;color:#b9b9b9" class="btn btn-default" id="SearchMore" data-toggle="modal"><fmt:message  key="advancedQuery"/></a>
                </div> 
            </div>
      </div>
      <div class="menu" id="bs-example-navbar-collapse-1">

      </div>
</div>
<script>
/* $(function(){
	$(".navLi").hover(function(){
		$(this).children("ul").show().children("a").css("color","#333").css("background","#ccc");
},function(){
	$(this).children("ul").hide().children("a").css("color","#fff").css("background","#383838");
});
}) */
</script>
