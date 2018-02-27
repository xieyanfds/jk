package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Module;
import com.xy.service.ModuleService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Module get(Class<Module> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Module> findPage(String hql, Page<Module> page, Class<Module> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Module entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			entity.setState(1);//1启用  0停用  默认为启用
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Module> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Module> entityClass, Serializable id) {
		String hql = "from Module where parentId = ?";
		List<Module> list = baseDao.find(hql, Module.class, new Object[]{id});
		if(list!=null && list.size()>0){
			for (Module Module : list) {
				deleteById(Module.class, Module.getId());
			}
		}
		Module module = baseDao.get(Module.class, id);
		if(module != null){
			baseDao.deleteById(entityClass, id);
		}
	}

	@Override
	public void delete(Class<Module> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(Module.class, id);
		}
		
	}
	
}
