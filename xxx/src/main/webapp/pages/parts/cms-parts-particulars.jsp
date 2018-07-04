<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>宇航元器件选用平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
   	<%@include file="/base.jsp" %> 
   <link rel="stylesheet" href="css/cms-headerPublic.css"/> 
    <link rel="stylesheet" href="<%=path %>/css/cms-parts-particulars.css"/>
    <link rel="stylesheet" href="scripts/bigerImages/zoomify.min.css" />
    <!-- ZTree样式引入 -->
	 <link rel="stylesheet" href="zTreeStyle/zTreeStyle.css" type="text/css">
	<!-- ZTree核心js引入 -->
	 <script type="text/javascript" src="js-tree/jquery.ztree.core.js"></script> 
    <script type="text/javascript" src="scripts/ECharts/echarts.min.js"></script>
    <script src="scripts/treeTable/jquery.treeTable.js" type="text/javascript"></script>
    <script type="text/javascript" src="scripts/jquery.raty.min.js"></script>
</head>
<body>
 <%@include file="/public.jsp" %> 
<div class="containerAll">
    <div class="containerAllPage">
        <!---------表格---------->
        <div id="main">
         <%@include file="/header.jsp" %>
             <div class="page-message">
                <h3 id="minuNavigation"><span class="label label-default">Now</span> <a href="pages/loginpage/index.jsp"><fmt:message  key="homePage"/></a>> <a href="pages/parts/cms-parts.jsp"><fmt:message  key="ComponentNavigator"/></a>></h3>
            </div>           
        <div class="table-responsive">
        <div class="part-banner">
                 <div class="part-banner-box">                    
										<img src="images/parts-1.png" id="show_img">					                     
					<div class="afterimg">				
                         <b><span id="minuItem"></span></b> 
                         <div class="detail-evaluate left">
                <a onclick="addCollectionFromMinute(this);" ><span id="loveSpan" class="glyphicon glyphicon-heart"></span> <fmt:message  key="attentionBtn"/></a>
                <a href="javascript:addCompareFromMinuti()"><span class="glyphicon glyphicon-sort"></span>  <fmt:message  key="contrastBtn"/></a>
                <a class="user-evaluate-btn"><span class="glyphicon glyphicon-star"></span>  <fmt:message  key="evaluateBtn"/></a>
               <%--  <a class="user-choose-btn" onclick="addToProductDialog1();"><span class="glyphicon glyphicon-check"></span>  <fmt:message  key="selectionBtn"/></a> --%>
               </div>                       
                     </div>
                     <div class="partpdf"><a class="btn btn-primary" onclick="particularsPdf()" id = "pdfId"><i></i>Datesheets</a></div>
                 </div>                 
        </div>          
                  <div class="parts-detail-tab-right left">
                   <div class="active" id="attribute">
                                <div class="attribute-box">
                                    <div class="partsTitle"><span><fmt:message  key="minute-attr"/></span></div>  
                                    <table class="table table-striped table-bordered partsAddTable"
							id="partDataTable">
							
						</table>  						                                
                                    <!-- <div class="left showAllBox">
                                      <a class="showAll" href="javascript:void(0)"><span class="glyphicon glyphicon-plus" id="changeSpan"></span><b id="trState"></b></a>
                                    </div>   -->                                                              
                                </div>
                            </div> 
                            <div class="left" id="maodianPdf"></div>
                                   <div class="left" id="Datesheets">
                                <div class="Datesheets-box" id="-1">
                                <div class="partsTitle"><span><fmt:message  key="minu-pdf"/></span></div>
                                    <div id="Datesheets-box">
                                    </div>
                                </div>
                            </div>                     
                            <div class="left" id="replace">
                            <div class="replace-box">
                            <div class="partsTitle"><span><fmt:message  key="minute-substituteMaterial"/></span></div>                                          
                                    <div id="">
                                    <table class="compare-picture-table compare-table" ID="firstTr">
                                <tr>
                                    <th class="table1Th">
                                        <span ><input type="checkbox" class="left"/><span class="left">高亮不同项</span></span>
                                        <span><input type="checkbox" class="left"/><span class="left">隐藏相同项</span></span>
                                    </th>
                                    <td class="compare-goods-td">
                                        <div class="compare-goods">
                                            <dl>
                                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                                <dd>瓷介电容器</dd>
                                                <dd class="view-now-box">
                                                <a href="<%=path %>/pages/parts/cms-parts-minute.jsp" class="btn btn-danger view-now">立即查看</a>
                                                </dd>                                               
                                            </dl>
                                          <a class="compare-goods-delete right">删除</a>                             
                                        </div>
                                    </td>
                                    <td class="compare-goods-td">
                                        <div class="compare-goods">
                                            <dl>
                                                <dt><img src="<%=path %>/images/compare1.png" alt=""/></dt>
                                                <dd>瓷介电容器</dd>
                                                <dd class="view-now-box">
                                                <a href="<%=path %>/pages/parts/cms-parts-detail.jsp" class="btn btn-danger view-now">立即查看</a>
                                                </dd>                                               
                                            </dl>
                                          <a class="compare-goods-delete right">删除</a>                             
                                        </div>
                                    </td>
                                    <td>
                                        <div class="add-simple">
                                            <a class="add-simple-btn">+添加同类商品</a>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="add-simple">
                                            <a>+添加同类商品</a>
                                        </div>
                                    </td>
                                </tr>
                                  <tr>
                                               <th>元器件型号</th>
                                               <td>2847-fnj</td>
                                               <td>dhneui2-3r</td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                                           <tr>
                                               <th>产品参数</th>
                                               <td>iefh39</td>
                                               <td>iejfuh8</td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                                           <tr>
                                               <th>物料名称</th>
                                               <td>瓷介电容器</td>
                                               <td>金属膜电容器</td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                                           <tr>
                                               <th>物料编码</th>
                                               <td>1234</td>
                                               <td>5678</td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                                           <tr>
                                               <th>元器件描述</th>
                                               <td></td>
                                               <td></td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                                           <tr>
                                               <th>制造商</th>
                                               <td></td>
                                               <td></td>
                                               <td></td>
                                               <td></td>
                                           </tr>
                            </table>                               
                                    </div>
                                </div>
                            </div> 
                            <div class="left" id="live">
                                <div class="live-box">
                                <div class="partsTitle"><span><fmt:message  key="minute-lifeCycle"/></span></div> 
                                    <div id="live-zheXian"></div>
                                </div>
                            </div>
                            <div class="left" id="project">
                                <div class="project-box">
                                <div class="partsTitle"><span><fmt:message  key="minute-productTraceability"/></span></div>
                                    <div id="project-box">
                                    </div>
                                </div>
                            </div>   
                            <div class="left" id="evaluate">
                                <div class="evaluate-box">
                                <div class="partsTitle"><span><fmt:message  key="minute-Eva"/></span></div>
                                <div id="evaluate_box">
                                </div>                            
                                </div>                               
                            </div>
                            <div class="clear"></div>
                            <div class="table-footers">
                                      <div id="Pagination" class="pagination"></div>                               
                             </div>
                </div>
            </div> 
        </div> 
        <!--  用户评价弹窗-->
         <div class="user-evaluate-window">
    <div class="user-evaluate-window-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-user"></span>  <fmt:message  key="minu-DBTip4"/></a>
        <button type="button" class="close user-evaluate-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
      <div class="user-evaluate-window-body">
             <div class="start-evaluate">
             <span class="left"><fmt:message  key="minu-DBTip5"/>：</span>
            <div id="star" class="left" data-score="0"></div>
             </div>
               <div class="word-evaluate">
             <span><fmt:message  key="minu-DBTip6"/>：</span>
             <input type="text" class="form-control"  name="evaContent" placeholder="<fmt:message  key="minu-DBTip7"/>"/>
             </div>
             <div class="evaluate-btn"><a href="javascript:saveRaty()" class="btn btn-gray"><fmt:message  key="saveBtn"/></a></div>
      </div>
