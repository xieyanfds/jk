<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
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
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   产品详情
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">编号：</td>
				<td class="tableContent"><input type="text" name="productNo" value="${productNo }" readonly/></td>

				<td class="columnTitle">照片：</td>
				<td class="tableContent"><img src="${ctx }/ufiles/jquery/${productImage}" height="60px" width="60px"/></td>
			</tr>
			<tr>
				<td class="columnTitle">描述：</td>
				<td class="tableContent"><input type="text" name="description" value="${description }" readonly/></td>

				<td class="columnTitle">厂家名称：</td>
				<td class="tableContent"><input type="text" name="factoryName" value="${factoryName }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">市场价：</td>
				<td class="tableContent"><input type="text" name="price" value="${price }" readonly/></td>

				<td class="columnTitle">长：</td>
				<td class="tableContent"><input type="text" name="sizeLenght" value="${sizeLenght }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">宽：</td>
				<td class="tableContent"><input type="text" name="sizeWidth" value="${sizeWidth }" readonly/></td>

				<td class="columnTitle">高：</td>
				<td class="tableContent"><input type="text" name="sizeHeight" value="${sizeHeight }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">颜色：</td>
				<td class="tableContent"><input type="text" name="color" value="${color }" readonly/></td>

				<td class="columnTitle">包装：</td>
				<td class="tableContent"><input type="text" name="packing" value="${packing }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">包装单位：</td>
				<td class="tableContent"><input type="text" name="packingUnit" value="${packingUnit }" readonly/></td>

				<td class="columnTitle">集装箱类别：</td>
				<td class="tableContent"><input type="text" name="type" value="${type }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">大箱尺寸长：</td>
				<td class="tableContent"><input type="text" name="mpsizeLenght" value="${mpsizeLenght }" readonly/></td>

				<td class="columnTitle">大箱尺寸宽：</td>
				<td class="tableContent"><input type="text" name="mpsizeWidth" value="${mpsizeWidth }" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">大箱尺寸高：</td>
				<td class="tableContent"><input type="text" name="mpsizeHeight" value="${mpsizeHeight }" readonly/></td>

				<td class="columnTitle">备注：</td>
				<td class="tableContent"><input type="text" name="remark" value="${remark }" readonly/></td>
			</tr>
	        <tr>
	            <td class="columnTitle">录入时间：</td>
	            <td class="tableContent"><input type="text" name="inputTime" value="${inputTime }" readonly/></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

