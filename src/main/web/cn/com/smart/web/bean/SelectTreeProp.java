package cn.com.smart.web.bean;

/**
 * 选择树属性
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class SelectTreeProp{

	private String id;
	
	private String name;
	
	private String parentId;
	
	private String isCheck;  //是否选中 1--选中；-1--未选中

	/**
	 * 获取唯一标识符
	 * @return 唯一标识符
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置唯一标识符
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取数节点显示的名称
	 * @return 节点名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置节点名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取父ID
	 * @return 返回父ID
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置父ID
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取是否选中
	 * @return 返回0或1 <br />
	 * 1--标识选中；0--标识未选中
	 */
	public String getIsCheck() {
		return isCheck;
	}

	/**
	 * 设置是否要选中的值
	 * @param isCheck 是否选中
	 * 1--标识选中，0--标识未选中
	 */
	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
}
