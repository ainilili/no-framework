package org.nico.aoc.throwable;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午3:15:34
 */

public class BookHandlingException extends Exception{

	private static final long serialVersionUID = 1L;

	public BookHandlingException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookHandlingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BookHandlingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BookHandlingException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BookHandlingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
