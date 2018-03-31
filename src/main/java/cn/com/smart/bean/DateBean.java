package cn.com.smart.bean;

import java.util.Date;

/**
 * 含有日期时间字段的实体Bean基类接口
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface DateBean extends BaseBean {

	/**
	 * 获取创建时间
	 * @return Date
	 */
	public Date getCreateTime();
	
	/**
	 * 设置创建时间
	 * @param creatTime
	 */
	public void setCreateTime(Date creatTime);
	
}
