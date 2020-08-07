function addSymbol(num) {
	return num >= 0 ? '+' + num : num;
};
var foreginData = null;
var chinaData = null;

$.when(
	$.ajax({
		//url: "https://view.inews.qq.com/g2/getOnsInfo?name=disease_foreign",
		url: "TianData?method=resForeginData",
		dataType: "json",
		cache: false,
		success: function(data) {
			//console.log("海外数据获取成功")
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
			//console.log("国内数据获取成功");
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

	})
).then(function() {

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
