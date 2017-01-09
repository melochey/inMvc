package com.example.rose;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

import com.example.dao.TasklistDao;
import com.example.model.Tasklist;
import com.example.util.servletUtil;
import com.example.web.LivingController;

public class GetLivingWords {
	
	public static void main(String[] args){
		String url="http://appweb.lizhi.fm/live/share?liveId=2565621501267106870";
		final GetLivingWords g=new GetLivingWords(url);
		final ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
		Runnable runnable = new Runnable() {  
            public void run() {  
            	if(g.shouldClose){
            		service.shutdown();
            	}
        		g.saveLiving();
            	System.out.println("catching data one time!");
            }  
        };  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS); 
//        service.shutdown();
	}
	
	public ApplicationContext context;
	private String url;
	
	private int index;//记录文件索引
	
	private WebDriverUtil web;
	
	private String date;
	
	boolean shouldClose=false;
	
	boolean isStarted=false;
	
	private String name;
	

	public GetLivingWords() {

	}

	public GetLivingWords(String url) {
		this.url = url;
	}
	
	public void run(){
		Scheduler scheduler=(Scheduler) context.getBean("scheduler");
		try {
			scheduler.unscheduleJob("cron_" + 0, Scheduler.DEFAULT_GROUP);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("run"+this.name);
	}
	
	public void saveLiving(){
		try {
			if(shouldClose){
				try {  
					JobDetail job = LivingController.scheduler.getJobDetail("job_"+name, Scheduler.DEFAULT_GROUP);
					JobDetail job1 = LivingController.scheduler.getJobDetail("job1_"+name, Scheduler.DEFAULT_GROUP);
					if(job!=null){
						String trikey="cron_" + name;
						System.out.println(trikey);
						LivingController.scheduler.unscheduleJob(trikey, Scheduler.DEFAULT_GROUP); 
					} 
					if(job1!=null){
						String trikey="cron1_" + name;
						System.out.println(trikey);
						LivingController.scheduler.unscheduleJob(trikey, Scheduler.DEFAULT_GROUP); 
					}
		        } catch (SchedulerException e) {  
		            e.printStackTrace();  
		        }  catch (Exception e) {
		        	e.printStackTrace();  
				}
				System.out.println("this job have closed");
				return;
			}
			if(web==null){
				web=new WebDriverUtil();
				web.setDate(date);
				web.setFmid(name);
			}
			JSONArray json = web.getContentByUrl(url);
			String t="";
			if(json!=null){
				t=json.toString();
			}
			//直播结束，关闭抓取服务，保存评论入库
			if(web.shouldClose&&web.isStarted){
				shouldClose=true;
//				Scheduler scheduler =(Scheduler)servletUtil.getContext().getBean("scheduler");
//				try {  
//					String trikey="cron_" + this.name;
//					System.out.println(trikey);
//	                scheduler.unscheduleJob(trikey, Scheduler.DEFAULT_GROUP);  
//	            } catch (SchedulerException e) {  
//	                e.printStackTrace();  
//	            }  catch (Exception e) {
//	            	e.printStackTrace();  
//				}
				System.out.println("save comments into db");
				DealLivingWords d=new DealLivingWords();
				String filePath="E:\\roseWords\\"+date+"\\"+name;
				d.setCategoryId(date);
				d.setFmId(Integer.valueOf(name));
				d.setFilePath(filePath);
				d.saveComments();
				System.out.println("save comments into db end !");
				Tasklist task=new Tasklist();
				task.setFmid(Integer.valueOf(name));
				task.setCategoryid(date);
				task.setFinished(1);
				TasklistDao td=(TasklistDao)servletUtil.getContext().getBean("tasklistDao");
				td.update(task);
				return;
			}else if(web.shouldClose&&!web.isStarted){
				System.out.println("直播未开始");
				return;
			}
			if(t.equals("[]")){
				return;
			}
			String fileName="E:\\roseWords\\";
			if(date==null){
				Date d=new Date();
				SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
				date=s.format(d);
			}
			
			fileName+=date+"\\"+this.name;
			File f=new File(fileName);
			if(!f.exists()){
				f.mkdirs();
			}
			fileName+="\\";
			index=f.listFiles().length;
			fileName+=index+".txt";
			index++;
			File f1=new File(fileName);
			if(!f1.exists()){
				f1.createNewFile();
			}
			FileOutputStream   out=new FileOutputStream(f1);
			out.write(t.getBytes());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
