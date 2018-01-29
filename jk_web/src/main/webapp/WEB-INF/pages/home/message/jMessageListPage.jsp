<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/layer/layer.js"></script>
<script type="text/javascript">

	function isOnlyChecked() {
		var checkBoxArray = document.getElementsByName('id');
		var count = 0;
		for (var index = 0; index < checkBoxArray.length; index++) {
			if (checkBoxArray[index].checked) {
				count++;
			}
		}
		//jquery
		/* var count = $("[input name='id']:checked").size(); */
		if (count == 1){
			return true;
		}
		else{
			return false;
		}
		
	}
	// 多选
	function isChecked(){
		 var checkBoxArray = document.getElementsByName('id');
			var count=0;
			for(var index=0; index<checkBoxArray.length; index++) {
				if (checkBoxArray[index].checked) {
					count++;
				}	
			}
		//jquery
		//var count = $("input[name='id']:checked").size();
		if(count>0)
			return true;
		else
			return false;
	}
	function toView() {
		if (isOnlyChecked()) {
			/* formSubmit('deptAction_toview','_self'); */
			formSubmit('messageAction_toview', '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
	//实现更新
	function toUpdate() {
		if (isOnlyChecked()) {
			var s = $("input:checked");  
			var oid = s.val();
            var state = document.getElementById(oid).value;
            if(state!=1){
                alert("已发送的不能修改!!!")
			}else{
				formSubmit('messageAction_toupdate','_self')
			} 
			
		}else {
				alert("请先选择一项并且只能选择一项，再进行操作！");
			
		}
		
	}
	
	//删除请求
	function toDelete() {
		if (isChecked()) {
			formSubmit('messageAction_delete','_self')
		} else {
			alert("请先选择一项或者多项，再进行操作！");
		}
	}
	// 发送请求
	function toSend() {
	// js 自带for循环
		/* var s = document.getElementsByName("t01"); */
 		if (isChecked()) {
			 var s = $("input:checked:gt(0)") 
			 for(var i=0;i<s.length;i++){
				 var state =$(s[i]).attr("a"); 
					if(state!=0){
						alert("已发送的项目不能再次发送！");
						return ;
					}
			}
		 	formSubmit('messageAction_submit','_self');
		}else {
			alert("请先选择一项或者多项，再进行操作！");
		}
		
	}
</script>	

</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
	<div id="innerMenubar">
	  <div id="navMenubar">
		<ul>
		<li id="view"><a href="#" onclick="toView();this.blur();">查看</a></li>
		<li id="new"><a href="#" onclick="formSubmit('messageAction_tocreate','_self');this.blur();">新增</a></li>
		<li id="update"><a href="#" onclick="toUpdate();this.blur();">修改</a></li>
		<li id="delete"><a href="#" onclick="toDelete();this.blur();">删除</a></li>
		<li id="people"><a href="#" onclick="formSubmit('messageAction_listReceive','_self');this.blur();">我的</a></li>
		<li id="new"><a href="#" onclick="formSubmit('messageAction_listReceive','_self');this.blur();">已接收</a></li>
		</ul>
	  </div>
	</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
    我的留言板
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">接收人</td>
		<td class="tableHeader">标题</td> 
		<td class="tableHeader">时间</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${page.links}
	
	<c:forEach  items="${page.results}" var="o" varStatus="status">
	<tr  class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${o.receive}</td>
		<td>${o.title}</td>
		<td>${o.messageTime}</td>
		<td>
			<c:if test="${o.state==1}"><font color="red"><b>未读</b></font></c:if>
			<c:if test="${o.state==2}"><font color="green"><b>已读</b></font></c:if>
			<input type="hidden" value="${o.state}" id="${o.id}">
		</td>
		
		<%-- <td><a href="#" onclick="toView">查看</a>|  <a href="${ctx}/home/messageAction_toupdate?id=${o.id}">修改</a> </td> --%>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
</form>
</body>
</html>

