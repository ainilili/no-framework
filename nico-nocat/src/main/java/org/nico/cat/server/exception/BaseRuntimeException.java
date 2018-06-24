package org.nico.cat.server.exception;

public class BaseRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public BaseRuntimeException() {
		super();
	}

	public BaseRuntimeException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BaseRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BaseRuntimeException(String arg0) {
		super(arg0);
	}

	public BaseRuntimeException(Throwable arg0) {
		super(arg0);
	}

	
}
