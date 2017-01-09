<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>liveComment</title>
<link href="<%=basePath%>/css/liveComment.css" rel="stylesheet">
<script type="text/javascript">
	var liveId='${liveId}',uId='${uId}',liveStartTime=(new Date()).getTime()-1000*60*5;
</script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/liveComment.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/colorpicker.js"></script>
</head>
<body>
	<div class="title">
		<div class="bkpic">
			<span>选择背景</span>
			<div class="colorPanel">
				<div id="picker"></div>
		        <div id="slide"></div>
			</div>
		</div>
		<div class="welcome">欢迎进入<span class="fmid">${fmid}</span>直播间</div>
		<div class="date">日期：<span class="category">${cid}</span></div>
	</div>
	<div class="living-wrap">
		<div class="online">
			在线<span id="onlineUser">0</span>人,累计<span id="onlineTotal">0</span>人
		</div>
		<div id="liveComment">
		</div>
	</div>
	
</body>
</html>