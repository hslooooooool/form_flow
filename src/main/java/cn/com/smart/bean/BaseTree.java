package cn.com.smart.bean;

/**
 * 树形接口类
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public interface BaseTree extends BaseBean {

	/**
	 * 获取父ID
	 * @return 返回父ID
	 */
	public String getParentId();

	/**
	 * 设置父ID
	 * @param parentId
	 */
	public void setParentId(String parentId);

	/**
	 * 获取序号
	 * @return Integer
	 */
	public Integer getSortOrder();

	/**
	 * 设置序号
	 * @param sortOrder
	 */
	public void setSortOrder(Integer sortOrder);
	
}
