package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Contract;
import com.xy.service.ContractService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Contract get(Class<Contract> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Contract> findPage(String hql, Page<Contract> page, Class<Contract> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Contract entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			entity.setTotalAmount(0d);
			entity.setState(0);//0草稿   1已上报   2已报运
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Contract> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Contract> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Contract> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(Contract.class, id);
		}
		
	}

	public void changeState(String[] ids, int state) {
		for (String s : ids) {
			//获取修改的购销合同
			Contract contract = baseDao.get(Contract.class, s);
			//修改它的状态,如果此时已报运就不能修改
			if(contract.getState()!=2) {
				contract.setState(state);
			}
			//持久化到数据库
			//baseDao.saveOrUpdate(contract);//可以不写
		}
	}
	
}
