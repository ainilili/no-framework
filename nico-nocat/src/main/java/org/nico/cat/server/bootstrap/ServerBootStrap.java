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
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.seeker.scan.impl.NicoScanner;
import org.nico.seeker.searcher.impl.NicoSearcher;
import org.nico.util.resource.ResourceUtils;
import org.nico.util.stream.StreamUtils;

/** 
 * The bootStrap of the server 
 * <p>
 * @author nico
 * @version createTime：2018年1月7日 上午12:09:36
 */
public class ServerBootStrap {
	
	private static Logging logging = LoggingHelper.getLogging(ServerBootStrap.class);
	
	public void start(){
		start(-1);
	}
	
	public void start(int port){
		/**
		 * Start Server with port
		 */
		long start = System.nanoTime();
		init();
		logging.info("Starting the service takes " + (System.nanoTime() - start)/1000000 + " milliseconds");
		if(port > 0) {
			new Server(port).start();
		}else {
			new Server().start();
		}
	}
	
	public void init() {
		try{
			logging.info("123Server bootStrap be launch on localhost ");
			/**
			 * Reader configuration by SEEKER
			 */
			String dom = StreamUtils.readStream2Str(ResourceUtils.getClasspathResource(ConfigKey.CONFIGURATION));
			
			/**
			 * Loader XML configuration from the path defined
			 */
			ServerLoaderProcess processer = new ServerLoaderProcess(new NicoSearcher(new NicoScanner(dom)));
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
