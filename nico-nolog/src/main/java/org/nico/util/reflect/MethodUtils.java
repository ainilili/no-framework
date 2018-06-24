package org.nico.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * MethodUtils
 * 
 * @author nico
 * @version createTime：2018年1月7日 下午7:19:37
 */
public class MethodUtils {

	public static Method getGetterMethod(Field field, Class<?> clazz) throws SecurityException {
		Method method = null;
		try {
			if(Boolean.class.isAssignableFrom(field.getType()) || field.getType().equals(boolean.class)){
				method = clazz.getDeclaredMethod((field.getName().startsWith("is") ? "" : "is") + upperFirstLetter(field.getName()));
			}else{
				method = clazz.getDeclaredMethod("get" + upperFirstLetter(field.getName()));
			}
		} catch (NoSuchMethodException e) {
			if(clazz.getSuperclass() != null){
				return getGetterMethod(field, clazz.getSuperclass());
			}
		}	
		return method;
	}

	public static Method getSetterMethod(Field field, Class<?> clazz) throws SecurityException{
		Method method = null;
		if(field != null){
			try {
				method = clazz.getDeclaredMethod("set" + upperFirstLetter(field.getName()), field.getType());
			} catch (NoSuchMethodException e) {
				if(clazz.getSuperclass() != null){
					return getSetterMethod(field, clazz.getSuperclass());
				}
			}	
		}
		return method;
	}

	public static Object invoke(Method method, Object obj, Object... objs) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException{
		if(objs != null){
			Object[] objArrays = new Object[objs.length];
			Class<?>[] classes = method.getParameterTypes();
			for(int index = 0; index < objs.length; index ++){
				objArrays[index] = TypeUtils.convert(classes[index], objs[index]);
			}
			return method.invoke(obj, objArrays);
		}else{
			return method.invoke(obj, objs);
		}
	}

	private static String upperFirstLetter(String fieldName){
		return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
