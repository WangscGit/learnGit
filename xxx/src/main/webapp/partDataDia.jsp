<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="css/cms-flow-manage-ketidai.css" />
<!-- 可替代物料选择弹窗 -->
   <div class="flow-replaceChoose">
    <div class="flow-replaceChoose-header bg-gray">
        <a><span class="glyphicon glyphicon-tasks"></span>  <fmt:message  key="minute-substituteMaterial"/></a>
        <button type="button" class="close flow-replaceChoose-close" onclick="closeThisDia();" aria-label="Close"><span>&times;</span></button>
    </div>
    <div class="flow-replaceChoose-body">   
       <div class="replaceChooseLeft"><ul id="partTreedia" class="ztree"></ul></div>
       <div class="replaceChooseRight">
         <div class="replaceChooseSearch">
         	<span class="title">已选物料：</span><span id="selectPDSpan"></span>
         	<a class="btn btn-danger right" onclick="diaPDSearch();"><span class="glyphicon glyphicon-search"></span>&nbsp;<fmt:message  key="serachBtn"/></a>
         	<input type="text" id="ITEM1" class="form-control" placeholder="<fmt:message  key="replaceSearchPh"/>"/>                
         </div>
         <div class="tableBox">
           <table class="table table-striped table-hover table-bordered">
                <thead>
                    <tr>
                          <td></td>
                          <td>物料编码</td>
                          <td>元器件名称</td>
                          <td>规格型号</td>
                          <td>制造商</td>
                          <td>质量等级</td>                         
                    </tr>
                </thead>
                <tbody id="dataTable1">
                        
                </tbody>
           </table>
           </div>
       </div>
       <div class="table-footers">
        	<div class="pagination" id="partDataPage1"></div>
       </div>
       <div class="nodeUser-choose-bottom">
            <a class="btn btn-gray" onclick="closeThisDia();"><span class="glyphicon glyphicon-remove-circle"></span> <fmt:message  key="resetBtn"/></a>
            <a class="btn btn-gray" onclick="diaPartDataSelect()"><span class="glyphicon glyphicon-ok-circle"></span> <fmt:message  key="determine"/></a>
        </div>
    </div>
</div>

<script type="text/javascript">
/******可替代物料选择按钮，弹出弹窗*****/
function showpartDataDia(){
	cou1=0;
	partArray=new Array();
    $("#selectPDSpan").html("");
	$("#ITEM1").val("");
	createPartDataTree();
	var partData=new Object();
	showData1(partData);
    $(".flow-replaceChoose").show();
}
//点击搜索按钮
function diaPDSearch(){
	var partData=new Object();
	var ITEM1=$("#ITEM1").val();
	partData.item=ITEM1;
	showData1(partData);
}
/**
 * 1.根据后台传递的json循环生成dl、dt、dd标签 
 * 2.显示所有的分类信息
 */
function createPartDataTree() {
	var partData=new Object();
	var str = "";
	var setting = {
			view : {
				//removeHoverDom: removeHoverDom,
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
				onClick : partTypNodeOnClick1
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
			$.fn.zTree.init($("#partTreedia"), setting, typeTreeList);
		}
	});
}
/** 
 * 1.ajax调用后台方法获取查询到的数据，动态展示到页面
 * pageNo:当前页；partData：条件
 */
