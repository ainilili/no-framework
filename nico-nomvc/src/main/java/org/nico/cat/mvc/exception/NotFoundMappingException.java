package org.nico.cat.mvc.exception;

import org.nico.cat.mvc.parameter.RequestMethod;

public class NotFoundMappingException extends Exception{

	private static final long serialVersionUID = 4706823760489841716L;

	public NotFoundMappingException() {
		super();
	}

	public NotFoundMappingException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public NotFoundMappingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotFoundMappingException(String mapping, RequestMethod method) {
		super("Not found mapping for [" + mapping + "]-[" + method + "]");
	}

	public NotFoundMappingException(Throwable arg0) {
		super(arg0);
	}

}
