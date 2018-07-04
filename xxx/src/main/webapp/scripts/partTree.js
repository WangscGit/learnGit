
//// 自动填充数据
//function parentNumAuto() {
//	var zTree = $.fn.zTree.getZTreeObj("partTree");
//	var nodes = zTree.getSelectedNodes();
//	var treeNode = nodes[0];
//	$.ajax({
//				url : "partClassController/getPnCode.do",
//				dataType : "json",
//				cache : false,
//				type : "post",
//				data : {
//					"treeNode" : JSON.stringify(treeNode)
//				},
//				success : function(json) {
//					$("#parentNum").autocomplete(json.pnCodeList, {
//								matchContains : true,
//								minChars : 0,
//								max : 20,
//								autoFill : true
//							})
//				},
//				error : function() {
//					//			layer.alert("数据连接异常,注册失败！");
//				}
//			});
//
//}

//获取树形结构
function getNodes() {
	var setting = {
		view : {
				// removeHoverDom: removeHoverDom,
				showIcon : false,
				showLine: false,
				addDiyDom: addChildNum
			},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : nodeOnClick
		}
	};

	$.ajax({
		url : "partClassController/selectAllPartClass.do",
		dataType : "json",
		cache : false,
		success : function(json) {
			$.fn.zTree.init($("#partTree"), setting, json);
			var treeObj = $.fn.zTree.getZTreeObj("partTree");
			treeObj.expandAll(true); // 默认展开全部
		},
		error : function() {
			//layer.alert("数据连接异常,注册失败！");
		}
	});
}
function addChildNum(treeId, treeNode){
	if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
	var aObj = $("#" + treeNode.tId + "_a");
	var spanObj = $("#" + treeNode.tId + "_span");
	var name=treeNode.name;
	aObj.prepend(""+treeNode.childNum+"");
}
// 选中节点，记录节点信息
function nodeOnClick(event, treeId, treeNode, clickFlag) {
	if(treeNode.children==undefined){
		$("#specialProperties").show();
	}else{
		$("#specialProperties").hide();
	}
	$("#partType").val(treeNode.name);
	$("#childNum").val(treeNode.childNum);
	$("#parentNum").val(treeNode.pId);
	$("#enName").val(treeNode.enName);
	$("#partType").prop("readonly",true);
	$("#childNum").prop("readonly",true);
	$("#parentNum").prop("readonly",true);
	$("#enName").prop("readonly",true);
	//图片信息
	$("#typeImg").val("");
	if(treeNode.imgUrl==null||treeNode.imgUrl==undefined){
		$("#img1").attr("src","");
	}else{
		$("#img1").attr("src",treeNode.imgUrl);
	}
	// 特殊属性
	$("#specialPropertie1").val(treeNode.specialPropertie1);
	$("#specialPropertie2").val(treeNode.specialPropertie2);
	$("#specialPropertie3").val(treeNode.specialPropertie3);
	$("#specialPropertie4").val(treeNode.specialPropertie4);
	$("#specialPropertie5").val(treeNode.specialPropertie5);
	$("#specialPropertie6").val(treeNode.specialPropertie6);
	$("#specialPropertie7").val(treeNode.specialPropertie7);
	$("#specialPropertie8").val(treeNode.specialPropertie8);
	$("#specialPropertie9").val(treeNode.specialPropertie9);
	$("#specialPropertie10").val(treeNode.specialPropertie10);
	$("#specialPropertie11").val(treeNode.specialPropertie11);
	$("#specialPropertie12").val(treeNode.specialPropertie12);
	$("#specialPropertie1").prop("readonly",true);
	$("#specialPropertie2").prop("readonly",true);
	$("#specialPropertie3").prop("readonly",true);
	$("#specialPropertie4").prop("readonly",true);
	$("#specialPropertie5").prop("readonly",true);
	$("#specialPropertie6").prop("readonly",true);
	$("#specialPropertie7").prop("readonly",true);
	$("#specialPropertie8").prop("readonly",true);
	$("#specialPropertie9").prop("readonly",true);
	$("#specialPropertie10").prop("readonly",true);
	$("#specialPropertie11").prop("readonly",true);
	$("#specialPropertie12").prop("readonly",true);
	lock=0;
	
}
//清空表单
function emptyInput(){
	$("#parentNum").unautocomplete();
	$("#partType").val("");
	$("#childNum").val("");
	$("#parentNum").val("");
	$("#enName").val("");
	// 特殊属性
	$("#specialPropertie1").val("");
	$("#specialPropertie2").val("");
	$("#specialPropertie3").val("");
	$("#specialPropertie4").val("");
	$("#specialPropertie5").val("");
	$("#specialPropertie6").val("");
	$("#specialPropertie7").val("");
	$("#specialPropertie8").val("");
	$("#specialPropertie9").val("");
	$("#specialPropertie10").val("");
	$("#specialPropertie11").val("");
	$("#specialPropertie12").val("");
	$("#typeImg").val("");
	$("#img1").attr("src","");
}
var lock=0;//添加、修改的保存是一个按钮，根据lock判断添加、修改、无操作。0-无操作，1-添加，2-修改
//点击添加按钮
function clickAddBtn(){
	loadProperties();
	var zTree = $.fn.zTree.getZTreeObj("partTree"), nodes = zTree
			.getSelectedNodes();
	if (nodes.length == 0) {
		layer.alert($.i18n.prop("qxzygjd"));
		return;
	}
	lock=1;
	$("#partType").val("");
	$("#childNum").val("");
	$("#enName").val("");
	$("#partType").prop("readonly",false);
	$("#childNum").prop("readonly",false);
	$("#parentNum").prop("readonly",false);
	$("#enName").prop("readonly",false);
	$("#specialPropertie1").prop("readonly",false);
	$("#specialPropertie2").prop("readonly",false);
	$("#specialPropertie3").prop("readonly",false);
	$("#specialPropertie4").prop("readonly",false);
	$("#specialPropertie5").prop("readonly",false);
	$("#specialPropertie6").prop("readonly",false);
	$("#specialPropertie7").prop("readonly",false);
	$("#specialPropertie8").prop("readonly",false);
	$("#specialPropertie9").prop("readonly",false);
	$("#specialPropertie10").prop("readonly",false);
	$("#specialPropertie11").prop("readonly",false);
	$("#specialPropertie12").prop("readonly",false);
//	parentNumAuto();
}
//点击修改按钮
function clickUpdateBtn(){
	loadProperties();
	var zTree = $.fn.zTree.getZTreeObj("partTree"), nodes = zTree
			.getSelectedNodes();
	if (nodes.length == 0) {
		layer.alert($.i18n.prop("qxzygjd"));
		return;
	}
	lock=2;
	$("#partType").val(nodes[0].name);
	$("#childNum").val(nodes[0].childNum);
	$("#enName").val(nodes[0].enName);
	$("#partType").prop("readonly",false);
	$("#childNum").prop("readonly",false);
	$("#parentNum").prop("readonly",false);
	$("#enName").prop("readonly",false);
	$("#specialPropertie1").prop("readonly",false);
	$("#specialPropertie2").prop("readonly",false);
	$("#specialPropertie3").prop("readonly",false);
	$("#specialPropertie4").prop("readonly",false);
	$("#specialPropertie5").prop("readonly",false);
	$("#specialPropertie6").prop("readonly",false);
	$("#specialPropertie7").prop("readonly",false);
	$("#specialPropertie8").prop("readonly",false);
	$("#specialPropertie9").prop("readonly",false);
	$("#specialPropertie10").prop("readonly",false);
	$("#specialPropertie11").prop("readonly",false);
	$("#specialPropertie12").prop("readonly",false);
//	parentNumAuto();
}

