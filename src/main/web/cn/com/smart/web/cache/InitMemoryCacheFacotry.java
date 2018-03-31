package cn.com.smart.web.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import cn.com.smart.cache.ICacheManager;
import cn.com.smart.cache.ICacheManagerAware;
import cn.com.smart.cache.InitCache;
import cn.com.smart.cache.memory.MemoryCacheManager;
import cn.com.smart.service.SmartContextService;

/**
 * 初始化缓存工厂
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class InitMemoryCacheFacotry {

	private Set<InitCache> initCaches;
	
	private Set<ICacheManagerAware> settingCaches;
	
	private static InitMemoryCacheFacotry instance;
	private ICacheManager cacheManager;
	
	/**
	 * 初始化工厂数据
	 */
	private InitMemoryCacheFacotry() {
		cacheManager = new MemoryCacheManager();
		List<InitCache> caches = SmartContextService.finds(InitCache.class);
		if(null != caches && caches.size()>0) {
			initCaches = new HashSet<InitCache>();
			initCaches.addAll(caches);
			caches = null;
		}	
		List<ICacheManagerAware> setCaches = SmartContextService.finds(ICacheManagerAware.class);
		if(null != setCaches && setCaches.size()>0) {
			settingCaches = new HashSet<ICacheManagerAware>();
			settingCaches.addAll(setCaches);
		}
		//设置缓存资源
		settingCacheManager();
		//添加初始化缓存源
		initCache();
	}
	
	/**
	 * 初始化缓存
	 */
	private void initCache() {
		if(null != initCaches && initCaches.size()>0) {
			for (InitCache initCahe: initCaches) {
				initCahe.initCache();
			}
		}
	}
	
	/**
	 * 赋值
	 */
	private void settingCacheManager() {
		if(null != settingCaches && settingCaches.size()>0) {
			for (ICacheManagerAware cacheAware : settingCaches ) {
				cacheAware.setCacheManager(cacheManager);
			}
		}
	}
	
	/**
	 * 获取初始化缓存工厂实例
	 * @return 初始化缓存工厂实例
	 */
	@PostConstruct
	public synchronized static InitMemoryCacheFacotry getInstance() {
		if(null == instance) {
			instance = new InitMemoryCacheFacotry();
		}
		return instance;
	}
	
}
