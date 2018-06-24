package org.nico.db.convert;

import java.lang.reflect.Field;

import org.nico.util.string.StringUtils;

public class ConvertType {
	
	/**
	 * Mysql field convert to Java field
	 * @param field need be injected field
	 * @param value mysql field
	 * @return Java obj
	 */
	public static Object mysqlConvert2Obj(Field field, Object value){
		Class<?> clazz = field.getType();
		if(StringUtils.isBlank(value)) return null;
		if(Enum.class.isAssignableFrom(clazz)){
			return clazz.getEnumConstants()[Integer.parseInt(String.valueOf(value))];
		}else if(Boolean.class.isAssignableFrom(clazz) || clazz.equals(boolean.class)){
			return (int) value == 1 ? true : false;
		}else{
			return value;
		}
	}
	
	/**
	 * Java field convert to Mysql field
	 * @param clazz Java field class type
	 * @param target Java field obj
	 * @return Mysql obj
	 */
	public static <E> Object objConvert2Mysql(Class<E> clazz, Object target){
		if(Enum.class.isAssignableFrom(clazz)){
			E[] enums = clazz.getEnumConstants();
			int index = 0;
			if(enums != null){
				for(E em: enums){
					if(em.equals(target)){
						return index;
					}
					index++;
				}
			}
			return null;
		}else if(Boolean.class.isAssignableFrom(clazz) || clazz.equals(boolean.class)){
			if(target != null){
				if((boolean)target){
					return 1;
				}else{
					return 0;
				}
			}else{
				return null;
			}
		}else{
			return target;
		}
	}
}
