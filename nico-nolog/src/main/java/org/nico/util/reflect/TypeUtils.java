package org.nico.util.reflect;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.nico.util.string.StringUtils;


/** 
 * 类型工具
 * @author nico
 * @version createTime：2018年1月7日 下午7:45:07
 */
public class TypeUtils {

	/**
	 * 数字类型集合
	 */
	public static final Set<Class<?>> NUM_CLASS_SET = new HashSet<Class<?>>(){
		private static final long serialVersionUID = -1925389609848043520L;
		{
			add(int.class);
			add(short.class);
			add(double.class);
			add(long.class);
			add(float.class);
			add(byte.class);
		}
	};

	/**
	 * Default date format
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss");

	/**
	 * 将target转为clazz的类型对象
	 * @param clazz 要转换的目标对象类型
	 * @param target 待转对象
	 * @return 转换后的对象
	 * @throws ParseException 
	 */
	public static Object convert(Class<?> clazz, Object target) throws ParseException{
		Object obj = null;
		if(Number.class.isAssignableFrom(clazz) || NUM_CLASS_SET.contains(clazz)){
			Number value = StringUtils.isBlank(target) ? 0 : (Number)Double.parseDouble(String.valueOf(target));
			if(clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(int.class)){
				obj = value.intValue();
			}else if(clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(double.class)){
				obj = value.doubleValue();
			}else if(clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(float.class)){
				obj = value.floatValue();
			}else if(clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(long.class)){
				obj = value.longValue();
			}else if(clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(short.class)){
				obj = value.shortValue();
			}else if(clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(byte.class)){
				obj = value.byteValue();
			}else{
				obj = clazz.cast(value);
			}
		}else if(String.class.isAssignableFrom(clazz)){
			obj = target == null ? null : String.valueOf(target);
		}else if(Date.class.isAssignableFrom(clazz)){
			if(target != null){
				if(target instanceof Date){
					obj = target;
				}else{
					obj = dateFormat.parse(String.valueOf(target));
				}
			}
		}else if(Enum.class.isAssignableFrom(clazz)){
			if(target != null){
				if(target instanceof Enum){
					obj = target;
				}else{
					Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
					obj = Enum.valueOf(enumClass, String.valueOf(target));
				}
			}
		}else if(clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(boolean.class)){
			obj = target == null ? null : Boolean.parseBoolean(String.valueOf(target));
		}
		return obj;
	}
}
