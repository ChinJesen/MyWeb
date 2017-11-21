<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@include file="../base.jsp" %>
<%-- <%
取消注释 import导firstweb包
	Object ps = request.getAttribute("pageSize");
	Object pn = request.getAttribute("pageNum");
	Object psSum = request.getAttribute("pageSizeSum");
	Object pnSum = request.getAttribute("pageNumSum");
	
	Object ob = request.getAttribute("clsInfo");
	ClassInfo clsInfo = null;
	if (ob!=null){
		clsInfo = (ClassInfo)ob;
	}
%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=path%>/WebRoot/css/commStyle.css">
<script type="text/javascript">
function _del(classId){
	if(window.confirm("确认删除？"))
	{
	window.location.href = "<%=path%>/clsMrg?flag=deleteclass&classId="+classId;
	}
}

function _modify(classId)
{
	window.location.href = "<%=path%>/clsMrg?flag=queryById&classId="+classId;	
}

function _page(num){
	var _pageNum = document.getElementById("pageNum").value;
	var _pageSize = document.getElementById("pageSize").value;
	_pageSize = parseInt(_pageSize) + parseInt(num);
	if(_pageSize>0 && _pageSize <= parseInt('${pageSizeSum}')){
	window.location.href = "<%=path%>/clsMrg?flag=queryAllclass&pageSize="+_pageSize+"&pageNum="+_pageNum;	
	}
}
function _cgPage(num)
{
	document.getElementById("pageSize").value = 1;
	window.document.forms[0].submit();
}
function _init(){
	document.getElementById("pageNum").value ='${pageNum}';
	document.getElementById("pageSize").value ='${pageSize}';
}
</script>
</head>
<body onload="_init()">
<form action="<%=path%>/clsMrg">
<input type="hidden" name="flag" value="queryAllclass">
<fieldset>
	<legend>按条件查询班级</legend>
	<table width="80%" align="center" border="1">
		<tr>
		<th>班级id</th>
			<td>
				<input type="text" name="classId" value='${classinfo.classId}'/>
			</td>
		<th>班级名称</th>
			<td>
				<input type="text" name="className" value='${classinfo.className}'/>
			</td>
			<td>
				<input type="submit" value="查询"/>
			</td>
		</tr>
	</table>
	</fieldset>
	<fieldset>
		<legend>班级查询结果</legend>
		<table width="80%" align="center" border="1">
			<tr>
				<th>班级ID</th>
				<th>班级姓名</th>
				<th>创建时间</th>
				<th>班级描述</th>
				<th>状态</th>
				<th>操作选项</th>
				
	<c:forEach items="${classList}" var="classes">
		<tr>
			<td>${classes.classId}</td>
			<td>${classes.className}</td>
			<td>${classes.createTime}</td>
			<td>${classes.remark}</td>
			<td>${classes.status}</td>
			<td align="center"><input onclick="_modify('${classes.classId}')" type="button" value="修改" onclick="_modify('${classes.classId}')"/><input onclick="_del('${classes.classId}')" type="button" value="删除"/></td>
		</tr>
	</c:forEach>
<%-- <%
	Object obj = request.getAttribute("classList");
		if (obj!=null){
			List<ClassInfo> classList = (List<ClassInfo>)obj;
			
			for(ClassInfo classes:classList)
			{
				out.print("<tr >");
				out.print("<td align="+"center"+">"+classes.getClassId()+"</td>");
				out.print("<td align="+"center"+">"+classes.getClassName()+"</td>");
				out.print("<td align="+"center"+">"+classes.getCreateTime()+"</td>");
				out.print("<td align="+"center"+">"+classes.getRemark()+"</td>");
				out.print("<td align="+"center"+">"+classes.getStatus()+"</td>");
				
				out.print("<td align="+"center"+"><input onclick='_modify('"+classes.getClassId()+"')' type='button' value='修改'/>");
				out.print("<input onclick='_del(\""+classes.getClassId()+"\")' type='button' value='删除'/></td>");
				out.print("</tr>");
			}
		}
%> --%>
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