<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
	<title></title>
    <link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/default.css" media="all"/>
	<script type="text/javascript" src="/components/jquery-ui/jquery-1.2.6.js"></script>
	<script language="javascript" src="${ctx}/js/common.js"></script>
	<link rel="stylesheet" href="${ctx }/skin/default/css/index.css" />
	<script type="text/javascript" src='${ctx }/skin/js/jquery-1.7.2.min.js'></script>
	<script type="text/javascript" src='${ctx }/skin/js/index.js'></script>
	<script type="text/javascript" src='${ctx }/js/circle_JT_min.js'></script>
<style>
	.curbody{ CURSOR: url(${ctx}/images/olmsg/shubiao.ani);background:url(${ctx}/images/olmsg/pic738x571.jpg); }
	.msgcontent{ width:218px;overflow:hidden;word-break:break-all;padding:10px;font-size:14px;color:#339966;font-family:Tahoma;line-height:180%; }
	.msgcontent p{ text-indent:0px;}
	/*.msgcontent ul( margin:0px;}*/
	.msgbackcontent{ width:218px;overflow:hidden;word-break:break-all;padding:10px;font-size:14px;color:#339966;font-family:Tahoma;line-height:180%; }
	.msgbackcontent p{ text-indent:0px;}
	/*.msgbackcontent ul( margin:0px;}*/
	li{ text-indent:0px;margin:0px;list-style:default; }
</style>
	
</head>
<%--<script language="javascript">
if(top.location!=main.location){
	top.location = main.location;
}
//-- 控制层移动start of script -->
var Obj='';
var index=10000;//z-index;
var color='';
var str='';
/*document.onmouseup=MUp
document.onmousemove=MMove*/
 
function MMove(){
	if(Obj!=''){
		document.all(Obj).style.left=event.x-pX;
		document.all(Obj).style.top=event.y-pY;
	}
}
 
function MUp(){
	if(Obj!=''){
		document.all(Obj).releaseCapture();
		Obj='';
	}
	var srcEle = event.srcElement;
	
	var children = srcEle.children;
	if(children.length>0){
		children[1].value = "1";		//isChange
		children[2].value = event.x-pX;
		children[3].value = event.y-pY;
	}
}
 
function MDown(objtd,id){
	Obj=id
	document.all(Obj).setCapture()
	pX = event.x-document.all(Obj).style.pixelLeft;
	pY = event.y-document.all(Obj).style.pixelTop;
}
 
//-- 控制层移动end of script -->
//获得焦点;
function getFocus(obj)
{
       if(obj.style.zIndex!=index)
       {
               index = index + 2;
               var idx = index;
               obj.style.zIndex=idx;
               obj.nextSibling.style.zIndex=idx-1;
       }
}
 
//针对未已阅的、未回复的、工作任务
function msgrevoke( id ){
	if(confirm("是否确定要撤销此条信息?")){
		//_Submit("/home/olmsgRevokeAction.do?flag=revoke&id="+id,null,"撤销");
	}
}
 
//需回复的留言
function msgback( id ){
	//_Submit("/home/olmsgUpdateAction.do?flag=back&id="+id,null,"回复");
}
 
function msgupdate( id , flag ){
	if(flag=="read"){
		if(!confirm("是否确定已阅此条信息?")){
			return false;
		}
	}else if(flag=="accept"){
		if(!confirm("是否确定接受此任务?")){
			return false;
		}
	}else if(flag=="fail"){
		if(!confirm("是否确定此任务未完成?")){
			return false;
		}
	}else if(flag=="success"){
		if(!confirm("是否确定此任务已完成?")){
			return false;
		}
	}else if(flag=="finished"){
		if(!confirm("是否确定完成?")){
			return false;
		}
	}
	//_Submit("/home/olmsgUpdateAction.do?flag="+flag+"&id="+id,null,"修改");
	
}
 
function msgdel( id ){
	if(confirm("是否确定要删除此条信息?")){
		_Submit("/home/olmsgDeleteAction.do?delId="+id,null,"删除");
	}
}
 
function msgstate( id , flag ){
	if(flag=="read"){
		if(!confirm("是否确定已阅此条信息?")){
			return false;
		}
	}else if(flag=="accept"){
		if(!confirm("是否确定接受此任务?")){
			return false;
		}
	}else if(flag=="fail"){
		if(!confirm("是否确定此任务未完成?")){
			return false;
		}
	}else if(flag=="success"){
		if(!confirm("是否确定此任务已完成?")){
			return false;
		}
	}else if(flag=="finished"){
		if(!confirm("是否确定完成?")){
			return false;
		}
	}
	//_Submit("/home/olmsgStateAction.do?flag="+flag+"&delId="+id,null,"已阅");
}
 
function changRowColor(obj){
//	obj.removeAttribute("className");
//	alert(obj.className);
//	obj.setAttribute("bgcolor","#FFECB0");
//	obj.sytle.backgroundColor = "#FFECB0";
}
 
function removeOverRowColor(obj){
//	alert(obj.getAttribute("style"));
}
 
function killErrors() {
	return true;
}
 
window.onerror = killErrors;
</script>--%>
<script type="text/javascript">
	function to_view(obj){
	    location.href='messageAction_toview_?id='+obj;
	}
</script>
 
<body class="curbody">

<div id="dom"></div>
<script type="text/javascript">
	$.circleJt({
		domId:'dom',//必须
		radius:100,//必须
		pbColor:'#00796b',//必需
		pbWidth:10,//非必需
		value:0,//必须
		totalValue:1000,//非必需
		percentage:true,//非必需
		fontSize:30,

		clock:true,//如果clock为真的时候，上述属性除value,totalValue,percentage均全都无效
		digitalWatch:true//以电子表形式显示
	});
</script>
<style>
	#dom #Circle_JT_cancvsDom{
		position: absolute;
		left: 1.289px;
	}
</style>
<!-- 工具栏部分 ToolBar -->
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
		<ul>
			<%--<input id="send"  type="text"/>--%>
			<%--<li id="save"><a href="#" title="保存留言窗口的位置信息">位置</a></li>
			<li id="stat"><a href="#">历史</a></li>--%>

		</ul>
    </div>
</div>
</div>
</div>
 
<logic:notEmpty name="olmsgList">
 
	
		
	<div id='ff8080813d00613e013d0067909e0009' style='position:absolute;right:2px;top:14px;z-index:1001; height:164px;background:none;' onmousedown='getFocus(this)'>
			<table border=0 cellspacing="0" cellpadding="0" width="220">
				<tr>
					<td style='cursor:move;' onmousedown="MDown(this,'ff8080813d00613e013d0067909e0009')" background="${ctx}/images/olmsg/C0FFE51.gif" height="45">
						<input type="hidden" name="id" class="input" value="ff8080813d00613e013d0067909e0009" />
						<input type="hidden" name="isChange" class="input" value="0" />
						<input type="hidden" name="posX" class="input" value="122" />
						<input type="hidden" name="posY" class="input" value="97" />
						&nbsp;
					</td>
				</tr>
				<tr>
					<td style='cursor:move;white-space:nowrap;' width='100%' onmousedown="MDown('ff8080813d00613e013d0067909e0009')" background="${ctx}/images/olmsg/C0FFE52.gif" >
						<div style="float:center;width:130px;padding-left:7px;font-family:Tahoma;color:gray;font-style : oblique;">
						</div>
						<%--<div style="float:right;width:80px;text-align:right;padding-right:7px;">

							<a style='cursor:pointer;' title="编辑" onclick="msgupdate('ff8080813d00613e013d0067909e0009','edit')"><img src="${ctx}/images/olmsg/doc_edit.gif"/></a>

							<a style='cursor:pointer;' title="删除" onclick="msgdel('ff8080813d00613e013d0067909e0009')"><img src="${ctx}/images/olmsg/doc_del.gif"/></a>

						</div>--%>
					</td>
				</tr>
				<tr>
					<td background="${ctx}/images/olmsg/C0FFE52.gif">
						<div class="msgcontent">
						欢迎使用综合管理平台<br/>双击查看留言
						</div>
					</td>
				</tr>
				<tr>
					<td id="tagBPic1" background="${ctx}/images/olmsg/C0FFE53.gif" height="63">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="50" align="center">
								<img border="0" src="${ctx}/images/olmsg/2.gif">
								</td>
								<td style="text-align:right;padding-right:8px;" nowrap>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	
		
		<%--<div id='ff8080813d00613e013d00681494000a' style='position:absolute;left:442px;top:91px;z-index:1002; height:164px;background:none;' onmousedown='getFocus(this)'>
			<table border=0 cellspacing="0" cellpadding="0" width="220">
				<tr>
					<td style='cursor:move;' onmousedown="MDown(this,'ff8080813d00613e013d00681494000a')" background="${ctx}/images/olmsg/FFE7E81.gif" height="45">
						<input type="hidden" name="id" class="input" value="ff8080813d00613e013d00681494000a" />
						<input type="hidden" name="isChange" class="input" value="0" />
						<input type="hidden" name="posX" class="input" value="442" />
						<input type="hidden" name="posY" class="input" value="91" />
						&nbsp;
					</td>
				</tr>
				<tr>
					<td style='cursor:move;white-space:nowrap;' width='100%' onmousedown="MDown('ff8080813d00613e013d00681494000a')" background="${ctx}/images/olmsg/FFE7E82.gif" >
						<div style="float:left;width:130px;padding-left:7px;font-family:Tahoma;color:gray;font-style : oblique;">
							2013-02-22 13:37
						</div>
						<div style="float:right;width:80px;text-align:right;padding-right:7px;">
							<a style='cursor:pointer;' title="编辑" onclick="msgupdate('ff8080813d00613e013d00681494000a','edit')"><img src="${ctx}/images/olmsg/doc_edit.gif"/></a>
							<a style='cursor:pointer;' title="删除" onclick="msgdel('ff8080813d00613e013d00681494000a')"><img src="${ctx}/images/olmsg/doc_del.gif"/></a>
						</div>
					</td>
				</tr>
				<tr>
					<td background="${ctx}/images/olmsg/FFE7E82.gif">
					<div class="msgcontent">
					本系统实现货运企业日常管理<br />
包括合同、报运、装箱、委托、发票等业务
					</div>
					</td>
				</tr>
				<tr>
					<td id="tagBPic2" background="${ctx}/images/olmsg/FFE7E83.gif" height="63">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="50" align="center">
								<img border="0" src="${ctx}/images/olmsg/0.gif">
								</td>
								<td style="text-align:right;padding-right:8px;" nowrap>
								[备忘]
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>--%>
	<div id='main'>
		<c:forEach items="${megList }" var="meg" varStatus="v">
			<input type="hidden" name="${v.index}">
			<dl class='paper a${v.count }' style="height: 300px;" ondblclick="to_view('${meg.id}')">
				<dt class="name">
					<span class='username'>${meg.createName }</span>
					<span class='num'>${meg.title }</span>
				</dt>
				<dt class='content'>${meg.message }</dt>
				<dt class='bottom'>
					<span class='time'><fmt:formatDate value='${meg.createTime }' pattern='yyyy-MM-dd HH:mm:ss' /></span>
					<span class='close'></span>
				</dt>
			</dl>

		</c:forEach>

	</div>

	<div id='send-form'>
		<p class='title'>
			<span>写下你的留言</span>
			<a href="#" id='close'>关闭</a>
		</p>
		<form action="" name='wish' id="message_form" method="post">
			<p>
				<label for="content">留言：(您还可以输入&nbsp;<span id='font-num'>50</span>&nbsp;个字)
				</label>
				<textarea name="content" id='content'></textarea>

				<div id='phiz'>
                    <img src="${ctx }/skin/images/phiz/zhuakuang.gif" alt="抓狂" /> <img
                        src="${ctx }/skin/images/phiz/baobao.gif" alt="抱抱" /> <img
                        src="${ctx }/skin/images/phiz/haixiu.gif" alt="害羞" /> <img
                        src="${ctx }/skin/images/phiz/ku.gif" alt="酷" /> <img
                        src="${ctx }/skin/images/phiz/xixi.gif" alt="嘻嘻" /> <img
                        src="${ctx }/skin/images/phiz/taikaixin.gif" alt="太开心" /> <img
                        src="${ctx }/skin/images/phiz/touxiao.gif" alt="偷笑" /> <img
                        src="${ctx }/skin/images/phiz/qian.gif" alt="钱" /> <img
                        src="${ctx }/skin/images/phiz/huaxin.gif" alt="花心" /> <img
                        src="${ctx }/skin/images/phiz/jiyan.gif" alt="挤眼" />
                </div>


			</p>
			<span id='send-btn'
				  onclick="formSubmit('messageAction_insert','_self');this.blur();">添加</span>
		</form>
	</div>

	<!--[if IE 6]>
	<script type="text/javascript" src="js/iepng.js"></script>
	<script type="text/javascript">
	DD_belatedPNG.fix('#send,#close,.close');
	</script>
	<![endif]-->

</logic:notEmpty>
 
 
</body>
<script type="text/javascript">
    top.leftFrame.location.href="homeAction_toleft.action?moduleName=home";
</script>
</html>

