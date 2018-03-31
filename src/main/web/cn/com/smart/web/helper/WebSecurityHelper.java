package cn.com.smart.web.helper;

import com.mixsmart.security.SecurityUtils;
import com.mixsmart.utils.StringUtils;

import cn.com.smart.init.config.InitSysConfig;

/**
 * 安全助手
 * @author lmq 
 * @version 1.0
 * @since 1.0
 */
public class WebSecurityHelper {

    private static final String SECRET_KEY = InitSysConfig.getInstance().getValue("secret.key");
    
    /**
     * 加密；使用系统默认配置的秘钥加密
     * @param value 需要加密的值
     * @return 返回加密后的值
     */
    public static String encrypt(String value) {
        if(StringUtils.isNotEmpty(value)) {
            value = SecurityUtils.desEncode(value, SECRET_KEY);
        }
        return value;
    }
    
    /**
     * 解密；使用系统默认配置的秘钥解密
     * @param value 需要解密的值
     * @return 返回解密后的值
     */
    public static String decrypt(String value) {
        if(StringUtils.isNotEmpty(value)) {
            value = SecurityUtils.desDecode(value, SECRET_KEY);
        }
        return value;
    }
    
    /**
     * 给URI添加授权标识
     * @param currentUri 当前URI
     * @param id 操作标识
     * @param uri 操作对应的URI
     * @return 返回添加授权token后的URI
     */
    public static String addUriAuth(String currentUri, String id, String uri) {
        if(StringUtils.isNotEmpty(uri) 
                && StringUtils.isNotEmpty(id) 
                && StringUtils.isNotEmpty(currentUri)) {
            String token = currentUri+"##"+id+"##"+uri;
            if(uri.contains("?")) {
                uri = uri + "&authToken=";
            } else {
                uri = uri + "?authToken=";
            }
            uri = uri + WebSecurityHelper.encrypt(token);
        }
        return StringUtils.handleNull(uri);
    }
    
    /**
     * 解析authToken内容
     * @param authToken
     * @return 返回一个数组；
     * <p>如果 <code>authToken</code> 值为空，则返回null；</p>
     * 否则返回：一个数组；
     * 第一个值为页面列表对应的URL；
     * 第二个值为：操作权限ID；
     * 第三个值为：操作对应的URI
     */
    public static String[] parseAuthToken(String authToken) {
        String[] array = null;
        if(StringUtils.isEmpty(authToken)) {
            return array;
        }
        authToken = WebSecurityHelper.decrypt(authToken);
        array = authToken.split("##");
        return array;
    }
}
