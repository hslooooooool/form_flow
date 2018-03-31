package cn.com.smart.form.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.smart.form.TableHandleType;
import cn.com.smart.form.bean.CreateTableData;
import cn.com.smart.form.bean.entity.TCreateTable;
import cn.com.smart.form.bean.entity.TCreateTableField;
import cn.com.smart.utils.DBUtil;

import com.mixsmart.utils.StringUtils;

/**
 * 创建表助手
 * @author lmq
 * @create 2015年6月28日
 * @version 1.0 
 * @since 
 *
 */
public class CreateTableHelper {

	private static final String CREATE_SQL = "create table ${table}(id varchar(50) PRIMARY KEY,${fields},state int(1),creator varchar(50),form_data_id varchar(50),create_time varchar(20))";

	private static final String DATA_FORMAT_VARCHAR = "varchar";
	private static final String DATA_FORMAT_INT = "int";
	private static final String DATA_FORMAT_NUMERIC = "numeric";
	private static final String DATA_FORMAT_DATETIME = "datetime";
	private static final String DATA_FORMAT_TEXT = "text";
	private static final String DATA_FORMAT_LONG_TEXT = "longtext";
	private static final String FIELD_ID = "id";
	
	private CreateTableData tableData;
	
	public CreateTableHelper(CreateTableData tableData) {
		this.tableData = tableData;
	}
	
