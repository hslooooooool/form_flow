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
import cn.com.smart.web.constant.enums.VersionType;

import com.mixsmart.utils.StringUtils;

/**
 * 版本信息实体类
 * @author lmq <br />
 * 2016年9月15日
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="t_n_version")
public class TNVersion extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4257684310254668325L;

	private String id;
	
	/**
	 * 版本
	 */
	private String version;
	
	private Long numVersion = 0l;
	
	private String type = VersionType.PC.getValue();

	/**
	 * 描述
	 */
	private String descr;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	/**
	 * 更新日期
	 */
	private String updateDate;

	/**
	 * 添加时间 
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
	
	@Column(name="type_",length=50, nullable=false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="version_",nullable=false, length=50)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name="descr", length=4000)
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Column(name="user_id", length=50, nullable=false,updatable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="update_date", length=20)
	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void initVersion() {
		this.version = "V1.0.0";
	}

	@Column(name="num_version")
	public Long getNumVersion() {
		if((null == numVersion && StringUtils.isNotEmpty(version)) || 
				numVersion == 0) {
			String num = version.replaceAll("[A-Za-z]|_|\\.", "");
			try {
				numVersion = Long.parseLong(num);
			} catch(Exception ex) {
				numVersion = 0l;
				ex.printStackTrace();
			}
		}
		return numVersion;
	}

	public void setNumVersion(Long numVersion) {
		this.numVersion = numVersion;
	}
	
}
