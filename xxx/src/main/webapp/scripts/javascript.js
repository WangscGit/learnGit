/**
 * Created by Administrator on 2017/6/22
 */
/******登录注册框垂直居中*****/
/*********js方法，不能实时监听浏览器窗口变化***********/
  /* var totalHeight = document.documentElement.clientHeight;
   var totalWidth = document.documentElement.clientWidth;
   var loginBoxH=$("#login-box").height();
   var registerBoxH=$("#register-box").height();*/
/*********resize方法，频繁操作dom，性能没那么好***********/
 /*   $(window).resize(function(){
        $("#login-box").css({left:($(window).width()-500)/2,top:($(window).height()-380)/2});
        $("#register-box").css({left:($(window).width()-500)/2,top:($(window).height()-530)/2});
 });*/
/******登录按钮切换*****/
$(function() {
    /******点击登录注册页面下滑 *****/
    $(".btn1,.click-login").click(function () {
        $("#login-box").css({top: -500}).animate({top: 0}, 500).siblings("#register-box,#forget-box").css("top", -500);
    });
    $(".btn2,.click-register").click(function () {
        $("#register-box").css({top: -500}).animate({top: 0}, 500).siblings("#login-box,#forget-box").css("top", -500);
        selectUserSelect();
    });
    //点登陆框中的忘记密码
   $(".click-forget").click(function () {
        $("#forget-box").css({top: -500}).animate({top: 0}, 500).siblings("#register-box").css("top", -500);
        $("#login-box").css("top", -600);
    });
 //点登陆下拉列表中的修改密码
   $(".click-changePawd").click(function () {
   	    $.ajax({
    		   url: "login/hasLogined.do",
    		   cache: false,
    		   dataType: "json",
    		   success: function(json){
    		   	    $("input[name='pubLoginName']").val(json.user.loginName);
    			    $("#changePawd-box").css({top: -500}).animate({top: 0}, 500).siblings("#register-box").css("top", -500);
    		   },
    		   error: function(){
    			   layer.alert("数据连接异常,注册失败！");
    		   }
    	   });
   });
    $("#SearchMore").click(function () {
        $("#search-box").css("display","block").siblings("#login-box").css("top", -500);
    });
    /******点击退出按钮下拉框消失，登陆注册按钮出现 *****/
    $(".login-out").click(function () {
    	var make="user";//退出标识
   	    $.ajax({
	 		   url: "/cms_cloudy/login/deleteSession.do",
	 		   data: {"make":make},
	 		   cache: false,
	 		   dataType: "json",
	 		   success: function(json){
	 		        $(".login-div>div").hide().siblings(".login-div>li").show();
	 		   },
	 		   error: function(){
	 			  layer.alert("数据连接异常！");
	 		   }
	 	   }); 
    });
    /******导航所在父元素的屏幕高度(初始)*****/
    var totalHeight = document.documentElement.clientHeight;
    $(".eye-screen").height(totalHeight);
    $(".IntelligentPush-box").height(totalHeight);
    $(window).resize(function () {
        /******导航所在父元素的屏幕高度（窗口变化）*****/
        var totalHeight = document.documentElement.clientHeight;
        $(".eye-screen").height(totalHeight);
        $(".IntelligentPush-box").height(totalHeight);
    }); 
    /******滚动锚点*****/
    $(".scroll_main").click(function () {
        $("html,body").animate({scrollTop: $("#main").offset().top}, 700);
    });
    /******滚动锚点-流程设计*****/
    $(".flow-design").click(function () {
        $("html,body").animate({scrollTop: $(".flow-design-head").offset().top-145}, 700);
    });
    /********user表格的增删查改*********/
    /*******删除******/
    $("body").on("click", ".userDeletebtn", function () {
        //$(this).parent().parent().remove();
    });
    /*******添加******/
    $("body").on("click", ".userAddbtn", function () {
    	 if(document.getElementById("addPosition").options.length == 0 || document.getElementById("addDepartment").options.length == 0){
 	       selectFull();//填充职位和部门下拉框
 	     }
    	initGroupFromUser('-1');//填充部门下拉框
        $(".user-addWindow").show();
    });
    $("body").on("click", ".user-addWindow-close", function () {
        $(".user-addWindow").hide();
    });
    $("body").on("click", ".select-addWindow-close", function () {
        $(".select-addWindow").hide();
    });
    /*******编辑******/
    $("body").on("click", ".userEditbtn", function () {
        //$(".user-editWindow").show();
    });
    $("body").on("click", ".user-editWindow-close", function () {
        $(".user-editWindow").hide();
    });  
    /********post表格的增删查改*********/
    /*******添加******/
    $("body").on("click", ".postAddbtn", function () {
        $(".post-addWindow").show();
    });
    $("body").on("click", ".post-addWindow-close", function () {
        $(".post-addWindow").hide();
    });  
    /*******编辑******/
    $("body").on("click", ".postEditbtn", function () {
        //$(".post-editWindow").show();
    });
    $("body").on("click", ".post-editWindow-close", function () {
        $(".post-editWindow").hide();
    });  
    /********department表格的增删查改*********/
    /*******添加******/
    $("body").on("click", ".departmentAddbtn", function () {
        $(".department-addWindow").show();
    });
    $("body").on("click", ".department-addWindow-close", function () {
        $(".department-addWindow").hide();
    });  
    /*******编辑******/
    $("body").on("click", ".departmentEditbtn", function () {
        //$(".department-editWindow").show();
    });
    $("body").on("click", ".department-editWindow-close", function () {
        $(".department-editWindow").hide();
    });  
    /********group表格的增删查改*********/
    /*******添加******/
    $("body").on("click", ".groupAddbtn", function () {
    	$("#inGroupIndex").val("");
    	$("#inGroupName").val("");
        $(".group-addWindow").show();
    });
    
    $("body").on("click", ".group-addWindow-close", function () {
        $(".group-addWindow").hide();
    });  
    /*******编辑******/   
    $("body").on("click", ".group-editWindow-close", function () {
        $(".group-editWindow").hide();
    });  
    /*******显示组成员弹窗******/
    
    $("body").on("click", ".group-user-close", function () {
        $(".group-user-box").hide();
    });
    /*******显示部门成员弹窗******/
    //$("body").on("click", ".department-member", function () {
        //$(".department-user-box").show();
   // });
    $("body").on("click", ".department-user-close", function () {
        $(".department-user-box").hide();
    });
    /********添加权限TAB的弹窗显示*********/
    /********添加同级点弹窗显示*********/
    
$("body").on("click", ".addlimit-addWindow-close", function () {
    $(".addlimit-addWindow").hide();
});
/********添加下级节点弹窗显示*********/
$("body").on("click", ".addlimit-add1Window-close", function () {
$(".addlimit-add1Window").hide();
});
/********修改弹窗显示*********/

$("body").on("click", ".addlimit-editWindow-close", function () {
$(".addlimit-editWindow").hide();
});
    /******弹出框始终居中*****/
    /*$(window).scroll(function() {
        var screenWidth = $(window).width();
        var screenHeight = $(window).height();
        var scrolltop = $(document).scrollTop();
        console.log(scrolltop);
        var objLeft = (screenWidth - 700)/2 ;
        console.log(objLeft);
        var objTop = (screenHeight - 370)/2+scrolltop;
        console.log(objTop);
        $(".group-user-box").css({marginLeft: objLeft + 'px', marginTop: objTop + 'px'});
    });*/
    /******树形结构*****/
    $(function () {
        $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
        $('.tree li.parent_li > span').on('click', function (e) {
            var children = $(this).parent('li.parent_li').find(' > ul > li');
            if (children.is(":visible")) {
                children.hide('fast');
                $(this).attr('title', 'Expand this branch').find(' > i').addClass('glyphicon-plus-sign').removeClass('glyphicon-minus-sign');
            } else {
                children.show('fast');
                $(this).attr('title', 'Collapse this branch').find(' > i').addClass('glyphicon-minus-sign').removeClass('glyphicon-plus-sign');
            }
            e.stopPropagation();
        });
    });
   
    /******上传模块删除*****/
    $("body").on("click", ".upload-delete", function () {
        $(this).parent().parent().remove();
    });
    /******$$$流程管理模块$$$*****/
    /******流程管理新增按钮显示下拉框*****/
    $(".btn1").hover(function(){
 	   $(".flow-manage-ul").css("display","block")
    },function(){
 	   $(".flow-manage-ul").css("display","none")
    })
/******流程管理点击申请,查看编辑跳转页面*****/
     $("body").on("click", ".shenqing", function () {
     	if($(this).attr("name") == "0"){
     		$("#goPartApplyPage").attr("onclick","goPartApplyPage(0)")
    	    $(".parts-applyChoose-window").show();
     	}else{
     	    layer.alert("找不到相对应页面，请联系管理员！");
     	}
    	//window.location.href = "/cms_cloudy/pages/workflowPage/cms-flow-manage-applyPart.jsp?ft=0";
    })
    $("body").on("click", ".flow-view-head-look", function () {
    	//window.location.href = "/cms_cloudy/pages/workflowPage/cms-flow-manage-look.jsp";
    })
    /******流程管理点击申请中任务执行人input框,弹出人员选择页面*****/
       $(".workMen").click(function(){
    	   $(".flow-setting-nodeUser-choose").show();
        });
   /** $(".workMen").blur(function(){
 	   $(".flow-setting-nodeUser-choose").hide();
     });**/
    /******流程管理中申请元器件页面中点击流程名称选择按钮,弹出人员选择页面*****/
    $("body").on("click", ".chooseName-btn", function () {
        $(".parts-applyChoose-window").show();
    });
    $("body").on("click", ".parts-applyChoose-window-close", function () {
        $(".parts-applyChoose-window").hide();
    }); 
    /******$$$流程设计部分$$$$$*****/
 /******点流程设计弹出弹窗*****/
    $("body").on("click", ".flow-design-online", function () {
        $(".flow-design-window").show();
    });
  /******关闭流程设计弹出弹窗*****/
 $("body").on("click", ".flow-design-window-close", function () {
        $(".flow-design-window").hide();
   });
    /******点设置分类按钮*****/
   $(".Btn2").hover(function(){
	   $(".flow-design-ul").css("display","block")
   },function(){
	   $(".flow-design-ul").css("display","none")
   })
    /******流程设计表格删除*****/
    $("body").on("click", ".flow-design-btn-delete", function () {
        /**$(this).parent().parent().remove();**/
    });
    /******$$$流程设置页面部分$$$*****/
    /******节点人员模块添加弹出页面*****/
    $("body").on("click", ".node-user-add", function () {
        $(".flow-setting-addUser").show();
    });
    $("body").on("click", ".flow-setting-addUser-close", function () {
        $(".flow-setting-addUser").hide();
    });
    /******节点人员模块添加弹出页面中表格删除*****/
    $("body").on("click", ".flow-setting-delete", function () {
        $(this).parent().parent().parent().remove();
    });
    /******节点人员模块添加弹出页面中添加表格*****/
    var flowSettingTb = $("#flow-setting-addUser-table");
    $("body").on("click", ".flow-setting-addUser-btn", function () {
        var hideTr = $("#flow-setting-hide_tr", flowSettingTb);
        var newTr = hideTr.clone().show();
         var tr=$('#flow-setting-addUser-table').find("tr:last");
        $(tr,flowSettingTb).after(newTr);
    });
    /******节点人员模块添加弹出页面中添加表格中选择用户模块*****/
    /******点击选择按钮弹出radio选择用户页面*****/
    $("body").on("click", ".flow-setting-choice", function () {
        $(".flow-setting-nodeUser").show();
    });
    $("body").on("click", ".flow-setting-nodeUser-close", function () {
        $(".flow-setting-nodeUser").hide();
    });
    /******点击指定用户弹出input框*****/
    $("body").on("click", "#nodeUser-radio", function () {
        if(this.checked){
            $(".nodeUser-hide").css("visibility","visible");
        }else{
            $(".nodeUser-hide").css("visibility","hidden");
        }
    });
    /******点击radio选择用户页面中选择用户按钮*****/
    /******弹出用户选择弹窗*****/
    $("body").on("click", ".nodeUser-body-choose-btn", function () {
        $(".flow-setting-nodeUser-choose").show();
    });
    $("body").on("click", ".flow-setting-nodeUser-choose-close", function () {
        $(".flow-setting-nodeUser-choose").hide();
    });
    /******用户选择弹窗中表格删除功能*****/
    $("body").on("click", ".nodeBtn-table1-deleteBtn", function () {
        $(this).parent().parent().remove();
    });
    /******节点按钮部分*****/
    /******点击节点按钮中编辑按钮，弹出子页面*****/
    $("body").on("click", ".nodeBtn-editBtn", function () {
        $(".flow-setting-nodeBtn").show();
    });
    $("body").on("click", ".flow-setting-nodeBtn-close", function () {
        $(".flow-setting-nodeBtn").hide();
    });
    /******$$$业务对象部分$$$*****/
    /******点击业务表旁添加按钮，弹出子页面*****/
    $("body").on("click", ".business-object-btn", function () {
        $(".business-object-window").show();
    });
    $("body").on("click", ".business-object-window-close", function () {
        $(".business-object-window").hide();
    });
    /******$$$代办流程部分$$$*****/
    /******点击代办流程页面中选择按钮，弹出子页面*****/
    $("body").on("click", ".flow-daiban-chooseBtn", function () {
        $(".flow-daiban-window").show();
    });
    $("body").on("click", ".flow-daiban-window-close", function () {
        $(".flow-daiban-window").hide();
    });
    /******进入审批页后左边一排功能按钮相关弹窗*****/
    /******点击哪个页面哪个页面出现在最上边*****/
   /* $("body").on("click", ".SPBox", function () {
    	$(this).css("z-index","100");
    });*/
    
    /******转办按钮*****/
    $("body").on("click", ".shenpi-toother-btn", function () {
        $(".shenPi-zhuanban-box").show();
    });
    
    $("body").on("click", ".shenPi-agree-box-close", function () {
        $(".shenPi-agree-box").hide();
    });
    $("body").on("click", ".shenPi-zhuanban-box-close", function () {
        $(".shenPi-zhuanban-box").hide();
    });
    
    /******点击同意按钮弹出页面中的选择用户弹窗*****/
    //$("body").on("click", ".shenPi-agree-box-chooseUser-btn", function () {
       // $(".shenPi-agree-box-chooseUser").show();
    //});
    $("body").on("click", ".shenPi-agree-box-chooseUser-close", function () {
        $(".shenPi-agree-box-chooseUser").hide();
    });
    /******流程图按钮*****/
    //$("body").on("click", ".shenpi-flow-btn", function () {
       // $(".shenPi-flow-box").show();
    //});
    $("body").on("click", ".shenPi-flow-box-close", function () {
        $('#processPng img').remove(); 
        $(".shenPi-flow-box").hide();
    });
   
    $("body").on("click", ".shenPi-history-box-close", function () {
        $(".shenPi-history-box").hide();
    });
    /******打回按钮*****/
    $("body").on("click", ".shenpi-back-btn", function () {
        $(".shenPi-back-box").show();
    });
    $("body").on("click", ".shenPi-back-box-close", function () {
        $(".shenPi-back-box").hide();
    });
    /******流程抄送按钮*****/
    $("body").on("click", ".shenpi-send-btn", function () {
        $(".shenPi-send-box").show();
    });
    $("body").on("click", ".shenPi-send-box-close", function () {
        $(".shenPi-send-box").hide();
    });
    /******点击流程抄送按钮弹出页面中的选择用户弹窗*****/
   // $("body").on("click", ".shenPi-send-box-chooseUser-btn", function () {
       // $(".shenPi-send-box-chooseUser").show();
  //  });
    $("body").on("click", ".shenPi-send-box-chooseUser-close", function () {
        $(".shenPi-send-box-chooseUser").hide();
    });
    /******发起沟通按钮*****/
    $("body").on("click", ".shenpi-talk-btn", function () {
        $(".shenPi-talk-box").show();
    });
    $("body").on("click", ".shenPi-talk-box-close", function () {
        $(".shenPi-talk-box").hide();
    });
    /******点击发起沟通按钮弹出页面中的选择用户弹窗*****/
    $("body").on("click", ".shenPi-talk-box-chooseUser-btn", function () {
        $(".shenPi-talk-box-chooseUser").show();
    });
    $("body").on("click", ".shenPi-talk-box-chooseUser-close", function () {
        $(".shenPi-talk-box-chooseUser").hide();
    });
    /******发起流转按钮*****/
    $("body").on("click", ".shenpi-turn-btn", function () {
        $(".shenPi-turn-box").show();
    });
    $("body").on("click", ".shenPi-turn-box-close", function () {
        $(".shenPi-turn-box").hide();
    });
    /******点击发起流转按钮弹出页面中的选择用户弹窗*****/
    $("body").on("click", ".shenPi-turn-box-chooseUser-btn", function () {
        $(".shenPi-turn-box-chooseUser").show();
    });
    $("body").on("click", ".shenPi-turn-box-chooseUser-close", function () {
        $(".shenPi-turn-box-chooseUser").hide();
    });
    /******转办代办按钮*****/
    $("body").on("click", ".shenpi-help-btn", function () {
        $(".shenPi-help-box").show();
    });
    $("body").on("click", ".shenPi-help-box-close", function () {
        $(".shenPi-help-box").hide();
    });
    /******点击转办代办按钮弹出页面中的选择用户弹窗*****/
    $("body").on("click", ".shenPi-help-box-chooseUser-btn", function () {
        $(".shenPi-help-box-chooseUser").show();
    });
    $("body").on("click", ".shenPi-help-box-chooseUser-close", function () {
        $(".shenPi-help-box-chooseUser").hide();
    });
    /******$$$流程代理部分$$$*****/
    /******点击流程代理页面中添加按钮，弹出子页面*****/
    $("body").on("click", ".flow-daili-head-add", function () {
        $(".flow-daili-addWindow").show();
    });
    $("body").on("click", ".flow-daili-addWindow-close", function () {
        $(".flow-daili-addWindow").hide();
    });
    /******点击流程代理页面中编辑按钮，弹出子页面*****/
    $("body").on("click", ".daili-table-edit", function () {
        $(".flow-daili-editWindow").show();
    });
    $("body").on("click", ".flow-daili-editWindow-close", function () {
        $(".flow-daili-editWindow").hide();
    });
    /******点击流程代理页面中详情按钮，弹出子页面*****/
    $("body").on("click", ".daili-table-more", function () {
        $(".flow-daili-moreWindow").show();
    });
    $("body").on("click", ".flow-daili-moreWindow-close", function () {
        $(".flow-daili-moreWindow").hide();
    });
    /******点击流程代理页面中子页面中选择按钮，弹出选择用户弹窗*****/
    $("body").on("click", ".flow-daili-window-chooseUser1Btn", function () {
        $(".flow-daili-window-chooseUser1").show();
    });
    $("body").on("click", ".flow-daili-window-chooseUser1-close", function () {
        $(".flow-daili-window-chooseUser1").hide();
    });
    $("body").on("click", ".flow-daili-window-chooseUser2Btn", function () {
        $(".flow-daili-window-chooseUser2").show();
    });
    $("body").on("click", ".flow-daili-window-chooseUser2-close", function () {
        $(".flow-daili-window-chooseUser2").hide();
    });
    /******$$$流程管理部分$$$*****/
    /******点击元器件申请页面中上传原理图符号按钮，弹出原理图弹窗*****/
    //$("body").on("click", ".symbolBtn", function () {
        //$(".flow-updateSymble").show();
    //});
    $("body").on("click", ".flow-updateSymble-close", function () {
        $(".flow-updateSymble").hide();
    });
    /******点击流程管理中修改页面中上传原理图符号按钮，弹出原理图弹窗*****/
   // $("body").on("click", ".symbolBtn-edit", function () {
        //$(".flow-updateSymble-edit").show();
    //});
    $("body").on("click", ".flow-updateSymble-edit-close", function () {
        $(".flow-updateSymble-edit").hide();
    });
    
    /******点击流程管理中查看页面中上传原理图符号按钮，弹出原理图弹窗*****/
    $("body").on("click", ".symbolBtn-look", function () {
        $(".flow-updateSymble-look").show();
    });
    $("body").on("click", ".flow-updateSymble-look-close", function () {
        $(".flow-updateSymble-look").hide();
    });
    /******点击流程管理页面中表格查看按钮，弹出详细信息弹窗*****/
    $("body").on("click", ".flow-view-head-look", function () {
        $(".flow-view-lookWindow").show();
    });
    $("body").on("click", ".flow-view-lookWindow-close", function () {
        $(".flow-view-lookWindow").hide();
    });
    /******点击流程管理页面中表格过程按钮，弹出审批过程弹窗*****/
    $("body").on("click", ".flow-view-head-road", function () {
        //$(".flow-view-processWindow").show();
    });
    $("body").on("click", ".flow-view-processWindow-close", function () {
        $(".flow-view-processWindow").hide();
    });
    /******点击流程管理页面中表格流程图按钮，弹出流程图弹窗*****/
   // $("body").on("click", ".flow-view-head-picture", function () {
        //$(".flow-view-processWindow1").show();
  //  });
    $("body").on("click", ".flow-view-processWindow1-close", function () {
        $(".flow-view-processWindow1").hide();
    });
    /******点击流程管理页面中表格催办按钮，弹出催办页面弹窗*****/
    //$("body").on("click", ".flow-view-head-ask", function () {
       // $(".flow-view-askWindow").show();
    //});
    $("body").on("click", ".flow-view-askWindow-close", function () {
        $(".flow-view-askWindow").hide();
    });
    /******点击流程管理页面中表格转办按钮，弹出催办页面弹窗*****/
    $("body").on("click", ".flow-view-head-turn", function () {
        $(".flow-view-turnWindow").show();
    });
    $("body").on("click", ".flow-view-turnWindow-close", function () {
        $(".flow-view-turnWindow").hide();
    });
    /******点击流程管理页面中表格转办页面中用户选择按钮，弹出用户选择弹窗*****/
    $("body").on("click", ".turnChooseUser", function () {
        $(".flow-setting-nodeUser-choose").show();
    });
    $("body").on("click", ".flow-setting-nodeUser-choose-close", function () {
        $(".flow-setting-nodeUser-choose").hide();
    });
    /******$$$已办流程部分$$$*****/
    
    $("body").on("click", ".flow-yiban-Window-close", function () {
        $(".flow-yiban-Window").hide();
    });
    /******$$$消息管理部分$$$*****/
    /******消息管理页面跳转*****/
    $(".message-view").click(function(){
    	window.location.href = getContextPathForWS()+"/pages/messagePage/cms-message.jsp";
    })
    $(".message-send").click(function(){
    	window.location.href = getContextPathForWS()+"/pages/messagePage/cms-messageSend.jsp";
    })
    /******消息发布删除*****/
    //$("body").on("click", ".message-delete", function () {
        //$(this).parent().parent().remove();
    //});
    /*******消息发布显示/隐藏消息弹窗******/
    //$("body").on("click", ".message-edit", function () {
       // $(".message-edit-page").show();
    //});
    $("body").on("click", ".message-edit-close,.message-edit-cancel", function () {
        $(".message-edit-page").hide();
    });
    //$("body").on("click", ".message-more", function () {
       // $(".message-more-page").show();
    //});
    $("body").on("click", ".message-more-close", function () {
        $(".message-more-page").hide();
    });
    $("body").on("click", ".message-add", function () {
        $(".message-add-page").show();
    });
    $("body").on("click", ".message-add-close,.message-add-cancel", function () {
        $(".message-add-page").hide();
        $(".message-add-page-end").hide();
    });
    /******$$$元器件库部分$$$*****/
    /******点击修改表的某一行，比较表才显示*****/
    $(".parts-history-window-box1 tr").click(function(){
        $(".parts-history-window-box2").show();
    })
     /******点击对比按钮，比较框显示/隐藏*****/
    $("body").on("click", ".compare-label", function () {
        //$(".parts-compare-box").show();
    });
    /*if($(".compare-input").attr('checked')!==undefined){
    	$(".parts-compare-box").show();
    }	*/
    $("body").on("click", ".compare-hidden", function () {
        $(".parts-compare-box").hide();
    });
    /******点击导入按钮，弹出导入元器件选框,关闭弹窗*****/
/*    $("body").on("click", ".partsInto-btn", function () {
        $(".parts-into-window").show();
    });
    $("body").on("click", ".parts-into-window-close", function () {
        $(".parts-into-window").hide();
    });*/
    /******元器件导入下拉*****/
    $("body").on("mouseover", ".daoru-btn", function () {
      	 $(".saveInsert").css("display","block");
      });
      $("body").on("mouseout", ".daoru-btn", function () {
    	  $(".saveInsert").css("display","none");
      });
    /******点击创建页面数据手册选择按钮，弹出页面****/
    /* $("body").on("click", ".attruser-btn", function () {
            $(".parts-producthand-window").show();
        });*/
        $("body").on("click", ".parts-producthand-window-close", function () {
            $(".parts-producthand-window").hide();
        });
    
    /******鼠标移入关注爱心变红*****/
    /****** hover 不是标准的事件，因此无法直接使用 live 和 delegate 进行处理（jq高版本用on）*****/
    $("body").on("mouseover", ".compare-view", function () {
    	 $(this).children("a").html("").html('<img src="/cms_cloudy/images/love-red.png">');
    });
    $("body").on("mouseout", ".compare-view", function () {
    	 $(this).children("a").html("").html('<img src="/cms_cloudy/images/love.png">');
    });
    /******鼠标移入图片膨胀*****/
    $("body").on("mouseover", ".part-introduce", function () {
   	 $(this).addClass('animated rotateIn');
   });
   $("body").on("mouseout", ".part-introduce", function () {
	   $(this).removeClass('animated rotateIn')
   });
    /******删除对比栏中的数据*****/
    //$("body").on("click", ".price-delete", function () {
        //$(this).parent().parent().parent().html("").html('<dt>+</dt>'+'<dd>您还可以继续添加</dd>');
    //});
    /******清空对比栏*****/
    $("body").on("click", ".del-items", function () {
        $(".item-empty-box").children("dl").html("").html('<dt>+</dt>'+'<dd>您还可以继续添加</dd>');
    });
    /******点击添加更多商品，弹出子选框*****/
    $("body").on("click", ".add-simple-btn", function () {
        $(".add-simple-window").show();
    });
    $("body").on("click", ".add-simple-window-close", function () {
        $(".add-simple-window").hide();
    });
    /******点击评价按钮，弹出用户评价弹窗*****/
    $("body").on("click", ".user-evaluate-btn", function () {
    	$.ajax({
    		url : "login/hasLogined.do",
    		dataType : "json",
    		cache : false,
    		type : "post",
    		success : function(json) {
    			if(json.result=='no'){
    				if(json.lang == "zh"){
    					layer.alert("请先登录！");
    				}else{
    				   layer.alert("Please Sign in first！");
    				}
    			}else if(json.result=='yes'){
    				$(".user-evaluate-window").show();
    			}
    		},
    		error : function() {
    		}
    	});
        
    });
    $("body").on("click", ".user-evaluate-window-close", function () {
        $(".user-evaluate-window").hide();
    });
    /******点击评价修改按钮，弹出用户评价修改弹窗
    $("body").on("click", ".evaluateEdit", function () {
        $(".user-evaluateEdit-window").show();
    });*****/
    $("body").on("click", ".user-evaluateEdit-window-close", function () {
        $(".user-evaluateEdit-window").hide();
    });
    /******鼠标移入对比页面商品时显示删除*****/
    $(".compare-goods-td").hover(function(){
    	$(this).find(".compare-goods-delete").show();
    },function(){
    	$(this).find(".compare-goods-delete").hide();
    });
    /******切换数据库管理页面的分立信息、字段定义、数据库备份*****/
    $(".dataList-btn").click(function(){
        $(".dataList").show();
        $(".dataField").hide();
        $(".databaseCopy").hide();
        $(".new-field").hide();
    })
    $(".dataField-btn").click(function(){
    	$(".dataField").show();
        $(".dataList").hide();
        $(".databaseCopy").hide();
        $(".new-field").hide();
    })
    $(".databaseCopy-btn").click(function(){
    	$(".databaseCopy").show();
        $(".dataList").hide();
        $(".dataField").hide();
        $(".new-field").hide();
    })
    $(".new-field-btn").click(function(){
    	$(".new-field").show();
    	$(".databaseCopy").hide();
        $(".dataList").hide();
        $(".dataField").hide();
    })
    /******数据库管理页面中字段定义中的表格编辑*****/
   /* $("body").on("click", ".data-change", function () {
        var tr = $(this).parent().parent().parent();
        $(".change-td", tr).each(function (i, el) {
            el = $(el);
            el.text();
            oldObj['num' + i] = el.text();
        });
        $(".change-td", tr).each(function (i, el) {
            el = $(el);
            //暂时,解决点击编辑按钮文本框内容消失的问题
            if("" !=el.text()){
            	 var html = "<input value='" + el.text() + "' type='text' class='form-control'>";
                 el.html(html);
            }
        });
        $(".databtn-group1", tr).hide();
        $(".databtn-group2", tr).show();
    });*/
    /*******保存******/
    $("body").on("click", ".user-save", function () {
    	  var tr= $(this).parent().parent().parent();
          var employeeNumber = "";
          var userNumber = "";
          var userName = "";
          var loginName = "";
          var position = "";
          var email = "";
          var telephone = "";
          var mobilePhone = "";
          var department = "";
          var createuser = "";
          var createtime = "";
          var isOrNot = "";
          $("input[type='text'],.select-control",tr).each(function(i,el){
              el = $(el);
              el.parent().text(el.val());
              if(i==0){
              	employeeNumber = el.val();
              	return true;
              }
              if(i==1){
              	userNumber = el.val();
              	return true;
              }
              if(i==2){
              	userName = el.val();
              	return true;
              }
              if(i==3){
              	loginName = el.val();
              	return true;
              }
              if(i==4){
              	position = el.val();
              	return true;
              }
              if(i==5){
              	email = el.val();
              	return true;
              }
              if(i==6){
              	telephone = el.val();
              	return true;
              }
              if(i==7){
              	mobilePhone = el.val();
              	return true;
              }
              if(i==8){
              	department = el.val();
              	return true;
              }
              if(i==9){
              	createuser = el.val();
              	return true;
              }
              if(i==10){
              	createtime = el.val();
              	return true;
              }
              if(i==11){
              	isOrNot = el.val();
              	return true;
              }
          });
          if("" == loginName){
             layer.alert("英文名不能为空！");
             return;
          }
          $.ajax({
          	url: "/cms_cloudy/user/inertOrUpdate.do",
  	    	contentType:'application/json;charset=UTF-8', 
          	cache: false,
          	data: {"employeeNumber":encodeURI(employeeNumber),"userNumber":encodeURI(userNumber),"userName":encodeURI(userName),"loginName":encodeURI(loginName),"position":encodeURI(position),"email":encodeURI(email),
          		"telephone":encodeURI(telephone),"mobilePhone":encodeURI(mobilePhone),"department":encodeURI(department),"createuser":encodeURI(createuser),"createtime":encodeURI(createtime),"isOrNot":encodeURI(isOrNot)},
          	dataType: "json",
          	success: function(json){
          		if(json.result == "insert"){
          			layer.alert('保存成功!');
          		    $("#Pagination").html('');
          			initUserList("");
          		}
          		if(json.result == "update"){
          			layer.alert('更新成功!');
          			$("#Pagination").html('');
          			initUserList("");
          		}
          	},
          	error: function(){
  	 			  layer.alert("数据连接异常！");
          	}
          });
          $(".user-btnGroup1",tr).hide();
          $(".user-btnGroup2",tr).show();
    });
    /*******数据库管理页面中字段定义中的表格保存******/
    $("body").on("click", ".data-save", function () {
        var tr = $(this).parent().parent().parent();
        var fieldName = "";
        var showName = "";
        var remark = "";
        var type = "";
        var IsDisplay = "";
        var IsUpdate = "";
        var IsAudit= "";
        var IsSearch = "";
        var IsMatch = "";
        $("td:not('.edit-td')", tr).each(function (i, el) {
            el = $(el);
            el.parent().text(el.val());
            if(i==0){
            	fieldName = el.val();
              	return true;
              }
              if(i==1){
            	 showName = el.val();
              	return true;
              }
              if(i==2){
            	  remark = el.val();
              	return true;
              }
              if(i==3){
            	  type = el.val();
              	return true;
              }
              if(i==4){
            	  IsDisplay = el.val();
              	return true;
              }
              if(i==5){
            	  IsUpdate = el.val();
              	return true;
              }
              if(i==6){
            	  IsAudit = el.val();
              	return true;
              }
              if(i==7){
            	  IsSearch = el.val();
              	return true;
              }
              if(i==8){
            	  IsMatch = el.val();
              	return true;
              }
              el.html(html);
        });
        $(".databtn-group2", tr).hide();
        $(".databtn-group1", tr).show();
    });
    /*******数据库管理页面中字段定义中的表格取消******/
    $("body").on("click", ".data-cancel", function () {
        var tr = $(this).parent().parent().parent();
        $(".change-td", tr).each(function (i, el) {
            el = $(el);
            el.text(oldObj['num' + i]);
        });
        $(".databtn-group2", tr).hide();
        $(".databtn-group1", tr).show();
        //暂时,刷新字段定义展示列表
        fieldFunction();
    });
    /*******数据库管理页面中字段定义中的表格删除******/
    $("body").on("click", ".data-delete", function () {
        $(this).parent().parent().parent().remove();
    });
    /*******数据库管理页面中字段定义中点击编辑按钮，弹出框显示隐藏******/
    $("body").on("click", ".data-change", function () {
        $(".field-editWindow").show();
    });
    $("body").on("click", ".field-editWindow-close", function () {
        $(".field-editWindow").hide();
    });
    /******用户评价页面nav切换变色*****/
    $(".evaluate-nav li").click(function(){
    	$(this).css("color","#d9534f").siblings("li").css("color","#555");
    })
})
/******产品结构页面*****/
/******产品结构页面中弹出选择用户弹窗
$("body").on("click", ".attruser-btn", function () {
        $(".productmix-chooseuser-window").show();
    });*****/
    $("body").on("click", ".productmix-chooseuser-window-close", function () {
        $(".productmix-chooseuser-window").hide();
    });
    /******产品结构页面中鼠标移入操作按钮出现下拉列表*****/
  $(".hand-workbtn1").hover(function(){
    	$(this).children().children(".handb").show();
    	$(this).children(".handul").show();
    },function(){
    	$(this).children().children(".handb").hide();
    	$(this).children(".handul").hide();
    });
  /******产品结构页面中鼠标移入展开收缩两个字变色*****/
  $("body").on("click", ".hand-work2", function () {
	  if($(this).children("img").attr("src")==("images/sup.png")){		  
		  $(this).children("img").attr("src","images/sdown.png");	 		 
	  	  	var treeObj = $.fn.zTree.getZTreeObj("bomtree");
            treeObj.expandAll(false); // 收缩所有节点
	  }else{		  		  
		  $(this).children("img").attr("src","images/sup.png");			 
		  	var treeObj = $.fn.zTree.getZTreeObj("bomtree");
            treeObj.expandAll(true); // 展开所有节点
	  }  	  	
  });
