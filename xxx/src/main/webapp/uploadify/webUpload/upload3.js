/*********************************WebUpload 多文件上传 begin*****************************************/
function initWebUpload(){
var $list = $("#thelist1");
  var fileSize = 0;  //总文件大小
  var fileName = []; //文件名列表
  var fileSizeOneByOne =[];//每个文件大小
  var  uploader ;// 实例化   
  uploader = WebUploader.create({ 
         auto:true, //是否自动上传
         pick: {
              id: '#multi',
              label: '选择文件',
              name:"multiFile"
          },
          swf: 'uploadify/webUpload/Uploader.swf',  
          fileVal:'multiFile',              //和name属性配合使用
          server: "/cms_cloudy/fileUpload/uploadFile.do",  
          duplicate:true, //同一文件是否可重复选择
          resize: false,
          formData: {
              "status":"multi",
              "contentsDto.contentsId":"0000004730",
              "uploadNum":"0000004730",
              "existFlg":'false'
          },  
          /**accept: {// 只允许选择图片文件格式
             title: 'Images',
             extensions: name,
             mimeTypes: 'image/*'
           },**/
          compress: null,//图片不压缩
          chunked: true,  //分片
          chunkSize: 5 * 1024 * 1024,   //每片5M
          chunkRetry:false,//如果失败，则不重试
          threads:1,//上传并发数。允许同时最大上传进程数。
          //fileNumLimit:50,//验证文件总数量, 超出则不允许加入队列
          // runtimeOrder: 'flash',  
          // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。  
          disableGlobalDnd: true
      });  
  
     // 当有文件添加进来的时候
     uploader.on( "fileQueued", function( file ) {
       console.log("fileQueued:");
       var upFileName = file.name
       var index1=upFileName.lastIndexOf(".");
       var index2=upFileName.length;
       var suffix=upFileName.substring(index1+1,index2);//后缀名
       uploadCheck(suffix);
         $list.append( "<div id='"+  file.id + "' class='item'>" +
             "<h4 class='info'>" + file.name + "</h4>" +
             "<p class='state'>等待上传...</p>" +
         "</div>" );
     });
     
     // 当开始上传流程时触发
     uploader.on( "startUpload", function() {
       console.log("startUpload");
       //添加额外的表单参数
       zTree = $.fn.zTree.getZTreeObj("treeDemo");
       var nodes=zTree.getSelectedNodes();
       var path = nodes[0].path;
         $.extend( true, uploader.options.formData, {"fileSize":fileSize,"path":path,"multiFileName":fileName,"fileSizeOneByOne":fileSizeOneByOne}); 
     });
     
     //当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。
     uploader.on("uploadAccept",function(object,ret){
         //服务器响应了
         //ret._raw  类似于 data
      console.log("uploadAccept");
         console.log(ret);
       
    })
     
     uploader.on( "uploadSuccess", function( file ) {
     	  initFileUpload();
     	  layer.alert("上传成功!");
         $( "#"+file.id ).find("p.state").text("已上传");
     });

     //出错之后要把文件从队列中remove调，否则，文件还在队里中，还是会上传到后台去
     uploader.on( "uploadError", function( file,reason  ) {
       $( "#"+file.id ).find("p.state").text("上传出错");
       console.log("uploadError");
       console.log(file);
       console.log(reason);
         //多个文件
         var fileArray = uploader.getFiles();
         for(var i = 0 ;i<fileArray.length;i++){
             //取消文件上传
              uploader.cancelFile(fileArray[i]);
              //从队列中移除掉
              uploader.removeFile(fileArray[i],true);
        }
         //发生错误重置webupload,初始化变量
         uploader.reset();
         fileSize = 0;
         fileName = [];
         fileSizeOneByOne=[];
     });
     
   //当validate不通过时，会以派送错误事件的形式通知调用者
     uploader.on("error",function(){
       console.log("error");
       uploader.reset();
       fileSize = 0;
         fileName = [];
         fileSizeOneByOne=[];
        alert("error");
     })
     
     
     //如果是在模态框里的上传按钮，点击file的时候不会触发控件
     //修复model内部点击不会触发选择文件的BUG
     /*    $("#multi .webuploader-pick").click(function () {
            uploader.reset();
            fileSize = 0;
            fileName = [];
            fileSizeOneByOne=[];
                $("#multi :file").click();
            });*/
        
     /**
     * 多文件上传
     */
    $("#multiUpload").on("click",function(){
    	alert(1);
      uploader.upload();
    })
    
   /**
    *取得每个文件的文件名和文件大小
   */
    //选择文件之后执行上传  
    $(document).on("change","input[name='multiFile']", function() {
          //multiFileName
          var fileArray1 = uploader.getFiles();
          var fileNames = [];
           for(var i = 0 ;i<fileArray1.length;i++){
              fileNames.push(fileArray1[i].name); //input 框用
              //后台用
              fileSize +=fileArray1[i].size;
               fileSizeOneByOne.push(fileArray1[i].size);
               fileName.push(fileArray1[i].name);
            }
           console.log(fileSize);
           console.log(fileSizeOneByOne);
           console.log(fileName);
    })
}
/*********************************WebUpload 多文件上传 end*****************************************/

