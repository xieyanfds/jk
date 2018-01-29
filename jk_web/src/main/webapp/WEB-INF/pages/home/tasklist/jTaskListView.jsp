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
   浏览代办任务
  </div>
  

 
    <div>
		<table class="commonTable"   cellspacing="1">
	        <tr>
	            <td class="columnTitle" style="border-color: black" >发布者：</td>
	            <td class="tableContent" style="border-color: black">${pusherName}</td>
	        
	            <td class="columnTitle" style="border-color: black">执行者：</td>
	            <td  class="tableContent"  style="border-color: black">${userName}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle" style="border-color: black">任务内容：</td>
	            <td class="tableContent" style="border-color: black">
	          	 <%--  ${content} --%>
	           	 <textarea name="content"  style="height:100px;width: 100px">${content}</textarea>
	            </td>
	        	
	        	
	            <td class="columnTitle" style="border-color: black">重要程度：</td>
	            <td class="tableContent" style="border-color: black">${major}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle" style="border-color: black">发布日期：</td>
	            <td class="tableContent" style="border-color: black">${pushDate}</td>
	        	
	            <td class="columnTitle" style="border-color: black">任务截止日期：</td>
	            <td class="tableContent" style="border-color: black">${endDate}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle" style="border-color: black">状态：</td>
	            <td class="tableContent" style="border-color: black">${state==0?"未完成":"已完成"}</td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

