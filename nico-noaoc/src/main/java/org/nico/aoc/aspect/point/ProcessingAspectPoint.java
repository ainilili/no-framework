package org.nico.aoc.aspect.point;

import java.lang.reflect.Method;

public class ProcessingAspectPoint extends AspectPoint{

	public ProcessingAspectPoint(Object beProxyObj, Method method, Object[] args) {
		super(beProxyObj, method, args);
	}

	public Object process() throws Throwable {
		return beProxyObj.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).invoke(beProxyObj, args);
	}
}
