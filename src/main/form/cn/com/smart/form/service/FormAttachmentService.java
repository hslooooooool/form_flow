package cn.com.smart.form.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.form.bean.entity.TFormAttachment;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.service.OPService;

/**
 * 表单附件服务类
 * @author lmq  2017年8月14日
 * @version 1.0
 * @since 1.0
 */
@Service
public class FormAttachmentService extends MgrServiceImpl<TFormAttachment> {

    /**
     * 过期时间
     */
    private static long EXPIRE_TIME = 24 * 60 * 60 * 1000;
    
    @Autowired
    private OPService opServ;
    
    /**
     * 保存表单附件
     * @param att 附件实体对象
     * @param formId  表单ID
     * @param formDataId 表单数据ID
     * @param userId 用户ID
     * @return 返回表单附件保存结果
     */
    public SmartResponse<String> saveAttachment(TNAttachment att, 
            String formId, String formDataId, String userId) {
        SmartResponse<String> smartResp = new SmartResponse<String>();
        if(null == att || StringUtils.isEmpty(formId)) {
            throw new NullArgumentException("参数为空");
        }
        TFormAttachment formAtt = new TFormAttachment();
        formAtt.setAttachmentId(att.getId());
        formAtt.setFormDataId(formDataId);
        formAtt.setFormId(formId);
        formAtt.setUserId(userId);
        formAtt.setCreateTimestamp(System.currentTimeMillis());
        smartResp = super.save(formAtt);
        return smartResp;
    }
    
    /**
     * 更新表单附件中的表单数据字段；
     * 通过附件选项卡上传附件时，因为表单数据未正式创建，所以当时的formDataId为临时的Id;
     * @param formDataId 表单数据ID
     * @param tmplFormDataId 临时的表单数据ID
     */
    public void updateFormDataId(String formDataId, String tmplFormDataId) {
        if(StringUtils.isNotEmpty(formDataId) && StringUtils.isNotEmpty(tmplFormDataId)) {
            Map<String, Object> params = new HashMap<String, Object>(2);
            params.put("formDataId", formDataId);
            params.put("tmplFormDataId", tmplFormDataId);
            super.execute("update_form_data_id", params);
        }
    }
    
    
    /**
     * 更新表单字段为空 
     * @param fieldId
     * @param formDataId
     * @param attId
     */
    public void updateFormField(String fieldId, String formDataId, String attId) {
        if(StringUtils.isEmpty(fieldId) || StringUtils.isEmpty(formDataId) || StringUtils.isEmpty(attId)) {
            return;
        }
        LoggerUtils.debug(logger, "正在将附件字段更新为空");
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("fieldId", fieldId);
        SmartResponse<Object> smartResp = opServ.getDatas("get_tablename_fieldname_byfieldid", param);
        if(OP_SUCCESS.equals(smartResp.getResult())) {
            Object[] array = (Object[])smartResp.getDatas().get(0);
            String tableName = StringUtils.handleNull(array[0]);
            String fieldName = StringUtils.handleNull(array[1]);
            param.clear();
            param.put("formDataId", formDataId);
            
            String querySql = SQLResUtil.getOpSqlMap().getSQL("get_field_value");
            if(StringUtils.isNotEmpty(querySql)) {
                String sourceValue = null;
                String targetValue = null;
                querySql = querySql.replace("${tableName}", tableName).replace("${fieldName}", fieldName);
                List<Object> list = getDao().queryObjSql(querySql, param);
                if(CollectionUtils.isNotEmpty(list)) {
                    sourceValue = StringUtils.handleNull(list.get(0));
                }
                if(StringUtils.isNotEmpty(sourceValue)) {
                    String[] sourceValues = sourceValue.split(MULTI_VALUE_SPLIT);
                    sourceValues = ArrayUtils.removeElement(sourceValues, attId);
                    targetValue = com.mixsmart.utils.ArrayUtils.arrayToString(sourceValues, MULTI_VALUE_SPLIT);
                }
                String fieldUpateValue = fieldName+"=";
                if(StringUtils.isEmpty(targetValue)) {
                    fieldUpateValue += "null";
                } else {
                    fieldUpateValue += "'"+targetValue+"'";
                }
                
                String updateSql = SQLResUtil.getOpSqlMap().getSQL("update_field_value");
                if(StringUtils.isNotEmpty(updateSql)) {
                    updateSql = updateSql.replace("${tableName}", tableName).replace("${fieldName}", fieldUpateValue);
                    if(getDao().executeSql(updateSql, param)>0) {
                        LoggerUtils.debug(logger, tableName+"表中的["+fieldName+"]附件字段更新为空[成功]");
                    } else {
                        LoggerUtils.error(logger, tableName+"表中的["+fieldName+"]附件字段更新为空[失败]");
                    }
                } else {
                    LoggerUtils.error(logger, "[update_field_value]对应的SQL语句为空");
                }
            } else {
                LoggerUtils.error(logger, "[get_field_value]对应的SQL语句为空");
            }
        } else {
            LoggerUtils.error(logger, "附件字段更新为空[失败]，原因是：获取表名及字段名称失败");
        }
    }
    
    /**
     * 删除过期的临时附件
     */
    public void deleteExpireTmpAtt() {
        LoggerUtils.debug(logger, "删除过期的临时表单附件");
        String sql = SQLResUtil.getOpSqlMap().getSQL("get_expire_form_attachment");
        if(StringUtils.isEmpty(sql)) {
            return;
        }
        Long expireTime = System.currentTimeMillis() - EXPIRE_TIME;
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("expireTime", expireTime);
        List<TFormAttachment> list = getDao().querySqlToEntity(sql, param, TFormAttachment.class);
        getDao().delete(list);
        LoggerUtils.debug(logger, "删除过期的临时表单附件[成功]");
    } 
    
    /**
     * 通过附件ID删除表单附件信息
     * @param attIds
     */
    public void deleteByAttIds(List<String> attIds) {
        if(CollectionUtils.isEmpty(attIds)) {
            return;
        }
        String sql = SQLResUtil.getOpSqlMap().getSQL("get_form_att_by_attid");
        if(StringUtils.isEmpty(sql)) {
            return;
        }
        LoggerUtils.debug(logger, "根据附件ID删除表单附件信息...");
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("attIds", attIds.toArray());
        List<TFormAttachment> list = getDao().querySqlToEntity(sql, param, TFormAttachment.class);
        if(null != list && list.size()>0) {
            getDao().delete(list);
        }
    }
    
    /**
     * 通过表单数据ID删除表单附件信息
     * @param formDataId
     */
    public void deleteByFormDataId(String formDataId) {
        if(StringUtils.isEmpty(formDataId)) {
            throw new NullArgumentException("formDataId参数为空");
        }
        String sql = SQLResUtil.getOpSqlMap().getSQL("get_form_att_by_formdataid");
        if(StringUtils.isEmpty(sql)) {
            throw new NullPointerException("get_form_att_by_formdataid对应的SQL为空");
        }
        LoggerUtils.debug(logger, "根据表单数据ID删除表单附件信息...");
        Map<String, Object> param = new HashMap<String, Object>(1);
        param.put("formDataId", formDataId);
        List<TFormAttachment> list = getDao().querySqlToEntity(sql, param, TFormAttachment.class);
        if(null != list && list.size()>0) {
            getDao().delete(list);
        }
    }
}
