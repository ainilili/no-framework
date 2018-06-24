package org.nico.cat.server.bootstrap.loader;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.seeker.searcher.SeekerSearcher;

/** 
 * Processing load
 * 
 * @author nico
 * @version createTime：2018年1月9日 下午9:13:14
 */
public class ServerLoaderProcess {

	private Logging logging = LoggingHelper.getLogging(ServerLoaderProcess.class);
	
	private SeekerSearcher seacher;
	
	public ServerLoaderProcess(SeekerSearcher seacher){
		this.seacher = seacher;
	}
	
	/**
	 * Process loader
	 * @param loader
	 */
	public void processLoader(ServerLoader loader){
		logging.info("Loader module " + loader.getClass().getSimpleName());
		loader.loader(seacher);
		reset();
	}
	
	/**
	 * Reset seacher to source domBean
	 */
	public void reset(){
		seacher.reset();
	}
}
