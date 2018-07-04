<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/cms-flow-UserChoose.css" />
<script type="text/javascript" src="scripts/doublebox-bootstrap.js"></script>

<!-- 用户选择弹窗new -->
<div class="UserChooseWindow SPBox">
	<div class="UserChooseWindow-header bg-gray">
		<a><span class="glyphicon glyphicon-tasks"></span> <fmt:message
				key="selectUser" /></a>
		<button type="button" class="close UserChooseWindow-close"
			aria-label="Close" onclick="closeUserChoose();">
			<span>&times;</span>
		</button>
	</div>
	<div class="UserChooseWindow-body">
		<div class="nodeUser-choose-tree1">
			<div id="ejstree1" class=" jstree jstree-1 jstree-default"
				role="tree" aria-multiselectable="true" tabindex="0"
				aria-activedescendant="ej1_2" aria-busy="false">
				<ul id="userTree" class="ztree"></ul>
			</div>
		</div>
		<div class="choose-table-box">
			<div class="ue-container">
				<select multiple="multiple" size="10" name="doublebox" class="demo"
					id="userDBSelect"></select>
			</div>
		</div>
		<div class="choose-bottom">
			<a class="btn btn-gray" onclick="closeDialog('UserChooseWindow')"><span
				class="glyphicon glyphicon-remove-circle"></span> <fmt:message
					key="resetBtn" /></a> <a class="btn btn-gray"
				onclick="processSelectUsers()"><span
				class="glyphicon glyphicon-ok-circle"></span> <fmt:message
					key="determine" /></a>
		</div>
	</div>
</div>
<script type="text/javascript">
//显示弹窗
function userChoose(){
    $(".UserChooseWindow").show();
}
//关闭弹窗按钮
function closeUserChoose(){
    $(".UserChooseWindow").hide();
}
//选择成员组树结构
function initgroupUserTree1() {
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
							onClick : userTreeOnClick1
						}
					};
					if (null != list && "" != list) {
						$.fn.zTree.init($("#userTree"), settingUser, list);// 加载树结构
						var treeObj = $.fn.zTree.getZTreeObj("userTree");
						treeObj.expandAll(true);//展开全部节点
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
/** 点击user树某一个节点* */
function userTreeOnClick1(event, treeId, treeNode, clickFlag) {
	initUserList(treeNode.id,'','','1');
}
//审批界面选择执行人 
var userNameSpanId="";//页面多个span调用同一个userlist时使用
var userLoginNameSpanId="";
/**
 * 第一个参数是组的id，第二个是页面存name的spanId，第三个是页面存登录名span的id，第四个是一个状态控制是否刷新树
 */
function initUserList( groupId, nameSpanId,loginNamespanId,state ) {
	if(state==undefined||state=='0'){
		initgroupUserTree1();//组成员树加载
	}
	if(loginNamespanId!=undefined&&loginNamespanId!=''){
		userLoginNameSpanId=loginNamespanId;
	}
	if(nameSpanId!=undefined&&nameSpanId!=''){
		userNameSpanId=nameSpanId;
	}
	if (typeof(groupId) == "undefined" || '-1' == groupId) {
		groupId = "";
	}
	/******生成右边框用户数据********/
	var rightHtml="";//option
	var selectedStr=",";//用于筛选后台查询到的用户
	if(state=='1'){//点击部门节点时，刷新数据。此时右边框中的用户不变，只改变左边框的数据。
		var selectedArr=$('#userDBSelect option:selected');//已选择的option集合
		var loginNameStr=",";
		for(var i=0;i<selectedArr.length;i++){
			selectedStr+=selectedArr[i].value+",";
			rightHtml+="<option value=\""+selectedArr[i].value+"\" selected='selected'>"+selectedArr[i].text+"</option>";
	 	}
	}else if(state=='0'){//点击选择按钮时，获取页面已选的用户，构造右边框数据。
		selectedStr+=$("#"+userLoginNameSpanId).is("span")?($('#'+loginNamespanId).html()+","):($('#'+loginNamespanId).val()+",");//已选择的option集合
		var nameStr=$("#"+userNameSpanId).is("span")?(","+$('#'+userNameSpanId).html()+","):(","+$('#'+userNameSpanId).val()+",");//已选择的option集合
		var selectedLoginArr=selectedStr.split(",");
		var selectedNameArr=nameStr.split(",");
		for(var i=0;i<selectedLoginArr.length;i++){
			if(selectedLoginArr[i]==''){
				continue;
			}
			rightHtml+="<option value=\""+selectedLoginArr[i]+"\" selected='selected'>"+selectedNameArr[i]+"</option>";
		}
	}
	/**************************************/
	$.ajax({
				url : 'user/selectAllUserForDBSelect.do',
				data : {
					'groupId' : groupId
				},
				type : 'post',
				dataType : 'json',
				cache : false,
				success : function(json) {
						var list = json.list;
						var html="";
						for (var i = 0; i < list.length; i++) {
							var user = list[i];
							if(selectedStr.indexOf(","+user.loginName+",")==-1){//判断是否在右边框内已生成
								html+="<option value=\""+user.loginName+"\">"+user.userName+"</option>";
							}
						}
						$('#userDBSelect').html(html+rightHtml);
						$('#userDBSelect').bootstrapDualListbox('refresh');
						$(".UserChooseWindow").show();
				},
				error : function() {
					layer.alert("数据连接异常,请联系管理员！");
				}
			});
}
//将选择的流程执行人显示到div上
function processSelectUsers() {
	var selectedArr=$('#userDBSelect option:selected');//已选择的option集合
	var loginNameStr="";
	var userNameStr="";
	for(var i=0;i<selectedArr.length;i++){
 		  loginNameStr+=selectedArr[i].value+",";//登录名
 		  userNameStr+=selectedArr[i].text+",";//用户姓名
 	}
	if($("#"+userLoginNameSpanId).is("span")){
		$("#"+userLoginNameSpanId).html(loginNameStr==""?"":loginNameStr.slice(0, -1));
	}else{
		$("#"+userLoginNameSpanId).val(loginNameStr==""?"":loginNameStr.slice(0, -1));
	}
	if($("#"+userNameSpanId).is("span")){
		$("#"+userNameSpanId).html(userNameStr==""?"":userNameStr.slice(0, -1));
	}else{
		$("#"+userNameSpanId).val(userNameStr==""?"":userNameStr.slice(0, -1));
	}
	$(".UserChooseWindow").hide();
}

	$(document).ready(function() {
		$('.UserChooseWindow').draggable();
		var userDBSelect = $('#userDBSelect').doublebox({
			nonSelectedListLabel : '全部人员',
			selectedListLabel : '已选择人员',
			preserveSelectionOnMove : 'moved',
			moveOnSelect : false,
			nonSelectedList : [],
			selectedList : [],
			optionValue : "loginName",
			optionText : "userName",
			doubleMove : true
		});
	});
</script>
