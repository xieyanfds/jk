<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
	<script type="text/javascript">
		$(function(){
			$("#sel01").change(function(){
				 $("#username").val($("select option:selected").html()); 
				
				/* alert($("select option:selected").html()); */
				
			})
		})
	
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>
	<input id="username" type=hidden name="userName" value="${userName }">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('tasklistAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改代办任务
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">发布者：</td>
	            <td class="tableContent"><input type="text" style="width: 500px; border: 1px solid rgb(54, 158, 146);" readonly="readonly"  name="pusherName" value="${pusherName}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">执行者：</td>
	            <td class="tableContent">
	            	 <select name="userId" id="sel01">
	            	<option value="">--请选择--</option>
		            <c:forEach items="${userList }" var="u">
		            	<option value="${u.id }"  ${userId==u.id ?"selected":""}>${u.userName }</option>
		            </c:forEach>
	            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">任务内容：</td>
	            <td class="tableContent"><%-- <input type="text" name="content" value="${content}"/> --%>
	             <textarea name="content"  style="height:150px;width: 600px">${content}</textarea>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">重要程度：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="major" ${major=='轻松' ?"checked":""} value="轻松">轻松
	            	<input type="radio" name="major" ${major=='高级' ?"checked":""} value="高级">高级
	            	<input type="radio" name="major" ${major=='超级' ?"checked":""} value="超级">超级
	            	<input type="radio" name="major" ${major=='令人疯狂的' ?"checked":""} value="令人疯狂的">令人疯狂的
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">发布日期：</td>
	            <td class="tableContent">
	             <input type="text" style="width:90px;" name="pushDate"
	            	 value="${pushDate}"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">任务截止日期：</td>
	            <td class="tableContent">
	             <input type="text" style="width:90px;" name="endDate"
	            	 value="${endDate}"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContentAuto" align="left">
            		<input type="radio" value="1" name="state"  ${state==1 ?"checked":""}>已完成
	        		<input type="radio" value="0" name="state"  ${state==0 ?"checked":""}>未完成
	            </td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

