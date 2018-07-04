<%@ page language="java" import="com.cms_cloudy.user.pojo.HrUser,java.util.*"  pageEncoding="utf-8"%>
<% 
String langPub = "zh";
Object langPubType=request.getSession().getAttribute("lang");
if(null != langPubType){
	langPub = langPubType.toString();
}
langPub=langPub.trim();
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="<%=langPub %>"/>
<fmt:setBundle basename="messages" />
<!--推送框星级显示-->
<script type="text/javascript" src="scripts/jquery.raty.min.js">
</script>
<!--登录框-->
 <div id="modal-login" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
         <div id="login-box-out">
  <div id="login-box">
      <span data-dismiss="modal" class="close">&times;</span>
      <div class="login-title">
          <a><img src="images/logoSmall.png"/></a>
          <b><fmt:message  key="cmsMain"></fmt:message></b>
      </div>
      <br/>
      <div class="with-line">
          <div class="line"></div>
          <b><fmt:message  key="login-P1"/></b>
          <div class="line"></div>
      </div>
      <br/>
      <div class="input-group">
      <div>
          <input type="text" class="form-control" id="loginName" placeholder="<fmt:message  key="placeholder-loginname"/>"/>
          <input type="password"  class="form-control" id = "passWord" placeholder="<fmt:message  key="placeholder-password"/>"/>
          <!--[if IE 9]>
                       <style>
                                   /* css in IE 9 */
                                  .ie9-only{
                                           display:inline-block;                                                                              
                                   }
                       </style>
               <p class="ie9-only">请输入密码</p>
          <![endif]-->       
           </div>
          <div class="clear"></div>
          <div class="loginBOX"><a class="login-btn" onclick="clickLoginBtn();"><fmt:message  key="loginBtn"/></a></div><br/>
          <p class="Areyousure">
              <span class="left" style="display:none;"><a class="click-forget" data-target="#modal-forget" data-toggle="modal" data-dismiss="modal">忘记密码>></a></span>
              <span class="right click-register" data-toggle="modal" data-dismiss="modal" data-target="#modal-register"><fmt:message  key="registerBtn"/></span>
              <b class="right"><fmt:message  key="login-p2"/></b>
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
          <a><img src="images/logoSmall.png"/></a>
          <b><fmt:message  key="cmsMain"></fmt:message></b>
      </div>
      <br/>
      <div class="with-line">
          <div class="line"></div>
          <b><fmt:message  key="login-p3"/></b>
          <div class="line"></div>
      </div>
    <div class="input-group">
         <input  class="form-control" type="text" id="loginNameSave" placeholder="<fmt:message  key="placeholder-loginname"/>"/><input type="email" id="emailSave" class="form-control reright" name="emailSave" placeholder="<fmt:message  key="placeholder-email"/>"/>       
          <input  class="form-control" type="password" id="passwordSave" placeholder="<fmt:message  key="placeholder-password"/>"/><input  class="form-control reright" type="password"  id="password2Save" placeholder="<fmt:message  key="placeholder-confirmPassword"/>"/>
          <!--[if IE 9]>
                       <style>
                                   /* css in IE 9 */
                                  .ie9-only1{
                                           display:inline-block;                                          
                                           font-size:12px;
                                           width:48%;      
                                           float:left;  
                                           padding-left:10px;                                                                           
                                   }
                       </style>
               <p class="ie9-only1">请输入密码</p>
               <p class="ie9-only1">请确认密码</p>
          <![endif]-->                    
           <input type="text" class="form-control" name="employeeNumber" id="employeeNumber" placeholder="<fmt:message  key="placeholder-employeeNum"/>"/><input type="text" class="form-control reright" name="userNumber" id="userNumber" placeholder="<fmt:message  key="placeholder-userNum"/>"/>
          <input type="text" class="form-control" name="userName" id="reuserName" placeholder="<fmt:message  key="placeholder-userName"/>"/><input type="text" class="form-control reright" name="telephone" id="telephone" placeholder="<fmt:message  key="placeholder-telephone"/>"/>   
          <input type="text" class="form-control mobilePhone" name="mobilePhone" id="mobilePhone" placeholder="<fmt:message  key="placeholder-mobilephone"/>"/>            
          <select name='type' class='form-control select-control'  id='addDepartment' name="department"></select>
          <select name='type' class='form-control select-control reright'  name="isOrNot">
                <option value=""><fmt:message  key="placeholder-onJob"/></option>
                <option value="0">在职</option>
                <option value="1">离职</option>
          </select>            
          <div class="register-statement">
              <%-- <input type="checkbox" class="register-check"/>
              <i><fmt:message  key="login-agree"/></i>
              <a href="#"><fmt:message  key="cmsAgreement"/></a> --%>
              <span>*</span><i><fmt:message  key="register-word"/></i>
          </div> 
          <br/> 
          <div><a class="register-btn" href="javascript:addRegisterUser()"><fmt:message  key="registered"/></a></div><br/>
          <p class="Areyousure">
              <span class="right click-login" data-target="#modal-login" data-toggle="modal" data-dismiss="modal"><fmt:message  key="login-p5"/></span>
              <b class="right"><fmt:message  key="login-p4"/></b>
          </p>
      </div>
  </div>
            </div>
        </div>
 </div>
 <!--忘记密码框-->
