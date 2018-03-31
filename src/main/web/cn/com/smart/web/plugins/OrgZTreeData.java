package cn.com.smart.web.plugins;

import com.mixsmart.utils.StringUtils;

/**
 *  ZTree插件---组织机构树形数据结构类
 * @author lmq
 *
 */
public class OrgZTreeData extends ZTreeData {

	@Override
	public String getIconSkin() {
		if(!isParent) {
			iconSkin = "org-leaf";
		}
		return super.getIconSkin();
	}

	@Override
	public Boolean getNocheck() {
		nocheck = true;
		if(StringUtils.isEmpty(checkFlag) || checkFlag.equals(flag)) {
			nocheck = false;
		} 
		return super.getNocheck();
	}
	
}
