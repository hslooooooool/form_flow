package cn.com.smart.cache.memory;

import java.util.Map;

import cn.com.smart.cache.ICache;

/**
 * 内存缓存
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <K>
 * @param <V>
 */
public class MemoryCache<K,V> implements ICache<K, V>{

	private final Map<K,V> map;
	
	public MemoryCache(Map<K,V> map) {
		this.map = map;
	}
	
	@Override
	public V get(K key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public V remove(K key) {
		return map.remove(key);
	}

	@Override
	public void clear() {
		map.clear();
	}
	
}
