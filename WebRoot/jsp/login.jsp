<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<title>登录</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

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
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

a {
	text-decoration: none;
}
</style>

</head>
<%
	String username = "";
	//记住密码功能，得到客户端保存的Cookie数据，回显到username输入框
	Cookie[] cookies = request.getCookies();
	for (int i = 0; cookies != null && i < cookies.length; i++) {
		if ("username".equals(cookies[i].getName())) {
			username = cookies[i].getValue();
		}
	}
%>
<body style="background-color: white;">
	<form action='<%=path%>/loginServlet' method='post'>
		<div
			style="width:100%;height:466px;background: url('../images/weixin_half.png');background-repeat: no-repeat;background-size: contain;position: absolute;">
			<div style="clear:both;"></div>
			<div style="text-align: center;margin-top:330px;">
				<input type="text" id="username" name="username" value='<%=username%>'
					placeholder=" 请输入工程识别码" size="35"
					style="width:200px;height:30px;border-radius: 15px;padding-left:10px;" />
				<a href="http://sao315.com/w/api/saoyisao"><input type="button"
					value="┇" style="border-radius: 5px;height:30px;" /> </a><br /> <br />
				<input type="submit" value="点 击 登 录" id="submit"
					style="background-color: #7ED321;width: 106px;height: 36px;color:
				#FFFFFF;border-radius: 15px;font-weight: bolder;" />
			</div>
		</div>
	</form>


	<script type="text/javascript">
		var qr = GetQueryString("qrresult");
		if (qr) {
			$("#username").val(qr);
		}
	
		function GetQueryString(name) {
			var reg = new RegExp("\\b" + name + "=([^&]*)");
			var r = location.href.match(reg);
			if (r != null) return unescape(r[1]);
		}
	</script>

</body>
</html>