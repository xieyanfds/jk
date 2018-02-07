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

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('productAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/component.png"/>
   产品新增
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        
	        <tr>
	            <td class="columnTitle">编号：</td>
	            <td class="tableContent"><input type="text" name="productNo" value=""/></td>
	        
	            <td class="columnTitle">照片：</td>
	            <td class="tableContent"><input type="text" name="productImage" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent"><input type="text" name="description" value=""/></td>
	       
	            <td class="columnTitle">厂家：</td>
	            <td class="tableContent">
	            	<s:select name="factory.id" list="factoryList"
	            		onchange="setFactoryName(this.options[this.selectedIndex].text);"
	            		listKey="id" listValue="factoryName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            	
	            	<input type="hidden" id="factoryName" name="factoryName" value=""/>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">市场价：</td>
	            <td class="tableContent"><input type="text" name="price" value=""/></td>
	        
	            <td class="columnTitle">长：</td>
	            <td class="tableContent"><input type="text" name="sizeLenght" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">宽：</td>
	            <td class="tableContent"><input type="text" name="sizeWidth" value=""/></td>
	        
	            <td class="columnTitle">高：</td>
	            <td class="tableContent"><input type="text" name="sizeHeight" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">颜色：</td>
	            <td class="tableContent"><input type="text" name="color" value=""/></td>
	        
	            <td class="columnTitle">包装：</td>
	            <td class="tableContent"><input type="text" name="packing" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">包装单位：</td>
	            <td class="tableContent"><input type="text" name="packingUnit" value=""/></td>
	        
	            <td class="columnTitle">集装箱类别：</td>
	            <td class="tableContent">
	            	<select name="type">
	            		<option value="20">20</option>
	            		<option value="40">40</option>
	            		<option value="40hc">40hc</option>
	            	</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">大箱尺寸长：</td>
	            <td class="tableContent"><input type="text" name="mpsizeLenght" value=""/></td>
	        
	            <td class="columnTitle">大箱尺寸宽：</td>
	            <td class="tableContent"><input type="text" name="mpsizeWidth" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">大箱尺寸高：</td>
	            <td class="tableContent"><input type="text" name="mpsizeHeight" value=""/></td>
	        
	            <td class="columnTitle">备注：</td>
	            <td class="tableContent"><input type="text" name="remark" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">录入时间：</td>
	            <td class="tableContent">
	            	<input type="text" style="width:90px;" name="inputTime" value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	             </td>
	        </tr>	
	        	
		</table>
	</div>
 
 
</form>
</body>
</html>

