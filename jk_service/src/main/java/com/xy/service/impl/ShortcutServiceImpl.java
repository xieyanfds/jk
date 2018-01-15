package com.xy.service.impl;


import com.xy.dao.BaseDao;
import com.xy.domain.Shortcut;
import com.xy.service.ShortcutService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
public class ShortcutServiceImpl implements ShortcutService {
	@Autowired
	private BaseDao baseDao;

	public List<Shortcut> find(String hql, Class<Shortcut> entityClass, Object[] params) {
		return baseDao.find(hql, Shortcut.class, params);
	}

	public Shortcut get(Class<Shortcut> entityClass, Serializable id) {
		return baseDao.get(Shortcut.class, id);
	}

	public Page<Shortcut> findPage(String hql, Page<Shortcut> page, Class<Shortcut> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Shortcut.class, params);
	}

	public void saveOrUpdate(Shortcut entity) {
		
		baseDao.saveOrUpdate(entity);
	}
	public void saveOrUpdateAll(Collection<Shortcut> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Shortcut> entityClass, Serializable id) {
		baseDao.deleteById(Shortcut.class, id);
	}

	public void delete(Class<Shortcut> entityClass, Serializable[] ids) {
		baseDao.delete(Shortcut.class, ids);
	}
	@Override
	public void save(Shortcut entity) {
	}
}


