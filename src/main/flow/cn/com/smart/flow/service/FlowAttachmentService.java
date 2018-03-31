package cn.com.smart.flow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.exception.DaoException;
import cn.com.smart.flow.bean.SubmitFormData;
import cn.com.smart.flow.bean.entity.TFlowAttachment;
import cn.com.smart.flow.dao.FlowAttachmentDao;
import cn.com.smart.res.SQLResUtil;
import cn.com.smart.service.impl.MgrServiceImpl;
import cn.com.smart.web.bean.entity.TNAttachment;
import cn.com.smart.web.service.OPService;

import com.mixsmart.utils.CollectionUtils;
import com.mixsmart.utils.LoggerUtils;
import com.mixsmart.utils.StringUtils;

/**
 * 流程附件
 * @author lmq
 * @version 1.0 
 * @since 
 *
 */
@Service
public class FlowAttachmentService extends MgrServiceImpl<TFlowAttachment> {
	@Autowired
	private OPService opServ;
	
	/**
	 * 更新附件信息
	 * @param data
	 * @param userId
	 * @return
	 */
	public boolean updateAtt(SubmitFormData data,String userId) {
		boolean is = false;
		if(null != data && StringUtils.isNotEmpty(userId)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("formId", data.getFormId());
			param.put("userId", userId);
			try {
				List<TFlowAttachment> processAtts = super.findByParam(param).getDatas();
				if(null != processAtts && processAtts.size()>0) {
					for (TFlowAttachment processAtt : processAtts) {
						processAtt.setOrderId(data.getOrderId());
						processAtt.setTaskId(data.getTaskId());
						processAtt.setTaskKey(data.getTaskKey());
						processAtt.setFormId("");
						processAtt.setUserId("");
					}
					is = OP_SUCCESS.equals(super.update(processAtts).getResult());
				}
				processAtts = null;
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return is;
	}
	
	/**
	 * 获取附件列表根据流程实例ID
	 * @param orderId 流程实例id
	 * @return 返回附件实体列表
	 */
	public List<TNAttachment> getAttachmentsByOrderId(String orderId) {
		return getDao().queryAttachmentByOrderId(orderId);
	}
	
	/**
	 * 获取附件列表根据流程实例ID及任务KEY
	 * @param orderId 流程实例id
	 * @param taskKey 
	 * @return 返回附件实体列表
	 */
	public List<TNAttachment> getAttachments(String orderId, String taskKey) {
		return getDao().queryAttachment(orderId, taskKey);
	}


	@Override
	public FlowAttachmentDao getDao() {
		return (FlowAttachmentDao)super.getDao();
	}
	
	/**
	 * 更新表单字段为空
	 * <p>请使用{@link cn.com.smart.form.service.FormAttachmentService#updateFormField(String, String, String)} 方法替换</p>
	 * @param fieldId
	 * @param formDataId
	 * @param attId
	 */
	@Deprecated
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
			String tableName = StringUtils.handNull(array[0]);
			String fieldName = StringUtils.handNull(array[1]);
			param.clear();
			param.put("formDataId", formDataId);
			
			String querySql = SQLResUtil.getOpSqlMap().getSQL("get_field_value");
			if(StringUtils.isNotEmpty(querySql)) {
				String sourceValue = null;
				String targetValue = null;
				querySql = querySql.replace("${tableName}", tableName).replace("${fieldName}", fieldName);
				List<Object> list = getDao().queryObjSql(querySql, param);
				if(CollectionUtils.isNotEmpty(list)) {
					sourceValue = StringUtils.handNull(list.get(0));
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
}
