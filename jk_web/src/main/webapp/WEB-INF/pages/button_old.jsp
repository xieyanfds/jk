<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	//判断是否选且只选了一项
	function isOnlyChecked() {
		var checkBoxArray = document.getElementsByName('id');
		var count = 0;
		for (var index = 0; index < checkBoxArray.length; index++) {
			if (checkBoxArray[index].checked) {
				count++;
			}
		}
		// jquery
		// var count = $("[input name='id']:checked").size();
		if (count == 1)
			return true;
		else
			return false;
	}

	// 判断是否至少选择了一项
	function isAtLeastCheckOne() {
		var checkBoxArray = document.getElementsByName('id');
		var count = 0;
		for (var index = 0; index < checkBoxArray.length; index++) {
			if (checkBoxArray[index].checked) {
				count++;
			}
		}
		// jquery
		// var count = $("[input name='id']:checked").size();
		if (count <= 0)
			return false;
		else
			return true;
	}

	// 实现查看
	function to_view(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
	// 实现更新
	function to_update(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}

	// 确认删除
	function to_delete(url) {
		if (isAtLeastCheckOne()) {
			if (window.confirm("确认删除所选项目？")) {
				formSubmit(url, '_self');
			}
		} else {
			alert("请至少选择一项进行删除！");
		}
	}

	//实现提交
	function to_submit(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}

	//实现取消
	function to_cancel(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
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
			alert("请先选择一项并且只能选择一项，再进行操作！");
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
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
	
	//实现权限分配
	function to_module(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
	
	//实现报运
	function to_export(url) {
		if (isAtLeastCheckOne()){
			formSubmit(url, '_self');
		} else {
			alert("请至少选择一项，再进行操作！");
		}
	}
	//实现电子报运
	function to_work_assign(url) {
		if (isOnlyChecked()){
			formSubmit(url, '_self');
			
		} else {
			alert("请先选择一项并且只能选择一项，再进行操作！");
		}
	}
</script>
<ul>
	<c:set var="aaa" value="" />
	<!-- 遍历当前登陆用户的角色 -->
	<c:forEach items="${_CURRENT_USER.roles }" var="role">
		<!-- 遍历每个角色下的模块 -->
		<c:forEach items="${role.modules }" var="module">
			<c:if test="${(moduleId eq module.remark) and module.ctype==2 }">
				<!-- 去除不同角下具有的相同模块 -->
				<c:if test="${fn:contains(aaa,module.name) eq false }">
					<c:set var="aaa" value="${aaa},${module.name }" />
					<li id="${module.cwhich }"><a href="#"
						onclick="to_${module.cwhich}('${module.curl }');this.blur();">${module.cpermission }</a></li>
				</c:if>
			</c:if>
		</c:forEach>
	</c:forEach>

</ul>
