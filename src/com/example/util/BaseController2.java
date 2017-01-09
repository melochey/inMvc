package com.example.util;

import java.util.List;

import com.example.model.Page;

public abstract class BaseController2<E> {
	public abstract BaseService2<E> initService();

	public int save(E e) {
		return initService().insert(e);
	}
	
	public List<E> find(Page<E> p){
		return initService().find(p);
	}
}
