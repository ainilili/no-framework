package org.nico.noson.entity;

import java.util.Map.Entry;
import java.util.Set;


public interface NoMap<K, V> {

	
	public V put(K key, V value);
	
	public boolean contains(K key);

	public V get(K key);
	
	public void remove(K key);
	
	public int size();
	
	public Set<Entry<K, V>> recordSet();
	
	public Set<K> keySet();
	
}
