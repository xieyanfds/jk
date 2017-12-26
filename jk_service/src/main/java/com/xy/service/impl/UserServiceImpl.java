package com.xy.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.xy.dao.BaseDao;
import com.xy.domain.User;
import com.xy.service.UserService;
import com.xy.utils.Encrypt;
import com.xy.utils.Page;
import com.xy.utils.SysConstant;
import com.xy.utils.UtilFuns;

public class UserServiceImpl implements UserService{
	
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	private SimpleMailMessage simpleMailMessage;
	private JavaMailSender javaMailSender;
	
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

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
		
		//再开启一个线程完成邮件发送功能
		//spring集成javaMail
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					simpleMailMessage.setTo(entity.getUserInfo().getEmail());
					simpleMailMessage.setSubject("新员工入职的系统账户通知");
					simpleMailMessage.setText("欢迎您加入本集团，您的用户名:"+entity.getUserName()+",初始密码："+SysConstant.DEFAULT_PASS);
					
					javaMailSender.send(simpleMailMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		th.start();
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
