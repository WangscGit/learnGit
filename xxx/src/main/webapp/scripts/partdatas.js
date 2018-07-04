/** 元器件库主要js */
var b = new Base64();
// 主页搜索按钮跳转页面
function searchFor(){
	var item=$("#searchInput").val();
	if(item!=null&&item!=""){
		saveSearch(item);
		item=b.encode(item);
		window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp?item="+item;
	}else{
		window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp";
	}
}

// 根据规格型号 0:根据规格型号搜索 1:高级查询
function searchByCondition(con) {
	if (con == '0') {// 查询
		var model = $("#model").val();
		if (model != null && model != "") {
			item=model.replace(/\*/g,"%");
			item=b.encode(item);// 参数base64编码
			window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp?item="+item;
		}else{
			window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp";
		}
	}
	if (con == '1') {// 高级查询
		partData = new Object();
		var r1 = $("#filedName1").find("option:selected").val();
		var r2 = $("#filedName2").find("option:selected").val();
		var r3 = $("#filedName3").find("option:selected").val();
		var andor1=$("#andor1").find("option:selected").val();
		var andor2=$("#andor2").find("option:selected").val();
		var str="";
		if (r1 != "") {
			var rr = r1.split(",");
			var filedName = "pd."+rr[0];
			var dataType = rr[1];
			if (r1.indexOf("nvarchar") != -1) {// 字符串类型
				var r1Value = $("#dataColumnInput1").val();
				if (r1Value != "") {
					partData.filed1 = filedName + " like '%" + r1Value.replace(/\*/g,"%") + "%'";
				}
			}
			if (r1.indexOf("date") != -1) {// 时间类型
				var relation1 = $("#relation1").find("option:selected").val();
				var r1Value = $("#dataColumnData1").val();
				if (r1Value != "") {
					r1Value=r1Value.replace("年","-");
					r1Value=r1Value.replace("月","-");
					r1Value=r1Value.replace("日","");
					if(relation1=="="){
						partData.filed1 = filedName +  ">= '"
							+ r1Value+" 00:00:00.000' and "+filedName+"<= DATEADD(day,1,'"+r1Value+" 00:00:00.000')";
					}else{
						partData.filed1 = filedName + " " + relation1 + "'"
							+ r1Value+"'";
					}
				}
			}
			if (r1.indexOf("bit") != -1) {// 布尔类型
				var r1Value = $("#dataColumnSelect1").val();
				if (r1Value != "") {
					partData.filed1 = filedName + " = '" + r1Value+"'";
				}
			}
			if (r1.indexOf("int") != -1) {// 数字类型
				var relation1 = $("#relation1").find("option:selected").val();
				var r1Value = $("#dataColumnInput1").val();
				if (r1Value != "") {
					partData.filed1 = filedName + " " + relation1 + " "
							+ r1Value;
				}
			}
			str=partData.filed1;
		}
		if (r2 != "") {
			var rr = r2.split(",");
			var filedName = "pd."+rr[0];
			var dataType = rr[1];
			if (r2.indexOf("nvarchar") != -1) {// 字符串类型
				var r2Value = $("#dataColumnInput2").val();
				if (r2Value != "") {
					partData.filed2 = filedName + " like '%" + r2Value.replace(/\*/g,"%") + "%'";
				}
			}
			if (r2.indexOf("date") != -1) {// 时间类型
				var relation2 = $("#relation2").find("option:selected").val();
				var r2Value = $("#dataColumnData2").val();
				if (r2Value != "") {
					r2Value=r2Value.replace("年","-");
					r2Value=r2Value.replace("月","-");
					r2Value=r2Value.replace("日","");
					if(relation2=="="){
						partData.filed2 = filedName +  ">= '"
							+ r2Value+" 00:00:00.000' and "+filedName+"<= DATEADD(day,1,'"+r2Value+" 00:00:00.000')";
					}else{
						partData.filed2 = filedName + " " + relation2 + "'"
							+ r2Value+"'";
					}
				}
			}
			if (r2.indexOf("bit") != -1) {// 布尔类型
				var r2Value = $("#dataColumnSelect2").val();
				if (r2Value != "") {
					partData.filed2 = filedName + " = '" + r2Value+"'";
				}
			}
			if (r2.indexOf("int") != -1) {// 数字类型
				var relation2 = $("#relation2").find("option:selected").val();
				var r2Value = $("#dataColumnInput2").val();
				if (r2Value != "") {
					partData.filed2 = filedName + " " + relation2 + " "
							+ r2Value;
				}
			}
			if(r1!=""){
				str=str+andor1+" "+partData.filed2;
			}else{
				str=partData.filed2;
			}
		}
		if (r3 != "") {
			var rr = r3.split(",");
			var filedName = "pd."+rr[0];
			var dataType = rr[1];
			if (r3.indexOf("nvarchar") != -1) {// 字符串类型
				var r3Value = $("#dataColumnInput3").val();
				if (r3Value != "") {
					partData.filed3 = filedName + " like '%" + r3Value.replace(/\*/g,"%") + "%'";
				}
			}
			if (r3.indexOf("date") != -1) {// 时间类型
				var relation3 = $("#relation3").find("option:selected").val();
				var r3Value = $("#dataColumnData3").val();
				if (r3Value != "") {
					r3Value=r3Value.replace("年","-");
					r3Value=r3Value.replace("月","-");
					r3Value=r3Value.replace("日","");
					if(relation3=="="){
						partData.filed3 = filedName +  ">= '"
							+ r3Value+" 00:00:00.000' and "+filedName+"<= DATEADD(day,1,'"+r3Value+" 00:00:00.000')";
					}else{
						partData.filed3 = filedName + " " + relation3 + "'"
							+ r3Value+"'";
					}
				}
			}
			if (r3.indexOf("bit") != -1) {// 布尔类型
				var r3Value = $("#dataColumnSelect3").val();
				if (r3Value != "") {
					partData.filed3 = filedName + " = '" + r3Value+"'";
				}
			}
			if (r3.indexOf("int") != -1) {// 数字类型
				var relation3 = $("#relation3").find("option:selected").val();
				var r3Value = $("#dataColumnInput3").val();
				if (r3Value != "") {
					partData.filed3 = filedName + " " + relation3 + " "
							+ r3Value;
				}
			}
			if(r2!=""){
				str=str+" "+andor2+" "+partData.filed3;
			}
			
			if(r2==""&&r1!=""){
				str=str+" "+andor2+" "+partData.filed3;
			}
			if(r2==""&&r1==""){
				str=partData.filed3;
			}
		}
		str=b.encode(str);// 参数base64编码
		window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp?str="+str;
	}
}
/**
 * 1.ajax调用后台方法获取查询到的数据，动态展示到页面 2.通过查询到的数据构造生产厂家筛选栏 pageNo:当前页；partData：条件
 */
