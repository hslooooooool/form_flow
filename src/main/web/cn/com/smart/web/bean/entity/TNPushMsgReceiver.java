package cn.com.smart.web.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;

import com.mixsmart.enums.YesNoType;

/**
 * 推送消息接收者
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_push_msg_receiver")
public class TNPushMsgReceiver extends BaseBeanImpl implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3880956325009393772L;

	private String id;
	
	private String msgId;
	
	/**
	 * 是否全发
	 */
	private Boolean isAll = YesNoType.NO.getValue();
	
	/**
	 * 接收者；有可能是个人，或部门
	 */
	private String receiver;
	
	/**
	 * 接收者类型；如部门；个人等
	 */
	private String receiverType;

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

	@Column(name="is_all")
	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	@Column(name="receiver", length=50)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name="receiver_type", length=100)
	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	@Override
	public TNPushMsgReceiver clone() {
		try {
			return (TNPushMsgReceiver)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