var cou1;//总数，用于控制添加时是否刷新分页信息
function showData1(partData,pageNo) {
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
				if(partArray.length>0){
					if((','+partArray.join(",")+',').indexOf(','+onePartData.PartCode+',')!=-1){
						ischecked="checked='true'";
					}
				}
				var headerStr = "<tr class=\"parts-search-data\"><td class=\"search-checkbox\"><input name=\"partCheck\" type=\"checkbox\""+ischecked+" onclick=\"checkPartDataSelect(this)\" value=\""+onePartData.PartCode+"\"/></td>"
				headerStr += "<td  style=\"width:118px\">" + onePartData.PartCode + "</td>";
				headerStr += "<td  style=\"width:118px\">" + onePartData.Part_Type + "</td>";
				headerStr += "<td  style=\"width:128px\">" + onePartData.ITEM + "</td>";
				headerStr += "<td>" + onePartData.Manufacturer + "</td>";
				headerStr += "<td>" + onePartData.KeyComponent + "</td>";
// 				headerStr += "<td>" + "<a class=\"data-book\" href=\"javascript:viewPdf('"+onePartData.Datesheet+"','"+onePartData.PartNumber+"','"+onePartData.Part_Type+"')\"><i class=\"PDF\"></i>"+"Datesheets"+"</a>" + "</td>";
// 				headerStr += "<td  style=\"display:none\"><input class=\"form-control\"  type=\"text\" value=\"1\" name=\"proNum\" class=\"\"/></td>";
// 				headerStr += "<td  id='"+onePartData.id+"' name=\"processState\" style=\"display:none\">" + onePartData.process_state + "</td>";
				str += headerStr;
			}
			//分页
			if(cou1 != json.count){
				cou1=json.count;
				$("#partDataPage1").pagination(json.count,{
					items_per_page : pageSize,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(resultList==null){
							showData1(partData,pageNo);
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			$("#dataTable1").html(str);
			resultList=null;
		},
		error : function() {
			layer.alert("服务器异常");
		}
	});
}
//节点点击事件
var treePartTypes1="";
function partTypNodeOnClick1(event, treeId, treeNode, clickFlag){
//	  var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
//    var r1 = window.location.search.substr(1).match(reg1);
//    var tempPartMark=""
//    if(r1!=null){
//    	tempPartMark=unescape(r1[2]);
//    }
	var partData=new Object();
	getTreePartTypes1(treeNode);
	treePartTypes1=treePartTypes1.substring(0,treePartTypes1.length-1);
	partData.partTypes=treePartTypes1;
	showData1(partData);
	treePartTypes1="";
}
//获取节点以及子节点的part_type
function getTreePartTypes1(treeNode){
	if(treeNode.children==undefined){
		treePartTypes1+=treeNode.name.split("※")[0]+",";
		return;
	}else{
		treePartTypes1+=treeNode.name.split("※")[0]+",";
		for(var i=0;i<treeNode.children.length;i++){
			getTreePartTypes1(treeNode.children[i]);
		}
	}
}
//数量生成单独标签
function  addPDDiy(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
	var aObj = $("#" + treeNode.tId + "_a");
	var spanObj = $("#" + treeNode.tId + "_span");
	var name=treeNode.name;
	var names=name.split("※");
// 	aObj.append("<b>("+names[1]+")</b>");
// 	if(treeNode.imgUrl==null||treeNode.imgUrl==undefined){
// 		aObj.prepend("<img src=''/>");
// 	}else{
// 		aObj.prepend("<img src='"+treeNode.imgUrl+"'/>");
// 	}
//	aObj.prepend("<img src='uploadImg/5f9efc49957249c4a2ab8c5f239f539e.png'/>");
	spanObj.html(names[0]);
}

/******复选框单击事件，记录选择的数据，翻页搜索不会丢失*****/
var partArray=new Array();
function checkPartDataSelect(_this){
	var ss=$(_this).prop("checked");
	if(ss){
		if(partArray.length==4){
			layer.alert("已选择4个物料");
			$(_this).prop("checked",false);
			return;
		}
		partArray.push($(_this).val());
	}else{
		partArray.splice($.inArray($(_this).val(), partArray), 1);
	}
	$("#selectPDSpan").html(partArray.join(","));
}
//点击弹窗的确认按钮
function diaPartDataSelect(){
	if(partArray.length!=0){
		$("#Alternative_Part_span").html(partArray.join(","));
	}
	$(".flow-replaceChoose").hide();
}
//关闭弹窗按钮
function closeThisDia(){
    $(".flow-replaceChoose").hide();
}

$(function() {
	$('.flow-replaceChoose').draggable();
});
</script>
