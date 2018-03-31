package cn.com.smart.form.helper;

import com.mixsmart.exception.NullArgumentException;
import com.mixsmart.utils.StringUtils;

/**
 * 表单数据助手类
 * @author lmq  2017年8月28日
 * @version 1.0
 * @since 1.0
 */
public class FormDataHelper {

    public static final String APP_NEW_PREFIX = "NEW_";
    
    /**
     * 生成新的表单数据ID
     * @return
     */
    public static String createNewFormDataId() {
        String appDesignId = APP_NEW_PREFIX + StringUtils.createSerialNum();
        return appDesignId;
    }
    
    /**
     * 处理表单数据ID；去掉前缀
     * @param formDataId 新的表单数据ID
     * @return 返回处理后的表单数据ID
     */
    public static String handleFormDataId(String formDataId) {
        if(StringUtils.isEmpty(formDataId)) {
            throw new NullArgumentException("formDataId参数为空");
        } else {
            if(formDataId.startsWith(APP_NEW_PREFIX) ) {
                if(formDataId.length() > APP_NEW_PREFIX.length()) {
                    formDataId = formDataId.substring(APP_NEW_PREFIX.length(), formDataId.length());
                } else {
                    throw new IllegalArgumentException("formDataId参数不合法");
                }
            }
        }
        return formDataId;
    }
    
}
