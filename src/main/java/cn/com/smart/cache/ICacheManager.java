package cn.com.smart.cache;


/**
 * 缓存管理器接口，该接口提供具体的cache实现
 * @author lmq
 * @version 1.0
 * @since JDK大于等于1.6
 * 
 * 2015年8月22日
 */
public interface ICacheManager {

	/**
	 * 根据名称获取缓存，如果该名称对应的缓存，不存在，则创建一个新的缓存并返回
	 * @param name
	 * @return ICache<K, V>
	 * @throws CacheException
	 */
	public <K, V> ICache<K, V> getCache(String name) throws CacheException;
	
	/**
	 * 销毁缓存
	 * @throws CacheException
	 */
	public void destory() throws CacheException;
	
}
