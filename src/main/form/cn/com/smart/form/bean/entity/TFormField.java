package cn.com.smart.form.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.com.smart.bean.BaseBeanImpl;
import com.mixsmart.enums.YesNoType;

/**
 * 表单字段属性
 * @author lmq
 *
 */
@Entity
@Table(name="t_form_field")
public class TFormField extends BaseBeanImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5102114044597010763L;

	private String id;
	
	private String formId;
	
	private String plugins;
	
	private String title;
	
	private String tableId;
	
	private String tableFieldId;
	
	private String type;
	
	private String flow;

	/**
	 * 表单字段的修改是否记录日志；
	 * 默认为不记录日志
	 */
	private Integer isLog = YesNoType.NO.getIndex();

	/**
	 * 是否为流程实例标题
	 * 1--是
	 * 0--否
	 */
	private String isInsTitle = YesNoType.NO.getStrValue();

	@Id
	@Column(name="id",length=50)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="form_id",length=50,nullable=false)
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	@Column(name="plugins",length=50)
	public String getPlugins() {
		return plugins;
	}

	public void setPlugins(String plugins) {
		this.plugins = plugins;
	}

	@Column(name="title",length=255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="type",length=126)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="flow",length=50)
	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	@Column(name="table_id",length=50,nullable=false)
	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	@Column(name="table_field_id",length=50,nullable=false)
	public String getTableFieldId() {
		return tableFieldId;
	}

	public void setTableFieldId(String tableFieldId) {
		this.tableFieldId = tableFieldId;
	}

	@Column(name="is_log")
	public Integer getIsLog() {
		if(null == isLog) {
			isLog = YesNoType.NO.getIndex();
		}
		return isLog;
	}

	public void setIsLog(Integer isLog) {
		this.isLog = isLog;
	}

	@Column(name="is_institle",length=10)
	public String getIsInsTitle() {
		return isInsTitle;
	}

	public void setIsInsTitle(String isInsTitle) {
		this.isInsTitle = isInsTitle;
	}
	
}
