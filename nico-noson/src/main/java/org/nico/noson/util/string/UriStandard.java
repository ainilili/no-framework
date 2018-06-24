package org.nico.noson.util.string;

import java.util.HashMap;
import java.util.Map;

public class UriStandard {
	
	/**
	 * Standard uri head
	 * 
	 * @param uri uri
	 * @return value
	 */
	public static String standardAngle(String uri){
		return uri.startsWith("/") ? uri : "/" + uri;
	}
	
	/**
	 * Gets the second half of the uri except the header
	 * 
	 * @param uri uri
	 * @param head head
	 * @return value
	 */
	public static String segmentAngle(String uri, String head){
		head = standardAngle(head);
		uri = standardAngle(uri);
		if(uri.startsWith(head)){
			return uri.substring(uri.indexOf(head) + head.length());
		}else{
			return null;
		}
	}
	
	public static Object[] parseUri(String url){
		Object[] targets = new Object[2];
		int index1 = url.lastIndexOf("/");
		String temp = url.substring(index1);
		int index2 = temp.indexOf("?");
		int index = url.length();
		Map<String, String> params = new HashMap<String, String>();
		targets[1] = params;
		if(index2 != -1){ 
			index = index1 + index2;
			String parameterStr = url.substring(index + 1);
			String[] parameters = parameterStr.split("&");
			for(String param: parameters){
				String[] ps = param.split("=");
				if(ps.length == 2){
					params.put(ps[0], ps[1]);
				}
			}
		}
		targets[0] = url.substring(0, index);
		return targets;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameters(String url){
		return (Map<String, String>) parseUri(url)[1];
	} 
	
	public static String getUri(String url){
		return (String) parseUri(url)[0];
	} 
	
}
