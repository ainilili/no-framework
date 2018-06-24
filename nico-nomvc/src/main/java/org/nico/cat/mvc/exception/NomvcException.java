package org.nico.cat.mvc.exception;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月16日 下午9:27:33
 */
public class NomvcException extends Exception{

	private static final long serialVersionUID = -3806097032130911208L;

	public NomvcException() {
		super();
	}

	public NomvcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NomvcException(String message, Throwable cause) {
		super(message, cause);
	}

	public NomvcException(String message) {
		super(message);
	}

	public NomvcException(Throwable cause) {
		super(cause);
	}

}
