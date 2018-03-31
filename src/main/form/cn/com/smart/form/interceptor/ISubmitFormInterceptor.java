package cn.com.smart.form.interceptor;

import com.mixsmart.enums.YesNoType;

import java.util.Map;

/**
 *
 * 提交表单拦截接口；提供表单保存前拦截的方法
 * 和表单保存后拦截的方法
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public interface ISubmitFormInterceptor {

    /**
     * 表单保存或更新前拦截
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据MAP对象
     * @param userId 用户ID
     * @return 返回ture或false；如果返回false表单将不会保存
     */
    boolean before(String formId, String formDataId, Map<String, Object> dataMap, String userId);

    /**
     * 表单保存或更新后拦截
     * @param state 表单保存或更新状态
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据MAP对象
     * @param userId 用户ID
     */
    void after(YesNoType state, String formId, String formDataId, Map<String, Object> dataMap, String userId);

}
