<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html> <head> <meta http-equiv="Content-Type" content="text/html;
charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

<script src="../js/jquery-2.1.4.js"></script>
<link href="../css/taskdate.css" rel="stylesheet">
<title>任务日历</title>
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
	/* $.getJSON()方法获取后台servlet数据，$.each()方法把获取到的数据打入HTML中 */
	$.getJSON("<%=path%>/taskdateServlet", function(taskDate) {
		var preMonth = "dcc";

		var taskDatelist = "";
		// 判断每天的数据是否为空，若为空，当天就显示空字符串
		$.each(taskDate.results, function(index, value) {
			if (value.v1 == null) {
				value.v1 = " ";
				value.w1 = " ";
			}
			if (value.v2 == null) {
				value.v2 = " ";
				value.w2 = " ";
			}
			if (value.v3 == null) {
				value.v3 = " ";
				value.w3 = " ";
			}
			if (value.v4 == null) {
				value.v4 = " ";
				value.w4 = " ";
			}
			if (value.v5 == null) {
				value.v5 = " ";
				value.w5 = " ";
			}
			if (value.v6 == null) {
				value.v6 = " ";
				value.w6 = " ";
			}
			if (value.v7 == null) {
				value.v7 = " ";
				value.w7 = " ";
			}

			var curMonth = value.m;
			var month = "";
			if (curMonth == preMonth) {
				month = "";
			} else {
				preMonth = curMonth;
				month = curMonth;
			}

			taskDatelist += '<div style="text-align:center; margin:20 auto;background-color:black;color:white;font-size:22px;" >' + month + '</div>' +
				'<div id="data">' +
				'<div class="date">' + value.w1 +
				'<div class="data">' + value.v1 + '</div>' + '</div>' +
				'<div class="date">' + value.w2 +
				'<div class="data">' + value.v2 + '</div>' + '</div>' +
				'<div class="date">' + value.w3 +
				'<div class="data">' + value.v3 + '</div>' + '</div>' +
				'<div class="date">' + value.w4 +
				'<div class="data">' + value.v4 + '</div>' + '</div>' +
				'<div class="date">' + value.w5 +
				'<div class="data">' + value.v5 + '</div>' + '</div>' +
				'<div class="date">' + value.w6 +
				'<div class="data">' + value.v6 + '</div>' + '</div>' +
				'<div class="date">' + value.w7 +
				'<div class="data">' + value.v7 + '</div>' + '</div>' +
				'</div>' +
				'<hr style="width:80%;margin:0 auto;border: 0;height: 1px;background: #333;background-image: linear-gradient(to right, #ccc, #333, #ccc);">';
		});

		/* 在content元素插入上面的headerdata */
		$('#content').after(taskDatelist);
	})
</script>
</head>
<body>
	<div id="header">
		<div class="header">周一</div>
		<div class="header">周二</div>
		<div class="header">周三</div>
		<div class="header">周四</div>
		<div class="header">周五</div>
		<div class="header">周六</div>
		<div class="header">周日</div>
		<hr
			style="width:100%;margin:0 auto;border: 0;height: 1px;background: #333;background-image: linear-gradient(to right, #ccc, #333, #ccc);">
	</div>
	<div class="clear"></div>
	<div id="content"></div>
</body>
</html>