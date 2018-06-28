package org.nico.aoc.scan.annotations.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nico.aoc.aspect.AspectProxy;
import org.nico.aoc.scan.entity.AspectType;

/** 
 * An annotation applied to a method that declares that the method is crosscutting
 * 
 * @author nico
 */

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Section {
	
	/**
	 * Custom proxy class, which must implement {@link AspectProxy}
	 * 
	 * @return Proxy class
	 */
	Class<? extends AspectProxy> proxyBy();
	
	
	AspectType type() default AspectType.CGLIB_PROXY;
	
}
