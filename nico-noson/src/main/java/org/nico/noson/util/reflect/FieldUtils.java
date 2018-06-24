package org.nico.noson.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.nico.noson.exception.NosonException;
import org.nico.noson.util.type.TypeUtils;


/**
 * FieldUtils
 * 
 * @author nico
 */
public class FieldUtils {
	
	/**
	 * The Field Set operation
	 * 
	 * @param field field
	 * @param obj object
	 * @param clazz class
	 * @param value parameter
	 * @throws NosonException Error
	 */
	public static void set(Field field, Object obj, Class<?> clazz, Object value) throws NosonException{
		Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
		if(currentMethod == null) return;
		try {
			value = TypeUtils.convertType(field.getType(), value);
			MethodUtils.invoke(currentMethod, obj, value);
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		} 
	}
	
	/**
	 * The Field Set operation
	 * 
	 * @param fieldName field name
	 * @param obj object
	 * @param clazz class
	 * @param value parameter
	 * @throws NosonException Error
	 */
	public static void set(String fieldName, Object obj, Class<?> clazz, Object value) throws NosonException{
		try {
		Field field = getField(fieldName, clazz);
		Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
		if(currentMethod == null) return;
		
			value = TypeUtils.convertType(field.getType(), value);
			MethodUtils.invoke(currentMethod, obj, value);
		} catch (Exception e) {
			throw new NosonException(e.getMessage(), e);
		} 
	}
	
	/**
	 * The Field Get operation
	 * 
	 * @param field field
	 * @param obj object
	 * @param clazz class
	 * @return Target value
	 */
	public static Object get(Field field, Object obj, Class<?> clazz){
		if(Modifier.isStatic(field.getModifiers())){
			field.setAccessible(true);
			try {
				return field.get(obj);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}finally{
				field.setAccessible(false);
			}
		}
		Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
		if(currentMethod == null) return null;
		try {
			return MethodUtils.invoke(currentMethod, obj);
		} catch (Exception e) {
		} 
		return null;
	}
	
	/**
	 * Get field from this clazz or it's super class
	 * 
	 * @param fieldName field name what do you want to get !
	 * @param clazz the class where is the field's home 
	 * @return target field
	 * @throws NosonException no found the field from the class
	 */
	public static Field getField(String fieldName, Class<?> clazz) throws NosonException{
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				field = getField(fieldName, clazz.getSuperclass());
			}else{
				return null;
			}
		}
		return field;
	}
	
}
