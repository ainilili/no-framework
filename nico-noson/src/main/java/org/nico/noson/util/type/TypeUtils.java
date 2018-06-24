package org.nico.noson.util.type;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nico.noson.NosonConfig;
import org.nico.noson.adapter.type.AbstractTypeAdapter;
import org.nico.noson.cache.CacheManager;
import org.nico.noson.entity.NoType;
import org.nico.noson.entity.TypeBean;
import org.nico.noson.exception.NosonException;
import org.nico.noson.scanner.impl.SimpleScanner.SimpleStruct;
import org.nico.noson.scanner.plant.AbstractPlant;
import org.nico.noson.util.reflect.FieldUtils;
import org.nico.noson.util.string.FormatUtils;

/** 
 * Noson type conversion tool
 * 
 * @author nico
 */
public class TypeUtils {

	public static final String BOOLEAN_TRUE = "TRUE";

	public static final String BOOLEAN_FALSE = "FALSE";

	public static final Set<Class<?>> NUM_CLASS_SET = new HashSet<Class<?>>(){
		private static final long serialVersionUID = -1925389609848043520L;
		{
			add(int.class);
			add(short.class);
			add(double.class);
			add(long.class);
			add(float.class);
			add(byte.class);
		}
	};

	/**
	 * Converts the Value in the Json string
	 * 
	 * @param param param
	 * @return obj
	 */
	public static Object typeAllotValue(String param){
		if(param == null)
			return param;
		Object result = param;
		if((result = CacheManager.getValueCache().getCache(param)) != null){
			return result;
		}else{
			try{
				result = NosonConfig.DEFAULT_DATE_FORMAT.parse(param);
			}catch(Exception e0){
				try {
					result = Integer.parseInt(param);
				}catch(NumberFormatException e) {
					try{
						result = Double.parseDouble(param);
					}catch(NumberFormatException e3){
						try{
							result = new BigDecimal(param);
						}catch(NumberFormatException e4){
							if(BOOLEAN_TRUE.equalsIgnoreCase(param) || BOOLEAN_FALSE.equalsIgnoreCase(param)){
								result = Boolean.parseBoolean(param);
							}else{
								if(param.equals("null")){
									result = null;
								}else{
									result = param;
								}
							}
						}
					}
				}
			}
		}
		result = result instanceof String ? FormatUtils.deEscape((String) result) : result;
		CacheManager.getValueCache().putCache(param, result);
		return result;
	}

	/**
	 * Transform the key in Json
	 * 
	 * @param param param
	 * @return value
	 */
	public static String typeAllotKey(String param){
		if(param == null)
			return param;
		String result = param;
		if(CacheManager.getKeyCache().containsCache(param)){
			return String.valueOf(CacheManager.getKeyCache().getCache(param));
		}
		result = result instanceof String ? FormatUtils.deEscape((String) result) : result;
		CacheManager.getKeyCache().putCache(param, result);
		return result;
	}

	/**
	 * Determine if it is a basic type
	 * 
	 * @param clazz Judged class
	 * @return result
	 */
	public static boolean isInseparable(Class<?> clazz){
		boolean inseparable = false;
		if(clazz.isPrimitive()){
			inseparable = true;
		}else if(Number.class.isAssignableFrom(clazz)){
			inseparable = true;
		}else if(clazz.isAssignableFrom(String.class)){
			inseparable = true;
		}else if(clazz.isAssignableFrom(Boolean.class) || clazz.equals(boolean.class)){
			inseparable = true;
		}else if(Date.class.isAssignableFrom(clazz)){
			inseparable = true;
		}else if(Enum.class.isAssignableFrom(clazz)){
			inseparable = true;
		}
		return inseparable;
	}

