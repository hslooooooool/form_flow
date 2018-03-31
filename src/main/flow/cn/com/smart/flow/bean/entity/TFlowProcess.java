package cn.com.smart.flow.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;

/**
 * 流程处理类(扩展)
 * @author lmq
 *
 */
@Entity
@Table(name="t_flow_process")
public class TFlowProcess extends BaseBeanImpl {

	private static final long serialVersionUID = 1570460863862270206L;

	private String id;
	
	private String processId;
	
	private String orgId;
	
	/**
	 * 是否有附件
	 * 1--有
	 * 0--无
	 */
    private String attachment;
	
	private String formId;
	
	private String flowType;
	
	/**
	 * 流程总节点数
	 */
	private Integer totalNodeNum = 0;
	
	/**
	 * 节点名称集合
	 */
	private String nodeNameCollection;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="process_id",length=50)
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	@Column(name="org_id",length=50)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@Column(name="attachment",length=2)
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name="form_id",length=50)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name="flow_type",length=127)
	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
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

	@Column(name="node_name_collection",length=4000)
	public String getNodeNameCollection() {
		return nodeNameCollection;
	}

	public void setNodeNameCollection(String nodeNameCollection) {
		this.nodeNameCollection = nodeNameCollection;
	}
	
}
