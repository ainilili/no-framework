package org.nico.noson.exception;

/** 
 * Json format error
 * 
 * @author nico
 */
public class NosonFormatException extends NosonException{

	private static final long serialVersionUID = 5658156169010157968L;

	public NosonFormatException() {
		super();
	}

	public NosonFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NosonFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NosonFormatException(String message) {
		super(message);
	}

	public NosonFormatException(Throwable cause) {
		super(cause);
	}

}
