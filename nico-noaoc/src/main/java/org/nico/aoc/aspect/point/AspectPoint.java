package org.nico.aoc.aspect.point;

import java.lang.reflect.Method;

import org.nico.aoc.aspect.buddy.AspectBuddy;

/** 
 * Load the information of the agent and provide the agent method to execute the entry.
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午10:32:06
 */

public class AspectPoint{
	
	protected Object beProxyObj;
	
	private Object sourceObj;
	
	protected Method method;
	
	protected Object[] args;
	
	public AspectPoint(Object beProxyObj, Method method, Object[] args) {
		this.beProxyObj = beProxyObj;
		this.method = method;
		this.args = args;
		if(beProxyObj != null){
			this.sourceObj = AspectBuddy.getTargetObject(beProxyObj);
		}
	}
	
	public Object getSourceObject(){
		return sourceObj;
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
