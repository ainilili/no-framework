package org.nico.cat.server.processer;

import java.io.IOException;
import java.net.Socket;

import org.nico.cat.server.processer.entity.ProcessResult;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.buddy.ResponseBuddy;
import org.nico.cat.server.response.parameter.HttpCode;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月4日 下午10:25:05
 */

public class ServerProcesser {

	private Socket client;

	public ServerProcesser(Socket client){
		this.client = client;
	}

	/**
	 * Process the client
	 * <p>
	 * then will call function <strong> {@link RequestProcesser} </strong> and <strong> {@link ResponseProcesser} </strong>
	 * to get request and response, in the end, assembly them into ProcesserResult
	 * @return {@link ProcessResult}
	 * @throws Exception 
	 * @throws IOException 
	 */
	public ProcessResult process() throws Throwable{
		ProcessResult result = new ProcessResult();
		result.setRequest(new RequestProcesser(client).processing());
		result.setResponse(new ResponseProcesser(result.getRequest()).processing());
		return result;
	}
}
