// 流程接收人加载
function initProcessReceivers() {
	$.ajax({
		url : 'user/selectAllUserToSend.do',
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			var list = json.userList
			var html = "";
			if (null != list && list.length > 0) {
				var line = Math.ceil(list.length / 2);// 行数
				var array = new Array();
				var first = true;// 是否是第一次进入循环
				for (var x = 0; x < list.length; x++) {
					array.push(list[x]);
				}
				for (var s = 0; s < line; s++) {
					var num = 0;
					if (first != true) {
						array.shift();
						array.shift();
					}
					if (array.length > 0) {
						html += '<tr>';
						for (var i = 0; i < array.length; i++) {
							var user = array[i];
							if (null == user) {
								break;
							}
							html += '<td><input type="checkbox" class="" name="selectReceiverToAnd" value="'
									+ user.loginName + '"/></td>';
							html += '<td><span>' + user.userName + '('
									+ user.loginName + ')' + '</span></td>';
							num++;
							first = false;
							if (num == 2) {
								num == 0;
								break;
							}
						}
						html += '</tr>';
					}
				}
			}
			$("#userListAdd").html(html);
			$(".message-add-page-end").show();
		},
		error : function() {
			layer.alert("数据请求异常，请联系管理员！");
		}
	})
}
// 提交审批------保存
function saveProcessMsg() {
	loadProperties();// 国际化
	var approveType = $("#appraveType").val();
	var ids = new Array;
	$("input[name='partCheck']:checkbox:checked").each(function() {
				ids.push($(this).val());
			});
	var receiverPerson = "";
	var checkNum = $("#userListAdd").find("input:checkbox");
	for (var n = 0; n < checkNum.length; n++) {
		if (checkNum[n].checked == true) {
			receiverPerson = receiverPerson + $(checkNum[n]).val() + ","
		}
	}
	if (receiverPerson == "") {
		layer.alert($.i18n.prop("processAlert2"));
		return;
	}
	receiverPerson = receiverPerson.substring(0, receiverPerson.length - 1);
	$.ajax({
				url : 'WorkflowApplyFormController/batchSubmitApproval.do',
				data : {
					'approveType' : approveType,
					'formType' : "0",// 元器件申请
					'ids' : JSON.stringify(ids),
					'receiverPerson' : receiverPerson
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					layer.alert(json.message, {}, function() {
								location.reload();
							});
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"));
				}
			})
}