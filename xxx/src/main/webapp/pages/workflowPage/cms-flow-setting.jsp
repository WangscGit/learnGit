<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>宇航元器件选用平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
<%@include file="/base.jsp"%>
<link rel="stylesheet" href="css/cms-headerPublic.css" />
<link rel="stylesheet" href="css/cms-flow-setting.css" />
<link rel="stylesheet" href="js-tree/dist/themes/default/style.css">
<script type="text/javascript" src="js-tree/dist/jstree.min.js"></script>
<!-- ZTree样式引入 -->
<link rel="stylesheet" href="zTreeStyle/zTreeStylepart.css"
	type="text/css" />
<link rel="stylesheet" href="zTreeStyle/newzTreeStyle.css"
	type="text/css" />
<!-- ZTree核心js引入 -->
<script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js-tree/jquery.ztree.excheck.js"></script>
<script type="text/javascript">
	$(function() {
		var processDefinitionId = GetQueryString('processDefinitionId');
		$.ajax({
					url : 'WorkflowMainsController/getPngForSetting.do',
					data : 'processDefinitionId=' + processDefinitionId,
					dataType : 'json',
					cache : false,
					success : function(json) {
						var pngHtml = '';
						var userTaskHtml = '';
						pngHtml += '<img src="/cms_cloudy/workflowImg/'+json.pngName+'" alt=""/>';
						var utList = json.utList;
						if (utList.length == 0) {
							userTaskHtml += '<div class="alert alert-danger" role="alert">该流程无需设置节点人员!</div>';
						} else {
							for (var x = 0; x < utList.length; x++) {
								userTaskHtml+="<div>";
								userTaskHtml += "<b>"+utList[x].name+"</b>";
								userTaskHtml += "</br>";
								userTaskHtml += "</br>";
								userTaskHtml +="<b class=\"shenpi\">"+"审批关系:"+ "</b>";
								var pList = utList[x].pList;
								var loginNameStr = "";
								var userNameStr = "";
								var select='';
								if (pList != undefined && pList.length > 0) {
									for (var i = 0; i < pList.length; i++) {
										loginNameStr += pList[i].loginName
												+ ",";
										userNameStr += pList[i].userName + ",";
									}
									loginNameStr = loginNameStr.substring(0,
											loginNameStr.length - 1);
									userNameStr = userNameStr.substring(0,
											userNameStr.length - 1);
									if(pList[0].utType=='and'){
										select="<option value=\"or\">or</option><option value=\"and\" selected=\"selected\">and</option>";
									}else{
										select="<option value=\"or\" selected=\"selected\">or</option><option value=\"and\" >and</option>";
									}
									
								}else{
									select="<option value=\"or\">or</option><option value=\"and\" >and</option>";
								}
								
								userTaskHtml+="<select name=\"typeOption\" id=\"utType"+utList[x].id+"\" class=\"form-control\">"+select+"</select>"
								        +"</br>"
								        +"</br>"
								        +"<b class=\"worker\">"+"执行人:"+ "</b>"
										+ "<a onclick=\"initUserList('','"
										+ utList[x].id
										+ "_name','"
										+ utList[x].id
										+ "','0')\" class=\"btn btn-black btn-xs\"><span class=\"glyphicon glyphicon-plus node-user-add\"></span></a>";
										
								
								userTaskHtml += "<span name='loginNameSpan' id='"+utList[x].id+"' style='display:none'>"
										+ loginNameStr
										+ "</span><span class=\"UserName\" id='"+utList[x].id+"_name'>"
										+ userNameStr + "</span>"
										+"</div>";
							}
						}
						//动态插入到页面中
						$("#flowPng").html(pngHtml);
						$("#userTaskInsert").html(userTaskHtml);
					},
					error : function() {
						layer.alert("服务器连接异常，请联系管理员!");
					}
				});
	});

	//保存流程配置人员信息
	function saveProcUser() {
		var processDefinitionId = GetQueryString('processDefinitionId');
		var lnsArr=$("span[name='loginNameSpan']");
		var procUserArr=new Array();
		for(var i=0;i<lnsArr.length;i++){
			var loginNameStr=lnsArr.eq(i).html();
			var taskKey=lnsArr.eq(i).attr("id");
			var userNameStr=$("#"+taskKey+"_name").html();
			var utType=$("#utType"+taskKey).val();
			if(loginNameStr==''){
				continue;
			}
			var lnArr=loginNameStr.split(",");
			var unArr=userNameStr.split(",");
			for(var j=0;j<lnArr.length;j++){
				var procUser=new Object();
				procUser.loginName=lnArr[j];
				procUser.userName=unArr[j];
				procUser.taskDefKey=taskKey;
				procUser.procDefId=processDefinitionId;
				procUser.utType=utType;
				procUserArr.push(procUser);
			}
		}
		$.ajax({
			url : "WorkflowMainsController/saveProcUser.do",
			dataType : "json",
			data :{'procUserArr':JSON.stringify(procUserArr)},
			cache : false,
			type : "post",
			success : function(json) {
				layer.alert(json.message);
			},
			error : function() {
			}
		});
	}
</script>
</head>
<body>
	<%@include file="/public.jsp"%>
	<div class="containerAll">
		<div class="containerAllPage">
			<div id="main">
				<%@include file="/header.jsp"%>
				<div class="page-message">
					<h3>
						<span class="label label-default">Now</span><a href="pages/loginpage/index.jsp">首页</a>><a href="pages/workflowPage/cms-flow.jsp">流程设计</a>><a
							class="active" >配置流程</a>
					</h3>
				</div>
				<div class="table-responsive">
					<div role="tabpanel" class="Tab">
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="flow-setting">
								<div class="flow-picture">
									<div class="flow-picture-head">
										<span>流程图</span>
									</div>
									<div class="flow-picture-body" id="flowPng"></div>
								</div>
								<div class="flow-accordion">
									<div class="flow-accordion-head">
										<span>流程配置</span>
									</div>
									<div class="flow-accordion-body">
										<div class="flow-accordion-body-head">
											<a class="btn btn-black flow-setting-save" onclick="saveProcUser();"><span
												class="glyphicon glyphicon-floppy-disk"></span> 保存配置</a>
												
												<a class="btn btn-black flow-setting-save" onclick="javascript:history.go(-1);"><span
												class="glyphicon glyphicon-floppy-disk"></span> 返回</a>
										</div>
										<div class="panel-group" id="accordion" role="tablist"
											aria-multiselectable="true">
											<div class="panel panel-default">
												<div class="panel-heading" role="tab" id="headingTwo">
													<h4 class="panel-title">
														<a class="collapsed" data-toggle="collapse"
															data-parent="#accordion" href="#collapseTwo"
															aria-expanded="false" aria-controls="collapseTwo">
															节点人员 </a>
													</h4>
												</div>
												<div role="tabpanel" aria-labelledby="headingTwo">
													<div class="panel-body">
														<div class="well node-user" id="userTaskInsert"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- 用户选择弹窗 -->
			<%@include file="/UserChoose.jsp"%>

			<%@include file="/footer.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="scripts/javascript.js"></script>
	<script type="text/javascript"
		src="scripts/workFlowJavascript.js"></script>
	<script>
		$(function() {
			$('.must').tooltip()
			$(
					'.flow-setting-addUser,.flow-setting-nodeUser,.flow-setting-nodeUser-choose,.flow-setting-nodeBtn,.business-object-window')
					.draggable();
		});
		$(function() {
			$('#jstree1').jstree();
		});
	</script>
</body>
</html>