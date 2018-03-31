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
 * 附件（实体Bean）
 * @author lmq
 * @version 1.0 2015年9月9日
 * @since 1.0
 *
 */
@Entity
@Table(name="t_n_attachment")
public class TNAttachment extends BaseBeanImpl implements DateBean {

	private static final long serialVersionUID = 1L;

	private String id;
	
    private String fileName;
	
	private String filePath;
	
	private String fileType;
	
	/**
	 * 文件后缀;如:gif,doc等
	 */
	private String fileSuffix;
	
	private long fileSize;
	
	private String userId;
	
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

	@Column(name="file_name",length=255)
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name="file_path",length=255)
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name="file_type",length=1024)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	@Column(name="file_suffix",length=50)
	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	@Column(name="file_size")
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	@Validate(nullable=false)
	@Column(name="user_id",length=50,nullable=false)
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

	@Override
	@Transient
	public String getPrefix() {
		return "att";
	}
	
	
}
