var map_2 = echarts.init(document.getElementById('map2'));

function addSymbol(num) {
	return num >= 0 ? '+' + num : num;
};
var foreginData = null;
var chinaData = null;
var newslist = null;

$.when(
	$.ajax({
		//url: "https://view.inews.qq.com/g2/getOnsInfo?name=disease_foreign",
		url: "TianData?method=resForeginData",
		dataType: "json",
		cache: false,
		success: function(data) {
			console.log("海外数据获取成功")
			foreginData = data.data;
			let res = data.data;
			res = res.globalStatis;
			$(".line .src #update_foregin").text(res.lastUpdateTime);

			$("#gconfirmedCount").text(res.confirm);
			$("#gcurrentConfirmedCount").text(res.nowConfirm);
			$("#gdeadCount").text(res.dead);
			$("#gcuredCount").text(res.heal);

			$("#add_gconfirmedCount").text(addSymbol(res.confirmAdd));
			$("#add_gcurrentConfirmedCount").text(addSymbol(res.nowConfirmAdd));
			$("#add_gdeadCount").text(addSymbol(res.deadAdd));
			$("#add_gcuredCount").text(addSymbol(res.healAdd));
		}
	}), $.ajax({
		url: "TianData?method=resChinaData",
		dataType: "json",
		cache: false,
		success: function(data) {
			console.log("国内数据获取成功");
			chinaData = data.data;
			var res = data.data;
			$(".line .src #update_china").text(res.lastUpdateTime);

			$("#confirmedCount").text(res.chinaTotal.confirm);
			$("#currentConfirmedCount").text(res.chinaTotal.nowConfirm);
			$("#deadCount").text(res.chinaTotal.dead);
			$("#curedCount").text(res.chinaTotal.heal);

			$("#add_confirmedCount").text(addSymbol(res.chinaAdd.confirm));
			$("#add_currentConfirmedCount").text(addSymbol(res.chinaAdd.nowConfirm));
			$("#add_deadCount").text(addSymbol(res.chinaAdd.dead));
			$("#add_curedCount").text(addSymbol(res.chinaAdd.heal));
		}

	}),$.ajax({
		url: "TianData?method=resTianForeginDate",
		dataType: "json",
		cache: false,
		success: function(data){
			newslist = data.newslist;
		}
	})
).then(function() {
	worldMap();
	importTop10();
	top10();
	globalDailyHistory();
	rate();
})

function importTop10() {
	var rank = echarts.init(document.getElementById('importTop10'));
	var num = [];
	var city = [];
	$.each(foreginData.importStatis.TopList, function(i, v) {
		num.push(v.importedCase);
		city.push(v.province);
	})
	var option = {
		tooltip: {
			formatter: function(params) {
				return '境外输入' + '<br/>' + params.name + ': ' + params.value;
			}
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			data: city,
			type: 'category',
			axisLabel: {
				interval: 0,
				rotate: 45,
				fontSize: 12,
			}
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			type: 'bar',
			data: num,
			barWidth: 14,
			itemStyle: {
				emphasis: {
					color: '#F9D774'
				}
			},

		}],
	};
	rank.setOption(option);
}

