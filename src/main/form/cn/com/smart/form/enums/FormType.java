package cn.com.smart.form.enums;

import com.mixsmart.utils.StringUtils;

/**
 * 表单类型
 * @author lmq  2017年4月25日
 * @version 1.0
 * @since 1.0
 */
public enum FormType {

	/**
	 * flow_form -- 流程表单
	 */
	FLOW_FORM("flow_form", "process/attachment/upload"),
	/**
	 * normal_form -- 普通表单
	 */
	NORMAL_FORM("normal_form","");
	
	private String value;
	
	private String uri;
	
	private FormType(String value, String uri) {
		this.value = value;
		this.uri = uri;
	}

	/**
	 * 通过值获取表单类型对象
	 * @param value
	 * @return
	 */
	public static FormType getObj(String value) {
		FormType type = null;
		if(StringUtils.isEmpty(value)) {
			return type;
		}
		for (FormType formType : FormType.values()) {
			if(formType.getValue().equals(value)) {
				type = formType;
				break;
			}
		}
		return type;
	}
	
	/****** getter and setter *****/
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
