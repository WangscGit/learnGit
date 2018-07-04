 /**构建websocket连接**/
      var websocket = null;  
        //判断当前浏览器是否支持WebSocket  
        if('WebSocket' in window){
        	var wsUrl = "ws://localhost:8086"+getContextPathForWS()+"/myHandler";
            websocket = new WebSocket(wsUrl);  
        }
        else{
            layer.alert('Not support websocket'); 
        }  
        //连接发生错误的回调方法  
        websocket.onerror = function(event){
            setMessageInnerHTML("error");  
        };  
        //连接成功建立的回调方法  
        websocket.onopen = function(event){
            setMessageInnerHTML("open");  
        }  
        //接收到消息的回调方法  
        websocket.onmessage = function(event){  
            setMessageInnerHTML(event.data);  
        }  
        //连接关闭的回调方法  
        websocket.onclose = function(){  
            setMessageInnerHTML("close");  
        }  
        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
        window.onbeforeunload = function(){  
            websocket.close();  
        }  
        //将消息显示在网页上 最新推荐 
        function setMessageInnerHTML(innerHTML){
        	var sendType = innerHTML.split(",");
        	if(sendType.length>0 && sendType[0] == "pressMessageCount"){//流程催办
        	   $(".messageNumber").html(sendType[sendType.length-1]);
      		   $(".receiveEmail").css({"display":"block"}).css({right: -260,bottom:-150}).animate({right: 0,bottom:0}, 1000);/* 消息提示框 */
        	}else if(sendType.length>0 && sendType[0] == "compulsionLogin"){//强制登录
        	    layer.alert('该账号已在异地登录！', {
                      }, function(){
                       location.reload();
                });
        	}else{
        	var json = new Array();
        	if('open' != innerHTML && 'close' != innerHTML){
        		var json= JSON.parse(innerHTML);
        	}
        	   var html = '';
                if( null != json && '' != json && json.length >0){
                	for(var xx=0;xx<json.length;xx++){
                	var hotId = 'hotx'+xx;
                	html += '<div class="Intelligent-son Intelligent-son-new">';
                	html += '<a>'+'<img class="left" src="images/parts-1.png">'+'</a>';
        	        html +='<div class="Intelligent-son-right right">';
        	        html +='<p>'+json[xx].item+'</p>';
        	        html += '<div id='+hotId+' class="hotStar" data-score="'+json[xx].Votes+'"></div>';
        	        // html +='<span>'+'<img src="images/fivestarts.png">'+'</span>';
        	        html +='<a href="'+getContextPathForWS()+'/pages/parts/cms-parts-particulars.jsp?goMinute='+json[xx].PartNumber+'&partId='+json[xx].id+'&tempPartMark='+'null'+'&isColl='+'false'+'" class="btn btn-danger">'+'详情'+'</a>';
        	        html +='</div>';
        	        html +='</div>';
                	}
               }
        	if(null != document.getElementById('newPartMain') && innerHTML != "open" && innerHTML != "close"){
        	   $("#newPartMain").html(html);
        	   $("#IntelligentPushId").css("display","block").css({right:-300}).animate({right:0},700);
    		   $("#fixedSideId").css({right:0}).animate({right:300},700);
         	   $(".receiveEmail").css({"display":"block"}).css({right: -260,bottom:-150}).animate({right: 0,bottom:0}, 1000);/* 消息提示框 */
    		   //document.getElementById('newPartMain').html = html;  
        	}
        	/*if(null != document.getElementById('newPartDetail') && innerHTML != "open" && innerHTML != "close"){
        		 $("#newPartDetail").html(html);
        		 $("#IntelligentPushId").css("display","block").css({right:-300}).animate({right:0},700);
    		     $("#fixedSideId").css({right:0}).animate({right:300},700);
        		//document.getElementById('newPartDetail').html = html ;  
        	}*/
        	 //设置星星展示为只读
    			if(null != json && "" != json){
    				if(null != json || json.length!=0){
            			for(var j=0;j<json.length;j++){
            				var hot = 'hotx'+j;
                			$("#"+hot).raty({ readOnly: true, score: json[j].Votes});
            			}
            	     }
    			}
        	}
        }  
        //关闭连接  
        function closeWebSocket(){  
            websocket.close();  
        }  
        //发送消息  
        function sendWebsocket(message){  
            websocket.send(message);  
        }  
