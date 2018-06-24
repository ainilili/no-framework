package org.nico.log;

/**
 * Logging interface
 * @author nico
 * @date 2017年12月19日
 */
public interface Logging {
	
	public void debug(Object o);
	
	public void info(Object o);
	
	public void hint(Object o);
	
	public void warning(Object o);
	
	public void error(Object o);
	
}
