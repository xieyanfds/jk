<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../baselist.jsp"%>
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
  
<ul>
<!-- http://localhost/jk_web/workFlowAction_list.action -->

<li id="view"><a href="#" onclick="formSubmit('workFlowAction_toview','_self');this.blur();">查看</a></li>
<!-- <li id="view"><a href="#" onclick="formSubmit('feedbackAction_toAco','_self');this.blur();">指派</a></li>
<li id="new"><a href="#" onclick="formSubmit('feedbackAction_tocreate','_self');this.blur();">新增</a></li> -->
<li id="submit"><a href="#" onclick="formSubmit('workFlowAction_toupdate','_self');this.blur();">解决</a></li>
<!-- <li id="submit"><a href="#" onclick="formSubmit('feedbackAction_submit','_self');this.blur();"></a></li> -->
<!-- <li id="new"><a href="#" onclick="formSubmit('feedbackAction_cancel','_self');this.blur();">取消</a></li>  -->
</ul>
  </div>
</div>
</div>
</div>
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    我的任务列表
  </div> 
<div>

<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">提出人</td>
		<td class="tableHeader">提出时间</td>
		<td class="tableHeader">标题</td>
		<td class="tableHeader">重要程度</td>
		<td class="tableHeader">联系电话</td>
		<td class="tableHeader">解决人</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		
		<td>${o.inputBy}</td>
		<td>${o.inputTime}</td>
		<td>${o.title}</td>
		<td>
			<c:if test="${o.classType==0 }">普通</c:if>
		    <c:if test="${o.classType==1 }">重要</c:if>
		    <c:if test="${o.classType==2 }"><font color="red">紧急</font></c:if>
		</td>
		<td>${o.tel}</td>
		<td>${o.answerBy}</td>
		<td>
			<c:if test="${o.state==0 }">草稿</c:if>
		    <c:if test="${o.state==1 }">已上报</c:if>
		    <c:if test="${o.state==2 }">待解决</c:if>
		    <c:if test="${o.state==3 }">已解决</c:if>
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