<div id="modal-forget" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"> <!--背景边框倒角阴影-->
         <div id="forget-box-out">
  <div id="forget-box">
      <span data-dismiss="modal" class="close">&times;</span>
      <div class="forget-title">
          <a><img src="images/logoSmall.png"/></a>
          <b><fmt:message  key="cmsMain"></fmt:message></b>
      </div>
      <br/>
      <div class="with-line">
          <div class="line"></div>
          <b>&nbsp;&nbsp;<fmt:message  key="public-resetPWD"/>&nbsp;&nbsp;</b>
          <div class="line"></div>
      </div>
      <br/>
      <div class="input-group">
      <div>
          <input type="text" class="form-control" id="logincode" placeholder="<fmt:message  key="placeholder-loginname"/>"/>
          <div class="clear"></div>
          <div class="code-box"><input type="text" id="Txtidcode" class="txtVerification" placeholder="<fmt:message  key="public-checkNo"/>"/><span id="idcode"></span></div>       
          </div>
          <div class="clear"></div>
          <div class="loginBOX"><a class="forget-btn" href="javascript:resetPassword()"><fmt:message  key="public-resetBtn"/></a></div>    
      </div>
  </div>
         </div>
    </div>
</div>
<!--修改密码框-->
<div id="modal-changePawd" class="modal">  <!--半透明遮罩-->
    <div class="modal-content"><!--背景边框倒角阴影-->
         <div id="changePawd-box-out">
  <div id="changePawd-box">
      <span data-dismiss="modal" class="close">&times;</span>
      <div class="changePawd-title">
          <a><img src="images/logoSmall.png"/></a>
          <b><fmt:message  key="cmsMain"></fmt:message></b>
      </div>
      <br/>
      <div class="with-line">
          <div class="line"></div>
          <b>&nbsp;&nbsp;<fmt:message  key="public-modifyPWD"/>&nbsp;&nbsp;</b>
          <div class="line"></div>
      </div>
      <br/>
      <div class="input-group">
      <div>
          <input type="text" class="form-control"  name = "pubLoginName" disabled placeholder="<fmt:message  key="placeholder-loginname"/>"/>
          <input type="password" class="form-control" name="pubPwd1" placeholder="<fmt:message  key="placeholder-newPassword"/>"/>
          <input type="password" class="form-control" name="pubPwd2" placeholder="<fmt:message  key="placeholder-confirmPassword"/>"/>
          <div class="clear"></div>
          <div class="code-box"><input type="text" id="Txtidcode1" class="txtVerification" placeholder="<fmt:message  key="public-checkNo"/>"/><span id="idcode1"></span></div>       
          </div>
          <div class="clear"></div>
          <div class="loginBOX"><a class="changePawd-btn" href="javascript:modifyPwd()"><fmt:message  key="modifyBtn"/></a></div>    
      </div>
  </div>
         </div>
    </div>
