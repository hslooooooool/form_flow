package cn.com.smart.flow.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;

/**
 * 流程附件
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Entity
@Table(name="t_flow_attachment")
public class TFlowAttachment extends BaseBeanImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4258710831936954772L;

	private String id;
	
	/**
	 * 附件ID
	 */
	private String attachmentId;
	
	/**
	 * 流程ID
	 */
	private String processId;
	
	/**
	 * 流程实例ID
	 */
	private String orderId;
	
	/**
	 * 任务ID
	 */
	private String taskId;
	
	/**
	 * 任务KEY（与taskId不一样）
	 * 该任务KEY表示设计流程图时，节点的标识符
	 * 如:rect1等
	 */
	private String taskKey;
	
	/**
	 * 流程附件类型
	 */
	private String attType;
	
	/**
	 * 表单ID
	 */
	private String formId;
	
	/**
	 * 用户ID（主要用于流程实例未启动前，添加附件时，与formId属性联合标记）
	 */
	private String userId;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="attachment_id",length=50,nullable=false)
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	@Column(name="process_id",length=50,nullable=false)
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name="order_id",length=50)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name="task_id",length=50)
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name="task_key",length=50)
	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	@Column(name="att_type",length=127)
	public String getAttType() {
		return attType;
	}

	public void setAttType(String attType) {
		this.attType = attType;
	}

	@Column(name="form_id",length=50)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name="user_id",length=50)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String toString() {
		return "id="+id+"\tattachmentId="+attachmentId;
	}
	
}
