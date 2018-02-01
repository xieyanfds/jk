<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${ctx }/layer/layer.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js" ></script>
<ul>
	<c:set var="aaa" value="" />
	<!-- 遍历当前登陆用户的角色 -->
	<c:forEach items="${_CURRENT_USER.roles }" var="role">
		<!-- 遍历每个角色下的模块 -->
		<c:forEach items="${role.modules }" var="module">
			<c:if test="${(moduleRemark eq module.remark) and module.ctype==2 }">
				<!-- 去除不同角色下具有的相同模块 -->
				<c:if test="${fn:contains(aaa,module.name) eq false }">
					<c:set var="aaa" value="${aaa},${module.name }" />
					<li id="${module.cwhich }"><a href="#"
						onclick="to_${module.cwhich}();this.blur();">${module.cpermission }</a></li>
				</c:if>
			</c:if>
		</c:forEach>
	</c:forEach>
</ul>
