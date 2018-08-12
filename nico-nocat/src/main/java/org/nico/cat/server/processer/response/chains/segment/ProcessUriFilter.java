package org.nico.cat.server.processer.response.chains.segment;

import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.FilterModule;
import org.nico.cat.server.container.moudle.realize.executor.ExecutorFactory;
import org.nico.cat.server.container.moudle.realize.executor.ModuleExecutor;
import org.nico.cat.server.container.moudle.realize.executor.impl.FilterExecutor;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Process uri filter
 * @author nico
 * @date 2018年1月11日
 */
public class ProcessUriFilter extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		
		if(container.getFilters() != null){
			for(FilterModule module: container.getFilters()){
				if(filterExcutor.matching(request.getUri(), module)){
					try {
						filterExcutor.execute(request, response, module);
					} catch (Exception e) {
						response.setResponseBody(e);
						return response;
					}
				}
			}
		}
		return next(request, response);
	}

}
