<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser" pageEncoding="utf-8"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
HrUser userObjs = (HrUser)session.getAttribute("user");
String str="";
%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <link href="<%=path %>/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=path %>/css/index.css"/>
    <link rel="stylesheet" href="<%=path %>/css/cms-1.css"/>
    <link rel="stylesheet" href="<%=path %>/css/animate.css">
    <script type="text/javascript" src="<%=path %>/scripts/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/ajaxfileupload.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<%=path %>/scripts/respond.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/html5shiv.min.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/DOMAssistantCompressed-2.7.4.js"></script>
    <script type="text/javascript" src="<%=path %>/scripts/ie-css3.js"></script>
    <![endif]-->
      <script type="text/javascript">
    $(function(){
          $.ajax({
        	url: "<%=path %>/user/selectAllUser.do",
  			dataType: "json",
  			cache: false, 
  			success: function(json){
  				var html = '';
  				if(null == json || "" == json){
					html +='<tr>';
					html +='<td colspan="13" align="center">--没有数据--</td>';
					html +='</tr>';
				}else{
					alert(json);
					for( var i = 0;i<json.length;i++){
					html +='<tr>';
					html +='<td>'+json[i].employeeNumber+'</td>';
					html +='<td>'+json[i].userNumber+'</td>';
					html +='<td>'+json[i].userName+'</td>';
					html +='<td>'+json[i].loginName+'</td>';
					html +='<td class="edit-select">'+json[i].position+'</td>';
					html +='<td>'+json[i].email+'</td>';
					html +='<td>'+json[i].telephone+'</td>';
					html +='<td>'+json[i].mobilePhone+'</td>';
					html +='<td class="edit-select">'+json[i].department+'</td>';
					html +='<td class="center">'+json[i].createuser+'</td>';
					html +='<td>'+json[i].createtime+'</td>';
					html +='<td>'+json[i].isOrNot+'</td>';
					html +='<td class="edit-td">';
					html +='<div class="user-btnGroup1" style="display:none">';
					html +='<a href="#" class="btn btn-primary btn-xs user-save"><span class="glyphicon glyphicon-edit"></span>保存</a>';
               	    html +='<a href="#" class="btn btn-success btn-xs user-edit-delete"><span class="glyphicon glyphicon-remove"></span>取消</a>';
               	    html +='</div>';
					html +='<div class="user-btnGroup2">';
					html +='<a href="#" class="btn btn-info btn-xs user-edit"><span class="glyphicon glyphicon-edit"></span> 编辑</a>';
               	    html +='<a href="javascript:deleteUser('+json[i].userId+')" class="btn btn-danger btn-xs user-delete"><span class="glyphicon glyphicon-remove"></span>删除</a>';
               	    html +='<a href="#" class="btn btn-warning btn-xs user-add"><span class="glyphicon glyphicon-plus"></span> 添加</a>';
               	 <!-- <a class="btn btn-warning btn-xs user-add"><span class="glyphicon glyphicon-plus"></span>  添加</a> -->
               	    html +='</div>';
               	    html +='</td>';
					html +='</tr>';
					}
				}
	             $('#show').html(html);
  			},
  			error: function(){
  				alert("数据连接异常,注册失败！");
  			}
          });
    	   $.ajax({
    		   
    		   url: "<%=path %>/login/hasLogined.do",
    		   cache: false,
    		   dataType: "json",
    		   success: function(json){
    			   if(json.result == "yes"){
    				   $(".login-div>li").hide().next(".dropdown").show();
    		           $(".login-btn").attr("data-dismiss","modal");
    			   }
    		   },
    		   error: function(){
    			   alert("数据连接异常,注册失败！");
    		   }
    	   });
    });
      /*  function login(){
    	   var loginName = $("#loginName").val();
    	   var passWord = $("#passWord").val();
    	   if("" == loginName && "" == passWord){
    		alert("请输入帐号和密码!");
   		    return; 
    	   }
    	   if("" == loginName){
    		    alert("请输入帐号!");
    		    return;
    	   }else{
    		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(loginName)){
					alert("帐号格式错误,暂时只支持英文字母或数字!");
					return;
			    }
    	   }
    	   if("" == passWord){
    		   alert("请输入密码!");
    		   return;
    	   }else{
    		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(passWord)){
					alert("密码格式错误,暂时只支持英文字母或数字!");
					return;
			    }
    	   }
    	   var test = 1;
     	   $.ajax({
    		   url: "login/loginCheck/"+1+".do",
    		   data: {"loginName":loginName,"passWord":passWord},
    		   cache: false,
    		   dataType: "json",
    		   success: function(json){
    			   alert(json);
    		   },
    		   error: function(){
    			   alert("数据连接异常！");
    		   }
    	   }); 
       } */
       /*导出excel*/
       function exportExcel(){
   		document.location.href='/cms_cloudy/export';
       }
       /*导入excel*/
       function readExcel(){
    	   $.ajaxFileUpload
	        (
	            {
	                url:'<%=path %>/user/readExcel.do',
	                secureuri:false,//一般设置为false
	                fileElementId:'bb',//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                dataType: 'json',//返回值类型 一般设置为json
	                success: function (data, status)  //服务器成功响应处理函数
	                {
	                    //alert(data.result);//从服务器返回的json中取出message中的数据,其中message为在struts2中action中定义的成员变量
	                    if(data.result == "fail"){
	                    	layer.alert("对不起，英文名不能为空,请检查!");
	                    	return;
	                    }
	                    else if(data.result == "ripeat"){
	                    	layer.alert("对不起，上传文件中英文名不能与数据库中英文名重复，请检查!");
	                    	return;
	                    }
	                    else if(data.result == "ripeatExcel"){
	                    	layer.alert("对不起,上传文件中英文名不能重复,请检查!");
	                    	return;
	                    }else{
	                    	layer.alert("导出成功!");
	                    }
	                },
	                error: function (data, status, e)//服务器响应失败处理函数
	                {
	                	layer.alert("数据连接异常,请联系管理员!");
	                }
	            }
	        )
       }
       function deleteUser(pid){
    	   alert(pid);
    	    $.ajax({
            	url: "/cms_cloudy/user/deleteUser.do",
            	cache: false,
            	data: {"id":pid},
            	dataType: "json",
            	success: function(json){
            		if(json.result == "success"){
      	 			  layer.alert("删除成功！");
            		}
            	},
            	error: function(){
    	 			  layer.alert("数据连接异常！");
            	}
            });
       }
       function insertUser(){
    	   var loginName=$("#loginNameSave").val();
    	   var password=$("#passwordSave").val();
    	   var password2=$("#password2Save").val();
    	   var email=$("#emailSave").val();
    	   $.ajax({
    		   url: "/cms_cloudy/user/inserUser.do",
   	    	   contentType:'application/json;charset=UTF-8', 
    		   data: {"loginName":loginName,"passWord":password,"email":encodeURI(email)},
    		   cache: false,
    		   dataType: "json",
    		   success: function(json){
    			   if(json.result == "update"){
    				   layer.alert("更新成功!");
    				   $(".register-box").hide();
    			   }
    			   if(json.result == "insert"){
    				   layer.alert("注册 成功!");
    				   $(".register-box").hide();
    			   }
    			   if(json.result == "fail"){
    				   layer.alert("注册失败,请检查数据的正确性!");
    			   }
    		   },
    		   error: function(){
    			   layer.alert("数据连接异常,注册失败！");
    		   }
    	   });
       }
    </script>
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
                    <input type="text" id="loginName" placeholder="请输入账号"/><br/><br/>
          <input type="password" id = "passWord" placeholder="请输入密码"/><br/><br/>
          <a class="login-btn" href="#">登录</a><br/>
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
                <input type="text" id="loginNameSave" placeholder="请输入账号"/><br/><br/>
                <input type="password" id="passwordSave" placeholder="请输入密码"/><br/><br/>
                <input type="password"  id="password2Save" placeholder="请确认密码"/><br/><br/>
                <input type="email" id="emailSave" placeholder="请输入邮箱"/><br/><br/>
                <div class="register-statement">
                        <input type="checkbox" class="register-check"/>
                        <i>我已认真阅读并接受</i>
                        <a href="#">《CmsCloudy声明》</a>
                    </div><br/>
                 <a class="register-btn" href="javascript:insertUser()">注册</a><br/>
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
<div class="nav-son-box">
    <a href="#content" class="sr-only sr-only-focusable">直接进入主内容区</a>
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
                <a class="navbar-brand" href="<%=path %>/pages/loginpage/login.jsp"><img src="<%=path %>/images/logonew.png" alt="公司logo"/></a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-nav-son index-nav">
                    <li class="active"><a href="<%=path %>/pages/loginpage/login.jsp">首页<span class="sr-only">(current)</span></a></li>
                    <li class="dropdown">
                        <a href="<%=path %>/pages/loginpage/cms-1.jsp" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">元器件库 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">常用库</a></li>
                            <li><a href="#">基础库</a></li>
                            <li><a href="#">电阻器</a></li>
                            <li><a href="#">电容器</a></li>
                            <li><a href="#">半导体分立器件</a></li>
                        </ul>
                    </li>
                    <li><a href="#">Link</a></li>
                    <li><a href="#">Link</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">用户管理 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="<%=path %>/pages/loginpage/cms-1.jsp">员工管理</a></li>
                            <li><a href="<%=path %>/pages/loginpage/cms-1.jsp">部门管理</a></li>
                            <li><a href="<%=path %>/pages/loginpage/cms-1.jsp">职位管理</a></li>
                            <li><a href="<%=path %>/pages/loginpage/cms-1.jsp">组管理</a></li>
                            <li class="divider"></li>
                            <li><a href="#">权限管理</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">流程管理 <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li><a href="#">流程信息</a></li>
                            <li><a href="#">流程任务</a></li>
                            <li><a href="#">流程设置</a></li>
                        </ul>
                    </li>
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
                            <% 
                        if(userObjs != null){
                        	str = userObjs.getLoginName();
                        }else{
                        	str = "迪丽热巴";
                        }
                        %>
                        <%=str %>
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-paperclip" aria-hidden="true"></span>  管理员</a></li>
                            <!--<li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>  Another action</a></li>
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>  Something else here</a></li>-->
                            <li role="presentation" class="login-out"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>  退出</a></li>
                        </ul>
                    </div>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
  <!--  <div class="soso-box">
        <input type="text" class="soso left" placeholder="搜索元器件"/>
        <div class="soso-btn btn btn-danger right">Search</div>
        <div class="clear"></div>
        <p class="soso-example">搜索示例：<span>h7468hfg86</span></p>
        <div class="soso-word">电子物料云管理大数据协作平台</div>
        <p class="soso-intro">电子元器件一体化设计管理</p>
    </div>
    <div class="todown">
        <a class="scroll_main"></a>
    </div>-->
