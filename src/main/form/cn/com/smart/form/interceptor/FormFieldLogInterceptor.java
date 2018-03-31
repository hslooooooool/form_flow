package cn.com.smart.form.interceptor;

import cn.com.smart.form.log.FormFieldLog;
import com.mixsmart.enums.YesNoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * 表单字段日志记录拦截
 * @author lmq
 * @version 1.0
 * @since 1.0
 */
@Component
public class FormFieldLogInterceptor implements ISubmitFormInterceptor {

    @Autowired
    private FormFieldLog formFieldLog;

    @Override
    public boolean before(String formId, String formDataId, Map<String, Object> dataMap, String userId) {
        formFieldLog.record(formId, formDataId, dataMap, userId);
        return true;
    }

    @Override
    public void after(YesNoType state, String formId, String formDataId, Map<String, Object> dataMap, String userId) {

    }
}
