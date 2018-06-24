package org.nico.cat.server.container.moudle.realize.executor;

import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.moudle.realize.executor.impl.ApiExecutor;
import org.nico.cat.server.container.moudle.realize.executor.impl.FilterExecutor;

/**
 * The factory of the executor
 * @author nico
 * @date 2018年1月11日
 */
public class ExecutorFactory {

	public static <T> ModuleExecutor<T> getExecutor(Class<T> clazz){
		if(FilterModule.class.isAssignableFrom(clazz)){
			return (ModuleExecutor<T>) FilterExecutor.getInstance();
		}else if(ApiModule.class.isAssignableFrom(clazz)){
			return (ModuleExecutor<T>) ApiExecutor.getInstance();
		}
		return null;
	}
	
	public static enum ExecutorType{
	
		FILTER,
		
		API;
	}
}
