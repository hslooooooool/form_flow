package cn.com.smart.web.cache.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.bean.entity.TNMenu;
import cn.com.smart.web.bean.entity.TNResource;
import cn.com.smart.web.bean.entity.TNRoleMenu;
import cn.com.smart.web.dao.IMenuDao;
import cn.com.smart.web.dao.impl.MenuDao;
import cn.com.smart.web.dao.impl.ResourceDao;

/**
 * 初始化菜单缓存
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Component
public class MenuMemoryCache implements ICacheManagerAware, InitCache, IMenuDao {

	public static final String MENU_CACHE = "menuCache";
	private static final Logger log = Logger.getLogger(MenuMemoryCache.class);
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private DictMemoryCache dictCache;
	@Autowired
	private RoleMenuMemoryCache roleMenuCache;
	
	private ICacheManager cacheManager;

	@Override
	public void setCacheManager(ICacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	@Override
	public void initCache() {
		try {
			log.info("正在初始化菜单缓存数据.....");
			ICache<String, List<TNMenu>> menuCaches = cacheManager.getCache(MENU_CACHE);
			List<TNMenu> datas = menuDao.queryByField(null, " sortOrder ASC");
			if(null != datas && datas.size()>0) {
				TNResource resource = null;
				for (TNMenu menu : datas) {
					if(StringUtils.isNotEmpty(menu.getResourceId())) {
						resource = resourceDao.find(menu.getResourceId());
						if (null != resource) {
							menu.setResource(resource);
						}
					}//if
				}//for
				resource = null;
				menuCaches.put(MENU_CACHE, datas);
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
	 * 获取所有菜单数据
	 * @return 菜单实体对象集合
	 */
	public List<TNMenu> findAll() {
		List<TNMenu> menus = null;
		try {
			menus = getMenus();
		} catch (CacheException ex) {
			ex.printStackTrace();
		}
		return menus;
	}
	
	@Override
	public List<Object> queryObjAll() {
		List<Object> lists = null;
		try {
			List<TNMenu> menus = getMenus();
			List<TNDict> states = dictCache.getItems("DATA_STATE");
			if(null != menus && !menus.isEmpty() && 
					null != states && !states.isEmpty()) {
				lists = new ArrayList<Object>();
				Object[] datas = null;
				for (TNMenu menu : menus) {
					datas = new Object[9];
					datas[0] = menu.getId();
					datas[1] = menu.getParentId();
					datas[2] = menu.getSortOrder();
					datas[3] = menu.getId();
					datas[4] = menu.getName();
					if(null != menu.getResource()) {
						datas[5] = menu.getResource().getName();
					}
					datas[6] = "";
					for (TNDict state : states) {
						if(menu.getState().equals(state.getBusiValue())) {
							datas[6] = state.getBusiName();
							break;
						}
					}
					datas[7] = menu.getSortOrder();
					datas[8] = menu.getIcon();
					lists.add(datas);
				}
				datas = null;
			}
			menus = null;
			states = null;
		} catch (CacheException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lists;
	}


	@Override
	public List<TNMenu> getValidAll() {
		List<TNMenu> lists = new ArrayList<TNMenu>();
		try {
			List<TNMenu> menus = getMenus();
			if(null != menus && menus.size()>0) {
				for (TNMenu menu : menus) {
					if(YesNoType.YES.getStrValue().equals(menu.getState())) 
						lists.add(menu);
				}//for
			}
		} catch (CacheException ex) {
			ex.printStackTrace();
		}
		return lists.isEmpty()?null:lists;
	}


	/**
	 * 从内存缓存中获取数据
	 */
	@Override
	public List<TNMenu> queryMenus(List<String> roleIds) {
		List<TNMenu> lists = null;
		try {
			List<TNMenu> menus = getMenus();
			if(null != menus && !menus.isEmpty()) {
				List<TNRoleMenu> roleMenus = roleMenuCache.queryByRole(roleIds);
				List<String> menuIds = duplicateRemoval(roleMenus);
				if(null != menuIds) {
					lists = new ArrayList<TNMenu>();
					for (String menuId : menuIds) {
						for (TNMenu menu : menus) {
							if(menuId.equals(menu.getId()) && YesNoType.YES.getStrValue().equals(menu.getState())) {
								lists.add(menu);
								break;
							}
						}//for
					}//for
				}
				//排序
				Collections.sort(lists, new Comparator<TNMenu>() {
					@Override
					public int compare(TNMenu o1, TNMenu o2) {
						return o1.getSortOrder().compareTo(o2.getSortOrder());
					}
					
				});
			}//if
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 从内存缓存中获取数据
	 */
	@Override
	public List<Object> queryMenuIdByResourceId(String[] resourceIds) {
		List<Object> lists = null;
		if(null == resourceIds || resourceIds.length<1)
			return lists;
		try {
			List<TNMenu> menus = getMenus();
			if(null != menus && !menus.isEmpty()) {
				lists = new ArrayList<Object>();
				for (String resourceId : resourceIds) {
					for (TNMenu menu : menus) {
						if(resourceId.equals(menu.getResourceId())) {
							lists.add(menu.getId());
							break;
						}
					}
				}
			}
		} catch (CacheException e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	
	/**
	 * 去重，去掉角色菜单集合中，重复的数据
	 * @param roleMenus
	 * @return 返回去重之后的菜单ID集合
	 */
	private List<String> duplicateRemoval(List<TNRoleMenu> roleMenus) {
		List<String> menuIds = new ArrayList<String>();
		for (TNRoleMenu roleMenu : roleMenus) {
			if(!menuIds.contains(roleMenu.getMenuId())) {
				menuIds.add(roleMenu.getMenuId());
			}
		}
		return menuIds.isEmpty()?null:menuIds;
	}

	
	/**
	 * 从内存中获取菜单信息
	 * @return 返回菜单实体对象集合
	 * @throws CacheException
	 */
	private List<TNMenu> getMenus() throws CacheException {
		ICache<String, List<TNMenu>> menuCaches = cacheManager.getCache(MENU_CACHE);
		return menuCaches.get(MENU_CACHE);
	}
}
