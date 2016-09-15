package cn.mayongfa.cache;

import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.mayongfa.dao.UserBasisDao;
import cn.mayongfa.model.UserBasis;
import net.rubyeye.xmemcached.exception.MemcachedException;

@Component
public class UserBasisCache extends MemcachedBasis {

	private Logger log = Logger.getLogger(UserBasisCache.class);
	@Autowired
	private UserBasisDao userBasisDao;

	/**
	 * 设置缓存
	 * 
	 * @param model
	 *            用户model
	 * @return
	 */
	public Boolean set(UserBasis model) {
		Boolean result = false;
		try {
			result = memcachedClient.set(getCacheKey(model.getId()), super.Exptime, model);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("", e);
		}
		return result;
	}

	/**
	 * 获取缓存
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public UserBasis get(long id) {
		UserBasis entity = new UserBasis();
		try {
			entity = memcachedClient.get(getCacheKey(id));
			if (entity == null || entity.getId() <= 0) {
				entity = userBasisDao.getEntity(id);
				this.set(entity);
			}
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("", e);
			entity = userBasisDao.getEntity(id);
		}
		return entity;
	}

	/**
	 * 删除缓存
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public Boolean delete(long id) {
		try {
			return memcachedClient.delete(getCacheKey(id));
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			log.error("", e);
		}
		return false;
	}

	/**
	 * 获取缓存 Key
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	private String getCacheKey(long id) {
		return super.Prefix + "UserBasis:" + id;
	}
}
