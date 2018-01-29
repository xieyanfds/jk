package com.xy.service.impl;


import com.xy.dao.BaseDao;
import com.xy.domain.TaskList;
import com.xy.service.TaskListService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description 任务管理
 * @date 2018/01/26.
 */
@Service
public class TaskListServiceImpl implements TaskListService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<TaskList> find(String hql, Class<TaskList> entityClass, Object[] params) {
		return baseDao.find(hql, TaskList.class, params);
	}
	@Override
	public TaskList get(Class<TaskList> entityClass, Serializable id) {
		return baseDao.get(TaskList.class, id);
	}
	@Override
	public Page<TaskList> findPage(String hql, Page<TaskList> page, Class<TaskList> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, TaskList.class, params);
	}
	@Override
	public void saveOrUpdate(TaskList entity) {
		//代表新增
		if(entity.getId()==null){	
			//设置状态
			entity.setState(0.0);
		}
		baseDao.saveOrUpdate(entity);
	}


	@Override
	public void saveOrUpdateAll(Collection<TaskList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}
	/**
	 * 删除一条代办任务
	 */
	@Override
	public void deleteById(Class<TaskList> entityClass, Serializable id) {
		baseDao.deleteById(TaskList.class, id);
	}
	/**
	 * 删除多条代办任务
	 */
	@Override
	public void delete(Class<TaskList> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			baseDao.deleteById(TaskList.class, id);
		}
	}


}
