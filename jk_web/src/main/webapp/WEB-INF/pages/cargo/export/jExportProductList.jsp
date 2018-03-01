<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
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
		<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
	</ul>
  </div>
</div>
</div>
</div>

<div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	报运单下货物列表
</div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr style="height: 42px;">
		<td class="tableHeader"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">厂家</td>
		<td class="tableHeader">货号</td>
		<td class="tableHeader">箱数</td>
		<td class="tableHeader">包装单位</td>
		<td class="tableHeader">数量</td>
		<td class="tableHeader">单价</td>
		<td class="tableHeader">含税</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
 	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="even" onmouseover="this.className='highlight'" onmouseout="this.className='even'" style="height: 42px;">
		<td></td>
		<td style="cursor: pointer;" onclick="statusToAction('exportProductAction_toview?id=${o.id}')"><a href="exportProductAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.factory.factoryName}</td>
		<td>${o.productNo}</td>
		<td>${o.boxNum}</td>
		<td>
			<c:if test="${o.packingUnit=='PCS'}">只</c:if>
			<c:if test="${o.packingUnit=='SETS'}">套</c:if>
		</td>
		<td>${o.cnumber}</td>
		<td>${o.price}</td>
		<td>${o.tax}</td>
	</tr>
	
		<c:forEach items="${o.extEproducts}" var="ext" varStatus="status">
			<tr style="height: 42px;" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
				<td>&nbsp;</td>
				<td align="right" onclick="statusToAction('extEproductAction_toview?id=${ext.id}')"><font color="blue"><a href="extEproductAction_toview?id=${ext.id}">附件：${status.index+1}&nbsp;</a></font></td>
				<td>${ext.factory.factoryName}</td>
				<td>${ext.productNo}</td>
				<td>&nbsp;</td>
				<td>
					<c:if test="${o.packingUnit=='PCS'}">只</c:if>
					<c:if test="${o.packingUnit=='SETS'}">套</c:if>
				</td>
				<td>${ext.cnumber}</td>
				<td>${ext.price}</td>
				<td>&nbsp;</td>
			</tr>
		</c:forEach>
	
	</c:forEach> 
	
	</tbody>
</table>
</div>
	<%@include file="../../page.jsp"%>
</form>

</body>
</html>

