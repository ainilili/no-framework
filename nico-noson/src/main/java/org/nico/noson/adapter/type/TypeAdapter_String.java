package org.nico.noson.adapter.type;

import java.math.BigDecimal;

import org.nico.noson.util.string.StringUtils;

public class TypeAdapter_String extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return target.toString();
	}

}
