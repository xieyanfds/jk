<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx }/layer/layer.js"></script>
<script type="text/javascript">
    function showDetail(uid) {
        $.post("${pageContext.request.contextPath}/sysadmin/userAction_findById4ajax.action",
            {
                "id" : uid
            },
            function(data) {
                var s = "<table width='100%'; border=1px;>"
                    + "<tr style='background: #add8d3;height: 40px;'><td >姓名</td><td>部门</td><td >电话</td><td>邮箱</td></tr>"
                    + "<tr style='background: #fff;height: 40px;'><td>"
                    + data.userName
                    + "</td><td>"
                    + data.dept.deptName
                    + "</td><td>"
                    + data.userInfo.telephone
                    + "</td><td>"
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
    // 实现查看
    function to_view(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }
    // 实现更新
    function to_update(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    // 确认删除
    function to_delete(url) {
        if (isAtLeastCheckOne()) {
            if (window.confirm("确认删除所选项目？")) {
                formSubmit(url, '_self');
            }
        } else {
            $("#envon #mess").html("请至少选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }
    function to_audit(url){
        if (isAtLeastCheckOne()) {
            if (window.confirm("确认通过所选项目？")) {
                formSubmit(url, '_self');
            }
        } else {
            $("#envon #mess").html("请至少选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
	}
</script>
<%@include file="../../alert.jsp"%>
</head>

<body>
	<form name="icform" method="post">
		<div id="menubar">
			<div id="middleMenubar">
				<div id="innerMenubar">
					<div id="navMenubar">
						<ul>
							<li id="startup"><a href="#" onclick="to_audit('taskListAction_audit');this.blur();">通过</a></li>
							<li id="view"><a href="#" onclick="to_view('taskListAction_toview');this.blur();">查看</a></li>
							<li id="update"><a href="#" onclick="to_update('taskListAction_toupdate');this.blur();">修改</a></li>
							<li id="delete"><a href="#" onclick="to_delete('taskListAction_delete');this.blur();">删除</a></li>
							<li id="back"><a href="${ctx}/taskListAction_list">返回</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" />
			我发布的任务列表
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
						</tr>
					</thead>
					<tbody class="tableBody">
						${page.links}

						<c:forEach items="${page.results}" var="o" varStatus="status">
							<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
								<td><input type="checkbox" name="id" value="${o.id}" /></td>
								<td style="cursor: pointer;" onclick="statusToAction('taskListAction_toview?id=${o.id}')"><a href="taskListAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
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
										<b><font color="red">未完成</font></b>
									</c:if>
									<c:if test="${o.state==1}">
										<b><font color="blue">已提交</font></b>
									</c:if>
									<c:if test="${o.state==2}">
										<b><font color="green">已完成</font></b>
									</c:if>
								</td>
								<td>${o.major}</td>
								<td><input type="button"
								onclick="showDetail('${o.userId}')" value="查看执行者详情"
								style="border: #4898d5 1px solid; background-color: #add8d3;color: #fff;border-radius: 4px;"></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>

		</div>
	</form>

</body>
</html>

