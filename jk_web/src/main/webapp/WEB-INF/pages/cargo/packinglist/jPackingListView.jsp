<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">

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
	<img src="${ctx }/skin/default/images/icon/index.png"/>
   浏览装箱单
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	       
	        <tr>
	            <td class="columnTitle">卖方：</td>
	            <td class="tableContent"><input type="text" name="seller" value="${seller}" readonly/></td>
	       
	            <td class="columnTitle">买方：</td>
	            <td class="tableContent"><input type="text" name="buyer" value="${buyer}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">发票号：</td>
	            <td class="tableContent"><input type="text" name="invoiceNo" value="${invoiceNo}" readonly/></td>
	        
	            <td class="columnTitle">发票日期：</td>
	            <td class="tableContent"><input type="text" name="invoiceDate" value="${invoiceDate}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">唛头：</td>
	            <td class="tableContent"><input type="text" name="marks" value="${marks}" readonly/></td>
	       
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent"><input type="text" name="descriptions" value="${descriptions}" readonly/></td>
	        </tr>	
	        
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">
					<input type="text" name="state" value="<c:if test="${state==0}">草稿</c:if><c:if test="${state==1}">已提交</c:if><c:if test="${state==2}">已委托</c:if>" readonly/>
	            </td>
	        </tr>	
	       
		</table>
	</div>

	<br/><br/>
	<div class="eXtremeTable" >
		<table id="ec_table" class="tableRegion" width="98%" >
			<thead>
			<tr style="height: 42px;">
				<td class="tableHeader">序号</td>
				<td class="tableHeader">报运号</td>
				<td class="tableHeader">货物数/附件数</td>
				<td class="tableHeader">信用证号</td>
				<td class="tableHeader">收货人及地址</td>
				<td class="tableHeader">装运港</td>
				<td class="tableHeader">目的港</td>
				<td class="tableHeader">运输方式</td>
				<td class="tableHeader">价格条件</td>
				<td class="tableHeader">制单日期</td>
				<td class="tableHeader">状态</td>
			</tr>
			</thead>
			<tbody class="tableBody" >

			<c:forEach items="${results}" var="o" varStatus="status">
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" style="height: 42px;">
					<td>${status.index+1}</td>
					<td>${o.id}</td>
					<td align="center">
							${o.exportProducts.size()}
						/
						<c:set var="extNumber" value="0"></c:set><!-- 设置一个变量，用来累加，初始值0 -->
						<c:forEach items="${o.exportProducts}" var="ep">
							<c:if test="${ep.extEproducts.size()!=0 }">
								<c:set var="extNumber" value="${extNumber + ep.extEproducts.size()}"/>
							</c:if>
						</c:forEach>
							${extNumber}
					</td>
					<td>${o.lcno}</td>
					<td>${o.consignee}</td>
					<td>${o.shipmentPort}</td>
					<td>${o.destinationPort}</td>
					<td>${o.transportMode}</td>
					<td>${o.priceCondition}</td>
					<td><fmt:formatDate value="${o.inputDate }" pattern="yyyy-MM-dd"/></td>
					<td>
						<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
						<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
						<c:if test="${o.state==2}"><font color="#00bfff">已电子报运</font></c:if>
						<c:if test="${o.state==3}"><font color="#00ff7f">已装船</font></c:if>
					</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</div>

</form>
</body>
</html>

