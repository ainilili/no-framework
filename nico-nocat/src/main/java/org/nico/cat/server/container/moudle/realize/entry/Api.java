package org.nico.cat.server.container.moudle.realize.entry;

import org.nico.cat.server.container.module.ApiModule;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Response after processing the request.
 * 
 * @author nico
 * @date 2018年1月12日
 */
public abstract class Api {

	/**
	 * Api module
	 */
	private ApiModule apiModule;
	
	/**
	 * Process the request and respond.
	 * 
	 * @param request {@link Request}
	 * @param response {@link Response}
	 */
	public abstract void doService(Request request, Response response) throws Exception;
	
	/**
	 * Get api module
	 * 
	 * @param module api module
	 */
	public ApiModule getModule(){
		return apiModule;
	};
	
	/**
	 * Set api module
	 * 
	 */
	public void setModule(ApiModule apiModule){
		this.apiModule = apiModule;
	}
}