</div>
<!---------表格---------->
<div id="main">
    <div>
        <ul class="nav nav-tabs" role="tablist" id="myTab">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">员工管理</a></li>
            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">职位管理</a></li>
            <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">部门管理</a></li>
            <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">权限管理</a></li>
        </ul>

        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">
                <div class="table-responsive">
                    <div class="batch">
                        <div class="left tr-number-box">
                            <a class="tr-number btn btn-danger"><select name="tr-number">
                                <option value="">10</option>
                                <option value="">20</option>
                                <option value="">all</option>
                            </select></a>
                            <a class="btn batch-btn btn-warning"><input type="file" name="userFile" onchange="readExcel(this)" id="bb"/>批量导入</a>
                            <a class="btn batch-btn btn-success" href="javascript:exportExcel()">批量导出</a>
                        </div>
                        <div class="input-group right soso-user">
                            <input type="text" class="form-control" placeholder="请输入用户名" aria-describedby="basic-addon2">
                            <span class="input-group-addon" id="basic-addon2"><span class="glyphicon glyphicon-search"></span></span>
                        </div>
                    </div>
                    <table class="table table-striped table-hover table-bordered" id="user-table">
                        <thead>
                        <tr role="row">
                            <th>
                                员工编号
                            </th>
                            <th>
                                用户编号
                            </th>
                            <th>
                                中文名
                            </th>
                            <th>
                                英文名
                            </th>
                            <th>
                                任职职位
                            </th>
                            <th>
                                邮箱
                            </th>
                            <th>
                                电话
                            </th>
                            <th>
                                手机
                            </th>
                            <th>
                                所在部门
                            </th>
                            <th>
                                创建者
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                是否离职
                            </th>
                            <th>
                                编辑
                            </th>
                        </tr>
                        </thead>

                        <tbody id="show">
