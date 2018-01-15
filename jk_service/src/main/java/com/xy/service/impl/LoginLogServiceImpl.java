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

	public List<LoginLog> find(String hql, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.find(hql, LoginLog.class, params);
	}

	public LoginLog get(Class<LoginLog> entityClass, Serializable id) {
		return baseDao.get(LoginLog.class, id);
	}

	public Page<LoginLog> findPage(String hql, Page<LoginLog> page, Class<LoginLog> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, LoginLog.class, params);
	}

	public void saveOrUpdate(LoginLog entity) {
		if(entity.getId()==null){								//代表新增
			baseDao.saveOrUpdate(entity);							//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	public void saveOrUpdateAll(Collection<LoginLog> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<LoginLog> entityClass, Serializable id) {
		baseDao.deleteById(LoginLog.class, id);
	}

	public void delete(Class<LoginLog> entityClass, Serializable[] ids) {
		baseDao.delete(LoginLog.class, ids);
	}

	@Override
	public void save(LoginLog entity) {
	}

}
