package org.nico.cat.server.response.buddy;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月10日 下午10:20:15
 */

public class ResponseContent {
	
	/**
	 * {@link Request}
	 */
	private Request request;
	
	/**
	 * Socket output stream.
	 */
	private OutputStream out;
	
	/**
	 * Http version. Example [HTTP/1.1]
	 */
	private String version;
	
	/**
	 * Http respond code
	 */
	private HttpCode httpcode;
	
	/**
	 * Http respond content Type
	 */
	private ContentType contentType;
	
	/**
	 * Http respond content length
	 */
	private long contentLength;
	
	/**
	 * Http respond body
	 */
	private Object responseBody;
	
	/**
	 * Http respond type
	 */
	private Object responseType;
	
	/**
	 * Character
	 */
	private String character;
	
	/**
	 * The wrapper of the output stream.
	 */
	private PrintStream stream;
	
	/**
	 * The status of Response
	 */
	private ResponseStatus status;
	
	/**
	 * Response Headers
	 */
	private ResponseHeader<String, String> headers;
	
	public ResponseContent(Request request){
		this.request = request;
		try {
			this.out = request.getClient().getOutputStream();
			this.stream = new PrintStream(this.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.headers = new ResponseHeader<String, String>();
		this.character = ConfigKey.server_charset;
		this.status = ResponseStatus.INIT;
	} 
	
	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public Object getResponseType() {
		return responseType;
	}

	public void setResponseType(Object responseType) {
		this.responseType = responseType;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public HttpCode getHttpcode() {
		return httpcode;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	
	public void push(){
		this.status = ResponseStatus.PUSHED;
	}
	
	public boolean isPushed(){
		return this.status == ResponseStatus.PUSHED;
	}

	public void setHttpcode(HttpCode httpcode) {
		this.httpcode = httpcode;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public Object getResponseBody() {
		return responseBody;
	}
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

	public PrintStream getStream() {
		return stream;
	}

	public void setStream(PrintStream stream) {
		this.stream = stream;
	}

	public ResponseHeader<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(ResponseHeader<String, String> headers) {
		this.headers = headers;
	}

	public static enum ResponseStatus{
		
		INIT,
		
		PUSHED,
		;
	}
}