var array=new Array;// 生产厂家list，定义为全局变量。单击生产厂家时不用更新生产厂家筛选栏；
var keyComponentList=new Array;// 质量等级list，定义为全局变量。单击质量等级时不用更新质量等级筛选栏；
var importantPartData;
var status=0;// 记录生产厂家展开隐藏状态 0-隐藏 1-展开
var part;// 生产厂家展开隐藏用到
var cou;// 总数，用于控制添加时是否刷新分页信息
function showData(partData,pageNo) {
	loadProperties();
	if(isNaN(pageNo)){
		pageNo = 0;
	}
	var addBasic = '1';
    pageNo = parseInt(addBasic)+parseInt(pageNo);
    importantPartData=partData;
	$.ajax({
		url : "partDataController/selectPartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(partData),
			"pageNo":pageNo
		},
		success : function(json) {// 生成页面标签
			// 展示数据
			var resultList = json.resultList;
			var str = "";
			var compareNums="";
			if(window.sessionStorage.getItem("searchIndex")!=undefined){
				compareNums = window.sessionStorage.getItem("searchIndex");
			}
			for (var i = 0; i < resultList.length; i++) {
				var onePartData = resultList[i];
				// 主数据table
				var headerStr = "<tr class=\"parts-search-data\"><td class=\"search-checkbox\"><input name=\"partCheck\" type=\"checkbox\" value=\""+onePartData.id+"\"/></td><td class=\"parts-search-img\"><img src=\"images/parts-1.png\" class=\"part-introduce\"/></td>"
				headerStr += "<td  style=\"display:none\">" + onePartData.PartNumber + "</td>";
				headerStr += "<td  style=\"width:118px\">" + onePartData.Part_Type + "</td>";
				headerStr += "<td  style=\"width:128px\">" + onePartData.ITEM + "</td>";
				headerStr += "<td>" + onePartData.Manufacturer + "</td>";
				headerStr += "<td>" + onePartData.KeyComponent + "</td>";
				headerStr += "<td>" + "<a class=\"data-book\" href=\"javascript:viewPdfInPartPage('"+onePartData.Datesheet+"','"+onePartData.PartNumber+"','"+onePartData.id+"','"+onePartData.isCollection+"')\"><i class=\"fa fa-file-pdf-o fa-lg\"></i>"+"Datesheets"+"</a>" + "</td>";
				headerStr += "<td  style=\"display:none\"><input class=\"form-control\"  type=\"text\" value=\"1\" name=\"proNum\" class=\"\"/></td>";
				headerStr += "<td  id='"+onePartData.id+"' name=\"processState\" style=\"display:none\">" + onePartData.process_state + "</td>";
				// 根据权限及收藏信息拼接footerStr
				var footerStr="";
				var f1="<td class=\"hand-work\">";
				var f2="<div class=\"view-box\"><i class=\"fa fa-caret-square-o-down fa-lg\"></i><a  class=\"partsMore\" href=\"pages/parts/cms-parts-particulars.jsp?goMinute="+onePartData.PartNumber+"&partId="+onePartData.id+"&tempPartMark="+GetQueryString("tempPartMark")+"&isColl="+onePartData.isCollection+"\">"+$.i18n.prop("proGominuBtn")+"</a></div>  ";
				// 判断是否关注
				var isColl=onePartData.isCollection==true? "images/love-red.png":"images/love.png";
				// 判断对比栏中是否有此元器件
				var isCompare=compareNums.indexOf(onePartData.PartNumber)!=-1? "checked=\"checked\"":"";
				
				var f3="<div class=\"view-box view-box-work\"><div class=\"left compare-label\"><input type=\"checkbox\""+isCompare+" id="+onePartData.PartNumber+" onclick=clickCheckBox('"+onePartData.PartNumber+"') class=\"compare-check\"/> <span>"+$.i18n.prop("contrast")+"</span></div><div class=\"left compare-view\" onclick=\"addCollection('"+onePartData.PartNumber+"',this);\"><img src=\""+isColl+"\" class=\"left\"  /><span>"+$.i18n.prop("collection")+"</span></div></div></td></tr>";
				// 判断选用权限，saveToPro在ringhts.js中维护
				/*
				 * var isSaveToPro=saveToPro==1? "<div class=\"view-box
				 * view-box1\"><img src=\"images/partChoose.png\" /><a
				 * class=\"partsMore\"
				 * onclick=\"addToProductDialog(this);\">"+$.i18n.prop("selectionBtn")+">></a></div>":"";
				 */
				var isSaveToPro=saveToPro==1? "<div class=\"view-box view-box1\"><i class=\"fa fa-th-large fa-lg\"></i><a  class=\"partsMore\" onclick=\"addToProductDialog(this);\">"+$.i18n.prop("selectionBtn")+">></a></div>":"";
				// 历史记录权限，historyRights在ringhts.js中维护
				var isHistoryRights=historyRights==1? "<div class=\"view-box\"><i class=\"fa fa-history fa-lg\"></i><a href=\"pages/parts/cms-parts-history.jsp?goHistory="+onePartData.PartNumber+"&tempPartMark="+GetQueryString("tempPartMark")+"\" class=\"partsHistory\">"+$.i18n.prop("history")+">></a></div>":"";
				footerStr=f1+isSaveToPro+f2+isHistoryRights+f3;
				
				str += headerStr + footerStr;
			}
			
			// 质量等级，在页面生成ul li
			if(json.keyComponentList.length!=0){
				keyComponentList=json.keyComponentList;
			}
			if(json.keyComponentList.length!=0){// 点击质量等级时不用更新质量等级筛选栏
				var len = keyComponentList.length,val;
        		var keyComponentStr="<b class=\"left\">"+$.i18n.prop("quality_grade")+"：</b><ul class=\"left\" >";
        		for (var i = 0; i < len; i++) {
    				val = keyComponentList[i];
    				if(val!=""&&val!=null){
    					keyComponentStr+="<li><a onclick=\"pushPartDatafromKeyCom('"+val+"','"+JSON.stringify(partData).replace(/\"/g,"~")+"')\">"+val+"</a></li>";
    				}
    			}
        		keyComponentList+="</ul>";
				$("#keyComponentDiv").html(keyComponentStr);
			}
			
			// 生产厂家在页面生成ul li
			if(partData.isPush!="0"){
				array=json.manuList;
			}
			if(partData.isPush!="0"){// 点击生产厂家时不用更新生产厂家筛选栏
				var maxwidth=5;// 设置最多显示的字数
				var len = array.length,val;
        		var manufacturerStr="<b class=\"left\">"+$.i18n.prop("manufacture_factory")+"：</b><ul class=\"left\" id=\"manuf\">";
        		if(len<=maxwidth){// 生产厂家多于maxwidth个时，生成+号，点击展开全部
        			for (var i = 0; i < len; i++) {
        				val = array[i];
        				if(val!=""&&val!=null){
        					manufacturerStr+="<li><a onclick=\"pushPartData('"+val+"','"+JSON.stringify(partData).replace(/\"/g,"~")+"')\">"+val+"</a></li>";
        				}
        			}
        		}else {
        			var l;
        			var stastr;
        			if(status==1){
        				stastr="onclick=\"createMaxwidthMenu("+len+","+maxwidth+",'"+$.i18n.prop("manufacture_factory")+"')\"><img src=\"images/shousuoBlack.png\"/></a>";
        				l=len;
        			}else if(status==0){
        				stastr="onclick=\"createOtherMenu("+len+","+maxwidth+",'"+$.i18n.prop("manufacture_factory")+"')\"><img src=\"images/zhankaiBlack.png\"/></a>";
        				l=maxwidth;
        			}
        			for (var i = 0; i < l; i++) {
        				val = array[i];
        				if(val!=""&&val!=null){
        					manufacturerStr+="<li><a onclick=\"pushPartData('"+val+"','"+JSON.stringify(partData).replace(/\"/g,"~")+"')\">"+val+"</a></li>";
        				}
        			}
        			manufacturerStr+="<a href='javascript:void(0);'"+stastr;
        		}
        		part=partData;
        		
				manufacturerStr+="</ul>";
				$("#manufacturerDiv").html(manufacturerStr);
			}
			
			// 隐藏弹窗
			$('#modal-search').modal('hide');
			// 分页
			if(cou != json.count){
				cou=json.count
				$("#partDataPage").pagination(json.count,{
					items_per_page : 20,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(resultList==null){
							showData(partData,pageNo);
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			$("#dataTable tr:gt(1)").remove();
			$("#dataTable").append(str);
			resultList=null;
		},
		error : function() {
			layer.alert("服务器异常");
			// 隐藏弹窗
			$('#modal-search').modal('hide');
		}
	});
}

function initTableHeader(){
	var headerHtml="<tbody><tr class=\"parts-search-input\"><th class=\"search-checkbox\"><input type=\"checkbox\" onclick=\"selectCheckBox();\" id=\"fircb\"/></th><th class=\"parts-search-img\">"+(en=='zh'?'外观':'Appearance')+"</th>";
	var middleHtml="";
	var footerHtml="<th class=\"hand-work\"></th> </tr> <tr class=\"parts-search-input parts-search-input1\"> <td></td> <td></td> <td><input type=\"text\" id=\"Part_Type1\" name=\"keyEvent\" class=\"form-control left\"/></td> <td><input type=\"text\" name=\"keyEvent\" id=\"ITEM1\" class=\"form-control back-p\"/></td> <td><input name=\"keyEvent\" type=\"text\" id=\"Manufacturer1\" class=\"form-control\"/></td> <td><input type=\"text\" id=\"KeyComponent1\" name=\"keyEvent\" class=\"form-control\"/></td> <td><input name=\"keyEvent\" type=\"text\" id=\"Datesheet1\" class=\"form-control\"/></td> <td class=\"num-checkbox\"></td></tr></tbody>";
	$.ajax({
		url : "partDataController/selectTalbeField.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			for(var i=0;i<json.showNameList.length;i++){
				var name=(en=='zh'? json.showNameList[i].showName:json.showNameList[i].englishName);
				middleHtml+="<th>"+name+"</th> ";
			}
// middleHtml+="<th class=\"num-checkbox\">选用数量</th>"
			$("#dataTable").prepend(headerHtml+middleHtml+footerHtml);
			keyDownEvent();
		},
		error : function() {
		}
	});
}
// 单击展开按钮
function createOtherMenu(len,maxwidth,manufacture){
		status=1;
		var s1="<b class=\"left\">"+manufacture+"：</b><ul class=\"left\" id=\"manuf\">";
		for (var i = 0; i < len; i++) {
			var val = array[i];
			if(val!=""&&val!=null){
				s1+="<li><a onclick=\"pushPartData('"+val+"','"+JSON.stringify(part).replace(/\"/g,"~")+"')\">"+val+"</a></li>";
			}
		}
		s1+="<a class=\"left\" href='javascript:void(0);' onclick=\"createMaxwidthMenu("+len+","+maxwidth+",'"+manufacture+"')\"><img src=\"images/shousuoBlack.png\"/></a>"
		s1+="</ul>";
		$("#manufacturerDiv").html(s1);
		
}
// 单击隐藏按钮
function createMaxwidthMenu(len,maxwidth,manufacture){
	status=0;
	var s2="<b class=\"left\">"+manufacture+"：</b><ul class=\"left\" id=\"manuf\">";
	for (var i = 0; i < maxwidth; i++) {
		var val = array[i];
		if(val!=""&&val!=null){
			s2+="<li><a onclick=\"pushPartData('"+val+"','"+JSON.stringify(part).replace(/\"/g,"~")+"')\">"+val+"</a></li>";
		}
	}
	s2+="<a href='javascript:void(0);' onclick=\"createOtherMenu("+len+","+maxwidth+",'"+manufacture+"')\"><img src=\"images/zhankaiBlack.png\"/></a>"
	s2+="</ul>";
	$("#manufacturerDiv").html(s2);
}
// 主数据页5个搜索框回车事件
function keyDownEvent(){
	$("input[name=keyEvent]").keydown(function(e){
		e = arguments.callee.caller.arguments[0]||window.event;
    	if(e.keyCode==13){
    		pushDataFromInput();
    	}
    });
	
}
// 通过页面几个输入框搜索已有的数据
var cou;// 总数，用于控制添加时是否刷新分页信息
function pushDataFromInput(pageNo){
	var partData=importantPartData;
	var Part_Type1=$("#Part_Type1").val();
	var ITEM1=$("#ITEM1").val();
	var Manufacturer1=$("#Manufacturer1").val();
	var KeyComponent1=$("#KeyComponent1").val();
	var Datesheet1=$("#Datesheet1").val();
	
	if(Part_Type1!=""){
		partData.Part_Type1=Part_Type1;
	}else if(partData.Part_Type1!=undefined){
		partData.Part_Type1=undefined;
	}
	if(ITEM1!=""){
		partData.ITEM1=ITEM1;
	}else if(partData.ITEM1!=undefined){
		partData.ITEM1=undefined;
	}
	if(Manufacturer1!=""){
		partData.Manufacturer1=Manufacturer1;
	}else if(partData.Manufacturer1!=undefined){
		partData.Manufacturer1=undefined;
	}
	if(KeyComponent1!=""){
		partData.KeyComponent1=KeyComponent1;
	}else if(partData.KeyComponent1!=undefined){
		partData.KeyComponent1=undefined;
	}
	if(Datesheet1!=""){
		partData.Datesheet1=Datesheet1;
	}else if(partData.Datesheet1!=undefined){
		partData.Datesheet1=undefined;
	}
	if(isNaN(pageNo)){
    	pageNo = 0;
	}
	var addBasic = '1';
    pageNo = parseInt(addBasic)+parseInt(pageNo);
    importantPartData=partData;
    
	$.ajax({
		url : "partDataController/selectPartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(partData),
			"pageNo":pageNo
		},
		success : function(json) {// 生成页面标签
			// 展示数据
			var resultList = json.resultList;
			var str = "";
			var compareNums="";
			if(window.sessionStorage.getItem("searchIndex")!=undefined){
				compareNums = window.sessionStorage.getItem("searchIndex");
			}
			for (var i = 0; i < resultList.length; i++) {
				var onePartData = resultList[i];
				// 主数据table
				var headerStr = "<tr class=\"parts-search-data\"><td class=\"search-checkbox\"><input name=\"partCheck\" type=\"checkbox\" value=\""+onePartData.id+"\"/></td><td class=\"parts-search-img\"><img src=\"images/parts-1.png\" class=\"part-introduce\"/></td>"
				headerStr += "<td  style=\"display:none\">" + onePartData.PartNumber + "</td>";
				headerStr += "<td  style=\"width:118px\">" + onePartData.Part_Type + "</td>";
				headerStr += "<td  style=\"width:128px\">" + onePartData.ITEM + "</td>";
				headerStr += "<td>" + onePartData.Manufacturer + "</td>";
				headerStr += "<td>" + onePartData.KeyComponent + "</td>";
				headerStr += "<td>" + "<a class=\"data-book\" href=\"javascript:viewPdfInPartPage('"+onePartData.Datesheet+"','"+onePartData.PartNumber+"','"+onePartData.id+"','"+onePartData.isCollection+"')\"><img src=\"images/PDF.png\" />"+"Datesheets"+"</a>" + "</td>";
				headerStr += "<td  style=\"display:none\"><input class=\"form-control\"  type=\"text\" value=\"1\" name=\"proNum\" class=\"\"/></td>";
				// 根据权限及收藏信息拼接footerStr
				var footerStr="";
				var f1="<td class=\"hand-work\">";
				var f2="<div class=\"view-box\"><i class=\"fa fa-caret-square-o-down fa-lg\"></i><a  class=\"partsMore\" href=\"pages/parts/cms-parts-particulars.jsp?goMinute="+onePartData.PartNumber+"&partId="+onePartData.id+"&tempPartMark="+GetQueryString("tempPartMark")+"\">"+$.i18n.prop("proGominuBtn")+"</a></div>  ";
				// 判断是否关注
				var isColl=onePartData.isCollection==true? "images/love-red.png":"images/love.png";
				// 判断对比栏中是否有此元器件
				var isCompare=compareNums.indexOf(onePartData.PartNumber)!=-1? "checked=\"checked\"":"";
				
				var f3="<div class=\"view-box view-box-work\"><div class=\"left compare-label\"><input type=\"checkbox\""+isCompare+" id="+onePartData.PartNumber+" onclick=clickCheckBox(\""+onePartData.PartNumber+"\") class=\"compare-check\"/> <span>"+$.i18n.prop("contrast")+"</span></div><div class=\"left compare-view\" onclick=\"addCollection('"+onePartData.PartNumber+"',this);\"><img src=\""+isColl+"\" class=\"left\"  /><span>"+$.i18n.prop("collection")+"</span></div></div></td></tr>";
				// 判断选用权限，saveToPro在ringhts.js中维护
				var isSaveToPro=saveToPro==1? "<div class=\"view-box view-box1\"><i class=\"fa fa-th-large fa-lg\"></i><a  class=\"partsMore\" onclick=\"addToProductDialog(this);\">"+$.i18n.prop("selectionBtn")+">></a></div>":"";
				// 历史记录权限，historyRights在ringhts.js中维护
				var isHistoryRights=historyRights==1? "<div class=\"view-box\"><i class=\"fa fa-history fa-lg\"></i><a href=\"pages/parts/cms-parts-history.jsp?goHistory="+onePartData.PartNumber+"&tempPartMark="+GetQueryString("tempPartMark")+"\" class=\"partsHistory\">"+$.i18n.prop("history")+">></a></div>":"";
				footerStr=f1+isSaveToPro+f2+isHistoryRights+f3;
				
				str += headerStr + footerStr;
			}
			// 分页
			
			if(cou != json.count){
				cou=json.count
				$("#partDataPage").pagination(json.count,{
					items_per_page : 20,
					num_edge_entries : pageNo,
					num_display_entries : 3,
					callback: function(pageNo, panel){
						if(resultList==null){
							pushDataFromInput(pageNo);
						}
					},
					link_to:"javascript:void(0);"
	    	 });
			}
			$("#dataTable tr:gt(1)").remove();
			$("#dataTable").append(str);
			resultList=null;
		},
		error : function() {
			layer.alert("服务器异常");
			// 隐藏弹窗
			$('#modal-search').modal('hide');
		}
	});
}
// 高级查询，查询字段下拉框，从后台获取查询字段信息动态生成下拉框
function putSeachFiled() {
	$.ajax({
		url : "partDataController/getFiledAndDataType.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			$.each(json, function(index, content) {// 生成下拉框
				// alert(JSON.stringify(content));
				$("#filedName1").append(
						"<option value=\"" + content.fieldName + ','
								+ content.dataType + "\">" + content.showName
								+ "</option>");
				$("#filedName2").append(
						"<option value=\"" + content.fieldName + ','
								+ content.dataType + "\">" + content.showName
								+ "</option>");
				$("#filedName3").append(
						"<option value=\"" + content.fieldName + ','
								+ content.dataType + "\">" + content.showName
								+ "</option>");
			});
		},
		error : function() {
		}
	});
}
/**
 * 1. 查询字段改变时，动态更新条件列和数据列 2.根据数据类型分为4种情况 3.页面为固定的3行查询条件，根据row参数判断哪一行发生改变
 */
function changeSelect(row) {
	if (row == 1) {
		var r1 = $("#filedName1").find("option:selected").val();
		var rr = r1.split(",");
		if (r1.indexOf("nvarchar") != -1||r1.indexOf("selectList") != -1) {// 字符串类型
			$("#relation1").empty();
			$("#relation1").append("<option value=\"like\">包含</option>");

			$("#dataColumnInput1").show();
			$("#dataColumnSelect1").hide();
			$("#dataColumnData1").hide();
		}
		if (r1.indexOf("date") != -1) {// 时间类型
			$("#relation1").empty();
			$("#relation1").append("<option value=\"=\">=</option>");
			$("#relation1").append("<option value=\"<\">&lt;</option>");
			$("#relation1").append("<option value=\">\">&gt;</option>");
			$("#relation1").append("<option value=\">=\">&ge;</option>");
			$("#relation1").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnData1").show();
			$("#dataColumnInput1").hide();
			$("#dataColumnSelect1").hide();
		}
		if (r1.indexOf("bit") != -1) {// 布尔类型
			$("#relation1").empty();
			$("#relation1").append("<option value=\"=\">=</option>");

			$("#dataColumnInput1").hide();
			$("#dataColumnSelect1").show();
			$("#dataColumnData1").hide();
		}
		if (r1.indexOf("int") != -1) {// 数字类型
			$("#relation1").empty();
			$("#relation1").append("<option value=\"=\">=</option>");
			$("#relation1").append("<option value=\"<\">&lt;</option>");
			$("#relation1").append("<option value=\">\">&gt;</option>");
			$("#relation1").append("<option value=\">=\">&ge;</option>");
			$("#relation1").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput1").show();
			$("#dataColumnSelect1").hide();
			$("#dataColumnData1").hide();
		}
		if (r1.indexOf("money") != -1) {// 数字类型
			$("#relation1").empty();
			$("#relation1").append("<option value=\"=\">=</option>");
			$("#relation1").append("<option value=\"<\">&lt;</option>");
			$("#relation1").append("<option value=\">\">&gt;</option>");
			$("#relation1").append("<option value=\">=\">&ge;</option>");
			$("#relation1").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput1").show();
			$("#dataColumnSelect1").hide();
			$("#dataColumnData1").hide();
		}
	}

	if (row == 2) {
		var r2 = $("#filedName2").find("option:selected").val();
		var rr = r2.split(",");
		if (r2.indexOf("nvarchar") != -1||r2.indexOf("selectList") != -1) {// 字符串类型
			$("#relation2").empty();
			$("#relation2").append("<option value=\"like\">包含</option>");

			$("#dataColumnInput2").show();
			$("#dataColumnSelect2").hide();
			$("#dataColumnData2").hide();
		}
		if (r2.indexOf("datetime") != -1) {// 时间类型
			$("#relation2").empty();
			$("#relation2").append("<option value=\"=\">=</option>");
			$("#relation2").append("<option value=\"<\">&lt;</option>");
			$("#relation2").append("<option value=\">\">&gt;</option>");
			$("#relation2").append("<option value=\">=\">&ge;</option>");
			$("#relation2").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnData2").show();
			$("#dataColumnInput2").hide();
			$("#dataColumnSelect2").hide();
		}
		if (r2.indexOf("bit") != -1) {// 布尔类型
			$("#relation2").empty();
			$("#relation2").append("<option value=\"=\">=</option>");

			$("#dataColumnInput2").hide();
			$("#dataColumnSelect2").show();
			$("#dataColumnData2").hide();
		}
		if (r2.indexOf("int") != -1) {// 数字类型
			$("#relation2").empty();
			$("#relation2").append("<option value=\"=\">=</option>");
			$("#relation2").append("<option value=\"<\">&lt;</option>");
			$("#relation2").append("<option value=\">\">&gt;</option>");
			$("#relation2").append("<option value=\">=\">&ge;</option>");
			$("#relation2").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput2").show();
			$("#dataColumnSelect2").hide();
			$("#dataColumnData2").hide();
		}
		if (r2.indexOf("money") != -1) {// 数字类型
			$("#relation2").empty();
			$("#relation2").append("<option value=\"=\">=</option>");
			$("#relation2").append("<option value=\"<\">&lt;</option>");
			$("#relation2").append("<option value=\">\">&gt;</option>");
			$("#relation2").append("<option value=\">=\">&ge;</option>");
			$("#relation2").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput1").show();
			$("#dataColumnSelect1").hide();
			$("#dataColumnData1").hide();
		}
	}

	if (row == 3) {
		var r3 = $("#filedName3").find("option:selected").val();
		var rr = r3.split(",");
		if (r3.indexOf("nvarchar") != -1||r3.indexOf("selectList") != -1) {// 字符串类型
			$("#relation3").empty();
			$("#relation3").append("<option value=\"like\">包含</option>");

			$("#dataColumnInput3").show();
			$("#dataColumnSelect3").hide();
			$("#dataColumnData3").hide();
		}
		if (r3.indexOf("datetime") != -1) {// 时间类型
			$("#relation3").empty();
			$("#relation3").append("<option value=\"=\">=</option>");
			$("#relation3").append("<option value=\"<\">&lt;</option>");
			$("#relation3").append("<option value=\">\">&gt;</option>");
			$("#relation3").append("<option value=\">=\">&ge;</option>");
			$("#relation3").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnData3").show();
			$("#dataColumnInput3").hide();
			$("#dataColumnSelect3").hide();
		}
		if (r3.indexOf("bit") != -1) {// 布尔类型
			$("#relation3").empty();
			$("#relation3").append("<option value=\"=\">=</option>");

			$("#dataColumnInput3").hide();
			$("#dataColumnSelect3").show();
			$("#dataColumnData3").hide();
		}
		if (r3.indexOf("int") != -1) {// 数字类型
			$("#relation3").empty();
			$("#relation3").append("<option value=\"=\">=</option>");
			$("#relation3").append("<option value=\"<\">&lt;</option>");
			$("#relation3").append("<option value=\">\">&gt;</option>");
			$("#relation3").append("<option value=\">=\">&ge;</option>");
			$("#relation3").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput3").show();
			$("#dataColumnSelect3").hide();
			$("#dataColumnData3").hide();
		}
		if (r3.indexOf("money") != -1) {// 数字类型
			$("#relation3").empty();
			$("#relation3").append("<option value=\"=\">=</option>");
			$("#relation3").append("<option value=\"<\">&lt;</option>");
			$("#relation3").append("<option value=\">\">&gt;</option>");
			$("#relation3").append("<option value=\">=\">&ge;</option>");
			$("#relation3").append("<option value=\"<=\">&le;</option>");

			$("#dataColumnInput1").show();
			$("#dataColumnSelect1").hide();
			$("#dataColumnData1").hide();
		}
	}
}

/**
 * 1.点击清空按钮调用此方法，清空表单数据 2.根据row判断点击的是哪一行的清空按钮
 */
function resetData(row) {
	if (row == '1') {
		$("#dataColumnInput1").val("");
		$("#dataColumnData1").val("");
		$("#dataColumnSelect1").val("");
		$("#relation1").val("");
		$("#filedName1").val("");
	}
	if (row == '2') {
		$("#dataColumnInput2").val("");
		$("#dataColumnData2").val("");
		$("#dataColumnSelect2").val("");
		$("#relation2").val("");
		$("#filedName2").val("");
	}
	if (row == '3') {
		$("#dataColumnInput3").val("");
		$("#dataColumnData3").val("");
		$("#dataColumnSelect3").val("");
		$("#relation3").val("");
		$("#filedName3").val("");
	}
}

/**
 * 1.根据后台传递的json循环生成dl、dt、dd标签 2.进入首页时调用 3.从目录内、目录外、元器件库、搜索框四个连接进入页面，4种情况
 */
function createTree() {
	// 构造导航栏
//	constructNavigation();
	/** 根据连接参数判断显示数据 */
	// 根据url判断是否是从首页搜索框进入页面
	var partData=new Object();
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	if(paraString[0].indexOf("item") != -1){
		var items=paraString[0].split("=");
		var item= b.decode(items[1]);
		partData.item=item;
	}
	if(paraString[0].indexOf("str") != -1){
		var strs=paraString[0].split("=");
		var str=b.decode(strs[1]);
		partData.str=str;
	}
	if(paraString[0].indexOf("tempPartMark") != -1){
		var tempPartMarks=paraString[0].split("=");
		var tempPartMark=tempPartMarks[1];
		partData.tempPartMark=tempPartMark;
	}
	
	/** ！！！！！！！！！！！！！！！！！！！！！！！！！！！！ */
	var str = "";
	var setting = {
			view : {
				// removeHoverDom: removeHoverDom,
				showIcon : false,
				addDiyDom: addDiyDom,
				showLine: false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : partTypNodeOnClick
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
			$.fn.zTree.init($("#partTree"), setting, typeTreeList);
			$("#partDataPage").html('');
			
			showData(partData);
		}
	});
}

// 数量生成单独标签
function  addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	var spanObj = $("#" + treeNode.tId + "_span");
	var name=treeNode.name;
	var names=name.split("※");
	aObj.append("<b>("+names[1]+")</b>");
	if(treeNode.imgUrl==null||treeNode.imgUrl==undefined){
		var imgUrl=getImgUrl(treeNode);
		if(imgUrl==null||imgUrl==undefined){
			aObj.prepend("<img src=''/>");
		}else{
			aObj.prepend("<img src='"+imgUrl+"'/>");
		}
	}else{
		aObj.prepend("<img src='"+treeNode.imgUrl+"'/>");
	}
// aObj.prepend("<img src='uploadImg/5f9efc49957249c4a2ab8c5f239f539e.png'/>");
	spanObj.html(names[0]);
}
// 递归获取父节点的imgUrl
function getImgUrl(treeNode){
	var par=treeNode.getParentNode();
	if(par){
		if(par.imgUrl==null||par.imgUrl==undefined){
			var url=getImgUrl(par);
			if(url!=null ||url!=undefined){
				return url;
			}else{
				return;
			}
		}else{
			return par.imgUrl;
		}
	}else{
		return null;
	}
}
// 刷新树（删除节点后刷新，查询后刷新-只显示查询到数据包含的节点）
function pushTree(treeNode/* ,param */){
// var aObj = $("#" + treeNode.tId + "_a");
// var b=aObj.children("b").eq(0);
// var names=b.html().split("(");
// var
// num=param=='0'?parseInt(names[names.length-1])-1:parseInt(names[names.length-1])+1;
// b.html("("+num+")");
// if(treeNode.pId!='0'&&treeNode.pId!=undefined){
// pushTree(treeNode.getParentNode(),param);
// }
	// 根据url判断是否是从首页搜索框进入页面
	var partData=new Object();
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	if(paraString[0].indexOf("tempPartMark") != -1){
		var tempPartMarks=paraString[0].split("=");
		var tempPartMark=tempPartMarks[1];
		partData.tempPartMark=tempPartMark;
	}
	var setting = {
			view : {
				// removeHoverDom: removeHoverDom,
				showIcon : false,
				addDiyDom: addDiyDom,
				showLine: false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick : partTypNodeOnClick
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
		success : function(json) {
			var partTypStr="";
			var typeTreeList = json.typeTreeList;
			$.fn.zTree.init($("#partTree"), setting, typeTreeList);
			$("#partDataPage").html('');
			var treeObj=$.fn.zTree.getZTreeObj("partTree");
			if(treeNode!=undefined){
				if(treeNode.open==true){
					treeObj.expandNode(treeNode,true,true,true);
				}
				treeObj.selectNode(treeNode);
				var pd=new Object();
				getTreePartTypes(treeNode);
				treePartTypes=treePartTypes.substring(0,treePartTypes.length-1);
				pd.partTypes=treePartTypes;
				pd.tempPartMark=treeNode.tempPartMark+"";
				showData(pd);
				treePartTypes="";
			}else{
				showData(partData);
			}
			
		}
	});
}
var treePartTypes="";
function partTypNodeOnClick(event, treeId, treeNode, clickFlag){
// var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
// var r1 = window.location.search.substr(1).match(reg1);
// var tempPartMark=""
// if(r1!=null){
// tempPartMark=unescape(r1[2]);
// }
	var partData=new Object();
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	if(paraString[0].indexOf("item") != -1){
		var items=paraString[0].split("=");
		var item= b.decode(items[1]);
		partData.item=item;
	}
	if(paraString[0].indexOf("str") != -1){
		var strs=paraString[0].split("=");
		var str=b.decode(strs[1]);
		partData.str=str;
	}
	if(paraString[0].indexOf("tempPartMark") != -1){
		var tempPartMarks=paraString[0].split("=");
		var tempPartMark=tempPartMarks[1];
		partData.tempPartMark=tempPartMark;
	}
	getTreePartTypes(treeNode);
	treePartTypes=treePartTypes.substring(0,treePartTypes.length-1);
	partData.partTypes=treePartTypes;
	showData(partData);
	treePartTypes="";
}
function getTreePartTypes(treeNode){
	if(treeNode.children==undefined){
		treePartTypes+=treeNode.name.split("※")[0]+",";
		return;
	}else{
		treePartTypes+=treeNode.name.split("※")[0]+",";
		for(var i=0;i<treeNode.children.length;i++){
			getTreePartTypes(treeNode.children[i]);
		}
	}
}
// 数组去重
function uniqueArray(array){
	var n = {}, r = [], len = array.length, val, type;
	for (var i = 0; i < array.length; i++) {
		val = array[i];
		type = typeof val; 
		if (!n[val]) {
			n[val] = [type];
			r.push(val); 
// } else if (n[val].indexOf(type) < 0) {
// n[val].push(type);
// r.push(val);
// }
			 
		}
	}
	return r;
}
// 点击生产厂家更新数据
function pushPartData(menufa,partDataString){
	var partData=JSON.parse(partDataString.replace(/~/g,"\""));
	partData.manufacturer=menufa;
	// 单击时不用重新获取生产厂家数据；
	partData.isPush="0";
	showData(partData);
}
function pushPartDatafromKeyCom(keycom,partDataString){
	var partData=JSON.parse(partDataString.replace(/~/g,"\""));
	partData.keyComponent=keycom;
	// 单击时不用重新获取质量等级数据；
	partData.isPush="0";
	showData(partData);
}
// 全选、反选
function selectCheckBox(){
	var j=0;
	$.each($("input[name='partCheck']"),function(i,o){
		if($(o).prop("checked")==false){
			if($("#fircb").prop("checked")==true){
				$(o).prop("checked",true);
				j++;
			}
		}
    });
	if(j==0){
		if($("#fircb").prop("checked")==false){
			$("input[name='partCheck']").prop("checked",false);
		}
	}
}

