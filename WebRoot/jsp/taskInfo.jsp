<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

<link rel="stylesheet" href="../css/taskInfo.css">
<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/moment.js"></script>
<title>任务详情</title>
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
<script type="text/javascript">
	/* 获取地址栏信息，截取taskId */
	var loc = location.href;
	var n1 = loc.length; //地址的总长度
	var n2 = loc.indexOf("="); //取得=号的位置
	var taskId = decodeURI(loc.substr(n2 + 1, n1 - n2)); //从=号后面的内容
	/* alert(id); */

	/* $.getJSON()方法根据taskId获取后台servlet数据，$.each()方法把获取到的数据打入HTML中 */
	$.getJSON("<%=path%>/taskInfoServlet?taskId=" + taskId, function(taskInfo) {

		var taskInfolist = "";

		$.each(taskInfo.results, function(index, value) {
			/* 利用moment.js对时间进行格式化 */
			var carTime = moment(value.出车时间).format('MM-DD HH:mm');
			/* alert(carTime); */
			taskInfolist += '<div id="header_left" style="background:#EEEEEE; margin-top:5px;float:left;">&nbsp;' + value.发货单编号 + '</div>' +
				'<div id="header_right">' + carTime + '</div><div style="clear:both;"></div>' +
				'<div id="footer" style="background:#F5F5FF;">' +
				'<div class="footer">' + value.车号 + '</div>' +
				'<div class="footer">' + value.货物类型 + '</div>' +
				'<div class="footer">' + value.签收量 + '</div>' +
				'<div class="footer">' + value.浇筑方式 + '</div><div style="clear:both;"></div>' +
				'</div>';

		});
		/* alert(taskInfolist); */
		$('#content').after(taskInfolist);

	})
</script>

</head>
<body>
	<div id="header">
		<div class="header">车号</div>
		<div class="header">货物类型</div>
		<div class="header">签收量</div>
		<div class="header">浇筑方式</div>
		<div style="clear:both;"></div>
	</div>
	<div id="content"></div>
</body>
</html>