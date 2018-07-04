$(document).ready(function() {
});
function clickLoginBtn(){
	loadProperties();
	//十六进制公钥  
	var loginName = $("#loginName").val();
 	var passWord = $("#passWord").val();
 	   if("" == loginName && "" == passWord){
 		layer.alert($.i18n.prop("check-nameAndPwd"));
		    return; 
 	   }
 	   if("" == loginName){
 		  layer.alert($.i18n.prop("check-loginName"));
 		    return;
 	   }else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(loginName)){
				   layer.alert($.i18n.prop("check-loginNameformat"));
					return;
			    }
 	   }
 	   if("" == passWord){
 		  layer.alert($.i18n.prop("check-password"));
 		   return;
 	   }else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(passWord)){
				   layer.alert($.i18n.prop("check-passwordformat"));
					return;
			    }
 	   }
     $.ajax({
 		   url: "/cms_cloudy/login/getPublicKey.do",
 		   type: 'post',
 		   cache: false,
 		   dataType: "json",
 		   success: function(json){
             	 setMaxDigits(130);
                 var key = new RSAKeyPair("10001","",json.pubmodules); 
                 var pwdMD5Twice = $.md5(passWord);
                 passWord = encryptedString(key, pwdMD5Twice);
                 login(loginName,passWord);
 		   },
 		   error: function(){
 			  layer.alert($.i18n.prop("alertError"));
 		   }
 	   });
}
// 登录
function login(loginName, passWord) {
	$.ajax({
		url : "login/loginCheck/" + 1 + ".do",
		data : {
			"loginName" : loginName,
			"passWord" : passWord
		},
		cache : false,
		dataType : "json",
		success : function(json) {
			if (json.result == "success") {
				if("" != getUrlParamInIndex("loginName") && null != getUrlParamInIndex("loginName")){
					window.location.href=getContextPathForWS()+"/pages/loginpage/index.jsp";
					window.sessionStorage["ExitSign"] = "yes";
				}else{
					window.location.reload();
				}
				$(".login-div>li").hide().next(".dropdown").show();
				$(".login-btn").attr("data-dismiss", "modal");
			}
			if (json.result == "fail") {
				layer.alert($.i18n.prop("check-loginState1"));
				return;
			}
			if (json.result == "notfound") {
				layer.alert($.i18n.prop("check-loginState2"));
				return;
			}
			if (json.result == "morePeople") {
				layer.alert("允许同时在线人数最多为30人！");
				return;
			}
			if (json.result == "onLine") {// 账户已登录
				var hrUser = json.loginUser;
				layer.confirm($.i18n.prop("check-loginPerson"), {
					btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
						// 按钮
				}, function() {
					var socketMsg = "compulsionLogin" + "," + hrUser.loginName;
					sendWebsocket(socketMsg);// 推送
					$.ajax({
								url : "login/compulsionLogin.do",
								dataType : "json",
								cache : false,
								type : "post",
								data : {
									"hrUser" : JSON.stringify(hrUser)
								},
								success : function(json) {
									window.location.reload();
									$(".login-div>li").hide().next(".dropdown")
											.show();
									$(".login-btn").attr("data-dismiss",
											"modal");
								}
							});
				});
			}
		},
		complete : function() {

		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
// 密码重置
function resetPassword(){
	loadProperties();
    var IsBy = $.idcode.validateCode();
    if($("#logincode").val() == "" ){
    	 layer.alert($.i18n.prop("check-loginName"));
    	 return;
    }
    if($("#Txtidcode").val() == "" ){
    	 layer.alert($.i18n.prop("check-Txtcode"));
    	 return;
    }
    if(IsBy == false){
    	 layer.alert($.i18n.prop("check-TxtcodeError"));
    	 return;
    }
     $.ajax({
 		   url: "user/resetPassword.do",
 		   data: {"loginNameReset":$("#logincode").val()},
 		   cache: false,
 		   type: "post",
 		   dataType: "json",
 		   success: function(json){
               if(json == null){
            	   layer.alert($.i18n.prop("check-haveAccord")); 
            	   return;
               }else{
               	$("#logincode").val('');
               	$("#Txtidcode").val('');
               	$("#modal-forget").modal('hide');
                 layer.alert($.i18n.prop("operationSus"));
               }
 		   },
 		   error: function(){
 			  layer.alert($.i18n.prop("alertError"));
 		   }
 	   }); 
}
/**修改密码**/
function modifyPwd(){
   loadProperties();
   var IsBy = $.idcode1.validateCode();
   var passWord = $("input[name='pubPwd1']").val();
   var passWord2 = $("input[name='pubPwd2']").val();
    if("" == passWord){
			  layer.alert($.i18n.prop("check-password"));
			  return;  
		  }else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(passWord)){
				   layer.alert($.i18n.prop("check-passwordformat"));
					return;
 	              }
		  }
          if("" == passWord2){
        	  layer.alert($.i18n.prop("check-password2"));
			  return; 
		  }
          if(passWord != passWord2){
        	  layer.alert($.i18n.prop("check-simplePWD"));
			  return; 
          }
    if($("#Txtidcode1").val() == "" ){
    	 layer.alert($.i18n.prop("check-Txtcode"));
    	 return;
    }
    if(IsBy == false){
    	 layer.alert($.i18n.prop("check-TxtcodeError"));
    	 return;
    }
    passWord = $.md5(passWord);
     $.ajax({
 		   url: "user/modifyPassword.do",
 		   data: {"pubLoginName":$("input[name='pubLoginName']").val(),'passWord':passWord},
 		   cache: false,
 		   type: "post",
 		   dataType: "json",
 		   success: function(json){
               if(json == null){
            	   layer.alert($.i18n.prop("check-haveAccord"));
            	   return;
               }else{
               		layer.alert($.i18n.prop("operationSus"), {
                      }, function(){
                    location.reload();
                  });
                /*$("input[name='pubPwd1']").val('');
                $("input[name='pubPwd2']").val('');
                $("#Txtidcode1").val('');
               	$("#modal-changePawd").modal('hide');//修改窗口关闭
               	 $("#bs-example-navbar-collapse-1").children("ul").html('');
               	 $(".login-div>div").hide().siblings(".login-div>li").show();
               	 foundMenu();
               	 $("#clickLogin").click();*/
               }
 		   },
 		   error: function(){
 			  layer.alert($.i18n.prop("alertError"));
 		   }
 	   }); 
}
function changeLang(language){
   $.ajax({
 		   url: "globalController/changeLanguage.do",
 		   data: {"language":language},
 		   cache: false,
 		   type: "post",
 		   dataType: "json",
 		   success: function(json){
                location.reload();
 		   },
 		   error: function(){
 			  layer.alert("数据连接异常！");
 		   }
 	   }); 
}
/**
 * index页面初始化-------直接登录(C端)
 */
function initLogin(){
	loadProperties();
	//十六进制公钥  
	var loginName = getUrlParamInIndex("loginName");
	var passWord = getUrlParamInIndex("passWord");
 	   if("" == loginName && "" == passWord){
		    return; 
 	   }
 	   if("" == loginName || null == loginName){
 		    return;
 	   }else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(loginName)){
				   layer.alert($.i18n.prop("check-loginNameformat"));
					return;
			    }
 	   }
 	   if("" == passWord || null == passWord){
 		   return;
 	   }else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(passWord)){
				   layer.alert($.i18n.prop("check-passwordformat"));
					return;
			    }
 	   }
     $.ajax({
 		   url: "login/getPublicKey.do",
 		   type: 'post',
 		   cache: false,
 		   dataType: "json",
 		   success: function(json){
             	 setMaxDigits(130);
                 var key = new RSAKeyPair("10001","",json.pubmodules); 
                // var pwdMD5Twice = $.md5(passWord);
                 passWord = encryptedString(key, passWord);
                 login(loginName,passWord);
 		   },
 		   error: function(){
 			  layer.alert($.i18n.prop("alertError"));
 		   }
 	   });
}
// 获取URL参数
function getUrlParamInIndex(key) {
	// 获取参数
	var url = window.location.search;
	// 正则筛选地址栏
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	// 匹配目标参数
	var result = url.substr(1).match(reg);
	// 返回参数值
	return result ? decodeURIComponent(result[2]) : null;
}
//获取根路径
function getContextPath() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}