package com.example.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.model.Comment;
import com.example.model.Page;
import com.example.model.Post;
import com.example.model.Stu;
import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.service.StuService;

public class test {
	public static void main(String[] a){
//		ApplicationContext c=new ClassPathXmlApplicationContext("application.xml");
//		PostService p = (PostService) c.getBean("postService");
//		CommentService common=(CommentService) c.getBean("commentService");
//		Comment c1=new Comment();
//		c1.setComment("ssss");
//		Post e=new Post();
//		e.setTitle("ssss");
//		p.insert(e);
//		common.insert(c1);
//		p.insertAll();
//		Page<Stu> page=new Page<Stu>();
//		Map m=new HashMap();
//		m.put("fid", 2);
//		page.setParams(m);
//		Page<Stu> l = p.find(page);
//		List<Stu> list=(List) l.getParams().get("result");
//		System.out.println(l.getTotalRecord());
//		for (Stu stu :list ) {
//			System.out.println(stu.getName());
//		}
		int[]  nums={1,2,3,5};
		List<Integer> list=new LinkedList<Integer>();
		for(int i=0;i<nums.length;i++){
			list.add(nums[i]);
		}
		for(int i=0;i<list.size()-1;i++){
			int b=list.get(i);
			int c=list.get(i+1);
			if(b<c){
				list.set(i, c);
				list.set(i+1, b);
			}
		}
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
}
