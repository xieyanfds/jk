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
	  <img src="${ctx }/skin/default/images/icon/address_book.png"/>
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
	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
		选择装箱单
	</div>
	<div class="eXtremeTable" >
		<table id="ec_table" class="tableRegion" width="98%" >
			<thead>
			<tr align="center" style="height: 42px;">
				<td class="tableHeader"></td>
				<td class="tableHeader">序号</td>
				<td class="tableHeader">卖方</td>
				<td class="tableHeader">买方</td>
				<td class="tableHeader">发票号</td>
				<td class="tableHeader">发票日期</td>
				<td class="tableHeader">状态</td>
			</tr>
			</thead>
			<tbody class="tableBody" >
			${links}

			<c:forEach items="${results}" var="o" varStatus="status">
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
					<td><input type="radio" name="id" value="${o.id}"/></td>
					<td>${status.index+1}</td>
					<td>${o.seller}</td>
					<td>${o.buyer}</td>
					<td>${o.invoiceNo}</td>
					<td>${o.invoiceDate}</td>
					<td>
						<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
						<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
						<c:if test="${o.state==2}"><font color="#00bfff">已委托</font></c:if>
						<c:if test="${o.state==3}"><font color="rgb(92, 37, 177)">已开发票</font></c:if>
						<c:if test="${o.state==4}"><font color="rgb(0, 96, 0)">已报账</font></c:if>
					</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</div>
 
</form>
</body>
</html>