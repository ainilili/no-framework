package org.nico.noson.adapter.type;

public class TypeAdapter_Enum extends AbstractTypeAdapter{

	@Override
	public Object typeAdapter(Class<?> clazz, Object target) {
		Object obj = null;
		if(target instanceof Enum){
			obj = target;
		}else{
			Class<? extends Enum> enumClass = (Class<? extends Enum>) clazz;
			if(target instanceof String){
				obj = Enum.valueOf(enumClass, String.valueOf(target));	
			}else if(target instanceof Number){
				obj = enumClass.getEnumConstants()[((Number)target).intValue()];
			}else{
				obj = null;
			}
		}
		return obj;
	}

}
