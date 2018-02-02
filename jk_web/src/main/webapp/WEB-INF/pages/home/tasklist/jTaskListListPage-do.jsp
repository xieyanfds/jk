<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">

    // 实现查看
    function to_view(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    // 解决
    function to_slove(url) {
        if (isAtLeastCheckOne()) {
            if (window.confirm("确认解决了所选项目？")) {
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
							<li id="view"><a href="#" onclick="to_view('taskListAction_toview');this.blur();">查看</a></li>
							<li id="startup"><a href="#" onclick="to_slove('taskListAction_slove');this.blur();">解决</a></li>
							<li id="back"><a href="${ctx}/taskListAction_list">返回</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>

		<div class="textbox-title">
			<img src="${ctx }/skin/default/images/icon/currency_yen.png" />
			待办任务列表
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