	/**
	 * Wrapping the transformed object
	 * 
	 * @param obj The object being transformed
	 * @return The wrapped string
	 */
	public static String typeWrap(Class<?> type, Object obj){
		if(obj == null) return emptyWrap(type, NosonConfig.ALLOW_EMPTY_TO_NULL);
		if(isInseparable(obj.getClass())){
			if(obj instanceof String){
				return "\"" + FormatUtils.enEscape((String) obj) + "\"";
			}else if(obj instanceof Date){
				return "\"" + NosonConfig.DEFAULT_DATE_FORMAT.format((Date)obj) + "\"";
			}else if(obj instanceof Enum){
				return "\"" + obj.toString() + "\"";
			}else{
				return obj.toString();
			}
		}
		return "\"" + obj.getClass().getName() + "\"";
	}

	/**
	 * If obj is null. this method can help noson finished convert
	 * 
	 * @param type	Obj's type
	 * @param toNull Whether object is set to null.
	 * @return result
	 */
	public static String emptyWrap(Class<?> type, boolean toNull){
		String result = null;
		if(Object.class.isAssignableFrom(type)){
			if(! toNull){
				if(Number.class.isAssignableFrom(type)){
					result = "0";
				}else if(String.class.isAssignableFrom(type)){
					result = "\"\"";
				}
			}
		}else{
			if(type.isAssignableFrom(int.class)){
				result = "0";
			}else if(type.isAssignableFrom(float.class)){
				result = "0.000";
			}else if(type.isAssignableFrom(double.class)){
				result = "0.000000";
			}else if(type.isAssignableFrom(long.class)){
				result = "0";
			}else if(type.isAssignableFrom(short.class)){
				result = "0";
			}else if(type.isAssignableFrom(byte.class)){
				result = "0";
			}else if(type.isAssignableFrom(boolean.class)){
				result = "false";
			}
		}
		return result;
	}

	/**
	 * Gets an instance that represents the structure type (List, Map, Object)
	 * 
	 * @param clazz 
	 * @return
	 * @throws NosonException
	 */
	public static Object getStructInstance(Class<?> clazz) throws NosonException{
		Object target = AbstractPlant.TYPE_PLANT.get(clazz);
		if(target == null){
			if(clazz.isArray()){
				target = new LinkedList<Object>();
			}else{
				try {
					target = clazz.newInstance();
				} catch (Exception e) {
					throw new NosonException(e.getMessage(), e);
				}
			}
		}else{
			target = ((AbstractPlant)target).get();
		}
		return target;
	}

	/**
	 * Insert param into the target
	 * 
	 * @param target 
	 * 			The target object
	 * @param key
	 * 			Injection parameter name
	 * @param value 
	 * 			Injection parameters
	 * @throws NosonException
	 */
	public static void setParamIntoObject(Object target, SimpleStruct currentStruct) throws NosonException{
		String key = currentStruct.getName();
		Object value = currentStruct.getValue();
		//对类型为array的特殊处理
		if(currentStruct.getClassType() != null 
				&& currentStruct.getValue() != null 
				&& currentStruct.getClassType().isArray()){
			Collection list = (Collection) value;
			Class<?> componentType = currentStruct.getClassType().getComponentType();
			Object array = Array.newInstance(componentType, list.size());
			int index = 0;
			for(Object obj: list){
				Array.set(array, index++, convertType(componentType, obj));
			}
			value = array;
		}
		if(target instanceof Collection){
			((Collection<Object>) target).add(value);
		}else if(target instanceof Map){
			((Map) target).put(key, value);
		}else if(target.getClass().isArray()){
			target = getNewArray(target, target.getClass().getComponentType(), value);
		}else{
			//			AbstractASMClassProxy asmClassProxy = null;
			//			if((asmClassProxy = (AbstractASMClassProxy) CacheManager.getClassProxyCache().getCache(target.getClass().getName())) == null){
			//				asmClassProxy = asmClassProxyBuilder.getASMClassProxy(target.getClass());
			//				CacheManager.getClassProxyCache().putCache(target.getClass().getName(), asmClassProxy);
			//			}
			TypeBean<?> typeBean = getFieldType(target.getClass(), key);

			Class<?> type = typeBean == null ? null : typeBean.getMainClass();

			if(type != null){
				Field field = getField(target.getClass(), key);

				value = convertType(type, value);
				if(! field.isAccessible()) {
					field.setAccessible(true);
				}
				//这里不检测
				//				if(VerifyHelper.startedVerify()) {
				//					NosonVerify.verify(field, value);
				//				}
				try {
					field.set(target, value);
				} catch (Exception e) {
					throw new NosonException(e);
				}
			}

			//			asmClassProxy.set(target, key, value);
		}
	}

