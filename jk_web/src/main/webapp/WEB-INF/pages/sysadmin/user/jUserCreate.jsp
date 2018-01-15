<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
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
<li id="save"><a href="#" onclick="formSubmit('userAction_insert','_self');this.blur();">保存</a></li>
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
   新增用户
  </div> 
  </div>
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
       		<tr>
	            <td class="columnTitle">所在部门：</td>
	            <td class="tableContent">
	            	<s:select name="dept.id" list="deptList"
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            </td>
	        </tr>
        	<tr>
	            <td class="columnTitle">登录名：</td>
	            <td class="tableContent"><input type="text" name="userName" value=""/></td>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContentAuto">
					<select name="state">
						<option value="1" >启用</option>
						<option value="0" >停用</option>
					</select>
	            </td>
	        </tr>
        	<tr>
	            <td class="columnTitle">姓名：</td>
	            <td class="tableContent"><input type="text" name="userInfo.name" value=""/></td>
	            <td class="columnTitle">直属领导：</td>
	            <td class="tableContent">
	            	<s:select name="userInfo.manager.id" list="userList"
	            		listKey="id" listValue="userInfo.name"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">入职时间：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userInfo.joinDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
				<td class="columnTitle">薪水：</td>
	            <td class="tableContent"><input type="text" name="userInfo.salary" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">等级：</td>
	            <td class="tableContentAuto">
					<select name="userInfo.degree">
						<option value="0" >超级管理员</option>
						<option value="1" >跨部门跨人员</option>
						<option value="2" >管理所有下属部门和人员</option>
						<option value="3" >管理本部门</option>
						<option value="4" >普通员工</option>
					</select>
	            </td>
				<td class="columnTitle">性别：</td>
	            <td class="tableContentAuto">
					<select name="userInfo.gender">
						<option value="1" >男</option>
						<option value="0" >女</option>
					</select>
	            </td>
	        </tr>	
        	<tr>
	            <td class="columnTitle">岗位：</td>
	            <td class="tableContent"><input type="text" name="userInfo.station" value=""/></td>
	            <td class="columnTitle">电话：</td>
	            <td class="tableContent"><input type="text" name="userInfo.telephone" value=""/></td>
	        </tr>	
        	<tr>
        	    <td class="columnTitle">邮箱：</td>
	            <td class="tableContent"><input type="text" name="userInfo.email" value=""/></td>
	            <td class="columnTitle">出生年月：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userInfo.birthday"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
        	<tr>
        	    <td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" name="userInfo.orderNo" value=""/></td>
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><input type="text" name="userInfo.remark" value=""/></td>
	        </tr>
		</table>
	</div>
 
</div>
</form>
</body>
</html>

