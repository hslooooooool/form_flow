package cn.com.smart.bean;

import java.io.Serializable;

/**
 * 实体Bean基类接口
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public interface BaseBean extends Serializable {

	/**
	 * 获取主键ID
	 * @return String
	 */
	public String getId();
	
	/**
	 * 设置主键ID值
	 * @param id 
	 */
	public void setId(String id);
	
	/**
	 * 获取前缀
	 * @return String 自定义前缀
	 */
	public String getPrefix();
	
}
