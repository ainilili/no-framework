package org.nico.cat.server.response.buddy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.response.buddy.ResponseHeader.Entry;
import org.nico.cat.server.response.parameter.ContentType;
import org.nico.cat.server.response.parameter.HttpCode;
import org.nico.cat.server.util.UriUtils;
import org.nico.cat.server.util.WrapperUtils;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/** 
 * The primary role of this class is to package the Response information 
 * in the form of an HTTP message
 * 
 * @author nico
 * @version createTime：2018年1月13日 下午7:33:53
 */
public class ResponseAssembly {
	
	private final static String DEFAULT_VERSION = "HTTP/1.0";
	
	private static Logging logging = LoggingHelper.getLogging("GR>> ");
	
	/**
	 * Verify Headers
	 * 
	 * @param content Response
	 */
	protected void verifyHeaders(ResponseContent content){
		content.setVersion(content.getVersion() == null ? content.getRequest().getVersion() == null ? DEFAULT_VERSION : content.getRequest().getVersion() : content.getVersion());
		content.setHttpcode(content.getHttpcode() == null ? HttpCode.HS200 : content.getHttpcode());
		content.setContentType(content.getContentType() == null ? ContentType.TEXT : content.getContentType());
		content.setCharacter(content.getCharacter() == null ? ConfigKey.server_charset : content.getCharacter());
		content.setResponseBody(content.getResponseBody() == null ? new StringBuffer(ResponseProcessKit.processHttpCode(content.getHttpcode())) : content.getResponseBody());
		try {
			content.setContentLength(content.getResponseBody() instanceof InputStream ? ((InputStream)content.getResponseBody()).available() : content.getResponseBody().toString().length());
		} catch (IOException e) {
			content.setContentLength(0);
		}
	}
	
	/**
	 * Assembly Headers
	 * 
	 * @param content
	 */
	protected void assembleHeaders(ResponseContent content){
		
		if(ResponseVerify.responseStream(content.getResponseBody())){
			content.getHeaders().putLast("Content-Length", String.valueOf(content.getContentLength()));
		}
		
		if(! content.getHeaders().containsKey("Content-Type")){
			content.getHeaders().putLast("Content-Type", WrapperUtils.wrapperContentType(content.getContentType(), UriUtils.getSuffix(content.getRequest().getUri())));
		}
		
		content.getHeaders().putLast("Accept-Ranges", "bytes");
		content.getHeaders().putLast("Connection", "keep-alive");
		content.getHeaders().putLast("Date", new Date().toString());
		content.getHeaders().putLast("Server", "Nocat");
		
		//push header
		content.getStream().println(content.getVersion() + " " + content.getHttpcode().getCode() + " " + content.getHttpcode().getMessage());
		if(content.getHeaders().size() > 0){
			for(Entry<String, String> entry: content.getHeaders().entryList()){
				String line = entry.getKey() + ":" + entry.getValue();
				content.getStream().println(line);
			}
		}
	}
	
	/**
	 * Assembly 
	 * 
	 * @param content
	 */
	protected void assembleHeadersBlank(ResponseContent content){
		content.getStream().println();
	}
	
	protected void assembleBody(ResponseContent content) throws IOException{
		if(ResponseVerify.responseStream(content.getResponseBody())){
			InputStream is = (InputStream)content.getResponseBody();
			int length = 0;
			byte[] bytes = new byte[1024];
			while ((length = is.read(bytes)) != -1) {
				content.getStream().write(bytes, 0, length);
			}
			is.close();
		}else{
			String html = content.getResponseBody().toString();
			content.getStream().println(html);
		}
//		logging.debug(content.getRequest().getUri() + " as " + content.getHeaders().getValue("Content-Type"));
	}
	
}
