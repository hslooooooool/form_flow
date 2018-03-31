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
import cn.com.smart.web.bean.entity.TNRoleMenu;
import cn.com.smart.web.dao.IRoleMenuDao;
import cn.com.smart.web.dao.impl.RoleMenuDao;

import com.mixsmart.utils.StringUtils;

/**
 * 角色菜单缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class RoleMenuMemoryCache implements InitCache,ICacheManagerAware,IRoleMenuDao {

	public static final String ROLE_MENU_CACHE = "roleMenuCache";
	private static final Logger log = Logger.getLogger(RoleMenuMemoryCache.class);
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	private ICacheManager cacheManager;

	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void initCache() {
		try {
			log.info("正在初始化[角色菜单]数据....");
			ICache<String, List<TNRoleMenu>> roleMenuCaches = cacheManager.getCache(ROLE_MENU_CACHE);
			List<TNRoleMenu> datas = roleMenuDao.findAll();
			if(null != datas && datas.size()>0) {
				roleMenuCaches.put(ROLE_MENU_CACHE, datas);
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
	 */
	@Override
	public List<TNRoleMenu> queryByRole(String roleId) {
		List<TNRoleMenu> lists = null;
		if(StringUtils.isEmpty(roleId)) {
			return lists;
		}
		lists = new ArrayList<TNRoleMenu>();
		try {
			List<TNRoleMenu> roleMenus = getRoleMenus();
			if(null != roleMenus && !roleMenus.isEmpty()) {
				for (TNRoleMenu roleMenu : roleMenus) {
					if(roleMenu.getRoleId().equals(roleId)) {
						lists.add(roleMenu);
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists.isEmpty()?null:lists;
	}
	
	/**
	 * 从内存缓存中获取数据
	 * @param roleIds
	 * @return 获取角色菜单实体对象集合
	 */
	public List<TNRoleMenu> queryByRole(List<String> roleIds) {
		List<TNRoleMenu> lists = null;
		if(null == roleIds || roleIds.isEmpty()) {
			return lists;
		}
		lists = new ArrayList<TNRoleMenu>();
		try {
			List<TNRoleMenu> roleMenus = getRoleMenus();
			if(null != roleMenus && !roleMenus.isEmpty()) {
				for (String roleId : roleIds) {
					for (TNRoleMenu roleMenu : roleMenus) {
						if(roleMenu.getRoleId().equals(roleId)) {
							lists.add(roleMenu);
						}
					}
				}
			}
		} catch(CacheException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists.isEmpty()?null:lists;
	}

	/**
	 * 从内存缓存中判断数据是否已关联
	 */
	@Override
	public boolean isRoleMenuCombinExist(String roleId, String menuId) {
		boolean is = false;
		if(StringUtils.isEmpty(roleId) || StringUtils.isEmpty(menuId)) {
			return is;
		}
		try {
			List<TNRoleMenu> roleMenus = getRoleMenus();
			if(null != roleMenus && !roleMenus.isEmpty()) {
				for (TNRoleMenu roleMenu : roleMenus) {
					if(roleMenu.getRoleId().equals(roleId) && 
							roleMenu.getMenuId().equals(menuId)) {
						is = true;
						break;
					}
				}
			}
		} catch (CacheException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 从内存缓存中获取角色菜单数据
	 * @return 获取角色菜单实体对象集合
	 * @throws CacheException
	 */
	private List<TNRoleMenu> getRoleMenus() throws CacheException {
		ICache<String, List<TNRoleMenu>> authCaches = cacheManager.getCache(ROLE_MENU_CACHE);
		return authCaches.get(ROLE_MENU_CACHE);
	}
}