function callback(Callback,param)
{
    Callback.Callback(param);
}
function AddItem(a,b,c) {
    var DesValue = a;
    var TargetValue = b;
    var bigDesValue= c;
    var max = DesValue.options.length;
    k=0;
    if (max==0)
        return;
    maxselected=0
    /* if (TargetValue.options.length >= 3) {
     alert("选中的项目不能超过3个");
     return;
     }*/
    for (var i=0;i<max;i++) {
        if (DesValue.options[i].selected) {
            maxselected=i+1;
            if (maxselected>=max)
                maxselected=0;
            for (var j=0;j<TargetValue.options.length;j++) {
                if (TargetValue.options[j].value == DesValue.options[i].value) {
                    layer.alert("您不能重复加入");
                    return;
                }
            }
            TargetValue.options.add(new Option(/*bigDesValue.options[bigDesValue.selectedIndex].text+'-'+*/DesValue.options[i].text,DesValue.options[i].value));

        }
        DesValue.options[i].selected =false;

    }
    DesValue.options[maxselected].selected =true;
}
function removeItem(c){
    var listbox = c;
    var max = listbox.options.length-1 ;
    for (var i=max;i>=0;i--) {
        if (listbox.options[i].selected) {
            listbox.options.remove(i);
        }
    }
}
$(window).scroll(function(){
	var t =  $(document).scrollTop();
	/*if(t > 0){
        $(".nav-son-outbox").css("margin-top", "-144px");
        console.log(2345);
    } */
	if(t > 200){
        $("#To-top").fadeIn(500);
    }else{
        $("#To-top").fadeOut(500);
    } 
});
//置顶
$("#To-top").click(function(){
    $("html,body").animate({scrollTop:0},1000);//回到顶端   
    });
