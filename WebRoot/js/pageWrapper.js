function getPageHtml(pageSize,curPage,total,url,wrapper){
		var content="<div wrapper='"+wrapper+"' pageSize='"+pageSize+"' url='"+url+"' class='pageCss'><ul class='pageUl'>";
		if(total==0){
			return "";
		}
		if(curPage==1){
			content+="<li id='1' class='cur'>1</li>";
		}else{
			content+="<li "+"id='"+(curPage-1)+"'>上一页</li>";
			content+="<li id='1'>1</li>";
		}
		var s,e;
		if(curPage>5){
			content+="<li>...</li>";
			s=curPage-3;
		}else{
			s=2;
		}
		if(curPage<total-5){
			e=curPage+3;
			//content+="<li>...</li>";
		}else{
			e=total-1;
		}
		
		for(var i=s;i<=e;i++){
			if(curPage==i){
				content+="<li id='"+i+"' class='cur'>"+i+"</li>";
			}else{
				content+="<li id='"+i+"'>"+i+"</li>";
			}
		}
		if(curPage<total-5){
			content+="<li>...</li>";
		}
		if(total>1){
			if(curPage==total){
				content+="<li id='"+total+"' class='cur'>"+total+"</li></ul></div>";
			}else{
				content+="<li id='"+total+"'>"+total+"</li>";
				content+="<li "+"id='"+(curPage+1)+"'>下一页</li>";
			}
		}
		content+="</ul></div>";
		return content;
	}

var bodys=$(".pageWrapper");
for(var i=0;i<bodys.length;i++){
	var item=bodys[i];
	var url=$(item).attr("url");
	var id=$(item).attr("id");
	var width=$(item).attr("width");
	var psize=$(item).attr("pageSize");
	psize=psize==undefined?10:psize;
	var title=$(item).attr("title");
	var renderLineFnc=$(item).attr("renderLine");
	var childs=$(item).children("div");
	var showFields=[];
	for(var j=0;j<childs.length;j++){
		showFields.push($(childs[j]).attr("name"));
	}
	(function(a,b,c){
		$.ajax({
			type:'post',
			url:url,
			data:{pageSize:psize},
			async:false,
			success:function(d){
				var json=eval("("+d+")");
				var curNo=json.curPage;
				var size=json.size;
				var total=json.total;
				var list=json.data;
				var pageHtml=getPageHtml(size,curNo,total,url,id);
				pageHtml="<div class='row'>"+pageHtml+"</div>";
				var dataHtml="<div class='row'>";
				for(var n=0;n<list.length;n++){
					var itemLine=list[n];
					var temp=[];
					for(var s=0;s<b.length;s++){
						temp.push(itemLine[b[s]]);
					}
					var itemHtml=window[a](temp);
					dataHtml+=itemHtml;
				}
				dataHtml+="</div>";
				var section="";
				if(title!=undefined&&title!=""){
					section="<div class='row'><p class='title'>"+title+"</p></div>";
				}
				section+=dataHtml+pageHtml;
				$("#"+c).html(section);
				if(width==undefined||width==""){
					$("#"+c).css("width","100%");
				}else{
					$("#"+c).css("width",width+"px");
				}
				$("#"+c).attr("fields",showFields);
				$("#"+c).attr("render",a);
			}
			
		});
	})(renderLineFnc,showFields,id);
	
}
//点击分页跳转执行
$(".pageWrapper").click(function(e){
	var o=e.target;
	var ulcss=$(o).parent().attr("class");
	if(o.nodeName=="LI"&&ulcss=="pageUl"){
		var id=o.id;
		if(id==""){
			return;
		}
		var p_div=$(o).parent().parent();
		var url=$(p_div).attr("url");
		var wrapper=$(p_div).attr("wrapper");
		var pageSize=$(p_div).attr("pageSize");
		var width=$(p_div).parent().parent().attr("width");
		var title=$(p_div).parent().parent().attr("title");
		var renderLineFnc=$(p_div).parent().parent().attr("render");
		var childs=$(p_div).parent().parent().attr("fields").split(",");
		var showFields=[];
		for(var j=0;j<childs.length;j++){
			showFields.push(childs[j]);
		}
		var param={};
		param["pageSize"]=pageSize;
		param["curPage"]=id;
		$.ajax({
			type:'get',
			url:url,
			data:param,
			success:function(d){
				var json=eval("("+d+")");
				var curNo=json.curPage;
				var size=json.size;
				var total=json.total;
				var list=json.data;
				var pageHtml=getPageHtml(size,curNo,total,url,wrapper);
				pageHtml="<div class='row'>"+pageHtml+"</div>";
				var dataHtml="<div class='row'>";
				for(var n=0;n<list.length;n++){
					var itemLine=list[n];
					var temp=[];
					for(var s=0;s<showFields.length;s++){
						temp.push(itemLine[showFields[s]]);
					}
					var itemHtml=window[renderLineFnc](temp);
					dataHtml+=itemHtml;
				}
				dataHtml+="</div>";
				var section="";
				if(title!=undefined&&title!=""){
					section="<div class='row'><p class='title'>"+title+"</p></div>";
				}
				section+=dataHtml+pageHtml;
				$("#"+wrapper).html(section);
				if(width==undefined||width==""){
					$("#"+wrapper).css("width","100%");
				}else{
					$("#"+wrapper).css("width",width+"px");
				}
			}
			
		});
	}
	
});