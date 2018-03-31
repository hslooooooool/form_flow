package cn.com.smart.web.constant.enums;

import com.mixsmart.utils.StringUtils;

/**
 * 登录类型
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public enum LoginType {

	/**
	 * normal -- 正常登录
	 */
	NORMAL("normal"),
	/**
	 * single -- 单点登录
	 */
	SINGLE("single");
	
	private String name;
	
	private LoginType(String name) {
		this.name = name;
	}
	
	public static LoginType getObj(String name) {
		LoginType obj = null;
		if(StringUtils.isEmpty(name)) {
			return obj;
		}
		for(LoginType loginType : LoginType.values()) {
			if(loginType.getName().equals(name)) {
				obj = loginType;
				break;
			}
		}
		return obj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
