package cn.com.smart.utils;

/**
 * 系统工具类
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年12月9日
 */
public class SystemUtils {

	/**
	 * 获取java版本信息
	 * @return 返回java版本信息
	 */
	public static String getJavaVersion() {
		return System.getProperty("java.version");
	}
	
	/**
	 * 获取java安装目录
	 * @return 返回java安装目录
	 */
	public static String getJavaHome() {
		return System.getProperty("java.home");
	}
	
	/**
	 * 操作系统名称
	 * @return 系统名称
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}
	
	/**
	 * 获取文件分隔符
	 * @return 文件分隔符
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	/**
	 * 获取路径分隔符
	 * @return 路径分隔符
	 */
	public static String getPathSeparator() {
		return System.getProperty("path.separator");
	}
	
	/**
	 * 获取行分隔符
	 * @return 行分隔符
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}
	
	/**
	 * 获取用户的账户名称
	 * @return 账户名称
	 */
	public static String getUserName() {
		return System.getProperty("user.name");
	}
	
	/**
	 * 获取用户的主目录
	 * @return 用户的主目录
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	/**
	 * 获取用户的当前工作目录
	 * @return 用户的当前工作目录
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}
}
