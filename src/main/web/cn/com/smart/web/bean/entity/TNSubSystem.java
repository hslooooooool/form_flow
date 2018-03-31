package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 记录子系统信息的实体类
 * @author lmq <br />
 * 2016年12月23日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_sub_system")
public class TNSubSystem extends BaseBeanImpl implements DateBean {

	/**
	 * 外部系统
	 */
	public static final String SYS_TYPE_EXTERNAL = "external_sys";
	/**
	 * 内部系统
	 */
	public static final String SYS_TYPE_INTERNAL = "internal_sys";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1695437137017685946L;

	private String id;
	
	private String name;
	
	private String url;
	
	private String icon;
	
	/**
	 * 系统类型；
	 * 如：
	 * external_sys -- 外部系统;
	 * internal_sys -- 内部系统;
	 * 外部系统和内部系统的区别在于：
	 * 在访问系统时，内部系统会在url地址后面加免登陆的uri地址及参数；
	 * 外部系统不会加地址和参数
	 */
	private String sysType;
	
	private String state;
	
	private Integer sortOrder;
	
	private String userId;
	
	private Date createTime;

	@Id
	@Column(name="id", length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="name", length=255, nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="url", length=255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="icon", length=100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name="sys_type", length=127)
	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	@Column(name="state", length=2, nullable=false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="sort_order", nullable=false)
	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name="user_id", length=50, nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="create_time", nullable=false, updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
