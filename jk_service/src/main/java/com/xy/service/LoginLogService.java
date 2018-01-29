package com.xy.service;


import com.xy.domain.LoginLog;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface LoginLogService {

	public List<LoginLog> find(String hql, Class<LoginLog> entityClass, Object[] params);
	public LoginLog get(Class<LoginLog> entityClass, Serializable id);
	public Page<LoginLog> findPage(String hql, Page<LoginLog> page, Class<LoginLog> entityClass, Object[] params);
	
	public void saveOrUpdate(LoginLog entity);
	public void saveOrUpdateAll(Collection<LoginLog> entitys);
	
	public void deleteById(Class<LoginLog> entityClass, Serializable id);
	public void delete(Class<LoginLog> entityClass, Serializable[] ids);
}
