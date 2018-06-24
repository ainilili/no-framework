package org.nico.noson.cache;

/** 
 * Local Cache Manager
 * 
 * @author nico
 */

public class CacheManager {

	private static ThreadLocal<Cache> localKeyCaches = new ThreadLocal<Cache>();
	
	private static ThreadLocal<Cache> localValueCaches = new ThreadLocal<Cache>();
	
	private static ThreadLocal<Cache> localClassProxyCaches = new ThreadLocal<Cache>();
	
	private static ThreadLocal<Cache> localFieldTypeCaches = new ThreadLocal<Cache>();
	
	private static ThreadLocal<Cache> localFieldCaches = new ThreadLocal<Cache>();
	
	public static Cache getKeyCache(){
		Cache cache = localKeyCaches.get();
		if(cache == null){
			cache = new SimpleCache();
			localKeyCaches.set(new SimpleCache());
		}
		return cache;
	}
	
	public static Cache getValueCache(){
		Cache cache = localValueCaches.get();
		if(cache == null){
			cache = new SimpleCache();
			localValueCaches.set(new SimpleCache());
		}
		return cache;
	}
	
	public static Cache getClassProxyCache(){
		Cache cache = localClassProxyCaches.get();
		if(cache == null){
			cache = new SimpleCache();
			localClassProxyCaches.set(new SimpleCache());
		}
		return cache;
	}
	
	public static Cache getFieldTypeCache(){
		Cache cache = localFieldTypeCaches.get();
		if(cache == null){
			cache = new SimpleCache();
			localFieldTypeCaches.set(new SimpleCache());
		}
		return cache;
	}
	
	public static Cache getFieldCache(){
		Cache cache = localFieldCaches.get();
		if(cache == null){
			cache = new SimpleCache();
			localFieldCaches.set(new SimpleCache());
		}
		return cache;
	}
	
	public static void clearLocalCache(){
		Cache cache = localKeyCaches.get();
		if(cache != null){
			cache.clearCache();
		}
		cache = localValueCaches.get();
		if(cache != null){
			cache.clearCache();
		}
		cache = localClassProxyCaches.get();
		if(cache != null){
			cache.clearCache();
		}
		cache = localFieldTypeCaches.get();
		if(cache != null){
			cache.clearCache();
		}
		cache = localFieldCaches.get();
		if(cache != null){
			cache.clearCache();
		}
	}
	
}
