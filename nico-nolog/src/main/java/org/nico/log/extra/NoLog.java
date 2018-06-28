package org.nico.log.extra;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.nico.log.Logging;
import org.nico.log.LoggingConfig;
import org.nico.log.define.LoggingLevel;
import org.nico.util.date.DateUtils;
import org.nico.util.stream.FileUtils;
import org.nico.util.string.line.LineUtils;

public class NoLog extends LoggingConfig implements Logging{

	/** Bound Class **/
	private Class<?> clazz;
	
	/** Bound Sign **/
	private String sign;
	
	/** Temporary reserved field properties **/
	private Map<String, Object> contains = new ConcurrentHashMap<String, Object>();
	
	public NoLog(Class<?> clazz){
		this.clazz = clazz;
	}
	
	public NoLog(String sign){
		this.sign = sign;
	}
	
	/**
	 * print message<p>
	 * 
	 * @param o	</br>print object
	 * @param outType 
	 * 	</br>1:system.out.print
	 * 	</br>2:system.err.print
	 */
	private void print(Object o, int outType) {
		String message = null;
		synchronized (contains) {
			
			contains.put(		"time", 					DateUtils.getDateFormat());
			
			contains.put(		"threadId", 				Thread.currentThread().getName() + "-" + Thread.currentThread().getId());
			
			contains.put(		"class", 					clazz == null ? sign : clazz.getName());
			
			contains.put(		"className", 				clazz == null ? sign : clazz.getSimpleName());
			
			contains.put(		"message", 					String.valueOf(o));
			
			contains.put(		"line", 					LineUtils.getLineInfo());
			
			message = placeHolder.replacePlaceholders(CONFIGURATE_OUTFORMAT, contains) + LINE_FEED;
		}
		switch(outType){
		case 1:	System.out.print(message); break;
		case 2: System.err.print(message); break;
		default: System.out.print(message); break;
		}
		if(o instanceof Exception){
			try {
				if(CONFIGURATE_PRINT_STACK_TRACE_SWITCH.on()){
					((Exception)o).printStackTrace();
					((Exception)o).printStackTrace(new PrintWriter(CONFIGURATE_LOGPATH));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			FileUtils.writerAfterBytes(message.getBytes(), CONFIGURATE_LOGPATH);
		}else{
			FileUtils.writerAfterBytes(message.getBytes(), CONFIGURATE_LOGPATH);
		}
	}
	
	/**
	 * print message by outType = 1
	 * 
	 * @param o	
	 * 			<br>print object
	 * @param outType 
	 * <br>
	 * <b>default:</b>	system.out.print
	 */
	private void print(Object o) {
		print(o, 1);
	}


	@Override
	public void warning(Object o) {
		if(compareLevel(LoggingLevel.valueOf(CONFIGURATE_LEVEL), LoggingLevel.WARNING)){
			contains.put("logType", CONFIGURATE_LEVEL_WARNING);
			print(o, 1);
		}
	}

	@Override
	public void info(Object o) {
		if(compareLevel(LoggingLevel.valueOf(CONFIGURATE_LEVEL), LoggingLevel.INFO)){
			contains.put("logType", CONFIGURATE_LEVEL_INFO);
			print(o, 1);
		}
	}

	@Override
	public void error(Object o) {
		if(compareLevel(LoggingLevel.valueOf(CONFIGURATE_LEVEL), LoggingLevel.ERROR)){
			contains.put("logType", CONFIGURATE_LEVEL_ERROR);
			print(o, 2);
		}
	}

	@Override
	public void hint(Object o) {
		if(compareLevel(LoggingLevel.valueOf(CONFIGURATE_LEVEL), LoggingLevel.HINT)){
			contains.put("logType", CONFIGURATE_LEVEL_HINT);
			print(o, 1);
		}
	}

	@Override
	public void debug(Object o) {
		if(compareLevel(LoggingLevel.valueOf(CONFIGURATE_LEVEL), LoggingLevel.DEBUG)){
			contains.put("logType", CONFIGURATE_LEVEL_DEBUG);
			print(o, 1);
		}
	}
	
	/**
	 * Compare LoggingLevel by level
	 * @param cfg	CONFIGURARTE level
	 * @param current	current level
	 * @return
	 */
	private boolean compareLevel(LoggingLevel cfg, LoggingLevel current){
		return cfg.getLevel() >= current.getLevel();
	}
}
