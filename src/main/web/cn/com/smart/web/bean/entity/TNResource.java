package cn.com.smart.web.bean.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

import com.mixsmart.utils.StringUtils;

/**
 * 资源（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月30日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_resource")
public class TNResource extends BaseBeanImpl implements DateBean {

	private static final long serialVersionUID = 4276684633498474970L;

	private String id;
	
	private String name;
	
	private String uri;
	
	private Date createTime;
	
	private String state;
	
	private String opAuths;
	
	private List<TNOPAuth> auths;
	
	private String type;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="name",length=255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="uri",length=255)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="state",length=2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Transient
	public List<TNOPAuth> getAuths() {
		return auths;
	}

	public void setAuths(List<TNOPAuth> auths) {
		this.auths = auths;
	}

	@Column(name="op_auths",length=255)
	public String getOpAuths() {
		return opAuths;
	}

	public void setOpAuths(String opAuths) {
		this.opAuths = opAuths;
	}
	
	
	/**
	 * auths集合转化为字符串，各项value直接用逗号(,)隔开的字符串
	 * @return 返回转化后的字符串
	 */
	@Transient
	public String authsToString() {
		String authStr = "";
		if(null != auths && auths.size()>0) {
			for (TNOPAuth auth : auths) {
				if(null != auth && !StringUtils.isEmpty(auth.getValue())) 
					authStr += auth.getValue()+",";
			}
		}
		return authStr;
	}

	@Column(name="type",length=50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
	
}
