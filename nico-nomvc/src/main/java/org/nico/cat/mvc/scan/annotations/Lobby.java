package org.nico.cat.mvc.scan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nico.cat.mvc.parameter.RequestMethod;


/** 
 * Mapping
 * @author nico
 * @version createTime：2018年1月15日 下午8:09:54
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Lobby {

	/**
	 * Request API mapping
	 * @return entry
	 */
	String mapping() default "";
	
	/**
	 * Request type
	 * @return request type
	 */
	RequestMethod requestMethod() default RequestMethod.DEFAULT;
}
