package cn.com.smart.web.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;
import cn.com.smart.validate.Validate;

/**
 * 自定义小窗口（实体Bean）
 * @author lmq
 * @version 1.0 2015年8月27日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_min_window")
public class TNMinWindow extends BaseBeanImpl implements DateBean {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	/**
	 * 是否显示标题
	 * 1--显示
	 * 0--不显示
	 */
	private Integer isShowTitle=1;
	
	private String uri;
	
	private String moreUri;
	
	private String height;
	
	private String state;
	
	private Double sortOrder;
	
	private Date createTime;

	@Validate(nullable=false)
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

	@Validate(nullable=false)
	@Column(name="uri",length=255,nullable=false)
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	@Column(name="more_uri",length=255)
	public String getMoreUri() {
		return moreUri;
	}

	public void setMoreUri(String moreUri) {
		this.moreUri = moreUri;
	}
	

	@Column(name="height",length=10)
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	@Column(name="state",length=2)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="sort_order")
	public Double getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	@Transient
	public String getPrefix() {
		return "minw";
	}

	@Column(name="is_show_title")
	public Integer getIsShowTitle() {
		return isShowTitle;
	}

	public void setIsShowTitle(Integer isShowTitle) {
		this.isShowTitle = isShowTitle;
	}
	
}
