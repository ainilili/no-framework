package org.nico.log;

import java.util.Properties;

import org.nico.log.define.LoggingLevel;
import org.nico.log.define.LoggingSwitch;
import org.nico.log.define.LoggingType;
import org.nico.log.extra.NoLog;
import org.nico.log.placeholder.PlaceholderConfig;
import org.nico.util.resource.ResourceUtils;

public class LoggingConfig extends PlaceholderConfig{
	
	protected static final String CONFIGURATE = "logno.properties";
	
	protected static final String CONFIGURATE_OUTFORMAT;
	
	protected static final String CONFIGURATE_LOGPATH;
	
	protected static final LoggingType CONFIGURATE_TYPE;
	
	protected static final String CONFIGURATE_LEVEL;
	
	protected static final String CONFIGURATE_LEVEL_DEBUG;
	
	protected static final String CONFIGURATE_LEVEL_INFO;
	
	protected static final String CONFIGURATE_LEVEL_HINT;
	
	protected static final String CONFIGURATE_LEVEL_WARNING;
	
	protected static final String CONFIGURATE_LEVEL_ERROR;
	
	protected static final LoggingSwitch CONFIGURATE_PRINT_STACK_TRACE_SWITCH;
	
	static{
		Properties logs = new Properties();
		try {
			logs.load(ResourceUtils.getClasspathResource(CONFIGURATE));
		} catch (Exception e) {
			System.err.println("NOLOG:[Warning]->" +e.getMessage());
		}
		CONFIGURATE_LOGPATH 	= logs.containsKey("nico.log.path") ? 
				
				(String) logs.get("nico.log.path") : "" ;
						
		CONFIGURATE_LEVEL 		= logs.containsKey("nico.log.level") ? 
				
				(String) logs.get("nico.log.level") : "INFO";
				
		CONFIGURATE_LEVEL_DEBUG	= logs.containsKey("nico.log.level.debug") ? 
				
				(String) logs.get("nico.log.level.debug") : LoggingLevel.DEBUG.getName();
				
		CONFIGURATE_LEVEL_INFO	= logs.containsKey("nico.log.level.info") 	? 
				
				(String) logs.get("nico.log.level.info") : LoggingLevel.INFO.getName();
				
		CONFIGURATE_LEVEL_HINT	= logs.containsKey("nico.log.level.hint") ? 
				
				(String) logs.get("nico.log.level.hint") : LoggingLevel.HINT.getName();
				
		CONFIGURATE_LEVEL_WARNING= logs.containsKey("nico.log.level.warning") ? 
				
				(String) logs.get("nico.log.level.warning") : LoggingLevel.WARNING.getName();
				
		CONFIGURATE_LEVEL_ERROR	= logs.containsKey("nico.log.level.error") ? 
				
				(String) logs.get("nico.log.level.error") : LoggingLevel.ERROR.getName();
				
		CONFIGURATE_OUTFORMAT 	= logs.containsKey("nico.log.format") ? 
				
				(String) logs.get("nico.log.format") : "${time} ${threadId} ${logType} ${class} => ${message}";
		
		CONFIGURATE_PRINT_STACK_TRACE_SWITCH = logs.containsKey("nico.log.print.stack.trace") ? 
				
				LoggingSwitch.valueOf(((String) logs.get("nico.log.print.stack.trace")).toUpperCase()) : LoggingSwitch.OFF;
				
		CONFIGURATE_TYPE = LoggingType.valueOf((logs.containsKey("nico.log.type") ? 
				
				(String)logs.get("nico.log.type") : LoggingType.NOLOG.toString()));
	}
	
	protected static String LINE_FEED = "\r\n";
	
	static{
		if(!System.getProperty("os.name").toLowerCase().startsWith("win")){  
			LINE_FEED = "\n";
		}  
	}
}
