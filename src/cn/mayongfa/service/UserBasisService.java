package cn.mayongfa.service;

import java.util.List;
import java.util.Map;

import cn.mayongfa.interceptor.DataSource;
import cn.mayongfa.interceptor.DataSourceType;
import cn.mayongfa.model.UserBasis;

public interface UserBasisService {

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	@DataSource
	public long Save(UserBasis entity);
	
	/**
	 * 删除
	 * @param ID
	 * @return
	 */
	@DataSource
	public Boolean Delete(long ID);
	
	/**
	 * 获取信息
	 * @param ID
	 * @return
	 */
	@DataSource(DataSourceType.Slave)
	public UserBasis getEntity(long ID);
	
	/**
	 * 获取信息
	 * @param phone
	 * @return
	 */
	@DataSource(DataSourceType.Slave)
	public UserBasis getEntity(String phone);
	
	/**
	 * 根据条件获取
	 * @return
	 */
	@DataSource(DataSourceType.Slave)
	public List<UserBasis> getList(Map<String, Object> whereMap, String OrderBy, int nStart, int nLimit);
	
	/**
	 * 根据条件获取数据条数
	 * @return
	 */
	@DataSource(DataSourceType.Slave)
	public int getListCount(Map<String, Object> whereMap);
	
	/**
	 * 获取所有
	 * @return
	 */
	@DataSource(DataSourceType.Slave)
	public List<UserBasis> getList();
}