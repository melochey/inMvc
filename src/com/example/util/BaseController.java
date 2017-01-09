package com.example.util;

import java.util.List;

import com.example.model.Page;

public abstract class BaseController<E> {
	public abstract BaseService<E> initService();

	public int save(E e) {
		return initService().insert(e);
	}
	
	public List<E> find(Page<E> p){
		return initService().find(p);
	}
}
