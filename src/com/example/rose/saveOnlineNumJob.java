package com.example.rose;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class saveOnlineNumJob extends QuartzJobBean  {
	//调度任务
	private GetLivingNum getLivingNum;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		getLivingNum.run();
	}

	public GetLivingNum getGetLivingNum() {
		return getLivingNum;
	}

	public void setGetLivingNum(GetLivingNum getLivingNum) {
		this.getLivingNum = getLivingNum;
	}
	
}
