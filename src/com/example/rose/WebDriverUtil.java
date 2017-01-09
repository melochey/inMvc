package com.example.rose;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.example.model.onlineUser;
import com.example.service.OnlineUserService;
import com.example.util.servletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class WebDriverUtil {
	private static ChromeDriverService service;
	private  WebDriver driver;
	private ChromeOptions options;
	private int i=1;
	private int k=1;
	private boolean init=false;
	private String FM;
	boolean shouldClose=false;
	boolean isStarted=false;
	int count=0;
	boolean isGoingon=false;
	boolean isNeedF5=false;
	private String fmid;
	private String date;
	public String getFmid() {
		return fmid;
	}
	public void setFmid(String fmid) {
		this.fmid = fmid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	private OnlineUserService onlineservice;
	public String getFM() {
		return FM;
	}
	public void setFM(String fM) {
		FM = fM;
	}

	public static void main(String[] args) throws IOException {
		Runnable runnable = new Runnable() {  
            public void run() {  
            	String url="http://appweb.lizhi.fm/live/share?liveId=2565497606495469110";
        		WebDriverUtil t=new WebDriverUtil();
        		System.out.println(t.getContentByUrl(url));
            }  
        };  
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MINUTES);  
	}
	
	public  JSONArray getContentByUrl(String url){
		JSONArray all=new JSONArray();
		if(service==null){
			try {
				service = new ChromeDriverService.Builder()
						.usingDriverExecutable(new File("E:\\selenium-2.53.1\\chromedriver.exe")).usingAnyFreePort()
						.build();
				service.start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(driver==null){
			options=new ChromeOptions();
			options.addArguments("--user-agent=iphone");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	        capabilities.setCapability("chromeOptions", options);
			driver = new RemoteWebDriver(service.getUrl(),capabilities);
		}
		
		if(!isStarted&&init){
			driver.get(url);
		}
		
		if(isNeedF5){
			driver.get(url);
			isNeedF5=false;
			i=1;
		}
		if(!init){
			driver.get(url);
			init=true;
		}
		//内容超过50时候刷新页面
		if(i>50){
			driver.get(url);
			i=1;
		}
		
		String title=driver.getTitle();
		System.out.println(title);
		String temp1;
		try {
			temp1=driver.findElement(By.className("live-title")).getText();
		} catch (Exception e) {
			temp1=null;
		}
		if(temp1!=null){
			shouldClose=true;
			return null;
		}else{
			isStarted=true;
			shouldClose=false;
		}
		
		//记录人数
		if(isStarted&&!shouldClose){
			try {
				String online=driver.findElement(By.id("online")).getAttribute("innerHTML");
				String total=driver.findElement(By.id("total")).getAttribute("innerHTML");
				onlineUser line=new onlineUser();
				line.setCategory(date);
				line.setFmId(Integer.valueOf(fmid));
				line.setCreateTime(new Date());
				line.setOnline(Integer.valueOf(online));
				line.setOntotal(Integer.valueOf(total));
				if(onlineservice==null){
					onlineservice=(OnlineUserService) servletUtil.getContext().getBean("onlineUserService");
				}
				onlineservice.insert(line);
			} catch (Exception e) {
				
			}
		}
				
		if(FM==null){
			try {
				FM=driver.findElement(By.className("navbar-user")).getText();
			} catch (Exception e) {
				e.printStackTrace();
				FM="default";
			}
		}
		List<WebElement> dom;
		try {
			dom= driver.findElements(By.xpath("//div[@id='liveComment']/div"));
		} catch (Exception e) {
			e.printStackTrace();
			dom=new ArrayList<WebElement>();
		}
		System.out.println("size:"+dom.size());
		int temp=i;
		if(temp>dom.size()){
			if(temp-dom.size()>5){
				i=1;
				temp=1;
			}else{
				temp=dom.size();
				i=dom.size();
			}
		}
		if(dom.size()>=50){
			isNeedF5=true;
		}
		for(int e=temp;e<=dom.size();e++){
			System.out.println("one for time:"+e+";k:"+k);
			if(k>=11||k==0){
				k=1;
			}
			for(;k<11;k++){
				String p="//div[@id='liveComment']/div["+i+"]/div["+k+"]";
				WebElement b=null ;
				try {
					b= driver.findElement(By.xpath(p));
				} catch (Exception e2) {
					b=null;
				}
				if(b==null){
					if(isGoingon==false){
						isGoingon=true;
						count=1;
					}else{
						count++;
					}
					if(count>5){//5分钟还未有数据则刷新页面
						isNeedF5=true;
					}
					System.out.println("break");
					break;
				}else{
					isGoingon=false;
					count=0;
				}
				WebElement img = b.findElement(By.tagName("img"));
				String img_url=img.getAttribute("src");
				String name=img.getAttribute("alt");
				String comment=b.getText();
				JSONObject j=new JSONObject();
				j.put("name", name);
				j.put("comment",comment);
				j.put("url", img_url);
				all.add(j);
				System.out.println("name:"+name+",comment:"+comment+",url:"+img_url);
			}
			if(k>=11){
				i++;
			}else{
				break;
			}
		}
		return all;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}