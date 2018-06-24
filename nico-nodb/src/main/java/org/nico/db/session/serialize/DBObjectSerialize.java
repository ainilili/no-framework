package org.nico.db.session.serialize;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.db.annotation.Param;
import org.nico.db.convert.ConvertType;
import org.nico.db.exception.NoGetMethodException;
import org.nico.db.exception.NoSetMethodException;
import org.nico.db.reflect.util.FieldUtils;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;


/** 
 * DB-Object 序列化
 * @author nico
 * @version 创建时间：2017年11月17日 下午11:48:56
 */

public class DBObjectSerialize {
	
	private static Logging logging = LoggingHelper.getLogging(DBObjectSerialize.class);
	
	/**
	 * map serialize to object
	 * @param result
	 * @param clazz
	 * @return
	 */
	public static <T> T MapSerializeObject(Map<String, Object> result, Class<T> clazz){
		T obj = null;
		if(clazz != null && result != null && result.size() > 0){
			try {
				obj = clazz.newInstance();
				Field[] entityFields = clazz.getDeclaredFields();
				for(Field field: entityFields){
					Param param = null;
					String key = field.getName();
					if((param = field.getDeclaredAnnotation(Param.class)) != null){
						key = param.value();
					}
					try {
						FieldUtils.set(field, obj, clazz, ConvertType.mysqlConvert2Obj(field, result.get(key)));
					} catch (NoSetMethodException e) {
						logging.error(e.getMessage());
					}
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
				logging.error(e.getMessage());
			}
		}
		return obj;
	}
	
	/**
	 * map list serialize to objects
	 * @param results
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> MapListSerializeObjects(List<Map<String, Object>> results, Class<T> clazz){
		List<T> list = new ArrayList<T>();
		if(clazz != null && results != null && results.size() > 0){
			for(Map<String, Object> result: results){
				list.add(MapSerializeObject(result, clazz));
			}
		}
		return list;
	}
}