// 删除选中信息
function deletePartData(){
	loadProperties();
	var ids=new Array;
	$("input[name='partCheck']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		alert($.i18n.prop("check-delete1"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [ $.i18n.prop("determineBtn"), $.i18n.prop("resetBtn") ]// 按钮
	}, function() {
		$.ajax({
			url : "partDataController/deletePartData.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"ids" : JSON.stringify(ids)
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg3"));
				var zTree = $.fn.zTree.getZTreeObj("partTree"); 
				var nodes = zTree.getSelectedNodes();
				var treeNode=nodes[0];
				pushTree(treeNode);
// var no=zTree.getNodesByFilter(function filter(node) {
// if(node.name.indexOf(partTypes[i])!=-1){
// return true;
// }
// return false;
// },true,treeNode);
// pushTree(no,"0");
				
			},
			error : function() {
			}
		});
	});
}



// //控制dt、dd展开隐藏，单击效果
// function treeEffect(){
// var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
// var r1 = window.location.search.substr(1).match(reg1);
// if(r1!=null){
// var tempPartMark=unescape(r1[2]);
// }
// $(".leftsidebar_box dt").css({
// "background-color" : "#fff"
// });
// $(".leftsidebar_box dd").hide();
// //单击效果
// $(".leftsidebar_box dt").click(function() {
// $(".leftsidebar_box dt").css({
// "background-color" : "#fff"
// });
// $(this).css({
// "background-color" : "#f2f2f2"
// });
// $(this).parent().find('dd').removeClass("menu_chioce");
// $(".leftsidebar_box dt img").attr("src",
// "images/select_xl01.png");
//		
// if($(this).parent().find('dd').attr("style")=='display: none;'){
// $(this).parent().find("img").attr("src",
// "images/select_xl.png");
// }
// $(".menu_chioce").slideUp();
//		
// $(this).parent().find('dd').slideToggle();
// $(this).parent().find('dd').addClass("menu_chioce");
//		
// //点击根节点展示所有子节点的partData
// var partTypes="";
//		
// if($(this).parent().children().length==1){
// var text = $(this).text();
// var partType = text.split("(");
// if(partType.length>2){
// partTypes=partType[0]+"("+partType[1]+",";
// }else{
// partTypes=partType[0]+",";
// }
// }
// $(this).parent().children("dd").each(function (i,o) {
// var text = $(o).text();
// var partType = text.split("(");
// if(partType.length>2){
// partTypes=partType[0]+"("+partType[1]+","+partTypes;
// }else{
// partTypes=partType[0]+","+partTypes;
// }
// });
// if(partTypes!=""){
// partTypes=partTypes.substring(0,partTypes.length-1);
// var partData=new Object();
// partData.partTypes=partTypes;
// $("#partDataPage").html('');
// if(tempPartMark!=null){
// partData.tempPartMark=tempPartMark;
// }
// showData(partData);
// }
// $("#fircb").prop("checked",false);
// });
// //第一个dl默认展开
// $("#firstDl dd").slideToggle();
// $("#firstDl dd").addClass("menu_chioce");
//	
// $(".leftsidebar_box dd").click(function() {
// var text = $(this).text();
// var partType = text.split("(");
// var partData=new Object();
// partData.partType=partType[0];
// $("#partDataPage").html('');
// if(tempPartMark!=null){
// partData.tempPartMark=tempPartMark;
// }
// $("#fircb").prop("checked",false);
// showData(partData);
// });
// }

