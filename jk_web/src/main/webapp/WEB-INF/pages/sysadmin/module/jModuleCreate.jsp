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
<li id="save"><a href="#" onclick="formSubmit('moduleAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   新增模块
  </div> 
  </div>
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
				<td class="columnTitle">父模块：</td>
				<td class="tableContent">
					<s:select name="parentId" list="moduleList"
							  listKey="id" listValue="name"
							  headerKey="" headerValue="--请选择--"
					></s:select>
				</td>
				<td class="columnTitle">层数：</td>
				<td class="tableContent">
					<select name="layerNum">
						<option value="1" >1</option>
						<option value="2" >2</option>
						<option value="3" >3</option>
					</select>
				</td>
	        </tr>
	        <tr>
				<td class="columnTitle">模块名：</td>
				<td class="tableContent">
					<input type="text" name="name" value=""/>
				</td>
				<td class="columnTitle">权限标识：</td>
				<td class="tableContent">
					<input type="text" name="ico" value=""/>
				</td>
	        </tr>
	        <tr>
				<td class="columnTitle">显示的描述：</td>
				<td class="tableContent">
					<input type="text" name="cpermission" value=""/>
				</td>
				<td class="columnTitle">访问路径：</td>
				<td class="tableContent">
					<input type="text" name="curl" value=""/>
				</td>
	        </tr>
	        <tr>
				<td class="columnTitle">类型：</td>
				<td class="tableContentAuto">
					<select name="ctype">
						<option value="0" >主菜单</option>
						<option value="1" >左侧菜单</option>
						<option value="2" >按钮</option>
						<%--<option value="4" >链接</option>
						<option value="5" >状态</option>--%>
					</select>
				</td>
				<td class="columnTitle">状态：</td>
				<td class="tableContentAuto">
					<select name="state">
						<option value="1" >启用</option>
						<option value="0" >停用</option>
					</select>
				</td>
	        </tr>
	        <tr>
				<td class="columnTitle">按钮类型：</td>
				<td class="tableContent"><input type="text" name="cwhich" value=""/></td>
				<td class="columnTitle">复用标识：</td>
				<td class="tableContent"><input type="text" name="remark" value=""/></td>
	        </tr>
			<tr>
				<td class="columnTitle">排序号：</td>
				<td class="tableContent"><input type="text" name="orderNo" value=""/></td>
			</tr>
		</table>
	</div>
 
 
</form>
</body>
</html>

