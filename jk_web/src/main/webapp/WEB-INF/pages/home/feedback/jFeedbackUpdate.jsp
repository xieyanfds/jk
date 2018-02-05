<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js" ></script>
	<script type="text/javascript">
		function toupdate(){
				var p = /^[0-9]+$/
				var phone = $("[name='tel']").val();
				if(p.exec(phone)){
					formSubmit('feedbackAction_update','_self');
				}else{
					alert("非法的联系电话格式!");
				}
			}
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="toupdate()">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改意见反馈
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
		<c:if test="${state==0 }">
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input type="text" name="title" value="${title}" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
		            <select name="classType">
		            	<option value="1" <c:if test="${classType==1 }">selected</c:if>>管理</option>
		            	<option value="2" <c:if test="${classType==2 }">selected</c:if>>安全</option>
		            	<option value="3" <c:if test="${classType==3 }">selected</c:if>>建议</option>
		            	<option value="4" <c:if test="${classType==4 }">selected</c:if>>其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value="${tel}" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	             <td class="tableContent">
	            	<select name="isShare">
		            	<option value="0" <c:if test="${isShare==0 }">selected</c:if>>不公开</option>
		            	<option value="1" <c:if test="${isShare==1 }">selected</c:if>>公开</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea name="content" style="height:150px;width: 800px">${content}</textarea></td>
	        </tr>	
	        
	     </c:if>
	     <c:if test="${state==1 }">
	     <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input type="text" name="title" value="${title}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
		            <select name="classType" disabled="disabled" >
		            	<option value="1" <c:if test="${classType==1 }">selected</c:if>>管理</option>
		            	<option value="2" <c:if test="${classType==2 }">selected</c:if>>安全</option>
		            	<option value="3" <c:if test="${classType==3 }">selected</c:if>>建议</option>
		            	<option value="4" <c:if test="${classType==4 }">selected</c:if>>其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value="${tel}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea name="content" style="height:150px;width: 800px" disabled="disabled">${content}</textarea></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent">
		            <select name="resolution">
		            	<option value="1" <c:if test="${resolution==1 }">selected</c:if>>已修改</option>
		            	<option value="2" <c:if test="${resolution==2 }">selected</c:if>>无需修改</option>
		            	<option value="3" <c:if test="${resolution==3 }">selected</c:if>>重复问题</option>
		            	<option value="4" <c:if test="${resolution==4 }">selected</c:if>>描述不完整</option>
		            	<option value="5" <c:if test="${resolution==5 }">selected</c:if>>无法再现</option>
		            	<option value="6" <c:if test="${resolution==6 }">selected</c:if>>其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">
		            <select name="difficulty">
		            	<option value="1" <c:if test="${difficulty==1 }">selected</c:if>>极难</option>
		            	<option value="2" <c:if test="${difficulty==2 }">selected</c:if>>比较难</option>
		            	<option value="3" <c:if test="${difficulty==3 }">selected</c:if>>有难度</option>
		            	<option value="4" <c:if test="${difficulty==4 }">selected</c:if>>一般</option>
		            </select>
	            </td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent">
	            	<select name="isShare">
		            	<option value="0" <c:if test="${isShare==0 }">selected</c:if>>不公开</option>
		            	<option value="1" <c:if test="${isShare==1 }">selected</c:if>>公开</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">解决办法：</td>
	            <td class="tableContent"><textarea name="solveMethod" style="height:150px;width: 800px">${solveMethod}</textarea></td>
	        </tr>	
	      </c:if>  	
		</table>
	</div>
 
 
</form>
</body>
</html>

