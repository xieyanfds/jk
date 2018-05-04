package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.Export;
import com.xy.domain.PackingList;
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
	public List<ShippingOrder> find(String hql, Object[] params) {
		return baseDao.find(hql, ShippingOrder.class, params);
	}

	@Override
	public ShippingOrder get(Serializable id) {
		return baseDao.get(ShippingOrder.class, id);
	}

	@Override
	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Object[] params) {
		return baseDao.findPage(hql, page, ShippingOrder.class, params);
	}

	@Override
	public void saveOrUpdate(ShippingOrder entity) {
		if(entity.getId()==null){	
			entity.setState(0);//代表新增
		}
		//修改对应装箱单状态
		PackingList packingList = baseDao.get(PackingList.class, entity.getId());
		packingList.setState(2);
		entity.setPackingList(packingList);
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String [] ids, Integer state) {
		for(String id :ids){
			ShippingOrder shippingOrder = baseDao.get(ShippingOrder.class, id);
			shippingOrder.setState(state);
			//可以不写
			baseDao.saveOrUpdate(shippingOrder);
		}
	}

	@Override
	public void saveOrUpdateAll(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Serializable id) {
		//将委托单下的装箱单状态改为1
		PackingList packingList = baseDao.get(PackingList.class, id);
		packingList.setState(1);
		baseDao.deleteById(ShippingOrder.class, id);
	}

	@Override
	public void delete(Serializable[] ids) {
		for(Serializable id:ids){
			this.deleteById(id);
		}
	}

}

