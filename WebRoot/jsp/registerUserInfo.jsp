<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="base.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
		#clsDiv ul{
			margin: 0px;
    		padding: 0px;
		}
		
		#clsDiv li{
			list-style-type: none;
			width: 200px;
			float: left;
		}
		#clsDiv label{
			cursor: pointer;
		}
</style>
<script type="text/javascript">
	var xmlHttp;
	
	function _validUsNo(val){
		xmlHttp = new XMLHttpRequest();
		xmlHttp.open("POST", "<%=path%>/userMrg?flag=validUserNo&userno="+val,true);
		xmlHttp.onreadystatechange = _rollBack;
		xmlHttp.send(null);
	}

	function _rollBack() {
		//服务器返回完毕，且正常返回
		if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
			if(xmlHttp.responseText == true || xmlHttp.responseText == "true"){
				document.getElementById("userNoMsg").innerHTML = "账户可用!";
			}else{
				document.getElementById("userNoMsg").innerHTML = "账户已存在!";
			}
		}
	}

</script>
<script type="text/javascript" src="<%=path%>/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=path%>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		$("#photoFile").change(function(){
			$.ajaxFileUpload({
				url:"<%=path%>/userMrg?flag=ajaxfileupload",
				secureuri:false,
				/* 获取id */
				fileElementId:"photoFile",
				dataType:'json',
				/* 回掉函数 */
				success:function(data,status){
					$("#photoImg").attr("src","<%=path%>/userMrg?flag=ajaxFileDownload&fileName="+data.filePath);
					$("#photo").val(data.filePath);
				}
			});
		});
	});

function _init(){
	//性别恢复
	 var sex = "${user.sex}";
	 if(sex!=null&&sex!=""){
		 if ($("#sexb").val()==sex){
			 $("#sexb").attr("checked","checked");
		 }else{
		 $("#sexg").attr("checked","checked");
	 }
	}
	 //爱好恢复
	 var ahs = "${user.ah}";
	 if(ahs!=null&&ahs!=""){
		 var ahArray = ahs.split("_");
		 for(var i in ahArray){
			 var ah = ahArray[i];
			 $.each($(":checkbox"),function(i,n){
				 if(n.value == ah){
					 n.checked = "checked";
				 }
			 });
		 }
	 }
	 //籍贯恢复
	 var jg = "${user.jg}";
	 if(jg != null && jg != "");
	 $("#jg").val(jg);
	 //角色恢复
	 var roleId = "${user.roleId}";
	 if(roleId != null && roleId != "");
	 $("#roleId").val(roleId);
	 //照片恢复
	 var photo = "${user.photo}"
		 if(photo!=null&&photo!=""){
			 var url = "<%=path%>/userMrg?flag=ajaxFileDownload&fileName="+photo;
			 $("#photoImg").attr("src",url);
		 }
	 //简介恢复
	 var jj= "${user.jj}";
	 $("#jj").val(jj);
}
</script>
<script type="text/javascript">
		$(function(){
			changeClass();
			$("#roleId").change(function(){changeClass();});
			
		});
		
		function changeClass()
		{
			var _roleId = $("#roleId").val();
			var _tr = $("#roleId").closest("tr").next();
			if(_roleId == "" || _roleId == "manger")
			{
				_tr.hide();
			}
			else
			{
				if(_roleId == "teacher")
				{
					$.each(_tr.find("input[name='clsId']"),function(i,n){
						n.type = "checkbox";
					});
				}
				else if(_roleId == "student")
				{
					$.each(_tr.find("input[name='clsId']"),function(i,n){
						n.type = "radio";
					});
				}
				_tr.find("input[name='clsId']").css({"display":"inline"});
				_tr.show();
			}
		}
</script>
</head>
<body onload="_init()">
<form action="<%=path%>/userMrg" method="get">
<fieldset>
<input type="hidden" name="flag" value="save"/>
<legend>用户注册信息</legend>
<center>${msg}</center>
<div calss = "muenDiv">
<table align="center" border ="1">
	<tr>
		<th>账号</th>
		<td>
			<input type= "text" name="userno" id="userno" value="${user.userno}" onchange = "_validUsNo(this.value)"/>
			<span id="userNoMsg"></span>
		</td>
		<td rowspan="8" width="200px;">
			<img src="" id="photoImg" style="width: 200px; height: 200px;"/>
		</td>
	</tr>
	<tr>
		<th>密码</th>
		<td>
			<input type= "password" name="userpw" value="${user.userpw}" id=""/>
		</td>
	</tr>
	<tr>
		<th>姓名</th>
		<td>
			<input type= "text" name="usernm" value="${user.usernm}" id=""/>
		</td>
	</tr>
	<tr>
		<th>年龄</th>
		<td>
			<input type= "text" name="userag" value="${user.userag}" id=""/>
		</td>
	</tr>
	<tr>
		<th>出生日期</th>
		<td>
			<input type="text" name="birthday" id="birthday" value="${user.birthday}"/>
			<img id="dataImg" onclick="WdatePicker({el:'birthday'})" src="<%=path%>/js/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
		</td>
	</tr>
	<tr>
		<th>创建日期</th>
		<td>
			<input type="date" name="createTime" id="createTime" value="${user.createTime}"/>
		</td>
	</tr>
	<tr>
		<th>状态</th>
		<td>
			<input type="text" name="status" id="status" value="${user.status}"/>
		</td>
	</tr>
	<tr>
		<th>性别</th>
		<td>
			<input type= "radio" name="sex" value="1" id="sexb" checked="checked"/><label style="cursor: pointer;" for ="sexb">男</label>
			<input type= "radio" name="sex" value="0" id="sexg"/><label style="cursor: pointer;" for ="sexg">女</label>
		</td>
	</tr>
	<tr>
		<th>爱好</th>
		<td>
			<input type= "checkbox" name="ah" value="1" id="eat"/><label style="cursor: pointer;"  for = "eat">吃饭</label>
			<input type= "checkbox" name="ah" value="2" id="sleep"/><label  style="cursor: pointer;" for = "sleep">睡觉</label>
			<input type= "checkbox" name="ah" value="3" id="com" /><label style="cursor: pointer;" for = "com">写代码</label>
			<input type= "checkbox" name="ah" value="4" id="game"/><label style="cursor: pointer;" for = "game">打游戏</label>
		</td>
	</tr>
	<tr>
		<th>籍贯</th>
		<td>
			<select name="jg" id="jg">
		<c:forEach items="${test['JG']}" var="sl">
			<option value="${sl.key}">${sl.value}</option>
		</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th>照片</th>
		<td>
			<input type="hidden" id="photo" name="photo">
			<input type="file" name="photoFile" id="photoFile">
		</td>
	</tr>
	<tr>
		<th>简介</th>
		<td>
			<textarea rows="5" cols="40" name="jj" id="jj" value="${user.jj}"></textarea>
		</td>
	</tr>
	<tr>
		<th>角色</th>
		<td>
			<select id="roleId" name="roleId">
				<c:forEach items="${roleMap}" var="role">
					<option value="${role.key}">${role.value}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<th>班级</th>
		<td>
			<div id="clsDiv">
			<ul>
				<c:forEach items="${clsMap}" var="clsMap" varStatus="v">
					<li>
						<input type="radio" name="clsId" id="clsId_${v.count}" value="${clsMap.key}" style="display: none;"/>
						<label for="clsId_${v.count}">${clsMap.value}</label>
					</li>
				</c:forEach>
			</ul>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="注册"/>
			<input type="reset" value="重写"/>
		</td>
	</tr>
</table>
</div>
</fieldset>
</form>
</body>
</html>