<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/left.css" media="all"/>

	<script language="javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/components/jquery-ui/jquery-1.2.6.js"></script>
	<script type="text/javascript" src="${ctx}/skin/default/js/toggle.js"></script>
 
	
    <script language="javascript">
    	$().ready(function(){
			$(fastMenu).hide();
			//document.getElementById('aa_3').click();	//默认打开我的留言板页面
    	});
    	
    	function showMenu( who ){
    		if(who=="fastMenu"){
    			$(fastMenu).show();
    			$(customerMenu).hide();
    		}else if(who=="customerMenu"){
    			$(customerMenu).show();
    			$(fastMenu).hide();
    		}
    	}
    </script>
 
    
</head>
 
<body id="left_frame">
<div class="PositionFrame_black" id="PositionFrame"></div>
 
 
<div id="sidebar1" class="sidebar">
	<div class="sidebar_t">
		<div class="sidebar_t_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_t_r"></div>
	</div>
	   <div class="panel">
	       <div class="panel_icon"><img src="${ctx}/skin/default/images/icon/user2.png" /></div>
	       <div class="panel-header">
	        <div class="panel-title">个人工作台</div>
	        <div class="panel-content">
				<ul>
					<li><a href="${ctx}/homeAction_tomain.action?moduleName=home" target="main" id="aa_3" onclick="linkHighlighted(this)">我的留言板</a></li>
					<li><a href="${ctx}/workFlowAction_list.action" target="main" id="aa_4" onclick="linkHighlighted(this)">我的代办任务</a></li>
					<li><a href="${ctx}/feedbackAction_list.action" target="main" id="aa_5" onclick="linkHighlighted(this)">意见反馈</a></li>
				</ul>
	        </div>
	        
	       </div>
	   </div>
    <div class="sidebar_t">
		<div class="sidebar_b_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_b_r"></div>
	</div>
</div>
 
 
 
 
 
<div id="sidebar2" class="sidebar">
	<div class="sidebar_t">
		<div class="sidebar_t_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_t_r"></div>
	</div>
 	    <div class="panel">
	        <div class="panel_icon"><img src="${ctx}/skin/default/images/icon/cubes.png" /></div>
	        <div class="panel-header">
		    	<div class="panel-title">我的常用功能</div>
					<div style="margin-top:5px;"></div>
					<!-- 以上为永久固定栏目，以下为活动栏目 -->
	        <div style="border-bottom:1px dotted #cee1df;">
	         切换:<a href="#" onmousemove="javascript:showMenu('fastMenu');">快捷菜单</a>
	        /
	        <a href="#" onmousemove="javascript:showMenu('customerMenu');">自定义菜单</a>
	        </div>
				<div id="fastMenu">
					<div class="FastMenu">
						<c:forEach items="${sessionScope.list}" var="o">
							<div class="panel-content"></div>
							<a href="${ctx}/${o.ico}${o.curl}" onclick="linkHighlighted(this)" target="main" id="aa_${o.id}"><font color="gray">${o.name}</font></a>
						</c:forEach>
						<a href="#" class="DelFastMenu"><font color="gray">清除常用功能列表</font></a>
					</div>

				</div>

				<div id="customerMenu">
					<div class="FastMenu"><img src="${ctx}/skin/default/images/notice.gif" style="margin-right:5px;" border="0" />
						<c:forEach items="${sessionScope.shortList}" var="o">
							<div class="panel-content"></div>
							<a href="${ctx}/${o.curl}" onclick="toto('${o.ico}'),linkHighlighted(this)" target="main" id="aa_${o.id}"><font color="gray">${o.name}</font></a>
						</c:forEach>
						<a href="${ctx}/sysadmin/roleAction_toUser" target="main" class="DelFastMenu"><font color="gray">去定义您的菜单？</font></a>
					</div>

				</div>
	        </div>
	    </div>
	 <div class="sidebar_t">
		<div class="sidebar_b_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_b_r"></div>
	</div>    
</div>
<script  type="text/javascript">
    function toto(obj){
        var url = "homeAction_tomodule";
        var param = {"moduleName":obj};
        $.post(url,param,function(){

        })
    }
</script>
 
<!-- begin1  -->
<div id="sidebar3" class="sidebar">
	<div class="sidebar_t">
		<div class="sidebar_t_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_t_r"></div>
	</div>
    <div class="panel">
    	<div class="panel_icon"><img src="${ctx}/skin/default/images/icon/businessman2.png" /></div>
        <div class="panel-header">
        <div class="panel-title">
		用户设定
        </div>
        
        <div class="panel-content">
			<ul>
				<li><a href="${ctx}/ownUserAction_toupdate.action" id="aa_1" target="main" onclick="linkHighlighted(this)">个人信息修改</a></li>
				<li><a href="${ctx}/cargo/feedbackAction_list.action" id="aa_2" target="main" onclick="linkHighlighted(this)">系统使用反馈</a></li>
			</ul>
        </div>
    </div>
    </div>
    <div class="sidebar_t">
		<div class="sidebar_b_l"></div>
		<div class="sidebar_t_c"></div>
		<div class="sidebar_b_r"></div>
	</div>
</div>
<!-- end1 -->
 
</body>
</html>