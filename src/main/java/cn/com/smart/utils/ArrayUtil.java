package cn.com.smart.utils;

/**
 * 数组工具类；
 * 该类已过时，请使用{@link com.mixsmart.utils.ArrayUtils}类代替
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
@Deprecated
public class ArrayUtil {

	
	/**
	 * 数组转化为字符串
	 * @param objs 数组
	 * @param separator 分隔符
	 * @return 返回数组转化成功后的字符串;失败返回：null
	 */
	public static String arrayToString(Object[] objs,String separator) {
		StringBuffer strBuff = null;
		if(null != objs && objs.length>0) {
			if(StringUtil.isEmpty(separator)) {
				separator = "";
			}
			strBuff = new StringBuffer();
			for (int i=0;i<objs.length;i++) {
				if(i < objs.length-1 ) {
					strBuff.append(String.valueOf(objs[i])+separator);
				} else {
					strBuff.append(String.valueOf(objs[i]));
				}
				
			}
		}
		objs = null;
		return (null != strBuff)?strBuff.toString():null;
	}
	
	
	/**
	 * 字符串转化为数组
	 * @param value 原字符串
	 * @param separator 分隔符
	 * @return 返回字符串分割成功后的数组
	 */
	public static String[] stringToArray(String value,String separator) {
		String[] array = null;
		if(!StringUtil.isEmpty(value) && !StringUtil.isEmpty(separator)) {
			array = value.split(separator);
		}
		value = null;
		return array;
	}
	
	
	/**
	 * 按separater分离成数组,判断该数组里面是否包含subStr
	 * @param str
	 * @param subStr
	 * @param separater
	 * @return 包含返回：true；否则返回：false
	 */
	public static boolean isArrayContains(String str,String subStr,String separater) {
		boolean is = false;
		if(null != str) {
			if(null == subStr) {
				is = true;
			} else {
				String[] strArray = str.split(separater);
				for (int i = 0; i < strArray.length; i++) {
					if(subStr.equals(strArray[i].trim())) {
						is = true;
						break;
					}
				}
			}
		}
		return is;
	}
	
	/**
	 * 判断该数组里面是否包含subObj
	 * @param objs
	 * @param subObj
	 * @return 包含返回：true；否则返回：false
	 */
	public static boolean isArrayContains(Object[] objs,Object subObj) {
		boolean is = false;
		if(null == subObj) {
			is = true;
		}
		if(objs.length>0 && null != subObj) {
			for (Object obj : objs) {
				if(subObj.toString().equals(obj.toString())) {
					is = true;
					break;
				}
			}
		} else 
			is = false;
		return is;
	}
	
	/**
	 * 按separater分离成数组,判断该数组里面是否包含subStr(不区分大小写)
	 * @param str
	 * @param subStr
	 * @param separater
	 * @return 包含返回：true；否则返回：false
	 */
	public static boolean isArrayContainsIgnoreCase(String str,String subStr,String separater) {
		boolean is = false;
		if(null != str) {
			if(null == subStr) {
				is = true;
			} else {
				String[] strArray = str.split(separater);
				for (int i = 0; i < strArray.length; i++) {
					if(subStr.equalsIgnoreCase(strArray[i].trim())) {
						is = true;
						break;
					}
				}
			}
		}
		return is;
	}
	
}
