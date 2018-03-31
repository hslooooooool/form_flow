package cn.com.smart.web.constant.enums;

/**
 * 页面打开方式
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public enum PageOpenStyle {

	/**
	 * cnoj-open-blank -- 打开一个新的弹出框
	 */
	OPEN_BLANK("cnoj-open-blank"),
	
	/**
	 * cnoj-open-tabs -- 在tab中打开
	 */
	OPEN_TAB("cnoj-open-tabs"),
	
	/**
	 * cnoj-open-slef -- 在本tab页面中打开
	 */
	OPEN_SLEF("cnoj-open-slef");
	
	private String value;
	
	private PageOpenStyle(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
