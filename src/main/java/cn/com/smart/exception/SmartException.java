package cn.com.smart.exception;

/**
 * 自定义异常类
 * @author lmq
 *
 */
public class SmartException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1315793286758249166L;

	public SmartException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public SmartException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public SmartException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public SmartException(Throwable cause) {
		super(cause);
	}
	
	
}
