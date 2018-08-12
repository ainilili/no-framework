package org.nico.cat.server.processer.response.chains.segment;

import java.util.Base64;
import java.util.Date;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.Response;

/**
 * Process uri filter
 * @author nico
 * @date 2018年1月11日
 */
public class ProcessSession extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		
		if(container != null){
			if(request.getSession() != null){
				if(! request.getSession().isEmpty()){
					if(! request.getCookieMap().containsKey(ConfigKey.SESSIONID)){
						String sessionToken = new String(Base64.getEncoder().encode(String.valueOf(new Date().getTime()).getBytes()));
						container.putSession(sessionToken, request.getSession());
						response.getHeaders().add("Set-Cookie", ConfigKey.SESSIONID + "=" + sessionToken + ";path=/");
					}
				}
			}else{

			}
		}
		return next(request, response);
	}

}
