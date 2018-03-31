package cn.com.smart.flow.sort;

import java.util.List;


/**
 * 分类排序接口
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年12月21日
 * @param <E>
 */
public interface ISortClassify<E> {

	/**
	 * 获取
	 * @param key
	 * @return
	 */
	public E get(String key);
	
	/**
	 * 放入
	 * @param key
	 * @param entity
	 */
	public void put(String key,E entity);
	
	/**
	 * 获取放入列表
	 * @return
	 */
	public List<SortClassifyBean<E>> getList();
	
	/**
	 * 销毁
	 */
	public void destory();
	
}
