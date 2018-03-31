package cn.com.smart.web.bean;

import java.util.List;

/**
 * 自动填充属性
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
public class AutoComplete {

	private String id;
	
	private String value;
	
	private String label;
	
	private List<Object> otherValue;

	/**
	 * 唯一标识符
	 * @return 返回唯一标识符
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置唯一标识符
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取业务值
	 * @return 业务值
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置业务值
	 * @param value 
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 获取显示标签
	 * @return 显示内容
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 设置显示标签
	 * @param label 
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	public List<Object> getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(List<Object> otherValue) {
		this.otherValue = otherValue;
	}
	
}
