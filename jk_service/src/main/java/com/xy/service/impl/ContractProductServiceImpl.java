package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.xy.dao.BaseDao;
import com.xy.domain.Contract;
import com.xy.domain.ContractProduct;
import com.xy.domain.ExtCproduct;
import com.xy.service.ContractProductService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractProductServiceImpl implements ContractProductService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct entity) {
		double amount = 0;
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//货物总金额
				entity.setAmount(amount);
			}
			//获取所属的购销合同
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			//获取总金额
			Double totalAmount = contract.getTotalAmount();
			//设置总金额相加
			contract.setTotalAmount(totalAmount+amount);
			//保存购销合同
			baseDao.saveOrUpdate(contract);//可以不写，快照机制会自动更新
		}else{
			//修改
			//取出货物原有金额
			double oldAmount = entity.getAmount();
			if(UtilFuns.isNotEmpty(entity.getPrice())&& UtilFuns.isNotEmpty(entity.getCnumber())){
				amount= entity.getPrice()*entity.getCnumber();//货物总金额
				entity.setAmount(amount);
			}
			//获取所属的购销合同
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			//获取原来的货物价格
			//设置修改后的总价格
			contract.setTotalAmount(contract.getTotalAmount()-oldAmount+amount);
		}
		//保存新增的货物
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(ContractProduct.class, id);
		}
		
	}

	public void delete(ContractProduct model) {
		//1.获取要删除的货物
		ContractProduct contractProduct = baseDao.get(ContractProduct.class, model.getId());
		
		//2.关联级别获取到货物的附件
		Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
		
		//3.获取到购销合同
		Contract contract = baseDao.get(Contract.class, model.getContract().getId());
		
		//4.修改购销合同的金额
		double extAmount = 0;
		for (ExtCproduct extCproduct : extCproducts) {
			extAmount+=extCproduct.getAmount();
		}
		contract.setTotalAmount(contract.getTotalAmount()-extAmount-contractProduct.getAmount());
		
		//5.保存
		baseDao.saveOrUpdate(contract);
		
		//6.删除货物，设置了附件<set name="extCproducts" cascade="all" inverse="true">级联删除
		baseDao.deleteById(ContractProduct.class, contractProduct.getId());
		
	}

}
