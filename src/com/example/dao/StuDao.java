package com.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.example.model.Stu;
import com.example.util.BaseDao;

@Repository
public class StuDao extends BaseDao<Stu> {

	public StuDao() {

	}

	@Override
	public String getNameSpace() {
		return "Stu";
	}

}
