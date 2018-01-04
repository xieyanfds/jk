package com.xy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.xy.utils.Page;
import org.apache.poi.ss.formula.functions.T;


/**
 * @author xieyan
 * @description 通用dao
 * @date 2017/09/15.
 */
public interface BaseDao {

	/**
	 * 查询所有，带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @return
	 */
	<T> List<T> find(String hql, Class<T> entityClass, Object[] params);

	/**
	 * 获取一条记录
	 * @param entityClass
	 * @param id
	 * @return
	 */
	<T> T get(Class<T> entityClass, Serializable id);

	/**
	 * 分页查询，将数据封装到一个page分页工具类对象
	 * @param hql
	 * @param page
	 * @param entityClass
	 * @param params
	 * @return
	 */
	<T> Page<T> findPage(String hql, Page<T> page, Class<T> entityClass, Object[] params);

	/**
	 * 新增和修改保存
	 * @param entity
	 * @param <T>
	 */
	<T> void saveOrUpdate(T entity);

	/**
	 * 批量新增和修改保存
	 * @param entitys
	 * @param <T>
	 */
	<T> void saveOrUpdateAll(Collection<T> entitys);

	/**
	 * 单条删除，按id
	 * @param entityClass
	 * @param id
	 * @param <T>
	 */
	<T> void deleteById(Class<T> entityClass, Serializable id);

	/**
	 * 批量删除
	 * @param entityClass
	 * @param ids
	 * @param <T>
	 */
	<T> void delete(Class<T> entityClass, Serializable[] ids);

}