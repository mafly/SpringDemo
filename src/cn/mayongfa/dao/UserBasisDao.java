package cn.mayongfa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import cn.mayongfa.common.DateUtil;
import cn.mayongfa.common.IdWorkerManage;
import cn.mayongfa.common.StrUtil;
import cn.mayongfa.model.UserBasis;

@Repository
public class UserBasisDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存
	 * 
	 * @param entity
	 *            ID=0 新增
	 * @return
	 */
	public long Save(UserBasis entity) {
		long ID = 0;
		try {
			if (entity.getId() == StrUtil.DEFAULT_NULL) {
				// 新增
				entity.setId(IdWorkerManage.getId());
				String strSql = "insert into userbasis (id,name,password,phone,status,createtime,updatetime) values (?,?,?,?,?,?,?)";

				Object obj[] = { entity.getId(), entity.getName(), entity.getPassword(), entity.getPhone(),
						entity.getStatus(), entity.getCreatetime(), entity.getUpdatetime() };
				int ret = jdbcTemplate.update(strSql, obj);
				if (ret > 0) {
					ID = entity.getId();
				}
			} else {
				// 修改
				String strSql = "update userbasis set id=?,name=?,password=?,phone=?,status=?,updatetime=? where id=?";
				Object obj[] = { entity.getId(), entity.getName(), entity.getPassword(), entity.getPhone(),
						entity.getStatus(), entity.getUpdatetime(), entity.getId() };
				int ret = jdbcTemplate.update(strSql, obj);
				if (ret > 0) {
					ID = entity.getId();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return ID;
	}

	/**
	 * 删除
	 * 
	 * @param ID
	 * @return
	 */
	public Boolean Delete(long ID) {
		String strSql = "delete from userbasis where id=?";
		Object obj[] = { DateUtil.dateToLong(), ID };
		int ret = jdbcTemplate.update(strSql, obj);
		return ret > 0;
	}

	/**
	 * 获取
	 * 
	 * @param ID
	 * @return
	 */
	public UserBasis getEntity(long ID) {
		final UserBasis entity = new UserBasis();
		String strSql = "select id,name,password,phone,status,createtime,updatetime from userbasis where id=?";
		jdbcTemplate.query(strSql, new Object[] { ID }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				entity.setId(rs.getLong("id"));
				entity.setName(rs.getString("name"));
				entity.setPassword(rs.getString("password"));
				entity.setPhone(rs.getString("phone"));
				entity.setStatus(rs.getInt("status"));
				entity.setCreatetime(rs.getDate("createtime"));
				entity.setUpdatetime(rs.getDate("updatetime"));
			}
		});
		return entity;
	}

	/**
	 * 获取
	 * 
	 * @param ID
	 * @return
	 */
	public UserBasis getEntity(String phone) {
		final UserBasis entity = new UserBasis();
		String strSql = "select id,name,password,phone,status,createtime,updatetime from userbasis where phone=?";
		jdbcTemplate.query(strSql, new Object[] { phone }, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				entity.setId(rs.getLong("id"));
				entity.setName(rs.getString("name"));
				entity.setPassword(rs.getString("password"));
				entity.setPhone(rs.getString("phone"));
				entity.setStatus(rs.getInt("status"));
				entity.setCreatetime(rs.getDate("createtime"));
				entity.setUpdatetime(rs.getDate("updatetime"));
			}
		});
		return entity;
	}

	/**
	 * 根据条件获取
	 * 
	 * @return
	 */
	public List<UserBasis> getList(Map<String, Object> whereMap, String OrderBy, int nStart, int nLimit) {
		final ArrayList<UserBasis> list = new ArrayList<UserBasis>();
		String basisSql = "select id,name,password,phone,status,createtime,updatetime from userbasis ";
		String strSql = "select id from userbasis where 1=1";
		StringBuffer whereSql = new StringBuffer();
		List<Object> whereobj = new ArrayList<Object>();
		if (whereMap.containsKey("id")) {
			long id = (long) whereMap.get("id");
			whereSql.append(" and id=?");
			whereobj.add(id);
		}
		if (whereMap.containsKey("name")) {
			String name = (String) whereMap.get("name");
			whereSql.append(" and name=?");
			whereobj.add(name);
		}
		if (whereMap.containsKey("password")) {
			String password = (String) whereMap.get("password");
			whereSql.append(" and password=?");
			whereobj.add(password);
		}
		if (whereMap.containsKey("phone")) {
			String phone = (String) whereMap.get("phone");
			whereSql.append(" and phone=?");
			whereobj.add(phone);
		}
		if (whereMap.containsKey("status")) {
			int status = (int) whereMap.get("status");
			whereSql.append(" and status=?");
			whereobj.add(status);
		}
		if (OrderBy == "")
			OrderBy = "createtime asc";
		whereSql.append(" order by " + OrderBy + " limit " + nStart + "," + nLimit);
		strSql = strSql + whereSql;
		Object obj[] = whereobj.toArray(new Object[whereobj.size()]);
		basisSql += " INNER JOIN (" + strSql + ") userbasis_id USING (id)";
		jdbcTemplate.query(basisSql, obj, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				UserBasis entity = new UserBasis();
				entity.setId(rs.getLong("id"));
				entity.setName(rs.getString("name"));
				entity.setPassword(rs.getString("password"));
				entity.setPhone(rs.getString("phone"));
				entity.setStatus(rs.getInt("status"));
				entity.setCreatetime(rs.getDate("createtime"));
				entity.setUpdatetime(rs.getDate("updatetime"));
				list.add(entity);
			}
		});
		return list;
	}

	/**
	 * 根据条件获取数据条数
	 * 
	 * @return
	 */
	public int getListCount(Map<String, Object> whereMap) {
		String strSql = "select count(id) from userbasis where 1=1";
		StringBuffer whereSql = new StringBuffer();
		List<Object> whereobj = new ArrayList<Object>();
		if (whereMap.containsKey("id")) {
			long id = (long) whereMap.get("id");
			whereSql.append(" and id=?");
			whereobj.add(id);
		}
		if (whereMap.containsKey("name")) {
			String name = (String) whereMap.get("name");
			whereSql.append(" and name=?");
			whereobj.add(name);
		}
		if (whereMap.containsKey("password")) {
			String password = (String) whereMap.get("password");
			whereSql.append(" and password=?");
			whereobj.add(password);
		}
		if (whereMap.containsKey("phone")) {
			String phone = (String) whereMap.get("phone");
			whereSql.append(" and phone=?");
			whereobj.add(phone);
		}
		if (whereMap.containsKey("status")) {
			int status = (int) whereMap.get("status");
			whereSql.append(" and status=?");
			whereobj.add(status);
		}
		strSql = strSql + whereSql;
		Object obj[] = whereobj.toArray(new Object[whereobj.size()]);
		return jdbcTemplate.queryForObject(strSql, obj, Integer.class);
	}

	/**
	 * 获取所有
	 * 
	 * @return
	 */
	public List<UserBasis> getList() {
		final ArrayList<UserBasis> list = new ArrayList<UserBasis>();
		String strSql = "select id,name,password,phone,status,createtime,updatetime from userbasis ";
		jdbcTemplate.query(strSql, new Object[] {}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				UserBasis entity = new UserBasis();
				entity.setId(rs.getLong("id"));
				entity.setName(rs.getString("name"));
				entity.setPassword(rs.getString("password"));
				entity.setPhone(rs.getString("phone"));
				entity.setStatus(rs.getInt("status"));
				entity.setCreatetime(rs.getDate("createtime"));
				entity.setUpdatetime(rs.getDate("updatetime"));
				list.add(entity);
			}
		});
		return list;
	}

}
