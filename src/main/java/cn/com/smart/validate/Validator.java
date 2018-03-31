package cn.com.smart.validate;

/**
 * 验证器
 * @author lmq
 *
 */
public interface Validator {

	public boolean validate() throws ValidateException;
	
}
