package cn.com.smart.form.interceptor;

import cn.com.smart.service.SmartContextService;
import com.mixsmart.enums.YesNoType;
import com.mixsmart.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提交表单上下文环境.
 *
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
public class SubmitFormContext {

    private static final Logger logger = LoggerFactory.getLogger(SubmitFormContext.class);

    private List<ISubmitFormInterceptor> interceptors;

    private static SubmitFormContext instance = new SubmitFormContext();

    private SubmitFormContext() {
        init();
    }

    public static SubmitFormContext getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    private void init() {
        if(null != interceptors) {
            interceptors.addAll(SmartContextService.finds(ISubmitFormInterceptor.class));
        } else {
            interceptors = SmartContextService.finds(ISubmitFormInterceptor.class);
        }
    }

    /**
     * 注册一个拦截器
     * @param submitInterceptor 拦截器
     */
    public void registor(ISubmitFormInterceptor submitInterceptor) {
        if(null != submitInterceptor) {
            if(null == interceptors) {
                interceptors = new ArrayList<ISubmitFormInterceptor>();
            }
            interceptors.add(submitInterceptor);
        }
    }

    /**
     * 移除一个拦截器
     * @param submitInterceptor 要删除的拦截器
     */
    public void remove(ISubmitFormInterceptor submitInterceptor) {
        if(null != submitInterceptor && null != interceptors && interceptors.size()>0) {
            interceptors.remove(submitInterceptor);
        }
    }

    /**
     * 执行指定表单拦截器的前置方法
     * @param interceptor 拦截器实现类
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据
     * @param userId 用户ID
     * @return 如果执行成功返回true；否则返回false
     */
    public boolean before(ISubmitFormInterceptor interceptor, String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        boolean is = interceptor.before(formId, formDataId, dataMap, userId);
        if(!is) {
            LoggerUtils.error(logger, "["+interceptor.toString()+"]拦截器返回了[false]..");
        }
        return is;
    }

    /**
     * 执行所有表单拦截器的前置方法
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据
     * @param userId 用户ID
     * @return 如果执行成功返回true；否则返回false
     */
    public boolean before(String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        boolean is = true;
        for (ISubmitFormInterceptor interceptor : interceptors) {
            is = is && this.before(interceptor, formId, formDataId, dataMap, userId);
        }
        return is;
    }

    /**
     * 执行指定表单拦截器的后置方法
     * @param interceptor 拦截器实现类
     * @param state 状态
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据
     * @param userId 用户ID
     */
    public void after(ISubmitFormInterceptor interceptor, YesNoType state, String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        interceptor.after(state, formId, formDataId, dataMap, userId);
    }

    /**
     * 执行所有表单拦截器的后置方法
     * @param state 状态
     * @param formId 表单ID
     * @param formDataId 表单数据ID
     * @param dataMap 表单数据
     * @param userId 用户ID
     */
    public void after(YesNoType state, String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        for (ISubmitFormInterceptor interceptor : interceptors) {
            this.after(interceptor, state, formId, formDataId, dataMap, userId);
        }
    }
}
