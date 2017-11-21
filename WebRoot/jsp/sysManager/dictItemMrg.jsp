<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var xmlHttp;

function _onCgGroupList(val){
	if (val != null ||!"".equals(val)){
	xmlHttp = new XMLHttpRequest();
	xmlHttp.open("GET","<%=path%>/DictMrgServlet?flag=queryDict&groupCode="+val,true);
	xmlHttp.onreadystatechange = rollBack;
	xmlHttp.send(null);
	}
}
function rollBack()
{
	if(xmlHttp.readyState == 4 && xmlHttp.status == 200)
	{
		var content = xmlHttp.responseText;
		/*JS当中eval函数可以将字符串转换为对象*/
		var array = eval(content);
			var _tab = document.getElementById("dictTab");
			for(var i = _tab.rows.lenth - 1;i>0;i--){
				_tab.removeChild(_tab.rows[i]);
			}
		for(var i in array){
		
			var _tr = document.createElement("tr");
			var _td1 = document.createElement("td");
			var _td2 = document.createElement("td");
			var _td3 = document.createElement("td");
			var _td4 = document.createElement("td");
			var _td5 = document.createElement("td");
			var _td6 = document.createElement("td");
			
			_td1.innerHTML = "<input type='hidden' name='dictCode' value='"+ array[i].dictCode+"'>"+ array[i].dictCode;
			_td2.innerHTML = array[i].dictValue;
			_td3.innerHTML = array[i].sn;
			_td4.innerHTML = array[i].status;
			_td5.innerHTML = array[i].remark;
			_td6.innerHTML = "<input type='button' value='修改' onclick='_modify(this)'><input type='button' value='删除' onclick='_delete(this)'>";
			
			_tr.appendChild(_td1);
			_tr.appendChild(_td2);
			_tr.appendChild(_td3);
			_tr.appendChild(_td4);
			_tr.appendChild(_td5);
			_tr.appendChild(_td6);
			_tab.appendChild(_tr); 
		}
	}
}

function _modify(obj){
	
	var _tr = obj.parentNode.parentNode;
	for(var i=0;i<_tr.cells.length - 1;i++){
		var content = _tr.cells[i].innerText;
		_tr.cells[i].innerText = "";
		var _input = document.createElement("input");
		_input.type = "text";
		switch(i){
		case 0:
			_input.name = "dictCodeNew";
			break;
		case 1:
			_input.name = "dictValue";
			break;
		case 2:
			_input.name = "sn";
			break;
		case 3:
			_input.name = "status";
			break;
		case 4:
			_input.name = "remark";
			break;
		}
		_input.value=content;
		_tr.cells[i].appendChild(_input);
	}
}
function _delete(obj){
	var _tr = obj.parentNode.parentNode;
	_tr.remove();
}

function _addRow(){
	var _tab = document.getElementById("dictTab");

	var _tr = document.createElement("tr");
	var _td1 = document.createElement("td");
	var _td2 = document.createElement("td");
	var _td3 = document.createElement("td");
	var _td4 = document.createElement("td");
	var _td5 = document.createElement("td");
	var _td6 = document.createElement("td");
	
	_td1.innerHTML = "<input type='text' name='dictCodeNew'/>";
	_td2.innerHTML = "<input type='text' name='dictValue'/>";
	_td3.innerHTML = "<input type='text' name='sn'/>";
	_td4.innerHTML = "<input type='text' name='status'/>";
	_td5.innerHTML = "<input type='text' name='remark'/>";
	_td6.innerHTML = "<input type='button' value='修改' onclick='_modify(this)'><input type='button' value='删除' onclick='_delete(this)'>";
	
	_tr.appendChild(_td1);
	_tr.appendChild(_td2);
	_tr.appendChild(_td3);
	_tr.appendChild(_td4);
	_tr.appendChild(_td5);
	_tr.appendChild(_td6);
	_tab.appendChild(_tr); 
}

function _addGroup(obj)
{
	if(obj.checked)
	{
		//document.getElementById("optionGroupDiv").style.display = "none";
		document.getElementById("addOptionGroupDiv").style.display = "inline";
	}
	else
	{
		//document.getElementById("optionGroupDiv").style.display = "inline";
		document.getElementById("addOptionGroupDiv").style.display = "none";
	}
}

function addGroupOption(){
	
	var _groupCode = document.getElementById("groupCode").value;
	var _groupName = document.getElementById("groupName").value;
	if((_groupCode != null && _groupCode != "")&&
			(_groupName != null && _groupName != ""))
	{
		var _select = document.getElementById("jg");
		var option = document.createElement("option");
		option.value = _groupCode+"_"+_groupName;
		//option.value=_groupCode;
		option.innerText = _groupName;
		option.selected = "selected";
		_select.appendChild(option);
		document.getElementById("optionGroupDiv").style.display = "inline";
		document.getElementById("addOptionGroupDiv").style.display = "none";
		document.getElementById("groupCode").value ="";
		document.getElementById("groupName").value ="";
		document.getElementById("addGroup").checked = "";
		/*清除数据*/
			var _tab = document.getElementById("dictTab");
			for(var i=_tab.rows.length - 1;i>0;i--)
			{
				_tab.removeChild(_tab.rows[i]);
			}
	}
}
</script>
</head>
<body class="input">
<form action="<%=path%>/DictMrgServlet">
<input type="hidden" name="flag" value="save">
<fieldset>
	<legend>数据字典维护</legend>
<table class="th" width="100%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th width="10%">工作组</th>
			<td width="70%">
				<div id="optionGroupDiv" style="display:inline;width: 300px;">
					<select id="jg" name="jg" onchange="_onCgGroupList(this.value)">
						<option value="">--请选择--</option>
						<c:forEach items="${groupMap}" var="group">
							<option value="${group.key}">${group.value}</option>
						</c:forEach>
					</select>
				</div>
				<input type="checkbox" id="addGroup" onchange="_addGroup(this)" value=""/><label for="addGroup" style="font-size:12px;cursor: pointer">新增</label>
			</td>
		
		
	<td align="center" width="20%">
		<input type="submit" value="保存">
		<input type="button" value="新增数据字典" onclick="_addRow()">
	</td>
	</tr>
</table>
<div id="addOptionGroupDiv" style="display: none;">
		<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<th>工作组Code</th>
			<td><input type="text" id="groupCode"/></td>
			<th>工作组Name</th>
			<td><input type="text" id="groupName"/></td>
			<td><input type="button" value="添加工作组" onclick="addGroupOption()"/></td>
		</tr>
		</table>
	</div>
<hr>
<table id="dictTab" class="th" width="100%" border="1" cellpadding="0" cellspacing="0">
	<tr>
		<th>字典组Code</th>
		<th>字典组Value</th>
		<th>序列</th>
		<th>状态</th>
		<th>描述</th>
		<th>操作</th>
	</tr>
</table>
</fieldset>
</form>
</body>
</html>