//限制上传的图片格式及尺寸
function limitImg() {
	var inputId=arguments[0];
	var imgId=arguments[1];
	var file = document.getElementById(arguments[0]);
	var pic = document.getElementById(arguments[1]);
	var maxSize = arguments[2];//图片大小
	var maxWidth = arguments[3] || 0;//图片宽度
	var maxHeight = arguments[4] || 0;//图片高度
	var postfix = getPostfix(file.value);
	var str = ".jpg,.png,.gif";
    var imgWigth=0;
    var imgHeight=0;
    if (str.indexOf(postfix.toLowerCase()) == -1) {
		layer.alert("图片格式不对，只能上传jpg、gif、png图像");
		$("#"+inputId).val("");
		$("#"+imgId).attr("src","");
		return;
	} /*else if (file.size > maxSize * 1024) {
		layer.alert("图片大小超过限制,请限制在" + maxSize + "K以内");
		return;
	} else if (file.size == -1){
		layer.alert("图片格式错误，可能是已经损坏或者更改扩展名导致，请重新选择一张图片");
		return;
	}*/
	
    if(window.FileReader){//chrome,firefox7+,opera,IE10+
    	oFReader = new FileReader();
    	if(file.files[0]==undefined){
    		return;
    	}
    	oFReader.readAsDataURL(file.files[0]);
    	oFReader.onload = function (oFREvent) {
    		var image = new Image();
    		image.onload=function(){
    			imgHeight=image.height;
    			imgWigth=image.width;
//    			pic.src = oFREvent.target.result;
				if (imgWigth > maxWidth) {
					layer.alert( "图片宽度超过限制，请保持在" + maxWidth + "像素内");
					$("#"+inputId).val("");
					$("#"+imgId).attr("src","");
					return;
				} else if (imgHeight > maxHeight) {
					layer.alert("图片高度超过限制，请保持在" + maxHeight + "像素内");
					$("#"+inputId).val("");
					$("#"+imgId).attr("src","");
					return;
				}else{
					pic.src = oFREvent.target.result;
				}
    		};
    		image.src=oFREvent.target.result;
    	};        
     }
	/*else if (document.all) {//IE9-//IE使用滤镜，实际测试IE6设置src为物理路径发布网站通过http协议访问时还是没有办法加载图片
		file.select();
		file.blur();//要添加这句，要不会报拒绝访问错误（IE9或者用ie9+默认ie8-都会报错，实际的IE8-不会报错）
		var reallocalpath = document.selection.createRange().text//IE下获取实际的本地文件路径
		//if (window.ie6) pic.src = reallocalpath; //IE6浏览器设置img的src为本地路径可以直接显示图片
		//else { //非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现，IE10浏览器不支持滤镜，需要用FileReader来实现，所以注意判断FileReader先
		pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
		pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';//设置img的src为base64编码的透明图片，要不会显示红xx
           // }
        }
	else if (file.files) {//firefox6-
		if (file.files.item(0)) {
        	url = file.files.item(0).getAsDataURL();
         	pic.src = url;
    	}
	}*/
}
//根据路径获取文件扩展名 
function getPostfix(path){ 
	return path.substring(path.lastIndexOf("."),path.length); 
}
// 点击保存，ajax请求后台方法
function addOrUpdateNode() {
	loadProperties();
	if(lock==0){
		layer.alert($.i18n.prop("qxdjtjhxg"));
		return false;
	}
	var node = new Object();
	var zTree = $.fn.zTree.getZTreeObj("partTree"); 
	var nodes = zTree.getSelectedNodes(); 
	var treeNode = nodes[0];
	var isCheckCNum=0;
	if($("#childNum").val()==treeNode.childNum&&lock==2&&$("#parentNum").val()==treeNode.pId){//修改时仅仅修改类型不验证第二级是否重复
		isCheckCNum=1;
	}
	if($("#parentNum").val()==""&&treeNode.pId==null&&$("#childNum").val()==treeNode.childNum&&lock==2){
		isCheckCNum=1;
	}
	if($("#childNum").val()==""){
		layer.alert($.i18n.prop("dejbnwk"));
		return;
	}
	var re =/^[0-9]+.?[0-9]*$/;
	if (!re.test($("#childNum").val())) { 
	　　　layer.alert($.i18n.prop("dejqsrsz")); 
		return ;
　　  }
	if($("#parentNum").val()!=""){
		if (!re.test($("#parentNum").val())) {
			layer.alert($.i18n.prop("dyjqsrsz")); 
			return ;
　　  		}
	}
	if($("#partType").val()==""){
		layer.alert($.i18n.prop("lxbnwk"));
		return;
	}
	if($("#enName").val()==""){
		layer.alert("英文名不能为空");
		return;
	}
	if(treeNode!=undefined&&treeNode.children!=undefined&&treeNode.pId != $("#parentNum").val()){// 改变树形结构
		if(lock==1){
			node.id=0;
		}else if(lock==2){
			node.id= treeNode.oid;
		}
		// 保存的节点信息****************************************************
		node.parentNum = $("#parentNum").val();
		node.partType = $("#partType").val();
		node.childNum = $("#childNum").val();
		node.enName=$("#enName").val();
		if(treeNode.imgId!=undefined){
			node.imgId=treeNode.imgId;
		}
		node.specialPropertie1 = $("#specialPropertie1").val();
		node.specialPropertie2 = $("#specialPropertie2").val();
		node.specialPropertie3 = $("#specialPropertie3").val();
		node.specialPropertie4 = $("#specialPropertie4").val();
		node.specialPropertie5 = $("#specialPropertie5").val();
		node.specialPropertie6 = $("#specialPropertie6").val();
		node.specialPropertie7 = $("#specialPropertie7").val();
		node.specialPropertie8 = $("#specialPropertie8").val();
		node.specialPropertie9 = $("#specialPropertie9").val();
		node.specialPropertie10 = $("#specialPropertie10").val();
		node.specialPropertie11 = $("#specialPropertie11").val();
		node.specialPropertie12 = $("#specialPropertie12").val();
		$.ajaxFileUpload({
			url : "partClassController/insertOrUpdatePartClass.do",
			dataType : "json",
			secureuri:false,
			type : "post",
			fileElementId:"typeImg",
			data : {
				"json" : JSON.stringify(node),
				"nodes":JSON.stringify(nodes),
				"isCheckCNum":isCheckCNum,
				"lock":lock
			},
			success : function(json,status) {
				if(json.isSuc==0){
					layer.alert(json.message);
					return;
				}else{
					layer.alert(json.message);
					lock=0;
					emptyInput();
					getNodes();
				}
			},
			error : function() {
			}
		});
	}else {// 不该变树形结构
		if(lock==1){
			node.id=0;
		}else if(lock==2){
			node.id= treeNode.oid;
		}
		// 保存的节点信息****************************************************
		node.parentNum = $("#parentNum").val();
		node.partType = $("#partType").val();
		node.childNum = $("#childNum").val();
		node.enName=$("#enName").val();
		if(treeNode.imgId!=undefined){
			node.imgId=treeNode.imgId;
		}
		node.specialPropertie1 = $("#specialPropertie1").val();
		node.specialPropertie2 = $("#specialPropertie2").val();
		node.specialPropertie3 = $("#specialPropertie3").val();
		node.specialPropertie4 = $("#specialPropertie4").val();
		node.specialPropertie5 = $("#specialPropertie5").val();
		node.specialPropertie6 = $("#specialPropertie6").val();
		node.specialPropertie7 = $("#specialPropertie7").val();
		node.specialPropertie8 = $("#specialPropertie8").val();
		node.specialPropertie9 = $("#specialPropertie9").val();
		node.specialPropertie10 = $("#specialPropertie10").val();
		node.specialPropertie11 = $("#specialPropertie11").val();
		node.specialPropertie12 = $("#specialPropertie12").val();
		$.ajaxFileUpload({
			url : "partClassController/insertOrUpdatePartClass.do",
			dataType : "json",
			secureuri:false,
			type : "post",
			fileElementId:"typeImg",
			data : {
				"json" : JSON.stringify(node),
				"isCheckCNum":isCheckCNum,
				"lock":lock
			},
			success : function(json,status) {
				if(json.isSuc==0){
					layer.alert(json.message);
					return;
				}else{
					layer.alert(json.message);
					lock=0;
					emptyInput();
					getNodes();
				}
			},
			error : function() {
				layer.alert("数据连接异常！");
			}
		});
	} 
	
}
function resetData() {
	window.location.reload();
}
// 删除节点信息
function deleteNode() {
	loadProperties();
	var zTree = $.fn.zTree.getZTreeObj("partTree"), nodes = zTree
			.getSelectedNodes();
	if (nodes.length == 0) {
		layer.alert($.i18n.prop("qxzygjd"));
		return;
	}
	layer.confirm($.i18n.prop("check-delete2"), {
		btn : [$.i18n.prop("determineBtn"), $.i18n.prop("resetBtn")]
			// 按钮
		}, function() {
		$.ajax({
			url : "partClassController/deletePartClass.do",
			dataType : "json",
			cache : false,
			type : "post",
			data : {
				"nodes" : JSON.stringify(nodes)
			},
			success : function(json) {
				layer.alert($.i18n.prop("alertMsg3"));
				getNodes();
				$("#partType").val("");
				$("#childNum").val("");
				$("#parentNum").val("");
				$("#specialPropertie1").val("");
				$("#specialPropertie2").val("");
				$("#specialPropertie3").val("");
				$("#specialPropertie4").val("");
				$("#specialPropertie5").val("");
				$("#specialPropertie6").val("");
				$("#specialPropertie7").val("");
				$("#specialPropertie8").val("");
				$("#specialPropertie9").val("");
				$("#specialPropertie10").val("");
				$("#specialPropertie11").val("");
				$("#specialPropertie12").val("");
			},
			error : function() {
				layer.alert("服务器连接异常，请联系管理员！");
			}

		});
	});
}


