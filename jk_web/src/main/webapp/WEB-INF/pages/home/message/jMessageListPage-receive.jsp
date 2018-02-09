<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layer/layer.js"></script>
<script type="text/javascript">

	function toView() {
		if (isOnlyChecked()) {
			formSubmit('messageAction_toview', '_self');
		} else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
		}
	}
	
</script>

<%@include file="../../alert.jsp"%>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="view"><a href="#" onclick="toView();this.blur();">查看</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/Alert_09.gif" width="32px;" height="32px;"/>
	我的留言板-已接收
</div>

<br/>
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">接收人</td>
		<td class="tableHeader">标题</td>
		<td class="tableHeader">时间</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >

	<c:forEach items="${results}" var="o" varStatus="status">
		<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
			<td><input type="checkbox" name="id" value="${o.id}"/></td>
			<td style="cursor: pointer;" onclick="statusToAction('messageAction_toview?id=${o.id}')"><a href="messageAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
			<td>${o.receive}</td>
			<td>${o.title}</td>
			<td>${o.messageTime}</td>
			<td>
				<c:if test="${o.state==1}"><font color="red"><b>未查看</b></font></c:if>
				<c:if test="${o.state==2}"><font color="green"><b>已查看</b></font></c:if>
			</td>

		</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
</div>
<%@include file="../../page.jsp"%>
</form>

</body>
</html>

