package org.nico.noson.adapter.type;


public class TypeAdapter_byt extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return ((Number)Double.parseDouble(target.toString())).byteValue();
	}

}
