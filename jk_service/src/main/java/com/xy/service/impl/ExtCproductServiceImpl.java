package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Contract;
import com.xy.domain.ContractProduct;
import com.xy.domain.ExtCproduct;
import com.xy.service.ExtCproductService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;

public class ExtCproductServiceImpl implements ExtCproductService {
	
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct entity) {
		double amount = 0;
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//附件总金额
				entity.setAmount(amount);
			}
			
			//修改购销合同的总金额 
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount()+amount);
			
			//保存购销合同的总金额 
			//快照机制，可以不写
		}else{
			//修改
			double oldAmount = entity.getAmount();//取出货物的原有总金额 
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//货物总金额
				entity.setAmount(amount);
			}
			
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());//根据购销合同的id,得到购销合同对象
			contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
		}
		//5.保存附件
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(ExtCproduct.class, id);
		}
	}

	public void delete(Class<ExtCproduct> entityClass, ExtCproduct entity) {
		//1.获取购销合同
		Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());
		
		//3.获取删除的附件
		ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, entity.getId());
		
		//修改购销合同的总金额 
		contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		
		//5.删除附件
		baseDao.deleteById(ExtCproduct.class, entity.getId());
	}
}
