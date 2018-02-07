<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
<!--  	<script type="text/javascript">
		function test() {
			alert("测试");
			alert($("select option:selected").html());
			if($("select option:selected").val().equals("--请选择--")){
/* 				alert("接收人不能空");
				return false;
			})){ */
			
			
			}else{
				alert("ture");
				formSubmit('messageAction_insert','_self');
				return true;
			}
		} 
	</script> -->

</head>


<body>
<form name="icform" method="post" onsubmit="return test()">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('messageAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/Alert_09.gif" width="32px;" height="32px;"/>
   新增留言
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">接收人：</td>
	            <td class="tableContent">
					<select name="receiveId">
						<c:forEach items="${userList }" var="u">
							<option value = "${u.id }">${u.userInfo.name}</option>
						</c:forEach>
					</select>
	            </td>
	        </tr>
	            <td class="columnTitle">时间：</td>
	 	           <td class="tableContent">
	            <input type="text" style="width:90px;" name="messageTime"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
	            </td>
	            
	        <tr> 
	        	<td class="columnTitle">标题：</td>
	        	 <td class="tableContent"><input type="text" name="title" style="width: 70%" value="${message}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">留言：</td>
	            <td class="tableContent">
	            <textarea name="message" style="height: 200px;width: 70%" >${message}</textarea>
	          	</td>
	        </tr>
		</table>
	</div>
</form>
</body>
</html>

