<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.4.4.js"></script>
</head>

<body>
<form name="icform" method="post">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
  <%@include file="/WEB-INF/pages/button.jsp" %>
	<%--<ul>
	<li id="view"><a href="javascript:toView()">查看</a></li>
	<li id="new"><a href="#" onclick="formSubmit('userAction_tocreate','_self');this.blur();">新增</a></li>
	<li id="update"><a href="#" onclick="javascript:toUpdate()">修改</a></li>
	<li id="people"><a href="#" onclick="javascript:torole()">角色</a></li>
	<li id="delete"><a href="#" onclick="javascript:todelete()">删除</a></li>
	</ul>--%>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/users4.png"/>
	用户列表
  </div> 
  </div>
  </div>
<div>

<br/>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">用户名</td>
		<td class="tableHeader">所在部门</td>
		<td class="tableHeader">等级</td>
		<td class="tableHeader">电话</td>
		<td class="tableHeader">入职时间</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >

	<c:forEach items="${results}" var="o" varStatus="status">
		<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
			<td><input type="checkbox" name="id" value="${o.id}"/></td>
			<td style="cursor: pointer;" onclick="statusToAction('userAction_toview?id=${o.id}')"><a href="userAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
			<td>${o.userName}</td>
			<td>${o.dept.deptName}</td>
			<td>${o.userInfo.degree==0?'超级管理员':o.userInfo.degree==1?'跨部门跨人员':o.userInfo.degree==2?'管理所有下属部门和人员':o.userInfo.degree==3?'管理本部门':'普通员工'}</td>
			<td>${o.userInfo.telephone}</td>
			<td><fmt:formatDate value='${o.userInfo.joinDate}' pattern="yyyy-MM-dd"/></td>
			<td>${o.state==1?"<font color='green'>启用</font>":"<font color='red'>已停用</font>"}</td>
		</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
</div>
</div>
	<%@include file="../../page.jsp"%>
</form>
</body>
</html>

