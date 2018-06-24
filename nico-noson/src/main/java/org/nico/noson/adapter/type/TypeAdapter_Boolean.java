package org.nico.noson.adapter.type;

public class TypeAdapter_Boolean extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return Boolean.parseBoolean(target.toString());
	}

}
