package cn.com.smart.flow.enums;

/**
 * 流程部署类型
 * @author 李湄强
 * @version 1.0
 * @since 1.0
 * <br />
 * 2016年7月5日
 */
public enum FlowDeployType {

	/**
	 * 保存（升级部署）
	 */
	SAVE("save", "保存"), 
	/**
	 * 更新（覆盖部署）
	 */
	UPDATE("update", "更新");
	
	private String value;
	
	private String name;
	
	private FlowDeployType(String value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 通过值获取流程部署对象
	 * @param value 值
	 * @return 返回流程部署对象
	 */
	public static FlowDeployType getObj(String value) {
		FlowDeployType deployType = null;
		for (FlowDeployType deployTypeTmp : FlowDeployType.values()) {
			if(deployTypeTmp.getValue().equals(value)) {
				deployType = deployTypeTmp;
				break;
			}
		}
		return deployType;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
