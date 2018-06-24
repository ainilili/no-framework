package org.nico.cat.server.exception.runtime;

import org.nico.cat.server.exception.BaseRuntimeException;

public class ProcessException extends BaseRuntimeException{

	private static final long serialVersionUID = -9178588800144713211L;

	public ProcessException(String arg0) {
		super(arg0);
	}

	public ProcessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	
}
