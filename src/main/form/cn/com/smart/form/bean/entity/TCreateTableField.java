package cn.com.smart.form.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.smart.bean.BaseBeanImpl;

import com.mixsmart.utils.StringUtils;

/**
 * 表字段
 * @author lmq
 * @create 2015年6月25日
 * @version 1.0 
 * @since 
 *
 */
@Entity
@Table(name="t_create_table_field")
public class TCreateTableField extends BaseBeanImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6351874907351173047L;

	private String id;
	
	/**
	 * 表ID
	 */
	private String tableId;
	
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字段注释
	 */
	private String fieldRemark;
	
	/**
	 * 数据格式
	 */
	private String dataFormat;
	
	/**
	 * 字段长度
	 */
	private String length;
	
	/**
	 * 字段排序
	 */
	private int sortOrder=0;
	
	/**
	 * 修改的属性
	 * 用于修改表的时候用
	 */
	private StringBuffer changeFields;

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="table_id",length=50,nullable=false)
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	@Column(name="field_name",length=127,nullable=false)
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name="field_remark",length=500)
	public String getFieldRemark() {
		return fieldRemark;
	}

	public void setFieldRemark(String fieldRemark) {
		this.fieldRemark = fieldRemark;
	}

	@Column(name="data_format",length=50)
	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	@Column(name="length",length=10)
	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}
	
	@Column(name="sort_order")
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Transient
	public StringBuffer getChangeFields() {
		if(null == changeFields) {
			changeFields = new StringBuffer();
		}
		return changeFields;
	}

	public void setChangeFields(StringBuffer changeFields) {
		this.changeFields = changeFields;
	}

	
	//////////is same///////////
	/**
	 * 判断是否相等
	 * @param field
	 * @return
	 */
	public boolean isSame(TCreateTableField field) {
		boolean is = false;
		if(null != field) {
			is = (this.id.equals(field.getId()) && this.fieldName.equals(field.getFieldName()) && 
					this.dataFormat.equals(field.getDataFormat()) && StringUtils.handleNumNull(this.length).equals(StringUtils.handleNumNull(field.getLength())) 
					&& StringUtils.handleNull(this.fieldRemark).equals(field.getFieldRemark()));
		}
		return is;
	}
	
	/**
	 * 判断名称是否相等
	 * @param field
	 * @return
	 */
	public boolean isNameSame(TCreateTableField field) {
		boolean is = false;
		if(null != field) {
			is = this.fieldName.equals(field.getFieldName());
		}
		return is;
	}
	
	
	/**
	 * 判断数据格式是否相等
	 * @param field
	 * @return
	 */
	public boolean isDataFormatSame(TCreateTableField field) {
		boolean is = false;
		if(null != field) {
			is = this.dataFormat.equals(field.getDataFormat());
		}
		return is;
	}
	
	
	/**
	 * 判断长度是否相等
	 * @param field
	 * @return
	 */
	public boolean isLengthSame(TCreateTableField field) {
		boolean is = false;
		if(null != field) {
			is = StringUtils.handleNumNull(this.length).equals(StringUtils.handleNumNull(field.getLength()));
		}
		return is;
	}
	
	/**
	 * 判断注释是否相等
	 * @param field
	 * @return
	 */
	public boolean isRemart(TCreateTableField field) {
		boolean is = false;
		if(null != field) {
			is = StringUtils.handleNull(this.getFieldRemark()).equals(StringUtils.handleNull(field.getFieldRemark()));
		}
		return is;
	}
}