<!--                         <tr> -->
<!--                             <td>1234</td> -->
<!--                             <td>1234</td> -->
<!--                             <td>邓超</td> -->
<!--                             <td>dengchao</td> -->
<!--                             <td class="edit-select">经理</td> -->
<!--                             <td>www.com</td> -->
<!--                             <td>12345678</td> -->
<!--                             <td>12345678</td> -->
<!--                             <td>开发部</td> -->
<!--                             <td class="center ">李晨</td> -->
<!--                             <td>17/7/11</td> -->
<!--                             <td>否</td> -->
<!--                             <td class="edit-td"><div class="user-btnGroup1" style="display:none"> -->
<!--                                 <a class="btn btn-primary btn-xs user-save"><span class="glyphicon glyphicon-edit"></span>  保存</a> -->
<!--                                 <a class="btn btn-success btn-xs user-edit-delete"><span class="glyphicon glyphicon-remove"></span>  取消</a> -->
<!--                             </div> -->
<!--                                 <div class="user-btnGroup2"> -->
<!--                                     <a class="btn btn-info btn-xs user-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a> -->
<!--                                     <a class="btn btn-danger btn-xs user-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a> -->
<!--                                     <a class="btn btn-warning btn-xs user-add"><span class="glyphicon glyphicon-plus"></span>  添加</a> -->
<!--                                 </div> -->
<!--                             </td> -->
<!--                         </tr> -->
                        </tbody>
                        <tbody id="hide">
                        <tr style="display:none" id="hide_tr">
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td class="edit-select"><select name='' class='form-control select-control' type='text'><option value='经理'>经理</option><option value='主管'>主管</option></select></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td><input value='' type='text' class="form-control"></td>
                            <td class="edit-td"><div class="user-btnGroup1">
                                <a class="btn btn-primary btn-xs user-save"><span class="glyphicon glyphicon-edit"></span>  保存</a>
                                <a class="btn btn-danger btn-xs user-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
                            </div>
                                <div class="user-btnGroup2" style="display:none">
                                    <a class="btn btn-info btn-xs user-edit"><span class="glyphicon glyphicon-edit"></span>  编辑</a>
                                    <a class="btn btn-danger btn-xs user-delete"><span class="glyphicon glyphicon-remove"></span>  删除</a>
                                    <a class="btn btn-warning btn-xs user-add"><span class="glyphicon glyphicon-plus"></span>  添加</a>
                                </div>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                    <div class="table-footer">
                        <ul class="pagination">
                            <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">Previous</span></a></li>
                            <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li><a href="#">Next</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div role="tabpanel" class="tab-pane" id="profile">
                <table class="table table-bordered table-hover">
                    <thead class="table-title">
                    <tr class="warning">
                        <td>用户名</td>
                        <td>员工号</td>
                        <td>职位</td>
                        <td>组别</td>
                        <td>编辑</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>小红</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小绿</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小兰</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小黑</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小白</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小花</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小粉</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div role="tabpanel" class="tab-pane" id="messages">
                <table class="table table-bordered table-hover">
                    <thead class="table-title">
                    <tr class="warning">
                        <td>用户名</td>
                        <td>员工号</td>
                        <td>职位</td>
                        <td>组别</td>
                        <td>编辑</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>小红</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小绿</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小兰</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小黑</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小白</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小花</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小粉</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div role="tabpanel" class="tab-pane" id="settings">
                <table class="table table-bordered table-hover">
                    <thead class="table-title">
                    <tr class="warning">
                        <td>用户名</td>
                        <td>员工号</td>
                        <td>职位</td>
                        <td>组别</td>
                        <td>编辑</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>小红</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小绿</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小兰</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小黑</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小白</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小花</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>小粉</td>
                        <td>1234</td>
                        <td>开发工程师</td>
                        <td>开发2组</td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

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
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
</body>
</html>