package org.nico.aoc.throwable;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月10日 下午6:29:22
 */

public class BookNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public BookNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
