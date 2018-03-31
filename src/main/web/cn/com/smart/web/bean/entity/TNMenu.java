package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseTree;
import cn.com.smart.validate.Validate;

/**
 * 菜单（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_MENU")
public class TNMenu implements BaseTree {

	private static final long serialVersionUID = -516084667117152167L;
	private String id;
	
	private String name;
	
	private String parentId;
	
	private String resourceId;
	
	private Integer sortOrder;
	
	private String state;
	
	private String icon;
	
	private TNResource resource;
	
	@Id
	@Column(name="id",length=50)
	@Validate(nullable=false,length="1,50")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name", length = 255)
	@Validate(nullable=false,length="1,255")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "parent_id", length=50,nullable=false)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name="resource_id",length=50)
	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name="sort_order",length=3)
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name="state",length=2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="icon",length=255)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	@Transient
	public String getPrefix() {
		return "m";
	}

	@Transient
	public TNResource getResource() {
		return resource;
	}

	public void setResource(TNResource resource) {
		this.resource = resource;
	}

}