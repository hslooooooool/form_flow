package cn.com.smart.form.service;

import java.util.Map;

import cn.com.smart.bean.SmartResponse;
import cn.com.smart.form.bean.QueryFormData;

/**
 * 处理表单数据接口
 * @author lmq
 * @create 2015年8月5日
 * @version 1.0 
 * @since 
 *
 */
public interface IFormDataService {

	/**
	 * 获取表单数据
	 * @param formDataId 表单数据ID
	 * @param formId 表单ID
	 * @param userId 用户ID
	 * @return 返回查询结果；
	 */
	public SmartResponse<QueryFormData> getFormData(String formDataId, String formId, String userId);
	
	/**
	 * 根据表单数据ID获取表单数据
	 * @param formDataId 表单数据Id
	 * @param formId 表单ID
	 * @return 返回查询结果
	 */
	public SmartResponse<QueryFormData> getFormDataByFormDataId(String formDataId,String formId);
	
	/**
	 * 保存或更新表单
	 * @param datas 表单数据
	 * @param formDataId 表单数据Id
	 * @param formId 表单Id
	 * @param userId 用户Id
	 * @param formState 表单状态 
	 * <p>1--保存(但未提交) </p>
     * <p>0-- 保存（并提交）</p>
	 * @return 返回保存或更新结果;
	 */
	public SmartResponse<String> saveOrUpdateForm(Map<String,Object> datas, String formDataId, 
	        String formId, String userId, Integer formState);
	
	/**
	 * 保存表单
	 * @param datas 表单数据Map对象
	 * @param formId 表单ID
	 * @param formDataId 表单数据ID
	 * @param userId 用户ID
	 * @param formState 表单状态 
	 * <p>1--保存(但未提交) </p>
     * <p>0-- 保存（并提交）</p>
	 * @return 返回表单数据ID
	 */
	public String saveForm(Map<String,Object> datas, String formId, String formDataId, String userId, Integer formState);
	
	/**
     * 保存表单
     * @param datas 表单数据Map对象
     * @param formId 表单ID
     * @param userId 用户ID
     * @param formState 表单状态 
     * <p>1--保存(但未提交) </p>
     * <p>0-- 保存（并提交）</p>
     * @return 返回表单数据ID
     */
    public String saveForm(Map<String,Object> datas, String formId, String userId, Integer formState);
	
	/**
	 * 更新表单数据
	 * @param datas 表单数据Map对象
	 * @param formId 表单ID
	 * @param formDataId 表单数据ID
	 * @param userId 用户ID
	 * @param formState 表单状态 
	 * <p>1--保存(但未提交) </p>
     * <p>0-- 保存（并提交）</p>
	 * @return 更新成功返回true；否则返回false
	 */
	public boolean updateForm(Map<String,Object> datas, String formId, String formDataId, String userId, Integer formState);
	
	/**
	 * 获取字段中的附件ID
	 * @param formId 表单ID
	 * @param plugins 插件名称
	 * @param formDataId 表单数据ID
	 * @return 返回获取结果；获取成功，通过getDatas()方法获取返回结果
	 */
	public SmartResponse<String> getFieldInAttIds(String formId, String[] plugins, String formDataId);
}
