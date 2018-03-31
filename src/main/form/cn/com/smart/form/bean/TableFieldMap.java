package cn.com.smart.form.bean;

import java.util.ArrayList;
import java.util.List;


/**
 * 表、字段映射关系bean
 * @author lmq
 * @create 2015年7月7日
 * @version 1.0 
 * @since 
 *
 */
public class TableFieldMap {

	private String id;
	
	private String plugin;
	
	private String tableId;
	
	private String tableName;
	
	private String tableFieldId;
	
	private String tableFieldName;
	
	private String tableFieldRemark;
	
	private Object value;
	
	private List<Object> values;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableFieldId() {
		return tableFieldId;
	}

	public void setTableFieldId(String tableFieldId) {
		this.tableFieldId = tableFieldId;
	}

	public String getTableFieldName() {
		return tableFieldName;
	}

	public void setTableFieldName(String tableFieldName) {
		this.tableFieldName = tableFieldName;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public List<Object> getValues() {
		if(null == values) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public String getTableFieldRemark() {
		return tableFieldRemark;
	}

	public void setTableFieldRemark(String tableFieldRemark) {
		this.tableFieldRemark = tableFieldRemark;
	}
	
}
