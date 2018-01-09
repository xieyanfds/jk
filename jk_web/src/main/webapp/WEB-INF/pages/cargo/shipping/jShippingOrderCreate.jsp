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
		<li id="save"><a href="#" onclick="formSubmit('shippingOrderAction_insert','_self');this.blur();">保存</a></li>
		<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
	</ul>
  </div>
</div>
</div>
</div>
   
	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
		新增委托单
	</div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContentAuto">
					<input type="radio" name="orderType" value="海运" checked class="input">海运
					<input type="radio" name="orderType" value="空运" class="input">空运
				</td>
	        
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent"><input type="text" name="shipper" value=""/></td>
	        </tr>
			<tr>
				<td class="columnTitle">提单抬头：</td>
				<td class="tableContent"><input type="text" name="consignee" value=""/></td>

				<td class="columnTitle">正本通知人：</td>
				<td class="tableContent"><input type="text" name="notifyParty" value=""/></td>
			</tr>
			<tr>
				<td class="columnTitle">信用证：</td>
				<td class="tableContent"><input type="text" name="lcNo" value=""/></td>

				<td class="columnTitle">装运港：</td>
				<td class="tableContent"><input type="text" name="portOfLoading" value=""/></td>
			</tr>
			<tr>
				<td class="columnTitle">转船港：</td>
				<td class="tableContent"><input type="text" name="portOfTrans" value=""/></td>

				<td class="columnTitle">卸货港：</td>
				<td class="tableContent"><input type="text" name="portOfDischarge" value=""/></td>
			</tr>
			<tr>
				<td class="columnTitle">装期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="loadingDate"
						   value=""
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
				<td class="columnTitle">效期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="limitDate"
						   value=""
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">是否分批：</td>
				<td class="tableContentAuto">
					<input type="radio" name="isBatch" value="1" checked class="input">是
					<input type="radio" name="isBatch" value="0" class="input">否
				</td>

				<td class="columnTitle">是否转船：</td>
				<td class="tableContentAuto">
					<input type="radio" name="isTrans" value="1" checked class="input">是
					<input type="radio" name="isTrans" value="0" class="input">否
				</td>
			</tr>
			<tr>
				<td class="columnTitle">份数：</td>
				<td class="tableContent"><input type="text" name="copyNum" value=""/></td>

				<td class="columnTitle">扼要说明：</td>
				<td class="tableContent"><input type="text" name="remark" value=""/></td>
			</tr>
			<tr>
				<td class="columnTitle">运输要求：</td>
				<td class="tableContent"><input type="text" name="specialCondition" value=""/></td>

				<td class="columnTitle">运费说明：</td>
				<td class="tableContent"><input type="text" name="freight" value=""/></td>
			</tr>
	        <tr>
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent"><input type="text" name="checkBy" value=""/></td>
	        
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="invoiceDate"
						 value=""
						onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
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
			<tr align="center">
				<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
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
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" align="center">
					<td><input type="checkbox" name="id" value="${o.id}"/></td>
					<td>${status.index+1}</td>
					<td>${o.seller}</td>
					<td>${o.buyer}</td>
					<td>${o.invoiceNo}</td>
					<td>${o.invoiceDate}</td>
					<td>
						<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
						<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
						<c:if test="${o.state==2}"><font color="#00bfff">已委托</font></c:if>
					</td>
				</tr>
			</c:forEach>

			</tbody>
		</table>
	</div>

</form>
</body>
</html>

