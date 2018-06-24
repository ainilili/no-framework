package org.nico.aoc.scan.entity;


import java.lang.annotation.Annotation;

import org.nico.aoc.scan.annotations.After;
import org.nico.aoc.scan.annotations.Around;
import org.nico.aoc.scan.annotations.Before;
import org.nico.aoc.scan.annotations.Wrong;

public enum AspectDic {

	BEFORE(Before.class),
	
	AROUND(Around.class),
	
	AFTER(After.class),
	
	WRONG(Wrong.class),
	
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
