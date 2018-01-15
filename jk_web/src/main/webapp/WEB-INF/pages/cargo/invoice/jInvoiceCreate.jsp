<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
	
	<!-- 尝试改造 -->
    <script type="text/javascript" src="${ctx}/components/jquery-ui/jquery-1.2.6.js"></script>
    <script type="text/javascript" src="${ctx}/js/tabledo.js"></script>	
	<script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>

</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('invoiceAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
	<div class="textbox-title">
	  <img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	  新增发票
	</div>
  

	<div>
		<table class="commonTable" cellspacing="1">

			<tr>
			   <!--  <td class="columnTitle">scNo：</td>
				<td class="tableContent"><input type="text" name="scNo" value=""/></td> -->

				<td class="columnTitle">提单号：</td>
				<td class="tableContent"><input type="text" name="blNo" value=""/></td>
			</tr>
			<tr>
				<td class="columnTitle">交易条款：</td>
				<td class="tableContent"><input type="text" name="tradeTerms" value=""/></td>

			</tr>


		</table>
	</div>

<br/><br/>
<div class="eXtremeTable" >
		<table id="ec_table" class="tableRegion" width="98%" >
			<thead>
			<tr align="center" style="height: 42px;">
				<td class="tableHeader"></td>
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
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
					<td><input type="radio" name="id" value="${o.id}"/></td>
					<td><a href="shippingOrderAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
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
 
</form>
</body>
</html>