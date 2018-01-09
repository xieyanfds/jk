package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Export;
import com.xy.domain.PackingList;
import com.xy.service.PackingListService;
import com.xy.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xieyan
 * @description 装箱单
 * @date 2017/12/26.
 */
@Service
public class PackingListServiceImpl implements PackingListService {
	@Autowired
	private BaseDao baseDao;

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params) {
		return baseDao.find(hql, PackingList.class, params);
	}

	public PackingList get(Class<PackingList> entityClass, Serializable id) {
		return baseDao.get(PackingList.class, id);
	}

	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, PackingList.class, params);
	}

	@Override
	public void saveOrUpdate(PackingList entity) {
		if(entity.getId()==null){	
			entity.setState(0);//代表新增
		}
		String [] ids = entity.getExportIds().split(", ");
		for (String id : ids) {
			Export export = baseDao.get(Export.class, id);
			export.setState(3);
		}
		entity.setExportNos(entity.getExportNos().substring(0,entity.getExportNos().length()-2));
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void changeState(String [] ids, Integer state) {
		for(String id :ids){
			PackingList packingList = baseDao.get(PackingList.class, id);
			packingList.setState(state);
			//可以不写
			baseDao.saveOrUpdate(packingList);
		}
	}

	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		//修改装箱单下每个报运单的状态，将已装箱改为已电子报运
		PackingList packingList = baseDao.get(PackingList.class, id);
		String exportIds = packingList.getExportIds();
		String[] split = exportIds.split(", ");
		for(String eid : split){
			Export export = baseDao.get(Export.class, eid);
			export.setState(2);
			//快照机制自动保存
		}
		baseDao.deleteById(PackingList.class, id);
	}

	@Override
	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			this.deleteById(PackingList.class,id);
		}
	}

}

