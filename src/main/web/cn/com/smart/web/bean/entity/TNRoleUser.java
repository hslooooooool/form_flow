package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;

/**
 * 角色与用户关联（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name = "T_N_ROLE_USER")
public class TNRoleUser extends BaseBeanImpl {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String roleId;
	private String userId;

	@Id
	@Column(name = "ID", length=50)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ROLE_ID", length = 50)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "USER_ID", length = 50)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}