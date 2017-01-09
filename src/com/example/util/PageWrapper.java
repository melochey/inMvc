package com.example.util;

public class PageWrapper {
	//分页插件
	public static String getPageHtml(int pageSize,int curPage,int total,String url){
		String content="<div pageSize='"+pageSize+"' url='"+url+"' class='pageCss'><ul>";
		if(total==0){
			return "";
		}
		if(curPage==1){
			content+="<li id='1' class='cur'>1</li>";
		}else{
			content+="<li "+"id='"+(curPage-1)+"'>上一页</li>";
			content+="<li id='1'>1</li>";
		}
		int s,e;
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
		
		for(int i=s;i<=e;i++){
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
}
