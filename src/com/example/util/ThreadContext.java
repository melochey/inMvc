package com.example.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

public class ThreadContext {
	private static final String REQUEST_KEY = ThreadContext.class.getName()+"_request";
	private static ThreadLocal localContext = new NamedInheritableThreadLocal(
			"Locale context");

	private static class NamedInheritableThreadLocal extends
			InheritableThreadLocal {
		protected Map initialValue() {
			return new HashMap();
		}

		public NamedInheritableThreadLocal(String name) {
			Assert.hasText(name, "Name must not be empty");
			this.name = name;
		}

		public String toString() {
			return name;
		}

		private final String name;
	}

	public static Map getContext() {
		return localContext == null ? null : new HashMap((Map) localContext
				.get());

	}

	public void setContext(Map value) {
		if (value == null || value.isEmpty()) {
			return;
		} else {
			localContext.set(value);
		}
	}

	public static void setValue(Object key, Object value) {
		if (key == null)
			throw new IllegalArgumentException("key cannot be null");
		if (value == null) {
			remove(key);
			return;
		} else {
			((Map) localContext.get()).put(key, value);
			//System.out.println(Thread.currentThread()+"#"+key+":"+value);
			return;
		}
	}

	public static Object remove(Object key) {
		Object value = ((Map) localContext.get()).remove(key);
		//System.out.println(key);
		return value;
	}
	
	private static Object getValue(Object key)
    {
        return ((Map)localContext.get()).get(key);
    }
	
	 public static void bindRequest(HttpServletRequest request)
	    {
	        if(request != null)
	        	setValue(REQUEST_KEY, request);
	    }

	public static HttpServletRequest unbindRequest() {
		return (HttpServletRequest)remove(REQUEST_KEY);
	}
}
