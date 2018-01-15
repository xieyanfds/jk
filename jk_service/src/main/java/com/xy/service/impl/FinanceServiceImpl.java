package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Finance;
import com.xy.domain.PackingList;
import com.xy.domain.ShippingOrder;
import com.xy.service.FinanceService;
import com.xy.service.FinanceService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description 财务
 * @date 2017/12/26.
 */
@Service
public class FinanceServiceImpl implements FinanceService {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params) {
		return baseDao.find(hql, Finance.class, params);
	}

	@Override
	public Finance get(Class<Finance> entityClass, Serializable id) {
		return baseDao.get(Finance.class, id);
	}

	@Override
	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Finance.class, params);
	}

	@Override
	public void saveOrUpdate(Finance entity) {
		if(entity.getId()==null){
			//代表新增
			entity.setState(0);

			//自动保存
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String [] ids, Integer state) {
		for(String id :ids){
			Finance invoice = baseDao.get(Finance.class, id);
			invoice.setState(state);
			//可以不写
			baseDao.saveOrUpdate(invoice);
		}
	}

	@Override
	public void saveOrUpdateAll(Collection<Finance> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Finance> entityClass, Serializable id) {
		//将委托单状态改为1
		ShippingOrder shippingOrder = baseDao.get(ShippingOrder.class, id);
		shippingOrder.setState(1);
		baseDao.deleteById(Finance.class, id);
	}

	@Override
	public void delete(Class<Finance> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			this.deleteById(Finance.class,id);
		}
	}

}

