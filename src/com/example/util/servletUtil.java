package com.example.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class servletUtil implements ApplicationContextAware{
	
	private static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context=arg0;
	}
	
	public static ApplicationContext getContext(){
		return context;
	}
}
