package com.xy.service;

import com.xy.domain.Finance;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 财务
 * @date 2017/12/26.
 */
public interface FinanceService {

	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params);
	public Finance get(Class<Finance> entityClass, Serializable id);
	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params);

	public void saveOrUpdate(Finance entity);
	public void saveOrUpdateAll(Collection<Finance> entitys);

	/**
	 * 修改状态
	 */
	public void changeState(String[] ids, Integer state);

	public void deleteById(Class<Finance> entityClass, Serializable id);
	public void delete(Class<Finance> entityClass, Serializable[] ids);
}
