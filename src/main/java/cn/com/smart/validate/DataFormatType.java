package cn.com.smart.validate;

/**
 * 验证数据格式
 * @author lmq
 *
 */
public interface DataFormatType {

	/**
	 * 数字（包括小数）
	 */
	public static final String NUM = "num";
	
	/**
	 * 小数
	 */
	public static final String DECIMAL = "decimal";
	
	/**
	 * 整数
	 */
	public static final String INTEGER = "integer";
	
	/**
	 * ip
	 */
	public static final String IP = "ip";
	
	/**
	 * 
	 */
	public static final String EMAIL = "email";
	
	/**
	 * 手机号码
	 */
	public static final String 	MOBILE_PHONE = "mobile_phone";
	
	/**
	 * 固定电话
	 */
	public static final String FIXED_TELPHONE = "fixed_telephone";
	
	/**
	 * 中文
	 */
	public static final String CHINESE = "chinese";
	
	/**
	 * qq
	 */
	public static final String QQ = "qq";
	
	/**
	 * 身份证号码
	 */
	public static final String ID_CARD = "idcard";
	
	
}
