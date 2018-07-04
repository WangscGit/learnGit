$(function(){
	loadProperties();//国际化
	$.ajax({
		url: 'partComponentArrt/selectPartHistoriesMainList.do',
		data: 'partNumber='+GetQueryString("goHistory"),
		dataType: 'json',
		cache: false,
		success: function(json){
			var dataHtml='';
			datas = json.data;
			if(null == datas || "" == json || datas.length == 0){
				dataHtml +='<tr>';
				dataHtml +='<td colspan="3" align="center">--'+$.i18n.prop("noData")+'--</td>';
				dataHtml +='</tr>';
			}else{
				for( var j = 0;j<datas.length;j++){
			    dataHtml +='<tr onclick=openHIstory(\''+datas[j].PartNumber+'\','+datas[j].VersionID+')>';
				dataHtml +='<td>'+'</span>'+datas[j].VersionID+'</td>';
				dataHtml +='<td>'+toDate(datas[j].ModifyDate)+'</td>';
				dataHtml +='<td>'+datas[j].Modifier+'</td>';
				dataHtml +='</tr>';
				}
			}
			$("#historiesData").html(dataHtml);
		},
		error: function(){
			layer.alert($.i18n.prop("alertError"));
		}
	});
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
        	contends += '<a class="active">'+$.i18n.prop("historyPage")+'</a>';
        }
    	$("#historyNavigation").append(contends);
})
/**修改历史记录查询测试****************/
function historiesTestData(){
	$.ajax({
		url: 'partComponentArrt/selectPartHistoriesList.do',
		dataType: 'json',
		cache: false,
		success: function(json){
			var tittleHtml = '';
			var dataHtml='';
			var tittle = json.showNames;
			var datas = json.data;
			if(null != tittle && tittle.length>0){
				tittleHtml +='<tr>';
				for(var i=0;i<tittle.length;i++){
					tittleHtml +='<td>'+tittle[i]+'</td>';
				}
				tittleHtml +='</tr>';
			}
			if(null == datas || "" == json || datas.length == 0){
				dataHtml +='<tr>';
				dataHtml +='<td colspan="'+tittle.length+'" align="center">--没有数据--</td>';
				dataHtml +='</tr>';
			}else{
				for( var j = 0;j<datas.length;j++){
					alert(datas);
				var addBasic = '1';
				var addBefore = i+'';
				var addAfter = parseInt(addBasic)+parseInt(addBefore);
				var tdData = datas[j].split(",");
				dataHtml +='<tr id="'+addAfter+'">';
				for(var x=0;x<tdData.length;x++){
					if(x==0){
						var tdDataFirst = tdData[x].split("[");
						dataHtml +='<td>'+tdDataFirst[1]+'</td>';
					}else if(x==tdData.length){
						var tdDataEnd = tdData[x].split("]");
						dataHtml +='<td>'+tdDataEnd[1]+'</td>';
					}else{
						dataHtml +='<td>'+tdData[x]+'</td>';
					}
				}
				dataHtml +='</tr>';
				}
			}
			$("#historiesTittle").html(tittleHtml);
			$("#historiesData").html(dataHtml);
		},
		error: function(){
			layer.alert("数据加载异常，请联系管理员！");
		}
	});
}
function toDate(v) {
var date = new Date();
date.setTime(v.time);
var y = date.getFullYear();
var m = date.getMonth()+1;
m = m<10?'0'+m:m;
var d = date.getDate();
d = d<10?("0"+d):d;
var h = date.getHours();
h = h<10?("0"+h):h;
var M = date.getMinutes();
M = M<10?("0"+M):M;
var str = y+"-"+m+"-"+d+" "+h+":"+M;
return str;
}
       