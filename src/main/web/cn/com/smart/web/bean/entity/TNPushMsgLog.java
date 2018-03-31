package cn.com.smart.web.bean.entity;

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
 * 消息推送日志
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_push_msg_log")
public class TNPushMsgLog extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7229656890961117102L;

	private String id;
	
	private String content;
	
	/**
	 * 消息类型
	 */
	private String msgType;
	
	/**
	 * 发送者
	 */
	private String sender;
	
	private Date createTime;

	@Id
	@Column(name="id", length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="content", length=1024, nullable=false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="msg_type", length=100, nullable=false)
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@Column(name="sender", length=50)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
