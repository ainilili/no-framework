package org.nico.cat.server.processer;


import org.nico.cat.server.container.Container;
import org.nico.cat.server.exception.runtime.ProcessException;
import org.nico.cat.server.processer.response.chains.ResponseProcessChains;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/** 
 * Process the request to get {@link Response}
 * @author nico
 * @version createTime：2018年1月4日 下午10:28:56
 */

public class ResponseProcesser {

	private Request request;

	public ResponseProcesser(Request request){
		this.request = request;
	}

	public Response processing(){
		Response response = new Response(request);
		try{
			Container.getInstance().setActivityResponse(response);
			ResponseProcessChains.process(request, response);
		}catch(Exception e){
			throw new ProcessException("An exception occurred while assembly the response：" + e.getMessage());
		}
		return response;
	}
}
