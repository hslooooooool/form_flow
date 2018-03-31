package cn.com.smart.context;

import java.util.List;

/**
 * 服务查询接口
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public interface ISmartContext {

	/**
	 * 根据服务名称，添加一个服务实例
	 * @param name
	 * @param obj
	 */
	public void put(String name,Object obj);
	
	/**
	 * 根据服务名称，添加一个服务类(类型)
	 * @param name
	 * @param clazz
	 */
	public void put(String name,Class<?> clazz);
	
	/**
	 * 根据给定名称判断是否存在服务
	 * @param name
	 * @return 存在返回true;否则返回false
	 */
	public boolean isExist(String name);
	
	/**
	 * 根据给定的类型查找服务实例
	 * @param clazz
	 * @return T 
	 */
	public <T> T find(Class<T> clazz);
	
	/**
	 * 根据给定的类型查找所有此类型的服务实例
	 * @param clazz
	 * @return List<T>
	 */
	public <T> List<T> finds(Class<T> clazz);
	
	/**
	 * 根据给定的服务名称、类型查找服务实例
	 * @param name
	 * @param clazz
	 * @return T
	 */
	public <T> T findByName(String name,Class<T> clazz);

	/**
	 * 根据名称获取服务实例
	 * @param name
	 * @return 返回实现类
	 */
	public Object findByName(String name);
}
