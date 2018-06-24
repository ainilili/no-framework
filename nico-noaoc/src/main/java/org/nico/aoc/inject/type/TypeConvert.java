package org.nico.aoc.inject.type;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/** 
 * 
 * @author nico
 * @version createTime：2017年12月16日 下午5:36:53
 */

public class TypeConvert {
	
	private Logging logging = LoggingHelper.getLogging(TypeConvert.class);
	
	private final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("YYY-MM-dd HH:mm:ss");
	
	/**
	 * Convernt str to Object follow the type
	 * @param type
	 * @param value
	 * @return
	 */
	public Object convert(Class<?> clazz, String value){
		Object target = null;
		if(String.class.isAssignableFrom(clazz)){
			target = value;
		}else if(int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)){
			target = Integer.parseInt(value);
		}else if(long.class.isAssignableFrom(clazz) || Long.class.isAssignableFrom(clazz)){
			target = Long.parseLong(value);
		}else if(float.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)){
			target = Float.parseFloat(value);
		}else if(double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)){
			target = Double.parseDouble(value);
		}else if(BigDecimal.class.isAssignableFrom(clazz)){
			target = BigDecimal.valueOf(Double.parseDouble(value));
		}else if(boolean.class.isAssignableFrom(clazz) || Boolean.class.isAssignableFrom(clazz)){
			target = Boolean.parseBoolean(value);
		}else if(Enum.class.isAssignableFrom(clazz)){
			target = (Enum<?>[])clazz.getEnumConstants()[Integer.parseInt(value)];
		}else if(Date.class.isAssignableFrom(clazz)){
			try {
				target = FORMAT_DATE.parse(value);
			} catch (ParseException e) {
				logging.error(e);
			}
		}
		return target;
	}
}
