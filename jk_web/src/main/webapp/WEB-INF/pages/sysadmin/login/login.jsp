<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>用户登录</title>
	<meta name="author" content="DeathGhost" />
	<link rel="stylesheet" type="text/css" href="${cgx}/loginCss/style.css" />
	<style>
		body {
			height: 100%;
			background: #0f7d77;
			/*background: #00897F;*/
			/*background: #169f84;*/
			/*background: url(../../../../skin/default/images/login/bg.png) no-repeat;*/
			background-size:cover;
			overflow: hidden;
		}

		canvas {
			z-index: -1;
			position: absolute;
		}
	</style>
	<script src="${cgx }/loginJs/jquery.js"></script>
	<script src="${cgx }/loginJs/verificationNumbers.js"></script>
	<script src="${cgx }/loginJs/Particleground.js"></script>
	<script type="text/javascript">
        if (top.location != self.location) {
            top.location = self.location;
        }
	</script>
	<script>
        //用户名判空
        function checkUsername() {
            var username = $("#userName").val();
            if (username.trim().length==0) {
                $("#divId").html("<font color='red' size='4px'>用户名不能为空!!!</font>");
            }else{
                $("#divId").html("");
            }
        }
        //密码判空
        function checkPwd() {
            var password = $("#password").val();
            if (password.trim().length==0) {
                $("#divId").html("<font color='red' size='4px'>密码不能为空!!!</font>");
            }else{
                $("#divId").html("");
            }
        }
        //页面加载加载样式
        $(document).ready(function() {
            //粒子背景特效
            $('body').particleground({
                dotColor : '#5cbdaa',
                lineColor : '#5cbdaa'
            });
        });
	</script>
</head>
<body>
<form action="${pageContext.request.contextPath }/login.action"
	  method="post" target="_self">
	<dl class="admin_login">
		<dt>
			<strong><font color="black">商务综合管理平台</font></strong> <em>Management
			System</em>
		</dt>
		<dd class="user_icon">
			<input type="text" placeholder="用户名" id="userName"
				   class="login_txtbx" name="username" onFocus="this.select();"
				   title="请您输入用户名" onblur="checkUsername()"/>
		</dd>
		<dd class="pwd_icon">
			<input type="password" placeholder="密码" class="login_txtbx"
				   name="password" id="password"
				   onfocus="$('#ts').css('display','none');this.select();"
				   onKeyDown="javascript:if(event.keyCode==13){ submitFind(); }"
				   title="请您输入密码" onblur="checkPwd()" />
		</dd>
		<dd>
			<input type="submit" value="立即登陆" class="submit_btn" />
		</dd>
		<dd>
			<div class="msgtip" align="center" id="divId">
				<c:if test="${!empty errorInfo}">
					<font color="red" size="4px">${errorInfo}</font>
				</c:if>
			</div>
		</dd>
		<dd>
			<p>© 2015-2016 DeathGhost 版权所有</p>
			<p>陕B2-20080224-1</p>
		</dd>
	</dl>
</form>
</body>
<script type="text/JavaScript">
    document.getElementById('login_main').userName.focus();
</script>
</html>
