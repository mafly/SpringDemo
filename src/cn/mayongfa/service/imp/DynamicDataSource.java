package cn.mayongfa.service.imp;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import cn.mayongfa.service.imp.JdbcContextHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return JdbcContextHolder.getJdbcType();
	}
}
