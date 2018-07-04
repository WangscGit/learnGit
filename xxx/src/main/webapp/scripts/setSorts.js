

function keyCtr(gridname) {

	$.extend($.fn.datagrid.methods, {
		keyCtr : function(jq) {
			return jq.each(function() {
				var grid = $(this);
				grid.datagrid('getPanel').panel('panel').attr('tabindex', 1)
						.bind('keydown ', function(e) {
							// e.preventDefault();//阻止浏览器默认动作
							if (event.ctrlKey && event.keyCode == 38) {
								var selected = grid.datagrid('getSelected');
								if (selected) {
									MoveTop(gridname);
								}
							};
							if (event.ctrlKey && event.keyCode == 40) {
								var selected = grid.datagrid('getSelected');
								if (selected) {
									MoveButtom(gridname);
								}
							};

							switch (event.keyCode || event.which) {
								case 38 : // up
									var selected = grid.datagrid('getSelected');
									if (selected) {
										MoveUp(gridname);
									} else {
										var rows = grid.datagrid('getRows');
										grid.datagrid('selectRow', rows.length
														- 1);
									}
									break;
								case 40 : // down
									var selected = grid.datagrid('getSelected');
									if (selected) {
										MoveDown(gridname);
									} else {
										grid.datagrid('selectRow', 0);
									}
									break;

							}

						});
			});
		}
	});

}
$(document).ready(function() {

	var editRow = undefined;
	var gridname = "Zcall_Table";
	$("#Zcall_Table").datagrid({

		collapsible : true,
		singleSelect : true,
		autoRowHeight : false,
		striped : true,
		rownumbers : true,
		animate : true,
		fitColumns : true,
		loadMsg : '加载中...',
		url : '../setSorts/dataList_Zc.do?',
		method : 'get',
		idField : 'id',
        onLoadSuccess : function(data) {
							$(this).datagrid('fixRownumber');
							$(this).datagrid('doCellTip', {
										'max-width' : '400px',
										'delay' : 500
									});
						},
		toolbar : [

		{
			text : '保存',

			iconCls : 'icon-save',
			handler : function() {
				$("#Zcall_Table").datagrid('endEdit', editRow);

				// 如果调用acceptChanges(),使用getChanges()则获取不到编辑和新增的数据。

				// 使用JSON序列化datarow对象，发送到后台。
				//var rows = $("#Zcall_Table").datagrid('getChanges');
				//var rowstr = JSON.stringify(rows);
				var rows = $("#Zcall_Table").datagrid("getRows"); // 这段代码是获取当前页的所有行。
				
				for (var i = 0; i < rows.length; i++) {
					// 获取每一行的数据
					var rowIndex = $('#Zcall_Table').datagrid('getRowIndex',rows[i]);
					
					rows[i].sorts = rowIndex+1;
				}
				var rowsStr = JSON.stringify(rows);
				swal({
				title : "确定保存吗？",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#DD6B55",
				confirmButtonText : "确定！",
				cancelButtonText : "取消！",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					$.ajax({
							cache : true,
							url : "../setSorts/saveRows.do",
							data :  {rows : rowsStr},
							type : "post",
										async : false,
							error : function(e){
								tips("connection error!");
							},
							success : function(data){
								swal("成功!","","success");
							}
						});

				} else {
					swal("取消！");
				}
			});

				
				
			}
		}, '-', {
			text : '撤销',
			iconCls : 'icon-redo',
			handler : function() {
				editRow = undefined;
				$("#Zcall_Table").datagrid('rejectChanges');
				$("#Zcall_Table").datagrid('unselectAll');
			}
		}, '-', {
			text : '刷新',
			iconCls : ' icon-2013040601125064_easyicon_net_16 ',
			handler : function() {
				$('#Zcall_Table').datagrid('load');
			}
		}, '-', {
			text : '上移',
			iconCls : 'icon-arrow_up',
			handler : function() {
				MoveUp(gridname);
			}
		}, '-', {
			text : '下移',
			iconCls : 'icon-arrow_down',
			handler : function() {
				MoveDown(gridname);
			}
		}, '-', {
			text : '置顶',
			iconCls : 'icon-20130406125647919_easyicon_net_16',
			handler : function() {
				MoveTop(gridname);
			}
		}, '-', {
			text : '置底',
			iconCls : 'icon-20130406125519344_easyicon_net_16',
			handler : function() {
				MoveButtom(gridname);
			}
		}, '-', {
			iconCls : 'icon-lightbulb',
			text : '快捷键功能:键盘' + '“↑”' + '表示上移,' + '键盘' + '“↓”' + '表示下移,'
					+ '键盘Ctrl+' + '“↑”' + '表示置顶,' + '键盘Ctrl+' + '“↓”' + '表示置底。'

		}]

	});
	keyCtr(gridname);
	// 实例化上下键盘事件
	$("#Zcall_Table").datagrid({}).datagrid("keyCtr");
});



