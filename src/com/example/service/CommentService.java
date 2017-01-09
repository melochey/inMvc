package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CommentDao;
import com.example.model.Comment;
import com.example.util.BaseDao2;
import com.example.util.BaseService2;
@Service
public class CommentService extends BaseService2<Comment>{
	@Autowired
	private CommentDao commentDao;

	@Override
	public BaseDao2 initDao() {
		return commentDao;
	}
	

}
