package cn.com.smart.form.bean;

import java.util.List;

import cn.com.smart.form.bean.entity.TCreateTable;
import cn.com.smart.form.bean.entity.TCreateTableField;

/**
 * 创建表时用到的bean
 * @author lmq
 * @create 2015年6月27日
 * @version 1.0 
 * @since 
 *
 */
public class CreateTableData {

	private TCreateTable table;
	
	private List<TCreateTableField> fields;

	public TCreateTable getTable() {
		return table;
	}

	public void setTable(TCreateTable table) {
		this.table = table;
	}

	public List<TCreateTableField> getFields() {
		return fields;
	}

	public void setFields(List<TCreateTableField> fields) {
		this.fields = fields;
	}
}
