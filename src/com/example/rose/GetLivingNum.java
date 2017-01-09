package com.example.rose;

import java.util.Date;
import java.util.Map;

import com.example.model.onlineUser;
import com.example.service.OnlineUserService;
import com.example.util.servletUtil;

public class GetLivingNum {
	private String date;
	private int fmid;
	private String liveid;
	private OnlineUserService onlineservice;
	
	public void run(){
		Map<String,Integer> map=CatchLivingWords.getLivingNum(liveid);
		int liveStatus=map.get("liveStatus");
		if(liveStatus!=1){
			return;
		}
		onlineUser line=new onlineUser();
		line.setCategory(date);
		line.setFmId(Integer.valueOf(fmid));
		line.setCreateTime(new Date());
		line.setOnline(map.get("online"));
		line.setOntotal(map.get("total"));
		if(onlineservice==null){
			onlineservice=(OnlineUserService) servletUtil.getContext().getBean("onlineUserService");
		}
		onlineservice.insert(line);
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getFmid() {
		return fmid;
	}
	public void setFmid(int fmid) {
		this.fmid = fmid;
	}
	public String getLiveid() {
		return liveid;
	}
	public void setLiveid(String liveid) {
		
		this.liveid = liveid;
	}
}
