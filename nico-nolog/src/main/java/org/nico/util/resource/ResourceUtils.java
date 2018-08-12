package org.nico.util.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.nico.util.collection.ArrayUtils;

/** 
 * 
 * @author nico
 * @version createTime：2018年1月9日 下午9:20:19
 */

public class ResourceUtils {

	public static final String CLASSPATH;
	
	public static final boolean RUNNING_IN_JAR;
	
	private static final String CLASS_SUFFIX = ".class";
	
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
		RUNNING_IN_JAR = isJarEnvironment();
	}
	
	public static boolean isJarEnvironment() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		if(ArrayUtils.isNotBlank(stackTraceElements)) {
			StackTraceElement rootStack = stackTraceElements[stackTraceElements.length - 1];
			try {
				return isJarEnvironment(Class.forName(rootStack.getClassName()));
			} catch (ClassNotFoundException e) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isJarEnvironment(Class<?> clazz) {
		URL resourcePath = clazz.getResource(clazz.getSimpleName() + ".class");
		return resourcePath == null ? false : resourcePath.toString().startsWith("jar");
	}
	
	public static InputStream getClasspathResource(String name) throws FileNotFoundException{
		if(RUNNING_IN_JAR) {
			String resourceName = (name.startsWith("/") ?  "" : "/") + name;
			return ResourceUtils.class.getResourceAsStream(resourceName);
		}
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
