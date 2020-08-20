<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>国内外新型冠状病毒肺炎疫情数据</title>
<link rel="shortcut icon" href="image/yq.png" type="image/x-icon" />

<!-- 
DNS数据
charts.js
script src="https://www.echartsjs.com/examples/vendors/echarts/echarts.min.js?_v_=1578305236132"></script>
china.js
 <script src="https://www.echartsjs.com/examples/vendors/echarts/map/js/china.js?_v_=1578305236132"></script>
 -->
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

a {
	text-decoration: none;
	cursor: pointer;
	color: grey;
}

a:hover {
	text-decoration: none;
}

#head_img {
	width: 100%
}

#all {
	position: relative;
	margin: 0 auto;
	width: 100%;
}

.line {
	margin-top: 20px;
	line-height: 25px;
	width: 100%;
}

.line .title {
	font-size: 16px;
	font-weight: bold;
}

.line .src {
	color: grey;
}

.show_data {
	background-color: #E2E2E2;
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 4px;
	padding: 10px 0 10px 0;
	width: 100%;
}

.data {
	width: 120px;
	position: relative;
	text-align: center;
}

.data .data_number {
	font-size: 18px;
	font-weight: 600;
}

.data .data_text {
	font-size: 14px;
}

.data .add {
	padding: 5px 0 0 0;
	font-size: 12px;
}

.two_button {
	margin-top: 15px;
	text-align: center;
	font-size: 12px;
}

.button_item {
	outline: none;
	width: 65px;
	height: 30px;
	margin-right: 45px;
	background-color: #eee;
}

#map-china {
	position: relative;
	margin: 0 auto;
	height: 500px;
	width: 800px;
}

#map-world {
	position: relative;
	margin: 0 auto;
	height: 500px;
	width: 800px;
}

#rank {
	position: relative;
	margin: 0 auto;
	height: 350px;
	width: 650px;
}

body {
	background-color: #F8F9FA;
	transform: scale(1.0);
	margin: 0 auto;
	width: 1000px;
}
</style>
<script type="text/javascript" src="js/echarts.min.js"></script>
<!-- 地图数据 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/map/china.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/map/world.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/map/nameMap.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>



</head>

<body>
	<div id="all">
	
		<div>
			<img alt="" id="head_img" src="image/top.jpg">
		</div>
		<div class="line">
			<div class="title">国外疫情</div>
			<div class="src">
				更新：&nbsp;<span id="update_foregin"></span>
			</div>
			<div class="src" id="text1"></div>
		</div>
		<div class="show_data">
			<div class="data">
				<div class="data_number" id="gconfirmedCount"
					style="color: #F55253;"></div>
				<div class="data_text">累计确诊</div>
				<div class="add">
					昨日 <span style="color: #F55253;" id="add_gconfirmedCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="gcurrentConfirmedCount"
					style="color: #cc0000;"></div>
				<div class="data_text">现存确诊</div>
				<div class="add">
					昨日 <span style="color: #cc0000;" id="add_gcurrentConfirmedCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="gcuredCount" style="color: #178B50;"></div>
				<div class="data_text">累计治愈</div>
				<div class="add">
					昨日 <span style="color: #178B50;" id="add_gcuredCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="gdeadCount" style="color: grey;"></div>
				<div class="data_text">累计死亡</div>
				<div class="add">
					昨日 <span style="color: grey;" id="add_gdeadCount"></span>
				</div>
			</div>
		</div>
		<div class="line">
			<div class="title">国内疫情</div>
			<div class="src">
				更新：&nbsp;<span id="update_china"></span>
			</div>
		</div>
		<div class="show_data">
			<div class="data">
				<div class="data_number" id="confirmedCount" style="color: #F55253;"></div>
				<div class="data_text">累计确诊</div>
				<div class="add">
					昨日 <span style="color: #F55253;" id="add_confirmedCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="currentConfirmedCount"
					style="color: #cc0000;"></div>
				<div class="data_text">现存确诊</div>
				<div class="add">
					昨日 <span style="color: #cc0000;" id="add_currentConfirmedCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="curedCount" style="color: #178B50;"></div>
				<div class="data_text">累计治愈</div>
				<div class="add">
					昨日 <span style="color: #178B50;" id="add_curedCount"></span>
				</div>
			</div>
			<div class="data">
				<div class="data_number" id="deadCount" style="color: grey;"></div>
				<div class="data_text">累计死亡</div>
				<div class="add">
					昨日 <span style="color: grey;" id="add_deadCount"></span>
				</div>
			</div>
		</div>


		<div class="line">
			<div class="title">国内历史部分疫情</div>
		</div>
		<div id="map-china">
			<div id="map1" style="width: 100%; height: 100%; margin: 0 auto;"></div>
		</div>
		<div class="two_button">
			<input class="button_item" id="button_item1" type="button"
				style="background-color: white; border: 1px solid red;" value="累计确诊">
			<input class="button_item" id="button_item2" type="button"
				value="现有确诊">
			<script type="text/javascript" src="js/user/mapData.js"></script>
		</div>


		<div class="line">
			<div class="title">国内境外输入确诊前十省市</div>
		</div>
		<div id="rank">
			<div id="importTop10"
				style="width: 100%; height: 100%; margin: 0 auto;"></div>
		</div>

		<div class="line">
			<div class="title">国外历史部分疫情</div>
		</div>
		<div id="map-world">
			<div id="map2" style="width: 100%; height: 100%; margin: 0 auto;"></div>

		</div>
		<div class="two_button">
			<input class="button_item" id="button_item3" type="button"
				style="background-color: white; border: 1px solid red;" value="累计确诊">
			<input class="button_item" id="button_item4" type="button"
				value="现有确诊">
			<script type="text/javascript" src="js/user/wordMapData.js"></script>
		</div>
		<div class="line">
			<div class="title">昨日新增国家前十</div>
		</div>
		<div id="rank">
			<div id="top10" style="width: 100%; height: 100%; margin: 0 auto;"></div>
		</div>

		<div class="line">
			<div class="title">全球疫情发展趋势</div>
		</div>
		<div id="rank">
			<div id="globalHistory"
				style="width: 100%; height: 100%; margin: 0 auto;"></div>
		</div>
		<div class="line">
			<div class="title">全球治愈/死亡率</div>
		</div>
		<div id="rank">
			<div id="rate" style="width: 100%; height: 100%; margin: 0 auto;"></div>
		</div>

		<script type="text/javascript" src="js/user/currData.js"></script>


	</div>



</body>

</html>
