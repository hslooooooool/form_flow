package cn.com.smart.web.plugins.bean;

import com.mixsmart.enums.YesNoType;

/**
 * ZTreeCheck节点
 * @author lmq <br />
 * 2017年1月5日
 * @version 1.0
 * @since 1.0
 */
public class ZTreeNodeCheck extends ZTreeNode {

	
	private Boolean checked = YesNoType.NO.getValue();
	
	private Boolean nocheck = YesNoType.NO.getValue();
	
	private Boolean chkDisabled = YesNoType.NO.getValue();
	
	private Boolean doCheck = YesNoType.YES.getValue();
	
	private Boolean halfCheck = YesNoType.NO.getValue();

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

	public Boolean getChkDisabled() {
		return chkDisabled;
	}

	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public Boolean getDoCheck() {
		return doCheck;
	}

	public void setDoCheck(Boolean doCheck) {
		this.doCheck = doCheck;
	}

	public Boolean getHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(Boolean halfCheck) {
		this.halfCheck = halfCheck;
	}
	
	
}
