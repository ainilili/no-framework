package org.nico.asm.proxy.reflect;

/** 
 * 
 * @author nico
 * @version createTime：2018年4月21日 下午3:00:07
 */

public abstract class AbstractASMClassProxy {

	public abstract void set(Object target, String fieldName, Object fieldValue);
	
	public abstract Object get(Object target, String fieldName);
}
