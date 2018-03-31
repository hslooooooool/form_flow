/**
 * 
 */
package cn.com.smart.web.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.web.bean.entity.TNOPAuth;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.bean.entity.TNRoleResource;
import cn.com.smart.web.dao.IRoleResourceDao;
import cn.com.smart.web.dao.impl.RoleResourceDao;

import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 角色资源缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class RoleResourceMemoryCache  implements InitCache,ICacheManagerAware, IRoleResourceDao {

	public static final String ROLE_RESOURCE_CACHE = "roleResCache";
	private static final Logger log = Logger.getLogger(RoleResourceMemoryCache.class);
	
	@Autowired
	private RoleResourceDao roleResDao;
	@Autowired
	private OPAuthMemoryCache authCache;
	@Autowired
	private ResourceMemoryCache resCache;
	
	private ICacheManager cacheManager;

	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void initCache() {
		try {
			log.info("正在初始化[角色资源]数据....");
			ICache<String, List<TNRoleResource>> roleResCaches = cacheManager.getCache(ROLE_RESOURCE_CACHE);
			List<TNRoleResource> datas = roleResDao.findAll();
			if(null != datas && datas.size()>0) {
				roleResCaches.put(ROLE_RESOURCE_CACHE, datas);
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
	 * 从内存缓存中通过角色ID获取权限
	 */
	@Override
	public Map<String, List<TNOPAuth>> queryByRole(String roleId) {
		Map<String,List<TNOPAuth>> authMaps = null;
		if(StringUtils.isEmpty(roleId)) 
			return authMaps;
		try {
			List<TNRoleResource> roleResources = getRoleResources();
			if(null != roleResources && !roleResources.isEmpty()) {
				authMaps = new HashMap<String, List<TNOPAuth>>();
				List<TNOPAuth> auths = null;
				for (TNRoleResource roleRes : roleResources) {
					if(roleRes.getRoleId().equals(roleId)) {
						if(StringUtils.isNotEmpty(roleRes.getOpAuths())) {
							auths = authCache.queryAuths(ArrayUtils.stringToArray(roleRes.getOpAuths(),","));
						}
						authMaps.put(roleRes.getResourceId(), auths);
					}
				}
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return authMaps;
	}

	/**
	 * 从内存缓存中通过条件获取角色资源
	 */
	@Override
	public List<TNRoleResource> queryByUriRoles(String uri, List<String> roleIds) {
		List<TNRoleResource> lists = null;
		if(StringUtils.isEmpty(uri) || null == roleIds || roleIds.isEmpty()) {
			return lists;
		}
		try {
			List<TNRoleResource> roleResources = getRoleResources();
			if(null != roleResources && !roleResources.isEmpty()) {
				lists = new ArrayList<TNRoleResource>();
				TNResource res = resCache.findByUri(uri);
				if(null != res) {
					for (String roleId : roleIds) {
						for (TNRoleResource roleRes : roleResources) {
							if(roleRes.getResourceId().equals(res.getId()) && roleId.equals(roleRes.getRoleId())) {
								lists.add(roleRes);
								break;
							}
						}//for
					}//for
				}//if
			}//if
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	/**
	 * 从内存缓存中获取角色资源数据
	 * @return 获取角色资源实体对象集合
	 * @throws CacheException
	 */
	private List<TNRoleResource> getRoleResources() throws CacheException {
		ICache<String, List<TNRoleResource>> authCaches = cacheManager.getCache(ROLE_RESOURCE_CACHE);
		return authCaches.get(ROLE_RESOURCE_CACHE);
	}
	
}
