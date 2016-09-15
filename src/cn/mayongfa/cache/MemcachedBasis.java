package cn.mayongfa.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.rubyeye.xmemcached.MemcachedClient;

@Component
public class MemcachedBasis {

	@Autowired
	protected MemcachedClient memcachedClient;
	
	/**
	 * 失效时间（秒）3600*24 一天
	 */
	protected int Exptime = 3600 * 24;
	
	/**
	 * 基础数据失效时间（秒）3600*24*7 一周
	 */
	protected int DataExptime = this.Exptime * 7;
	
	protected String Prefix = "SPRINGDEMO:";
}
