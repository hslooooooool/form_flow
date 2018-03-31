package cn.com.smart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.mixsmart.utils.StringUtils;

/**
 * 日期工具类
 * @author lmq
 * @version 1.0
 * @since JDK1.6
 * 2015年8月22日
 */
public class DateUtil extends BaseUtil {

	/**
	 * 时间转为日期格式 <br />
	 * 如：formatter参数为空时，默认格式为“yyyy-MM-dd HH:mm:ss”
	 * @param date
	 * @param formatter 
	 * @return 日期转换后的字符串 
	 */
	public static String dateToStr(Date date,String formatter) {
		String value = null;
		if(null != date) {
			if(StringUtils.isEmpty(formatter)) {
				formatter = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			value = dateFormat.format(date);
			dateFormat = null;
		}
		return value;
	}
	
}
