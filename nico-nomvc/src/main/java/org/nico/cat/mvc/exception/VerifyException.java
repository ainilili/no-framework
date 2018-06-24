package org.nico.cat.mvc.exception;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午4:44:19
 */

public class VerifyException extends RuntimeException{

	private static final long serialVersionUID = 1L;


	public VerifyException(String message) {
		super(message);
	}

}
