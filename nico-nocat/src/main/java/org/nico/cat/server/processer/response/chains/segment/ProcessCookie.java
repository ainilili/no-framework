package org.nico.cat.server.processer.response.chains.segment;

import java.util.Base64;
import java.util.Date;
import java.util.Map.Entry;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.processer.response.chains.AbstractResponseProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Cookie;
import org.nico.cat.server.response.Response;
import org.nico.cat.server.util.CookieUtils;

/**
 * Process uri filter
 * @author nico
 * @date 2018年1月11日
 */
public class ProcessCookie extends AbstractResponseProcess{

	@Override
	public Response process(Request request, Response response) throws Exception {
		if(! response.getCookies().isEmpty()){
			for(Cookie currentCookie: response.getCookies()){
				String assemblyCookie = CookieUtils.assemblyCookie(currentCookie);
				response.getHeaders().add("Set-Cookie", assemblyCookie);
			}
		}
		return next(request, response);
	}

}
