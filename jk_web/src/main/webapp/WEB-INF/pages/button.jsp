<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

    //实现提交
    function to_submit(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    //实现取消
    function to_cancel(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    //实现打印
    function to_print(url) {
        if('${moduleId}'=="cargo_outProduct"){
            formSubmit(url, '_self');
            return;
        }
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    //实现新增
    function to_create(url) {
        formSubmit(url, '_self');
    }

    //实现角色分配
    function to_people(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    //实现权限分配
    function to_module(url) {
        if (isOnlyChecked()) {
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    //实现报运
    function to_export(url) {
        if (isAtLeastCheckOne()){
            formSubmit(url, '_self');
        } else {
            $("#envon #mess").html("请至少选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

</script>
<%@include file="alert.jsp"%>
<ul>
	<c:set var="aaa" value="" />
	<!-- 遍历当前登陆用户的角色 -->
	<%--<c:forEach items="${_CURRENT_USER.roles }" var="role">
		<!-- 遍历每个角色下的模块 -->
		<c:forEach items="${role.modules }" var="module">
			<c:if test="${(moduleRemark eq module.remark) and module.ctype==2 }">
				<!-- 去除不同角下具有的相同模块 -->
				<c:if test="${fn:contains(aaa,module.name) eq false }">
					<c:set var="aaa" value="${aaa},${module.name }" />
					<li id="${module.cwhich }"><a href="#"
						onclick="to_${module.cwhich}('${module.curl }');this.blur();">${module.cpermission }</a></li>
				</c:if>
			</c:if>
		</c:forEach>
	</c:forEach>--%>

	<c:forEach items="${ALL_PERMISSION }" var="module">
		<c:if test="${(moduleRemark eq module.remark) and module.ctype==2 }">
			<!-- 去除不同角下具有的相同模块 -->
			<c:if test="${fn:contains(aaa,module.name) eq false }">
				<c:set var="aaa" value="${aaa},${module.name }" />
				<li id="${module.cwhich }"><a href="#"
											  onclick="to_${module.cwhich}('${module.curl }');this.blur();">${module.cpermission }</a></li>
			</c:if>
		</c:if>
	</c:forEach>

</ul>