/****器件数据统计与推送******/ 
/**首页热门搜索初始化**/
function initIndexSearch(){
	loadProperties();
	     $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/selectHotSearchData.do',
			dataType : 'json',
			cache : false,
			success : function(json) {
				var html = '';//热门搜索：
                html += '<span>'+$.i18n.prop("indexHotSearce")+'</span>';
                if(null != json && "" != json){
                    for(var i=json.length-1;i>=0;i--){ 
                    	html += '<a href="javascript:searchParts(\''+json[i].inputContent+'\')" data-toggle="popover" title=\''+json[i].inputContent+'\'>'+json[i].inputContent+'</a>';
                    	html += '&nbsp;&nbsp;';
          	      }
               }
                 $(".soso-example").html(html);
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
		});
}
/**主页点击热门搜索内容跳转页面**/
function searchParts(item){
		saveSearch(item);
		item=b.encode(item);
		window.location.href=getContextPathForWS()+"/pages/parts/cms-parts.jsp?item="+item;
}
/**最新推荐**/
function pushNewPart(){
	     $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/selectPushNewPart.do',
			dataType : 'json',
			cache : false,
			success : function(json) {
				var html = '';
                if(null != json && '' != json && json.length >0){
                	for(var xx=0;xx<json.length;xx++){
                	var hotId = 'hot'+xx;
                	html += '<div class="Intelligent-son Intelligent-son-new">';
                	html += '<a>'+'<img class="left" src="images/parts-1.png">'+'</a>';
        	        html +='<div class="Intelligent-son-right right">';
        	        html +='<p>'+json[xx].item+'</p>';
        	        html += '<div id='+hotId+' class="hotStar" data-score="'+json[xx].Votes+'"></div>';
        	        // html +='<span>'+'<img src="images/fivestarts.png">'+'</span>';
        	        html +='<a href="'+getContextPathForWS()+'/pages/parts/cms-parts-particulars.jsp?goMinute='+json[xx].PartNumber+'&partId='+json[xx].id+'&tempPartMark='+'null'+'&isColl='+'false'+'" class="btn btn-danger">'+'详情'+'</a>';
        	        html +='</div>';
        	        html +='</div>';
                	}
               }
                if(null != document.getElementById('newPartMain')){
        	       $("#newPartMain").html(html);
        	     }
        	   // if(null != document.getElementById('newPartDetail')){
        		  //  $("#newPartDetail").html(html);
        	  //   }
        	     //设置星星展示为只读
    			if(null != json && "" != json){
    				if(null != json || json.length!=0){
            			for(var j=0;j<json.length;j++){
            				var hot = 'hot'+j;
                			$("#"+hot).raty({ readOnly: true, score: json[j].Votes});
            			}
            	     }
    			}
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
		});
}
/**浏览最多初始化**/
function selectHotSearchFromSelf(){
    $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/selectHotSearchFromSelf.do',
			dataType : 'json',
			cache : false,
			success : function(json) {
				var html = '';
                if(null != json && '' != json && json.length>0){
                	html += '<ul>';
                    for(var i=json.length-1;i>=0;i--){
                    	var starId = 'hot'+i;
                    	html +=  '<li> '+'<div class="Intelligent-son">';
                    	html +=  '<a>'+'<img class="left" src="images/parts-1.png">'+'</a>';
                    	html +=  '<div class="Intelligent-son-right right">';
                    	html +=  '<p>'+json[i].item+'</p>';
                    	html += '<div id='+starId+' class="hotStar" data-score="'+json[i].Votes+'"></div>';
                    	//html +=  '<span>'+'<img src="images/fivestarts.png">'+'</span>';
                    	html +=  '<a href="'+getContextPathForWS()+'/pages/parts/cms-parts-particulars.jsp?goMinute='+json[i].PartNumber+'&partId='+json[i].id+'&tempPartMark='+'null'+'" class="btn btn-danger">'+'详情'+'</a>';
                    	html +=  '</div>';
                    	html +=  '</div>';
                    	html +=  '</li>';
          	        }
          	        html += '</ul>';
               }
                $("#scrollDiv").html(html);
                if(null !=json &&  json.length>3){
                    $("#scrollDiv").Scroll({line:1,speed:500,timer:3000,up:"but_up",down:"but_down"});
                }
                //设置星星展示为只读
    			if(null != json && "" != json){
    				if(null != json || json.length!=0){
            			for(var j=0;j<json.length;j++){
            				var hot = 'hot'+j;
                			$("#"+hot).raty({ readOnly: true, score: json[j].Votes});
            			}
            	     }
    			}
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
		});
}
/**推送数据保存**/
function addPushDatas(data){
    $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/insertPushDatas.do',
			dataType : 'json',
			cache : false,
			success: function(json){
			sendWebsocket(data);//推送
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
		});
}
/**跳转至详情页**/
function goMinuByPush(item){
    $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/goMinuByPush.do',
			data: 'item='+item,
			type: 'post',
			dataType : 'json',
			cache : false,
			success: function(json){
			   if(json == null || "" == json){
			      layer.alert("找不到该数据的详细信息，请刷新重试！");
			   }else{
			      window.location.href=getContextPathForWS()+"/pages/parts/cms-parts-particulars.jsp?goMinute="+json.PartNumber+"&partId="+json.id;
			   }
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
	});
}
/**指定人消息发送**/
function appointSend(){
  sendWebsocket("appoint");
}
/**数据采集**/
function dataAcquisition(type,data){
    if("minu" == type || "follow" == type){
       $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/setDataAcquisition.do',
			data: {'type':type,'data':data},
			type: 'post',
			dataType : 'json',
			cache : false,
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
	  });
    }else if("select" == type){
    	     $.ajax({
			url : getContextPathForWS()+'/pushPartDataController/setDataAcquisition.do',
			data: {'type':type,'data':JSON.stringify(data)},
			type: 'post',
			dataType : 'json',
			cache : false,
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
	    });
    }
}
/**待办数量显示**/
function getWorkNum(){
    $.ajax({
			url : getContextPathForWS()+'/WorkflowMainsController/selectProcessTaskNum.do',
			type: 'post',
			dataType : 'json',
			cache : false,
			success: function(json){
			   $(".work-num").html(json);
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}
	});
}
//获取根路径
function getContextPathForWS() {
    var pathName = document.location.pathname;
    var index = pathName.substr(1).indexOf("/");
    var result = pathName.substr(0,index+1);
    return result;
}
//base64编码方式
function Base64() {

	// private property
	_keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	base64DecodeChars = new Array(
	         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	         -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
	         52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
	         -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
	         15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
	        -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
	        41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);
	// public method for encoding
	this.encode = function(input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;
		input = _utf8_encode(input);
		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
			output = output + _keyStr.charAt(enc1) + _keyStr.charAt(enc2)
					+ _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
		}
		return output;
	}

	// public method for decoding
	this.decode = function(str) {
		var c1, c2, c3, c4;
        var i, len, out;
        len = str.length;
        i = 0;
        out = "";
        while(i < len) {
        
        do {
            c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while(i < len && c1 == -1);
        if(c1 == -1)
            break;
        
        do {
            c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];
        } while(i < len && c2 == -1);
        if(c2 == -1)
            break;
        out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
        
        do {
            c3 = str.charCodeAt(i++) & 0xff;
            if(c3 == 61)
            return out;
            c3 = base64DecodeChars[c3];
        } while(i < len && c3 == -1);
        if(c3 == -1)
            break;
        out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
        
        do {
            c4 = str.charCodeAt(i++) & 0xff;
            if(c4 == 61)
            return out;
            c4 = base64DecodeChars[c4];
        } while(i < len && c4 == -1);
        if(c4 == -1)
            break;
        out += String.fromCharCode(((c3 & 0x03) << 6) | c4);
        }
        return _utf8_decode(out);
	}

	// private method for UTF-8 encoding
	_utf8_encode = function(string) {
		string = string.replace(/\r\n/g, "\n");
		var utftext = "";
		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);
			if (c < 128) {
				utftext += String.fromCharCode(c);
			} else if ((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			} else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}
		return utftext;
	}

	// private method for UTF-8 decoding
	_utf8_decode = function(str) {
		var out, i, len, c;
	       var char2, char3;
	       out = "";
	       len = str.length;
	       i = 0;
	       while(i < len) {
	       c = str.charCodeAt(i++);
	       switch(c >> 4)
	       { 
	         case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
	           // 0xxxxxxx
	           out += str.charAt(i-1);
	           break;
	          case 12: case 13:
	           // 110x xxxx   10xx xxxx
	           char2 = str.charCodeAt(i++);
	           out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
	           break;
	         case 14:
	           // 1110 xxxx  10xx xxxx  10xx xxxx
	           char2 = str.charCodeAt(i++);
	           char3 = str.charCodeAt(i++);
	           out += String.fromCharCode(((c & 0x0F) << 12) |
	                          ((char2 & 0x3F) << 6) |
	                          ((char3 & 0x3F) << 0));
	          break;
	       }
	       }
	       return out;
	}
}
//通过正则表达式获得url参数
function GetQueryString(name)
  {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
  }