<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<%=path%>/clsMrg" method="get">
<fieldset>
<input type="hidden" name="flag" value="update"/>
<legend>修改班级信息</legend>
<div calss = "muenDiv">
<table align="center" border ="0">
	<tr>
		<th>班级ID</th>
		<td>
			<input type= "text" name="classId" id=""/>
		</td>
	</tr>
	<tr>
		<th>班级姓名</th>
		<td>
			<input type= "text" name="className" id=""/>
		</td>
	</tr>
	<tr>
		<th>创建时间</th>
		<td>
			<input type= "date" name="createTime" id=""/>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea rows="5" cols="30" name="remark"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="修改"/>
			<input type="reset" value="重写"/>
		</td>
	</tr>
</table>
</div>
</fieldset>
</form>
</body>
</html>