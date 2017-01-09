package com.example.web;


import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.model.Stu;
import com.example.service.StuService;
@Controller

public class StuController {
	private StuService stuService;

	public void setStuService(StuService stuService) {
		this.stuService = stuService;
	}

	public StuService getStuService() {
		return stuService;
	}
	
	@RequestMapping("insert")
	public @ResponseBody String insert(HttpServletRequest request){
		stuService.insert(new Stu("a",2));
		return "success";
	}
}
