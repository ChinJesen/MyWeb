<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>题库录入</title>
<style type="text/css">
	.optionDiv{
		width:80%;
		height:20px;
	}
	
	.optionDiv li{
		border: 1px solid;
	    padding: 5px;
	    width: 50px;
	    text-align: center;
	    cursor: pointer;
	    background-color: #4d5f5f;
	    font-size: 12px;
	    list-style-type: none;
    	float: left;
	}
	
	.contentDiv ul{
		height: 84px;
    	overflow: hidden;
	}
	
	.contentDiv ul li
	{
		list-style-type: none;
	}
</style>
<script>
	$(function(){
		//var a = ${user.userName};
		//alert(a);
		//通过专业获得科目
		$("#specialtyType").change(function(){
			var spCode = $(this).val();
			//移除option
			$("#subjectType option:gt(0)").remove();
			$("#knowledgePointType option:gt(0)").remove();
			
			if(spCode != ""){
				$.getJSON("<%=path%>/QusetionMrgServlet?flag=getSubJect&spCode="+spCode,function(data){
					if(data)
					{
						var obj = $("#subjectType");
						appendOption(obj,data);
					}
				});
			}
		});
		//通过科目获得知识点
		$("#subjectType").change(function(){
			var subCode = $(this).val();
			$("#knowledgePointType option:gt(0)").remove();
			if(subCode != null)
			{
				$.getJSON("<%=path%>/QusetionMrgServlet?flag=getKPointType&subCode="+subCode,function(data){
					if(data)
					{
						var obj = $("#knowledgePointType");
						appendOption(obj,data);
					}
				});
			}
		});
	});
	//增加选择下拉框
	function appendOption(obj,data)
	{
		for(var i in data)
		{
			for(var j in data[i])
			{
				for(var n in data[i][j])
				{
					obj.append("<option value='"+n+"'>"+data[i][j][n]+"</option>");
				}
			}
		}
		obj.removeAttr("disabled");
	}
</script>
<script type="text/javascript">
	//选项ABCDE的ascii增加
	$(function(){
		$(".optionDiv li:last").click(function(){
			$(this).prev().clone(true).insertBefore($(this));
			var newLi = $(this).prev();
			var ascii = 65 + newLi.index();
			if(ascii < 70)
			{
				var content = String.fromCharCode(ascii) +"选项";
				newLi.text(content);

				$(".contentDiv li:last").clone(true).insertAfter($(".contentDiv li:last"));
				$(".contentDiv li:last").find("textarea").val("");
				$(".contentDiv li:last").find("textarea").attr("name","option"+(newLi.index() + 1));
				
				$(this).prev().trigger("click");
				
				$(".anserDiv li:last").clone(true).insertAfter($(".anserDiv li:last"));
				$(".anserDiv li:last").find("label").text(content);
				
				$(".anserDiv li:last").find(":radio").val("option"+(newLi.index() + 1));
				$(".anserDiv li:last").find(":checkbox").val("option"+(newLi.index() + 1));
			}
			else
			{
				$(this).prev().remove();
			}
		});
		
		$(".optionDiv li:not(:last)").click(function(){
			$(".optionDiv li:not(:last)").css({"backgroundColor":"rgb(255, 152, 0)"});
			$(this).css({"backgroundColor":"#2196F3"});
			
			var _index = $(this).index();
			$(".contentDiv li").css({"display":"none"});
			$(".contentDiv li:eq("+_index+")").css({"display":"inline"});
			
		});
		
		$("#questionType").change(function(){
			var _val = $(this).val();
			if(_val != "")
			{
				$(".anserDiv li").find("label").prev().remove();
				if("single" == _val)
				{
					$.each($(".anserDiv li"),function(i,n){
						var j = i + 1;
						$(n).prepend("<input type='radio' name='answer' value='option"+j+"'/>");
					});
				}
				else if("multiple" == _val)
				{
					$.each($(".anserDiv li"),function(i,n){
						var j = i + 1;
						$(n).prepend("<input type='checkbox' name='answer' value='option"+j+"'/>");
					});
				}
				else if("shortanswer" == _val)
				{
					
				}
			}
		});
	});
</script>
<script type="text/javascript">
function _init(){
	
	var _specialtyType = "${qsBank.specialtyType}";
	if (_specialtyType != null && _specialtyType != "")
		{
		$("#specialtyType").val(_specialtyType);
		$("#specialtyType").trigger("change");
		
		var _subjectType = "${qsBank.subjectType}";
		if (_subjectType != null && _subjectType != "")
			{
				window.setTimeout(function(){
				$("#subjectType").val(_subjectType);
				$("#subjectType").trigger("change");
				
				var _knowledgePointType = "${qsBank.knowledgePointType}";
				if (_knowledgePointType != null && _knowledgePointType != "")
					{
						window.setTimeout(function(){
							$("#knowledgePointType").val(_knowledgePointType);
						},100);
					
					}
				},100);
			}
		}
	
//	var optionContent = "${optionContent}";
}
</script>
</head>
<body>
<form action="<%=path%>/QusetionMrgServlet" method="get">
	<input type="hidden" name="flag" value="save">
	<fieldset>
	<legend>题库录入</legend>
	<center>${msg}</center>
	<table border="1" width="70%" align="center">
		<tr>
			<th>题目</th>
			<td>
			<textarea name="question" rows="3" cols="20">${qsBank.question}</textarea>
			</td>
		</tr>
		<tr>
			<th>专业</th>
			<td>
			<select name="specialtyType" id="specialtyType">
				<option>--请选择--</option>
				<c:forEach items="${test['SP']}" var="sl">
					<option value="${sl.key}">${sl.value}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<th>科目</th>
			<td>
				<select id="subjectType" disabled="disabled" name="subjectType">
					<option value="">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>知识点</th>
			<td>
			<select id="knowledgePointType" disabled="disabled" name="knowledgePointType">
					<option value="">--请选择--</option>
				</select>
			</td>
		</tr>
		<tr>
			<th>题型</th>
			<td>
			<select name="questionType" id="questionType">
				<option>--请选择--</option>
				<c:forEach items="${test['options']}" var="etype">
					<option value="${etype.key}">${etype.value}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		<tr>
			<th>选项</th>
			<td>
			<div class="optionDiv">
				<ul>
					<li>A选项</li>
					<li>添加</li>
				</ul>
			</div>
			<div class="contentDiv">
				<ul>
					<li>
						<textarea name="option1" rows="4" cols="20"></textarea>
					</li>
				</ul>
			</div>
			<div class="anserDiv">
				<ol type="i">
					<li><label>A选项</label></li>
				</ol>
			</div>			
			</td>
		</tr>
		<tr>
			<th>创建人</th>
			<td>
			<input type="hidden" name="userid" value="${userInfo.userid}"/>
			<input type="text" value="${userInfo.usernm}" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
			<input type="submit" value="提交"/>
			<input type="reset" value="重写"/>
			</td>
		</tr>
	</table>
</fieldset>
</form>
</body>
</html>