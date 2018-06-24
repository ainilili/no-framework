package org.nico.noson.adapter.type;


public class TypeAdapter_Double extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		return Double.parseDouble(target.toString());
	}

}
