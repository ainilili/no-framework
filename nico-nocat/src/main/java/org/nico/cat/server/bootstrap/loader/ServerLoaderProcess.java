package org.nico.cat.server.bootstrap.loader;

import org.nico.fig.center.ConfigCenter;
import org.nico.fig.center.bean.NocatBean;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/** 
 * Processing load
 * 
 * @author nico
 * @version createTime：2018年1月9日 下午9:13:14
 */
public class ServerLoaderProcess {

	private Logging logging = LoggingHelper.getLogging(ServerLoaderProcess.class);
	
	private NocatBean nocatCenter;
	
	public ServerLoaderProcess(NocatBean nocatCenter) {
		super();
		this.nocatCenter = nocatCenter;
	}

	/**
	 * Process loader
	 * @param loader
	 */
	public void processLoader(ServerLoader loader){
		logging.info("Loader module " + loader.getClass().getSimpleName());
		loader.loader(nocatCenter);
	}
	
}
