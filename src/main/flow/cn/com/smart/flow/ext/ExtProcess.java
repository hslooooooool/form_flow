package cn.com.smart.flow.ext;

import org.snaker.engine.entity.Process;


/**
 * 
 * @author lmq
 *
 */
public class ExtProcess extends Process {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4003866893057029751L;
	
	private String orgId;
	
	private String attachment;
	
	private String formId;
	
	private String flowType;
	
	private ExtProcessModel model;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Override
	public ExtProcessModel getModel() {
		if(null == model) {
			model = new ExtProcessModel();
			model.convert(super.getModel());
			model.setOrgId(orgId);
			model.setAttachment(attachment);
			model.setFormId(formId);
			model.setFlowType(flowType);
		}
		return model;
	}
	
	
	/**
	 * 
	 * @param process
	 */
	public void convert(Process process) {
		this.setBytes(process.getBytes());
		this.setContent(process.getContent());
		this.setCreateTime(process.getCreateTime());
		this.setCreator(process.getCreator());
		this.setDisplayName(process.getDisplayName());
		this.setId(process.getId());
		this.setInstanceUrl(process.getInstanceUrl());
		this.setName(process.getName());
		this.setState(process.getState());
		this.setType(process.getType());
		this.setVersion(process.getVersion());
		this.setModel(process.getModel());
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
