package cn.com.smart.form.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.exception.DaoException;
import cn.com.smart.form.bean.entity.TCreateTableField;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.web.bean.entity.TNDict;
import cn.com.smart.web.dao.impl.RelateDictDaoImpl;

/**
 * @author lmq
 * @create 2015年6月25日
 * @version 1.0 
 * @since 
 *
 */
@Repository("formTableFieldDao")
public class FormTableFieldDao extends RelateDictDaoImpl<TCreateTableField>{

	@Override
	public void asgmtValueByDict(List<TCreateTableField> ts, List<TNDict> dicts) {
		for (TCreateTableField tableField : ts) {
			for (TNDict dict : dicts) {
				if(tableField.getDataFormat().equals(dict.getBusiValue())) {
					tableField.setDataFormat(dict.getBusiName());
					break;
				}
			}
		}
	}

	@Override
	public void asgmtValueByDict(TCreateTableField t, List<TNDict> dicts) {
		for (TNDict dict : dicts) {
			if(t.getDataFormat().equals(dict.getBusiValue())) {
				t.setDataFormat(dict.getBusiName());
				break;
			}
		}
	}

	/**
	 * 根据表字段ID获取表字段实体集合
	 * @param fieldIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TCreateTableField> getTableFields(String[] fieldIds) {
		List<TCreateTableField> tableFields = null;
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_table_fields");
		if(StringUtils.isEmpty(sql)) {
			return tableFields;
		}
		Map<String,Object> param = new HashMap<String, Object>(1);
		param.put("ids", fieldIds);
		try {
			SQLQuery sqlQuery = (SQLQuery) super.getQuery(sql, param, true);
			sqlQuery.addEntity(TCreateTableField.class);
			tableFields = sqlQuery.list();
		} catch (Exception e) {
			throw new DaoException(e);
		} finally {
			param = null;
		}
		return tableFields;
	}
}
