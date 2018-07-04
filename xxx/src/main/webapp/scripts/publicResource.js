/**
 *  公共资源JS
 */

//选择数据手册弹出框
 function loadDatesheetDiv(){
 	 loadProperties();//国际化
     var html = '';
     html += '<div class="parts-producthand-window-header bg-gray">';
     html += '<a class="left"><span class="glyphicon glyphicon-tasks"></span>';
     html += ''+$.i18n.prop("selectDatesheets")+'</a>';
     html += '<button type="button" class="close parts-producthand-window-close"';
     html += 'aria-label="Close">';
     html += '<span>&times;</span>';
     html += '</button>';
     html += '</div>';
	 html += '<div class="productmix-treeBox1-end">';		
	 html += '<ul id="datasheetTree" class="newztree"></ul>';		
	 html += '</div>';		
	 html += '<div class="producthand-btn left"><a class="btn btn-gray withshangchuan"';		
	 html += 'onclick="saveSelectDatasheet()">'+$.i18n.prop("saveBtn")+'</a>';	
	 html += '<div class="uploadDatesheetDiv">';
     html += '<form id="batchUploadDatesheetFileForm" action="" method="post"';
	 html += 'enctype="multipart/form-data">';		
	 html += '<a class="inputBox-end btn btn-gray right"><input id="dateSheetUpload" name="dateSheetUpload"  onchange="batchUploadFile()"  type="file" class="right"  multiple/>'+$.i18n.prop("uploadBtn")+'</a>';		
	 html += '</form>';		
	 html += '</div>';		
	 html += '</div>';	
	 $(".parts-producthand-window").html(html);
//	<input type="file" id="dateSheetUpload" name="dateSheetUpload" multiple  style="filter:alpha(opacity=0);opacity:0;width: 0;height: 0;"/>  -->
 }
 //批量上传文件DIV
 function batchUploadFilesDiv(){
 	 loadProperties();//国际化
 	 var html = '';
     html += '<form id="batchUploadFilesForm" action="" method="post"';
	 html += 'enctype="multipart/form-data">';		
	 html += '<a class="inputBox-end right"><input id="batchUploadFilesInp" name="batchUploadFilesInp"  onchange="batchUploadFilesFromManage()"  type="file" class="right"  multiple accept="*"/>'+$.i18n.prop("uploadBtn")+'</a>';		
	 html += '<input type="hidden" name="test">';		
	 html += '</form>';	
	 $(".batchUploadFilesDiv").html(html);
 }
 //定时访问服务器(一分钟)
//setInterval('refreshService()', 60000);
function refreshService() {
	$.ajax({
				url : 'sessionListener/sessionCheckFrompage.do',
				dataType : 'json',
				cache : false,
				success : function(json) {
				},
				error : function() {
					layer.alert($.i18n.prop("alertError"), {}, function() {
								location.reload();
							});
				}
			})
}
/**
 * 1.根据后台传递的json循环生成dl、dt、dd标签 
 * 2.进入首页时调用
 * 3.从目录内、目录外、元器件库、搜索框四个连接进入页面，4种情况
 */
function createProcessPartTree() {
	var str = "";
	var setting = {
			view : {
				//removeHoverDom: removeHoverDom,
				showIcon : false,
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
		url : "partClassController/selectAllPartClass.do",
		dataType : "json",
		cache : false,
		type : "post",
		success : function(json) {// 双重循环生成dl、dd二级树形结构
			$.fn.zTree.init($("#partTree"), setting, json);
		}
	});
}
function  addProcessPDDiy(treeId, treeNode) {
	if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
	var aObj = $("#" + treeNode.tId + "_a");
	var spanObj = $("#" + treeNode.tId + "_span");
	var name=treeNode.name;
	aObj.prepend(""+treeNode.childNum+"");
}

//器件导入限制Excel大小
function checkSize(input) {
	var Sys = {};
	var flag;
	var filesize = 0;
	// 判断浏览器种类
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		Sys.ie = true;
	}
	// 获取文件大小
	if (Sys.ie) {
		var fileobject = new ActiveXObject("Scripting.FileSystemObject");// 获取上传文件的对象
		var file = fileobject.GetFile(input.value);// 获取上传的文件
		filesize = file.Size;// 文件大小
	} else {
		filesize = input.files[0].size;
	}
	 var size = filesize / 1024;    
      if(size>500000){  
    	  layer.alert("附件不能大于500M");
          flag =  false
      }else{
      	  flag = true;
      }
	return flag;
}
//上传文件不能为空！
function checkFileEmpty(input) {
	var Sys = {};
	var flag;
	var filesize = 0;
	// 判断浏览器种类
	if (!!window.ActiveXObject || "ActiveXObject" in window) {
		Sys.ie = true;
	}
	// 获取文件大小
	if (Sys.ie) {
		var fileobject = new ActiveXObject("Scripting.FileSystemObject");// 获取上传文件的对象
		var file = fileobject.GetFile(input.value);// 获取上传的文件
		filesize = file.Size;// 文件大小
	} else {
		filesize = input.files[0].size;
	}
	 var size = filesize / 1024;    
      if(size<=0){  
    	  layer.alert("上传的文件不能为空！");
          flag =  false
      }else{
      	  flag = true;
      }
	return flag;
}
/*** 
path 要显示值的对象id 
****/  
function browseFolder(path) {  
try {  
    var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939";  //选择框提示信息  
    var Shell = new ActiveXObject("Shell.Application");  
    var Folder = Shell.BrowseForFolder(0, Message, 64, 17);//起始目录为：我的电脑  
//var Folder = Shell.BrowseForFolder(0,Message,0); //起始目录为：桌面  
    if (Folder != null) {  
        Folder = Folder.items();  // 返回 FolderItems 对象  
        alert(Folder);
        return Folder;  
    }  
}  
catch (e) {  
    alert(e.message);  
}finally{
	folderUpload();
}  
} 