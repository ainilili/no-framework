package org.nico.db.reflect.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.nico.db.exception.NoGetMethodException;
import org.nico.db.exception.NoSetMethodException;
import org.nico.db.exception.phrase.SqlExceptionPhrase;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

public class MethodUtils {
	
	private static Logging logging = LoggingHelper.getLogging(MethodUtils.class);
	
	public static Method getGetterMethod(Field field, Class<?> clazz) throws NoGetMethodException{
		Method method = null;
		try {
			if(Boolean.class.isAssignableFrom(field.getType()) || field.getType().equals(boolean.class)){
				method = clazz.getDeclaredMethod((field.getName().startsWith("is") ? "" : "is") + upperFirstLetter(field.getName()));	
			}else{
				method = clazz.getDeclaredMethod("get" + upperFirstLetter(field.getName()));
			}
		} catch (NoSuchMethodException | SecurityException e) {
			throw new NoGetMethodException(SqlExceptionPhrase.NO_GET_METHOD_EXCEPTION + field.getName());
		}
		return method;
	}
	
	public static Method getSetterMethod(Field field, Class<?> clazz) throws NoSetMethodException{
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("set" + upperFirstLetter(field.getName()), field.getType());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new NoSetMethodException(SqlExceptionPhrase.NO_SET_METHOD_EXCEPTION + field.getName());
		}
		return method;
	}
	
	public static Object invoke(Method method, Object obj, Object... objs){
			try {
				return method.invoke(obj, objs);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logging.error(e.getMessage() + " - " + method.getName() + " - " +  Arrays.asList(objs));
			}
			return null;
	}
	
	private static String upperFirstLetter(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
