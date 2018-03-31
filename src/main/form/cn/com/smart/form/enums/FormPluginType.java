package cn.com.smart.form.enums;

import com.mixsmart.utils.StringUtils;

/**
 * 表单插件类型
 * @author lmq <br />
 * 2016年8月27日
 * @version 1.0
 * @since 1.0
 */
public enum FormPluginType {

	/**
	 * checkbox -- 复选框
	 */
	Checkbox("checkbox"),
	/**
	 * radios -- 单选框
	 */
	Radios("radios"),
	/**
	 * select -- 选择框
	 */
	Select("select"),
	/**
	 * textarea -- 文本域
	 */
	Textarea("textarea"),
	/**
	 * text -- 输入框
	 */
	Text("text"),
	/**
	 * listctrl -- 列表
	 */
	Listctrl("listctrl");

	private String value;
	
	private FormPluginType(String value) {
		this.value = value;
	}
	
	/**
	 * 根据值获取表单插件类型对象
	 * @param value
	 * @return
	 */
	public FormPluginType getObj(String value) {
		FormPluginType formPluginType = null;
		if(StringUtils.isNotEmpty(value)) {
			for (FormPluginType tmp : FormPluginType.values()) {
				if(tmp.getValue().equals(value)) {
					formPluginType = tmp;
					break;
				}
			}
		}
		return formPluginType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
