package com.example.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import com.example.util.ThreadContext;
//��ÿһ��request��Ӧ���߳���Թ��ڳ���controller֮��ĵط�����request
public class RequestListener implements ServletRequestListener{

	public void requestDestroyed(ServletRequestEvent sre) {
		ThreadContext.unbindRequest();
	}

	public void requestInitialized(ServletRequestEvent sre) {
		HttpServletRequest r=(HttpServletRequest) sre.getServletRequest();
//		System.out.println(r.getRequestURL());
		ThreadContext.bindRequest(r);
	}

}
