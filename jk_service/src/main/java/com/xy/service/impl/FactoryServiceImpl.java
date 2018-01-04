package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Factory;
import com.xy.service.FactoryService;
import com.xy.service.FactoryService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactoryServiceImpl implements FactoryService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Factory get(Class<Factory> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Factory> findPage(String hql, Page<Factory> page, Class<Factory> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Factory entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			entity.setState(0);//0草稿   1已上报   2已报运
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Factory> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Factory> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Factory> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(Factory.class, id);
		}
		
	}

	public void changeState(String[] ids, int state) {
		for (String s : ids) {
			//获取修改的购销合同
			Factory Factory = baseDao.get(Factory.class, s);
			//修改它的状态
			Factory.setState(state);
			//持久化到数据库
			//baseDao.saveOrUpdate(Factory);//可以不写
		}
	}
	
}
