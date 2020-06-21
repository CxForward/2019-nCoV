$("#button_item1").click(function() {
	$("#button_item1").css({
		"background-color": "white",
		"border": "1px solid red"
	});
	$("#button_item2").css({
		"background-color": "#eee",
		"border": "none"
	});
	map_1.clear();
	showMap1();

});
$("#button_item2").click(function() {
	$("#button_item1").css({
		"background-color": "#eee",
		"border": "none"
	});
	$("#button_item2").css({
		"background-color": "white",
		"border": "1px solid red"
	});
	map_1.clear();
	showMap2();
});



var map_1 = echarts.init(document.getElementById('map1'));
var days = new Array();
var province = [];
var data = new Array();
var data1 = [];
var data2 = [];
var sum = [];

function getBetweenDateStr(start, end) {
	var result = [];
	var beginDay = start.split("-");
	var endDay = end.split("-");
	var diffDay = new Date();
	var dateList = new Array;
	var i = 0;
	diffDay.setDate(beginDay[2]);
	diffDay.setMonth(beginDay[1] - 1);
	diffDay.setFullYear(beginDay[0]);
	result.push(start);
	while (i == 0) {
		var countDay = diffDay.getTime() + 24 * 60 * 60 * 1000;
		diffDay.setTime(countDay);
		dateList[2] = diffDay.getDate();
		dateList[1] = diffDay.getMonth() + 1;
		dateList[0] = diffDay.getFullYear();
		if (String(dateList[1]).length == 1) {
			dateList[1] = "0" + dateList[1]
		};
		if (String(dateList[2]).length == 1) {
			dateList[2] = "0" + dateList[2]
		};
		result.push(dateList[0] + "-" + dateList[1] + "-" + dateList[2]);
		if (dateList[0] == endDay[0] && dateList[1] == endDay[1] && dateList[2] == endDay[2]) {
			i = 1;
		}
	};

	return result;
};

function currDate() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1 >= 10 ? (date.getMonth()) + 1 : ('0' + (date.getMonth() + 1));
	var day = date.getDate() >= 10 ? date.getDate() : ('0' + date.getDate());
	return year + "-" + month + "-" + day;
};
//ajax异步请求
$.when(
	$(function() {

		$.each(getBetweenDateStr('2020-02-14', currDate()), function(j, value) {
			$.ajax({
				url: "DataApi?method=selectByDate&date=" + value,
				dataType: "json",
				cache: false,
				async: false,
				type: "get",
				success: function(res, status) {
					if (res.provinceList[0] == null) {
						console.log(value + '数据未更新');
					} else {
						const currProvince = [];
						const currData1 = [];
						const currData2 = [];
						days.push(value);
						for (var i = 0; i < 34; i++) {
							currProvince.push(res.provinceList[i].provinceShortName);
							currData1.push(res.provinceList[i].confirmedCount);
							currData2.push(res.provinceList[i].currentConfirmedCount);
						}
						//console.log(value);
						province.push(currProvince);
						data1.push(currData1);
						data2.push(currData2);
						//JSON.stringify(res); //返回内容绑定到ID为result的标签
					}

				}
			});

		})
	})

).then(function() {
	var s = setTimeout(showMap1, 100);
})


function sumData() {
	for (var i = 0; i < data.length; i++) {
		sum[i] = 0;
		for (var j = 0; j < data[i].length; j++) {
			sum[i] = sum[i] + data[i][j]
		}
	};
}


