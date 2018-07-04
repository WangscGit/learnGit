$(function(){
	var compareNum = Array();
	    compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	loadProperties();//国际化
	$.ajax({
	    url:  '/cms_cloudy/partComponentArrt/selectPartDataToCompare.do',
	    data: 'partNumber='+JSON.stringify(compareNum),
	    dataType: 'json',
	    cache: false,
	    success: function(json){
	    	var showNames = json.showName;
	    	var queryFields = json.queryFields;
	        var dataList = json.dataList;
	        var mapLinked = json.mapLinked;
	        var mainHtml = '';
	        //var detailHtml = '';
	        if(null == mapLinked || "" == mapLinked || mapLinked.length == 0){
	         mainHtml += '<tr>';
	         mainHtml += '<th>';
             mainHtml += '<span>' +'<input type="checkbox" class="left" onclick="checkDiverse()" name="checkDiverse"/>' + '<span class="left">' + $.i18n.prop("checkDiverse") + '</span>' + '</span>';                     
             mainHtml += '<span>' +'<input type="checkbox" class="left" onclick="checkSimple()" name="checkSimple" />' + '<span class="left">' + $.i18n.prop("checkSimple") + '</span>' + '</span>';                     
             mainHtml += '</th>';
             for(var i = 0;i<4;i++){
             mainHtml += '<td>';
             mainHtml += ' <div class="add-simple">';
             mainHtml += '<a>'+'--'+'</a>';
             mainHtml += '</div>';
             mainHtml += '</td>';
             }
             mainHtml += ' </tr>';
             mainHtml +='<tr>';
			 mainHtml +='<td colspan="'+5+'" align="center">'+$.i18n.prop("noData")+'</td>';
			 mainHtml +='</tr>';
	        }else{
	        var noData = parseInt(4)-parseInt(mapLinked.length);
	         mainHtml += '<tr>';
	         mainHtml += '<th>';
             mainHtml += '<span>' +'<input type="checkbox" class="left" onclick="checkDiverse()" name="checkDiverse" id = "checkDiverse"/>' + '<span class="left">' + $.i18n.prop("checkDiverse") + '</span>' + '</span>';                     
             mainHtml += '<span>' +'<input type="checkbox" class="left" onclick="checkSimple()" name="checkSimple" id = "checkSimple"/>' + '<span class="left">' + $.i18n.prop("checkSimple") + '</span>' + '</span>';                     
             mainHtml += '</th>';
              for(var i = 0;i<mapLinked.length;i++){
              mainHtml += '<td class="compare-goods-td">';
              mainHtml += '<div class="compare-goods">';
              mainHtml += '<dl>';
              mainHtml += '<dt>'+'<img src="/cms_cloudy/images/compare1.png" alt=""/>'+'</dt>';
              mainHtml += '<dd>'+mapLinked[i].Part_Type+'</dd>';
              mainHtml += '<dd class="view-now-box">'+'<a href="/cms_cloudy/pages/parts/cms-parts-particulars.jsp?goMinute='+mapLinked[i].PartNumber+'&partId='+mapLinked[i].id+'" class="btn btn-danger view-now">'+$.i18n.prop("nLookBtn")+'</a>'+'</dd>';
              mainHtml += '</dl>';
              mainHtml += ' <a class="compare-goods-delete right" href="javascript:deleteOneCompares(\''+mapLinked[i].PartNumber+'\')">'+$.i18n.prop("deleteBtn")+'</a> ';
              mainHtml += '</div> ';
              mainHtml += '</td>';
              }
              if(noData>0){
             for(var n = 0;n<noData;n++){
             mainHtml += '<td>';
             mainHtml += ' <div class="add-simple">';
             mainHtml += '<a>'+'--'+'</a>';
             mainHtml += '</div>';
             mainHtml += '</td>';
             }
             }
             mainHtml += ' </tr>';
             var aaaaa="--";
             for(var j=0;j<showNames.length;j++){
            	if(queryFields[j] == 'PartNumber'){
            		mainHtml += '<tr style="display:none">';
            	}else{
            		mainHtml += '<tr>';
            	}
             	mainHtml += '<th>'+showNames[j]+'</th>';
             	for(var xx=0;xx<mapLinked.length;xx++){
                 var dataListArray= dataList[xx];
                 if("" == dataListArray[j] || null == dataListArray[j] || " null" == dataListArray[j] || "null" == dataListArray[j]){
                 	 mainHtml += '<td>'+''+'</td>';
                 }else if(queryFields[j] == "schematic_img" || queryFields[j] == "ens_img" || queryFields[j] == "shape_img" || queryFields[j] == "size_img" || queryFields[j] == "characteristic_curve_img" || queryFields[j] == "typical_ap_img"){
                	mainHtml += '<td class="partsImages">'
					mainHtml += '<div class="left parts-images" data-toggle="tooltip"';
					mainHtml += 'data-placement="right" title="图片上传最佳尺寸【550px*320px】">';
					mainHtml += '<img src=' + dataListArray[j] + '>';
					mainHtml += '</div>';
					mainHtml += '</td>'
                 }else{
                 	 mainHtml += '<td>'+dataListArray[j]+'</td>';
                 }
             	}
             	if(noData>0){
             			for(var ss=0;ss<noData;ss++){
                        mainHtml += '<td>'+aaaaa+'</td>';
             	     }
             	}
             	mainHtml += '</tr>';
             }
	        }
	        $("#firstTr").html(mainHtml);
	        //$("#otherTr").html(detailHtml);
	    /******鼠标移入对比页面商品时显示删除*****/
        $(".compare-goods-td").hover(function(){
    	$(this).find(".compare-goods-delete").show();
        },function(){
    	$(this).find(".compare-goods-delete").hide();
        });
        /**图片显示**/
        $('.parts-images img').zoomify();
        /** 删除ID行* */
		var tableList = document.getElementById("firstTr");
		var removeIdTr = tableList.getElementsByTagName("tr");
		if (removeIdTr.length > 1) {
			$(removeIdTr[1].remove());
		}
	    },
	    error: function(){
	    	layer.alert($.i18n.prop("alertError"));
	    }
	})
	/**导航栏动态加载**/
	 var htmls = GetQueryString("tempPartMark");
        var contends = "";
        if("" != htmls){
        	if(htmls == 'false'){
        		contends+='<a href="pages/parts/cms-parts.jsp?tempPartMark=false">'+$.i18n.prop("DirIn")+'></a>';
        	}
        	if(htmls == "true"){
        		contends+='<a href="pages/parts/cms-parts.jsp?tempPartMark=true">'+$.i18n.prop("DirOut")+'></a>';
        	}
        	contends += '<a class="active">'+$.i18n.prop("comparePage")+'</a>';
        }
    	$("#compareNavigation").append(contends);
})
//删除某个对比项
function deleteOneCompares(data){
   var compareNums = Array();
	    compareNums = JSON.parse(window.sessionStorage.getItem("searchIndex"));
	     for(var x=0;x<compareNums.length;x++){
	     	if(compareNums[x] == data){
	     		compareNums.splice(x,1);
	     		//$("#"+partNumber).attr('checked',false);
	     	}
	     }
	 var tempIndex = JSON.stringify(compareNums);
     window.sessionStorage["searchIndex"] = tempIndex;
     window.location.reload();
}
//对比页---高亮不同项
function checkDiverse(){
    var table=document.getElementById("firstTr");//获取表格
	var Tr=table.getElementsByTagName("tr");//获取行
    var compareNum = Array();
	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));//获取对比数
	if(Tr.length <=1){ // 如果只有一行 说明没有数据
	   return;
	}
	if(compareNum.length<=1){
	    return;
	}
	if($("#checkDiverse").is(':checked')){ //checkBox选中
	for(var x=0;x<Tr.length;x++){
	   if(x == 0){//第一行不进行比较
	     continue;
	   }
	var list = new Array(); //集合初始化 存放td的text()
	var tdArr = $(Tr[x]).find("td");//获取td集合 //获得指定td的值tdArr.eq(s).text() 
	for(var s=0;s<compareNum.length;s++){
		var i = "";
		var value = tdArr.eq(s).html();
		value  =   value.replace(/^\s+|\s+$/g,"");
		if("" == value || null == value){ //如果td为空 为其赋值 去重时
	 	      i =  "空";
		}else{
			  i =  value;
		}
		list.push(i);
	  }
	  list = list.unique3();//数组去重
	  if(list.length != 1){ //如果相同，说明有重复项
	  	$(Tr[x]).css("background","#d9534f").css("color","#fff");
	  }
	}
	}else{//取消选中状态
	for(var x=0;x<Tr.length;x++){
		//$(Tr[x]).show();
		$(Tr[x]).css("background","").css("color","#333");
	  }
	}
}
//数组去重
Array.prototype.unique3 = function(){
 var res = [];
 var json = {};
 for(var i = 0; i < this.length; i++){
  if(!json[this[i]]){
   res.push(this[i]);
   json[this[i]] = 1;
  }
 }
 return res;
}
//隐藏相同项--对比
function checkSimple(){
   var table=document.getElementById("firstTr");//获取表格
	var Tr=table.getElementsByTagName("tr");//获取行
    var compareNum = Array();
	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));//获取对比数
	if(Tr.length <=1){ // 如果只有一行 说明没有数据
	   return;
	}
	if(compareNum.length<=1){
	    return;
	}
	if($("#checkSimple").is(':checked')){ //checkBox选中
	for(var x=0;x<Tr.length;x++){
	   if(x == 0){//第一行不进行比较
	     continue;
	   }
	var list = new Array(); //集合初始化 存放td的text()
	var tdArr = $(Tr[x]).find("td");//获取td集合 //获得指定td的值tdArr.eq(s).text() 
	for(var s=0;s<compareNum.length;s++){
		var i = "";
		var value = tdArr.eq(s).html();
		value  =   value.replace(/^\s+|\s+$/g,"");
		if("" == value || null == value){ //如果td为空 为其赋值 去重时
	 	      i =  "空";
		}else{
			  i =  value;
		}
		list.push(i);
	  }
	  list = list.unique3();//数组去重
	  if(list.length == 1){ //如果相同，说明有重复项
	  	$(Tr[x]).hide();
	  }
	}
	}else{//取消选中状态
	for(var x=0;x<Tr.length;x++){
		$(Tr[x]).show();
	  }
	}
}