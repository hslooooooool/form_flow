package cn.com.smart.validate;

/**
 * 自定义验证接口
 * @author lmq
 *
 */
public interface CustomValidator extends Validator {

	public void setObj(Object obj);
	
	public void setValue(Object value);
	
}
