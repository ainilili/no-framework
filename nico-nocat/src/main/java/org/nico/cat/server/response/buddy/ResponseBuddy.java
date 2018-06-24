package org.nico.cat.server.response.buddy;

import java.io.IOException;
import java.net.Socket;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;

/**
 * Response buddy
 * 
 * @author nico
 * @date 2018年4月12日
 */
public class ResponseBuddy extends ResponseAssembly{

	private Socket client;
	
	public ResponseBuddy(Socket client) {
		this.client = client;
	}

	public void push(Response response){
		
		HttpCode httpCode = response.getHttpcode();
		Object responseBody = response.getResponseBody();
		
		if(httpCode == null){
			httpCode = HttpCode.HS404;
			response.setHttpcode(httpCode);
		}
		
		if(responseBody == null){
			responseBody = ResponseProcessKit.processHttpCode(httpCode);
			response.setResponseBody(responseBody);
		}
		
		if(responseBody instanceof Throwable){
			httpCode = HttpCode.HS500;
			response.setHttpcode(httpCode);
		}
			
		if(httpCode == HttpCode.HS500){
			SimpleFailurePush(response, (Throwable) responseBody);
		}else{
			SimpleSuccessPush(response);
		}
	}

	public void SimpleSuccessPush(Response response){
		try {
			
			verifyHeaders(response);
			assembleHeaders(response);
			assembleHeadersBlank(response);
			assembleBody(response);
			
			response.getStream().flush();
			response.getStream().close();
		} catch (IOException e) {
			SimpleFailurePush(response, e);
		}
	}

	public void SimpleFailurePush(Response response, Throwable e){
		response.getHeaders().putLast("Content-Type", "application/json;charset=" + ConfigKey.server_charset);
		verifyHeaders(response);
		assembleHeaders(response);
		assembleHeadersBlank(response);
		e.printStackTrace(response.getStream());
		response.getStream().close();
	}
	
	public void SimpleFailurePush(Throwable e){
		Response response = new Response(new Request().setClient(client));
		response.getHeaders().putLast("Content-Type", "application/json;charset=" + ConfigKey.server_charset);
		response.setHttpcode(HttpCode.HS500);
		SimpleFailurePush(response, e);
	}

}
