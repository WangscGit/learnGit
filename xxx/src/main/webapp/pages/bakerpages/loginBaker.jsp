<%@ page language="java" pageEncoding="utf-8"%>  
<!DOCTYPE html>
<html lang="en">
<head>
    <title>宇航元器件选用平台</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css"/>
    <link rel="stylesheet" href="css/animate.css">
    <script type="text/javascript" src="scripts/jquery-1.11.3.js"></script>
    <script type="text/javascript" src="scripts/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="scripts/respond.min.js"></script>
    <script type="text/javascript" src="scripts/html5shiv.min.js"></script>
    <script type="text/javascript" src="scripts/DOMAssistantCompressed-2.7.4.js"></script>
    <script type="text/javascript" src="scripts/ie-css3.js"></script>
    <![endif]-->
    <script type="text/javascript">
       function login(){
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
       }
       function insertUser(){
    	   var loginName=$("#loginNameSave").val();
    	   var password=$("#passwordSave").val();
    	   var password2=$("#password2Save").val();
    	   var email=$("#emailSave").val();
    	   alert(email);
    	   $.ajax({
    		   url: "user/inserUser.do",
   	    	   contentType:'application/json;charset=UTF-8', 
    		   data: {"loginName":loginName,"passWord":password,"email":encodeURI(email)},
    		   cache: false,
    		   dataType: "json",
    		   success: function(json){
    			   alert(json);
    		   },
    		   error: function(){
    			   alert("数据连接异常,注册失败！");
    		   }
    	   });
       }
    </script>
</head>
<body>  

<div id="modal-login" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
         <div id="login-box-out">
  <div id="login-box">
      <span data-dismiss="modal" class="close">&times;</span>
      <div class="login-title">
          <a><img src="images/cloud.png"/></a>
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
          <a class="login-btn" href="javascript:login()">登录</a><br/>
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
<div id="modal-register" class="modal">  <!--半透明遮罩-->
        <div class="modal-content"> <!--背景边框倒角阴影-->
            <div id="register-box-out">
  <div id="register-box">
      <span data-dismiss="modal" class="close">&times;</span>
      <div class="login-title">
          <a><img src="images/cloud.png"/></a>
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
          <input type="password" id="password2Save" placeholder="请确认密码"/><br/><br/>
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

  <div class="hah">
      <input data-target="#modal-login" class="btn1 btn btn-success" data-toggle="modal" value="登录">
      <input data-target="#modal-register" class="btn2 btn btn-success" data-toggle="modal" value="注册">
  </div>
  <script type="text/javascript" src="scripts/javascript.js"></script>
</body>  
</html> 