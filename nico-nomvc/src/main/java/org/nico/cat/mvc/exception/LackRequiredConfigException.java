package org.nico.cat.mvc.exception;

/** 
 * LackRequiredConfigException
 * @author nico
 * @version createTime：2018年1月15日 下午10:31:49
 */
public class LackRequiredConfigException extends NomvcException{

	private static final long serialVersionUID = -8413448980488461555L;

	public LackRequiredConfigException(String attribute) {
		super("Lack of necessary attributes " + attribute);
	}
}
