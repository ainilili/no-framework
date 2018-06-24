package org.nico.cat.server.exception.error;

import org.nico.cat.server.exception.BaseErrorException;

/**
 * Stream read Exception
 * 
 * @author nico
 * @date 2018年2月26日
 */
public class StreamReadException extends BaseErrorException{

	private static final long serialVersionUID = 1L;

	public StreamReadException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StreamReadException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public StreamReadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public StreamReadException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public StreamReadException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
}
