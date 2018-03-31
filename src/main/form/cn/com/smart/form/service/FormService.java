package cn.com.smart.form.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.smart.form.bean.LogFieldInfo;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.web.constant.IWebConstant;
import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.LoggerUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.form.bean.entity.TForm;
import cn.com.smart.form.parser.factory.FormParserFactory;
import cn.com.smart.form.parser.factory.NotFindParserException;
import cn.com.smart.service.impl.MgrServiceImpl;

import com.mixsmart.utils.StringUtils;

/**
 * 
 * @author lmq
 * @create 2015年7月4日
 * @version 1.0 
 * @since 
 *
 */
@Service
public class FormService extends MgrServiceImpl<TForm> implements IFormService {
	
	@Autowired
	private DynamicFormManager formManager;

	@SuppressWarnings("unchecked")
	@Override
	public SmartResponse<String> parseForm(TForm form,Map<String,Object> dataMap) {
		SmartResponse<String> smartResp = new SmartResponse<String>();
		if(null == form || null == dataMap || dataMap.size()<1) {
			return smartResp;
		}
		boolean isUpdate = true;
		if(StringUtils.isEmpty(form.getId())) {
			form.setId(StringUtils.createSerialNum());
			isUpdate = false;
		}
        String parseHtml = (String)dataMap.get("parse");
        List<Map<String, Object>> lists = (List<Map<String, Object>>)dataMap.get("data");
        for (Map<String, Object> mapTmp : lists) {
        	Object plugin = mapTmp.get("leipiplugins");
        	if(null != plugin) {
            	try {
            		//解析表单
            		FormParserFactory parserFactory = FormParserFactory.getInstance();
					String parseContent = parserFactory.parse(plugin.toString(), mapTmp);
					if(StringUtils.isNotEmpty(parseContent)) {
						parseHtml = parseHtml.replace("{"+mapTmp.get("name")+"}", parseContent);
					}
				} catch (NotFindParserException e) {
					e.printStackTrace();
				}
        	}
		}
        Map<String, Object> datas = (Map<String, Object>)dataMap.get("add_fields");
        if(null != datas && datas.size()>0)
        	formManager.process(form, datas);
		String template = (String)dataMap.get("template");
		form.setOriginalHtml(template);
		form.setParseHtml(parseHtml);
		if(isUpdate) {
			smartResp = update(form);
		} else {
			smartResp = save(form);
		}
		return smartResp;
	}

	@Override
	public List<Object> queryFieldValue(String[] fieldNames, String tableName, String formDataId) {
		if(null == fieldNames || fieldNames.length == 0
				|| StringUtils.isEmpty(tableName) || StringUtils.isEmpty(formDataId)) {
			throw new NullArgumentException();
		}
		String sql = SQLResUtil.getOpSqlMap().getSQL("get_field_value");
		StringUtils.isAssert(sql,"[get_field_value]SQL语句为空");
		String fieldNameStr = ArrayUtils.arrayToString(fieldNames, IWebConstant.MULTI_VALUE_SPLIT);
		sql = sql.replace("${fieldName}", fieldNameStr);
		sql = sql.replace("${tableName}",tableName);
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("formDataId", formDataId);
		return getDao().queryObjSql(sql, param);
	}

	@Override
	public List<LogFieldInfo> getLogFieldInfo(String formId) {
		String sql = SQLResUtil.getOpSqlMap().getSQL("log_field_info");
		StringUtils.isAssert(sql,"获取到的[log_field_info]SQL语句为空");
		Map<String, Object> param = new HashMap<String, Object>(1);
		param.put("formId", formId);
		return getDao().querySqlToBean(sql, param, LogFieldInfo.class);
	}
	
	@Override
    public String getInstanceTitle(Map<String, Object> datas, String formId, String userId, String name) {
        String insTitle = null;
        if(null == datas || StringUtils.isEmpty(userId)) 
            return insTitle;
        insTitle = getTitleFormParams(datas, formId, name);
        return insTitle;
    }
	
	/**
     * 从参数中获取标题
     * @param datas
     * @param formId
     * @param processName
     * @return
     */
    private String getTitleFormParams(Map<String, Object> datas, String formId, String processName) {
        String title = null;
        if(null == datas || datas.size() == 0 || StringUtils.isEmpty(formId)) {
            LoggerUtils.error(logger, "表单提交的数据为空");
            return title;
        }
        //获取流程实例标题对应的字段ID
        String sql = SQLResUtil.getOpSqlMap().getSQL("get_institle_fieldid_by_form");
        if(StringUtils.isEmpty(sql)) {
            return title;
        }
        Map<String,Object> params = new HashMap<String, Object>(1);
        params.put("formId", formId);
        processName = StringUtils.isEmpty(processName)?"":(processName+"-");
        List<Object> lists = getDao().queryObjSql(sql, params);
        if(null != lists && lists.size()>0) {
            String insTitleFieldId = StringUtils.handleNull(lists.get(0));
            title = StringUtils.handleNull(datas.get(insTitleFieldId));
            if(StringUtils.isNotEmpty(title)) {
                title = processName + title;
            } else {
                LoggerUtils.info(logger, "标题获取失败");
                title = null;
            }
        }
        return title;
    }
}
