package com.xy.service;

import com.xy.domain.ShippingOrder;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 委托单
 * @date 2017/12/26.
 */
public interface ShippingOrderService {

	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params);
	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id);
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params);
	
	public void saveOrUpdate(ShippingOrder entity);
	public void saveOrUpdateAll(Collection<ShippingOrder> entitys);

	/**
	 * 修改状态
	 */
	public void changeState(String [] ids,Integer state);

	public void deleteById(Class<ShippingOrder> entityClass, Serializable id);
	public void delete(Class<ShippingOrder> entityClass, Serializable[] ids);
}