//统计页面TAB
jQuery(document).ready(function($){
	var tabs = $('.cd-tabs');
	
	tabs.each(function(){
		var tab = $(this),
			tabItems = tab.find('ul.cd-tabs-navigation'),
			tabContentWrapper = tab.children('ul.cd-tabs-content'),
			tabNavigation = tab.find('nav');

		tabItems.on('click', 'a', function(event){
			event.preventDefault();
			var selectedItem = $(this);
			if( !selectedItem.hasClass('selected') ) {
				var selectedTab = selectedItem.data('content'),
					selectedContent = tabContentWrapper.find('li[data-content="'+selectedTab+'"]'),
					slectedContentHeight = selectedContent.innerHeight();
				
				tabItems.find('a.selected').removeClass('selected');
				selectedItem.addClass('selected');
				selectedContent.addClass('selected').siblings('li').removeClass('selected');
				//animate tabContentWrapper height when content changes 
				tabContentWrapper.animate({
					'height': slectedContentHeight
				}, 200);
			}
		});

		//hide the .cd-tabs::after element when tabbed navigation has scrolled to the end (mobile version)
		checkScrolling(tabNavigation);
		tabNavigation.on('scroll', function(){ 
			checkScrolling($(this));
		});
	});	
	$(window).on('resize', function(){
		tabs.each(function(){
			var tab = $(this);
			checkScrolling(tab.find('nav'));
			tab.find('.cd-tabs-content').css('height', 'auto');
		});
	});
	function checkScrolling(tabs){
		var totalTabWidth = parseInt(tabs.children('.cd-tabs-navigation').width()),
		 	tabsViewport = parseInt(tabs.width());
		if( tabs.scrollLeft() >= totalTabWidth - tabsViewport) {
			tabs.parent('.cd-tabs').addClass('is-ended');
		} else {
			tabs.parent('.cd-tabs').removeClass('is-ended');
		}
	}
});

