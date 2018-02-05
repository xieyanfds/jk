<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		  <%@include file="/WEB-INF/pages/button.jsp" %>
			<%--<ul>
				<li id="view"><a href="#" onclick="javascript:toView()">查看</a></li>
				<li id="new"><a href="#" onclick="formSubmit('moduleAction_tocreate','_self');this.blur();">新增</a></li>
				<li id="update"><a href="#" onclick="javascript:toUpdate()">修改</a></li>
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
    模块列表
  </div> 
  </div>
  </div>
</div>

<br/>
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">模块名</td>
		<td class="tableHeader">层数</td>
		<td class="tableHeader">权限标识</td>
		<td class="tableHeader">链接</td>
		<td class="tableHeader">类型</td>
		<td class="tableHeader">从属</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${links }
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td style="cursor: pointer;" onclick="statusToAction('moduleAction_toview?id=${o.id}')"><a href="moduleAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.name}</td>
		<td>${o.layerNum}</td>
		<td>${o.cpermission}</td>
		<td>${o.curl}</td>
		<td>${o.ctype}</td>
		<td>${o.belong}</td>
		<td>${o.state==1?"启用":"已停用"}</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

