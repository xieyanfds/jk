package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.LoginLog;
import com.xy.service.LoginLogService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Service
public class LoginLogServiceImpl implements LoginLogService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<LoginLog> find(String hql, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.find(hql, LoginLog.class, params);
	}
	@Override
	public LoginLog get(Class<LoginLog> entityClass, Serializable id) {
		return baseDao.get(LoginLog.class, id);
	}
	@Override
	public Page<LoginLog> findPage(String hql, Page<LoginLog> page, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, LoginLog.class, params);
	}
	@Override
	public void saveOrUpdate(LoginLog entity) {
		baseDao.saveOrUpdate(entity);
	}



	@Override
	public void saveOrUpdateAll(Collection<LoginLog> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<LoginLog> entityClass, Serializable id) {
		baseDao.deleteById(LoginLog.class, id);
	}

	@Override
	public void delete(Class<LoginLog> entityClass, Serializable[] ids) {
		baseDao.delete(LoginLog.class, ids);
	}


}
