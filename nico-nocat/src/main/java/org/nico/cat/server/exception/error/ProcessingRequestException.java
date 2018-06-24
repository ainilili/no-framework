package org.nico.cat.server.exception.error;

import org.nico.cat.server.exception.BaseRuntimeException;

/**
 * Processing {@link Request} Exception
 * @author nico
 * @date 2018年1月12日
 */
public class ProcessingRequestException extends BaseRuntimeException{

	private static final long serialVersionUID = -4148820151743522848L;

	public ProcessingRequestException() {
		super();
	}

	public ProcessingRequestException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ProcessingRequestException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ProcessingRequestException(String arg0) {
		super(arg0);
	}

	public ProcessingRequestException(Throwable arg0) {
		super(arg0);
	}
	
}
