package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Export;
import com.xy.domain.ShippingOrder;
import com.xy.service.ShippingOrderService;
import com.xy.service.ShippingOrderService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/26.
 */
@Service
public class ShippingOrderServiceImpl implements ShippingOrderService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.find(hql, ShippingOrder.class, params);
	}

	@Override
	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id) {
		return baseDao.get(ShippingOrder.class, id);
	}

	@Override
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ShippingOrder.class, params);
	}

	@Override
	public void saveOrUpdate(ShippingOrder entity) {
		if(entity.getId()==null){	
			entity.setState(0);//代表新增
		}
		baseDao.saveOrUpdate(entity);
	}



	public void saveOrUpdateAll(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ShippingOrder> entityClass, Serializable id) {
		baseDao.deleteById(ShippingOrder.class, id);
	}

	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids) {
		baseDao.delete(ShippingOrder.class, ids);
	}

}

