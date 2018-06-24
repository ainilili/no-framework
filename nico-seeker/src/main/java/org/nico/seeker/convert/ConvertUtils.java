package org.nico.seeker.convert;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年9月2日 上午11:30:57
 */

public class ConvertUtils {
	
	/**
	 * map转为url请求参数
	 * @param map
	 * @return
	 */
	public static String map2UrlParams(Map<String, Object> map){
		StringBuffer cache = new StringBuffer("?");
		if(map != null){
			for(Entry<String, Object> entry: map.entrySet()){
				cache.append(entry.getKey());
				cache.append("=");
				cache.append(entry.getValue().toString());
				cache.append("&");
			}
		} 
		cache.delete(cache.length() - 1, cache.length());
		return cache.toString();
	}
	
}
