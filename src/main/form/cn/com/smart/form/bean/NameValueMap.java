package cn.com.smart.form.bean;

/**
 * 名称值对应关系
 * @author lmq
 * @create 2015年7月13日
 * @version 1.0 
 * @since 
 *
 */
public class NameValueMap {

    private String name;
	
	private Object value;
	
	private String other;

	public NameValueMap() {
		
	}

	public NameValueMap(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
}
