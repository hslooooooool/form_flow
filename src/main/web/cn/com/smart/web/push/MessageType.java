package cn.com.smart.web.push;

/**
 * 消息类型
 * @author lmq 
 * @version 1.0
 * @since 1.0
 */
public enum MessageType {

	/**
	 * 公告通道
	 */
	NOTICE("notice"),
	
	/**
	 * 消息提醒通道
	 */
	MESSAGE("message"),
	
	/**
	 * 待办提醒
	 */
	TODO("todo");

	private String value;
	
	/**
	 * 根据值获取消息类型对象
	 * @param value 值
	 * @return
	 */
	public static MessageType getObj(String value) {
		MessageType obj = null;
		for (MessageType type : MessageType.values()) {
			if(type.getValue().equals(value)) {
				obj = type;
				break;
			}
		}
		return obj;
	}
	
	private MessageType(String value) {
		this.value = value;
	}

	/*****getter and setter*****/
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
