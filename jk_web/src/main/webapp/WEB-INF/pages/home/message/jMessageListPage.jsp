<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
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
		<%--<ul>
		<li id="view"><a href="#" onclick="toView();this.blur();">查看</a></li>
		<li id="new"><a href="#" onclick="formSubmit('messageAction_tocreate','_self');this.blur();">新增</a></li>
		<li id="update"><a href="#" onclick="toUpdate();this.blur();">修改</a></li>
		<li id="delete"><a href="#" onclick="toDelete();this.blur();">删除</a></li>
		<li id="people"><a href="#" onclick="formSubmit('messageAction_listReceive','_self');this.blur();">我的</a></li>
		</ul>--%>
		<%@include file="../../button.jsp"%>
	  </div>
	</div>
</div>
</div>

	<script type="text/javascript">
        //实现更新
        function to_update(url) {
            if (isOnlyChecked()) {
                var s = $("input:checked");
                var oid = s.val();
                var state = document.getElementById(oid).value;
                if(state!=1){
                    $("#envon #mess").html("抱歉，已查阅的不能修改！");
                    EV_modeAlert('envon');
                }else{
                    formSubmit(url,'_self')
                }

            }else {
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }

        }

        //实现角色分配
        function to_people(url) {
            formSubmit(url, '_self');
        }
	</script>

	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/Alert_09.gif" width="32px;" height="32px;"/>
		我的留言板
	</div>

<br/>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">创建人</td>
		<td class="tableHeader">接收人</td>
		<td class="tableHeader">标题</td>
		<td class="tableHeader">时间</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	<%--${page.links}--%>
	
		<c:forEach  items="${results}" var="o" varStatus="status">
		<tr  class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
			<td><input type="checkbox" name="id" value="${o.id}"/></td>
			<td style="cursor: pointer;" onclick="statusToAction('messageAction_toview?id=${o.id}')"><a href="messageAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
			<td>${o.createName}</td>
			<td>${o.receive}</td>
			<td>${o.title}</td>
			<td>${o.messageTime}</td>
			<td>
				<c:if test="${o.state==1}"><font color="red"><b>未读</b></font></c:if>
				<c:if test="${o.state==2}"><font color="green"><b>已读</b></font></c:if>
				<input type="hidden" value="${o.state}" id="${o.id}">
			</td>

		</tr>
		</c:forEach>
	
	</tbody>
</table>
</div>
</div>
<%@include file="../../page.jsp"%>
</form>
</body>


</html>

