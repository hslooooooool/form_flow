package cn.com.smart.form.parser.factory;

/**
 * 自定义异常---未找到解析器
 * @author lmq
 * @create 2015年7月4日
 * @version 1.0 
 * @since 
 *
 */
public class NotFindParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8468348060268974370L;

	public NotFindParserException() {
		super("没有找到表单解析器");
	}
	
	public NotFindParserException(String plugin) {
		super("没有找到表单解析器["+plugin+"]");
	}
	
}
