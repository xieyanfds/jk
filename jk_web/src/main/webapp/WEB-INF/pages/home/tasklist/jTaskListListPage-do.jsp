<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${ctx }/layer/layer.js"></script>
<script type="text/javascript">
    function further(uid,tid) {
		/*获取当前登录用户id*/
        var cur_u_id ="${_CURRENT_USER.id}";
        if(uid!=cur_u_id){
            $("#envon #mess").html("您不是任务执行者！不能下放任务！");
            EV_modeAlert('envon');
        }else{

            $.post("${ctx}/sysadmin/userAction_hasManager",{"id":cur_u_id},
                function(data) {
                    console.info(data);
                    if (data == 0) {
                        $("#envon #mess").html("您不是上级领导,不能将任务下放！");
                        EV_modeAlert('envon');
                    } else {
                        var s = "<form id='form01' action='${ctx}/taskListAction_updateToManager.action' method='post'>"
                            + "<select id='sel001' name='userId' style='font-weight: bold;'>";
                        $(data).each(function(i,n){
                            s+="<option value='"+n.id+"'>"+n.userName+"</option>";
                        })
                        s+= "</select></br></br>"
                            + "<input type='submit' style='border-radius: 4px;background: aliceblue;' value='下放任务'>"
                            + "<input type='hidden' name='id' value='"+tid+"'>"
                            + "</form>";

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
							<td class="tableHeader">任务下放</td>
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
										   onclick="further('${o.userId}','${o.id }')" value="任务向下发放"
										   style="border: #4898d5 1px solid; background-color: #add8d3;color: #fff;border-radius: 4px;"
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

