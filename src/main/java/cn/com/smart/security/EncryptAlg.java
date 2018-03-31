package cn.com.smart.security;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import com.mixsmart.utils.StringUtils;

/**
 * 加密算法；该类已过时；请使用{@link com.mixsmart.security.SecurityUtils} 类代替
 * @author lmq
 *
 */
@Deprecated
public class EncryptAlg {

	/**
	 * MD5加密
	 * @param value
	 * @return 返回MD5加密后的值
	 */
	public static String MD5(String value) {
		if(StringUtils.isNotEmpty(value)) {
			byte[] bytes;
			try {
				bytes = value.getBytes("UTF-8");
				bytes = Coder.encryptMD5(bytes);
				StringBuffer md5StrBuff = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
						md5StrBuff.append("0").append(Integer.toHexString(0xFF & bytes[i]));
					else
						md5StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
				}
				value = md5StrBuff.toString();
			} catch (UnsupportedEncodingException e) {
				value = null;
				e.printStackTrace();
			} catch (Exception e) {
				value = null;
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * SHA加密
	 * @param value
	 * @return 返回加密后的值
	 */
	public static String SHA(String value) {
		if(StringUtils.isNotEmpty(value)) {
			byte[] bytes;
			try {
				bytes = value.getBytes("UTF-8");
				bytes = Coder.encryptSHA(bytes);
				value = byteArrayToHex(bytes);
			} catch (UnsupportedEncodingException e) {
				value = null;
				e.printStackTrace();
			} catch (Exception e) {
				value = null;
				e.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * 字符数组转化为十六进制
	 * @param byteArray
	 * @return 返回十六进制字符串
	 */
	private static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		char[] resultCharArray =new char[byteArray.length * 2];
		int index = 0;
		for(byte b :byteArray){
			resultCharArray[index++] = hexDigits[b>>>4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}
	
	/**
	 * DES加密
	 * @param value
	 * @param key
	 * @return 返回加密后的值
	 */
	public static String DESEncode(String value,String key) {
		if(StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(key)) {
			try {
				byte[] bytes = value.getBytes("UTF-8");
				bytes = DESCoder.encrypt(bytes,key);
				value = Base64.encodeBase64String(bytes);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	/**
	 * DES解密
	 * @param value
	 * @param key
	 * @return 返回解密后的值
	 */
	public static String DESDecode(String value,String key) {
		try {
			byte[] bytes = Base64.decodeBase64(value);
			bytes = DESCoder.decrypt(bytes,key);
			value = new String(bytes,"UTF8");
		} catch (UnsupportedEncodingException e) {
			value = null;
			e.printStackTrace();
		} catch (Exception e) {
			value = null;
			e.printStackTrace();
		}
		return value;
	}
}
