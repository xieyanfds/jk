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
<li id="view"><a href="#" onclick="javascript:toView()">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('roleAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="javascript:toUpdate()">修改</a></li>
<li id="delete"><a href="#" onclick="javascript:todelete()">删除</a></li>
<li id="people"><a href="#" onclick="javascript:toModule()" title="分配权限">权限</a></li>
</ul>--%>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
	  <div class="textbox-inner-header">
		  <div class="textbox-title">
			角色列表
		  </div>
	  </div>
  </div>
<div>

<br/>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<tr align="center">
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">名称</td>
		<td class="tableHeader">说明</td>
	</tr>
	${links }
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr>
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td style="cursor: pointer;" onclick="statusToAction('roleAction_toview?id=${o.id}')"><a href="roleAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.name}</td>
		<td>${o.remark}</td>
	</tr>
	</c:forEach>
</table>
</div>
 
</div>r
 
</div>
</form>
<%--<script type="text/javascript">
	window.onload=function(){
	    var table = document.getElementById("ec_table");
//	    alert(321)
//	    alert(table.firstElementChild.children[0].innerHTML)
        var trs = table.getElementsByTagName("tr");
        for (var i = 0; i < trs.length; i++) {
            var j = i + 1;
            if (j % 2 == 0) { //偶数行
                trs[i].className= "even";
                trs[i].onmouseover=function(){
                    this.className='highlight';
                }
                trs[i].onmouseout=function(){
                    this.className='even';
                }
            }else{
                trs[i].className= "odd";
                trs[i].onmouseover=function(){
                    this.className='highlight';
                }
                trs[i].onmouseout=function(){
                    this.className='odd';
                }
			}
        }
	}
</script>--%>
</body>
</html>

