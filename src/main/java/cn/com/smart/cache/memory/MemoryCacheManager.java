package cn.com.smart.cache.memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.com.smart.cache.CacheException;
import cn.com.smart.cache.ICache;
import cn.com.smart.cache.ICacheManager;

import com.mixsmart.utils.StringUtils;

/**
 * 内存缓存管理
 * @author lmq
 * @version 1.0
 * @since JDK大于等于1.6
 * 
 * 2015年8月22日
 */
public class MemoryCacheManager implements ICacheManager {

	private final ConcurrentMap<String, ICache<?,?>> caches;
	
	public MemoryCacheManager() {
		caches = new ConcurrentHashMap<String, ICache<?,?>>();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K,V> ICache<K, V> getCache(String name) throws CacheException {
	    if(StringUtils.isEmpty(name)) {
	    	throw new IllegalArgumentException("name不能为空！");
	    }
	    ICache cache = caches.get(name);
	    if(null == cache) {
	    	cache = new MemoryCache<Object, Object>(new ConcurrentHashMap<Object, Object>());
	    	ICache existCache = caches.putIfAbsent(name, cache);
	    	if(null != existCache) {
	    		cache = existCache;
	    	}
	    }
		return cache;
	}
	
	@Override
	public void destory() {
		if(!caches.isEmpty()) {
			caches.clear();
		}
	}
	
}
