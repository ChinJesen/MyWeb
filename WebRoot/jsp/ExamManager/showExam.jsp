<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题库信息查询</title>
</head>
<body>
<form action="<%=path%>/QusetionMrgServlet" method="get">
<input type="hidden" name="flag" value="queryAll"/>
<fieldset>
<legend>条件查询结果</legend>
</fieldset>
<fieldset>
<legend>查询结果</legend>
<table border="1" width="100%" align="center">
	<tr>
		<th>序号</th>
		<th>题目</th>
		<th>专业</th>
		<th>科目</th>
		<th>知识点</th>
		<th>题目类型</th>
		<th>创建时间</th>
		<th>创建人</th>
		<th>状态</th>
		<th>操作</th>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td align="center">
			<input type="button" value="删除"/>
			<input type="button" value="修改"/>
			<input type="button" value="详情"/>
		</td>
	</tr>
</table>
</fieldset>
</form>
</body>
</html>