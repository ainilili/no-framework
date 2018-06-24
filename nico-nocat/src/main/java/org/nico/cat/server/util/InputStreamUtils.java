package org.nico.cat.server.util;

import java.io.IOException;

import org.nico.cat.server.exception.error.StreamReadException;
import org.nico.cat.server.stream.ByteBuffer;
import org.nico.util.string.StringUtils;

/** 
 * Operation input stream
 * @author nico
 * @version createTime：2018年1月7日 下午2:22:19
 */
public class InputStreamUtils {

	/**
	 * Get byte[] from inputStream
	 * 
	 * @param byteBuffer InputStream wrapper
	 * @param length Read length
	 * @return	byte[]
	 * @throws IOException 
	 */
	public static byte[] getBytes(ByteBuffer byteBuffer, int length) throws IOException{
		byte[] bytes = new byte[length];
		readPostBody(byteBuffer, bytes, length);
		return bytes;
	}

	/**
	 * Read post body in an array.
	 */
	public static int readPostBody(ByteBuffer byteBuffer, byte body[], int len)
			throws IOException {
		int offset = 0;
		do {
			int inputLen = byteBuffer.read(body, offset, len - offset);
			if (inputLen <= 0) {
				return offset;
			}
			offset += inputLen;
		} while ((len - offset) > 0);
		return len;

	}

	/**
	 * Get Header from inputStream
	 * 
	 * @param byteBuffer InputStream wrapper
	 * @return	String
	 * @throws StreamReadException 
	 * @throws IOException 
	 */
	public static String getHeader(ByteBuffer byteBuffer) throws StreamReadException{
		byte[] bytes = null;
		StringBuffer header = new StringBuffer();
		try{
			while((bytes = byteBuffer.readLine()) != null){
				String line = new String(bytes);
				header.append(line);
				if(StringUtils.isBlank(line)){
					break;
				}
			}
		}catch(IOException e){
			throw new StreamReadException("InputStream read happend an ERROR：" + e.getMessage());
		}
		return header.toString();
	} 



}
