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
<li id="view"><a href="#" onclick="formSubmit('shippingOrderAction_toview','_self');this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('shippingOrderAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="formSubmit('shippingOrderAction_toupdate','_self');this.blur();">修改</a></li>
<li id="delete"><a href="#" onclick="formSubmit('shippingOrderAction_delete','_self');this.blur();">删除</a></li>
<li id="submit"><a href="#" onclick="formSubmit('shippingOrderAction_submit','_self');this.blur();">提交</a></li>
<li id="cancel"><a href="#" onclick="formSubmit('shippingOrderAction_cancel','_self');this.blur();">取消</a></li>
<li id="print"><a href="#" onclick="formSubmit('shippingOrderAction_print','_self');this.blur();">打印</a></li>
</ul>--%>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/screen.png"/>
    委托单列表
  </div> 
  
<div>

<br/>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr align="center">
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">货主</td>
		<td class="tableHeader">提单抬头</td>
		<td class="tableHeader">正本通知人</td>
		<td class="tableHeader">信用证</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">转船港</td>
		<td class="tableHeader">卸货港</td>
		<td class="tableHeader">装期</td>
		<td class="tableHeader">效期</td>
		<td class="tableHeader">是否分批</td>
		<td class="tableHeader">是否转船</td>
		<td class="tableHeader">份数</td>
		<td class="tableHeader">扼要说明</td>
		<td class="tableHeader">运输要求</td>
		<td class="tableHeader">运费说明</td>
		<td class="tableHeader">复核人</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" align="center">
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td style="cursor: pointer;" onclick="statusToAction('shippingOrderAction_toview?id=${o.id}')"><a href="shippingOrderAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.orderType}</td>
		<td>${o.shipper}</td>
		<td>${o.consignee}</td>
		<td>${o.notifyParty}</td>
		<td>${o.lcNo}</td>
		<td>${o.portOfLoading}</td>
		<td>${o.portOfTrans}</td>
		<td>${o.portOfDischarge}</td>
		<td>${o.loadingDate}</td>
		<td>${o.limitDate}</td>
		<td>
			<c:if test="${o.isBatch==1}">是</c:if>
			<c:if test="${o.isBatch==0}">不是</c:if>
		</td>
		<td>
			<c:if test="${o.isTrans==1}">是</c:if>
			<c:if test="${o.isTrans==0}">不是</c:if>
		</td>
		<td>${o.copyNum}</td>
		<td>${o.remark}</td>
		<td>${o.specialCondition}</td>
		<td>${o.freight}</td>
		<td>${o.checkBy}</td>
		<td>
			<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
			<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
			<c:if test="${o.state==2}"><font color="#00bfff">已报账</font></c:if>
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