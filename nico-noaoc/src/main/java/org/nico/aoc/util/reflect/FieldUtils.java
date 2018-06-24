package org.nico.aoc.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.nico.aoc.scan.annotations.Label;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;



public class FieldUtils {
	
	private static Logging logging = LoggingHelper.getLogging(FieldUtils.class);
	
	public static void set(Field field, Object obj, Class<?> clazz, Object value) throws IllegalArgumentException, IllegalAccessException{
		if(Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(Label.class)){
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}else{
			Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
			try {
				value = convertType(field, value);
				MethodUtils.invoke(currentMethod, obj, value);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logging.error(e + "on field：" + field.getName() + ":" + value.getClass());
			}
		}
	}
	
	public static Object get(Field field, Object obj, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException{
		if(Modifier.isStatic(field.getModifiers())){
			field.setAccessible(true);
			Object target = field.get(obj);
			field.setAccessible(false);
			return target;
		}else{
			Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
			try {
				return MethodUtils.invoke(currentMethod, obj);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				logging.error(e);
			} 
			return null;
		}
	}
	
	/**
	 * Get field from this clazz or it's super class
	 * @param fieldName field name what do you want to get !
	 * @param clazz the class where is the field's home 
	 * @return target field
	 * @throws NoSuchFieldException no found the field from the class
	 */
	public static Field getField(String fieldName, Class<?> clazz) throws NoSuchFieldException{
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				field = getField(fieldName, clazz.getSuperclass());
			}else{
				throw e;
			}
		}
		return field;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> Object convertType(Field field, Object value){
		String target = String.valueOf(value);
		if(Enum.class.isAssignableFrom(field.getType())) return Enum.valueOf((Class<T>)field.getType(), target);
		if(Number.class.isAssignableFrom(field.getType())) return Double.parseDouble(target);
		if(Boolean.class.isAssignableFrom(field.getType()) || boolean.class.isAssignableFrom(field.getType())) return Boolean.parseBoolean(target);
		if(String.class.isAssignableFrom(field.getType())) return target;
		return value;
	}
	
	
}
