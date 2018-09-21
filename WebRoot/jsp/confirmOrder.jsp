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

<title>确认订单</title>

<link href="../css/confirmOrder.css" rel="stylesheet">
<script src="../js/jquery-2.1.4.js"></script>
<script src="http://mockjs.com/dist/mock.js"></script>
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
	$.ajax({
		url : 'https://easy-mock.com/mock/5b6d34c1e8d0181ad6818c6d/WeixinTest/confirmOrder',
		
		dataType : 'json'
	}).done(function(res) {
		$("#contract").html(res.合同编号);
		$("#getCarNum").val(res.车号);
		$("#carNum").html(res.车号);
		$("#projectName").html(res.工程名称);
		$("#builder").html(res.施工单位);
		$("#address").html(res.施工地点);
		$("#constructionSite").html(res.施工部位);
		$("#type").html(res.产品种类);
		$("#need").html(res.数量);
		$("#strength").html(res.强度等级);
		$("#antifreeze").html(res.抗冻等级);
		$("#impervious").html(res.抗渗等级);
		$("#collapse").html(res.坍落度);
		$("#concreting").html(res.浇注方式);
		$("#deliverTime").html(res.供砼时间);
	})
</script>
</head>
<body>
	<form action="<%=path %>/confirmOrderServlet?" method="get" >
		<div id="content">
			<h2>确&nbsp;认&nbsp;订&nbsp;单</h2>
			<input type="text" name="getCarNum" id="getCarNum" hidden>
			<div class="info">
				<div class="left">合同编号：</div>
				<div class="right" id="contract">
					<!-- Y218-00043 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">车号：</div>
				<div class="right" id="carNum">
					<!-- 168 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">工程名称：</div>
				<div class="right" id="projectName">
					<!-- 恒大御景湾 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">施工单位：</div>
				<div class="right" id="builder">
					<!-- 中国建筑第五工程局有限公司 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">施工地点：</div>
				<div class="right" id="address">
					<!-- 新村 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">施工部位：</div>
				<div class="right" id="constructionSite">
					<!-- 15-34/A-U轴12层墙柱、构造柱 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">产品种类：</div>
				<div class="right" id="type">
					<!-- 砼 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">数&nbsp;&nbsp;&nbsp;&nbsp;量：</div>
				<div class="right" id="need">
					<!-- 6.00 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">强度等级：</div>
				<div class="right" id="strength">
					<!-- C30 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">抗冻等级：</div>
				<div class="right" id="antifreeze">
					<!-- F300 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">抗渗等级：</div>
				<div class="right" id="impervious">
					<!-- P8 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">坍落度：</div>
				<div class="right" id="collapse">
					<!-- 120 ± 20 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">浇筑方式：</div>
				<div class="right" id="concreting">
					<!-- 自卸 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div class="info">
				<div class="left">供砼时间：</div>
				<div class="right" id="deliverTime">
					<!-- 2018-08-10 -->
				</div>
			</div>
			<div style="clear:both"></div>
			<div id="submit">
				<input type="submit" value="确 认 收 货" onclick="fun()"/>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		/* form表单提交的地址中带参数 */
		function fun(){
			var getCarNum = $("#getCarNum").val();
			var form = $("from")[0];
			form.action = form.action+"getCarNum="+getCarNum;
		}
	</script>
</body>
</html>