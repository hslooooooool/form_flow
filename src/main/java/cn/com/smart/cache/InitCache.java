package cn.com.smart.cache;

/**
 * 初始化缓存接口 <br />
 * 启动服务时，实现了该接口的类，会初始化数据到内存
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
public interface InitCache {

	/**
	 * 初始化缓存
	 */
	public void initCache();
	
	/**
	 * 刷新缓存(既重新初始化缓存)
	 */
	public void refreshCache();
	
}
