package cn.com.smart.config;

/**
 * 配置接口类
 * @author lmq
 *
 */
public interface IConfig {

   public String getValue(String key);
	
	/**
	 * 变量处理
	 * @param value
	 * @return String
	 */
   public String handleVar(String value);
   
	/**
	 * 处理值中含有的系统变量
	 * @param value
	 * @return String
	 */
   public String handleSysVar(String value);
	
}
