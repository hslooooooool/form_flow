package cn.com.smart.form.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.dao.impl.BaseDaoImpl;
import cn.com.smart.exception.DaoException;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.res.SQLResUtil;

/**
 * 
 * @author lmq
 *
 */
@Repository("formDao")
public class FormDao extends BaseDaoImpl<TForm>{


	@Override
	public boolean delete(Serializable id) throws DaoException {
		boolean is = false;
		if(null != id && StringUtils.isNotEmpty(id.toString())) {
			String[] ids = id.toString().split(",");
			List<TForm> forms = find(ids);
			if(null != forms && forms.size()>0) {
				String sql = SQLResUtil.getOpSqlMap().getSQL("del_form");
				if(StringUtils.isNotEmpty(sql)) {
					Map<String,Object> params = new HashMap<String, Object>(1);
					params.put("id", ids);
					is = executeSql(sql.split(";"), params);
				}
			}
			forms = null;
			ids = null;
		}
		return is;
	}
	
	
	/**
	 * 判断表单数据是否存在
	 * @param formId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean isExistData(String formId,String id) throws Exception {
		boolean is = false;
		if(StringUtils.isNotEmpty(formId) && StringUtils.isNotEmpty(id)) {
			TForm form = find(formId);
			if(null != form) {
				//获取表名
				String tableName = form.getName();
				String sql = "select count(*) form "+tableName+" where id=:id";
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("id", id);
				long count = exeCountSql(sql, param);
				is = (count>0)?true:false;
				param = null;
			}
			form = null;
		}
		return is;
	}
}
