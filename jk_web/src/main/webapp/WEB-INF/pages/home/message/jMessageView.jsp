<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
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
				<li id="back"><a href="#" onclick="formSubmit('messageAction_list','_self');this.blur();">返回</a></li>
			</ul>
		</div>
	</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
   留言板
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>  
	            <td class="columnTitle">接收人：</td>
	            <td class="tableContent">
					<select name="receiveId" disabled>
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
					<input type="text" style="width:90px;" name="messageTime"
						   value="${messageTime }"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" readonly/>
				</td>
	        </tr>
	       	<tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent">
					<input type="text" name="title" value="${title}" style="width: 70%" readonly/>
				</td>
	           
	        </tr>	
	        <tr>
	            <td class="columnTitle">留言：</td>
	            <td class="tableContent">
	            <textarea name="message" style="height: 200px;width: 70%" readonly="readonly" >${message}</textarea></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

