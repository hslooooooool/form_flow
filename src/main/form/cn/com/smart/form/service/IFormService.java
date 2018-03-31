package cn.com.smart.form.service;


import cn.com.smart.bean.SmartResponse;
import cn.com.smart.form.bean.LogFieldInfo;
import cn.com.smart.form.bean.entity.TForm;

import java.util.List;
import java.util.Map;

/**
 * 表单服务类
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public interface IFormService {

    /**
     * 解析表单
     * @param form 表单实体对象
     * @param dataMap 数据
     * @return 返回SmartResponse对象
     */
    SmartResponse<String> parseForm(TForm form, Map<String, Object> dataMap);

    /**
     * 查询表单字段值
     * @param fieldNames 字段名称数组
     * @param tableName 表单名称
     * @param formDataId 表单数据ID
     * @return 返回字段值列表；<br />
     * 如果字段是多个（两个及两个以上），则返回的Object为一个数组对象；返回的数组顺序和字段名称数组顺序一致；<br />
     * 如：字段名称数组第一个为name，则返回Object数组的第一个值为name字段对应的值
     */
    List<Object> queryFieldValue(String[] fieldNames, String tableName, String formDataId);

    /**
     * 获取需要记录日志的字段信息
     * @param formId 表单ID
     * @return 返回字段信息列表
     */
    List<LogFieldInfo> getLogFieldInfo(String formId);
    
    /**
     * 获取实例标题（流程实例标题或表单实例标题）
     * @param datas 表单数据
     * @param formId 表单ID
     * @param userId 用户ID
     * @param name 名称（当获取的是流程实例标题时为流程名称，当获取的时表单实例标题时为表单名称）
     * @return 返回实例标题
     */
    String getInstanceTitle(Map<String, Object> datas, String formId, String userId, String name);
}
