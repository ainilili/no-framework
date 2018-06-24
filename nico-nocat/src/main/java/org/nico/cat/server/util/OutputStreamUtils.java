package org.nico.cat.server.util;

import java.io.OutputStream;
import java.io.PrintStream;

/** 
 * Operation output stream
 * @author nico
 * @version createTime：2018年1月7日 下午2:23:56
 */
public class OutputStreamUtils {
	
	public static void println(String packet, OutputStream outputStream){
		PrintStream stream = new PrintStream(outputStream);
		stream.println(packet);
		stream.flush();
		stream.close();
	}
}
