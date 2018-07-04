/** 统计模块主要js */


//单击确定、进入页面显示所有图形
function showChart() {
	countProductPn();
	countAddPartData($("#one")[0], "div1");
	countAddPartData($("#one")[0], "div2");
	countAddPartData($("#one")[0], "div3");
}
/**
 * 统计在一定时间内新增partData的partType数量
 */
function countAddPartData(_this,dId){
	//获取startDate和endDate
	var startDate=$(_this).parent().find("input").eq(0).val();
	var endDate=$(_this).parent().find("input").eq(1).val();
//	startDate=startDate.replace("年","-").replace("月","-").replace("日","");
//	endDate=endDate.replace("年","-").replace("月","-").replace("日","");
	var divId=dId;
	
	//请求后台获取partType和count
	$.ajax({
		url : "partDataController/countPartData.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"startDate" : startDate,
			"endDate": endDate,
			"divId":divId
		},
		success : function(json) {
			var dataList=json.dataList;
			var partTypeList=json.partTypeList;
			var myChart = echarts.init($("#"+divId)[0]);
			var option="";
			if (divId == 'div2' || divId == 'div3') {
				option = {
					title : {
						text : json.name,
						subtext : '',
						x : 'center'
					},
					xAxis : {
						  type: 'category',
						data : json.typeList
					},
					yAxis : {
						type : 'value'
					},
					series : [{
								
								data : json.numLsit,
								type : 'bar'
							}]
				};

			} else {
				// 构造饼图
				option = {
					title : {
						text : json.name,
						subtext : '',
						x : 'center'
					},
					tooltip : {
						trigger : 'item',
						formatter : "{b} : {c} ({d}%)"
					},
					legend : {
						orient : 'vertical',
						left : 'left',
						data : partTypeList
					},
					series : [{
								name : json.name,
								type : 'pie',
								radius : '65%',
								center : ['50%', '60%'],
								label : {
									normal : {
										formatter : '{b|{b}：}{c}  {per|{d}%}  ',
										backgroundColor : '#eee',
										borderColor : '#aaa',
										borderWidth : 1,
										borderRadius : 4,
										rich : {
											a : {
												color : '#999',
												lineHeight : 22,
												align : 'center'
											},
											hr : {
												borderColor : '#aaa',
												width : '100%',
												borderWidth : 0.5,
												height : 0
											},
											b : {
												fontSize : 16,
												lineHeight : 33
											},
											per : {
												color : '#eee',
												backgroundColor : '#334455',
												padding : [2, 4],
												borderRadius : 2
											}
										}
									}
								},
								data : dataList,
								itemStyle : {
									emphasis : {
										shadowBlur : 10,
										shadowOffsetX : 0,
										shadowColor : 'rgba(0, 0, 0, 0.5)'
									}
								}
							}]
				};
			}
			myChart.setOption(option);
		},
		error : function() {
		}
	});
}

// 统计productPn数据
function countProductPn(){
	var startDate=$("#one").eq(0).parent().find("input").eq(0).val();
	var endDate=$("#one").eq(0).parent().find("input").eq(1).val();
	$.ajax({
		url : "partDataController/countProductPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"startDate" : startDate,
			"endDate": endDate,
			"gm":'g'
		},
		success : function(json) {
			var dataList=json.dataList;
			var typeList=json.typeList;
			var myChart = echarts.init($("#china")[0]);
			// 构造饼图
			option = {
   				title : {
        			text: json.name,
       				subtext: '',
        			x:'center'
   				},
    			tooltip : {
        			trigger: 'item',
        			formatter: "{b} : {c} ({d}%)"
   	 			},
    			legend: {
        			orient: 'vertical',
        			left: 'left',
        			data: typeList
    			},
    			series : [{
            		name: json.name,
            		type: 'pie',
            		radius : '65%',
            		center: ['50%', '60%'],
            		label: {
               		 	normal: {
                    		formatter: '{b|{b}：}{c}  {per|{d}%}  ',
                    		backgroundColor: '#eee',
                    		borderColor: '#aaa',
                   	 		borderWidth: 1,
                    		borderRadius: 4,
                    		rich: {
                        		a: {
                            		color: '#999',
                            		lineHeight: 22,
                            		align: 'center'
                        		},
                        		hr: {
                            		borderColor: '#aaa',
                            		width: '100%',
                            		borderWidth: 0.5,
                            		height: 0
                        		},
                        		b: {
                            		fontSize: 12,
                            		lineHeight: 26
                        		},
                        		per: {
                            		color: '#eee',
                            		backgroundColor: '#334455',
                            		padding: [2, 4],
                            		borderRadius: 2
                        		}
                    		}
                		}
            		},
            		data:dataList,
            		itemStyle: {
                		emphasis: {
                    		shadowBlur: 10,
                    		shadowOffsetX: 0,
                    		shadowColor: 'rgba(0, 0, 0, 0.5)'
                		}
            		}
        		}]
			};
			myChart.setOption(option);
		},
		error : function() {
		}
	});
	$.ajax({
		url : "partDataController/countProductPn.do",
		dataType : "json",
		cache : false,
		type : "post",
		data : {
			"startDate" : startDate,
			"endDate": endDate,
			"gm":'m'
		},
		success : function(json) {
			var dataList=json.dataList;
			var typeList=json.typeList;
			var myChart = echarts.init($("#super")[0]);
			//构造饼图
			option = {
   				title : {
        			text: json.name,
       				subtext: '',
        			x:'center'
   				},
    			tooltip : {
        			trigger: 'item',
        			formatter: "{b} : {c} ({d}%)"
   	 			},
    			legend: {
        			orient: 'vertical',
        			left: 'left',
        			data: typeList
    			},
    			series : [{
            		name: json.name,
            		type: 'pie',
            		radius : '65%',
            		center: ['50%', '60%'],
            		label: {
               		 	normal: {
                    		formatter: '{b|{b}：}{c}  {per|{d}%}  ',
                    		backgroundColor: '#eee',
                    		borderColor: '#aaa',
                   	 		borderWidth: 1,
                    		borderRadius: 4,
                    		rich: {
                        		a: {
                            		color: '#999',
                            		lineHeight: 22,
                            		align: 'center'
                        		},
                        		hr: {
                            		borderColor: '#aaa',
                            		width: '100%',
                            		borderWidth: 0.5,
                            		height: 0
                        		},
                        		b: {
                            		fontSize: 12,
                            		lineHeight: 26
                        		},
                        		per: {
                            		color: '#eee',
                            		backgroundColor: '#334455',
                            		padding: [2, 4],
                            		borderRadius: 2
                        		}
                    		}
                		}
            		},
            		data:dataList,
            		itemStyle: {
                		emphasis: {
                    		shadowBlur: 10,
                    		shadowOffsetX: 0,
                    		shadowColor: 'rgba(0, 0, 0, 0.5)'
                		}
            		}
        		}]
			};
			myChart.setOption(option);
		},
		error : function() {
		}
	});
}