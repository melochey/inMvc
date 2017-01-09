package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.OnlineUserDao;
import com.example.model.onlineUser;
import com.example.util.BaseDao;
import com.example.util.BaseService;

@Service
public class OnlineUserService extends BaseService<onlineUser> {

	@Autowired
	private OnlineUserDao onlineUserDao;

	@Override
	public BaseDao initDao() {
		return onlineUserDao;
	}

}
