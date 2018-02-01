<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript">
        function to_solve(){
            if(isOnlyChecked()){
                var degree = "${_CURRENT_USER.userInfo.degree}";
                var $state = $("input[name='id']:checked").attr("id");
                var state = $("span[id="+$state+"] font").html();
                if(state=='已处理'){
                    $("#envon #mess").html("该问题已解决!");
                    EV_modeAlert('envon');
                }else if(degree ==  4){
                    $("#envon #mess").html("您没有权限!");
                    EV_modeAlert('envon');
                }else{
                    var id = $("input[name='id']:checked").val();
                    $.post("${ctx}/feedbackAction_toview",{"id":id},function(data){
                        $("#solve #id").attr("value",data.id);
                        $("#solve #title").attr("value",data.title);
                        $("#solve #classType").find("option[value='"+data.classType+"']").attr("selected","selected");
                        $("#solve #tel").attr("value",data.tel);
                        $("#solve #content").html(data.content);
                        $("#solve #isShare").find("option[value='"+data.isShare+"']").attr("selected","selected");
                        layer.open({
                            type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
                            title:"解决意见反馈",//标题
                            area: ['1000px', '600px'],//大小
                            shadeClose: true, //点击弹层外区域 遮罩关闭
                            content: $("#solveSpan").html()//内容
                        });
                    },"json");
                }
            }else{
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }

        function to_view(){
            if(isOnlyChecked()){
                var id = $("input[name='id']:checked").val();
                var content;
                $.post("${ctx}/feedbackAction_toview",{"id":id},function(data){
                    $("#view #inputBy").attr("value",data.inputBy);
                    $("#view #inputTime").attr("value",data.inputTime);
                    $("#view #title").attr("value",data.title);
                    $("#view #classType").find("option[value='"+data.classType+"']").attr("selected","selected");
                    $("#view #tel").attr("value",data.tel);
                    $("#view #isShare").find("option[value='"+data.isShare+"']").attr("selected","selected");
                    $("#view #content").text(data.content);
                    $("#view #answerBy").attr("value",data.answerBy);
                    $("#view #answerTime").attr("value",data.answerTime);
                    $("#view #resolution").find("option[value='"+data.resolution+"']").attr("selected","selected");
                    $("#view #difficulty").find("option[value='"+data.difficulty+"']").attr("selected","selected");
                    $("#view #solveMethod").text(data.solveMethod);
                    if(data.state==0){
                        $("#view #state").attr("value","未处理");
                        $("#view #state").css("color","red");
                    }else{
                        $("#view #state").attr("value","已处理");
                        $("#view #state").css("color","green");
                    }
                    layer.open({
                        type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
                        title:"查看意见反馈",//标题
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

        function to_update(){
            if(isOnlyChecked()){
                var $state = $("input[name='id']:checked").attr("id");
                var state = $("span[id="+$state+"] font").html();
                if(state=='已处理'){
                    $("#envon #mess").html("该问题已解决，不可修改!");
                    EV_modeAlert('envon');
                }else{
                    var id = $("input[name='id']:checked").val();
                    var content;
                    $.post("${ctx}/feedbackAction_toupdate",{"id":id},function(data){
                        $("#update #id").attr("value",data.id);
                        $("#update #title").attr("value",data.title);
                        $("#update #classType").find("option[value='"+data.classType+"']").attr("selected","selected");
                        $("#update #tel").attr("value",data.tel);
                        $("#update #isShare").find("option[value='"+data.isShare+"']").attr("selected","selected");
                        $("#update #content").text(data.content);
                        layer.open({
                            type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
                            title:"查看意见反馈",//标题
                            area: ['1000px', '600px'],//大小
                            shadeClose: true, //点击弹层外区域 遮罩关闭
                            content: $("#updateSpan").html()//内容
                        });
                    },"json");
                }
            }else{
                $("#envon #mess").html("请先选择一项并且只能选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }

        function to_delete(){
            if(isAtLeastCheckOne()){
                if(confirm("确认删除吗?")){
                    formSubmit('feedbackAction_delete','_self');
                }
            }else{
                $("#envon #mess").html("请至少选择一项，再进行操作！");
                EV_modeAlert('envon');
            }
        }

        function to_create(){
            layer.open({
                type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
                title:"新增意见反馈",//标题
                area: ['1000px', '600px'],//大小
                shadeClose: true, //点击弹层外区域 遮罩关闭
                content: $("#createSpan").html()//内容
            });
        }

        function create(){
            var p = /^[0-9]+$/;
            var phone = $(".create:odd [name='tel']").val();
            if(p.exec(phone)){
                $(".create").get(1).submit();
            }else{
                //设置envon内容
                alert("非法的联系电话格式!");
            }
        }

        function solve(){
            $(".solve").get(1).submit();
        }

        function updateMy(){
            $(".update").get(1).submit();
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
		<%@include file="../layerButton.jsp"%>
	</div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    意见反馈列表
  </div> 
  
<div>

<br/>
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
	            <td class="tableContent" ><input id="inputBy" type="text" name="inputBy" value=""  readonly/></td>
	            <td class="columnTitle">提出时间：</td>
	            <td class="tableContent" ><input id="inputTime" type="text" name="inputTime" value=""  readonly/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent" ><input id="title" type="text" name="title" value=""  readonly/></td>
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent"  >
					<select id="classType" name="classType" disabled>
		            	<option value="1">管理</option>
		            	<option value="2">安全</option>
		            	<option value="3">建议</option>
		            	<option value="4">其他</option>
		            </select>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">
					<input id="tel" type="text" name="tel" value=""  readonly/></td>
	            <td class="columnTitle">是否公开：</td>
	            <td class="tableContent">
					<select id="isShare" name="isShare" disabled>
		            	<option value="0">不公开</option>
		            	<option value="1">公开</option>
	            	</select>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent" >
					<textarea id="content" style="height:150px;width: 100%" readonly></textarea>
				</td>
				<td class="columnTitle">状态：</td>
	            <td class="tableContent">
					<input id="state" type="text" name="state" value=""  readonly/></td>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决人：</td>
	            <td class="tableContent" >
					<input id="answerBy" type="text" name="answerBy" value=""  readonly/></td>
				</td>
	            <td class="columnTitle">解决时间：</td>
	            <td class="tableContent">
					<input id="answerTime" type="text" name="answerTime" value=""  readonly/></td>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">解决方式：</td>
	            <td class="tableContent" >
		            <select id="resolution" name="resolution" disabled>
		            	<option value="1" >已修改</option>
		            	<option value="2" >无需修改</option>
		            	<option value="3" >重复问题</option>
		            	<option value="4" >描述不完整</option>
		            	<option value="5" >无法再现</option>
		            	<option value="6" >其他</option>
		            </select>
				</td>
	            <td class="columnTitle">解决难度：</td>
	            <td class="tableContent">
					<select name="difficulty" id="difficulty" disabled>
		            	<option value="1" >极难</option>
		            	<option value="2" >比较难</option>
		            	<option value="3" >有难度</option>
		            	<option value="4" >一般</option>
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
	            <td class="tableContent">
					<input id="title" type="text" name="title" value="${title}" readonly />
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
				 <select id="classType" name="classType" disabled="disabled" >
					<option value="1">管理</option>
					<option value="2">安全</option>
					<option value="3">建议</option>
					<option value="4">其他</option>
				</select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input id="tel" type="text" name="tel" value="${tel}" readonly style="width: 70%"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent"><textarea id="content" name="content" style="height:150px;width: 70%" readonly>${content}</textarea></td>
	        </tr>	
	        <tr>
				<%--65*15--%>
	            <td class="columnTitle">是否公开：</td>
	             <td class="tableContent">
	            	<select name="isShare" id="isShare" disabled>
		            	<option value="1" <c:if test="${isShare==1 }">selected</c:if>>公开</option>
		            	<option value="0" <c:if test="${isShare==0 }">selected</c:if>>不公开</option>
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
	             <td class="tableContent"><textarea id="solveMethod" name="solveMethod" style="height:150px;width: 70%">${solveMethod}</textarea></td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>
</span>
<!-- ++++++++++++++++++++++++++++++++++++++++++++++修改页面++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ -->
<span style="display: none;" id="updateSpan"><form id="update" name="icform" method="post" class="update" action="${ctx }/feedbackAction_update">
	<input type="hidden" name="id" id="id" value="${id}"/>

<div id="menubar">
	<div id="middleMenubar">
	<div id="innerMenubar">
	  <div id="navMenubar">
		<ul>
			<li id="save"><a href="#" onclick="updateMy()">保存</a></li>
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
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input id="title" type="text" name="title" value="" /></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">类别：</td>
	            <td class="tableContent">
		            <select id="classType" name="classType">
		            	<option value="1" >管理</option>
		            	<option value="2" >安全</option>
		            	<option value="3" >建议</option>
		            	<option value="4" >其他</option>
		            </select>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">
					<input id="tel" type="text" name="tel" value="" />
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否公开：</td>
	             <td class="tableContent">
	            	<select id="isShare" name="isShare">
		            	<option value="0" >不公开</option>
		            	<option value="1" >公开</option>
	            	</select>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">内容：</td>
	            <td class="tableContent">
					<textarea id="content" name="content" style="height:150px;width: 100%"></textarea>
				</td>
	        </tr>	

		</table>
	</div>
</form>
</span>
</body>
</html>

