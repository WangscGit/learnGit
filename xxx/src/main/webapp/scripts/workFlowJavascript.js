/** 页面初始化 **/
function initHtml(pageNo) {
	 loadProperties();//国际化
	if (isNaN(pageNo)) {
		pageNo = 0;
	}
	var searchMsg = $("#searchMsg").val();
	var addBasic = '1';
	pageNo = parseInt(addBasic) + parseInt(pageNo);
	$.ajax({
		url : 'WorkflowMainsController/selectBasicDeploymentProcess.do',
		dataType : 'json',
		cache : false,
		data : {
			"pageNo" : pageNo,
			'searchMsg' : searchMsg
		},
		success : function(json) {
			var html = '';
			if (null == json || "" == json) {
				html += '<tr>';
				html += '<td colspan="10" align="center">'+$.i18n.prop("noData")+'</td>';
				html += '</tr>';
			} else {
				var count = json.coun;
				var pageNo = json.pageNo;
				for (var i = 0; i < json.list.length; i++) {
					var addBasic = '1';
					var addBefore = i + '';
					var addAfter = parseInt(addBasic) + parseInt(addBefore);
					var categorySign = json.list[i].categorySign == 'partProcess'?'0':json.list[i].categorySign;
					html += '<tr>';
					html += '<td>';
					html += '<input type="checkbox" name="checkOne" value="'
							+ json.list[i].id + '" onclick="checkOne('
							+ json.list[i].id + ')">';
					html += '</td>';
					html += '<td>' + addAfter + '</td>';
					html += '<td>' + json.list[i].processName + '</td>';
					html += '<td>' + json.list[i].processKey + '</td>';
					var type = json.list[i].cdefine3;
					html += '<td>' + type + '</td>';
					html += '<td>' + json.list[i].processCreatePerson + '</td>';
					html += '<td>' + json.list[i].processCreateTime + '</td>';
					// html +='<td>'+json.list[i].processState+'</td>';
					html += '<td>' + json.list[i].processVersion + '</td>';
					html += '<td>';
					// "javascript:"
					// onclick="window.open("/cms_cloudy/pages/workflowPage/cms-flow-setting.jsp","newwindow","height=768,width=1366,scrollbars=yes,status
					// =yes")
					// html +='<a
					// href="javascript:startProcess(\''+json.list[i].processKey+'\','+json.list[i].id+')"
					// class="btn btn-xs flow-design-btn-begin green"><span
					// class="glyphicon glyphicon-play-circle"></span>启动</a>';
					// <a href=""javascript:" onclick="window.open('<%=path
					// %>/pages/workflowPage/cms-flow-setting.jsp','newwindow','height=768,width=1366,scrollbars=yes,status
					// =yes')" class="btn btn-info btn-xs
					// flow-design-btn-set"><span class="glyphicon
					// glyphicon-wrench"></span> 设置</a>
					// html +='<a
					// href="javascript:goPage('+json[i].deploymentId+')"
					// class="btn btn-info btn-xs flow-design-btn-set""><span
					// class="glyphicon glyphicon-wrench"></span>设置</a>';
					var base64 = new Base64();
					html += '<a href="pages/workflowPage/cms-flow-setting.jsp?processDefinitionId='
							+ json.list[i].processDefinitionId
							+ '" class="btn btn-xs btn-danger flow-design-btn-set""><span class="glyphicon glyphicon-wrench"></span> '+$.i18n.prop("shezhi")+'</a>';
					html += '<a class="btn btn-xs btn-black flow-design-btn-design" name="copyDes" href="modeler.html?modelId='
							+ json.list[i].modelId
							+ '&formType='+json.list[i].processKey+'&presName='+base64.encode(json.list[i].processName)+'"><span class="glyphicon glyphicon-pencil"></span> '+$.i18n.prop("DesignBtn")+'</a>';
					var formTypes = null == json.list[i].cdefine3 || ""==json.list[i].cdefine3 ? "":json.list[i].cdefine3;
					html += '<a class="btn btn-xs btn-danger flow-design-btn-delete" name="processDel" href="javascript:deleteProcessChart(' + json.list[i].deploymentId
							+ ",'" + json.list[i].id + "','" + json.list[i].processKey + "'"
							+ ')" ><span class="glyphicon glyphicon-remove"></span> '+$.i18n.prop("deleteBtn")+'</a>';
					html += '<a class="btn btn-xs flow-design-btn-cancel  btn-black" name="processClr" onclick="cleanData('+json.list[i].id+',\''+json.list[i].processKey+'\')"><span class="glyphicon glyphicon-refresh"></span> '+$.i18n.prop("clearData")+'</a>';
					html+=categorySign == "0" ?'<a class="btn btn-xs btn-danger flow-design-btn-peizhi" onclick="showProcessConfigDia(\''+json.list[i].processDefinitionId+'\');"><span class="glyphicon glyphicon-refresh"></span> '+$.i18n.prop("sxpz")+'</a>':'';
					html += '</td>';
					html += '</tr>';
				}
				if ($("#partDataPage").html().length == '') {
					$("#partDataPage").pagination(count, {
								items_per_page : 5,
								num_edge_entries : pageNo,
								num_display_entries : 8,
								callback : function(pageNo, panel) {
									if (count == null) {
										initHtml(pageNo);
									}
								},
								link_to : "javascript:void(0);"
							});
				}
			}
			$("#processBasicList").html(html);
			count = null;
			processDesignRight();//流程设计权限加载
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
/** 流程页面树结构初始化* */
function flowTree() {
	var setting = {
		view : {
			// removeHoverDom: removeHoverDom,
			showIcon : false,
			showLine : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : zTreeOnClick
			// 点击某一个节点触发
		}
	};
	var zNodes = [{
				id : 1,
				pId : 0,
				name : (en=='en'?'Process center':'流程中心')
			},// designCenter
			{
				id : 10,
				pId : 1,
				name : (en=='en'?'process category':'流程类别')
			}, 
			{
				id : 11,
				pId : 1,
				name : (en=='en'?'process design':'流程设计')
			}, {
				id : 12,
				pId : 1,
				name : (en=='en'?'Process management':'流程管理')
			}, {
				id : 13,
				pId : 1,
				name : (en=='en'?'Process monitoring':'流程监控')
			}, {
				id : 14,
				pId : 1,
				name : (en=='en'?'Process task':'流程任务')
			}, {
				id : 141,
				pId : 14,
				name : (en=='en'?'Pending task':'待办任务')
			}, {
				id : 142,
				pId : 14,
				name : (en=='en'?'Do the task':'已办任务')
			}];
	$.fn.zTree.init($("#flowTree"), setting, zNodes);// 加载树结构
	var treeObj = $.fn.zTree.getZTreeObj("flowTree");
	treeObj.expandAll(true); // 默认展开全部
}
/** 点击某一个节点**/
function zTreeOnClick(event, treeId, treeNode, clickFlag) {
	if (treeNode.id == 12) {// 流程管理
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-manage.jsp";
	} else if (treeNode.id == 13) {// 流程监控
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-monitoring.jsp";
	} else if (treeNode.id == 14) {// 流程任务
		// window.location.href =
		// "/cms_cloudy/pages/workflowPage/cms-flow-daiBan.jsp";
	} else if (treeNode.id == 141) {// 待办任务
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-daiBan.jsp";
	} else if (treeNode.id == 142) {// 已办任务
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-yiBan.jsp";
	} else if (treeNode.id == 11) {// 流程设计
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
	}else if (treeNode.id == 10) {// 流程类别
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-category.jsp";
	} else if (treeNode.id == 1) {// 流程中心
		// window.location.href = "/cms_cloudy/pages/workflowPage/cms-flow.jsp";
	}
}
/** 启动流程实例* */
function startProcess(processKey, id) {
	$.ajax({
				url : 'WorkflowMainsController/startProcessInstance.do',
				data : {
					'processKey' : processKey,
					'id' : id
				},
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (json.result == "success") {
						layer.alert('流程启动成功！');
					} else if (json.result == "fail") {
						layer.alert('流程启动失败！');
					}
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}
/** 跳转到流程设计界面* */
function goPage(value) {
	window.open('/cms_cloudy/pages/workflowPage/cms-flow-setting.jsp?deployId='
					+ value + '', 'newwindow',
			'height=900, width=1600, scrollbars=yes,status=yes');
}
/** 创建流程模型并跳转到流程设计界面* */
function createmodel() {
	 loadProperties();//国际化
	var workFlowModelName = $("#workFlowModelName").val();
	var workFlowDescription = $("#workFlowDescription").val();
	var formType = $("#formType").val();
	var key = "";
	if (null == workFlowModelName || "" == workFlowModelName) {
		layer.alert($.i18n.prop("processAlert4"));
		return;
	}
	if (null == formType || "" == formType) {
		layer.alert($.i18n.prop("processAlert5"));
		return;
	}
	if (null == workFlowDescription || "" == workFlowDescription) {
		workFlowDescription = "Description...";
	}
	if("0" == formType){
		key = "partProcess";
	}else{
	   key = formType;
	}
	var b = new Base64();
	var str=b.encode(workFlowModelName);////流程名称加密传输
	// 添加流程模型
	window.location.href = getContextPathForWS()+'/model/create?workFlowModelName='
			+ str + '&workFlowDescription=' + workFlowDescription
			+ '&key=' +key+ '';
}
// 转办弹窗确定按钮
function makeZhuanban() {
	var processInstanceId = GetQueryString('processInstanceId');// 流程主表ID
	var detailId = GetQueryString('id');//任务id
	var processAssigns = $("#loginAgreeSelectUser1").html();// 执行人
	$.ajax({
		url : 'WorkflowMainsController/zhuanbanProcessTask.do',
		type : 'post',
		data : {
			'id' : detailId,
			'processInstanceId' : processInstanceId,
			'processAssigns' : processAssigns
		},
		dataType : 'json',
		cache : false,
		success : function(json) {
			if (json.result == "success") {
				layer.alert('操作成功!', {
						}, function() {
							window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-daiBan.jsp";
						});
			}

		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
// 添加同意操作 state: agree同意按钮 disagree打回按钮
function makeShenPiAgreeBox(state) {
	var processInstanceId = GetQueryString('processInstanceId');// 流程定义ID
	var detailId = GetQueryString('id');//任务id
	var jumpType = $("input:radio[name='jumpType']:checked").val();// 跳转类型
	var nodeOption = "";
	var typeOption=$("#typeOption1").val();//人员审批关系
	var processAssigns = $("#loginAgreeSelectUser").html();// 执行人，多人用逗号隔开
	var aproveAdvice = $("input[name='aproveAdvice']").val();// 审批意见
	if(state=='disagree'){
		aproveAdvice=$("input[name='disAgreeAdvice']").val();
	}
	if (state=='agree'&&'' == processAssigns) {
		layer.alert("请选择执行人！");
		return;
	}
	if(jumpType=='freeStyle'){
		nodeOption=$("#nodeOption").val();// 目标节点
	}
	$.ajax({
				url : 'WorkflowMainsController/agreeProcessTask.do',
				type : 'post',
				data : {
					'jumpType' : jumpType,
					'nodeOption' : nodeOption,
					'typeOption':typeOption,
					'id' : detailId,
					'processInstanceId' : processInstanceId,
					'aproveAdvice' : aproveAdvice,
					'processAssigns' : processAssigns,
					'state':state
				},
				dataType : 'json',
				cache : false,
				success : function(json) {
					if (json.result == "success") {
//						layer.alert('操作成功!', {
//							skin : 'layui-layer-molv' // 样式类名
//						}, function() {
							window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-daiBan.jsp";
//						});
					}
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}
/** 关闭审批通过DIV* */
function closeShenPiAgreeBox() {
	$('.shenPi-agree-box').hide();
}
/** 关闭转办通过DIV* */
function closeZhuanbanAgreeBox() {
	$('.shenPi-zhuanban-box').hide();
}

// 完成当前审批人任务
//function completeMyProcessTask(taskId, detailId) {
//	$.ajax({
//		url : 'WorkflowMainsController/completeMyProcessTask.do',
//		data : {
//			'taskId' : taskId,
//			'detailId' : detailId
//		},
//		type : 'post',
//		dataType : 'json',
//		cache : false,
//		success : function(json) {
//			if (json.result == "success") {
//				layer.alert('操作成功!', {
//							skin : 'layui-layer-molv' // 样式类名
//						}, function() {
//							window.location.href = "pages/workflowPage/cms-flow-daiBan.jsp";
//						});
//			}
//		},
//		error : function() {
//			layer.alert("数据连接异常,请联系管理员！");
//		}
//	});
//}
// 审批界面流程图按钮
$("body").on("click", ".shenpi-flow-btn", function() {
	$('#processPng img').remove();
	var xhr = new XMLHttpRequest();
	xhr.open("get",
			"flowPng/processTracking.do?taskId=" + GetQueryString('id'), true);
	xhr.responseType = "blob";
	xhr.onload = function() {
		if (this.status == 200) {
			var blob = this.response;
			var img = document.createElement("img");
			img.onload = function(e) {
				window.URL.revokeObjectURL(img.src);
			};
			img.src = window.URL.createObjectURL(blob);
			$("#pngDiv").html(img);
			$(".shenPi-flow-box").show();
		}
	};
	xhr.send();
});

//单选的用户弹窗
function initOneUserList(groupId, pageNo,spanId) {
	if(spanId!=undefined&&spanId!=''){
		userSpanId=spanId;
	}
	var loginName = $("#searchText").val();
	if (typeof(groupId) == "undefined" || '-1' == groupId) {
		groupId = "";
	}
	if (isNaN(pageNo)||pageNo=='') {
		pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic) + parseInt(pageNo);
	$.ajax({
				url : 'user/selectAllUserForSelect.do',
				data : {
					'groupId' : groupId,
					'loginName' : loginName,
					'pageNo' : pageNo
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var html = '';
					if (null == json.list || "" == json.list) {
						html += '<tr>';
						html += '<td colspan="3" align="center">--没有数据--</td>';
						html += '</tr>';
						$('#show-user').html(html);
						$(".shenPi-agree-box-chooseUser").show();
					} else {
						var list = json.list;
						var count = json.count;
						var pageNo = json.pageNo;
						var pageSize = json.pageSize;
						for (var i = 0; i < list.length; i++) {
							var user = list[i];
							html += '<tr>';
							html += '<td class="choose-table-input">'
									+ '<input type="radio" onclick="checkOneUser('
									+ user.userId
									+ ')" name="checkOneUser" value="'
									+ user.loginName + '">' + '</td>';
							html += '<td>' + user.userName + '</td>';
							html += '<td>' + user.loginName + '</td>';
							html += '</tr>';
						}
						// 分页插件
						if ($("#Pagination").html().length == '') {
							$("#Pagination").pagination(count, {
										items_per_page : pageSize,
										num_edge_entries : pageNo,
										num_display_entries : 8,
										callback : function(pageNo, panel) {
											if (count == null) {
												initOneUserList(groupId, pageNo);
											}
										},
										link_to : "javascript:void(0);"
									});
						}
						list = null;
						$('#show-user').html(html);
						count = null;
						$(".shenPi-agree-box-chooseUser").show();
					}
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}

// 选择成员组树结构
function initOnegroupUserTree() {
	$.ajax({
				url : 'HrGroupController/selectAllGroupTree.do',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var list = json;
					var settingUser = {
						view : {
							showIcon : false
						},
						data : {
							simpleData : {
								enable : true
							}
						},
						callback : {
							onClick : oneUserTreeOnClick
						}
					};
					if (null != list && "" != list) {
						$.fn.zTree.init($("#userTree"), settingUser, list);// 加载树结构
						var treeObj = $.fn.zTree.getZTreeObj("userTree");
						treeObj.expandAll(true);
					} else {
						var zNodesUser = [{
									id : -1,
									pId : 0,
									name : "Design center",
									open : true
								}];
						$.fn.zTree.init($("#userTree"), settingUser, zNodesUser);// 加载树结构
					}
				},
				error : function() {
				}
			})
}
// 选择成员组树结构
function initccgroupUserTree() {
	$.ajax({
				url : 'HrGroupController/selectAllGroupTree.do',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var list = json;
					var settingUser = {
						view : {
							showIcon : false
						},
						data : {
							simpleData : {
								enable : true
							}
						},
						callback : {
							onClick : ccuserTreeOnClick
						}
					};
					if (null != list && "" != list) {
						$.fn.zTree.init($("#userCCTree"), settingUser, list);// 加载树结构
						var treeObjCC = $.fn.zTree.getZTreeObj("userCCTree");
						treeObjCC.expandAll(true);
					} else {
						var zNodesUser = [{
									id : -1,
									pId : 0,
									name : "Design center",
									open : true
								}];
						$.fn.zTree
								.init($("#userCCTree"), settingUser, zNodesUser);// 加载树结构
					}
				},
				error : function() {
				}
			})
}

/** 点击user树某一个节点* */
function oneUserTreeOnClick(event, treeId, treeNode, clickFlag) {
       $("#searchText").val('');
	   $("#Pagination").html('');
	   initOneUserList(treeNode.id);
}

//抄送人员树加载
function ccuserTreeOnClick(event, treeId, treeNode, clickFlag){
      $("#searchCCText").val('');
	  $("#Paginationcc").html('');
	  selectCCPerson(treeNode.id);
}
/** 用户复选框全选反选操作* */
function checkAlluser() {
	var CheckBox = document.getElementsByName('checkOneUser');
	if ($("#checkAlluser").prop('checked')) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked == false) {
				CheckBox[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked = true) {
				CheckBox[i].checked = false;
			}
		}
	}
}
/** 用户复选框全选反选操作* */
function checkAllusercc() {
	var CheckBox = document.getElementsByName('checkOneUsercc');
	if ($("#checkAllusercc").prop('checked')) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked == false) {
				CheckBox[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked = true) {
				CheckBox[i].checked = false;
			}
		}
	}
}
/** 用户复选框单个选择* */
function checkOneUser(id) {
	var CheckBox = document.getElementsByName('checkOneUser');
	var num = 0;
	if (CheckBox.length > 0) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked) {
				num++;
			}
		}
	}
	if (num == CheckBox.length) {
		$("#checkAlluser").prop('checked', true);
	} else {
		$("#checkAlluser").prop('checked', false);
	}
}
/** 用户复选框单个选择* */
function checkOneUsercc(id) {
	var CheckBox = document.getElementsByName('checkOneUsercc');
	var num = 0;
	if (CheckBox.length > 0) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked) {
				num++;
			}
		}
	}
	if (num == CheckBox.length) {
		$("#checkAllusercc").prop('checked', true);
	} else {
		$("#checkAllusercc").prop('checked', false);
	}
}
// 条件筛选执行人
function searchUsers() {
	if("ccSelectUser" == userSpanId ){
		$("#Paginationcc").html('');
	     selectCCPerson();
	}else{
	    $("#Pagination").html('');
	      initUserList();
	}
}

// 条件筛选执行人
function searchOneUser() {
	$("#Pagination").html('');
	initOneUserList();
}

// 将单选弹窗选择的人员显示到div上
function processOneSelectUser() {
	var ids = "";
	ids=$("input[name='checkOneUser']:radio:checked").val();
	if (ids == "") {
		layer.alert("请选择执行人！");
		return;
	}
	var name=$("input[name='checkOneUser']:radio:checked").parent().next().html();
	if(userSpanId==''){
		layer.alert("系统错误！");
		return;
	}
	
	$("#"+userSpanId).html(ids);
	$("#"+userSpanId+"Show").html(name);
	$(".shenPi-agree-box-chooseUser").hide();
}
// 取消流程执行人选择
function closeProcessSelectUsers() {
	$(".shenPi-agree-box-chooseUser").hide();
}
//// 已办页面数据初始化
//function initYibanProcess() {
//	$.ajax({
//		url : 'WorkflowMainsController/selectDoneProcessTask.do',
//		dataType : 'json',
//		cache : false,
//		success : function(json) {
//			var html = '';
//			if (null == json || "" == json) {
//				html += '<tr>';
//				html += '<td colspan="10" align="center">--没有数据--</td>';
//				html += '</tr>';
//			} else {
//				for (var i = 0; i < json.length; i++) {
//					var addBasic = '1';
//					var addBefore = i + '';
//					var addAfter = parseInt(addBasic) + parseInt(addBefore);
//					html += '<tr>';
//					html += '<td>' + addAfter + '</td>';
//					html += '<td>' + json[i].processName + '</td>';
//					html += '<td>' + json[i].processKey + '</td>';
//					html += '<td>' + json[i].processCreatePerson + '</td>';// 流程发起人
//					html += '<td>' + json[i].processNodeTaskname + '</td>';// 流程节点任务
//					html += '<td>' + json[i].processTaskStarttime + '</td>';// 流程发起时间
//					html += '<td>' + json[i].processTaskExpirtime + '</td>';// 任务到期时间
//					html += '<td>' + json[i].processTaskState + '</td>';// 流程待办类型
//					html += '<td></td>';// 期限状态
//					html += '<td>';
//					html += '<a class="btn btn-xs flow-yiban-more green"><span class="glyphicon glyphicon-star"></span>  详情</a>';
//					html += '<a class="btn btn-warning btn-xs flow-yiban-return"><span class="glyphicon glyphicon-share-alt"></span>  撤回</a>';
//					html += '</td>';
//					html += '</tr>';
//				}
//			}
//			$("#processYiban").html(html);
//		},
//		error : function() {
//			layer.alert("数据连接异常,请联系管理员！");
//		}
//	});
//}

function buildPartNumber(){
	$("[name='PartCode']").val(rulePartNumber1);
}
var rulePartNumber1="";
// 初始化元器件申请界面
function initaddPage() {
//	var tempPartMark = getUrlParam("tempPartMark");// 目录内外
	var name = b.decode(getUrlParam("name"));// 元器件名称
//	tempPartMark = $.trim(tempPartMark);
	// 获取审批节点配置信息
	$.ajax({
		url : "ProcessConfigureController/getProcessConfigure.do",
		data : {
			'taskKey' : 'startEvent'
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json1) {
			var processConfigure = json1.processConfigure;
			if(null == processConfigure || processConfigure == "0"){
				layer.alert('请到流程设计界面配置属性信息！', {
                }, function(){
            		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                });
				return;
			}
			if('0' == processConfigure.normalAttrs && '0' == processConfigure.qualityAttrs && '0' == processConfigure.designAttrs && '0' == processConfigure.purchaseAttrs){
				 layer.alert('请到流程设计界面配置属性信息！', {
                 }, function(){
             		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                 });
				 return;
			}
			if(json1.pList!=undefined&&json1.pList.length!=0){
				var pList=json1.pList;
				$("#typeOption").val(pList[0].utType);
				var userHtml="";
				var uNameHtml="";
				for(var j=0;j<pList.length;j++){
					userHtml+=pList[j].loginName+",";
					uNameHtml+=pList[j].userName+",";
				}
				userHtml=userHtml.substring(0,userHtml.length-1);
				uNameHtml=uNameHtml.substring(0,uNameHtml.length-1);
				$("#taskPerson").val(userHtml);
				$("#taskPersonName").val(uNameHtml);
			}
			if (processConfigure != '0') {
				$.ajax({
					url : 'partComponent/workfolwPartaddPageField.do',
					type : 'post',
					dataType : 'json',
					cache : false,
					data : {
//						"tempPartMark" : tempPartMark,
						"partType" : name
					},
					success : function(json) {
						$("#Part_Type_value").children("[name='Part_Type']")
								.val(name);
//						if (tempPartMark == false || tempPartMark == 'false') {
//							$("[name='DirInOROut']").val('目录内');
//						} else {
//							$("[name='DirInOROut']").val('目录外');
//						}
//						$("[name='TempPartMark']").val(tempPartMark);
//						$("[name='PartNumber']").val(json.partNumber);
						rulePartNumber1=json.partNumber;
						var partData=new Object();
						partData.PartCode=json.partNumber;
						partData.Part_Type=name;
						// 动态生成添加字段的显示名
//						var normalAttrsStr = "";// 基本属性
//						var qualityAttrsStr = "";// 质量属性
//						var designAttrsStr = "";// 设计属性
//						var purchaseAttrsStr = "";// 采购属性
//						var normalRP = 4;// 控制属性跨列的变量
//						var qualityRP = 4;// 控制属性跨列的变量
//						var designRP = 5;// 控制属性跨列的变量
//						var purchaseRP = 4;// 控制属性跨列的变量
//						var fixedInsertList = json.fixedInsertList;
//						// 固定字段的显示名
//						for (var i = 0; i < fixedInsertList.length; i++) {
//							$("#" + fixedInsertList[i].fieldName)
//									.html(fixedInsertList[i].showName);
//							if (fixedInsertList[i].fieldName == "PartNumber"
//									|| fixedInsertList[i].fieldName == "ITEM"
//									|| fixedInsertList[i].fieldName == "Datesheet") {
//								$("#" + fixedInsertList[i].fieldName)
//										.prepend("<span style=\"color:red;\">*</span>");
//							}
//							// 字段为下拉框时
//							if (fixedInsertList[i].dataType == 'selectList') {
//								var selectHtml = "<select class=\"form-control saveValue\" name=\""
//										+ fixedInsertList[i].fieldName + "\" >";
//								for (var j = 0; j < fixedInsertList[i].fsList.length; j++) {
//									selectHtml += "<option value=\""
//											+ fixedInsertList[i].fsList[j].value
//											+ "\">"
//											+ fixedInsertList[i].fsList[j].name
//											+ "</option>";
//								}
//								selectHtml += "</select>";
//								$("#" + fixedInsertList[i].fieldName + "_value")
//										.html(selectHtml);
//							}
//							if (fixedInsertList[i].fieldName == 'Datesheet') {
//								var select = en == 'zh' ? "选择" : "Select";
//								var dh = "<a id=\""
//										+ fixedInsertList[i].fieldName
//										+ "1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:selectDatesheet()\">"
//										+ select
//										+ "</a><span id=\"addDatesheet\" class=\"DatesheetShow\"></span>";
//								$("#" + fixedInsertList[i].fieldName + "_value")
//										.html(dh);
//							}
//							if (fixedInsertList[i].fieldName == 'ITEM') {
//								var itemLang = en == 'zh' ? "选择" : "Select";
//								var itemHtml = '';
//								itemHtml += '<input class="form-control saveValue symbol" name="ITEM" />';
//								itemHtml += '<a class="btn btn-xs btn-danger symbolBtn" onclick="showpartDataDiaLog()">'
//										+ itemLang + '</a>';
//								$("#" + fixedInsertList[i].fieldName + "_value")
//										.html(itemHtml);
//							}
//							if (fixedInsertList[i].fieldName == 'PartNumber') {
//								var select = en == 'zh' ? "生成" : "build";
//								var dh = "<a id=\""
//										+ fixedInsertList[i].fieldName
//										+ "1\"  class=\"btn btn-xs btn-danger symbolBtn\" onclick=\"buildPartNumber();\">"
//										+ select + "</a>";
//								$("#" + fixedInsertList[i].fieldName + "_value")
//										.append(dh);
//							}
//							/***************************************************
//							 * if(fixedInsertList[i].fieldName=='Part_Type'){
//							 * var select=en=='zh'? "选择":"Select"; var dh="<a
//							 * id=\""+fixedInsertList[i].fieldName+"1\"
//							 * class=\"btn btn-xs btn-danger chooseName-btn
//							 * left\">"+select+"</a><input
//							 * class=\"form-control chooseName right\"
//							 * name=\"Part_Type\" readonly=\"readonly\" /><span
//							 * id=\"addDatesheet\" class=\"DatesheetShow\"></span>";
//							 * $("#"+fixedInsertList[i].fieldName+"_value").html(dh); }
//							 **************************************************/
//						}
//						// 生成其他其他添加字段
//						for (var i = 0; i < applyList.length; i++) {
//							// 先判断是下拉还是输入框
//							var str = "<select class=\"form-control saveValue\" name=\""
//									+ applyList[i].fieldName + "\" >";
//							if (applyList[i].dataType == 'selectList') {
//								for (var j = 0; j < applyList[i].fsList.length; j++) {
//									str += "<option value=\""
//											+ applyList[i].fsList[j].value
//											+ "\">"
//											+ applyList[i].fsList[j].name
//											+ "</option>";
//								}
//								str += "</select>";
//							} else {
//								if (',welding_library,package_symbols,step_model,'
//										.indexOf("," + applyList[i].fieldName
//												+ ",") != -1) {
//									var select = en == 'zh' ? "选择" : "Select";
//									str = "<a id=\""
//											+ applyList[i].fieldName
//											+ "1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:initUploadDia('add"
//											+ applyList[i].fieldName
//											+ "')\">"
//											+ select
//											+ "</a><span id=\"add"
//											+ applyList[i].fieldName
//											+ "\" class=\"DatesheetShow\">"
//											+ "</span>";
//								} else {
//									str = "<input class=\"form-control saveValue\" name=\""
//											+ applyList[i].fieldName + "\" />";
//								}
//							}
//                            if(applyList[i].fieldName == "design_tool"){
//                               var str = "<select class=\"form-control saveValue\" onchange=\"controlPcbFiled()\"name=\""
//									+ applyList[i].fieldName + "\" >";
//								for (var j = 0; j < applyList[i].fsList.length; j++) {
//									str += "<option value=\""
//											+ applyList[i].fsList[j].value
//											+ "\">"
//											+ applyList[i].fsList[j].name
//											+ "</option>";
//								}
//								str += "</select>";
//                            }
//							if (applyList[i].type == 1) {// 基本属性
//								normalAttrsStr += "<tr><td class=\"sameLength\" >"
//										+ applyList[i].showName
//										+ "</td><td colspan=\"3\" class=\"dynamicFile\">"//动态字段标识dynamicFile
//										+ str
//										+ "</td></tr>";
//								normalRP++;
//								continue;
//							}
//							if (applyList[i].type == 2) {// 质量属性
//								qualityAttrsStr += "<tr><td class=\"sameLength\" >"
//										+ applyList[i].showName
//										+ "</td><td colspan=\"3\" class=\"dynamicFile\">"
//										+ str
//										+ "</td></tr>";
//								qualityRP++;
//								continue;
//							}
//							if (applyList[i].type == 3) {// 设计属性
//								designAttrsStr += "<tr><td class=\"sameLength\" >"
//										+ applyList[i].showName
//										+ "</td><td colspan=\"3\" class=\"dynamicFile\">"
//										+ str
//										+ "</td></tr>";
//								designRP++;
//								continue;
//							}
//							if (applyList[i].type == 4) {// 采购属性
//								purchaseAttrsStr += "<tr><td class=\"sameLength\" >"
//										+ applyList[i].showName
//										+ "</td><td colspan=\"3\" class=\"dynamicFile\">"
//										+ str
//										+ "</td></tr>";
//								purchaseRP++;
//								continue;
//							}
//						}
						// 生成其他其他添加字段
						var insertList = json.applyList;
						var normalList=new Array();// 基本属性集合
						var qualityList=new Array();// 质量属性集合
						var designList=new Array();// 设计属性集合
						var purchaseList=new Array();// 采购属性集合
						for(var i=0;i<insertList.length;i++){
							if(insertList[i].type==1){// 基本属性
								normalList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==2){// 质量属性
								qualityList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==3){// 设计属性
								designList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==4){// 采购属性
								purchaseList.push(insertList[i]);
								continue;
							}
						}
						var normalObj=getApplyFieldTr(normalList,partData);
						var qualityObj=getApplyFieldTr(qualityList,partData);
						var designObj=getApplyFieldTr(designList,partData);
						var purchaseObj=getApplyFieldTr(purchaseList,partData);
						var normalAttrsStr=normalObj.str;// 基本属性
						var qualityAttrsStr=qualityObj.str;// 质量属性
						var designAttrsStr=designObj.str;// 设计属性
						var purchaseAttrsStr=purchaseObj.str;// 采购属性
						var normalRP=normalObj.rp;// 控制属性跨列的变量
						var qualityRP=qualityObj.rp;// 控制属性跨列的变量
						var designRP=designObj.rp;// 控制属性跨列的变量
						var purchaseRP=purchaseObj.rp;// 控制属性跨列的变量
						// 基本属性
						if(normalAttrsStr!=''){
							normalAttrsStr=normalAttrsStr.substring(4,normalAttrsStr.length);
							normalAttrsStr='<tr><td rowspan="'+normalRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("jbsx")+'</td>'+normalAttrsStr;
						}
						// 质量属性
						if(qualityAttrsStr!=''){
							qualityAttrsStr=qualityAttrsStr.substring(4,qualityAttrsStr.length);
							qualityAttrsStr='<tr><td rowspan="'+qualityRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("zlsx")+'</td>'+qualityAttrsStr;
						}
						// 设计属性
						if(designAttrsStr!=''){
							designAttrsStr=designAttrsStr.substring(4,designAttrsStr.length);
							designAttrsStr='<tr><td rowspan="'+designRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("sjsx")+'</td>'+designAttrsStr;
						}
						// 采购属性
						if(purchaseAttrsStr!=''){
							purchaseAttrsStr=purchaseAttrsStr.substring(4,purchaseAttrsStr.length);
							purchaseAttrsStr='<tr><td rowspan="'+purchaseRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("cgsx")+'</td>'+purchaseAttrsStr;
						}
						if(processConfigure.normalAttrs != '1'){
							normalAttrsStr="";
						}
						if(processConfigure.qualityAttrs != '1'){
							qualityAttrsStr="";
						}
						if(processConfigure.designAttrs != '1'){
							designAttrsStr="";
						}
						if(processConfigure.purchaseAttrs != '1'){
							purchaseAttrsStr="";
						}
						$("#partDataTbody").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
//                        uploadRights();//上传按钮权限维护
                        //controlPcbFiled();//原理图符号上传按钮控制
					},
					error : function() {
						layer.alert("数据连接异常,请联系管理员！");
					}
				});
			} else {
				$("#normalTbody").hide();
				$("#qualityTbody").hide();
				$("#designTbody").hide();
				$("#purchaseTbody").hide();
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
//生成字段tr
function getApplyFieldTr(insertList,partData,similar){
	var obj=new Object();
	var string="";
	var rp=0;
	for(var i=0;i<insertList.length;i++){
		var xh="";
		var value=partData==undefined?"":partData[insertList[i].fieldName];
		value=value==undefined?"":value;
		if(insertList[i].fieldName == "PartCode" || insertList[i].fieldName == "ITEM" || insertList[i].fieldName == "Datesheet"){
			xh="<span style=\"color:red;\">*</span>";
		}
		var str="";
		if(insertList[i].fieldName=='Datesheet'){
			var select=en=='zh'? "选择":"Select";
			str="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:selectDatesheet()\">"+select+"</a><span id=\"addDatesheet\" class=\"DatesheetShow\">"+value+"</span>";
		}
		if (partData.PartNumber == undefined||similar=="similar") {
			if (insertList[i].fieldName == 'ITEM') {
				var select = en == 'zh' ? "选择" : "Select";
				str += '<input class="form-control saveValue symbol" name="ITEM" />';
				str += '<a class="btn btn-xs btn-danger symbolBtn" onclick="showpartDataDiaLog()">'
						+ select + '</a>';
			}
		}
		if(insertList[i].fieldName=='PartCode'){
			var select=en=='zh'? "生成":"build";
			str="<input class=\"form-control saveValue symbol\" name=\"PartCode\" readonly=\"readonly\" value=\""+value+"\" />";
			str="<input class=\"form-control saveValue symbol\" name=\"PartCode\"  value=\""+value+"\" />";
			str+="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger symbolBtn\" onclick=\"buildPartNumber();\">"+select+"</a>";
			if(similar!="similar"){
				rulePartNumber1=partData==undefined?"":partData[insertList[i].fieldName];
			}
		}
		if(insertList[i].fieldName=='Part_Type'){
			var select=en=='zh'? "选择":"select";
			str="<input class=\"form-control saveValue\" name=\"Part_Type\" value=\""+value+"\" readonly=\"readonly\" />";
		}
		if(insertList[i].fieldName=='Alternative_Part'){
			var select=en=='zh'? "选择":"select";
			str+='<a class="btn btn-xs btn-danger replaceChooseBtn" onclick="showpartDataDia();">'+select+'</a><span id="Alternative_Part_span"></span>';
		}
		if(insertList[i].fieldName=='Sym_for_ADR'){
			var select=en=='zh'? "选择":"select";
			str="<input class=\"form-control saveValue symbol\" name=\"Sym_for_ADR\" />";
			str+='<a class="btn btn-xs btn-danger symbolBtn" onclick="symbolUploadBefore(\'0\')" id="Sym_for_ADR_A">'+select+'</a>';
		}
		if(insertList[i].fieldName=='Sym_for_CAP'){
			var select=en=='zh'? "选择":"select";
			str="<input class=\"form-control saveValue symbol\" name=\"Sym_for_CAP\" />";
			str+='<a class="btn btn-xs btn-danger symbolBtn" onclick="symbolUploadBefore(\'1\')" id="Sym_for_CAP_A">'+select+'</a>';
		}
		if(',welding_library,package_symbols,step_model,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			var select=en=='zh'? "选择":"Select";
			str="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:initUploadDia('add"+insertList[i].fieldName+"')\">"+select+"</a><span id=\"add"+insertList[i].fieldName+"\" class=\"DatesheetShow\">"+value+"</span>";
		}
		if(',shape_img,size_img,characteristic_curve_img,typical_ap_img,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			str="<div class=\"left parts-images\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"图片上传最佳尺寸【550px*320px】\">" +
					"<img src=\""+value+"\" id=\""+insertList[i].fieldName+"1\"></div><div class=\"right uplogo\"><a class=\"inputBox right\">" +
							"<img src=\"images/partsUpload.png\"/>"+
					"<input id=\""+insertList[i].fieldName+"2\" name=\""+insertList[i].fieldName+"2\" type=\"file\" class=\"uploadBtnClass\" onchange=\"limitImg('"+insertList[i].fieldName+"2','"+insertList[i].fieldName+"1', 100, 560, 320);\" /></a></div>";
		}
		if(str==""){
			// 判断是下拉还是输入框
			str="<select class=\"form-control saveValue\" name=\""+insertList[i].fieldName+"\" >";
			if(insertList[i].dataType=='selectList'){
				for(var j=0;j<insertList[i].fsList.length;j++){
					var selected="";
					if(insertList[i].fsList[j].value==value){
						selected="selected='true'";
					}
					str+="<option "+selected+" value=\""+insertList[i].fsList[j].value+"\">"+insertList[i].fsList[j].name+"</option>";
				}
				str+="</select>";
			}else{
				str="<input class=\"form-control saveValue\" value=\""+value+"\" name=\""+insertList[i].fieldName+"\" />";
			}
		}
		if(i==insertList.length-1&&rp%2==0){
			string+="<tr><td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td colspan=\"3\" >"+str+"</td></tr>";
		}else{
			if(rp%2==0){//左边
				string+="<tr>";
			}
			string+="<td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td class=\"valueLength\">"+str+"</td>";
			if(rp%2!=0){
				string+="</tr>";
			}
		}
		rp++;
	}
	obj.str=string;
	obj.rp=rp%2==0?parseInt(rp/2):(parseInt(rp/2)+1);
	return obj;
}
// 元器件申请页面的保存按钮 state 1添加 2修改 3新增并启动流程 4修改并启动流程
function savepartApplyPage(state) {
	loadProperties();
	var values = $(".saveValue");
	var partData = new Object();
	for (var i = 0; i < values.length; i++) {
		var td = values[i];
		partData[$(td).attr('name')] = $(td).val();
	}
	if ($("[name='PartCode']").val() == '') {
		layer.alert($.i18n.prop("check_partNumber"));
		return;
	}
	if ($("[name='ITEM']").val() == '') {
		layer.alert($.i18n.prop("check_model"));
		return;
	}
	if ($("#addDatesheet").html() == '') {
		layer.alert($.i18n.prop("processAlert1"));
		return;
	}
	var formType = getUrlParam("ft");
	var taskId = getUrlParam("taskId");
	var processAssignees = $("#taskPerson").val();
	var typeOption = $("#typeOption").val();
	if ("" == processAssignees || null == processAssignees) {
		layer.alert($.i18n.prop("processAlert2"));
		return;
	}
	if (state == '2' || state == '4') {
		var id = getUrlParam("partId");// id
		partData.id = id;
	}
	partData.Datesheet = $("#addDatesheet").html();
	partData.Alternative_Part=$("#Alternative_Part_span").html();
	partData.welding_library=$("#addwelding_library").html();
	partData.package_symbols=$("#addpackage_symbols").html();
	partData.step_model=$("#addstep_model").html();
	$.ajaxFileUpload({
		url : "WorkflowApplyFormController/savepartApplyPage.do",
		dataType : "json",
		secureuri : false,
		type : "post",
		fileElementId : ["shape_img2", "size_img2",
				"characteristic_curve_img2", "typical_ap_img2",
				"schematic_img2", "ens_img2"],
		data : {
			"jsonData" : JSON.stringify(partData),
			"lock" : state,
			"formType" : formType,
			"taskId" : taskId,
			"typeOption" : typeOption,
			"processAssignees" : processAssignees
		},
		success : function(json, status) {
			if (json.message == '1') {
				window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-manage.jsp";
			}
			if (json.message == '2') {
				layer.alert($.i18n.prop("check_login"));
			}
			if (json.message == '0') {
				layer.alert($.i18n.prop("onepartNumber"));
			}
			if (json.message == '3') {
				layer.alert($.i18n.prop("processAlert3"), {
                //skin: 'layui-layer-molv' //样式类名
              }, function(){
                  location.href = "pages/workflowPage/cms-flow.jsp";
              });
			}
		},
		error : function() {
		}
	});
}
// 审批页中元器件申请的保存按钮
function savepartDataFromShenpiPage() {
	loadProperties();
	var values = $(".saveValue");
	var partData = new Object();
	for (var i = 0; i < values.length; i++) {
		var td = values[i];
		partData[$(td).attr('name')] = $(td).val();
	}
	if ($("[name='PartCode']").val() == '') {
		layer.alert($.i18n.prop("check_partNumber"));
		return;
	}
	if ($("[name='ITEM']").val() == '') {
		layer.alert($.i18n.prop("check_model"));
		return;
	}
	partData.id = shenpibusId;
	//数据手册获取
	var dSheet = '';
	var dLength = $("#addDatesheet").find("a");
	dLength.each(function(i,x){
	   var ds = $(this).text();
	   if(i==dLength.length-1){
	      dSheet += ds;
	   }else{
	      dSheet += ds+',';
	   }
	})
	partData.Datesheet = dSheet;
	partData.Alternative_Part=$("#Alternative_Part_span").html();
	partData.welding_library=$("#addwelding_library").html();
	partData.package_symbols=$("#addpackage_symbols").html();
	partData.step_model=$("#addstep_model").html();
	$.ajaxFileUpload({
		url : "WorkflowApplyFormController/savepartDataFromShenpiPage.do",
		dataType : "json",
		secureuri : false,
		type : "post",
		fileElementId : ["shape_img2", "size_img2",
				"characteristic_curve_img2", "typical_ap_img2",
				"schematic_img2", "ens_img2"],
		data : {
			"jsonData" : JSON.stringify(partData),
			"lock":'2'
		},
		success : function(json, status) {
			if (json.message == '1') {
				layer.alert($.i18n.prop("alertMsg2"));
			}else if (json.message == '0'){
				layer.alert($.i18n.prop("onepartNumber"));
			}
			
		},
		error : function() {
		}
	});
}
//初始化审批页按钮
function initSpBtn(){
	var processInstanceId = GetQueryString('processInstanceId');//流程实例ID
	var taskId = GetQueryString('id');//任务id
	$.ajax({
		url : 'WorkflowMainsController/initSpBtn.do',
		dataType : 'json',
		cache : false,
		data:{"processInstanceId":processInstanceId,"taskId":taskId},
		success : function(json) {
			if(json.message=='0'){
				window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow-daiBan.jsp";
			}
			if(json.showDisagreeBtn!=0){
				$("#disagreeBtn").show();
			}
		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
//初始化元器件申请审批页
var shenpibusId="";//审批页业务id,审批页保存按钮用到
function initPartPage(){
	var processInstanceId = GetQueryString('processInstanceId');//流程实例ID
	var taskId = GetQueryString('id');//任务id
	$.ajax({
		url : 'WorkflowMainsController/initPartPage.do',
		dataType : 'json',
		cache : false,
		data:{"processInstanceId":processInstanceId,"taskId":taskId},
		success : function(json) {
			var wte=json.wte;
			initpartApplyShenPiPage(wte.cdefine4,wte.cdefine3,wte.cdefine5);
			shenpibusId=wte.cdefine4;
		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
// 审批页
function initpartApplyShenPiPage(partId,processDefId,taskKey) {
	var id = partId;
	var tId = "0";
	// 获取审批节点配置信息
	$.ajax({
		url : "ProcessConfigureController/getProcessConfigure.do",
		data : {
			'taskKey' : taskKey,
			'processDefId' : processDefId
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json1) {
			var processConfigure = json1.processConfigure;
			if(null == processConfigure || processConfigure == "0"){
				layer.alert('请到流程设计界面配置属性信息！', {
                }, function(){
            		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                });
				return;
			}
			if('0' == processConfigure.normalAttrs && '0' == processConfigure.qualityAttrs && '0' == processConfigure.designAttrs && '0' == processConfigure.purchaseAttrs){
				 layer.alert('请到流程设计界面配置属性信息！', {
                 }, function(){
             		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                 });
				 return;
			}
			if (processConfigure != '0') {
				$.ajax({
					url : 'WorkflowApplyFormController/selectApplyEditPageField.do',
					type : 'post',
					dataType : 'json',
					data : {
						"tId" : tId,
						"id" : id
					},
					cache : false,
					success : function(json) {
						var partData = json.partData;
						var typePerson = json.typePerson;
						var assignePerson = json.assignePerson;
							// 流程执行人显示
							if (isNaN(partId)) {
								$("#taskPerson").val(assignePerson);
								$("#typeOption").val(typePerson);
							}
							var insertList = json.insertList;
							// 审批页数据手册预览
							if (tId == 0) {
								var partDates = partData.Datesheet;
								var dateShet = partDates.split(",");
								var dateArray = new Array();
								for (var a = 0; a < dateShet.length; a++) {
									if (a == dateShet.length - 1) {
										dateArray
												.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''
														+ dateShet[a]
														+ '\')">'
														+ dateShet[a] + '</a>');
									} else {
										dateArray
												.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''
														+ dateShet[a]
														+ '\')">'
														+ dateShet[a]
														+ '</a>'
														+ ',');
									}
								}
								partData.Datesheet=dateArray.join(",");
							}
							// 生成其他其他添加字段
							var normalList=new Array();// 基本属性集合
							var qualityList=new Array();// 质量属性集合
							var designList=new Array();// 设计属性集合
							var purchaseList=new Array();// 采购属性集合
							for(var i=0;i<insertList.length;i++){
								if(insertList[i].type==1){// 基本属性
									normalList.push(insertList[i]);
									continue;
								}
								if(insertList[i].type==2){// 质量属性
									qualityList.push(insertList[i]);
									continue;
								}
								if(insertList[i].type==3){// 设计属性
									designList.push(insertList[i]);
									continue;
								}
								if(insertList[i].type==4){// 采购属性
									purchaseList.push(insertList[i]);
									continue;
								}
							}
							var normalObj=getApplyFieldTr(normalList,partData);
							var qualityObj=getApplyFieldTr(qualityList,partData);
							var designObj=getApplyFieldTr(designList,partData);
							var purchaseObj=getApplyFieldTr(purchaseList,partData);
							var normalAttrsStr=normalObj.str;// 基本属性
							var qualityAttrsStr=qualityObj.str;// 质量属性
							var designAttrsStr=designObj.str;// 设计属性
							var purchaseAttrsStr=purchaseObj.str;// 采购属性
							var normalRP=normalObj.rp;// 控制属性跨列的变量
							var qualityRP=qualityObj.rp;// 控制属性跨列的变量
							var designRP=designObj.rp;// 控制属性跨列的变量
							var purchaseRP=purchaseObj.rp;// 控制属性跨列的变量
							// 基本属性
							if(normalAttrsStr!=''){
								normalAttrsStr=normalAttrsStr.substring(4,normalAttrsStr.length);
								normalAttrsStr='<tr><td rowspan="'+normalRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("jbsx")+'</td>'+normalAttrsStr;
							}
							// 质量属性
							if(qualityAttrsStr!=''){
								qualityAttrsStr=qualityAttrsStr.substring(4,qualityAttrsStr.length);
								qualityAttrsStr='<tr><td rowspan="'+qualityRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("zlsx")+'</td>'+qualityAttrsStr;
							}
							// 设计属性
							if(designAttrsStr!=''){
								designAttrsStr=designAttrsStr.substring(4,designAttrsStr.length);
								designAttrsStr='<tr><td rowspan="'+designRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("sjsx")+'</td>'+designAttrsStr;
							}
							// 采购属性
							if(purchaseAttrsStr!=''){
								purchaseAttrsStr=purchaseAttrsStr.substring(4,purchaseAttrsStr.length);
								purchaseAttrsStr='<tr><td rowspan="'+purchaseRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("cgsx")+'</td>'+purchaseAttrsStr;
							}
							if(processConfigure.normalAttrs != '1'){
								normalAttrsStr="";
							}
							if(processConfigure.qualityAttrs != '1'){
								qualityAttrsStr="";
							}
							if(processConfigure.designAttrs != '1'){
								designAttrsStr="";
							}
							if(processConfigure.purchaseAttrs != '1'){
								purchaseAttrsStr="";
							}
							$("#partDataTable").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
					},
					error : function() {
						layer.alert("数据连接异常,请联系管理员！");
					}
				});
			}else{
				$("#normalTbody").hide();
				$("#qualityTbody").hide();
				$("#designTbody").hide();
				$("#purchaseTbody").hide();
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
// 器件申请单编辑/查看界面 *similar:选择类似器件标识
function initpartApplyPage(partId,similar) {
	var id = "";
	var tId = "";
	if (isNaN(partId)) {
		id = getUrlParam("partId");// id
		tId = getUrlParam("taskId");// id
	} else {// 审批页，元器件编辑
		id = partId;
		tId = "0";
	}
	// 获取审批节点配置信息
	$.ajax({
		url : "ProcessConfigureController/getProcessConfigure.do",
		data : {
			'taskKey' : 'startEvent',
			'tId':tId
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json1) {
			var processConfigure = json1.processConfigure;
			if(null == processConfigure || processConfigure == "0"){
				layer.alert('请到流程设计界面配置属性信息！', {
                }, function(){
            		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                });
				return;
			}
			if('0' == processConfigure.normalAttrs && '0' == processConfigure.qualityAttrs && '0' == processConfigure.designAttrs && '0' == processConfigure.purchaseAttrs){
				 layer.alert('请到流程设计界面配置属性信息！', {
                 }, function(){
             		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                 });
				 return;
			}
			if (processConfigure != '0') {
				$.ajax({
					url : 'WorkflowApplyFormController/selectApplyEditPageField.do',
					type : 'post',
					dataType : 'json',
					data : {
						"tId" : tId,
						"id" : id
					},
					cache : false,
					success : function(json) {
						var partData = json.partData;
						var typePerson = json.typePerson;
						var assignePerson = json.assignePerson;
						var insertList = json.insertList;
						// 审批页数据手册预览
						if (tId == 0) {
							var partDates = partData.Datesheet;
							var dateShet = partDates.split(",");
							var dateArray = new Array();
							for (var a = 0; a < dateShet.length; a++) {
								if (a == dateShet.length - 1) {
									dateArray
											.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''
													+ dateShet[a]
													+ '\')">'
													+ dateShet[a] + '</a>');
								} else {
									dateArray
											.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''
													+ dateShet[a]
													+ '\')">'
													+ dateShet[a]
													+ '</a>'
													+ ',');
								}
							}
							partData.Datesheet=dateArray.join(",");
						}
						// 生成其他其他添加字段
						var normalList=new Array();// 基本属性集合
						var qualityList=new Array();// 质量属性集合
						var designList=new Array();// 设计属性集合
						var purchaseList=new Array();// 采购属性集合
						for(var i=0;i<insertList.length;i++){
							if(insertList[i].type==1){// 基本属性
								normalList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==2){// 质量属性
								qualityList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==3){// 设计属性
								designList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==4){// 采购属性
								purchaseList.push(insertList[i]);
								continue;
							}
						}
						var normalObj=getApplyFieldTr(normalList,partData,similar);
						var qualityObj=getApplyFieldTr(qualityList,partData,similar);
						var designObj=getApplyFieldTr(designList,partData,similar);
						var purchaseObj=getApplyFieldTr(purchaseList,partData,similar);
						var normalAttrsStr=normalObj.str;// 基本属性
						var qualityAttrsStr=qualityObj.str;// 质量属性
						var designAttrsStr=designObj.str;// 设计属性
						var purchaseAttrsStr=purchaseObj.str;// 采购属性
						var normalRP=normalObj.rp;// 控制属性跨列的变量
						var qualityRP=qualityObj.rp;// 控制属性跨列的变量
						var designRP=designObj.rp;// 控制属性跨列的变量
						var purchaseRP=purchaseObj.rp;// 控制属性跨列的变量
						// 基本属性
						if(normalAttrsStr!=''){
							normalAttrsStr=normalAttrsStr.substring(4,normalAttrsStr.length);
							normalAttrsStr='<tr><td rowspan="'+normalRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("jbsx")+'</td>'+normalAttrsStr;
						}
						// 质量属性
						if(qualityAttrsStr!=''){
							qualityAttrsStr=qualityAttrsStr.substring(4,qualityAttrsStr.length);
							qualityAttrsStr='<tr><td rowspan="'+qualityRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("zlsx")+'</td>'+qualityAttrsStr;
						}
						// 设计属性
						if(designAttrsStr!=''){
							designAttrsStr=designAttrsStr.substring(4,designAttrsStr.length);
							designAttrsStr='<tr><td rowspan="'+designRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("sjsx")+'</td>'+designAttrsStr;
						}
						// 采购属性
						if(purchaseAttrsStr!=''){
							purchaseAttrsStr=purchaseAttrsStr.substring(4,purchaseAttrsStr.length);
							purchaseAttrsStr='<tr><td rowspan="'+purchaseRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("cgsx")+'</td>'+purchaseAttrsStr;
						}
						if(processConfigure.normalAttrs != '1'){
							normalAttrsStr="";
						}
						if(processConfigure.qualityAttrs != '1'){
							qualityAttrsStr="";
						}
						if(processConfigure.designAttrs != '1'){
							designAttrsStr="";
						}
						if(processConfigure.purchaseAttrs != '1'){
							purchaseAttrsStr="";
						}
						$("#partDataTbody").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
							// 流程执行人显示
							if (isNaN(partId)) {
								$("#taskPerson").val(assignePerson);
								$("#taskPersonName").val(assignePerson);
								$("#typeOption").val(typePerson);
							}
							
						
					},
					error : function() {
						layer.alert("数据连接异常,请联系管理员！");
					}
				});
			} else {
				$("#normalTbody").hide();
				$("#qualityTbody").hide();
				$("#designTbody").hide();
				$("#purchaseTbody").hide();
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
//管理页点击查询按钮
function manageSearch(){
  $("#Pagination").html('');
  initProcessManage();
}
// 流程管理页面初始化
function initProcessManage(pageNo) {
	loadProperties();//国际化
	var processName = $("input[name='processName']").val();
	var processState = $("select[name='processState']").val();
	var processTaskPerson = $("input[name='processTaskPerson']").val();
	var item = $("input[name='item']").val();
	var startTime = $("#createTimeBegin").val();
	var endTime = $("#createTimeFinish").val();
	var formTypeFormManage = $("#formTypeFormManage").val();
	if (isNaN(pageNo)) {
		pageNo = 0;
	}
	var addBasics = '1';
	pageNo = parseInt(addBasics) + parseInt(pageNo);
	$.ajax({
		url : getContextPathForWS()+'/WorkflowMainsController/selectProcessManager.do',
		data : {
			'pageNo' : pageNo,
			'processName' : processName,
			'processState' : processState,
			'processTaskPerson' : processTaskPerson,
			'startTime' : startTime,
			'item' : item,
			'formTypeFormManage' : formTypeFormManage,
			'endTime' : endTime
		},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(data) {
			var html = '';
			if (null == data || "" == data) {
				html += '<tr>';
				html += '<td colspan="10" align="center">'+$.i18n.prop("noData")+'</td>';
				html += '</tr>';
			} else {
				var json = data.list;
				var count = data.count;
				var pageNo = data.pageNo;
				for (var i = 0; i < json.length; i++) {
					var addBasic = '1';
					var addBefore = i + '';
					var addAfter = parseInt(addBasic) + parseInt(addBefore);
					var processName = json[i].process_name == null
							? ''
							: json[i].process_name;
					var items = json[i].item == null
							? ''
							: json[i].item;
					var taskName = json[i].taskName == '0' ? '元器件申请' : '';
					var processTaskperson = json[i].process_task_person == null
							? ''
							: json[i].process_task_person;
					var processTaskST = json[i].process_task_starttime == null
							? ''
							: json[i].process_task_starttime;
					var processNodeTaskname = json[i].process_node_taskname == null
							? ''
							: json[i].process_node_taskname;
				    items = items + "" +json[i].categoryName;
					html += '<tr>';
					html += '<td>' +'<input type="checkbox" onclick="manageCheckOne('+json[i].id+')" name="manageCheckOne" value="'+json[i].id+'">' + '</td>';
					html += '<td>' + addAfter + '</td>';
					html += '<td>' + items+ '</td>';
					html += '<td>' + processName + '</td>';
					//html += '<td>' + taskName+ '</td>';
					html += '<td>' +processTaskperson+ '</td>';//流程发起人
					html += '<td>' + processTaskST + '</td>';//流程发起时间
					//html += '<td>' +processNodeTaskname + '</td>';//当前办理人
					//html += '<td>' +''+ '</td>';//超时时间
					//html += '<td>' + json[i].processTaskExpirtime + '</td>';//任务到期时间
					html += '<td>' + json[i].process_task_state + '</td>';//流程待办类型
				    html += '<td>';
				    html += '<a class="btn btn-xs btn-danger flow-view-head-look" onclick="applyFormLookPage('+json[i].id+','+json[i].taskName+','+json[i].cdefine4+')""><span class="glyphicon glyphicon-eye-open"></span> '+$.i18n.prop("lookBtn")+'</a>';
				    html += '<a class="btn btn-xs btn-danger flow-view-head-edit" onclick="applyFormEditPage('+json[i].id+','+json[i].taskName+','+json[i].cdefine4+')""><span class="glyphicon glyphicon-edit"></span> '+$.i18n.prop("updateBtn")+'</a>';
				    html += '<a class="btn btn-xs btn-danger flow-view-head-road" onclick="findHisActivitiList('+json[i].id+','+json[i].cdefine2+')"><span class="glyphicon glyphicon-time"></span> '+$.i18n.prop("processBtn")+'</a>';
				    html += '<a class="btn btn-xs btn-black flow-view-head-ask" onclick="pressDo('+json[i].id+','+json[i].taskName+',\''+json[i].process_task_state+'\')"><span class="glyphicon glyphicon-volume-down"></span> '+$.i18n.prop("RemindersBtn")+'</a>';
				    html += '<a class="btn btn-xs btn-black flow-view-head-none" onclick="deleteTask('+json[i].id+','+json[i].cdefine4+','+json[i].cdefine2+','+json[i].taskName+')"><span class="glyphicon glyphicon-trash"></span> '+$.i18n.prop("voidBtn")+'</a>';
				    html += '<a class="btn btn-xs btn-black flow-view-head-picture" href="javascript:proceessPngLoad('+json[i].id+',\''+json[i].process_task_state+'\')"><span class="glyphicon glyphicon-picture"></span> '+$.i18n.prop("processPng")+'</a>';
				    html += '</td>';
					html += '</tr>';
				}
				if ($("#Pagination").html().length == '') {
					$("#Pagination").pagination(count, {
								items_per_page : 5,
								num_edge_entries : pageNo,
								num_display_entries : 8,
								callback : function(pageNo, panel) {
									if (count == null) {
										initProcessManage(pageNo);
									}
								},
								link_to : "javascript:void(0);"
							});
				}
			}
			$("#processManageList").html(html);
			count = null;
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
/** 复选框单个选择* */
function checkOne(id) {
	var CheckBox = document.getElementsByName('checkOne');
	var num = 0;
	if (CheckBox.length > 0) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked) {
				num++;
			}
		}
	}
	if (num == CheckBox.length) {
		$("#checkAll").prop('checked', true);
	} else {
		$("#checkAll").prop('checked', false);
	}
}
/** 复选框全选反选操作* */
function checkAll() {
	var CheckBox = document.getElementsByName('checkOne');
	if ($("#checkAll").prop('checked')) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked == false) {
				CheckBox[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked = true) {
				CheckBox[i].checked = false;
			}
		}
	}
}
// 流程表单绑定流程模版 type=0:元器件申请
function settingFormType(type) {
	var ids = new Array;
	loadProperties();// 国际化
	$("input[name='checkOne']:checkbox:checked").each(function() {
				ids.push($(this).val());
			});
	if (ids.length == 0) {
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}
	$.ajax({
				url : 'WorkflowMainsController/settingFormType.do',
				data : {
					'ids' : JSON.stringify(ids),
					'type' : type
				},
				dataType : 'json',
				cache : false,
				success : function(json) {
                  layer.alert(json);
					$("#partDataPage").html('');
					initHtml();
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员！");
				}
			})
}
// 器件申请界面选择执行人
function userChooseFromPartApply() {
	 loadProperties();//国际化
	var ids = "";
	$("input[name='checkOneUser']:checkbox:checked").each(function() {
				ids = ids + $(this).val() + ",";
			});
	if (ids == "") {
		layer.alert($.i18n.prop("processAlert2"));
		return;
	}
	$("#taskPerson").val(ids.slice(0, -1));
	$(".flow-setting-nodeUser-choose").hide();
}
// 获取URL参数
function getUrlParam(key) {
	// 获取参数
	var url = window.location.search;
	// 正则筛选地址栏
	var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	// 匹配目标参数
	var result = url.substr(1).match(reg);
	// 返回参数值
	return result ? decodeURIComponent(result[2]) : null;
}
// 点击流程管理修改按钮---页面跳转
function applyFormEditPage(taskId, ft, partId) {
	$.ajax({
				url : 'WorkflowApplyFormController/processStateQuery.do',
				data : 'taskId=' + taskId,
				dataType : 'json',
				cache : false,
				success : function(json) {
					var lang = json.lang;
					var task = json.task;
					if (task.processTaskState != '未启动') {
						if("zh" != lang){
							layer.alert("Cannot be modified .");
						}else{
							layer.alert("该流程任务已启动，不能修改！");
						}
					} else {
						if ('0' == ft) {//元器件申请
		                    window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-manage-edit.jsp?taskId="
				                + taskId + "&ft=" + ft + "&partId=" + partId;
	                    }
					}
				},
				error : function() {
					layer.alert("Error...");
				}
			});
}
// 待办/已办 页面数据初始化 state 0待办 1已办
var cou='';
function initDaiBan(state,pageNo) {
	if(isNaN(pageNo)){
		pageNo = 0;
	}
	var pageSize=5;
	var addBasic = '1';
    pageNo = parseInt(addBasic)+parseInt(pageNo);
	var taskName = $("#taskName").val();
	var processCreatePerson = $("#processCreatePerson").html();
	var workFlowName = $("#workFlowName").val();
	var createTimeBegin = $("#createTimeBegin").val();
	var createTimeFinish = $("#createTimeFinish").val();
	$.ajax({
		url : 'WorkflowMainsController/selectProcessTask.do',
		dataType : 'json',
		cache : false,
		data : {
			"taskName" : taskName,
			"processCreatePerson" : processCreatePerson,
			"workFlowName" : workFlowName,
			"createTimeBegin" : createTimeBegin,
			"createTimeFinish" : createTimeFinish,
			"state":state,
			"pageNo":pageNo,
			"pageSize":pageSize
		},
		success : function(json) {
			var taskList=json.taskList;
			var html = '';
			if (null == taskList || "" == taskList) {
				html += '<tr>';
				html += '<td colspan="9" align="center">--'+(en=='en'?'no data':'没有数据')+'--</td>';
				html += '</tr>';
			} else {
				var title="";
				var shenpiPage="";
				
				for (var i = 0; i < taskList.length; i++) {
					if(taskList[i].title=='0'){//元器件申请
						title="元器件申请";
						shenpiPage="pages/workflowPage/cms-flow-shenPi.jsp";//元器件申请审批编辑页面
					}
					var addBasic = '1';
					var addBefore = i + '';
					var addAfter = parseInt(addBasic) + parseInt(addBefore);
					html += '<tr>';
					html += '<td>' + addAfter + '</td>';
					html += '<td>' + taskList[i].item + '</td>';//标题
//					html += '<td>' + taskList[i].workFlowName + '</td>';//流程名称
					html += '<td>' + taskList[i].processCreatePerson + '</td>';// 流程发起人
					html += '<td>' + taskList[i].taskName + '</td>';// 流程节点任务
					html += '<td>' + taskList[i].processTaskStarttime + '</td>';// 流程发起时间
					html += '<td>' + taskList[i].processTaskState + '</td>';// 流程待办类型
					
					if(state=='0'){
						html += '<td>';
						html += '<a href="'+shenpiPage+'?processInstanceId='
								+ taskList[i].cdefine2
								+ '&id='
								+ taskList[i].id
								+ '" class="btn btn-danger btn-xs intoShenpi-page"><span class="glyphicon glyphicon-arrow-right"></span>'+(en=='en'?'approvalPage':'进入审批页')+'</a>';
						html += '</td>';
					}else{
						html += '<td>' + taskList[i].processState + '</td>';// 流程待办类型
						html +='<td>';
						html += '<a class="btn btn-xs btn-danger flow-yiban-more" onclick="yibanProDetail('+taskList[i].id+')"><span class="glyphicon glyphicon-star"></span>  '+(en=='en'?'detail':'详情')+'</a>';
						if(taskList[i].processState=='正在审批'){//当前任务正在审批时，可以撤回
							html += '<a class="btn btn-warning btn-xs flow-yiban-return" onclick="retractTask(\''+taskList[i].cdefine2+'\',\''+taskList[i].cdefine5+'\',\''+taskList[i].id+'\');"><span class="glyphicon glyphicon-share-alt"></span>  '+(en=='en'?'retract':'撤回')+'</a>';
						}
						html += '</td>';
					}
					html += '</tr>';
				}
			}
			$("#processDaibanList").html(html);
			//分页
			if(cou != json.cou){
				cou=json.cou;
				$("#daibanPagination").pagination(json.cou,{
					items_per_page : pageSize,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(taskList==null){
							initDaiBan(state,pageNo)
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			taskList=null;
		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
//待办页重置按钮
function resetDaiBanSearch() {
	$("#title").val("");
	$("#processCreatePerson").html("");
	$("#processCreatePersonShow").html("");
	$("#workFlowName").val("");
	$("#createTimeBegin").val("");
	$("#createTimeFinish").val("");
}
//流程监控页重置按钮
function resetjiankongSearch() {
	$("#startTimeBegin").val("");
	$("#startUser").html("");
	$("#lastUser").html("");
	$("#startUserShow").html("");
	$("#lastUserShow").html("");
	$("#startTimeEnd").val("");
	$("#lastTimeBegin").val("");
	$("#lastTimeEnd").val("");
}
//点击流程管理修改按钮---页面跳转
function applyFormLookPage(taskId, ft, partId) {
	if ('0' == ft) {
		window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-manage-look.jsp?taskId="
				+ taskId + "&ft=" + ft + "&partId=" + partId;
	}
}
//元器件申请单中流程图片查看
function processPngLook() {
	var formType = GetQueryString('ft');
	var taskId = GetQueryString('taskId');
	$.ajax({
		url : 'WorkflowApplyFormController/getPngForApply.do',
		data : {'formType':formType,'taskId':taskId},
		dataType : 'json',
		cache : false,
		success : function(json) {
			if (json.pngName == '0') {
				layer.alert('找不到流程模板，请到流程设计界面进行模板创建', {
							//skin : 'layui-layer-molv' // 样式类名
						}, function() {
							location.href = "pages/workflowPage/cms-flow.jsp";
						});
			} else {
				var pngHtml = '';
				var userTaskHtml = '';
				pngHtml += '<img src="workflowImg/' + json.pngName
						+ '" alt=""/>';
				// 动态插入到页面中
				$("#flowPng").html(pngHtml);
			}
		},
		error : function() {
			layer.alert("服务器连接异常，请联系管理员!");
		}
	});
}
// 催办
function pressDo(taskId,formType,state){
	loadProperties();//国际化
	if(state == "未启动"){
		layer.alert($.i18n.prop("processAlert9"));
		return;
	}
	if(state == "审批通过"){
		layer.alert($.i18n.prop("processAlert10"));
		return;
	}
	$.ajax({
		url : 'WorkflowApplyFormController/pressDo.do',
		data : {'taskId':taskId,'formType':formType},
		dataType : 'json',
		cache : false,
		success : function(json) {
			if(json.user == null){
				layer.alert(json.msg);
			}else{
			    $("input[name='launchPerson']").val(json.user.loginName);
			    $("input[name='msgContent']").val(json.msgContent);
			     $("#pressId").attr("href","javascript:sendPressMsg("+taskId+","+formType+")"); 
			     $(".flow-view-askWindow").show();
			}
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
//发送催办消息
function sendPressMsg(taskId,formType){
	loadProperties();//国际化
  var msgContent = $("input[name='msgContent']").val();
  $.ajax({
		url : 'WorkflowApplyFormController/insertpressMessage.do',
		data : {'taskId':taskId,'msgContent':msgContent,'formType':formType},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			  for(var r=0;r<json.length;r++){
			  	  sendWebsocket(json[r]);
			  }
			   $(".flow-view-askWindow").hide();
			   layer.alert($.i18n.prop("operationSus"));
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
// 元器件申请树结构保存
function goPartApplyPage(formType) {
	loadProperties();
	if ("0" == formType) {
		var zTree = $.fn.zTree.getZTreeObj("partTree");
		var nodes = zTree.getSelectedNodes();
		if (nodes.length == 0 || nodes[0].children != undefined) {
			layer.alert($.i18n.prop("check_last"));
			return;
		}
		var name = nodes[0].name;
		name = b.encode(name);
		window.location.href = getContextPath()+"/pages/workflowPage/cms-flow-manage-applyPart.jsp?name=" + name + "&ft=0";
	}
}
// 器件申请单查看界面
function initpartApplyLookPage() {
	var id = getUrlParam("partId");// id
	var tId = getUrlParam("taskId");// id
	// 获取审批节点配置信息
	$.ajax({
		url : "ProcessConfigureController/getProcessConfigure.do",
		data : {
			'taskKey' : 'startEvent',
			'tId' : tId
		},
		cache : false,
		type : 'post',
		dataType : "json",
		success : function(json1) {
			var processConfigure = json1.processConfigure;
			if(null == processConfigure || processConfigure == "0"){
				layer.alert('请到流程设计界面配置属性信息！', {
                }, function(){
            		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                });
				return;
			}
			if('0' == processConfigure.normalAttrs && '0' == processConfigure.qualityAttrs && '0' == processConfigure.designAttrs && '0' == processConfigure.purchaseAttrs){
				 layer.alert('请到流程设计界面配置属性信息！', {
                 }, function(){
             		window.location.href=getContextPathForWS()+"/pages/workflowPage/cms-flow.jsp";
                 });
				 return;
			}
			if (processConfigure != '0') {
				$.ajax({
					url : 'WorkflowApplyFormController/selectApplyEditPageField.do',
					type : 'post',
					dataType : 'json',
					data : {
						"tId" : tId,
						"id" : id
					},
					cache : false,
					success : function(json) {
						var partData = json.partData;
						var typePerson = json.typePerson;
						var assignePerson = json.assignePerson;
						var lang = json.lang;
						var insertList=json.insertList;
						// 生成其他其他添加字段
						var normalList=new Array();// 基本属性集合
						var qualityList=new Array();// 质量属性集合
						var designList=new Array();// 设计属性集合
						var purchaseList=new Array();// 采购属性集合
						for(var i=0;i<insertList.length;i++){
							if(insertList[i].type==1){// 基本属性
								normalList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==2){// 质量属性
								qualityList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==3){// 设计属性
								designList.push(insertList[i]);
								continue;
							}
							if(insertList[i].type==4){// 采购属性
								purchaseList.push(insertList[i]);
								continue;
							}
						}
						var normalObj=getApplyParticularFieldTr(normalList,partData);
						var qualityObj=getApplyParticularFieldTr(qualityList,partData);
						var designObj=getApplyParticularFieldTr(designList,partData);
						var purchaseObj=getApplyParticularFieldTr(purchaseList,partData);
						var normalAttrsStr=normalObj.str;// 基本属性
						var qualityAttrsStr=qualityObj.str;// 质量属性
						var designAttrsStr=designObj.str;// 设计属性
						var purchaseAttrsStr=purchaseObj.str;// 采购属性
						var normalRP=normalObj.rp;// 控制属性跨列的变量
						var qualityRP=qualityObj.rp;// 控制属性跨列的变量
						var designRP=designObj.rp;// 控制属性跨列的变量
						var purchaseRP=purchaseObj.rp;// 控制属性跨列的变量
						// 基本属性
						if(normalAttrsStr!=''){
							normalAttrsStr=normalAttrsStr.substring(4,normalAttrsStr.length);
							normalAttrsStr='<tr><td rowspan="'+normalRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("jbsx")+'</td>'+normalAttrsStr;
						}
						// 质量属性
						if(qualityAttrsStr!=''){
							qualityAttrsStr=qualityAttrsStr.substring(4,qualityAttrsStr.length);
							qualityAttrsStr='<tr><td rowspan="'+qualityRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("zlsx")+'</td>'+qualityAttrsStr;
						}
						// 设计属性
						if(designAttrsStr!=''){
							designAttrsStr=designAttrsStr.substring(4,designAttrsStr.length);
							designAttrsStr='<tr><td rowspan="'+designRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("sjsx")+'</td>'+designAttrsStr;
						}
						// 采购属性
						if(purchaseAttrsStr!=''){
							purchaseAttrsStr=purchaseAttrsStr.substring(4,purchaseAttrsStr.length);
							purchaseAttrsStr='<tr><td rowspan="'+purchaseRP+'" class="normalAttrs" id="normalAttrsTd">'+$.i18n.prop("cgsx")+'</td>'+purchaseAttrsStr;
						}
						if(processConfigure.normalAttrs != '1'){
							normalAttrsStr="";
						}
						if(processConfigure.qualityAttrs != '1'){
							qualityAttrsStr="";
						}
						if(processConfigure.designAttrs != '1'){
							designAttrsStr="";
						}
						if(processConfigure.purchaseAttrs != '1'){
							purchaseAttrsStr="";
						}
						$("#partDataTbody").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
						// 流程执行人显示
						var tasks = json.TDK;
						$("#addDatesheet").html(partData.Datesheet);
						var htmlP = '';
						var zxrName = "zh" == lang
								? "当前执行节点/人"
								: "Current executor";
						if (null != tasks && "" != tasks) {
							tasks = tasks.split(",")
							for (var xx = 0; xx < tasks.length; xx++) {
								htmlP += '<tr>';
								htmlP += '<td colspan="2"class="aboutLiucheng">'
										+ zxrName + '</td>';
								htmlP += '<td colspan="3" id="">';
								htmlP += '<input class="form-control workMen"value="'
										+ tasks[xx]
										+ ": "
										+ assignePerson
										+ '" disabled/>';
								htmlP += '</td>';
								htmlP += '</tr>';
							}
							$("#typeOptionId").after(htmlP);
						} else {
							$("#typeOptionId").hide();
						}
						$("#typeOption").val(typePerson);
					},
					error : function() {
						layer.alert("数据连接异常,请联系管理员！");
					}
				});
			} else {
				$("#normalTbody").hide();
				$("#qualityTbody").hide();
				$("#designTbody").hide();
				$("#purchaseTbody").hide();
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
//生成字段tr
function getApplyParticularFieldTr(insertList,partData){
	var obj=new Object();
	var string="";
	var rp=0;
	for(var i=0;i<insertList.length;i++){
		var xh="";
		var value=partData==undefined?"":partData[insertList[i].fieldName];
		value=value==undefined?"":value;
		var str=value;
		if(',shape_img,size_img,characteristic_curve_img,typical_ap_img,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			str="<div class=\"left parts-images\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"图片上传最佳尺寸【550px*320px】\">" +
					"<img src=\""+value+"\" id=\""+insertList[i].fieldName+"1\"></div>";
		}
		if(i==insertList.length-1&&rp%2==0){
			string+="<tr><td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td colspan=\"3\" >"+str+"</td></tr>";
		}else{
			if(rp%2==0){
				string+="<tr>";
			}
			string+="<td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td class=\"valueLength\">"+str+"</td>";
			if(rp%2!=0){
				string+="</tr>";
			}
		}
		rp++;
	}
	obj.str=string;
	obj.rp=rp%2==0?parseInt(rp/2):(parseInt(rp/2)+1);
	return obj;
}
//流程管理页面----搜索框内容重置
function resetManageSearch(){
    $("input[name='processName']").val('');
	$("select[name='processState']").val('');
	$("input[name='processTaskPerson']").val('');
	$("input[name='item']").val('');
	$("#createTimeBegin").val('');
	$("#createTimeFinish").val('');
	$("#formTypeFormManage").val('');
}
// /流程管理页面----作废
function deleteTask(taskId, businessId, processInstanceId, formType) {
	loadProperties();//国际化
	layer.confirm($.i18n.prop("deleteAlert"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
		}, function() {
		$.ajax({
					url : getContextPathForWS()+'/WorkflowApplyFormController/deleteTask.do',
					data : {
						'taskId' : taskId,
						'businessId' : businessId,
						'processInstanceId' : processInstanceId,
						'formType' : formType
					},
					type : 'post',
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert(json);
						  $("#Pagination").html('');
                          initProcessManage();
					},
					error : function() {
						layer.alert($.i18n.prop("alertError"));
					}
				});
	});
}
/******流程审批页审批历史*****/
function getTaskHistory(){
	var processInstanceId = GetQueryString('processInstanceId');// 流程实例ID
	$.ajax({
		url : 'WorkflowMainsController/getTaskHistory.do',
		data : {'processInstanceId':processInstanceId},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			var wfList=json.wfList;
			var html=""
			for(var i=0;i<wfList.length;i++){
				html+="<tr>";
				var workflowTaskEntitiy=wfList[i];
				html+="<td>"+(i+1)+"</td>";//序号
				html+="<td>"+workflowTaskEntitiy.taskName+"</td>";//任务名称
				html+="<td>"+workflowTaskEntitiy.createTime+"</td>";//创建时间
				html+="<td>"+workflowTaskEntitiy.endTime+"</td>";//审批结束时间
				html+="<td>"+workflowTaskEntitiy.processAssignees+"</td>";//执行人
				html+="<td>"+workflowTaskEntitiy.processAproveAdvice+"</td>";//审批意见
				html+="</tr>";
			}
			$("#taskHistory").html(html);
			$(".shenPi-history-box").show();
		},
		error : function() {
			layer.alert("服务器连接异常，请联系管理员!");
		}
	});
}
/**
 * 历史活动查询
 */
function findHisActivitiList(taskId,processInstanceId) {
	loadProperties();//国际化
	if(null == processInstanceId || "" == processInstanceId){
		layer.alert($.i18n.prop("processAlert9"));
		return;
	}
	$.ajax({
		url : 'WorkflowApplyFormController/findHisActivitiList.do',
		data : {'processInstanceId':processInstanceId,'taskId':taskId},
		type : 'post',
		dataType : 'json',
		cache : false,
		success : function(json) {
			var wfList=json.wfList;
			var html=""
			for(var i=0;i<wfList.length;i++){
				html+="<tr>";
				var workflowTaskEntitiy=wfList[i];
				html+="<td>"+(i+1)+"</td>";//序号
				html+="<td>"+workflowTaskEntitiy.taskName+"</td>";//任务名称
				html+="<td>"+workflowTaskEntitiy.createTime+"</td>";//创建时间
				html+="<td>"+workflowTaskEntitiy.endTime+"</td>";//审批结束时间
				html+="<td>"+workflowTaskEntitiy.processAssignees+"</td>";//执行人
				html+="<td>"+workflowTaskEntitiy.processAproveAdvice+"</td>";//审批意见
				html+="</tr>";
			}
			$("#hisActivitiHtml").html(html);
			$(".flow-view-processWindow").show();
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
//流程管理---过程---动态流程图加载
function proceessPngLoad(taskId,processTaskState){
	loadProperties();//国际化
	if("未启动" == processTaskState){
		  layer.alert($.i18n.prop("processAlert9"));
		  return;
	}
  var xhr = new XMLHttpRequest();
	xhr.open("get",
			"workflowPictureLoadController/processTracking.do?taskId=" + taskId, true);
	xhr.responseType = "blob";
	xhr.onreadystatechange = function() {
		if (xhr.status == 200 && xhr.readyState == 4) {
			var blob = this.response;
			var img = document.createElement("img");
			img.onload = function(e) {
				window.URL.revokeObjectURL(img.src);
			};
			img.src = window.URL.createObjectURL(blob);
			$("#hisActivitiPng").html(img);
			 $(".flow-view-processWindow1").show();
		}
	};
	xhr.send();
}
//流程监控页 流程历史列表
var jiankongCou=0;
function processHis(pageNo){
	if (isNaN(pageNo)) {
		pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic) + parseInt(pageNo);
	var pageSize=5;
	var startTimeBegin=$("#startTimeBegin").val();
	var startTimeEnd=$("#startTimeEnd").val();
	var startUser=$("#startUser").html();
	var lastTimeBegin=$("#lastTimeBegin").val();
	var lastTimeEnd=$("#lastTimeEnd").val();
	var lastUser=$("#lastUser").html();
	$.ajax({
				url : 'WorkflowMainsController/selectProcessHistory.do',
				data : {
					'startTimeBegin' : startTimeBegin,
					'startTimeEnd' : startTimeEnd,
					'startUser' : startUser,
					'lastTimeBegin' : lastTimeBegin,
					'lastTimeEnd' : lastTimeEnd,
					'lastUser' : lastUser,
					"pageNo":pageNo,
					"pageSize":pageSize
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var html = '';
					if (null == json || "" == json) {
						html += '<tr>';
						html += '<td colspan="5" align="center">--没有数据--</td>';
						html += '</tr>';
					} else {
						var wfList=json.wfList;
						for(var i=0;i<wfList.length;i++){
							var wf=wfList[i];
							html +='<tr><td>'+wf.item+'</td><td>'+wf.processState+'</td><td>'+wf.processCreatePerson+'</td><td>'+wf.createTime+'</td></tr>';
						}
					}
					$("#processHis").html(html);
					
					//分页
			if(jiankongCou != json.cou){
				jiankongCou=json.cou;
				$("#jiankongPagination").pagination(json.cou,{
					items_per_page : pageSize,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(wfList==null){
							processHis(pageNo)
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			wfList=null;
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});
}
// 统计人员审批的节点数量
function countTaskByUser() {
	$.ajax({
				url : 'WorkflowMainsController/countTaskByUser.do',
				data : {},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if(en=='en'){
						title1='Statistics of personnel participation';
						name1='Number of nodes involved in approval';
						title2='Average time statistics for approval';
						name2='Average time to participate in process approval';
					}else{
						title1='人员参与度统计';
						name1='参与审批的节点数量';
						title2='审批的平均时间统计(单位：h)';
						name2='参与流程审批的平均时间';
					}
					var myChart = echarts.init(document.getElementById('Chart1'));
					var option1 = {
						title : {
							text : title1,
							subtext : '',
							x : 'center'
						},
						tooltip : {
							trigger : 'item',
							formatter : "{a} <br/>{b} : {c} ({d}%)"
						},
						legend : {
							orient : 'vertical',
							left : 'left',
							data : json.userList
						},
						series : [{
									name : name1,
									type : 'pie',
									radius : '55%',
									center : ['50%', '60%'],
									data : json.chartList1,
									itemStyle : {
										emphasis : {
											shadowBlur : 10,
											shadowOffsetX : 0,
											shadowColor : 'rgba(0, 0, 0, 0.5)'
										}
									}
								}]
					};
					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option1);
					
					var myChart = echarts.init(document.getElementById('Chart2'));
					var option2 = {
						title : {
							text : title2,
							subtext : '',
							x : 'center'
						},
						tooltip : {
							trigger : 'item',
							formatter : "{a} <br/>{b} : {c} ({d}%)"
						},
						legend : {
							orient : 'vertical',
							left : 'left',
							data : json.userList
						},
						series : [{
									name : name2,
									type : 'pie',
									radius : '55%',
									center : ['50%', '60%'],
									data : json.chartList2,
									itemStyle : {
										emphasis : {
											shadowBlur : 10,
											shadowOffsetX : 0,
											shadowColor : 'rgba(0, 0, 0, 0.5)'
										}
									}
								}]
					};
					// 使用刚指定的配置项和数据显示图表。
					myChart.setOption(option2);
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});

}
//流程设计-删除流程模板
function deleteProcessChart(deploymentId,id,formType){
	if(formType == 'partProcess'){
		formType = "0";
	}
	loadProperties();//国际化
   layer.confirm($.i18n.prop("processAlert7"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
		}, function() {
		$.ajax({
					url : getContextPathForWS()+'/WorkflowMainsController/deleteProcessChart.do',
					data : {
						'id' : id,
						'formType' : formType,
						'deploymentId' : deploymentId
					},
					type : 'post',
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert($.i18n.prop("alertMsg3"));
						  $("#partDataPage").html('');
						  initHtml();
					},
					error : function() {
						layer.alert($.i18n.prop("alertError"));
					}
				});
	});
}
//流程设计-清除数据
function cleanData(id,formType){
	if(formType != "partProcess"){
		layer.alert("操作成功！");
		return;
	}else{
		partProcess = "0";
	}
	loadProperties();//国际化
    layer.confirm($.i18n.prop("processAlert8"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
		}, function() {
		$.ajax({
					url : getContextPathForWS()+'/WorkflowMainsController/cleanProcessData.do',
					data : {
						'id' : id,
						'formType' : formType
					},
					type : 'post',
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert($.i18n.prop("operationSus"));
						  $("#partDataPage").html('');
						  initHtml();
					},
					error : function() {
						layer.alert($.i18n.prop("alertError"));
					}
				});
	});
}
function manageCheckOne(id){
var CheckBox = document.getElementsByName('manageCheckOne');
	var num = 0;
	if (CheckBox.length > 0) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked) {
				num++;
			}
		}
	}
	if (num == CheckBox.length) {
		$("#manageCheckAll").prop('checked', true);
	} else {
		$("#manageCheckAll").prop('checked', false);
	}
}
/** 复选框全选反选操作* */
function manageCheckAll() {
	var CheckBox = document.getElementsByName('manageCheckOne');
	if ($("#manageCheckAll").prop('checked')) {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked == false) {
				CheckBox[i].checked = true;
			}
		}
	} else {
		for (var i = 0; i < CheckBox.length; i++) {
			if (CheckBox[i].checked = true) {
				CheckBox[i].checked = false;
			}
		}
	}
}
//流程管理报表下载
function loadProcessInformation(){
	loadProperties();//国际化
   	var ids=new Array;
	$("input[name='manageCheckOne']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}else{
	   window.location.href=getContextPathForWS()+"/WorkflowApplyFormController/exportProcessInformation.do?ids="+JSON.stringify(ids);
	}
}

// 审批页同意按钮 弹窗的构造
function agreeDia() {
	var processInstanceId = GetQueryString('processInstanceId');//流程实例ID
	var taskId = GetQueryString('id');//任务id
	
	$.ajax({
				url : 'WorkflowMainsController/createAgreeDia.do',
				data : {
					'processInstanceId' : processInstanceId,
					'taskId':taskId
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if(json.message=='success'){
						var unbeginList=json.unbeginList;//未开始任务
						var tuList=json.tuList;//已配置人员
						var nowTasktype=json.type;
						if(nowTasktype=='and'){
							$("#freeStyle").parent().remove();
							$("#nodeSelectTr").remove();
						}
						var html="";
						for(var i=0;i<unbeginList.length;i++){
							html+="<option value=\""+unbeginList[i].taskDefinitionKey+"\">"+unbeginList[i].name+"</option>"
						}
						if(tuList.length>0){
							var userHtml="";
							var uNameHtml="";
							var type="";//人员审批关系
							for(var j=0;j<tuList.length;j++){
								userHtml+=tuList[j].loginName+",";
								uNameHtml+=tuList[j].userName+",";
								type=tuList[j].utType;
							}
							userHtml=userHtml.substring(0,userHtml.length-1);
							uNameHtml=uNameHtml.substring(0,uNameHtml.length-1);
							$("#typeOption1").parent().html(type);//人员审批关系
							$("#typeTr").show();
							
							$("#agreeSelectUser").html(uNameHtml);//已配置的人员显示
							$("#loginAgreeSelectUser").html(userHtml);//已配置的人员显示
							$("#selectBtn").hide();
						}else if(json.isLast=='1'){
							$("#selectBtn").parent().parent().remove();
							$("#jumpTr").remove();
						}else{
							$("#typeTr").show();
						}
						$("#nodeOption").html(html);
						$(".shenPi-agree-box").show();
						
					}else{
						layer.alert("数据有误！");
					}
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});
}
//正常跳转、自由跳转切换时
function changeTiaozhuanType(){
	$("#nodeSelectTr").show();
}
//正常跳转、自由跳转切换时
function changeTiaozhuanType1(){
	$("#nodeSelectTr").hide();
}
/******点击已办流程页面中表格详情按钮，弹出详细信息弹窗*****/
function yibanProDetail(taskId){
	$.ajax({
				url : 'WorkflowMainsController/getyibanProDetail.do',
				data : {
					'taskId' : taskId
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var wte=json.wte;
					$("#detailtitle").html(wte.title);
					$("#detailprocessNodeTaskname").html(wte.processNodeTaskname);
					$("#detailprocessTaskState").html(wte.processTaskState);
					$("#detailprocessTaskPerson").html(wte.processTaskPerson);
					$("#detailprocessTaskStarttime").html(wte.processTaskStarttime);
					$("#detailprocessTaskExpirtime").html(wte.processTaskExpirtime);
					$("#detailprocessAproveAdvice").html(wte.processAproveAdvice);
					$("#detailcdefine1").html(wte.cdefine1);
					$("#detailcdefine2").html(wte.cdefine2);
					$("#detailcdefine3").html(wte.cdefine3);
					$("#detailprocessKey").html(wte.processKey);
					$("#detailprocessName").html(wte.processName);
					$(".flow-yiban-Window").show();
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});
	
}
//已办任务撤回功能
function retractTask(processInstanceId,taskDefinitionKey,taskId) {
	$.ajax({
				url : 'WorkflowMainsController/retractTask.do',
				data : {
					'processInstanceId' : processInstanceId,
					'taskDefinitionKey' : taskDefinitionKey,
					'taskId':taskId
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					if(json.message=='1'){
						layer.alert('操作成功!', {
//							skin : 'layui-layer-molv' // 样式类名
						}, function() {
							location.reload();
						});
						
					}else if(json.message=='0'){
						layer.alert("信息有误！");
					}
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});
}
/**审批页----抄送**/
function sendProcessCC() {
	var processCC = $("input[name='processCC']").val();
	var receiverCC = $("#loginAgreeSelectUser2").html();
	if(receiverCC == ''){
		layer.alert("请选择人员！");
		return;
	}
	if(processCC == ''){
		layer.alert("请填写原因！");
		return;
	}
	$.ajax({
				url : 'WorkflowApplyFormController/insertCCMessage.do',
				data : {
					'processCC' : processCC,
					'processInstanceId' : GetQueryString('processInstanceId'),
					'receiverCC' : receiverCC
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					for(var x=0;x<json.length;x++){
					   sendWebsocket(json[x]);
					}
					$(".shenPi-send-box").hide();
					layer.alert("抄送成功！");
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员!");
				}
			});
}
/** 审批页----抄送----人员选择框* */
function selectCCPerson(groupId,pageNo,spanId) {
	userChoose();
	if (spanId != undefined && spanId != '') {
		userSpanId = spanId;
	}
	var loginName = $("#searchCCText").val();
	if (typeof(groupId) == "undefined" || '-1' == groupId) {
		groupId = "";
	}
	if (isNaN(pageNo) || pageNo == '') {
		pageNo = 0;
	}
	var addBasic = '1';
	pageNo = parseInt(addBasic) + parseInt(pageNo);
	$.ajax({
				url : 'user/selectAllUserForSelect.do',
				data : {
					'groupId' : groupId,
					'loginName' : loginName,
					'pageNo' : pageNo
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
					var html = '';
					if (null == json.list || "" == json.list) {
						html += '<tr>';
						html += '<td colspan="3" align="center">--没有数据--</td>';
						html += '</tr>';
						$('#ccUserList').html(html);
						$(".shenPi-send-box-chooseUser").show();
					} else {
						var list = json.list;
						var count = json.count;
						var pageNo = json.pageNo;
						var pageSize = json.pageSize;
						for (var i = 0; i < list.length; i++) {
							var user = list[i];
							html += '<tr>';
							html += '<td class="choose-table-input">'
									+ '<input type="checkbox" onclick="checkOneUsercc('
									+ user.userId
									+ ')" name="checkOneUsercc" value="'
									+ user.loginName + '">' + '</td>';
							html += '<td>' + user.userName + '</td>';
							html += '<td>' + user.loginName + '</td>';
							html += '</tr>';
						}
						// 分页插件
						if ($("#Paginationcc").html().length == '') {
							$("#Paginationcc").pagination(count, {
										items_per_page : pageSize,
										num_edge_entries : pageNo,
										num_display_entries : 8,
										callback : function(pageNo, panel) {
											if (count == null) {
												selectCCPerson(groupId, pageNo);
											}
										},
										link_to : "javascript:void(0);"
									});
						}
						$('#ccUserList').html(html);
						count = null;
						$(".shenPi-send-box-chooseUser").show();
					}
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}
/**选择抄送人员**/
function sendSelectUsers(){
    var ids = "";
	$("input[name='checkOneUsercc']:checkbox:checked").each(function() {
				ids = ids + $(this).val() + ",";
			});
	if (ids == "") {
		layer.alert("请选择执行人！");
		return;
	}
	$("#ccSelectUser").val(ids.slice(0, -1));
	$(".shenPi-send-box-chooseUser").hide();
}
/**保存选择de数据手册**/
 function selectDatesheetFromshenpi(){
    var treeObj = $.fn.zTree.getZTreeObj("datasheetTree");
	var nodes=treeObj.getChangeCheckedNodes();
	var selectDatasheet= new Array();
	if(null == nodes ||nodes.length == 0){
		layer.alert("请选择数据手册！");
		return;
	}
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].checked==true && nodes[i].id != "-1"){
			var filename = nodes[i].name
            var index1=filename.lastIndexOf(".");  
            var index2=filename.length;
            var postf=filename.substring(index1,index2);//后缀名  
            if(postf != ".pdf"){
              layer.alert("请选择pdf格式的数据手册!");
              return;
            }
			if(i == nodes.length-1){
			   selectDatasheet.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''+nodes[i].name+'\')">'+nodes[i].name+'</a>');
			}else{
			   selectDatasheet.push('<a style="cursor:pointer" onclick="viewPdfFromsp(\''+nodes[i].name+'\')">'+nodes[i].name+'</a>'+',');
			}
		}
	}
	$("#addDatesheet").html(selectDatasheet);
	$(".parts-producthand-window").hide();
}
/**pdf预览**/
function viewPdfFromsp(_this){
	if("" == _this || null == _this){
		layer.alert("该器件下没有相对应的数据手册！");
		return;
	}
	var pdfNum = _this.split(",");
	$.ajax({
		url : getContextPathForWS()+"/partComponentArrt/setPdfnameSession.do",
		data:'files='+_this,
		cache : false,
		type: 'post',
		dataType : "json",
		success : function(json) {
			if(json == "notFound"){
				layer.alert("服务器找不到相对应的数据手册！");
			}else{
			  for(var i=0;i<json.length;i++){
				  window.location.href=getContextPathForWS()+"/generic/web/viewer.html?file="+getContextPathForWS()+"/partComponentArrt/displayPDF.do";				   
			   }
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
 /******点击流程设计中配置按钮，弹出配置弹窗*****/
var processDefId1="";
function showProcessConfigDia(processDefId){
	processDefId1=processDefId;
	$.ajax({
			url : "ProcessConfigureController/initConfigureDia.do",
			data:'processDefId='+processDefId,
			cache : false,
			type: 'post',
			dataType : "json",
			success : function(json) {
				var userTaskList=json.userTaskList;
				var html="<option value='0'>请选择</option>";
				for(var i=0;i<userTaskList.length;i++){
					var key=userTaskList[i].key;
					var name=userTaskList[i].name;
					html+="<option value='"+key+"'>"+name+"</option>";
				}
				$("#processTask").html(html);
				
				$("#normalAttrs").prop('checked',false);
				$("#qualityAttrs").prop('checked',false);
				$("#designAttrs").prop('checked',false);
				$("#purchaseAttrs").prop('checked',false);
				$(".flow-allocation-window").show();
				
			},
			error : function() {
				layer.alert("数据连接异常,注册失败！");
			}
		});
}
//选择某个审批节点时 获取其配置信息
function getProcessConfig(){
	var taskKey=$("#processTask").val();
	
	$.ajax({
			url : "ProcessConfigureController/getProcessConfigure.do",
			data:{'taskKey':taskKey,'processDefId':processDefId1},
			cache : false,
			type: 'post',
			dataType : "json",
			success : function(json) {
				var processConfigure=json.processConfigure;
				if(processConfigure!='0'){
					if(processConfigure.normalAttrs=='1'){
						$("#normalAttrs").prop('checked',true);
					}else{
						$("#normalAttrs").prop('checked',false);
					}
					if(processConfigure.qualityAttrs=='1'){
						$("#qualityAttrs").prop('checked',true);
					}else{
						$("#qualityAttrs").prop('checked',false);
					}
					if(processConfigure.designAttrs=='1'){
						$("#designAttrs").prop('checked',true);
					}else{
						$("#designAttrs").prop('checked',false);
					}
					if(processConfigure.purchaseAttrs=='1'){
						$("#purchaseAttrs").prop('checked',true);
					}else{
						$("#purchaseAttrs").prop('checked',false);
					}
				}else{
					$("#normalAttrs").prop('checked',false);
					$("#qualityAttrs").prop('checked',false);
					$("#designAttrs").prop('checked',false);
					$("#purchaseAttrs").prop('checked',false);
				}
			},
			error : function() {
				layer.alert("数据连接异常,注册失败！");
			}
		});
}

//保存某个审批节点的配置信息
function saveProcessConfig(){
	var taskKey=$("#processTask").val();
	if(taskKey=='0'){
		layer.alert("请选择一个审批节点");
		return;
	}
	var normalAttrs=$("#normalAttrs").prop('checked')==true?'1':'0';
	var qualityAttrs=$("#qualityAttrs").prop('checked')==true?'1':'0';
	var designAttrs=$("#designAttrs").prop('checked')==true?'1':'0';
	var purchaseAttrs=$("#purchaseAttrs").prop('checked')==true?'1':'0';
	$.ajax({
			url : "ProcessConfigureController/insertOrUpdateProcessConfigure.do",
			data:{'taskKey':taskKey,'processDefId':processDefId1,'normalAttrs':normalAttrs,'qualityAttrs':qualityAttrs,'designAttrs':designAttrs,'purchaseAttrs':purchaseAttrs},
			cache : false,
			type: 'post',
			dataType : "json",
			success : function(json) {
				layer.alert("保存成功！")
			},
			error : function() {
				layer.alert("数据连接异常,注册失败！");
			}
		});
}
$("body").on("click", ".flow-allocation-window-close", function() {
	$(".flow-allocation-window").hide();
});
//点击ITEM选择按钮 弹出类似器件弹窗
var simpleCount;
//器件分类树 生成
function createPartDataTreeSimple() {
	var partData=new Object();
	var str = "";
	var setting = {
			view : {
				showIcon : false,
				addDiyDom: addPDDiy,
				showLine: false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : partTypNodeOnClickSimple
			}
		};
	
	$.ajax({
		url : "partClassController/getPartTypeTree.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(partData)
		},
		success : function(json) {// 双重循环生成dl、dd二级树形结构
			var partTypStr="";
			var typeTreeList = json.typeTreeList;
			$.fn.zTree.init($("#partTreeToSimple"), setting, typeTreeList);
		}
	});
}
//器件数据查询
function showPartToSimple(partData,pageNo) {
	if(isNaN(pageNo)){
		pageNo = 0;
	}
	var addBasic = '1';
    pageNo = parseInt(addBasic)+parseInt(pageNo);
    var pageSize='7';
	$.ajax({
		url : "partDataController/selectPartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(partData),
			"pageNo":pageNo,
			"pageSize":pageSize
		},
		success : function(json) {//生成页面标签
			//展示数据
			var resultList = json.resultList;
			var str = "";
			for (var i = 0; i < resultList.length; i++) {
				var onePartData = resultList[i];
				//主数据table
				var ischecked="";
				if(onePartData.id == $("#radioVal").html()){
					ischecked = "checked='true'";
				}
				var headerStr = "<tr class=\"parts-search-data\"><td class=\"search-checkbox\"><input name=\"partRadio\" type=\"radio\""+ischecked+" onclick=\"radioPartDataSelect(this)\"  value=\""+onePartData.id+"\"/></td>"
				headerStr += "<td  style=\"width:118px\">" + onePartData.PartCode + "</td>";
				headerStr += "<td  style=\"width:118px\">" + onePartData.Part_Type + "</td>";
				headerStr += "<td  style=\"width:128px\">" + onePartData.ITEM + "</td>";
				headerStr += "<td>" + onePartData.Manufacturer + "</td>";
				headerStr += "<td>" + onePartData.KeyComponent + "</td>";
				str += headerStr;
			}
			//分页
			if(simpleCount != json.count){
				simpleCount=json.count;
				$("#partDataPageSimple").pagination(json.count,{
					items_per_page : pageSize,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(resultList==null){
							showPartToSimple(partData,pageNo);
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			$("#dataTableSimple").html(str);
			resultList=null;
		},
		error : function() {
			layer.alert("服务器异常");
		}
	});
}
//点击选中类似器件按钮
function showpartDataDiaLog(){
	simpleCount = 0;
	var itemSimple = $("input[name='ITEM']").val();
	$("#itemSimple").val(itemSimple);
	createPartDataTreeSimple();
	var partData=new Object();
	partData.item=itemSimple;
	showPartToSimple(partData);
   $('.flow-replaceChoose-end').show();
}
//节点点击事件
var treePartTypesSimple="";
function partTypNodeOnClickSimple(event, treeId, treeNode, clickFlag){
	var partData=new Object();
	getTreePartTypesSimple(treeNode);
	treePartTypesSimple=treePartTypesSimple.substring(0,treePartTypesSimple.length-1);
	partData.partTypes=treePartTypesSimple;
	partData.tempPartMark=treeNode.tempPartMark+"";
	showPartToSimple(partData);
	treePartTypesSimple="";
}
//获取节点以及子节点的part_type
function getTreePartTypesSimple(treeNode){
	if(treeNode.children==undefined){
		treePartTypesSimple+=treeNode.name.split("※")[0]+",";
		return;
	}else{
		treePartTypesSimple+=treeNode.name.split("※")[0]+",";
		for(var i=0;i<treeNode.children.length;i++){
			getTreePartTypesSimple(treeNode.children[i]);
		}
	}
}
//类似器件界面选择一个器件
function radioPartDataSelect(data){
   $("#radioVal").html($(data).val());
}
//类似器件点击确定按钮
function simplePartDataSelect() {
	var partId = $("#radioVal").html();
	if(!isNaN(partId)){
	  $(".dynamicFile").parent().remove();//清除动态添加字段
	  initpartApplyPage(partId,"similar");
	}
	$(".flow-replaceChoose-end").hide();
}
//类似器件多条件查询
function similarQuery(){
    var partData=new Object();
	var itemSimple=$("#itemSimple").val();
	partData.item=itemSimple;
	showPartToSimple(partData);
}
////根据选择的设计工具 进行原理图符号上传控制
//function controlPcbFiled(){
//	return;
//   var selectVal = $("select[name='design_tool']").val();//设计工具字段值
//   if(selectVal == "Concept HDL"){
//       $("input[name='Sym_for_CAP']").hide();
//       $("#Sym_for_ADR_A").show();
//   }else{
//   	   $("input[name='Sym_for_CAP']").show();
//   	   $("#Sym_for_ADR_A").hide();
//   }
//}
// 流程类别列表初始化
function initCategoryList() {
	loadProperties();//国际化
	$.ajax({
		url : "ProcessCategoryController/selectProcessCategoryList.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {},
		success : function(json) {
			var list = json.list;
			var html = '';
			if (null == list || list.length == 0) {
				html += '<tr>';
				html += '<td colspan="8" align="center">'
						+ $.i18n.prop("noData") + '</td>';
				html += '</tr>';
			} else {
				var addBasic = '1';
				for (var x = 0; x < list.length; x++) {
					var pc = list[x];
					var index = parseInt(addBasic) + parseInt(x);
					html += '<tr>';
					html += '<td>' + '<input type="checkbox" name="selectOneCategory" value="'+pc.id+'"/>' + '</td>';
					html += '<td style="display:none">' + pc.id + '</td>';
					html += '<td>' + index + '</td>';
					html += '<td>' + pc.categoryName + '</td>';
					html += '<td>' + pc.categorySign + '</td>';
					html += '<td>' + pc.createPerson + '</td>';
					html += '<td>' + transformationDate(pc.createTime) + '</td>';
					html += '<td>' + pc.remark + '</td>';
					html += '<td>'
							+ '<a class="btn btn-danger btn-xs categoryEditBtn" onclick="updateProcessCategoryBefore(this)"><span class="glyphicon glyphicon-edit"></span>&nbsp;'+$.i18n.prop("updateBtn")+'</a>'
							+ '</td>';
					html += '</tr>';
				}
			}
			$("#initCategoryList").html(html);
		},
		error : function() {
			layer.alert($.i18n.prop("alertError"));
		}
	});
}
//添加流程分类信息
function insertProcessCategory(){
    var name = $("input[name='categoryName']").val();
    var sign = $("input[name='categorySign']").val();
    var remark = $("input[name='remark']").val();
    if('' == name || null == name){
        layer.alert("类别名称不能为空！");
        return;
    }
    if('' == sign || null == sign){
        layer.alert("类别标识不能为空！");
        return;
    }
    $.ajax({
		url : "ProcessCategoryController/insertProcessCategory.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {'name':name,'sign':sign,'remark':remark},
		success : function(json) {
			if("existence" == json){
				layer.alert("类别标识不能重复！");
				$("input[name='categorySign']").val('');
				return;
			}
			layer.alert("添加成功！");
			initCategoryList();//刷新类别列表
			$(".categoryAddWindow").hide();
			$("input[name='categoryName']").val('');
            $("input[name='categorySign']").val('');
            $("input[name='remark']").val('');
		},
		error : function() {
			layer.alert("数据异常！");
		}
	});
}
// 修改流程分类信息之前
function updateProcessCategoryBefore(_this) {
	var tr = $(_this).parent().parent();
	var tdList = tr.find("td");
	$("input[name='CategoryId']").val($(tdList[1]).html());
	$("input[name='updateCategoryName']").val($(tdList[3]).html());
	$("input[name='updateCategorySign']").val($(tdList[4]).html());
	$("input[name='updateRemark']").val($(tdList[7]).html());
	$(".categoryEditWindow").show();
}
// 修改流程分类信息
function updateProcessCategory(){
    var id = $("input[name='CategoryId']").val();
    var name = $("input[name='updateCategoryName']").val();
	var sign = $("input[name='updateCategorySign']").val();
	var remark = $("input[name='updateRemark']").val();
	if('' == name || null == name){
        layer.alert("类别名称不能为空！");
        return;
    }
    if('' == sign || null == sign){
        layer.alert("类别标识不能为空！");
        return;
    }
    $.ajax({
		url : "ProcessCategoryController/updateProcessCategory.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {'name':name,'sign':sign,'remark':remark,'id':id},
		success : function(json) {
			layer.alert("修改成功！");
			initCategoryList();//刷新类别列表
			$(".categoryEditWindow").hide();
		},
		error : function() {
			layer.alert("数据异常！");
		}
	});
}
//日期格式转换
function transformationDate(v) {
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
  //流程类别----复选框(全选)
function selectAllCategory() {
	if ($("#selectAllCategory").is(':checked')) {
		$("input[name='selectOneCategory']").prop("checked", true);
	} else {
		$("input[name='selectOneCategory']").prop("checked", false);
	}
}
//批量删除流程类别信息
function batchDeleteCategoty() {
	var ids = new Array;
	loadProperties();//国际化
	$("input[name='selectOneCategory']:checkbox:checked").each(function() {
				ids.push($(this).val());
			});
	if (ids.length == 0) {
		layer.alert($.i18n.prop("batchDeleteTip"));
		return;
	}
	//询问框
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
			// 按钮
		}, function() {
		$.ajax({
					url : 'ProcessCategoryController/deleteProcessCategory.do',
					data : 'ids=' + JSON.stringify(ids),
					dataType : 'json',
					cache : false,
					success : function(json) {
						layer.alert("删除成功！");
			            initCategoryList();//刷新类别列表
					},
					error : function() {
						layer.alert("服务器连接异常，请联系管理员！");
					}
				})
	});
}
// 在线流程设计----类别加载
function loadProcessCategory() {
	$.ajax({
				url : "ProcessCategoryController/selectProcessCategoryList.do",
				dataType : "json",
				cache : false,
				type : "post",
				success : function(json) {
					var list = json.list;
					var html = '';
					var liHtml = '';
					if(null == list || list.length<=0){
						html += '<option value="">请选择</option>';
					}else{
					   for(var i=0;i<list.length;i++){
					   	var pc = list[i];
					   	var value = pc.categorySign == "partProcess" ? "0":pc.categorySign;
					     html += '<option value="'+value+'">'+pc.categoryName+'</option>';
					     liHtml += '<li class="shenqing" name="'+value+'"><a>'+pc.categoryName+'</a></li>';
					   }
					}
					$("#formType").html(html);
					$(".flow-manage-ul").html(liHtml);
				},
				error : function() {
					layer.alert("数据异常！");
				}
			});
}