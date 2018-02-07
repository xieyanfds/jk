<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
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
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/screen.png"/>
	浏览委托单
  </div>



	<div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">运输方式：</td>
				<td class="tableContentAuto">
					<select name="orderType" disabled>
						<option value="海运" <c:if test="${orderType=='海运'}">selected</c:if> >海运</option>
						<option value="空运"<c:if test="${orderType=='空运'}">selected</c:if> >空运</option>
					</select>
					<%--<input type="radio" name="orderType" value="海运" class="input"  <c:if test="${orderType=='海运'}">checked</c:if> readonly />海运
					<input type="radio" name="orderType" value="空运" class="input"  <c:if test="${orderType=='空运'}">checked</c:if> readonly>空运--%>
				</td>

				<td class="columnTitle">货主：</td>
				<td class="tableContent"><input type="text" name="shipper" value="${shipper}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">提单抬头：</td>
				<td class="tableContent"><input type="text" name="consignee" value="${consignee}" readonly/></td>

				<td class="columnTitle">正本通知人：</td>
				<td class="tableContent"><input type="text" name="notifyParty" value="${notifyParty}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">信用证：</td>
				<td class="tableContent"><input type="text" name="lcNo" value="${lcNo}" readonly/></td>

				<td class="columnTitle">装运港：</td>
				<td class="tableContent"><input type="text" name="portOfLoading" value="${portOfLoading}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">转船港：</td>
				<td class="tableContent"><input type="text" name="portOfTrans" value="${portOfTrans}" readonly/></td>

				<td class="columnTitle">卸货港：</td>
				<td class="tableContent"><input type="text" name="portOfDischarge" value="${portOfDischarge}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">装期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="loadingDate"
						   value="${loadingDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" readonly/>
				</td>
				<td class="columnTitle">效期：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="limitDate"
						   value="${limitDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" readonly/>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">是否分批：</td>
				<td class="tableContentAuto">
					<select name="isBatch" disabled>
						<option value="1" <c:if test="${isBatch==1}">selected</c:if>>是</option>
						<option value="0" <c:if test="${isBatch==1}">selected</c:if>>否</option>
					</select>
					<%--<input type="radio" name="isBatch" value="1" class="input" <c:if test="${isBatch==1}">checked</c:if> readonly>是
					<input type="radio" name="isBatch" value="0" class="input" <c:if test="${isBatch==0}">checked</c:if> readonly>否--%>
				</td>

				<td class="columnTitle">是否转船：</td>
				<td class="tableContentAuto">
					<select name="isBatch" disabled>
						<option value="1" <c:if test="${isTrans==1}">selected</c:if>>是</option>
						<option value="0" <c:if test="${isTrans==1}">selected</c:if>>否</option>
					</select>
					<%--<input type="radio" name="isTrans" value="1" class="input" <c:if test="${isTrans==1}">checked</c:if> readonly>是
					<input type="radio" name="isTrans" value="0" class="input" <c:if test="${isTrans==0}">checked</c:if> readonly>否--%>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">份数：</td>
				<td class="tableContent"><input type="text" name="copyNum" value="${copyNum}" readonly/></td>

				<td class="columnTitle">扼要说明：</td>
				<td class="tableContent"><input type="text" name="remark" value="${remark}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">运输要求：</td>
				<td class="tableContent"><input type="text" name="specialCondition" value="${specialCondition}" readonly/></td>

				<td class="columnTitle">运费说明：</td>
				<td class="tableContent"><input type="text" name="freight" value="${freight}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">复核人：</td>
				<td class="tableContent"><input type="text" name="checkBy" value="${checkBy}" readonly/></td>
			</tr>
		</table>
	</div>

	<br/><br/>
	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
		对应的装箱单
	</div>

	<div class="eXtremeTable" >
		<table id="ec_table" class="tableRegion" width="98%" >
			<thead>
				<tr align="center" style="height: 42px;">
					<td class="tableHeader">序号</td>
					<td class="tableHeader">卖方</td>
					<td class="tableHeader">买方</td>
					<td class="tableHeader">发票号</td>
					<td class="tableHeader">发票日期</td>
					<td class="tableHeader">状态</td>
				</tr>
			</thead>
			<tbody class="tableBody" >
				<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" align="center" style="height: 42px;">
					<td>1</td>
					<td>${o.seller}</td>
					<td>${o.buyer}</td>
					<td>${o.invoiceNo}</td>
					<td>${o.invoiceDate}</td>
					<td>
						<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
						<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
						<c:if test="${o.state==2}"><font color="#00bfff">已开发票</font></c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
 
</form>
</body>
</html>

