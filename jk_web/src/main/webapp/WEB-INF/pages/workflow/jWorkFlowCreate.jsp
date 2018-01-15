<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
		<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('feedbackAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增系统反馈使用
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">提出人：</td>
	            <td class="tableContent"><input type="text" name="inputBy" value=""/></td>
	        
	            <td class="columnTitle">提出时间：</td>
	            
	            
	              <td class="tableContent">
					<input type="text" style="width:90px;" name="inputTime"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td> 
	        </tr>	
	        <tr>
	            <td class="columnTitle">标题：</td>
	            <td class="tableContent"><input type="text" name="title" value=""/></td>
	        
	            <td class="columnTitle">分类：</td>
	            <td class="tableContent"><input type="text" name="classType" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="tel" value=""/></td>
	      
	            <td class="columnTitle">解决人：</td>
	            <td class="tableContent"><input type="text" name="answerBy" value=""/></td>
	        </tr>	
		</table>
	</div>
 
 
</form>
</body>
</html>

