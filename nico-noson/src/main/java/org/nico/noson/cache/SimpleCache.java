package org.nico.noson.cache;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleCache implements Cache{

	private Map<Object, Object> mapCache = new ConcurrentHashMap<Object, Object>(100, 0.8f);

//	private NosonTimer cacheTimer = new NosonTimer();

	private long delay;

	public SimpleCache(long delay) {
		this.delay = delay;
	}

	public SimpleCache() {}

	@Override
	public Object getCache(Object key) {
		return mapCache.get(key);
	}

	@Override
	public Object putCache(Object key, Object value){
		return putCache(key, value, this.delay);
	}

	@Override
	public Object putCache(Object key, Object value, long delay){
		if(value == null) return null;
		if(delay > 0){
//			cacheTimer.addCacheTask(key, this, delay);
		}
		return mapCache.put(key, value);
	}

	@Override
	public boolean containsCache(Object key){
		return mapCache.containsKey(key);
	}

	@Override
	public Object removeCache(Object key){
		return mapCache.remove(key);
	}

	@Override
	public Set<Entry<Object, Object>> cacheSet(){
		return mapCache.entrySet();
	}

	@Override
	public void clearCache(){
//		cacheTimer.cancel();
		mapCache.clear();
	}
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	class NosonTimer {

		private Timer cacheTimer;

		public NosonTimer() {
			this.cacheTimer = new Timer();
		}

		public void addCacheTask(final Object key, final Cache cache, long delay){
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					synchronized (cache) {
						cache.removeCache(key);
					}
				}
			};
			cacheTimer.schedule(task, delay);
		}

		public void cancel(){
			cacheTimer.cancel();
		}

	}

}
