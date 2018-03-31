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
 * 流程页面模版类
 * @author lmq
 *
 */
@Entity
@Table(name="t_flow_page_model")
public class TFlowPageModel extends BaseBeanImpl implements DateBean {

	private static final long serialVersionUID = 4922855461899513686L;

	private String id;
	
	private String name;
	
	private String uri;
	
	private String viewUri;
	
	private String state;
	
	private String userId;
	
	private Date createTime;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="name",length=127)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="uri",length=127)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name="view_uri",length=127)
	public String getViewUri() {
		return viewUri;
	}

	public void setViewUri(String viewUri) {
		this.viewUri = viewUri;
	}

	@Column(name="state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="user_id",length=50,updatable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