function worldMap() {
	var virusDatas = [];
	$.each(newslist, function(i, v) {
		virusDatas[i] = {};
		virusDatas[i].name = v.provinceName;
		virusDatas[i].value = v.confirmedCount;
	})
	/*virusDatas.push({
		name: "中国",
		value: chinaData.chinaTotal.confirm
	});*/
	var option_world = {
		title: {

		},
		tooltip: {
			formatter: function(params) {
				if(params.name&&params.value){
					return '确诊<br/>' +params.name +'：' + params.value;
				}
				else if(params.name){
					return params.name;
				}
			}
		},
		toolbox: {
			feature: {
				saveAsImage: {},
				restore: {}
			},
			emphasis: {
				iconStyle: {
					textFill: 'red'
				}
			},
			itemSize: 16,
			itemGap: 82,
			left: '39%',
			bottom: '5%'
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
			]
		},
		series: [{
			name: '',
			type: 'map',
			roam: true,
			mapType: 'world',
			data: virusDatas,
			nameMap: nameMap,
			itemStyle: {
				emphasis: {
					areaColor: '#c9ffff',
					label: {
						show: true
					}
				}
			}
		}]
	};
	map_2.setOption(option_world);
	$("#button_item3").click(function() {
		$("#button_item3").css({
			"background-color": "white",
			"border": "1px solid red"
		});
		$("#button_item4").css({
			"background-color": "#eee",
			"border": "none"
		});

		blind('confirmedCount');
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
		blind('currentConfirmedCount');
	});

	function blind(key) {
		$.each(newslist, function(i, v) {
			virusDatas[i].value = v[key];
		})
		/*virusDatas[virusDatas.length - 1].value = chinaData.chinaTotal[key];*/
		option_world.series[0].data = virusDatas;
		map_2.setOption(option_world);
	}
}

function top10() {
	var rank = echarts.init(document.getElementById('top10'));
	var num = [];
	var country = [];
	$.each(foreginData.countryAddConfirmRankList, function(i, v) {
		num.push(v.addConfirm);
		country.push(v.nation);
	})
	var option = {
		tooltip: {
			formatter: function(params) {
				return '新增' + '<br/>' + params.name + ': ' + params.value;
			}
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			data: country,
			type: 'category',
			axisLabel: {
				interval: 0,
				rotate: 45,
				fontSize: 12,
			}
		},
		yAxis: {
			type: 'value'
		},
		series: [{
			type: 'bar',
			data: num,
			barWidth: 14,
			itemStyle: {
				emphasis: {
					color: '#F9D774'
				}
			},

		}],
	};
	rank.setOption(option);
};

function globalDailyHistory() {
	var dailyHistory = echarts.init(document.getElementById('globalHistory'));
	var daily = [];
	var confirm = [];
	var dead = [];
	var heal = [];
	var newAddConfirm = [];
	$.each(foreginData.globalDailyHistory, function(i, v) {
		confirm.push(v.all.confirm);
		dead.push(v.all.dead);
		heal.push(v.all.heal);
		newAddConfirm.push(v.all.newAddConfirm);
		daily.push(v.date);
	});
	var option = {
		title: {
			text: ''
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['累计确诊', '新增确诊', '累计治愈', '累计死亡']
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			axisLabel: {
				interval: 5,
				rotate: 45,
				fontSize: 12,
			},
			data: daily
		},
		yAxis: {
			name: '单位(人)',
			type: 'value'
		},
		series: [{
				name: '累计确诊',
				type: 'line',
				data: confirm
			},
			{
				name: '新增确诊',
				type: 'line',
				data: newAddConfirm
			},
			{
				name: '累计治愈',
				type: 'line',
				data: heal
			},
			{
				name: '累计死亡',
				type: 'line',
				data: dead
			}
		]
	};
	dailyHistory.setOption(option);
}

function rate() {
	var rate_change = echarts.init(document.getElementById('rate'));
	var daily = [];
	var deadRate = [];
	var healRate = [];
	$.each(foreginData.globalDailyHistory, function(i, v) {
		deadRate.push(v.all.deadRate);
		healRate.push(v.all.healRate);
		daily.push(v.date);
	});
	var option = {
		title: {
			text: ''
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data: ['治愈率', '死亡率']
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			axisLabel: {
				interval: 5,
				rotate: 45,
				fontSize: 12,
			},
			data: daily
		},
		yAxis: {
			name: '单位%',
			type: 'value'
		},
		series: [


			{
				name: '治愈率',
				type: 'line',
				data: healRate
			},
			{
				name: '死亡率',
				type: 'line',
				data: deadRate
			}
		]
	};
	rate_change.setOption(option);
}
