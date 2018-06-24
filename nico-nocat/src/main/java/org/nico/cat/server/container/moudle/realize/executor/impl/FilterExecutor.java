package org.nico.cat.server.container.moudle.realize.executor.impl;

import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.moudle.realize.entry.Filter;
import org.nico.cat.server.container.moudle.realize.executor.ModuleExecutor;
import org.nico.cat.server.exception.runtime.ExecuteMoudleException;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.matching.MatchingUtils;


public class FilterExecutor implements ModuleExecutor<FilterModule>{

	private Logging logging = LoggingHelper.getLogging(FilterExecutor.class);

	private FilterExecutor(){}
	
	private static FilterExecutor executor = new FilterExecutor();
	
	public static FilterExecutor getInstance(){
		return executor;
	}
	
	@Override
	public boolean matching(String uri, FilterModule module) {
		return MatchingUtils.isMatch(module.getUri(), uri);
	}

	@Override
	public void execute(Request request, Response response, FilterModule module){
		Filter filter = null;
		try {
			Container container = Container.getInstance();
			if(! module.isSingleton()){
				if(container.containsFilter(module.getUri())){
					filter = container.getFilter(module.getUri());
				}
			}
			if(filter == null){
				filter = module.getHandler().newInstance();
			}
			container.putFilter(module.getUri(), filter);
			filter.filter(request, response);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ExecuteMoudleException("Excute filter " + module.getUri() + " fail：" + e.getMessage());
		}
	}

}
