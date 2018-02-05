<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.3.min.js" ></script>
	<script type="text/javascript" src="${ctx }/layer/layer.js" ></script>
</head>
<script type="text/javascript">
		function view(id) {
			var msg = $("#"+id).prop("value");
            var dd = "<div style='padding:12px;line-height: 30px;'>&nbsp;&nbsp;&nbsp;&nbsp;"+msg+"</div>";
            layer.open({
		        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
		        title:"系统使用反馈",//标题
		        area: ['300px', '400px'],//大小
		        shadeClose: false, //点击弹层外区域 遮罩关闭
		        content: dd
		    });
		}

		function isChecked(){
	    	 var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				}
			//jquery
			//var count = $("[input name='id']:checked").size();
			if(count>=1){
				return true;
			}
			else {
				return false;
			}
	     }
		//删除请求
	     function toDelete() {
	    	 if (isChecked()) {
		    	 if (window.confirm("确定要删除?")){
		    		 formSubmit("systemFeedbackAction_delete",'_self');
		    	 }
	    	 } else {
	    		 alert("请至少选择一项");
	    	 }
	     }
		
	</script>
<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
	<ul>
	<li id="new"><a href="#" onclick="formSubmit('systemFeedbackAction_tocreate','_self');this.blur();">新增</a></li>
	<li id="delete"><a href="#" onclick="toDelete()">删除</a></li>
	</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
	系统使用反馈
  </div> 


<br/>
<div>
<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">创建人</td>
		<td class="tableHeader">创建部门</td>
		<td class="tableHeader">反馈消息</td>
		<td class="tableHeader">系统反馈时间</td>
		<td class="tableHeader"></td>
	</tr>
	</thead>
	<tbody class="tableBody" >
${page.links}
	
	<c:forEach items="${page.results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		<td>${o.createName}</td>
		<td>${o.createDeptName}</td>
		<td>
			<c:if test="${fn:length(o.message)  >6}">${fn:substring(o.message,0,6)}...</c:if>
			<c:if test="${fn:length(o.message)  <7}">${o.message}</c:if>
		</td>
		<td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /><input type="hidden" id="${o.id }" value="${o.message }"></td>
		<td><input type="button" id="btn11" value="详情" onclick="view('${o.id}')"/></td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

