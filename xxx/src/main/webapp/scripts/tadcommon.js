$("#downloadButton").click(function(){$("#tadialog").css('display','inline'); $("#tadialogbackground").css('display','inline');});
$(".dialog-close,#tadialogCancel").click(function(){$("#tadialog").css('display','none');$("#tadialogbackground").css('display','none');});
$("#extendExport").click(function(){$("#tadialog").css('display','inline'); $("#tadialogbackground").css('display','inline');});
$("#tangram-download").click(function(){
	var mpvalue = [];//定义一个数组    
	var indicators ="";
	$('input[name="download__report"]:checked').each(function(){mpvalue.push($(this).val());});	
	for(i=0;i<mpvalue.length;i++)indicators+=mpvalue[i]+',';indicators=indicators.substr(0, indicators.length-1);
	var downurl = maindownurl;
	downurl = downurl+"&indicators="+indicators;
	if(window.startDate){downurl = downurl+"&startDate="+startDate+"&endDate="+endDate;}
	downurl = downurl + "&sField="+$('#sField').val()+"&sOpr="+$('#sOpr').val()+"&sWord="+ $('#sWord').val();
	window.location.href = downurl;
	$("#tadialog").css('display','none');
	$("#tadialogbackground").css('display','none');
});
  	  

