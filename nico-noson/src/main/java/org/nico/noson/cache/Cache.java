package org.nico.noson.cache;

import java.util.Map.Entry;
import java.util.Set;

/**
 * Noson internal cache.
 * 
 * @author nico
 */
public interface Cache {

	public Object getCache(Object key);
	
	public Object putCache(Object key, Object value);
	
	public Object putCache(Object key, Object value, long delay);
	
	public boolean containsCache(Object key);
	
	public Object removeCache(Object key);
	
	public Set<Entry<Object, Object>> cacheSet();
	
	public void clearCache();
	
}
