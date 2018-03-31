package cn.com.smart.web.tag.bean;

/**
 * 选择事件属性
 * @author lmq
 *
 */
public class SelectedEventProp {

	private String eventType;
	
	private String uri;
	
	private String target;
	
	/**
	 * 变量参数名称,默认参数名称为"id"
	 */
	private String varParamName="id";
	
	public SelectedEventProp(String eventType,String uri,String target) {
		this.eventType = eventType;
		this.uri = uri;
		this.target = target;
	}
	
	public SelectedEventProp(String eventType,String uri,String target,String varParamName) {
		this.eventType = eventType;
		this.uri = uri;
		this.target = target;
		this.varParamName = varParamName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVarParamName() {
		return varParamName;
	}

	public void setVarParamName(String varParamName) {
		this.varParamName = varParamName;
	}
	
}
