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
			String [] ids = entity.getExportIds().split(", ");
			for (String id : ids) {
				Export export = baseDao.get(Export.class, id);
				export.setState(3);
			}
		}
		baseDao.saveOrUpdate(entity);
	}



	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		baseDao.deleteById(PackingList.class, id);
	}

	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		baseDao.delete(PackingList.class, ids);
	}

}

