package org.nico.noson.adapter.type;

import java.math.BigDecimal;


public class TypeAdapter_BigDecimal extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return new BigDecimal(Double.parseDouble(target.toString()));
	}

}
