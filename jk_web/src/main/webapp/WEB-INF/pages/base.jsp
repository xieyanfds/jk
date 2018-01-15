<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" isELIgnored="false" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/default.css" media="all"/>
<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/table.css" media="all"/>
<script language="javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript">
    window.onload=function(){
        var table = document.getElementById("ec_table");
        var trs = table.getElementsByTagName("tr");
        for (var i = 0; i < trs.length; i++) {
            var j = i + 1;
            if (j % 2 == 1) { //奇数行
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
</script>
