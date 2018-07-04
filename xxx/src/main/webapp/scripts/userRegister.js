function selectUserSelect(){
	loadProperties();//国际化
   var pHtml = '';
   var dHtml = '';
   $.ajax({
     url: 'departmentController/selectDeptAndPositon.do',
     dataType: 'json',
     cache: false,
     success: function(json){
         var deptList = json.deptList;
         var positionList = json.positionList;
                if(null == deptList || 0 == deptList.length){
				dHtml += '<option value= "">'+$.i18n.prop("placeholder-dept")+'</option>';
				}else{
				dHtml += '<option value= "">'+$.i18n.prop("placeholder-dept")+'</option>';
				for(var s=0;s<deptList.length;s++){
					var value = deptList[s];
				    dHtml += '<option value= '+value.id+'>'+value.departmentName+'</option>';
				 }
				}
               if(null == positionList || 0 == positionList.length){
				pHtml += '<option value= "">'+'请选择职位'+'</option>';
				}else{
				pHtml += '<option value= "">'+'请选择职位'+'</option>';
				for(var X=0;X<positionList.length;X++){
					var value = positionList[X];
				     pHtml += '<option value= '+value.id+'>'+value.positionName+'</option>';
				 }
				}
				$("#addPosition").html(pHtml);
 	            $("#addDepartment").html(dHtml);
     
     },
     error: function(){
       layer.alert("服务器连接异常，请联系管理员！");
     }
   })
}
 //用户信息保存
 function addRegisterUser(){
		var employeeNumber = $("input[name='employeeNumber']").val();
		var userNumber = $("input[name='userNumber']").val();
		var userName = $("input[name='userName']").val();
		var position = $("#addPosition").val();
		var email = $("input[name='emailSave']").val();
		var telephone = $("input[name='telephone']").val();
		var mobilePhone = $("input[name='mobilePhone']").val();
		var department = $("#addDepartment").val();
		var isOrNot = $("select[name='isOrNot']").val();
		var loginName = $("#loginNameSave").val();
		var password = $("#passwordSave").val();
		var password2 = $("#password2Save").val();
		if("" == loginName){
    		   layer.alert("请输入帐号!");
    		   return;
		}
		var msg = /^[A-Za-z0-9]+$/;
		if(!msg.test(loginName)){
			layer.alert("用户名暂时只支持英文以及数字！");
			return;
		}
		if("" == userName){
             layer.alert("中文名不能为空！");
             return;
        }
		if("" == password){
			layer.alert("请输入密码!");
			return;
		}else{
 		   var msg = /^[A-Za-z0-9]+$/;
			   if(!msg.test(password)){
				   layer.alert("帐号格式错误,暂时只支持英文字母或数字!");
					return;
 	              }
		}
		if("" == password2){
			layer.alert("请确认密码！");
			return;
		}
		if(password != password2){
			layer.alert("输入两次的密码不一致!");
			return;
		}
		password = $.md5(password);//加密
		$.ajax({
			url: 'user/inertOrUpdate.do',
  	    	contentType:'application/json;charset=UTF-8', 
          	cache: false,
          	data: {"employeeNumber":encodeURI(employeeNumber),"userNumber":encodeURI(userNumber),"userName":encodeURI(userName),"loginName":encodeURI(loginName),"position":encodeURI(position),"email":encodeURI(email),
          		"telephone":encodeURI(telephone),"mobilePhone":encodeURI(mobilePhone),"department":encodeURI(department),"isOrNot":encodeURI(isOrNot),"passWord":encodeURI(password),"type":"add"},
          	dataType: "json",
          	success: function(json){
          		if(json.result == "insert"){
          			layer.alert('保存成功!');
          			$("input[name='employeeNumber']").val('');
                    $("input[name='userNumber']").val('');
                    $("input[name='userName']").val('');
                    $("input[name='loginName']").val('');
                    $("#addPosition").val('');
                    $("input[name='email']").val('');
                    $("input[name='telephone']").val('');
                    $("input[name='mobilePhone']").val('');
                    $("#addDepartment").val('');
                    $("select[name='isOrNot']").val('');
          		    $("#Pagination").html('');
          		    $("#loginNameSave").val('');
	            	$("#passwordSave").val('');
	            	$("#password2Save").val('');
	            	$("#emailSave").val('');
					$("#modal-register").modal('hide');
          		}
          		if(json.result == "repeart"){
          			layer.alert('该用户已存在，无须重复添加!');
          			$("input[name='loginName']").val('');
          		}
          	},
          	error: function(){
  	 			  layer.alert("数据连接异常！");
          	}
          });
 }

