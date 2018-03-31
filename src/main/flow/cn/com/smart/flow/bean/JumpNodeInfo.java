package cn.com.smart.flow.bean;

import com.mixsmart.enums.YesNoType;

/**
 * 跳转节点信息
 * @author lmq <br />
 * 2016年10月15日
 * @version 1.0
 * @since 1.0
 */
public class JumpNodeInfo {

	private String name;
	
	private String value;
	
	private Integer isBackNode = YesNoType.NO.getIndex();
	
	private Integer isSelectAssigner = YesNoType.NO.getIndex();
	
	/**
	 * 选择类型
	 */
	private String selectStyle;
	
	public JumpNodeInfo() {

	}

	public JumpNodeInfo(String name, String value, Integer isBackNode) {
		this.name = name;
		this.value = value;
		this.isBackNode = isBackNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getIsBackNode() {
		return isBackNode;
	}

	public void setIsBackNode(Integer isBackNode) {
		this.isBackNode = isBackNode;
	}

	public Integer getIsSelectAssigner() {
		return isSelectAssigner;
	}

	public void setIsSelectAssigner(Integer isSelectAssigner) {
		this.isSelectAssigner = isSelectAssigner;
	}

	public String getSelectStyle() {
		return selectStyle;
	}

	public void setSelectStyle(String selectStyle) {
		this.selectStyle = selectStyle;
	}

	
	
}
