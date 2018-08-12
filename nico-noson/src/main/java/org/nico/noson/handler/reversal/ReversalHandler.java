package org.nico.noson.handler.reversal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.nico.noson.Noson;
import org.nico.noson.NosonConfig;
import org.nico.noson.annotations.JsonIgnore;
import org.nico.noson.cache.CacheManager;
import org.nico.noson.entity.ReversalRecorder;
import org.nico.noson.util.type.TypeUtils;

public abstract class ReversalHandler {

	public ReversalHandler nextHandler;

	public String handleList(Collection<Object> list, ReversalRecorder recorder){
		recorder.add(list);
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int index = 0;
		for(Object value: list){
			value = cycleCheck(recorder, value);
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}
		builder.append("]");
		recorder.remove(list);
		return builder.toString();
	}
	
	public String handleArray(Object array, ReversalRecorder recorder){
		recorder.add(array);
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int index = 0;
		int len = Array.getLength(array);
		for(int point = 0; point < len; point ++){
			Object value = Array.get(array, point);
			value = cycleCheck(recorder, value);
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}
		builder.append("]");
		recorder.remove(array);
		return builder.toString();
	}
	
	public String handleNoson(Noson noson, ReversalRecorder recorder){
		recorder.add(noson);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(Entry<String, Object> record: noson.entrySet()){
			Object value = cycleCheck(recorder, record.getValue());
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append("\"" + record.getKey() +"\":");
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}

		builder.append("}");
		recorder.remove(noson);
		return builder.toString();
	}

	public String handleMap(Map<Object, Object> map, ReversalRecorder recorder){
		recorder.add(map);
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int index = 0;
		for(Entry<Object, Object> entry: map.entrySet()){
			Object value = cycleCheck(recorder, entry.getValue());
			if(value != null || NosonConfig.ALLOW_EMPTY){
				if(index > 0){
					builder.append(",");
				}
				builder.append("\"" + entry.getKey() +"\":");
				builder.append(value == null ? null : handleCommon(value.getClass(), value, recorder));
				index ++;
			}
		}
		builder.append("}");
		recorder.remove(map);
		return builder.toString();
	}

	public String handleObject(Object obj, ReversalRecorder recorder){
		recorder.add(obj);
		if(obj == null) return "{}";
		StringBuilder builder = new StringBuilder();
		Class<?> clazz = obj.getClass();
		
		Field[] fields = null;
		if((fields = (Field[]) CacheManager.getFieldTypeCache().getCache(clazz.getName())) == null){
			fields = clazz.getDeclaredFields();
			CacheManager.getFieldTypeCache().putCache(clazz.getName(), fields);
		}
		
//		AbstractASMClassProxy asmClassProxy = null;
//		if((asmClassProxy = (AbstractASMClassProxy) CacheManager.getClassProxyCache().getCache(clazz.getName())) == null){
//			try {
//				asmClassProxy = TypeUtils.asmClassProxyBuilder.getASMClassProxy(clazz);
//			} catch (NosonException e) {
//				e.printStackTrace();
//			}
//			CacheManager.getClassProxyCache().putCache(clazz.getName(), asmClassProxy);
//		}
		
		builder.append("{");
		int index = 0;
		for(Field field: fields){
			Object value;
			try {
//				value = asmClassProxy.get(obj, field.getName());
				if(! field.isAccessible()) {
					field.setAccessible(true);
				}
				value = field.get(obj);
				value = cycleCheck(recorder, value);
				if(value != null || NosonConfig.ALLOW_EMPTY){
					if(field.getDeclaredAnnotation(JsonIgnore.class) != null) continue;
					if(! NosonConfig.ALLOW_MODIFY.contains(Modifier.toString(field.getModifiers()))) continue;
					if(index > 0){
						builder.append(",");
					}
					builder.append("\"" + field.getName() +"\":");
					builder.append(handleCommon(value == null ? field.getType() : value.getClass(), value, recorder));
					index ++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		builder.append("}");
		recorder.remove(obj);
		return builder.toString();
	}

	public String handleCommon(Class<?> type, Object currentObj, ReversalRecorder recorder){
		StringBuilder builder = new StringBuilder();
		if(TypeUtils.isInseparable(type) || currentObj == null){
			builder.append(TypeUtils.typeWrap(type, currentObj));
		}else if(Collection.class.isAssignableFrom(type)){
			builder.append(handleList((Collection<Object>)currentObj, recorder));
		}else if(Noson.class.isAssignableFrom(type)){
			builder.append(handleNoson((Noson)currentObj, recorder));
		}else if(Map.class.isAssignableFrom(type)){
			builder.append(handleMap((Map<Object, Object>)currentObj, recorder));
		}else if(type.isArray()){
			builder.append(handleArray(currentObj, recorder));
		}else{
			builder.append(handleObject(currentObj, recorder));
		}
		return builder.toString();
	}

	public Object cycleCheck(ReversalRecorder recorder, Object value){
		if(value == null){
			return null;
		}
		if(recorder.contains(value) 
				&& 
				recorder.getCount(value) > NosonConfig.ALLOW_CYCLE_MAX_COUNT){
			return null;
		}
		return value;
	}

	public abstract String handle(Object obj);
}
