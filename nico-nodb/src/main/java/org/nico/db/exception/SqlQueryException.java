package org.nico.db.exception;

/** 
 * Sql query exception
 * 
 * @author nico
 * @version createTime：2018年5月22日 下午11:44:39
 */

public class SqlQueryException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SqlQueryException(String message) {
		super(message);
	}

}
