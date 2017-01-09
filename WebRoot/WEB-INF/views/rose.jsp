<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>台风和玫瑰</title>
<link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<style type="text/css">
	.item{border-right:1px solid #fff;padding:8px 8px;color:#fff;font-size:16px;font-weight:bold;width:200px;float:left;text-align: center}
	.fmlist{cursor:pointer}
	.title{font-size: 18px;font-weight:bold;line-height:36px;color:#fff;background-color: #23A70F;padding-left:18px}
	.row{margin-top:10px;}
	a{color:#fff;text-decoration: none}
	a:hover{text-decoration: none;color:#fff}
	body{background: url(<%=basePath%>img/index.jpg) center top;}
</style>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="item" style="background-color:#e74c3c"><a target="_new" href="../living/open">抓取入口</a></div>
			<div class="item" style="background-color:#8e44ad"><a target="_new" href="../wordslist/yourComment">查询入口</a></div>
		</div>
		<div class="row">
			<p class="title">主播列表</p>
			<div class="fmlist"></div>
		</div>
	</div>
	<script type="text/javascript">
		var colors=["#1abc9c","#2ecc71","#e74c3c","#8e44ad","#3498db","#f1c40f","#34495e","#16a085","#F4511E"];
		$(function(){
			$.ajax({
				type:"get",
				url:"allfm",
				success:function(data){
					var content="";
					var list=eval("("+data+")");
					for(var i=0;i<list.length;i++){
						var index=parseInt(Math.random()*colors.length);
						var bgColor=colors[index];
						/* var dc=colors.splice(index, 1);
						console.log(dc) */
						content+="<div class='item' style='background-color:"+bgColor+"'>"+list[i]+"</div>";
					}
					$(".fmlist").html(content);
				}
			});
			$(".fmlist").click(function(e){
				var t=e.target;
				if(t.className=="item"){
					var fmid=t.innerText;
					var url="fmitem?fmid="+fmid;
					window.open(url);
				}
			});
		});
	</script>
</body>
</html>
