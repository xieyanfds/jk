package com.xy.service;


import com.xy.domain.TaskList;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;



public interface TaskListService {

	public List<TaskList> find(String hql, Class<TaskList> entityClass, Object[] params);
	public TaskList get(Class<TaskList> entityClass, Serializable id);
	public Page<TaskList> findPage(String hql, Page<TaskList> page, Class<TaskList> entityClass, Object[] params);
	
	public void saveOrUpdate(TaskList entity);
	public void saveOrUpdateAll(Collection<TaskList> entitys);
	
	public void deleteById(Class<TaskList> entityClass, Serializable id);
	public void delete(Class<TaskList> entityClass, Serializable[] ids);
}
