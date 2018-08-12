package org.nico.cat.server.container.moudle.realize.entry;

import java.util.Map;

import org.nico.cat.server.container.module.ListenerModule;

/**
 * Listener
 * @author nico
 * @date 2018年1月11日
 */
public interface Listener {

	/**
	 * Initialize the listener.
	 * @param properties The loading parameters
	 * @param listenerModule Monitor module
	 */
	public void init(Map<String, Object> properties, ListenerModule listenerModule) throws Exception;
}
