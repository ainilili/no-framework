package org.nico.cat.mvc.verify;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.nico.cat.mvc.scan.annotations.*;

/** 
 * 该类用于字段验证</br>
 * 验证{@link NoVerfiy}的字段
 * 
 * 
 * @author nico
 * @version createTime：2018年3月25日 下午4:02:02
 */
public class ObjectVerify {

	private static final Map<Class<?>, Verifier<?>> VERIFIERS = new HashMap<Class<?>, Verifier<?>>(){
		private static final long serialVersionUID = -6056461536531069544L;
		{
			put(NotNull.class, new NotNullVerifier());
			put(Range.class, new RangeVerifier());
			put(Length.class, new LengthVerifier());
			put(Match.class, new MatchVerifier());
		}
	};
	
	private Map<Class<?>, Field[]> cacheForFields = new HashMap<Class<?>, Field[]>();
	
	/**
	 * Verify obj
	 * 
	 * @param obj
	 * @return
	 */
	public boolean verify(Object obj) {
		if(obj == null) {
			return true;
		}
		if(obj instanceof Collection) {
			Collection coll = (Collection) obj;
			if(! coll.isEmpty()) {
				for(Object o: coll) {
					verify(o);
				}
			}
		}else if(obj instanceof Map){
			Map map = (Map) obj;
			if(! map.isEmpty()) {
				for(Object o: map.values()) {
					verify(o);
				}
			}
		}else{
			verifyObj(obj);
		}
		return true;
	}
	
	/**
	 * Verify obj
	 * 
	 * @param obj
	 * @return
	 */
	public boolean verifyObj(Object obj) {
		if(obj == null) {
			return true;
		}
		Class<?> clazz = obj.getClass();
		
		Field[] fields = null;
		
		if((fields = cacheForFields.get(clazz)) == null) {
			fields = clazz.getDeclaredFields();
		}
		
		if(fields == null || fields.length == 0) {
			return true;
		}
		
		for(Field field: fields) {
			
			if(! field.isAccessible()) {
				field.setAccessible(true);
			}
			
			Object value;
			try {
				value = field.get(obj);
				if(field.isAnnotationPresent(Verify.class)) {
					verify(value);
				}else {
					verify(field, value);
				}
			} catch (IllegalArgumentException e) {
				//jump
			} catch (IllegalAccessException e) {
				//jump
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * Field verify
	 * 
	 * @param field be verify field
	 * @param value value
	 * @return boolean
	 * @throws VerifyException
	 */
	public boolean verify(Field field, Object value){
		if(field == null) {
			throw new NullPointerException("verify field is null.");
		}
		verify(field.getDeclaredAnnotations(), value);
		return true;
	}
	
	public boolean verify(Parameter param, Object value){
		if(param == null) {
			throw new NullPointerException("verify param is null.");
		}
		verify(param.getDeclaredAnnotations(), value);
		return true;
	}
	
	private void verify(Annotation[] annotations, Object value) {
		if(annotations != null && annotations.length > 0) {
			for(Annotation anno: annotations) {
				if(anno.annotationType().isAnnotationPresent(NV.class)) {
					Verifier<Annotation> verifier = (Verifier<Annotation>) VERIFIERS.get(anno.annotationType());
					if(verifier != null) {
						if(! verifier.verify(anno, value)) {
							verifier.throwError(anno);
						}
					}
				}
			}
		}
	}
	
}
