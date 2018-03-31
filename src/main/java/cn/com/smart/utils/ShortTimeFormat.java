package cn.com.smart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 短时间格式
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年8月22日
 */
@Deprecated
public class ShortTimeFormat {

	private Date pDate;
	
	private Date currentDate;
	
	private String dateFormatter = "yyyy-MM-dd HH:mm:ss";
	
	
	private final static long SECONDS = 1000;
	
	private final static long MINUTE = 60*SECONDS;
	
	private final static long HOURS = 60*MINUTE;
	
	private final static long DAY = 24*HOURS; 
	
	private final static long MONTH = 30*DAY;
	
	private final static long YEAR = 12*MONTH;
	
	
	/**
	 * 
	 * @param pDate
	 */
	public ShortTimeFormat(Date pDate) {
		this.pDate = pDate;
		this.currentDate = new Date();
	}
	
	/**
	 * 
	 * @param dateStr
	 * @throws Exception
	 */
	public ShortTimeFormat(String dateStr) throws Exception {
		SimpleDateFormat dateFormate = new SimpleDateFormat(dateFormatter);
		try {
			this.pDate = dateFormate.parse(dateStr);
			this.currentDate = new Date();
		}catch (Exception e) {
			throw new Exception("传入的时间格式错误");
		}
	}
	
	/**
	 * 转化为短时间格式
	 * @return 转化后的结果
	 */
	public String toShortTime() {
		long pTime = pDate.getTime();
		long cTime = currentDate.getTime();
		long intervalTime = cTime-pTime;
		String value = toShortHMS(intervalTime);
		return value;
	}
	
	/**
	 * 转换为时分秒
	 * @param intervalTime
	 * @return 转化后的结果
	 */
	protected String toShortHMS(long intervalTime) {
		String value = null;
		if(intervalTime < DAY) {
			int tNum = 0;
			String unit = null;
			if(intervalTime<MINUTE) {
				tNum = (int) (intervalTime/SECONDS);
				unit = "秒";
			} else if(intervalTime<HOURS) {
				tNum = (int)(intervalTime/MINUTE);
				unit = "分钟";
			} else {
				tNum = (int)(intervalTime/HOURS);
				unit = "小时";
			}
			if(tNum==0) {
				tNum = 1;
			}
			value = tNum+unit+"前";
		} else {
			value = toShortDay(intervalTime);
		}
		return value;
	}
	
	/**
	 * 转换为天
	 * @param intervalTime
	 * @return 转化后的结果
	 */
	protected String toShortDay(long intervalTime) {
		String value = null;
		if(intervalTime<MONTH) {
			int tNum = (int) (intervalTime/DAY);
			if(tNum==0) {
				tNum = 1;
			}
			value = tNum+"天前";
		} else {
			value = toShortMonth(intervalTime);
		}
		return value;
	}
	
	/**
	 * 转化为月
	 * @param intervalTime 
	 * @return 转化后的结果
	 */
	protected String toShortMonth(long intervalTime) {
		String value = null;
		if(intervalTime<YEAR) {
			int tNum = (int) (intervalTime/MONTH);
			if(tNum==0) {
				tNum = 1;
			}
			value = tNum+"月前";
		} else {
			value = toShortYear(intervalTime);
		}
		return value;
		
	}
	
	/**
	 * 转化为年
	 * @param intervalTime
	 * @return 转化后的结果
	 */
	protected String toShortYear(long intervalTime) {
		String value = null;
		int tNum = (int) (intervalTime/YEAR);
		if(tNum==0) {
			tNum = 1;
		}
		value = tNum+"年前";
		return value;
	}
	
}
