package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.TasklistDao;
import com.example.model.Tasklist;
import com.example.util.BaseDao;
import com.example.util.BaseService;

@Service
public class TasklistService extends BaseService<Tasklist> {
	
	@Autowired
	private TasklistDao tasklistDao;

	@Override
	public BaseDao initDao() {
		return tasklistDao;
	}
	
	
	public List<String> allfm(){
		return tasklistDao.allfm();
	}
}
