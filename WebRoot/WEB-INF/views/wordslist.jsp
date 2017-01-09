<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
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
	padding: 15px 0;
	border-top: 1px dotted #ccc;
	margin-top:4px;
}
.head img {
    float: left;
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
.s-fc7, a.s-fc7:hover {
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
.s-fc4, a.s-fc4:hover {
    color: #999;
}
.pageCss{padding-top:20px;padding-bottom:20px;overflow:hidden}
.pageCss li{float:left;text-decoration: none;padding:4px 8px;list-style-type: none;margin-right:6px;cursor: pointer;background-color: #eaeaea;}
.cur{background-color:#23A70F !important;color:#fff}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div ><a target="_blank" style="margin-right:10px;line-height:48px;float:right" href="yourComment?fmid=${fmid}&&categoryid=${categoryid}">搜索个人评论</a><a target="_blank" style="line-height:48px;margin-right:10px;float:right" href="statistics?fmid=${fmid}&&categoryid=${categoryid}">本次直播数据分析</a></div>
		</div>
		<div class='row'>
			<c:forEach items="${list}" var="item">
				<div class="itm" data-id="${item.id}">
					<div class="head">
						<a href="javascript:void(0)"><img
							src="${item.url}"></a>
					</div>
					<div class="cntwrap">
						<div class="">
							<div class="cnt f-brk">
								<a href="javascript:void(0)" class="s-fc7">${item.name}</a>
								：${item.comment}
							</div>
						</div>
						<div class="rp">
							<div class="time s-fc4"><fmt:formatDate pattern="yyyy-MM-dd" 
           					 value="${item.createTiem}" /></div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="row">${page}</div>
	</div>
	<script type="text/javascript">
		var search=location.search;
		search=search.length>1?search.substring(1):"";
		var map={};
		if(search!=""){
			var a=search.split("&");
			for(var i=0;i<a.length;i++){
				var kv=a[i].split("=");
				if(kv[0]!="pageSize"&&kv[0]!="curPage"){
					map[kv[0]]=kv[1];
				}
			}
		}
		$(".pageCss ul").click(function(e){
			var o=e.target;
			if(o.nodeName=="LI"){
				var id=o.id;
				var url=$(".pageCss").attr("url");
				var pageSize=$(".pageCss").attr("pageSize");
				
				var furl=url+"?curPage="+id+"&pageSize="+pageSize;
				for(var k in map){
					furl+="&"+k+"="+map[k];
				}
				location.href=furl;
			}
			
		})
	</script>
</body>
</html>