</div>
        <!--  用户评价修改弹窗-->
         <div class="user-evaluateEdit-window">
    <div class="user-evaluateEdit-window-header bg-gray">
        <a class="left"><span class="glyphicon glyphicon-user"></span>  <fmt:message  key="minu-DBTip8"/></a>
        <button type="button" class="close user-evaluateEdit-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
      <div class="user-evaluate-window-body">
             <div class="start-evaluate">
             <span class="left"><fmt:message  key="minu-DBTip5"/>：</span>
            <div id="starUpdate" class="left" data-score="0"></div>
             </div>
               <div class="word-evaluate">
             <span><fmt:message  key="minu-DBTip6"/>：</span>
             <input type="text" class="form-control"  name="evaContentUpdate"/>
             </div>
             <div class="evaluate-btn"><a id="updateRaty" onclick="updateRaty()" class="btn btn-gray"><fmt:message  key="saveBtn"/></a></div>
      </div>
</div>
<!-- 添加到产品页面 -->
 <div class="parts-product-window">
    <div class="parts-product-window-header bg-danger">
        <a class="left"><span class="glyphicon glyphicon-tasks"></span><fmt:message  key="minute-addPro"/></a>
        <button type="button" class="close parts-product-window-close" aria-label="Close"><span>&times;</span></button>
    </div>
       <div class="productmix-treeBox">
       	<ul id="bomtree" class="ztree"></ul>
		</div>
       <table class="parts-product-window-table">
        	<tr>
                <td colspan="2">
                <a class="btn btn-danger left" onclick="addToProduct1();"><fmt:message  key="saveBtn"/></a>
                </td>
            </tr>
        </table>
