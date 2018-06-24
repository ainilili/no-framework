package org.nico.aoc.scan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Representing this class is a Book.
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:08:06
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Book {

	String name() default "";
}
