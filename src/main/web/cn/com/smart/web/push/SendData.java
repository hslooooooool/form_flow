package cn.com.smart.web.push;

/**
 * 发送数据对象
 * @author lmq
 * @version 1.0
 */
public class SendData {

	private String msgId;
	
	private String title;
	
	private String content;
	
	private String url;
	
	/**
	 * 创建发送数据对象
	 * @param title 标题
	 * @param content 内容
	 */
	public SendData(String msgId, String title, String content) {
		this.msgId = msgId;
		this.title = title;
		this.content = content;
	}

	public SendData() {
		
	}

	/**
	 * 创建发送数据对象
	 * @param msgId 消息ID
	 * @param title 标题
	 * @param content 内容
	 * @param url URL
	 */
	public SendData(String msgId, String title, String content, String url) {
		this.msgId = msgId;
		this.title = title;
		this.content = content;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
}