	/**
	 * 生成创建表的SQL语句
	 * @return
	 */
	public String createSQL() {
		String createSql = CREATE_SQL.replace("${table}", tableData.getTable().getTableName());
		StringBuffer fieldBuf = new StringBuffer();
		List<TCreateTableField> fields = tableData.getFields();
		for (TCreateTableField field : fields) {
			if(FIELD_ID.equals(field.getFieldName())) {
				continue;
			}
			fieldBuf.append(field.getFieldName()+" ");
			fieldBuf.append(getDataType(field));
			fieldBuf.append(",");
		}
		//去掉组合语句时多余的那个逗号","
		fieldBuf.delete(fieldBuf.length()-1, fieldBuf.length());
		return createSql.replace("${fields}", fieldBuf.toString());
	}
	
	
	/**
	 * 根据数据类型和长度，组合成SQL语句的数据类型
	 * @param field
	 * @return
	 */
	private String getDataType(TCreateTableField field) {
		StringBuffer fieldBuf = new StringBuffer();
		if(DATA_FORMAT_VARCHAR.equals(field.getDataFormat())) {
			fieldBuf.append(" "+field.getDataFormat()+"(");
			if(!StringUtils.isNum(field.getLength()) || StringUtils.isEmpty(field.getLength())) {
				fieldBuf.append("50");
				field.setLength("50");
			} else {
				String num = Double.parseDouble(field.getLength())>4000?"4000":field.getLength();
				fieldBuf.append(num);
				field.setLength(num);
			}
			fieldBuf.append(")");
		} else if(DATA_FORMAT_INT.equals(field.getDataFormat())) {
			fieldBuf.append(" "+field.getDataFormat()+"(");
			if(StringUtils.isNum(field.getLength()) || StringUtils.isEmpty(field.getLength())) {
				fieldBuf.append("5");
				field.setLength("5");
			} else {
				String num = Double.parseDouble(field.getLength())>11?"11":field.getLength();
				fieldBuf.append(num);
				field.setLength(num);
			}
			fieldBuf.append(")");
		} else if(DATA_FORMAT_NUMERIC.equals(field.getDataFormat())) {
			fieldBuf.append(" "+field.getDataFormat()+"(");
			if(!StringUtils.isCheckRegex(field.getLength(),"\\d+,\\d+") || StringUtils.isEmpty(field.getLength())) {
				fieldBuf.append("8,3");
				field.setLength("8,3");
			} else {
				String[] values = field.getLength().split(",");
				String num1 = null;
				String num2 = null;
				if(values.length==2) {
					int n1 = Integer.parseInt(values[0]);
					int n2 = Integer.parseInt(values[1]);
					n1 = n1 > 50 ? 50 : n1;
					n2 = n2 > 10 ? 10 : n2;
					num1 = String.valueOf(n1);
					num2 = String.valueOf(n2);
				} else {
					int n1 = Integer.parseInt(values[1]);
					n1 = n1 > 50 ? 50 : n1;
					num1 = String.valueOf(n1);
					num2 = "3";
				}
				fieldBuf.append(num1+","+num2);
				field.setLength(num1+","+num2);
			}
			fieldBuf.append(")");
		} else if(DATA_FORMAT_DATETIME.equals(field.getDataFormat())) {
			if(DBUtil.isMySQL() || DBUtil.isSQLServer()) {
				fieldBuf.append(" datetime ");
			} else if(DBUtil.isOracle()) {
				fieldBuf.append(" date ");
			}
		} else if(DATA_FORMAT_TEXT.equals(field.getDataFormat())) {
			fieldBuf.append(" "+field.getDataFormat()+" ");
		} else if(DATA_FORMAT_LONG_TEXT.equals(field.getDataFormat())) {
			fieldBuf.append(" "+field.getDataFormat()+" ");
		}
		return fieldBuf.toString();
	}
	
	
	/**
	 * 处理字段
	 * @param table
	 * @param oldFields
	 * @return
	 */
	public Map<String,List<TCreateTableField>> handleTableAndFields(TCreateTable table,List<TCreateTableField> oldFields) {
		Map<String,List<TCreateTableField>> tableFieldMaps = null;
		if(null != oldFields && oldFields.size()>0 && null != tableData 
				&& null !=tableData.getFields() && tableData.getFields().size()>0 && null != table) {
			tableFieldMaps = new HashMap<String, List<TCreateTableField>>();
			List<TCreateTableField> newList = new ArrayList<TCreateTableField>();
			for (TCreateTableField newField : tableData.getFields()) {
				if(StringUtils.isEmpty(newField.getId()) && StringUtils.isNotEmpty(newField.getFieldName())) {
					newField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" add "+newField.getFieldName()+" "+getDataType(newField));
					newField.setTableId(table.getId());
					newList.add(newField);
				}
			}
			//判断表名是否修改
			if(table.getId().equals(tableData.getTable().getId()) && !table.getTableName().equals(tableData.getTable().getTableName())) {
				if(DBUtil.isMySQL() || DBUtil.isDB2()) 
					table.setChangeTable("RENAME TABLE "+table.getTableName()+" TO "+tableData.getTable().getTableName());
				else if(DBUtil.isOracle()) 
					table.setChangeTable("alter TABLE "+table.getTableName()+" rename to "+tableData.getTable().getTableName());
				else if(DBUtil.isSQLServer()) 
					table.setChangeTable("EXEC sp_rename TABLE "+table.getTableName()+" , "+tableData.getTable().getTableName());
			}
			if(newList.size()>0)
				tableFieldMaps.put(TableHandleType.TABLE_FIELD_NEW_FLAG, newList);
			List<TCreateTableField> delList = new ArrayList<TCreateTableField>();
			List<TCreateTableField> updateList = new ArrayList<TCreateTableField>();
			for (TCreateTableField oldField : oldFields) {
				boolean isExist = false; //判断以前字段中是否存在修改后的这个字段
				boolean isChange = false; //判断字段是否改变(以前字段与修改后的字段对比)
				for (TCreateTableField newField : tableData.getFields()) {
					if(StringUtils.isEmpty(newField.getId())) {
						continue;
					}
					if(oldField.getId().equals(newField.getId())) {
						isExist = true;
					    if(!oldField.isSame(newField)) {
							if(!oldField.isNameSame(newField)) {
								if(DBUtil.isMySQL()) {
									oldField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" change column "+oldField.getFieldName()+" "+newField.getFieldName()+" "+getDataType(oldField)+";");
								} else {
									oldField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" rename column "+oldField.getFieldName()+" to "+newField.getFieldName()+";");
								}
								isChange = true;
							}
							if(!oldField.isDataFormatSame(newField)) {
								String dataType = getDataType(newField);
								oldField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" modify column "+newField.getFieldName()+" "+dataType+";");
								isChange = true;
							} else if(!oldField.isLengthSame(newField)) {
								String dataType = getDataType(newField);
								oldField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" modify column "+newField.getFieldName()+" "+dataType+";");
								isChange = true;
							} else if(!oldField.isRemart(newField)) {
								isChange = true;
							}
							if(isChange) {
								oldField.setFieldName(newField.getFieldName());
								oldField.setDataFormat(newField.getDataFormat());
								oldField.setFieldRemark(newField.getFieldRemark());
								oldField.setLength(newField.getLength());
							}
						}
					    if(isChange) {
					    	updateList.add(oldField);
					    }
					  break;
				   }//if
				}//for
				if(!isExist) {
					oldField.getChangeFields().append("alter table "+tableData.getTable().getTableName()+" drop column "+oldField.getFieldName());
					delList.add(oldField);
				}
			}//for
			if(delList.size()>0) {
				tableFieldMaps.put(TableHandleType.TABLE_FIELD_DEL_FLAG, delList);
			}
			if(updateList.size()>0) {
				tableFieldMaps.put(TableHandleType.TABLE_FIELD_UPDATE_FLAG, updateList);
			}
		}
		return tableFieldMaps;
	}
	
}
