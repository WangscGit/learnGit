 //用户信息保存
 function addUser(){
 	     loadProperties();
 	      var employeeNumber = $("input[name='employeeNumberAdd']").val();
          var userNumber = $("input[name='userNumberAdd']").val();
          var userName = $("input[name='userNameAdd']").val();
          var loginName = $("input[name='loginName']").val();
          var passWord = $("input[name='passWord']").val();
          var passWord2 = $("input[name='passWord2']").val();
          var position = $("#addPosition").val();
          var email = $("input[name='email']").val();
          var telephone = $("input[name='telephoneAdd']").val();
          var mobilePhone = $("input[name='mobilePhoneAdd']").val();
          var department = $("#addDepartments").val();
          var isOrNot = $("select[name='isOrNot']").val();
          var groupVal = $("#id_select").val();
          var groupText = new Array();
          $("#id_select").find("option:selected").each(function(){
        	  groupText.push($(this).text());
          });
          if("" == loginName){
             layer.alert($.i18n.prop("check-loginName"));
             return;
          }
          if("" == userName){
             layer.alert($.i18n.prop("check-userName"));
             return;
          }
          var msg = /^[A-Za-z0-9]+$/;
			if(!msg.test(loginName)){
				layer.alert($.i18n.prop("check-userNameformat"));
				return;
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
          if("" == passWord2){
        	  layer.alert($.i18n.prop("check-password2"));
			  return; 
		  }
          if(passWord != passWord2){
        	  layer.alert($.i18n.prop("check-simplePWD"));
			  return; 
          }
        var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if("" != email){
          if(!myreg.test(email))
         {
         	 layer.alert($.i18n.prop("check-email"));
         	 return;
         }
        }
        passWord = $.md5(passWord);//加密
          $.ajax({
          	url: 'user/inertOrUpdate.do',
  	    	contentType:'application/json;charset=UTF-8', 
          	cache: false,
          	data: {"employeeNumber":encodeURI(employeeNumber),"userNumber":encodeURI(userNumber),"userName":encodeURI(userName),"loginName":encodeURI(loginName),"position":encodeURI(position),"email":encodeURI(email),
          		"telephone":encodeURI(telephone),"mobilePhone":encodeURI(mobilePhone),"department":encodeURI(department),"isOrNot":encodeURI(isOrNot),"type":"add",'passWord':passWord,'groupVal':JSON.stringify(groupVal),'groupText':JSON.stringify(groupText)},
          	dataType: "json",
          	success: function(json){
          		if(json.result == "insert"){
          			layer.alert($.i18n.prop("alertMsg2"));
          			$("input[name='employeeNumberAdd']").val('');
                    $("input[name='userNumberAdd']").val('');
                    $("input[name='userNameAdd']").val('');
                    $("input[name='loginName']").val('');
                    $("input[name='passWord']").val('');
                    $("input[name='passWord2']").val('');
                    $("#addPosition").val('');
                    $("input[name='email']").val('');
                    $("input[name='telephoneAdd']").val('');
                    $("input[name='mobilePhoneAdd']").val('');
                    $("#addDepartments").val('');
                    $("select[name='isOrNot']").val('');
          			$(".user-addWindow").hide();
          		    $("#Pagination").html('');
          		    $('#id_select').selectpicker('val', '');
					$('#id_select').selectpicker('refresh');
          			initUserList("");
          		}
          		if(json.result == "repeart"){
          			layer.alert($.i18n.prop("check-insertState1"));
          			$("input[name='loginName']").val('');
          		}
          	},
          	error: function(){
  	 			  layer.alert($.i18n.prop("alertError"));
          	}
          });
 }
  //用户信息更新
 function updateUser(){
 	      var employeeNumber = $("input[name='employeeNumberUpdate']").val();
          var userNumber = $("input[name='userNumberUpdate']").val();
          var userName = $("input[name='userNameUpdate']").val();
          var loginName = $("input[name='loginNameUpdate']").val();
          var passWord = $("input[name='passWordUpdate']").val();
          var passWord2 = $("input[name='passWord2Update']").val();
          var position = $("#positionUpdate").val();
          var email = $("input[name='emailUpdate']").val();
          var telephone = $("input[name='telephoneUpdate']").val();
          var mobilePhone = $("input[name='mobilePhoneUpdate']").val();
          var department = $("#departmentUpdate").val();
          var isOrNot = $("#isOrNotUpdate").val();
          var groupVal = $("#id_select1").val();
          var groupText = new Array();
          $("#id_select1").find("option:selected").each(function(){
        	  groupText.push($(this).text());
          });
          var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if ("" == userName) {
		layer.alert($.i18n.prop("check-userName"));
		return;
	}
	if ("" == passWord2) {
            layer.alert($.i18n.prop("check-password"));
            return;
        }
          if("" != email){
          if(!myreg.test(email))
         {
         	 layer.alert($.i18n.prop("check-email"));
         	 return;
         }
        }
         //passWord = $.md5(passWord);//加密
          $.ajax({
          	url: "/cms_cloudy/user/inertOrUpdate.do",
  	    	contentType:'application/json;charset=UTF-8', 
          	cache: false,
          	data: {"employeeNumber":encodeURI(employeeNumber),"userNumber":encodeURI(userNumber),"userName":encodeURI(userName),"loginName":encodeURI(loginName),"position":encodeURI(position),"email":encodeURI(email),
          		"telephone":encodeURI(telephone),"mobilePhone":encodeURI(mobilePhone),"department":encodeURI(department),"isOrNot":encodeURI(isOrNot),"passWord":passWord,'groupVal':JSON.stringify(groupVal),'groupText':JSON.stringify(groupText)},
          	dataType: "json",
          	success: function(json){
          		if(json.result == "insert"){
          			layer.alert('保存成功!');
          		    $("#Pagination").html('');
          			initUserList("");
          		}
          		if(json.result == "update"){
          			layer.alert($.i18n.prop("alertMsg1"));
          			$("input[name='employeeNumberUpdate']").val('');
                    $("input[name='userNumberUpdate']").val('');
                    $("input[name='userNameUpdate']").val('');
                    $("input[name='loginNameUpdate']").val('');
                    $("#positionUpdate").val('');
                    $("input[name='emailUpdate']").val('');
                    $("input[name='telephoneUpdate']").val('');
                    $("input[name='mobilePhoneUpdate']").val('');
                    $("#departmentUpdate").val('');
                    $("#isOrNotUpdate").val('');
          			$(".user-editWindow").hide();
          			$("#Pagination").html('');
          			initUserList("");
          		}
          	},
          	error: function(){
  	 			  layer.alert($.i18n.prop("alertError"));
          	}
          });
 }
 //关闭添加用户框
 function closeAddUser(){
   $(".user-addWindow").hide();
 }
 //关闭修改用户框
 function closeUploadUser(){
   $(".user-editWindow").hide();
 }
 //用户修改之前
 function userUpdateBefore(){
 	loadProperties();
 var ids=new Array;
	$("input[name='checkOneUser']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}
	if(ids.length>1){
	   layer.alert($.i18n.prop("check-edit2"));
		return;
	}
	$.ajax({
          	url: "/cms_cloudy/user/updateBefore.do",
          	cache: false,
          	data: {'id':JSON.stringify(ids)},
          	dataType: "json",
          	success: function(json){
          	  var list = json.list;
          	  var user = list[0];
          	   $("input[name='employeeNumberUpdate']").val(user.employeeNumber);
               $("input[name='userNumberUpdate']").val(user.userNumber);
               $("input[name='userNameUpdate']").val(user.userName);
               $("input[name='loginNameUpdate']").val(user.loginName);
               selectFull2(user);
               //$("select[name='positionUpdate']").val(user.position);
               $("input[name='emailUpdate']").val(user.email);
               $("input[name='telephoneUpdate']").val(user.telephone);
               $("input[name='mobilePhoneUpdate']").val(user.mobilePhone);
               $("input[name='passWordUpdate']").val(user.password);
               $("input[name='passWord2Update']").val(user.password);
               $("#isOrNotUpdate").val(user.isOrNot);
               //$("input[name='isOrNotUpdate']").val(user.isOrNot);
               initGroupFromUser(user.userId);
          	   $(".user-editWindow").show();
          	},
          	error: function(){
  	 			  layer.alert("数据连接异常！");
          	}
          });
 }
 //校验登录名是否已经存在
 function checkLoginName(state,loginName){
 }
 //职位查询
 function initPosition(pageNo){
   if(isNaN(pageNo)){
    		pageNo = 0;
   }
  var addBasic = '1';
  pageNo = parseInt(addBasic)+parseInt(pageNo);
  $.ajax({
        	url: "/cms_cloudy/positionController/selectPositionList.do",
        	data: {'pageNo':pageNo},
  			dataType: "json",
  			cache: false, 
  			success: function(json){
  				var html = '';
  				if(null == json.list || "" == json.list){
					html +='<tr>';
					html +='<td colspan="8" align="center">--没有数据--</td>';
					html +='</tr>';
					  $('#show-position').html(html);
				}else{
					var list = json.list;
					var count = json.count;
        	        var pageNo = json.pageNo;
        	        var pageSize= json.pageSize;
					for( var i = 0;i<list.length;i++){
					var index = '1';
                    index = parseInt(index)+parseInt(i);
					var position = list[i];
					html +='<tr>';
					html += '<td>'+'<input type="checkbox" onclick="checkOnePosition('+position.id+')" name="checkOnePosition" value="'+position.id+'">'+'</td>';
					html +='<td>'+index+'</td>';
					html +='<td>'+position.positionName+'</td>';
					html +='<td>'+position.createuser+'</td>';
					html +='<td>'+toDate(position.createtime)+'</td>';
					html +='<td>'+position.modifyuser+'</td>';
					if(null != position.modifytime && "" != position.modifytime){
		            html +='<td>'+toDate(position.modifytime)+'</td>';
					}else{
					html +='<td>'+''+'</td>';
					}
					html +='<td>'+position.positionRemark+'</td>';
					html +='</tr>';
					}
					//分页插件
    			if($("#PaginationPosition").html().length == ''){
    			       $("#PaginationPosition").pagination(
    			    		   count,
    	    	                {
    	    	                    items_per_page : pageSize,
    	    	                    num_edge_entries : pageNo,
    	    	                    num_display_entries : 8,
    	    	                    callback: function(pageNo, panel){
    	    	                       if(count==null){
    	    	                    	   initPosition(pageNo);
						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	        });
    			}
    			  $('#show-position').html(html);
    			  count =null;
				}
  			},
  			error: function(){
  				alert("数据连接异常,查询失败！");
  			}
          });
 }
  //部门查询
 function initDepartment(pageNo){
 	loadProperties();
   if(isNaN(pageNo)){
    		pageNo = 0;
   }
  var addBasic = '1';
  pageNo = parseInt(addBasic)+parseInt(pageNo);
  $.ajax({
        	url: "/cms_cloudy/departmentController/selectDepartmentList.do",
        	data: {'pageNo':pageNo},
  			dataType: "json",
  			cache: false, 
  			success: function(json){
  				var html = '';
  				if(null == json.list || "" == json.list){
					html +='<tr>';
					html +='<td colspan="10" align="center">--'+$.i18n.prop("noData")+'--</td>';
					html +='</tr>';
					  $('#show-department').html(html);
				}else{
					var list = json.list;
					var count = json.count;
        	        var pageNo = json.pageNo;
        	        var pageSize= json.pageSize;
					for( var i = 0;i<list.length;i++){
					var index = '1';
                    index = parseInt(index)+parseInt(i);
					var department = list[i];
					html +='<tr>';
					html += '<td>'+'<input type="checkbox" onclick="checkOneDepartmentId('+department.id+')" name="checkOneDepartmentId" value="'+department.id+'">'+'</td>';
					html +='<td>'+index+'</td>';
					html +='<td>'+department.departmentName+'</td>';
					html +='<td>'+department.departmentNo+'</td>';
					html +='<td>'+department.departmentMaster+'</td>';
					html +='<td>'+department.departmentDescript+'</td>';
					html +='<td>'+department.createuser+'</td>';
					html +='<td>'+toDate(department.createtime)+'</td>';
					html +='<td>'+department.modifyuser+'</td>';
					if(null != department.modifytime && "" != department.modifytime){
					html +='<td>'+toDate(department.modifytime)+'</td>';
					}else{
					html +='<td>'+''+'</td>';
					}
					html +='<td class="edit-td">';
					html +='<div class="user-btnGroup2">';
					html += '<a class="btn btn-danger btn-xs department-member" onclick="deptUserList('+department.id+')">'+'<span class="glyphicon glyphicon-star-empty"></span>'+$.i18n.prop("user-Showmember")+'</a>';
               	    html +='</div>';
               	    html +='</td>';
					html +='</tr>';
					}
					//分页插件
    			if($("#PaginationDepartment").html().length == ''){
    			       $("#PaginationDepartment").pagination(
    			    		   count,
    	    	                {
    	    	                    items_per_page : pageSize,
    	    	                    num_edge_entries : pageNo,
    	    	                    num_display_entries : 8,
    	    	                    callback: function(pageNo, panel){
    	    	                       if(count==null){
    	    	                    	   initDepartment(pageNo);
						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	        });
    			}
    			  $('#show-department').html(html);
    			  count =null;
				}
  			},
  			error: function(){
  				layer.alert($.i18n.prop("alertError"));
  			}
          });
 }
 //部门添加
 function addDepartment(){
 	loadProperties();
    var departmentName = $("input[name='departmentName']").val();
    var departmentNo = $("input[name='departmentNo']").val();
    var departmentMaster = $("input[name='departmentMaster']").val();
    var departmentDescript = $("input[name='departmentDescript']").val();
    if("" == departmentName){
       layer.alert($.i18n.prop("check-deptName"));
       return;
    }
    if("" == departmentNo){
       layer.alert($.i18n.prop("check-deptNo"));
       return;
    }
    $.ajax({
      url: 'departmentController/insertDepartment.do',
       contentType:'application/json;charset=UTF-8', 
      data: {'departmentName':encodeURI(departmentName),
                 'departmentNo':encodeURI(departmentNo),
                 'departmentMaster':encodeURI(departmentMaster),
                 'departmentDescript':encodeURI(departmentDescript)
                },
      dataType: 'json',
      cache: false,
      success: function(json){
         if(json.result == "add"){
            layer.alert($.i18n.prop("alertMsg2"));
            $(".department-addWindow").hide();
            $("input[name='departmentName']").val('');
            $("input[name='departmentNo']").val('');
            $("input[name='departmentMaster']").val('');
            $("input[name='departmentDescript']").val('');
            $("#PaginationDepartment").html('');
            initDepartment();
         }
      },
      error: function(){
      layer.alert($.i18n.prop("alertError"));
      }
    })
 }
 //部门修改之前
 function departmentUpdateBefore(){
 loadProperties();
 var ids=new Array;
	$("input[name='checkOneDepartmentId']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}
	if(ids.length>1){
	   layer.alert($.i18n.prop("check-edit2"));
		return;
	}
	$.ajax({
          	url: "/cms_cloudy/departmentController/updateBefore.do",
          	cache: false,
          	data: {'id':JSON.stringify(ids)},
          	dataType: "json",
          	success: function(json){
          	  var list = json.list;
          	  var dept = list[0];
            $("input[name='departmentNameUpdate']").val(dept.departmentName);
            $("input[name='departmentNoUpdate']").val(dept.departmentNo);
            $("input[name='departmentMasterUpdate']").val(dept.departmentMaster);
            $("input[name='departmentDescriptUpdate']").val(dept.departmentDescript);
          	$(".department-editWindow").show();
          	},
          	error: function(){
  	 			  layer.alert("数据连接异常！");
          	}
      });
 }
  //部门修改
 function updateDepartment(){
 		loadProperties();
 	 var ids=new Array;
	$("input[name='checkOneDepartmentId']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
    var departmentName = $("input[name='departmentNameUpdate']").val();
    var departmentNo = $("input[name='departmentNoUpdate']").val();
    var departmentMaster = $("input[name='departmentMasterUpdate']").val();
    var departmentDescript = $("input[name='departmentDescriptUpdate']").val();
    if("" == departmentName){
       layer.alert($.i18n.prop("check-deptName"));
       return;
    }
    if("" == departmentNo){
       layer.alert($.i18n.prop("check-deptNo"));
       return;
    }
    $.ajax({
      url: 'departmentController/updateDepartment.do',
      contentType:'application/json;charset=UTF-8', 
      data: {'departmentName':encodeURI(departmentName),
                 'departmentNo':encodeURI(departmentNo),
                 'departmentMaster':encodeURI(departmentMaster),
                 'departmentDescript':encodeURI(departmentDescript),
                 'id':JSON.stringify(ids)
                },
      dataType: 'json',
      cache: false,
      success: function(json){
         if(json.result == "update"){
            layer.alert($.i18n.prop("alertMsg1"));
            $(".department-editWindow").hide();
            $("input[name='departmentNameUpdate']").val('');
            $("input[name='departmentNoUpdate']").val('');
            $("input[name='departmentMasterUpdate']").val('');
            $("input[name='departmentDescriptUpdate']").val('');
            $("#PaginationDepartment").html('');
            initDepartment();
         }else{//部门名称重复
        	 layer.alert(json.result);
             $("input[name='departmentNameUpdate']").val('');
         }
      },
      error: function(){
      layer.alert($.i18n.prop("alertError"));
      }
    })
 }
  //职位添加
 function addPosition(){
 	loadProperties();
    var positionName = $("input[name='positionName']").val();
    var positionRemark = $("input[name='positionRemark']").val();
    if("" == positionName){
       layer.alert($.i18n.prop("check-optionName"));
       return;
    }
    $.ajax({
      url: 'positionController/insertPosition.do',
      contentType:'application/json;charset=UTF-8', 
      data: {'positionName':encodeURI(positionName),'positionRemark':encodeURI(positionRemark)},
      dataType: 'json',
      cache: false,
      success: function(json){
         if(json.result == "add"){
            layer.alert($.i18n.prop("alertMsg2"));
            $(".post-addWindow").hide();
            $("input[name='positionName']").val('');
            $("input[name='positionRemark']").val('');
            $("#PaginationPosition").html('');
            initPosition();
         }
      },
      error: function(){
      layer.alert($.i18n.prop("alertError"));
      }
    })
 }
 //职位修改之前
 function positionUpdateBefore(){
 	loadProperties();
 var ids=new Array;
	$("input[name='checkOnePosition']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}
	if(ids.length>1){
	   layer.alert($.i18n.prop("check-edit2"));
		return;
	}
	$.ajax({
          	url: "/cms_cloudy/positionController/updateBefore.do",
          	cache: false,
          	data: {'id':JSON.stringify(ids)},
          	dataType: "json",
          	success: function(json){
          	  var list = json.list;
          	  var position = list[0];
          	   $("input[name='positionNameUpdate']").val(position.positionName);
               $("input[name='positionRemarkUpdate']").val(position.positionRemark);
          	   $(".post-editWindow").show();
          	},
          	error: function(){
  	 			  layer.alert("数据连接异常！");
          	}
          });
 }
  //职位修改
 function updatePosition(){
 	loadProperties();
 	var ids = Array();
 	$("input[name='checkOnePosition']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
    var positionName = $("input[name='positionNameUpdate']").val();
    var positionRemark = $("input[name='positionRemarkUpdate']").val();
    if("" == positionName){
       layer.alert($.i18n.prop("check-optionName"));
       return;
    }
    $.ajax({
      url: 'positionController/updatePosition.do',
      contentType:'application/json;charset=UTF-8', 
      data: {'positionName':encodeURI(positionName),'positionRemark':encodeURI(positionRemark),'id':JSON.stringify(ids)},
      dataType: 'json',
      cache: false,
      success: function(json){
         if(json.result == "update"){
            layer.alert($.i18n.prop("alertMsg1"));
            $(".post-editWindow").hide();
            $("input[name='positionNameUpdate']").val('');
            $("input[name='positionRemarkUpdate']").val('');
            $("#PaginationPosition").html('');
            initPosition();
         }else{
             layer.alert(json.result);
             $("input[name='positionNameUpdate']").val('');
         }
      },
      error: function(){
      layer.alert($.i18n.prop("alertError"));
      }
    })
 }
 //删除职位
 function deletePosition(){
 	loadProperties();
   	var ids = Array();
 	$("input[name='checkOnePosition']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-delete1"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
		}, function() {
		$.ajax({
					url : 'positionController/deletePosition.do',
					data : 'id=' + JSON.stringify(ids),
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert($.i18n.prop("alertMsg3"));
						$("#PaginationPosition").html('');
						initPosition();
						if ($("#checkAlluser").prop('checked')) {
							$("#checkAlluser").prop('checked', false);
						}
					},
					error : function() {
						layer.alert("服务器连接异常，请联系管理员！");
					}
				});
	});
 }
  //删除部门
 function deleteDepartment(){
 	loadProperties();
    var ids=new Array;
	$("input[name='checkOneDepartmentId']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-delete1"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
		}, function() {
		$.ajax({
			url : 'departmentController/deleteDepartment.do',
			data : 'id=' + JSON.stringify(ids),
			dataType : 'json',
			cache : false,
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg3"));
				$("#PaginationDepartment").html('');
				initDepartment();
				if ($("#checkAllDepartment").prop('checked')) {
					$("#checkAllDepartment").prop('checked', false);
				}
			},
			error : function() {
				layer.alert($.i18n.prop("alertError"));
			}
		});
	});
 }
 /**用户复选框全选反选操作**/
function checkAlluser(){
var CheckBox=document.getElementsByName('checkOneUser');
if($("#checkAlluser").prop('checked')){
  for(var i=0;i<CheckBox.length;i++){
  	if(CheckBox[i].checked==false){
  	    CheckBox[i].checked=true;
  	}
   }
}else{
   for(var i=0;i<CheckBox.length;i++){
   	if(CheckBox[i].checked=true){
   	    CheckBox[i].checked=false;
   	}
   }
}
}
/**用户复选框单个选择**/
function checkOneUser(id){
	var CheckBox=document.getElementsByName('checkOneUser');
    var num=0;
    if(CheckBox.length>0){
     for(var i=0;i<CheckBox.length;i++){
      if(CheckBox[i].checked){
      	num++;
      }
    }
    }
    if( num == CheckBox.length){
    $("#checkAlluser").prop('checked',true); 
    }else{
    $("#checkAlluser").prop('checked',false); 
    }
}
 /**职位复选框全选反选操作**/
function checkAllPosition(){
var CheckBox=document.getElementsByName('checkOnePosition');
if($("#checkAllPosition").prop('checked')){
  for(var i=0;i<CheckBox.length;i++){
  	if(CheckBox[i].checked==false){
  	    CheckBox[i].checked=true;
  	}
   }
}else{
   for(var i=0;i<CheckBox.length;i++){
   	if(CheckBox[i].checked=true){
   	    CheckBox[i].checked=false;
   	}
   }
}
}
/**职位复选框单个选择**/
function checkOnePosition(id){
	var CheckBox=document.getElementsByName('checkOnePosition');
    var num=0;
    if(CheckBox.length>0){
     for(var i=0;i<CheckBox.length;i++){
      if(CheckBox[i].checked){
      	num++;
      }
    }
    }
    if( num == CheckBox.length){
    $("#checkAllPosition").prop('checked',true); 
    }else{
    $("#checkAllPosition").prop('checked',false); 
    }
}
 /**部门复选框全选反选操作**/
function checkAllDepartment(){
var CheckBox=document.getElementsByName('checkOneDepartmentId');
if($("#checkAllDepartment").prop('checked')){
  for(var i=0;i<CheckBox.length;i++){
  	if(CheckBox[i].checked==false){
  	    CheckBox[i].checked=true;
  	}
   }
}else{
   for(var i=0;i<CheckBox.length;i++){
   	if(CheckBox[i].checked=true){
   	    CheckBox[i].checked=false;
   	}
   }
}
}
/**部门复选框单个选择**/
function checkOneDepartmentId(id){
	var CheckBox=document.getElementsByName('checkOneDepartmentId');
    var num=0;
    if(CheckBox.length>0){
     for(var i=0;i<CheckBox.length;i++){
      if(CheckBox[i].checked){
      	num++;
      }
    }
    }
    if( num == CheckBox.length){
    $("#checkAllDepartment").prop('checked',true); 
    }else{
    $("#checkAllDepartment").prop('checked',false); 
    }
}
function selectFull(){
	loadProperties();
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
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var s=0;s<deptList.length;s++){
					var value = deptList[s];
				     dHtml += '<option value= '+value.id+'>'+value.departmentName+'</option>';
				 }
				}
               if(null == positionList || 0 == positionList.length){
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var X=0;X<positionList.length;X++){
					var value = positionList[X];
				     pHtml += '<option value= '+value.id+'>'+value.positionName+'</option>';
				 }
				}
				$("#addPosition").html(pHtml);
 	            $("#addDepartments").html(dHtml);
     
     },
     error: function(){
       layer.alert("服务器连接异常，请联系管理员！");
     }
   })
}
function selectFull2(user){
	loadProperties();
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
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var s=0;s<deptList.length;s++){
					var value = deptList[s];
					if("" != user.department && user.department == value.id){
							dHtml += '<option value= '+value.id+' selected>'+value.departmentName+'</option>';
					}else{
							dHtml += '<option value= '+value.id+'>'+value.departmentName+'</option>';
					}
				 }
				}
               if(null == positionList || 0 == positionList.length){
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var X=0;X<positionList.length;X++){
					var value = positionList[X];
					if("" !=user.position && user.position == value.id){
						 pHtml += '<option value= '+value.id+' selected>'+value.positionName+'</option>';
					}else{
						 pHtml += '<option value= '+value.id+'>'+value.positionName+'</option>';
					}
				 }
				}
				$("#positionUpdate").html(pHtml);
 	            $("#departmentUpdate").html(dHtml);
     
     },
     error: function(){
       layer.alert($.i18n.prop("alertError"));
     }
   })
}
//查询select赋值
function selectFull3(user){
	loadProperties();
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
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				dHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var s=0;s<deptList.length;s++){
					var value = deptList[s];
					if("" != user.department && user.department == value.id){
							dHtml += '<option value= '+value.id+' selected>'+value.departmentName+'</option>';
					}else{
							dHtml += '<option value= '+value.id+'>'+value.departmentName+'</option>';
					}
				 }
				}
               if(null == positionList || 0 == positionList.length){
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				}else{
				pHtml += '<option value= "请选择">'+$.i18n.prop("selectBtn")+'</option>';
				for(var X=0;X<positionList.length;X++){
					var value = positionList[X];
					if("" !=user.position && user.position == value.id){
						 pHtml += '<option value= '+value.id+' selected>'+value.positionName+'</option>';
					}else{
						 pHtml += '<option value= '+value.id+'>'+value.positionName+'</option>';
					}
				 }
				}
				$("#position"+user.userId).html(pHtml);
 	            $("#dept"+user.userId).html(dHtml);
     
     },
     error: function(){
       layer.alert("服务器连接异常，请联系管理员！");
     }
   })
}
//部门人员信息查询
function deptUserList(deptId){
	loadProperties();
     $.ajax({
	     url: 'user/selectAllUserByDept.do',
	     data: 'deptId='+deptId,
	     dataType: 'json',
	     cache: false,
	     success: function(data){
	     	var json = data.list;
	        var html = '';
  				if(null == json || "" == json){
					html +='<tr>';
					html +='<td colspan="1" align="center">--'+$.i18n.prop("noData")+'--</td>';
					html +='</tr>';
				}else{
					var i =Math.ceil(json.length/2);
					var array = Array();
					for(var jj=0;jj<json.length;jj++){
						array.push(json[jj]);
					}
					var first ='yes';
					for(var x=0;x<i;x++){
						  var num = 0;
						  if( first !='yes'){
						  array.shift();
						  array.shift();
						  }
					      html += '<tr>';
					      if(array.length>0){
					      for(var a=0;a<array.length;a++){
					      	var user = array[a];
					      	if(null == user){
					      	  break;
					      	}
					      	html += '<td>'+'<span>'+$.i18n.prop("user-Membername")+'：'+'</span>'+'</td>';
					      	html += '<td>'+'<b>'+user.userName+'</b>'+'</td>';
					         num++;
					         //array.shift();
					          first ='no';
					         if(num == 2){
					         	num == 0;
					         	 break;
					         }
					      }
					      }
					    html += '</tr>';
					}
				}
				$("#deptUserList").html(html);
				$(".department-user-box").show();
	     },
	     error: function(){
	      alert($.i18n.prop("alertError"));
	     }
	 })
}
//关闭职位添加框
function closeAddPosition(){
$(".post-addWindow").hide();
}
//关闭职位修改框
function closeUpdatePosition(){
$(".post-editWindow").hide();
}
//关闭部门添加框
function closeAddDepartment(){
$(".department-addWindow").hide();
}
//关闭部门修改框
function closeUpdateDepartment(){
$(".department-editWindow").hide();
}
//用户导入模板下载
function onloadTemp(){
	document.location.href=getContextPathForWS()+'/fileExportController/downloadUserTemplation.do';
}
//用户管理 --- 用户组加载
function initGroupFromUser(state){
	$.ajax({
		url : 'HrGroupController/selectAllGroup.do',
		data : 'state='+state,
		dataType : 'json',
		cache : false,
		success : function(json) {
			var hgList = json.hgList;
			var selectedGroup = json.selectedGroup;
			var html = '';
			if(null == hgList || hgList.length == 0){
				$('.selectpicker').selectpicker('val', '');
				$('.selectpicker').selectpicker('refresh');
			}else{
				$('#id_select1').find('option').remove();
				$('#id_select').find('option').remove();
				var seletedList = new Array();
				for(var x=0; x<hgList.length; x++){
					var group = hgList[x];
					var selectAdd = $("#id_select");
					var	selectEdit = $("#id_select1");
					selectAdd.append("<option value='"+group.groupId+"'>"  
	                      + group.groupName + "</option>");
					selectEdit.append("<option value='"+group.groupId+"'>"  
							+ group.groupName + "</option>");
				}
				if(state != '-1'){
					for(var v=0; v<selectedGroup.length; v++){//获取已分配的组
						seletedList.push(selectedGroup[v].group_id);
					}
					$('#id_select1').selectpicker('val', seletedList);
					$('#id_select1').selectpicker('refresh');
				}else{
					$('#id_select').selectpicker('val', '');
					$('#id_select').selectpicker('refresh');
				}
			}
		},
		error : function() {
			layer.alert("数据加载异常！");
		}
	});
}
//用户列表导出
function exportUserByCondition(){
	var ids=new Array;
	$("input[name='checkOneUser']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	window.location.href=getContextPathForWS()+"/user/exportExcel.do?ids="+JSON.stringify(ids)
}
