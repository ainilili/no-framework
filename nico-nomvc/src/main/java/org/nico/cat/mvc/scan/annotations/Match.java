package org.nico.cat.mvc.scan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Parameter Verfiy
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午1:31:51
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@NV
public @interface Match {
	
	String regex();
	
	String message() default "Match is error";
	
}
