package cn.com.smart.constant;

/**
 * 系统常量定义
 * @author lmq
 *
 */
public interface IConstant {
	
	/**
	 * 系统配置文件
	 */
	public static final String SYS_CONFIG_FILE = "/sysconfig.properties";
	
	/**
	 * 开发工具配置文件
	 */
	public static final String DEV_TOOL_CONFIG_FILE = "/devtool-config.properties";
	
	/**
	* 项目状态----开发模式
	*/
	public final static String PROJECT_DEV_MODEL = "1";
	
	/**
	* 项目状态----产品模式
	*/
	public final static String PROJECT_PRODUCT_MODEL = "0";
	
	/**
	 * 角色状态---超级管理员角色
	 */
	public final static String ROLE_SUPER_ADMIN = "super_admin";
	
	
	/////////////操作结果常量定义///////////////////
	/**
	* 操作标识--成功
	*/
	public final static String OP_SUCCESS = "1";
	/**
	* 成功标识返回的默认提示信息
	*/
	public final static String OP_SUCCESS_MSG = "数据操作成功";
	
	/**
	* 操作标识--没有数据
	*/
	public final static String OP_NOT_DATA_SUCCESS = "0";
	/**
	* 没有数据标识返回的默认提示信息
	*/
	public final static String OP_NOT_DATA_SUCCESS_MSG = "没有查询到相关数据";
	
	/**
	* 操作标识--有敏感词汇
	*/
	public final static String OP_SW = "2";
	/**
	* 有敏感词汇标识返回的默认提示信息
	*/
	public final static String OP_SW_MSG = "有敏感词汇";
	
	/**
	* 操作标识--失败
	*/
	public final static String OP_FAIL = "-1";
	/**
	* 有敏感词汇标识返回的默认提示信息
	*/
	public final static String OP_FAIL_MSG = "数据操作失败";
	
	/**
	 * 多值分隔符“,”
	 */
	public final static String MULTI_VALUE_SPLIT = ",";
	
	/**
	 * 多条语句间分隔符“;”
	 */
	public final static String MULTI_STATEMENT_SPLIT = ";";
	
	/**
	 * 组合值分隔符“_”
	 */
	public static final String COMBINE_VALUE_SEPARATOR = "_";
	
}
