package org.nico.noson.entity;

import java.util.Arrays;

public class TypeBean<T> {

	private Class<T> mainClass;
	
	private TypeBean<?>[] genericityBeans;

	public TypeBean(Class<T> mainClass, TypeBean<?>[] genericityBeans) {
		this.mainClass = mainClass;
		this.genericityBeans = genericityBeans;
	}
	
	public TypeBean(Class<T> mainClass) {
		super();
		this.mainClass = mainClass;
	}

	public Class<T> getMainClass() {
		return mainClass;
	}

	public void setMainClass(Class<T> mainClass) {
		this.mainClass = mainClass;
	}

	public TypeBean<?>[] getGenericityBeans() {
		return genericityBeans;
	}

	public void setGenericityBeans(TypeBean<?>[] genericityBeans) {
		this.genericityBeans = genericityBeans;
	}

	@Override
	public String toString() {
		return "TypeBean [mainClass=" + mainClass + ", genericityBeans=" + Arrays.toString(genericityBeans) + "]";
	}
}
