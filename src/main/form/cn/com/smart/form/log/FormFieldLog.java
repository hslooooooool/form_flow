package cn.com.smart.form.log;

import cn.com.smart.form.bean.LogFieldInfo;
import cn.com.smart.form.bean.TableFieldMap;
import cn.com.smart.form.bean.entity.TFormFieldChangeLog;
import cn.com.smart.form.service.FormFieldChangeLogServ;
import cn.com.smart.form.service.IFormService;
import cn.com.smart.web.constant.IWebConstant;
import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.ArrayUtils;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 *
 * 表单字段记录日志
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class FormFieldLog {

    private static final Logger logger = LoggerFactory.getLogger(FormFieldLog.class);

    @Autowired
    private FormFieldChangeLogServ changeLogServ;
    @Autowired
    private IFormService formService;

    /**
     * 记录日志
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据
     * @param userId 用户ID
     */
    public void record(String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        if(StringUtils.isEmpty(formId)) {
            throw new NullArgumentException("formId或formDataId为空");
        }
        if(StringUtils.isEmpty(formDataId)) {
            LoggerUtils.info(logger,"formDataId为空，不记录字段修改日志");
            return;
        }
        if(null == dataMap || dataMap.size() == 0) {
            LoggerUtils.info(logger,"提交的表单数据["+formDataId+"]为空，不需要记录日志");
            return;
        }
        List<LogFieldInfo> fieldInfoList = formService.getLogFieldInfo(formId);
        if(CollectionUtils.isEmpty(fieldInfoList)) {
            LoggerUtils.info(logger, "该表单["+formId+"]没有需要记录的日志字段！");
            return;
        }
        Map<String, TableFieldMap> sourceDataMap = getFieldSourceValue(fieldInfoList, formDataId);
        Set<Map.Entry<String, TableFieldMap>> sets = sourceDataMap.entrySet();
        List<TFormFieldChangeLog> changeLogs = new ArrayList<TFormFieldChangeLog>();
        for(Map.Entry<String, TableFieldMap> set : sets) {
            String logFieldId = set.getKey();
            Object valueObj = dataMap.get(logFieldId);
            if(null == valueObj) {
                continue;
            }
            String newValue = null;
            //如果原始值为空
            if(null == set.getValue().getValues() || set.getValue().getValues().size() == 0) {
                if(valueObj.getClass().isArray()) {
                    newValue = ArrayUtils.arrayToString((Object[])valueObj, IWebConstant.MULTI_VALUE_SPLIT);
                } else {
                    newValue = valueObj.toString();
                }
                addChangeLog(changeLogs, set.getValue(), formDataId, formId, userId,
                        newValue,"新增数据",FormFieldOperateType.ADD);
                continue;
            }
            //如果提交过来的值是一个数组，则认为是listctrl插件，listctrl插件只能大概判断是否修改
            if(valueObj.getClass().isArray()) {
                Object[] valueArray = (Object[])valueObj;
                newValue = ArrayUtils.arrayToString(valueArray, IWebConstant.MULTI_VALUE_SPLIT);
                if(set.getValue().getValues().size() == valueArray.length) {
                    boolean isSame = true;
                    int len = valueArray.length;
                    for(int i=0; i<len;i++) {
                        if(!set.getValue().getValues().get(i).equals(valueArray[i])) {
                            isSame = false;
                            break;
                        }
                    }
                    if(!isSame)
                        addChangeLog(changeLogs, set.getValue(), formDataId, formId,
                                userId, newValue,"修改列表控件数据",FormFieldOperateType.EDIT);
                } else
                    addChangeLog(changeLogs, set.getValue(), formDataId, formId,
                            userId, newValue,"删除或添加列表控件数据",FormFieldOperateType.DELETE);
            } else {
                //如果提交的是一个非数组（但也有可能是listctrl提交来的）;
                // 通过原值判断，是否为listctrl；判断listctrl的条件是，是否有多个值
                if(set.getValue().getValues().size()>1) {
                    addChangeLog(changeLogs, set.getValue(), formDataId, formId,
                            userId, newValue,"删除数据",FormFieldOperateType.DELETE);
                } else {
                    //判断是否相等
                    if(!valueObj.equals(set.getValue().getValues().get(0))
                            || ("file".equals(set.getValue().getPlugin())) && "".equals(valueObj))  {
                        addChangeLog(changeLogs, set.getValue(), formDataId, formId,
                                userId, valueObj.toString(),"修改数据",FormFieldOperateType.EDIT);
                    }
                }
            }
        }//for[map]
        if(changeLogs.size()>0) {
            LoggerUtils.info(logger,"保存字段修改日志");
            changeLogServ.save(changeLogs);
            LoggerUtils.info(logger,"保存字段修改日志[成功]");
        } else {
            LoggerUtils.info(logger,"没有修改记录日志对应的字段值");
        }
    }

    /**
     * 获取需要记录日志字段对应的原值
     * @param logFieldInfoList 需要记录日志的字段信息列表
     * @param formDataId 表单数据ID
     * @return 返回字段对应原值Map
     */
    private Map<String, TableFieldMap> getFieldSourceValue(List<LogFieldInfo> logFieldInfoList, String formDataId) {
        Map<String, List<LogFieldInfo>> tableMaps = new HashMap<String, List<LogFieldInfo>>();
        for (LogFieldInfo fieldInfo : logFieldInfoList) {
            List<LogFieldInfo>  list = tableMaps.get(fieldInfo.getTableName());
            list = (List<LogFieldInfo>)classifyMapAsgmt(tableMaps, list, fieldInfo.getTableName(), null);
            list.add(fieldInfo);
        }
        Map<String, TableFieldMap>  sourceValueMap = new HashMap<String, TableFieldMap>(logFieldInfoList.size());
        Set<Map.Entry<String, List<LogFieldInfo>>> items = tableMaps.entrySet();
        for (Map.Entry<String, List<LogFieldInfo>> item : items) {
            String[] fieldNames = new String[item.getValue().size()];
            for (int i = 0; i < item.getValue().size(); i++) {
                fieldNames[i] = item.getValue().get(i).getTableFieldName();
            }
            List<Object> objList = formService.queryFieldValue(fieldNames, item.getKey(), formDataId);
            if(CollectionUtils.isNotEmpty(objList)) {
                for (Object obj : objList) {
                    if(null != obj && obj.getClass().isArray()) {
                        Object[] arrayObj = (Object[]) obj;
                        for (int i = 0; i<arrayObj.length; i++) {
                            sourceValueHandle(sourceValueMap, item.getValue().get(i), objList.size(), arrayObj[i]);
                        }
                    } else {
                        sourceValueHandle(sourceValueMap, item.getValue().get(0), objList.size(), obj);
                    }
                }
            } else {
                //没有原始值时
                for (int i = 0; i < item.getValue().size(); i++) {
                    TableFieldMap fieldMap = item.getValue().get(i).convertFieldMap();
                    fieldMap.setValues(null);
                    sourceValueMap.put(item.getValue().get(i).getTableFieldId(), fieldMap);
                }
            }
        }//for
        return sourceValueMap;
    }

    /**
     * 分类对象赋值
     * @param classifyMap 分类map
     * @param collections 集合
     * @param key 分类map Key
     * @param initialCapacity 集合初始容量
     */
    private Collection<?> classifyMapAsgmt(Map classifyMap,Collection<?> collections, String key, Integer initialCapacity) {
        if(null == collections) {
            if(null == initialCapacity || initialCapacity == 0) {
                collections = new ArrayList<Object>();
            } else {
                collections = new ArrayList<Object>(initialCapacity);
            }
            classifyMap.put(key, collections);
        }
        return collections;
    }

    /**
     * 处理原始值
     * @param valueMap 原始值分类map
     * @param fieldInfo 字段信息
     * @param size 集合初始容量
     * @param value 初始值
     */
    private void sourceValueHandle(Map<String, TableFieldMap> valueMap, LogFieldInfo fieldInfo, int size, Object value) {
        String fieldId = fieldInfo.getTableFieldId();
        TableFieldMap fieldMap = valueMap.get(fieldId);
        if(null == fieldMap) {
            fieldMap = fieldInfo.convertFieldMap();
            fieldMap.setValues(new ArrayList<Object>(size));
            valueMap.put(fieldInfo.getTableFieldId(), fieldMap);
        }
        if(null != value) {
            fieldMap.getValues().add(StringUtils.handleNull(value));
        }
    }

    /**
     * 添加修改日志
     * @param changeLogs 修改日志列表
     * @param fieldMap 字段MAP对象
     * @param formDataId 表单数据ID
     * @param formId 表单ID
     * @param userId 用户ID
     * @param newValue 新值
     * @param remarks 备注
     */
    private void addChangeLog(List<TFormFieldChangeLog> changeLogs, TableFieldMap fieldMap, String formDataId,
                              String formId, String userId, String newValue, String remarks, String operateType) {
        TFormFieldChangeLog changeLog = new TFormFieldChangeLog();
        changeLog.setFormDataId(formDataId);
        changeLog.setFormId(formId);
        if(null == fieldMap.getValues()) {
            changeLog.setSourceValue(null);
        } else {
            changeLog.setSourceValue(ArrayUtils.arrayToString(fieldMap.getValues().toArray(), IWebConstant.MULTI_VALUE_SPLIT));
        }
        changeLog.setUserId(userId);
        changeLog.setValue(newValue);
        changeLog.setTableFieldId(fieldMap.getTableFieldId());
        changeLog.setFlag(operateType);
        changeLog.setRemarks(remarks);
        changeLogs.add(changeLog);
    }

}
