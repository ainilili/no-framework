package org.nico.cat.server.processer.request.chains.segment;

import java.io.IOException;
import java.util.Map;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.server.exception.error.StreamReadException;
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
	
	private final int BLOCK_LENGTH = 10000917;
	
	@Override
	public Request process(String packet, Request request) throws Exception {
		String contentType = null;
		if(request.getHeaders().containsKey("Content-Type")){
			contentType = request.getHeader("Content-Type");
			if(contentType.indexOf("multipart/form-data") != -1){
				request.setFormData(true);
			}else{
				if(request.getHeaders().containsKey("Content-Length")){
					long contentLength = Long.parseLong(request.getHeader("Content-Length"));
					byte[] body = null;
					StringBuffer cache = new StringBuffer();
					while(contentLength > 0){
						try {
							body = InputStreamUtils.getBytes(request.getByteBuffer(), contentLength > BLOCK_LENGTH ? BLOCK_LENGTH : (int)contentLength);
							cache.append(new String(body, ConfigKey.server_charset));
						} catch (IOException e) {
							throw new StreamReadException(e.getMessage());
						}
						if(contentLength > BLOCK_LENGTH){
							contentLength -= BLOCK_LENGTH;
						}else{
							contentLength = 0;
						}
					}
					request.setBody(cache.toString());
					
					if(contentType.equals("application/x-www-form-urlencoded")) {
						Map<String, Object> formParams = UriUtils.getProperties("?" + request.getBody());
						request.setBody(Noson.reversal(formParams));
					}
				}
			}
		}
		return next(packet, request);
	}

}
