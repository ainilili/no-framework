package org.nico.cat.config;

import org.nico.util.placeholder.PlaceHolderHelper;
import org.nico.util.placeparser.PlaceParserHelper;

/** 
 * 模板配置
 * @author nico
 * @version createTime：2018年1月6日 下午9:52:35
 */
public class ConfigTemplate {
	
	/** 模板前后缀 **/
	public static final String TEMPLATE_PARSER_PREFIX = "${";
	public static final String TEMPLATE_PARSER_SUFFIX = "}";
	
	public static final PlaceParserHelper PLACE_PARSER = 
			new PlaceParserHelper(ConfigTemplate.TEMPLATE_PARSER_PREFIX, ConfigTemplate.TEMPLATE_PARSER_SUFFIX);
	
	public static final PlaceHolderHelper PLACE_HOLDER = 
			new PlaceHolderHelper(ConfigTemplate.TEMPLATE_PARSER_PREFIX, ConfigTemplate.TEMPLATE_PARSER_SUFFIX);
	
	/** Rquest请求模板 **/
	public static final String TEMPLATE_HTTP_HEADER;
	public static final String TEMPLATE_HTTP_HOST;
	public static final String TEMPLATE_HTTP_CONNECTION;
	public static final String TEMPLATE_HTTP_CONTENT_TYPE;
	public static final String TEMPLATE_HTTP_CONTENT_LENGTH;
	public static final String TEMPLATE_HTTP_CACHE_CONTROL;
	public static final String TEMPLATE_HTTP_USER_AGENT;
	public static final String TEMPLATE_HTTP_ACCEPT;
	public static final String TEMPLATE_HTTP_ACCEPT_ENCODING;
	public static final String TEMPLATE_HTTP_ACCEPT_LANGUAGE;
	public static final String TEMPLATE_HTTP_COOKIE;
	public static final String TEMPLATE_HTTP_BODY;
	
	public static final String TEMPLATE_RESPONSE_SYSTEM_BODY;
	
	/** Linux和W	indow换行符 **/
	public static final String LINE_FEED;
	
	static{
		LINE_FEED = !System.getProperty("os.name").toLowerCase().startsWith("win") ? "\n" : "\r\n"; 
//		TEMPLATE_PARSER_PREFIX = "${";
//		TEMPLATE_PARSER_SUFFIX = "}";
		TEMPLATE_HTTP_HEADER 			= "${method} ${uri} ${version}" 		+ LINE_FEED;
		TEMPLATE_HTTP_HOST 				= "Host: ${host}:${port}" 				+ LINE_FEED;
		TEMPLATE_HTTP_CONTENT_TYPE		= "Content-Type: ${contentType}"		+ LINE_FEED;
		TEMPLATE_HTTP_CONTENT_LENGTH	= "Content-Length: ${contentLength}"	+ LINE_FEED;
		TEMPLATE_HTTP_CONNECTION 		= "Connection: ${connection}" 			+ LINE_FEED;
		TEMPLATE_HTTP_CACHE_CONTROL 	= "Cache-Control: ${cacheControl}"	 	+ LINE_FEED;
		TEMPLATE_HTTP_USER_AGENT 		= "User-Agent: ${userAgent}" 			+ LINE_FEED;
		TEMPLATE_HTTP_ACCEPT 			= "Accept: ${accept}" 					+ LINE_FEED;
		TEMPLATE_HTTP_ACCEPT_ENCODING 	= "Accept-Encoding: ${acceptEncoding}" 	+ LINE_FEED;
		TEMPLATE_HTTP_ACCEPT_LANGUAGE 	= "Accept-Language: ${acceptLanguage}" 	+ LINE_FEED;
		TEMPLATE_HTTP_COOKIE			= "Cookie: ${cookies}"					+ LINE_FEED;
		TEMPLATE_HTTP_BODY 				= LINE_FEED + LINE_FEED + "${body}";
		
		
		TEMPLATE_RESPONSE_SYSTEM_BODY	= "<h1>${message}</h1>";
	}
}
