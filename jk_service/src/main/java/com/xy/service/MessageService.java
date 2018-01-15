package com.xy.service;


import com.xy.domain.Message;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface MessageService {
	// 查询所有，带条件查询
		public List<Message> find(String hql, Class<Message> entityClass, Object[] params);

		// 获取一条记录
		public Message get(Class<Message> entityClass, Serializable id);

		// 分页查询，将数据封装到一个page分页工具类对象
		public Page<Message> findPage(String hql, Page<Message> page, Class<Message> entityClass, Object[] params);

		// 新增和修改保存
		public void saveOrUpdate(Message entity);

		// 批量新增和修改保存
		public void saveOrUpdateAll(Collection<Message> entitys);

		// 单条删除，按id
		public void deleteById(Class<Message> entityClass, Serializable id);

		// 批量删除
		public void delete(Class<Message> entityClass, Serializable[] ids);
}
