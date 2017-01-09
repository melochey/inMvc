package com.example.rose;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.dao.AppUserDao;
import com.example.dao.LivingWordsDao;
import com.example.model.AppUser;
import com.example.model.LivingWords;
import com.example.util.servletUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CatchLivingWords {
	public static void main(String[] a){
		String u="https://appweb.lizhi.fm/live/comments?liveId=2569140067328428086&start=1479643200000&count=50000";
		//GetFromWebApi(u);
		//System.out.println(checkUserSex("2552948884632739884"));
		String liveId="2570002722246937654";
		
		//boolean match=Pattern.matches("\\d*", liveId);
		
		System.out.println(String.valueOf("我们很快就退了😄"));
	}
	
	public static String change(String str){
		StringBuffer s=new StringBuffer();
		for (int i = 0; i < str.length(); i++)  
		{  
		    char a = str.charAt(i);
		    s.append(a);
		}  
		return s.toString();
	}
	
	public static void GetFromWebApi(String liveId,String cid,int  fmId){
		LivingWordsDao livingWordsDao=(LivingWordsDao) servletUtil.getContext().getBean("livingWordsDao");
		AppUserDao appUserDao=(AppUserDao) servletUtil.getContext().getBean("appUserDao");
		String url="";
		try {
			SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
			Date time1=sf.parse(cid);
			long time=time1.getTime();
			url="https://appweb.lizhi.fm/live/comments?liveId="+liveId+"&start="+time+"&count=50000";
			String content=HttpClientUtil.getContentByUrl(url, null);
			JSONObject json = JSONObject.fromObject(content);
			JSONObject data=(JSONObject) json.get("comments");
			JSONArray list=(JSONArray) data.get("list");
			for(int i=0;i<list.size();i++){
				JSONObject item=(JSONObject) list.get(i);
				String userid=(String) item.get("userId");
				String userName=String.valueOf(item.get("userName")) ;
				String portrait=(String) item.get("portrait");
				String comment=String.valueOf(item.get("comment")) ;
				//save every comment
				LivingWords e=new LivingWords() ;
				e.setCategoryId(cid);
				e.setComment(filterEmoji(comment,"#"));
				e.setFmid(fmId);
				e.setUrl(portrait);
				e.setName(filterEmoji(userName,"#"));
				e.setCreateTiem(new Date());
				e.setUserid(Long.valueOf(userid));
				livingWordsDao.insert(e);
				//save user
				if(appUserDao.selectById(userid).size()==0){
					AppUser a=new AppUser();
					a.setImgurl(portrait);
					a.setIsdelete(0);
					a.setUserid(Long.valueOf(userid));
					a.setUsername(filterEmoji(userName,"#"));
					int sex=CatchLivingWords.checkUserSex(userid);
					a.setSex(sex);
					a.setCreateTime(time1);
					appUserDao.insert(a);
				};
				//System.out.println(":"+userid+":"+userName+":"+portrait+":"+comment);
			}
			System.out.println(list.size());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	//判断性别
	public static int  checkUserSex(String uid){
		String root="http://m.lizhi.fm/user/"+uid;
		int sex=0;
		Document doc;
		try {
			doc = (Document) Jsoup.connect(root).get();
			Elements ele = doc.select(".female-icon");
			if(ele==null||ele.size()==0){
				sex=1;
			}else{
				sex=2;
			}
		} catch (IOException e) {
			e.printStackTrace();
			sex=-1;
		}
		return sex;
	}
	
	public static  Map<String,Integer> getLivingNum(String liveId){
		String url="https://appweb.lizhi.fm/live/info?liveId="+liveId+"&uId=h5_KpMRsFyuq7Ey8uWT";
		int onlineCount=0;
		int totalCount=0;
		int liveStatus=0;
		Map<String,Integer> map=new HashMap<String, Integer>();
		try {
			String content=HttpClientUtil.getContentByUrl(url, null);
			JSONObject json = JSONObject.fromObject(content);
			JSONObject line =(JSONObject) json.get("live");
			onlineCount=Integer.valueOf(line.get("onlineCount")+"");
			totalCount=Integer.valueOf(line.get("totalCount")+"");
			liveStatus=Integer.valueOf(line.get("liveStatus")+"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		map.put("online", onlineCount);
		map.put("total", totalCount);
		map.put("liveStatus", liveStatus);
		return map;
	}

/**
     * emoji表情替换
     *
     * @param source 原字符串
     * @param slipStr emoji表情替换成的字符串                
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source,String slipStr) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        }else{
            return source;
        }
    }

}
