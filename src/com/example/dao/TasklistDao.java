package com.example.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.model.Tasklist;
import com.example.util.BaseDao;
@Repository
public class TasklistDao extends BaseDao<Tasklist> {

	@Override
	public String getNameSpace() {
		return "Tasklist";
	}
	
	public List<String> allfm(){
		return getSession().selectList(getNameSpace()+".allfm");
	}
}
