package org.nico.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.string.StringUtils;

/**
 * FieldUtils
 * 
 * @author nico
 * @version createTime：2018年1月7日 下午7:21:19
 */
public class FieldUtils {

	public static void set(Field field, Object obj, Class<?> clazz, Object... values) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException {
		Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
		if(currentMethod != null){
			MethodUtils.invoke(currentMethod, obj, values);	
		}else{
			throw new NoSuchMethodException();
		}
	}

	public static FieldEntity get(Field field, Object obj, Class<?> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ParseException{
		Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
		Object value = MethodUtils.invoke(currentMethod, obj);
		if(value != null){
			String fieldName = getFieldName(field);
			return new FieldEntity(fieldName, value);
		}
		return null;
	}

	/**
	 * Get field by name
	 * @param fieldName field name
	 * @param clazz target class
	 * @return field
	 */
	public static Field getField(String fieldName, Class clazz){
		if(StringUtils.isBlank(fieldName) || clazz == null) return null;
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			if(clazz.getSuperclass() != null){
				return getField(fieldName, clazz.getSuperclass());
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return field;
	}

	public static String getFieldName(Field field){
		return field.getName();
	}

	public static class FieldEntity{

		private String fieldName;

		private Object obj;

		public FieldEntity(String fieldName, Object obj) {
			super();
			this.fieldName = fieldName;
			this.obj = obj;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public Object getObj() {
			return obj;
		}

		public void setObj(Object obj) {
			this.obj = obj;
		}
	}

}
