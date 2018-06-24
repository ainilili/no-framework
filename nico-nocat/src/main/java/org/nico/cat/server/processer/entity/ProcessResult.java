package org.nico.cat.server.processer.entity;


import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月4日 下午10:45:59
 */

public class ProcessResult {

	private Response response;
	
	private Request request;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
	
}
