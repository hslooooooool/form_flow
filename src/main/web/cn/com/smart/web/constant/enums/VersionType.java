package cn.com.smart.web.constant.enums;

/**
 * 客户端类型
 * @author lmq <br />
 * 2016年9月15日
 * @version 1.0
 * @since 1.0
 */
public enum VersionType {

	/**
	 * PC端
	 */
	PC(0,"pc"),
	/**
	 * Android端
	 */
	CLIENT_ANDROID(1,"android"),
	/**
	 * IOS端
	 */
	CLIENT_IOS(2,"ios");
	
	private int index;
	
	private String value;
	
	private VersionType(int index, String value) {
		this.index = index;
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