/************************************webuploader的自带参数提交到后台的参数列表*************************
/************************************webuploader的自带参数提交到后台的参数列表*************************
 * {

//web uploader 的自带参数 
lastModifiedDate=[Wed Apr 27 2016 16:45:01 GMT+0800 (中国标准时间)], 
chunks=[3], chunk=[0], 
type=[audio/wav], uid=[yangl],  id=[WU_FILE_0], 
size=[268620636], name=[3.wav],

//formData的参数
contentsDto.contentsId=[0000004730], existFlg=[false], 
status=[file], uploadNum=[0000004730]
}

可以用这个方法打印webuploader的自带参数列表，基本上就是上面的那一些
 //当某个文件的分块在发送前触发
 uploader.on("uploadBeforeSend",function(object ,data){
   console.log("uploadBeforeSend");
   console.log(object);
   console.log(data);
 })
*********************************************************************************************/
/************************************stream上传初始化开始*************************
/**
 * 配置文件（如果没有默认字样，说明默认值就是注释下的值）
 * 但是，on*（onSelect， onMaxSizeExceed...）等函数的默认行为
 * 是在ID为i_stream_message_container的页面元素中写日志
 */
	var config = {
		browseFileId : "i_select_files", /** 选择文件的ID, 默认: i_select_files */
		browseFileBtn : "<div>请选择文件</div>", /** 显示选择文件的样式, 默认: `<div>请选择文件</div>` */
		dragAndDropArea: "i_select_files", /** 拖拽上传区域，Id（字符类型"i_select_files"）或者DOM对象, 默认: `i_select_files` */
		dragAndDropTips: "<span>把文件(文件夹)拖拽到这里</span>", /** 拖拽提示, 默认: `<span>把文件(文件夹)拖拽到这里</span>` */
		filesQueueId : "i_stream_files_queue", /** 文件上传容器的ID, 默认: i_stream_files_queue */
		filesQueueHeight : 200, /** 文件上传容器的高度（px）, 默认: 450 */
		messagerId : "i_stream_message_container", /** 消息显示容器的ID, 默认: i_stream_message_container */
		multipleFiles: true, /** 多个文件一起上传, 默认: false */
		autoUploading: false, /** 选择文件后是否自动上传, 默认: true */
//		autoRemoveCompleted : true, /** 是否自动删除容器中已上传完毕的文件, 默认: false */
//		maxSize: 104857600//, /** 单个文件的最大大小，默认:2G */
//		retryCount : 5, /** HTML5上传失败的重试次数 */
//		postVarsPerFile : { /** 上传文件时传入的参数，默认: {} */
//			param1: "val1",
//			param2: "val2"
//		},
		swfURL : "/cms_cloudy/pages/staticpage/swf/FlashUploader.swf", /** SWF文件的位置 */
		tokenURL : "/cms_cloudy/tokencontroller/tk", /** 根据文件名、大小等信息获取Token的URI（用于生成断点续传、跨域的令牌） */
		frmUploadURL : "/cms_cloudy/formDatacontroller/fd", /** Flash上传的URI */
		uploadURL : "/cms_cloudy/streamcontroller/upload"/** HTML5上传的URI */
//		simLimit: 200, /** 单次最大上传文件个数 */
//		extFilters: [".txt", ".rpm", ".rmvb", ".gz", ".rar", ".zip", ".avi", ".mkv", ".mp3"], /** 允许的文件扩展名, 默认: [] */
//		onSelect: function(list) {alert('onSelect')}, /** 选择文件后的响应事件 */
//		onMaxSizeExceed: function(size, limited, name) {alert('onMaxSizeExceed')}, /** 文件大小超出的响应事件 */
//		onFileCountExceed: function(selected, limit) {alert('onFileCountExceed')}, /** 文件数量超出的响应事件 */
//		onExtNameMismatch: function(name, filters) {alert('onExtNameMismatch')}, /** 文件的扩展名不匹配的响应事件 */
//		onCancel : function(file) {alert('Canceled:  ' + file.name)}, /** 取消上传文件的响应事件 */
//		onComplete: function(file) {alert('onComplete')}, /** 单个文件上传完毕的响应事件 */
//		onQueueComplete: function() {alert('onQueueComplete')}, /** 所以文件上传完毕的响应事件 */
//		onUploadError: function(status, msg) {alert('onUploadError')} /** 文件上传出错的响应事件 */
	};
	var _t = new Stream(config);
/************************************stream上传初始化结束*************************
 * {

//web uploader 的自带参数 
lastModifiedDate=[Wed Apr 27 2016 16:45:01 GMT+0800 (中国标准时间)], 
chunks=[3], chunk=[0], 
type=[audio/wav], uid=[yangl],  id=[WU_FILE_0], 
size=[268620636], name=[3.wav],

//formData的参数
contentsDto.contentsId=[0000004730], existFlg=[false], 
status=[file], uploadNum=[0000004730]
}

可以用这个方法打印webuploader的自带参数列表，基本上就是上面的那一些
 //当某个文件的分块在发送前触发
 uploader.on("uploadBeforeSend",function(object ,data){
   console.log("uploadBeforeSend");
   console.log(object);
   console.log(data);
 })
*********************************************************************************************/
 
