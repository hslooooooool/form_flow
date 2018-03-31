package cn.com.smart.exception;

/**
 * Dao异常
 * @author lmq
 *
 */
public class DaoException extends SmartRuntimeException {

	private static final long serialVersionUID = -6274289293425089591L;

	/**
	 * Dao执行时异常
	 * @param msg
	 */
	public DaoException(String msg) {
		super(msg);
	}
	
	/**
	 * Dao执行时异常
	 * @param message
	 * @param cause
	 */
	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Dao执行时异常
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}
}
