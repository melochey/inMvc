package com.example.util;

import java.util.List;

import com.example.model.Page;

public abstract class BaseService<E> {
	public abstract BaseDao initDao();

	public int insert(E e) {
		return initDao().insert(e);
	}
	
	public List<E> find(Page<E> page){
		return initDao().find(page);
	}
	
	public void delete(E e){
		initDao().delete(e);
	}
}