	/**
	 * Get {@link Field} type from clazz by field name with cache.
	 * 
	 * @param clazz 
	 * 			Target class
	 * @param fieldName 
	 * 			Field name
	 * @return
	 * 			Field Type
	 * @throws NosonException
	 */
	public static TypeBean<?> getFieldType(Class<?> clazz, String fieldName) throws NosonException{
		TypeBean<?> typeBean = null;
		String fieldCacheKey = clazz.getName() + fieldName;
		if((typeBean = (TypeBean<?>) CacheManager.getFieldTypeCache().getCache(fieldCacheKey)) == null){
			Field field = FieldUtils.getField(fieldName, clazz);
			if(field != null){
				typeBean = TypeUtils.getGenericityType(field);
				CacheManager.getFieldTypeCache().putCache(fieldCacheKey, typeBean);
			}
		}
		return typeBean;
	}

	/**
	 * Get {@link Field} from clazz by field name with cache.
	 * 
	 * @param clazz 
	 * 			Target class
	 * @param fieldName 
	 * 			Field name
	 * @return
	 * 			Field
	 * @throws NosonException
	 */
	public static Field getField(Class<?> clazz, String fieldName) throws NosonException{
		Field field = null;
		String fieldCacheKey = clazz.getName() + fieldName;
		if((field = (Field) CacheManager.getFieldCache().getCache(fieldCacheKey)) == null){
			field = FieldUtils.getField(fieldName, clazz);
			CacheManager.getFieldCache().putCache(fieldCacheKey, field);
		}
		return field;
	}

	public static <T> Object getNewArray(Object array, Class<T> componentType, Object value) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, NosonException{
		int len = Array.getLength(array);
		Object newArray = Array.newInstance(componentType, len + 1);
		System.arraycopy(array, 0, newArray, 0, len);
		Array.set(newArray, len, convertType(componentType, value));
		return newArray;
	}

	/**
	 * Generically iterate over the type to be parsed, storing 
	 * the main transformation type in an array, primarily providing 
	 * an ordered type match to the Convert phase
	 * 
	 * @param clazz The type to be resolved
	 * @return Type and its generic iteration
	 */
	public static List<Class<?>> getGenericityTypeIterate(Class<?> clazz){
		List<Class<?>> typeList = new ArrayList<Class<?>>();
		if(clazz != null){
			TypeBean<?> header = getGenericityType(clazz);
			typeList = getGenericityTypeIterate(header);
		}
		return typeList;
	}

	public static List<Class<?>> getGenericityTypeIterate(TypeBean<?> header){
		List<Class<?>> typeList = new ArrayList<Class<?>>();

		//如果是NoType类型，则跳过其本身处理，获取其第一个泛型处理！
		if(NoType.class.isAssignableFrom(header.getMainClass())){
			header = header.getGenericityBeans()[0];
		}
		typeList.add(header.getMainClass());
		TypeBean<?> currentType = header;
		while(currentType != null && currentType.getGenericityBeans() != null){
			TypeBean<?>[] typeBeans = currentType.getGenericityBeans();
			switch(typeBeans.length){
			case 0:
				typeList.add(Object.class);
				currentType = null;
				break;
			case 1:
				currentType = typeBeans[0];
				typeList.add(currentType.getMainClass());
				break;
			case 2:
				currentType = typeBeans[1];
				typeList.add(currentType.getMainClass());
				break;
			default:
				throw new RuntimeException("Exceeding the limit of the generic length !!");
			}
		}
		return typeList;
	}

