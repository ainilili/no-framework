package org.nico.noson.adapter.type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nico.noson.exception.NosonException;

/**
 * Abstract type adapters, all other types of adapters inherit 
 * this abstract class and implement other types of adaptation.
 * 
 * @author nico
 */
public abstract class AbstractTypeAdapter {
	
	public static final Map<Class<?>, AbstractTypeAdapter> TYPE_ADAPTER_MAP;
	
	static{
		TYPE_ADAPTER_MAP = new HashMap<Class<?>, AbstractTypeAdapter>(){
			private static final long serialVersionUID = 7332557419323776675L;
			{
				put(boolean.class, new TypeAdapter_bool());
				put(Boolean.class, new TypeAdapter_Boolean());
				put(byte.class, new TypeAdapter_byt());
				put(Byte.class, new TypeAdapter_Byte());
				put(short.class, new TypeAdapter_sho());
				put(Short.class, new TypeAdapter_Short());
				put(int.class, new TypeAdapter_int());
				put(Integer.class, new TypeAdapter_Integer());
				put(long.class, new TypeAdapter_lon());
				put(Long.class, new TypeAdapter_Long());
				put(float.class, new TypeAdapter_flo());
				put(Float.class, new TypeAdapter_Float());
				put(double.class, new TypeAdapter_doub());
				put(Double.class, new TypeAdapter_Double());
				put(BigDecimal.class, new TypeAdapter_BigDecimal());
				put(Date.class, new TypeAdapter_Date());
				put(Enum.class, new TypeAdapter_Enum());
				put(String.class, new TypeAdapter_String());
			}
		};
		
	}

	public abstract Object typeAdapter(Class<?> clazz, Object target) throws NosonException;
}
