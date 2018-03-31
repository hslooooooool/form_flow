package cn.com.smart.flow.ext;

import org.snaker.engine.model.ProcessModel;

/**
 * 重写ProcessModel
 * @author lmq
 *
 */
public class ExtProcessModel extends ProcessModel {

	private static final long serialVersionUID = -5523482615464915678L;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
    private String attachment;
	
	private String formId;
	
	private String flowType;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 
	 * @param processModel
	 */
	public void convert(ProcessModel processModel) {
		if(null != processModel) {
			this.setDisplayName(processModel.getDisplayName());
			this.setExpireTime(processModel.getExpireTime());
			this.setGenerator(processModel.getGenerator());
			this.setInstanceNoClass(processModel.getInstanceNoClass());
			this.setInstanceUrl(processModel.getInstanceUrl());
			this.setName(processModel.getName());
			this.setNodes(processModel.getNodes());
			
		}
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
}
