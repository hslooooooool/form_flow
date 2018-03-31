package cn.com.smart.form.parser.factory;

import java.util.List;
import java.util.Map;

import cn.com.smart.form.parser.IFormParser;
import cn.com.smart.service.SmartContextService;

import com.mixsmart.utils.StringUtils;

/**
 * 表单解析工厂
 * @author lmq
 * @create 2015年7月4日
 * @version 1.0 
 * @since 
 *
 */
public class FormParserFactory {

	private List<IFormParser> formParsers;
	
	private static FormParserFactory instance = new FormParserFactory();
	
	private FormParserFactory() {
		formParsers = SmartContextService.finds(IFormParser.class);
	}
	
	public static FormParserFactory getInstance() {
		return instance;
	}
	
	/**
	 * 解析表单设计器元素
	 * @param plugin
	 * @param dataMap
	 * @return
	 * @throws NotFindParserException
	 */
	public String parse(String plugin,Map<String,Object> dataMap) throws NotFindParserException {
		String parseContent = null;
		if(StringUtils.isNotEmpty(plugin)) {
			IFormParser formParser = getParser(plugin);
			if(null == formParser) {
				throw new NotFindParserException(plugin);
			}
			parseContent = formParser.parse(dataMap);
		} 
		return parseContent;
	}
	
	
	/**
	 * 获取解析器
	 * @param plugin
	 * @return
	 */
	protected IFormParser getParser(String plugin) {
		if(null != formParsers && formParsers.size()>0) {
			for (IFormParser formParser : formParsers) {
				if(formParser.getPlugin().equals(plugin)) {
					return formParser;
				}
			}
		}
		return null;
	}
	 
	
	/**
	 * 注册解析器
	 * @param plugin
	 * @param formParser
	 * @return
	 */
	public boolean register(IFormParser formParser) {
		boolean is = false;
		if(null != formParser) {
			formParsers.add(formParser);
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 注册解析器
	 * @param parserBeans
	 * @return
	 */
	public boolean register(List<IFormParser> parsers) {
		boolean is = false;
		if(null != parsers && parsers.size()>0) {
			formParsers.addAll(parsers);
			is = true;
		}
		return is;
	}
	
}
