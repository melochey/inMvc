package com.example.util;

import java.util.List;

import com.example.model.Page;

public abstract class BaseService2<E> {
	public abstract BaseDao2 initDao();

	public int insert(E e) {
		return initDao().insert(e);
	}
	
	public List<E> find(Page<E> page){
		return initDao().find(page);
	}
}
