package org.nico.noson.adapter.type;


public class TypeAdapter_sho extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return ((Number)Double.parseDouble(target.toString())).shortValue();
	}

}
