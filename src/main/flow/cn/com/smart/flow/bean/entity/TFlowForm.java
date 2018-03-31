package cn.com.smart.flow.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 流程表单
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Entity
@Table(name="t_flow_form")
public class TFlowForm extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4449745384418088089L;

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 表单ID
	 */
	private String formId;
	
	/**
	 * 表单数据ID
	 */
	private String formDataId;
	
	/**
	 * 流程ID
	 */
	private String processId;
	
	/**
	 * 流程名称
	 */
	private String processName;
	
	/**
	 * 流程实例ID
	 */
	private String orderId;
	
	/**
	 * 起草人组织机构ID
	 */
	private String orgId;
	
	/**
	 * 创建流程实例的用户ID
	 */
	private String userId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 流程总节点数
	 */
	private Integer totalNodeNum = 0;
	
	/**
	 * 执行到的节点数
	 */
	private Integer executeNodeNum = 0;
	
	/**
	 * 执行进度
	 */
	private Float progress = 0f;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="form_id",length=50,nullable=false)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name="form_data_id",length=50,nullable=false)
	public String getFormDataId() {
		return formDataId;
	}

	public void setFormDataId(String formDataId) {
		this.formDataId = formDataId;
	}

	@Column(name="process_id",length=50,nullable=false)
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name="order_id",length=50,nullable=false)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name="user_id",length=50,nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="title",length=255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="org_id",length=50)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name="total_node_num",nullable=false)
	public Integer getTotalNodeNum() {
		if(null == totalNodeNum) {
			totalNodeNum = 0;
		}
		return totalNodeNum;
	}

	public void setTotalNodeNum(Integer totalNodeNum) {
		this.totalNodeNum = totalNodeNum;
	}

	@Column(name="execute_node_num",nullable=false)
	public Integer getExecuteNodeNum() {
		return executeNodeNum;
	}

	public void setExecuteNodeNum(Integer executeNodeNum) {
		this.executeNodeNum = executeNodeNum;
	}

	@Column(name="progress")
	public Float getProgress() {
		if(null == progress) {
			progress = 0f;
		}
		return progress;
	}

	public void setProgress(Float progress) {
		this.progress = progress;
	}

	@Column(name="process_name", length=255)
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
}
