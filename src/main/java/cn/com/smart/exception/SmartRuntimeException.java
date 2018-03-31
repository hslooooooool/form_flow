package cn.com.smart.exception;

/**
 * 运行时异常
 * @author lmq
 * @version 1.0
 * @since 1.0
 * 2015年8月22日
 */
public class SmartRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1379218411429200084L;

	/**
	 * 
	 */
	public SmartRuntimeException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public SmartRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param message
	 */
	public SmartRuntimeException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public SmartRuntimeException(Throwable cause) {
		super(cause);
	}
}
