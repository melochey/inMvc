package com.example.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.LivingWords;
import com.example.model.Page;
import com.example.model.Tasklist;
import com.example.model.onlineUser;
import com.example.service.LivingWordsService;
import com.example.service.OnlineUserService;
import com.example.service.TasklistService;
import com.example.util.BaseController;
import com.example.util.BaseService;
import com.example.util.DateJsonUtil;
import com.example.util.PageWrapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@RequestMapping("wordslist")
public class LivingWordsController extends BaseController<LivingWords> {
	@Autowired
	private LivingWordsService livingWordsService;
	@Autowired
	private TasklistService tasklistService;
	@Autowired
	private OnlineUserService onlineUserService;
	

	@Override
	public BaseService<LivingWords> initService() {
		return livingWordsService;
	}
	
	@RequestMapping("online")
	@ResponseBody
	public String online(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<onlineUser> page=new Page<onlineUser>();
		page.setPageSize(1000);
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<onlineUser> list = onlineUserService.find(page);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonUtil("yyyy-MM-dd HH:mm:ss"));
		JSONArray jsonarray = JSONArray.fromObject(list,jsonConfig);
		return jsonarray.toString();
	}
	
	@RequestMapping("info")
	public ModelAndView wordslist(HttpServletRequest request){
		ModelAndView m=new ModelAndView("wordslist");
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.find(page);
		String pageStr=PageWrapper.getPageHtml(page.getPageSize(), page.getPageNo(), page.getTotalPage(), "info");
		m.addObject("list", list);
		m.addObject("page", pageStr);
		m.addObject("fmid", fmid);
		m.addObject("categoryid", categoryId);
		return m;
	}
	
	@RequestMapping("statistics")
	public ModelAndView statistics(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		int num= livingWordsService.countUser(page);
		Page<Tasklist> p1=new Page<Tasklist>();
		map.remove("categoryid");
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sf.parse(categoryId);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("createtime", date);
		p1.setParams(map);
		p1.setPageSize(1);
		List<Tasklist> tl=tasklistService.find(p1);
		int ln=0;
		if(tl.size()==1){
			String cid=tl.get(0).getCategoryid();
			Page<LivingWords> p2=new Page<LivingWords>();
			map.remove("createtime");
			map.put("categoryid", cid);
			p2.setParams(map);
			ln= livingWordsService.countUser(p2);
		}
		
		ModelAndView m=new ModelAndView("statistics");
		m.addObject("fmid", fmid);
		m.addObject("categoryid", categoryId);
		m.addObject("num", num);
		m.addObject("ln",(num-ln));
		return m;
	}
	
	@RequestMapping("sexlist")
	@ResponseBody
	public String sexlist(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Map mparams=new HashMap();
		mparams.put("fmid", fmid);
		mparams.put("categoryid", categoryId);
		List<Map> kvalue=livingWordsService.countSex(mparams);
		JSONArray array=new JSONArray();
		for(Map m:kvalue){
			int num=Integer.valueOf(m.get("num")+"");
			int sex=Integer.valueOf(m.get("sex")+"");
			String k=sex==1?"male":sex==2?"female":"unknow";
			JSONObject j=new JSONObject();
			j.put("type", k);
			j.put("num", num);
			array.add(j);
		}
		return array.toString();
	}
	
	@RequestMapping("totalsex")
	@ResponseBody
	public String totalsex(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Map mparams=new HashMap();
		mparams.put("fmid", fmid);
		mparams.put("categoryid", categoryId);
		List<Map> kvalue=livingWordsService.totalSex(mparams);
		JSONArray array=new JSONArray();
		for(Map m:kvalue){
			int num=Integer.valueOf(m.get("num")+"");
			int sex=Integer.valueOf(m.get("sex")+"");
			String k=sex==1?"male":sex==2?"female":"unknow";
			JSONObject j=new JSONObject();
			j.put("type", k);
			j.put("num", num);
			array.add(j);
		}
		return array.toString();
	}
	
	@RequestMapping("yourComment")
	public ModelAndView yourComment(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		ModelAndView m=new ModelAndView("yourComment");
		m.addObject("fmid", fmid);
		m.addObject("categoryid", categoryId);
		return m;
	}
	
	@RequestMapping("maxLenComment")
	@ResponseBody
	public String maxLenComment(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.maxLenComment(page);
		JSONArray jsonarray = JSONArray.fromObject(list); 
		JSONObject data =new JSONObject();
		data.put("size", page.getPageSize());
		data.put("curPage", page.getPageNo());
		data.put("total", page.getTotalPage());
		data.put("data", jsonarray);
		return data.toString();
	}
	
	@RequestMapping("firstCome")
	@ResponseBody
	public String firstCome(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.firstCome(page);
		JSONArray jsonarray = JSONArray.fromObject(list); 
		JSONObject data =new JSONObject();
		data.put("size", page.getPageSize());
		data.put("curPage", page.getPageNo());
		data.put("total", page.getTotalPage());
		data.put("data", jsonarray);
		return data.toString();
	}
	
	@RequestMapping("searchCommentsByUser")
	@ResponseBody
	public String searchCommentsByUser(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		String name=request.getParameter("name");
		try {
			name =new String(name.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Page<LivingWords> page=new Page<LivingWords>();
		page.setPageSize(10000);
		Map map=new HashMap();
		if(StringUtils.isNotEmpty(fmid)){
			map.put("fmid", fmid);
		}
		if(StringUtils.isNotEmpty(categoryId)){
			map.put("categoryid", categoryId);
		}
		map.put("name", name);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.searchCommentsByUser(page);
		JSONArray jsonarray = JSONArray.fromObject(list); 
		JSONObject data =new JSONObject();
		data.put("size", page.getPageSize());
		data.put("curPage", page.getPageNo());
		data.put("total", page.getTotalPage());
		data.put("data", jsonarray);
		return data.toString();
	}
	
	@RequestMapping("mostActiviteUsers")
	@ResponseBody
	public String mostActiviteUsers(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.mostActiviteUsers(page);
		JSONArray jsonarray = JSONArray.fromObject(list); 
		JSONObject data =new JSONObject();
		data.put("size", page.getPageSize());
		data.put("curPage", page.getPageNo());
		data.put("total", page.getTotalPage());
		data.put("data", jsonarray);
		return data.toString();
	}
	
	@RequestMapping("payMoneyComment")
	@ResponseBody
	public String payMoneyComment(HttpServletRequest request){
		String fmid=request.getParameter("fmid");
		String categoryId=request.getParameter("categoryid");
		Page<LivingWords> page=new Page<LivingWords>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		Map map=new HashMap();
		map.put("fmid", fmid);
		map.put("categoryid", categoryId);
		page.setParams(map);
		List<LivingWords> list=livingWordsService.payMoneyComment(page);
		JSONArray jsonarray = JSONArray.fromObject(list); 
		JSONObject data =new JSONObject();
		data.put("size", page.getPageSize());
		data.put("curPage", page.getPageNo());
		data.put("total", page.getTotalPage());
		data.put("data", jsonarray);
		return data.toString();
	}
}
