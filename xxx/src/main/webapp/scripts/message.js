$(function(){
   initSysMessage();//消息列表初始化
})
//消息列表初始化
function initSysMessage(){
	var whetherRead = $("#queryType").html();
	if(whetherRead == ''){
		whetherRead = 'false';//默认查询未读消息
	}
	$.ajax({
	    url : 'sysMessageController/selectMessageList.do',
	    data: 'whetherRead='+whetherRead,
	    type: 'post',
	    dataType: 'json',
	    cache: false,
	    success: function(json){
	    var list = json;
	    var html = '';
	    if(null == list || "" == list || list.lenght <= 0){
	       html +='<tr>';
		   html +='<td colspan="8" align="center">'+'--'+'</td>';
		   html +='</tr>';
	    }else{
	       for(var x=0;x<list.length;x++){
	          var msg = list[x];
	          html += '<tr>';
	          if(msg.cdefine1 == "javascript:void(0)"){
	        	  html +='<td style="color:#ed1b24">'+msg.msgType+'</td>';  
	          }else{
		          html +='<td class="messagelink">'+'<a href="'+msg.cdefine1+'">'+msg.msgType+'</a></td>';
	          }
	          html +='<td>'+msg.msgTittle+'</td>';
	          html +='<td>'+msg.msgContent+'</td>';
	          html +='<td>'+msg.msgLevel+'</td>';
	          html +='<td>'+msg.launchPerson+'</td>';
	          html +='<td>'+toDate(msg.launchTime)+'</td>';
	          var rt = (typeof(msg.readTime) == "undefined")?'':toDate(msg.readTime);
	          if("false" != whetherRead){
	          	html +='<td name="readTd">'+rt+'</td>';
	          }
	          var isRead=msg.whetherRead == true? "checked=checked":"";
	          html +='<td><input type="checkbox" class="form-control message-view-input" '+isRead+' onclick="clickRead(this,'+msg.id+')" id="'+msg.id+'"/></td>';
	          html += ' </tr>';
	       }
	    }
	    $("#messageList").html(html);
	    },
	    error: function(){
	      layer.alert("数据请求异常，请联系管理员！");
	    }
	})
}
//点击未读消息按钮
function noReadMsg(){
  $("#queryType").html('false');
  $("#readTime").hide();
  initSysMessage();
}
//点击全部消息按钮
function allMsg(){
  $("#queryType").html('true');
   $("#readTime").show();
   initSysMessage();
}
//点击阅读
function clickRead(state,id){
	var tr = $(state).parent().parent();
   if($("#"+id).is(':checked')){
   	if($("#queryType").html() != "true"){
   	   $(tr).remove();
   	}else{
        $(tr).find("td").each(function(i,x){
           if($(x).attr("name") == "readTd"){
               $(x).html(toDate(new Date().getTime()));
           }
        })	
   	}
      updateMsg(id,'0');
   }else{
   	if($("#queryType").html() != "true"){
   		$(tr).remove();
   	}else{
        $(tr).find("td").each(function(i,x){
           if($(x).attr("name") == "readTd"){
               $(x).html('');
           }
        })	
   	}
      updateMsg(id,'1');
   }
}
//更新阅读信息
function updateMsg(id,type){
     $.ajax({
	    url : 'sysMessageController/updateMessage.do',
	    data: {'id':id,'type':type},
	    type: 'post',
	    dataType: 'json',
	    cache: false,
	    success: function(json){
	    if(null == json){
	      layer.alert("数据异常，数据库找不到对应的该条记录！");
	      return;
	    } 
	     $(".messageNumber").html(json);
	    },
	    error: function(){
	      layer.alert("数据请求异常，请联系管理员！");
	    }
	})
}
//消息发布----添加
function saveMsg(){
	 loadProperties();//国际化
	var msgTittle = $("input[name='msgTittle']").val();
	var msgType = $("select[name='msgType']").val();
	var msgLevel = $("select[name='msgLevel']").val();
	var msgContent = $("input[name='msgContent']").val();
	var receiverPerson = new Array();
	var checkNum = $("#userListAdd")
								.find("input:checkbox");
	for (var n = 0; n < checkNum.length; n++) {
		if(checkNum[n].checked == true){
			receiverPerson.push($(checkNum[n]).val());
		}
	}
	if('' == msgTittle){
		layer.alert($.i18n.prop("messageAlert1"));
		return;
	}
	if('' == msgType){
		layer.alert($.i18n.prop("messageAlert2"));
		return;
	}
	if('' == msgLevel){
		layer.alert($.i18n.prop("messageAlert3"));
		return;
	}
	if('' == msgContent){
		layer.alert($.i18n.prop("messageAlert4"));
		return;
	}
	if(receiverPerson.length <= 0){
		layer.alert($.i18n.prop("messageAlert5"));
		return;
	}
    $.ajax({
				url : 'sysMessageController/insertMessage.do',
				data : {
					'msgTittle' : msgTittle,
					'msgType' : msgType,
					'msgLevel' : msgLevel,
					'msgContent' : msgContent,
					'receiverPerson' : JSON.stringify(receiverPerson)
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					for (var x = 0; x < json.length; x++) {
						sendWebsocket(json[x]);
					}
					$(".message-add-page").hide();
					layer.alert($.i18n.prop("messageAlert6"));
					$("input[name='msgTittle']").val('');
					$("input[name='msgContent']").val('');
					var checkNums = $("#userListAdd")
								.find("input:checkbox");
					for (var xx = 0; xx < checkNums.length; xx++) {
						checkNums[xx].checked = false;
					}
					initsendMsgpage();// 消息发布列表刷新
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			})
}
//消息发布----接收人加载
function receiverList(){
	 $.ajax({
				url : 'user/selectAllUserToSend.do',
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var list = json.userList
					var html = "";
					if( null != list && list.length > 0 ){
					   var line =Math.ceil(list.length/2);//行数
					   var array = new Array();
					   var first = true;//是否是第一次进入循环
					   for(var x=0;x<list.length;x++){
					          array.push(list[x]);
					   }
					   for(var s=0;s<line;s++){
					   	  var num = 0;
					   	  if(first != true){
					   	     array.shift();
						     array.shift();
					   	  }
                        if(array.length>0){
                           html += '<tr>';
					       for(var i=0;i<array.length;i++){
					          var user=array[i];
					          if(null == user){
					      	      break;
					      	   }
				              html += '<td><input type="checkbox" class="" name="selectReceiverToAnd" value="'+user.loginName+'"/></td>';
				              html += '<td><span>'+user.userName+'('+user.loginName+')'+'</span></td>';
				              num++;
				              first = false;
					         if(num == 2){
					         	num == 0;
					         	break;
					         }
					        }
					       html += '</tr>';
                        }
					   }
					}
					$("#userListAdd").html(html);
					$("#userListUpdate").html(html);
					$("#userListLook").html(html);
				},
				error : function() {
					layer.alert("数据请求异常，请联系管理员！");
				}
		})
}
//消息发布----查询列表初始化
function initsendMsgpage(){
	loadProperties();//国际化
   $.ajax({
	    url : 'sysMessageController/selectSendMessageList.do',
	    type: 'post',
	    dataType: 'json',
	    cache: false,
	    success: function(json){
	    var list = json;
	    var html = '';
	    if(null == list || "" == list || list.lenght <= 0){
	       html +='<tr>';
		   html +='<td colspan="9" align="center">'+'--'+'</td>';
		   html +='</tr>';
	    }else{
	       for(var x=0;x<list.length;x++){
	          var msg = list[x];
	          var modifier = null == msg.cdefine2 ? "":msg.cdefine2;
	          var modifiyTime = null == msg.cdefine5 ? "":toDate(msg.cdefine5);
	          html += '<tr>';
	          html +='<td class="messagelink">'+'<a href="'+msg.cdefine1+'">'+msg.msgType+'</a></td>';
	          html +='<td>'+msg.msgTittle+'</td>';
	          html +='<td>'+msg.msgContent+'</td>';
	          html +='<td>'+msg.msgLevel+'</td>';
	          html +='<td>'+msg.launchPerson+'</td>';
	          html +='<td>'+toDate(msg.launchTime)+'</td>';
	          html +='<td>'+modifier+'</td>';
	          html +='<td>'+modifiyTime+'</td>';
	          html +='<td class="work">';
	          html += '<a class="btn btn-danger btn-xs message-edit" onclick="updateSendMsgBefore('+msg.id+')"><span class="glyphicon glyphicon-edit"></span>  '+$.i18n.prop("updateBtn")+'</a>';
	          html += '<a class="btn btn-danger btn-xs message-delete" onclick="deleteMsg('+msg.id+')"><span class="glyphicon glyphicon-remove"></span>  '+$.i18n.prop("deleteBtn")+'</a>';
	          html += '<a class="btn btn-danger btn-xs message-more" onclick="msgLoolDialog('+msg.id+')"><span class="glyphicon glyphicon-file"></span>  '+$.i18n.prop("moreBtn")+'</a>';
	          html +='</td>';
	          html += ' </tr>';
	       }
	    }
	    $("#sendMsgpage").html(html);
	    },
	    error: function(){
	      layer.alert($.i18n.prop("alertError"));
	    }
	})
}
// 消息发布----修改前
function updateSendMsgBefore(id) {
	$.ajax({
				url : 'sysMessageController/updateSendMsgBefore.do',
				data : 'id=' + id,
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (null != json) {
						var html = "";
						var msg = json.message;
						var ul = json.receiverList;
						$("input[name='msgTittleUpdate']").val(msg.msgTittle);
						$("select[name='msgTypeUpdate']").val(msg.msgType);
						$("select[name='msgLevelUpdate']").val(msg.msgLevel);
						$("input[name='msgContentUpdate']").val(msg.msgContent);
						var checkNum = $("#userListUpdate")
								.find("input:checkbox");
						for (var n = 0; n < checkNum.length; n++) {
							var value = $(checkNum[n]).val();
							var result = false;
							for (var i = 0; i < ul.length; i++) {
								if (value == ul[i].receiverPerson) {
									result = true;
								}
							}
							if (result == true) {
								checkNum[n].checked = true;
							} else {
								checkNum[n].checked = false;
							}
							result = false;
						}
						$("#modifyBtn").attr("href",
								"javascript:modifyMsg(" + msg.id + ")");
						$(".message-edit-page").show();
					} else {
						layer.alert("数据库找到不对相对应的数据！");
					}
				},
				error : function() {
					layer.alert("数据请求异常，请联系管理员！");
				}
			})
}
// 消息发布----提交修改信息
function modifyMsg(id){
	 loadProperties();//国际化
  var msgTittle = $("input[name='msgTittleUpdate']").val();
	var msgType = $("select[name='msgTypeUpdate']").val();
	var msgLevel = $("select[name='msgLevelUpdate']").val();
	var msgContent = $("input[name='msgContentUpdate']").val();
	var receiverPerson = new Array();
	var checkNum = $("#userListUpdate")
								.find("input:checkbox");
	for (var n = 0; n < checkNum.length; n++) {
		if(checkNum[n].checked == true){
			receiverPerson.push($(checkNum[n]).val());
		}
	}
	if('' == msgTittle){
		layer.alert($.i18n.prop("messageAlert1"));
		return;
	}
	if('' == msgType){
		layer.alert($.i18n.prop("messageAlert2"));
		return;
	}
	if('' == msgLevel){
		layer.alert($.i18n.prop("messageAlert3"));
		return;
	}
	if('' == msgContent){
		layer.alert($.i18n.prop("messageAlert4"));
		return;
	}
	if('' == receiverPerson){
		layer.alert($.i18n.prop("messageAlert5"));
		return;
	}
    $.ajax({
				url : 'sysMessageController/modifyMsg.do',
				data : {
					'msgTittle' : msgTittle,
					'msgType' : msgType,
					'msgLevel' : msgLevel,
					'msgContent' : msgContent,
					'id' : id,
					'receiverPerson' : JSON.stringify(receiverPerson)
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if(null == json ){
					  layer.alert("数据异常，数据库找不到对应的该条记录！");
					}else{
					  for (var x = 0; x < json.length; x++) {
						  sendWebsocket(json[x]);
					  }
					  layer.alert($.i18n.prop("alertMsg1"));
					}
					$(".message-edit-page").hide();
					$("input[name='msgTittleUpdate']").val('');
					$("select[name='msgTypeUpdate']").val('');
					$("select[name='msgLevelUpdate']").val('');
					$("input[name='msgContentUpdate']").val('');
					$("select[name='receiverPersonUpdate']").val('');
					initSysMessage();//未读消息列表刷新
					initsendMsgpage();//消息发布列表刷新
				},
				error : function() {
					layer.alert("数据请求异常，请联系管理员！");
				}
		})
}
// 消息发布----详细信息查看
function msgLoolDialog(id) {
	$.ajax({
				url : 'sysMessageController/updateSendMsgBefore.do',
				data : 'id=' + id,
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (null != json) {
						var html = "";
						var ul = json.receiverList;
						var msg = json.message;
						$("input[name='msgTittleLook']").val(msg.msgTittle);
						$("select[name='msgTypeLook']").val(msg.msgType);
						$("select[name='msgLevelLook']").val(msg.msgLevel);
						$("input[name='msgContentLook']").val(msg.msgContent);
						var checkNum = $("#userListLook")
								.find("input:checkbox");
						for (var n = 0; n < checkNum.length; n++) {
							var value = $(checkNum[n]).val();
							var result = false;
							for (var i = 0; i < ul.length; i++) {
								if (value == ul[i].receiverPerson) {
									result = true;
								}
							}
							if (result == true) {
								checkNum[n].checked = true;
							} else {
								checkNum[n].checked = false;
							}
							result = false;
						}
						$(".message-more-page").show();
					} else {
						layer.alert("数据库找到不对相对应的数据！");
					}
				},
				error : function() {
					layer.alert("数据请求异常，请联系管理员！");
				}
			})
}
// 消息发布----删除
function deleteMsg(id){
  loadProperties();// 国际化
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
		}, function() {
		$.ajax({
					url : 'sysMessageController/deleteMessage.do',
					data : 'id=' + id,
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert($.i18n.prop("alertMsg3"));
						initsendMsgpage();// 消息发布列表刷新
					},
					error : function() {
						layer.alert($.i18n.prop("alertError"));
					}
				})
	});
}
//日期格式转换
function toDate(v) {
    	var date = new Date();
    	date.setTime(v);
    	var y = date.getFullYear();
    	var m = date.getMonth()+1;
    	m = m<10?'0'+m:m;
    	var d = date.getDate();
    	d = d<10?("0"+d):d;
    	var h = date.getHours();
    	h = h<10?("0"+h):h;
    	var M = date.getMinutes();
    	M = M<10?("0"+M):M;
    	var s = date.getSeconds();
    	s = s<10?("0"+s):s;
    	var str = y+"-"+m+"-"+d+" "+h+":"+M+":"+s;
    	return str;
  }