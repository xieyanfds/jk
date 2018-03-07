package com.xy.service.impl;

import com.google.common.collect.Sets;
import com.xy.dao.BaseDao;
import com.xy.domain.Module;
import com.xy.domain.Role;
import com.xy.domain.User;
import com.xy.domain.Userinfo;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * @author xieyan
 * @description 用户管理
 * @date 2017/12/26.
 */
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
		//重载session中用户权限


		baseDao.saveOrUpdate(entity);
		
	}

	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	@Override
	public void deleteById(Class<User> entityClass, Serializable id) {
		List<Userinfo> users = baseDao.find("from Userinfo where manager.id = '" + id + "'", Userinfo.class, null);
		for(Userinfo user : users){
			user.setManager(null);
			//快照机制
//			baseDao.saveOrUpdate(user);
		}
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<User> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			this.deleteById(User.class, id);
		}
		
	}
	
}
