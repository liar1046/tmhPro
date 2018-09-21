<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<link rel="icon"
	href="https://static.jianshukeji.com/highcharts/images/favicon.ico">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/moment.js"></script>
<link href="../css/task.css" rel="stylesheet">
<title>任务列表</title>
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
	var finished = 0;
	var need = 0;

	/* $.getJSON()方法获取后台servlet数据，$.each()方法把获取到的数据打入HTML中 */
	$.getJSON("<%=path%>/taskServlet", function(data) {

		var headerdata = "";
		var tasklist = "";

		/* finished和need分别为已完成供应量和总需求量，遍历所有任务获得 */

		$.each(data.results, function(index, value) {
			var total = parseInt(index) + 1;
			var tongTime = moment(value.供砼时间).format("MM月DD日HH点");
			headerdata = '<div class="clear"></div><div id="header_data"><a href="taskdate.jsp">' +
				'<div id="finished">' + parseInt(finished) + '<div id="line">' + need + '</div></div></a>' +
				'</div><div class="clear"></div>' +
				'<div id="add">' +
				'<div id="tasklist">任务列表</div><a href="order.jsp">' +
				'<img src="../images/add.png" width="40px;" height="40px"></a>' +
				'<div id="total">共&nbsp;' + total + '&nbsp;条</div><div class="clear">' +
				'</div><div class="clear">';
			/* alert(index+':'+value); */
			tasklist += '<a onclick="trans(this)">' +
				'<div hidden id="taskId">' + value.任务编号 + '</div>' +
				'<div id="content_head">' +
				value.任务编号 + '&nbsp;&gt;&nbsp' + tongTime + '&nbsp;&gt;&nbsp<span>' + value.浇注方式 + '</span>' +
				'</div>' +
				'<div class="clear">' + '</div>' +
				'<div id="content_left_up">' +
				'<span>' + value.完成 + '</span>m<sup>3</sup>' +
				'</div>' +
				'<div id="content_right_up">' + value.工程名称 + '</div>' +
				'<div class="clear">' + '</div>' +
				'<div id="content_left_down">' + value.强度 + '</div>' +
				'<div id="content_right_down">' + value.施工部位 + '</div>' +
				'<div class="clear">' + '</div>' +
				'</a>' +
				'<hr style="width:80%;margin:0 auto;border: 0;height: 1px;background: #333;background-image: linear-gradient(to right, #ccc, #333, #ccc);">' +
				'<br>';

			finished += parseFloat(value.完成);
			need += parseFloat(value.需供);

		});

		/* 引用 */
		$(function() {
			var finish = parseInt(finished);
			var unfinish = parseInt(need - finished);
			$('#header_data').css("height", 150);
			$('#header_data').css("width", 150);
			$('#header_data').highcharts({
				chart : {
					type : 'pie',
				},
				title : {
					text : ''
				},
				plotOptions : {
					pie : {
						dataLabels : {
							enabled : true,
							distance : -50,
							y : 10,
							style : {
								fontSize : "12px"
							}
						},
						stacking : 'normal'
					}
				},
				credits : {
					enabled : false
				},
				series : [ {
					type : 'pie',
					name : '任务完成占比',
					size : '100%',
					dataLabels : {
						distance : -40
					},
					data : [ {
						name : '',
						y : finish,
						dataLabels : {
							rotation : 0,
						}
					}, {
						name : '',
						y : unfinish,
						dataLabels : {
							rotation : 0
						}
					} ]
				} ]
			}, function(c) {
				setTimeout(function() {
					rotate(c)
				}, 500)
			});
		});
		function rotate(chart) {
			var allY,
				angle1,
				angle2,
				angle3;
			$.each(chart.series, function(i, s) {
				angle1 = 0;
				angle2 = 0;
				angle3 = 0;
				allY = 0;
				$.each(s.points, function(i, p) {
					allY += p.y;
				});
				$.each(s.points, function(i, p) {
					angle2 = angle1 + p.y * 360 / (allY);
					angle3 = angle2 - p.y * 360 / (2 * allY);
					p.update({
						dataLabels : {
							rotation : angle3 > 180 ? 90 + angle3 : -90 + angle3
						}
					})
					angle1 = angle2;
				});
			});
		}

		/* 在content元素插入上面的headerdata */
		$('#header').after(headerdata);
		/* 在content元素插入上面的table */
		$('#content').after(tasklist);

		// 仅仅用来加载contractServlet信息，执行contractServlet		
		$.getJSON("<%=path%>/contractServlet", function(contractData) {})

	})
</script>
</head>
<body>
	<div id="header" style="width:100%;">
		<div id="header_data" style="width:200px;height:200px;margin:0 auto;"></div>
	</div>
	<div id="content"></div>
</body>
<script>
	/* 获取taskId,并传到任务详情界面 */
	function trans(adata) {
		var taskId = adata.children[0].innerHTML;
		/* alert(taskId); */

		location.href = "<%=path%>/jsp/taskInfo.jsp?taskId=" + taskId;
	}
</script>
</html>