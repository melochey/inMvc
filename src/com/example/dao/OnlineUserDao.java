package com.example.dao;

import org.springframework.stereotype.Repository;

import com.example.model.onlineUser;
import com.example.util.BaseDao;
@Repository
public class OnlineUserDao  extends BaseDao<onlineUser> {

	@Override
	public String getNameSpace() {
		return "onlineUser";
	}
   
}