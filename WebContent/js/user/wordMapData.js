$("#button_item3").click(function() {
	$("#button_item3").css({
		"background-color": "white",
		"border": "1px solid red"
	});
	$("#button_item4").css({
		"background-color": "#eee",
		"border": "none"
	});
	map_2.clear();
	showMap3();

});
$("#button_item4").click(function() {
	$("#button_item3").css({
		"background-color": "#eee",
		"border": "none"
	});
	$("#button_item4").css({
		"background-color": "white",
		"border": "1px solid red"
	});
	map_2.clear();
	showMap4();
});



var map_2 = echarts.init(document.getElementById('map2'));
var gdays = new Array();
var nation = [];
var gdata = new Array();
var gdata1 = [];
var gdata2 = [];
var gsum = [];
var m =0;
$.when(
	$(function() {
			$.ajax({
				url: "DataApi?method=selectByForeginDate",
				dataType: "json",
				cache: false,
				async: false,
				type: "get",
				success: function(res, status) {
					$.each(res,function(i,e){
						m++;
						const currProvince = [];
						const currData1 = [];
						const currData2 = [];
						$.each(e,function(j,v){
							currProvince.push(v.provinceName);
							currData1.push(v.confirmedCount);
							currData2.push(v.currentConfirmedCount);
						});
						nation.push(currProvince);
						gdata1.push(currData1);
						gdata2.push(currData2);
					});
					$.each(getBetweenDateStr('2020-06-15', currDate()), function(j, value) {
						gdays.push(value);
					});
					
					while(gdays.length>m){
						gdays.pop();
					}
				/*	if (res.nationList[0] == null) {
						console.log(value + '国外数据未更新');
					} else {
						const currProvince = [];
						const currData1 = [];
						const currData2 = [];
						const nationList = res.nationList;
						$.each(nationList, function(i, v) {
							currProvince.push(v.provinceName);
							currData1.push(v.confirmedCount);
							currData2.push(v.currentConfirmedCount);
						});
						nation.push(currProvince);
						gdata1.push(currData1);
						gdata2.push(currData2);
					}*/
				}
			});
		
		
	})
).then(function() {
	
	var gs = setTimeout(showMap3, 100);
})




function gsumData() {
	for (var i = 0; i < gdata.length; i++) {
		gsum[i] = 0;
		for (var j = 0; j < gdata[i].length; j++) {
			gsum[i] = gsum[i] + gdata[i][j]
		}
	};
}


var goption = {
	baseOption: {
		timeline: {
			axisType: 'category',
			// realtime: false,
			// loop: false,
			autoPlay: true,
			playInterval: 1000,
			symbolSize: 10,
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
			left: '5%',
			right: '5%',
			bottom: '0%',
			width: '85%',
			label: {
				fontSize: 12,
				interval: gdays.length > 60 ? (gdays.length>120?25:15) : 8
			},
			// controlStyle: {
			//     position: 'left'
			// },
			data: gdays,
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
					min: 100000,
					color: '#73240D'
				},
				{
					min: 10000,
					max: 99999,
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
			map: 'world',
			roam: true,
			right: '35%',
			left: '2%',
			bottom: '10%',
			zoom: 0.9,
			top: '28%',
			nameMap: nameMap,
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
				name: '',
				type: 'bar',
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

function ginsertOption() {
	for (var n = 0; n < gdays.length; n++) {
		var res = new Array();
		for (var j = 0; j < gdata[n].length; j++) {
			res.push({
				name: nation[n][j],
				value: gdata[n][j]
			});
		}
		res.sort(function(a, b) {
			return a.value - b.value;
		});
		var res1 = [];
		var res2 = [];
		for (var t = 0; t < 10; t++) {
			res1[t] = res[res.length - 1 - t].name;
			res2[t] = res[res.length - 1 - t].value;

		}
		goption.options.push({
			title: [{
					text: gdays[n] + '新型冠状病毒全球感染人数' + gsum[n],
					textStyle: {
						color: '#2D3E53',
						fontSize: 18
					},
					left: '10%',
					top: '10%',
				},
				{
					show: true,
					text: '感染人数前十的国家或地区',
					textStyle: {
						color: '#2D3E53',
						fontSize: 18
					},
					right: '8%',
					top: '10%'
				}
			],
			tooltip: {
				show: true,
				formatter: function(params) {
					if (params.name && params.value) {
						return '确诊<br/>' + params.name + '：' + (params.value > 0 ? params.value:0);
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
				mapType: 'world',
				data: res,
			}, {
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

function showMap3() {
	gdata = gdata1;
	gsumData();
	goption.options = [];
	ginsertOption();
	map_2.setOption(goption);
}

function showMap4() {
	gdata = gdata2;
	gsumData();
	goption.options = [];
	ginsertOption();
	map_2.setOption(goption);
}
