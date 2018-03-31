/**
 * 
 */
package cn.com.smart.web.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.dao.IOPAuthDao;
import cn.com.smart.web.dao.impl.OPAuthDao;

import com.mixsmart.utils.StringUtils;

/**
 * 操作权限缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class OPAuthMemoryCache implements InitCache,ICacheManagerAware,IOPAuthDao {

	private static final Logger log = Logger.getLogger(OPAuthMemoryCache.class);
	
	public static final String OP_AUTH_CACHE = "opAuthCache";
	
	@Autowired
	private OPAuthDao authDao;
	
	private ICacheManager cacheManager;
	
	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void initCache() {
		try {
			log.info("正在初始化[操作权限]缓存数据.....");
			ICache<String, List<TNOPAuth>> authCaches = cacheManager.getCache(OP_AUTH_CACHE);
			List<TNOPAuth> datas = authDao.findAll();
			if(null != datas && datas.size()>0) {
				authCaches.put(OP_AUTH_CACHE, datas);
			}
			datas = null;
		} catch (CacheException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void refreshCache() {
		initCache();
	}
	

	/**
	 * 从内存缓存中获取所有操作权限
	 */
	@Override
	public List<TNOPAuth> queryAll() {
		List<TNOPAuth> auths = null;
		try {
			auths = getAuths();
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return auths;
	}
	
	/**
	 * 从内存缓存中获取按条件的数据
	 */
	@Override
	public List<TNOPAuth> queryAuths(String[] values) {
		List<TNOPAuth> lists = null;
		if(null != values && values.length>0) {
			try {
				List<TNOPAuth> auths = getAuths();
				if(null != auths && !auths.isEmpty()) {
					lists = new ArrayList<TNOPAuth>();
					for (String value : values) {
						for(TNOPAuth auth : auths) {
							if(auth.getValue().equals(value)) {
								lists.add(auth);
								break;
							}
						}//for[2]
					}//for[1]
				}//if
			} catch (CacheException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	
	/**
	 * 通过ID 从内存缓存中获取数据
	 * @param id
	 * @return 返回操作权限实体对象
	 */
	public TNOPAuth find(String id) {
		TNOPAuth auth = null;
		if(StringUtils.isNotEmpty(id)) {
			try {
				List<TNOPAuth> auths = getAuths();
				if(null != auths && !auths.isEmpty()) {
					for (TNOPAuth opAuth : auths) {
						if(opAuth.getId().equals(id)) {
							auth = opAuth;
							break;
						}
					}
				}
			} catch (CacheException ex) {
				ex.printStackTrace();
			}
		}
		return auth;
	}

	/**
	 * 从内存缓存中获取数据字典数据
	 * @return 返回操作权限实体对象集合
	 * @throws CacheException
	 */
	private List<TNOPAuth> getAuths() throws CacheException {
		ICache<String, List<TNOPAuth>> authCaches = cacheManager.getCache(OP_AUTH_CACHE);
		return authCaches.get(OP_AUTH_CACHE);
	}
	
}
