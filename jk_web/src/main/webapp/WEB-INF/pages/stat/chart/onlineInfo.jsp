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
			src="${pageContext.request.contextPath }/components/amchartNew/amcharts/serial.js"
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
			width: 1200px;
			height: 500px;
		}
	</style>

	<script type="text/javascript">
        var chartData = ${result};

        var chart = AmCharts.makeChart( "chartdiv", {
            "type": "serial",
            "marginTop": 30,
            "dataProvider": chartData,
            "categoryField": "time",
            "categoryAxis": {
                "parseDates": false,
                "gridAlpha": 0.15,
                "minorGridEnabled": true,
                "axisColor": "#DADADA"
            },
            "valueAxes": [ {
                "axisAlpha": 0.2,
                "id": "v1"
            } ],
            "graphs": [ {
                "title": "red line",
                "id": "g1",
                "valueAxis": "v1",
                "valueField": "amount",
                "bullet": "round",
                "bulletBorderColor": "#FFFFFF",
                "bulletBorderAlpha": 1,
                "lineThickness": 2,
                "lineColor": "#b5030d",
                "negativeLineColor": "#0352b5",
                "balloonText": "[[category]]<br><b><span style='font-size:14px;'>value: [[value]]</span></b>"
            } ],
            "chartCursor": {
                "fullWidth": true,
                "cursorAlpha": 0.1
            },
            "chartScrollbar": {
                "scrollbarHeight": 40,
                "color": "#FFFFFF",
                "autoGridCount": true,
                "graph": "g1"
            },
            "mouseWheelZoomEnabled": true,
            "export": {
                "enabled": true,
                "position" : "top-left"
            }
        } );

        chart.addListener( "dataUpdated", zoomChart );

        // this method is called when chart is first inited as we listen for "dataUpdated" event
        function zoomChart() {
            // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
            chart.zoomToIndexes( chartData.length - 40, chartData.length - 1 );
        }
	</script>
</head>
<body>
<div id="chartdiv"></div>
</body>
</html>