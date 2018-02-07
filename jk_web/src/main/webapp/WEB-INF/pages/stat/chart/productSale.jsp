<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/6
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    #chartdiv {
        width: 1056px;
        height: 500px;
    }
</style>

<!-- Resources -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/components/amchartNew/amcharts/plugins/export/export.css" type="text/css" media="all">
<script src="${pageContext.request.contextPath }/components/amchartNew/amcharts/amcharts.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/components/amchartNew/amcharts/serial.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath }/components/amchartNew/amcharts/plugins/export/export.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/components/amchartNew/amcharts/themes/dark.js" type="text/javascript"></script>

<!-- Chart code -->
<script>
    var chart = AmCharts.makeChart("chartdiv", {
        "theme": "none",
        "type": "serial",
        "startDuration": 2,
        "dataProvider": ${result}/*[{
            "country": "USA",
            "visits": 4025,
            "color": "#FF0F00"
        },  {
            "country": "Poland",
            "visits": 328,
            "color": "#000000"
        }],*/,
        "valueAxes": [{
            "position":"left",
            "title": "产品销售排行"
        }],
        "graphs": [{
            "balloonText": "[[category]]: <b>[[value]]</b>",
            "fillColorsField": "color",
            "fillAlphas": 1,
            "lineAlpha": 0.1,
            "type": "column",
            "valueField": "samount"
        }],
        "depth3D": 20,
        "angle": 30,
        "chartCursor": {
            "categoryBalloonEnabled": false,
            "cursorAlpha": 0,
            "zoomable": false
        },
        "categoryField": "product_no",
        "categoryAxis": {
            "gridPosition": "start",
            "labelRotation": 90
        },
        "export": {
            "enabled": true,
            "position" : "center",

        }

    });
</script>

<!-- HTML -->
<div id="chartdiv"></div>
</body>
</html>
