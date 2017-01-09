package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.AppUserDao;
import com.example.model.AppUser;
import com.example.util.BaseDao;
import com.example.util.BaseService;
@Service
public class AppUserService extends BaseService<AppUser> {
	@Autowired
	private AppUserDao appUserDao;

	@Override
	public BaseDao initDao() {
		return appUserDao;
	}

}
