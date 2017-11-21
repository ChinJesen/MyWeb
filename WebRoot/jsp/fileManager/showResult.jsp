<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../base.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/WebRoot/css/commStyle.css">
</head>
<body>
<fieldset>
	<legend>处理结果</legend>
	<ul>
		<li>共处理【${fn:length(len)}】条</li>
		<li>成功【${scsCount}】条</li>
		<li>失败【${fidCount}】条</li>
	</ul>
<hr>
<table>
	<tr>
		<th>序号</th>
		<th>账户</th>
		<th>姓名</th>
		<th>年龄</th>
		<th>性别</th>
		<th>爱好</th>
		<th>籍贯</th>
	</tr>
	<c:forEach items="${userList}" var="user" varStatus="v">
	<c:if test="${len[v.index] >=0}">
	<tr>
	</c:if>
	<c:if test="${len[v.index] <0}">
	<tr style="background-color:#9e9e9e;">
	</c:if>
		<td>${v.count}</td>
		<td>${user.userno}</td>
		<td>${user.usernm}</td>
		<td>${user.userag}</td>
		<td>${user.sex}</td>
		<td>${user.ah}</td>
		<td>${user.jg}</td>
	</tr>
	</c:forEach>
	</table>
</fieldset>
</body>
</html>