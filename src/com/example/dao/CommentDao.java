package com.example.dao;

import org.springframework.stereotype.Repository;

import com.example.model.Comment;
import com.example.util.BaseDao2;
@Repository
public class CommentDao  extends BaseDao2<Comment>{

	@Override
	public String getNameSpace() {
		return "Comment";
	}
}
