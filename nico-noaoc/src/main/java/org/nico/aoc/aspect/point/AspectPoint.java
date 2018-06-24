package org.nico.aoc.aspect.point;

import java.lang.reflect.Method;

/** 
 * Load the information of the agent and provide the agent method to execute the entry.
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午10:32:06
 */

public class AspectPoint{
	
	protected Object obj;
	
	protected Method method;
	
	protected Object[] args;
	
	public AspectPoint(Object obj, Method method, Object[] args) {
		this.obj = obj;
		this.method = method;
		this.args = args;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	
}
