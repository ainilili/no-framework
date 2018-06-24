package org.nico.noson.adapter.type;


public class TypeAdapter_Float extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return ((Number)Double.parseDouble(target.toString())).floatValue();
	}

}
