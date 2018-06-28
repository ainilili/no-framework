package org.nico.cat.server.util;

import org.nico.cat.server.request.extra.Cookie;
import org.nico.util.string.StringUtils;

/** 
 * Cookie Utils
 * @author nico
 * @version createTime：2018年2月4日 下午1:10:53
 */

public class CookieUtils {

	/**
	 * Assembly Object {@link Cookie} Convert To String
	 * @param cookie
	 * @return
	 */
	public static String assemblyCookie(Cookie cookie){
		StringBuffer buf = new StringBuffer();
		if(cookie != null){
			if(StringUtils.isNotBlank(cookie.getName())){
				buf.append(cookie.getName() + "=");
				if(StringUtils.isNotBlank(cookie.getValue())){
					buf.append(cookie.getValue());
					if(StringUtils.isNotBlank(cookie.getDomain())){
						buf.append(";domain=" + cookie.getDomain());
					}
					if(StringUtils.isNotBlank(cookie.getPath())){
						buf.append(";path=" + cookie.getPath());
					}
					if(StringUtils.isNotBlank(cookie.getExpires())){
						buf.append(";expires=" + cookie.getExpires());
					}
					if(cookie.isHttpOnly()){
						buf.append(";HttpOnly");
					}
					if(cookie.isSecure()){
						buf.append(";secure");
					}
					if(cookie.getMaxAge() != null && cookie.getMaxAge() > -1) {
						buf.append(";max-age=" + cookie.getMaxAge());
					}
				}
			}
		}
		return buf.toString();
	}
}
