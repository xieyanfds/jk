<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/9
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<input type="hidden"  id="pageSize" value="${pageSize}" name="page.pageSize" />
<input type="hidden"  id="pageNo1" value="${pageNo}" name="page.pageNo" />
<div id="pageToolbar"></div>

<link rel="stylesheet" type="text/css" href="${ctx}/css/paging.css">
<script type="text/javascript" src="${ctx}/js/jquery-1.11.2.js"></script>
<script type="text/javascript" src="${ctx}/js/query.js"></script>
<script type="text/javascript" src="${ctx}/js/paging.js"></script>
<script>
    /*$('#pageTool').Paging({pagesize:10,count:100,callback:function(page,size,count){
     console.log(arguments)
     alert('当前第 ' +page +'页,每页 '+size+'条,总页数：'+count+'页')
     }});*/
    $('#pageToolbar').Paging({pagesize:${pageSize},count:${totalRecord},current:${pageNo},toolbar:true,callback:function(currentNo,pagesize,count){
        console.log(arguments)
        if(currentNo != null && currentNo != '') {
            $("#pageNo1").val(currentNo);
        }
        if(pagesize != null && pagesize != '') {
            $("#pageSize").val(pagesize);
        }
        formSubmit('${url}',"_self");
    }});
    $('#pageToolbar ul').append('<li>共${totalRecord}条记录</li>');
</script>