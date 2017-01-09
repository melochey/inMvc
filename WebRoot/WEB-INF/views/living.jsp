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
<title>add work</title>
<link href="<%=basePath%>/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/bootstrap.min.js"></script>
<style type="text/css">
	body{background-color:rgba(255, 193, 7, 0.18)}
	@font-face{
		font-family:"wbficonregular";
		src:url('<%=basePath%>fonts/wbficon.woff');
	}
	input{vertical-align:middle;height: 34px;}
	.pageCss{padding-top:20px;padding-bottom:20px;overflow:hidden}
	.pageCss li{float:left;text-decoration: none;padding:4px 8px;list-style-type: none;margin-right:6px;cursor: pointer;background-color: #eaeaea;}
	.cur{background-color:#23A70F !important;color:#fff}
	.itm {
	padding: 15px 0;
	position:relative;
	}
.timetag{left: -12px;
    position: absolute;
    /* top: 21px; */
    font-size: 16px;
    font-weight: bold;
    line-height: 40px;
    color: #F44336;}
    .W_ficon {
    font-family: "wbficonregular" !important;
    display: inline-block;
    -webkit-font-smoothing: antialiased;
}
.S_ficon{color:#23A70F}
	.head img {
	    float: left;
	    width: 50px;
	    height: 50px;
	    margin-right: -100px;
	}
	.cntwrap {
	    margin-left: 20px;
	}
	.cnt {
	    line-height: 40px;
	    margin-left:120px;
	}
	.f-brk {
	    word-wrap: break-word;
	    word-break: break-all;
	    white-space: normal;
	}
	.s-fc7 {
	    color: #0c73c2;
	    font-size:16px !important;
	    font-weight:bold !important;
	}
	.s-fc7:hover{
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
	.f-brk span{margin-left:20px;padding:4px 8px;}
	.living{color:#fff;background-color:#23A70F;}
	.pointer{cursor:pointer}
	.tasklist{margin-top:20px;border-top:1px solid #23A70F;padding-top:15px;border-left: 1px solid #23A70F;}
</style>
</head>
<body>
	<div class="container">
		<div class="row ">
			<div class="col-lg-11">
				<div class="row" style="margin-top:20px">
					<div class="col-md-4 search-control-container">
						<div class="Select search-select Select--single">
							<div class="Select-control">
								<input placeholder="链接" class="col-md-10" name="url" type="text" />
							</div>
						</div>
					</div>
					<div class="col-md-3 search-control-container">
						<div class="Select search-select Select--single">
							<div class="Select-control">
								<input placeholder="id" class="col-md-10" name="name" type="text" />
							</div>
						</div>
					</div>
					<div class="col-md-3 search-control-container">
						<div class="Select search-select Select--single is-searchable">
							<div class="Select-control">
								<input placeholder="频率"  class="col-md-10" name="bite" type="text" />
							</div>
						</div>
					</div>
					<div class="col-md-2 search-control-container">
						<button class="btn btn-block btn-cfl-blue search-btn">Add</button>
					</div>
				</div>
			</div>
		</div>
		<div class='row tasklist' >
			<c:forEach items="${list}" var="item">
				<div class="itm" data-id="${item.id}">
							<span class="timetag"><em node-type="left_item" class="W_ficon S_ficon">D</em>&nbsp;&nbsp;${item.categoryid}</span>
							<div class="cnt f-brk">
								<a target="_blank" href="../wordslist/info?fmid=${item.fmid}&categoryid=${item.categoryid}" class="s-fc7">${item.fmid}</a>
								：<span class="living"><c:if test="${item.finished==0}">正在直播</c:if><c:if test="${item.finished==1}">直播结束</c:if></span>
								<span>${item.url}</span>
								<span class="living pointer addTask" fmid="${item.fmid}" cid="${item.categoryid}" url="${item.url}">添加任务</span>
								<span class="living pointer closeTask" fmid="${item.fmid}" cid="${item.categoryid}">关闭任务</span>
								<%-- <span class="living pointer onffLineComments" fmid="${item.fmid}" cid="${item.categoryid}">离线导入</span> --%>
								<span class="living pointer onLineComments" fmid="${item.fmid}" clicked="no" cid="${item.categoryid}" liveId="${item.url}">在线导入</span>
								<span class="living pointer liveComment" liveId="${item.url}" fmid="${item.fmid}" cid="${item.categoryid}" >实时监测</span>
							</div>
				</div>
			</c:forEach>
		</div>
		<div class="row">${page}</div>
	</div>
	<script type="text/javascript">
		$(".addTask").click(function(){
			var data={};
			data["name"]=$(this).attr("fmid");
			data["url"]=$(this).attr("url");
			data["categoryid"]=$(this).attr("cid");
			data["bite"]=1;
			$.ajax({
				type:"post",
				url:"../living/add",
				data:data,
				success:function(d){
					if(d.indexOf("{")==-1){
						alert(d);
						return;
					}
					var r=eval("("+d+")");
					if(r.isnew==1){
						location.reload();
					}else{
						alert("add success!");
					}
					
				}
			});
		});
		$(".closeTask").click(function(){
			var data={};
			data["name"]=$(this).attr("fmid");
			data["cid"]=$(this).attr("cid");
			$.ajax({
				type:"post",
				url:"../living/close",
				data:data,
				success:function(d){
					alert(d);
				}
			});
		});
		$(".search-btn").click(function(){
			var data={};
			data["name"]=$("input[name='name']").val();
			data["url"]=$("input[name='url']").val();
			data["bite"]=$("input[name='bite']").val();
			$.ajax({
				type:"post",
				url:"../living/add",
				data:data,
				success:function(d){
					if(d.indexOf("{")==-1){
						alert(d);
						return;
					}
					var r=eval("("+d+")");
					if(r.isnew==1){
						location.reload();
					}else{
						alert("add success!");
					}
				}
			});
		});
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
		$(".onLineComments").click(function(){
			if($(this).attr("clicked")=="has"){
				return;
			}
			var data={};
			data["fmid"]=$(this).attr("fmid");
			data["cid"]=$(this).attr("cid");
			data["liveId"]=$(this).attr("liveId");
			$(this).css("background-color","#FF5722");
			$(this).attr("clicked","has");
			var that=this;
			$.ajax({
				type:"post",
				url:"../living/importFromJson",
				data:data,
				success:function(d){
					alert(d);
					$(that).attr("clicked","no");
					$(that).css("background-color","#23A70F");
				}
			});
		})
		$(".onffLineComments").click(function(){
			if($(this).attr("clicked")=="has"){
				return;
			}
			var data={};
			data["fmid"]=$(this).attr("fmid");
			data["cid"]=$(this).attr("cid");
			$(this).attr("background-color","#FF9800");
			$(this).attr("clicked","has");
			$.ajax({
				type:"post",
				url:"../living/import",
				data:data,
				success:function(d){
					alert(d);
					$(this).attr("clicked","no");
					$(this).attr("background-color","#23A70F");
				}
			});
		})
		$(".liveComment").click(function(){
			var liveId=$(this).attr("liveId");
			var fmid=$(this).attr("fmid");
			var cid=$(this).attr("cid");
			var i=liveId.indexOf("=");
			liveId=liveId.substring(i+1);
			window.open("../living/liveComment?liveId="+liveId+"&fmid="+fmid+"&cid="+cid);
		});
	</script>
</body>
</html>