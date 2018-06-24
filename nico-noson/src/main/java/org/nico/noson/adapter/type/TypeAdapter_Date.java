package org.nico.noson.adapter.type;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.nico.noson.NosonConfig;
import org.nico.noson.exception.TypeParseException;
import org.nico.noson.util.string.StringUtils;

public class TypeAdapter_Date extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) throws TypeParseException {
		Object obj = null;
		if(target instanceof Date){
			obj = target;
		}else{
			try {
				obj = NosonConfig.DEFAULT_DATE_FORMAT.parse(String.valueOf(target));
			} catch (ParseException e) {
				throw new TypeParseException(e.getMessage(), e);
			}
		}
		return obj;
	}

}