// 点击创建按钮时，弹窗页面构造
function addPartDataDialog(){
	loadProperties();
    var zTree = $.fn.zTree.getZTreeObj("partTree"); 
	var nodes = zTree.getSelectedNodes();
	if(nodes.length==0||nodes[0].children!=undefined){
		layer.alert($.i18n.prop("check_last"));
		return;
	}
	var name=nodes[0].name;
	var tempPartMark=nodes[0].tempPartMark;
	var names=name.split("※");
	name=names[0];
	// 获取字段信息
	$.ajax({
		url : "partComponent/selectAddOrUpdateField.do",
		dataType : "json",
		cache : false,
		data:{"partType":name,"tempPartMark":tempPartMark},
		type : "post",
		success : function(json) {
			var dataList=json.dataList;
			var str="";
			for(var i=0;i<dataList.length;i++){
				var fname = (en=='zh'?dataList[i].showName:dataList[i].englishName);
				var select =en=='zh'?"选择":"select";
				if(dataList[i].fieldName=='TempPartMark'){
					if(tempPartMark==true){
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"目录外\"/></td></tr>"
					}else if(tempPartMark==false){
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"目录内\"/></td></tr>"
					}else{
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"\"/></td></tr>"
					}
				}else if(dataList[i].fieldName=='Part_Type'){
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input id=\""+dataList[i].fieldName+"\" value=\""+name+"\" type=\"text\" readonly=\"true\" class=\"form-control\"/></td></tr>"
				}else if(dataList[i].fieldName=='Datesheet'){
					// <a class="btn btn-xs btn-danger attruser-btn left"
					// href="javascript:selectUser()">选择</a><span id="firthUser"
					// class="userShow"></span>
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><a id=\""+dataList[i].fieldName+"\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:selectDatesheet()\">"+select+"</a><span id=\"addDatesheet\" class=\"DatesheetShow\"></span></td></tr>"
				}else if(dataList[i].fieldName=='KeyComponent'){
					var option="";
					var fsList=dataList[i].fsList;
					for(var j=0;j<fsList.length;j++){
						option+="<option value=\""+fsList[j].value+"\">"+fsList[j].name+"</option>";
					}
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><select id=\""+dataList[i].fieldName+"\" class=\"form-control\">"+option+"</select></td></tr>"
				}else if(dataList[i].fieldName=='PartNumber'){
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input id=\""+dataList[i].fieldName+"\" type=\"text\" value= "+json.partNumber+" class=\"form-control\"/></td></tr>"
				}else{
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input id=\""+dataList[i].fieldName+"\" type=\"text\" class=\"form-control\"/></td></tr>"
				}
			}
			$("#addPartDataTable").html(str);
			$(".parts-add-window").show();
		},
		error : function() {
		}
	});
}

