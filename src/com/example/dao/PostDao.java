package com.example.dao;

import org.springframework.stereotype.Repository;

import com.example.model.Post;
import com.example.util.BaseDao;
@Repository
public class PostDao extends BaseDao<Post>{

	@Override
	public String getNameSpace() {
		return "Post";
	}

}
