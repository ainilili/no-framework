package org.nico.noson.adapter.type;


public class TypeAdapter_doub extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return Double.parseDouble(target.toString());
	}

}
