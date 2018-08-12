package org.nico.cat.server.request;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import org.nico.cat.server.request.buddy.RequestContent;
import org.nico.cat.server.request.extra.Cookie;
import org.nico.cat.server.request.extra.Resource;
import org.nico.cat.server.request.extra.Session;
import org.nico.cat.server.stream.ByteBuffer;

/** 
 * The client's request
 * <p>
 * contains http agreement request details
 * @author nico
 * @version createTime：2018年1月4日 下午10:20:16
 */
public class Request extends RequestContent{
	
	private Socket client;
	
	private ByteBuffer byteBuffer;
	
	private Session session;
	
	private Map<String, Cookie> cookieMap;
	
	private Map<String, Resource> resourceMap;
	
	public Request() {}

	public Request(Socket client) throws IOException{
		this.client = client;
		init();
	}
	
	public void init() throws IOException{
		this.byteBuffer = new ByteBuffer(client.getInputStream());
	}

	
	public Map<String, Resource> getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(Map<String, Resource> resourceMap) {
		this.resourceMap = resourceMap;
	}

	public Map<String, Cookie> getCookieMap() {
		return cookieMap;
	}

	public void setCookieMap(Map<String, Cookie> cookieMap) {
		this.cookieMap = cookieMap;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Socket getClient() {
		return client;
	}

	public Request setClient(Socket client) {
		this.client = client;
		return this;
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}

	public void setByteBuffer(ByteBuffer byteBuffer) {
		this.byteBuffer = byteBuffer;
	}
	
}
