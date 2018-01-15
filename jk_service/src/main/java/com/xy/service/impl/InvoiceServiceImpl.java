package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.PackingList;
import com.xy.domain.Invoice;
import com.xy.domain.ShippingOrder;
import com.xy.service.InvoiceService;
import com.xy.service.InvoiceService;
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
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params) {
		return baseDao.find(hql, Invoice.class, params);
	}

	@Override
	public Invoice get(Class<Invoice> entityClass, Serializable id) {
		return baseDao.get(Invoice.class, id);
	}

	@Override
	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Invoice.class, params);
	}

	@Override
	public void saveOrUpdate(Invoice entity) {
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String [] ids, Integer state) {
		for(String id :ids){
			Invoice invoice = baseDao.get(Invoice.class, id);
			invoice.setState(state);
			//可以不写
			baseDao.saveOrUpdate(invoice);
		}
	}

	@Override
	public void saveOrUpdateAll(Collection<Invoice> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Invoice> entityClass, Serializable id) {
		//将委托单状态改为1
		ShippingOrder shippingOrder = baseDao.get(ShippingOrder.class, id);
		shippingOrder.setState(1);
		baseDao.deleteById(Invoice.class, id);
	}

	@Override
	public void delete(Class<Invoice> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			this.deleteById(Invoice.class,id);
		}
	}

}

