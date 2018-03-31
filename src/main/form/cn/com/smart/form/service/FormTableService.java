package cn.com.smart.form.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.form.TableHandleType;
import cn.com.smart.form.bean.CreateTableData;
import cn.com.smart.form.bean.TableFieldMap;
import cn.com.smart.form.bean.entity.TCreateTable;
import cn.com.smart.form.bean.entity.TCreateTableField;
import cn.com.smart.form.dao.FormTableFieldDao;
import cn.com.smart.form.helper.CreateTableHelper;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 创建表单对应的数据表
 * @author lmq
 * @create 2015年6月28日
 * @version 1.0 
 * @since 
 *
 */
@Service
public class FormTableService extends MgrServiceImpl<TCreateTable> {

	@Autowired
	private FormTableFieldDao tableFieldDao;
	
	/**
	 * 保存表的相关信息，并创建表
	 * @param tableData
	 * @param userId
	 * @return
	 */
	public SmartResponse<String> createTable(CreateTableData tableData,String userId) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("表创建失败");
		String msg = null;
		if(null == tableData || null == tableData.getTable() 
				|| CollectionUtils.isEmpty(tableData.getFields())  
				|| StringUtils.isEmpty(userId)) {
			return smartResp;
		}
		List<Integer> removeIndex = new ArrayList<Integer>();
		int index = 0;
		//与字段关联起来
		for (TCreateTableField field : tableData.getFields()) {
			if(StringUtils.isEmpty(field.getFieldName())) {
				removeIndex.add(index);
			}
			index++;
		}
		if(CollectionUtils.isNotEmpty(removeIndex)) {
			for (int i = 0; i < removeIndex.size(); i++) {
				tableData.getFields().remove(removeIndex.get(i).intValue());
			}
		}
		CreateTableHelper tableHelper = new CreateTableHelper(tableData);
		String createSql = tableHelper.createSQL();
		if(StringUtils.isEmpty(createSql)) {
			return smartResp;
		}
		try {
			msg = "保存表信息";
			LoggerUtils.debug(logger, msg);
			tableData.getTable().setUserId(userId);
			//保存表信息
			smartResp = super.save(tableData.getTable());
			if(OP_SUCCESS.equals(smartResp.getResult())) {
				LoggerUtils.debug(logger, msg+"[成功]");
				//与字段关联起来
				for (TCreateTableField field : tableData.getFields()) {
					field.setTableId(smartResp.getData());
				}
				msg = "保存字段信息";
				LoggerUtils.debug(logger, "正在"+msg);
				
				if(null != tableFieldDao.save(tableData.getFields())) {
					LoggerUtils.debug(logger, msg+"[成功]");
					msg = "创建["+tableData.getTable().getTableName()+"]表";
					LoggerUtils.debug(logger, "正在"+msg);
					getDao().executeSql(createSql);
					LoggerUtils.debug(logger, msg+"[成功]");
					smartResp.setResult(OP_SUCCESS);
					smartResp.setMsg("表创建成功");
				} else {
					LoggerUtils.error(logger, msg+"[失败]");
				}
			} else {
				LoggerUtils.error(logger, msg+"[失败]");
			}
		} catch (DaoException e) {
			LoggerUtils.error(logger, "创建表[异常]");
			e.printStackTrace();
		} finally {
			tableData = null;
		}
		return smartResp;
	}
	
	
	/**
	 * 更新表结构
	 * @param tableData
	 * @param userId
	 * @return
	 */
	public SmartResponse<String> updateTable(CreateTableData tableData,String userId) throws DaoException {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		smartResp.setMsg("表保存失败");
		if(null == tableData || null == tableData.getTable() 
				|| null == tableData.getFields() || tableData.getFields().size()<1 
				|| StringUtils.isEmpty(userId)) {
			return smartResp;
		}
		Map<String,Object> param = new HashMap<String, Object>();
		TCreateTable oldTable = super.find(tableData.getTable().getId()).getData();
		if(null == oldTable) {
				return smartResp;
		}
		//获取老的字段信息
		param.put("tableId", tableData.getTable().getId());
		List<TCreateTableField> oldFields = tableFieldDao.queryByField(param);
		if(null == oldFields || oldFields.size()<1) {
			return smartResp;
		}
		//处理字段信息
		CreateTableHelper tableHelper = new CreateTableHelper(tableData);
		Map<String,List<TCreateTableField>> mapFields = tableHelper.handleTableAndFields(oldTable, oldFields);
		//把新的数据更新到老的对象里面
		oldTable.setTableName(tableData.getTable().getTableName());
		oldTable.setRemark(tableData.getTable().getRemark());
		//更新表信息
		if(!OP_SUCCESS.equals(super.update(oldTable).getResult())) {
			return smartResp;
		}
		if(StringUtils.isNotEmpty(oldTable.getChangeTable())) //判断表是否需要修改
			getDao().executeSql(oldTable.getChangeTable());

		if(null != mapFields && mapFields.size() > 0) {
			//获取处理后的字段信息
			List<TCreateTableField> fields = null;
			for(String key : mapFields.keySet()) {
				fields = mapFields.get(key);
				if(TableHandleType.TABLE_FIELD_UPDATE_FLAG.equals(key)) { //修改字段
					tableFieldDao.update(fields);
				} else if(TableHandleType.TABLE_FIELD_NEW_FLAG.equals(key)) { //添加字段
					tableFieldDao.save(fields);
				} else if(TableHandleType.TABLE_FIELD_DEL_FLAG.equals(key)) { //删除字段
					tableFieldDao.delete(fields);
				}
				for (TCreateTableField field : fields) {
					String[] sqls = field.getChangeFields().toString().split(";");
					for (int i = 0; i < sqls.length; i++) {
						tableFieldDao.executeSql(sqls[i]);
					}
				}
			}
		}
		smartResp.setResult(OP_SUCCESS);
		smartResp.setMsg("修改成功");
		tableData = null;
		return smartResp;
	}
	
	
	/**
	 * 获取表相关信息
	 * @param tableId
	 * @return
	 */
	public SmartResponse<TCreateTable> findTableInfo(String tableId) {
		SmartResponse<TCreateTable> smartResp = new SmartResponse<TCreateTable>();
		if(StringUtils.isEmpty(tableId)) {
		    return smartResp;
		}
		try {
			TCreateTable formTable = super.find(tableId).getData();
			if(null == formTable) {
				return smartResp;
			}
			List<TCreateTableField> fields = formTable.getFields();
			if(null != fields && fields.size()>0) {
				tableFieldDao.relateDict(fields, "TABLE_FIELD_DATA_FORMAT");
				formTable.setFields(fields);
			}
			smartResp.setResult(OP_SUCCESS);
			smartResp.setMsg(OP_SUCCESS_MSG);
			smartResp.setData(formTable);
			fields = null;formTable = null;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return smartResp;
	}
	
	
	/**
	 * 表单字段映射
	 * @param formId
	 * @return
	 */
	public List<TableFieldMap> tableFieldMap(String formId) {
		List<TableFieldMap> tfMaps = null;
		if(StringUtils.isEmpty(formId)) {
			return tfMaps;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("formId", formId);
		try {
			List<Object> datas = tableFieldDao.queryObjSql(SQLResUtil.getOpSqlMap().getSQL("get_table_fields_value_map"), params);
			if(null != datas && datas.size()>0) {
				tfMaps = new ArrayList<TableFieldMap>();
				TableFieldMap tfMap = null;
				for (Object obj : datas) {
					Object[] objs = (Object[])obj;
					tfMap = new TableFieldMap();
					tfMap.setId(StringUtils.handleNull(objs[0]));
					tfMap.setPlugin(StringUtils.handleNull(objs[1]));
					tfMap.setTableId(StringUtils.handleNull(objs[2]));
					tfMap.setTableName(StringUtils.handleNull(objs[3]));
					tfMap.setTableFieldId(StringUtils.handleNull(objs[4]));
					tfMap.setTableFieldName(StringUtils.handleNull(objs[5]));
					tfMaps.add(tfMap);
				}
			}
		} catch (DaoException ex) {
			ex.printStackTrace();
		}
		return tfMaps;
	}
	
}