	/**
	 * Generic iteration of the type to be parsed and packaged into a TypeBean tree structure.
	 * 
	 * @param clazz class
	 * @return {@link TypeBean}
	 */
	public static TypeBean<?> getGenericityType(Class<?> clazz){
		TypeBean<?> typeBean = new TypeBean(clazz);
		if(clazz == null)
			throw new NullPointerException("Class is null");
		Type superclass = clazz.getGenericSuperclass();
		if(superclass != null){
			if(superclass instanceof Class) {
				//				throw new RuntimeException("Missing type parameter.");
			} else {
				ParameterizedType parameterized = (ParameterizedType)superclass;
				if(! Modifier.isPublic(clazz.getModifiers())){
					typeBean.setMainClass((Class)parameterized.getRawType());
				}
				Type[] types = typesWrapper(clazz, parameterized.getActualTypeArguments());
				typeBean.setGenericityBeans(getGenericityTypes(types));
			}
		}
		return typeBean;
	}

	public static TypeBean<?> getGenericityType(Field field){
		Class<?> clazz = field.getType();
		TypeBean<?> typeBean = new TypeBean(clazz);
		try {
			Type type = field.getGenericType();
			if(type instanceof ParameterizedType) {
				ParameterizedType parameterized = (ParameterizedType)field.getGenericType();
				if(! Modifier.isPublic(clazz.getModifiers())){
					typeBean.setMainClass((Class)parameterized.getRawType());
				}
				Type[] types = TypeUtils.typesWrapper(clazz, parameterized.getActualTypeArguments());
				typeBean.setGenericityBeans(TypeUtils.getGenericityTypes(types));
			}
		}catch(Exception e) {
		}
		
		return typeBean;
	}

	public static Type[] typesWrapper(Class<?> clazz, Type[] types){
		int len = 1;
		if(Map.class.isAssignableFrom(clazz)){
			len = 2;
		}else if(Collection.class.isAssignableFrom(clazz)){
			len = 1;
		}
		if(types == null){
			types = new Type[]{String.class, Object.class};
		}else if(types.length != len){
			Type[] newTypes = new Type[len];
			for(int index = 0; index < newTypes.length; index ++){
				if(index < types.length){
					newTypes[index] = types[index];
				}else{
					newTypes[index] = Object.class;
				}
			}
			types = newTypes;
		}
		return types;
	}

	/**
	 * Gets an array of generic types for Class
	 * 
	 * @param clazz class
	 * @return {@link TypeBean}
	 */
	public static TypeBean<?>[] getGenericityTypes(Type[] types){
		if(types.length == 0)
			throw new ArrayIndexOutOfBoundsException(0);
		TypeBean<?>[] typeBeans = new TypeBean[types.length];
		int index = 0;
		for(Type type: types){
			TypeBean<?> typeBean = null;
			if(type instanceof ParameterizedType){
				Class<?> targetClass = (Class<?>)((ParameterizedType) type).getRawType();
				typeBean = new TypeBean(targetClass);
				typeBean.setGenericityBeans(getGenericityTypes(typesWrapper(targetClass, ((ParameterizedType) type).getActualTypeArguments())));
			}else{
				typeBean = new TypeBean((Class<?>)type);
			}
			typeBeans[index ++] = typeBean;
		}
		return typeBeans;
	}

	/**
	 * Converts target to clazz type object
	 * 
	 * @param clazz The target object type to be transformed
	 * @param target Turn to object
	 * @return The transformed object
	 * @throws NosonException Error
	 */
	public static Object convertType(Class<?> clazz, Object target) throws NosonException{
		if(target == null && clazz == null){
			return null;
		}
		if(target == null && clazz != null){
			return emptyWrap(clazz, NosonConfig.ALLOW_EMPTY_TO_NULL);
		}

		Class<?> key = clazz;

		if(Enum.class.isAssignableFrom(clazz)){
			key = Enum.class;
		}
		AbstractTypeAdapter typeAdapter = null;
		if( null != (typeAdapter = AbstractTypeAdapter.TYPE_ADAPTER_MAP.get(key))){
			return typeAdapter.typeAdapter(clazz, target);
		}else{
			return target;
		}
	}

}
