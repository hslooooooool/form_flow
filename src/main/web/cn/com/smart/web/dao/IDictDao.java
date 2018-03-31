/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;

import cn.com.smart.web.bean.entity.TNDict;

/**
 * 数据字典Dao接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IDictDao {

	
	/**
	 * 通过业务值获取数据项
	 * @param busiValue
	 * @return 返回实体对象集合
	 */
	public List<TNDict> getItems(String busiValue);
	
	
	/**
	 * 通过业务值获取数据项
	 * @param busiValue 业务值
	 * @param name 支持按名称搜索
	 * @return 返回值为数组List集合
	 */
	public List<Object> getItems(String busiValue,String name);
	
	
	/**
	 * 通过ID获取其子数据项
	 * @param id 数据字典ID
	 * @param name 通过名称搜索
	 * @return 返回数组对象集合
	 */
	public List<Object> getItemById(String id,String name);
	
	
	/**
	 * 获取所有数据
	 * @return 返回数组对象集合
	 */
	public List<Object> queryObjAll();
	
}
