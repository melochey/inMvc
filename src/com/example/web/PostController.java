package com.example.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Comment;
import com.example.model.Page;
import com.example.model.Post;
import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.util.BaseController;
import com.example.util.BaseService;
import com.example.util.ReverObj2Json;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController<Post> {
	@Autowired	
	private PostService postService;
	@Autowired	
	private CommentService commentService;
	@Override
	public BaseService<Post> initService() {
		return postService;
	}
	
	@RequestMapping("/insert")
	public String insertPost(Post p,HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		Cookie[] cookies = req.getCookies();
		for(Cookie c:cookies){
			if(c.getName().equals("userId")){
				p.setCreatorId(Integer.valueOf(c.getValue()));
			}
		}
		int id=save(p);
		System.out.println(id);
		return "redirect:/post/list";
	}
	@RequestMapping("/list")
	public ModelAndView listPost(){
		Page<Post> p=new Page<Post>();
		List<Post> list=find(p);
		ModelAndView mv=new ModelAndView("list_post");
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping("/item")
	public ModelAndView getItem(@RequestParam("id") int id){
		Page<Post> p=new Page<Post>();
		Map m=new HashMap();
		m.put("id", id);
		p.setParams(m);
		Post post=find(p).get(0);
		Page<Comment> p1=new Page<Comment>();
		p1.setPageSize(10);
		m.remove("id");
		m.put("postId", id);
		m.put("fatherId", 0);
		p1.setParams(m);
		List<Comment> comments=commentService.find(p1);
		String allComments=ReverObj2Json.reverComment(comments);
		ModelAndView mv=new ModelAndView("item_post");
		mv.addObject("item", post);
		mv.addObject("comment", allComments);
		return mv;
		
	}
}
