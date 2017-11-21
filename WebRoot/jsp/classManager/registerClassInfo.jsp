<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>班级管理</title>
<style type="text/css">
	.teacherDiv{
		width: 200px;
	   /* height: 200px;*/
	    border: 1px solid #999;
	    position: absolute;
	}
	.spanStyle{
		font-size: 12px;
		cursor: pointer;
	}
</style>
<script type="text/javascript">
var validFlag = false;
$(function(){
	$("#spTeachter").focus(function(){
		$("#showTeacherDiv").show();
	});
	var timeId;
	$("#spTeachter").keyup(function(){
		var _val = $(this).val();
		var url = "<%=path%>/clsMrg?flag=queryTeahcer&teacherName="+_val;
		window.clearTimeout(timeId);
		timeId = window.setTimeout(function(){sendAjax(url);},500);
		//经过多少时间后去执行某个方法
		//window.setTimeout("ts001()", 1000);
		//每经过多少时间后去执行某个方法
		//window.setInterval("ts001()", 1000);
	});
	$("#className").change(function(){
		var _val = $(this).val();
		var url = "<%=path%>/clsMrg?flag=queryClassInfo&className="+_val;
		$.get(url,function(data){
			if(data == true || data == "true"){
				$("#className").next().text("有效");
				validFlag = true;
			}else{
				$("#className").next().text("无效");
				validFlag = false;
			}
		});
	});
});

function sendAjax(url){
	$.getJSON(url,function(data){
		//在当前元素后面添加一个DIV图层
		$("#spTeachter").siblings().remove();
		$("#spTeachter").closest("td").append("<div id='showTeacherDiv'></div>");
		//$("#showTeacherDiv").css({"width":"200px","height":"200px","border":"1px solid red"});
		$("#showTeacherDiv").addClass("teacherDiv");
		$("#showTeacherDiv").mouseleave(function(){
			$(this).hide();		
		});
	
		var divWidth = $("#spTeachter").outerWidth() - 2;
		$("#showTeacherDiv").css({"width":divWidth});
		$("#showTeacherDiv").append("<table width='100%'>");
		var _tab = $("#showTeacherDiv>table");
		for(var i in data)
		{
			for(var j in data[i])
			{
				_tab.append("<tr>");
				var _tr = _tab.find("tr:last");
				_tr.append("<td style='border-bottom:1px dashed #f99a0e'>");
				_td = _tr.find("td:last");
				_td.append("<input type='checkbox' name='tcerId' value='"+data[i][j].userId+"'/> <span>");
				var _span = _td.find("span");
				_span.addClass("spanStyle");
				_span.append(data[i][j].userName);
				
				_tr.hover(function(){
					$(this).css({"backgroundColor":"#f99a0e"});
				},function(){
					$(this).css({"backgroundColor":""});
				});
				/*
				_tr.click(function(){
					var _text = $(this).find("td>span").text();
					$("#spTeachter").val(_text);
				});*/
			}
		}
		$("#showTeacherDiv").find(":checkbox").change(function(){
			var sVal = null;
			var sId = null;
			$.each($("#showTeacherDiv").find(":checked"),function(i,n){
				var _text = $(n).next().text();
				var _tcerId = n.value;
				if(sVal == null || sVal == "")
				{
					sVal = _text;
					sId = _tcerId;
				}
				else
				{
					sVal += ","+_text;
					sId += ","+_tcerId;
				}
				$("#spTeachter").val(sVal);
				$("#teacherIds").val(sId);
			});
		});
		
	});
}
function _init()
{
	var sp = "${clsInfo.specialty}";
	if(sp != null && sp != "")
	{
		$("#specialty").val(sp);
	}
	
	var spTeacher = "${spTeachter}";
	if(spTeacher != null || spTeacher != ""){
		$("#spTeacher").val(spTeacher);
	}
	var teacherIds = "${teacherIds}";
	if(teacherIds != null || teacherIds != ""){
		$("#teacherIds").val(teacherIds);
	}
}
function _sub(){
	if(validFlag == false){
		$("#className").next.text("无效");
		return false;
	}
	return true;
}
</script>
</head>
<body onload="_init()">
<form action="<%=path%>/clsMrg" method="get" onsubmit="return _sub()" autocomplete="off">
<fieldset>
<input type="hidden" name="flag" value="add"/>
<input type="hidden" name="teacherIds" id="teacherIds"/>
<legend>添加班级信息</legend>
<center>${msg}</center>
<div calss = "muenDiv">
<table align="center" border ="1">
	<tr>
		<th>班级姓名</th>
		<td>
			<input type= "text" name="className" id="className"/>
			<span></span>
		</td>
	</tr>
	<tr>
		<th>专业</th>
		<td>
			<select name="special" id="special">
		<c:forEach items="${test['SP']}" var="sl">
			<option value="${sl.key}">${sl.value}</option>
		</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th>开班时间</th>
		<td>
			<input type= "date" name="createTime" id=""/>
		</td>
	</tr>
	<tr>
		<th>毕业时间</th>
		<td>
			<input type= "date" name="endTime" id=""/>
		</td>
	</tr>
	<tr>
		<th>描述</th>
		<td>
			<textarea rows="5" cols="30" name="remark"></textarea>
		</td>
	</tr>
	<tr>
		<th>专业老师</th>
		<td>
			<input type="text" id="spTeachter" name="spTeachter"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="添加"/>
			<input type="reset" value="重写"/>
		</td>
	</tr>
</table>
</div>
</fieldset>
</form>
</body>
</html>