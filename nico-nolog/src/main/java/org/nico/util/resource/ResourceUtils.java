package org.nico.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月9日 下午9:20:19
 */

public class ResourceUtils {

	public static final String CLASSPATH;
	
	static{
		CLASSPATH = Thread.class.getResource("/") != null 
				? 
				Thread.class.getResource("/").getPath() 
				: 
				Thread.currentThread().getContextClassLoader().getResource("/") != null 
				? 
				Thread.currentThread().getContextClassLoader().getResource("/").getPath() 
				: 
				"";
	}
	
	public static InputStream getClasspathResource(String name) throws FileNotFoundException{
		return new FileInputStream(new File(CLASSPATH + name));
	}
	
	public static InputStream getResource(String path) throws FileNotFoundException{
		return new FileInputStream(new File(path));
	}
	
	public static FileInputStream getResourceAsFile(String path) throws FileNotFoundException{
		return new FileInputStream(new File(path));
	}
	
	public static File getFile(String path){
		return new File(path);
	}
	
}
