package com.example.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.model.Page;

public abstract class BaseDao<E> extends SqlSessionDaoSupport {
	
	private SqlSession s;
	
	public abstract String getNameSpace();

	public void initDao() {
		s = this.getSqlSession();
		System.out.println("sqlSession:" + s);
	}
	
	public SqlSession getSession(){
		return s;
	}
	
	public int insert(E e){
		
		return s.insert(getNameSpace()+".ist", e);
	}
	
	public void update(E e){
		s.update(getNameSpace()+".updateByPrimaryKeySelective", e);
	}
	
	public void delete(E e){
		s.delete(getNameSpace()+".del",e);
	}
	
	public List<E> find(Page<E> e){
		List<E> l = s.selectList(getNameSpace()+".find", e);
		return l;
	}
	
	public List<E> selectById(Object e){
		List<E> l = s.selectList(getNameSpace()+".selectById", e);
		return l;
	}
}
