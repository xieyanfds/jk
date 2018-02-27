<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
<li id="view"><a href="#" onclick="formSubmit('exportAction_toview','_self');this.blur();">查看</a></li>
<li id="update"><a href="#" onclick="formSubmit('exportAction_toupdate','_self');this.blur();">修改</a></li>
<li id="delete"><a href="#" onclick="formSubmit('exportAction_delete','_self');this.blur();">删除</a></li>
<li id="submit"><a href="#" onclick="formSubmit('exportAction_submit','_self');this.blur();">提交</a></li>
<li id="cancel"><a href="#" onclick="formSubmit('exportAction_cancel','_self');this.blur();">取消</a></li>
<li id="work_assign"><a href="#" onclick="formSubmit('exportAction_export','_self');this.blur();">电子报运</a></li>
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
                    $("#envon #mess").html("抱歉，此报运单已上报，必须取消才能更改！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==2){
                    $("#envon #mess").html("抱歉，此报运单已电子报运，不能更改！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==3){
                    $("#envon #mess").html("抱歉，此报运单已装船，不能更改！");
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
                if(state==2 || state==3){
                    $("#envon #mess").html("抱歉，此报运单已电子报运或已装船，不可删除！！");
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
                    $("#envon #mess").html("抱歉，此报运单已经提交过！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==2){
                    $("#envon #mess").html("抱歉，该报运单已电子报运！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==3){
                    $("#envon #mess").html("抱歉，该报运单已装船！");
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
                    $("#envon #mess").html("抱歉，此报运单是取消状态！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==2){
                    $("#envon #mess").html("抱歉，该报运单已电子报运，不可取消！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==3){
                    $("#envon #mess").html("抱歉，该报运单已装船，不可取消！");
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
        //实现电子报运
        function to_work_assign(url) {
            if (isOnlyChecked()){
                var s = $("input:checked");
                var oid = s.val();
                var state = document.getElementById(oid).value;
                if(state==0){
                    $("#envon #mess").html("抱歉，此报运单是取消状态！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==2){
                    $("#envon #mess").html("抱歉，该报运单已是电子报运状态！");
                    EV_modeAlert('envon');
                    return;
                }else if(state==3){
                    $("#envon #mess").html("抱歉，该报运单已装船！");
                    EV_modeAlert('envon');
                    return;
                }
                formSubmit(url, '_self');

            } else {
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }
	</script>



<div class="textbox-title">
<img src="${ctx }/skin/default/images/icon/step.png"/>
出口报运列表
</div>

<br/>
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">报运号</td>
		<td class="tableHeader">货物数/附件数</td>
		<td class="tableHeader">信用证号</td>
		<td class="tableHeader">收货人及地址</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">目的港</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">价格条件</td>
		<td class="tableHeader">制单日期</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >

	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td style="cursor: pointer;" onclick="statusToAction('exportAction_toview?id=${o.id}')"><a href="exportAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.id}</td>
		<td align="center">
			${o.exportProducts.size()}
			/
			<c:set var="extNumber" value="0"></c:set><!-- 设置一个变量，用来累加，初始值0 -->
			<c:forEach items="${o.exportProducts}" var="ep">
			   <c:if test="${ep.extEproducts.size()!=0 }">
					<c:set var="extNumber" value="${extNumber + ep.extEproducts.size()}"/>
				</c:if>
			</c:forEach>
			${extNumber}
		</td>		
		<td>${o.lcno}</td>
		<td>${o.consignee}</td>
		<td>${o.shipmentPort}</td>
		<td>${o.destinationPort}</td>
		<td>${o.transportMode}</td>
		<td>${o.priceCondition}</td>
		<td><fmt:formatDate value="${o.inputDate }" pattern="yyyy-MM-dd"/></td>
		<td>
			<c:if test="${o.state==0}"><font color="red">草稿</font></c:if>
			<c:if test="${o.state==1}"><font color="green">已提交</font></c:if>
			<c:if test="${o.state==2}"><font color="#00bfff">已电子报运</font></c:if>
			<c:if test="${o.state==3}"><font color="rgb(55, 214, 75)">已装船</font></c:if>
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

