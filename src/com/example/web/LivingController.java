package com.example.web;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.dao.TasklistDao;
import com.example.model.LivingWords;
import com.example.model.Page;
import com.example.model.Tasklist;
import com.example.rose.CatchLivingWords;
import com.example.rose.DealLivingWords;
import com.example.rose.GetLivingNum;
import com.example.rose.GetLivingWords;
import com.example.rose.HttpClientUtil;
import com.example.rose.MotivateJob;
import com.example.rose.saveOnlineNumJob;
import com.example.service.LivingWordsService;
import com.example.service.TasklistService;
import com.example.util.BaseController;
import com.example.util.BaseService;
import com.example.util.PageWrapper;
import com.example.util.servletUtil;

@Controller
@RequestMapping("/living")
public class LivingController extends BaseController<Tasklist> {
	public static Scheduler scheduler;
	@Autowired
	private TasklistService tasklistService;
	@Autowired
	private TasklistDao tasklistDao;
	@Autowired
	private LivingWordsService livingWordsService;
	@RequestMapping("open")
	public ModelAndView init(HttpServletRequest request){
		ModelAndView m=new ModelAndView("living");
		Page<Tasklist> page=new Page<Tasklist>();
		if(request.getParameter("curPage")!=null){
			page.setPageNo(Integer.valueOf(request.getParameter("curPage")));
		}
		if(request.getParameter("pageSize")!=null){
			page.setPageSize(Integer.valueOf(request.getParameter("pageSize")));
		}
		List<Tasklist> list = tasklistService.find(page);
		m.addObject("list", list);
		String pageStr=PageWrapper.getPageHtml(page.getPageSize(), page.getPageNo(), page.getTotalPage(), "open");
		m.addObject("page", pageStr);
		return m;
	}
	
	@RequestMapping("import")
	@ResponseBody
	public String importData(HttpServletRequest request){
		String name=request.getParameter("fmid");//标识Id
		String cid=request.getParameter("cid");
		if(name==null){
			return "need name!";
		}
		if(cid==null){
			return "need cid!";
		}
		LivingWords e=new LivingWords();
		e.setFmid(Integer.valueOf(name));
		e.setCategoryId(cid);
		livingWordsService.delete(e);
		DealLivingWords d=new DealLivingWords();
		d.setFilePath("E:\\roseWords\\"+cid+"\\"+name);
		d.setFmId(Integer.valueOf(name));
		d.setCategoryId(cid);
		d.saveComments();
		return "import success";
	}
	
	@RequestMapping("importFromJson")
	@ResponseBody
	public String importDataFromJson(HttpServletRequest request){
		String name=request.getParameter("fmid");//标识Id
		String cid=request.getParameter("cid");
		String liveId=request.getParameter("liveId");
		if(liveId==null){
			return "need liveId!";
		}
		if(name==null){
			return "need name!";
		}
		if(cid==null){
			return "need cid!";
		}
		int t=liveId.indexOf("=");
		liveId=liveId.substring(t+1);
		boolean match=Pattern.matches("\\d*", liveId);
		if(!match){
			return "valid liveId!";
		}
		Tasklist task=new Tasklist();
		task.setFmid(Integer.valueOf(name));
		task.setCategoryid(cid);
		task.setFinished(1);
		tasklistDao.update(task);
		//清空
		LivingWords e=new LivingWords();
		e.setFmid(Integer.valueOf(name));
		e.setCategoryId(cid);
		livingWordsService.delete(e);
		//导入
		CatchLivingWords.GetFromWebApi(liveId, cid,Integer.valueOf(name));
		return "import success";
	}

	@RequestMapping("close")
	@ResponseBody
	public String closeShedule(HttpServletRequest request){
		String name=request.getParameter("name");//标识Id
		String cid=request.getParameter("cid");
		if(name==null){
			return "need name!";
		}
		try {  
//			JobDetail job = scheduler.getJobDetail("job_"+name, Scheduler.DEFAULT_GROUP);
//			if(job!=null){
//				String trikey="cron_" + name;
//				System.out.println(trikey);
//	            scheduler.unscheduleJob(trikey, Scheduler.DEFAULT_GROUP); 
//			} 
			JobDetail job1 = scheduler.getJobDetail("job2_"+name, Scheduler.DEFAULT_GROUP);
			if(job1!=null){
				String trikey="cron2_" + name;
				System.out.println(trikey);
	            scheduler.unscheduleJob(trikey, Scheduler.DEFAULT_GROUP); 
			} 
        } catch (SchedulerException e) {  
            e.printStackTrace();  
        }  catch (Exception e) {
        	e.printStackTrace();  
		}
		Tasklist task=new Tasklist();
		task.setFmid(Integer.valueOf(name));
		task.setCategoryid(cid);
		task.setFinished(1);
		tasklistDao.update(task);
		return "close successful!";
	}
	
