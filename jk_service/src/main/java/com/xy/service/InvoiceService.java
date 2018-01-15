package com.xy.service;

import com.xy.domain.Invoice;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 发票
 * @date 2017/12/26.
 */
public interface InvoiceService {

	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params);
	public Invoice get(Class<Invoice> entityClass, Serializable id);
	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params);

	public void saveOrUpdate(Invoice entity);
	public void saveOrUpdateAll(Collection<Invoice> entitys);

	/**
	 * 修改状态
	 */
	public void changeState(String[] ids, Integer state);

	public void deleteById(Class<Invoice> entityClass, Serializable id);
	public void delete(Class<Invoice> entityClass, Serializable[] ids);
}
