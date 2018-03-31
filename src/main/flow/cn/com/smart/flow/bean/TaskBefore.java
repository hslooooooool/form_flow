package cn.com.smart.flow.bean;

import java.util.List;
import java.util.Map;

import cn.com.smart.flow.ext.ExtTaskModel;

/**
 * 任务前置参数对象
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年11月17日
 */
public class TaskBefore {

	private SubmitFormData formData;
	
	private String userId;
	
	private String orgId;
	
	private List<String> outputNames;
	
	private Map<String,Object> nextAssigners;
	
	private Map<String,Object> params;
	
	private ExtTaskModel taskModel;
	
	/**
	 * 用于跳转的时候
	 */
	private ExtTaskModel targetTaskModel;
	
	public TaskBefore(SubmitFormData formData, String userId, String orgId,
			List<String> outputNames, Map<String, Object> nextAssigners,
			Map<String, Object> params,ExtTaskModel taskModel) {
		this.formData = formData;
		this.userId = userId;
		this.orgId = orgId;
		this.outputNames = outputNames;
		this.nextAssigners = nextAssigners;
		this.params = params;
		this.taskModel = taskModel;
	}

	public SubmitFormData getFormData() {
		return formData;
	}

	public void setFormData(SubmitFormData formData) {
		this.formData = formData;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<String> getOutputNames() {
		return outputNames;
	}

	public void setOutputNames(List<String> outputNames) {
		this.outputNames = outputNames;
	}

	public Map<String, Object> getNextAssigners() {
		return nextAssigners;
	}

	public void setNextAssigners(Map<String, Object> nextAssigners) {
		this.nextAssigners = nextAssigners;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public ExtTaskModel getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(ExtTaskModel taskModel) {
		this.taskModel = taskModel;
	}

	public ExtTaskModel getTargetTaskModel() {
		return targetTaskModel;
	}

	public void setTargetTaskModel(ExtTaskModel targetTaskModel) {
		this.targetTaskModel = targetTaskModel;
	}
	
}
