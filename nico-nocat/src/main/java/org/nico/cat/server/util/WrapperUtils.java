package org.nico.cat.server.util;

import java.util.HashMap;
import java.util.Map;

import org.nico.cat.server.response.parameter.ContentType;

/** 
 * Wrapper utils
 * @author nico
 * @version createTime：2018年1月14日 下午1:18:37
 */
public class WrapperUtils {

	public static Map<String, String> specialContentType = new HashMap<String, String>(10);
	
	static {
		//font
		specialContentType.put("svg", wrapperContentType(ContentType.IMAGE, "svg+xml"));
		specialContentType.put("woff", wrapperContentType(ContentType.APPLICATION, "x-font-woff"));
		specialContentType.put("ttf", wrapperContentType(ContentType.APPLICATION, "x-font-ttf"));
		specialContentType.put("swf", wrapperContentType(ContentType.APPLICATION, "x-shockwave-flash"));
	}
	
	/**
	 * Wrapper content type
	 * @param type
	 * @param name
	 * @return
	 */
	public static String wrapperContentType(ContentType type, String name){
		String contentType = null;
		if((contentType = specialContentType.get(name)) != null) {
			return contentType;
		}
		return type.getContentType() + name;
	}
	
}
