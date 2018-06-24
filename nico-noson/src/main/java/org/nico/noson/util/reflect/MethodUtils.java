package org.nico.noson.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Method Utils
 * 
 * @author nico
 */
public class MethodUtils {
	
	/**
	 * Gets the Getter method for the Field
	 * 
	 * @param field Target Field
	 * @param clazz Target Class
	 * @return Getter method
	 */
	public static Method getGetterMethod(Field field, Class<?> clazz){
		Method method = null;
		try {
			if(field.getType().equals(boolean.class)){
				method = clazz.getDeclaredMethod("is" + upperFirstLetter(field.getName()));	
			}else{
				method = clazz.getDeclaredMethod("get" + upperFirstLetter(field.getName()));
			}
		} catch (Exception e) {
			if(clazz.getSuperclass() != null){
				method = getGetterMethod(field, clazz.getSuperclass());
			}
		}
		return method;
	}
	
	/**
	 * Gets the Setter method for the Field
	 * 
	 * @param field The target Field,
	 * @param clazz The target Class
	 * @return A Setter method
	 */
	public static Method getSetterMethod(Field field, Class<?> clazz) {
		Method method = null;
		try {
			method = clazz.getDeclaredMethod("set" + upperFirstLetter(field.getName()), field.getType());
		}catch (Exception e) {
			if(clazz.getSuperclass() != null){
				method = getSetterMethod(field, clazz.getSuperclass());
			}else{
				e.printStackTrace();
			}
		}
		return method;
	}
	
	/**
	 * Execution method
	 * 
	 * @param method The method to execute
	 * @param obj The object where the method is executed
	 * @param params The method parameters
	 * @return The execution result
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invoke(Method method, Object obj, Object... params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return method.invoke(obj, params); 
	}
	
	/**
	 * The field is capitalized
	 * 
	 * @param fieldName This field is capitalized
	 * @return Target value
	 */
	private static String upperFirstLetter(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