// 点击修改按钮时，弹窗页面构造
function updatePartDataDialog(){
	loadProperties();
	var ids=new Array;
	$("input[name='partCheck']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length!=1){
		layer.alert($.i18n.prop("check_one"));
		return;
	}
	// 根据id查询数据，展示到修改弹窗
	
	// 获取字段信息
	$.ajax({
		url : "partComponent/selectAddOrUpdateField.do",
		dataType : "json",
		cache : false,
		type : "post",
		data :{"id":ids[0]},
		success : function(json) {
			var dataList=json.dataList;
			var partMap=json.partMap;
			var str="";
			for(var i=0;i<dataList.length;i++){
				var fname = (en=='zh'?dataList[i].showName:dataList[i].englishName);
				var select= en=='zh'?"选择":"select";
				if(dataList[i].fieldName=='TempPartMark'){
					if(partMap[dataList[i].fieldName]==true){
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"目录外\"/></td></tr>"
					}else if(partMap[dataList[i].fieldName]==false){
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"目录内\"/></td></tr>"
					}else{
						str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input type=\"text\" readonly=\"true\" class=\"form-control\"  id=\""+dataList[i].fieldName+"\" value=\"\"/></td></tr>"
					}
				}else if(dataList[i].fieldName=='Part_Type'){
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input value=\""+partMap[dataList[i].fieldName]+"\" id=\""+dataList[i].fieldName+"\" value=\""+name+"\" type=\"text\" readonly=\"true\" class=\"form-control\"/></td></tr>"
				}else if(dataList[i].fieldName=='Datesheet'){
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><a id=\""+dataList[i].fieldName+"\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:selectDatesheet()\">"+select+"</a><span id=\"updateDatesheet\" class=\"DatesheetShow\">"+partMap[dataList[i].fieldName]+"</span></td></tr>"
				}else if(dataList[i].fieldName=='KeyComponent'){
					var option="";
					var fsList=dataList[i].fsList;
					for(var j=0;j<fsList.length;j++){
						if(fsList[j].value==partMap[dataList[i].fieldName]){
							option+="<option value=\""+fsList[j].value+"\" selected=\"true\">"+fsList[j].name+"</option>";
						}
						option+="<option value=\""+fsList[j].value+"\">"+fsList[j].name+"</option>";
					}
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><select id=\""+dataList[i].fieldName+"\" class=\"form-control\">"+option+"</select></td></tr>"
				}else if(dataList[i].fieldName=='PartNumber'){
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input  value=\""+partMap[dataList[i].fieldName]+"\" id=\""+dataList[i].fieldName+"\" readonly=\"true\" type=\"text\" class=\"form-control\"/></td></tr>"
				}else{
					str+="<tr><td><span><b>*</b>"+fname+"：</span></td><td><input value=\""+partMap[dataList[i].fieldName]+"\" id=\""+dataList[i].fieldName+"\" type=\"text\" class=\"form-control\"/></td></tr>"
				}
				
			}
			$("#updatePartDataTable").html(str);
			$(".parts-change-window").show();
			
		},
		error : function() {
		}
	});
	
}
// 点击修改弹窗中的保存按钮 保存partdata数据
function updatePartData(){
	loadProperties();
	// 获取数据
	var trList = $("#updatePartDataTable").children().children("tr");
	var newPartData=new Object();
	var arr=new Array;
	for (var i=0;i<trList.length;i++) {
		var tdArr = trList.eq(i).find("td");
		var id=tdArr.eq(1).children().attr("id");
		var value = "";
		if(id=="Datesheet"){
		   value = $("#updateDatesheet").html();
		}else{
		   value = tdArr.eq(1).children().val();
		}
		if(value=="目录内"){
			newPartData[id]='false';
		}else if(value=="目录外"){
			newPartData[id]='true';
		}else{
			newPartData[id]=value;
		}
		// 必填校验
		if(value == ""){
		   layer.alert(tdArr.eq(0).children().html().substring(8,tdArr.eq(0).children().html().length-1)+$.i18n.prop("proAlertMsg6"));
		   return;
		}
		arr.push(value);
	}
	newPartData.id=$("input[name='partCheck']:checkbox:checked").val();
	// 调用后台接口 修改
	$.ajax({
		url : "partDataController/updatePartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(newPartData)
		},
		success : function(json) {
			if(json.message == "fail"){
				layer.alert($.i18n.prop("notUpdate"));
			}else{
				$(".parts-change-window").hide();
				var zTree = $.fn.zTree.getZTreeObj("partTree"); 
				var nodes = zTree.getSelectedNodes();
				var treeNode=nodes[0];
				pushTree(treeNode);
			}
		},
		error : function() {
		}
	});
}

// 点击创建弹窗中的保存按钮 保存partdata数据
function addPartData(){
	loadProperties();
	// 获取数据
	var trList = $("#addPartDataTable").children().children("tr");
	var newPartData=new Object();
	for (var i=0;i<trList.length;i++) {
		var tdArr = trList.eq(i).find("td");
		var id=tdArr.eq(1).children().attr("id");
		var value = "";
		if(id=="Datesheet"){
		   value = $("#addDatesheet").html();
		}else{
		   value = tdArr.eq(1).children().val();
		}
		if(value=="目录内"){
			newPartData[id]='false';
		}else if(value=="目录外"){
			newPartData[id]='true';
		}else{
			newPartData[id]=value;
		}
		// 必填校验
		if(value == ""){
			layer.alert(tdArr.eq(0).children().html().substring(8,tdArr.eq(0).children().html().length-1)+$.i18n.prop("proAlertMsg6"));
			return;
		}
	}
	// 调用后台接口 保存
	$.ajax({
		url : "partDataController/insertPartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"jsonData" : JSON.stringify(newPartData)
		},
		success : function(json) {
			if(json.id==0){
				layer.alert($.i18n.prop("partNumberIsExisted"));
				return;
			}
			$(".parts-add-window").hide();
			var zTree = $.fn.zTree.getZTreeObj("partTree"); 
			var nodes = zTree.getSelectedNodes();
			var treeNode=nodes[0];
			pushTree(treeNode);
			addPushDatas(newPartData.ITEM);// 数据保存
		},
		error : function() {
		}
	});
	
}

// 点击选用，弹窗
var partNumbers=new Array;// 选用时用到 partNumbers
function addToProductDialog(_this){
	loadProperties();
	partNumbers=new Array;
	if(_this==undefined){
		var lock=false;
		$("input[name='partCheck']:checkbox:checked").each(function(){
			var proNum=$(this).parent().parent().find("td").find("input[name='proNum']").val();
			if(proNum==""||proNum==0){
				lock=true;
				return false;
			}
			partNumbers.push($(this).parent().parent().find("td").eq(2).html()+","+proNum);
		});
		if(lock){
			layer.alert("请填写选用数量");
			return;
		}
		if(partNumbers.length==0){
			layer.alert($.i18n.prop("check-delete1"));
			return;
		}
	}else{
		var proNum=$(_this).parent().parent().parent().find("td").find("input[name='proNum']").val();
		if(proNum==""||proNum==0){
			layer.alert("请填写选用数量");
			return;
		}
		partNumbers.push($(_this).parent().parent().parent().find("td").eq(2).html()+","+proNum);
	}
	var setting = {
		view : {
			showIcon : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : selectProductNode
		}
	};
	$.ajax({
		url : "productBomController/selectSelectProductBomList.do",
		dataType : "json",
		cache : false,
		success : function(json) {
			$(document).ready(function() {
				$.fn.zTree.init($("#bomtree"), setting, json);
				var treeObj = $.fn.zTree.getZTreeObj("bomtree");
				treeObj.expandAll(true); // 默认展开全部
				$(".parts-product-window").show();
			});
		},
		error : function() {
			// alert("数据连接异常,注册失败！");
		}

	});
	
}

function selectProductNode(event, treeId, treeNode, clickFlag){
	if(treeNode.children!=undefined){
		layer.alert($.i18n.prop("selectProductNode"));
	}
}
// 选用弹窗保存按钮
function addToProduct(){
	var treeObj = $.fn.zTree.getZTreeObj("bomtree");
	var nodes = treeObj.getSelectedNodes();
	if(nodes.length==0){
		layer.alert($.i18n.prop("selectProductNode"));
		return;
	}
	if(nodes[0].children!=undefined){
		layer.alert($.i18n.prop("selectProductNode"));
		return;
	}
	var productId=nodes[0].id;
	// 调用后台接口 保存
	$.ajax({
		url : "productBomController/insertProductPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"partNumbers":JSON.stringify(partNumbers),
			"productId" : productId
		},
		success : function(json) {
			$(".parts-product-window").hide();
			$.each($("input[name='partCheck']"),function(i,o){
				if($(o).prop("checked")==true){
					$(o).prop("checked",false);
				}
		    });
			$.each($("input[name='proNum']"),function(i,o){
				$(o).val(1);
		    });
		    dataAcquisition("select",partNumbers);
			partNumbers= new Array();
		},
		error : function() {
		}
	});
}
/** ****点击添加按钮，弹出添加元器件选框,关闭弹窗**** */
$("body").on("click", ".parts-add-window-close", function () {
    $(".parts-add-window").hide();
});
/** ****点击修改按钮，弹出修改元器件选框,关闭弹窗**** */
$("body").on("click", ".parts-change-window-close", function () {
    $(".parts-change-window").hide();
});
/** ****点击导入按钮，弹出导入元器件选框,关闭弹窗**** */
$("body").on("click", ".parts-into-window-close", function () {
    $(".parts-into-window").hide();
});
/** ****点击添加到产品按钮，弹出导入元器件选框,关闭弹窗**** */
$("body").on("click", ".parts-product-window-close", function () {
    $(".parts-product-window").hide();
});
// 元器件对比个数初始化
var compareNum = new Array();
// 将需要对比的元器件放到session中
function clickCheckBox(data){
	loadProperties();// 国际化
	var compareNums = Array();
	    compareNums = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	   if(null != compareNums){
	   		  compareNum = compareNums;
	   }
	if($("#"+data).is(':checked')){
		if(null != compareNum && compareNum.length>0){
    		for(var i=0;i<compareNum.length;i++){
    			if(data == compareNum[i]){
    				layer.alert($.i18n.prop("compareAlertMsg1"));
        			addCompareDate();
                    return;
    			}
    		}
    	}
	   if(compareNum.length <4){
       compareNum.push(data);
	   }else{
		   layer.alert($.i18n.prop("compareAlertMsg2"));
	     $("#"+data).attr('checked',false);
	     addCompareDate();
	      return;
	    }
	}else{
		if(compareNum.length >0){
	    for(var i=0;i<compareNum.length;i++){
	    	if(data == compareNum[i]){
	    		compareNum.splice(i,1);
	    	}
	    }
	   }
	}
	 var tempIndex = JSON.stringify(compareNum);
     window.sessionStorage["searchIndex"] = tempIndex;
	// $.cookie('compareNum', JSON.stringify(compareNum) , {expires:
	// 1});//cookie有存储长度限制
	compareDialog();
	addCompareDate();
	if(compareNum.length == 0){
		$(".parts-compare-box").hide();
	}else{
		$(".parts-compare-box").show();
	}
}
/** 加载选择的对比对象* */
function addCompareDate(){
	loadProperties();
	  var compareNums = Array();
	    compareNums = JSON.parse(window.sessionStorage.getItem("searchIndex"));
		 var html ='';
		if(compareNums.length == 0){
		 for(var j=0;j<4;j++){
		 	var addBasic = '1';
            var proNum = parseInt(addBasic)+parseInt(j);
		 	html += '<dl class="item-empty"><dt>'+proNum+'</dt><dd>'+$.i18n.prop("compareColumnMsg")+'</dd></dl>';
		 }
		 $("#compareDialog").html(html);
	   }else{
	    var noData = parseInt(4)-parseInt(compareNums.length);
	    $.ajax({
	        url: 'partComponentArrt/selectCompartDataByPart.do',
	        data: {'compareNums':JSON.stringify(compareNums)},
	        dataType: 'json',
	        cache: false,
	        success: function(json){
	         if(null != json){
	         	var jsonList = json
	         	for(var i=0;i<jsonList.length;i++){
	         		var jsonObject =jsonList[i]; 
	         		    	html += '<dl class="hasItem" id="cmp_item_2788652" fore="0">';
	                        html += '<dt>';
	                        html += '<a href="javascript:void(0)">';
	                        html += '<img src="/cms_cloudy/images/p1.jpg" width="50" height="50">';
	                        html += '</a>';
	                        html += '</dt>';
	                        html += '<dd>';
	                        html += '<a class="diff-item-name" href="javascript:void(0)">'+jsonObject.Part_Type+'</a>';
	                        html += '<span class="p-price">'+'<b class="xinghao">' +jsonObject.ITEM+'</b>';
	                        html += '<a class="price-delete" href="javascript:deleteOneCompare(\''+jsonObject.PartNumber+'\')" >'+$.i18n.prop("deleteBtn")+'</a>';
	                        html += '</span>';
	                        html += '</dd>';
	                        html += '</dl>';
	         	}
	         	 if(noData>0){
	         	 	var proNums = jsonList.length;
	         	 	proNums++;
	    	       for(var a=0;a<noData;a++){
		 	               html += '<dl class="item-empty"><dt>'+proNums+++'</dt><dd>'+$.i18n.prop("compareColumnMsg")+'</dd></dl>';
	    	      }
	            }
	         }
	         $("#compareDialog").html(html);
	        },
	        error: function(){
	        	layer.alert("服务器异常，请联系管理员！");
	        }
	    })
	}
}
/** 删除一个对比对象* */
function deleteOneCompare(partNumber){
	var compareNums = Array();
	compareNums = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	for (var x = 0; x < compareNums.length; x++) {
		if (compareNums[x] == partNumber) {
			compareNums.splice(x, 1);
			$("#" + partNumber).attr('checked', false);
		}
	}
	var tempIndex = JSON.stringify(compareNums);
	window.sessionStorage["searchIndex"] = tempIndex;
	compareDialog();
	addCompareDate();
	if(compareNums.length == 0){
		$(".parts-compare-box").hide();
	}else{
		$(".parts-compare-box").show();
	}
}
// 清空对比栏
function clearCompare(){
	var compareNum = Array();
	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	for (var x = 0; x < compareNum.length; x++) {
		$("#" + compareNum[x]).attr('checked', false);
	}
	var compareNums = Array();
	var tempIndex = JSON.stringify(compareNums);
	window.sessionStorage["searchIndex"] = tempIndex;
	compareDialog();
	addCompareDate();
	$(".parts-compare-box").hide();
}
// 为对比数赋值
function compareDialog(){
	var compareNum = Array();
	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	var  eleShopCart = document.querySelector("#compareNum");
	eleShopCart.querySelector(".compare-num").innerHTML = compareNum.length;
}
// 点击对比进入对比页面
$("#compareNum").click(function(){
       document.location.href='/cms_cloudy/pages/parts/cms-parts-compare.jsp?tempPartMark='+GetQueryString("tempPartMark")+'';
});
function addCollectionFromMinute(_this){
	var partNumber= GetQueryString("goMinute");
	var cla=$(_this).find("span").attr("class");
	if(cla=="glyphicon glyphicon-heart"){// 取消收藏
		var partNumbers=new Array();
		partNumbers.push(partNumber);
		  $.ajax({
		        url : "partComponentArrt/deleteUserPn.do",
		        dataType : "json",
		        cache : false,
		        type : "post",
		        data : {
		            "partNumbers" : JSON.stringify(partNumbers)
		        },
		        success : function(json) {
		        	$(_this).find("span").attr("class","glyphicon glyphicon-heart-empty");
		        },
		        error : function() {
		        }
		    });
	}else{
	$.ajax({
		url : "partComponentArrt/insertUserPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"partNumber" : partNumber
		},
		success : function(json) {
			$(_this).find("span").attr("class","glyphicon glyphicon-heart");
		},
		error : function() {
			layer.alert("服务器异常");
		}
	});
	}
}

