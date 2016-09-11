package cn.mayongfa.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.mayongfa.dao.UserBasisDao;
import cn.mayongfa.model.UserBasis;
import cn.mayongfa.service.UserBasisService;

@Service
public class UserBasisServiceImp implements UserBasisService {

	@Autowired
	private UserBasisDao UserBasisdao;
	
	@Override
	public int getListCount(Map<String, Object> whereMap) {
		throw new RuntimeException();
		//return UserBasisdao.getListCount(whereMap);
	}
	
	@Override
	public long Save(UserBasis entity) {
		return UserBasisdao.Save(entity);
	}

	@Override
	public Boolean Delete(long ID) {
		return UserBasisdao.Delete(ID);
	}

	@Override
	public UserBasis getEntity(long ID) {
		return UserBasisdao.getEntity(ID);
	}
	
	@Override
	public UserBasis getEntity(String phone) {
		return UserBasisdao.getEntity(phone);
	}

	@Override
	public List<UserBasis> getList(Map<String, Object> whereMap, String OrderBy, int nStart, int nLimit){
		return UserBasisdao.getList(whereMap, OrderBy, nStart, nLimit);
	}

	
	@Override
	public List<UserBasis> getList() {
		return UserBasisdao.getList();
	}

}