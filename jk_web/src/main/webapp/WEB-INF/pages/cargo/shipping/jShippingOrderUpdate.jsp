<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="packingList.id" value="${id}"/>

	<div id="menubar">
	<div id="middleMenubar">
	<div id="innerMenubar">
	  <div id="navMenubar">
		<ul>
			<li id="save"><a href="#" onclick="formSubmit('shippingOrderAction_update','_self');this.blur();">保存</a></li>
			<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
		</ul>
	  </div>
	</div>
	</div>
	</div>
   
	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/screen.png"/>
		修改委托单
	</div>



	<div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">运输方式：</td>
				<td class="tableContentAuto">
					<select name="orderType">
						<option value="海运" ${orderType=='海运'?"selected":"" } >海运</option>
						<option value="空运" ${orderType=='空运'?"selected":"" } >空运</option>
					</select>
				</td>

				<td class="columnTitle">货主：</td>
				<td class="tableContent"><input type="text" name="shipper" value="${shipper}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">提单抬头：</td>
				<td class="tableContent"><input type="text" name="consignee" value="${consignee}" /></td>

				<td class="columnTitle">正本通知人：</td>
				<td class="tableContent"><input type="text" name="notifyParty" value="${notifyParty}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">信用证：</td>
				<td class="tableContent"><input type="text" name="lcNo" value="${lcNo}" /></td>

				<td class="columnTitle">装运港：</td>
				<td class="tableContent"><input type="text" name="portOfLoading" value="${portOfLoading}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">转船港：</td>
				<td class="tableContent"><input type="text" name="portOfTrans" value="${portOfTrans}" /></td>

				<td class="columnTitle">卸货港：</td>
				<td class="tableContent"><input type="text" name="portOfDischarge" value="${portOfDischarge}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">装期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="loadingDate"
						   value="${loadingDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" />
				</td>
				<td class="columnTitle">效期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="limitDate"
						   value="${limitDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" />
				</td>
			</tr>
			<tr>
				<td class="columnTitle">是否分批：</td>
				<td class="tableContentAuto">
					<select name="isBatch">
						<option value="1" ${isBatch=='1'?"selected":"" }>是</option>
						<option value="0" ${isBatch=='0'?"selected":"" }>否</option>
					</select>
				</td>

				<td class="columnTitle">是否转船：</td>
				<td class="tableContentAuto">
					<select name="isBatch">
						<option value="1" ${isTrans=='1'?"selected":"" }>是</option>
						<option value="0" ${isTrans=='0'?"selected":"" }>否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">份数：</td>
				<td class="tableContent"><input type="text" name="copyNum" value="${copyNum}" /></td>

				<td class="columnTitle">扼要说明：</td>
				<td class="tableContent"><input type="text" name="remark" value="${remark}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">运输要求：</td>
				<td class="tableContent"><input type="text" name="specialCondition" value="${specialCondition}" /></td>

				<td class="columnTitle">运费说明：</td>
				<td class="tableContent"><input type="text" name="freight" value="${freight}" /></td>
			</tr>
			<tr>
				<td class="columnTitle">复核人：</td>
				<td class="tableContent"><input type="text" name="checkBy" value="${checkBy}" /></td>

				<td class="columnTitle">正本通知人：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="invoiceDate"
						   value="${invoiceDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" />
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
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" align="center" style="height: 42px;">
					<td><input type="radio" name="id" value="${o.id}" <c:if test="${id==o.id}">checked</c:if> /></td>
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