</div>
<!-- 侧边栏-->
  <div class="fixedSide" id="fixedSideId">
            <ul>
                <li onclick="toMyCollection();" class="message-li myCollect"><em class="tab-text"><fmt:message  key="index-collect"/></em></li>
                <li id="workNum" class="message-li waitWork"><b class="work-num"></b><em class="tab-text"><fmt:message  key="to-doTasks"/></em></li>
               <!--  <li data-toggle="tooltip" data-placement="left" title="0415-2487859"><img src="images/phone.png" alt="" class="phone-img"/></li> -->
                <li id="messageNum" class="message-li notMessage"><b class="messageNumber"></b><em class="tab-text"><fmt:message  key="unreadMessages"/></em></li>
                <li class="newSend message-li cleverSend"><em class="tab-text"><fmt:message  key="intelligentPush"/></em></li>
                <li id="compareNum" class="message-li myCompare"><b class="compare-num">0</b><em class="tab-text"><fmt:message  key="myCompare"/></em></li>
            </ul>
  </div>
  <!-- 置顶按钮-->
        <div id="To-top">
            <a><img src="images/To-top.png" alt=""  id="test"/></a>
        </div>
    <!-- 收到邮件提示框-->    
       <div class="receiveEmail">
            <div class="receiveEmailTop">
                <p><b>提示</b></p>
                <div class="none-email" id="none-email" style="cursor:pointer;">
                    <img src="images/none-email.png">
                    <b>未读消息</b>
                    <span><b>+1</b></span>
                </div>
            </div>
            <p class="hulue">关闭</p>
       </div>
<div class="IntelligentPush-box" id="IntelligentPushId">
          <div class="IntelligentPush-header">
                  <img class="light" src="images/light.png">
                  <span><fmt:message  key="public-find"/></span>
                  <!-- <button type="button" class="close select-addWindow-close" aria-label="Close"><span>&times;</span></button> -->
                   <img class="right newSend btnClose" src="images/pushClose.png">
          </div>
          <div class="IntelligentPush-body scrollbox">
                      <b><fmt:message  key="public-look"/></b>
                      <div id="scrollDiv">
                     </div>
                     <b><fmt:message  key="public-Recommend"/></b>
                     <div id="newPartMain">
                     </div>                    
                     <!-- <div class="Notsend"><span class="glyphicon glyphicon-off"></span><a>不再推送</a></div> -->
          </div>          
</div>
<div id="LoadingPng">  <!--半透明遮罩-->
    <div class="LoadingBox"><img src="images/loading1.gif"></div>
