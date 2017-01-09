package com.example.rose;

import java.util.HashMap;
import java.util.Map;

public class testJdRemind {
	public static void main(String[] a){
		boolean btn=true;//À¢Ã·–—
        if(btn){
            for(int i=0;i<1;i++){
                String httpOrgCreateTest = "http://api.m.jd.com/client.action?functionId=miaoShaClock&clientVersion=5.4.1&build=38399&client=android&d_brand=Meizu&d_model=MX5&osVersion=5.1&screen=1920*1080&partner=meizu&uuid=867992026522862-38bc1a3a79ab&area=12_933_8558_40324&networkType=wifi&st=1478437328181&sign=01d472e8220bf98cefa1fb2861219fae&sv=102";  
                Map<String,String> createMap = new HashMap<String,String>();  
                createMap.put("body","{\"spuId\":\"2168681\",\"state\":1}");  
                Map<String,String> head = new HashMap<String,String>(); 
                String httpOrgCreateTestRtn = HttpClientUtil.doPost1(httpOrgCreateTest,createMap,head,"utf-8");  
                System.out.println("result:"+httpOrgCreateTestRtn); 
            }
        }
	}
}
