<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript">
		var root='<%=basePath%>';
	</script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/index.js"></script>
<style type="text/css">
	body,p{margin:0;padding:0;font-size:14px}
	p{line-height:32px;margin:2px;}
	.main{margin:0 auto;width:90%;text-align:center}
	.comment{width:80%;height:60px;}
	.item_comment{width:100%;height:60px;}
	.comment_item{padding-top:12px;padding-bottom:12px;text-align:left;border-bottom: 1px solid #eaeaea}
	.comment_bodys{padding-left:40px;}
	.comment_item1{padding-top:12px;padding-bottom:12px;text-align:left;border-bottom: 1px solid #eaeaea}
	#comment_list{margin:0 auto;width:80%;}
	#comment_list p{background-color:#eaeaea;} 
	.item_reply{
	    color: white;
	    font-size: 12px;
	    letter-spacing: 1px;
	    background: #3385ff;
	    border-bottom: 1px solid #2d78f4;
	    padding:0px 4px;

	    float:right}
	#reply,.submit_item{
		width: 100px;
	    height: 36px;
	    color: white;
	    font-size: 15px;
	    letter-spacing: 1px;
	    background: #3385ff;
	    border-bottom: 1px solid #2d78f4;
	    outline: medium;
	    padding:8px 8px;
	    -webkit-appearance: none;
	    -webkit-border-radius: 0;
	}
	.comment_body p{background-color:#fff !important;}
</style>
</head>
<body>
	<div class="main">
		<p>标题：${item.title }</p>
		<p>正文：${item.content }</p>
		<p>作者：${item.creatorId}</p>
		<div id="comment_body">
			<textarea class="comment" rows="3" cols="3" id="comment"></textarea>
			<p><span id="reply">回复</span></p>
		</div>
		<div id="comment_list">
			${comment}
		</div>
		
	</div>
	<script type="text/javascript">
		var postId=${item.id};
		$(function(){
			$("#reply").click(function(e){
				var comment=$("#comment").val();
				$.ajax({
					type:"post",
					url:root+"/comment/insert",
					data:{"postId":postId,"comment":comment,"fatherId":0,"creatorId":userid,"floorId":0},
					success:function(data){
						if(data!='-1'){
							var json=eval("("+data+")");
							var item='<div class="comment_item">'+userid+':'+json.comment+'<span class="item_reply" id="reply'+json.creatorId+'_'+json.id+'_'+json.id+'">回复</span></div>';
							$("#comment_list").append(item);
						}
					}
				});
				
			});
			$("#comment_list").click(function(e){
				var cur=e.target;
				if(cur.nodeName!="SPAN"){
					return;
				}
				if(cur.id.indexOf("reply")!=-1){
					var prt=$(cur).parent();
					var p=prt.next();
					var array=cur.id.substring(5).split("_");
					var cid=array[1];
					var creatorId=array[0];
					if(creatorId==userid){
						alert("不能回复自己");
						return;
					}
					if(p.length==0){
						var sub_comment='<div class="comment_body" id="comment_body'+cid+'"><textarea class="item_comment" rows="3" cols="3" id="item_comment'+cid+'"></textarea><p><span class="submit_item" id="submit'+creatorId+'_'+cid+'_'+array[2]+'">回复</span></p></div>';
						prt.after(sub_comment);
					}else{
						if(p[0].className!="comment_body"){
							var sub_comment='<div class="comment_body" id="comment_body'+cid+'"><textarea class="item_comment" rows="3" cols="3" id="item_comment'+cid+'"></textarea><p><span class="submit_item" id="submit'+creatorId+'_'+cid+'_'+array[2]+'">回复</span></p></div>';
							p.before(sub_comment);
						}else{
							if(p[0].style.display!="none"){
								p[0].style.display="none";
							}else{
								p[0].style.display="block";
							}
							
						}
							
					}
				}else if(cur.id.indexOf("submit")!=-1){
					var array1=cur.id.substring(6).split("_");
					var fid=array1[1];
					var creatorId=array1[0];
					var cid="#item_comment"+fid;
					var did="#comment_body"+fid;
					$(did).css("display","none");
					var item_comment="回复"+creatorId+":"+$(cid).val();
					$.ajax({
						type:"post",
						url:root+"/comment/insert",
						data:{"postId":postId,"comment":item_comment,"fatherId":fid,"creatorId":userid,"floorId":array1[2]},
						success:function(data){
							if(data!='-1'){
								var json=eval("("+data+")");
								var item='<div class="comment_item">'+userid+':'+json.comment+'<span class="item_reply" id="reply'+userid+'_'+json.id+'_'+array1[2]+'">回复</span></div>';
								$(".comment_bodys").append(item);
							}
						}
					});
				}
			});
		});
	</script>
</body>
</html>