<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="jsp/base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My_First_Web</title>
<script type="text/javascript">
var xmlHttp;
var validFlag = false;
var obj = {vCode:"",
		   vName:""};

function _onValid(val)
{
	xmlHttp = new XMLHttpRequest();
	xmlHttp.open("POST", "<%=path%>/ValidLogServlet?vCode="+val, true);
	xmlHttp.onreadystatechange = _rollBack;
	xmlHttp.send(null);
}

function _rollBack()
{
	if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	{
		var content = xmlHttp.responseText;
		if(content == "true"  || content == true)
		{
			document.getElementById("validMsg").innerHTML="验证成功";
			validFlag = true;
		}
		else
		{
			document.getElementById("validMsg").innerHTML="验证失败";
			validFlag = false;
		}
	}
}

function _onCgValid()
{
	
	document.getElementById("validImg").src="<%=path%>/ValidLogServlet?"+Math.random();
}

function _onSub()
{
	var userno = document.getElementById("userno").value
	if(userno==null||userno==""){
		document.getElementById("userNo").innerHTML="<span style='color:red'>账号不能为空";
		return false;
	}
	var userpw = document.getElementById("userpw").value
	if(userpw==null||userpw==""){
		document.getElementById("userPw").innerHTML="<span style='color:red'>密码不能为空";
		return false;
	}
	
	if(!validFlag)
	{
		return false;
	}
	return true;
}
function _focus(){
	document.getElementById("userPw").innerHTML="";
	document.getElementById("userNo").innerHTML="";
	document.getElementById("msg").innerHTML="";
}
</script>
<style type="text/css">
.login{
		background-image: url('images/1.jpg');
		height: 600px;
	}
</style>
</head> 
<body>
<div class="login">
<form action="<%=path%>/userMrg" onsubmit="return _onSub()">
<fieldset>
	<input type="hidden" name="flag" value="login">
	<center id="msg">${msg}</center>
	<table width="40%" border="1" align="center">
		<tr>
			<th>账号</th>
			<td><input type="text" name="userno" align="center" id="userno" onfocus="_focus()">
			<span id="userNo"></span>
			</td>
		</tr>
		<tr>
			<th>密码</th>
			<td><input type="password" name="userpw" align="center" id="userpw" onfocus="_focus()">
			<span id="userPw"></span>
			</td>
		</tr>
		<tr>
			<th>验证码</th>
			<td>
				<input type="text" name="userValid" id="" onchange="_onValid(this.value)"/>
				<img id="validImg" src="<%=path%>/ValidLogServlet" onclick="_onCgValid()"/>
				<span id="validMsg"></span>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="登录"/>
				<input type="reset" value="重置"/>
			</td>
		</tr>
	</table>
	</fieldset>
	</form>
</div>
</body>
</html>