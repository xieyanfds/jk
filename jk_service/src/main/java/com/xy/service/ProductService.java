package com.xy.service;

import com.xy.domain.Product;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 产品
 * @date 2017/12/26.
 */
public interface ProductService {

	public List<Product> find(String hql, Class<Product> entityClass, Object[] params);
	public Product get(Class<Product> entityClass, Serializable id);
	public Page<Product> findPage(String hql, Page<Product> page, Class<Product> entityClass, Object[] params);

	public void saveOrUpdate(Product entity);
	public void saveOrUpdateAll(Collection<Product> entitys);

	/**
	 * 修改状态
	 */
	public void changeState(String[] ids, Integer state);

	public void deleteById(Class<Product> entityClass, Serializable id);
	public void delete(Class<Product> entityClass, Serializable[] ids);
}
