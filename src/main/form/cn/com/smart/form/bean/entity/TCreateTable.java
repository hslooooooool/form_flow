package cn.com.smart.form.bean.entity;

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

/**
 * 表
 * @author lmq
 * @create 2015年6月25日
 * @version 1.0 
 * @since 
 *
 */
@Entity
@Table(name="t_create_table")
public class TCreateTable extends BaseBeanImpl implements DateBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5892912047647565383L;

	private String id;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	/**
	 * 注释
	 */
	private String remark;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建人
	 */
	private String userId;
	
	private List<TCreateTableField> fields;
	
	private String changeTable;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="table_name",length=127,nullable=false)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Column(name="remark",length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time",updatable=false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name="user_id",length=50,nullable=false,updatable=false)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public List<TCreateTableField> getFields() {
		return fields;
	}

	public void setFields(List<TCreateTableField> fields) {
		this.fields = fields;
	}

	@Transient
	public String getChangeTable() {
		return changeTable;
	}

	public void setChangeTable(String changeTable) {
		this.changeTable = changeTable;
	}
	
}
