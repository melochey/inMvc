package com.example.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.model.Comment;
import com.example.model.Page;
import com.example.model.Post;
import com.example.service.CommentService;

import net.sf.json.JSONObject;


public class ReverObj2Json {
	
	public static void main(String[] a){
		Post p=new Post();
		p.setContent("ss\"s");
		p.setId(23);
		try {
			System.out.println(rever(p));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static String rever(Object o) throws IllegalArgumentException, IllegalAccessException{
		JSONObject json=new JSONObject();
		Field[] fields = o.getClass().getDeclaredFields();
		for(Field f:fields){
			f.setAccessible(true);
			String key=f.getName();
//			Class type = f.getType();
//			System.out.println(type.getName());
			Object value = f.get(o);
			json.put(key, value);
		}
		return json.toString();
	}
	
	public static String reverTree(List<Comment> comments){
		Comment list=new Comment();
		Comment root=new Comment();
		root.setId(0);
		root.setFatherId(-1);
		comments.add(root);
		for(Comment c:comments){
			Comment f=getComment(comments, c.getFatherId());
			if(f==null){
				continue;
			}
			f.getChildren().add(c);
		}
		RecursionTree(root,list,0);
		return list.getComment();
	}
	
	
	public static String reverComment(List<Comment> comments){
		CommentService commentService=(CommentService) servletUtil.getContext().getBean("commentService");
		String c_content="";
		for(Comment c:comments){
			String item = "";
			item+="<div class=\"comment_item\">"+c.getCreatorId()+":";
			item+=c.getComment()+"<span class=\"item_reply\" id=\"reply"+c.getCreatorId()+"_"+c.getId()+"_"+c.getId()+"\">»Ø¸´</span>";
			item+="<div class=\"comment_bodys\">";
			int floorId=c.getId();
			Page<Comment> p1=new Page<Comment>();
			p1.setPageSize(10);
			Map m=new HashMap();
			m.put("floorId", c.getId());
			p1.setParams(m);
			List<Comment> children=commentService.find(p1);
			for(Comment t:children){
				item+="<div class=\"comment_item\">"+t.getCreatorId()+":"+t.getComment()+"<span class=\"item_reply\" id=\"reply"+t.getCreatorId()+"_"+t.getId()+"_"+t.getFloorId()+"\">»Ø¸´</span></div>";
			}
			item+="</div></div>";
			c_content+=item;
		}
		return c_content;
	}
	
	public static Comment getComment(List<Comment> comments,int fid){
		for(Comment c:comments){
			if(c.getId()==fid){
				return c;
			}
		}
		return null;
	}
	
	public static void RecursionTree(Comment root,Comment list,int order){
		if(root.getId()!=0){
			String c_content="";
			String css=order>1?"comment_item1":"comment_item";
			c_content+="<div class=\""+css+"\">"+root.getCreatorId()+":";
			c_content+=root.getComment()+"<span class=\"item_reply\" id=\""+root.getCreatorId()+"item_reply"+root.getId()+"\">»Ø¸´</span>";
			if(order==1){
				c_content+="<div class=\"comment_bodys\">";
			}
			list.setComment(list.getComment()+c_content);
		}
		if(root.getChildren()!=null&&root.getChildren().size()>0){
			order++;
			for(Comment c:root.getChildren()){
				RecursionTree(c,list,order);
			}
		}
		if(root.getId()!=0){
			list.setComment(list.getComment()+"</div>");
			if(order==1){
				list.setComment(list.getComment()+"</div>");
			}
		}
	}
}
