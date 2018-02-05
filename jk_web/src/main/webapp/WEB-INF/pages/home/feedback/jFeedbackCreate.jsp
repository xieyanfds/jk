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
<li id="save"><a href="#" onclick="formSubmit('feedbackAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增意见反馈
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">标题:：</td>
	            <td class="tableContent"><input type="text" name="title" value="" style="width:500px;"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent">
		            <select name="classType">
		            	<option value="1">管理</option>
		            	<option value="2">安全</option>
		            	<option value="3">建议</option>
		            	<option value="4">其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value="" style="width:500px;"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent">
	            	<select name="isShare">
		            	<option value="0">不公开</option>
		            	<option value="1">公开</option>
	            	</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea name="content" style="height:150px;width: 800px"></textarea></td>
	        </tr>	
		</table>
	</div>
 
</form>
</body>
</html>

