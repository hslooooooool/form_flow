package cn.com.smart.web.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息，用于在HttpSession中保存用户信息
 * @author lmq
 * @version 1.0
 * @since 1.0
 *
 */
public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5583539878283875891L;

	private String id;
	
	private String loginId;
	
	private String username;
	
	private String fullName;
	
	private String orgId;
	
	/**
	 * 部门ID
	 */
	private String departmentId;
	
	private String deptName;
	
	private String seqDeptNames;
	
	/**
	 * 岗位ID
	 */
	private String positionId;
	
	private String positionName;
	
	private List<String> menuRoleIds;
	
	private List<String> roleIds;
	
	private List<String> orgIds;
	
	/*private String email;
	
	private String tel;
	
	private String sex;
	
	private String city;*/
	
	private String anonymous;
	
	private String avatar;

	/**
	 * 获取用户ID
	 * @return 用户ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置用户ID
	 * @param id 登陆系统时，系统自动设置用户ID值
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * @return 返回用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 用户匿名（预留属性）
	 * @return 返回用户匿名；现在默认返回null
	 */
	public String getAnonymous() {
		return anonymous;
	}

	/**
	 * 设置匿名（预留属性）
	 * @param anonymous
	 */
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	/**
	 * 设置用户头像（预留属性）
	 * @return 返回用户的头像图标的名称
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * 设置用户头像 （预留属性）
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 获取用户所属组织机构的ID
	 * @return
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 设置用户所属的组织机构ID
	 * @param orgId
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 获取用户所拥有的角色ID集合
	 * @return 返回用户拥有角色的集合 <br />
	 * 该角色ID主要是用户菜单权限的控制，查询菜单时使用该条件过滤
	 */
	public List<String> getRoleIds() {
		return roleIds;
	}

	/**
	 * 设置用户所用有的角色ID
	 * @param roleIds
	 */
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * 获取用户的部门ID
	 * @return 部门ID <br />
	 * 当用户直接隶属组织机构时，则返回null
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * 设置用户的部门ID
	 * @param departmentId
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * 组织机构ID集合
	 * @return 返回组织机构ID集合 <br />
	 * 该集合中包含了部门ID，主要是用于数据权限的控制。<br />
	 * 该属性取决了，用户能看哪些数据。
	 */
	public List<String> getOrgIds() {
		return orgIds;
	}

	/**
	 * 设置组织机构ID集合,既设置数据权限
	 * @param orgIds
	 */
	public void setOrgIds(List<String> orgIds) {
		this.orgIds = orgIds;
	}

	/**
	 * 用户的全名（真实姓名）
	 * @return 用户姓名
	 */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * 设置用户的全名
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 获取用户所属部门的名称
	 * @return 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置用户所属部门的名称
	 * @param deptName
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取用户所属拥有的菜单角色ID集合
	 * @return 菜单角色ID集合
	 */
	public List<String> getMenuRoleIds() {
		return menuRoleIds;
	}

	/**
	 * 设置菜单角色ID集合
	 * @param menuRoleIds
	 */
	public void setMenuRoleIds(List<String> menuRoleIds) {
		this.menuRoleIds = menuRoleIds;
	}

	/**
	 * 获取用户的职位ID
	 * @return 职位ID
	 */ 
	public String getPositionId() {
		return positionId;
	}

	/**
	 * 设置用户的职位ID
	 * @param positionId
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * 获取用户的职位名称
	 * @return 职位名称
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * 设置用户职位名称
	 * @param positionName
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getSeqDeptNames() {
		return seqDeptNames;
	}

	public void setSeqDeptNames(String seqDeptNames) {
		this.seqDeptNames = seqDeptNames;
	}
	
	
}
