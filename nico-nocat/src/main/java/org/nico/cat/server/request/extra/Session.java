package org.nico.cat.server.request.extra;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Session
 * @author nico
 * @date 2018年2月2日
 */
public class Session implements Map<String, Object>{

	private Map<String, Object> storeMap;
	
	public Session(){
		this.storeMap = new HashMap<String, Object>();
	}
	
	@Override
	public void clear() {
		storeMap.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return storeMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return storeMap.containsValue(value);
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return storeMap.entrySet();
	}
	
	@Override
	public Object put(String key, Object value) {
		return storeMap.put(key, value);
	}

	@Override
	public Object get(Object key) {
		return storeMap.get(key);
	}

	@Override
	public boolean isEmpty() {
		return storeMap.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return storeMap.keySet();
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		storeMap.putAll(m);
	}

	@Override
	public Object remove(Object key) {
		return storeMap.remove(key);
	}

	@Override
	public int size() {
		return storeMap.size();
	}

	@Override
	public Collection<Object> values() {
		return storeMap.values();
	}

	@Override
	public String toString() {
		return "Session [storeMap=" + storeMap + "]";
	}

}
