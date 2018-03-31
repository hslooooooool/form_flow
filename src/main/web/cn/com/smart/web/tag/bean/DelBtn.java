package cn.com.smart.web.tag.bean;

/**
 * 删除按钮
 * @author lmq
 *
 */
public class DelBtn extends BaseBtn {

	protected String msg;
	
	protected String refreshUri;
	
	protected String target;
	
	protected String callback;
	
	public DelBtn() {
		this.id="del";
	}
	
	/**
	 * 删除构造函数，不推荐在使用
	 * @param uri
	 * @param busi
	 * @param msg
	 * @param refreshUri
	 * @param target
	 * @param callback
	 */
	@Deprecated
	public DelBtn(String uri,String busi,String msg,String refreshUri,String target,String callback) {
		this.id="del";
		this.uri = uri;
		this.busi = busi;
		this.msg = msg;
		this.refreshUri = refreshUri;
		this.target = target;
		this.callback = callback;
	}
	
	/**
	 * 删除构造函数，不推荐在使用
	 * @param uri
	 * @param busi
	 * @param msg
	 * @param refreshUri
	 * @param target
	 * @param callback
	 * @param btnStyle
	 * @param name
	 */
	@Deprecated
	public DelBtn(String uri,String busi,String msg,String refreshUri,String target,String callback,String btnStyle,String name) {
		this.id="del";
		this.uri = uri;
		this.busi = busi;
		this.msg = msg;
		this.refreshUri = refreshUri;
		this.target = target;
		this.callback = callback;
		this.btnStyle = btnStyle;
		this.name = name;
	}
	
	/**
	 * 
	 * @param uri
	 * @param msg
	 * @param refreshUri
	 * @param target
	 * @param jsCallback
	 */
	public DelBtn(String uri,String msg,String refreshUri,String target,String jsCallback) {
		this.id="del";
		this.uri = uri;
		this.msg = msg;
		this.refreshUri = refreshUri;
		this.target = target;
		this.callback = jsCallback;
	}
	
	/**
	 * 
	 * @param uri
	 * @param msg
	 * @param refreshUri
	 * @param target
	 * @param jsCallback
	 * @param btnStyle
	 * @param name
	 */
	public DelBtn(String uri,String msg,String refreshUri,String target,String jsCallback,String btnStyle,String name) {
		this.id="del";
		this.uri = uri;
		this.msg = msg;
		this.refreshUri = refreshUri;
		this.target = target;
		this.callback = jsCallback;
		this.btnStyle = btnStyle;
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getRefreshUri() {
		return refreshUri;
	}

	public void setRefreshUri(String refreshUri) {
		this.refreshUri = refreshUri;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

    @Override
    @Deprecated
    public String getBusi() {
        return super.getBusi();
    }

    @Override
    @Deprecated
    public void setBusi(String busi) {
        super.setBusi(busi);
    }
	
	
}
