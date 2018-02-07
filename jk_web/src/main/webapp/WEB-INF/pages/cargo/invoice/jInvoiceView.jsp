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
	<img src="${ctx }/skin/default/images/icon/address_book2.png"/>
   浏览发票
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">发票号：</td>
				<td class="tableContent"><input type="text" name="scNo" value="${scNo}" readonly/></td>

				<td class="columnTitle">提单号：</td>
				<td class="tableContent"><input type="text" name="blNo" value="${blNo}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">交易条款：</td>
				<td class="tableContent"><input type="text" name="tradeTerms" value="${tradeTerms}" readonly/></td>

				<td class="columnTitle">发票时间：</td>
				<td class="tableContent"><input type="text" name="createTime" value="${createTime}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">委托单号：</td>
				<td class="tableContent"><input type="text" name="id" value="${id}" readonly/></td>

				<td class="columnTitle">状态：</td>
				<td class="tableContent">
					<input type="text" name="state" value="<c:if test="${state==0}">草稿</c:if><c:if test="${state==1}">已提交</c:if>" readonly/>
				</td>
			</tr>

		</table>
	</div>
 
 
</form>
</body>
</html>

