package cn.com.smart.web.plugins;

/**
 *  ZTree插件---基本树形数据结构类
 * @author lmq
 *
 */
public class ZTreeData {
	
    protected String id;
	
	protected String pId;
	
	protected Boolean isParent = false;
	
	protected String name;
	
	protected String flag;
	
	protected String icon;
	
	protected String iconSkin;
	
	//是否展开
	protected Boolean open = true;
	
	//是否选中
	protected Boolean checked = false;
		
	protected Boolean nocheck = false;
		
	//是否可以选择
	protected Boolean doCheck = false;
	
	protected String checkFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getNocheck() {
		return nocheck;
	}

	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}

	public Boolean getDoCheck() {
		return doCheck;
	}

	public void setDoCheck(Boolean doCheck) {
		this.doCheck = doCheck;
	}
	
	
}
