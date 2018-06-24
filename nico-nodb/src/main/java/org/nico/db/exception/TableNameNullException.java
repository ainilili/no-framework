package org.nico.db.exception;

public class TableNameNullException extends Exception{

	private static final long serialVersionUID = -4755502761970020913L;

	public TableNameNullException() {
		super();
	}

	public TableNameNullException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public TableNameNullException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TableNameNullException(String arg0) {
		super(arg0);
	}

	public TableNameNullException(Throwable arg0) {
		super(arg0);
	}

	
}
