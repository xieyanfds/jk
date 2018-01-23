package com.xy.service.impl;

import com.xy.dao.BaseDao;
import com.xy.domain.User;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao baseDao;

	
	@Override
	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public User get(Class<User> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public void saveOrUpdate(final User entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			//新增,设置主键
			String uuid = UUID.randomUUID().toString();
			entity.setId(uuid);
			entity.getUserInfo().setId(uuid);
			//设置密码
			entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));
		}
		
		baseDao.saveOrUpdate(entity);
		
	}

	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<User> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<User> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(User.class, id);
		}
		
	}
	
}
