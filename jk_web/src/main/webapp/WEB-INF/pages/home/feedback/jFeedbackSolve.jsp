<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('feedbackAction_solve','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   解决意见反馈
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input type="text" name="title" value="${title}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
		            <select name="classType" disabled="disabled">
		            	<option value="1" <c:if test="${classType==1 }">selected</c:if>>管理</option>
		            	<option value="2" <c:if test="${classType==2 }">selected</c:if>>安全</option>
		            	<option value="3" <c:if test="${classType==3 }">selected</c:if>>建议</option>
		            	<option value="4" <c:if test="${classType==4 }">selected</c:if>>其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value="${tel}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea name="content" style="height:150px;width: 800px" disabled="disabled">${content}</textarea></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	             <td class="tableContent">
	            	<select name="isShare">
		            	<option value="0" <c:if test="${isShare==0 }">selected</c:if>>不公开</option>
		            	<option value="1" <c:if test="${isShare==1 }">selected</c:if>>公开</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent">
		            <select name="resolution">
		            	<option value="1" >已修改</option>
		            	<option value="2" >无需修改</option>
		            	<option value="3" >重复问题</option>
		            	<option value="4" >描述不完整</option>
		            	<option value="5" >无法再现</option>
		            	<option value="6" >其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">
		            <select name="difficulty">
		            	<option value="1" >极难</option>
		            	<option value="2" >比较难</option>
		            	<option value="3" >有难度</option>
		            	<option value="4" >一般</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决办法：</td>
	             <td class="tableContent"><textarea name="solveMethod" style="height:150px;width: 800px">${solveMethod}</textarea></td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>
</body>
</html>

