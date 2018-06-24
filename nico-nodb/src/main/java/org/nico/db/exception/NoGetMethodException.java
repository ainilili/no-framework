package org.nico.db.exception;

public class NoGetMethodException extends Exception{

	private static final long serialVersionUID = 5390344322213450856L;
	
	public NoGetMethodException() {
		super();
	}

	public NoGetMethodException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoGetMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoGetMethodException(String message) {
		super(message);
	}

	public NoGetMethodException(Throwable cause) {
		super(cause);
	}
	

}
