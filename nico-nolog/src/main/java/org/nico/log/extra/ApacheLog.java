package org.nico.log.extra;


import org.apache.log4j.Logger;
import org.nico.log.Logging;



public class ApacheLog implements Logging{

	private Logger logger;
	
	public ApacheLog(Class<?> clazz){
		this.logger = Logger.getLogger(clazz);
	}
	
	public ApacheLog(String sign){
		this.logger = Logger.getLogger(sign);
	}

	@Override
	public void debug(Object o) {
		logger.debug(o);
	}

	@Override
	public void info(Object o) {
		logger.info(o);
	}

	@Override
	public void hint(Object o) {
		logger.info(o);
	}

	@Override
	public void warning(Object o) {
		logger.warn(o);
	}

	@Override
	public void error(Object o) {
		logger.error(o);
	}

	
}
