package org.nico.cat.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 
 * 
 * @author nico
 * @version createTime：2018年5月16日 下午10:29:05
 */

public class ServerConfig {
	
	/**
	 * Normal
	 */
	protected int coresizeNormal = 100;
	
	/**
	 * Max 
	 */
	protected int coresizeMax = 500;
	
	/**
	 * Keep Alive(s)
	 */
	protected int keepaliveTime = 10;  
	
	/**
	 * Thread pools
	 */
	protected ThreadPoolExecutor  serverThreadPool =  new ThreadPoolExecutor(
			
				coresizeNormal,
				
				coresizeMax,
				
				keepaliveTime,
				
				TimeUnit.SECONDS,
				
				new LinkedBlockingQueue<Runnable>()
	){
		{
			allowCoreThreadTimeOut(false);
		}
	}; 
	
	public ThreadPoolExecutor getPools(){
		return serverThreadPool;
	}
}
