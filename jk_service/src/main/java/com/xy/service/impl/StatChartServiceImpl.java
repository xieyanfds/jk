package com.xy.service.impl;

import java.util.List;

import com.xy.dao.springdao.SqlDao;
import com.xy.service.StatChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatChartServiceImpl implements StatChartService {

	@Autowired
	private SqlDao sqlDao;

	@Override
	public List<String> execSQL(String sql) throws Exception {
		return sqlDao.executeSQL(sql);
	}
	
	

}
