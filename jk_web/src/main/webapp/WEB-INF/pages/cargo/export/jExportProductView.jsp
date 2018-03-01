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
	<img src="${ctx }/skin/default/images/icon/server_view.png"/>
   查看报运单下货物
  </div> 

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">生产厂家：</td>
	            <td class="tableContent">
	            	 <s:select name="factory.id" list="factoryList" 
	            				listKey="id" listValue="factoryName"
	            				headerKey="" headerValue="--请选择--" disabled="true"/>
	            </td>
	            <td class="columnTitle">货号：</td>
	            <td class="tableContentAuto"><input type="text" name="productNo" value="${productNo }" readonly/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">数量：</td>
	            <td class="tableContent"><input type="text" name="cnumber" value="${cnumber }" readonly/>
	            </td>
	            <td class="columnTitle">包装单位：</td>
	            <td class="tableContentAuto">
					<select id="packingUnit" name="packingUnit" disabled>
						<option value="PCS" <c:if test="${packingUnit=='PCS'}">selected</c:if>>只</option>
						<option value="SETS" <c:if test="${packingUnit=='SETS'}">selected</c:if>>套</option>
					</select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">单价：</td>
	            <td class="tableContent"><input type="text" name="price" value="${price }" readonly/></td>
	            <td class="columnTitle">出口单价：</td>
	            <td class="tableContent"><input type="text" name="exPrice" value="${exPrice }" readonly/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">毛重：</td>
	            <td class="tableContent"><input type="text" name="grossWeight" value="${grossWeight }" readonly/></td>
	            <td class="columnTitle">净重：</td>
	            <td class="tableContent"><input type="text" name="netWeight" value="${netWeight }" readonly/></td>
	        </tr>
			<tr>
				<td class="columnTitle">尺寸长：</td>
				<td class="tableContent"><input type="text" name="sizeLength" value="${sizeLength }" readonly/></td>
				<td class="columnTitle">尺寸宽：</td>
				<td class="tableContent"><input type="text" name="sizeWidth" value="${sizeWidth }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">尺寸高：</td>
				<td class="tableContent"><input type="text" name="sizeHeight" value="${sizeHeight }" readonly/></td>
				<td class="columnTitle">件数：</td>
				<td class="tableContent"><input type="text" name="boxNum" value="${boxNum }" readonly/></td>
			</tr>
		</table>
	</div>

</form>
</body>
</html>

