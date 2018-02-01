<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
<script type="text/javascript">
	//查看
    function to_view(){
        if(isOnlyChecked()){
            $("#receiveId").empty();
            $.ajax({
                type: "POST",
                url:"${ctx}/messageAction_jsonToView",
                data:null,
				dataType:"json",
				async:false,
				success:function(data){
					$(data).each(function(i,n){
						$("#receiveId").append("<option value = "+n.id+">"+n.userInfo.name+"</option>");
					});
				}
			});
           /* $.post("${ctx}/messageAction_jsonToView",null,function(data){
                /!*$(data).each(function(i,n){
                    $("#receiveId").append("<option value = n.id>n.userInfo.name</option>");
                })*!/
			},"json");*/
            var id = $("input[name='id']:checked").val();
            $.post("${ctx}/messageAction_toview",{"id":id},function(data){
                $("#viewSpan #receiveId").find("option[value='"+data.receiveId+"']").attr("selected","selected");
                $("#viewSpan #messageTime").attr("value",data.messageTime);
                $("#viewSpan #title").attr("value",data.title);
                $("#viewSpan #message").text(data.message);
                layer.open({
                    type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
                    title:"查看留言",//标题
                    area: ['1000px', '600px'],//大小
                    shadeClose: true, //点击弹层外区域 遮罩关闭
                    content: $("#viewSpan").html()//内容
                });
            },"json");

        }else{
            $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
            EV_modeAlert('envon');
        }
    }

    function to_create(){
        layer.open({
            type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
            title:"新增留言",//标题
            area: ['1000px', '600px'],//大小
            shadeClose: true, //点击弹层外区域 遮罩关闭
            content: $("#createSpan").html()//内容
        });
    }

	//实现更新
	function to_update() {
		if (isOnlyChecked()) {
			var s = $("input:checked");  
			var oid = s.val();
            var state = document.getElementById(oid).value;
            if(state!=1){
                alert("已发送的不能修改!!!")
			}else{
				formSubmit('messageAction_toupdate','_self')
			} 
			
		}else {
				alert("请先选择一项并且只能选择一项，再进行操作！");
			
		}
		
	}
	
	//删除请求
	function toDelete() {
		if (isChecked()) {
			formSubmit('messageAction_delete','_self')
		} else {
			alert("请先选择一项或者多项，再进行操作！");
		}
	}
	// 发送请求
	function toSend() {
	// js 自带for循环
		/* var s = document.getElementsByName("t01"); */
 		if (isChecked()) {
			 var s = $("input:checked:gt(0)") 
			 for(var i=0;i<s.length;i++){
				 var state =$(s[i]).attr("a"); 
					if(state!=0){
						alert("已发送的项目不能再次发送！");
						return ;
					}
			}
		 	formSubmit('messageAction_submit','_self');
		}else {
			alert("请先选择一项或者多项，再进行操作！");
		}
		
	}
</script>	

</head>
<%@include file="../../alert.jsp"%>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
	<div id="innerMenubar">
	  <div id="navMenubar">
		<%--<ul>
		<li id="view"><a href="#" onclick="toView();this.blur();">查看</a></li>
		<li id="new"><a href="#" onclick="formSubmit('messageAction_tocreate','_self');this.blur();">新增</a></li>
		<li id="update"><a href="#" onclick="toUpdate();this.blur();">修改</a></li>
		<li id="delete"><a href="#" onclick="toDelete();this.blur();">删除</a></li>
		<li id="people"><a href="#" onclick="formSubmit('messageAction_listReceive','_self');this.blur();">我的</a></li>
		</ul>--%>
		<%@include file="../layerButton.jsp"%>
	  </div>
	</div>
</div>
</div>
   
<div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
	我的留言板
</div>

