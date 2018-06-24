package org.nico.cat.server.response.buddy;

import java.io.IOException;
import java.io.InputStream;

/**
 * Response transmitter
 * 
 * @author nico
 * @date 2018年1月12日
 */
public interface AbstractResponsePrinter {
	
	/**
	 * Write file's inputStream to client 
	 * @param bytes
	 */
	<T extends InputStream>void write(T inputStream) throws IOException;
	
	/**
	 * Print value to respond body
	 * @param value
	 */
	void print(String value);
	
	/**
	 * Print value to respond body with line feed
	 * @param value
	 */
	void println(String value);
	
}
