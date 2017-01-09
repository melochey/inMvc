package com.example.dao;

import org.springframework.stereotype.Repository;

import com.example.model.AppUser;
import com.example.util.BaseDao;
@Repository
public class AppUserDao extends BaseDao<AppUser> {

	@Override
	public String getNameSpace() {
		return "AppUser";
	}
   
}