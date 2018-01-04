package com.xy.dao.impl;

import com.xy.dao.BaseDao;
import com.xy.utils.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * @author xieyan
 * @description 通用dao
 * @date 2017/09/15.
 */
public class BaseDaoImpl implements BaseDao{
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 带条件查询
	 * @param hql
	 * @param entityClass
	 * @param params
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> List<T> find(String hql, Class<T> entityClass, Object[] params) {
		Query query = this.getSession().createQuery(hql);
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.list();
	}

	/**
	 * 获取一条，根据主键id
	 * @param entityClass
	 * @param id
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) this.getSession().get(entityClass, id);
	}

	/**
	 * 分页查询，查询两次，一次查询总数，一次查询分页记录
	 * @param hql
	 * @param page
	 * @param entityClass
	 * @param params
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> Page<T> findPage(String hql, Page<T> page, Class<T> entityClass, Object[] params){
		
		Query query = this.getSession().createQuery(hql);
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		//查询一次，获取记录总数
//		String hql1 = "select count(*) "+hql;
//		Query query2 = this.getSession().createQuery(hql1);
//		List<Number> list = query2.list();
//		page.setTotalRecord(list.get(0).intValue());
		int count = query.list().size();
		page.setTotalRecord(count);
		
		//设置分页
		query.setFirstResult((page.getPageNo()-1)*page.getPageSize());	//设置开始位置
		query.setMaxResults(page.getPageSize());				//设置获取几条
		page.setResults((List<T>)query.list());
		
		return page;
	}

	/**
	 * 新增和修改，hibernate根据id是否为null自动判断
	 * @param entity
	 * @param <T>
	 */
	@Override
	public <T> void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	/**
	 * 集合保存，这时新增还是修改，就自动判断，调用时是否简洁。适合批量新增和修改时。（Mrecord控件）
	 * @param entitys
	 * @param <T>
	 */
	@Override
	public <T> void saveOrUpdateAll(Collection<T> entitys){
		for(T entity : entitys){
			this.saveOrUpdate(entity);
			//为什么hibernate批量操作时，要用循环一条一条记录去更新？
			//hibernate中存在一级缓存，当进行批量数据操作时，会大大降低效率，所以可以采用处理一定量数据之后就清空缓存
			//最有效率的方式是用jdbc操作，使用batch的方式效率会有很大提升
		}
	}

	/**
	 * 按主键id删除
	 * @param entityClass
	 * @param id
	 * @param <T>
	 */
	@Override
	public <T> void deleteById(Class<T> entityClass, Serializable id) {
		this.getSession().delete(get(entityClass, id));
	}

	/**
	 * 批量删除
	 * @param entityClass
	 * @param ids
	 * @param <T>
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Serializable[] ids) {
		for(Serializable s : ids){
			deleteById(entityClass, s);
		}
	}


}

