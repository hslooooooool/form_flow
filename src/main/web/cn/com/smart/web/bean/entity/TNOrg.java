package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.BaseTree;
import cn.com.smart.bean.DateBean;
import cn.com.smart.validate.Validate;

/**
 * 组织机构（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_ORG")
public class TNOrg extends BaseBeanImpl implements BaseTree,DateBean {

	public static final String PREFIX = "ORG";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String name;
	
	private String code;
	
	private String parentId;
	
	/**
	 * 排序顺序
	 */
	private Integer sortOrder;
	
	private String seqParentIds;
	
	private String seqNames;
	
	
	/**
	 * 组织机构类型
	 * company -- 公司;
	 * department -- 部门
	 */
	private String type;
	
	/**
	 * 联系人
	 */
	private String contacts;
	
	/**
	 * 联系电话
	 */
	private String contactNumber;
	
	private Date createTime;
	
	@Id
	@Column(name = "id", length=50)
	@Validate(nullable=false,length="1,50")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 64)
	@Validate(nullable=false,length="1,64")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 127)
	@Validate(nullable=false,length="1,127")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "parent_id", length=32,nullable=false)
	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="sort_order",length=5)
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Column(name="type",length=50)
	@Validate(nullable=false,length="1,50")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name="seq_parent_ids",length=1000)
	public String getSeqParentIds() {
		return seqParentIds;
	}

	public void setSeqParentIds(String seqParentIds) {
		this.seqParentIds = seqParentIds;
	}

	@Column(name="seq_names",length=1000)
	public String getSeqNames() {
		return seqNames;
	}

	public void setSeqNames(String seqNames) {
		this.seqNames = seqNames;
	}
	
	@Column(name="contacts",length=50)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	@Column(name="contact_number",length=50)
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	@Transient
	public String getPrefix() {
		return PREFIX;
	}
	

}