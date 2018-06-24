package org.nico.cat.server.processer.request.chains.segment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.exception.error.StreamReadException;
import org.nico.cat.server.exception.error.UnsupportException;
import org.nico.cat.server.processer.request.chains.AbstractRequestProcess;
import org.nico.cat.server.request.Request;
import org.nico.cat.server.util.InputStreamUtils;
import org.nico.cat.server.util.UriUtils;
import org.nico.noson.Noson;

/**
 * Process packet header
 * @author nico
 * @date 2018年1月5日
 */
public class ProcessPacketBody extends AbstractRequestProcess{
	
	private final int BLOCK_LENGTH = 99999999;
	
	@Override
	public Request process(String packet, Request request) throws Exception {
		if(request.getHeaders().containsKey("Content-Length")){
			long contentLength = Long.parseLong(request.getHeader("Content-Length"));
			byte[] body = null;
			StringBuffer cache = new StringBuffer();
			while(contentLength > 0){
				try {
					body = InputStreamUtils.getBytes(request.getByteBuffer(), contentLength > BLOCK_LENGTH ? BLOCK_LENGTH : (int)contentLength);
					try {
						cache.append(new String(body, "iso-8859-1"));
					} catch (UnsupportedEncodingException e) {
						throw new UnsupportException("Unsupport the charset：" + e.getMessage());
					}
					request.setBody(new String(body, ConfigKey.server_charset));
					
					Object contentType = null;
					if((contentType = request.getHeaders().get("Content-Type")) != null) {
						if(contentType.equals("application/x-www-form-urlencoded")) {
							Map<String, Object> formParams = UriUtils.getProperties("?" + request.getBody());
							request.setBody(Noson.reversal(formParams));
						}
					}
				} catch (IOException e) {
					throw new StreamReadException(e.getMessage());
				}
				if(contentLength > BLOCK_LENGTH){
					contentLength -= BLOCK_LENGTH;
				}else{
					contentLength = 0;
				}
			}
			
		}
		return next(packet, request);
	}

}
