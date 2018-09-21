<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<script src="../js/jquery-2.1.4.js"></script>
<link href="../css/order.css" rel="stylesheet">
<link href="../css/dateSelect.css" rel="stylesheet">
<script	src="../js/date.js"></script>
<title>下单</title>
<!-- <script type="text/javascript">
	// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
	var useragent = navigator.userAgent;
	if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
		// 这里警告框会阻塞当前页面继续加载
		alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
		// 以下代码是用javascript强行关闭当前页面
		var opened = window.open('about:blank', '_self');
		opened.opener = null;
		opened.close();
	}
</script> -->

<script>
	/* $.getJSON()方法获取后台servlet数据，js方法把获取到的数据打入HTML中 */
	$.getJSON("<%=path%>/contractServlet", function(contractData) {
		$("#contractId").val(contractData.合同编号);
		$("#projectName").val(contractData.工程名称);
		$("#builder").val(contractData.施工单位);
		$("#address").val(contractData.施工地点);
		$("#distance").val(contractData.运距);
	})
</script>
<script>
	function validate_required(field, alerttxt) {
		with (field){
		if (value == null || value == "") {
			alert(alerttxt);return false
		} else {
			return true
		}
		}
	}

	function validate_form(thisform) {
		with (thisform){
		if (validate_required(projectName, "工程名称不能为空") == false) {
			projectName.focus();return false
		}
		if (validate_required(builder, "施工单位不能为空") == false) {
			builder.focus();return false
		}
		if (validate_required(address, "施工地点不能为空") == false) {
			adderss.focus();return false
		}
		if (validate_required(constructionSite, "施工部位不能为空") == false) {
			constructionSite.focus();return false
		}
		if (validate_required(type, "产品种类不能为空") == false) {
			type.focus();return false
		}
		if (validate_required(need, "数量不能为空") == false) {
			need.focus();return false
		}
		if (validate_required(strength, "强度等级不能为空") == false) {
			strength.focus();return false
		}
		if (validate_required(collapse, "坍落度不能为空") == false) {
			collapse.focus();return false
		}
		if (validate_required(deliverTime, "供砼时间不能为空") == false) {
			deliverTime.focus();return false
		}
		if (validate_required(phone, "联系电话不能为空") == false) {
			phone.focus();return false
		}
		}
	}
</script>