var option = {
	baseOption: {
		timeline: {
			axisType: 'category',
			// realtime: false,
			// loop: false,
			autoPlay: true,
			playInterval: 2000,
			symbolSize: 12,
			//	symbol: 'circle',//时间长轴的形式
			checkpointStyle: {
				symbol: 'circle' //时间轴上移动时的亮的标记形状
			},
			controlStyle: { //按钮设置
				itemSize: 16
			},
			lineStyle: {
				opacity: 0.4,
			},
			left: '2%',
			right: '2%',
			bottom: '0%',
			width: '85%',
			label: {
				fontSize: 12,
				interval: 15,
			},
			// controlStyle: {
			//     position: 'left'
			// },
			data: days,
			tooltip: {
				formatter: function(params) {
					//console.log(params);
					//return '确诊人数：' + sum[params.dataIndex];
					return '日期： ' + params.name;

				}
			},
		},
		visualMap: {
			type: 'piecewise',
			pieces: [{
					min: 10000,
					color: '#73240D'
				},
				{
					min: 10000,
					max: 19999,
					color: '#BB0000'
				},
				{
					min: 1000,
					max: 9999,
					color: '#BD430A'
				},
				{
					min: 100,
					max: 999,
					color: '#E08150'
				},
				{
					min: 10,
					max: 99,
					color: '#E9B090'
				},
				{
					min: 1,
					max: 9,
					color: '#FFE5DB'
				},
				{
					value: 0,
					color: 'white'
				}
			],
			orient: 'vertical',
			itemWidth: 20,
			itemHeight: 10,
			itemGap: 6,
			//showLabel: true,
			seriesIndex: [0],

			textStyle: {
				color: '#7B93A7',
				fontSize: 10
			},
			bottom: '10%',
			left: "5%",
		},
		toolbox: {
			feature: {
				saveAsImage: {},
				restore: {}
			},
			iconStyle: {
				normal: {
					borderColor: '#7B93A7'
				}
			},
			emphasis: {
				iconStyle: {
					textFill: 'red'
				}
			},
			itemSize: 14,
			itemGap: 30,
			bottom: '12%',
			left: '35%',
		},
		grid: [{
			right: '10%',
			top: '30%',
			bottom: '10%',
			width: '20%'
		}],

		xAxis: [{
			min: 0,
			show: false,

		}],
		yAxis: [{
				name: '',
				inverse: true,
				offset: '5',
				type: 'category',
				data: '',
				axisTick: {
					show: false,
				},
				axisLabel: {
					//rotate:45,
					textStyle: {
						fontSize: 12,
						color: '#000',
					},
					interval: 0
				},
				axisLine: {
					show: false,
				},
				splitLine: {
					show: false
				},
			},

		],
		geo: {
			map: 'china',
			roam: true,
			right: '35%',
			left: '5%',
			zoom: 0.8,
			label: {
				show: true,
				fontSize: 10

			},
			itemStyle: {
				emphasis: {
					areaColor: '#c9ffff'
				}
			}
		},
		series: [{
				name: '',
				type: 'map',
				geoIndex: 0,
			},
			{
				'name': '',
				'type': 'bar',
				xAxisIndex: 0, //指定柱状图数据显示到：grid坐标系：0
				yAxisIndex: 0,
				zlevel: 2,
				barWidth: '40%',
				label: {
					normal: {
						show: true,
						fontSize: 14,
						position: 'right',

					}
				},
			},

		],

	},
	animationDurationUpdate: 2000,
	animationEasingUpdate: 'quinticInout',
	options: []
};

function insertOption() {
	for (var n = 0; n < days.length; n++) {
		var res = new Array();
		for (var j = 0; j < data[n].length; j++) {
			res.push({
				name: province[n][j],
				value: data[n][j]
			});
		}
		res.sort(function(a, b) {
			return b.value - a.value;
		}).slice(0, 6);

		res.sort(function(a, b) {
			return a.value - b.value;
		});
		var res1 = [];
		var res2 = [];
		for (t = 0; t < 10; t++) {
			res1[t] = res[res.length - 1 - t].name;
			res2[t] = res[res.length - 1 - t].value;

		}
		option.options.push({
			title: [{
					text: days[n] + '新型冠状病毒全国感染人数' + sum[n],
					textStyle: {
						color: '#2D3E53',
						fontSize: 18
					},
					left: '10%',
					top: '10%',
				},
				{
					show: true,
					text: '感染人数前十的省份',
					textStyle: {
						color: '#2D3E53',
						fontSize: 18
					},
					right: '10%',
					top: '10%'
				}
			],
			tooltip: {
				show: true,
				formatter: function(params) {
					if (params.name && params.value) {
						return '确诊<br/>' + params.name + '：' + params.value;
					} else if (params.name) {
						return params.name;
					}
				},
				textStyle: {
					fontSize: 14,
				}
			},
			yAxis: {
				data: res1,

			},
			series: [{
				type: 'map',
				data: res,
			}, {
				type: 'bar',
				data: res2,
				itemStyle: {
					color: function(params) {
						// build a color map as your need.
						var colorList = [{
								colorStops: [{
									offset: 0,
									color: '#BB0000' // 0% 处的颜色
								}, {
									offset: 1,
									color: '#BD430A' // 100% 处的颜色
								}]
							},
							{
								colorStops: [{
									offset: 0,
									color: '#E08150' // 0% 处的颜色
								}, {
									offset: 1,
									color: '#FFE5DB' // 100% 处的颜色
								}]
							}
						];
						if (params.dataIndex < 3) {
							return colorList[0]
						} else {
							return colorList[1]
						}
					},
					barBorderRadius: [0, 5, 5, 0],
				},
			}]
		});
	};
};

function showMap1() {
	data = data1;
	sumData();
	option.options = [];
	insertOption();
	map_1.setOption(option);
}

function showMap2() {
	data = data2;
	sumData();
	option.options = [];
	insertOption();
	map_1.setOption(option);
}
