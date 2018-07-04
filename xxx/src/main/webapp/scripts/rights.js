/** 菜单权限、数据权限主要js */

//构建菜单
function foundMenu(){
	loadProperties();
	$.ajax({
		url : "HrRightsController/getMenuRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			var menuRightList=json.menuRightList;
			if(menuRightList==undefined){
				menuRightList=new Array;
			}
//			var dataRightList=json.dataRightList;
			var headerHtml="<ul class=\"nav navbar-nav index-nav left\"><li class=\"active1 navLi\"><a href=\"/cms_cloudy/pages/loginpage/index.jsp\" hidefocus=\"true\">"+$.i18n.prop("homePage")+"<span class=\"sr-only\">(current)</span></a></li>";
			var middleHtml="";
			var	shejiHtml="<li class=\"navLi\"><a href=\"/cms_cloudy/pages/parts/cms-parts.jsp\"  hidefocus=\"true\">"+$.i18n.prop("ComponentNavigator")+"</a></li>";
			var chanpinHtml="";
			for(var i=0;i<menuRightList.length;i++){
				var parentId=menuRightList[i].parentId;
				if(parentId==0){
					if(menuRightList[i].id==61){//产品结构管理
						chanpinHtml+="<li class=\"navLi\"><a href=\""+menuRightList[i].url+"\"  hidefocus=\"true\">"+$.i18n.prop(menuRightList[i].id)+"</a>";
						continue;
					}
					//添加根菜单
					middleHtml+="<li class=\"navLi\"><a href=\""+menuRightList[i].url+"\"  hidefocus=\"true\">"+$.i18n.prop(menuRightList[i].id)+"</a>";
					//循环添加子菜单
					middleHtml+="<ul>";
					for(var j=0;j<menuRightList.length;j++){
						if(menuRightList[j].parentId!=0&&menuRightList[j].parentId==menuRightList[i].id){
							middleHtml+="<li><a href=\""+menuRightList[j].url+"\">"+$.i18n.prop(menuRightList[j].id)+"</a></li>";
						}
					}
					middleHtml+="</ul>";
					middleHtml+="</li>";
					
				}
				
			}
			var footerHtml="<li class=\"navLi\"><a href=\"http://www.bjdihao.com.cn\" hidefocus=\"true\">"+$.i18n.prop("AboutUs")+"</a></li><li class=\"navLi\"><a hidefocus=\"true\">"+$.i18n.prop("resources")+"</a><ul><li><a href=\"javascript:download(1)\">"+$.i18n.prop("manualLoad")+"</a></li><li><a href=\"javascript:download(2)\">"+$.i18n.prop("clientLoad")+"</a></li></ul></li></ul>";
			$("#bs-example-navbar-collapse-1").prepend(headerHtml+chanpinHtml+shejiHtml+middleHtml+footerHtml);
			
			/******鼠标移入nav出现下拉菜单*****/
   		$(".navLi").hover(function(){
       	 		$(this).children("ul").show();
    		},function(){
        		$(this).children("ul").hide();
    		});
		},
		error : function() {
		}
	});
}

