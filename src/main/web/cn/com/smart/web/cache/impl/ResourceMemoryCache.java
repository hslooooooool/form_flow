/**
 * 
 */
package cn.com.smart.web.cache.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.filter.bean.FilterParam;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.dao.IResourceDao;
import cn.com.smart.web.dao.impl.OPAuthDao;
import cn.com.smart.web.dao.impl.ResourceDao;

/**
 * 资源缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class ResourceMemoryCache implements InitCache,ICacheManagerAware,IResourceDao {

	private static final Logger log = Logger.getLogger(ResourceMemoryCache.class);
	
	public static final String RES_CACHE = "resCache";
	
	@Autowired
	private ResourceDao resDao;
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
			log.info("正在初始化[资源]数据....");
			ICache<String, List<TNResource>> resourceCaches = cacheManager.getCache(RES_CACHE);
			List<TNResource> datas = resDao.queryByField(null, " createTime asc");
			if(null != datas && datas.size()>0) {
				for (TNResource res : datas) {
					if(StringUtils.isNotEmpty(res.getOpAuths())) {
						List<TNOPAuth> auths = authDao.queryAuths(ArrayUtils.stringToArray(res.getOpAuths(), ","));
						if(null != auths && !auths.isEmpty())
							res.setAuths(auths);
					}
				}
				resourceCaches.put(RES_CACHE, datas);
			}
			datas = null;
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void refreshCache() {
		log.info("重新初始化[资源]数据....");
		initCache();
	}
	
	/**
	 * 从内存缓存中获取资源
	 */
	@Override
	public List<TNResource> queryContainAuths(FilterParam searchParam) {
		List<TNResource> lists = null;
		String name = (null == searchParam)?null:searchParam.getName();
		try {
			List<TNResource> resources = getResources();
			if(null != resources && !resources.isEmpty()) {
				lists = new ArrayList<TNResource>();
				boolean isAdd = false;
				for (TNResource res : resources) {
					isAdd = false;
					if(StringUtils.isNotEmpty(name))
						isAdd = (YesNoType.YES.getStrValue().equals(res.getState()) && res.getName().contains(name));
					else 
						isAdd = YesNoType.YES.getStrValue().equals(res.getState());
					if(isAdd)
						lists.add(res);
				}
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 通过ID 从内存缓存中获取资源
	 * @param id
	 * @return 返回资源实体对象
	 */
	public TNResource find(String id) {
		TNResource res = null;
		if(StringUtils.isEmpty(id)) {
			return res;
		}
		try {
			List<TNResource> resources = getResources();
			if(null != resources && !resources.isEmpty()) {
				for (TNResource resource : resources) {
					if(resource.getId().equals(id)) {
						res = resource;
						break;
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 通过uri获取资源
	 * @param uri 
	 * @return 返回资源实体对象
	 */
	public TNResource findByUri(String uri) {
		TNResource res = null;
		if(StringUtils.isEmpty(uri)) {
			return res;
		}
		try {
			List<TNResource> resources = getResources();
			if(null != resources && !resources.isEmpty()) {
				for (TNResource resource : resources) {
					if(resource.getUri().equals(uri)) {
						res = resource;
						break;
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		}
		return res;
	}
	
	
	/**
	 * 从内存中获取资源信息
	 * @return 返回资源实体对象集合
	 * @throws CacheException
	 */
	private List<TNResource> getResources() throws CacheException {
		ICache<String, List<TNResource>> resCaches = cacheManager.getCache(RES_CACHE);
		return resCaches.get(RES_CACHE);
	}
}
