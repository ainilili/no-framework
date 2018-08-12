package org.nico.fig.center.bean;

import java.util.List;
import java.util.Map;

public class NocatBean {

	private int port = 8080;
	
	private String resourcePath = "/static";
	
	private String charset = "utf-8";
	
	private int reviceBufferSize = 20971520;
	
	private int timeout = 0;
	
	private List<String> welcomes;
	
	private List<ListenerBean> listeners;
	
	private List<FilterBean> filters;
	
	private List<ApiBean> apis;
	
	private Map<String, Object> properties; 
	
	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public List<ApiBean> getApis() {
		return apis;
	}

	public void setApis(List<ApiBean> apis) {
		this.apis = apis;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public int getReviceBufferSize() {
		return reviceBufferSize;
	}

	public void setReviceBufferSize(int reviceBufferSize) {
		this.reviceBufferSize = reviceBufferSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public List<String> getWelcomes() {
		return welcomes;
	}

	public void setWelcomes(List<String> welcomes) {
		this.welcomes = welcomes;
	}

	public List<ListenerBean> getListeners() {
		return listeners;
	}

	public void setListeners(List<ListenerBean> listeners) {
		this.listeners = listeners;
	}

	public List<FilterBean> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterBean> filters) {
		this.filters = filters;
	}

	public static class ListenerBean{
		
		private String handler;
		
		private String payload;

		public String getHandler() {
			return handler;
		}

		public void setHandler(String handler) {
			this.handler = handler;
		}

		public String getPayload() {
			return payload;
		}

		public void setPayload(String payload) {
			this.payload = payload;
		}
		
	}
	
	public static class FilterBean{
		
		private String uri;
		
		private String handler;
		
		private String payload;
		
		private boolean isSingle;
		
		public String getPayload() {
			return payload;
		}

		public void setPayload(String payload) {
			this.payload = payload;
		}

		public boolean isSingle() {
			return isSingle;
		}

		public void setSingle(boolean isSingle) {
			this.isSingle = isSingle;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getHandler() {
			return handler;
		}

		public void setHandler(String handler) {
			this.handler = handler;
		}
		
	}
	
	public static class ApiBean{
		
		private String uri;
		
		private String payload;
		
		private String handler;
		
		private boolean isSingle;
		
		public boolean isSingle() {
			return isSingle;
		}

		public void setSingle(boolean isSingle) {
			this.isSingle = isSingle;
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

		public String getHandler() {
			return handler;
		}

		public void setHandler(String handler) {
			this.handler = handler;
		}
		
	}
}
