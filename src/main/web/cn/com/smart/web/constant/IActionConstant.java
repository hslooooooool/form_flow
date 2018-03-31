package cn.com.smart.web.constant;

/**
 * action(controller)常量定义
 * @author lmq
 * @version 1.0 2015年9月9日
 * @since 1.0
 *
 */
public interface IActionConstant extends IWebConstant {
	
	/**
	 * 在SESSION里面存放---用户登录成功后的用户信息
	 */
	public static final String SESSION_USER_KEY = "ccs_user_info";
	
	/**
	 * 在SESSION里面存放---登录前访问的URI
	 */
	public static final String SESSION_LOGIN_BEFORE_URI = "login_before_uri";
	
	/**
	 * 存储在session里面的验证码
	 */
	public static final String SESSION_CAPTCHA_LOGIN = "captcha_login";
	
	/**
	 * 登陆
	 */
	public static final String LOGIN = "login";
	
	/**
	 * 退出
	 */
	public static final String LOGOUT = "logout";
	
	/**
	 * 列表
	 */
	public static final String LIST = "list";
	
	/**
	 * 增加
	 */
	public static final String ADD = "add";
	
	/**
	 * 编辑
	 */
	public static final String EDIT = "edit";
	
	
	/**
	 * 分页常量值
	 */
	public final static int PRE_PAGE_SIZE = 15;
	
}
