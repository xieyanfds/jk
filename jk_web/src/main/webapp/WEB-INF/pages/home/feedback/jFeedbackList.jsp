<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js" ></script>
	<script type="text/javascript" src="${ctx }/layer/layer.js" ></script>
	<script type="text/javascript">
		function isOnlyChecked(){
			 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
			if(count==1)
				return true;
			else
				return false;
		}

		function isChecked(){
			 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
			if(count>0)
				return true;
			else
				return false;
		}
		
		function toSolve(){
			if(isOnlyChecked()){
				var degree = "${_CURRENT_USER.userInfo.degree}";
				var $state = $("input[name='id']:checked").attr("id");
				var state = $("span[id="+$state+"] font").html();
				if(state=='已处理'){
					alert("该问题已解决!");
				}else if(degree ==  4){
					alert("您没有权限!");
				}else{
					var id = $("input[name='id']:checked").val();
					$.post("${ctx}/feedbackAction_toview",{"id":id},function(data){
			  			 $("#solve #title").attr("value",data.title);
			  			 $("#solve #id").attr("value",data.id);
			  			 $("#solve #tel").attr("value",data.tel);
			  			 $("#solve #classType").attr("value",data.classType==1?"管理":data.classType==2?"安全":data.classType==3?"建议":"其他"); 
			  			 $("#solve #content").html(data.content);
			  			 $("#solve #solveMethod").html(data.solveMethod);
				  		 layer.open({
					        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
					        title:"解决意见反馈",//标题
					        area: ['1000px', '800px'],//大小
					        shadeClose: false, //点击弹层外区域 遮罩关闭
					        content: $("#solveSpan").html()//内容
					     }); 
			  		 },"json");
				}
			}else{
			    alert("请选择一项且只能选择一项！");
			}
		}

		function toView(){
		  	 if(isOnlyChecked()){
		  		 var id = $("input[name='id']:checked").val();
		  		 var content;
		  		 $.post("${ctx}/feedbackAction_toview",{"id":id},function(data){
		  			 $("#view #inputBy").html(data.inputBy);
		  			 $("#view #inputTime").html(data.inputTime);
		  			 $("#view #title").html(data.title);
		  			 $("#view #classType").html(data.classType==1?"管理":data.classType==2?"安全":data.classType==3?"建议":"其他");
		  			 $("#view #tel").html(data.tel);
		  			 $("#view #isShare").html(data.isShare==0?"不公开":"公开");
		  			 $("#view #content").html(data.content);
		  			 $("#view #answerBy").html(data.answerBy);
		  			 $("#view #answerTime").html(data.answerTime);
		  			 $("#view #resolution").html(data.resolution==1?"已修改":data.resolution==2?"无需修改":data.resolution==3?"重复问题":data.resolution==4?"描述不完整":data.resolution==5?"无法再现":data.resolution==6?"其他":"");
		  			 $("#view #difficulty").html(data.difficulty==1?"极难":data.difficulty==2?"比较难":data.difficulty==3?"有难度":data.difficulty==4?"一般":"");
		  			 $("#view #state").html(data.state==0?"<font color='red'>未处理</font>":"<font color='green'>已处理</font>");
		  			 $("#view #solveMethod").html(data.solveMethod);
			  		 layer.open({
				        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
				        title:"查看意见反馈",//标题
				        area: ['1000px', '600px'],//大小
				        shadeClose: true, //点击弹层外区域 遮罩关闭
				        content: $("#viewSpan").html()//内容
				     }); 
		  		 },"json");
		  		 
		  	 }else{
		  		 alert("请选择一项且只能选择一项！");
		  	 }
		}

		function toUpdate(){
		  	 if(isOnlyChecked()){
		  		formSubmit('feedbackAction_toupdate','_self');
		  	 }else{
		  		 alert("请选择一项且只能选择一项！");
		  	 }
		}

		function toDelete(){
		  	 if(isChecked()){
		  		 if(confirm("确认删除吗?")){
			  		 formSubmit('feedbackAction_delete','_self');
		  		 }
		  	 }else{
		  		 alert("请至少选择一项，再进行操作！");
		  	 }
		}
		
		function toCreate(){
			layer.open({
		        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
		        title:"新增意见反馈",//标题
		        area: ['1000px', '600px'],//大小
		        shadeClose: false, //点击弹层外区域 遮罩关闭
		        content: $("#createSpan").html()//内容
		     }); 
		}
		
		function create(){
			var p = /^[0-9]+$/
			var phone = $(".create:odd [name='tel']").val();
			if(p.exec(phone)){
			     $(".create").get(1).submit(); 
			}else{
				alert("非法的联系电话格式!");
			}
		}
		
		function solve(){
			 $(".solve").get(1).submit(); 
		}

	</script>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="view"><a href="#" onclick="toView();this.blur();">查看</a></li>
