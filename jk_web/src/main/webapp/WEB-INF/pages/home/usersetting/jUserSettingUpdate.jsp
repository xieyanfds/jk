<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
	
</head>

<body>
<form name="icform" method="post">
      <input type="hidden" name="id" value="${id}"/>
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="work_assign"><a href="#" onclick="formSubmit('userSettingAction_toupdatePassword','_self');this.blur();">修改密码</a></li>
<li id="save"><a href="#" onclick="formSubmit('userSettingAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/users_content.png"/>
   查看用户
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	           <td class="columnTitle">所在部门：</td>
	            <td class="tableContent"><input type="text" disabled value="${dept.deptName }"/></td>
	           <%--  <td class="columnTitle">状态：</td>
	            <td class="tableContentAuto">
	              <input type="radio" name="state" class="input" ${state==0?'checked':'' } value="0">停用 
	              <input type="radio" name="state" class="input"  ${state==1?'checked':'' } value="1">启用 
	            </td> --%>
	        </tr>		
	        <tr>
	            <td class="columnTitle">用户名：</td>
	            <td class="tableContent"><input type="text" name="userName" value="${userName }"/></td>
	            <td class="columnTitle">薪水：</td>
	            <td class="tableContent"><input type="text" disabled name="userInfo.salary" value="${userInfo.salary }"/></td>
	        </tr>	
	        
	        <tr>
	            <td class="columnTitle">姓名：</td>
	            <td class="tableContent"><input type="text" name="userInfo.name" value="${userInfo.name }"/></td>
	            <td class="columnTitle">直属领导：</td>
	            <td class="tableContent"><input type="text" disabled name="userInfo.manager.userInfo.name" value="${userInfo.manager.userInfo.name }"/></td>
	        </tr>	
	        <tr>
	           <td class="columnTitle">入职时间：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" disabled name="userInfo.joinDate"
	            	 value="${userInfo.joinDate }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	           <td class="columnTitle">出生年月：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="userInfo.birthday"
	            	 value="${userInfo.birthday }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">性别：</td>
	            <td class="tableContentAuto">
	            	<c:if test="${userInfo.gender==1 }">
	            		<input type="radio" name="userInfo.gender" value="1" class="input" checked/>男
	            		<input type="radio" name="userInfo.gender" value="0" class="input"/>女
	            	</c:if>
	            	<c:if test="${userInfo.gender==0 }">
	            		<input type="radio" name="userInfo.gender" value="1" class="input" />男
	            		<input type="radio" name="userInfo.gender" value="0" class="input" checked/>女
	            	</c:if>
	            </td>
	            <td class="columnTitle">岗位：</td>
	            <td class="tableContent"><input type="text" name="userInfo.station" value="${userInfo.station }"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">电话：</td>
	            <td class="tableContent"><input type="text" name="userInfo.telephone" value="${userInfo.telephone }"/></td>
	            <td class="columnTitle">邮箱：</td>
	            <td class="tableContent"><input type="text" name="userInfo.email" value="${userInfo.email }"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">权限：</td>
	            <td class="tableContent"><input type="text" disabled name="userInfo.degree" value="${userInfo.degree }"/></td>
	            <td class="columnTitle">备注：</td>
	            <td class="tableContent"><input type="text" name="userInfo.remark" value="${userInfo.remark }"/></td>
	        </tr>
	        <%-- <tr>
	            <td class="columnTitle">创建部门：</td>
	            <td class="tableContent"><input type="text" disabled name="createDept" value="${createDept }"/></td>
	            <td class="columnTitle">创建人：</td>
	            <td class="tableContent"><input type="text" disabled name="createBy" value="${createBy }"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">创建时间：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" disabled name="createTime"
	            	 value="${createTime }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
					<input type="hidden" style="width:90px;" disabled name="userInfo.createTime"
	            	 value="${userInfo.createTime }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	            <td class="columnTitle">更新人：</td>
	            <td class="tableContent"><input type="text" disabled name="updateBy" value="${updateBy }"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">更新时间：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" disabled name="updateTime"
	            	 value="${updateTime }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
					<input type="hidden" style="width:90px;" disabled name="userInfo.updateTime"
	            	 value="${userInfo.updateTime }"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	            <td class="columnTitle">排序号：</td>
	            <td class="tableContent"><input type="text" disabled name="userInfo.orderNo" value="${userInfo.orderNo }"/></td>
	        </tr> --%>
	        
		</table>
	</div>
 </form>
</body>
</html>