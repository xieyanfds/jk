package com.xy.service;

import com.xy.domain.AccessLog;
import com.xy.utils.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author xieyan
 * @description action日志
 * @date 2017/12/26.
 */
public interface AccessLogService {
	//查询所有，带条件查询
	public  List<AccessLog> find(String hql, Class<AccessLog> entityClass, Object[] params);
	//获取一条记录
	public  AccessLog get(Class<AccessLog> entityClass, Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public  Page<AccessLog> findPage(String hql, Page<AccessLog> page, Class<AccessLog> entityClass, Object[] params);
	
	//新增和修改保存
	public  void saveOrUpdate(AccessLog entity);

	//单条删除，按id
	public  void deleteById(Class<AccessLog> entityClass, Serializable id);
	//批量删除
	public  void delete(Class<AccessLog> entityClass, Serializable[] ids);
	
}
