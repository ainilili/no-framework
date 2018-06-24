package org.nico.noson.util.reflect;

import java.util.Collection;
import java.util.Map;


public class ClassUtils {
	
	/**
	 * 用户自定义类判断
	 * 
	 * @param clz 
	 * 		要判断的类
	 * @return true/false
	 */
	public static boolean isJavaClass(Class<?> clz) {  
		return (clz != null && clz.getClassLoader() == null) || Map.class.isAssignableFrom(clz) || Collection.class.isAssignableFrom(clz);  
	}
	
}