//点击未读消息进入消息管理页面
$("#messageNum").click(function(){
       document.location.href='pages/messagePage/cms-message.jsp';
});
/** 用户注册* */
function insertUser() {
	var loginName = $("#loginNameSave").val();
	var password = $("#passwordSave").val();
	var password2 = $("#password2Save").val();
	var email = $("#emailSave").val();
    	   if("" == loginName){
    		   layer.alert("请输入帐号!");
    		   return;
    	   }
	      var msg = /^[A-Za-z0-9]+$/;
			if(!msg.test(loginName)){
				layer.alert("用户名暂时只支持英文以及数字！");
				return;
			}
    	   if("" == password){
    		   layer.alert("请输入密码!");
    		   return;
    	   }
    	   if("" == password2){
    		   layer.alert("请确认密码！");
    		   return;
    	   }
    	   if(password != password2){
    		   layer.alert("输入两次的密码不一致!");
    		   return;
    	   }
    	   var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
           if("" != email){
             if(!myreg.test(email))
            {
            	 layer.alert("请输入正确的邮箱格式！");
            	 return;
            }
           }else{
               layer.alert("请输入邮箱!");
    		   return;
           }
	$.ajax({
		url : "/cms_cloudy/user/inserUser.do",
		contentType : 'application/json;charset=UTF-8',
		data : {
			"loginName" : loginName,
			"passWord" : password,
			"email" : encodeURI(email)
		},
		cache : false,
		dataType : "json",
		success : function(json) {
			if (json.result == "repeart") {
				layer.alert("该账户已存在，无须重复注册！");
				$("#loginNameSave").val('');
			}
			if (json.result == "insert") {
				layer.alert("注册成功!");
				$("#loginNameSave").val('');
	            $("#passwordSave").val('');
	            $("#password2Save").val('');
	            $("#emailSave").val('');
				$(".register-box").hide();
			}
			if (json.result == "fail") {
				layer.alert("注册失败,请检查数据的正确性!");
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
//行编辑数据初始化
    var oldObj = {
        num1: '',
        num2: '',
        num3: '',
        num4: '',
        num5: '',
        num6: '',
        num7: '',
        num8: '',
        num9: '',
        num10: '',
        num11: '',
        num12: ''
    };
    //元器件推送
    var n = 0;  
    /*var time = null;
    var session = window.sessionStorage.getItem("pushSession");
    time=window.setTimeout(function () {
    	$(".IntelligentPush-box").css("display","block").css({right:-300}).animate({right:0},700);
    	$(".fixedSide").css({right:0}).animate({right:300},700);
    }, 2000);
    if(session!="BTS"){
    	selectHotSearchFromSelf();*//**浏览最多初始化**//*
    	pushNewPart();*//**最新推荐**//*
    }    */
    /*$("#newSend").click(function(){	
    	 time=window.setTimeout(function () {
    			$(".IntelligentPush-box").css("display","block").css({right:-300}).animate({right:0},700);
    			$(".fixedSide").css({right:0}).animate({right:300},700);
    		}, 1000); */  
    	$(".newSend").click(function(){	
        	var css = document.getElementById("fixedSideId").style.right;
        	if(css == "" || css == null || css == "0px"){//n++%2===0
        		$("#IntelligentPushId").css("display","block").css({right:-300}).animate({right:0},700);
    			$("#fixedSideId").css({right:0}).animate({right:300},700);
        	}else{
        		$("#IntelligentPushId").css("display","block").css({right:0}).animate({right:-300},700);
    			$("#fixedSideId").css({right:300}).animate({right:0},700);
        	}
       		}); 
     /*$(".IntelligentPushBtn").click(function(){
    	       var tempIndex = 'BTS';
    		   window.sessionStorage["pushSession"] = tempIndex;
    		   var compareNum = window.sessionStorage.getItem("pushSession");
    		    	 $(".IntelligentPush-box").hide();   
    		})*/
    /* $(".IntelligentPush-box").click(function(){
    	function timer(){
    		window.clearTimeout(time);  
    	    n++;
    	    time=window.setInterval(function () {
    	    	$(".IntelligentPush-box").css("display","block").css({right:-300}).animate({right:0},700);
    	    }, 10000);
    	}
    	timer();
    }) */
    	/*侧边栏特效*/
    	$(".fixedSide li").hover(function(){
    		$(this).css("background","#d9534f").css("border","none");
    		$(this).children("b").css("color","#fff");
    		$(this).children("em").css("background","#d9534f").css("color","#fff").stop().css({right:-68}).animate({right:34},700);
    	},function(){
    		$(this).css("background","#f2f2f2").css("border","1px solid #ccc").css("border-bottom","none");
    		$(this).children("b").css("color","#d9534f");
    		$(this).children("em").css("background","#f2f2f2").css("color","#888").stop().css({right:34}).animate({right:-68},700);
    	})
    	$(".myCollect").hover(function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-306px -56px","background-color":"#d9534f"});
    	},function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-257px -56px","background-color":"#f2f2f2"});
    	})  
    	$(".waitWork").hover(function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-203px -104px","background-color":"#d9534f"});
    	},function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-154px -106px","background-color":"#f2f2f2"});
    	})  
    	$(".notMessage").hover(function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-103px -108px","background-color":"#d9534f"});
    	},function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-57px -108px","background-color":"#f2f2f2"});
    	}) 
    	 $(".cleverSend").hover(function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-408px -57px","background-color":"#d9534f"});
    	},function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-355px -57px","background-color":"#f2f2f2"});
    	}) 
    	$(".myCompare").hover(function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-152px -57px","background-color":"#d9534f"});
    	},function(){
    		$(this).css({"background":"url(/cms_cloudy/images/cssJL.png)","background-position":"-107px -57px","background-color":"#f2f2f2"});
    	}) 
