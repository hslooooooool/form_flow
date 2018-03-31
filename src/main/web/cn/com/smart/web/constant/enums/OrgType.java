package cn.com.smart.web.constant.enums;

/**
 * 组织机构类型 <br />
 * COMPANY(1,"company") --- 公司 <br />
 * DEPARTMENT(2,"department") -- 部门 <br />
 * 
 * @author lmq
 * @version 1.0 2015年9月9日
 * @since 1.0
 *
 */
public enum OrgType {
	
	COMPANY(1,"company"),DEPARTMENT(2,"department");
	
	private int index;
	private String value;
	
	private OrgType(int index,String value) {
		this.index = index;
		this.value = value;
	}
	
	public String getValue(int index) {
		String valueTmp = null;
		for (OrgType orgType : OrgType.values()) {
			if(orgType.getIndex() == index) {
				valueTmp = orgType.getValue();
				break;
			}
		}
		return valueTmp;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
