package cn.com.smart.form.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

import com.mixsmart.enums.YesNoType;

/**
 * 表单帮助 -- 实体对象
 * @author lmq <br />
 * 2016年10月19日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_form_helper")
public class TFormHelper extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4918526904981506051L;

	public static final String PREFIX = "FH";
	
	private String id;
	
	private String title;
	
	/**
	 * 状态；是否有效
	 */
	private Integer state = YesNoType.YES.getIndex();
	
	private String content;
	
	private String userId;
	
	private Date createTime;

	@Id
	@Column(name="id", length=50, nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="title", length=255, nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Lob
	@Column(name="content",columnDefinition="TEXT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	@Transient
	public String getPrefix() {
		return PREFIX;
	}
}
