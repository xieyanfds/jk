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
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	查看意见反馈
  </div>



	<div>
		<table class="commonTable" cellspacing="1">
			<tr>
				<td class="columnTitle">提出人：</td>
				<td class="tableContent" ><input id="inputBy" type="text" name="inputBy" value="${inputBy}"  readonly/></td>
				<td class="columnTitle">提出时间：</td>
				<td class="tableContent" >
					<input type="text" style="width:112px;" name="inputTime"
						   value="<fmt:formatDate value="${inputTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly/>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">标题：</td>
				<td class="tableContent" ><input id="title" type="text" name="title" value="${title}"  readonly/></td>
				<td class="columnTitle">分类：</td>
				<td class="tableContent"  >
					<select id="classType" name="classType" disabled>
						<option value="1" <c:if test="${classType==1}">selected</c:if>>管理</option>
						<option value="2" <c:if test="${classType==2}">selected</c:if>>安全</option>
						<option value="3" <c:if test="${classType==3}">selected</c:if>>建议</option>
						<option value="4" <c:if test="${classType==4}">selected</c:if>>其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">联系电话：</td>
				<td class="tableContent">
					<input id="tel" type="text" name="tel" value="${tel}"  readonly/></td>
				<td class="columnTitle">是否公开：</td>
				<td class="tableContent">
					<select id="isShare" name="isShare" disabled>
						<option value="0" <c:if test="${isShare==0}">selected</c:if>>不公开</option>
						<option value="1" <c:if test="${isShare==1}">selected</c:if>>公开</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">内容：</td>
				<td class="tableContent" >
					<textarea id="content" style="height:150px;width: 100%" readonly>${content}</textarea>
				</td>
				<td class="columnTitle">状态：</td>
				<td class="tableContent">
					<input id="state" type="text" style="color:${state==0?"red":"green"};" name="state" value="${state==0?"未解决":"已解决"}"  readonly/></td>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">解决人：</td>
				<td class="tableContent" >
					<input id="answerBy" type="text" name="answerBy" value="${answerBy}"  readonly/></td>
				</td>
				<td class="columnTitle">解决时间：</td>
				<td class="tableContent">
					<input type="text" style="width:112px;" name="answerTime"
						   value="<fmt:formatDate value="${answerTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
						   onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});" readonly/>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">解决方式：</td>
				<td class="tableContent" >
					<select id="resolution" name="resolution" disabled>
						<option value="1" <c:if test="${resolution==1}">selected</c:if>>已修改</option>
						<option value="2" <c:if test="${resolution==2}">selected</c:if>>无需修改</option>
						<option value="3" <c:if test="${resolution==3}">selected</c:if>>重复问题</option>
						<option value="4" <c:if test="${resolution==4}">selected</c:if>>描述不完整</option>
						<option value="5" <c:if test="${resolution==5}">selected</c:if>>无法再现</option>
						<option value="6" <c:if test="${resolution==6}">selected</c:if>>其他</option>
					</select>
				</td>
				<td class="columnTitle">解决难度：</td>
				<td class="tableContent">
					<select name="difficulty" id="difficulty" disabled>
						<option value="1" <c:if test="${difficulty==1}">selected</c:if>>极难</option>
						<option value="2" <c:if test="${difficulty==2}">selected</c:if>>比较难</option>
						<option value="3" <c:if test="${difficulty==3}">selected</c:if>>有难度</option>
						<option value="4" <c:if test="${difficulty==4}">selected</c:if>>一般</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="columnTitle">解决办法：</td>
				<td class="tableContent" >
					<textarea id="solveMethod" style="height:150px;width: 100%" readonly>${solveMethod}</textarea>
				</td>
			</tr>
		</table>
	</div>
 
 
</form>
</body>
</html>

