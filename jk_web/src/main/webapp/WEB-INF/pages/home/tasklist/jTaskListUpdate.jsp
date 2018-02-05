<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.4.4.min.js"></script>
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
		<li id="save"><a href="#" onclick="formSubmit('taskListAction_update','_self');this.blur();">保存</a></li>
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
	            <td class="tableContent"><input type="text" style="width: 600px; border: 1px solid rgb(54, 158, 146);" readonly="readonly"  name="pusherName" value="${pusherName}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">执行者：</td>
	            <td class="tableContent">
					<select name="userId" id="sel01">
						<c:forEach items="${userList }" var="u">
							<option value="${u.id }"  ${userId==u.id ?"selected":""}>${u.userName }</option>
						</c:forEach>
					</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">重要程度：</td>
	            <td class="tableContentAuto">
					<select name="major" >
						<option value="轻松"  ${major=='轻松' ?"selected":""}>轻松</option>
						<option value="高级"  ${major=='高级' ?"selected":""}>高级</option>
						<option value="超级"  ${major=='超级' ?"selected":""}>超级</option>
						<option value="令人疯狂的"  ${major=='令人疯狂的' ?"selected":""}>令人疯狂的</option>
					</select>
	            </td>
	        </tr>
	        <%--<tr>
	            <td class="columnTitle">发布日期：</td>
	            <td class="tableContent">
	             <input type="text" style="width:90px;" name="pushDate"
	            	 value="${pushDate}"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
	            </td>
	        </tr>--%>
	        <tr>
	            <td class="columnTitle">任务截止日期：</td>
	            <td class="tableContent">
	             <input type="text" style="width:90px;" name="endDate"
	            	 value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd HH:mm:ss" />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"/>
	            </td>
	        </tr>
			<tr>
				<td class="columnTitle">任务内容：</td>
				<td class="tableContent"><%-- <input type="text" name="content" value="${content}"/> --%>
					<textarea name="content"  style="height:150px;width: 600px">${content}</textarea>
				</td>
			</tr>
		</table>
	</div>
 
 
</form>
</body>
</html>