//元器件页面按钮权限
var saveToPro=0;//选用按钮权限，partdata.js的showData中使用
var historyRights=0;//历史信息权限，partdata.js的showData中使用
function foundDataRights(){
	loadProperties();
	$.ajax({
		url : "HrRightsController/getDataRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			var dataRightList=json.dataRightList;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			var html="";
			for(var i=0;i<dataRightList.length;i++){
				if(dataRightList[i].rightsNote=="y1"){
					/*html+="<a class=\"partsAdd-btn\"    onclick=\"goInsertPage();\"><img src=\"images/daochu.png\" />"+$.i18n.prop("addBtn")+"</a> ";*/
					html+="<a class=\"partsAdd-btn\"    onclick=\"goInsertPage();\"><i class=\"fa fa-plus-square fa-lg\"></i>"+$.i18n.prop("addBtn")+"</a> ";
//					html+="<a class=\"partsAdd-btn\"   onclick=\"addPartDataDialog();\" ><i></i>"+$.i18n.prop("addBtn")+"</a> ";
				}
				if(dataRightList[i].rightsNote=="y2"){
					html+="<a class=\"partsChange-btn\"   onclick=\"goEditPage();\"><i class=\"fa fa-pencil-square fa-lg\"></i>"+$.i18n.prop("updateBtn")+"</a> ";
//					html+="<a class=\"partsChange-btn\"   onclick=\"updatePartDataDialog();\"><i></i>"+$.i18n.prop("updateBtn")+"</a> ";
				}
				if(dataRightList[i].rightsNote=="y3"){
					html+="<a class=\"partsDelete-btn\"  onclick=\"deletePartData();\"><i class=\"fa fa-trash fa-lg\"></i>"+$.i18n.prop("deleteBtn")+"</a> ";
				}
			/*	if(dataRightList[i].rightsNote=="y5"){
					saveToPro=1;
					html+="<a class=\"partsChange-btn choose-btn\"  onclick=\"addToProductDialog();\" ><i class=\"fa fa-th-large fa-lg\"></i>"+$.i18n.prop("selectionBtn")+"</a> ";
				}*/
				if(dataRightList[i].rightsNote=="y6"){
					historyRights=1;
				}
				if(dataRightList[i].rightsNote=="y4"){
					html+="<a class=\"partsInto-btn daoru-btn\">" +
							"<i class=\"fa fa-arrow-circle-o-down fa-lg\"></i>"+$.i18n.prop("importBtn")+
							"<ul class=\"saveInsert\">" +"<li>"+"<input type=\"file\" class=\"savedaoru\" onclick=\"clickExportBtn(1)\" onchange=\"partDataExport()\" name=\"partDataExport\" id=\"partDataExport\">" +$.i18n.prop("saveimportBtn")+"</li>"+
							"<li>"+"<input type=\"file\" class=\"updatedaoru\" onclick=\"clickExportBtn(2)\" onchange=\"updateDataExport()\" name=\"partDataUpdateExport\" id=\"partDataUpdateExport\">"+$.i18n.prop("newimportBtn")+"</li>"+
            	             "</ul>"+
							"</a> ";
				}
			}
			html+="<a class=\"partsOut-btn\"   onclick=\"outPutPartData();\"><i class=\"fa fa-arrow-circle-o-up fa-lg\"></i>"+$.i18n.prop("exportBtn")+"</a> ";
			html+="<a class=\"partsSubmit-btn\"   onclick=\"submitApproval();\"><i class=\"fa fa-check-square-o fa-lg\"></i>"+$.i18n.prop("submitApproval")+"</a> ";
			$("#dataRightsBtn").html(html);
		},
		error : function() {
		}
	});
}
//产品结构页按钮权限
function productDataRights(){
	$.ajax({
		url : "HrRightsController/getDataRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			var dataRightList=json.dataRightList;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			var html="";
			for(var i=0;i<dataRightList.length;i++){
				if(dataRightList[i].rightsNote=="cxh"){
					$("#cxh").removeAttr("style");
				}
				if(dataRightList[i].rightsNote=="cfx"){
					$("#cfx").removeAttr("style");
				}
				if(dataRightList[i].rightsNote=="cdj"){
					$("#cdj").removeAttr("style");
				}
				if(dataRightList[i].rightsNote=="cdb"){
					$("#cdb").removeAttr("style");
				}
				if(dataRightList[i].rightsNote=="c2"){
					$("#c2Btn").show();
				}
			}
		},
		error : function() {
		}
	});
}
//HEADER页按钮权限
function headerDataRights(){
	$.ajax({
		url : "HrRightsController/getDataRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			$("#rPwdRight").hide();
			var dataRightList=json.dataRightList;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			var html="";
			for(var i=0;i<dataRightList.length;i++){
				if(dataRightList[i].rightsNote=="rPwdRight"){
					$("#rPwdRight").show();
				}
			}
		},
		error : function() {
		}
	});
}
//MINUTE页按钮权限
function minuteDataRights(){
	$.ajax({
		url : "HrRightsController/getDataRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			$("#minuteSave").hide();
			$("#minuteCheckIn").hide();
			$("#minuteCheckOut").hide();
			var dataRightList=json.dataRightList;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			var html="";
			for(var i=0;i<dataRightList.length;i++){
				if(dataRightList[i].rightsNote=="minuteSave"){
					$("#minuteSave").show();
				}
				if(dataRightList[i].rightsNote=="minuteCheckIn"){
					$("#minuteCheckIn").show();
				}
				if(dataRightList[i].rightsNote=="minuteCheckOut"){
					$("#minuteCheckOut").show();
				}
			}
		},
		error : function() {
		}
	});
}
//流程设计界面权限添加
function processDesignRight(){
    $.ajax({
		url : "HrRightsController/getDataRightsFromSession.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			$("#processDes").hide();
			$("a[name*='processDel']").hide();
			$("a[name*='processClr']").hide();
			$("a[name*='copyDes']").hide();
			var dataRightList=json.dataRightList;
			if(dataRightList==undefined){
				dataRightList=new Array;
			}
			var html="";
			for(var i=0;i<dataRightList.length;i++){
				if(dataRightList[i].rightsNote=="processDes"){
					$("#processDes").show();
				}
				if(dataRightList[i].rightsNote=="processDel"){
					$("a[name*='processDel']").show();
				}
				if(dataRightList[i].rightsNote=="processClr"){
					$("a[name*='processClr']").show();
				}
				if(dataRightList[i].rightsNote=="copyDes"){
					$("a[name*='copyDes']").show();
				}
			}
		},
		error : function() {
		}
	});
}
//原理图符号及封装符号上传------权限维护
//function uploadRights(){
//$.ajax({
//		url : "HrRightsController/getDataRightsFromSession.do",
//		dataType : "json",
//		cache : false,
//		type : "post",
//		success : function(json) {
//			$("#Sym_for_ADR_A").hide();
//			var dataRightList=json.dataRightList;
//			if(dataRightList==undefined){
//				dataRightList=new Array;
//			}
//			var html="";
//			for(var i=0;i<dataRightList.length;i++){
//				if(dataRightList[i].rightsNote=="conceptBtn"){
//					$("#Sym_for_ADR_A").show();
//				}
//			}
//		},
//		error : function() {
//		}
//	});
//}