package cn.mayongfa.common;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import cn.mayongfa.common.JdbcContextHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		// 获取当前数据源连接
		return JdbcContextHolder.getJdbcType();
	}
}
