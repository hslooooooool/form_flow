package cn.com.smart.web.sso;

import com.mixsmart.security.SecurityUtils;

public class SSOHelper {

	/**
	 * 获取加密KEY
	 * @param ips
	 * @param validTime
	 * @return
	 */
	public static String getKey(String ips, long validTime) {
		return SecurityUtils.md5(ips+validTime);
	}
	
	/**
	 * 获取有效时间(加密)
	 * @param startTime
	 * @param validTime
	 * @return
	 */
	public static String getValidTime(long validTime) {
		String validTimeStr = validTime+"";
		return SecurityUtils.desEncode(validTimeStr, SSOUtils.getSecretKey());
	}
	
	/**
	 * 获取有效时间（解密）
	 * @param validTimeStr
	 * @return
	 */
	public static Long getValidTime(String validTimeStr) {
		validTimeStr = SecurityUtils.desDecode(validTimeStr, SSOUtils.getSecretKey());
		return Long.parseLong(validTimeStr);
	}
}