// 增加标题模糊查询的功能
function query() {

	var detail = $('#query_detail').combobox('getValue');
	switch (detail) {
		case 'fj' :
			$('#Zcall_Table').datagrid("options").url = '../setSorts/dataList_Fj.do';
			$('#Zcall_Table').datagrid('load');
			break;
		case 'zc' :
			$('#Zcall_Table').datagrid("options").url = '../setSorts/dataList_Zc.do';
			$('#Zcall_Table').datagrid('load');
			break;
		case 'bd' :
			$('#Zcall_Table').datagrid("options").url = '../setSorts/dataList_Bd.do';
			$('#Zcall_Table').datagrid('load');
			break;
	}
          var tabname = $("#query_detail").combobox("getText");
							$("#tablename").text(tabname);// 填充内容
}
// 格式化字典
function formatDictionary(row) {
	var s = '<span style="font-weight:bold;margin-left: 5px; ">' + row.text
			+ '</span>';
	return s;
}

// 数据库选择
$(document).ready(function() {
			$("#query_detail").combobox({
						editable : false,
						showItemIcon : true,
						formatter : formatDictionary,
						onLoadSuccess : function() {
							$('.combo').click(function() {
										$(this).prev().combobox('showPanel');
									});
						},
						onChange : function(n, o) {
							
						}
					});
		});

// 百分比窗口的高度
function getHeight(percent) {
	return document.body.clientHeight * percent
}

// 上移
function MoveUp(gridname) {
	var row = $('#' + gridname).datagrid('getSelected');
	var index = $('#' + gridname).datagrid('getRowIndex', row);
	mysort(index, 'up', gridname);

}
// 下移
function MoveDown(gridname) {
	var row = $('#' + gridname).datagrid('getSelected');
	var index = $('#' + gridname).datagrid('getRowIndex', row);
	mysort(index, 'down', gridname);

}

// 置顶
function MoveTop(gridname) {
	var row = $('#' + gridname).datagrid('getSelected');
	var index = $('#' + gridname).datagrid('getRowIndex', row);
	mysort(index, 'top', gridname);

}
// 置底
function MoveButtom(gridname) {
	var row = $('#' + gridname).datagrid('getSelected');
	var index = $('#' + gridname).datagrid('getRowIndex', row);
	mysort(index, 'buttom', gridname);

}

// 判断向上，向下，置底，置顶的算法
function mysort(index, type, gridname) {
	if ("up" == type) {
		if (index != 0) {
			var toup = $('#' + gridname).datagrid('getData').rows[index];
			var todown = $('#' + gridname).datagrid('getData').rows[index - 1];
			$('#' + gridname).datagrid('getData').rows[index] = todown;
			$('#' + gridname).datagrid('getData').rows[index - 1] = toup;
			$('#' + gridname).datagrid('refreshRow', index);
			$('#' + gridname).datagrid('refreshRow', index - 1);
			$('#' + gridname).datagrid('selectRow', index - 1);
		}
	} else if ("down" == type) {
		var rows = $('#' + gridname).datagrid('getRows').length;
		if (index != rows - 1) {
			var todown = $('#' + gridname).datagrid('getData').rows[index];
			var toup = $('#' + gridname).datagrid('getData').rows[index + 1];
			$('#' + gridname).datagrid('getData').rows[index + 1] = todown;
			$('#' + gridname).datagrid('getData').rows[index] = toup;
			$('#' + gridname).datagrid('refreshRow', index);
			$('#' + gridname).datagrid('refreshRow', index + 1);
			$('#' + gridname).datagrid('selectRow', index + 1);
		}
	} else if ("top" == type) {
		var rows = $('#' + gridname).datagrid('getRows').length;
		var toup = $('#' + gridname).datagrid('getData').rows[index];
		for (var i = index; i > 0; i--) {
			var todown = $('#' + gridname).datagrid('getData').rows[i - 1];
			$('#' + gridname).datagrid('getData').rows[i] = todown;
			$('#' + gridname).datagrid('refreshRow', i);
		}
		$('#' + gridname).datagrid('getData').rows[0] = toup;
		$('#' + gridname).datagrid('refreshRow', 0);
		$('#' + gridname).datagrid('selectRow', 0);

	} else if ("buttom" == type) {
		var rows = $('#' + gridname).datagrid('getRows').length;
		var todown = $('#' + gridname).datagrid('getData').rows[index];
		for (var i = index; i < rows - 1; i++) {

			var toup = $('#' + gridname).datagrid('getData').rows[i + 1];
			$('#' + gridname).datagrid('getData').rows[i] = toup;
			$('#' + gridname).datagrid('refreshRow', i);
		}
		$('#' + gridname).datagrid('getData').rows[rows - 1] = todown;
		$('#' + gridname).datagrid('refreshRow', rows - 1);
		$('#' + gridname).datagrid('selectRow', rows - 1);

	}

}

