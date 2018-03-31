package cn.com.smart.web.push;

/**
 * 消息接收者类型
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public enum MessageReceiverType {

	/**
	 * org -- 组织架构（部门）
	 */
	ORG("org"),
	
	/**
	 * user -- 用户
	 */
	USER("user"),
	
	/**
	 * all -- 全发 
	 */
	ALL("all");
	
	private String value;
	
	private MessageReceiverType(String value) {
		this.value = value;
	}

	/**
	 * 根据值获取消息接收者类型对象
	 * @param value 值
	 * @return
	 */
	public static MessageReceiverType getObj(String value) {
		MessageReceiverType obj = null;
		for (MessageReceiverType type : MessageReceiverType.values()) {
			if(type.getValue().equals(value)) {
				obj = type;
				break;
			}
		}
		return obj;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
