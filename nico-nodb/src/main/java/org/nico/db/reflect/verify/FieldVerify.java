package org.nico.db.reflect.verify;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.nico.db.annotation.Ignore;
import org.nico.db.annotation.Primary;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年12月6日 下午8:46:18
 */

public class FieldVerify {
	
	/**
	 * 判断field修饰符是否符合nodb的约束
	 * @param field 要验证的字段
	 * @return
	 */
	public static boolean isAccessField(Field field){
		int mod = field.getModifiers();
		if(field.isAnnotationPresent(Ignore.class)) return false;
		if(!Modifier.isPrivate(mod))return false;
		if(Modifier.isStatic(mod)) return false;
		if(Modifier.isFinal(mod)) return false;
		return true;
	} 
	
	/**
	 * 判断是否是主键
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isPrimaryKey(Field field){
		return field.isAnnotationPresent(Primary.class);
	}
}
