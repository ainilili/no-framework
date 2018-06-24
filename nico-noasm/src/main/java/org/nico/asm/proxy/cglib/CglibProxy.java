package org.nico.asm.proxy.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/** 
 * A class that delegates methods of a class or class based on Cglib encapsulation
 * 
 * @author nico
 * @version createTime：2018年6月23日 下午9:14:48
 */

public class CglibProxy {
	
	/**
	 * Get the proxy object
	 * 
	 * @param beProxyClass
	 * 			Represented class
	 * @param handler
	 * 			{@link InvocationHandler}
	 * @return 
	 * 			Target object
	 */
	public static <T> Object newProxyInstance(Class<T> beProxyClass, final InvocationHandler handler){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beProxyClass);
		enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return handler.invoke(obj, method, args);
            }
        });
		return (T) enhancer.create();
	}
	
}
