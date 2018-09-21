<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

<title>电子签单列表</title>
<link href="../css/ordersToConfirm.css" rel="stylesheet">
<script src="../js/jquery-2.1.4.js"></script>
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

</head>
<body>
	<div id="header">
		订&nbsp;单&nbsp;列&nbsp;表
	</div>
	<div id="content"></div>
	<script>
		$.getJSON('https://easy-mock.com/mock/5b6d34c1e8d0181ad6818c6d/WeixinTest/ordersToConfirm', function(orders) {
	
			var orderslist = "";
			$.each(orders.results, function(index, value) {
				orderslist += '<a onclick="trans(this)"><div hidden id="carNum"> + value.车号 + </div><div id="content_head">&nbsp;&nbsp;<span>' +
					value.车号  + '</span>&nbsp;&gt;&nbsp;' + value.浇注方式 + '</div>' +
					'<div class="clear"></div>' +
					'<div id="content_left_up"><span>' + value.数量 + '</span>m<sup>3</sup></div>' +
					'<div id="content_right_up">' + value.工程名称 + '</div>' +
					'<div class="clear"></div>' +
					'<div id="content_left_down">' + value.强度等级 + '</div>' +
	     			'<div id="content_right_down">' + value.施工部位 + '</div>' +
					'<div class="clear"></div>' +
					'</a>' +
					'<hr style="width:80%;margin:0 auto;border: 0;height: 1px;background: #333;background-image: linear-gradient(to right, #ccc, #333, #ccc);"/><br/>';
			});
	
			/* 在content元素插入上面的table */
			$('#content').after(orderslist);
		})
		
		function trans(obj){
			var carNum = obj.children[0].innerHTML;
			
			location.href = "<%=path %>/jsp/confirmOrder.jsp?carNum="+carNum;
		}
	</script>
</body>
</html>