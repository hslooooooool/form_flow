package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseTree;
import cn.com.smart.validate.Validate;

/**
 * 数据字典（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_DICT")
public class TNDict implements BaseTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String parentId;
	
	private String busiName;
	
	private String busiValue;
	
	private Integer busiLevel;
	
	private Integer sortOrder;
	
	private String state;

	@Id
	@Column(name="id",length=50)
	@Validate(nullable=false,length="1,50")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "parent_id", length = 50,nullable=false)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "busi_name", length = 255,nullable=false)
	@Validate(nullable=false,length="1,255")
	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	@Column(name = "busi_level")
	public Integer getBusiLevel() {
		return this.busiLevel;
	}

	public void setBusiLevel(Integer busiLevel) {
		this.busiLevel = busiLevel;
	}

	@Column(name = "sort_order",length=5)
	public Integer getSortOrder() {
		return this.sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name = "state", length=2)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="busi_value",length=255,nullable=false)
	@Validate(nullable=false,length="1,255")
	public String getBusiValue() {
		return busiValue;
	}

	public void setBusiValue(String busiValue) {
		this.busiValue = busiValue;
	}

	@Override
	@Transient
	public String getPrefix() {
		return "d";
	}

}