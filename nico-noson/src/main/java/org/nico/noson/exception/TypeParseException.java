package org.nico.noson.exception;

public class TypeParseException extends NosonException{

	private static final long serialVersionUID = 1L;

	public TypeParseException() {
		super();
	}

	public TypeParseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TypeParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public TypeParseException(String message) {
		super(message);
	}

	public TypeParseException(Throwable cause) {
		super(cause);
	}
	
}