function formatKind(val, row, index) {
	if (val == 1) {
		return "<font color='#FF0000'><b>是</b></font>";
	} else {
		return "<font >否</font> ";
	}
}


function formatSort(val, row, index) {
	var index = parseInt(index)+1;
	var sorts = parseInt(row.sorts);
	var change = index - sorts;
	var result = "";
	if (change>0) {
		result = "&nbsp&nbsp<font color='red'><b>"+"+"+change+"</b></font>"
	}else if(change==0){
		 result = "";
	}else{
		result = "&nbsp&nbsp<font color='green'><b>"+change+"</b></font>"
	}
		return "<font ><b>"+row.sorts+"</b></font>&nbsp"+"&nbsp"+result;
	
}
// 简单的ajax请求 短路与
function formatEnum(val, row, index) {
	var result = "";
	if (val != null && val.replace(/(^s*)|(s*$)/g, "").length != 0
			&& val !== undefined && val !== '') {
		$.ajax({
					type : "POST",
					async : false,
					url : '../prdDictionary/getDictionary_en.do',
					data : {
						dictionary_en : val
					},
					error : function(request) {
						swal({
									title : "未知错误,请重试",
									type : "warning",
									timer : 1000,
									showConfirmButton : false
								});
					},
					success : function(data) {
						result = data.data;
					}

				});
		return result;
	} else {
		return null;
	}
}

// 输入类型：1_文本框,2_下拉框,3_日期,4_单选框,5_复选框,6_树形菜单,7_图片上传8_文本域,9_文件上传
function formatType(val, row, index) {
	if (val == 1) {
		return "文本框";
	} else if (val == 2) {
		return "下拉框";
	} else if (val == 3) {
		return "日期";
	} else if (val == 4) {
		return "单选框";
	} else if (val == 5) {
		return "复选框";
	} else if (val == 6) {
		return "树形菜单";
	} else if (val == 7) {
		return "图片上传 ";
	} else if (val == 8) {
		return "文本域";
	} else {
		return "文件上传";
	}
}

// 数据类型加工方法 数据类型(长度大小)
function formatDataKind(val, row, index) {
	var data_kind = row.data_kind;
	var length = row.length;
	return data_kind + "(" + length + ")";
}

// 提示框效果
function remarkFormater(value, row, index) {
	var content = '';
	var abValue = value + '';
	if (value != undefined) {
		if (value.length >= 22) {
			abValue = value.substring(0, 19) + "...";
			content = '<a href="javascript:;"  title="' + value
					+ '" class="easyui-tooltip">' + abValue + '</a>';
		} else {
			content = '<a href="javascript:;"  title="' + abValue
					+ '" class="easyui-tooltip">' + abValue + '</a>';
		}
	}
	return content;
}
// easyui 替换指定字符串
function remarkReplace2(value) {

	/*
	 * 替换
	 */

	value = value.replace(/S/g, '设备');
	value = value.replace(/J/g, '家具');
	value = value.replace(/T/g, '图书');
	value = value.replace(/D/g, '低值品');
	value = value.replace(/F/g, '房屋');
	value = value.replace(/Z/g, '动植物');
	value = value.replace(/R/g, '软件');
	value = value.replace(/Q/g, '车辆');
	// value = value.replace(/K/g, '材料仓库');
	value = value.replace(/W/g, '文物');
	value = value.replace(/C/g, '无形资产');
	value = value.replace(/X/g, '土地');
	value = value.replace(/G/g, '构筑物');

	return value;
}