</div>
        <!---------页尾---------->
       <%@include file="/footer.jsp" %>
    </div>
</div>
<script type="text/javascript" src="<%=path %>/scripts/javascript.js"></script>
<script type="text/javascript" src="<%=path %>/scripts/partMinute.js"></script>
<script type="text/javascript" src="scripts/partTree.js"></script>
	<script type="text/javascript" src="scripts/bigerImages/zoomify.min.js"></script>
<script>
     (function(){
        function Zoom(object){
            this.zoomArea=$(".zoom",object);//保存促发放大效果的区域
            this.moveArea=$(".move",object);//保存移动区域
            this.zoomDetail=$(".zoomDetail",object);//保存放大镜区域
            this.zoomDetailImg=$("img",this.zoomDetail);//保存放大镜里面的图
            this.zoomAreaWidth=this.zoomArea.width();
            this.moveAreaWidth=this.moveArea.width();
            this.zoomAreaHeight=this.zoomArea.height();
            this.moveAreaHeight=this.moveArea.height();
            this.zoomDetailWidth=this.zoomDetail.width();
            this.zoomDetailHeight=this.zoomDetail.height();
            this.zoomAreaOffset=this.zoomArea.offset();//初始化放大区域在视口中的相对偏移;
            this.XY=null;//初始化鼠标相对于放大区域的偏移偏移值
            this.moveBili=null;//
            var _this_=this;
            this.zoomArea.mousemove(function(e){//当鼠标在放大区域移动的时候执行
                _this_.move(e.pageX,e.pageY);
            }).mouseover(function(){
                _this_.moveArea.show();
                _this_.zoomDetail.show();
            }).mouseout(function(){
                _this_.moveArea.hide();
                _this_.zoomDetail.hide();
            });
            this.calculate();//初始化并计算出需要的比例值
            //以下是小图部分的功能实现
            this.l=0;
            this.scrollObj=$(".slideMain ul",object);//保存ul滚动对象
            this.lis=this.scrollObj.children();//保存小图片列表
            this.btnR=$(".btnR",object);//保存右边按钮
            this.btnL=$(".btnL",object);//保存左边边按钮
            this.lis.click(function(){
                _this_.changeImgSrc(this);
            });
            if(this.lis.length>6){//判断图片数是否超出显示区域，是的话就注册滚动事件
                this.s=this.lis.length-6;//获取多余出来的图片数
                this.scrollObj.width(60*this.lis.length+"px");//当图片数超出默认值时，设置ul的宽度
                this.btnL.click(function(){_this_.scrollRight();}).mouseover(function(){$(this).addClass("hover")}).mouseout(function(){$(this).removeClass("hover");});
                this.btnR.click(function(){_this_.scrollLeft();}).mouseover(function(){$(this).addClass("hover")}).mouseout(function(){$(this).removeClass("hover");});;
            }
        };
        Zoom.prototype={
            scrollLeft:function(){
                if(Math.abs(this.l)==this.s){return};
                this.l--;
                this.scrollObj.animate({left:this.l*58+"px"},"fast");
            },
            scrollRight:function(){
                if(this.l==0){return};
                this.l++;
                this.scrollObj.animate({left:this.l*58+"px"},"fast");
            },
            changeImgSrc:function(o){
                //改变标识样式
                $(o).addClass("selected").siblings().removeClass("selected");
                this.zoomArea.find("img").attr("src",$(o).find("img").attr("medium-img"));
                this.zoomDetailImg.attr("src",$(o).find("img").attr("medium-img"));

            },
            move:function(x,y){//鼠标在放大区域移动的时候执行的函数
                this.XY=this.mousePosAndSetPos(x,y);//计算出鼠标相对于放大区域的x,y值
                //设置滑块的位置
                this.moveArea.css({
                    left:this.XY.offsetX+"px",
                    top:this.XY.offsetY+"px"
                });
                //设置大图在细节位置
                this.zoomDetailImg.css({
                    marginLeft:-this.XY.offsetX*this.moveBili+"px",
                    marginTop:-this.XY.offsetY*this.moveBili+"px"
                });
            },
            mousePosAndSetPos:function(x,y){//实时计算并设置滑块的位置
                x=x-this.zoomAreaOffset.left-this.moveArea.width()/2;
                y=y-this.zoomAreaOffset.top-this.moveArea.height()/2;
                x=x<0?0:x;
                y=y<0?0:y;
                x=x>(this.zoomAreaWidth-this.moveAreaWidth)?this.zoomAreaWidth-this.moveAreaWidth:x;
                y=y>(this.zoomAreaHeight-this.moveAreaHeight)?this.zoomAreaHeight-this.moveAreaHeight:y;
                return {
                    offsetX:x,
                    offsetY:y
                };
            },
            calculate:function(){//计算函数
                var widthBili,heightBili;
                //计算移动的滑块与放大镜铺面显示的比例宽高
                widthBili=(this.zoomAreaWidth*this.zoomDetailWidth)/this.moveAreaWidth;
                heightBili=(this.zoomAreaHeight*this.zoomDetailHeight)/this.moveAreaHeight;
                //把比出来的宽高
                this.zoomDetailImg.css({width:widthBili+"px",height:heightBili+"px"});
                //返回移动的比例
                this.moveBili=(widthBili-this.zoomDetailWidth)/(this.zoomAreaWidth-this.moveAreaWidth);
            }
        };
        var zoom=new Zoom($(".ZoomMain").eq(0));
        /**动态加载导航栏**/
        loadProperties();//国际化
        var htmls = getUrlParam("tempPartMark");
        var contends = "";
        if("" != htmls){
        	if(htmls == 'false'){
        		contends+='<a href="pages/parts/cms-parts.jsp?tempPartMark=false">'+$.i18n.prop("DirIn")+'></a>';
        	}
        	if(htmls == "true"){
        		contends+='<a href="pages/parts/cms-parts.jsp?tempPartMark=true">'+$.i18n.prop("DirOut")+'></a>';
        	}
        	contends += '<a class="active">'+$.i18n.prop("minutePage")+'</a>';
        }
    	$("#minuNavigation").append(contends);
    })(); 
    $("#star").raty();
    $("#stars").raty();
    var Votes = 0;//星星初始化
    $('#star').raty({
    	  click: function(score, evt) {
    		  Votes = score;
    	  }
    });
    //保存评论内容
    function saveRaty(){
    	loadProperties();
    	var evaContent = $("input[name='evaContent']").val();
    	var PartNumber = getUrlParam("goMinute");
    	var xxx  =$('#star').raty('score');
    	if(typeof(xxx) == "undefined"){
    		layer.alert($.i18n.prop("minu-evaTip1"));
    		return;
    	}
    	if("" == evaContent){
    		layer.alert($.i18n.prop("minu-evaTip2"));
            return;
    	}
    	$.ajax({
    		url: 'partComponentArrt/insertPartEvaluation.do',
    		contentType:'application/json;charset=UTF-8', 
    		data: {'Votes':xxx,'evaContent':encodeURI(evaContent),'PartNumber':PartNumber},
    		dataType: 'json',
    		cache: false,
    		success: function(json){
    			/**清空星级评价**/
    			$('#star').raty({
                 score: function() {
                 return $(this).attr('data-score');
                  }
                 });
    			$("input[name='evaContent']").val('')
    			$('.user-evaluate-window').hide();
//     			$("#evaluate").css("color","#d9534f").siblings("li").css("color","#555");
    			layer.alert($.i18n.prop("alertMsg2"));
    			$("#Pagination").html('');
    			evaluateShow();
    		},
    		error: function(){
    			layer.alert("数据处理异常，请联系管理员!");
    		}
    	})
    }
    function evaluateShow(currentPage){
    	loadProperties();
    	if(isNaN(currentPage)){
    		currentPage = 0;
    	}
    	var PartNumber = getUrlParam("goMinute");
    	var addBasic = '1';
		var addAfter = parseInt(addBasic)+parseInt(currentPage);
    	$.ajax({
    		url: 'partComponentArrt/selectPartEvaluationList.do',
    		data: {'PartNumber':PartNumber,'page':addAfter},
    		dataType: 'json',
    		cache: false,
    		success: function(json){
    			var partEvaluationList = json.list;
    			var evaluate_box = '';
    			if(null == partEvaluationList || partEvaluationList.length==0){
    				evaluate_box += '<div class="evaluate-nav">';
    				evaluate_box += '<ul>';
    				evaluate_box += '<li class="first">'+$.i18n.prop("minute-allEnv")+'(0)'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-goodEnv")+'(0)'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-midEnv")+'(0)'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-badEnv")+'(0)'+'</li>';
    				evaluate_box += '<li class="last">'+$.i18n.prop("noData")+'</li>';
    				evaluate_box += '</ul>';
    				evaluate_box += '</div>';
    				$("#evaluate_box").html(evaluate_box);
    			}else{
    				var total = json.total;
    				var bestEva = json.bestEva;
    				var betterEva = json.betterEva;
    				var badEva = json.badEva;
    				var count=json.count;
    				var pageCount = json.pageCount;
    				var currentPage = json.currentPage;
    				var pageSize = json.pageSize;
    				var mapData = json.mapData;
    				evaluate_box += '<div class="evaluate-nav">';
    				evaluate_box += '<ul>';
    				evaluate_box += '<li class="first">'+$.i18n.prop("minute-allEnv")+'('+count+')'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-goodEnv")+'('+bestEva+')'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-midEnv")+'('+betterEva+')'+'</li>';
    				evaluate_box += '<li>'+$.i18n.prop("minute-badEnv")+'('+badEva+')'+'</li>';
    				evaluate_box += '<li class="last">'+$.i18n.prop("minute-timeSort")+'</li>';//默认时间排序
    				evaluate_box += '</ul>';
    				evaluate_box += '</div>';
    			for(var i=0;i<partEvaluationList.length;i++){
    				evaluate_box += '<div class="evaluate-body left">';
    				evaluate_box += '<div class="left evaluate-user">';
    				evaluate_box += '<div class="evaluate-name">';
    				evaluate_box += '<img src="images/eva-user.png" alt=""/>';
    				evaluate_box += '<span>'+partEvaluationList[i].userName+'</span>';
    				evaluate_box += '</div>';
    				evaluate_box += '</div>';
    				evaluate_box += '<div class="evaluate-word left">';
    				evaluate_box += '<div id='+i+' class="star" data-score="'+partEvaluationList[i].votes+'"></div>';
    				evaluate_box += '<div>'+partEvaluationList[i].evaContent+'</div>';
    				evaluate_box += '<div>';
    				evaluate_box += '<span>【'+mapData[0].Part_Type+'-'+mapData[0].ITEM+'】</span>';
    				evaluate_box += '<span class="evaluate-data">'+toDate(partEvaluationList[i].createTime)+'</span>';
    				evaluate_box += '<span class="right"><a class="evaluateEdit" onclick="evaluateEdit(this,'+partEvaluationList[i].id+')">'+"修改"+'</a><a class="evaluateDelete" onclick="evaluateDel('+partEvaluationList[i].id+')">'+"删除"+'</a></span>';
    				evaluate_box += '</div>';
    				evaluate_box += '</div>';
    				evaluate_box += '</div>';
    			}
    			//分页插件
    			if($("#Pagination").html().length == ''){
    			       $("#Pagination").pagination(
    			    		   count,
    	    	                {
    	    	                    items_per_page : pageSize,
    	    	                    num_edge_entries : currentPage,
    	    	                    num_display_entries : 8,
    	    	                    callback: function(currentPage, panel){
    	    	                    	if(count==null){
    	    	                    		evaluateShow(currentPage);
 						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	                });
    			}
    			$("#evaluate_box").html(evaluate_box);
    			count=null;
    			}
    			//设置星星展示为只读
    			if(null != partEvaluationList){
    				if(null != partEvaluationList || partEvaluationList.length!=0){
            			for(var j=0;j<partEvaluationList.length;j++){
                			$("#"+j).raty({ readOnly: true, score: partEvaluationList[j].votes});
            			}
            	     }
    			}
    		},
    		error: function(){
    			layer.alert("数据处理异常，请联系管理员!");
    		}
    	})
    }
    //详情页添加对比产品
    function addCompareFromMinuti(){
    	loadProperties();//国际化
    	var PartNumber = getUrlParam("goMinute");
    	var compareNum = Array();
    	compareNum = JSON.parse(window.sessionStorage.getItem("searchIndex"));
    	if(null == compareNum){
    		compareNum = Array();
    	}
    	if(null != compareNum && compareNum.length>0){
    		for(var i=0;i<compareNum.length;i++){
    			if(PartNumber == compareNum[i]){
        			layer.alert($.i18n.prop("minu-DBTip1"));
                    return;
    			}
    		}
    	}
    	  if(compareNum.length <4){
    	       compareNum.push(PartNumber);
    	       var tempIndex = JSON.stringify(compareNum);
    	       window.sessionStorage["searchIndex"] = tempIndex;
    		     $("#"+PartNumber).attr('checked',true);
    		     var  eleShopCart = document.querySelector("#compareNum");
    				if(null != compareNum){
    					eleShopCart.querySelector(".compare-num").innerHTML = compareNum.length;
    				}
      		     layer.alert($.i18n.prop("minu-DBTip2"));
    	  }else{
    		   layer.alert($.i18n.prop("minu-DBTip3"));
    		   return;
    	  }
    }
    //日期格式转换
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
    	var s = date.getSeconds();
    	s = s<10?("0"+s):s;
    	var str = y+"-"+m+"-"+d+" "+h+":"+M+":"+s;
    	return str;
    	}
    $(document).ready(function(){
    	initParticularsAttr();//属性信息
        replaceShow();//可替代料显示
        evaluateShow();//评价类别
        minuteDataRights();//权限
    	loadZhexian();//折线jiazai
    	loadTree();//产品追溯
    	$('.parts-images img').zoomify();
    	dataAcquisition("minu",getUrlParam("goMinute"));//数据采集
        $('.user-evaluate-window,.parts-product-window,.user-evaluateEdit-window').draggable();
        $(".parts-product-window").hide();
    })
</script>
</body>
</html>