<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
	<script type="text/javascript">
		//动态设置生产厂家的名称 
		function setFactoryName(val){
			document.getElementById("factoryName").value = val;
		}
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('productAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   产品修改
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	       <tr>
	            <td class="columnTitle">编号：</td>
	            <td class="tableContent"><input type="text" name="productNo" value="${productNo }"/></td>
	        
	            <td class="columnTitle">照片：</td>
	            <td class="tableContent"><input type="text" name="productImage" value="${productImage }"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent"><input type="text" name="description" value="${description }"/></td>
	       
	            <td class="columnTitle">厂家：</td>
	            <td class="tableContent">
	            	<s:select name="factory.id" list="factoryList"
	            		onchange="setFactoryName(this.options[this.selectedIndex].text);"
	            		listKey="id" listValue="factoryName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            	
	            	<input type="hidden" id="factoryName" name="factoryName" value="${factoryName }"/>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">市场价：</td>
	            <td class="tableContent"><input type="text" name="price" value="${price }"/></td>
	        
	            <td class="columnTitle">长：</td>
	            <td class="tableContent"><input type="text" name="sizeLenght" value="${sizeLenght }"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">宽：</td>
	            <td class="tableContent"><input type="text" name="sizeWidth" value="${sizeWidth }"/></td>
	        
	            <td class="columnTitle">高：</td>
	            <td class="tableContent"><input type="text" name="sizeHeight" value="${sizeHeight }"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">颜色：</td>
	            <td class="tableContent"><input type="text" name="color" value="${color }"/></td>
	        
	            <td class="columnTitle">包装：</td>
	            <td class="tableContent"><input type="text" name="packing" value="${packing }"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">包装单位：</td>
	            <td class="tableContent"><input type="text" name="packingUnit" value="${packingUnit }"/></td>
	        
	            <td class="columnTitle">集装箱类别：</td>
	            <td class="tableContent">
	            	<select name="type">
	            		<option value="20" <c:if test="${type=='20' }">selected</c:if>>20</option>
	            		<option value="40" <c:if test="${type=='40' }">selected</c:if>>40</option>
	            		<option value="40hc" <c:if test="${type=='40hc' }">selected</c:if>>40hc</option>
	            	</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">大箱尺寸长：</td>
	            <td class="tableContent"><input type="text" name="mpsizeLenght" value="${mpsizeLenght }"/></td>
	        
	            <td class="columnTitle">大箱尺寸宽：</td>
	            <td class="tableContent"><input type="text" name="mpsizeWidth" value="${mpsizeWidth }"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">大箱尺寸高：</td>
	            <td class="tableContent"><input type="text" name="mpsizeHeight" value="${mpsizeHeight }"/></td>
	        
	            <td class="columnTitle">备注：</td>
	            <td class="tableContent"><input type="text" name="remark" value="${remark }"/></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