//详情页显示全部数据
    	$(".showAll").click(function(){
    		var Lei=$(this).children("span").hasClass("glyphicon-plus");
    		if(Lei==true){
    			$(this).children("span").removeClass("glyphicon-plus").addClass("glyphicon-minus");
    			$(this).children("b").html("部分隐藏");
    			changeTr();
    			}else{
    				$(this).children("span").removeClass("glyphicon-minus").addClass("glyphicon-plus");
    				$(this).children("b").html("全部显示");
    				changeTr();
    			}		
    		}
)
//关闭弹出框
function closeDialog(_class){
  $("."+_class).hide();
  //遮罩层
  $("#"+_class).hide();
}
//英文切换
function changeLanguage(){   		
    		var text=$(".change-language").children(".language").html();    		
    		if(text=="English"){
    			//$(".change-language").children("a").html("English");
    			//$(".change-language").children("img").attr("src","images/English.png"); 
    			changeLang("zh");
    		}else{
    			//$(".change-language").children("a").html("中文(简体)");
    			//$(".change-language").children("img").attr("src","images/Chinese.png");
    			changeLang("en");  			
    		}
    	}
    	//追加css文件
    	var dynamicLoading = {
    			  css: function(path){
    			 if(!path || path.length === 0){
    			  throw new Error('argument "path" is required !');
    			 }
    			 var head = document.getElementsByTagName('head')[0];;
    			    var link = document.createElement('link');
    			    link.href = path;
    			    link.rel = 'stylesheet';
    			    link.type = 'text/css';
    			    head.appendChild(link);
    			  }    			 
    			}
    	//元器件申请修改页面点击流程图按钮跳到流程图位置
    	 $(".flowPicBtn").click(function(){
			 document.getElementById("liuchengTitle").scrollIntoView();
		 })   	 
