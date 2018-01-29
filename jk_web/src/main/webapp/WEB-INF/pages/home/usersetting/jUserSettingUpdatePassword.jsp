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
<li id="work_assign"><a href="#" onclick="if (confirm('确定修改吗?')){formSubmit('userSettingAction_updatePassword','_self');this.blur();}">保存修改</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
修改密码
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
			
	        <tr>
	            <td class="columnTitle">原始密码：</td>
	            <td class="tableContent"><input type="password" name="passwordOld" value=""/></td>
	            <td class="columnTitle" style="width:120px;color:red;">${errorInfo1 }${errorInfo4 }</td>
	            <td class="tableContent"><input type="hidden" /></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">新密码：</td>
	            <td class="tableContent"><input type="password" name="password" value=""/></td>
	            <td class="columnTitle" style="width:120px;color:red;">${errorInfo2 }${errorInfo5 }</td>
	            <td class="tableContent"><input type="hidden" /></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">再次输入新密码：</td>
	            <td class="tableContent"><input type="password" name="passwordNew" value=""/></td>
	            <td class="columnTitle" style="width:120px;color:red;">${errorInfo5 }${errorInfo3 }</td>
	            <td class="tableContent"><input type="hidden" /></td>
	        </tr>	
		</table>
	</div>
 </form>
</body>
</html>