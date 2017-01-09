package com.example.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.service.CommentService;
import com.example.util.BaseController;
import com.example.util.BaseController2;
import com.example.util.BaseService;
import com.example.util.BaseService2;
import com.example.util.ReverObj2Json;

@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController2<Comment> {
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("insert")
	@ResponseBody
	public String isnert(Comment m){
		String r="-1";
		int a=save(m);
		m.setCreateTime(new Date());
		if(a==1){
			try {
				r=ReverObj2Json.rever(m);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	@Override
	public BaseService2<Comment> initService() {
		return commentService;
	}

	
	
	

}
