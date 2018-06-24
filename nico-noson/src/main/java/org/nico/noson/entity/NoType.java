package org.nico.noson.entity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/** 
 * Stroge type of the be converted class
 * 
 * @author nico
 */
public class NoType<T>{
	
	final Type type;

	//这里的空参构造方法权限修饰符是protected,那木只有其子类可访问，预示着要使用子类构造。
	protected NoType() {
		this.type = getSuperclassTypeParameter(this.getClass());//这里传入的子类，后面2行不用看
	}

	static Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();//获取到子类的父类Type
		if(superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		} else {
			ParameterizedType parameterized = (ParameterizedType)superclass;//将Type类型向下转型为参数化类型ParameterizedType
			return parameterized.getActualTypeArguments()[0];//这里getActualTypeArguments()返回的是一个数组，由于只有一个泛型参数,直接[0]。
		}
	}

	public Type getType() {
		return type;
	}

}
