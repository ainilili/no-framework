package org.nico.log.define;

/**
 * Logging level
 * level rule: 
 * debug
 * @author nico
 * @date 2017年12月19日
 */
public enum LoggingLevel {
	
	DEBUG(0, "[DBUG]"),
	
	INFO(-1, "[INFO]"),
	
	HINT(-2, "[HINT]"),
	
	WARNING(-3, "[WARNG]"),
	
	ERROR(-4, "[EROR]");
	
	private int level;
	
	private String name;

	private LoggingLevel(int level, String name) {
		this.level = level;
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

}
