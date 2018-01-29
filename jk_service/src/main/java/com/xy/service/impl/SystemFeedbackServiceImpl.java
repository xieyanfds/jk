package com.xy.service.impl;


import com.xy.dao.BaseDao;
import com.xy.domain.SystemFeedback;
import com.xy.service.SystemFeedbackService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 系统使用反馈
 * @date 2017/12/26.
 */
@Service
public class SystemFeedbackServiceImpl implements SystemFeedbackService {
	@Autowired
	private BaseDao baseDao;

	public List<SystemFeedback> find(String hql, Class<SystemFeedback> entityClass, Object[] params) {
		return baseDao.find(hql, SystemFeedback.class, params);
	}

	public SystemFeedback get(Class<SystemFeedback> entityClass, Serializable id) {
		return baseDao.get(SystemFeedback.class, id);
	}

	public Page<SystemFeedback> findPage(String hql, Page<SystemFeedback> page, Class<SystemFeedback> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, SystemFeedback.class, params);
	}

	public void saveOrUpdate(SystemFeedback entity) {
		if(entity.getId()==null){								//代表新增
			//entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}



	public void saveOrUpdateAll(Collection<SystemFeedback> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<SystemFeedback> entityClass, Serializable id) {
		baseDao.deleteById(SystemFeedback.class, id);
	}

	public void delete(Class<SystemFeedback> entityClass, Serializable[] ids) {
		baseDao.delete(SystemFeedback.class, ids);
	}
}
