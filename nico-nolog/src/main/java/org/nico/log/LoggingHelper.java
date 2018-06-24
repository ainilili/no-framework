package org.nico.log;

import org.nico.log.extra.ApacheLog;
import org.nico.log.extra.Log4j;
import org.nico.log.extra.NoLog;

public class LoggingHelper {

	
	public static Logging getLogging(Class<?> clazz){
		Logging logging = null;
		switch(LoggingConfig.CONFIGURATE_TYPE){
		case NOLOG:
			logging = new NoLog(clazz);
			break;
		case LOG4J:
			logging = new Log4j(clazz);
			break;
		case APACHELOG:
			logging = new ApacheLog(clazz);
			break;
		default:logging = new NoLog(clazz);
		}
		return logging;
	}
	
	public static Logging getLogging(String sign){
		Logging logging = null;
		switch(LoggingConfig.CONFIGURATE_TYPE){
		case NOLOG:
			logging = new NoLog(sign);
			break;
		case LOG4J:
			logging = new Log4j(sign);
			break;
		case APACHELOG:
			logging = new ApacheLog(sign);
			break;
		default:logging = new NoLog(sign);
		}
		return logging;
	}
}
