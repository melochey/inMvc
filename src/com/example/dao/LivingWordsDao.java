package com.example.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.model.LivingWords;
import com.example.model.Page;
import com.example.util.BaseDao;
@Repository
public class LivingWordsDao extends BaseDao<LivingWords> {
	@Override
	public String getNameSpace() {
		return "LivingWords";
	}
	
	public List<LivingWords> maxLenComment(Page<LivingWords> e){
		List<LivingWords> l = getSession().selectList(getNameSpace()+".maxLenComment", e);
		return l;
	}
	
	public List<LivingWords> firstCome(Page<LivingWords> e){
		List<LivingWords> l = getSession().selectList(getNameSpace()+".firstCome", e);
		return l;
	}
	
	public List<LivingWords> searchCommentsByUser(Page<LivingWords> e){
		List<LivingWords> l = getSession().selectList(getNameSpace()+".searchCommentsByUser", e);
		return l;
	}
	
	public List<LivingWords> payMoneyComment(Page<LivingWords> e){
		List<LivingWords> l = getSession().selectList(getNameSpace()+".payMoneyComment", e);
		return l;
	}
	
	public int countUser(Page<LivingWords> e){
		int num=getSession().selectOne(getNameSpace()+".countUser", e);
		return num;
	}	
	
	public List<LivingWords> mostActiviteUsers(Page<LivingWords> e){
		List<LivingWords> l = getSession().selectList(getNameSpace()+".mostActiviteUsers", e);
		return l;
	}
	
	public List<Map> countSex(Map m){
		List<Map> result= getSession().selectList(getNameSpace()+".countSex", m);
		return result;
	}
	
	public List<Map> totalSex(Map m){
		List<Map> result= getSession().selectList(getNameSpace()+".totalSex", m);
		return result;
	}
}
