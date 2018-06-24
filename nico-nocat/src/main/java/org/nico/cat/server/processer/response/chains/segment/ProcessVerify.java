package org.nico.cat.server.processer.response.chains.segment;

import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

public class ProcessVerify extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		
		return next(request, response);
	}
	
}
