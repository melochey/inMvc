package com.example.rose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {  
	private static String doGet(String url,Map heades,String charset){
		HttpClient httpClient=null;
		HttpGet get=null;
		String result = null;
		try{
			httpClient=new DefaultHttpClient();
			get=new HttpGet(url);
			 Iterator<Entry<String, String>> ite = heades.entrySet().iterator();
	            while(ite.hasNext()){
	            	Entry<String,String> elem = (Entry<String, String>) ite.next();  
	            	get.setHeader(elem.getKey(),elem.getValue());
	            }
	          
	          HttpResponse response = httpClient.execute(get); 
	            if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,charset);  
	                }  
	            }
		}catch(Exception e){
			
		}
		return result;
	}
    public static String doPostProxy(String url,Map<String,String> map,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            //httpClient = new DefaultHttpClient(); 
            // 创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
                   // HttpClient  
            httpClient = httpClientBuilder.build();  
            // 依次是目标请求地址，端口号,协议类型  
            HttpHost target = new HttpHost(url, 80, "http"); 

            HttpHost proxy = new HttpHost("127.0.0.1", 8888, "http");  
            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();  
            httpPost = new HttpPost(url);  
            httpPost.setConfig(config);
            httpPost.setHeader("Charset","UTF-8");
            //httpPost.setHeader("Connection","close");
            httpPost.setHeader("Accept-Encoding","gzip,deflate");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("User-Agent","Dalvik/2.1.0 (Linux; U; Android 5.1; MX5 Build/LMY47I)");
            httpPost.setHeader("Host", "api.m.jd.com");
            //httpPost.setHeader("Connection","close");
           // httpPost.setHeader( new BasicHeader("Cookie", "whwswswws=8loCjswyWG2T0gtzAzIBEIFcr/kXoCNlf5iCXxNqbCvL1g7vlQ5zxg=="));
           // httpPost.setHeader("jdc-backup","whwswswws=8loCjswyWG2T0gtzAzIBEIFcr/kXoCNlf5iCXxNqbCvL1g7vlQ5zxg==");
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(target,httpPost); 
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        Header[] l = httpPost.getHeaders("Connection");
        for(Header h:l){
        System.out.println(h.getName()+":"+h.getValue());
        }
        return result;  
    }  
    
    public static String doPost1(String url,Map<String,String> map,Map<String,String> head,String charset){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new DefaultHttpClient();  
            httpPost = new HttpPost(url);  
            Iterator<Entry<String, String>> ite = head.entrySet().iterator();
            while(ite.hasNext()){
            	Entry<String,String> elem = (Entry<String, String>) ite.next();  
            	 httpPost.setHeader(elem.getKey(),elem.getValue());
            }
           // httpPost.setHeader( new BasicHeader("Cookie", "whwswswws=8loCjswyWG2T0gtzAzIBEIFcr/kXoCNlf5iCXxNqbCvL1g7vlQ5zxg=="));
           // httpPost.setHeader("jdc-backup","whwswswws=8loCjswyWG2T0gtzAzIBEIFcr/kXoCNlf5iCXxNqbCvL1g7vlQ5zxg==");
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            if(map!=null){
            	   Iterator iterator = map.entrySet().iterator();  
   	            while(iterator.hasNext()){  
   	                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
   	                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
   	            }  
            }
         
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
    public static String getContentByUrl(String url,Map head) throws UnsupportedEncodingException{
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		if(head!=null){
			Iterator<Entry<String, String>> ite = head.entrySet().iterator();
	        while(ite.hasNext()){
	        	Entry<String,String> elem = (Entry<String, String>) ite.next();  
	        	request.setHeader(elem.getKey(),elem.getValue());
	        }
		}
		String result = null;
		try {
			HttpResponse response = client.execute(request);
			 if(response != null){  
	                HttpEntity resEntity = response.getEntity();  
	                if(resEntity != null){  
	                    result = EntityUtils.toString(resEntity,"UTF-8");  
	                }  
	            }  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return result;
	}
    
    public static InputStream getInputStreamByUrl(String url,Map head) throws UnsupportedEncodingException{
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		if(head!=null){
			Iterator<Entry<String, String>> ite = head.entrySet().iterator();
	        while(ite.hasNext()){
	        	Entry<String,String> elem = (Entry<String, String>) ite.next();  
	        	request.setHeader(elem.getKey(),elem.getValue());
	        }
		}
		InputStream sb = null;
		try {
			HttpResponse reponse = client.execute(request);
			sb = reponse.getEntity().getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return sb;
	}
}  
