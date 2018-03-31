package cn.com.smart.form.enums;

/**
 * 表单数据源类型
 * @author lmq <br />
 * 2016年9月30日
 * @version 1.0
 * @since 1.0
 */
public enum FormDataSourceType {

	/**
	 * dict -- 数据字典
	 */
	DICT("dict"),
	/**
	 * cus_uri -- 自定义URI
	 */
	CUSTOM_URI("cus_uri"),
	/**
	 * cus_item -- 自定义选项
	 */
	CUSTOM_ITEM("cus_item");
	
	private String value;
	
	private FormDataSourceType(String value) {
		this.value = value;
	} 
	
	/**
	 * 值转换为表单数据源类型
	 * @param value 数据值
	 * @return
	 */
	public static FormDataSourceType getObj(String value) {
		FormDataSourceType sourceType = null;
		for (FormDataSourceType sourceTypeTmp : FormDataSourceType.values()) {
			if(sourceTypeTmp.getValue().equals(sourceTypeTmp.getValue())) {
				sourceType = sourceTypeTmp;
				break;
			}
		}//for
		return sourceType;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
