package org.nico.cat.server.container.module;

import org.nico.cat.server.container.moudle.realize.entry.Listener;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月8日 下午10:24:32
 */
public class ListenerModule {
	
	private String payload;
	
	private Class<Listener> handler;
	
	public ListenerModule(String payload, Class<Listener> handler) {
		super();
		this.payload = payload;
		this.handler = handler;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Class<Listener> getHandler() {
		return handler;
	}

	public void setHandler(Class<Listener> handler) {
		this.handler = handler;
	}

	@Override
	public String toString() {
		return "Listener [payload=" + payload + ", handler=" + handler + "]";
	}
	
}
