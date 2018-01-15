<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
   浏览厂家
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">厂家编号：</td>
	            <td class="tableContent"><input type="text" name="id" value="${id}"/></td>
	        
	            <td class="columnTitle">货物/附件：</td>
	            <td class="tableContent"><input type="text" name="ctype" value="${ctype}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">厂家全称：</td>
	            <td class="tableContent"><input type="text" name="fullName" value="${fullName}" readonly/></td>
	        
	            <td class="columnTitle">厂家简称：</td>
	            <td class="tableContent"><input type="text" name="factoryName" value="${factoryName}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系人：</td>
	            <td class="tableContent"><input type="text" name="contacts" value="${contacts}" readonly/></td>
	        
	            <td class="columnTitle">电话：</td>
	            <td class="tableContent"><input type="text" name="phone" value="${phone}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">手机：</td>
	            <td class="tableContent"><input type="text" name="mobile" value="${mobile}" readonly/></td>
	        
	            <td class="columnTitle">传真：</td>
	            <td class="tableContent"><input type="text" name="fax" value="${fax}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">地址：</td>
	            <td class="tableContent"><input type="text" name="address" value="${address}" readonly/></td>
	           	<td class="columnTitle" >创建时间：</td>
				<td class="tableContent"><input type="text" name="createTime" value="<fmt:formatDate value='${createTime }' pattern='yyyy-MM-dd' />" readonly/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">验货员：</td>
	            <td class="tableContent"><input type="text" name="inspector" value="${inspector}" readonly/></td>
	        
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><input type="text" name="remark" value="${remark}" readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" name="orderNo" value="${orderNo}" readonly/></td>
	        
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent"><input type="text" name="state" value="${state==1?"正常":"停用"}"/>
	            </td>
	        </tr>	
	       
		</table>
	</div>
 
 
</form>
</body>
</html>

