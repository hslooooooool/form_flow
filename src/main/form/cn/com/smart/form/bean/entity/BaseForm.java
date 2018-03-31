package cn.com.smart.form.bean.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.smart.bean.BaseBeanImpl;
import cn.com.smart.bean.DateBean;

/**
 * 表单父类
 * @author lmq <br />
 * 2016年11月21日
 * @version 1.0
 * @since 1.0
 */
@MappedSuperclass
public class BaseForm extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1041370078969782656L;

	private String id;
	
	private String name;
	
	private String type;
	
	private String originalHtml;
	
	private String parseHtml;
	
	private int fieldNum = 0;
	
	private String creator;
	
	private Date createTime;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="name",length=255,nullable=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="type",length=50,nullable=false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Lob
	@Column(name="original_html",columnDefinition="LONGTEXT")
	public String getOriginalHtml() {
		return originalHtml;
	}

	public void setOriginalHtml(String originalHtml) {
		this.originalHtml = originalHtml;
	}

	@Lob
	@Column(name="parse_html",columnDefinition="LONGTEXT")
	public String getParseHtml() {
		return parseHtml;
	}

	public void setParseHtml(String parseHtml) {
		this.parseHtml = parseHtml;
	}

	@Column(name="field_num")
	public int getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
	}
	
	@Column(name="creator",length=50,nullable=false,updatable=false)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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
