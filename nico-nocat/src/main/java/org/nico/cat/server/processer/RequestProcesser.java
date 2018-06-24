package org.nico.cat.server.processer;

import java.io.IOException;
import java.net.Socket;

import org.nico.cat.server.container.Container;
import org.nico.cat.server.exception.runtime.ProcessException;
import org.nico.cat.server.processer.request.chains.RequestProcessChains;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.util.InputStreamUtils;

/** 
 * Process the client to get {@link Request}
 * @author nico
 * @version createTime：2018年1月4日 下午10:28:38
 */

public class RequestProcesser {

	private Socket client;

	public RequestProcesser(Socket client){
		this.client = client;
	}

	/**
	 * Process socket(client) to full the request
	 * @return {@link Request}
	 * @throws IOException 
	 */
	public Request processing() throws Exception{
		Request request = new Request(client);
		try{
			Container.getInstance().setActivityRequest(request);
			String header = InputStreamUtils.getHeader(request.getByteBuffer());
			RequestProcessChains.process(header, request);
		}catch(Exception e){
			throw new ProcessException("An exception occurred while processing the request：" + e.getMessage(), e);
		}
		return request;
	}

}
