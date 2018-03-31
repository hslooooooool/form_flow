package cn.com.smart.exception;

/**
 * Service异常
 * @author lmq
 *
 */
public class ServiceException extends SmartRuntimeException {

	private static final long serialVersionUID = 5433003077251038078L;

	/**
	 * Service执行时异常
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Service执行时异常
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Service执行时异常
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
}
