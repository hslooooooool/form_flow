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
 * 消息读取情况日志；
 * 该日志只针对系统内部推送，并且读取了的情况；
 * 如钉钉推送的，该表中不会相关的记录
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_push_msg_read_log")
public class TNPushMsgReadLog extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -562180171861566994L;

	private String id;
	
	private String msgId;
	
	/**
	 * 推送消息接收ID；即:TNPushMsgReceiver类中的ID
	 */
	private String msgReceiveId;
	
	/**
	 * 读取消息的用户ID
	 */
	private String userId;
	
	/**
	 * 消息读取时间
	 */
	private Date createTime;

	@Id
	@Column(name="id", length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="msg_id", length=50, nullable=false)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name="msg_receive_id", length=50, nullable=false)
	public String getMsgReceiveId() {
		return msgReceiveId;
	}

	public void setMsgReceiveId(String msgReceiveId) {
		this.msgReceiveId = msgReceiveId;
	}

	@Column(name="user_id", length=50, nullable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
