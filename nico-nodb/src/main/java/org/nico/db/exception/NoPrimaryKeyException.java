package org.nico.db.exception;

/**
 * 无主键异常
 * @author liuqianyuan
 *
 */
public class NoPrimaryKeyException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoPrimaryKeyException() {
		super();
	}

	public NoPrimaryKeyException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoPrimaryKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPrimaryKeyException(String message) {
		super(message);
	}

	public NoPrimaryKeyException(Throwable cause) {
		super(cause);
	}
	

}
