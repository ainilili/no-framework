package org.nico.cat.mvc.scan.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * Who have me, Who can join Noson verfiy
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午1:31:51
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface NV {
	
	
}