</div>
<!-- <div style="disaplay: none;"> -->
<!-- 	<iframe id=ref1 style="display: none" -->
<!-- 		src="pages/data/ref1.jsp"></iframe> -->
<!-- </div> -->
<script type="text/javascript" src="scripts/partdatas.js"></script>
<script type="text/javascript" src="scripts/userRegister.js"></script>
<script type="text/javascript" src="scripts/jq_scroll.js"></script>
<!-- 加密 -->
<script src="scripts/jQuery.md5.js" type="text/javascript"></script>
<script src="scripts/BigInt.js" type="text/javascript"></script>
<script src="scripts/RSA.js" type="text/javascript"></script>
<script src="scripts/Barrett.js" type="text/javascript"></script>
<script type="text/javascript">
 $(function(){
	 $("[name='hiredate1']").datepicker(
				{dateFormat: "yy年mm月dd日"}
			);
	 $("[name='hiredate2']").datepicker(
				{dateFormat: "yy年mm月dd日"}
			);
	 $("[name='hiredate3']").datepicker(
				{dateFormat: "yy年mm月dd日"}
			);
	 /* 邮件提示框 */
 	 function showEmailmessage(){		  
 		 $(".receiveEmail").css({"display":"block"}).css({right: -260,bottom:-150}).animate({right: 0,bottom:0}, 1000);
	 }  
 	    $(".hulue").click(function(){
 	    	$(".receiveEmail").css({right: 0,bottom:0}).animate({right: -260,bottom:-150}, 1000).css({"display":"block"});
 	    })
 	    $("#none-email").on("click",function(){
 			window.location.href=getContextPathForWS()+"/pages/messagePage/cms-message.jsp";
        });
	/**用户是否登录**/
	checkLogin();
	//构建菜单
	foundMenu();
	/**对比数显示**/
	putSeachFiled();
	/**待办数量显示**/
	getWorkNum();
	var compareNum = Array();
	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	var  eleShopCart = document.querySelector("#compareNum");
	if(null != compareNum){
		eleShopCart.querySelector(".compare-num").innerHTML = compareNum.length;
	}
	//搜索回车事件
	$('#model').keydown(function(e){
		e = arguments.callee.caller.arguments[0]||window.event;
		if(e.keyCode==13){
			searchByCondition('0');
		}
	});
	//登录回车事件
	$('#passWord').keydown(function(e){
		e = arguments.callee.caller.arguments[0]||window.event;
		if(e.keyCode==13){
			clickLoginBtn();
		}
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
		if( $('#model').val()=="" ){
			$('#model').css({
	    		"color" : "#ccc"  
			})  
			$('#model').val("型号规格:eg.RMK1608*j");  
		}  
		$('#model').focus(function () {
			if( $(this).val()=="型号规格:eg.RMK1608*j" ){  
	    		$(this).val("");  
	    	}  
	    	$('#model').css({  
	        	"color" : "#fff"  
	    	})  
		})    
		$('#model').blur(function () {
			if( $(this).val()=="" ){
				$(this).val("型号规格:eg.RMK1608*j");  
				$('#model').css({
	                "color" : "#ccc"  
	            })  
	    	}else {
	    		$('#model').css({  
	        		"color" : "#fff"  
	        	})  
	    }  
		})  
		if( $('#searchInput').val()=="" ){
			$('#searchInput').css({
	    		"color" : "#ccc"  
			})  
			$('#searchInput').val("搜索元器件");  
		}  
		$('#searchInput').focus(function () {
			if( $(this).val()=="搜索元器件" ){  
	    		$(this).val("");  
	    	}  
	    	$('#searchInput').css({  
	        	"color" : "#fff"  
	    	})  
		})    
		$('#searchInput').blur(function () {
			if( $(this).val()=="" ){
				$(this).val("搜索元器件");  
				$('#searchInput').css({
	                "color" : "#ccc"  
	            })  
	    	}else {
	    		$('#searchInput').css({  
	        		"color" : "#fff"  
	        	})  
	    }  
		})  
		 if($('#loginName').val()==""||$('#loginNameSave').val()==""||$('#emailSave').val()==""||$('#loginNameSave').val()==""||$('#emailSave').val()==""||$('#employeeNumber').val()==""||$('#userNumber').val()==""||$('#reuserName').val()==""||$('#telephone').val()==""||$('#mobilePhone').val()==""){  
	            $('#searchInput,#loginName,#passWord').css({  
	                "color" : "#666"  
	            })  	            
	            $('#loginName').val("请输入账号");
	            $('#loginNameSave').val("请输入账号");
	            $('#emailSave').val("请输入邮箱");
	            $('#employeeNumber').val("请输入员工编号");
	            $('#userNumber').val("请输入用户编号");
	            $('#reuserName').val("请输入中文名");
	            $('#telephone').val("请输入电话");
	            $('#mobilePhone').val("请输入手机");
	        }  	        
	        $('#loginName').focus(function () {
	        	if( $(this).val()=="请输入账号" ){  
	                $(this).val("");  
	            }  
	            $('#loginName').css({  
	                "color" : "#666"  
	            })  
	        })    
	        $('#loginNameSave').focus(function () {
	        	if( $(this).val()=="请输入账号" ){  
	                $(this).val("");  
	            }  
	            $('#loginNameSave').css({  
	                "color" : "#666"  
	            })  
	        })    
	        $('#emailSave').focus(function () {
	        	if( $(this).val()=="请输入邮箱" ){  
	                $(this).val("");  
	            }  
	            $('#emailSave').css({  
	                "color" : "#666"  
	            })  
	        })    	
	         $('#employeeNumber').focus(function () {
        	if( $(this).val()=="请输入员工编号" ){  
                $(this).val("");  
            }  
            $('#employeeNumber').css({  
                "color" : "#666"  
            })  
        })   
         $('#userNumber').focus(function () {
        	if( $(this).val()=="请输入用户编号" ){  
                $(this).val("");  
            }  
            $('#userNumber').css({  
                "color" : "#666"  
            })  
        })   
         $('#reuserName').focus(function () {
        	if( $(this).val()=="请输入中文名" ){  
                $(this).val("");  
            }  
            $('#reuserName').css({  
                "color" : "#666"  
            })  
        })   
          $('#telephone').focus(function () {
        	if( $(this).val()=="请输入电话" ){  
                $(this).val("");  
            }  
            $('#telephone').css({  
                "color" : "#666"  
            })  
        })   
        $('#mobilePhone').focus(function () {
        	if( $(this).val()=="请输入手机" ){  
                $(this).val("");  
            }  
            $('#mobilePhone').css({  
                "color" : "#666"  
            })  
        })   
	        $('#loginName').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入账号");  
	                $('#loginName').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#loginName').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#loginNameSave').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入账号");  
	                $('#loginNameSave').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#loginNameSave').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#emailSave').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入邮箱");  
	                $('#emailSave').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#emailSave').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        }) 
	        $('#employeeNumber').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入员工编号 "); 
	                $('#employeeNumber').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#employeeNumber').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#userNumber').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入用户编号 "); 
	                $('#userNumber').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#userNumber').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#reuserName').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入中文名"); 
	                $('#reuserName').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#reuserName').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#telephone').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入电话"); 
	                $('#telephone').css({  
	                    "color" : "#666"  
	                })  
	            }else {  
	                $('#telephone').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })  
	        $('#mobilePhone').blur(function () {  
	            if( $(this).val()=="" ){  
	                $(this).val("请输入手机"); 
	                $('#mobilePhone').css({  
	                    "color" : "#666"  
	                })  
	            }else { 
	                $('#mobilePhone').css({  
	                    "color" : "#666"  
	                })  
	            }  
	        })
	}	
});
$('.phone-img').addClass('animated infinite flash');
$('.phone-img').hover(function(){
	  $(this).removeClass('animated flash')
	},function(){
		$(this).addClass('animated infinite flash')
	})
