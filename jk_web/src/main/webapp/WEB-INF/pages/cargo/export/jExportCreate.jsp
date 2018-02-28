<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
   <!-- 保存的是购销合同的id,用逗号空格进行分隔 -->
   <input type="hidden" name="contractIds" value="${id }" />
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
	<ul>
	<li id="save"><a href="#" onclick="formSubmit('exportAction_insert','_self');this.blur();">保存</a></li>
	<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
	</ul>
  </div>
</div>
</div>
</div>
   
	<div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/step.png"/>
	新增出口报运
	</div>
  
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">信用证号</td>
	            <td class="tableContent"><input type="text" name="lcno" /></td>
	       
	            <td class="columnTitle">收货人及地址</td>
	            <td class="tableContent"><input type="text" name="consignee"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">唛头</td>
	            <td class="tableContent"><input type="text" name="marks"/></td>
	       
	            <td class="columnTitle">装运港</td>
	            <td class="tableContent"><input type="text" name="shipmentPort"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">目的港</td>
	            <td class="tableContent"><input type="text" name="destinationPort"/></td>
	       
	            <td class="columnTitle">运输方式</td>
	            <td class="tableContent"><input type="text" name="transportMode" /></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">价格条件</td>
	            <td class="tableContent"><input type="text" name="priceCondition"/></td>
	      
	            <td class="columnTitle">备注</td>
	            <td class="tableContent"><input type="text" name="remark" /></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">总箱数</td>
	            <td class="tableContent"><input type="text" name="boxNums"/></td>
	       
	            <td class="columnTitle">总毛重</td>
	            <td class="tableContent"><input type="text" name="grossWeights"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">体积</td>
	            <td class="tableContent"><input type="text" name="measurements"/></td>
	        </tr>	
	       
		</table>
	</div>

	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
		报运的购销合同列表
	</div>

	<br/>
	<div class="eXtremeTable" >
		<table id="ec_table" class="tableRegion" width="98%" >
			<thead>
			<tr style="height: 42px;">
				<td class="tableHeader"></td>
				<td class="tableHeader">序号</td>
				<td class="tableHeader">客户名称</td>
				<td class="tableHeader">合同号</td>
				<td class="tableHeader">货物数/附件数</td>
				<td class="tableHeader">制单人</td>
				<td class="tableHeader">审单人</td>
				<td class="tableHeader">验货员</td>
				<td class="tableHeader">签单日期</td>
				<td class="tableHeader">交货期限</td>
				<td class="tableHeader">船期</td>
				<td class="tableHeader">贸易条款</td>
				<td class="tableHeader">总金额</td>
			</tr>
			</thead>

			<tbody class="tableBody" >

			<c:forEach items="${results}" var="o" varStatus="status">
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
					<td></td>
					<td style="cursor: pointer;" onclick="statusToAction('contractAction_toview?id=${o.id}')"><a href="contractAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
					<td>${o.customName}</td>
					<td>
						<a href="contractAction_toview?id=${o.id}">${o.contractNo}</a>
					</td>
					<td align="center">
							${o.contractProducts.size()}
						/
						<c:set var="extNumber" value="0"></c:set><!-- 设置一个变量，用来累加，初始值0 -->
						<c:forEach items="${o.contractProducts}" var="cp">
							<c:set var="extNumber" value="${extNumber + cp.extCproducts.size()}"/>
						</c:forEach>
							${extNumber}
					</td>
					<td>${o.inputBy}</td>
					<td>${o.checkBy}</td>
					<td>${o.inspector}</td>
					<td><fmt:formatDate value="${o.signingDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${o.deliveryPeriod}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${o.shipTime}" pattern="yyyy-MM-dd"/></td>
					<td>${o.tradeTerms}</td>
					<td>${o.totalAmount}</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</div>
 
</form>
</body>
</html>

