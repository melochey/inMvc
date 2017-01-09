package com.example.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.Page;
import com.example.model.Tasklist;
import com.example.service.TasklistService;
import com.example.util.PageWrapper;

@RequestMapping("index")
@Controller
public class IndexController {
	
	@Autowired
	private TasklistService tasklistService;
	
	@RequestMapping("rose")
	public String index(){
		return "rose";
	}
	
	@RequestMapping("allfm")
	@ResponseBody
	public String allfm(){
		List<String> list=tasklistService.allfm();
		JSONArray jsonarray = JSONArray.fromObject(list);
		return jsonarray.toString();
	}
	
	
	@RequestMapping("fmitem")
	@ResponseBody
	public ModelAndView fmitem(@Param(value = "fmid") String fmid,HttpServletRequest request){
		Page<Tasklist> page=new Page<Tasklist>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		ModelAndView m=new ModelAndView("living");
		Map map=new HashMap();
		map.put("fmid", fmid);
		page.setParams(map);
		List<Tasklist> list = tasklistService.find(page);
		String pageStr=PageWrapper.getPageHtml(page.getPageSize(), page.getPageNo(), page.getTotalPage(), "fmitem");
		m.addObject("page", pageStr);
		m.addObject("list", list);
		return m;
	}
}
