package cn.mayongfa.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mayongfa.cache.UserBasisCache;
import cn.mayongfa.dao.UserBasisDao;
import cn.mayongfa.model.UserBasis;
import cn.mayongfa.service.UserBasisService;

@Service
public class UserBasisServiceImp implements UserBasisService {

	@Autowired
	private UserBasisDao UserBasisdao;
	@Autowired
	private UserBasisCache UserBasiscache;

	@Override
	public long Save(UserBasis entity) {
		long id = UserBasisdao.Save(entity);
		if (id > 0) {
			UserBasiscache.set(entity);
		}
		return id;
	}

	@Override
	public Boolean Delete(long ID) {
		boolean result = UserBasisdao.Delete(ID);
		if (result) {
			UserBasiscache.delete(ID);
		}
		return result;
	}

	@Override
	public UserBasis getEntity(long ID) {
		return UserBasiscache.get(ID);
	}

	@Override
	public UserBasis getEntity(String phone) {
		return UserBasisdao.getEntity(phone);
	}

	@Override
	public int getListCount(Map<String, Object> whereMap) {
		throw new RuntimeException();
		// return UserBasisdao.getListCount(whereMap);
	}
	
	@Override
	public List<UserBasis> getList(Map<String, Object> whereMap, String OrderBy, int nStart, int nLimit) {
		return UserBasisdao.getList(whereMap, OrderBy, nStart, nLimit);
	}

	@Override
	public List<UserBasis> getList() {
		return UserBasisdao.getList();
	}

}