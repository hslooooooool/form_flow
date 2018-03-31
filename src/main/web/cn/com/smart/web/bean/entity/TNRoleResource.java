package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.validate.Validate;

/**
 * 角色资源映射操作权限（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name = "t_n_role_resource")
public class TNRoleResource extends BaseBeanImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String resourceId;
	private String roleId;
	private String opAuths;
	
	@Id
	@Column(name = "id",length=50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Validate(nullable=false)
	@Column(name="resource_id",length=50,nullable=false)
	public String getResourceId() {
		return resourceId;
	}
	
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Column(name="op_auths",length=255)
	public String getOpAuths() {
		return opAuths;
	}
	public void setOpAuths(String opAuths) {
		this.opAuths = opAuths;
	}
	
	@Validate(nullable=false)
	@Column(name="role_id",length=50,nullable=false)
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
