package org.nico.aoc.scan.buddy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.nico.aoc.scan.annotations.ConstructionLabel;
import org.nico.aoc.scan.annotations.Label;
import org.nico.aoc.scan.entity.LabelType;
import org.nico.util.collection.ArrayUtils;
import org.nico.util.collection.CollectionUtils;

/** 
 * Annotation buddy
 * @author nico
 * @version createTime：2018年1月21日 下午3:08:02
 */

public class AnnotationBuddy {

	/**
	 * Gets the Field for the Label annotation of the specified type.
	 * 
	 * @param clazz The target class
	 * @param type The target type
	 * @return	List<Field>
	 */
	public static List<Field> getLabelFields(Class<?> clazz, LabelType type){
		List<Field> fieldList = new ArrayList<Field>();
		List<Field> allFields = new ArrayList<Field>();
		getFields(allFields, clazz);
		if(CollectionUtils.isNotBlank(allFields)){
			for(Field field: allFields){
				Label label = null;
				if((label = field.getDeclaredAnnotation(Label.class)) != null){
					if(type == null){
						fieldList.add(field);
					}else{
						if(label.type() == type){
							fieldList.add(field);
						}
					}
				}
			}
		}
		return fieldList;
	}
	
	/**
	 * Get field by clazz & superClass
	 * 
	 * @param fieldList 
	 * 			Stroge Field
	 * @param clazz
	 * 			Target class
	 */
	private static void getFields(List<Field> fieldList, Class<?> clazz){
		if(clazz != null){
			Field[] fields = clazz.getDeclaredFields();
			if(ArrayUtils.isNotBlank(fields)){
				fieldList.addAll(Arrays.asList(fields));
			}
			getFields(fieldList, clazz.getSuperclass());
		}
	}

	/**
	 * Gets the Field for the Label annotation of the all.
	 * 
	 * @param clazz The target class
	 * @return	List<Field>
	 */
	public static List<Field> getLabelFields(Class<?> clazz){
		return getLabelFields(clazz, null);
	}
	
	/**
	 * Gets the constructs that need to be injected.
	 * 
	 * @param clazz The target class
	 * @return {@link Constructor}
	 */
	public static Constructor<?> getLabelConstructionParams(Class<?> clazz){
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		if(ArrayUtils.isNotBlank(constructors)){
			for(Constructor<?> constructor: constructors){
				if(constructor.isAnnotationPresent(ConstructionLabel.class)){
					return constructor;
				}
			}
		}
		return null;
	}
}