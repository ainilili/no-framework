package org.nico.cat.server.util;

import java.util.HashMap;
import java.util.Map;

import org.nico.util.string.StringUtils;


/** 
 * Uri utils
 * @author nico
 * @version createTime：2018年1月14日 下午1:19:11
 */

public class UriUtils {

	/**
	 * Get suffix from uri
	 * @param uri
	 * @return
	 */
	public static String getSuffix(String uri){
		if(StringUtils.isNotBlank(uri)){
			if(uri.indexOf(".") != -1){
				String[] segments = uri.split("[.]");
				return segments[segments.length - 1];	
			}
		}
		return "html";
	}

	/**
	 * Parse the uri from untreatedUri.
	 * @param untreatedUri untreatedUri
	 * @return uri
	 */
	public static String getUri(String untreatedUri){
		if(untreatedUri != null){
			int index = -1;
			if((index = untreatedUri.indexOf("?")) != -1){
				return untreatedUri.substring(0, index);
			}
		}
		return untreatedUri;
	}

	/**
	 * Parse the properties from untreatedUri.
	 * 
	 * @param untreatedUri untreatedUri
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getProperties(String untreatedUri){
		if(untreatedUri != null){
			int index = -1;
			if((index = untreatedUri.indexOf("?")) != -1) {
				untreatedUri = untreatedUri.substring(index + 1);
			}
			if(untreatedUri.length() > 0){
				String propertyPart = untreatedUri;
				if(propertyPart != null){
					String[] properties = propertyPart.split("[&]");
					Map<String, Object> propertyMap = new HashMap<String, Object>();
					for(String property: properties){
						String[] key_value = property.split("[=]", 2);
						if(key_value.length == 2){
							propertyMap.put(key_value[0], key_value[1]);
						}
					}
					return propertyMap;
				}
			}
		}
		return null;
	}
	
	/**
	 * Tidy uri format
	 * <br>"/" -> "/"
	 * <br>"get" -> "/get"
	 * <br>"get/" -> "/get"
	 * <br>"/get/" 	-> "/get"
	 * 
	 * @param uri uri
	 * @return after tidy uri
	 */
	public static String tidyUri(String uri){
		if(StringUtils.isNotBlank(uri)){
			if(! uri.startsWith("/")){
				uri = "/" + uri;
			}
			if(uri.endsWith("/")){
				uri = uri.substring(0, uri.length() - 1);
			}
		}else{
			return "";
		}
		return uri;
	}
}
