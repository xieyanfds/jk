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
<li id="save"><a href="#" onclick="formSubmit('moduleAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   修改模块
  </div> 
  </div>
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">模块名：</td>
	            <td class="tableContent"><input type="text" name="name" value="${name}"/></td>
	            <td class="columnTitle">层数：</td>
	            <td class="tableContent"><input type="text" name="layerNum" value="${layerNum}"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">权限标识：</td>
	            <td class="tableContent"><input type="text" name="cpermission" value="${cpermission}"/></td>
	            <td class="columnTitle">链接：</td>
	            <td class="tableContent"><input type="text" name="curl" value="${curl}"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">类型：</td>
	            <td class="tableContentAuto">
					<select name="ctype">
						<option value="1" <c:if test="${ctype==1}">selected</c:if>>主菜单</option>
						<option value="2" <c:if test="${ctype==2}">selected</c:if>>左侧菜单</option>
						<option value="3" <c:if test="${ctype==3}">selected</c:if>>按钮</option>
						<option value="4" <c:if test="${ctype==4}">selected</c:if>>链接</option>
						<option value="5" <c:if test="${ctype==5}">selected</c:if>>状态</option>
					</select>
	            </td>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContentAuto">
					<select name="state">
						<option value="1" <c:if test="${state==1}">selected</c:if>>启用</option>
						<option value="0" <c:if test="${state==0}">selected</c:if>>停用</option>
					</select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">从属：</td>
	            <td class="tableContent"><input type="text" name="belong" value="${belong}"/></td>
	            <td class="columnTitle">复用标识：</td>
	            <td class="tableContent"><input type="text" name="cwhich" value="${cwhich}"/></td>
	        </tr>			
	        <tr>
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><input type="text" name="remark" value="${remark}"/></td>
				<td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" name="orderNo" value="${orderNo}"/></td>
	        </tr>			
		</table>
	</div>
 
</div>
</form>
</body>
</html>

