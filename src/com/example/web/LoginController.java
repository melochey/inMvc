package com.example.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import com.example.service.CommentService;
import com.example.service.PostService;
import com.example.util.ThreadContext;

@Controller
public class LoginController {

	@RequestMapping(value = "home1")
	public ModelAndView get(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ServletRequestAttributes a = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = a.getRequest();
		Map context = ThreadContext.getContext();
		req.getRequestDispatcher("/home1").forward(req, res);
		return null;
		// return new ModelAndView("redirect:/home1");
	}

	@RequestMapping(value = "home")
	public String get1(HttpServletRequest req) {
		return "index";
	}
	
	@RequestMapping(value="login")
	public String login(HttpServletRequest req,HttpServletResponse res){
		return "login";
	}
	
	@RequestMapping(value="loginin")
	public String loginin(HttpServletRequest req,HttpServletResponse res){
		String creatorId=req.getParameter("creatorId");
		Cookie c=new Cookie("userId", creatorId);
		c.setMaxAge(7*24*60*60);
		res.addCookie(c);
		return "index";
	}
}