<li id="new"><a href="#" onclick="toCreate();this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="toUpdate();this.blur();">修改</a></li>
<li id="delete"><a href="#" onclick="toDelete();this.blur();">删除</a></li>
<li id="update"><a href="#" onclick="toSolve();this.blur();">解决</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    意见反馈列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">提出人</td>
		<td class="tableHeader">提出时间</td>
		<td class="tableHeader">标题</td>
		<td class="tableHeader">分类</td>
		<td class="tableHeader">联系电话</td>
		<td class="tableHeader">解决人</td>
		<td class="tableHeader">解决时间</td>
		<td class="tableHeader">解决方式</td>
		<td class="tableHeader">解决难度</td>
		<td class="tableHeader">是否公开</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${links}
	
	<c:forEach items="${results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input class="maininput" type="checkbox" name="id" value="${o.id}" id="${status.index+1 }"/></td>
		<td>${status.index+1}</td>
		<td>${o.inputBy}</td>
		<td>${o.inputTime}</td>
		<td>${o.title}</td>
		<td>${o.classType==1?"管理":o.classType==2?"安全":o.classType==3?"建议":"其他"}</td>
		<td>${o.tel}</td>
		<td>${o.answerBy}</td>
		<td>${o.answerTime}</td>
		<td>${o.resolution==1?"已修改":o.resolution==2?"无需修改":o.resolution==3?"重复问题":o.resolution==4?"描述不完整":o.resolution==5?"无法再现":o.resolution==6?"其他":""}</td>
		<td>${o.difficulty==1?"极难":o.difficulty==2?"比较难":o.difficulty==3?"有难度":o.difficulty==4?"一般":""}</td>
		<td>${o.isShare==0?"不公开":"公开"}</td>
		<td><span id="${status.index+1}">${o.state==0?"<font color='red'>未处理</font>":"<font color='green'>已处理</font>"}</span></td>
	</tr>
	</c:forEach>
	</tbody>
</table>
</div>
</div>
</form>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++查看页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="viewSpan"><form name="icform" method="post" id="view">

  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	查看意见反馈
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">提出人：</td>
	            <td class="tableContent" id="inputBy">${inputBy}</td>
	            <td class="columnTitle">提出时间：</td>
	            <td class="tableContent" id="inputTime">${inputTime}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent" id="title">${title}</td>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent" id="classType" >${classType==1?"管理":classType==2?"安全":classType==3?"建议":"其他"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent" id="tel">${tel}</td>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent" id="isShare">${isShare==0?"不公开":"公开"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent" id="content"><textarea style="height:150px;width: 500px" disabled="disabled">${content}</textarea></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决人：</td>
	            <td class="tableContent" id="answerBy">${answerBy}</td>
	            <td class="columnTitle">解决时间：</td>
	            <td class="tableContent" id="answerTime">${answerTime}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent" id="resolution">${o.resolution==1?"已修改":o.resolution==2?"无需修改":resolution==3?"重复问题":resolution==4?"描述不完整":resolution==5?"无法再现":resolution==6?"其他":""}</td>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent" id="difficulty">${o.difficulty==1?"极难":difficulty==2?"比较难":difficulty==3?"有难度":difficulty==4?"一般":""}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent" id="state">${state==0?"<font color='red'>未处理</font>":"<font color='green'>已处理</font>"}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决办法：</td>
	            <td class="tableContent" id="solveMethod"><textarea  style="height:150px;width: 500px" disabled="disabled">${solveMethod}</textarea></td>
	        </tr>	
		</table>
	</div>
 
 
</form></span>
<!-- ++++++++++++++++++++++++++++++++++++++++++++++新增页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->


<span style="display: none;" id="createSpan"><form name="icform" method="post" class="create" action="${ctx }/feedbackAction_insert">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="create()">保存</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增意见反馈
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">标题:：</td>
	            <td class="tableContent"><input type="text" name="title" value="" style="width:500px;"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent">
		            <select name="classType">
		            	<option value="1">管理</option>
		            	<option value="2">安全</option>
		            	<option value="3">建议</option>
		            	<option value="4">其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value="" style="width:500px;"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent">
	            	<select name="isShare">
		            	<option value="0">不公开</option>
		            	<option value="1">公开</option>
	            	</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea name="content" style="height:150px;width: 800px"></textarea></td>
	        </tr>	
		</table>
	</div>
 
</form></span>


<!-- ++++++++++++++++++++++++++++++++++++++++++++++解决页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->

<span style="display: none;" id="solveSpan"><form name="icform" method="post" class="solve" id="solve" action="${ctx }/feedbackAction_solve">
	<input type="hidden" name="id" id="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="solve()">保存</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   解决意见反馈
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input id="title" type="text" name="title" value="${title}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
	            <input id="classType" type="text" name="classType"  value="" disabled="disabled" style="width: 500px"/>
		           <%--  <select name="classType" disabled="disabled" id="classType">
		            	<option value="1" <c:if test="${classType==1 }">selected</c:if>>管理</option>
		            	<option value="2" <c:if test="${classType==2 }">selected</c:if>>安全</option>
		            	<option value="3" <c:if test="${classType==3 }">selected</c:if>>建议</option>
		            	<option value="4" <c:if test="${classType==4 }">selected</c:if>>其他</option>
		            </select> --%>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input id="tel" type="text" name="tel" value="${tel}" disabled="disabled" style="width: 500px"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea id="content" name="content" style="height:150px;width: 800px" disabled="disabled">${content}</textarea></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	             <td class="tableContent">
	            	<select name="isShare" id="isShare">
		            	<option value="0" <c:if test="${isShare==0 }">selected</c:if>>不公开</option>
		            	<option value="1" <c:if test="${isShare==1 }">selected</c:if>>公开</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent">
		            <select name="resolution" id="resolution">
		            	<option value="1" >已修改</option>
		            	<option value="2" >无需修改</option>
		            	<option value="3" >重复问题</option>
		            	<option value="4" >描述不完整</option>
		            	<option value="5" >无法再现</option>
		            	<option value="6" >其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">
		            <select name="difficulty" id="difficulty">
		            	<option value="1" >极难</option>
		            	<option value="2" >比较难</option>
		            	<option value="3" >有难度</option>
		            	<option value="4" >一般</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决办法：</td>
	             <td class="tableContent"><textarea id="solveMethod" name="solveMethod" style="height:150px;width: 800px">${solveMethod}</textarea></td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>

<!-- ++++++++++++++++++++++++++++++++++++++++++++++修改页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="updateSpan"><form name="icform" method="post" class="update" id="update" action="${ctx }/feedbackAction_update">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('feedbackAction_update','_self');this.blur();">保存</a></li>
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

