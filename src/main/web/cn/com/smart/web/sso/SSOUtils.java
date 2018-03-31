package cn.com.smart.web.sso;

import cn.com.smart.constant.IConstant;
import cn.com.smart.init.config.InitSysConfig;

import com.mixsmart.utils.StringUtils;

/**
 * SSO工具类
 * @author lmq <br />
 * 2016年12月20日
 * @version 1.0
 * @since 1.0
 */
public class SSOUtils {

	/**
	 * 获取客户端的白名单IP地址
	 * @return 返回数组
	 */
	public static String[] getClientWhiteIps() {
		String whiteIp = InitSysConfig.getInstance().getValue("sso.client.ips");
		String[] whiteIps = null;
		if(StringUtils.isNotEmpty(whiteIp)) {
			whiteIps = whiteIp.split(IConstant.MULTI_VALUE_SPLIT);
		}
		return whiteIps;
	}
	
	/**
	 * 获取密匙
	 * @return 返回密匙
	 */
	public static String getSecretKey() {
		String secretKey = InitSysConfig.getInstance().getValue("secret.key");
		secretKey = StringUtils.isEmpty(secretKey)?"123456":secretKey;
		return secretKey;
	}
	
	/**
	 * 获取SSO服务器访问地址
	 * @return
	 */
	public static String getSSOServerURL() {
		return InitSysConfig.getInstance().getValue("sso.service");
	}
}
