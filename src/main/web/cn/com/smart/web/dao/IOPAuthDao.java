/**
 * 
 */
package cn.com.smart.web.dao;

import java.util.List;

import cn.com.smart.web.bean.entity.TNOPAuth;

/**
 * 操作权限Dao接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface IOPAuthDao {
	
	/**
	 * 获取所有操作权限信息
	 * @return 返回数组对象集合
	 */
	public List<TNOPAuth> queryAll();
	
	
	/**
	 * 通过值获取对应的操作权限信息
	 * @param values 权限值数组
	 * @return 返回操作权限实体对象集合
	 */
	public List<TNOPAuth> queryAuths(String[] values);
}