<br/>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">接收人</td>
		<td class="tableHeader">标题</td>
		<td class="tableHeader">时间</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${page.links}
	
	<c:forEach  items="${page.results}" var="o" varStatus="status">
	<tr  class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td><a href="messageAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.receive}</td>
		<td>${o.title}</td>
		<td>${o.messageTime}</td>
		<td>
			<c:if test="${o.state==1}"><font color="red"><b>未读</b></font></c:if>
			<c:if test="${o.state==2}"><font color="green"><b>已读</b></font></c:if>
			<input type="hidden" value="${o.state}" id="${o.id}">
		</td>
		
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
</div>
</form>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++查看页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="viewSpan"><form name="icform" method="post">

	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
		留言板
	</div>



	<div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">接收人：</td>
				<td class="tableContent">
					<select id="receiveId" name="receiveId" disabled>
						<option value = "" >--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">时间：</td>
				<td class="tableContent">
					<input class="laydate-icon" id="start" type="text" name="pre_time" value="" placeholder="请点击此处选择时间" readonly/>
					<%--
					<input id="messageTime" type="text" style="width:90px;" name="messageTime"
						   value="${messageTime }"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});" readonly/>--%>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">标题：</td>
				<td class="tableContent">
					<input id="title" type="text" name="title" value="${title}" style="width: 70%" readonly/>
				</td>

			</tr>
			<tr>
				<td class="columnTitle">留言：</td>
				<td class="tableContent">
					<textarea id="message" name="message" style="height: 200px;width: 70%" readonly="readonly" >${message}</textarea></td>
			</tr>
		</table>
	</div>

</form>
</span>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++新增页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="createSpan"><form name="icform" method="post" onsubmit="return test()">

	<div id="menubar">
		<div id="middleMenubar">
			<div id="innerMenubar">
				<div id="navMenubar">
					<ul>
						<li id="save"><a href="#" onclick="formSubmit('messageAction_insert','_self');this.blur();">保存</a></li>
						<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
		新增留言
	</div>



	<div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">接收人：</td>
				<td class="tableContent">
					<select name="receiveId">
						<c:forEach items="${userList }" var="u">
							<option value = "${u.id }">${u.userInfo.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<td class="columnTitle">时间：</td>
			<td class="tableContent">
				<input type="text" style="width:90px;" name="messageTime"
					   value=""
					   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
			</td>

			<tr>
				<td class="columnTitle">标题：</td>
				<td class="tableContent"><input type="text" name="title" style="width: 70%" value="${message}"/></td>
			</tr>
			<tr>
				<td class="columnTitle">留言：</td>
				<td class="tableContent">
					<textarea name="message" style="height: 200px;width: 70%" >${message}</textarea>
				</td>
			</tr>
		</table>
	</div>
</form>
</span>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++修改页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="updateSpan"><form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

	<div id="menubar">
		<div id="middleMenubar">
			<div id="innerMenubar">
				<div id="navMenubar">
					<ul>
						<li id="save"><a href="#" onclick="formSubmit('messageAction_update','_self');this.blur();">保存</a></li>
						<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="textbox-title">
		<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
		修改我的留言
	</div>



	<div>
		<table class="commonTable" cellspacing="1">

			<tr>
				<td class="columnTitle">接收人：</td>
				<td class="tableContent">
					<select name="receiveId">
						<c:forEach items="${userList }" var="u">
							<c:if test= "${u.id==receiveId}">
								<option value = "${u.id }" selected>${u.userInfo.name}</option>
							</c:if>
							<c:if test= "${u.id!=receiveId}">
								<option value = "${u.id }" >${u.userInfo.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">时间：</td>
				<td class="tableContent">
					<input type="text" style="width:90px;" name="messageTime"
						   value="${messageTime }"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
			</tr>
			<tr>
				<td class="columnTitle">标题：</td>
				<td class="tableContent"><input type="text" name="title" style="width: 70%" value="${title}"/></td>
			</tr>

			<tr>
				<td class="columnTitle">留言：</td>
				<td class="tableContent">
					<textarea name="message" style="height: 200px;width: 70%">${message}</textarea>
			</tr>
		</table>
	</div>


</form>
</span>

</body>
</html>

