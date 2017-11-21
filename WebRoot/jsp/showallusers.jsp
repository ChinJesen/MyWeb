<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@include file="base.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8 ">
<title>Insert title here</title>
<script type="text/javascript">
function _del(userid){
	if(window.confirm("确认删除？"))
	{
	window.location.href = "<%=path%>/userMrg?flag=delete&userid="+userid;
	}
}

function _modify(userid)
{
	window.location.href = "<%=path%>/userMrg?flag=queryById&userid="+userid;	
}

function _page(num){
	var _pageSize = document.getElementById("pageSize").value;
	_pageSize = parseInt(_pageSize) + parseInt(num);
	if(_pageSize>0 && _pageSize <= parseInt('${pageSizeSum}')){
		document.getElementById("pageSize").value = _pageSize;
		window.document.forms[0].submit();
	}
}

function _cgPage(num)
{
	document.getElementById("pageSize").value = 1;
	window.document.forms[0].submit();
}

function _init(){
	document.getElementById("pageNum").value ='${pageNum}';
	document.getElementById("pageSize").value = '${pageSize}';
	var sexArray = document.getElementById("sex");
	/* int */
	for (var i = 0; i < sexArray.length; i++) {
		if(sexArray[i].value =='${user.sex}'){
			sexArray[i].selected = "selected";
		}
	}
}
</script>
</head>
<body onload="_init()">
<form action="<%=path%>/userMrg">
<input type="hidden" name="flag" value="queryAll">
	<fieldset>
	<legend>按条件查询</legend>
	<table width="80%" align="center" border="1">
		<tr>
		<th>账号</th>
			<td>
				<input type="text" name="userno" value='${user.userno}'/>
			</td>
		<th>姓名</th>
			<td>
				<input type="text" name="usernm" value='${user.usernm}'/>
			</td>
		<th>性别</th>
			<td>
				<select name="sex" id="sex">
					<option value="">--请选择--</option>
					<option value="1">男</option>
					<option value="0">女</option>
				</select>
			</td>
			<td>
				<input type="submit" value="查询"/>
			</td>
		</tr>
	</table>
	</fieldset>
	<fieldset>
		<legend>用户查询结果</legend>
		<table width="80%" align="center" border="1">
			<tr>
				<th>序号</th>
				<th>账号</th>
				<th>密码</th>
				<th>姓名</th>
				<th>年龄</th>
				<th>性别</th>
				<th>爱好</th>
				<th>籍贯</th>
				<th>操作选项</th>
			</tr>
	<c:forEach items="${requestScope.userlist}" var="user" varStatus="v">		
		<c:if test="${v.count%2==0}">
			<tr style="background-color:#C05A12">
		</c:if>
		<c:if test="${v.count%2!=0}">
			<tr>
		</c:if>
			<td>${v.count}</td>
			<td>${user.userno}</td>
			<td>${user.userpw}</td>
			<td>${user.usernm}</td>
			<td>${user.userag}</td>
			<td>${user.sex eq '1'?'男':'女'}</td>
			<td>${user.ah}</td>
			<td>${user.jg}</td>
			
			<td align="center">
			<input onclick="_modify('${user.userid}')" type="button" value="修改"/>
			<input onclick="_del('${user.userid}')" type="button" value="删除"/>
			</td>
		
	</c:forEach>
			<tr>
				<td colspan="10" align="right">
					总条数[${pageNumSum}],总页数[${pageSizeSum}],当前页数[${pageSize}]
					<input type="hidden" name="pageSize" id="pageSize"/>
					<input type="button" value="上一页" onclick="_page(-1)"/>
					<input type="button" value="下一页" onclick="_page(1)"/>
					<select name="pageNum" id="pageNum" onchange="_cgPage(this.value)">
					  <option value="5">5</option>
					  <option value="10">10</option>
					  <option value="20">20</option>
					  <option value="50">50</option>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>