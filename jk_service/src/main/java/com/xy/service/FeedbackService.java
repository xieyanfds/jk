package com.xy.service;


import com.xy.domain.Feedback;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface FeedbackService {

	public List<Feedback> find(String hql, Class<Feedback> entityClass, Object[] params);
	public Feedback get(Class<Feedback> entityClass, Serializable id);
	public Page<Feedback> findPage(String hql, Page<Feedback> page, Class<Feedback> entityClass, Object[] params);
	
	public void saveOrUpdate(Feedback entity);
	public void saveOrUpdateAll(Collection<Feedback> entitys);
	
	public void deleteById(Class<Feedback> entityClass, Serializable id);
	public void delete(Class<Feedback> entityClass, Serializable[] ids);
	public void submit(String[] ids, Integer state);
}
