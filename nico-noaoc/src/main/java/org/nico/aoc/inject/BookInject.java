package org.nico.aoc.inject;

import java.util.Map;

/** 
 * Inject Book Interface
 * @author nico
 * @version 创建时间：2017年12月13日 下午9:19:33
 */
public interface BookInject {
	
	/**
	 * Parameters inject into the object by set method
	 * @param object be injected Object
	 * @param parameters the map of key-value -> field-fieldValue
	 */
	public void parameterSetInject(Object object, Map<Object, Object> paramSource) throws Throwable;
	
	/**
	 * Parameters inject into the object by constructor
	 * @param clazz be injected class
	 * @param parameters the map of key-value -> field-fieldValue
	 * return T object
	 */
	public <T> T parameterConstructorInject(Class<T> clazz, Map<Object, Object> parameters)  throws Throwable;
}