// 添加关注
function addCollection(partNumber,_this){
	var src=$(_this).children("img").attr("src");
	if(src=="images/love-red.png"){// 取消收藏
		var partNumbers=new Array();
		partNumbers.push(partNumber);
		  $.ajax({
		        url : "partComponentArrt/deleteUserPn.do",
		        dataType : "json",
		        cache : false,
		        type : "post",
		        data : {
		            "partNumbers" : JSON.stringify(partNumbers)
		        },
		        success : function(json) {
		        	$(_this).children("img").attr("src","images/love.png");
		        },
		        error : function() {
		        }
		    });
	}else{
	$.ajax({
		url : "partComponentArrt/insertUserPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"partNumber" : partNumber
		},
		success : function(json) {
			if(json.resultCode==1){
				$(_this).children("img").attr("src","images/love-red.png");
			}
			dataAcquisition("follow",partNumber);
		},
		error : function() {
		}
	});
	}
}

// 点击导航栏♥ 跳转到我的收藏页面
function toMyCollection(){
	loadProperties();
	$.ajax({
		url : "login/hasLogined.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {
			if(json.result=='no'){
				layer.alert($.i18n.prop("check_login"));
			}else if(json.result=='yes'){
				document.location.href='/cms_cloudy/pages/parts/cms-parts-save.jsp?collection=1';
			}
		},
		error : function() {
		}
	});
}
// 根据链接信息构造导航
function constructNavigation(){
	var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
    var r1 = window.location.search.substr(1).match(reg1);
    if(r1!=null){
    	var firstName=unescape(r1[2]);
    	var str="";
    	if(firstName!=null){
    		var mln=$.i18n.prop("DirIn");
    		var mlw=$.i18n.prop("DirOut");;
    		if(firstName=='false')
    			str+="<a class=\"active\" href=\"pages/parts/cms-parts.jsp?tempPartMark=false\">"+mln+"</a>";
    		if(firstName=='true')
    			str+="<a class=\"active\" href=\"pages/parts/cms-parts.jsp?tempPartMark=true\">"+mlw+"</a>";
    	}
    	$("#navigation").append(str);
    }
}

