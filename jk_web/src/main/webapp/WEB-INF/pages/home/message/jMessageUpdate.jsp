<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
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
<li id="save"><a href="#" onclick="formSubmit('messageAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	  <img src="${ctx }/skin/default/images/icon/Alert_09.gif" width="32px;" height="32px;"/>
   修改我的留言
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">

	        <tr>
	            <td class="columnTitle">接收人：</td>
				<td class="tableContent">
				<select name="receiveId">
					<c:forEach items="${userList }" var="u">
						<c:if test= "${u.id==receiveId}">
							<option value = "${u.id }" selected>${u.userInfo.name}</option>
						</c:if>
						<c:if test= "${u.id!=receiveId}">
							<option value = "${u.id }" >${u.userInfo.name}</option>
						</c:if>
					</c:forEach>
				</select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">时间：</td>
				<td class="tableContent">
	            <input type="text" style="width:112px;" name="messageTime"
	            	 value="<fmt:formatDate value="${messageTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,opposite:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
	        </tr>
	         <tr> 
	        	<td class="columnTitle">标题：</td>
	        	<td class="tableContent"><input type="text" name="title" style="width: 70%" value="${title}"/></td>
	        </tr>
	        	
	        <tr>
	            <td class="columnTitle">留言：</td>
	            <td class="tableContent">
	            <textarea name="message" style="height: 200px;width: 70%">${message}</textarea>
	        </tr>
		</table>
	</div>
 
 
</form>
</body>
</html>

