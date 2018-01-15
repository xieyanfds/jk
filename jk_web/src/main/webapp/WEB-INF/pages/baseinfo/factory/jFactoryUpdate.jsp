<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('factoryAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改厂家信息
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">货物/附件：</td>
	            <td class="tableContent"><input type="text" name="ctype" value="${ctype}"/></td>
	       
	            <td class="columnTitle">厂家全称：</td>
	            <td class="tableContent"><input type="text" name="fullName" value="${fullName}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">厂家简称：</td>
	            <td class="tableContent"><input type="text" name="factoryName" value="${factoryName}"/></td>
	        
	            <td class="columnTitle">联系人：</td>
	            <td class="tableContent"><input type="text" name="contacts" value="${contacts}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">电话：</td>
	            <td class="tableContent"><input type="text" name="phone" value="${phone}"/></td>
	        
	            <td class="columnTitle">手机：</td>
	            <td class="tableContent"><input type="text" name="mobile" value="${mobile}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">传真：</td>
	            <td class="tableContent"><input type="text" name="fax" value="${fax}"/></td>
	        
	            <td class="columnTitle">地址：</td>
	            <td class="tableContent"><input type="text" name="address" value="${address}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">验货员：</td>
	            <td class="tableContent"><input type="text" name="inspector" value="${inspector}"/></td>
	        
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><input type="text" name="remark" value="${remark}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" name="orderNo" value="${orderNo}"/></td>
	        
	            <td class="columnTitle">厂家状态：</td>
	            <td class="tableContent"><input type="text" name="state" value="${state}"/></td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>
</body>
</html>

