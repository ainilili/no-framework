package org.nico.noson.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class NoLinkedMap<K, V> implements NoMap<K, V>{

	private Map<K, V> map = new LinkedHashMap<K, V>();
	
	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}

	@Override
	public boolean contains(K key) {
		return map.containsKey(key);
	}

	@Override
	public V get(K key) {
		return map.get(key);
	}

	@Override
	public void remove(K key) {
		map.remove(key);
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Set<Entry<K, V>> recordSet() {
		return map.entrySet();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

}
