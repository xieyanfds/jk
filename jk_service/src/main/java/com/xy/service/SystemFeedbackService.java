package com.xy.service;


import com.xy.domain.SystemFeedback;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description 系统使用反馈
 * @date 2017/12/26.
 */


public interface SystemFeedbackService {

	public List<SystemFeedback> find(String hql, Class<SystemFeedback> entityClass, Object[] params);
	public SystemFeedback get(Class<SystemFeedback> entityClass, Serializable id);
	public Page<SystemFeedback> findPage(String hql, Page<SystemFeedback> page, Class<SystemFeedback> entityClass, Object[] params);
	
	public void saveOrUpdate(SystemFeedback entity);
	public void saveOrUpdateAll(Collection<SystemFeedback> entitys);
	
	public void deleteById(Class<SystemFeedback> entityClass, Serializable id);
	public void delete(Class<SystemFeedback> entityClass, Serializable[] ids);
}