</head>
<body>
	<form action="<%=path%>/orderServlet" method="post"
		onsubmit="return validate_form(this)">
		<div id="content">
			<h2>添&nbsp;加&nbsp;任&nbsp;务</h2>
			<div class="info">
				<div class="left">任务编号：</div>
				<div class="right">
					<input type="text" id="taskId" name="taskId"
						placeholder="自动生成，不需填写" readonly="readonly" />
				</div>
			</div>
			<div class="info">
				<div class="left">合同编号：</div>
				<div class="right">
					<input type="text" id="contractId" name="contractId"
						readonly="readonly" />
				</div>
			</div>
			<div class="info">
				<div class="left">工程名称：</div>
				<div class="right">
					<input type="text" id="projectName" name="projectName" />
				</div>
			</div>
			<div class="info">
				<div class="left">施工单位：</div>
				<div class="right">
					<input type="text" id="builder" name="builder" />
				</div>
			</div>
			<div class="info">
				<div class="left">施工地点：</div>
				<div class="right">
					<input type="text" id="address" name="address" />
				</div>
			</div>
			<div class="info">
				<div class="left">运&nbsp;&nbsp;&nbsp;&nbsp;距：</div>
				<div class="right">
					<input type="text" id="distance" name="distance" />
				</div>
			</div>
			<div class="info">
				<div class="left">施工部位：</div>
				<div class="right">
					<input type="text" id="constructionSite" name="constructionSite"
						placeholder=" 必填" />
				</div>
			</div>
			<div class="info">
				<div class="left">产品种类：</div>
				<div class="right">
					<select name="type">
						<option value="砼">砼</option>
						<option value="砂浆">砂浆</option>
					</select>
				</div>
			</div>
			<div class="info">
				<div class="left">数&nbsp;&nbsp;&nbsp;&nbsp;量：</div>
				<div class="right">
					<input type="text" id="need" name="need" placeholder=" 请填写数字"
						onkeyup="value=value.replace(/[^\d]/g,'')" />
				</div>
			</div>
			<div class="info">
				<div class="left">强度等级：</div>
				<div class="right">
					<select name="strength">
						<option selected disabled style="display: none;">-- 请选择 --</option>
						<option value="C10">C10</option>
						<option value="C15">C15</option>
						<option value="C20">C20</option>
						<option value="C25">C25</option>
						<option value="C30">C30</option>
						<option value="C35">C35</option>
						<option value="C40">C40</option>
						<option value="C45">C45</option>
						<option value="C50">C50</option>
						<option value="C55">C55</option>
						<option value="C60">C60</option>
					</select>
				</div>
			</div>
			<div class="info">
				<div class="left">抗冻等级：</div>
				<div class="right">
					<select name="antifreeze">
						<option selected disabled style="display: none;">-- 请选择 --</option>
						<option value="F50">F50</option>
						<option value="F100">F100</option>
						<option value="F100">F150</option>
						<option value="F200">F200</option>
						<option value="F250">F250</option>
						<option value="F300">F300</option>
						<option value="F350">F350</option>
						<option value="F400">F400</option>
						<option value=">F400">>F400</option>
					</select>					
				</div>
			</div>
			<div class="info">
				<div class="left">抗渗等级：</div>
				<div class="right">
					<select name="impervious">
						<option selected disabled style="display: none;">-- 请选择 --</option>
						<option value="P4">P4</option>
						<option value="P6">P6</option>
						<option value="P8">P8</option>
						<option value="P10">P10</option>
						<option value="P12">P12</option>
						<option value=">P12">>P12</option>
					</select>
				</div>
			</div>
			<div class="info">
				<div class="left">坍落度：</div>
				<div class="right">
					<select name="collapse">
						<option selected disabled style="display: none;">-- 请选择 --</option>
						<option value="100 ± 20">100 ± 20</option>
						<option value="120 ± 20">120 ± 20</option>
						<option value="140 ± 20">140 ± 20</option>
						<option value="160 ± 20">160 ± 20</option>
						<option value="180 ± 20">180 ± 20</option>
						<option value="200 ± 20">200 ± 20</option>
						<option value="220 ± 20">220 ± 20</option>
					</select>
				</div>
			</div>
			<div class="info">
				<div class="left">浇筑方式：</div>
				<div class="right">
					<select name="concreting">
						<option selected disabled style="display: none;">-- 请选择 --</option>
						<option value="泵送（37米）">泵送（37米）</option>
						<option value="泵送（48米）">泵送（48米）</option>
						<option value="泵送（52米）">泵送（52米）</option>
						<option value="自卸">自卸</option>
						<option value="自备泵">自备泵</option>
						<option value="塔吊">塔吊</option>
					</select>
				</div>
			</div>
			<div class="info">
				<div class="left">供砼时间：</div>
				<div class="right">
					<input type="text" id="date" name="deliverTime"
						placeholder=" 必填，格式：1996-05-01" />
				</div>
			</div>
			<div class="info">
				<div class="left">联系电话：</div>
				<div class="right">
					<input type="text" id="phone" name="phone" placeholder=" 必填" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				</div>
			</div>
			<div class="info">
				<div class="left">备&nbsp;&nbsp;&nbsp;&nbsp;注：</div>
				<div class="right">
					<textarea rows="7" cols="30" id="comment" name="comment"
						placeholder="请填写"></textarea>
				</div>
			</div>
			<div class="info">
				<div class="left">录入人：</div>
				<div class="right">
					<input type="text" id="entering" name="entering" placeholder=" 请填写" />
				</div>
			</div>
			<div class="info">
				<div class="left">分公司列表：</div>
				<div class="right">
					<input type="text" id="filiale" name="filiale" placeholder=" 请填写" />
				</div>
			</div>
			<div id="submit">
				<input type="submit" value="确 认 并 提 交" />
			</div>
		</div>
	</form>
	<script type="text/javascript" src="../js/dateSelect.js"></script>
	<script type="text/javascript">
		$("#date").dateSelect();
	</script>
</body>
</html>