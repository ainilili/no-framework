package org.nico.cat.server.request.buddy;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.cat.server.request.parameter.RequestMethod;
import org.nico.cat.server.util.UriUtils;

/** 
 * Request content
 * @author nico
 * @version createTime：2018年1月7日 下午7:08:12
 */

public class RequestContent {

	/**
	 * Request uri
	 */
	private String uri;

	/**
	 * Request method
	 */
	private RequestMethod method;

	/**
	 * Request version
	 */
	private String version;
	
	/**
	 * SessionId
	 */
	private String sessionId;

	/**
	 * Request host
	 */
	private String host;

	/**
	 * Request prot
	 */
	private String port;

	/**
	 * Request body
	 */
	private String body;

	/**
	 * Uri Redirect
	 */
	private String uriRedirect;

	/**
	 * Api Redirect
	 */
	private String apiRedirect;
	
	/**
	 * Is form data
	 */
	private boolean isFormData;

	/**
	 * Request url properties
	 */
	private Map<String, Object> properties;
	
	/**
	 * Request headers
	 */
	private Map<String, Object> headers;
	
	{
		properties = new HashMap<String, Object>();
		
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	/**
	 * Get the attribute value
	 * 
	 * @param name 
	 * 			The property name
	 * 
	 * @return {@link String}
	 */
	public String getProperty(String name){
		if(this.properties.get(name) != null){
			return this.properties.get(name).toString();
		}else{
			return null;
		}
	}
	
	public boolean isFormData() {
		return isFormData;
	}

	public void setFormData(boolean isFormData) {
		this.isFormData = isFormData;
	}

	public void setProperty(String key, String value){
		this.properties.put(key, value);
	}
	
	public Map<String, Object> getHeaders() {
		return headers;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getHeader(String name){
		return headers != null ? (String) headers.get(name) : null;
	}

	public void setHeaders(Map<String, Object> headers) {
		this.headers = headers;
	}

	/**
	 * Access to flow
	 * 
	 * @param name 
	 * 			The property name
	 * 
	 * @return {@link InputStream}
	 */
	public InputStream getStream(String name){
		Object obj = getProperty(name);
		if(obj != null){
			if(obj instanceof InputStream){
				return (InputStream)obj;
			}
		}
		return null;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = UriUtils.getUri(uri);
		Map<String, Object> properties = UriUtils.getProperties(uri);
		if(properties != null){
			this.properties.putAll(properties);
		}
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUriRedirect() {
		return uriRedirect;
	}

	public void setUriRedirect(String uriRedirect) {
		this.uriRedirect = uriRedirect;
	}

	public String getApiRedirect() {
		return apiRedirect;
	}

	public void setApiRedirect(String apiRedirect) {
		this.apiRedirect = apiRedirect;
	}

}
