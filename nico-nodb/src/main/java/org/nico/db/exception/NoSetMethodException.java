package org.nico.db.exception;

public class NoSetMethodException extends Exception{

	private static final long serialVersionUID = 5390344322213450856L;
	
	public NoSetMethodException() {
		super();
	}

	public NoSetMethodException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoSetMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoSetMethodException(String message) {
		super(message);
	}

	public NoSetMethodException(Throwable cause) {
		super(cause);
	}
	

}
