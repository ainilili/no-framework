package org.nico.cat.mvc.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtils {

	public static String readStreamByLen(InputStream inputStream, int len){
		byte[] buffer = new byte[len];
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bis.read(buffer, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new String(buffer);
	}
}
