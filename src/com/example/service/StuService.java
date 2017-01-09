package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.StuDao;
import com.example.model.Page;
import com.example.model.Stu;

@Service
public class StuService {
	private StuDao stuDao;

	public void setStuDao(StuDao stuDao) {
		this.stuDao = stuDao;
		System.out.println("dao is seted");
	}

	public int insert(Stu name) {
		return stuDao.insert(name);
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void insertAll() {
		stuDao.insert(new Stu("exception",2));
		stuDao.insert(new Stu("exception1",2));
		stuDao.insert(new Stu("exception",2));
		stuDao.insert(new Stu("exception1",2));
		//throw new RuntimeException();
	}

	public Page<Stu> find(Page<Stu> page) {
		List<Stu> list=stuDao.find(page);
		page.getParams().put("result",list );
		return page;
	}

}
