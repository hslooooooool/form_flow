package cn.com.smart.web.tag.bean;

/**
 * 刷新按钮
 * @author lmq
 *
 */
public class RefreshBtn extends BaseBtn {
	
	protected String target;
	
	public RefreshBtn() {
		this.id = "refresh";
	}
	
	public RefreshBtn(String uri,String busi,String target) {
		this.id = "refresh";
		this.uri = uri;
		this.busi = busi;
		this.target = target;
	}
	
	public RefreshBtn(String uri,String target) {
        this.id = "refresh";
        this.uri = uri;
        this.target = target;
    }
	
	public RefreshBtn(String uri,String busi,String target,String btnStyle,String name) {
		this.id = "refresh";
		this.uri = uri;
		this.busi = busi;
		this.target = target;
		this.btnStyle = btnStyle;
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
}
