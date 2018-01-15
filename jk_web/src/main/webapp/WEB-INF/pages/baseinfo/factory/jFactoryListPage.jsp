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
				<li id="view"><a href="#" onclick="formSubmit('factoryAction_toview','_self');this.blur();">查看</a></li>
				<li id="new"><a href="#" onclick="formSubmit('factoryAction_tocreate','_self');this.blur();">新增</a></li>
				<li id="update"><a href="#" onclick="formSubmit('factoryAction_toupdate','_self');this.blur();">修改</a></li>
				<li id="delete"><a href="#" onclick="formSubmit('factoryAction_delete','_self');this.blur();">删除</a></li>
			</ul>--%>
		  </div>
		</div>
	</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    厂家信息
  </div> 


<br/>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
		<tr>
			<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
			<td class="tableHeader">序号</td>
			<td class="tableHeader">编号</td>
			<td class="tableHeader">厂家简称</td>
			<td class="tableHeader">货物/附件</td>
			<td class="tableHeader">创建时间</td>
			<td class="tableHeader">厂家全称</td>
			<td class="tableHeader">联系人</td>
			<td class="tableHeader">电话</td>
			<td class="tableHeader">手机</td>
			<td class="tableHeader">传真</td>
			<td class="tableHeader">地址</td>
			<td class="tableHeader">验货员</td>
			<td class="tableHeader">说明</td>
			<td class="tableHeader">排序号</td>
			<td class="tableHeader">厂家状态</td>
		</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td><a href="factoryAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.id}</td>
		<td>${o.factoryName}</td>
		<td>${o.ctype}</td>
		<td><fmt:formatDate value="${o.createTime }" pattern="yyyy-MM-dd"/></td>
		<td>${o.fullName}</td>
		<td>${o.contacts}</td>
		<td>${o.phone}</td>
		<td>${o.mobile}</td>
		<td>${o.fax}</td>
		<td>${o.address}</td>
		<td>${o.inspector}</td>
		<td>${o.remark}</td>
		<td>${o.orderNo}</td>
		<td>
			<c:if test="${o.state==0}">停用</c:if>
			<c:if test="${o.state==1}">正常</c:if>
		</td>
	
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

