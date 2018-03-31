package cn.com.smart.form.enums;

/**
 * 表单控制字段类型
 * @author lmq <br />
 * 2016年10月17日
 * @version 1.0
 * @since 1.0
 */
public enum FormControlFieldType {

	/**
	 * 1--mix-control-form-hide -- 表单字段隐藏或显示
	 */
	MIX_SHOW_HIDE("1","mix-showhide");
	
	private String value;
	
	private String pluginValue;
	
	private FormControlFieldType(String value, String pluginValue) {
		this.value = value;
		this.pluginValue = pluginValue;
	}

	/**
	 * 获取表单字段类型
	 * @param value
	 * @return
	 */
	public static FormControlFieldType getObjByValue(String value) {
		FormControlFieldType formControlType = null;
		for (FormControlFieldType fcft : FormControlFieldType.values()) {
			if(fcft.getValue().equals(value)) {
				formControlType = fcft;
			}
		}
		return formControlType;
	}
	
	/**
	 * 获取表单控制字段类型
	 * @param pluginValue
	 * @return
	 */
	public static FormControlFieldType getObj(String pluginValue) {
		FormControlFieldType formControlType = null;
		for (FormControlFieldType fcft : FormControlFieldType.values()) {
			if(fcft.getPluginValue().equals(pluginValue)) {
				formControlType = fcft;
			}
		}
		return formControlType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPluginValue() {
		return pluginValue;
	}

	public void setPluginValue(String pluginValue) {
		this.pluginValue = pluginValue;
	}
}
