/**页面加载后初始化方法**/
$(function(){
	initTree();
	$("#Pagination").html('');
  initFileUpload();
  $("#uploadTable").hide();
 $('.stream-uploadWindow').draggable();
  //initWebUpload();//文件上传初始化
  //uploadFiles();
});
function initTree(){
	loadProperties();//国际化
   var setting = {
			view: {
				//removeHoverDom: removeHoverDom,
				showIcon: false,
				addDiyDom: addDiyDomInUpload,
				showLine: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
		        onClick: zTreeOnClick
	         }
		};
		var zNodes =[
			{ id:1, pId:0, name:$.i18n.prop("designFile"), imgUrl:"UploadTreeImg/test2.png", open:true,path:""},//designCenter
			{ id:11, pId:1, name:$.i18n.prop("principleLibrary"), imgUrl:"UploadTreeImg/test2.png", open:true,path:""},
			{ id:111, pId:11, name:$.i18n.prop("captureLib"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/SYM/CDSSYM"},
			{ id:112, pId:11, name:$.i18n.prop("conceptLib"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/SYM/ADRSYM"},//capture/concept/
			{ id:12, pId:1, name:$.i18n.prop("packagingLibrary"), imgUrl:"UploadTreeImg/test2.png", open:true,path:""},
			{ id:121, pId:12, name:$.i18n.prop("weldingLibrary"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/Footprint/Pad"},
			{ id:122, pId:12, name:"Flash symbols", imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/Footprint/Flash"},//-----
			{ id:123, pId:12, name:"Package symbols", imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/Footprint/FPT"},
			{ id:13, pId:1, name:$.i18n.prop("stepModel"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/STEPModel"},
			{ id:14, pId:1, name:$.i18n.prop("satModel"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/SATModel"},
			{ id:15, pId:1, name:$.i18n.prop("typicalCircuit"), imgUrl:"UploadTreeImg/test2.png", open:true,path:""},
			{ id:151, pId:15, name:$.i18n.prop("dsnFile"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/TS/DSN"},
			{ id:152, pId:15, name:$.i18n.prop("mddFile"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Design/TS/MDD"},
			{ id:2, pId:0, name:$.i18n.prop("dxdCenterlib"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/LMC"},
			{ id:3, pId:0, name:$.i18n.prop("dataBook"), imgUrl:"UploadTreeImg/test2.png", path:"cms_server/Datasheet"}
		];
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);//加载树结构
}
//数量生成单独标签
function  addDiyDomInUpload(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	var name=treeNode.name;
	if(treeNode.imgUrl==null||treeNode.imgUrl==undefined){
		aObj.prepend("<img src=''/>");
	}else{
		aObj.prepend("<img src='"+treeNode.imgUrl+"'/>");
	}
}
function showIconForTree(treeId, treeNode) {
			return !treeNode.isParent;
};
/**点击某一个节点**/
function zTreeOnClick(event, treeId, treeNode, clickFlag){
     var path=[];
	 path=getChildren(path,treeNode);
	 $("#Pagination").html('');
	 initFileUpload(path);
}
//获得子节点
  function getChildren(ids,treeNode){
  	if("" != treeNode.path){
  	 ids.push(treeNode.path);
  	}
       if (treeNode.isParent){
       for(var obj in treeNode.children){
        getChildren(ids,treeNode.children[obj]);
        }
       }
       return ids;
   }
/**异步加载上传文件数据**/
function initFileUpload(path,pageNo){
	if(typeof(path) == "undefined" ){
    	path = 'undefined';
   }
    if(isNaN(pageNo)){
    	pageNo = 0;
   }
   var addBasics = '1';
  pageNo = parseInt(addBasics)+parseInt(pageNo);
	 $.ajax({
	     url: 'fileUpload/selectFileUploadList.do',
	     data: {'path':JSON.stringify(path),'pageNo':pageNo},
	     dataType: 'json',
	     cache: false,
	     success: function(json){
	         var fileList = json.fileList;
	         var html = '';
	         if(null == fileList || fileList.length == 0){
	            html +='<tr>';
				html +='<td colspan="6" align="center">--没有数据--</td>';
				html +='</tr>';
	         }else{
	            for(var i=0;i<fileList.length;i++){
	            var addBasic = '1';
				var addBefore = i+'';
				var index = parseInt(addBasic)+parseInt(addBefore);
				var count = json.count;
        	    var pageNo = json.pageNo;
	            var fileData = fileList[i];
	            var exchangeSize = null==fileData.size || '' == fileData.size ? '0KB':fileData.size;
	            if(exchangeSize.indexOf("KB") == -1){
	            	exchangeSize = Math.ceil((parseInt(exchangeSize)/1024).toFixed(2)) + "KB";
	            }
	             html += '<tr>';
	             html += '<td>'+'<input type="checkbox" onclick="checkOne('+fileData.id+')" name="checkOne" value="'+fileData.id+'">'+'</td>';
	             html += '<td>'+index+'</td>';
	             html += '<td>'+fileData.name+'</td>';
	             html += '<td>'+exchangeSize+'</td>';
	             html += '<td>'+fileData.uploader+'</td>';
	             html += '<td>'+toDate(fileData.uploadDate)+'</td>';
	             html += '</tr>';
	            }
    			if($("#Pagination").html().length == ''){
    			       $("#Pagination").pagination(
    			    		   count,
    	    	                {
    	    	                    items_per_page : 15,
    	    	                    num_edge_entries : pageNo,
    	    	                    num_display_entries : 8,
    	    	                    callback: function(pageNo, panel){
    	    	                       if(count==null){
    	    	                    	   initFileUpload(path,pageNo);
						                 }
    	    	                   },
    	   	                    link_to:"javascript:void(0);"
    	    	        });
    			}
	         }
	          $("#fileHtml").html(html);
	          fileList=null;
	           count =null;
	    },
	     error: function(){
	         layer.alert("数据加载异常，请联系管理员！");
	     }
	 })
}
function uploadBefore(){
	loadProperties();//国际化
  	 zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var nodes=zTree.getSelectedNodes();
    if(null == nodes || "" == nodes ){
　　layer.alert($.i18n.prop("upload-alert1"));
　　return;
     }
     if("" == nodes[0].path){
     	layer.alert($.i18n.prop("upload-alert2"));
　　return;
     }
     var fileType = new Array();
     uploadCheck(fileType,nodes[0].path);
     if("cms_server/Design/SYM/ADRSYM" == nodes[0].path){//上传文件夹
    	 if (!!window.ActiveXObject || "ActiveXObject" in window) {
    		 layer.alert("IE浏览器暂时不支持上传文件夹！");
    		 return;
//    	      uploadSettingDoc(fileType,nodes[0].path);
//    	      $("#uploadBtn").show();
//    	      $(".stream-uploadWindow").show();
//    		 browseFolder('uploadFolderInp');
    	 }else{
    	      $("#uploadFolderInp").click();
    	 }
     }else{
     	if("cms_server/Datasheet" == nodes[0].path){
     		$("#batchUploadFilesInp").removeAttr("accept");
     	}else{
     	    $("#batchUploadFilesInp").attr("accept",fileType);
     	}
     	$("#batchUploadFilesInp").attr("onchange","batchUploadFilesFromManage('"+nodes[0].path+"')");
        $("#batchUploadFilesInp").click();
     }
}
//上传校验
    function uploadCheck(fileType,path){
    if("cms_server/Design/SYM/CDSSYM" == path){
      fileType.push(".olb");
    }
    if("cms_server/Design/Footprint/Pad" == path){
      fileType.push(".pad");
    }
    if("cms_server/Design/Footprint/Flash" == path){
       fileType.push(".dra");
    }
    if("cms_server/Design/Footprint/FPT" == path){
       fileType.push(".dra");
    }
    if("cms_server/Design/STEPModel" == path){
       fileType.push(".stp");
    }
    if("cms_server/Design/SATModel" == path){
       fileType.push(".sat");
    }
    if("cms_server/Design/TS/DSN" == path){
       fileType.push(".dsn");
    }
       if("cms_server/Design/TS/MDD" == path){
        fileType.push(".mdd");
    }
       if("cms_server/LMC" == path){
        fileType.push(".lmc");
    }
    if("cms_server/Design/SYM/ADRSYM" == path){
         fileType.push(".doc");
    }
    return fileType;
}
   var showshtml ='';
   var row='0';
   var notUpload = '';
   var haved = '';
   var type = '';
function uploadFiles(){
 $("#file_upload").uploadify({  
      'uploader'       : '/cms_cloudy/uploadify/scripts/uploadify.swf',
      'script'             :  '/cms_cloudy/fileUpload/uploadFile.do',
      'method'         : 'POST',
      'cancelImg'      : '/cms_cloudy/images/uploadGif.gif',
      'queueID'        : 'uploadfileQueue', //和存放队列的DIV的id一致 
      'fileDataName'   : 'file_upload', //和以下input的name属性一致 
      'auto'           : true, //是否自动开始 
      'multi'          : true, //是否支持多文件上传 
      'buttonImg'      : '/cms_cloudy/uploadify/scripts/showUplodify.png',
      'buttonText'     : '上传', //按钮上的文字 
      'wmode'          : 'transparent',
      //将要上传的文件对象的名称 必须与后台controller中抓取的文件名保持一致      
        'fileObjName':'pic', 
        'removeCompleted' : false,
      'simUploadLimit' : 5, //一次同步上传的文件数目 
      'sizeLimit'      : 20971520*5, //设置单个文件大小限制100M 
      'queueSizeLimit' : 50, //队列中同时存在的文件个数限制 
      //'fileDesc'       : '支持格式:jpg/gif/jpeg/png/bmp/rar/zip/doc', //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
      //'fileExt'        : '*.jpg;*.gif;*.jpeg;*.png;*.bmp;*.rar;*.zip;*.doc',//允许的格式  
      'onSelectOnce'   : function(event,data) {
                 document.getElementById("sc").style.display="block";
                 document.getElementById("qx").style.display="block";
   },//全部上传完成事件
   'onAllComplete'  : function(event,data) {
              layer.alert("上传成功！");
              var operation='';
    	      if(notUpload != ''){
    	      	notUpload=(notUpload.substring(notUpload.length-1)==',')?notUpload.substring(0,notUpload.length-1):notUpload;
    	         layer.alert("该节点下只能上传"+type+"类型的文件，"+notUpload+"上传失败！");
    	      }
               if('' != haved){//存在相同名称文件操作
               	   haved=(haved.substring(haved.length-1)==',')?haved.substring(0,haved.length-1):haved;
                   layer.confirm(haved+'上传失败，'+'同一节点下不能相同文件名文件！', {
                   btn: ['保存','覆盖','取消'] //按钮
                   }, function(){
                  operation="保存";
                  layer.alert("操作成功！");
                   }, function(){
                  operation="覆盖";
                    $.ajax({
                        url: 'fileUpload/changFile.do',
                        data:{
                             'Name':haved,'path':type,'type':'chang'
                        },
                        dataType: 'json',
                        cache: false,
                        success: function(){
                            layer.alert("操作成功！");
                        },
                        error: function(){
                        layer.alert("覆盖操作失败！");
                        }
                      })
                   },  function(){
                   	 if('' == operation){
                      $.ajax({
                        url: 'fileUpload/changFile.do',
                        data:{
                             'Name':haved,'path':type,'type':'delete'
                        },
                        dataType: 'json',
                        cache: false,
                        success: function(){
                            layer.alert("操作成功！");
                        },
                        error: function(){
                        layer.alert("取消操作失败！");
                        }
                      })
                   }
                   });
                   operation = '';
               }else{
                 layer.alert("上传成功！");
               }
	  showshtml ='';
      row='0';
      haved='';//文件名称不符合
      notUpload = '';//文件格式不符合
      type = '';
      $("#Pagination").html('');
      initFileUpload();
   },
    //选中事件
   'onSelect'   : function(event, queueID, fileObj) {
   	           zTree = $.fn.zTree.getZTreeObj("treeDemo");
               var nodes=zTree.getSelectedNodes();
   	            if("cms_server/Design/SYM/CDSSYM"==nodes[0].path){
   	             if(".olb" == fileObj.type){
   	             	type = nodes[0].path;
   	             	alert(type);
   	             jQuery("#file_upload").uploadifySettings('scriptData',{'path':type});  
   	             //判断该文件是否已经存在start
   	             	 $.ajax({
                        url: 'fileUpload/checkFileIsExis.do',
                        data: 'fileName='+fileObj.name,
                        dataType: 'json',
                        cache: false,
                        success: function(json){
                          if(json.result == "yes"){
                             haved=haved+fileObj.name+",";
                          }
                        },
                        error: function(){
                        layer.alert("取消操作失败！");
                        }
                      })
   	             //判断该文件是否已经存在END
   	             showshtml += '<tr class="changeShow">';
	             showshtml += '<td style="text-align:center;vertical-align:middle;">'+fileObj.name+'</td>';
	             showshtml += '<td style="text-align:center;vertical-align:middle;">'+fileObj.size+'</td>';
	             showshtml += '<td align="center" valign="middle">'+'<img src="/cms_cloudy/images/uploadGif.gif" style="width:200px; height:50px;"/>'+'</div>'+'</td>';
	             showshtml += '</tr>';
	             $("#uploadHtml").html(showshtml);
	             var addBefore = 1+'';
				  row = parseInt(row)+parseInt(addBefore);
	              //alert(document.getElementById("uploadHtml").rows.length);
				  alert(row);
				  if(row >= 5){
				     $("#mainTable").hide();
	                 $("#uploadTable").show();
	                 sleep(4000);
	                 $("#mainTable").show();
	                 $("#uploadTable").hide();
				  }
   	            }else{
   	            	 notUpload=notUpload+fileObj.name+",";
   	            	  $('#file_upload').uploadifyCancel(queueID);//清除这个队列队列
   	                 //$('#file_upload').uploadifyClearQueue();  //清除所有队列
   	            }
   	         }
   },
   onComplete: function (event, queueID, fileObj, response, data) { 
	jQuery('<li></li>').appendTo('.files').html(response); 
    }, 
     onError: function(event, queueID, fileObj) { 
         		alert("文件:" + fileObj.name + "上传失败"); 
   			}
   	   }); 
}
function uploadifyUpload(){ 
       var sizeLimit=5;  
	   jQuery("#fileupload").uploadifySettings('scriptData',{'method':'fileAdd','sizeLimit':sizeLimit});  
	   jQuery('#fileupload').uploadifyUpload(); 
	} 
function uploadFile(data){
$.ajaxFileUpload
	        (
	            {
	                url:'/cms_cloudy/fileUpload/uploadFile.do',
	                secureuri:false,//一般设置为false
	                fileElementId:'uploadFileId',//文件上传空间的id属性  <input type="file" id="file" name="file" />
	                dataType: 'json',//返回值类型 一般设置为json
	                success: function (data, status)  //服务器成功响应处理函数
	                {
	                    //alert(data.result);//从服务器返回的json中取出message中的数据,其中message为在struts2中action中定义的成员变量
	                  if(data.message == "success"){
	                     layer.alert("上传成功！");	                	
	                  }else{
	                  	 layer.alert("上传失败！");	                	
	                  }
	                  $("#Pagination").html('');
	                   initFileUpload();
	                },
	                error: function (data, status, e)//服务器响应失败处理函数
	                {
	                	layer.alert("数据连接异常,请联系管理员!");
	                }
	            }
	        )
}
/**复选框全选反选操作**/
function checkAll(){
var CheckBox=document.getElementsByName('checkOne');
if($("#checkAll").prop('checked')){
  for(var i=0;i<CheckBox.length;i++){
  	if(CheckBox[i].checked==false){
  	    CheckBox[i].checked=true;
  	}
   }
}else{
   for(var i=0;i<CheckBox.length;i++){
   	if(CheckBox[i].checked=true){
   	    CheckBox[i].checked=false;
   	}
   }
}
}
/**复选框单个选择**/
function checkOne(id){
	var CheckBox=document.getElementsByName('checkOne');
    var num=0;
    if(CheckBox.length>0){
     for(var i=0;i<CheckBox.length;i++){
      if(CheckBox[i].checked){
      	num++;
      }
    }
    }
    if( num == CheckBox.length){
    $("#checkAll").prop('checked',true); 
    }else{
    $("#checkAll").prop('checked',false); 
    }
}
/**批量删除上传的文件**/
function deleteUploadFile(){
 	var ids=new Array;
	loadProperties();//国际化
	$("input[name='checkOne']:checkbox:checked").each(function(){
		ids.push($(this).val());
	});
	if(ids.length==0){
		layer.alert($.i18n.prop("check-edit1"));
		return;
	}
	//询问框
		 layer.confirm($.i18n.prop("check-delete2"), {
		 btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]// 按钮
			}, function(){
				$.ajax({
	            url: 'fileUpload/deleteFileUpload.do',
	           data: 'ids='+JSON.stringify(ids),
	           dataType: 'json',
	            cache: false,
	            success: function(json){
	               layer.alert($.i18n.prop("alertMsg3"));
	               zTree = $.fn.zTree.getZTreeObj("treeDemo");
	               var nodes=zTree.getSelectedNodes();
	               if(null == nodes[0]){
	            	   $("#Pagination").html('');
	            	   initFileUpload();  
	               }else{
	   	            zTreeOnClick(null,null,nodes[0],null);
	               }
	               if($("#checkAll").prop('checked')){
	               $("#checkAll").prop('checked',false); 
	             }
	},
	error: function(){
	layer.alert("服务器连接异常，请联系管理员！");
	}
	})
			});
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
    	var str = y+"-"+m+"-"+d;
    	return str;
    	}
function readFiles(){
    var files = document.getElementById("files").files;
    for (var file = 0 ; file < files.length; file++) {
        /**var reader = new FileReader();
        reader.onload = function(e){
            var object = new Object();
            object.content = e.target.content;
            var json_upload = "jsonObject=" + JSON.stringify(object);
            var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
            xmlhttp.open("POST", "http://localhost:8080/cms_cloudy/fileUpload/uploadFile.do");
            xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xmlhttp.send(json_upload);
        }
        reader.readAsBinaryString(files);*/
    	//alert(files[file]);
    	upload(files[file]);
    }
}
 function upload(file) {
                var fd = new FormData();
                fd.append("image", file);
                fd.append("key", "6528448c258cff474ca9701c5bab6927");
                var xhr = new XMLHttpRequest();
                xhr.open("POST", "http://localhost:8080/cms_cloudy/fileUpload/uploadFile.do");
                xhr.onload = function() {
                      
                }
                xhr.send(fd);
       }
       var _t;
function uploadSettingDoc(fileType,path){
       	loadProperties();//国际化
       	if(fileType == ".doc"){
       		var config = {
		browseFileId : "i_select_files", /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div id= 'docDiv' class=‘selectDiv’>"+$.i18n.prop("streamFile1")+"</div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: "i_select_files", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>"+$.i18n.prop("streamFile2")+"</span>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
		filesQueueId : "i_stream_files_queue", /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : "i_stream_message_container", /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles: true, /** 多个文件一起上传, 默认: false */
		autoUploading: false, /** 选择文件后是否自动上传, 默认: true */
//		autoRemoveCompleted : true, /** 是否自动删除容器中已上传完毕的文件, 默认: false */
//		maxSize: 104857600//, /** 单个文件的最大大小，默认:2G */
//		retryCount : 5, /** HTML5上传失败的重试次数 */
		postVarsPerFile : { /** 上传文件时传入的参数，默认: {} */
			param1: path
		},
		swfURL : "/cms_cloudy/pages/staticpage/swf/FlashUploader.swf", /** SWF文件的位置 */
		tokenURL : "/cms_cloudy/tokencontroller/tk", /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
		frmUploadURL : "/cms_cloudy/formDatacontroller/fd", /** Flash上传的URI */
		uploadURL : "/cms_cloudy/streamcontroller/upload",/** HTML5上传的URI */
//		simLimit: 200, /** 单次最大上传文件个数 */
//		extFilters: [".txt", ".rpm", ".rmvb", ".gz", ".rar", ".zip", ".avi", ".mkv", ".mp3"], /** 允许的文件扩展名, 默认: [] */
		//extFilters: fileType, /** 允许的文件扩展名, 默认: [] */
		//onSelect: function(list) {/** 选择文件后的响应事件 */
		   //layer.alert("该节点下。。。。");
		//} 
//		onMaxSizeExceed: function(size, limited, name) {alert('onMaxSizeExceed')}, /** 文件大小超出的响应事件 */
//		onFileCountExceed: function(selected, limit) {alert('onFileCountExceed')}, /** 文件数量超出的响应事件 */
//		onExtNameMismatch: function(name, filters) {alert('onExtNameMismatch')}, /** 文件的扩展名不匹配的响应事件 */
//		onCancel : function(file) {alert('Canceled:  ' + file.name)}, /** 取消上传文件的响应事件 */
//		onComplete: function(file) {alert('onComplete')}, /** 单个文件上传完毕的响应事件 */
		onQueueComplete: function() {
		 //询问框
			nameAlikeDoc();
		} /** 所以文件上传完毕的响应事件 */
//		onUploadError: function(status, msg) {alert('onUploadError')} /** 文件上传出错的响应事件 */
	};
	 _t = new Stream(config);
	$('#docDiv').html('');
       	}else{
       	var config = {
		browseFileId : "i_select_files", /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div class=‘selectDiv’>"+$.i18n.prop("streamFile1")+"</div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: "i_select_files", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>"+$.i18n.prop("streamFile2")+"</span>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
		filesQueueId : "i_stream_files_queue", /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : "i_stream_message_container", /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles: true, /** 多个文件一起上传, 默认: false */
		autoUploading: false, /** 选择文件后是否自动上传, 默认: true */
//		autoRemoveCompleted : true, /** 是否自动删除容器中已上传完毕的文件, 默认: false */
//		maxSize: 104857600//, /** 单个文件的最大大小，默认:2G */
//		retryCount : 5, /** HTML5上传失败的重试次数 */
		postVarsPerFile : { /** 上传文件时传入的参数，默认: {} */
			param1: path
		},
		swfURL : "/cms_cloudy/pages/staticpage/swf/FlashUploader.swf", /** SWF文件的位置 */
		tokenURL : "/cms_cloudy/tokencontroller/tk", /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
		frmUploadURL : "/cms_cloudy/formDatacontroller/fd", /** Flash上传的URI */
		uploadURL : "/cms_cloudy/streamcontroller/upload",/** HTML5上传的URI */
//		simLimit: 200, /** 单次最大上传文件个数 */
//		extFilters: [".txt", ".rpm", ".rmvb", ".gz", ".rar", ".zip", ".avi", ".mkv", ".mp3"], /** 允许的文件扩展名, 默认: [] */
		extFilters: fileType,/** 允许的文件扩展名, 默认: [] */
		//onSelect: function(list) {/** 选择文件后的响应事件 */
			//if(path == "cms_server/Design/TS/DSN"){
			 // for(var x=0;x<list.length;x++){
			 //   alert(list[x].name);
			//  }
		//	}
	//	},
//		onMaxSizeExceed: function(size, limited, name) {alert('onMaxSizeExceed')}, /** 文件大小超出的响应事件 */
//		onFileCountExceed: function(selected, limit) {alert('onFileCountExceed')}, /** 文件数量超出的响应事件 */
//		onExtNameMismatch: function(name, filters) {alert('onExtNameMismatch')}, /** 文件的扩展名不匹配的响应事件 */
//		onCancel : function(file) {alert('Canceled:  ' + file.name)}, /** 取消上传文件的响应事件 */
//		onComplete: function(file) {alert('onComplete')}, /** 单个文件上传完毕的响应事件 */
		onQueueComplete: function() {/** 所以文件上传完毕的响应事件 */
			nameAlikeDoc();
		} 
//		onUploadError: function(status, msg) {alert('onUploadError')} /** 文件上传出错的响应事件 */
	};
	 _t = new Stream(config);
       	}
 }
 //是否有相同文件名
function nameAlikeDoc(){
	loadProperties();//国际化
	$.ajax({
	url: 'fileUpload/checkNameAlike.do',
	dataType: 'json',
	cache: false,
	success: function(data){
        if(null != data && "" != data){
	    var json = {
     	title:$.i18n.prop("tipTxt")+"：",
			msg:$.i18n.prop("upload-alert3"),
			buttons:[
			{ title:$.i18n.prop("saveBtn"),color:"purple",click:function(){
			       $.ajax({
                        url: 'fileUpload/saveAlikeFile.do',
                        data: 'dataList='+JSON.stringify(data),
                        dataType: 'json',
                        cache: false,
                        success: function(datas){
                        	var jsons = {
						    title:$.i18n.prop("tipTxt")+"：",
						    msg:$.i18n.prop("alertMsg2"),
						    buttons:[
							{ title:$.i18n.prop("determineBtn"),color:"red",click:function(){} }
						    ]
					        }
                        	 zTree = $.fn.zTree.getZTreeObj("treeDemo");
                            var nodes=zTree.getSelectedNodes();
                            zTreeOnClick(null,null,nodes[0],null);
                           $.alertView(jsons);
                        },
                        error: function(){
                        layer.alert("保存操作失败！");
                        }
                      })
			} },
			{ title:$.i18n.prop("coverTxt"),color:"green",click:function(){
			        $.ajax({
                        url: 'fileUpload/coverAlikeFile.do',
                        data: 'dataList='+JSON.stringify(data),
                        dataType: 'json',
                        cache: false,
                        success: function(datas){
                            var jsons = {
						    title:$.i18n.prop("tipTxt")+"：",
						    msg:$.i18n.prop("operationSus"),
						    buttons:[
							{ title:$.i18n.prop("determineBtn"),color:"red",click:function(){} }
						    ]
					        }
                            zTree = $.fn.zTree.getZTreeObj("treeDemo");
                            var nodes=zTree.getSelectedNodes();
                            zTreeOnClick(null,null,nodes[0],null);
                           $.alertView(jsons);
                        },
                        error: function(){
                        layer.alert("覆盖操作失败！");
                        }
                      })
			} },
			{ title:$.i18n.prop("cancelBtn"),color:"red",click:function(){
			          $.ajax({
                        url: 'fileUpload/deleteAlikeFile.do',
                        data: 'dataList='+JSON.stringify(data),
                        dataType: 'json',
                        cache: false,
                        success: function(datas){
                             var jsons = {
						    title:$.i18n.prop("tipTxt")+"：",
						    msg:$.i18n.prop("operationSus"),
						    buttons:[
							{ title:$.i18n.prop("determineBtn"),color:"red",click:function(){} }
						    ]
					        }
                           $.alertView(jsons);
                        },
                        error: function(){
                        layer.alert("覆盖操作失败！");
                        }
                      })
			} }
			]
	       }
          $.alertView(json);
        }else{
          var xx = {
				title:$.i18n.prop("tipTxt")+"：",
				msg:$.i18n.prop("alertMsg4"),
				 buttons:[
				{ title:$.i18n.prop("determineBtn"),color:"red",click:function(){} }
				 ]
				 }
            $.alertView(xx);
        }
         _t.destroy();
        _t=null;
      $("#uploadBtn").hide();
      $(".stream-uploadWindow").hide();
      $("#i_stream_message_container").html('');
      zTree = $.fn.zTree.getZTreeObj("treeDemo");
      var nodes=zTree.getSelectedNodes();
      zTreeOnClick(null,null,nodes[0],null);
	},
	error: function(){
	layer.alert("数据加载异常，请联系管理员！");
	}
	})
}
 //关闭stream上传按钮
   $("body").on("click", ".stream-uploadWindow-close", function () {
        _t.destroy();
        _t=null;
      $("#uploadBtn").hide();
      $(".stream-uploadWindow").hide();
      $("#i_stream_message_container").html('');
}); 