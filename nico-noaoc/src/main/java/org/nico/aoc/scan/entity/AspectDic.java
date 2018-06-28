package org.nico.aoc.scan.entity;


import java.lang.annotation.Annotation;

import org.nico.aoc.scan.annotations.aspect.After;
import org.nico.aoc.scan.annotations.aspect.Around;
import org.nico.aoc.scan.annotations.aspect.Before;
import org.nico.aoc.scan.annotations.aspect.Wrong;

public enum AspectDic {

	BEFORE(Before.class),
	
	AROUND(Around.class),
	
	AFTER(After.class),
	
	WRONG(Wrong.class),
	
	NULL(null),
	;
	
	private Class<? extends Annotation> annotationClass;

	private AspectDic(Class<? extends Annotation> annotationClass) {
		this.annotationClass = annotationClass;
	}
	
	public Class<? extends Annotation> getAnnotationClass() {
		return annotationClass;
	}

	public boolean eq(Class<?> clazz){
		if(clazz == null){
			return false;
		}
		return clazz.getName().equals(annotationClass.getName());
	}
	
}
