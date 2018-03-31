package cn.com.smart.web.tag;

import cn.com.smart.web.constant.enums.BtnPropType;

/**
 * 自定义标签按钮基类
 * @author lmq
 *
 */
public class BtnTag extends BaseTag {

	private static final long serialVersionUID = -5251860490557328881L;

	protected String id;
	
	protected String uri;
	
	protected String busi;
	
	protected String btnStyle = BTN_DEFAULT_THEME;
	
	protected String name;
	
	protected String selectedType = BtnPropType.SelectType.NONE.getValue();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getBusi() {
		return busi;
	}

	public void setBusi(String busi) {
		this.busi = busi;
	}

	public String getBtnStyle() {
		return btnStyle;
	}

	public void setBtnStyle(String btnStyle) {
		this.btnStyle = btnStyle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelectedType() {
		return selectedType;
	}

	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}
	
}
