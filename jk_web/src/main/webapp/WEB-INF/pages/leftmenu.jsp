<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script language="javascript" src="${ctx}js/common.js"></script>
<script type="text/javascript" src="${ctx}/components/jquery-ui/jquery-1.2.6.js"></script>
<script type="text/javascript" src="${ctx}/skin/default/js/toggle.js"></script>
<ul>
	<c:set var="aaa" value=""/>
	<!-- 遍历当前登录用户的角色列表 -->
	<c:forEach items="${_CURRENT_USER.roles }" var="role">
	<!-- 遍历每个角色下的模块 -->
		<c:forEach items="${role.modules }" var="module">
		     <c:if test="${(moduleName eq module.remark) and module.ctype==1  }">
				 <!-- 如果该模块没有输出过，则要进行输出，否则这个模块就不输出 -->
			     <c:if test="${fn:contains(aaa,module.cpermission) eq false }">
		            <c:set var="aaa" value="${aaa},${module.cpermission }"/>
		            <li><a href="${ctx}/${module.curl}" onclick="toto('${module.ico}'),linkHighlighted(this)" target="main" id="aa_${module.id}">${module.cpermission }</a></li>
		         </c:if>
		     </c:if>
		            
	 	</c:forEach>
	</c:forEach>
</ul>
<script  type="text/javascript">
    function toto(obj){
        var url = "homeAction_tomodule";
        var param = {"moduleName":obj};
        $.post(url,param,function(){

		})
    }
</script>