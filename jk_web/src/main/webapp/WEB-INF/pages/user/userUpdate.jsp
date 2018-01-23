<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
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
			<li id="save"><a href="#" onclick="formSubmit('ownUserAction_update','_parent');this.blur();">保存</a></li>
			<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
			</ul>
		  </div>
		</div>
	</div>
</div>
   

    <div>
		<table class="commonTable" cellspacing="1">
	       
	        <tr>
	            <td class="columnTitle">用户名：</td>
	            <td class="tableContent"><input type="text" name="userName" value="${userName }" readonly="readonly"/></td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">请输入旧密码：</td>
	            <td class="tableContentAuto">
	              <input type="text" name="oldPassword" value=""> 
	            </td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">请输入新密码：</td>
	            <td class="tableContentAuto">
	              <input type="text" name="newPassword" value=""> 
	            </td>
	        </tr>			
	         <tr>
	            <td class="columnTitle">确认新密码：</td>
	            <td class="tableContentAuto">
	              <input type="text" name="checkPassword" value=""> 
	            </td>
	        </tr>			
		</table>
	</div>
 </form>
</body>
</html>