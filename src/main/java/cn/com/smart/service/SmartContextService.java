/**
 * 
 */
package cn.com.smart.service;

import java.util.List;

import cn.com.smart.context.ISmartContext;

/**
 * 框架上下文服务类
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public class SmartContextService {

	private static ISmartContext context;

	/**
	 * 框架上下文服务类
	 * @return 返回服务对象
	 */
	public static ISmartContext getContext() {
		return context;
	}

	/**
	 * 
	 * @param context
	 */
	public static void setContext(ISmartContext context) {
		SmartContextService.context = context;
	}
	
	/**
	 * 根据服务名称，添加一个服务实例
	 * @param name
	 * @param obj
	 */
	public static void put(String name, Object obj) {
		context.put(name, obj);
	}

	/**
	 * 根据服务名称，添加一个服务类(类型)
	 * @param name
	 * @param clazz
	 */
	public static void put(String name, Class<?> clazz) {
		context.put(name, clazz);
	}

	/**
	 * 根据给定名称判断是否存在服务
	 * @param name
	 * @return 存在返回true;否则返回false
	 */
	public static boolean isExist(String name) {
		return context.isExist(name);
	}

	/**
	 * 根据给定的类型查找服务实例
	 * @param clazz
	 * @return T
	 */
	public static <T> T find(Class<T> clazz) {
		return context.find(clazz);
	}

	/**
	 * 根据给定的类型查找所有此类型的服务实例
	 * @param clazz
	 * @return List<T>
	 */
	public static <T> List<T> finds(Class<T> clazz) {
		return context.finds(clazz);
	}

	/**
	 * 根据给定的服务名称、类型查找服务实例
	 * @param name
	 * @param clazz
	 * @return T
	 */
	public static <T> T findByName(String name, Class<T> clazz) {
		return context.findByName(name, clazz);
	}

	/**
	 * 根据名称获取实例对象
	 * @param name
	 * @return
	 */
	public static Object findByName(String name) {
		return context.findByName(name);
	}
}
