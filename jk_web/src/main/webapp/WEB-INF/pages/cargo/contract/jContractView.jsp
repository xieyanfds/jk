<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>

<div class="eXtremeTable" >
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
			浏览购销合同
		</div>



		<div>
			<table class="commonTable" cellspacing="1">
				<tr>
					<td class="columnTitle">客户名称：</td>
					<td class="tableContent"><input type="text" name="customName" value="${customName }" readonly/></td>
					<td class="columnTitle">打印版式：</td>
					<td class="tableContentAuto">
						<select name="printStyle" disabled>
							<option value="2" ${printStyle=='2'?"checked":"" } >两款</option>
							<option value="1" ${printStyle=='1'?"checked":"" } >一款</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="columnTitle">合同号：</td>
					<td class="tableContent"><input type="text" name="contractNo" value="${contractNo }" readonly/></td>
					<td class="columnTitle">收购方：</td>
					<td class="tableContent"><input type="text" name="offeror" value="${offeror }" readonly/></td>
				</tr>
				<tr>
					<td class="columnTitle">制单人：</td>
					<td class="tableContent"><input type="text" name="inputBy" value="${inputBy }" readonly/></td>
					<td class="columnTitle">审单人：</td>
					<td class="tableContent"><input type="text" name="checkBy" value="${checkBy }" readonly/></td>
				</tr>
				<tr>
					<td class="columnTitle">验货员：</td>
					<td class="tableContent"><input type="text" name="inspector" value="${inspector }" readonly/></td>
					<td class="columnTitle">签单日期：</td>
					<td class="tableContent"><input type="text"  name="signingDate" value="${signingDate }" readonly/></td>
				</tr>
				<tr>
					<td class="columnTitle">重要程度：</td>
					<td class="tableContentAuto">
						<input type="radio" name="importNum" value="3" ${importNum==3?"checked":"" } class="input" readonly>★★★
						<input type="radio" name="importNum" value="2" ${importNum==2?"checked":"" } class="input" readonly>★★
						<input type="radio" name="importNum" value="1" ${importNum==1?"checked":"" } class="input" readonly>★
					</td>
					<td class="columnTitle">船期：</td>
					<td class="tableContent"><input type="text"  name="shipTime" value="${shipTime }" readonly/></td>
				</tr>
				<tr>
					<td class="columnTitle">贸易条款：</td>
					<td class="tableContent"><input type="text" name="tradeTerms" value="${tradeTerms }" readonly/></td>
					<td class="columnTitle">交货期限：</td>
					<td class="tableContent"><input type="text"  name="deliveryPeriod" value="${deliveryPeriod }" readonly/></td>
				</tr>
				<tr>
					<td class="columnTitle">要求：</td>
					<td class="tableContent"><input type="text" name="crequest" value="${crequest }" readonly/></td>
					<td class="columnTitle">说明：</td>
					<td class="tableContent"><input type="text" name="remark" value="${remark }" readonly/></td>
				</tr>
			</table>
		</div>


<br/><br/>
<div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	货物列表
</div>
	<table id="ec_table" class="tableRegion" width="98%" >
		<thead>
		<tr style="height: 42px;">
			<td class="tableHeader">序号</td>
			<td class="tableHeader">厂家</td>
			<td class="tableHeader">货号</td>
			<td class="tableHeader">装率</td>
			<td class="tableHeader">箱数</td>
			<td class="tableHeader">包装单位</td>
			<td class="tableHeader">数量</td>
			<td class="tableHeader">单价</td>
			<td class="tableHeader">总金额</td>
		</tr>
		</thead>
		<tbody class="tableBody" >

		<c:forEach items="${contractProducts}" var="cp" varStatus="status">
			<tr bgcolor="#c3f3c3" height="30" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
				<td>${status.index+1}</td>
				<td>${cp.factoryName}</td>
				<td>${cp.productNo}</td>
				<td>${cp.loadingRate}</td>
				<td>${cp.boxNum}</td>
				<td>${cp.packingUnit}</td>
				<td>${cp.cnumber}</td>
				<td>${cp.price}</td>
				<td>${cp.amount}</td>
			</tr>
				<c:forEach items="${cp.extCproducts}" var="ext" varStatus="status">
					<tr height="30" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
						<td align="right"><font color="blue">附件：${status.index+1}&nbsp;</font></td>
						<td>${ext.factoryName}</td>
						<td>${ext.productNo}</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>${ext.packingUnit}</td>
						<td>${ext.cnumber}</td>
						<td>${ext.price}</td>
						<td>${ext.amount}</td>
					</tr>
				</c:forEach>
		</c:forEach>

		</tbody>
	</table>
	</div>

</form>
</body>
</html>