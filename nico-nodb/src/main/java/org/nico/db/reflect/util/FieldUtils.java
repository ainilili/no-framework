package org.nico.db.reflect.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.nico.db.annotation.Param;
import org.nico.db.convert.ConvertType;
import org.nico.db.exception.NoGetMethodException;
import org.nico.db.exception.NoSetMethodException;

public class FieldUtils {
	
	public static void set(Field field, Object obj, Class<?> clazz, Object... values) throws NoSetMethodException{
		try {
			Method currentMethod = MethodUtils.getSetterMethod(field, clazz);
			MethodUtils.invoke(currentMethod, obj, values);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public static FieldEntity get(Field field, Object obj, Class<?> clazz) throws NoGetMethodException{
		Method currentMethod = MethodUtils.getGetterMethod(field, clazz);
		try {
			Object value = MethodUtils.invoke(currentMethod, obj);
			value = ConvertType.objConvert2Mysql(field.getType(), value);
			if(value != null){
				String fieldName = getFieldName(field);
				return new FieldEntity(fieldName, value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFieldName(Field field){
		if(field.isAnnotationPresent(Param.class)){
			return field.getAnnotation(Param.class).value();
		}else{
			return field.getName();
		}
	}
	
	public static String getFieldName(Class<?> clazz, String fieldName) {
		Field field;
		try {
			field = clazz.getDeclaredField(fieldName);
			if(field.isAnnotationPresent(Param.class)){
				return field.getAnnotation(Param.class).value();
			}else{
				return field.getName();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return fieldName;
		
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
