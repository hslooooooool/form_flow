package cn.com.smart.web.plugins.bean;

/**
 * ZTree 图标节点
 * @author lmq <br />
 * 2017年1月5日
 * @version 1.0
 * @since 1.0
 */
public class ZTreeNodeIcon extends ZTreeNode {

	private String iconSkin;
	
	private String icon;
	
	private String iconOpen;
	
	private String iconClose;

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconOpen() {
		return iconOpen;
	}

	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}

	public String getIconClose() {
		return iconClose;
	}

	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	
}
