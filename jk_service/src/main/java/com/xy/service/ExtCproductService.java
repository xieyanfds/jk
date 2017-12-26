package com.xy.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.domain.ExtCproduct;
import com.xy.utils.Page;

public interface ExtCproductService {
	//查询所有，带条件查询
	public  List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params);
	//获取一条记录
	public  ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public  Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Class<ExtCproduct> entityClass, Object[] params);
	
	//新增和修改保存
	public  void saveOrUpdate(ExtCproduct entity);
	//批量新增和修改保存
	public  void saveOrUpdateAll(Collection<ExtCproduct> entitys);
	
	//单条删除，按id
	public  void deleteById(Class<ExtCproduct> entityClass, Serializable id);
	//批量删除
	public  void delete(Class<ExtCproduct> entityClass, Serializable[] ids);
	//删除附件
	public void delete(Class<ExtCproduct> entityClass, ExtCproduct model);
	
}
