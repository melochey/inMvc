package com.example.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.LivingWordsDao;
import com.example.model.LivingWords;
import com.example.model.Page;
import com.example.util.BaseDao;
import com.example.util.BaseService;
@Service
public class LivingWordsService extends BaseService<LivingWords> {
	@Autowired
	private LivingWordsDao livingWordsDao;

	@Override
	public BaseDao initDao() {
		return livingWordsDao;
	}
	
	public List<LivingWords> maxLenComment(Page<LivingWords> e){
		List<LivingWords> l = livingWordsDao.maxLenComment(e);
		return l;
	}
	
	public List<LivingWords> firstCome(Page<LivingWords> e){
		List<LivingWords> l = livingWordsDao.firstCome(e);
		return l;
	}
	
	public List<LivingWords> searchCommentsByUser(Page<LivingWords> e){
		List<LivingWords> l = livingWordsDao.searchCommentsByUser(e);
		return l;
	}
	
	public int countUser(Page<LivingWords> e){
		return livingWordsDao.countUser(e);
	}
	
	public List<LivingWords> payMoneyComment(Page<LivingWords> e){
		List<LivingWords> l = livingWordsDao.payMoneyComment(e);
		return l;
	}
	
	public List<LivingWords> mostActiviteUsers(Page<LivingWords> e){
		List<LivingWords> l = livingWordsDao.mostActiviteUsers(e);
		return l;
	}
	
	public List<Map> countSex(Map m){
		return livingWordsDao.countSex(m);
	}
	
	public List<Map> totalSex(Map m){
		return livingWordsDao.totalSex(m);
}
}
