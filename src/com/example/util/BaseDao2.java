package com.example.util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

import com.example.model.Page;

public abstract class BaseDao2<E> extends DaoSupport {
	private SqlSessionFactory sqlSessionFactory;
	private SqlSession s;

	public int insert(E e) {

		return s.insert(getNameSpace() + ".ist", e);
	}
	
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		Assert.notNull(sqlSessionFactory, "sqlSessionFactory is not null spectid");
	}

	public abstract String getNameSpace();

	public void update(E e) {
		s.update(getNameSpace() + ".upt", e);
	}

	public void delete(int id) {
		s.delete(getNameSpace() + ".del");
	}

	public List<E> find(Page<E> e) {
		List<E> l = s.selectList(getNameSpace() + ".find", e);
		return l;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.s=sqlSessionFactory.openSession();
	}

}
