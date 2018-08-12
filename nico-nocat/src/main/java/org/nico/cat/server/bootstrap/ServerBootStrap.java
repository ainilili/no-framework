package org.nico.cat.server.bootstrap;

import java.io.IOException;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.Server;
import org.nico.cat.server.bootstrap.loader.ServerLoaderProcess;
import org.nico.cat.server.bootstrap.loader.impl.LoaderApi;
import org.nico.cat.server.bootstrap.loader.impl.LoaderConfig;
import org.nico.cat.server.bootstrap.loader.impl.LoaderFilter;
import org.nico.cat.server.bootstrap.loader.impl.LoaderListener;
import org.nico.cat.server.bootstrap.loader.impl.LoaderParameter;
import org.nico.cat.server.bootstrap.loader.impl.LoaderWelcome;
import org.nico.cat.server.container.Container;
import org.nico.fig.center.ConfigCenter;
import org.nico.fig.loader.ConfigLoader;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.resource.ResourceUtils;
import org.nico.util.stream.StreamUtils;
import org.nico.util.string.StringUtils;

/** 
 * The bootStrap of the server 
 * <p>
 * @author nico
 * @version createTime：2018年1月7日 上午12:09:36
 */
public class ServerBootStrap {
	
	private static Logging logging = LoggingHelper.getLogging(ServerBootStrap.class);
	
	private String env;
	
	public void start(String env){
		start(-1, env);
	}
	
	public void start(){
		start(-1, null);
	}
	
	public void start(int port){
		start(port, null);
	}
	
	public void start(int port, String env){
		/**
		 * Start Server with port
		 */
		long start = System.nanoTime();
		init(env);
		logging.info("Starting the service takes " + (System.nanoTime() - start)/1000000 + " milliseconds");
		if(port > 0) {
			new Server(port).start();
		}else {
			new Server().start();
		}
	}
	
	public void init(String env) {
		try{
			logging.info("Server bootStrap be launch on localhost ");
			/**
			 * Reader configuration by SEEKER
			 */
			ConfigLoader.loader(env);
			
			
			/**
			 * Loader XML configuration from the path defined
			 */
			ServerLoaderProcess processer = new ServerLoaderProcess(ConfigCenter.getInstance().getNocat());
			processer.processLoader(new LoaderConfig());
			processer.processLoader(new LoaderWelcome());
			processer.processLoader(new LoaderApi());
			processer.processLoader(new LoaderFilter());
			processer.processLoader(new LoaderParameter());
			processer.processLoader(new LoaderListener());
			
			/**
			 * Init Container (init listener)
			 */
			Container.getInstance().init();
			
		}catch(IOException e){
			logging.error("Not found cat.xml in this classpath.");
			logging.error(e);
		}catch(Exception e){
			logging.error(e);
		}
	}
	
	
	
}
