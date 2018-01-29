package com.xy.service.impl;


import com.xy.dao.BaseDao;
import com.xy.domain.Message;
import com.xy.service.MessageService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	private BaseDao baseDao;


	@Override
	public List<Message> find(String hql, Class<Message> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}
	@Override
	public Message get(Class<Message> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}
	@Override
	public Page<Message> findPage(String hql, Page<Message> page, Class<Message> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}
	@Override
	public void saveOrUpdate(Message entity) {

		baseDao.saveOrUpdate(entity);
	}
	@Override
	public void saveOrUpdateAll(Collection<Message> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}
	@Override
	public void deleteById(Class<Message> entityClass, Serializable id) {

	}

	@Override
	public void delete(Class<Message> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
