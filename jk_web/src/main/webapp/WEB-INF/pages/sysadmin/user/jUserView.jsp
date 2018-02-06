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
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   查看用户
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">所在部门：</td>
				<td class="tableContent">
					<s:select name="dept.id" list="deptList"
							  listKey="id" listValue="deptName"
							  headerKey="" headerValue="--请选择--" disabled="true"
					></s:select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">用户名：</td>
				<td class="tableContent">
					<input type="text" name="userName" value="${userName }" readonly/>
				</td>
				<td class="columnTitle">状态：</td>
				<td class="tableContentAuto">
					<select name="state" disabled>
						<option value="1" <c:if test="${state==1}">selected</c:if>>启用</option>
						<option value="0" <c:if test="${state==0}">selected</c:if>>停用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">姓名：</td>
				<td class="tableContent"><input type="text" name="userInfo.name" value="${userInfo.name}" readonly/></td>
				<td class="columnTitle">直属领导：</td>
				<td class="tableContent">
					<s:select name="userInfo.manager.id" list="userList"
							  listKey="id" listValue="userInfo.name"
							  headerKey="" headerValue="--请选择--" disabled="true"
					></s:select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">入职时间：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="userInfo.joinDate"
						   value="<fmt:formatDate value='${userInfo.joinDate}' pattern="yyyy-MM-dd"/>${userInfo.joinDate}"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'}); readonly"/>
				</td>
				<td class="columnTitle">出生年月：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="userInfo.birthday"
						   value="<fmt:formatDate value='${userInfo.birthday}' pattern="yyyy-MM-dd"/>"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'}); readonly"/>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">等级：</td>
				<td class="tableContentAuto">
					<select name="userInfo.degree" disabled>
						<option value="0" <c:if test="${userInfo.degree==0}">selected</c:if>>超级管理员</option>
						<option value="1" <c:if test="${userInfo.degree==1}">selected</c:if>>跨部门跨人员</option>
						<option value="2" <c:if test="${userInfo.degree==2}">selected</c:if>>管理所有下属部门和人员</option>
						<option value="3" <c:if test="${userInfo.degree==3}">selected</c:if>>管理本部门</option>
						<option value="4" <c:if test="${userInfo.degree==4}">selected</c:if>>普通员工</option>
					</select>
				</td>
				<td class="columnTitle">性别：</td>
				<td class="tableContentAuto">
					<select name="userInfo.gender" disabled>
						<option value="1" <c:if test="${userInfo.gender==1}">selected</c:if>>男</option>
						<option value="0" <c:if test="${userInfo.gender==0}">selected</c:if>>女</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">薪水：</td>
				<td class="tableContent"><input type="text" name="userInfo.salary" value="${userInfo.salary}" readonly/></td>
				<td class="columnTitle">岗位：</td>
				<td class="tableContent"><input type="text" name="userInfo.station" value="${userInfo.station}" readonly/></td>

			</tr>
			<tr>
				<td class="columnTitle">电话：</td>
				<td class="tableContent"><input type="text" name="userInfo.telephone" value="${userInfo.telephone}" readonly/></td>
				<td class="columnTitle">邮箱：</td>
				<td class="tableContent"><input type="text" name="userInfo.email" value="${userInfo.email}" readonly/></td>
			</tr>
			<tr>
				<td class="columnTitle">排序号：</td>
				<td class="tableContent"><input type="text" name="userInfo.orderNo" value="${userInfo.orderNo}" readonly/></td>
				<td class="columnTitle">说明：</td>
				<td class="tableContent"><input type="text" name="userInfo.remark" value="${userInfo.remark}" readonly/></td>
			</tr>
		</table>
	</div>
 </form>
</body>
</html>