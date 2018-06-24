package org.nico.noson.scanner.buddy;

import org.nico.noson.NosonConfig;
import org.nico.noson.adapter.JsonParseAdapter;
import org.nico.noson.cache.CacheManager;
import org.nico.noson.entity.NoType;
import org.nico.noson.exception.NosonException;
import org.nico.noson.handler.reversal.ReversalHandlerQueue;
import org.nico.noson.handler.reversal.impl.ReversalArrayHandler;
import org.nico.noson.handler.reversal.impl.ReversalListHandler;
import org.nico.noson.handler.reversal.impl.ReversalMapHandler;
import org.nico.noson.handler.reversal.impl.ReversalNosonHandler;
import org.nico.noson.handler.reversal.impl.ReversalObjectHandler;
import org.nico.noson.handler.reversal.impl.ReversalVerityHandler;


public class JsonWrapper{

	private static ReversalHandlerQueue handleQueue;

	static{
		{
			handleQueue = new ReversalHandlerQueue();
			handleQueue.add(new ReversalVerityHandler());
			handleQueue.add(new ReversalNosonHandler());
			handleQueue.add(new ReversalListHandler());
			handleQueue.add(new ReversalArrayHandler());
			handleQueue.add(new ReversalMapHandler());
			handleQueue.add(new ReversalObjectHandler());
		}
	}

	public static <T> T convert(Object obj, Class<T> clazz){
		try {
			if(obj instanceof String){
				return NosonConfig.DEFAULT_SCANNER.scan(JsonParseAdapter.adapter((String) obj), clazz);
			}else{
				return NosonConfig.DEFAULT_SCANNER.scan(reversal(obj), clazz);
			}
		} catch (NosonException e) {
			throw new RuntimeException(e);
		}finally{
			clear();
		}
	}

	public static <T> T convert(Object obj, NoType<T> type){
		try {
			return (T) NosonConfig.DEFAULT_SCANNER.scan(obj instanceof String? JsonParseAdapter.adapter((String) obj) : reversal(obj), type.getClass());
		} catch (NosonException e) {
			throw new RuntimeException(e);
		}finally{
			clear();
		}
	}

	private static void clear(){
		CacheManager.clearLocalCache();
	}

	public static String reversal(Object obj){
		try{
			return handleQueue.handle(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			clear();
		}
	}
	
}
