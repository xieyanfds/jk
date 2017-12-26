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
<li id="save"><a href="#" onclick="formSubmit('deptAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改部门
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">上级部门：</td>
	            <td class="tableContent">
	            <!-- 
	                 name属性代表下拉框的名称
	                 list: 绑定的集合名   List<Dept>
	                 headerKey:代表首选项的value属性
	                 headerValue:代表首选项的文本
	                 listKey: <option value="${id }"
	                 listValue：代表中间的文本
	                 struts2标签默认具有自动回显     
	             	   原理： 它默认会取值栈的栈顶
	               -->
	            	<s:select name="parent.id" list="deptList"
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">部门名称：</td>
	            <td class="tableContent"><input type="text" name="deptName" value="${deptName }"/>
	            </td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>