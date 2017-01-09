<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>add work</title>
<link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<style type="text/css">
input {
	vertical-align: middle;
	height: 34px;
}

.itm {
	padding: 5px 0;
	border-top: 1px dotted #ccc;
	margin-top: 4px;
	overflow: hidden;
}

.head {
	overflow: hidden;
	width: 50px;
	height: 50px;
	float: left;
}

.head img {
	width: 50px;
	height: 50px;
	margin-right: -100px;
}

.cntwrap {
	margin-left: 60px;
}

.cnt {
	line-height: 20px;
}

.f-brk {
	word-wrap: break-word;
	word-break: break-all;
	white-space: normal;
}

.s-fc7,a.s-fc7:hover {
	color: #0c73c2;
}

div.rp {
	margin-top: 15px;
	text-align: right;
}

.time {
	float: left;
	margin: 0 !important;
}

.s-fc4,a.s-fc4:hover {
	color: #999;
}

.pageCss {
	padding-top: 20px;
	padding-bottom: 20px;
	overflow: hidden
}

.pageCss li {
	float: left;
	text-decoration: none;
	padding: 4px 8px;
	list-style-type: none;
	margin-right: 6px;
	cursor: pointer;
	background-color: #eaeaea;
}

.cur {
	background-color: #23A70F !important;
	color: #fff
}

.pageWrapper {
	float: left;
	margin-left: 34px;
}

.title {
	background-color: #23A70F;
	color: #fff;
	padding-left: 16px;
	line-height: 48px;
	font-size: 16px;
	font-weight: bold;
}
</style>
</head>
<body>

	<div class="container">
		<div class="row" style="margin: 20px auto;width: 80%;">
			<div class="col-md-4 search-control-container">
				<div class="Select search-select Select--single">
					<div class="Select-control">
						<input placeholder="名称" class="col-md-10" name="name" type="text" />
					</div>
				</div>
			</div>
			<div class="col-md-2 search-control-container">
				<button class="btn btn-block btn-cfl-blue search-btn">Add</button>
			</div>
		</div>
		<div class="pageWrapper" width='1000' title='你的评论' fields='url,name,comment'
			renderLine="maxLine"
			url="searchCommentsByUser?fmid=${fmid}&&categoryid=${categoryid}"
			id="wrapper1"></div>
	</div>
	<script type="text/javascript">
		function maxLine(attr) {
			var line = '<div class="itm"><div class="head"><a href="javascript:void(0)"><img src="'+attr[0]+'"></a></div><div class="cntwrap"><div class=""><div class="cnt f-brk"><a href="javascript:void(0)" class="s-fc7">'
					+ attr[1] + '</a>：' + attr[2] + '</div></div></div></div>';
			return line;
		}
		$(".search-btn").click(
				function() {
					var data = {};
					data["name"] = $("input[name='name']").val();
					var url = $(".pageWrapper").attr("url");
					var width = $(".pageWrapper").attr("width");
					var title = $(".pageWrapper").attr("title");
					var renderLineFnc = $(".pageWrapper").attr("renderLine");
					var childs = $(".pageWrapper").attr("fields").split(",");
					var showFields = [];
					for (var j = 0; j < childs.length; j++) {
						showFields.push(childs[j]);
					}
					$.ajax({
						type : 'get',
						url : url,
						data : data,
						success : function(d) {
							var json = eval("(" + d + ")");
							var list = json.data;
							var dataHtml = "<div class='row'>";
							for (var n = 0; n < list.length; n++) {
								var itemLine = list[n];
								var temp = [];
								for (var s = 0; s < showFields.length; s++) {
									temp.push(itemLine[showFields[s]]);
								}
								var itemHtml = window[renderLineFnc](temp);
								dataHtml += itemHtml;
							}
							dataHtml += "</div>";
							var section = "";
							if (title != undefined && title != "") {
								section = "<div class='row'><p class='title'>"
										+ title + "</p></div>";
							}
							section += dataHtml;
							$(".pageWrapper").html(section);
							if (width == undefined || width == "") {
								$(".pageWrapper").css("width", "100%");
							} else {
								$(".pageWrapper").css("width", width + "px");
							}
						}

					});
				});
	</script>
</body>
</html>