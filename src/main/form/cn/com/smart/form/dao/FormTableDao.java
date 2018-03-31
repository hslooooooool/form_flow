package cn.com.smart.form.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.form.bean.entity.TCreateTable;
import cn.com.smart.form.bean.entity.TCreateTableField;

/**
 * @author lmq
 * @create 2015年6月25日
 * @version 1.0 
 * @since 
 *
 */
@Repository("formTableDao")
public class FormTableDao extends BaseDaoImpl<TCreateTable>{

	@Autowired
	private FormTableFieldDao tableFieldDao;

	@Override
	public TCreateTable find(Serializable id) throws DaoException {
		TCreateTable table = super.find(id);
		if(null != table) {
			//与字段关联
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("tableId", id);
			List<TCreateTableField> fields = tableFieldDao.queryByField(param," sortOrder asc");
			if(null != fields && fields.size()>0) {
				table.setFields(fields);
			}
			fields = null; param = null;
		}
		return table;
	}

	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id) {
			String[] ids = id.toString().split(",");
			for (int i = 0; i < ids.length; i++) {
				TCreateTable table = find(ids[i]);
				if(null != table) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("tableId", ids[i]);
					if(tableFieldDao.delete(param) && super.delete(ids[i])) {
						//删除表
						try{
						    executeSql("DROP TABLE "+table.getTableName());
						} catch(Exception ex) {
							ex.printStackTrace();
						}
						is = true;
					}
				}
			}
		}
		return is;
	}
	
}
