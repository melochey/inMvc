package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PostDao;
import com.example.model.Post;
import com.example.util.BaseDao;
import com.example.util.BaseService;

@Service
public class PostService extends BaseService<Post> {
	@Autowired
	private PostDao postDao;

	@Override
	public BaseDao initDao() {
		return postDao;
	}
	
}
