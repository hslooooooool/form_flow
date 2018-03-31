package cn.com.smart.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构基础类
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class TreeProp extends BaseBeanImpl implements BaseTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4097284568878637860L;

	private String id;
	
	private String name;
	
	private String parentId;
	
	private String flag;
	
	private Integer sortOrder;
	
	private String uri;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
	/**
	 * 数组转List
	 * @param treeProps
	 * @return List
	 */
    public static List<TreeProp> array2List(TreeProp[] treeProps) {
    	List<TreeProp> lists = null;
    	if(null != treeProps && treeProps.length>0) {
    		lists = new ArrayList<TreeProp>(treeProps.length);
    		for (int i = 0; i < treeProps.length; i++) {
    			lists.add(treeProps[i]);
			}
    	}
    	treeProps = null;
    	return lists;
	}
}
