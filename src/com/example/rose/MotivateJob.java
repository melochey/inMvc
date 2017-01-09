package com.example.rose;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MotivateJob extends QuartzJobBean  {
	private static ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");
	public static void main(String[] a){
		  Scheduler scheduler = (Scheduler)ctx.getBean("scheduler");
		  System.out.println("Scheduling to run tasks.");  
	        for (int i = 0; i < 1; i++) {  
	            try {  
	            	GetLivingWords getLivingWords = new GetLivingWords();
	            	getLivingWords.context=ctx;
	            	getLivingWords.setName("task_"+i);
	                JobDetail jobDetail = new JobDetail();  
	                jobDetail.setName("job_" + i);  
	                jobDetail.getJobDataMap().put("getLivingWords", getLivingWords);  
	                jobDetail.setJobClass(MotivateJob.class);  
	                scheduler.addJob(jobDetail, true);  
	                CronTrigger cronTrigger =new CronTrigger("cron_" + i, Scheduler.DEFAULT_GROUP, jobDetail.getName(), Scheduler.DEFAULT_GROUP);  
	                cronTrigger.setCronExpression("0 0/1 * * * ?");  
	                scheduler.scheduleJob(cronTrigger);  
	            } catch (ParseException e) {  
	                e.printStackTrace();  
	            } catch (SchedulerException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  
//	        try {  
//	            Thread.sleep(60 * 1000);  
//	        } catch (InterruptedException e) {  
//	            e.printStackTrace();  
//	        }  
	  
	        System.out.println("Un-scheduling to run tasks.");  
//	        for (int i = 0; i < 5; i++) {  
//	            try {  
//	                scheduler.unscheduleJob("cron_" + i, Scheduler.DEFAULT_GROUP);  
//	            } catch (SchedulerException e) {  
//	                e.printStackTrace();  
//	            }  
//	        }  
	}
	
	//调度任务
	private GetLivingWords getLivingWords;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		getLivingWords.saveLiving();
		// getLivingWords.run();
	}

	public GetLivingWords getGetLivingWords() {
		return getLivingWords;
	}

	public void setGetLivingWords(GetLivingWords getLivingWords) {
		this.getLivingWords = getLivingWords;
	}
	
}
