package org.nico.noson.scanner.plant;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractPlant {

	public final static Map<Class<?>, AbstractPlant> TYPE_PLANT = new HashMap<Class<?>, AbstractPlant>();
	
	static{
		TYPE_PLANT.put(byte[].class, new ByteArrayPlant());
		TYPE_PLANT.put(Byte[].class, new ByteObjArrayPlant());
		
		TYPE_PLANT.put(short[].class, new ShortArrayPlant());
		TYPE_PLANT.put(Short[].class, new ShortObjArrayPlant());
		
		TYPE_PLANT.put(int[].class, new IntArrayPlant());
		TYPE_PLANT.put(Integer[].class, new IntObjArrayPlant());
		
		TYPE_PLANT.put(long[].class, new LongArrayPlant());
		TYPE_PLANT.put(Long[].class, new LongObjArrayPlant());
		
		TYPE_PLANT.put(float[].class, new FloatArrayPlant());
		TYPE_PLANT.put(Float[].class, new FloatObjArrayPlant());
		
		TYPE_PLANT.put(double[].class, new DoubleArrayPlant());
		TYPE_PLANT.put(Double[].class, new DoubleObjArrayPlant());
		
		
		TYPE_PLANT.put(BigDecimal[].class, new BigDecimalArrayPlant());
		TYPE_PLANT.put(List.class, new ListPlant());
		TYPE_PLANT.put(Set.class, new SetPlant());
		TYPE_PLANT.put(Map.class, new MapPlant());
		TYPE_PLANT.put(Collection.class, new CollectionPlant());
		
	}
	
	public abstract <T extends Object> T get();
}
