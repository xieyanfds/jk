package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Product;
import com.xy.domain.ShippingOrder;
import com.xy.service.ProductService;
import com.xy.service.ProductService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description 发票
 * @date 2017/12/26.
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Product> find(String hql, Class<Product> entityClass, Object[] params) {
		return baseDao.find(hql, Product.class, params);
	}

	@Override
	public Product get(Class<Product> entityClass, Serializable id) {
		return baseDao.get(Product.class, id);
	}

	@Override
	public Page<Product> findPage(String hql, Page<Product> page, Class<Product> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Product.class, params);
	}

	@Override
	public void saveOrUpdate(Product entity) {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String [] ids, Integer state) {
		for(String id :ids){
			Product invoice = baseDao.get(Product.class, id);
			//可以不写
			baseDao.saveOrUpdate(invoice);
		}
	}

	@Override
	public void saveOrUpdateAll(Collection<Product> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Product> entityClass, Serializable id) {
		//将委托单状态改为1
		ShippingOrder shippingOrder = baseDao.get(ShippingOrder.class, id);
		shippingOrder.setState(1);
		baseDao.deleteById(Product.class, id);
	}

	@Override
	public void delete(Class<Product> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			this.deleteById(Product.class,id);
		}
	}

}