$('.btnClose').hover(function(){
	$(this).addClass('animated rotateIn')
},function(){
	$(this).removeClass('animated rotateIn')
})
$("#workNum").click(function(){
	$.ajax({
		url : "login/hasLogined.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			if(json.result=='no'){
				layer.alert($.i18n.prop("check_login"));
			}else if(json.result=='yes'){
				document.location.href='/cms_cloudy/pages/workflowPage/cms-flow-daiBan.jsp';
			}
		},
		error : function() {
		}
	});
     
});
/*判断是否有placeholder属性*/
function isPlaceholder(){
	var input = document.createElement('input');  
	return "placeholder" in input;  
}
$("#register-box,#login-box,#forget-box,#changePawd-box").draggable();
</script>
 <script type="text/javascript">
$(document).ready(function(){
	     /**是否取消退出按钮**/
	    if(window.sessionStorage.getItem("ExitSign") == "yes"){
	    	$(".login-out").hide();
	    	$.ajax({
	    		url : "login/setMaxInactiveInterval.do",
	    		dataType : "json",
	    		cache : false,
	    		type : "post",
	    		success : function(json) {
	    			
	    		},
	    		error : function() {
	    		}
	    	});
	    }
	    selectHotSearchFromSelf();/**浏览最多初始化**/
	    pushNewPart();/**最新推荐**/
        $("#scrollDiv").Scroll({line:1,speed:500,timer:3000,up:"but_up",down:"but_down"});
        headerDataRights();//header页数据权限
});
//忘记密码验证码
$.idcode.setCode();
$.idcode1.setCode();
// setInterval('myrefresh()', 10000);
// function myrefresh() {
// 	document.getElementById('ref1').contentWindow.location.reload(true);
// }
</script> 

