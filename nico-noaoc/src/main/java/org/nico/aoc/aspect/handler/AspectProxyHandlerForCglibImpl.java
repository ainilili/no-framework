package org.nico.aoc.aspect.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import org.nico.aoc.aspect.AspectProxy;
import org.nico.aoc.aspect.buddy.AspectBuddy.ExecutionEntity.MethodWrapper;
import org.nico.aoc.aspect.point.ProcessingAspectPoint;
import org.nico.aoc.scan.entity.AspectDic;

/** 
 * 
 * @author nico
 * @version createTime：2018年3月7日 下午10:40:39
 */

public class AspectProxyHandlerForCglibImpl extends AspectProxyHandlerSupport implements InvocationHandler{

	public AspectProxyHandlerForCglibImpl(Object aspectObj, Method aspectMethod, List<MethodWrapper> beProxyMethods, Object beProxyObject, AspectDic aspectDic){  

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
				point.process();
			}else if(aspectDic == AspectDic.AFTER){
				point.process();
				aspectMethod.invoke(aspectObj, point);
			}else if(aspectDic == AspectDic.WRONG){
				try{
					point.process();
				}catch(Throwable e){
					aspectMethod.invoke(aspectObj, point, e);
				}
			}else if(aspectDic == AspectDic.NULL){
				AspectProxy proxyObj = (AspectProxy)aspectObj;
				try{
					proxyObj.before(point);
					result = proxyObj.around(point);
					proxyObj.after(point);
				}catch(Throwable e){
					proxyObj.wrong(point, e);
				}
			}
		}else{
			result = point.process();
		}
		return result;
	}


}
