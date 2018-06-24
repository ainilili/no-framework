package org.nico.cat.server.processer.request.chains.segment;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.container.Container;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Cookie;
import org.nico.cat.server.request.extra.Session;
import org.nico.util.string.StringUtils;

/**
 * Process packet header
 * @author nico
 * @date 2018年01月05号
 */
public class ProcessPacketCookie extends AbstractRequestProcess{

	@Override
	public Request process(String packet, Request request) throws Exception {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		if(request.getHeaders().containsKey("Cookie")){
			String cookiePending = request.getHeader("Cookie");
			if(StringUtils.isNotBlank(cookiePending)){
				String[] cookies = cookiePending.split("[;]");
				for(String cookie: cookies){
					String[] kv = cookie.trim().split("[=]", 2);
					cookieMap.put(kv[0], new Cookie(kv[0], kv[1]));
				}
			}
		}
		request.setCookieMap(cookieMap);
		String sessionId = null;
		if(cookieMap.containsKey(ConfigKey.SESSIONID)){
			sessionId = cookieMap.get(ConfigKey.SESSIONID).getValue();
			if(Container.getInstance().containsSession(sessionId)){
				request.setSession(Container.getInstance().getSession(sessionId));
			}else{
				Container.getInstance().putSession(sessionId, request.getSession());
			}
		}
		return next(packet, request);
	}

}
