package org.nico.cat.server.response.buddy;

import java.io.InputStream;

/** 
 * Response verify
 * @author nico
 * @version createTime：2018年1月13日 下午9:19:08
 */

public class ResponseVerify {
	
	/**
	 * Verify that the response body exists as a stream.
	 * @param resonseBody
	 * @return
	 */
	public static boolean responseStream(Object resonseBody){
		if(resonseBody instanceof InputStream){
			return true;
		}
		return false;
	}
}
