<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
  <%@include file="/WEB-INF/pages/button.jsp" %>
<%--<ul>
<li id="view"><a href="#" onclick="formSubmit('financeAction_toview','_self');this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('financeAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="formSubmit('financeAction_toupdate','_self');this.blur();">修改</a></li>
<li id="delete"><a href="#" onclick="formSubmit('financeAction_delete','_self');this.blur();">删除</a></li>
<li id="submit"><a href="#" onclick="formSubmit('financeAction_submit','_self');this.blur();">提交</a></li>
<li id="cancel"><a href="#" onclick="formSubmit('financeAction_cancel','_self');this.blur();">取消</a></li>
</ul>--%>
  </div>
</div>
</div>
</div>

<script type="text/javascript">
        // 实现更新
        function to_update(url) {
            if (isOnlyChecked()) {
                var s = $("input:checked");
                var oid = s.val();
                var state = document.getElementById(oid).value;
                if(state==1){
                    $("#envon #mess").html("抱歉，此财务单已提交，必须取消才能更改！");
                    EV_modeAlert('envon');
                    return;
                }else{
                    formSubmit(url,'_self')
                }
            } else {
                $("#envon #mess").html("抱歉，请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }

        // 确认删除
        function to_delete(url) {
            if (!isAtLeastCheckOne()) {
                $("#envon #mess").html("抱歉，请至少选择一项进行删除！！");
                EV_modeAlert('envon');
                return;
            }
            var checks = $("input[name='id']:checked");
            for(var i=0;i<checks.size();i++){
                var state = document.getElementById(checks[i].value).value;
                if(state==1){
                    $("#envon #mess").html("抱歉，此财务单已提交，请先取消！！");
                    EV_modeAlert('envon');
                    return;
                }
            }
			/*checks.each(function(i,n){
			 var state = document.getElementById(n.value).value;
			 if(state==2){
			 $("#envon #mess").html("抱歉，已报运的购销合同不能删除！！");
			 EV_modeAlert('envon');
			 return;
			 }
			 })*/
            if (window.confirm("确认删除所选项目？")) {
                formSubmit(url, '_self');
            }
        }
        //实现提交
        function to_submit(url) {
            if (isOnlyChecked()) {
                var s = $("input:checked");
                var oid = s.val();
                var state = document.getElementById(oid).value;
                if(state==1){
                    $("#envon #mess").html("抱歉，此财务单已经提交过！");
                    EV_modeAlert('envon');
                    return;
                }else{
                    formSubmit(url,'_self')
                }
            } else {
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }

        //实现取消
        function to_cancel(url) {
            if (isOnlyChecked()) {
                var s = $("input:checked");
                var oid = s.val();
                var state = document.getElementById(oid).value;
                if(state==0){
                    $("#envon #mess").html("抱歉，此财务单是取消状态！！");
                    EV_modeAlert('envon');
                    return;
                }else{
                    formSubmit(url,'_self')
                }
            } else {
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }
	</script>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    财务报运列表
  </div> 
  
<div>

<br/>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr align="left">
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">制单人</td>
		<td class="tableHeader">制单日期</td>
		<td class="tableHeader">状态</td>
		
	</tr>
	</thead>
	<tbody class="tableBody" align="left" >

	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" align="center">
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td style="cursor: pointer;" onclick="statusToAction('financeAction_toview?id=${o.id}')"><a href="financeAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.inputBy}</td>
		<td>${o.inputDate}</td>
		<td>
			<c:if test="${o.state==0}">草稿</c:if>
			<c:if test="${o.state==1}"><b><font color="green">已提交</font></b></c:if>
			<input type="hidden" value="${o.state}" id="${o.id}">
		</td>
		
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
</div>
	<%@include file="../../page.jsp"%>
</form>
</body>
</html>

