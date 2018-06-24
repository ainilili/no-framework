package org.nico.cat.server.container.module;

import org.nico.cat.server.container.moudle.realize.entry.Api;
import org.nico.util.string.StringUtils;

/** 
 * Api entity
 * @author nico
 * @version createTime：2018年1月8日 下午10:22:41
 */
public class ApiModule{
	
	private String uri;
	
	private Class<Api> handler;
	
	private String payload;
	
	private boolean singleton;

	public ApiModule(String payload, String uri, Class<Api> handler, boolean singleton) {
		super();
		this.uri = uri;
		this.handler = handler;
		this.payload = payload;
		this.singleton = singleton;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Class<Api> getHandler() {
		return handler;
	}

	public void setHandler(Class<Api> handler) {
		this.handler = handler;
	}

	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	@Override
	public String toString() {
		return "ApiModule [uri=" + uri + ", handler=" + handler + ", payload=" + payload + ", singleton=" + singleton
				+ "]";
	}

}
