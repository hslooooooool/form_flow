package cn.com.smart.cache;

import cn.com.smart.exception.SmartException;


/**
 * 缓存异常
 * @author lmq
 * @version 1.0
 * @since JDK版本大于等于1.6
 * 
 * 2015年8月22日
 */
public class CacheException extends SmartException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1819537238013814177L;

	/**
	 * 创建缓存异常
	 */
	public CacheException() {
		super();
	}

	/**
	 * 创建缓存异常
	 * @param message
	 * @param cause
	 */
	public CacheException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 创建缓存异常
	 * @param message
	 */
	public CacheException(String message) {
		super(message);
	}

	/**
	 * 创建缓存异常
	 * @param cause
	 */
	public CacheException(Throwable cause) {
		super(cause);
	}
}
