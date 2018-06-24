package org.nico.cat.server.container.module;

import org.nico.cat.server.container.moudle.realize.entry.Filter;
import org.nico.util.string.StringUtils;

/** 
 * Filter
 * @author nico
 * @version createTime：2018年1月8日 下午10:19:07
 */
public class FilterModule {
	
	private String uri;
	
	private Class<Filter> handler;
	
	private String payload;
	
	private boolean singleton;

	public FilterModule(String uri, Class<Filter> handler, String payload, boolean singleton) {
		super();
		this.uri = StringUtils.trim(uri);
		this.handler = handler;
		this.payload = payload;
		this.singleton = singleton;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Class<Filter> getHandler() {
		return handler;
	}

	public void setHandler(Class<Filter> handler) {
		this.handler = handler;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	@Override
	public String toString() {
		return "FilterModule [uri=" + uri + ", handler=" + handler + ", payload=" + payload + ", singleton=" + singleton
				+ "]";
	}

}
