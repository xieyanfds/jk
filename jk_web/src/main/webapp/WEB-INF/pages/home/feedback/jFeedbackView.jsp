<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   浏览部门
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">提出人:：</td>
	            <td class="tableContent">${inputBy}</td>
	            <td class="columnTitle">提出时间：</td>
	            <td class="tableContent">${inputTime}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent">${title}</td>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent">${classType==1?"管理":classType==2?"安全":classType==3?"建议":"其他"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">${tel}</td>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent">${isShare==0?"不公开":"公开"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea style="height:150px;width: 500px" disabled="disabled">${content}</textarea></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决人：</td>
	            <td class="tableContent">${answerBy}</td>
	            <td class="columnTitle">解决时间：</td>
	            <td class="tableContent">${answerTime}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent">${o.resolution==1?"已修改":o.resolution==2?"无需修改":resolution==3?"重复问题":resolution==4?"描述不完整":resolution==5?"无法再现":resolution==6?"其他":""}</td>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">${o.difficulty==1?"极难":difficulty==2?"比较难":difficulty==3?"有难度":difficulty==4?"一般":""}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">${state==0?"<font color='red'>未处理</font>":"<font color='green'>已处理</font>"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决办法：</td>
	            <td class="tableContent"><textarea  style="height:150px;width: 500px" disabled="disabled">${solveMethod}</textarea></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

