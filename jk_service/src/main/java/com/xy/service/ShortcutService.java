package com.xy.service;


import com.xy.domain.Shortcut;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface ShortcutService {

	public List<Shortcut> find(String hql, Class<Shortcut> entityClass, Object[] params);
	public Shortcut get(Class<Shortcut> entityClass, Serializable id);
	public Page<Shortcut> findPage(String hql, Page<Shortcut> page, Class<Shortcut> entityClass, Object[] params);
	
	public void save(Shortcut entity);
	public void saveOrUpdate(Shortcut entity);
	public void saveOrUpdateAll(Collection<Shortcut> entitys);
	
	public void deleteById(Class<Shortcut> entityClass, Serializable id);
	public void delete(Class<Shortcut> entityClass, Serializable[] ids);
}
