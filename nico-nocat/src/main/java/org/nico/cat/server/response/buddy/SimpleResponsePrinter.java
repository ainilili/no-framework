package org.nico.cat.server.response.buddy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.nico.cat.config.ConfigKey;
import org.nico.cat.config.ConfigTemplate;
import org.nico.cat.server.request.Request;
import org.nico.log.Logging;
import org.nico.log.LoggingHelper;

/**
 * Simple implements launcher to response body
 * @author nico
 * @date 2018年1月12日
 */
public class SimpleResponsePrinter extends ResponseContent implements AbstractResponsePrinter{

	private Logging logging = LoggingHelper.getLogging(SimpleResponsePrinter.class);
	
	/**
	 * Extends super class construction
	 * @param request
	 */
	public SimpleResponsePrinter(Request request) {
		super(request);
	}

	@Override
	public <T extends InputStream> void write(T inputStream) throws IOException {
		super.setResponseBody(inputStream);
	}
	
	@Override
	public void print(String value) {
		Object tmp = null;
		if (null != (tmp = super.getResponseBody())) {
			if(tmp instanceof InputStream){
				super.setResponseBody(value);
			}else{
				((StringBuffer)super.getResponseBody()).append(value);
			}
		}else{
			super.setResponseBody(new StringBuffer(value));
		}

	}

	@Override
	public void println(String value) {
		print(value + ConfigTemplate.LINE_FEED);
	}
	
}
