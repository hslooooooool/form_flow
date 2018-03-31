package cn.com.smart.web.sso.bean;

import cn.com.smart.web.bean.UserInfo;

/**
 * 单点登录访问返回的用户信息
 * @author lmq <br />
 * 2016年12月20日
 * @version 1.0
 * @since 1.0
 */
public class SSOUserInfo {

	private String userId;
	
	private String username;
	
	private String fullName;
	
	private String orgId;
	
	private String deptId;
	
	private String deptName;
	
	/**
	 * 岗位ID
	 */
	private String positionId;
	
	private String positionName;
	
	private String sessionId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * 设置返回给客户端的用户信息；
	 * 通过 <code>userInfo</code> 给 {@link SSOUserInfo} 类对应对象赋值；
	 * @param userInfo 存储在session中的用户信息对象
	 */
	public void convertSSOUserInfo(UserInfo userInfo) {
		if(null != userInfo) {
			this.userId = userInfo.getId();
			this.username = userInfo.getUsername();
			this.fullName = userInfo.getFullName();
			this.orgId = userInfo.getOrgId();
			this.deptId = userInfo.getDepartmentId();
			this.deptName = userInfo.getDeptName();
			this.positionId = userInfo.getPositionId();
			this.positionName = userInfo.getPositionName();
		}
	}
	
	/**
	 * 转化为 {@link UserInfo} 对象
	 * @return 返回 {@link UserInfo} 对象
	 */
	public UserInfo convertUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setDepartmentId(this.deptId);
		userInfo.setId(this.userId);
		userInfo.setDeptName(this.deptName);
		userInfo.setFullName(this.fullName);
		userInfo.setOrgId(this.orgId);
		userInfo.setUsername(this.username);
		userInfo.setPositionId(this.positionId);
		userInfo.setPositionName(this.positionName);
		return userInfo;
	}
}
