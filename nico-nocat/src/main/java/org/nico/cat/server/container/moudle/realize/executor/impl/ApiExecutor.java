package org.nico.cat.server.container.moudle.realize.executor.impl;

import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.moudle.realize.entry.Api;
import org.nico.cat.server.container.moudle.realize.executor.ModuleExecutor;
import org.nico.cat.server.exception.runtime.ExecuteMoudleException;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.util.matching.MatchingUtils;

public class ApiExecutor implements ModuleExecutor<ApiModule>{

	private ApiExecutor(){}
	
	private static ApiExecutor executor = new ApiExecutor();
	
	public static ApiExecutor getInstance(){
		return executor;
	}
	
	@Override
	public boolean matching(String uri, ApiModule module) {
		return MatchingUtils.isMatch(module.getUri(), uri);
	}

	@Override
	public void execute(Request request, Response response, ApiModule module) throws Exception {
		try {
			Api api = null;
			Container container = Container.getInstance();
			if(! module.isSingleton()){
				if(container.containsApi(module.getUri())){
					api = container.getApi(module.getUri());
				}
			}
			if(api == null){
				api = module.getHandler().newInstance();
			}
			container.putApi(module.getUri(), api);
			api.setModule(module);
			api.doService(request, response);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ExecuteMoudleException("Excute api " + module.getUri() + " fail：" + e.getMessage());
		}
	}



}
