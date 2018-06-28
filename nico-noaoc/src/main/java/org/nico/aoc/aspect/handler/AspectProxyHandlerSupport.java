package org.nico.aoc.aspect.handler;

import java.lang.reflect.Method;
import java.util.List;

import org.nico.aoc.aspect.buddy.AspectBuddy.ExecutionEntity.MethodWrapper;
import org.nico.aoc.scan.entity.AspectDic;

/** 
 * Store the information of the agent.
 * 
 * @author nico
 * @version createTime：2018年3月31日 下午9:35:44
 */
public class AspectProxyHandlerSupport {

	protected Method aspectMethod;  

	protected Object aspectObj;

	protected Object beProxyObject;

	protected List<MethodWrapper> beProxyMethods;
	
	protected AspectDic aspectDic;

	public Object getBeProxyObject() {
		return beProxyObject;
	}

}
