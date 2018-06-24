package org.nico.cat.server.processer.buddy;

import java.io.IOException;
import java.io.InputStream;

import org.nico.log.Logging;
import org.nico.log.LoggingHelper;
import org.nico.util.stream.StreamUtils;

/**
 * Parser's buddy to helper him smoothly work
 * @author nico
 * @date 2018年1月5日
 */
public class ParserBuddy {

	private static Logging logging = LoggingHelper.getLogging(ParserBuddy.class);
	
	/**
	 * Parser the inputStream to get String
	 * 
	 * @param stream inputStream
	 * @return string
	 */
	public static String parserInputStream(InputStream stream){
		try {
			return StreamUtils.readStream2Str(stream);
		} catch (IOException e) {
			logging.warning(e);
		}
		return null;
	}
}
