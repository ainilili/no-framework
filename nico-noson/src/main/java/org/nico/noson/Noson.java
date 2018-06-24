package org.nico.noson;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.nico.noson.entity.NoType;
import org.nico.noson.scanner.buddy.JsonWrapper;

/** 
 * Json parse main class
 * 
 * @author nico
 * @version 2017-11-24 9:58:06
 */

public class Noson extends LinkedHashMap<String, Object> implements Map<String, Object> {

	private static final long serialVersionUID = 463826524664855432L;

	/**
	 * Parsing the Json string into a Noson data structure 
	 * (the Json string starts with '{')
	 * 
	 * @param json The Json string to be parsed
	 * @return {@link Noson}
	 */
	public static Noson parseNoson(String json){
		return JsonWrapper.convert(json, Noson.class);
	}
	
	/**
	 * Parsing the Json string into a Map data structure 
	 * (the Json string starts with '{')
	 * 
	 * @param json The Json string to be parsed
	 * @return {@link Map}
	 */
	public static Map<String, Object> parseMap(String json){
		return JsonWrapper.convert(json, Map.class);
	}

	/**
	 * Parsing the Json string into a List data structure 
	 * (the Json string starts with '['])
	 * 
	 * @param json The Json string to be parsed
	 * @return {@link List}
	 */
	public static List<Object> parseArray(String json){
		return JsonWrapper.convert(json, List.class);
	}

	/**
	 * Converts a Json string into a Class data structure
	 * 
	 * @param json The Json string to be converted
	 * @param clazz The data structure to be transformed
	 * @return Target transformation object
	 */
	public static <T> T convert(String json, Class<T> clazz){
		return JsonWrapper.convert(json, clazz);
	}

	/**
	 * Convert obj into a Class data structure
	 * 
	 * @param obj The object to be transformed
	 * @param clazz The data structure to be transformed
	 * @return Target transformation object
	 */
	public static <T> T convert(Object obj, Class<T> clazz){
		return JsonWrapper.convert(obj, clazz);
	}
	
	/**
	 * Converts noson into a Class data structure
	 * 
	 * @param noson The transformed noson
	 * @param clazz The data structure to be transformed
	 * @return Target transformation object
	 */
	public static <T> T convert(Noson noson, Class<T> clazz){
		return JsonWrapper.convert(noson, clazz);
	}

	/**
	 * Converts a List into a Class data structure
	 * 
	 * @param objs Converted List
	 * @param clazz The data structure to be transformed
	 * @return Target transformation object
	 */
	public static <T> T convert(List<Object> objs, Class<T> clazz){
		return JsonWrapper.convert(objs, clazz);
	}

	/**
	 * Converts the formulated object into a 'T' type data structure in NoType, 
	 * which enables Json to be transformed into a complex data structure</br>
	 * Example：<br>
	 * Map<String, User> map = Noson.convert(json, new NoType<<b>Map&lt;String, User&gt;</b>>(){});<br>
	 * The above code will convert json to Map<String, User> format
	 * 
	 * @param obj The object being transformed
	 * @param type Convert to the data structure corresponding to the generic type
	 * @return Target transformation object
	 */
	public static <T> T convert(Object obj, NoType<T> type){
		return JsonWrapper.convert(obj, type);
	}

	/**
	 * 将对象序列化成Json串
	 * 
	 * @param obj 要被序列化的对象
	 * @return 序列化后的字符串
	 */
	public static String reversal(Object obj){
		return JsonWrapper.reversal(obj);
	}

	/**
	 * Noson自身序列化成Json
	 */
	public String toString(){
		return reversal(this);
	}

}
