<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--<script type="text/javascript">
    //|------------------------------------------------------------------------------------
    //|
    //| 说明：JS弹出全屏遮盖的对话框(弹出层后面有遮盖效果，兼容主流浏览器)
    //|       实现了在最大化、拖动改变窗口大小和拖动滚动条时都可居中显示。
    //|
    //| 注意：主要使用EV_modeAlert和EV_closeAlert这两个函数；
    //|       (EV_modeAlert-弹出对话框，EV_closeAlert-关闭对话框)；
    //|       注意：使用时，请在body标签内(不要在其它元素内)写一div，
    //|       再给这div赋一id属性，如：id="myMsgBox"，
    //|       然后就可以调用EV_modeAlert('myMsgBox')来显示了。
    //|       还有，请给你这div设置css：display:none让它在开始时不显示
    //|------------------------------------------------------------------------------------
    //|
    //用来记录要显示的DIV的ID值
    var EV_MsgBox_ID=""; //重要
    //弹出对话窗口(msgID-要显示的div的id)
    function EV_modeAlert(msgID){
        //创建大大的背景框
        var bgObj=document.createElement("div");
        bgObj.setAttribute('id','EV_bgModeAlertDiv');
        document.body.appendChild(bgObj);
        //背景框满窗口显示
        EV_Show_bgDiv();
        //把要显示的div居中显示
        EV_MsgBox_ID=msgID;
        EV_Show_msgDiv();
    }

    //关闭对话窗口
    function EV_closeAlert(){
        var msgObj=document.getElementById(EV_MsgBox_ID);
        var bgObj=document.getElementById("EV_bgModeAlertDiv");
        msgObj.style.display="none";
        document.body.removeChild(bgObj);
        EV_MsgBox_ID="";
    }

    //窗口大小改变时更正显示大小和位置
    window.onresize=function(){
        if (EV_MsgBox_ID.length>0){
            EV_Show_bgDiv();
            EV_Show_msgDiv();
        }
    }

    //窗口滚动条拖动时更正显示大小和位置
    window.onscroll=function(){
        if (EV_MsgBox_ID.length>0){
            EV_Show_bgDiv();
            EV_Show_msgDiv();
        }
    }

    //把要显示的div居中显示
    function EV_Show_msgDiv(){
        var msgObj   = document.getElementById(EV_MsgBox_ID);
        msgObj.style.display  = "block";
        var msgWidth = msgObj.scrollWidth;
        var msgHeight= msgObj.scrollHeight;
        var bgTop=EV_myScrollTop();
        var bgLeft=EV_myScrollLeft();
        var bgWidth=EV_myClientWidth();
        var bgHeight=EV_myClientHeight();
        var msgTop=Math.round((bgHeight-msgHeight)/2)-260;
        var msgLeft=Math.round((bgWidth-msgWidth)/2)-190;
        msgObj.style.position = "absolute";
        msgObj.style.top      = msgTop+"px";
        msgObj.style.left     = msgLeft+"px";
        msgObj.style.zIndex   = "10001";

    }
    //背景框满窗口显示
    function EV_Show_bgDiv(){
        var bgObj=document.getElementById("EV_bgModeAlertDiv");
        var bgWidth=EV_myClientWidth();
        var bgHeight=EV_myClientHeight();
        var bgTop=EV_myScrollTop();
        var bgLeft=EV_myScrollLeft();
        bgObj.style.position   = "absolute";
        bgObj.style.top        = bgTop+"px";
        bgObj.style.left       = bgLeft+"px";
        bgObj.style.width      = bgWidth + "px";
        bgObj.style.height     = bgHeight + "px";
        bgObj.style.zIndex     = "10000";
        bgObj.style.background = "#777";
        bgObj.style.filter     = "progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=60,finishOpacity=60);";
        bgObj.style.opacity    = "0.6";
    }
    //网页被卷去的上高度
    function EV_myScrollTop(){
        var n=window.pageYOffset
            || document.documentElement.scrollTop
            || document.body.scrollTop || 0;
        return n;
    }
    //网页被卷去的左宽度
    function EV_myScrollLeft(){
        var n=window.pageXOffset
            || document.documentElement.scrollLeft
            || document.body.scrollLeft || 0;
        return n;
    }
    //网页可见区域宽
    function EV_myClientWidth(){
        var n=window.parent.document.documentElement.clientWidth
            || document.body.clientWidth || 0;
        return n;
    }
    //网页可见区域高
    function EV_myClientHeight(){
        var n=window.parent.document.documentElement.clientHeight
            || document.body.clientHeight || 0;
        return n;
    }
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
            EV_modeAlert('envon');
		}
	}
	// 实现更新
	function to_update(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
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
            EV_modeAlert('envon1');
		}
	}

	//实现提交
	function to_submit(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
            EV_modeAlert('envon');
		}
	}

	//实现取消
	function to_cancel(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
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
            EV_modeAlert('envon');
		}
	}
	
	//实现权限分配
	function to_module(url) {
		if (isOnlyChecked()) {
			formSubmit(url, '_self');
		} else {
            EV_modeAlert('envon');
		}
	}
	
	//实现报运
	function to_export(url) {
		if (isAtLeastCheckOne()){
			formSubmit(url, '_self');
		} else {
            EV_modeAlert('envon1');
		}
	}
	//实现电子报运
	function to_work_assign(url) {
		if (isOnlyChecked()){
			formSubmit(url, '_self');
			
		} else {
            EV_modeAlert('envon');
		}
	}
</script>--%>
<%@include file="js.jsp"%>
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
<%--
<div id="envon" style=" width:301px; background:url(../../skin/default/images/errmsg/err_bg.gif) no-repeat; overflow:hidden; display:none;height: 150px;">
	<div style="width: 220px;height: 70px;margin: 32px -22px 20px 40px;" align="center">请先选择一项并且只能选择一项，再进行操作！</div>
	<input type="button" value="关闭" name="" style="margin: -13px 120px;" onclick="EV_closeAlert()" />
</div>
<div id="envon1" style=" width:301px; background:url(../../skin/default/images/errmsg/err_bg.gif) no-repeat; overflow:hidden; display:none;height: 150px;">
	<div style="width: 220px;height: 70px;margin: 32px -22px 20px 40px;" align="center">请至少选择一项，再进行操作！</div>
	<input type="button" value="关闭" name="" style="margin: -13px 120px;width: 50px;" onclick="EV_closeAlert()" />
</div>--%>
