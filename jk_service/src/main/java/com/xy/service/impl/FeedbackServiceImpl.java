package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Feedback;
import com.xy.service.FeedbackService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Feedback> find(String hql, Class<Feedback> entityClass, Object[] params) {
		return baseDao.find(hql, Feedback.class, params);
	}
	@Override
	public Feedback get(Class<Feedback> entityClass, Serializable id) {
		return baseDao.get(Feedback.class, id);
	}
	@Override
	public Page<Feedback> findPage(String hql, Page<Feedback> page, Class<Feedback> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Feedback.class, params);
	}

	@Override
	public void saveOrUpdate(Feedback entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(0);								//状态：0停用1启用 默认启用
			entity.setInputTime(new Date());
			entity.setCreateTime(new Date());
		}
		baseDao.saveOrUpdate(entity);
	}


	@Override
	public void saveOrUpdateAll(Collection<Feedback> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}
	@Override
	public void deleteById(Class<Feedback> entityClass, Serializable id) {
		baseDao.deleteById(Feedback.class, id);
	}
	@Override
	public void delete(Class<Feedback> entityClass, Serializable[] ids) {
		baseDao.delete(Feedback.class, ids);
	}

	@Override
	public void submit(String[] ids, Integer state) {
		for (String id : ids) {
			Feedback f=baseDao.get(Feedback.class,id);
			f.setState(state);
		}
		
	}

}