	@RequestMapping("liveComment")
	public ModelAndView liveComment(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView m=new ModelAndView("liveComment");
		String liveId=request.getParameter("liveId");
		String fmid=request.getParameter("fmid");
		String cid=request.getParameter("cid");
		m.addObject("liveId", liveId);
		m.addObject("fmid", fmid);
		m.addObject("cid", cid);
		m.addObject("uId", "h5_KpMRsFyuq7Ey8uWT");
		return m;
	}
	
	@RequestMapping("info")
	@ResponseBody
	public String info(HttpServletRequest request,HttpServletResponse response) {
		String liveId=request.getParameter("liveId");
		String uId=request.getParameter("uId");
		String url="https://appweb.lizhi.fm/live/info?liveId="+liveId+"&uId="+uId;
		String content="";
		try {
			content=HttpClientUtil.getContentByUrl(url, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	@RequestMapping("comment")
	@ResponseBody
	public String comment(HttpServletRequest request,HttpServletResponse response) {
		String liveId=request.getParameter("liveId");
		String start=request.getParameter("start");
		String count=request.getParameter("count");
		String url="https://appweb.lizhi.fm/live/comments?liveId="+liveId+"&start="+start+"&count="+count;
		String content="";
		try {
			content=HttpClientUtil.getContentByUrl(url, null);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	@RequestMapping("add")
	@ResponseBody
	public String addJob(HttpServletRequest request,HttpServletResponse response) {
		if(scheduler==null){
			scheduler = (Scheduler)servletUtil.getContext().getBean("scheduler");
		}
		String url=request.getParameter("url");//抓取连接
		String date=request.getParameter("categoryid");
		if(url==null){
			return "need url!";
		}
		String name=request.getParameter("name");//标识Id
		if(name==null){
			return "need name!";
		}
		try {
			JobDetail job = scheduler.getJobDetail("job2_"+name, Scheduler.DEFAULT_GROUP);
			if(job!=null){
				return "this job has existed!";
			}
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		int bite=10;
		//String expression="0 0/10 * * * ?";
//		if(request.getParameter("bite")!=null&&request.getParameter("bite")!=""){
//			bite=Integer.valueOf(request.getParameter("bite"));
//			expression="0 0/"+bite+" * * * ?";//每隔多少分钟执行一次
//		}
		//GetLivingWords getLivingWords = new GetLivingWords(url);
		Date d=new Date();
		if(date==null){
			SimpleDateFormat s=new SimpleDateFormat("yyyyMMdd");
			date=s.format(d);
		}
		//getLivingWords.setName(name);
		//getLivingWords.setDate(date);
//		JobDetail jobDetail = new JobDetail();  
//        jobDetail.setName("job_" + name);  
//        jobDetail.getJobDataMap().put("getLivingWords", getLivingWords);  
//        jobDetail.setJobClass(MotivateJob.class);  
        //添加抓取人数job2
        int t=url.indexOf("=");
        String liveid=url.substring(t+1);
		boolean match=Pattern.matches("\\d*", liveid);
		if(!match){
			return "valid liveId!";
		}
		GetLivingNum getLivingNum=new GetLivingNum();
		getLivingNum.setDate(date);
		getLivingNum.setFmid(Integer.valueOf(name));
		getLivingNum.setLiveid(liveid);
		JobDetail jobDetail2 = new JobDetail();  
        jobDetail2.setName("job2_" + name);  
        jobDetail2.getJobDataMap().put("getLivingNum", getLivingNum);  
        jobDetail2.setJobClass(saveOnlineNumJob.class);  
        try {
        	//scheduler.addJob(jobDetail, true);
			scheduler.addJob(jobDetail2, true);
//			CronTrigger cronTrigger =new CronTrigger("cron_" + name, Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);  
//	        cronTrigger.setCronExpression(expression);  
		    CronTrigger cronTrigger2 =new CronTrigger("cron2_" + name, Scheduler.DEFAULT_GROUP, jobDetail2.getName(), Scheduler.DEFAULT_GROUP);  
		    cronTrigger2.setCronExpression("0/10 * * * * ?");  
//		    scheduler.scheduleJob(cronTrigger);  
		    scheduler.scheduleJob(cronTrigger2); 
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Page<Tasklist> p=new Page<Tasklist>();
        Map m=new HashMap();
        m.put("fmid", name);
        m.put("categoryid", date);
        p.setParams(m);
		//add task
        List<Tasklist> templ = tasklistService.find(p);
        int isfirst=0;
        Tasklist task;
        if(templ.size()==0){
        	isfirst=1;
        	task=new Tasklist();
            task.setFmid(Integer.valueOf(name));
            task.setCategoryid(date);
            task.setBite(bite);
            task.setCreateTime(new Date());
            task.setIsdelete(0);
            task.setFinished(0);
            task.setUrl(url);
            save(task);
        }else{
        	task=templ.get(0);
    		task.setFmid(Integer.valueOf(name));
    		task.setCategoryid(date);
    		task.setFinished(0);
    		tasklistDao.update(task);
        }
        JSONObject r=new JSONObject();
        r.put("isnew", isfirst);
        r.put("task", task);
       return r.toString();
	}
	
	
	@Override
	public BaseService<Tasklist> initService() {
		return tasklistService;
	}
}
