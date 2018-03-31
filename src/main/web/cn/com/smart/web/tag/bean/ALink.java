package cn.com.smart.web.tag.bean;

import cn.com.smart.web.constant.enums.PageOpenStyle;

/**
 * 超链接设置
 * @author lmq
 * 
 *
 */
public class ALink {

	private String uri;
	
	/**
	 * 链接所在位置
	 */
	private String linkPostion="1";
	
	/**
	 * 参数的名称多个参数名之间用英文逗号隔开	
	 */
	private String paramName="id";
	
	/**
	 * 参数下标，多个之间用英文逗号隔开,如果是常量，则加单引号
	 */
	private String paramIndex="0";
	
	/**
	 * a标签target属性
	 * 如:_blank等
	 */
	private String aTarget;
	
	/**
	 * class表示的属性
	 * 如：cnoj-open-blank,cnoj-open-slef等
	 */
	private String classTarget = PageOpenStyle.OPEN_BLANK.getValue();
	
	private String dialogWidth = "600";
	
	private String dialogTitle="信息";
	
	/**
	 * 单元格回调接口
	 */
	private ICustomCellCallback cellCallback;

	public ALink() {
	}

	public ALink(String uri,String dialogWidth, String dialogTitle) {
		this.uri = uri;
		this.dialogWidth = dialogWidth;
		this.dialogTitle = dialogTitle;
	}
	
	public ALink(String uri,String dialogWidth, String dialogTitle,ICustomCellCallback cellCallback) {
		this.uri = uri;
		this.dialogWidth = dialogWidth;
		this.dialogTitle = dialogTitle;
		this.cellCallback = cellCallback;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLinkPostion() {
		return linkPostion;
	}

	public void setLinkPostion(String linkPostion) {
		this.linkPostion = linkPostion;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamIndex() {
		return paramIndex;
	}

	public void setParamIndex(String paramIndex) {
		this.paramIndex = paramIndex;
	}

	public String getaTarget() {
		return aTarget;
	}

	public void setaTarget(String aTarget) {
		this.aTarget = aTarget;
	}

	public String getClassTarget() {
		return classTarget;
	}

	/**
	 * 改为 {@link #setClassTarget(PageOpenStyle)} 方法
	 * @param classTarget
	 */
	@Deprecated
	public void setClassTarget(String classTarget) {
		this.classTarget = classTarget;
	}
	
	public void setClassTarget(PageOpenStyle pageOpenStyle) {
		if(null != pageOpenStyle) {
			this.classTarget = pageOpenStyle.getValue();
		}
	}

	public String getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(String dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public String getDialogTitle() {
		return dialogTitle;
	}

	public void setDialogTitle(String dialogTitle) {
		this.dialogTitle = dialogTitle;
	}

	public ICustomCellCallback getCellCallback() {
		return cellCallback;
	}

	public void setCellCallback(ICustomCellCallback cellCallback) {
		this.cellCallback = cellCallback;
	}
	
}
