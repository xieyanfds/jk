<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>amCharts examples</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/components/amchartNew/style.css"
	type="text/css">
<script
	src="${pageContext.request.contextPath }/components/amchartNew/amcharts/amcharts.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/components/amchartNew/amcharts/funnel.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath }/components/amchartNew/amcharts/plugins/export/export.js"></script>
<link type="text/css"
	href="${pageContext.request.contextPath }/components/amchartNew/amcharts/plugins/export/export.css"
	rel="stylesheet">
<style>
body, html {
	height: 100%;
	padding: 0;
	margin: 0;
	overflow: hidden;
	font-size: 11px;
	font-family: Verdana;
}

#chartdiv {
	width: 700px;
	height: 600px;
}
</style>

<script type="text/javascript">
    var chartdata = ${result};
	var chart = AmCharts.makeChart("chartdiv", {
		"type" : "funnel",
		"dataProvider" : chartdata,
		"balloon" : {
			"fixedPosition" : true
		},
		"legend" : {},
		"valueField" : "samount",
		"titleField" : "factoryName",
		"marginRight" : 240,
		"marginLeft" : 50,
		"startX" : -500,
		"depth3D" : 100,
		"angle" : 40,
		"outlineAlpha" : 1,
		"outlineColor" : "#FFFFFF",
		"outlineThickness" : 2,
		"labelPosition" : "right",
		"balloonText" : "[[factoryName]]: [[samount]]n[[description]]",
		"export" : {
			"enabled" : true
		}
	});
</script>
<body>
	<div id="chartdiv"></div>
</body>

</html>