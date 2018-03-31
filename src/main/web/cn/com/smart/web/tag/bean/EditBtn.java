package cn.com.smart.web.tag.bean;

/**
 * 编辑按钮(添加按钮)
 * @author lmq
 *
 */
public class EditBtn extends BaseBtn {

    protected String title;
    protected String width="600";
    
    protected String beforeCheck;
    
    public EditBtn(String id) {
    	this.id = id;
    }
	
	public EditBtn(String id,String uri,String busi,String title,String width) {
		this.id = id;
		this.uri = uri;
		this.busi = busi;
		this.title = title;
		this.width = width;
	}
	
	public EditBtn(String id,String uri,String title,String width) {
        this.id = id;
        this.uri = uri;
        this.title = title;
        this.width = width;
    }
	
	public EditBtn(String id,String uri,String busi,String title,String width,String btnStyle,String name) {
		this.id = id;
		this.uri = uri;
		this.busi = busi;
		this.title = title;
		this.width = width;
		this.btnStyle = btnStyle;
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getBeforeCheck() {
		return beforeCheck;
	}

	public void setBeforeCheck(String beforeCheck) {
		this.beforeCheck = beforeCheck;
	}
}
