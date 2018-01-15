<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
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
   浏览任务
  </div>

 
     <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">提出人：</td>
	            <td class="tableContent">${inputBy}</td>
	        
	            <td class="columnTitle">提出时间：</td>
	            <td class="tableContent">${inputTime}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent">${title}</td>
	        
	            <td class="columnTitle">重要级别：</td>
	            <td class="tableContent">
			<c:if test="${classType==0 }">普通</c:if>
		    <c:if test="${classType==1 }">重要</c:if>
		    <c:if test="${classType==2 }"><font color="red">紧急</font></c:if>
		</td>
	        </tr>	
	        
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">${tel}</td>
	       
	            <td class="columnTitle">解决人：</td>
	            <td class="tableContent">${answerBy}</td>
	        </tr>	
			  <td class="columnTitle">解决时间：</td>
			    <td class="tableContent">
			  ${answerTime}</td>
	        	</tr>
	          <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">${tel}</td>
	            <td class="columnTitle">解决方法：</td>
	            <td class="tableContent">${solveMethod}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent">${resolution}</td>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">
	         ${difficulty}
	           </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否分享：</td>
	            <td class="tableContent">
	            
	            ${isShare}
	            
	      		</td>
	        </tr>	






	        
	        
	        
	        
	        
		</table>
	</div>
</form>
</body>
</html>

