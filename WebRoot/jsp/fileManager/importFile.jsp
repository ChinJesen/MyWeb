<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/WebRoot/css/commStyle.css">
</head>
<body>
<form action="<%=path%>/FileMrgServlet" method="post" enctype="multipart/form-data" target="resFrame">
	<input type="file" name="fileItem"/>
	<input type="text" name="mrgName">
	<input type="submit" value="上传">
</form>
<iframe name="resFrame" style="height: 500px; width: 100%;" frameborder="0" src="<%=path%>/jsp/fileManager/showResult.jsp"></iframe>
</body>
</html>