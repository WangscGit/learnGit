/*左侧列表*/
		 $(function(){
				$('ul.ztb_content_02 li').click(function(){				
						$(this).addClass('ztb_online').siblings('li.ztb_online').removeClass('ztb_online');
						$(this).parents('li').siblings('li').children('ul').find('li.ztb_online').removeClass('ztb_online');		
					})						
					$(".building").click(function(){
						$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-build.jsp");  
					   })
				    $(".materialSelection").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-materialSelection.jsp");  
				      })
				      $(".theoryInput").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-designInput.jsp");  
				      })		      
				      $(".consistencyCheck").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-consistencyCheck.jsp");  
				      })
				       $(".designReview").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-designReview.jsp");  
				      })
				      $(".functionalSimulation").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-functionalSimulation.jsp");  
				      })				     
				      $(".simulationSI").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-simulationSI.jsp");  
				      })	
				       $(".simulationPI").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-simulationPI.jsp");  
				      }) 
				      $(".lastSimulation").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-lastSimulation.jsp");  
				      }) 	
				      $(".PCBdesign").click(function(){
							$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-PCBdesign.jsp");  
					}) 
					$(".StandardizeFigure").click(function(){
							$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-StandardizeFigure.jsp");  
					})	
					$(".pigeonhole").click(function(){
							$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-pigeonhole.jsp");  
					})	
					$(".createDatabase").click(function(){
						$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-createDatabase.jsp");  
				})	
				//规则检查及报告，BOM表锚点到原理图输入页面中的对应位置
				$(".ruleCheck").click(function(){
					$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-designInput.jsp#ruleCheck");  
			})	
			$(".BOMtable").click(function(){
				$(".flow-design-processRight iframe").attr("src", "pages/design-processPage/cms-design-process-designInput.jsp#BOMtable");  
		})	
})
   	function clickli(dom){
				var ul = $(dom).next('ul');
				if (ul.is(":hidden")) {
					ul.css('display','block')
				}else{
					ul.css('display','none')
				}				
			}   
		 /*左侧列表滚动*/
	function js(obj){return document.getElementById(obj)}
var maxHeight=js("img").getElementsByTagName("ul")[0].getElementsByTagName("li").length*105;
//滚动图片的最大高度
var targety = 211;
//一次滚动距离
var dx;
var a=null;
function moveTop(){
 var le=parseInt(js("img").scrollTop);
 if(le>211){
 targety=parseInt(js("img").scrollTop)-211;
 }else{
 targety=parseInt(js("img").scrollTop)-le-1;
 }
 scTop();
}
function scTop(){
 dx=parseInt(js("img").scrollTop)-targety;
 js("img").scrollTop-=dx*.3;
 clearScroll=setTimeout(scTop,50);
 if(dx*.3<1){
 clearTimeout(clearScroll);
 }
}
function moveBottom(){
 var le=parseInt(js("img").scrollTop)+211;
 var maxL=maxHeight-600;
 if(le<maxL){
 targety=parseInt(js("img").scrollTop)+211;
 }else{
 targety=maxL
 }
 scBottom();
}
function scBottom(){
 dx=targety-parseInt(js("img").scrollTop);
 js("img").scrollTop+=dx*.3;
 a=setTimeout(scBottom,50);
 if(dx*.3<1){
 clearTimeout(a)
 }
}
/*iframe高度自适应*/
function changeFrameHeight(){
    var iframe= document.getElementById("myframe"); 
   /* ifm.height=document.documentElement.clientHeight;方法一获取浏览器窗口高度*/
    /*方法一根据内容获取iframe高度*/
   if(navigator.userAgent.indexOf("MSIE")>0||navigator.userAgent.indexOf("rv:11")>0||navigator.userAgent.indexOf("Firefox")>0){  
       iframe.height=iframe.contentWindow.document.body.scrollHeight;  
   }else{  
       iframe.height=iframe.contentWindow.document.documentElement.scrollHeight;  
   }  
}
window.onresize=function(){  
     changeFrameHeight();  
} 
/*异步请求抓取的数据，因为ajax还没有完全把数据追加到页面。所以要在回调函数里调用重设iframe高度的方法*/
function reSetIframeHeight(){  
    window.top.window.iframeAutoHeight();  
}  