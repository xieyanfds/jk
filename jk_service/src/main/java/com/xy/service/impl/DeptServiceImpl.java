package com.xy.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.xy.dao.BaseDao;
import com.xy.domain.Dept;
import com.xy.service.DeptService;
import com.xy.utils.Page;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public Dept get(Class<Dept> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<Dept> findPage(String hql, Page<Dept> page, Class<Dept> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Dept entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增
			entity.setState(1);//1启用  0停用  默认为启用
		}
		baseDao.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<Dept> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<Dept> entityClass, Serializable id) {
		String hql = "from Dept where parent.id = ?";
		List<Dept> list = baseDao.find(hql, Dept.class, new Object[]{id});
		if(list!=null && list.size()>0){
			for (Dept dept : list) {
				deleteById(Dept.class, dept.getId());
			}
		}
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Dept> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(Dept.class, id);
		}
		
	}

	@Override
	public void findSubDept(ArrayList<Dept> list, Class<Dept> deptClass, String id) {
		List<Dept> list2 = baseDao.find("from Dept where state = 1 and parent.id = ?", Dept.class, id);
		if (list2 != null && list2.size() > 0) {
			list.addAll(list2);
			for (Dept dept : list2) {
				findSubDept(list, Dept.class, dept.getId());
			}

		}
	}

}
