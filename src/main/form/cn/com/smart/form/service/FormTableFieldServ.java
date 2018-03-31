package cn.com.smart.form.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.form.bean.entity.TCreateTableField;
import cn.com.smart.form.dao.FormTableFieldDao;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.service.OPService;

@Service
public class FormTableFieldServ extends MgrServiceImpl<TCreateTableField> {
    @Autowired
    private OPService opServ;

	public List<TCreateTableField> getTableFields(String[] fieldIds) {
		return getDao().getTableFields(fieldIds);
	}

	@Override
	public FormTableFieldDao getDao() {
		return (FormTableFieldDao)super.getDao();
	}
	
	/**
	 * 通过插件类型获取数据库表及字段
	 * @param formId 表单ID
	 * @param plugins 插件类型
	 * @return 
	 */
	public Map<String,List<String>> getTableFieldByPugin(String formId, String[] plugins) {
	    Map<String, List<String>> dataMap = null;
	    if(StringUtils.isEmpty(formId) || null == plugins || plugins.length == 0) {
	        return dataMap;
	    }
	    Map<String, Object> param = new HashMap<String, Object>(2);
	    param.put("formId", formId);
	    param.put("plugins", plugins);
	    SmartResponse<Object> smartResp = opServ.getDatas("get_table_fields_by_plugin", param);
	    if(OP_SUCCESS.endsWith(smartResp.getResult())) {
	        dataMap = new HashMap<String, List<String>>();
	        List<Object> list = smartResp.getDatas();
	        for (Object obj : list) {
                Object[] objArray = (Object[])obj;
                String tableName = StringUtils.handleNull(objArray[0]);
                List<String> fields = dataMap.get(tableName);
                if(null == fields) {
                    fields = new ArrayList<String>();
                    dataMap.put(tableName, fields);
                }
                fields.add(StringUtils.handleNull(objArray[1]));
            }
	    }
	    return dataMap;
	    
	}
	
}
