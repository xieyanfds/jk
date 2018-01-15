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
		<li id="view"><a href="#" onclick="formSubmit('productAction_toview','_self');this.blur();">查看</a></li>
		<li id="new"><a href="#" onclick="formSubmit('productAction_tocreate','_self');this.blur();">新增</a></li>
		<li id="update"><a href="#" onclick="formSubmit('productAction_toupdate','_self');this.blur();">修改</a></li>
		<li id="delete"><a href="#" onclick="formSubmit('productAction_delete','_self');this.blur();">删除</a></li>
	</ul>--%>
	</div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    产品列表
  </div> 
  
<div>

<br/>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">编号</td>
		<td class="tableHeader">描述</td>
		<td class="tableHeader">厂家名称</td>
		<td class="tableHeader">市场价</td>
		<td class="tableHeader">长</td>
		<td class="tableHeader">宽</td>
		<td class="tableHeader">高</td>
		<td class="tableHeader">集装箱类别</td>
		<td class="tableHeader">录入时间</td>
		
	</tr>
	</thead>
	<tbody class="tableBody" >
${page.links}
	
	<c:forEach items="${page.results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td><a href="productAction_toview?id=${o.id}" style="color:blue;">${status.index+1}</a></td>
		<td>${o.productNo}</td>
		<td>${o.description}</td>
		<td>${o.factoryName}</td>
		<td>${o.price}</td>
		<td>${o.sizeLenght}</td>
		<td>${o.sizeWidth}</td>
		<td>${o.sizeHeight}</td>
		<td>${o.type}</td>
		<%-- <td>${o.type}</td>
		<td>${o.qty}</td>
		<td>${o.cbm}</td>
		<td>${o.mpsizeLenght}</td>
		<td>${o.mpsizeWidth}</td>
		<td>${o.mpsizeHeight}</td> --%>
		<td>${o.inputTime}</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

