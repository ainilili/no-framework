package org.nico.util.stream;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import org.nico.util.string.StringUtils;

/**
 * 文件流操作工具类
 * @author nico
 *
 */
public class FileUtils {

	/**
	 * 获取指定路径下的文件
	 * @param catalogs
	 * @return
	 */
	public static File getFile(String catalogs){
		File file = new File(catalogs);
		if(!file.exists()){
			try {
				file.setWritable(true, false);
				file.setWritable(true, false);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static void write(byte[] bytes, String path, String encoding) throws IOException{
		File file = new File(path); 
		file.delete();
		file.createNewFile(); 
		FileOutputStream out = new FileOutputStream(file);
		out.write(bytes);
		out.flush();
		out.close();
	}

	public static String read(String path, String encoding) throws IOException {  
		String content = "";  
		File file = new File(path);  
		BufferedReader reader = new BufferedReader(new InputStreamReader(  
				new FileInputStream(file), encoding));  
		String line = null;  
		while ((line = reader.readLine()) != null) {  
			content += line + "\n";  
		}  
		reader.close();  
		return content;  
	}  


	/**
	 * 在文件末写入
	 * @param bytes
	 * @param catalogs
	 * @return
	 */
	public static boolean writerAfterBytes(byte[] bytes, String catalogs){
		File file = getFile(catalogs);
		RandomAccessFile resource = null;
		try{
			resource = new RandomAccessFile(file, "rw");
			long length = resource.length();
			int seek = (int) length;
			resource.seek(seek);
			resource.write(bytes);
			resource.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(resource != null){
				try {
					resource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * 在指定字节后写入
	 * @param seek
	 * @param file
	 */
	public static String writerAfterBytes(int seek, byte[] bytes, String result, String catalogs){
		File file = FileUtils.getFile(catalogs);
		RandomAccessFile resource = null;
		try{
			resource = new RandomAccessFile(file, "rw");
			long length = resource.length();
			if(seek != -1){
				byte[] cache = readBytes(resource, seek, length);
				resource.seek(seek);
				resource.write(bytes);
				resource.seek(seek + bytes.length);
				resource.write(cache);
			}else{
				seek = (int) length;
				resource.seek(seek);
				resource.write(bytes);
			}
			resource.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(resource != null){
				try {
					resource.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}


	/**
	 * 读取文件段内字节
	 * @param resource
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(RandomAccessFile resource, long start, long end) throws IOException{
		int fileLength = (int) resource.length();
		int keepLength = (int) (fileLength - start);
		byte[] cache = new byte[keepLength];
		resource.seek(start);
		resource.read(cache);
		return cache;
	}

	/**
	 * 从文件中读取内容
	 * @param resource
	 * @return
	 * @throws IOException
	 */
	public static String readFile2String(File file) throws IOException{
		FileInputStream inputStream = new FileInputStream(file);
		FileChannel channel = inputStream.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		StringBuffer stringBuffer = new StringBuffer();
		int len = 0;
		while(true){
			buffer.clear();
			if((len = channel.read(buffer)) != -1){
				stringBuffer.append(new String(buffer.array(), 0, len));
				buffer.flip();
			}else{
				break;
			}
		}
		channel.close();
		inputStream.close();
		return stringBuffer.toString();
	}

	/**
	 * 判断文件是否为图片
	 * @param file
	 * @return
	 */
	public static boolean isImage(File file){  
		boolean valid = true;
		try {
			valid = isImage(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			valid = false;
		}
		return valid;
	}  
	
	/**
	 * 判断InputStream是否为图片
	 * @param file
	 * @return
	 */
	public static boolean isImage(InputStream inputStream){
		boolean valid = true;   
		BufferedImage image;
		try {
			image=ImageIO.read(inputStream);
			if (image == null) {  
				valid = false;  
			}  
		} catch(IOException ex) {  
			valid=false;  
		}  
		return valid;
	}

	/**
	 * 获取文件后缀
	 * @param name
	 * @return
	 */
	public static String getSuffix(String name){
		if(StringUtils.isNotBlank(name)){
			String[] strs = name.split("[.]");
			if(strs != null && strs.length > 0){
				return strs[strs.length - 1];
			}
		}
		return null;
	}

	/**
	 * 获取文件后缀
	 * @param name
	 * @return
	 */
	public static String getPuffix(String name){
		if(StringUtils.isNotBlank(name)){
			String[] strs = name.split("[.]");
			if(strs != null && strs.length > 0){
				StringBuffer sb = new StringBuffer(strs[0]);
				for(int index = 1; index < strs.length - 1; index ++ ){
					sb.append("." + strs[index]);
				}
				return sb.toString();
			}
		}
		return null;
	}


}
