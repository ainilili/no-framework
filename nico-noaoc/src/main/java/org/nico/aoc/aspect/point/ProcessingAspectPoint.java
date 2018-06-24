package org.nico.aoc.aspect.point;

import java.lang.reflect.Method;

public class ProcessingAspectPoint extends AspectPoint{

	public ProcessingAspectPoint(Object obj, Method method, Object[] args) {
		super(obj, method, args);
	}

	public Object process() throws Throwable {
		return method.invoke(obj, args);
	}
}
