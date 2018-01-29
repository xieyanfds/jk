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
  查看
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">创建人：</td>
	            <td class="tableContent">${createName}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">系统使用反馈：</td>
	            <td class="tableContent">${message}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">创建人id：</td>
	            <td class="tableContent">${createBy}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">创建人部门：</td>
	            <td class="tableContent">${createDept}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">创建时间：</td>
	            <td class="tableContent">${createTime}</td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