/** 判断用户是否登录* */
function checkLogin() {
	$.ajax({
		url : "/cms_cloudy/login/hasLogined.do",
		cache : false,
		dataType : "json",
		success : function(json) {
			if (json.result == "yes") {
				$(".index-login").hide();
				$(".dropdown").show();
				$(".login-btn").attr("data-dismiss", "modal");
				showMsgNumber();// 消息个数展示
			}else{
			   $(".messageNumber").html('0');
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}



var sheetNum = new Array();// EXCEL中sheet数组
var exportPath = "";// 导入时临时路径
var exportFileType = "";// 导入时文件类型
/** 器件导入* */
function partDataExport(number){
	 if(isNaN(number)){
    	number = 0;
      }
	 /*
		 * var fileSize = 0; var isIE = /msie/i.test(navigator.userAgent) &&
		 * !window.opera; if (isIE && !number.files) { var filePath
		 * =$("#partDataExport").val(); var fileSystem = new
		 * ActiveXObject("Scripting.FileSystemObject"); var file =
		 * fileSystem.GetFile (filePath); fileSize = file.Size; } else {
		 * fileSize = number.files[0].size; } var size = fileSize / 1024;
		 * if(size>2000){ layer.alert("附件不能大于2M"); filePath=""; return }
		 */
	 var fileType = $("#partDataExport").val();
     var fileName = fileType.substring(fileType.lastIndexOf(".")+1).toLowerCase();
     if(fileName !="xls" && fileName !="xlsx"){
    	 layer.alert("请选择excel格式文件上传！");
         fileType="";
         return
     }
     if(checkSize(document.getElementById("partDataExport"))){// 文件大小验证
     	$.ajaxFileUpload
	        (
	            {
	                url:'fileExportController/exportFile.do',
	                secureuri:false,
	                fileElementId:'partDataExport',
	                dataType: 'json',
	                success: function (data, status) 
	                {
	                	if(null != data && "" != data){
	                		if(null != data.rowNoEmpty && "" != data.rowNoEmpty){
	                		  layer.alert(data.rowNoEmpty, {
                             }, function(){
                                  location.reload();
                               });
	                		   return;
	                		}
// if(null != data.noPart && "" != data.noPart){
// layer.alert(data.noPart, {
// }, function(){
// location.reload();
// });
// return;
// }
	                	 sheetNum = data;
	                     var mainHtml = '';
	                     var detailHtml = '';
	                     var selectName = data[0].selectName;
	                     var selecrValue = data[0].selecrValue;
	                     exportPath = data[0].path;
	                     exportFileType = data[0].fileType;
	                     var selectNameSp = selectName.split(",");
	                     var selecrValueSp = selecrValue.split(",");
	                     detailHtml += '<tr>';// style="display:block"
	                     detailHtml += '<th>'+'<input type="checkbox" onclick="selectAllFieldToExport();" id="selectAllFieldToExport">'+'</th>';
	                     detailHtml += '<th>'+'字段名'+'</th>';
	                     detailHtml += '<th>'+'原始字段'+'</th>';
	                     detailHtml += '</tr>';
	                     var show = "_show";
	                     var hide = "_hide";
	                     for(var i=0;i<data.length;i++){
	                     	var sheetName = data[i].sheetName;
	                     	var fieldName = data[i].fieldName;
	                        mainHtml += '<option value= '+i+'>'+sheetName+'</option>';
	                        var fieldNameList = fieldName.split(",");
	                        var _class = show+i;
	                        for(var x=0;x<fieldNameList.length;x++){
	                        var name = fieldNameList[x];
	                        detailHtml += '<tr class="'+_class+'">';
	                        detailHtml += '<td>'+'<input type="checkbox" name="'+_class+'">'+'</td>';
	                        detailHtml += '<td>'+name+'</td>';
	                        detailHtml += '<td>'+'<select class="form-control" onchange="changeFieldSelect(this)" name="'+_class+'">'+'<option value="">'+'请选择'+'</option>';
	                        for(var s=0;s<selectNameSp.length;s++){
	                        	if(selectNameSp[s] == name){
	                        		detailHtml += '<option value="'+selecrValueSp[s]+'" selected>'+selectNameSp[s]+'</option>';
	                        	}else{
	                        	    detailHtml += '<option value="'+selecrValueSp[s]+'">'+selectNameSp[s]+'</option>';
	                        	}
	                        }
	                        detailHtml += '</select>';
	                        detailHtml += '</td>';
	                        detailHtml += '</tr>';
	                        }
	                     }
                           $("#exportField").html(detailHtml);
                           $("#addSelectSheet").html(mainHtml);
                           for(var z=0;z<data.length;z++){
                            if(z == number){
                            	 var s_class = "_show"+z;
                                  $("."+s_class).show();
                            }else{
                            	 var s_class = "_show"+z;
                            	 $("."+s_class).hide();
                            }
                           }
	                	   $(".parts-into-window").show();
	                	}
	                },
	                error: function (data, status, e)// 服务器响应失败处理函数
	                {
	                	layer.alert("数据连接异常,请联系管理员!");
	                }
	            }
	        )
     }
}
// 关闭数据导入框
function closeExportDatas(){
	location.reload();
	$(".parts-into-window").hide();
}
// 关闭数据导入框
function closeExportDiv(){
	location.reload();
}
/** 改变sheet选择框* */
function changeSheet(){
	var value = $("#addSelectSheet").val();
	for(var x=0;x<sheetNum.length;x++){
        if(x == value){
        	 var s_class = "_show"+x;
              $("."+s_class).show();
        }else{
        	 var s_class = "_show"+x;
        	 $("."+s_class).hide();
        }
      }
}
/** 保存导入的数据* */
function saveExportDatas(type){
	var originalMarry = "yes";
	var checkExportList = new Array();// 勾选数据集
	var requiredNum = 0;
	$("#addSelectSheet option").each(function () {// 循环所有的工作表
	       var addSelectSheetVal = $(this).val(); // 获取单个value
	       var thisName = "_show"+addSelectSheetVal; 
	       $("input[name='"+thisName+"']:checkbox:checked").each(function(){// 循环当前工作表下所有勾选的数据
	         	var thisTr = $(this).parent().parent();// 获取当前tr
	    	   var thisFiledTd = thisTr.find("td")[2];// 获取当前tr的第三个td(原始字段)
	    	   var thisFiledVal = $(thisFiledTd).children("select").val();// 获取原始字段value
                if(thisFiledVal == ''){// 已勾选的行 必须匹配原始字段列
                	thisFiledTd = $(this).parent().parent().find("td")[1];
                	layer.alert("sheet"+addSelectSheetVal+","+$(thisFiledTd).html()+"：未匹配原始字段！");
                	originalMarry = "no";
                }
    	        var td2 = thisFiledTd.children;
    	        var _class = $(thisTr).attr("class");
            	var value = $(td2).val();
    	        if("" == value){
    	              value == "-1";
    	          }
    	          if(value == "PartCode"){// 必须匹配物料编码和元器件名称列
    	          	requiredNum ++;
    	          }
            	var data ={'_class':_class,'value':value};
    	        checkExportList.push(data);
	       });
	});
	if(originalMarry == "no"){
		checkExportList = new Array();
		return;
	}
	if(checkExportList.length == 0){
		layer.alert("请勾选要导入的字段！");
		return;
	}
		if(requiredNum != $("#addSelectSheet option").size()){
			  layer.alert("请勾选工作表内的物料编码并进行字段匹配！");
			  return;
		} 
    if(type == '2'){
    	if(checkExportList.length < 2){
    		layer.alert("更新操作至少勾选两个来进行导入！");
    		return;
    	}
	}
	var table=document.getElementById("exportField");
    var trs=table.getElementsByTagName("tr");
	var exportList = new Array();
    for(var i = 1;i<trs.length;i++){
    	var tdArr = $(trs[i]).find("td");
    	var td2 = tdArr[2].children;
    	var _class = $(trs[i]).attr("class");
    	var value = $(td2).val();
    	if("" == value){
    	   value = "";
    	}else{
    	   if(!$(tdArr[0]).find("input[type='checkbox']").is(':checked')){// 匹配了原始字段但没有勾选复选框将值---滞空
    	        value = "";
    	   }
    	}
    	var data ={'_class':_class,'value':value};
    	exportList.push(data);
    }
    if(type == '1'){// 保存导入
    	$.ajax({
    		url: 'fileExportController/saveExportForPartdata.do',
    		data: {'exportList':JSON.stringify(exportList),'exportPath':exportPath,'exportFileType':exportFileType,'type':type},
            type: 'post',
    		dataType: 'json',
            cache: false,
            beforeSend: function(){
    			$("#LoadingPng").css("display","block");
    		},
            success: function(json){
            	if(json.result == "noState"){
            		layer.alert("请匹配器件状态字段！");      
            		return;
                }else if(json.result == "noPart"){
                    layer.alert("请匹配物料编码字段！");      
            		return;
                }else if(json.result == "noPartData"){
                    layer.alert("物料编码不能为空，请检查Excel数据！");      
            		return;
                }else if(json.result == "rePartData"){
                    layer.alert(json.value+"在数据库中已存在，物料编码不能重复！");      
            		return;
                }else if(json.result == "noTempPartMark"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "noDirInOROut"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "noValueTempPartMark"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "valueTypeTempPartMark"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "noFoundPartTree"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "noLastPartTree"){
                    layer.alert(json.msg);      
            		return;
                }else if(json.result == "updateSuccess"){
                	layer.alert('更新成功！', {
                    }, function(){
                        location.reload();
                    });
                }else if(json.result == "error"){
                	$(".parts-into-window").hide();
                	layer.confirm('部分数据导入失败，是否生成错误报告！', {
                		btn : [ '生成', '取消']// 按钮
                	}, function() {
                		var sessionKey = json.sessionKey;
                		window.location.href=getContextPathForWS()+"/fileExportController/exportMistakeResult.do?sessionKey="+sessionKey;
                		$(".layui-layer-btn1").click();
                	});
                }else{
                	layer.alert('导入成功', {
                  }, function(){
                      location.reload();
                  });
                }
            },
            complete : function(){
          	  $("#LoadingPng").hide();
    		},
            error: function(){
            	layer.alert("服务器异常，请联系管理员！");
            }
    	})
    }else{// 更新导入
    $.ajax({
		url: 'fileExportController/updateExportForPartdata.do',
		data: {'exportList':JSON.stringify(exportList),'exportPath':exportPath,'exportFileType':exportFileType,'type':type},
        type: 'post',
		dataType: 'json',
        cache: false,
        beforeSend: function(){
			$("#LoadingPng").css("display","block");
		},
        success: function(json){
        	if(json.result == "error"){
            	$(".parts-into-window").hide();
            	layer.confirm('部分数据导入失败，是否生成错误报告！', {
            		btn : [ '生成', '取消']// 按钮
            	}, function() {
            		var sessionKey = json.sessionKey;
            		window.location.href=getContextPathForWS()+"/fileExportController/exportMistakeResult.do?sessionKey="+sessionKey;
            		$(".layui-layer-btn1").click();
            	});
            }else{
            	layer.alert('更新成功！', {
              }, function(){
                  location.reload();
              });
            }
        },
        complete : function(){
      	  $("#LoadingPng").hide();
		},
        error: function(){
        	layer.alert("服务器异常，请联系管理员！");
        }
	})
  } 
}
/** 下载* */
function download(data){
	if('1' == data){
	  data='CMS_Suite用户手册.pdf';
	  document.location.href='fileExportController/download.do?data=' + data+'&type='+1;
	}else{
	  data='CMS_Suite客户端.rar';
	  document.location.href='fileExportController/download.do?data=' + data+'&type='+2;
	}
}
// 导出partData数据
function outPutPartData(){
	$.ajax({
		url : "login/hasLogined.do",
		cache : false,
		dataType : "json",
		success : function(json) {
			if(json.result=='no'){
				if(json.lang == "zh"){
    					layer.alert("请先登录！");
    				}else{
    				   layer.alert("Please Sign in first！");
    		       }
			}else{
				var ids=new Array;
				$("input[name='partCheck']:checkbox:checked").each(function(){
					ids.push($(this).val());
				});
				window.location.href=getContextPathForWS()+"/partDataController/exportExcel.do?ids="+JSON.stringify(ids);
			}
		},
		error : function() {
			layer.alert("数据连接异常,注册失败！");
		}
	});
}
// 选择数据手册
function selectDatesheet(){
	$.ajax({
	   url: 'partComponentArrt/selectDatasheets.do',
	   dataType: 'json',
	   cache: false,
	   success: function(json){
	   var list = json.list;
	 var settingDatasheet = {
		view : {
			showIcon : false
		},
		check: {
             enable: true
         },
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick :selectTreeByDatesheet
		}
	};
	if(null != list && "" != list){
	  $.fn.zTree.init($("#datasheetTree"), settingDatasheet, list);// 加载树结构
	  var treeObj = $.fn.zTree.getZTreeObj("datasheetTree");
	  treeObj.expandAll(true); 
	 }else{
	   var zNodesUser =[
			{ id:-1, pId:0, name:"数据手册", open:true}
		   ];
		   $.fn.zTree.init($("#datasheetTree"), settingDatasheet, zNodesUser);// 加载树结构
	 }
	    $(".parts-producthand-window").show();
	},
     error: function(){
	 layer.alert("服务器异常，请联系管理员！")   
    }
  })
}
/** 保存选择de数据手册* */
 function saveSelectDatasheet(){
    var treeObj = $.fn.zTree.getZTreeObj("datasheetTree");
	var nodes=treeObj.getChangeCheckedNodes();
	var selectDatasheet= "";
	for(var i=0;i<nodes.length;i++){
		if(nodes[i].checked==true && nodes[i].id != "-1"){
			var filename = nodes[i].name
            var index1=filename.lastIndexOf(".");  
            var index2=filename.length;
            var postf=filename.substring(index1,index2);// 后缀名
            if(postf != ".pdf"){
              layer.alert("请选择pdf格式的数据手册!");
              return;
            }
			if(i == nodes.length-1){
			   selectDatasheet = selectDatasheet+nodes[i].name;
			}else{
			   selectDatasheet = selectDatasheet+nodes[i].name+",";
			}
		}
	}
	$("#addDatesheet").html(selectDatasheet);
	$("#updateDatesheet").html(selectDatasheet);
	$(".parts-producthand-window").hide();
}
/** pdf预览* */
function viewPdfInPartPage(_this,partNumber,partId,isColl){
	if("" == _this || null == _this){
		layer.alert("该器件下没有相对应的数据手册！");
		return;
	}
	var pdfNum = _this.split(",");
	if(pdfNum.length >1){// 跳转至相对应的锚点
		var reg1 = new RegExp("(^|&)tempPartMark=([^&]*)(&|$)");
	    var r1 = window.location.search.substr(1).match(reg1);
	    if(r1!=null){
	    	r1=unescape(r1[2]);
	    }
      window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-particulars.jsp?goMinute="+partNumber+"&tempPartMark="+r1+"&partId="+partId+"&isColl="+isColl+"&goPdf=-1";
	}else{
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
}
function saveSearch(searchData){
   $.ajax({
      url: 'partDataController/insertSearch.do',
      data: 'searchData='+searchData,
      type: 'post',
      dataType: 'json',
      cache: false,
      error: function(){
         layer.alert("数据连接异常,请联系管理员！");
      }
   })
}

// 点击添加按钮
function goInsertPage(){
// loadProperties();
// var zTree = $.fn.zTree.getZTreeObj("partTree");
// var nodes = zTree.getSelectedNodes();
// if(nodes.length==0||nodes[0].children!=undefined){
// layer.alert($.i18n.prop("check_last"));
// return;
// }
// var name=nodes[0].name;
// var tempPartMark=nodes[0].tempPartMark;
// var names=name.split("※");
// name=b.encode(names[0]);
	window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-add.jsp";
}

// 点击修改按钮
function goEditPage(){
	loadProperties();
	var ids=new Array;
	$("input[name='partCheck']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length!=1){
		layer.alert($.i18n.prop("check_one"));
		return;
	}
	window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-edit.jsp?id="+ids[0];
}
function buildPartNumber(){
	var partType=$("input[name='Part_Type']").val();
	if(partType==undefined||partType==''){
		layer.alert($.i18n.prop("qxzyqjmc"));
		return;
	}
	$("[name='PartCode']").val(rulePartNumber);
}
// 初始化添加元器件页面
var rulePartNumber="";
function initInsertPage() {
	$.ajax({
		url : 'partComponent/selectInsertPageField.do',
		type : 'post',
		dataType : 'json',
		cache : false,
		data : {
		},
		success : function(json) {
			// 动态生成添加字段的显示名
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
			var normalObj=getFieldTr(normalList);
			var qualityObj=getFieldTr(qualityList);
			var designObj=getFieldTr(designList);
			var purchaseObj=getFieldTr(purchaseList);
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
			$("#partDataTable").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);
		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
// 生成字段tr
function getFieldTr(insertList,partData){
	var obj=new Object();
	var string="";
	var rp=0;
	for(var i=0;i<insertList.length;i++){
		var xh="";
		var value=partData==undefined?"":partData[insertList[i].fieldName];
		value=value==undefined?"":value;
		if(insertList[i].fieldName == "PartCode" || insertList[i].fieldName == "ITEM" || insertList[i].fieldName == "Datesheet"|| insertList[i].fieldName == "Part_Type"){
			xh="<span style=\"color:red;\">*</span>";
		}
		var str="";
		if(insertList[i].fieldName=='Datesheet'){
			var select=en=='zh'? "选择":"Select";
			str="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:selectDatesheet()\">"+select+"</a><span id=\"addDatesheet\" class=\"DatesheetShow\">"+value+"</span>";
		}
		if(insertList[i].fieldName=='PartCode'){
			var select=en=='zh'? "生成":"build";
			str="<input class=\"form-control saveValue symbol left\" name=\"PartCode\" value=\""+value+"\" />";
			str+="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger symbolBtn\" onclick=\"buildPartNumber();\">"+select+"</a>";
			rulePartNumber=partData==undefined?"":partData[insertList[i].fieldName];
		}
		if(insertList[i].fieldName=='Part_Type'){
			var select=en=='zh'? "选择":"select";
			/*str="<input class=\"form-control saveValue symbol\" name=\"Part_Type\" value=\""+value+"\" readonly=\"readonly\" />";*/
			if(partData==undefined){//添加
				str="<input class=\"form-control saveValue symbol\" name=\"Part_Type\"  readonly=\"readonly\" />";
				str+="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger symbolBtn\" onclick=\"initPartTypeDia();\">"+select+"</a>";
			}else{
				str="<input class=\"form-control saveValue\" name=\"Part_Type\" value=\""+value+"\" readonly=\"readonly\" />";
			}
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
			str="<input class=\"form-control saveValue symbol left\" name=\"Sym_for_CAP\" />";
			str+='<a class="btn btn-xs btn-danger symbolBtn" onclick="symbolUploadBefore(\'1\')" id="Sym_for_CAP_A">'+select+'</a>';
		}
		if(',welding_library,package_symbols,step_model,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			var select=en=='zh'? "选择":"Select";
			str="<a id=\""+insertList[i].fieldName+"1\"  class=\"btn btn-xs btn-danger attruser-btn left\" href=\"javascript:initUploadDia('add"+insertList[i].fieldName+"')\">"+select+"</a><span id=\"add"+insertList[i].fieldName+"\" class=\"DatesheetShow\">"+value+"</span>";
		}
		if(',shape_img,size_img,characteristic_curve_img,typical_ap_img,'.indexOf(","+insertList[i].fieldName+",")!=-1){
			str="<div class=\"left parts-images\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"图片上传最佳尺寸【550px*320px】\">" +
					"<img src=\""+value+"\" id=\""+insertList[i].fieldName+"1\"></div><div class=\"right uplogo\"><a class=\"inputBox right\">" +
							"<img  src=\"images/partsUpload.png\"/>"+
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
			if(rp%2==0){
				string+="<tr>";
			}
			string+="<td class=\"sameLength\" id=\""+insertList[i].fieldName+"\" >"+xh+insertList[i].showName+"</td><td >"+str+"</td>";
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
// 初始化修改元器件页面
function initEditPage() {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	var id=(paraString[0].split("="))[1];// id
	$.ajax({
		url : 'partComponent/selectEditPageField.do',
		type : 'post',
		dataType : 'json',
		data :{"id":id},
		cache : false,
		success : function(json) {
			var partData=json.partData;
			// 动态生成添加字段的显示名
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
			var normalObj=getFieldTr(normalList,partData);
			var qualityObj=getFieldTr(qualityList,partData);
			var designObj=getFieldTr(designList,partData);
			var purchaseObj=getFieldTr(purchaseList,partData);
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
			$("#partDataTable").html(normalAttrsStr+qualityAttrsStr+designAttrsStr+purchaseAttrsStr);

		},
		error : function() {
			layer.alert("数据连接异常,请联系管理员！");
		}
	});
}
// 添加、修改页面的保存按钮 state 1添加 2修改
function saveFromPage(state){
	loadProperties();
	var values=$(".saveValue");
	var partData=new Object();
	for(var i=0;i<values.length;i++){
		var td=values[i];
		partData[$(td).attr('name')]=$(td).val();
	}
	if($("[name='Part_Type']").val()==''){
		layer.alert($.i18n.prop("qxzyqjmc"));
		return;
	}
	if($("[name='PartCode']").val()==''){
		layer.alert($.i18n.prop("check_partNumber"));
		return;
	}
	if($("[name='ITEM']").val()==''){
		layer.alert($.i18n.prop("check_model"));
		return;
	}
	if($("[name='Datesheet']").val()==''){
		layer.alert($.i18n.prop("processAlert1"));
		return;
	}
	var url = location.href;
	var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
	// if(state=='1'){
// var temPartMark=(paraString[0].split("="))[1];//目录内外
// partData.TempPartMark=temPartMark;
// }
	if(state=='2'){
		var id=(paraString[0].split("="))[1];// id
		partData.id=id;
	}
	partData.Datesheet=$("#addDatesheet").html();
	partData.Alternative_Part=$("#Alternative_Part_span").html();
	partData.welding_library=$("#addwelding_library").html();
	partData.package_symbols=$("#addpackage_symbols").html();
	partData.step_model=$("#addstep_model").html();
	$.ajaxFileUpload({
		url : "partDataController/insertPartDataFromPage.do",
		dataType: 'json',
		secureuri:false,
		type : "post",
		fileElementId:["shape_img2","size_img2","characteristic_curve_img2","typical_ap_img2","schematic_img2","ens_img2"],
		data : {
			"jsonData" : JSON.stringify(partData),
			"lock":state
		},
		success : function(json,status) {
			if(json.message=='1'){
			    if(state=='1'){
				       addPushDatas(partData.ITEM);// 数据保存
				  }
				window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp";
			}
			if(json.message=='2'){
				layer.alert($.i18n.prop("check_login"));
			}
			if(json.message=='0'){
				layer.alert($.i18n.prop("onepartNumber"));
			}
		},
		error : function() {
		}
	});
}
// 消息个数展示
function showMsgNumber(){
    $.ajax({
			url : 'sysMessageController/selectMessageCount.do',
			type: 'post',
			dataType : 'json',
			cache : false,
			success: function(json){
			   $(".messageNumber").html(json);
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
	});
}
// 流程审批
function submitApproval(){
  loadProperties();
  var msg = '';
	partNumbers=new Array;
		$("input[name='partCheck']:checkbox:checked").each(function(){
			var item = $(this).parent().parent().find("td").eq(4).html();
			$(this).parent().parent().find("td").each(function(i,x){
			   if($(x).attr("name") == "processState"){
                    if($(x).html() == "审批中"){
                          msg = item+"："+$.i18n.prop("processAlert11");
                          return;
                    }
                    partNumbers.push($(x).attr("id"));
                }
			});
		});
		if(''!=msg){
			layer.alert(msg);
			return;
		}
		if(partNumbers.length==0){
			layer.alert($.i18n.prop("check-delete1"));
			return;
		}
		if(partNumbers.length>1){
			initProcessReceivers();// 弹出流程执行人弹出框
		}else{// 只选择一条数据的情况下
			$.ajax({
				url : 'WorkflowApplyFormController/submitApproval.do',
				data : {'formType':'0','partId':JSON.stringify(partNumbers)},
				type: 'post',
				dataType : 'json',
				cache : false,
				success: function(json){
					if(null != json.message){
					   layer.alert(json.message);
					}else{
					   window.location.href = getContextPathForWS()+"/pages/workflowPage/cms-flow-manage-edit.jsp?taskId="
					                + json.taskId + "&ft=" + "0" + "&partId=" + partNumbers;
					}
				},
				error : function() {
					layer.alert("服务器连接异常，请联系管理员！");
				}
		});
	}
}
// 数据手册选择框----点击事件
function selectTreeByDatesheet(event, treeId, treeNode, clickFlag){
    if(treeNode.id == -1){
        $("#dateSheetUpload").trigger("click");
    }
}
// 全选(导入器件数据字段匹配框)
function selectAllFieldToExport(){
	var sheetVal = $("#addSelectSheet").val();
	var name = "_show"+sheetVal;
	if($("#selectAllFieldToExport").is(':checked')){		
		$("input[name='"+name+"']").prop("checked",true);
	}else{
		$("input[name='"+name+"']").prop("checked",false);
	}
}
// 原始字段change事件
function changeFieldSelect(_this){
	if ('' != $(_this).val()) {
		var sheetVal = $("#addSelectSheet").val();
		var name = "_show"+sheetVal;
		var selectList = $("select[name='"+name+"']");
		var s = 0;
		for (var i = 0; i < selectList.length; i++) {
			if ($(_this).val() == $(selectList[i]).val()) {
				s++;
			}
		}
		if(s != 1 ){
			layer.alert("该字段已存在！");
			$(_this).val('');
			return;
		}
	}
}
// 初始化元器件类型弹窗
function initPartTypeDia(){
	createProcessPartTree();
	$(".parts-applyChoose-window").show();
}
// 刷新特殊属性及其他一些字段值
function savePartType(){
	var zTree = $.fn.zTree.getZTreeObj("partTree");
	var nodes = zTree.getSelectedNodes();
	if (nodes.length == 0 || nodes[0].children != undefined) {
		layer.alert($.i18n.prop("check_last"));
		return;
	}
	var name = nodes[0].name;
	var tempPartMark = nodes[0].tempPartMark;
	var names = name.split("※");
	name = names[0];
	
	$.ajax({
		url : 'partComponent/selectProPertiesByPT.do',
		data : {
			 "tempPartMark" : tempPartMark,
			 "partType":name
		},
		type: 'post',
		dataType : 'json',
		cache : false,
		success: function(json){
			var property=json.property;
			for(var p in property){
				if(property[p]!=undefined&&property[p]!=''){
					if($("#"+p)){
						$("#"+p).html(property[p]);
					}
				}
			}
//			$("input[name='DirInOROut']").val(tempPartMark=='true'?'目录外':'目录内');
//			$("input[name='TempPartMark']").val(tempPartMark);
			$("input[name='Part_Type']").val(name);
			$("input[name='PartCode']").val(json.partNumber);
			rulePartNumber=json.partNumber;
			$(".parts-applyChoose-window").hide();
			
		},
		error : function() {
			layer.alert("服务器连接异常，请联系管理员！");
		}
});
}
// 点击导入按钮 1:保存导入 2:更新导入
function clickExportBtn(type){
	if(type=='1'){
		$("#saveExportDatas").attr("onclick","saveExportDatas(1)");
	}else{
		$("#saveExportDatas").attr("onclick","saveExportDatas(2)");
	}
}
/** 器件更新导入* */
function updateDataExport(number){
	 if(isNaN(number)){
    	number = 0;
      }
	 var fileType = $("#partDataUpdateExport").val();
     var fileName = fileType.substring(fileType.lastIndexOf(".")+1).toLowerCase();
     if(fileName !="xls" && fileName !="xlsx"){
    	 layer.alert("请选择excel格式文件上传！");
         fileType="";
         return
     }
     if(checkSize(document.getElementById("partDataUpdateExport"))){// 文件大小验证
     	$.ajaxFileUpload
	        (
	            {
	                url:'fileExportController/exportFile.do',
	                secureuri:false,
	                fileElementId:'partDataUpdateExport',
	                dataType: 'json',
	                success: function (data, status) 
	                {
	                	if(null != data && "" != data){
	                		if(null != data.rowNoEmpty && "" != data.rowNoEmpty){
	                		  layer.alert(data.rowNoEmpty, {
                             }, function(){
                                  location.reload();
                               });
	                		   return;
	                		}
	                	 sheetNum = data;
	                     var mainHtml = '';
	                     var detailHtml = '';
	                     var selectName = data[0].selectName;
	                     var selecrValue = data[0].selecrValue;
	                     exportPath = data[0].path;
	                     exportFileType = data[0].fileType;
	                     var selectNameSp = selectName.split(",");
	                     var selecrValueSp = selecrValue.split(",");
	                     detailHtml += '<tr>';// style="display:block"
	                     detailHtml += '<th>'+'<input type="checkbox" onclick="selectAllFieldToExport();" id="selectAllFieldToExport">'+'</th>';
	                     detailHtml += '<th>'+'字段名'+'</th>';
	                     detailHtml += '<th>'+'原始字段'+'</th>';
	                     detailHtml += '</tr>';
	                     var show = "_show";
	                     var hide = "_hide";
	                     for(var i=0;i<data.length;i++){
	                     	var sheetName = data[i].sheetName;
	                     	var fieldName = data[i].fieldName;
	                        mainHtml += '<option value= '+i+'>'+sheetName+'</option>';
	                        var fieldNameList = fieldName.split(",");
	                        var _class = show+i;
	                        for(var x=0;x<fieldNameList.length;x++){
	                        var name = fieldNameList[x];
	                        detailHtml += '<tr class="'+_class+'">';
	                        detailHtml += '<td>'+'<input type="checkbox" name="'+_class+'">'+'</td>';
	                        detailHtml += '<td>'+name+'</td>';
	                        detailHtml += '<td>'+'<select class="form-control" onchange="changeFieldSelect(this)" name="'+_class+'">'+'<option value="">'+'请选择'+'</option>';
	                        for(var s=0;s<selectNameSp.length;s++){
	                        	if(selectNameSp[s] == name){
	                        		detailHtml += '<option value="'+selecrValueSp[s]+'" selected>'+selectNameSp[s]+'</option>';
	                        	}else{
	                        	    detailHtml += '<option value="'+selecrValueSp[s]+'">'+selectNameSp[s]+'</option>';
	                        	}
	                        }
	                        detailHtml += '</select>';
	                        detailHtml += '</td>';
	                        detailHtml += '</tr>';
	                        }
	                     }
                           $("#exportField").html(detailHtml);
                           $("#addSelectSheet").html(mainHtml);
                           for(var z=0;z<data.length;z++){
                            if(z == number){
                            	 var s_class = "_show"+z;
                                  $("."+s_class).show();
                            }else{
                            	 var s_class = "_show"+z;
                            	 $("."+s_class).hide();
                            }
                           }
	                	   $(".parts-into-window").show();
	                	}
	                },
	                error: function (data, status, e)// 服务器响应失败处理函数
	                {
	                	layer.alert("数据连接异常,请联系管理员!");
	                }
	            }
	        )
     }
}