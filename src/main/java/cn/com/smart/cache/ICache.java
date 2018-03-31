package cn.com.smart.cache;


/**
 * 缓存接口
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 * @param <K>
 * @param <V>
 */
public interface ICache<K,V> {

	/**
	 * 根据key中缓存中获取对应的值
	 * @param key
	 * @return V
	 */
	public V get(K key);
	
	/**
	 * 添加到缓存
	 * @param key
	 * @param value
	 * @return V
	 */
	public V put(K key,V value);
	
	/**
	 * 从缓存中删除指定key的对象
	 * @param key
	 * @return V
	 */
	public V remove(K key);
	
	/**
	 * 清空缓存
	 */
	public void clear();
	
}
