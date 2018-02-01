<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx }/layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		$("tr:gt(0):odd").css("background-color", "#F0EDED");
		$("tr:gt(0):even").css("background-color", "#FFFFFF");
		
		
		var s = $("td:contains('吃')")
		for(var i=0;i<s.length;i++){
			$(s[i]).css("background-color", "#CE2C6A");
		}
		
	})

	function yanzheng() {
		//发送ajsx 判断当前用户是否存在下属
		$.post("${ctx}/home/taskListAction_isManager", function(data) {
			if (data == 0) {
				//不存在下属
				alert("您不是领导,不能发布任务")
			} else {
				//存在下属
				formSubmit('taskListAction_tocreate', '_self');
				this.blur();
			}
		});
	}

	function showDetail(uid) {
		/* layer.open({
			type:1,
			title:"订单详情",
			area:['400px','300px'],
			shadeClose:false,
			content:"订单"
		}); */
		$.post("${pageContext.request.contextPath}/sysadmin/userAction_findById4ajax.action",
						{
							"id" : uid
						},
						function(data) {
							var s = "<table width='100%' border=1px><tr style='background: yellow;height: 100px;'><td >执行者名称</td><td>执行者部门</td><td >执行者电话</td><td>执行者邮箱</td></tr>"
									+ "<tr style='background: #39E8E3;height: 100px;'><td style='height: 100px;'>"
									+ data.userName
									+ "</td><td style='height: 100px;'>"
									+ data.dept.deptName
									+ "</td><td style='height: 100px;'>"
									+ data.userInfo.telephone
									+ "</td><td style='height: 100px;'>"
									+ data.userInfo.email
									+ "</td></tr></table>";

							layer.open({
								type : 0,
								title : "查看任务执行者详情",
								area : [ '600px', '500px' ],
								shadeClose : true,
								content : s
							});
						}, "json");

	}

 	function further(uid,tid) {
 		/*获取当前登录用户id*/
 		var cur_u_id ="${_CURRENT_USER.id}";
 		if(uid!=cur_u_id){
 			alert("您不是任务执行者!不能下放任务")
 		}else{
 		
		$.post("${pageContext.request.contextPath}/sysadmin/userAction_hasManager.action",{"id" : uid},
				function(data) {
						console.info(data);
						 	if (data == 0) {
								alert("您不是上级领导,不能将任务下放!")
							} else {
								var s = "<form id='form01'  action='${ctx}/home/tasklistAction_updateToManager.action' method='post'><select id='sel001' name='userId' style='color: crimson; font-weight: bold; background: aquamarine;'><option value='"+data.id+"'>"+data.userName+"<option> </select>"
										+"</br></br><input  type='submit'  value='下放任务'> <input type='hidden' name='id' value='"+tid+"'></form>"
								     
									 layer.open({
											type : 0,
											title : "任务转移给哪位下属",
											area : [ '200px', '200px' ],
											shadeClose : true,
											content : s
										});
							}	 
							
							
			}, "json");
 		}
	}
 
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
		if (count == 1) {
			return true;
		} else {
			return false;
		}

	}

	function isChecked() {
		var checkBoxArray = document.getElementsByName('id');
		var count = 0;
		for (var index = 0; index < checkBoxArray.length; index++) {
			if (checkBoxArray[index].checked) {
				count++;
			}
		}
		//jquery
		//var count = $("input[name='id']:checked").size();
		if (count > 0)
			return true;
		else
			return false;
	}
	function toView() {
		if (isOnlyChecked()) {
			/* formSubmit('deptAction_toview','_self'); */
			formSubmit('taskListAction_toview', '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
	//实现更新
	function toUpdate() {
		if (isOnlyChecked()) {
			formSubmit('taskListAction_toupdate', '_self')
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}

	//删除请求
	function toDelete() {
		if (isChecked()) {
			if (confirm("确定删除吗?")) {
				formSubmit('taskListAction_delete', '_self')
			}
		} else {
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
							<!-- <li id="new"><a href="#" onclick="formSubmit('tasklistAction_tocreate','_self');this.blur();">新增</a></li>-->
							<li id="new"><a href="#" onclick="yanzheng()">发布</a></li>
							<li id="update"><a href="#"
								onclick="toUpdate();this.blur();">修改</a></li>
							<li id="delete"><a href="#"
								onclick="toDelete();this.blur();">删除</a></li>
							<li id="people"><a href="#"
								onclick="formSubmit('taskListAction_onlyLookMyTask.action','_self');this.blur();">我的任务</a></li>
							<li id="assess"><a href="#"
								onclick="formSubmit('taskListAction_findAllTask.action','_self');this.blur();">所有任务</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" />
			代办任务列表
		</div>

		<br/>
		<div>

			<div class="eXtremeTable">
				<table id="ec_table"  class="tableRegion" width="98%">
				
					<thead>
						<tr>
							<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
							<td class="tableHeader">序号</td>
							<td class="tableHeader">执行者</td>
							<td class="tableHeader">发布者</td>
							<td class="tableHeader">任务内容</td>
							<td class="tableHeader">发布日期</td>
							<td class="tableHeader">任务截止日期</td>
							<td class="tableHeader">状态</td>
							<td class="tableHeader">重要程度</td>
							<td class="tableHeader">执行者详情</td>
							<td class="tableHeader">任务下放</td>
						</tr>
					</thead>
					<tbody class="tableBody">
						${page.links}

						<c:forEach items="${page.results}" var="o" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
								<td><input type="checkbox" name="id" value="${o.id}" /></td>
								<td><a href="taskListAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
								<td>${o.userName}</td>
								<td>${o.pusherName}</td>
								<td>
									<c:if test="${fn:length(o.content) >10}">${fn:substring(o.content,0,6)}...</c:if>
									<c:if test="${fn:length(o.content) <11}">${o.content}</c:if>
								</td>
								<td><fmt:formatDate value="${o.pushDate}" pattern="yyyy-MM-dd" /></td>
								<td><fmt:formatDate value="${o.endDate}" pattern="yyyy-MM-dd" /></td>
								<td>
									<c:if test="${o.state==0}">
										<b><font color="#2e8ded">未完成</font></b>
									</c:if>
									<c:if test="${o.state==1}">
										<b><font color="green">已完成</font></b>
									</c:if>
								</td>
								<td>${o.major}</td>
								<td><input type="button"
									onclick="showDetail('${o.userId}')" value="查看执行者详情"
									style="border-color: #4898d5; background-color: #2e8ded;color: #fff; "></td>
								<td><input type="button"
									onclick="further('${o.userId}','${o.id }')" value="任务向下发放"
									style="border-color: #4898d5; background-color: #2e8ded;color: #fff; "
									></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>

		</div>
	</form>

</body>
</html>

