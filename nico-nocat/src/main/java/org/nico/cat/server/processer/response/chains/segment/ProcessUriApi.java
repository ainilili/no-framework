package org.nico.cat.server.processer.response.chains.segment;

import org.nico.cat.server.container.Container;
import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.container.moudle.realize.executor.ExecutorFactory;
import org.nico.cat.server.exception.error.ProcessingRequestException;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.util.string.StringUtils;

/**
 * Process uri filter
 * @author nico
 * @date 2018年1月11日
 */
public class ProcessUriApi extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		if(! response.isPushed()){
			if(container.getApis() != null){
				for(ApiModule module: container.getApis()){
					if(apiExcutor.matching(request.getUri(), module)){
						try {
							apiExcutor.execute(request, response, module);
							if(StringUtils.isNotBlank(request.getUriRedirect())){
								request.setUriRedirect(request.getUriRedirect());
								break;
							}else if(StringUtils.isNotBlank(request.getApiRedirect())){
								request.setUri(request.getApiRedirect());
								request.setApiRedirect(null);
								return process(request, response);
							}else if(response.isPushed()){
								response.setHttpcode(HttpCode.HS200);
							}else{
								try {
									throw new ProcessingRequestException("Request is no next action.");
								} catch (ProcessingRequestException e) {
									response.setResponseBody(e);
									return response;
								}
							}
						} catch (Exception e) {
							response.setResponseBody(e);
							return response;
						}
					}
				}
			}
		}
		return next(request, response);
	}

}
