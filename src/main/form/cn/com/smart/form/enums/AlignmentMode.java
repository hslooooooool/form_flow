package cn.com.smart.form.enums;

import com.mixsmart.utils.StringUtils;

/**
 * 排列方式
 * @author lmq <br />
 * 2016年8月27日
 * @version 1.0
 * @since 1.0
 */
public enum AlignmentMode {

	/**
	 * orgchecked0 -- 横排
	 */
	Horizontal("orgchecked0"),
	/**
	 * orgchecked1 -- 竖排
	 */
	Vertical("orgchecked1");
	
	private String value;
	
	private AlignmentMode(String value) {
		this.value = value;
	}

	/**
	 * 根据值获取排列方式对象
	 * @param value
	 * @return
	 */
	public AlignmentMode getObj(String value) {
		AlignmentMode alignModel = null;
		if(StringUtils.isNotEmpty(value)) {
			for (AlignmentMode tmp : AlignmentMode.values()) {
				if(tmp.getValue().equals(value)) {
					alignModel = tmp;
					break;
				}
			}
		}
		return alignModel;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
