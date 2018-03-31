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
import cn.com.smart.web.bean.entity.TNOrg;
import cn.com.smart.web.dao.impl.OrgDao;

import com.mixsmart.utils.StringUtils;

/**
 * 组织机构缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class OrgMemoryCache implements InitCache,ICacheManagerAware {

    private static final Logger log = Logger.getLogger(OrgMemoryCache.class);
	
	public static final String ORG_CACHE = "orgCache";
	
	@Autowired
	private OrgDao orgDao;
	
	private ICacheManager cacheManager;
	

	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void initCache() {
		try {
			log.info("正在初始化[组织机构]缓存数据.....");
			ICache<String, List<TNOrg>> orgCaches = cacheManager.getCache(ORG_CACHE);
			List<TNOrg> datas = orgDao.findAll();
			if(null != datas && datas.size()>0) {
				orgCaches.put(ORG_CACHE, datas);
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
	 * 从内存缓存中获取数据
	 * @param id
	 * @return 返回组织机构实体对象
	 */
	public TNOrg find(String id) {
		TNOrg org = null;
		if(StringUtils.isEmpty(id))
			return org;
		try {
			List<TNOrg> orgs = getOrgs();
			if(null != orgs && !orgs.isEmpty()) {
				for (TNOrg o : orgs) {
					if(o.getId().equals(id)) {
						org = o;
						break;
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return org;
	}
	
	/**
	 * 从内存缓存中获取数据
	 * @param id
	 * @return 返回组织机构实体对象集合
	 */
	public List<TNOrg> find(String[] ids) {
		List<TNOrg> lists = null;
		if(null == ids || ids.length<1)
			return lists;
		try {
			List<TNOrg> orgs = getOrgs();
			if(null != orgs && !orgs.isEmpty()) {
				lists = new ArrayList<TNOrg>();
				for (String id : ids) {
					for (TNOrg o : orgs) {
						if(o.getId().equals(id)) {
							lists.add(o);
							break;
						}
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return lists;
	}
	
	/**
	 * 从内存缓存中获取所有组织机构数据
	 * @return 返回组织机构实体对象集合
	 */
	public List<TNOrg> findAll() {
		try {
			return getOrgs();
		} catch (CacheException ex) {
			return null;
		}
	}
	
	
	/**
	 * 从内存中获取组织机构信息
	 * @return 返回组织机构对象集合
	 * @throws CacheException
	 */
	private List<TNOrg> getOrgs() throws CacheException {
		ICache<String, List<TNOrg>> orgCaches = cacheManager.getCache(ORG_CACHE);
		return orgCaches.get(ORG_CACHE);
	}

}
