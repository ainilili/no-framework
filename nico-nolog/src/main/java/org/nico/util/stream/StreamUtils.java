package org.nico.util.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StreamUtils {

	private static Lock lock = new ReentrantLock();

	/** 
	 * 读取流 
	 * @param inStream 
	 * @return 字节数组 
	 * @throws IOException 
	 */  
	public static byte[] readStream(InputStream inStream) throws IOException  { 
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));  
		StringBuilder reqStr = new StringBuilder();  
		char[] buf = new char[2048];  
		int len = -1;
		while ((len = br.read(buf)) != -1) {  
			reqStr.append(new String(buf, 0, len));  
		}  
		br.close();
		return reqStr.toString().getBytes();
	} 

	/**
	 * 读取文件到字节流
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static byte[] readStream(String uri, boolean inJar) throws IOException  {  
		InputStream inputStream = null;
		if(inJar){
			inputStream = StreamUtils.class.getClassLoader().getResourceAsStream(uri);
		}else{
			File file = new File(uri);
			if(file.exists()){
				inputStream = new FileInputStream(file);
			}
		}
		if(inputStream != null){
			return readStream(inputStream);
		}
		return null; 
	}

	/**
	 * 读取文本到字符串
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public static String readStream2Str(String uri) throws IOException  {  
		byte[] results = readStream(uri, false);
		return results == null ? null : new String(results);
	}

	public static String readStream2Str(InputStream inputStream) throws IOException  {  
		byte[] results = readStream(inputStream);
		return results == null ? null : new String(results);
	}

	public static String readJarStream2Str(String uri) throws IOException  { 
		byte[] results = readStream(uri, true);
		return results == null ? null : new String(results);
	}

	/**
	 * 流写入文件
	 * @param is input Stream
	 * @param outfile out target file
	 */
	public static void streamSaveAsFile(InputStream is, File outfile) {  
		FileOutputStream fos = null;  
		try {  
			File file = outfile;  
			fos = new FileOutputStream(file);  
			byte[] buffer = new byte[1024];  
			int len;  
			while((len = is.read(buffer)) > 0){  
				fos.write(buffer, 0, len);  
			}  

		} catch (Exception e) {  
			e.printStackTrace();  
			throw new RuntimeException(e);  
		} finally {  
			try {  
				is.close();  
				fos.close();  
			} catch (Exception e2) {  
				e2.printStackTrace();  
				throw new RuntimeException(e2);  
			}  
		}  
	}  

}
