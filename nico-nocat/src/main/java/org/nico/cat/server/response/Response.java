package org.nico.cat.server.response;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nico.cat.server.request.Request;
import org.nico.cat.server.request.extra.Cookie;
import org.nico.cat.server.response.buddy.SimpleResponsePrinter;

/** 
 * Response to the client like browser
 * The specific actions of the response depend on the implementation of the parent class {@link SimpleResponsePrinter}.
 * 
 * @author nico
 * @version createTime：2018年1月4日 下午10:44:38
 */
public class Response extends SimpleResponsePrinter{

	private List<Cookie> cookies;
	
	public Cookie addCookie(Cookie cookie){
		cookies.add(cookie);
		return cookie;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	public Response(Request request){
		super(request);
		this.cookies = new ArrayList<Cookie>();
	}
	
}
