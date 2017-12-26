package com.xy.service.impl;

import java.util.List;

import com.xy.dao.springdao.SqlDao;
import com.xy.service.StatChartService;

public class StatChartServiceImpl implements StatChartService {
	
	private SqlDao sqlDao;
	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}
	public List<String> execSQL(String sql) throws Exception {
		return sqlDao.executeSQL(sql);
	}
	
	

}
