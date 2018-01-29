package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Contract;
import com.xy.domain.AccessLog;
import com.xy.domain.ExtCproduct;
import com.xy.service.AccessLogService;
import com.xy.service.AccessLogService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class AccessLogServiceImpl implements AccessLogService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<AccessLog> find(String hql, Class<AccessLog> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public AccessLog get(Class<AccessLog> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<AccessLog> findPage(String hql, Page<AccessLog> page, Class<AccessLog> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(AccessLog entity) {
		baseDao.saveOrUpdate(entity);
	}


	@Override
	public void deleteById(Class<AccessLog> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<AccessLog> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(AccessLog.class, id);
		}
		
	}

	public void delete(AccessLog model) {
		baseDao.deleteById(AccessLog.class, model.getId());
		
	}

}
