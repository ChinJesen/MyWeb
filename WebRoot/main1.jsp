<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="jsp/base.jsp" %>
<%@taglib uri="http://tengen.com/prive" prefix="p" %>
<!DOCTYPE html>
<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<head>
<title>网页作业</title>
<script type="text/javascript">
	function showorhide(obj) {
		var _div = obj.nextSibling.nextSibling;
		var _display = _div.style.display;
		if (_display == "none") {
			_div.style.display = "inline";
		} else {
			_div.style.display = "none";
		}
	}
</script>

</head>
<body>
<div class="log">学校教务信息管理系统</div>
<div class="caozuo">可操作列表
	<p:prive value="XTGL">
	<div style="cursor: pointer;" onclick="showorhide(this)">系统管理</div>
	<div style="display: none;">
			<a href="jsp/sysManager/dictItemMrg.jsp" target="mainFrame"><li>数据字典维护</li></a>
		</div>
	</p:prive>
	<p:prive value="YHGL">
	<div style="cursor: pointer;" onclick="showorhide(this)">用户管理</div>
		<div style="display: none;">
			<a href="jsp/fileManager/importFile.jsp" target="mainFrame"><li>批量添加用户信息</li></a>
			<a href="jsp/registerUserInfo.jsp" target="mainFrame"><li>添加用户信息</li></a>
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>查询用户信息</li></a>
		</div>
	</p:prive>
	<p:prive value="BJGL">
	<div style="cursor: pointer;" onclick="showorhide(this)">班级管理</div>
		<div style="display: none;">
			<a href="jsp/classManager/registerClassInfo.jsp" target="mainFrame"><li>添加班级信息</li></a>
			<a href="<%=path%>/clsMrg?flag=queryAllclass" target="mainFrame"><li>查询班级信息</li></a>
		</div>
	</p:prive>
	<p:prive value="TKGL">
		<div style="cursor: pointer;" onclick="showorhide(this)">题库管理</div>
		<div style="display: none;">
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>批量添加题库信息</li></a>
			<a href="jsp/ExamManager/register.jsp" target="mainFrame"><li>添加题库信息</li></a>
			<a href="jsp/ExamManager/showExam.jsp" target="mainFrame"><li>查询题库信息</li></a>
		</div>
	</p:prive>
	<p:prive value="SJGL">
		<div style="cursor: pointer;" onclick="showorhide(this)">试卷管理</div>
		<div style="display: none;">
			<a href="jsp/fileManager/importFile.jsp" target="mainFrame"><li>批量添加用户信息</li></a>
			<a href="jsp/registerUserInfo.jsp" target="mainFrame"><li>添加用户信息</li></a>
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>查询用户信息</li></a>
		</div>
	</p:prive>
	<p:prive value="KSGL">
		<div style="cursor: pointer;" onclick="showorhide(this)">考试管理</div>
		<div style="display: none;">
			<a href="jsp/fileManager/importFile.jsp" target="mainFrame"><li>批量添加用户信息</li></a>
			<a href="jsp/registerUserInfo.jsp" target="mainFrame"><li>添加用户信息</li></a>
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>查询用户信息</li></a>
		</div>
	</p:prive>
	<p:prive value="ZXKS">
		<div style="cursor: pointer;" onclick="showorhide(this)">在线考试</div>
		<div style="display: none;">
			<a href="jsp/fileManager/importFile.jsp" target="mainFrame"><li>批量添加用户信息</li></a>
			<a href="jsp/registerUserInfo.jsp" target="mainFrame"><li>添加用户信息</li></a>
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>查询用户信息</li></a>
		</div>
	</p:prive>
	<p:prive value="LSJL">
		<div style="cursor: pointer;" onclick="showorhide(this)">历史记录</div>
		<div style="display: none;">
			<a href="jsp/fileManager/importFile.jsp" target="mainFrame"><li>批量添加用户信息</li></a>
			<a href="jsp/registerUserInfo.jsp" target="mainFrame"><li>添加用户信息</li></a>
			<a href="<%=path%>/userMrg?flag=queryAll" target="mainFrame"><li>查询用户信息</li></a>
		</div>
	</p:prive>
</div>
<div class="neirong">
	<iframe name="mainFrame" class="ifram" frameborder="0" src="http://www.swupl.edu.cn/"></iframe>
</div>
<div class="banquan">
<h6 align="center">
<a href="http://www.swupl.edu.cn/" target="_blank" >版权所有©西南政法大学</a> 
<a href="http://www.swupl.edu.cn/" target="_blank" >使用系统前必读</a> 
<a href="http://www.swupl.edu.cn/" target="_blank" >邮编：401120&nbsp;</a>
<a href="http://www.swupl.edu.cn/" target="_blank" >渝ICP备05001036号&nbsp;</a>
<a href="http://www.swupl.edu.cn/" target="_blank" >地址：重庆市渝北区回兴街道宝圣大道301号</a>
</h6>
</div>
</body>
</html>