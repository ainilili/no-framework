package org.nico.aoc.aspect.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import org.nico.aoc.aspect.buddy.AspectBuddy.ExecutionEntity.MethodWrapper;
import org.nico.aoc.aspect.point.ProcessingAspectPoint;
import org.nico.aoc.scan.entity.AspectDic;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午10:40:39
 */

public class AspectCglibProxyHandlerImpl extends AspectHandlerInfo implements InvocationHandler{

	public AspectCglibProxyHandlerImpl(Object aspectObj, Method aspectMethod, List<MethodWrapper> beProxyMethods, Object beProxyObject, AspectDic aspectDic){  

		this.aspectMethod = aspectMethod;  
		this.aspectObj = aspectObj;

		this.beProxyMethods = beProxyMethods;
		this.beProxyObject = beProxyObject;

		this.aspectDic = aspectDic;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		ProcessingAspectPoint point = new ProcessingAspectPoint(beProxyObject, method, args);
		Object result = null;
		if(beProxyMethods.contains(new MethodWrapper(method))){
			if(aspectDic == AspectDic.AROUND){
				result = aspectMethod.invoke(aspectObj, point);
			}else if(aspectDic == AspectDic.BEFORE){
				aspectMethod.invoke(aspectObj, point);
				result = point.process();
			}else if(aspectDic == AspectDic.AFTER){
				result = point.process();
				aspectMethod.invoke(aspectObj, point);
			}else if(aspectDic == AspectDic.WRONG){
				try{
					result = point.process();
				}catch(Throwable e){
					aspectMethod.invoke(aspectObj, point, e);
				}
			}
		}else{
			result = point.process();
		}
		return result;
	}


}
