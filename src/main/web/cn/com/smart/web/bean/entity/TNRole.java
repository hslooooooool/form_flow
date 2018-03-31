package cn.com.smart.web.bean.entity;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.DateBean;
import cn.com.smart.validate.Validate;


/**
 * 角色（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_ROLE")
public class TNRole implements DateBean {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String name;
	
	private String descr;//角色描述
	
	private String userId;//创建用户ID
	
	private String state;
	
	private String flag;
	
	private Date createTime;
	
	private List<TNMenu> menus;
	
	private List<TNResource> resources;
	
	
	@Id
	@Column(name = "id", length = 50)
	@Validate(nullable=false,length="1,50")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 127)
	@Validate(nullable=false,length="1,127")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "descr",length = 255)
	@Validate(length="1,255")
	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name = "user_id", length = 32,updatable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="state",length=2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="flag",length=127,updatable=false)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Override
	@Transient
	public String getPrefix() {
		return "r";
	}

	@Transient
	public List<TNMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<TNMenu> menus) {
		this.menus = menus;
	}

	@Transient
	public List<TNResource> getResources() {
		return resources;
	}

	public void setResources(List<TNResource> resources) {
		this.resources = resources;
	}

}