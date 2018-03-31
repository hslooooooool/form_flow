package cn.com.smart.form.parser;

import java.util.Map;

/**
 * 表单解析接口
 * @author lmq
 * @create 2015年7月4日
 * @version 1.0 
 * @since 
 *
 */
public interface IFormParser {
	
	/**
	 * 获取表单插件(如:text,select,radios,listctrl等)
	 * @return
	 */
	public String getPlugin();
	
	/**
	 * 解析表单
	 * @param dataMap
	 * @return
	 */
	public String parse(Map<String,Object> dataMap);
	
}
