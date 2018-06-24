package org.nico.cat.mvc.exception;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月16日 下午9:27:04
 */

public class NullConfigException extends NomvcException{

	private static final long serialVersionUID = 7089718329815298776L;

	public NullConfigException() {
		super("Nomvc's configuration is empty");
	}

}
