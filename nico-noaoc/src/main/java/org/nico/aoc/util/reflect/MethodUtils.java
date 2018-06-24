package org.nico.aoc.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

public class MethodUtils {
	
	private static Logging logging = LoggingHelper.getLogging(MethodUtils.class);
	
	public static Method getGetterMethod(Field field, Class<?> clazz){
		Method method = null;
		try {
			if(Boolean.class.isAssignableFrom(field.getType()) || field.getType().equals(boolean.class)){
				method = clazz.getDeclaredMethod("is" + upperFirstLetter(field.getName()));	
			}else{
				method = clazz.getDeclaredMethod("get" + upperFirstLetter(field.getName()));
			}
		} catch (NoSuchMethodException | SecurityException e) {
			if(clazz.getSuperclass() != null){
				method = getGetterMethod(field, clazz.getSuperclass());
			}else{
				logging.error(e);
			}
		}
		return method;
	}
	
	public static Method getSetterMethod(Field field, Class<?> clazz) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("set" + upperFirstLetter(field.getName()), field.getType());
		}catch (NoSuchMethodException | SecurityException e) {
			if(clazz.getSuperclass() != null){
				method = getSetterMethod(field, clazz.getSuperclass());
			}else{
				logging.error(e);
			}
		}
		return method;
	}
	
	public static Method getSetterMethod(String fieldName, Class<?> clazz) {
		Method method = null;
		String methodName = "set" + upperFirstLetter(fieldName);
		try {
			Method[] methods = clazz.getDeclaredMethods();
			if(methods != null && methods.length > 0){
				for(Method m: methods){
					if(m.getName().equals(methodName) && m.getParameterTypes() != null && m.getParameterTypes().length == 1){
						method = m;
						break;
					}
				}
			}
		}catch (SecurityException e) {
			if(clazz.getSuperclass() != null){
				method = getSetterMethod(fieldName, clazz.getSuperclass());
			}else{
				logging.error(e);
			}
		}
		return method;
	}
	
	
	public static Object invoke(Method method, Object obj, Object... objs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return method.invoke(obj, objs);
	}
	
	private static String upperFirstLetter(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
