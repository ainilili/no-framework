package org.nico.aoc.scan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.nico.aoc.scan.entity.LabelType;

/** 
 * Representing this property is an injection property.
 * 
 * @author nico
 * @version createTime：2018年1月21日 下午2:10:49
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Label {

	LabelType type() default LabelType.BYALL;
	
	String name() default "";
}
