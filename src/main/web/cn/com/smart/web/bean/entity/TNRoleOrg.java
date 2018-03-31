package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.validate.Validate;

/**
 * 角色组织机构关联（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_role_org")
public class TNRoleOrg extends BaseBeanImpl {
	
	public static final String ROLE_FLAG = "role";
	public static final String ORG_FLAG = "org";

	private static final long serialVersionUID = -7509145426503527101L;

	private String id;
	
	private String roleId;
	
	private String orgId;
	
	private String flag;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Validate(nullable=false)
	@Column(name="role_id",length=50,nullable=false)
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Validate(nullable=false)
	@Column(name="org_id",length=50,nullable=false)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name="flag",length=50)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
