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
import cn.com.smart.bean.DateBean;
import cn.com.smart.validate.DataFormatType;
import cn.com.smart.validate.Validate;

/**
 * 用户（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_USER")
public class TNUser extends BaseBeanImpl implements DateBean {

	public static final String PREFIX = "U";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 唯一编号（系统自动编号）
	 */
	private String id; 
	
	/**
	 * 用户名
	 */
	private String username;
	
	private String password;
	
	/**
	 * 全名（真实姓名）
	 */
	private String fullName; 
	
	private String mobileNo;
	
	private String qq;
	
	private String email;
	
	private String orgId;
	
	/**
	 * 岗位ID
	 */
	private String positionId;
	
	private String remark;
	
	
	/***
	 * 是否有效 
	 * 1--有效;
	 * 0--无效
	 */
	private String state;
	
	private Date createTime;
	
	/**
	 * 排序顺序
	 */
	private Integer sortOrder = 1;

	@Id
	@Column(name="id",length=50)
	@Validate(nullable=false,length="1,50")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="username",length=50,nullable=false,unique=true)
	@Validate(nullable=false,length="1,50")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name="password",length=32,updatable=false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="full_name",length=50)
	@Validate(nullable=false,length="1,50")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name="mobile_no",length=32)
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name="qq",length=20)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name="email",length=255)
	@Validate(dataFormatType = DataFormatType.EMAIL)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="org_id",length=32)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name="remark",length=255)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="state",length=2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="position_id",length=50)
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="sort_order")
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	@Transient
	public String getPrefix() {
		return PREFIX;
	}
}