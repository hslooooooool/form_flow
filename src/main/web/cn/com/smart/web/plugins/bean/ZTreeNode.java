package cn.com.smart.web.plugins.bean;

import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.StringUtils;

/**
 * ZTree基础属性
 * @author lmq <br />
 * 2017年1月5日
 * @version 1.0
 * @since 1.0
 */
public class ZTreeNode {

	protected String id;
	
	protected String pId;
	
	protected String name;
	
	protected Boolean open = YesNoType.NO.getValue();
	
	protected Boolean isParent = YesNoType.NO.getValue();

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

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Object open) {
		String openStr = StringUtils.handleNull(open);
		if(StringUtils.isNotEmpty(openStr)) {
			YesNoType yesNo = YesNoType.getObjByStrValue(openStr);
			this.open = (null != yesNo) ? yesNo.getValue():YesNoType.NO.getValue();
		}
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Object isParent) {
		String isParentStr = StringUtils.handleNull(isParent);
		if(StringUtils.isNotEmpty(isParentStr)) {
			YesNoType yesNo = YesNoType.getObjByStrValue(isParentStr);
			this.isParent = (null != yesNo) ? yesNo.getValue():YesNoType.NO.getValue();
		}
	}
	
}
