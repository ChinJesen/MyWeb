<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="servlet.*,entity.*"%>
 <%@include file="base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
		Object obj = request.getAttribute("userInfo");
			UserInfo user = null;
			if(obj != null){
			user = (UserInfo)obj;
		}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=path%>/userMrg" method="get">
<fieldset>
<input type="hidden" name="flag" value="update"/>
<legend>修改用户信息</legend>
<table align="center" border ="0">
	<tr>
		<th>账号</th>
		<td>
			<input type= "hidden" name="userid" value="<%=user.getUserid()%>"/>
			<input type= "text" name="userno" id="userno" readonly="readonly" value="<%=user.getUserno()%>"/>
		</td>
	</tr>
	<tr>
		<th>密码</th>
		<td>
			<input type= "password" name="userpw" id="userpw" value="<%=user.getUserpw()%>"/>
		</td>
	</tr>
	<tr>
		<th>姓名</th>
		<td>
			<input type= "text" name="usernm" id="usernm" value="<%=user.getUsernm()%>"/>
		</td>
	</tr>
	<tr>
		<th>年龄</th>
		<td>
			<input type= "text" name="userag" id="userag" value="<%=user.getUserag()%>"/>
		</td>
	</tr>
	<tr>
		<th>性别</th>
		<td>
			<input type= "radio" name="sex" value="1" id=""/>男
			<input type= "radio" name="sex" value="0" id=""/>女
		</td>
	</tr>
	<tr>
		<th>爱好</th>
		<td>
			<input type= "checkbox" name="ah" value="1" id=""/>吃饭
			<input type= "checkbox" name="ah" value="2" id=""/>睡觉
			<input type= "checkbox" name="ah" value="3" id=""/>写代码
			<input type= "checkbox" name="ah" value="4" id=""/>打游戏
		</td>
	</tr>
	<tr>
		<th>籍贯</th>
		<td>
			<select name="jg">
				<option value="" >--请选择--</option>
				<option value="1" >--重庆--</option>
				<option value="2" >--四川--</option>
			</select>
		</td>
	</tr>
	<tr>
		<th>简介</th>
		<td>
			<textarea rows="5" cols="40" value="jj"></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="修改"/>
			<input type="reset" value="重写"/>
		</td>
	</tr>
</table>
</fieldset>
</form>
</body>
</html>